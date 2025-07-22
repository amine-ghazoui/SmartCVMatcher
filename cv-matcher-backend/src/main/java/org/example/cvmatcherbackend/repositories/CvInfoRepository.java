package org.example.cvmatcherbackend.repositories;

import org.example.cvmatcherbackend.entities.CvInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvInfoRepository extends JpaRepository<CvInfo, Long> {
}
