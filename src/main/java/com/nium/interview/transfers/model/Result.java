package com.nium.interview.transfers.model;

import java.util.Set;

import lombok.Data;
import org.springframework.data.util.Pair;

/**
 * POJO representing a result
 */
@Data
public class Result {
    private Set<Pair<Long, Double>> balances;
    private Set<Account> accountsWithHighestBalance;
    private Set<Account> mostFrequentlyAccessedAccountsAsSource;
}
