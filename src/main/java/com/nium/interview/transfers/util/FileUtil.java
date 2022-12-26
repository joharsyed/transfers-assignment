package com.nium.interview.transfers.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.nium.interview.transfers.exception.FileException;

/**
 * Utility class to perform file operations on disk
 */
public final class FileUtil {

    private FileUtil() {

    }

    /**
     * Reads data from the given file
     *
     * @param fileName filename
     * @return a list where each entry is as the line read from the file
     */
    public static List<String> readLinesFromFile(final String fileName) {
        final Path path = getPathByFileNameInResourceFolder(fileName);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileException("Couldn't read the input file", e);
        }
    }

    /**
     * Finds the path of the given file in the resource folder of the application. The file will be created if it doesn't exist.
     *
     * @param fileName filename
     * @return the path of the file
     */
    public static Path getPathByFileNameInResourceFolder(final String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("File name is missing");
        }
        final Path newFilePath = Paths.get("src", "main", "resources", fileName);
        if (Files.exists(newFilePath)) {
            return newFilePath;
        }
        try {
            return Files.createFile(newFilePath);
        } catch (IOException e) {
            throw new FileException(String.format("Couldn't get the path of the file [%s]", fileName), e);
        }
    }
}
