package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class ReviewRequest {
    @NotBlank(message = "application id must not be blank")
    public Long applicationId;
    @NotBlank(message = "skill id must not be blank")
    public Long skillId;
    @NotBlank(message = "role must not be blank")
    public String role;
    @NotBlank(message = "decision must not be blank")
    public String decision;
}
