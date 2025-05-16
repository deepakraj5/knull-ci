package com.knullci.knull.application.service;

import com.knullci.knull.application.command.CreateJobCommand;
import com.knullci.knull.application.dto.CreateJobResponseDto;
import com.knullci.knull.domain.model.Job;

import java.util.List;

public interface JobService {
    CreateJobResponseDto createJob(CreateJobCommand command);
    Job editJob();
    com.knullci.knull.infrastructure.persistence.entity.Job getJobById(Integer id);
    List<Job> getAllJobs();
}
