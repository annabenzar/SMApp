package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.ListIngredientModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

public class ToBuyAdapter extends RecyclerView.Adapter<ToBuyViewHolder> {
    private Context context;
    private List<ListIngredientModel> ingredientsList;



    public ToBuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_tobuy_list, parent, false);
        ToBuyViewHolder viewHolder = new ToBuyViewHolder(contactView);
        return viewHolder;
    }

    public ToBuyAdapter(List<ListIngredientModel> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public void onBindViewHolder(@NonNull ToBuyViewHolder holder, int position) {
        final ListIngredientModel newIngredientsList = ingredientsList.get(position);

        holder.ingredient.setText(newIngredientsList.getIngredient());
        holder.nameRecipe.setText(newIngredientsList.getNameRecipe());

        holder.deleteIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ce se intampla la click pe un cardview

                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference oneUserIngredients = ingredientsDatabase.child(currentUser);

                oneUserIngredients.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                            //cautare cheie cu ingredientul cautat
                            String ingredientRetrieved = String.valueOf(postSnapshot.child("ingredient").getValue());

                            if(ingredientRetrieved.equals(newIngredientsList.getIngredient())){
                                postSnapshot.child("ingredient").getRef().removeValue();
                                postSnapshot.child("nameRecipe").getRef().removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

}
