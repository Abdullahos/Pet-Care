package com.example.demo.rest;

import com.example.demo.dto.ApplicationRequest;
import com.example.demo.entity.Application;
import com.example.demo.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationRest {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Void> saveApplication(@RequestBody ApplicationRequest applicationRequest) {
        applicationService.saveApplication(applicationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<Application> findAllApplications() {
        return applicationService.findAllApplications();
    }

    @GetMapping("/{id}")
    public Application showAllApplications(@PathVariable Long id) {
        return applicationService.findApplicationById(id);

    }
}
