package com.bakalaurs.spring.models;

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

@Table(name = "identity_table") // table in DB
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Identity {
    @Column(name = "Idi")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private long idi;

    @Column(name = "Name")
    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "[A-Z]{1}[a-z]+")
    private String name;

    @Column(name = "Surname")
    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "[A-Z]{1}[a-z]+")
    private String surname;

    @OneToMany(mappedBy = "identity")
    private Collection<Face> faces;

    public Identity(@NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[A-Z]{1}[a-z]+") String name,
            @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[A-Z]{1}[a-z]+") String surname) {
        this.name = name;
        this.surname = surname;
    }

}
