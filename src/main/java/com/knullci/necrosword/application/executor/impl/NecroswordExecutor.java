package com.knullci.necrosword.application.executor.impl;

import com.knullci.necrosword.application.executor.AbstractShellExecutor;
import com.knullci.necrosword.application.executor.CommandExecutor;

public class NecroswordExecutor extends AbstractShellExecutor {

    public NecroswordExecutor(String knullFile, String workingDirectory, CommandExecutor commandExecutor) {
        super(knullFile, workingDirectory, commandExecutor);
    }

}
