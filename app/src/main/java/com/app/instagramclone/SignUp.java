package com.app.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName,edtPower,edtSpeed;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtName = findViewById(R.id.edtName);
        edtPower = findViewById(R.id.edtPower);
        edtSpeed = findViewById(R.id.edtSpeed);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(SignUp.this);
    }

    @Override
    public void onClick(View view) {

        try {
            ParseObject parseObject = new ParseObject("Boxing");
            parseObject.put("Name", edtName.getText().toString());
            parseObject.put("Speed", Integer.parseInt(edtSpeed.getText().toString()));
            parseObject.put("Power", Integer.parseInt(edtPower.getText().toString()));
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toasty.success(SignUp.this, edtName.getText() + " is uploaded to the server", Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            });
        }
        catch (Exception e) {
            Toasty.error(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }
}