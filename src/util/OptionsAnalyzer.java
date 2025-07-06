package util;

import exception.UnknownParameterException;
import model.Options;

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
    private static final Pattern pattern = Pattern.compile(OPTIONS_PATTERN);

    private OptionsAnalyzer() {
    }

    public static Options analyze(String[] args) {
        String parametersLine = String.join(" ", args);
        Matcher matcher = pattern.matcher(parametersLine);
        Options options = Options.defaultOptions();

        while (true) {
            try {
                while (matcher.find()) {
                    String match = matcher.group();
                    if ("-s".equals(match)) {
                        options.setShort(true);
                    } else if ("-f".equals(match)) {
                        options.setFull(true);
                    } else if ("-a".equals(match)) {
                        options.setNeedAppend(true);
                    } else if (match.startsWith("-p ")) {
                        String prefix = matcher.group("prefix");
                        options.setPrefix(prefix);
                    } else if (match.startsWith("-o ")) {
                        String pathToResult = matcher.group("path");
                        options.setPathToResult(pathToResult);
                    } else if (match.endsWith(".txt")) {
                        String fileName = matcher.group("filename");
                        options.addFileToRead(fileName);
                    } else {
                        throw new UnknownParameterException("Unknown parameter: " + match);
                    }
                }
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
                matcher = changeParametersMatcher();
                continue;
            }
            break;
        }
        return options;
    }

    private static Matcher changeParametersMatcher() {
        printErrorMessage();
        Scanner scanner = new Scanner(System.in);
        String newCommandLine = scanner.nextLine();
        return pattern.matcher(newCommandLine);
    }

    private static void printErrorMessage() {
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
}
