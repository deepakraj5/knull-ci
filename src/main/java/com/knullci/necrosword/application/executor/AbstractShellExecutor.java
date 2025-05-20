package com.knullci.necrosword.application.executor;

import com.knullci.necrosword.application.dto.CommandExecutorResult;
import com.knullci.necrosword.application.factory.YamlParser;
import com.knullci.necrosword.application.model.Knull;
import com.knullci.necrosword.application.model.KnullStage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public abstract class AbstractShellExecutor {

    // TODO: parse, save build stages, execute each stage, save and stream logs,
    // TODO: update stage and build status accordingly

    private final String knullFile;
    private final String workingDirectory;
    private List<KnullStage> stages;
    protected final CommandExecutor commandExecutor;

    private final Logger logger = LoggerFactory.getLogger(AbstractShellExecutor.class);

    public AbstractShellExecutor(String knullFile, String workingDirectory, CommandExecutor commandExecutor) {
        this.knullFile = knullFile;
        this.workingDirectory = workingDirectory;
        this.commandExecutor = commandExecutor;
    }

    public void execute() {
        this.parseKnullFile();
        this.executeStages();
    }

    @SneakyThrows
    private void parseKnullFile() {
        logger.info("Parsing knull file: " + this.knullFile);

        Knull knull = YamlParser.parseYaml(this.knullFile);

        logger.info("Parsed knull file: " + this.knullFile);

        this.stages = knull.getStages();
    }

    private void executeStages() {
        for (KnullStage stage : stages) {
            logger.info("Executing the stage: " + stage.getName());

            CommandExecutorResult result = commandExecutor.execute(stage.getCommand(), new File(workingDirectory));

            if (result.exitCode != 0) {
                // update the status to failed
                logger.error("Build failed");
                break;
            }
        }
    }

}
