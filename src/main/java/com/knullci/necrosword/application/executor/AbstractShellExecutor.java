package com.knullci.necrosword.application.executor;

import com.knullci.knull.domain.model.Stage;
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

    private final Logger logger = LoggerFactory.getLogger(AbstractShellExecutor.class);


    public final void execute(String knullFile, String workDir, Integer buildId) {
        try
        {
            this.beforeExecute();

            List<KnullStage> stages = this.parseKnullFile(knullFile);

            var stageModels = this.saveStages(stages, buildId);

            this.executeStages(workDir, buildId, stageModels);

            this.afterExecute();
        } catch (Exception exception) {
            this.onFailure();
        }
    }

    @SneakyThrows
    private List<KnullStage> parseKnullFile(String knullFile) {
        logger.info("Parsing knull file: " + knullFile);

        Knull knull = YamlParser.parseYaml(knullFile);

        logger.info("Parsed knull file: " + knullFile);

        return knull.getStages();
    }

    protected abstract List<Stage> saveStages(List<KnullStage> stages, Integer buildId);

    protected abstract void executeStages(String workDir, Integer buildId, List<Stage> stages);

    private void beforeExecute() {

    }

    private void afterExecute() {

    }

    private void onFailure() {

    }

}
