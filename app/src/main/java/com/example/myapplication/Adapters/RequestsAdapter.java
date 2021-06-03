package com.example.myapplication.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.UserEntity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.requestsDatabase;

public  class RequestsAdapter extends RecyclerView.Adapter<RequestsViewHolder> {
    private Context context;
    private List<UserEntity> usersRequestsList;

    public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_requests_list, parent, false);
        RequestsViewHolder viewHolder = new RequestsViewHolder(contactView);
        return viewHolder;
    }

    public RequestsAdapter(List<UserEntity> usersList) {
        this.usersRequestsList = usersList;
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestsViewHolder holder, final int position) {
        final UserEntity newUsersRequestsList = usersRequestsList.get(position);

        final FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser(); //user-ul curent conectat
        final String currentUserID = newUsersRequestsList.getId(); //user-ul curent din lista

        Glide.with(context).load(newUsersRequestsList.getProfilePicURL()).into(holder.profilePicRequest);

        holder.userNameRequest.setText(newUsersRequestsList.getName());
        holder.userFirstNameRequest.setText(newUsersRequestsList.getFirstname());

        //ACCEPT INVITATION
        //prima faza, daca cel ce a trimis cererea nu este intr-o familie deja
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sterg din tabela de request-uri, request-ul curent
                requestsDatabase.child(currentUserID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Deleted from request table",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"Task unsuccesful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                final String familyKey = familyDatabase.push().getKey();
                final int[] partOfAFam = {0};
                final HashMap hashMap = new HashMap();
                final HashMap adminHashMap = new HashMap();
                hashMap.put("status", "normal member");
                adminHashMap.put("status","admin");

                //query pe familyTable ca sa vedem daca cel care ne adauga e deja intr-o familie
                familyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postsnapshot : snapshot.getChildren()){
                            for(DataSnapshot newPostsnapshot: postsnapshot.getChildren()){
                                String userParser = newPostsnapshot.getKey();
                                if(userParser.equals(currentUserID)){
                                    partOfAFam[0] = 1;
                                    Toast.makeText(context,"Found your user in a fam!",Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if(partOfAFam[0] ==1){
                                familyDatabase.child(postsnapshot.getKey()).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        Toast.makeText(context,"You've been added to the existing family!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            }
                        }
                        //creez un id random in care bag id-urile celor doi useri(cel ce a trimis, si cel conectat care accepta)
                        //pun toast cu numele celui ce a tr
                        if(partOfAFam[0] == 0){
                            familyDatabase.child(familyKey).child(currentUserID).updateChildren(adminHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,"Added first user!",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            familyDatabase.child(familyKey).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,"You are now a new family!",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
                //cautare dupa alte cereri pe care le am eu si user-ul care mi-a tr cerere
                //stergerea lor
                requestsDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postsnapshot:snapshot.getChildren()){
                            String otherUser = postsnapshot.getKey();
                            for(DataSnapshot newpostsnapshot : postsnapshot.getChildren()){
                                String myUser = newpostsnapshot.getKey();
                                if((myUser.equals(mUser.getUid())) || (myUser.equals(currentUserID))){
                                    requestsDatabase.child(otherUser).child(myUser).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context,"Removed the other request!",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                usersRequestsList.remove(position);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return usersRequestsList.size();
    }

}

