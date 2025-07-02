package util;

import model.Options;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionsAnalyzer {

    private static final String OPTIONS_PATTERN =
            "(?<full>-f\\b)" +
                    "|(?<short>-s\\b)" +
                    "|(?<append>-a\\b)" +
                    "|(-o\\s(?<path>(?:([/\\\\])\\w+)+\\b))" +
                    "|(?:-p\\s(?<prefix>\\w+[_-]*))" +
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
        while (matcher.find()) {
            try {
                String match = matcher.group();
                if (match.equals("-s")) {
                    isShort = true;
                } else if (match.equals("-f")) {
                    isFull = true;
                } else if (match.equals("-a")) {
                    needAppend = true;
                } else if (match.startsWith("-p ")) {
                    prefix = matcher.group("prefix");
                } else if (match.startsWith("-o ")) {
                    pathToFile = matcher.group("path");
                } else if (match.endsWith(".txt")) {
                    filesToRead.add(matcher.group("filename"));
                } else {
                    System.out.println("Unknown command: " + match);
                }
            } catch (RuntimeException exception) {
                System.out.println("Error get group");
            }
        }
        return buildOptions(isShort, isFull, needAppend, prefix, pathToFile, filesToRead);
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
