package ch.ledcom.janki.utils;

import java.util.Date;

import static java.lang.Math.toIntExact;

public class DateUtils {
    public static Integer nowAsInt() {
        return toIntExact(new Date().getTime() / 1000);
    }
}
