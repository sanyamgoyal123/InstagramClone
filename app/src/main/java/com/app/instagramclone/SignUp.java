package com.app.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpEmail, edtSignUpUserName, edtSignUpPassword;
    private Button btnSignUp1, btnSignIn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUserName = findViewById(R.id.edtSignUpUserName);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp1);
                }
                return false;
            }
        });
        btnSignUp1 = findViewById(R.id.btnSignUp1);
        btnSignIn1 = findViewById(R.id.btnSignIn1);

        btnSignUp1.setOnClickListener(this);
        btnSignIn1.setOnClickListener(this);
        if(ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMedia();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp1:
                if(edtSignUpEmail.getText().toString().equals("") ||
                        edtSignUpUserName.getText().toString().equals("") ||
                        edtSignUpPassword.getText().toString().equals("")) {
                    Toasty.info(SignUp.this, "Email UserName and Password is required!", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUserName.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Signing up " + edtSignUpUserName.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toasty.success(SignUp.this, appUser.get("username") + " is signed up successfully!",
                                        Toast.LENGTH_SHORT, true).show();
                                transitionToSocialMedia();
                            } else {
                                Toasty.error(SignUp.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.btnSignIn1:
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
                break;
        }
    }

    public void layoutSignUpTapped(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transitionToSocialMedia() {
        Intent intent = new Intent(SignUp.this, SocialMedia.class);
        startActivity(intent);
    }
}