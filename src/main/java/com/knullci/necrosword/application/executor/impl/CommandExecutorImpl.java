package com.knullci.necrosword.application.executor.impl;

import com.knullci.knull.application.dto.LogStreamResponse;
import com.knullci.necrosword.application.dto.CommandExecutorResult;
import com.knullci.necrosword.application.executor.CommandExecutor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandExecutorImpl implements CommandExecutor {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public CommandExecutorImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(CommandExecutorImpl.class);

    @Override
    @SneakyThrows
    public CommandExecutorResult execute(String cmd, File workDirectory) {
        return this.executeCmd(cmd, workDirectory, null);
    }

    @Override
    @SneakyThrows
    public CommandExecutorResult execute(String cmd, File workDirectory, Integer buildId) {
        return this.executeCmd(cmd, workDirectory, buildId);
    }

    @SneakyThrows
    private CommandExecutorResult executeCmd(String cmd, File workDirectory, Integer buildId) {
        List<String> commands = new ArrayList<>();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            commands.add("cmd.exe");
            commands.add("/c");
        } else {
            commands.add("bash");
            commands.add("-c");
        }

        commands.add(cmd);

        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        builder.directory(workDirectory);

        Process process = builder.start();

        String destination = buildId != null ? "/topic/logs." + buildId : null;

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                FileOutputStream fos = new FileOutputStream(workDirectory + "/log.txt", true);
                DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(fos));
        ) {
            outputStream.writeBytes(cmd + System.lineSeparator());

            String line;
            while ((line = reader.readLine()) != null) {
                outputStream.writeBytes(line + System.lineSeparator());

                if (destination != null) {
                    simpMessagingTemplate.convertAndSend(destination, new LogStreamResponse(line));
                }
            }
        }

        int exitCode = process.waitFor();
        return new CommandExecutorResult(exitCode);
    }
}
