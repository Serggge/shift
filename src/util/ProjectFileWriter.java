package util;

import model.Options;
import model.TypeAlias;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class ProjectFileWriter {

    private ProjectFileWriter() {
    }

    public static void writeData(Map<TypeAlias, List<String>> content, Options options) {
        String prefix = options.getPrefix();
        boolean needAppend = options.isNeedAppend();

        Path pathToFile = createSubDirectories(options.getPathToFile());

        for (TypeAlias type : content.keySet()) {
            if (!content.get(type).isEmpty()) {
                String fullFileName = prefix + type.toString().toLowerCase() + ".txt";
                Path fullPath = pathToFile.resolve(fullFileName);
                OpenOption appendOption = needAppend ? StandardOpenOption.APPEND : StandardOpenOption.WRITE;
                writeToFile(fullPath, content.get(type), StandardOpenOption.CREATE, appendOption);
            }
        }
    }

    private static void writeToFile(Path path, List<String> content, OpenOption... openOptions) {
        try {
            Files.write(path, content, StandardCharsets.UTF_8, openOptions);
        } catch (IOException e) {
            System.out.println("Can't write to file: " + path.getFileName());
        }
    }

    private static Path createSubDirectories(String pathToFile) {
        Path newPath = Path.of("");
        if (!pathToFile.isBlank()) {
            String[] directories = pathToFile.split("/");
            try {
                return Files.createDirectories(Path.of("", directories));
            } catch (IOException e) {
                System.out.println("Can't create directories tree: " + pathToFile);
            }
        }
        return newPath;
    }
}
