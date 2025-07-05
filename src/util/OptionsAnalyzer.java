package util;

import exception.UnknownParameterException;
import model.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionsAnalyzer {

    private static final String OPTIONS_PATTERN =
                    "(?<full>-f\\b)" +
                    "|(?<short>-s\\b)" +
                    "|(?<append>-a\\b)" +
                    "|(-o\\s(?<path>(?:/\\w+)+))" +
                    "|(-p\\s(?<prefix>\\w+[_-]?))" +
                    "|(?<filename>\\w+.txt)";

    private OptionsAnalyzer() {
    }

    public static Options analyze(String[] args) {
        Pattern pattern = Pattern.compile(OPTIONS_PATTERN);
        Matcher matcher = pattern.matcher(String.join(" ", args));
        boolean isShort = false;
        boolean isFull = false;
        boolean needAppend = false;
        String prefix = "";
        String pathToFile = "";
        List<String> filesToRead = new ArrayList<>();

        while (true) {
            try {
                while (matcher.find()) {
                    String match = matcher.group();
                    if ("-s".equals(match)) {
                        isShort = true;
                    } else if ("-f".equals(match)) {
                        isFull = true;
                    } else if ("-a".equals(match)) {
                        needAppend = true;
                    } else if (match.startsWith("-p ")) {
                        prefix = matcher.group("prefix");
                    } else if (match.startsWith("-o ")) {
                        pathToFile = matcher.group("path");
                    } else if (match.endsWith(".txt")) {
                        filesToRead.add(matcher.group("filename"));
                    } else {
                        throw new UnknownParameterException("Unknown parameter: " + match);
                    }
                }
            } catch (RuntimeException exception) {
                printErrorMessage(exception.getMessage());
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                matcher.reset();
                matcher = pattern.matcher(userInput);
                continue;
            }
            break;
        }
        return buildOptions(isShort, isFull, needAppend, prefix, pathToFile, filesToRead);
    }

    private static void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        System.out.println("""
                The following parameters apply:
                -s : to display short statistics
                -f : to display full statistics
                -a : if need to append data to file
                -p <prefix> : sets the prefix for the resulting files
                -o <path> : specifies the path to the location of the resulting files
                file1.txt file2.txt <more> : list text files which need to read and sort data
                """);
    }

    private static Options buildOptions(boolean isShort, boolean isFull, boolean needAppend, String prefix,
                                        String pathToFile, List<String> filesToRead) {
        return Options.builder()
                      .isShort(isShort)
                      .isFull(isFull)
                      .needAppend(needAppend)
                      .prefix(prefix)
                      .pathToFile(pathToFile)
                      .filesToRead(filesToRead)
                      .build();
    }
}
