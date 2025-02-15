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

    public static String replaceExtension(String filePath, String newExtension) {
        File file = new File(filePath);
        String fileName = file.getName();
        
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex); // Remove old extension
        }
        
        return file.getParent() != null 
            ? file.getParent() + File.separator + fileName + "." + newExtension 
            : fileName + "." + newExtension;
    }

    public static String toCamelCase(String str) {
        // Convert to lowercase and split by space, underscore, or hyphen
        String[] words = str.toLowerCase().split("[ _-]+"); 
        
        if (words.length == 0) return "";
                
        StringBuilder camelCase = new StringBuilder(); // Keep the first word lowercase
        for (int i = 0; i < words.length; i++) {
            camelCase.append(Character.toUpperCase(words[i].charAt(0)))
                     .append(words[i].substring(1));
        }

        return camelCase.toString();
    }

    public static void main(String[] args) {
       
        try {
            genFetureFiles();
            genStepFiles();
            // generateStepDefinitions(basePromptDirectory + "/step_definition_prompt.txt", "src/temp/java/stepDefinitions/LoginSteps.java");
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

    public static void genStepFiles() throws OllamaBaseException, IOException, InterruptedException{
        String step_file = basePromptDirectory + "/steps/";            
        File folder = new File(step_file);
         // List all files in the directory
        File[] files = folder.listFiles();

        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(600);
       
        if (files != null) {
            for (File file : files) {
                // Check if it is a file (not a directory)
                if (file.isFile()) {
                    System.out.println("------------------------------------------------------------------------ Processing file: " + file.getName());
                    String  stepOutputFile = "src/temp/resources/steps/" + replaceExtension(toCamelCase(file.getName()), "java");                        
                    generateStepDefinitions(step_file + file.getName(), stepOutputFile);
                }
            }
        } else {
            System.out.println("Folder is empty or does not exist.");
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
                // Check if it is a file (not a directory)
                if (file.isFile()) {
                    System.out.println("Processing file: " + file.getName());
                    String  featureOutputFile = "src/temp/resources/features/" + toCamelCase(file.getName());                        
                    generateFeatureFile(feature_file + file.getName(), featureOutputFile);
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
