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
    private static boolean PROD_MODE = true;
    
    static {
        basePromptDirectory = FileUtils.loadConfig();
    }

    public static void main(String[] args) {  
        try {
            OllamaAPI ollamaAPI = new OllamaAPI(OLLAMA_HOST);
            ollamaAPI.setRequestTimeoutSeconds(600);

            // genPOMFiles(ollamaAPI);
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
                    
                    String featurePrompt = FileUtils.readFromFile(feature_file + file.getName());
                    generatePromptResponse(ollamaAPI, featurePrompt, featureOutputFile);
                    
                    // Generating Step Definition Java file
                    getStepPrompt(ollamaAPI, file, featureOutputFile);
                    getTestPrompt(ollamaAPI, file);


                }
            }
        } else {
            System.out.println("Folder is empty or does not exist.");
        }        
    }

    private static void getStepPrompt(OllamaAPI ollamaAPI, File file, String featureOutputFile)
            throws IOException, OllamaBaseException, InterruptedException {

        String className  = FileUtils.replaceExtension(file.getName(), "");
        String stepPrompt = PromptGenerator.getStepPrompt(featureOutputFile, featureOutputFile, className);
        String stepOutputFile = FileUtils.getFileNameWithType(file.getName(),"src/test/java/steps/", "Steps");
        generatePromptResponse(ollamaAPI, stepPrompt, stepOutputFile);
    }

    private static void getTestPrompt(OllamaAPI ollamaAPI, File file) throws OllamaBaseException, IOException, InterruptedException{

        String pomFileContent = FileUtils.getPOMFile(file.getName());
        String className  = FileUtils.replaceExtension(file.getName(), "java");
        String prompt = PromptGenerator.getTestCaseClassPrompt(pomFileContent, className);
        String writeFile = FileUtils.getFileNameWithType(file.getName(),"src/test/java/testCases/", "Test");
        generatePromptResponse(ollamaAPI, prompt, writeFile);



    }

    // public static void genPOMFiles(OllamaAPI ollamaAPI) throws OllamaBaseException, IOException, InterruptedException{
    //     String page_file = basePromptDirectory + "/pages/";            
    //     File folder = new File(page_file);

    //     File[] files = folder.listFiles();       

    //     if (files != null) {
    //         for (File file : files) {                
    //             if (file.isFile()) {
    //                 System.out.println("Processing file: " + file.getName());
    //                 String  pageFile = "src/temp/java/pages/" + FileUtils.toCamelCase(file.getName());                                                                
    //                 String featurePrompt = FileUtils.readFromFile(page_file + file.getName());
    //                    generatePromptResponse(ollamaAPI, featurePrompt, pageFile);
    //             }
    //         }
    //     } else {
    //         System.out.println("Folder is empty or does not exist.");
    //     }        
    // }


    public static void generatePromptResponse(OllamaAPI ollamaAPI, String prompt, String outputFilePath) 
        throws OllamaBaseException, IOException, InterruptedException {            
    if (prompt == null || prompt.isEmpty()) {
        System.err.println("Input prompt is empty or missing.");
        return;
    }
    if (PROD_MODE){
        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, options);
        FileUtils.saveToFile(outputFilePath, response.getResponse());
    }
    else{
        System.out.println(prompt);
    }
    
    }
}
