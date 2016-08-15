package com.github.coolrunner86.braintrain;

/**
 * Created by flex on 04.08.16.
 */
public class ReverseFlipGenerator implements PatternGenerator {
    private static final Byte[] PATTERN_REVERSE_FLIP = new Byte[] { 1, 9, 1, 8, 1, 7, 1, 6, 1,
                                                                    5, 1, 4, 1, 3, 1, 2, 1, 1,
                                                                    9, 8, 7, 6, 5, 4, 3, 2, 1 };

    @Override
    public Byte[] generatePattern() {
        return PATTERN_REVERSE_FLIP;
    }
}
