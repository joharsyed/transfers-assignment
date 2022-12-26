package com.nium.interview.transfers.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import com.nium.interview.transfers.common.AbstractTest;
import com.nium.interview.transfers.repository.AccountRepository;
import com.nium.interview.transfers.repository.TransferRepository;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.TransferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

@SpringBootTest
class AccountServiceTest extends AbstractTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferRepository transferRepository;

    @BeforeEach
    void setup() {
        accountRepository.deleteAll();
        transferRepository.deleteAll();
        accountService.initAccounts(null);
        transferService.doTransfers(null);
    }

    @AfterEach
    void tearDown() {
        cleanResourcesDirectory();
    }

    @Test
    void testInitAccounts_success() {
        assertTrue(accountRepository.findById(112233L).isPresent());
    }

    @Test
    void testInitAccounts_invalid() {
        assertThrows(IllegalArgumentException.class, () -> accountService.initAccounts("invalid_accounts.txt"));
    }

    @Test
    void testUpdateAccounts_nonExistingSourceAccountId() {
        assertThrows(IllegalArgumentException.class, () -> accountService.updateAccounts(123456L, 112233L, 3));
    }

    @Test
    void testUpdateAccounts_nonExistingDestinationAccountId() {
        assertThrows(IllegalArgumentException.class, () -> accountService.updateAccounts(112233L, 123456L, 1));
    }

    @Test
    void testUpdateAccounts_invalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> accountService.updateAccounts(0L, 112233L, 0));
    }

    @Test
    void testFindAccountsWithHighestBalance() {
        assertEquals(334455, accountService.findAccountsWithHighestBalance().iterator().next().getId());
    }

    @Test
    void testFindAccountsWithHighestBalance_noAccountsExists() {
        accountRepository.deleteAll();
        assertTrue(accountService.findAccountsWithHighestBalance().isEmpty());
    }

    @Test
    void testFindMostFrequentlyAccessedAccounts() {
        assertEquals(112233, accountService.findMostFrequentlyAccessedAccountsAsSource().iterator().next().getId());
    }

    @Test
    void testFindMostFrequentlyAccessedAccounts_noAccountsExists() {
        accountRepository.deleteAll();
        assertTrue(accountService.findMostFrequentlyAccessedAccountsAsSource().isEmpty());
    }

    @Test
    void testFindAccountsBalance() {
        Set<Pair<Long, Double>> accountsBalance = accountService.findAccountsBalance();
        assertEquals(36.77, accountsBalance.stream().filter(pair -> 112233L == pair.getFirst()).findFirst().get().getSecond());
        assertEquals(30.122, accountsBalance.stream().filter(pair -> 223344L == pair.getFirst()).findFirst().get().getSecond());
        assertEquals(85.808, accountsBalance.stream().filter(pair -> 334455L == pair.getFirst()).findFirst().get().getSecond());
    }

    @Test
    void testFindAccountsBalance_noAccountsExists() {
        accountRepository.deleteAll();
        assertTrue(accountService.findAccountsBalance().isEmpty());
    }

    @Test
    void testDebitSourceAccount_insufficientBalance() {
        assertThrows(IllegalStateException.class, () -> ((AccountServiceImpl) accountService).debitAccount(112233L, 1000.0));
    }
}