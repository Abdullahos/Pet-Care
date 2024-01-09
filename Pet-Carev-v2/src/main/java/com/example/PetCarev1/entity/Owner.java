package com.example.PetCarev1.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owner")
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

    public Owner(){

    }

    public Owner(String name, String contact, List<Pet> petList) {
        this.name = name;
        this.contact = contact;
        this.petList = petList;
    }

    public Owner(Long id, String name, String contact, List<Pet> petList) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.petList = petList;
    }
    //    getter/setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }


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
