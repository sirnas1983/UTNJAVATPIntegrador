package model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("technician")
public class Technician extends Person{

    public Technician(){
        this.specialities = new ArrayList<>();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "technician_specialities")
    private List<Service> specialities;

    @Setter @Getter
    private Boolean isAvailable;

    @Setter @Getter
    private String whatsapp;

    @Setter @Getter
    @Enumerated(EnumType.STRING)
    private FavoriteCom favoriteCom;

    @Override
    public String toString(){
        return this.getId().toString() + "- " + this.getName() + " - Esp: " + this.getSpecialities()+ " - Disp:" + (this.isAvailable ? "si" : "no");
    }
}
