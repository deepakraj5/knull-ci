package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.dto.LogStreamResponse;
import com.knullci.knull.application.service.LogService;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;

@Service
public class LogServiceImpl implements LogService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public LogServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    @SneakyThrows
    public void streamLogsByBuildId(Integer buildId) {
        String destination = "/topic/logs." + buildId;

        File logFile = new File("/Users/deepakraj/Documents/Deepak/workspace/test-next-ssr/log.txt");

        try (RandomAccessFile reader = new RandomAccessFile(logFile, "r")) {
            String line;

            while ((line = reader.readLine()) != null) {
                simpMessagingTemplate.convertAndSend(destination, new LogStreamResponse(line));
            }
        }
    }
}
