package ar.com.wolox.android.example.utils;

import android.support.annotation.Nullable;

/**
 * Utilities to work with strings;
 */
public final class StringUtils {

    static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
            "*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    /**
     * Private constructor for utility class;
     */
    private StringUtils() {
    }

    public static boolean isEmail(@Nullable String text) {
        return text != null && text.matches(EMAIL_REGEX);
    }

    public static boolean isEmpty(@Nullable String text) {
        return text == null || text.length() == 0;
    }
}
