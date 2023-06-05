package com.bakalaurs.spring.services;

import java.util.ArrayList;

import com.bakalaurs.spring.models.Image;

public interface IImageService {
    ArrayList<Image> selectAllImages();

    Image insertNewImage(String path, String url);
}
