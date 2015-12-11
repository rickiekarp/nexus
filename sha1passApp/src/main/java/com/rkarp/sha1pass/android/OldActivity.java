//package com.rkarp.sha1pass.android;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SignatureException;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.text.ClipboardManager;
//import android.text.method.PasswordTransformationMethod;
//import android.text.method.SingleLineTransformationMethod;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.*;
//import com.rkarp.sha1pass.core.Base64Coder;
//import com.rkarp.sha1pass.core.Constants;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//
//public class OldActivity extends Activity {
//    EditText sentence_tf;
//    EditText peek_tf;
//    Button hexBtn;
//    Button hexHalfBtn;
//    Button b64Btn;
//    Button b64HalfBtn;
//    Switch viewMode;
//    Switch secureMode;
//    Switch hmacMode;
//    Switch complexMode;
//    boolean isSecure = false;
//    boolean half = false;
//    boolean hmac = false;
//    boolean complex = false;
//    AlertDialog alert;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        sentence_tf = (EditText) findViewById(R.id.editText1);
//        sentence_tf.setTransformationMethod(new PasswordTransformationMethod());
//
//        peek_tf = (EditText) findViewById(R.id.editText2);
//        hexBtn = (Button) findViewById(R.id.hexBtn);
//        hexHalfBtn = (Button) findViewById(R.id.hexHalfBtn);
//        b64Btn = (Button) findViewById(R.id.b64Btn);
//        b64HalfBtn = (Button) findViewById(R.id.b64HalfBtn);
//        viewMode = (Switch) findViewById(R.id.viewMode);
//        secureMode = (Switch) findViewById(R.id.secureMode);
//        hmacMode = (Switch) findViewById(R.id.hmacMode);
//        complexMode = (Switch) findViewById(R.id.complexMode);
//
//        hexBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                half = false;
//                try {
//                    calcHex();
//                } catch (SignatureException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        hexHalfBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                half = true;
//                try {
//                    calcHex();
//                } catch (SignatureException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        b64Btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                half = false;
//                try {
//                    calcBase64();
//                } catch (SignatureException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        b64HalfBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                half = true;
//                try {
//                    calcBase64();
//                } catch (SignatureException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        viewMode.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (viewMode.isChecked()) {
//                    sentence_tf.setTransformationMethod(new SingleLineTransformationMethod ());
//                } else {
//                    sentence_tf.setTransformationMethod(new PasswordTransformationMethod());
//                }
//            }
//        });
//
//        secureMode.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (secureMode.isChecked()) {
//                    viewMode.setEnabled(false); viewMode.setChecked(false);
//                    isSecure = true;
//                    sentence_tf.setTransformationMethod(new PasswordTransformationMethod());
//                } else {
//                    viewMode.setEnabled(true);
//                    isSecure = false;
//                    sentence_tf.setText(""); peek_tf.setText("Peek");
//                    send2Clipboard("", "Clipboard cleared");
//                    sentence_tf.setTransformationMethod(new SingleLineTransformationMethod());
//                }
//            }
//        });
//
//        hmacMode.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (hmacMode.isChecked()) {
//                    hmac = true;
//                } else {
//                    hmac = false;
//                }
//            }
//        });
//
//        complexMode.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (complexMode.isChecked()) {
//                    complex = true;
//                } else {
//                    complex = false;
//                }
//            }
//        });
//
//        //get versionName from AndroidManifest
//        Context context = getApplicationContext(); // or activity.getApplicationContext()
//        PackageManager packageManager = context.getPackageManager();
//        String packageName = context.getPackageName();
//
//        String myVersionName = "not available"; // initialize String
//
//        try {
//            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        //create about dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.about) + " " + Constants.app_title);
//        builder.setMessage("Version: " + myVersionName + "\n\n" + getText(R.string.description));
//        alert = builder.create();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.open_options_menu_id:
//                alert.show();
//                break;
//
//            default:
//                throw new IllegalArgumentException("Unexpected action value "+item.getItemId());
//        }
//
//        return true;
//    }
//
//    protected void calcHex() throws SignatureException {
//
//        String data = sentence_tf.getText().toString();
//        byte[] output = getSha1(data);
//        String hex = bytesToHex(output);
//
//        if (half) { hex = hex.substring(0, hex.length() / 2); }
//        send2Clipboard(hex, "Copied to Clipboard");
//        peekText(hex);
//
//        if (hmac)
//        {
//            //calculate HMAC string
//            String s = calcHMAC(data, hex);
//
//            //get the half of the encoded string
//            if (half) { s = s.substring(0, hex.length()/2); }
//
//            //append complex string
//            if (complex) { s = s + Constants.comp_string; }
//
//            //copy encoded string to clipboard and set the peek TextField text
//            send2Clipboard(s, "Copied to Clipboard");
//            peekText(s);
//        }
//
//        else if (complex)
//        {
//            //get the half of the encoded string
//            if (half) { hex = hex.substring(0, hex.length()/2); }
//
//            //copy encoded string to clipboard and set the peek TextField text
//            send2Clipboard(hex + Constants.comp_string, "Copied to Clipboard");
//            peekText(hex + Constants.comp_string);
//        }
//        else
//        {
//            //get the half of the encoded string
//            if (half) { hex = hex.substring(0, hex.length()/2); }
//
//            //copy encoded string to clipboard and set the peek TextField text
//            send2Clipboard(hex, "Copied to Clipboard");
//            peekText(hex);
//        }
//    }
//
//    protected void calcBase64() throws SignatureException {
//        String data = sentence_tf.getText().toString();
//        String b64 = String.valueOf(Base64Coder.encode(getSha1(data)));
//        if (half) { b64 = b64.substring(0, b64.length() / 2); }
//
//        if (hmac)
//        {
//            //calculate HMAC string
//            String s = calcHMAC(data,b64);
//
//            //calculate Base64 string
//            s = String.valueOf(Base64Coder.encode(getSha1(s)));
//
//            //get the half of the encoded string
//            if (half) { s = s.substring(0, s.length() / 2); }
//
//            //append complex string
//            if (complex) { s = s + Constants.comp_string; }
//
//            //copy encoded string to clipboard and set the peek TextField text
//            send2Clipboard(s, "Copied to Clipboard");
//            peekText(s);
//        }
//        else if (complex)
//        {
//            //get the half of the encoded string
//            if (half) { b64 = b64.substring(0, b64.length() / 2); }
//
//            //copy encoded string to clipboard and set the peek TextField text
//            send2Clipboard(b64 + Constants.comp_string, "Copied to Clipboard");
//            peekText(b64 + Constants.comp_string);
//        }
//        else
//        {
//            //get the half of the encoded string
//            if (half) { b64 = b64.substring(0, b64.length() / 2); }
//
//            //copy encoded string to clipboard and set the peek TextField text
//            send2Clipboard(b64, "Copied to Clipboard");
//            peekText(b64);
//        }
//    }
//
//    private void peekText(String s) {
//        if (!isSecure) {
//            peek_tf.setText(s.substring(0, 6));
//        } else {
//            peek_tf.setText("Peek");
//        }
//    }
//
//    public String calcHMAC(String data, String key) throws SignatureException {
//
//        final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
//
//        try {
//            // get an hmac_sha1 key from the raw key bytes
//            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
//
//            // get an hmac_sha1 Mac instance and initialize with the signing key
//            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
//            mac.init(signingKey);
//
//            byte[] digest = mac.doFinal(data.getBytes());
//
//            StringBuilder sb = new StringBuilder(digest.length*2);
//            String s;
//            for (byte b : digest){
//                s = Integer.toHexString(0xFF & b);
//                sb.append(s);
//            }
//            s = sb.toString();
//
//            return s;
//
//        } catch (Exception e) {
//            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
//        }
//    }
//
//    private byte[] getSha1(String p) {
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA1");
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("SHA1Pass", "npe", e);
//        }
//        try {
//            md.update(p.getBytes("utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            Log.e("SHA1Pass", "error utf-8", e);
//        }
//        return md.digest();
//    }
//
//    protected void send2Clipboard(String s, String msg) {
//        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        clipboard.setText(s);
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    public static String bytesToHex(byte[] data) {
//        StringBuffer buf = new StringBuffer();
//        for (byte aData : data) {
//            int halfbyte = (aData >>> 4) & 0x0F;
//            int two_halfs = 0;
//            do {
//                if ((0 <= halfbyte) && (halfbyte <= 9))
//                    buf.append((char) ('0' + halfbyte));
//                else
//                    buf.append((char) ('a' + (halfbyte - 10)));
//                halfbyte = aData & 0x0F;
//            } while (two_halfs++ < 1);
//        }
//        return buf.toString();
//    }
//}