package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtils {
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

    public static String readFileToString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static String readFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    public static String loadConfig() {
        Properties properties = new Properties();
        String basePromptDirectory = "";
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/config.properties"))) {
            properties.load(input);
            basePromptDirectory = properties.getProperty("prompt.base.directory");           
        } catch (IOException e) {
            System.err.println("Failed to load config file. Using default path.");
            basePromptDirectory = "src/main/resources/prompt";
        }
        return basePromptDirectory;
    }

    public static void saveToFile(String filePath, String content) {
        try {
            // Remove markdown-style code formatting (```java and ```)
            content = content.replaceAll("```java", "").replaceAll("```", "").trim();

            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getFileNameWithType(String fileName, String outputPath, String type) {
    	String originalFileName = fileName; // Example: "Login.txt"
    	String camelCaseFileName = FileUtils.toCamelCase(originalFileName); // Converts "Login.txt" -> "LoginTxt"

    	// Extract the base name without extension
    	String baseName = camelCaseFileName.substring(0, camelCaseFileName.lastIndexOf('.')); // "Login"
    	String extension = camelCaseFileName.substring(camelCaseFileName.lastIndexOf('.'));   // ".txt"

    	// Append "Step" before the extension
    	String fileNameWithSteps = baseName + type + extension; // "LoginStep.txt"

    	// Build the final path
    	StringBuilder stepOutputFile = new StringBuilder();
    	stepOutputFile.append(outputPath).append(fileNameWithSteps);

    	String stepOutputPath = stepOutputFile.toString(); // Convert StringBuilder to String
        stepOutputPath = replaceExtension(stepOutputPath, "java");
    	return stepOutputPath;
    }

    public static String getPOMFile(String fileName) {
        String str = FileUtils.getFileNameWithType(fileName, "src/main/java/pages/", "Page");		
        return readFromFile(str);		
    }

    public static String readAndReplaceMarkdown(String filePath, Map<String, String> placeholders) throws IOException {
        // Read the markdown file content
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        // Replace placeholders
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        return content;
    }
}
