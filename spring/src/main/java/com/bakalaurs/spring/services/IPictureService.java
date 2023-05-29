package com.bakalaurs.spring.services;

import java.util.ArrayList;

import com.bakalaurs.spring.models.Picture;

public interface IPictureService {
    ArrayList<Picture> selectAllPictures();

    Picture insertNewPicture(String path, String url);
}
