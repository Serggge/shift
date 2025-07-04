import model.Options;
import model.TypeAlias;
import util.*;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String input = "-s -a -p sample- -o /some/path 1.txt 2.txt";

        Options options = OptionsAnalyzer.analyze(input.split(" "));
        Map<String, List<String>> filesContent = ProjectFileReader.readByOptions(options);
        Map<TypeAlias, List<String>> sortedContent = DataSorter.processData(filesContent);
        StatsPrinter.print(sortedContent, options);
        ProjectFileWriter.writeData(sortedContent, options);
    }
}