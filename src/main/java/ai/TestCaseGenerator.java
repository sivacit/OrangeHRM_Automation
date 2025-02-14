package ai;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestCaseGenerator {
    private static final String OLLAMA_HOST = "http://localhost:11434/";
    private static final String MODEL = "mistral"; // Use 'gemma' if needed

    public static void main(String[] args) {
        try {
			generateFeatureFile();
		} catch (OllamaBaseException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			generateStepDefinitions();
		} catch (OllamaBaseException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void generateFeatureFile() throws OllamaBaseException, IOException, InterruptedException {
        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        String prompt = new PromptBuilder()
                .addLine("Generate a Cucumber BDD feature file for login functionality for the website:")
                .addLine("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login.")
                .addLine("The test should:")
                .addLine("- Open the login page.")
                .addLine("- Enter valid credentials (admin/admin123).")
                .addLine("- Verify successful login.")
                .addLine("- Handle invalid credentials case.")
                .build();

        Options options = new OptionsBuilder().build();

        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        String generatedFeature = response.getResponse();

        saveToFile("src/test/resources/features/Login.feature", generatedFeature);
    }

    public static void generateStepDefinitions() throws OllamaBaseException, IOException, InterruptedException {
        OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
        ollamaAPI.setRequestTimeoutSeconds(60);

        String prompt = new PromptBuilder()
                .addLine("Generate Java step definitions for the following Cucumber feature file:")
                .addLine("Feature: Login functionality")
                .addLine("Scenario: Successful login")
                .addLine("  Given I open the OrangeHRM login page")
                .addLine("  When I enter username \"Admin\"")
                .addLine("  And I enter password \"admin123\"")
                .addLine("  And I click on the login button")
                .addLine("  Then I should be redirected to the dashboard")
                .addLine("")
                .addLine("Scenario: Invalid login")
                .addLine("  Given I open the OrangeHRM login page")
                .addLine("  When I enter an invalid username \"wrongUser\"")
                .addLine("  And I enter an invalid password \"wrongPass\"")
                .addLine("  And I click on the login button")
                .addLine("  Then I should see an error message \"Invalid credentials\"")
                .build();

        Options options = new OptionsBuilder().build();

        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        String generatedStepDefs = response.getResponse();

        saveToFile("src/test/java/stepDefinitions/LoginSteps.java", generatedStepDefs);
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
