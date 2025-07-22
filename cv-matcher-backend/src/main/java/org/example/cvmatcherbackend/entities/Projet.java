package org.example.cvmatcherbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Projet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String titre;
    public String description;
    public String technologie;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    public CvInfo cvInfo;
}
