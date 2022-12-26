package com.nium.interview.transfers.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.nium.interview.transfers.common.AbstractTest;
import com.nium.interview.transfers.model.Transfer;
import com.nium.interview.transfers.repository.TransferRepository;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.TransferService;
import com.nium.interview.transfers.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransferServiceTest  extends AbstractTest {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    @AfterEach
    void tearDown() {
        cleanResourcesDirectory();
    }

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferRepository transferRepository;

    @BeforeEach
    void setUp() {
        accountService.initAccounts(null);
        transferService.doTransfers(null);
    }

    @Test
    void testDoTransfers() {
        LocalDate localDate = DateUtil.toLocalDate("10/08/2055", DATE_PATTERN);
        assertTrue(StreamSupport.stream(transferRepository.findAll().spliterator(), true)
                .anyMatch(getTransactionPredicate(1445L, 60.0, 0L, 112233L, localDate)));
        assertTrue(StreamSupport.stream(transferRepository.findAll().spliterator(), true)
                .anyMatch(getTransactionPredicate(1446L, 25.03, 0L, 223344L, localDate)));

        localDate = DateUtil.toLocalDate("11/08/2055", DATE_PATTERN);
        assertTrue(StreamSupport.stream(transferRepository.findAll().spliterator(), true)
                .anyMatch(getTransactionPredicate(1447L, 67.67, 0L, 334455L, localDate)));
        assertTrue(StreamSupport.stream(transferRepository.findAll().spliterator(), true)
                .anyMatch(getTransactionPredicate(1448L, 11.11, 112233L, 223344L, localDate)));

        localDate = DateUtil.toLocalDate("13/08/2055", DATE_PATTERN);
        assertTrue(StreamSupport.stream(transferRepository.findAll().spliterator(), true)
                .anyMatch(getTransactionPredicate(1449L, 12.12, 112233L, 334455L, localDate)));
        assertTrue(StreamSupport.stream(transferRepository.findAll().spliterator(), true)
                .anyMatch(getTransactionPredicate(1450L, 6.018, 223344L, 334455L, localDate)));
    }

    private Predicate<Transfer> getTransactionPredicate(Long transactionId, double amount, Long sourceId, Long destinationId, LocalDate date) {
        return transfer -> transfer.getId().equals(transactionId) && transfer.getAmount() == amount && transfer.getSourceAccountId().equals(sourceId) && transfer.getDestinationAccountId().equals(destinationId) && transfer.getDate().equals(date);
    }

    @Test
    void testDoTransfers_invalidInputFileIncompleteData() {
        assertThrows(IllegalArgumentException.class, () -> transferService.doTransfers("transfers_incompleteData.txt"));
    }

}