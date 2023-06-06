package com.bakalaurs.spring.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "face_table") // table in DB
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Face {
    @Column(name = "Idf")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private long idf;

    @Column(name = "FacePath")
    @NotNull
    @Pattern(regexp = ".+\\.(png|jpeg|jpg)")
    private String facePath;

    @ManyToOne
    @JoinColumn(name = "Idp")
    private Image pictureTakenFrom;

    @ManyToOne
    @JoinColumn(name = "Idi")
    private Identity identity;

    public Face(@NotNull @Pattern(regexp = ".+\\.(png|jpeg|jpg)") String facePath, Image pictureTakenFrom,
            Identity identity) {
        this.facePath = facePath;
        this.pictureTakenFrom = pictureTakenFrom;
        this.identity = identity;
    }

}
