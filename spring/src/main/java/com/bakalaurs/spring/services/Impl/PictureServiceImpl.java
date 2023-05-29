package com.bakalaurs.spring.services.Impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.bakalaurs.spring.models.Picture;
import com.bakalaurs.spring.repos.IPictureRepo;
import com.bakalaurs.spring.services.IPictureService;

public class PictureServiceImpl implements IPictureService {
    @Autowired
    IPictureRepo pictureRepo;

    public ArrayList<Picture> selectAllPictures() {
        return (ArrayList<Picture>) pictureRepo.findAll();
    }
}
