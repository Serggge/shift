package util;

import exception.FileReadingException;
import model.Options;
import model.UserChoice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProjectFileReader {

    private static String pathToFile = "";

    private ProjectFileReader() {
    }

    public static Map<String, List<String>> readByOptions(Options options) {
        Map<String, List<String>> allFilesContent = new LinkedHashMap<>();
        List<String> fileList = options.getFileList();

        for (String fileName : fileList) {
            boolean needBreak = false;
            while (!needBreak) {
                try {
                    Map.Entry<String, List<String>> fileContent = readFile(pathToFile, fileName);
                    allFilesContent.put(fileContent.getKey(), fileContent.getValue());
                    break;
                } catch (FileReadingException exception) {
                    System.out.println(exception.getMessage());
                    Scanner scanner = new Scanner(System.in);
                    UserChoice userChoice = getUserChoice(scanner);
                    switch (userChoice) {
                        case RETRY:
                            continue;
                        case NAME:
                            System.out.println("Enter the file name:");
                            fileName = scanner.nextLine();
                            break;
                        case PATH:
                            System.out.println("Enter the path to file:");
                            pathToFile = scanner.nextLine();
                            break;
                        case BREAK:
                            needBreak = true;
                    }
                }
            }
        }
        return allFilesContent;
    }

    private static Map.Entry<String, List<String>> readFile(String pathToFile, String fileName) {
        try {
            Path fullPath = Path.of("", pathToFile.split("/")).resolve(fileName);
            List<String> fileContent = Files.readAllLines(fullPath);
            return Map.entry(fileName, fileContent);
        } catch (IOException exception) {
            throw new FileReadingException("Can't read the file: " + fileName, exception);
        }
    }

    private static UserChoice getUserChoice(Scanner scanner) {
        String userInput;

        while (true) {
            printError();
            userInput = scanner.nextLine();
            try {
                return switch (userInput.toLowerCase().strip()) {
                    case "" -> UserChoice.RETRY;
                    case "break" -> UserChoice.BREAK;
                    case "path" -> UserChoice.PATH;
                    case "name" -> UserChoice.NAME;
                    default -> throw new IllegalStateException("Unexpected value: " + userInput);
                };
            } catch (IllegalStateException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void printError() {
        System.out.println("""
                Make sure the file's location is correct end press [Enter] button.
                You can specify a different location of the file or his name.
                Enter <"path"> to change the file search path,
                or <"name"> to select a file with a different name
                or <"brake"> if you want to skip this file""");
    }
}