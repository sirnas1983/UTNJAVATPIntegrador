package model;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class")
@RequiredArgsConstructor
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String name;

    protected String adress;

    protected String idNum;

    protected String email;

    protected DocTipe docTipe;

    public String toString(){
        return this.id.toString() + " - " + this.name;
    }


}
