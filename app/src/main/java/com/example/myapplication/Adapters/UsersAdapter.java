package com.example.myapplication.Adapters;


import android.content.Context;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.requestsDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {
    private Context context;
    private List<UserEntity> usersList;

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
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position) {
        final UserEntity newUsersList = usersList.get(position);

        final FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser(); //user-ul curent conectat
        final String currentUserID = newUsersList.getId(); //user-ul curent din lista


        holder.nameUserView.setText(newUsersList.getName());
        holder.firstNameUserView.setText(newUsersList.getFirstname());
        holder.addFamButton.setVisibility(View.VISIBLE);


        final int[] iHaveRequest = {0};

        //VERIFICARE CERERI LA DESCHIDEREA SEARCHACTIVITY -> CERERI
        //SA SE APELEZE MEREU la fiecare schimbare in acitivitate
        requestsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String keySender = postSnapshot.getKey();
                    //daca am cereri trimise deja
                    if(mUser.getUid().equals(keySender)){
                        for(DataSnapshot newSnapshot : postSnapshot.getChildren()){
                            String keyReceiver = newSnapshot.getKey();
                            if(currentUserID.equals(keyReceiver)){
                                holder.addFamButton.setVisibility(View.INVISIBLE);
                                holder.cancelFamButton.setVisibility(View.VISIBLE);
                            }
                        }
                        //daca am cereri primite
                    }else if(currentUserID.equals(keySender)){
                        for(DataSnapshot anotherSnapshot : postSnapshot.getChildren()){
                            String receiver = anotherSnapshot.getKey();
                            if(mUser.getUid().equals(receiver)){
                                iHaveRequest[0]=1;
                                holder.addFamButton.setVisibility(View.INVISIBLE);
                                holder.cancelFamButton.setVisibility(View.INVISIBLE);
                                holder.userInOtherGroup.setVisibility(View.INVISIBLE);

                                holder.acceptFamButton.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //VERIFICARE IN FAMILY TABLE
        familyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot : snapshot.getChildren()){
                    for(DataSnapshot postsnapshot2: postsnapshot.getChildren()){
                        String currentUserIDNew = postsnapshot2.getKey();
                        String statusRetrieved = String.valueOf(postsnapshot2.child("status").getValue());
                        if((currentUserIDNew.equals(mUser.getUid())) && (statusRetrieved.equals("normal member"))){
                            holder.addFamButton.setVisibility(View.INVISIBLE);
                            holder.acceptFamButton.setVisibility(View.INVISIBLE);
                        }
                        if(currentUserIDNew.equals(currentUserID)){
                            for(DataSnapshot postsnapshot3: postsnapshot.getChildren()){
                                String mUserID = postsnapshot3.getKey();
                                //daca si user-ul conectat este in grup
                                if(mUser.getUid().equals(mUserID)){
                                    holder.addFamButton.setVisibility(View.INVISIBLE);
                                    holder.cancelFamButton.setVisibility(View.INVISIBLE);
                                    holder.userInOtherGroup.setVisibility(View.INVISIBLE);
                                    holder.alreadFam.setVisibility(View.VISIBLE);
                                    break;

                                }else{
                                    //daca doar user-ul curent e intr-o familie
                                    holder.addFamButton.setVisibility(View.INVISIBLE);
                                    holder.cancelFamButton.setVisibility(View.INVISIBLE);
                                    holder.userInOtherGroup.setVisibility(View.VISIBLE);
                                    //dc am cerere de la acel user care e intr-o familie, nu arat user in other group
                                    if(iHaveRequest[0]==1){
                                        holder.userInOtherGroup.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //CERERE TRIMISA DE MINE
        holder.addFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificam daca nu am tr deja cerere
                //memorez referinte catre request-uri
                HashMap hashMap = new HashMap();
                hashMap.put("status", "pending");
                requestsDatabase.child(mUser.getUid()).child(currentUserID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Request sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //CERERE ANULATA DE MINE
        holder.cancelFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vizibilitate butoane
                holder.cancelFamButton.setVisibility(View.INVISIBLE);
                holder.addFamButton.setVisibility(View.VISIBLE);
                //stergere in baza de date
                requestsDatabase.child(mUser.getUid()).child(currentUserID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                            Toast.makeText(context, "Request canceled!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,task.getException().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //CERERE REFUZATA
        /*holder.declineFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vizibilitate butoane
                holder.acceptFamButton.setVisibility(View.INVISIBLE);
                holder.declineFamButton.setVisibility(View.INVISIBLE);
                holder.cancelFamButton.setVisibility(View.INVISIBLE);
                holder.addFamButton.setVisibility(View.VISIBLE);


                //stergere din baza de date
                requestsDatabase.child(currentUserID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Request declined!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,task.getException().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //parcurg din nou family table sa vad daca user-ul de la care am avut request
                //e intr-o fam deja, daca e, nu ii mai pot da napoi cerere
                familyDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postsnapshot : snapshot.getChildren()){
                            for(DataSnapshot postsnapshot2: postsnapshot.getChildren()){
                                String currentUserIDNew = postsnapshot2.getKey();
                                if(currentUserIDNew.equals(currentUserID)) {
                                    holder.addFamButton.setVisibility(View.INVISIBLE);
                                    holder.userInOtherGroup.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

            }
        });*/

        //CERERE ACCPETATA
        //prima faza, daca cel ce a trimis cererea nu este intr-o familie deja
        holder.acceptFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    holder.acceptFamButton.setVisibility(View.INVISIBLE);

                    //sterg din tabela de request-uri carerea de la user-ul curent la mine
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
                                //DACA USER-UL DE LA CARE AM CERERE, E INTR-O FAMILIE
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

                            //DACA USER_UL DE LA CARE AM CERERE NU E INTR_O FAM
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
                        public void onCancelled(@NonNull DatabaseError error) {}
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
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}

