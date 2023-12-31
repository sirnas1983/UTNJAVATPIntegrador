package model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Person client;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Person technician;
    private Boolean isResolved;
    private Double resolutionTime;


    @Override
    public String toString(){
        return  this.id.toString() + " -Cli: " + this.client.getName() + " -Tec: " + this.technician.getName() +
                " -Descripcion:"+ this.problem.getDescription() + " -Tiempo: " + this.resolutionTime.toString() +
                " -Resuelto: " + (this.isResolved ? "si" : "no");
    }

}
