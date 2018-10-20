package com.rkarp.appcore.util.crypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class SHA1Coder {

    public static byte[] getSHA1(String p) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA1");
        md.update(p.getBytes("utf-8"));
        return md.digest();
    }

    public static byte[] getSHA1Bytes(String data, boolean isHMAC) {
        byte[] sha1 = new byte[0];
        try {
            sha1 = getSHA1(data);
            if (isHMAC) {
                sha1 = HMACCoder.encode(data, sha1);
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    public static String getSHA1String(byte[] digest) {
        StringBuilder sb = new StringBuilder(digest.length*2);
        String s;
        for (byte b : digest) {
            s = Integer.toHexString(0xFF & b);
            sb.append(s);
        }
        return sb.toString();
    }
}
