package com.knullci.necrosword.application.dto;

import java.util.List;

public class CommandExecutorResult {

    public final int exitCode;
    private final List<String> output;

    public CommandExecutorResult(int exitCode, List<String> output) {
        this.exitCode = exitCode;
        this.output = output;
    }

}
