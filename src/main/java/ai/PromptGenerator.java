package ai;

import java.io.IOException;

import utils.FileUtils;

public class PromptGenerator {
    public static String getStepPrompt(String featureFilePath, String stepOutputFile, String className) throws IOException  {
        String str3 = FileUtils.readFileToString(featureFilePath);        
        StringBuilder sb = new StringBuilder();
        sb.append("Generate a Java Step Definition file for the following Cucumber BDD feature file:\n");
        sb.append("Java className should be: " + className + "\n" );        
        sb.append(str3).append("\n\n"); // File content
        sb.append("Requirements:\n");
        sb.append("Use Selenium WebDriver to interact with the browser.\n");
        sb.append("package should be package steps;."); 
        // sb.append("Use cucumber 7 and import the following classes:");
        // sb.append("import io.cucumber.junit.CucumberOptions;\n" + //
        //                 "import io.cucumber.junit.Cucumber;\n" + //
        //                 "\n" + //
        //                 "import io.cucumber.java.en.Given;\n" + //
        //                 "import io.cucumber.java.en.Then;\n" + //
        //                 "import io.cucumber.java.en.When;");
        sb.append("Implement @Given, @When, @And, and @Then step definitions in Java.\n");
        sb.append("Use TestNG for assertions.\n");
        sb.append("Define a method to open the login page, input credentials, click the login button, and verify the login status.\n");
        sb.append("Handle invalid login cases with proper validation of the error message.\n");
        sb.append("Do not include explanations or setup instructions, just the Java code.");   
        sb.append("Do not include any notes or comments."); 
       

        String result = sb.toString();        
        return result;        
    }
}
