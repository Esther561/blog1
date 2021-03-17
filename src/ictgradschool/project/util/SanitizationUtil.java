package ictgradschool.project.util;

public class SanitizationUtil {
    public static String sanitize(String input) {
        String output;
        output = input.replaceAll("<", "&lt;");
        output = output.replaceAll(">", "&gt;");
        return output;
    }

    public static String sanitizeAndShorten(String input, int limit) {
        String output = sanitize(input);
        return output.substring(0, Math.min(limit, output.length()));
    }
}
