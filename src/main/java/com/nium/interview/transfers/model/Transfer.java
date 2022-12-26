package com.nium.interview.transfers.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Data class for Transfer
 */

@Getter
@Setter
@Entity
public class Transfer {

    @Id
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Double amount;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer)) return false;
        Transfer that = (Transfer) o;
        return new EqualsBuilder().append(id, that.id).append(sourceAccountId, that.sourceAccountId).append(destinationAccountId, that.destinationAccountId)
                .append(amount, that.amount).append(date, that.date).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(sourceAccountId)
                .append(destinationAccountId).append(amount).append(date).toHashCode();
    }
}
