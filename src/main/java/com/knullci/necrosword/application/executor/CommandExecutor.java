package com.knullci.necrosword.application.executor;

import com.knullci.necrosword.application.dto.CommandExecutorResult;

import java.io.File;

public interface CommandExecutor {
    @Deprecated
    CommandExecutorResult execute(String cmd, File workDirectory, boolean isSecretCmd);
    CommandExecutorResult execute(String cmd, File workDirectory, Integer buildId, boolean isSecretCmd);
}
