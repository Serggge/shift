package util;

import model.Options;
import model.TypeAlias;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StatsPrinter {

    private StatsPrinter() {
    }

    public static void print(Map<TypeAlias, List<String>> content, Options options) {
        if (options.isFull()) {
            printShort(content);
            printFull(content);
        } else if (options.isShort()) {
            printShort(content);
        } else {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choose statistics [short/full]:");
                String userInput = scanner.nextLine().toUpperCase();
                switch (userInput) {
                    case "SHORT":
                        printShort(content);
                        return;
                    case "FULL":
                        printFull(content);
                        return;
                    default:
                        System.out.println("Incorrect input, try again!");
                }
            }
        }
    }

    private static void printShort(Map<TypeAlias, List<String>> content) {
        System.out.println("Short statistic: ");
        content.forEach((key, value) -> System.out.println(key + ": number of elements: " + value.size()));
    }

    private static void printFull(Map<TypeAlias, List<String>> content) {
        System.out.println("Full statistic: ");
        if (content.containsKey(TypeAlias.FLOATS) && !content.get(TypeAlias.FLOATS)
                                                             .isEmpty()) {
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
        double max = Double.MIN_VALUE;
        double min = Double.MIN_VALUE;
        double sum = 0;
        for (String line : floats) {
            double doubleValue = Double.parseDouble(line);
            max = Math.max(max, doubleValue);
            min = Math.min(min, doubleValue);
            sum+= doubleValue;
        }
        System.out.printf("Float statistic:\nMax: %f, Min: %f, Sum: %f, Average: %f\n", max, min, sum, (sum / floats.size()));
    }

    private static void printForIntegerContent(List<String> ints) {
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;
        long sum = 0L;
        for (String line : ints) {
            long intValue = Long.parseLong(line);
            max = Math.max(max, intValue);
            min = Math.min(min, intValue);
            sum+= intValue;
        }
        System.out.printf("Integer statistic:\nMax: %d, Min: %d, Sum: %d, Average: %f\n", max, min, sum, ((double) sum / ints.size()));
    }

    private static void printForStringContent(List<String> strings) {
        int longest = 0;
        int shortest = Integer.MAX_VALUE;
        for (String line : strings) {
            longest = Math.max(longest, line.length());
            shortest = Math.min(shortest, line.length());
        }
        System.out.printf("String statistic:\nLongest string: %d, Shortest string: %d\n", longest, shortest);
    }

}
