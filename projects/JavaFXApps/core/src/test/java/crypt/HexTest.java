package crypt;

import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.util.crypt.ColorCoder;
import net.rickiekarp.core.util.crypt.HexCoder;
import net.rickiekarp.core.util.crypt.SHA1Coder;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HexTest {

    @Test
    void testHex() {
        String expected = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        String actual = null;
        try {
            actual = HexCoder.bytesToHex(SHA1Coder.getSHA1("test"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    void testHexColorEncryption() {
        String[] expectedArray = {
                "064c21023e5e72af550940a5951d274a1fc8422b", //blue
                "c6b4de3528f2f9d55340974b36a9097e5b4ec0c2", //black
                "7d864075b02f9ad5d9dd55862370f20f8adfa84e", //green
                "ab090146d0627ac1292f1d7022e8204512f89bdd", //orange
                "c525544a7cd8656560f020c1970f13471e94107e", //red
                "32ea51bb27956b2483bc184b9da12313f0430b53", //yellow
                "c2e65748c6bc1e7858dd1052a8c9451c86b6d580", //purple
                "265fd9266ff6515146e8980ac6f1dbbfeabba937"  //cyan
        };
        String actual;
        for (int i = 0; i < ColorCoder.colorArray.length; i++) {
            try {
                actual = HexCoder.bytesToHex(SHA1Coder.getSHA1(ColorCoder.colorArray[i].toString()));
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                return;
            }
            assertEquals(expectedArray[i], actual);
        }
    }
}
