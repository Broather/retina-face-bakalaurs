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
@RequestMapping("/image")
public class ImageController {

    @Autowired
    IFileStorageService storageService;
    @Autowired
    IPictureService pictureService;

    @GetMapping("list")
    public String getListpictures(Model model) {
        model.addAttribute("images", pictureService.selectAllPictures());
        return "image-list";
    }

    @GetMapping("/upload")
    public String getUploadImage(Model model) {
        return "image-upload";
    }

    @PostMapping("/upload")
    public String postUploadImage(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            storageService.save(file);
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
            // TODO: implement
            // // save it in ./pictures
            // long idp = pictureService.insertNewPicture(picture_path, null);
            // // pass picture_path to detect.py which saves the faces in ./all_faces and
            // // returns list[face_path]
            // ArrayList<Long> facesToIdentitfy = new ArrayList<Long>();
            // for (String face_path : face_paths) {
            // long idf = faceService.insertNewFace(face_path,
            // pictureService.selectPictureById(idp));
            // facesToIdentitfy.add(idf);
            // }

            // for (long idf : facesToIdentitfy) {
            // // pass faceService.selectFaceById(idf).getFacePath() to identify.py which
            // // returns list[face_path]
            // // handle empty list
            // // [facePath] ->
            // // [identities (Idi)] ->
            // // histogram the occurences ->
            // // identity with the most occurences ->
            // // selectFaceById(idf).setIdi(identity);
            // }
        } catch (Exception e) {
            model.addAttribute("message",
                    "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage());
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