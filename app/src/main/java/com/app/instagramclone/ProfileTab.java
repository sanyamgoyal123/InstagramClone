package com.app.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import es.dmoral.toasty.Toasty;

public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtBio, edtProfession, edtHobbies, edtFavSport;
    private Button btnUpdate;

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtProfession = view.findViewById(R.id.edtProfession);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        edtFavSport = view.findViewById(R.id.edtFavSport);

        btnUpdate = view.findViewById(R.id.btnUpdateInfo);
        final ParseUser currUser = ParseUser.getCurrentUser();

        if(currUser.get("profileName") == null)
            edtProfileName.setText("");
        else {
            edtProfileName.setText(currUser.get("profileName") + "");
        }
        if(currUser.get("profileBio") == null)
            edtBio.setText("");
        else {
            edtBio.setText(currUser.get("profileBio") + "");
        }
        if(currUser.get("profession") == null)
            edtProfession.setText("");
        else {
            edtProfession.setText(currUser.get("profession") + "");
        }
        if(currUser.get("hobbies") == null)
            edtHobbies.setText("");
        else {
            edtHobbies.setText(currUser.get("hobbies") + "");
        }
        if(currUser.get("favSport") == null)
            edtFavSport.setText("");
        else {
            edtFavSport.setText(currUser.get("favSport") + "");
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtProfileName.getText().toString().equals("")) {
                    Toasty.info(getContext(),"Profile Name is required!", Toasty.LENGTH_SHORT, true).show();
                }
                else {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Uploading Data");
                    progressDialog.show();
                    currUser.put("profileName", edtProfileName.getText().toString());
                    currUser.put("profileBio", edtBio.getText().toString());
                    currUser.put("profession", edtProfession.getText().toString());
                    currUser.put("hobbies", edtHobbies.getText().toString());
                    currUser.put("favSport", edtFavSport.getText().toString());

                    currUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toasty.success(getContext(), "Data Uploaded to the Server!", Toast.LENGTH_SHORT, true).show();
                            } else {
                                Toasty.error(getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
        return view;
    }
}