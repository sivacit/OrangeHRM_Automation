package ai;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;
import utils.ConfigReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestCaseGenerator2 {
    private static final String OLLAMA_HOST = "http://localhost:11434/";
    private static final String MODEL = "mistral"; // Use 'gemma' if needed

    public static void main(String[] args) {        
        try {
        	generateFeatureFile();
        	generateStepDefinitions();
			 generatePageObjectModel();
		} catch (OllamaBaseException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }

    public static void generateFeatureFile() throws OllamaBaseException, IOException, InterruptedException {
        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        String baseUrl = ConfigReader.get("base.url");

        String prompt = new PromptBuilder()
                .addLine("Generate a Cucumber BDD feature file for login functionality.")
                .addLine("Base URL: " + baseUrl)
                .addLine("Test Cases:")
                .addLine("- Open the login page.")
                .addLine("- Enter valid credentials and verify successful login.")
                .addLine("- Handle invalid credentials case.")
                .build();

        Options options = new OptionsBuilder().build();
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        String generatedFeature = response.getResponse();

        saveToFile("src/temp/resources/features/Login.feature", generatedFeature);
    }

    public static void generateStepDefinitions() throws OllamaBaseException, IOException, InterruptedException {
        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        String username = ConfigReader.get("login.username");
        String password = ConfigReader.get("login.password");

        String prompt = new PromptBuilder()
                .addLine("Generate Java step definitions for the following Cucumber feature file.")
                .addLine("Use these credentials:")
                .addLine("Username: " + username)
                .addLine("Password: " + password)
                .build();

        Options options = new OptionsBuilder().build();
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        String generatedStepDefs = response.getResponse();

        saveToFile("src/temp/java/stepDefinitions/LoginSteps.java", generatedStepDefs);
    }

    public static void generatePageObjectModel() throws OllamaBaseException, IOException, InterruptedException {
        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        String prompt = new PromptBuilder()
                .addLine("Generate a Selenium Page Object Model (POM) for automating login functionality.")
                .build();

        Options options = new OptionsBuilder().build();
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        String generatedPageObject = response.getResponse();

        saveToFile("src/temp/java/pages/LoginPage.java", generatedPageObject);
    }

    private static void saveToFile(String filePath, String content) {
        try {
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
