package org.example.cvmatcherbackend.mappers;

import org.example.cvmatcherbackend.dtos.*;
import org.example.cvmatcherbackend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CvMapperImpl {

    public CvDTO fromCvInfo(CvInfo cvInfo) {
        CvDTO cvDTO = new CvDTO();
        BeanUtils.copyProperties(cvInfo, cvDTO);

        if (cvInfo.getCompetences() != null) {
            cvDTO.setCompetences(
                    cvInfo.getCompetences().stream()
                            .map(this::fromCompetence)
                            .collect(Collectors.toList())
            );
        }

        if (cvInfo.getLangues() != null) {
            cvDTO.setLangues(
                    cvInfo.getLangues().stream()
                            .map(this::fromLangues)
                            .collect(Collectors.toList())
            );
        }

        if (cvInfo.getDiplomes() != null) {
            cvDTO.setDiplomes(
                    cvInfo.getDiplomes().stream()
                            .map(this::fromDiplomes)
                            .collect(Collectors.toList())
            );
        }

        if (cvInfo.getExperiences() != null) {
            cvDTO.setExperiences(
                    cvInfo.getExperiences().stream()
                            .map(this::fromExperience)
                            .collect(Collectors.toList())
            );
        }

        if (cvInfo.getProjets() != null) {
            cvDTO.setProjets(
                    cvInfo.getProjets().stream()
                            .map(this::fromProjet)
                            .collect(Collectors.toList())
            );
        }

        return cvDTO;
    }

    public CvInfo fromCvDTO(CvDTO cvDTO) {
        CvInfo cvInfo = new CvInfo();
        BeanUtils.copyProperties(cvDTO, cvInfo);

        // Mapping compétences
        if (cvDTO.getCompetences() != null) {
            List<Competence> competences = cvDTO.getCompetences().stream()
                    .map(this::fromCompetenceDTO)
                    .collect(Collectors.toList());
            cvInfo.setCompetences(competences);
        }

        // Mapping langues
        if (cvDTO.getLangues() != null) {
            List<Langues> langues = cvDTO.getLangues().stream()
                    .map(this::fromLanguesDTO)
                    .collect(Collectors.toList());
            cvInfo.setLangues(langues);
        }

        // Mapping diplomes
        if (cvDTO.getDiplomes() != null) {
            List<Diplomes> diplomes = cvDTO.getDiplomes().stream()
                    .map(this::fromDiplomesDTO)
                    .collect(Collectors.toList());
            cvInfo.setDiplomes(diplomes);
        }

        // Mapping expériences
        if (cvDTO.getExperiences() != null) {
            List<Experience> experiences = cvDTO.getExperiences().stream()
                    .map(this::fromExperienceDTO)
                    .collect(Collectors.toList());
            cvInfo.setExperiences(experiences);
        }

        // Mapping projets
        if (cvDTO.getProjets() != null) {
            List<Projet> projets = cvDTO.getProjets().stream()
                    .map(this::fromProjetDTO)
                    .collect(Collectors.toList());
            cvInfo.setProjets(projets);
        }

        return cvInfo;
    }

    //**********************************************************************************

    public CompetenceDTO fromCompetence(Competence competence) {
        CompetenceDTO competenceDTO = new CompetenceDTO();
        BeanUtils.copyProperties(competence, competenceDTO);
        return competenceDTO;
    }

    public Competence fromCompetenceDTO(CompetenceDTO competenceDTO) {
        Competence competence = new Competence();
        BeanUtils.copyProperties(competenceDTO, competence);
        return competence;
    }

    //**********************************************************************************

    public ExperienceDTO fromExperience(Experience experience) {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        BeanUtils.copyProperties(experience, experienceDTO);
        return experienceDTO;
    }

    public Experience fromExperienceDTO(ExperienceDTO experienceDTO) {
        Experience experience = new Experience();
        BeanUtils.copyProperties(experienceDTO, experience);
        return experience;
    }

    //**********************************************************************************

    public DiplomesDTO fromDiplomes(Diplomes diplomes) {
        DiplomesDTO diplomesDTO = new DiplomesDTO();
        BeanUtils.copyProperties(diplomes, diplomesDTO);
        return diplomesDTO;
    }

    public Diplomes fromDiplomesDTO(DiplomesDTO diplomesDTO) {
        Diplomes diplomes = new Diplomes();
        BeanUtils.copyProperties(diplomesDTO, diplomes);
        return diplomes;
    }

    //**********************************************************************************

    public LanguesDTO fromLangues(Langues langues){
        LanguesDTO languesDTO = new LanguesDTO();
        BeanUtils.copyProperties(langues, languesDTO);
        return languesDTO;
    }

    public Langues fromLanguesDTO(LanguesDTO languesDTO) {
        Langues langues = new Langues();
        BeanUtils.copyProperties(languesDTO, langues);
        return langues;
    }

    //**********************************************************************************

    public ProjetDTO fromProjet(Projet projet){
        ProjetDTO projetDTO = new ProjetDTO();
        BeanUtils.copyProperties(projet, projetDTO);
        return projetDTO;
    }

    public Projet fromProjetDTO(ProjetDTO projetDTO) {
        Projet projet = new Projet();
        BeanUtils.copyProperties(projetDTO, projet);
        return projet;
    }
}
