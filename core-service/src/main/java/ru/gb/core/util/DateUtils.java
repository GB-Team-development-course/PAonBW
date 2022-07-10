package ru.gb.core.util;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public final static String DATE_FORMAT = "uuuu-MM-dd'T'HH:mm:ss'Z'";
    public final static String DATE_FORMAT_REPORT = "dd-MM-uuuu";

    public static LocalDateTime fromString(@NonNull final String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String currentTimeToFormattedString() {
        return toFormattedString(LocalDateTime.now());
    }

    public static String toFormattedString(@NonNull final LocalDateTime dateTime) {
        return dateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

}
