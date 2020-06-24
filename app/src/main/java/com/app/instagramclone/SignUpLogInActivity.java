package com.app.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import es.dmoral.toasty.Toasty;

public class SignUpLogInActivity extends AppCompatActivity {

    private EditText edtUserNameSignUp, edtPasswordSignUp, edtUserNameLogIn, edtPasswordLogIn;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtUserNameLogIn = findViewById(R.id.edtUserNameLogIn);
        edtPasswordLogIn = findViewById(R.id.edtPasswordLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toasty.success(SignUpLogInActivity.this,
                                    appUser.get("username") + " account is created.",
                                    Toast.LENGTH_SHORT, true).show();
                        }
                        else {
                            Toasty.error(SignUpLogInActivity.this, e.getMessage(),
                                    Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(edtUserNameLogIn.getText().toString(),
                        edtPasswordLogIn.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user != null && e == null) {
                                    Toasty.success(SignUpLogInActivity.this,
                                            user.getUsername() + " successfully logged in.",
                                            Toast.LENGTH_SHORT, true).show();
                                }
                                else {
                                    Toasty.error(SignUpLogInActivity.this, e.getMessage(),
                                            Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        });
            }
        });
    }
}
