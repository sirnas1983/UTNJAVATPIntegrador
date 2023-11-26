package model;

import lombok.Data;

import javax.persistence.*;
import java.time.Period;

@Data
@Entity
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    private Double resolutionTime;

    private String description;
}
