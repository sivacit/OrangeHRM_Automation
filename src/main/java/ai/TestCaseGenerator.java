package ai;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import utils.FileUtils;

public class TestCaseGenerator {
    private static final String OLLAMA_HOST = "http://localhost:11434/";
    private static final String MODEL = "mistral"; // Use 'gemma' if needed
    private static String basePromptDirectory;

    static {
        basePromptDirectory = FileUtils.loadConfig();
    }

    public static void main(String[] args) {  
        try {
            genFetureFiles();           
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }    

    public static void genFetureFiles() throws OllamaBaseException, IOException, InterruptedException{
        String feature_file = basePromptDirectory + "/features/";            
        File folder = new File(feature_file);
        // List all files in the directory
        File[] files = folder.listFiles();

        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(600);

        if (files != null) {
            for (File file : files) {                
                if (file.isFile()) {
                    System.out.println("Processing file: " + file.getName());
                    String  featureOutputFile = "src/temp/resources/features/" + FileUtils.toCamelCase(file.getName());                        
                    String  stepOutputFile = "src/temp/java/steps/" + FileUtils.replaceExtension(FileUtils.toCamelCase(file.getName()), "java");       
                    generateFeatureFile(feature_file + file.getName(), featureOutputFile);
                    String stepPrompt = PromptGenerator.getStepPrompt(featureOutputFile, featureOutputFile);
                    generateStepDefinitionsNew(stepPrompt, stepOutputFile);
                }
            }
        } else {
            System.out.println("Folder is empty or does not exist.");
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
        ollamaAPI.setRequestTimeoutSeconds(600);
        
        Options options = new OptionsBuilder().build();
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        saveToFile(outputFilePath, response.getResponse());
    }

    public static void generateStepDefinitionsNew(String prompt, String outputFilePath) 
            throws OllamaBaseException, IOException, InterruptedException {
        if (prompt == null || prompt.isEmpty()) {
            System.err.println("Step definition prompt file is empty or missing.");
            return;
        }

        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(600);

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
