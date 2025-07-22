package org.example.cvmatcherbackend.services;

import org.example.cvmatcherbackend.dtos.CvDTO;

import java.util.List;

public interface CvService {

    void saveCv(CvDTO cvInfoDTO);
    List<CvDTO> searchByCompetence(String competence);
    List<CvDTO> searchByNom(String name);
    List<CvDTO> searchByTitre(String title);
    List<CvDTO> searchByProfil(String profile);
    List<CvDTO> searchByDiplome(String diploma);
    List<CvDTO> searchByExperienceEntreprise(String entreprise);
    List<CvDTO> searchByLangue(String language);

}
