package com.nium.interview.transfers.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Data class for Account
 */
@Getter
@Setter
@Entity
public class Account {

    @Id
    private Long id;
    private Double balance;
    private Integer accessedAsSourceFrequency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account that = (Account) o;
        return new EqualsBuilder().append(id, that.id).append(balance, that.balance).append(accessedAsSourceFrequency, that.accessedAsSourceFrequency).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(balance).append(accessedAsSourceFrequency).toHashCode();
    }
}
