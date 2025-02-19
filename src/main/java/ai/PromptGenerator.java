package ai;

import java.io.IOException;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute.Use;

import utils.FileUtils;

public class PromptGenerator {
    public static String getStepPrompt(String featureFilePath, String stepOutputFile, String className) throws IOException  {
        String str3 = FileUtils.readFileToString(featureFilePath);        
        StringBuilder sb = new StringBuilder();
        sb.append("Generate a Java Step Definition file for the following Cucumber BDD feature file:\n");
        sb.append("Java className should be: " + className + "\n" );        
        sb.append("import classes properly");
        sb.append(str3).append("\n\n"); // File content
        sb.append("Requirements:\n");
        sb.append("Use Selenium WebDriver to interact with the browser.\n");
        sb.append("package should be package steps;.");         
        sb.append("Implement @Given, @When, @And, and @Then step definitions in Java.\n");
        sb.append("Use TestNG for assertions.\n");
        sb.append("Define a method to open the login page, input credentials, click the login button, and verify the login status.\n");
        sb.append("Handle invalid login cases with proper validation of the error message.\n");
        sb.append("Do not include explanations or setup instructions, just the Java code.");   
        sb.append("Do not include any notes or comments."); 
       

        String result = sb.toString();        
        return result;        
    }

    // public static String getTestCaseClassPrompt(String pageContent, String testFileName){
    //     StringBuilder str = new StringBuilder();
    //     str.append("\\nGenerate a Java TestNG class named ProfileTest using the following existing ProfilePage.java Page Object Model (POM) file:\\n");
    //     str.append(pageContent);
    //     str.append("\n\n\n The test class should:\n");
    //     str.append("The " + testFileName + " class should:\n");
    //     str.append("Be inside the testCases package.\n");
    //     str.append("Use TestNG annotations (@BeforeClass, @Test, @AfterClass).\n");
    //     str.append("Initialize WebDriver and navigate to the profile page in @BeforeClass.\n");
    //     str.append("Load properties from src/test/resources/config.properties for credentials and profile data.\n");
    //     str.append("Implement a testUpdateProfile method that:\n");
    //     str.append("Reads First Name, Middle Name, and Last Name from properties.\n");
    //     str.append("Calls the respective methods from ProfilePage.java to update the profile.\n");
    //     str.append("Clicks the Save button.\n");
    //     str.append("Verifies if the update was successful using an assertion.\n");
    //     str.append("Close the WebDriver in @AfterClass.\n");                            
    //     str.append("Do not include explanations or setup instructions, just the Java code.");   
    //     str.append("Do not include any notes or comments."); 
    //     System.out.println(str);
    //     return str.toString();
    // }

    public static String getTestCaseClassPromptNew(String pageContent, String className) throws IOException{               
        Map<String, String> values = Map.of(
            "pomFile", pageContent,
            "className", className + "Test"                            
        );

        String result = FileUtils.readAndReplaceMarkdown("src/main/resources/prompt/tests/tests.md", values);
        return result;            
    }
}
