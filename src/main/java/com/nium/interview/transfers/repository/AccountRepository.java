package com.nium.interview.transfers.repository;

import java.util.Set;

import com.nium.interview.transfers.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface for CRUD operations on the Account object
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * @return the accounts having the highest balance
     */
    @Query(value = "SELECT * FROM account a where a.balance = (select max(balance) from account)", nativeQuery = true)
    Set<Account> findAccountsWithHighestBalance();

    /**
     *
     * @return the most frequently used accounts as a source
     */
    @Query(value = "SELECT * FROM account a where a.accessed_as_source_frequency = (select max(accessed_as_source_frequency) from account)", nativeQuery = true)
    Set<Account> findMostFrequentlyAccessedAccountsAsSource();
}
