package com.example.demo.rest;

import com.example.demo.dto.ReviewRequest;
import com.example.demo.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupRest {
    private final GroupService groupService;

    @PostMapping
    public void makeDecision(@Valid @RequestBody ReviewRequest request) {
        groupService.makeDecision(request);
    }
}
