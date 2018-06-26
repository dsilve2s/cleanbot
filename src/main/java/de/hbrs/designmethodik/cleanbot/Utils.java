package de.hbrs.designmethodik.cleanbot;

public class Utils {
    private Utils() {}

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) { }
    }

    public static <T> T requireNonNull(final T t) {
        if (t == null) throw new NullPointerException();
        return t;
    }
}
