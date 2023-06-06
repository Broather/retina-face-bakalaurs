package com.bakalaurs.spring.services.Impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakalaurs.spring.models.Image;
import com.bakalaurs.spring.repos.IImageRepo;
import com.bakalaurs.spring.services.IImageService;

@Service
public class ImageServiceImpl implements IImageService {
    @Autowired
    IImageRepo imageRepo;

    public ArrayList<Image> selectAllImages() {
        return (ArrayList<Image>) imageRepo.findAll();
    }

    public Image insertNewImage(String name, String url) {
        Image picture = new Image(name, url);
        imageRepo.save(picture);
        return picture;
    }

    public Image selectImageByName(String name) {
        return imageRepo.findImageByName(name);
    }
}
