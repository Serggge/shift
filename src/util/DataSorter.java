package util;

import model.TypeAlias;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSorter {

    private static final String FLOAT_PATTERN = "^(-?\\d+\\.\\d+(E[-+]\\d+)?)$";
    private static final String INTEGER_PATTERN = "^(-?\\d+)$";
    private static final String STRING_PATTERN = "^(.*[A-Za-zА-ЯЁа-яё]{2,}.*$)";

    private DataSorter() {
    }

    public static Map<TypeAlias, List<String>> processData(Map<String, List<String>> filesContent) {
        Map<TypeAlias, List<String>> sortedContent = new HashMap<>();

        for (String fileName : filesContent.keySet()) {
            List<String> contentOfFile = Collections.unmodifiableList(filesContent.get(fileName));

            List<String> floatContent = sortContentByPattern(contentOfFile, Pattern.compile(FLOAT_PATTERN));
            addToResultMap(TypeAlias.FLOATS, sortedContent, floatContent);

            List<String> integerContent = sortContentByPattern(contentOfFile, Pattern.compile(INTEGER_PATTERN));
            addToResultMap(TypeAlias.INTEGERS, sortedContent, integerContent);

            List<String> stringContent = sortContentByPattern(contentOfFile, Pattern.compile(STRING_PATTERN));
            addToResultMap(TypeAlias.STRINGS, sortedContent, stringContent);
        }
        return sortedContent;
    }

    private static List<String> sortContentByPattern(List<String> content, Pattern pattern) {
        List<String> result = new LinkedList<>();
        Matcher matcher;

        for (String line : content) {
            matcher = pattern.matcher(line);
            if (matcher.matches()) {
                result.add(matcher.group(1));
            }
        }
        return result;
    }

    private static void addToResultMap(TypeAlias typeAlias, Map<TypeAlias, List<String>> resultMap, List<String> content) {
        List<String> currentContent = resultMap.getOrDefault(typeAlias, new LinkedList<>());
        currentContent.addAll(content);
        resultMap.put(typeAlias, currentContent);
    }
}