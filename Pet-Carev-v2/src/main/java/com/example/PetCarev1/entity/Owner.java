package com.example.PetCarev1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

//    fields
    @Id
    @SequenceGenerator(
            name = "owner_sequence",
            sequenceName = "owner_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "owner_sequence"
    )
    private Long id;


    @Column(name = "name")
    private String name;

    @Column
    private String contact;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<Pet> petList;

    //    Convenience Method
    public void add(Pet pet){
        if(petList == null){
            synchronized (this){
                if (petList == null){
                    petList = new ArrayList<>();
                }
            }
        }

        pet.setOwner(this);
        petList.add(pet);
    }

//    to string

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", petList=" + petList +
                '}';
    }
}
