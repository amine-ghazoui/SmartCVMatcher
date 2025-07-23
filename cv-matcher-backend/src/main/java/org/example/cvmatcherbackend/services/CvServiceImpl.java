package org.example.cvmatcherbackend.services;

import lombok.AllArgsConstructor;
import org.example.cvmatcherbackend.dtos.CvDTO;
import org.example.cvmatcherbackend.entities.Competence;
import org.example.cvmatcherbackend.entities.CvInfo;
import org.example.cvmatcherbackend.mappers.CvMapperImpl;
import org.example.cvmatcherbackend.repositories.CompetenceRepository;
import org.example.cvmatcherbackend.repositories.CvInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CvServiceImpl implements CvService {

    private final CompetenceRepository competenceRepository;
    private CvInfoRepository cvInfoRepository;
    private CvMapperImpl cvMapper;

    @Override
    public void saveCv(CvDTO cvInfoDTO) {
        if (cvInfoDTO == null){
            throw new IllegalArgumentException("cvInfoDTO cannot be null");
        }
        CvInfo cvInfo = cvMapper.fromCvDTO(cvInfoDTO);

        List<Competence> competences = new ArrayList<>();
        if(cvInfo.getCompetences() != null){
            for (Competence comp : cvInfo.getCompetences()){
                Optional<Competence> existing = competenceRepository.findByNom(comp.getNom());

                if(existing.isPresent()){ // Vérifie si une compétence existe
                    competences.add(existing.get()); // Ajoute la compétence existante à la liste
                }
                else {
                    competences.add(competenceRepository.save(comp));
                }
            }
        }
        cvInfo.setCompetences(competences);
        cvInfoRepository.save(cvInfo);
    }

    @Override
    public List<CvDTO> searchByCompetence(String competence) {
        return List.of();
    }

    @Override
    public List<CvDTO> searchByNom(String name) {
        return List.of();
    }

    @Override
    public List<CvDTO> searchByTitre(String title) {
        return List.of();
    }

    @Override
    public List<CvDTO> searchByProfil(String profile) {
        return List.of();
    }

    @Override
    public List<CvDTO> searchByDiplome(String diploma) {
        return List.of();
    }

    @Override
    public List<CvDTO> searchByExperienceEntreprise(String entreprise) {
        return List.of();
    }

    @Override
    public List<CvDTO> searchByLangue(String language) {
        return List.of();
    }
}
