package model;

import java.util.Collections;
import java.util.List;

public class Options {

    private final boolean isFull;
    private final boolean isShort;
    private final boolean needAppend;
    private final String  prefix;
    private final String pathToFile;
    private final List<String> filesToRead;

    private Options(Builder builder) {
        this.isFull = builder.isFull;
        this.isShort = builder.isShort;
        this.needAppend = builder.needAppend;
        this.prefix = builder.prefix;
        this.pathToFile = builder.pathToFile;
        this.filesToRead = builder.filesToRead;
    }

    public List<String> getFileList() {
        return Collections.unmodifiableList(filesToRead);
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isShort() {
        return isShort;
    }

    public boolean isNeedAppend() {
        return needAppend;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean isFull;
        private boolean isShort;
        private boolean needAppend;
        private String prefix;
        private String pathToFile;
        private List<String> filesToRead;

        public Builder isFull(boolean isFull) {
            this.isFull = isFull;
            return this;
        }

        public Builder isShort(boolean isShort) {
            this.isShort =isShort;
            return this;
        }

        public Builder needAppend(boolean needAppend) {
            this.needAppend = needAppend;
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder pathToFile(String pathToFile) {
            this.pathToFile = pathToFile;
            return this;
        }

        public Builder filesToRead(List<String> filesToRead) {
            this.filesToRead = filesToRead;
            return this;
        }

        public Options build() {
            return new Options(this);
        }
    }
}
