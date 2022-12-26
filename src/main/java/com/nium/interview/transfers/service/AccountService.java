package com.nium.interview.transfers.service;

import java.util.Set;

import com.nium.interview.transfers.model.Account;
import org.springframework.data.util.Pair;

/**
 * Classes implementing this interface, perform different operations on Account resource
 */
public interface AccountService {

    /**
     * Initializes account according to the ids given in the provided file
     *
     * @param accountInputFileName input file name containing ids of accounts
     */
    void initAccounts(String accountInputFileName);

    /**
     * Updates the source account and destination account by debiting and crediting respectively by the given amount
     *
     * @param sourceAccountId      the id of source account
     * @param destinationAccountId the id of destination account
     * @param amount               the amount to be debited and credited
     */
    void updateAccounts(Long sourceAccountId, Long destinationAccountId, double amount);

    /**
     * Finds account having the highest balance
     *
     * @return the accounts which have the (equally) highest balance
     */
    Set<Account> findAccountsWithHighestBalance();

    /**
     * Finds the most frequently used accounts as source
     *
     * @return the most frequently used accounts as source
     */
    Set<Account> findMostFrequentlyAccessedAccountsAsSource();

    /**
     * Finds the accounts' balance
     *
     * @return accounts' balance
     */
    Set<Pair<Long, Double>> findAccountsBalance();
}
