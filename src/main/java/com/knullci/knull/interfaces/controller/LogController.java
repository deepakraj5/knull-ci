package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.dto.LogMessage;
import com.knullci.knull.application.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@CrossOrigin(origins = "*")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/{buildId}")
    public ResponseEntity<List<LogMessage>> getLogsByBuildId(@PathVariable Integer buildId) {
        List<LogMessage> logs = this.logService.getLogsByBuildId(buildId);

        return ResponseEntity.ok(logs);
    }
}
