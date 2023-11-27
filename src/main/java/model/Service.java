package model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING) private Software software;
    @Enumerated(EnumType.STRING) private OpSys opSys;

    @Override
    public String toString(){
        return this.id.toString() + " " + this.software.name() + " para " + this.opSys.name();
    }

}
