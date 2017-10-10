package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.exception.BcptDatabaseException;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Validation Util
 *
 * Created by Alex Elkin on 11.09.2017.
 */
public class ValidationUtil {

    public static void validateNotEmpty(String string, String message) {
        if (StringUtils.isEmpty(string)) throw new BcptDatabaseException(message);
    }

    public static void validateNotEmpty(String[] strings, String message) {
        validateNotNull(strings, message);
        Arrays.stream(strings).forEach(s -> validateNotEmpty(s, message));
    }

    public static void validateNotNull(Object o, String message) {
        if (o == null) throw new BcptDatabaseException(message);
    }

    public static void validateTrue(Boolean value, String message) {
        if (value == null || !value) throw new BcptDatabaseException(message);
    }
}
