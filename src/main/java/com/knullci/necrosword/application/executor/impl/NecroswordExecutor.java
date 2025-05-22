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

    private final CommandExecutor commandExecutor;

    private static final Logger logger = LoggerFactory.getLogger(NecroswordExecutor.class);

    public NecroswordExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    protected void executeStages(String workDir, Integer buildId) {
        for (KnullStage stage : this.stages) {
            logger.info("Executing the stage: " + stage.getName());

            CommandExecutorResult result = commandExecutor.execute(stage.getCommand(), new File(workDir), buildId);

            if (result.exitCode != 0) {
                // update the status to failed
                logger.error("Build failed");
                break;
            }
        }
    }

}
