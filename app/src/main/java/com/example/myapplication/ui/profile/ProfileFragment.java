package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.FirebaseLoginActivity;
import com.example.myapplication.Helpers.StorageHelper;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private TextView username,nametv,firstnametv,emailtv,agetv;
    private TextView editablename,editablefirstname,editableemail,editableage;
    private Button buttonAddEditData, buttonUploadRecipe,buttonSearchFam;

    StorageHelper storageHelper = StorageHelper.getInstance();
    private FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        initializeViews(root);
        setValues();
        setOnClickListeners();
        return root;
    }


    public void initializeViews(View root){
        username = root.findViewById(R.id.name_home);

        nametv = root.findViewById(R.id.profile_name);
        editablename = root.findViewById(R.id.profile_yourname);

        firstnametv = root.findViewById(R.id.profile_firstname);
        editablefirstname = root.findViewById(R.id.profile_yourfirstname);

        emailtv = root.findViewById(R.id.profile_email);
        editableemail = root.findViewById(R.id.profile_youremail);

        agetv = root.findViewById(R.id.profile_age);
        editableage = root.findViewById(R.id.profile_yourage);

        buttonAddEditData = root.findViewById(R.id.addEditData_button);
        buttonUploadRecipe = root.findViewById(R.id.uploadRecipe_button);
        buttonSearchFam = root.findViewById(R.id.searchFam_button);
    }

    public void setValues(){
        username.setText(storageHelper.getUserEntity().getFirstname());
        editablename.setText(storageHelper.getUserEntity().getName());
        editablefirstname.setText(storageHelper.getUserEntity().getFirstname());
        editableemail.setText(storageHelper.getUserEntity().getEmail());
        editableage.setText(storageHelper.getUserEntity().getAge());
    }
    public void setOnClickListeners() {
        buttonAddEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                startActivity(intent);
            }
        });
        buttonUploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UploadARecipeActivity.class);
                startActivity(intent);
            }
        });
        buttonSearchFam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}