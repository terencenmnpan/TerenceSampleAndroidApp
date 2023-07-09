package io.terence.myapplication.config;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {
    @TypeConverter
    public static LocalDate localDateFromString(String stringValue) {
        return stringValue == null ? null : LocalDate.parse(stringValue);
    }

    @TypeConverter
    public static String localDateToString(LocalDate localDate) {
        return localDate == null ? null : localDate.toString();
    }
}
