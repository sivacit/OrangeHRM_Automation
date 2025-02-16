package ai;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.File;
import java.io.IOException;
import utils.FileUtils;

public class TestCaseGenerator {
    private static final String OLLAMA_HOST = "http://localhost:11434/";
    private static final String MODEL = "mistral"; // Use 'gemma' if needed
    private static String basePromptDirectory;
    private static Options options = new OptionsBuilder().build();
    
    static {
        basePromptDirectory = FileUtils.loadConfig();
    }

    public static void main(String[] args) {  
        try {
            OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
            ollamaAPI.setRequestTimeoutSeconds(600);

            genFetureFiles(ollamaAPI);           
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }    

    public static void genFetureFiles(OllamaAPI ollamaAPI) throws OllamaBaseException, IOException, InterruptedException{
        String feature_file = basePromptDirectory + "/features/";            
        File folder = new File(feature_file);

        File[] files = folder.listFiles();       

        if (files != null) {
            for (File file : files) {                
                if (file.isFile()) {
                    System.out.println("Processing file: " + file.getName());
                    String  featureOutputFile = "src/test/resources/features/" + FileUtils.toCamelCase(file.getName());                        
                    System.out.println("Before append ::>" + FileUtils.toCamelCase(file.getName()));
                    
                    String stepOutputFile = "src/test/java/steps/" + FileUtils.getStepFileName(file.getName());
                    System.out.println("Falase ::>" + stepOutputFile);


                    String featurePrompt = FileUtils.readFromFile(feature_file + file.getName());
                    generatePromptResponse(ollamaAPI, featurePrompt, featureOutputFile);
                    
                    // Generating Step Definition Java file
                    String className  = FileUtils.replaceExtension(file.getName(), "");
                    String stepPrompt = PromptGenerator.getStepPrompt(featureOutputFile, featureOutputFile, className);
                    generatePromptResponse(ollamaAPI, stepPrompt, stepOutputFile);
                }
            }
        } else {
            System.out.println("Folder is empty or does not exist.");
        }        
    }

    public static void generatePromptResponse(OllamaAPI ollamaAPI, String prompt, String outputFilePath) 
        throws OllamaBaseException, IOException, InterruptedException {            
    if (prompt == null || prompt.isEmpty()) {
        System.err.println("Input prompt is empty or missing.");
        return;
    }
    
    OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
    FileUtils.saveToFile(outputFilePath, response.getResponse());
    }
}
