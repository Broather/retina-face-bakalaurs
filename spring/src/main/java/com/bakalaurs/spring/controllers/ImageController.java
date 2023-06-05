package com.bakalaurs.spring.controllers;

import java.io.IOException;
import java.io.InputStream;
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

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    IFileStorageService storageService;
    @Autowired
    IImageRepo pictureRepo;

    @GetMapping("/list")
    public String getListpictures(Model model) {
        List<Image> images = storageService.loadAll().map(path -> {
            String file_path = path.toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString())
                    .build()
                    .toString();

            return pictureRepo.save(new Image(file_path, url));
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
    public String searchImage(Model model, @PathVariable String filename) {
        System.out.println(filename);
        ProcessBuilder processBuilder = new ProcessBuilder("python", Path.of("search.py").toString(), filename);
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try {
            processBuilder.start();
        } catch (IOException e) {
        }
        return "debug-page.html";
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        file.getFilename() + "\"")
                .body(file);
    }

    // @GetMapping("/delete/{filename:.+}")
    // public String deleteImage(@PathVariable String filename, Model model,
    // RedirectAttributes redirectAttributes) {
    // try {
    // boolean existed = storageService.delete(filename);

    // if (existed) {
    // redirectAttributes.addFlashAttribute("message", "Delete the image
    // successfully: " + filename);
    // } else {
    // redirectAttributes.addFlashAttribute("message", "The image does not exist!");
    // }
    // } catch (Exception e) {
    // redirectAttributes.addFlashAttribute("message",
    // "Could not delete the image: " + filename + ". Error: " + e.getMessage());
    // }

    // return "redirect:/pictures";
    // }
}