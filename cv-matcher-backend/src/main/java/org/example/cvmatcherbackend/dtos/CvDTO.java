package org.example.cvmatcherbackend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CvDTO {

    public Long Id;
    public String nom;
    public String titre;
    public String email;
    public String telephone;
    public String profil;

    public List<CompetenceDTO> competences;
    public List<LanguesDTO> langues;
    public List<DiplomesDTO> diplomes;

    public List<ExperienceDTO> experiences;
    public List<ProjetDTO> projets;
}
