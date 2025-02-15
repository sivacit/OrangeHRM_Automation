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
import utils.FileUtils;


public class TestCaseGenerator {
    private static final String OLLAMA_HOST = "http://localhost:11434/";
    private static final String MODEL = "mistral"; // Use 'gemma' if needed
    private static String basePromptDirectory;

    static {
        loadConfig();
    }

    public static void main(String[] args) {  
        try {
            genFetureFiles();
            //genStepFilesNew();
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

    private static String getStepPrompt(String featureFilePath, String stepOutputFile) throws IOException  {
        String str3 = FileUtils.readFileToString(featureFilePath);        
        StringBuilder sb = new StringBuilder();
        sb.append("Generate a Java Step Definition file for the following Cucumber BDD feature file:\n");
        sb.append(str3).append("\n\n"); // File content
        sb.append("Requirements:\n");
        sb.append("Use Selenium WebDriver to interact with the browser.\n");
        sb.append("Implement @Given, @When, @And, and @Then step definitions in Java.\n");
        sb.append("Use TestNG for assertions.\n");
        sb.append("Define a method to open the login page, input credentials, click the login button, and verify the login status.\n");
        sb.append("Handle invalid login cases with proper validation of the error message.\n");
        sb.append("Do not include explanations or setup instructions, just the Java code.");        
        String result = sb.toString();        
        return result;        
    }

    // public static void genStepFiles() throws OllamaBaseException, IOException, InterruptedException{
    //     String step_file = basePromptDirectory + "/steps/";            
    //     File folder = new File(step_file);
    //      // List all files in the directory
    //     File[] files = folder.listFiles();

    //     OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
    //     ollamaAPI.setRequestTimeoutSeconds(600);
       
    //     if (files != null) {
    //         for (File file : files) {
    //             // Check if it is a file (not a directory)
    //             if (file.isFile()) {
    //                 System.out.println("------------------------------------------------------------------------ Processing file: " + file.getName());
    //                 String  stepOutputFile = "src/temp/resources/steps/" + FileUtils.replaceExtension(FileUtils.toCamelCase(file.getName()), "java");                        
    //                 generateStepDefinitions(step_file + file.getName(), stepOutputFile);
    //             }
    //         }
    //     } else {
    //         System.out.println("Folder is empty or does not exist.");
    //     }        
    // }

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
                    String  featureOutputFile = "src/temp/resources/features/" + FileUtils.toCamelCase(file.getName());                        
                    String  stepOutputFile = "src/temp/java/steps/" + FileUtils.replaceExtension(FileUtils.toCamelCase(file.getName()), "java");       
                    generateFeatureFile(feature_file + file.getName(), featureOutputFile);
                    String stepPrompt = getStepPrompt(featureOutputFile, featureOutputFile);
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
        ollamaAPI.setRequestTimeoutSeconds(60);
        
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
