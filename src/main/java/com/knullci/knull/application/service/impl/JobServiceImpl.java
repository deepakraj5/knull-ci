package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.command.CreateJobCommand;
import com.knullci.knull.application.dto.CreateJobResponseDto;
import com.knullci.knull.application.service.JobService;
import com.knullci.knull.domain.factory.JobFactory;
import com.knullci.knull.domain.model.Job;
import com.knullci.knull.infrastructure.persistence.repository.JobRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    @SneakyThrows
    public CreateJobResponseDto createJob(CreateJobCommand command) {

        logger.info("Creating job with name: " + command.getName());

        var existingJob = this.jobRepository.getJobByName(command.getName());

        if (!Objects.isNull(existingJob)) {
            logger.warn("Job with name: " + command.getName() + " already exists");
            throw new Exception("Job with name: " + command.getName() + " already exists");
        }

        Job job = JobFactory.createFromCommand(command);

        var createdJob = this.jobRepository.save(job.toEntity());
        job.setId(createdJob.getId());

        logger.info("New job created with id: " + job.getId());

        return JobFactory.createJobResponseDto(job);
    }

    @Override
    public Job editJob() {
        return null;
    }

    @Override
    @SneakyThrows
    public com.knullci.knull.infrastructure.persistence.entity.Job getJobById(Integer id) {
        Optional<com.knullci.knull.infrastructure.persistence.entity.Job> job = this.jobRepository.findById(id);
        if (job.isEmpty()) {
            logger.warn("No job found for the given id: " + id);
            throw new Exception("No job found for the given id: " + id);
        }

        return job.get();
    }

    @Override
    public List<Job> getAllJobs() {
        return null;
    }
}
