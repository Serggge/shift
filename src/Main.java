import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }
        String userInput = "-s -a -o /some/path/value -p sample- in1.txt in2.txt";
        String optionsPattern = "(-[sapfo]\\w?)|([a-z]+-\\s??)|(\\w+\\.txt)|((/?[a-z]+/?)+)";
        Pattern pattern = Pattern.compile(optionsPattern);
        Matcher matcher = pattern.matcher(userInput);
/*        String optionsLine = String.join(" ", args);*/

        while (matcher.find()) {
            try {
                System.out.println(matcher.group());
                System.out.println("group 1 success read");
            } catch (RuntimeException exception) {
                System.out.println("Error get group");
            }
        }


/*        String floatPattern = "^-?\\d+[.,]?\\d+(E([+\\-])\\d+)?$";
        Pattern pattern = Pattern.compile(floatPattern);
        Matcher matcher = pattern.matcher("1,55555");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filename:");
        String fileName = scanner.nextLine();
        scanner.close();
        String absolutePath = fileName;

        Path path = Paths.get(absolutePath);
        List<String> strings = Collections.emptyList();
        try {
            strings = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Something wrong");
        }

        strings.forEach(System.out::println);*/
    }
}