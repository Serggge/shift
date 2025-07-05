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

    public static void writeData(Map<TypeAlias, List<String>> filesContent, Options options) {
        String prefix = options.getPrefix();
        boolean needAppend = options.isNeedAppend();
        Path pathToFile = createSubDirectories(options.getPathToFile());

        for (TypeAlias fileType : filesContent.keySet()) {
            if (!filesContent.get(fileType).isEmpty()) {
                String fullFileName = prefix + fileType.toString().toLowerCase() + ".txt";
                Path fullPath = pathToFile.resolve(fullFileName);

                if (needAppend) {
                    writeToFile(fullPath, filesContent.get(fileType), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } else {
                    writeToFile(fullPath, filesContent.get(fileType), StandardOpenOption.CREATE);
                }
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
        if (!pathToFile.isBlank()) {
            String[] directories = pathToFile.split("/");
            try {
                return Files.createDirectories(Path.of("", directories));
            } catch (IOException e) {
                System.out.printf("Can't create directories tree: %s. Files will be write to the current directory\n", pathToFile);
            }
        }
        return Path.of("");
    }
}