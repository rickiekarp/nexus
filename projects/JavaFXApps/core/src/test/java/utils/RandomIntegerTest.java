package utils;

import net.rickiekarp.core.util.CommonUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomIntegerTest {

    @Test
    void testNumberGeneration() {
        int actual;
        for (int i = 1; i <= 10; i++) {
            actual = CommonUtil.randInt(1, i);
            assertTrue(actual >= 1 && actual <= i);
        }
    }
}
