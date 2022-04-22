package net.katool.common;

import java.time.format.DateTimeFormatter;

/**
 * Created with hongchen.cao
 * Date: 2018-11-29 15:35
 */
public class DateTimeFormatUtils {
    public static final DateTimeFormatter DATETIME_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATETIME_SECOND_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_SECOND_PLAIN_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter DATE_PLAIN_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_LINE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

}
