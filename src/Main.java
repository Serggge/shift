import model.Options;
import model.TypeAlias;
import util.*;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Options options = OptionsAnalyzer.analyze(args);
        Map<String, List<String>> filesContent = ProjectFileReader.readByOptions(options);
        Map<TypeAlias, List<String>> sortedContent = DataSorter.processData(filesContent);
        StatsPrinter.print(sortedContent, options);
        ProjectFileWriter.writeData(sortedContent, options);
    }
}