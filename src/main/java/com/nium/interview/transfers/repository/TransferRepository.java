package com.nium.interview.transfers.repository;

import com.nium.interview.transfers.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for CRUD operations on the Transfer object
 */
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
