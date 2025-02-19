package ai;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.compress.compressors.FileNameUtil;

import utils.FileUtils;
import java.nio.file.*;

public class Test {

    // public static void main(String args[]){
    //     String fileName = "login.txt";
	// 	String className  = FileUtils.replaceExtension(fileName, "java");
    //     String  pageFile = "src/main/java/pages/" + FileUtils.toCamelCase(className);   		
	// 	System.out.println(pageFile);
    //     String str = FileUtils.getFileNameWithType("profile.txt", "src/main/pages/", "Page");
    //     System.err.println(str);
    // }

    // public static String readAndReplaceMarkdown(String filePath, Map<String, String> placeholders) throws IOException {
    //     // Read the markdown file content
    //     String content = new String(Files.readAllBytes(Paths.get(filePath)));
    //     // Replace placeholders
    //     for (Map.Entry<String, String> entry : placeholders.entrySet()) {
    //         content = content.replace("${" + entry.getKey() + "}", entry.getValue());
    //     }

    //     return content;
    // }

    public static void main(String[] args) throws IOException {
        PromptGenerator.getTestCaseClassPromptNew(FileUtils.readFileToString("src/main/java/pages/ProfilePage.java"), "ProfileTest");
        // try {
        //     Map<String, String> values = Map.of(
        //         "name", "Siva",
        //         "date", "2025-02-18",
        //         "location", "Coimbatore"
        //     );

        //     String result = FileUtils.readAndReplaceMarkdown("src/main/resources/prompt/steps/example.md", values);
        //     System.out.println(result);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}
