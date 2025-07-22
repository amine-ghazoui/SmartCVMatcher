package org.example.cvmatcherbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class CvInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nom;
    private String titre;
    private String email;
    private String telephone;
    private String profil;

    @OneToMany(mappedBy = "cvInfo", cascade = CascadeType.ALL)
    private List<Competence> competences;

    @OneToMany(mappedBy = "cvInfo", cascade = CascadeType.ALL)
    private List<Langues> langues;

    @OneToMany(mappedBy = "cvInfo", cascade = CascadeType.ALL)
    private List<Diplomes> diplomes;

    @OneToMany(mappedBy = "cvInfo", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "cvInfo", cascade = CascadeType.ALL)
    private List<Projet> projets;

}
