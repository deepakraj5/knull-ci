package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.dto.LogStreamRequest;
import com.knullci.knull.application.service.LogService;
import lombok.SneakyThrows;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @MessageMapping("/logs")
    @SneakyThrows
    public void streamLogs(LogStreamRequest request) {
        this.logService.streamLogsByBuildId(request.getBuildId());
    }

}
