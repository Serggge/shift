package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Options {

    private boolean isFull;
    private boolean isShort;
    private boolean needAppend;
    private String  prefix;
    private String pathToResult;
    private final List<String> filesToRead;

    private Options(boolean isFull, boolean isShort, boolean needAppend, String prefix, String pathToResult, List<String> filesToRead) {
        this.isFull = isFull;
        this.isShort = isShort;
        this.needAppend = needAppend;
        this.prefix = prefix;
        this.pathToResult = pathToResult;
        this.filesToRead = filesToRead;
    }

    public static Options defaultOptions() {
        return new Options(false, false, false, "", "", new LinkedList<>());
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

    public String getPathToResult() {
        return pathToResult;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public void setShort(boolean aShort) {
        isShort = aShort;
    }

    public void setNeedAppend(boolean needAppend) {
        this.needAppend = needAppend;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setPathToResult(String pathToResult) {
        this.pathToResult = pathToResult;
    }

    public void addFileToRead(String fileName) {
        filesToRead.add(fileName);
    }
}