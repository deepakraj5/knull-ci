package com.knullci.necrosword.application.factory;

import com.knullci.necrosword.application.model.Knull;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class YamlParser {

    public static Knull parseYaml(String yamlFileLocation) throws Exception {
        Yaml yaml = new Yaml();

        try (InputStream inputStream = new FileInputStream(yamlFileLocation)) {
            return yaml.loadAs(new BufferedInputStream(inputStream), Knull.class);
        } catch (RuntimeException | IOException exception) {
            throw new Exception("Failed to parse yaml file", exception);
        }
    }

}
