package com.github.coolrunner86.braintrain;

import java.security.SecureRandom;

/**
 * Created by flex on 04.08.16.
 */
public class RandomGenerator implements PatternGenerator {

    @Override
    public Byte[] generatePattern() {
        SecureRandom r = new SecureRandom();
        int[] count = new int[10];
        Byte[] result = new Byte[NUMBERS_COUNT];

        for(int i = 0; i < 10; i++)
            count[i] = 0;

        for(int i = 0; i < NUMBERS_COUNT; i++)
        {
            byte b = (byte)(r.nextInt(8) + 1);

            if(count[b] == 3)
            {
                i--;
                continue;
            }

            count[b]++;
            result[i] = b;
        }

        return result;
    }
}
