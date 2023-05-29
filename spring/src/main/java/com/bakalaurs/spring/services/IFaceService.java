package com.bakalaurs.spring.services;

import com.bakalaurs.spring.models.Identity;
import com.bakalaurs.spring.models.Picture;

public interface IFaceService {
    long insertNewFace(String facePath, Picture pictureTakenFrom, Identity identity);
}
