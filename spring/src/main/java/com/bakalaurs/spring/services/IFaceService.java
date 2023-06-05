package com.bakalaurs.spring.services;

import com.bakalaurs.spring.models.Identity;
import com.bakalaurs.spring.models.Image;

public interface IFaceService {
    long insertNewFace(String facePath, Image pictureTakenFrom, Identity identity);
}
