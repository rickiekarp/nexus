package crypt;

import com.rkarp.appcore.util.crypt.Base64Coder;
import com.rkarp.appcore.util.crypt.ColorCoder;
import com.rkarp.appcore.util.crypt.SHA1Coder;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class Base64Test {

    @Test
    public void testBase64() {
        String expected = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";
        String actual = null;
        try {
            actual = String.valueOf(Base64Coder.encode(SHA1Coder.getSHA1("test")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testBase64ColorEncryption() {
        String[] expectedArray = {
                "BkwhAj5ecq9VCUCllR0nSh/IQis=", //blue
                "xrTeNSjy+dVTQJdLNqkJfltOwMI=", //black
                "fYZAdbAvmtXZ3VWGI3DyD4rfqE4=", //green
                "qwkBRtBiesEpLx1wIuggRRL4m90=", //orange
                "xSVUSnzYZWVg8CDBlw8TRx6UEH4=", //red
                "MupRuyeVaySDvBhLnaEjE/BDC1M=", //yellow
                "wuZXSMa8HnhY3RBSqMlFHIa21YA=", //purple
                "Jl/ZJm/2UVFG6JgKxvHbv+q7qTc="  //cyan
        };
        String actual = null;
        for (int i = 0; i < ColorCoder.colorArray.length; i++) {
            System.out.println(ColorCoder.colorArray[i].toString());
            try {
                actual = String.valueOf(Base64Coder.encode(SHA1Coder.getSHA1(ColorCoder.colorArray[i].toString())));
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            assertEquals(expectedArray[i], actual);
        }
    }
}
