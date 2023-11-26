package model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("client")
public class Client extends Person{

    public Client(){
        this.services = new ArrayList<>();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "client_services")
    private List<Service> services;

    @Override
    public String toString(){
        return this.getId().toString() + "- " + this.getName();
    }
}
