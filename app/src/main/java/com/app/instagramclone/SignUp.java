package com.app.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName,edtPower,edtSpeed;
    private Button btnSubmit, btnAllData;
    private TextView txtGetData;
    private String allBoxers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtName = findViewById(R.id.edtName);
        edtPower = findViewById(R.id.edtPower);
        edtSpeed = findViewById(R.id.edtSpeed);
        txtGetData = findViewById(R.id.txtGetData);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnAllData = findViewById(R.id.btnAllData);
        btnSubmit.setOnClickListener(SignUp.this);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Boxing");
                parseQuery.getInBackground("BZ7nK2vfpF", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText("Name: " + object.get("Name")
                                    + " - " + "Power: " + object.get("Power")
                                    + " - " + "Speed: " + object.get("Speed"));
                        }
                    }
                });
            }
        });

        btnAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Boxing");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null) {
                            if(objects.size() > 0) {
                                for(ParseObject parseObject : objects) {
                                    allBoxers = allBoxers + parseObject.get("Name") + "\n";
                                }
                                Toasty.success(SignUp.this, allBoxers, Toast.LENGTH_SHORT, true).show();
                            }
                            else {
                                Toasty.error(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                });
            }
        });
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