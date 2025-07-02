import model.Options;
import model.TypeAlias;
import util.*;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        args = new String[8];
        args[0] = "-f";
        args[1] = "-a";
        args[2] = "-o";
        args[3] = "/some/path/value";
        args[4] = "-p";
        args[5] = "sample_";
        args[6] = "1.txt";
        args[7] = "2.txt";

        Options options = OptionsAnalyzer.analyze(args);
        Map<String, List<String>> filesContent = ProjectFileReader.readByOptions(options);
        Map<TypeAlias, List<String>> sortedContent = DataSorter.processData(filesContent);
        StatsPrinter.print(sortedContent, options);
        ProjectFileWriter.writeData(sortedContent, options);
    }
}