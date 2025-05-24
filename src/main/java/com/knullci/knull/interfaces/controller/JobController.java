package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.command.CreateJobCommand;
import com.knullci.knull.application.dto.CreateJobRequestDto;
import com.knullci.knull.application.dto.CreateJobResponseDto;
import com.knullci.knull.application.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<CreateJobResponseDto> createJob(@RequestBody CreateJobRequestDto createJob) {
        CreateJobResponseDto job = this.jobService.createJob(
                new CreateJobCommand(createJob)
        );

        return ResponseEntity.ok(job);
    }

}
