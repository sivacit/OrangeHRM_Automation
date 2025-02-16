package ai;

import utils.FileUtils;

public class Test {

    public static void main(String args[]){
        String fileName = "login.txt";
//        String stepOutputFile = "src/test/java/steps/" + FileUtils.replaceExtension(FileUtils.toCamelCase(fileName), "java");   
        System.out.println(getStepFileName(fileName));
//        stepOutputFile.append(fileName).append("java");
//        System.out.println(stepOutputFile.toString());                            
    }
    
    public static String getStepFileName(String fileName) {
    	String originalFileName = fileName; // Example: "Login.txt"
    	String camelCaseFileName = FileUtils.toCamelCase(originalFileName); // Converts "Login.txt" -> "LoginTxt"

    	// Extract the base name without extension
    	String baseName = camelCaseFileName.substring(0, camelCaseFileName.lastIndexOf('.')); // "Login"
    	String extension = camelCaseFileName.substring(camelCaseFileName.lastIndexOf('.'));   // ".txt"

    	// Append "Step" before the extension
    	String fileNameWithSteps = baseName + "Step" + extension; // "LoginStep.txt"

    	// Build the final path
    	StringBuilder stepOutputFile = new StringBuilder();
    	stepOutputFile.append("src/test/java/steps/").append(fileNameWithSteps);

    	String stepOutputPath = stepOutputFile.toString(); // Convert StringBuilder to String

    	System.out.println(stepOutputPath);
    	return stepOutputPath;

    }
    
}

