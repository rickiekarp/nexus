package com.projekt.assistantapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projekt.assistantapp.config.Configuration;
import com.projekt.assistantapp.db.AppDatabase;
import com.projekt.assistantapp.interfaces.IOnCreateAccountResult;
import com.projekt.assistantapp.interfaces.IOnGetTokenResult;
import com.projekt.assistantapp.interfaces.IOnLoginResult;
import com.projekt.assistantapp.preferences.IsLoggedIn;
import com.projekt.assistantapp.preferences.Token;
import com.projekt.assistantapp.tasks.LoginTask;
import com.projekt.assistantapp.tasks.RegistrationTask;
import com.projekt.assistantapp.tasks.TokenTask;
import com.projekt.assistantapp.utils.Util;

/**
 * Created by sebastian on 24.11.17.
 */

public class LoginActivity extends AppCompatActivity implements IOnCreateAccountResult, IOnLoginResult, IOnGetTokenResult {

    private String username = "", password = "";
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        database = AppDatabase.getDatabase(this);
        sp = this.getSharedPreferences(Constants.Preferences.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);


        EditText editUsername = findViewById(R.id.edit_name);
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
            }
        });

        EditText editPassword = findViewById(R.id.edit_password);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });

        Button buttonLogin = findViewById(R.id.button_login);
        Button buttonRegister = findViewById(R.id.button_register);


        buttonRegister.setOnClickListener(v -> {
            if (username.isEmpty() || password.isEmpty()) {
                showInvalidCredentialsToast();
            } else {
                progressDialog = ProgressDialog.show(this, "", "Registrierung wird ausgeführt", true, false);
                sp.edit().putString(Constants.Preferences.PREF_USERNAME, username).apply();
                RegistrationTask registrationTask = new RegistrationTask(sp, username, password, this);
                registrationTask.execute();
            }
        });

        buttonLogin.setOnClickListener(v -> {
            if (username.isEmpty() || password.isEmpty()) {
                showInvalidCredentialsToast();
            } else {
                progressDialog = ProgressDialog.show(this, "", "Login wird ausgeführt", true, false);
                TokenTask getTokenTask = new TokenTask(sp, this, username, password);
                getTokenTask.execute();

                sp.edit().putString(Constants.Preferences.PREF_USERNAME, username).apply();
            }
        });

        EditText serverField = findViewById(R.id.server);
        serverField.setText(Configuration.host);
        serverField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Configuration.host = s.toString();
            }
        });
    }

    private void showInvalidCredentialsToast() {
        Toast.makeText(this, "Please enter a valid username and password", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onCreationSuccess() {
        LoginTask loginTask = new LoginTask(sp, this, database);
        loginTask.execute();
    }

    @Override
    public void onCreationError() {
        progressDialog.dismiss();
        AlertDialog errorDialog = new AlertDialog.Builder(this).create();
        errorDialog.setTitle("Error");
        errorDialog.setMessage("Account already exists");
        errorDialog.show();
    }

    @Override
    public void onLoginSuccess() {
        setLoggedIn(true);
        Util.startMainActivity(this);
    }

    @Override
    public void onLoginError() {
        progressDialog.dismiss();
        AlertDialog errorDialog = new AlertDialog.Builder(this).create();
        errorDialog.setTitle("Error");
        errorDialog.setMessage("Please check your internet connection");
        errorDialog.show();

        setLoggedIn(false);
    }

    private void setLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IsLoggedIn.KEY, isLoggedIn);
        editor.apply();
    }

    private void removeToken() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Token.KEY);
        editor.apply();
    }


    @Override
    public void onGetTokenSuccess() {
        LoginTask loginTask = new LoginTask(sp, this, database);
        loginTask.execute();
    }

    @Override
    public void onGetTokenError() {
        progressDialog.dismiss();
        AlertDialog errorDialog = new AlertDialog.Builder(this).create();
        errorDialog.setTitle("Error");
        errorDialog.setMessage("Please check your internet connection");
        errorDialog.show();
        removeToken();
    }
}
