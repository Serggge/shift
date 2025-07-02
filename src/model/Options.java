package model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import java.util.Collections;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Options {

    boolean isFull;
    boolean isShort;
    boolean needAppend;
    String prefix;
    String pathToFile;
    @Getter(AccessLevel.PRIVATE)
    List<String> filesToRead;

    public List<String> getFileList() {
        return Collections.unmodifiableList(filesToRead);
    }
}
