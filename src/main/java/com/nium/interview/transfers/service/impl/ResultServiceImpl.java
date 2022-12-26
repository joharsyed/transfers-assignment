package com.nium.interview.transfers.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.nium.interview.transfers.exception.FileException;
import com.nium.interview.transfers.model.Result;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.ResultService;
import com.nium.interview.transfers.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service is used to prepare and write the result
 */
@Service
public class ResultServiceImpl implements ResultService {

    private static final String DEFAULT_OUTPUT_FILENAME = "output.txt";

    private final AccountService accountService;

    @Autowired
    public ResultServiceImpl(final AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Prepares result containing accounts' balances, account having the highest balance and the most frequently used account as a source
     *
     * @return result
     */
    @Override
    public Result prepareResult() {
        final Result result = new Result();
        result.setBalances(accountService.findAccountsBalance());
        result.setAccountsWithHighestBalance(accountService.findAccountsWithHighestBalance());
        result.setMostFrequentlyAccessedAccountsAsSource(accountService.findMostFrequentlyAccessedAccountsAsSource());
        return result;
    }

    /**
     * Writes result to the given file
     *
     * @param result   result containing accounts' balances, account having the highest balance and the most frequently used account as a source
     * @param fileName filename, default file name is used if none is provided
     */
    @Override
    public void writeResultToFile(final Result result, final String fileName) {
        final String outputFileName = fileName == null ? DEFAULT_OUTPUT_FILENAME : fileName;
        final Path path = FileUtil.getPathByFileNameInResourceFolder(outputFileName);
        writeResult("#Balances\n", path, StandardOpenOption.TRUNCATE_EXISTING);
        result.getBalances().forEach(account -> writeResult("\n" + account.getFirst() + " - " + String.format("%.2f", account.getSecond()), path, StandardOpenOption.APPEND));
        writeResult("\n\n#Bank Account with highest balance\n", path, StandardOpenOption.APPEND);
        result.getAccountsWithHighestBalance().forEach(account -> writeResult("\n" + account.getId(), path, StandardOpenOption.APPEND));
        writeResult("\n\n#Frequently used source bank account\n", path, StandardOpenOption.APPEND);
        result.getMostFrequentlyAccessedAccountsAsSource().forEach(account -> writeResult("\n" + account.getId(), path, StandardOpenOption.APPEND));
    }

    void writeResult(final String str, final Path path, final OpenOption openOption) {
        try {
            Files.writeString(path, str, openOption);
        } catch (IOException e) {
            throw new FileException(String.format("Couldn't write [%s] to the file [%s]", str, path), e);
        }
    }
}
