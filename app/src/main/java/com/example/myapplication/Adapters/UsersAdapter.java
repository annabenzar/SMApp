package com.example.myapplication.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.ListIngredientModel;
import com.example.myapplication.Models.ListUserModel;
import com.example.myapplication.Models.UserEntity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.requestsDatabase;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {
    private Context context;
    private List<UserEntity> usersList;
    private String currentState = "no_request_sent";
    FirebaseUser mUser;
    FirebaseAuth mAuth;


    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_users_list, parent, false);
        UsersViewHolder viewHolder = new UsersViewHolder(contactView);
        return viewHolder;
    }

    public UsersAdapter(List<UserEntity> usersList) {
        this.usersList = usersList;
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, int position) {
        final UserEntity newUsersList = usersList.get(position);

        holder.nameUserView.setText(newUsersList.getName());
        holder.firstNameUserView.setText(newUsersList.getFirstname());

        //preluare id user curent
        final String currentUserID = newUsersList.getId();

        holder.addFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adaugare membru

                mUser = mAuth.getCurrentUser();
                //verificam daca nu am tr deja cerere
                if(currentState.equals("no_request_sent")){
                    //memorez referinte catre request-uri
                    HashMap hashMap = new HashMap();
                    hashMap.put("status","pending");
                    requestsDatabase.child(mUser.getUid()).child(currentUserID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context,"Your request was sent!",Toast.LENGTH_SHORT).show();
                                currentState="request_sent_pending";
                                holder.addFamButton.setText("Cancel Request");
                            }else{
                                Toast.makeText(context,task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                //cazul in care s-au trimis request, cancel
                if(currentState.equals("request_sent_pending")||currentState.equals("request_sent_declined")){
                    //putem sterge cererea
                    requestsDatabase.child(mUser.getUid()).child(currentUserID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(context, "You have canceled request!", Toast.LENGTH_SHORT).show();
                                currentState="no_request_sent";
                                holder.addFamButton.setText("ADD");
                            }else{
                                Toast.makeText(context,task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    public void addFamMember(String userId){

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

}

