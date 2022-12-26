package com.nium.interview.transfers.service;

/**
 * Classes implementing this interface, perform transfers between accounts
 */
public interface TransferService {

    /**
     * Carries out transfers according to the given file
     * @param transferFileName the name of the file containing the transfer details
     */
    void doTransfers(String transferFileName);
}
