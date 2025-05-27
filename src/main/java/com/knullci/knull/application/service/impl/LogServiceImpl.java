package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.dto.LogMessage;
import com.knullci.knull.application.service.LogService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Value("${knull.workspace.directory}")
    private String workspaceDirectory;

    @Override
    @SneakyThrows
    public List<LogMessage> getLogsByBuildId(Integer buildId) {

        String logFileLocation =  workspaceDirectory + "/logs/" + buildId + "/logs.txt";
        List<LogMessage> logs = new ArrayList<>();

        Path path = Paths.get(logFileLocation);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFileLocation)))
        {
            String line = reader.readLine();
            while (line != null) {
                logs.add(new LogMessage(line));
                line = reader.readLine();
            }
        }

        return logs;
    }
}
