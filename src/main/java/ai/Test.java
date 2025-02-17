package ai;

import utils.FileUtils;

public class Test {

    public static void main(String args[]){
        String fileName = "login.txt";
		String className  = FileUtils.replaceExtension(fileName, "java");
        String  pageFile = "src/main/java/pages/" + FileUtils.toCamelCase(className);   		
		System.out.println(pageFile);
        String str = FileUtils.getFileNameWithType("profile.txt", "src/main/pages/", "Page");
        System.err.println(str);
    }
}

