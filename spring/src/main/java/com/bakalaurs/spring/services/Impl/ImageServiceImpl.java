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
    IImageRepo pictureRepo;

    public ArrayList<Image> selectAllImages() {
        return (ArrayList<Image>) pictureRepo.findAll();
    }

    public Image insertNewImage(String path, String url) {
        Image picture = new Image(path, url);
        pictureRepo.save(picture);
        return picture;
    }
}
