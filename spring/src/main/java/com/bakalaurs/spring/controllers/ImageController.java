package com.bakalaurs.spring.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bakalaurs.spring.models.Image;
import com.bakalaurs.spring.repos.IImageRepo;
import com.bakalaurs.spring.services.IFileStorageService;
import com.bakalaurs.spring.services.IImageService;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    IFileStorageService storageService;
    @Autowired
    IImageRepo imageRepo;
    @Autowired
    IImageService imageService;

    @GetMapping("/list")
    public String getListpictures(Model model) {
        // TODO: initialize imageRepo in storageService.init() and call
        // imageRepo.findAll() (beans)
        List<Image> images = storageService.loadAll().map(path -> {
            String name = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString())
                    .build()
                    .toString();
            if (!imageRepo.existsImageByName(name)) {
                return imageRepo.save(new Image(name, url));
            } else {
                return new Image(name, url);
            }
        }).collect(Collectors.toList());
        model.addAttribute("images", images);
        return "image-list.html";
    }

    @GetMapping("/upload")
    public String getUploadImage(Model model) {
        return "image-upload.html";
    }

    @PostMapping("/upload")
    public String postUploadImage(Model model, @RequestParam("file") MultipartFile file) {
        try {
            storageService.save(file);
            model.addAttribute("message", "Uploaded the image successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not upload the image: " + file.getOriginalFilename() + "\nError: " + e.toString());
        }
        return "image-upload";
    }

    @GetMapping("/search/{filename:.+}")
    public String searchImage(Model model, @PathVariable(name = "filename") String image_name_to_search_by)
            throws Exception {
        Image image_to_search_by = imageService.selectImageByName(image_name_to_search_by);

        // search.py takes image_name and gets all images from /images where .verify ==
        // true
        ProcessBuilder processBuilder = new ProcessBuilder("python", "search.py", image_to_search_by.getName());
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        Process process = processBuilder.start();
        int exit_code = process.waitFor();

        // TODO: call .detect_faces in java just to check more than one face here before
        // calling search.py
        if (exit_code == 69) {
            System.out.println();
            model.addAttribute("message", "more than one faces in image: " + image_to_search_by.getName());
            return "message-page.html";
        }
        // read the temp-image-names.txt for the names found by search.py
        List<Image> images = Files.lines(Path.of("temp-image-names.txt")).map(image_name -> {
            return imageService.selectImageByName(image_name);
        }).collect(Collectors.toList());

        model.addAttribute("image_searched_by", image_to_search_by);
        model.addAttribute("images", images);

        return "image-list.html";

    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}