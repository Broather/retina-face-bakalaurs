package com.bakalaurs.spring.services.Impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakalaurs.spring.models.Picture;
import com.bakalaurs.spring.repos.IPictureRepo;
import com.bakalaurs.spring.services.IPictureService;

@Service
public class PictureServiceImpl implements IPictureService {
    @Autowired
    IPictureRepo pictureRepo;

    public ArrayList<Picture> selectAllPictures() {
        return (ArrayList<Picture>) pictureRepo.findAll();
    }

    public Picture insertNewPicture(String path, String url){
        Picture picture = new Picture(path, url);
        pictureRepo.save(picture);
        return picture;
    }
}
