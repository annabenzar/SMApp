package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.FamilyGroupActivity;
import com.example.myapplication.Models.ListFamilyGroupUserModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;

public class FamilyGroupAdapter extends RecyclerView.Adapter<FamilyGroupViewHolder> {

    private Context context;
    private List<ListFamilyGroupUserModel> familyGroupList;

    @NonNull
    @Override
    public FamilyGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_familygroup_list,parent,false);
        FamilyGroupViewHolder viewHolder = new FamilyGroupViewHolder(contactView);
        return viewHolder;
    }
    public FamilyGroupAdapter(List<ListFamilyGroupUserModel> familyGroupList){
        this.familyGroupList=familyGroupList;
    }

    @Override
    public void onBindViewHolder(@NonNull final FamilyGroupViewHolder holder, final int position) {
        final ListFamilyGroupUserModel listFamilyGroupUserModel = familyGroupList.get(position);

        final FirebaseUser mUSer = FirebaseAuth.getInstance().getCurrentUser();
        final  String currentUserID = listFamilyGroupUserModel.getIdFamilyGroupUser(); //user-ul curent din lista

        Glide.with(context).load(listFamilyGroupUserModel.getProfilePicURL()).into(holder.profilePicFam);

        holder.nameFamilyGroupUser.setText(listFamilyGroupUserModel.getNameFamilyGroupUser());
        holder.firstNameFamilyGroupUser.setText(listFamilyGroupUserModel.getFirstnameFamilyGroupUSer());
        holder.statusFamilyGroupUser.setText(listFamilyGroupUserModel.getStatusFamilyGroupUser());

        //afisare admin in dreptul adminului
        if(holder.statusFamilyGroupUser.getText().equals("admin")){
            holder.statusFamilyGroupUser.setVisibility(View.VISIBLE);
        }

        //parcurgem baza de date a fam sa vedem daca user-ul conectat e admin
        //punem buton de delete la toti userii
        familyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot:snapshot.getChildren()){
                    for(DataSnapshot newpoastsnapshot:postsnapshot.getChildren()){
                        String familyUserId = newpoastsnapshot.getKey();
                        if(familyUserId.equals(mUSer.getUid())){
                            final String status = String.valueOf(newpoastsnapshot.child("status").getValue());
                            if(status.equals("admin")){
                                holder.deleteFamilyGroupUser.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //daca nu e admin sa se poata sterge doar pe el
        if(currentUserID.equals(mUSer.getUid())){
            holder.deleteFamilyGroupUser.setVisibility(View.VISIBLE);
        }

        //functionalitate de delete din grup
        holder.deleteFamilyGroupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cautare user curent in baza de date a familiei
                familyDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postsnapshot:snapshot.getChildren()){
                            for(DataSnapshot newpostsnapshot:postsnapshot.getChildren()){
                                String userId= newpostsnapshot.getKey();
                                if(userId.equals(currentUserID)){
                                    familyDatabase.child(postsnapshot.getKey()).child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context,"Deleted from family table!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                familyGroupList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return familyGroupList.size();
    }
}
