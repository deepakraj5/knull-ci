package com.knullci.necrosword.application.executor.impl;

import com.knullci.necrosword.application.dto.CommandExecutorResult;
import com.knullci.necrosword.application.executor.CommandExecutor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandExecutorImpl implements CommandExecutor {

    private static final Logger logger = LoggerFactory.getLogger(CommandExecutorImpl.class);

    @Value("${knull.workspace.directory}")
    private String workspaceDirectory;

    @Override
    @SneakyThrows
    @Deprecated
    public CommandExecutorResult execute(String cmd, File workDirectory, boolean isSecretCmd) {
        return this.executeCmd(cmd, workDirectory, null, isSecretCmd);
    }

    @Override
    @SneakyThrows
    public CommandExecutorResult execute(String cmd, File workDirectory, Integer buildId, boolean isSecretCmd) {
        return this.executeCmd(cmd, workDirectory, buildId, isSecretCmd);
    }

    @SneakyThrows
    private CommandExecutorResult executeCmd(String cmd, File workDirectory, Integer buildId, boolean isSecretCmd) {
        List<String> commands = new ArrayList<>();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            commands.add("cmd.exe");
            commands.add("/c");
        } else {
            commands.add("bash");
            commands.add("-c");
        }

        commands.add(cmd);

        String logFileLocation = workspaceDirectory + "/logs/" + buildId;

        Path path = Paths.get(logFileLocation);
        if (!Files.exists(path)) {
            new File(logFileLocation).mkdirs();
        }

        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        builder.directory(workDirectory);
//        builder.redirectOutput(new File(logFileLocation + "/logs.txt")); // directly write logs to the file, but not appending (need to check)

        Process process = builder.start();


        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                FileOutputStream fos = new FileOutputStream(logFileLocation + "/logs.txt", true);
                DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(fos));
        ) {
            if (isSecretCmd) {
                outputStream.writeBytes("*****" + System.lineSeparator());
            } else {
                outputStream.writeBytes(cmd + System.lineSeparator());
            }

            outputStream.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                outputStream.writeBytes(line + System.lineSeparator());
                outputStream.flush();
            }
        }

        int exitCode = process.waitFor();
        return new CommandExecutorResult(exitCode);
    }
}
