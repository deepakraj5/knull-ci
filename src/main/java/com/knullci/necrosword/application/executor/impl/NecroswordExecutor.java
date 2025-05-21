package com.knullci.necrosword.application.executor.impl;

import com.knullci.necrosword.application.dto.CommandExecutorResult;
import com.knullci.necrosword.application.executor.AbstractShellExecutor;
import com.knullci.necrosword.application.executor.CommandExecutor;
import com.knullci.necrosword.application.model.KnullStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class NecroswordExecutor extends AbstractShellExecutor {

    private static final Logger logger = LoggerFactory.getLogger(NecroswordExecutor.class);

    // TODO: resolve the dependency
    public NecroswordExecutor(String knullFile, String workingDirectory, Integer buildId, CommandExecutor commandExecutor) {
        super(knullFile, workingDirectory, buildId, commandExecutor);
    }

    @Override
    protected void executeStages() {
        for (KnullStage stage : this.stages) {
            logger.info("Executing the stage: " + stage.getName());

            CommandExecutorResult result = commandExecutor.execute(stage.getCommand(), new File(this.workingDirectory), this.buildId);

            if (result.exitCode != 0) {
                // update the status to failed
                logger.error("Build failed");
                break;
            }
        }
    }

}
