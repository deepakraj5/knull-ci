package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.service.BuildService;
import com.knullci.knull.domain.model.Build;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/builds")
@CrossOrigin(value = "*")
public class BuildController {

    private final BuildService buildService;

    public BuildController(BuildService buildService) {
        this.buildService = buildService;
    }

    @GetMapping
    public ResponseEntity<List<Build>> getBuildByStatus(@RequestParam(defaultValue = "QUEUE") String status) {
        List<Build> builds = this.buildService.getBuilds(status);

        return ResponseEntity.ok(builds);
    }

}
