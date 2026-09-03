package hexlet.code.util;

public final class Strings {

    private static final int DEFAULT_MAX_LENGTH = 200;

    private Strings() {
    }

    public static String truncate(String value) {
        return truncate(value, DEFAULT_MAX_LENGTH);
    }

    public static String truncate(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength) + "...";
    }
}
