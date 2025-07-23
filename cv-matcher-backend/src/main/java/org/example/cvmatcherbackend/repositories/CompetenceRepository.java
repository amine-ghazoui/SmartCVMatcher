package org.example.cvmatcherbackend.repositories;

import org.example.cvmatcherbackend.entities.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    Optional<Competence> findByNom(String name);
}
