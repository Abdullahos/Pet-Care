package com.example.PetCarev1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class RequestNewService {
    public Long petId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime dueDate;
    public List<Long> skillsIds;
}
