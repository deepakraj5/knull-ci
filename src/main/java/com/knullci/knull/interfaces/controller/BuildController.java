package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.command.CreateBuildCommand;
import com.knullci.knull.application.service.BuildService;
import com.knullci.knull.application.service.StageService;
import com.knullci.knull.domain.model.Build;
import com.knullci.knull.domain.model.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/builds")
@CrossOrigin(value = "*")
public class BuildController {

    private final BuildService buildService;
    private final StageService stageService;

    public BuildController(BuildService buildService, StageService stageService) {
        this.buildService = buildService;
        this.stageService = stageService;
    }

    @GetMapping
    public ResponseEntity<List<Build>> getBuildByStatus(@RequestParam(defaultValue = "ALL") String status) {
        List<Build> builds = this.buildService.getBuilds(status);

        return ResponseEntity.ok(builds);
    }

    @GetMapping("/{id}/stages")
    public ResponseEntity<List<Stage>> getBuildStages(@PathVariable("id") Integer id) {
        List<Stage> stages = this.stageService.getAllStageByBuildId(id);

        return ResponseEntity.ok(stages);
    }

    @PostMapping("/{buildId}")
    public ResponseEntity<String> createNewBuild(@PathVariable Integer buildId) {
        this.buildService.createNewBuild(
                new CreateBuildCommand(buildId)
        );

        return ResponseEntity.ok("Created new build");
    }

}
