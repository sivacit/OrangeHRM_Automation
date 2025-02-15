package ai;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.File;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;



public class TestCaseGenerator {
    private static final String OLLAMA_HOST = "http://localhost:11434/";
    private static final String MODEL = "mistral"; // Use 'gemma' if needed
    private static String basePromptDirectory;

    static {
        loadConfig();
    }

    public static void main(String[] args) {
        try {
            generateFeatureFile(basePromptDirectory + "/feature_prompt.txt", "src/temp/resources/features/Login.feature");
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            generateStepDefinitions(basePromptDirectory + "/step_definition_prompt.txt", "src/temp/java/stepDefinitions/LoginSteps.java");
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/config.properties"))) {
            properties.load(input);
            basePromptDirectory = properties.getProperty("prompt.base.directory");
            System.out.println("Loaded prompt directory: " + basePromptDirectory);
        } catch (IOException e) {
            System.err.println("Failed to load config file. Using default path.");
            basePromptDirectory = "src/main/resources/prompt";
        }
    }

    public static void generateFeatureFile(String promptFilePath, String outputFilePath) 
            throws OllamaBaseException, IOException, InterruptedException {
        String prompt = readFromFile(promptFilePath);
        if (prompt == null || prompt.isEmpty()) {
            System.err.println("Feature prompt file is empty or missing.");
            return;
        }

        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        Options options = new OptionsBuilder().build();
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        saveToFile(outputFilePath, response.getResponse());
    }

    public static void generateStepDefinitions(String promptFilePath, String outputFilePath) 
            throws OllamaBaseException, IOException, InterruptedException {
        String prompt = readFromFile(promptFilePath);
        if (prompt == null || prompt.isEmpty()) {
            System.err.println("Step definition prompt file is empty or missing.");
            return;
        }

        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        Options options = new OptionsBuilder().build();
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        saveToFile(outputFilePath, response.getResponse());
    }

    private static String readFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    private static void saveToFile(String filePath, String content) {
        try {
            // Remove markdown-style code formatting (```java and ```)
            content = content.replaceAll("```java", "").replaceAll("```", "").trim();

            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
            System.out.println("File generated: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
