package com.nium.interview.transfers.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorTest {


    @Test
    void testValidateId() {
        Validator.validateId("3325");
    }

    @Test
    void testValidateId_negativeId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateId("-3325"));
    }
}