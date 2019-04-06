package com.projekt.assistantapp.fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.projekt.assistantapp.R;
import com.projekt.assistantapp.utils.crypt.Base64Coder;
import com.projekt.assistantapp.utils.crypt.EncryptFunc;

import java.security.SignatureException;

/**
 * Created by rickie on 12/5/17.
 */
public class EncryptFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    AppCompatEditText sentence_tf;
    AppCompatEditText peek_tf;
    AppCompatButton hexBtn;
    AppCompatButton b64Btn;
    SwitchCompat viewMode;
    SwitchCompat secureMode;
    SwitchCompat hmacMode;
    SwitchCompat complexMode;

    boolean isSecure = false;
    boolean hmac = false;
    boolean complex = false;

    //string which is added in complex mode
    public static String comp_string = ".H0k";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_encrypt, container, false);

        getActivity().setTitle(R.string.action_encrypt);

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        sentence_tf = view.findViewById(R.id.editText1);
        sentence_tf.setTransformationMethod(new PasswordTransformationMethod());
        peek_tf = view.findViewById(R.id.editText2);
        hexBtn = view.findViewById(R.id.hexBtn);
        b64Btn = view.findViewById(R.id.b64Btn);
        viewMode = view.findViewById(R.id.viewMode);
        secureMode = view.findViewById(R.id.secureMode);
        hmacMode = view.findViewById(R.id.hmacMode);
        complexMode = view.findViewById(R.id.complexMode);

        hexBtn.setOnClickListener(v -> {
            try {
                calcHex();
            } catch (SignatureException e) {
                e.printStackTrace();
            }
        });

        b64Btn.setOnClickListener(v -> {
            try {
                calcBase64();
            } catch (SignatureException e) {
                e.printStackTrace();
            }
        });

        viewMode.setOnClickListener(v -> {
            if (viewMode.isChecked()) {
                sentence_tf.setTransformationMethod(new SingleLineTransformationMethod());
            } else {
                sentence_tf.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        secureMode.setOnClickListener(v -> {
            if (secureMode.isChecked()) {
                viewMode.setEnabled(false); viewMode.setChecked(false);
                isSecure = secureMode.isChecked();
                sentence_tf.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                viewMode.setEnabled(true);
                isSecure = secureMode.isChecked();
                sentence_tf.setText(""); peek_tf.setText("Peek");
                send2Clipboard("", "Clipboard cleared");
                sentence_tf.setTransformationMethod(new SingleLineTransformationMethod());
            }
        });

        hmacMode.setOnClickListener(v -> hmac = hmacMode.isChecked());

        complexMode.setOnClickListener(v -> complex = complexMode.isChecked());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    private void peekText(String s) {
        if (!isSecure) {
            peek_tf.setText(s.substring(0, 6));
        } else {
            peek_tf.setText("Peek");
        }
    }

    protected void calcHex() throws SignatureException {
        String data = sentence_tf.getText().toString();
        byte[] output = EncryptFunc.getSha1(data);
        String hex = EncryptFunc.bytesToHex(output);

        send2Clipboard(hex, "Copied to Clipboard");
        peekText(hex);

        if (hmac)
        {
            //calculate HMAC string
            String s = EncryptFunc.calcHMAC(data, hex);

            //append complex string
            if (complex) { s = s + comp_string; }

            //copy encoded string to clipboard and set the peek TextField text
            send2Clipboard(s, "Copied to Clipboard");
            peekText(s);
        }

        else if (complex)
        {
            //copy encoded string to clipboard and set the peek TextField text
            send2Clipboard(hex + comp_string, "Copied to Clipboard");
            peekText(hex + comp_string);
        }
        else
        {
            //copy encoded string to clipboard and set the peek TextField text
            send2Clipboard(hex, "Copied to Clipboard");
            peekText(hex);
        }
    }

    protected void calcBase64() throws SignatureException {
        String data = sentence_tf.getText().toString();
        String b64 = String.valueOf(Base64Coder.encode(EncryptFunc.getSha1(data)));

        if (hmac) {
            //calculate HMAC string
            String s = EncryptFunc.calcHMAC(data,b64);

            //calculate Base64 string
            s = String.valueOf(Base64Coder.encode(EncryptFunc.getSha1(s)));

            //append complex string
            if (complex) { s = s + comp_string; }

            //copy encoded string to clipboard and set the peek TextField text
            send2Clipboard(s, "Copied to Clipboard");
            peekText(s);
        } else if (complex) {
            //copy encoded string to clipboard and set the peek TextField text
            send2Clipboard(b64 + comp_string, "Copied to Clipboard");
            peekText(b64 + comp_string);
        } else {
            //copy encoded string to clipboard and set the peek TextField text
            send2Clipboard(b64, "Copied to Clipboard");
            peekText(b64);
        }
    }

    protected void send2Clipboard(String s, String msg) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied text", s);
        clipboard.setPrimaryClip(clip);
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }
}