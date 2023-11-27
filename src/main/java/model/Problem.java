package model;

import lombok.Data;

import javax.persistence.*;

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

    @Override
    public String toString(){
        return this.id.toString() + " " + this.description + " - " + this.service + " - Tiempo aprox:" + this.getResolutionTime() + " hs.";
    }
}
