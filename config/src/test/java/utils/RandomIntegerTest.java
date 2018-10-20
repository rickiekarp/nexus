package utils;

import net.rickiekarp.core.util.CommonUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RandomIntegerTest {

    @Test
    public void testNumberGeneration() {
        int actual;
        for (int i = 1; i <= 10; i++) {
            actual = CommonUtil.randInt(1, i);
            assertTrue(actual >= 1 && actual <= i);
        }
    }
}
