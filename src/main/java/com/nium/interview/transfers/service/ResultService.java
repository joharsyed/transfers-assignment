package com.nium.interview.transfers.service;

import com.nium.interview.transfers.model.Result;

/**
 * Classes implementing this interface, prepare result and write it to a file
 */
public interface ResultService {

    /**
     * Prepares result containing accounts' balances, account having the highest balance and the most frequently used account as a source
     * @return result
     */
    Result prepareResult();

    /**
     * Writes result to the given file
     * @param result result containing accounts' balances, account having the highest balance and the most frequently used account as a source
     * @param fileName filename, default file name is used if none is provided
     */
    void writeResultToFile(Result result, String fileName);
}
