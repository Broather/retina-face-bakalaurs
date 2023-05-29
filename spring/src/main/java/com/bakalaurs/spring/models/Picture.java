package com.bakalaurs.spring.models;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "picture_table") // table in DB
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Picture {
    @Column(name = "Idp")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private long idp;

    @Column(name = "PicturePath")
    @NotNull
    @Pattern(regexp = ".+\\.(png|jpeg|jpg)")
    private String picturePath;

    @OneToMany(mappedBy = "pictureTakenFrom")
    @ToString.Exclude
    private Collection<Face> faces;
}
