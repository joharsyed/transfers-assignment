package com.nium.interview.transfers.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.List;

import com.nium.interview.transfers.common.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileUtilTest extends AbstractTest {

    @AfterEach
    void tearDown() {
        cleanResourcesDirectory();
    }

    @Test
    void readLinesFromFile() {
        List<String> lines = FileUtil.readLinesFromFile("accounts.txt");
        assertTrue(lines.contains("0"));
        assertTrue(lines.contains("112233"));
        assertTrue(lines.contains("223344"));
        assertTrue(lines.contains("334455"));
    }

    @Test
    void getPathByFileNameInResourceFolder() {
        Assertions.assertEquals(Paths.get("src", "main", "resources", "accounts.txt").toString(), FileUtil.getPathByFileNameInResourceFolder("accounts.txt").toString());
    }

    @Test
    void getPathByFileNameInResourceFolder_nonExistingFile() {
        Assertions.assertEquals(Paths.get("src", "main", "resources", "non_existing_file.txt").toString(), FileUtil.getPathByFileNameInResourceFolder("non_existing_file.txt").toString());
    }

    @Test
    void readLinesFromFile_fileIsMissing() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> FileUtil.readLinesFromFile(null));
    }
}