package util;

import model.TypeAlias;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSorter {

    private static final String FLOAT_PATTERN = "^(-?\\d+\\.\\d+(E[-+]\\d+)?)$";
    private static final String INTEGER_PATTERN = "^(-?\\d+)$";
    private static final String STRING_PATTERN = "^(.*[A-Za-zА-ЯЁа-яё]+[^+-].*$)";

    private DataSorter() {
    }

    public static Map<TypeAlias, List<String>> processData(Map<String, List<String>> filesContent) {
        Map<TypeAlias, List<String>> result = new HashMap<>();
        for (String fileName : filesContent.keySet()) {
            List<String> contentOfFile = filesContent.get(fileName);
            addToResult(contentOfFile, result, TypeAlias.FLOATS, Pattern.compile(FLOAT_PATTERN));
            addToResult(contentOfFile, result, TypeAlias.INTEGERS, Pattern.compile(INTEGER_PATTERN));
            addToResult(contentOfFile, result, TypeAlias.STRINGS, Pattern.compile(STRING_PATTERN));
        }
        return result;
    }

    private static void addToResult(List<String> contentOfFile, Map<TypeAlias, List<String>> result,
                                                             TypeAlias typeAlias, Pattern pattern) {
        List<String> contentByType = sortByPattern(contentOfFile, pattern);
        List<String> savedResult = result.getOrDefault(typeAlias, new LinkedList<>());
        savedResult.addAll(contentByType);
        result.put(typeAlias, savedResult);
    }


    private static List<String> sortByPattern(List<String> content, Pattern pattern) {
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
}
