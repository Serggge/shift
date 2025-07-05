package util;

import model.Options;
import model.TypeAlias;
import model.UserChoice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StatsPrinter {

    private StatsPrinter() {
    }

    public static void print(Map<TypeAlias, List<String>> content, Options options) {
        if (options.isFull()) {
            printShortInfo(content);
            printFullInfo(content);
        } else if (options.isShort()) {
            printShortInfo(content);
        } else {
            UserChoice userChoice = getUserChoice();
            switch (userChoice) {
                case SHORT:
                    printShortInfo(content);
                    break;
                case FULL:
                    printFullInfo(content);
            }
        }
    }

    private static void printShortInfo(Map<TypeAlias, List<String>> content) {
        System.out.println("Short statistic: ");
        content.forEach((key, value) ->
                System.out.printf("%s: number of elements: %d\n", key, value.size()));
    }

    private static void printFullInfo(Map<TypeAlias, List<String>> content) {
        System.out.println("Full statistic: ");
        if (content.containsKey(TypeAlias.FLOATS) && !content.get(TypeAlias.FLOATS).isEmpty()) {
            printForFloatContent(content.get(TypeAlias.FLOATS));
        }
        if (content.containsKey(TypeAlias.INTEGERS) && !content.get(TypeAlias.INTEGERS).isEmpty()) {
            printForIntegerContent(content.get(TypeAlias.INTEGERS));
        }
        if (content.containsKey(TypeAlias.STRINGS) && !content.get(TypeAlias.STRINGS).isEmpty()) {
            printForStringContent(content.get(TypeAlias.STRINGS));
        }
    }

    private static void printForFloatContent(List<String> floats) {
        BigDecimal max = BigDecimal.valueOf(Double.MIN_VALUE);
        BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal sum = BigDecimal.ZERO;
        int size = 0;

        for (String number : floats) {
            try {
                BigDecimal doubleValue = new BigDecimal(number);
                max = max.max(doubleValue);
                min = min.min(doubleValue);
                sum = sum.add(doubleValue);
                size++;
            } catch (NumberFormatException exception) {
                System.out.println("Float parse error: " + exception.getMessage());
            }
        }
        System.out.printf("""
                Floats:
                Max: %f, Min: %f, Sum: %f, Average: %f
                """, max, min, sum, sum.divide(BigDecimal.valueOf(size), RoundingMode.HALF_UP));
    }

    private static void printForIntegerContent(List<String> ints) {
        BigInteger max = BigInteger.valueOf(Long.MIN_VALUE);
        BigInteger min = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger sum = BigInteger.ZERO;
        int size = 0;

        for (String number : ints) {
            try {
                BigInteger intValue = new BigInteger(number);
                max = max.max(intValue);
                min = min.min(intValue);
                sum = sum.add(intValue);
                size++;
            } catch (NumberFormatException exception) {
                System.out.println("Integer parse error: " + exception.getMessage());
            }
        }
        System.out.printf("""
                Integers:
                Max: %d, Min: %d, Sum: %d, Average: %d
                """, max, min, sum, (sum.divide(BigInteger.valueOf(size))));
    }

    private static void printForStringContent(List<String> strings) {
        int longest = 0;
        int shortest = Integer.MAX_VALUE;

        for (String line : strings) {
            longest = Math.max(longest, line.length());
            shortest = Math.min(shortest, line.length());
        }
        System.out.printf("""
                Strings:
                Longest string: %d, Shortest string: %d
                """, longest, shortest);
    }

    private static UserChoice getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose statistics [short/full]:");
            String userInput = scanner.nextLine().toUpperCase();
            try {
                return switch (userInput) {
                    case "SHORT" -> UserChoice.SHORT;
                    case "FULL" -> UserChoice.FULL;
                    default -> throw new IllegalStateException("Unexpected value: " + userInput);
                };
            } catch (IllegalStateException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}