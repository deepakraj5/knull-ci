package com.knullci.necrosword.application.executor;

import com.knullci.necrosword.application.factory.YamlParser;
import com.knullci.necrosword.application.model.Knull;
import com.knullci.necrosword.application.model.KnullStage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractShellExecutor {

    // TODO: parse, save build stages, execute each stage, save and stream logs,
    // TODO: update stage and build status accordingly

    private String knullFile;
    protected List<KnullStage> stages;
    private final Logger logger = LoggerFactory.getLogger(AbstractShellExecutor.class);


    public final void execute(String knullFile, String workDir, Integer buildId) {
        // TODO: make state less and pass it as params
        // TODO: create methods for before, after, failure scenario
        this.knullFile = knullFile;
        this.parseKnullFile();
        this.executeStages(workDir, buildId);
    }

    @SneakyThrows
    private void parseKnullFile() {
        logger.info("Parsing knull file: " + this.knullFile);

        Knull knull = YamlParser.parseYaml(this.knullFile);

        logger.info("Parsed knull file: " + this.knullFile);

        this.stages = knull.getStages();
    }

    protected abstract void executeStages(String workDir, Integer buildId);

}
