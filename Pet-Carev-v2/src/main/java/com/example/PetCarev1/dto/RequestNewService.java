package com.example.PetCarev1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestNewService {
    private Long petId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
    private List<Long> skillsIds;
}
