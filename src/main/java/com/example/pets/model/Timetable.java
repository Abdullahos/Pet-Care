package com.example.pets.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Timetable {
    private int sunday = 8;
    private int monday = 8;
    private int tuesday = 8;
    private int wednesday = 8;
    private int thursday = 8;
}