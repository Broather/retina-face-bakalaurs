package com.bakalaurs.spring.controllers;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bakalaurs.spring.models.Picture;
import com.bakalaurs.spring.repos.IPictureRepo;
import com.bakalaurs.spring.services.IFileStorageService;
import com.bakalaurs.spring.services.IPictureService;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    IFileStorageService storageService;
    @Autowired
    IPictureRepo pictureRepo;

    @GetMapping("/list")
    public String getListpictures(Model model) {
        List<Picture> pictures = storageService.loadAll().map(path -> {
            String name = path.getFileName().toString();
            String file_path = path.toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build()
                    .toString();

            return pictureRepo.save(new Picture(name, file_path, url));
        }).collect(Collectors.toList());
        model.addAttribute("images", pictures);
        return "image-list.html";
    }

    @GetMapping("/upload")
    public String getUploadImage(Model model) {
        return "image-upload.html";
    }

    @PostMapping("/upload")
    public String postUploadImage(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            storageService.save(file);
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
            // TODO: implement

        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.toString());
        }
        return "image-upload";
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