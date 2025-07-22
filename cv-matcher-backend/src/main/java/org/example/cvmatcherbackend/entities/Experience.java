package org.example.cvmatcherbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Experience {

    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String entreprise;
    private String description;
    private String technologie;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CvInfo cvInfo;
}
