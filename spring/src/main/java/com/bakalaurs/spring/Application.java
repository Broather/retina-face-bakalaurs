package com.bakalaurs.spring;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import com.bakalaurs.spring.models.Identity;
import com.bakalaurs.spring.repos.IFaceRepo;
import com.bakalaurs.spring.repos.IIdentityRepo;
import com.bakalaurs.spring.repos.IImageRepo;
import com.bakalaurs.spring.services.IFaceService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner testModel(IFaceService faceService,
            IImageRepo pictureRepo,
            IIdentityRepo identityRepo) {

        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                // localhost:8080/h2-console
                // initialise face_table with identified faces from ./identified_faces.
                // with a manually assigned identity and pictureTakenFrom = null
                Identity jc = new Identity("Jackie", "Chan");
                Identity bg = new Identity("Bill", "Gates");
                identityRepo.save(jc);
                identityRepo.save(bg);

                faceService.insertNewFace("./identified_faces\\Bill_Gates_0001.jpg", null, bg);
                faceService.insertNewFace("./identified_faces\\Bill_Gates_0002.jpg", null, bg);
                faceService.insertNewFace("./identified_faces\\Bill_Gates_0003.jpg", null, bg);
                faceService.insertNewFace("./identified_faces\\Jackie_Chan_0001.jpg", null, jc);
                faceService.insertNewFace("./identified_faces\\Jackie_Chan_0002.jpg", null, jc);
                faceService.insertNewFace("./identified_faces\\Jackie_Chan_0003.jpg", null, jc);
            }
        };

    }
}
