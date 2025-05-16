package com.knullci.necrosword.application.executor.impl;

import com.knullci.necrosword.application.dto.CommandExecutorResult;
import com.knullci.necrosword.application.executor.CommandExecutor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandExecutorImpl implements CommandExecutor {

    private final Logger logger = LoggerFactory.getLogger(CommandExecutorImpl.class);

    @Override
    @SneakyThrows
    public CommandExecutorResult execute(String cmd, File workDirectory) {

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

        List<String> output = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
                output.add(line);
            }
        }

        int exitCode = process.waitFor();
        return new CommandExecutorResult(exitCode, output);
    }
}
