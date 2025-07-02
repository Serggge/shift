package util;

import model.Options;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectFileReader {

    private ProjectFileReader() {
    }

    public static Map<String, List<String>> readByOptions(Options options) {
        Map<String, List<String>> result = new LinkedHashMap<>();
        List<String> fileList = options.getFileList();
        for (String fileName : fileList) {
            try {
                List<String> fileContent = Files.readAllLines(Path.of(fileName));
                result.put(fileName, fileContent);
            } catch (IOException e) {
                System.out.println("Can't read the file: " + fileName);
            }
        }
        return result;
    }
}