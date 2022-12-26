package com.nium.interview.transfers.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to perform date into different formats
 */
public final class DateUtil {
    private DateUtil() {

    }

    /**
     *
     * @param date the date which format to be changed
     * @param datePattern the pattern which is used to format the date to
     * @return the formatted date
     */
    public static LocalDate toLocalDate(final String date, final String datePattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(date, formatter);
    }
}
