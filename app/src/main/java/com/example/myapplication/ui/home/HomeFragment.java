package com.example.myapplication.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.UserDatabase;
import com.example.myapplication.UserEntity;

public class HomeFragment extends Fragment {

    private TextView nametv, firstnametv, emailtv, agetv;
    private TextView editablename, editablefirstname, editableemail, editableage;
    private Button button;

    private UserDatabase userDatabase;
    StorageHelper storageHelper = StorageHelper.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        TextView username = root.findViewById(R.id.name_home);

        nametv = root.findViewById(R.id.profile_name);
        editablename = root.findViewById(R.id.profile_yourname);

        firstnametv = root.findViewById(R.id.profile_firstname);
        editablefirstname = root.findViewById(R.id.profile_yourfirstname);

        emailtv = root.findViewById(R.id.profile_email);
        editableemail = root.findViewById(R.id.profile_youremail);

        agetv = root.findViewById(R.id.profile_age);
        editableage = root.findViewById(R.id.profile_yourage);

        button = root.findViewById(R.id.profile_button);
        userDatabase = UserDatabase.getInstance(getActivity());


        username.setText(storageHelper.getProfileEntity().getFirstname());
        setValues();
        setOnClicklisteners();

        return root;
    }

    public void setValues() {
            editablename.setText(storageHelper.getProfileEntity().getName());
            editablefirstname.setText(storageHelper.getProfileEntity().getFirstname());
            editableemail.setText(storageHelper.getProfileEntity().getEmail());
            String s = String.valueOf(storageHelper.getProfileEntity().getAge());
            editableage.setText(s);
    }

    public void setOnClicklisteners() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                startActivity(intent);
            }
        });
    }
}