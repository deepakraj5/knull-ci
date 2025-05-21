package com.knullci.necrosword.application.dto;

import java.util.List;

public class CommandExecutorResult {

    public final int exitCode;

    public CommandExecutorResult(int exitCode) {
        this.exitCode = exitCode;
    }

}
