package com.rkarp.appcore.util.crypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;

class HMACCoder {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    static byte[] encode(String data, byte[] key) throws SignatureException {
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            return mac.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
    }

    static String encode(String data, String key) throws SignatureException {
        try {
            byte[] digest = encode(data, key.getBytes());
            StringBuilder sb = new StringBuilder(digest.length*2);
            String s;
            for (byte b : digest){
                s = Integer.toHexString(0xFF & b);
                sb.append(s);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
    }

}
