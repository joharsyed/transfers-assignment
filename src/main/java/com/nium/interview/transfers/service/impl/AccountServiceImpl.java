package com.nium.interview.transfers.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.nium.interview.transfers.model.Account;
import com.nium.interview.transfers.repository.AccountRepository;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.util.FileUtil;
import com.nium.interview.transfers.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * The service is used to perform operations on Account resource
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final String DEFAULT_INPUT_FILE = "accounts.txt";

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Initializes account according to the ids given in the provided file
     *
     * @param accountInputFileName input file name containing ids of accounts
     */
    @Override
    public void initAccounts(final String accountInputFileName) {
        final String fileName = accountInputFileName == null ? DEFAULT_INPUT_FILE : accountInputFileName;
        FileUtil.readLinesFromFile(fileName).forEach(this::createAccount);
    }

    private Account createAccount(final String accountId) {
        Validator.validateId(accountId);
        final Account account = new Account();
        account.setBalance(0.0);
        account.setId(Long.parseLong(accountId));
        account.setAccessedAsSourceFrequency(0);
        return accountRepository.save(account);
    }

    /**
     * Updates the source account and destination account by debiting and crediting respectively by the given amount
     *
     * @param sourceAccountId      the id of source account
     * @param destinationAccountId the id of destination account
     * @param amount               the amount to be debited and credited
     */
    @Override
    public void updateAccounts(final Long sourceAccountId, final Long destinationAccountId, final double amount) {
        if (amount <= 0.0) {
            throw new IllegalArgumentException(String.format("Amount should be higher than 0 [%s].", amount));
        }
        debitAccount(sourceAccountId, amount);
        creditAccount(destinationAccountId, amount);
    }

    Account debitAccount(final Long sourceAccountId, final Double amount) {
        final Optional<Account> sourceAccountOptional = accountRepository.findById(sourceAccountId);
        if (sourceAccountOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format("Couldn't find the account having id [%s].", sourceAccountId));
        }
        final Account sourceAccount = sourceAccountOptional.get();
        if (isBalanceSufficient(amount, sourceAccount)) {
            throw new IllegalStateException("Insufficient balance.");
        }
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        // update access frequency of source account by 1 if the source account's id is not 0
        if (0L != sourceAccount.getId()) {
            sourceAccount.setAccessedAsSourceFrequency(sourceAccount.getAccessedAsSourceFrequency() + 1);
        }
        return accountRepository.save(sourceAccount);
    }

    private boolean isBalanceSufficient(final double amount, final Account sourceAccount) {
        // account having id 0 doesn't have a balance based on the given requirements
        return 0L != sourceAccount.getId() && amount >= sourceAccount.getBalance();
    }

    private Account creditAccount(final Long destinationAccountId, final double amount) {
        final Optional<Account> destinationAccountOptional = accountRepository.findById(destinationAccountId);
        if (destinationAccountOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format("Couldn't find the account having id [%s].", destinationAccountId));
        }
        final Account destinationAccount = destinationAccountOptional.get();
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        return accountRepository.save(destinationAccount);
    }

    /**
     * Finds account having the highest balance
     *
     * @return the accounts which have the (equally) highest balance
     */
    @Override
    public Set<Account> findAccountsWithHighestBalance() {
        return accountRepository.findAccountsWithHighestBalance();
    }

    /**
     * Finds the most frequently used accounts as source
     *
     * @return the most frequently used accounts as source
     */
    @Override
    public Set<Account> findMostFrequentlyAccessedAccountsAsSource() {
        return accountRepository.findMostFrequentlyAccessedAccountsAsSource();
    }

    /**
     * Finds the accounts' balance
     *
     * @return accounts' balance
     */
    @Override
    public Set<Pair<Long, Double>> findAccountsBalance() {
        final Set<Pair<Long, Double>> results = new HashSet<>();
        accountRepository.findAll().forEach(account -> {
            if (0L != account.getId()) {
                results.add(Pair.of(account.getId(), account.getBalance()));
            }
        });
        return results;
    }
}
