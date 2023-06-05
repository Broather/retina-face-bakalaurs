package com.bakalaurs.spring.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakalaurs.spring.models.Face;
import com.bakalaurs.spring.models.Identity;
import com.bakalaurs.spring.models.Image;
import com.bakalaurs.spring.repos.IFaceRepo;
import com.bakalaurs.spring.services.IFaceService;

@Service
public class FaceServiceImpl implements IFaceService {
    @Autowired
    IFaceRepo faceRepo;

    public long insertNewFace(String facePath, Image pictureTakenFrom, Identity identity) {
        Face face = new Face(facePath, pictureTakenFrom, identity);
        faceRepo.save(face);
        return face.getIdf();
    }
}
