package org.example.cvmatcherbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Diplomes {

    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CvInfo cvInfo;
}
