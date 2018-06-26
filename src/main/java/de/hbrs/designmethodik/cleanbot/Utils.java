package de.hbrs.designmethodik.cleanbot;

import java.util.Random;

public class Utils {

    private static final Random RANDOM = new Random();

    private Utils() {}

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) { }
    }

    public static <T extends Enum> T randomEnum(final T[] values) {
        return values[RANDOM.nextInt(values.length)];
    }

    public static <T> T requireNonNull(final T t) {
        if (t == null) throw new NullPointerException();
        return t;
    }
}
