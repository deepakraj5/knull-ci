package com.knullci.necrosword.application.factory;

import com.knullci.necrosword.application.executor.CommandExecutor;
import com.knullci.necrosword.application.executor.impl.NecroswordExecutor;
import org.springframework.stereotype.Service;

@Service
public class NecroswordExecutorFactory {

    private final CommandExecutor commandExecutor;

    public NecroswordExecutorFactory(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public NecroswordExecutor createExecutor(String knullFile, String workingDirectory) {
        return new NecroswordExecutor(knullFile, workingDirectory, commandExecutor);
    }

}
