package org.example.cvmatcherbackend.web;

import lombok.AllArgsConstructor;
import org.example.cvmatcherbackend.dtos.CvDTO;
import org.example.cvmatcherbackend.services.CvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cvs")
public class CvController {

    private CvService cvService;

    @PostMapping
    public ResponseEntity<?> recevoirCv(@RequestBody CvDTO cvDTO) {
        cvService.saveCv(cvDTO);
        return ResponseEntity.ok("CV enregistré avec succès !");
    }
}
