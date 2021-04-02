package com.nextplugins.testserver.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yy à HH:mm:ss");

    public static String format(long time) {
        return FORMAT.format(new Date(time)).replace("à", "às");
    }

}
