package com.knullci.necrosword.application.executor;

import com.knullci.necrosword.application.dto.CommandExecutorResult;

import java.io.File;

public interface CommandExecutor {
    CommandExecutorResult execute(String cmd, File workDirectory);
    CommandExecutorResult execute(String cmd, File workDirectory, Integer buildId);
}
