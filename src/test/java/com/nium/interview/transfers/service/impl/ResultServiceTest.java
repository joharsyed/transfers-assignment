package com.nium.interview.transfers.service.impl;

import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.nium.interview.transfers.common.AbstractTest;
import com.nium.interview.transfers.exception.FileException;
import com.nium.interview.transfers.model.Result;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.ResultService;
import com.nium.interview.transfers.service.TransferService;
import com.nium.interview.transfers.util.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResultServiceTest extends AbstractTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private TransferService transferService;

    private Result result;

    @BeforeEach
    void setUp() {
        accountService.initAccounts(null);
        transferService.doTransfers(null);
        result = resultService.prepareResult();
    }

    @AfterEach
    void tearDown() {
        cleanResourcesDirectory();
    }

    @Test
    void writeResults_providedFileName() {
        List<String> resultLines = FileUtil.readLinesFromFile("result.txt");
        Assertions.assertFalse(resultLines.contains("334455 - 85.81"));
        resultService.writeResultToFile(result, "result.txt");
        resultLines = FileUtil.readLinesFromFile("result.txt");
        Assertions.assertTrue(resultLines.contains("334455 - 85.81"));
    }

    @Test
    void writeResults_fileNameMissingFileExt() {
        List<String> resultLines = FileUtil.readLinesFromFile("results");
        Assertions.assertFalse(resultLines.contains("334455 - 85.81"));
        resultService.writeResultToFile(result, "results");
        resultLines = FileUtil.readLinesFromFile("results");
        Assertions.assertTrue(resultLines.contains("334455 - 85.81"));
    }
}