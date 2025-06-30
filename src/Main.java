import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String regEx = "^?-+\\d?\\.+\\d(E(- | \\+)+\\d)?$";
        Pattern pattern = Pattern.compile("^-?\\d+[.,]?\\d+(E([+\\-])\\d+)?$");
        Matcher matcher = pattern.matcher("1,55555");

        System.out.println(matcher.find());

    }
}