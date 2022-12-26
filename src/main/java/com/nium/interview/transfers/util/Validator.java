package com.nium.interview.transfers.util;

import java.util.regex.Pattern;

/**
 * Utility class to perform data invalidation
 */
public final class Validator {

    private static final Pattern NON_NEGATIVE_NUMBER_PATTERN = Pattern.compile("\\d+");

    private Validator() {

    }

    /**
     * @param id id
     * @return return id after validating
     */
    public static String validateId(final String id) {
        if (!NON_NEGATIVE_NUMBER_PATTERN.matcher(id).matches()) {
            throw new IllegalArgumentException("Invalid Id " + id);
        }
        return id;
    }
}
