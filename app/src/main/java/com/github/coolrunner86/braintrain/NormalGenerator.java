package com.github.coolrunner86.braintrain;

/**
 * Created by flex on 04.08.16.
 */
public class NormalGenerator implements PatternGenerator {
    private static final Byte[] PATTERN_NORMAL = new Byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9,
                                                              1, 1, 1, 2, 1, 3, 1, 4, 1,
                                                              5, 1, 6, 1, 7, 1, 8, 1, 9 };

    @Override
    public Byte[] generatePattern() {
        return PATTERN_NORMAL;
    }
}
