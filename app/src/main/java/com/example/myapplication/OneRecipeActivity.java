package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Models.ListIngredientModel;
import com.example.myapplication.Models.ListRecipeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.favoritesDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

public class OneRecipeActivity extends AppCompatActivity {

    public static Context context;
    private VideoView imageOneRecipe;
    private TextView nameOneRecipe, timeOneRecipe,ingredientsOneRecipe,prepOneRecipe,authorOneRecipe,byOneRecipe;
    private TextView ingrVisible,prepVisible;
    private Button buttonWish;
    MediaController mediaController;
    private String imgUrl,name,time,type,ingredients,prep,author;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onerecipe);
        mediaController  = new MediaController(this);
        Bundle bundle = getIntent().getExtras();
        initializeViews();
        setValues(bundle);
        setOnClickListeners();
    }

    @SuppressLint("WrongViewCast")
    public void initializeViews(){
        imageOneRecipe=findViewById(R.id.img_OneRecipe);

        nameOneRecipe=findViewById(R.id.name_OneRecipe);
        timeOneRecipe=findViewById(R.id.time_OneRecipe);
        buttonWish = findViewById(R.id.button_wish);

        ingrVisible=findViewById(R.id.ingredients_visible_OneRecipe);
        ingredientsOneRecipe=findViewById(R.id.ingredients_OneRecipe);

        prepVisible=findViewById(R.id.prep_visible_OneRecipe);
        prepOneRecipe=findViewById(R.id.preparation_OneRecipe);

        authorOneRecipe=findViewById(R.id.author_OneRecipe);
        byOneRecipe=findViewById(R.id.by_OneRecipe);
    }

    public void setValues(Bundle bundle){

        //get fields from recyclerView list
        imgUrl=bundle.getString("url");
        name=bundle.getString("name");
        time=bundle.getString("time");
        type=bundle.getString("type");
        ingredients=bundle.getString("ingredients");
        prep=bundle.getString("prep");
        author=bundle.getString("author");

        context = getApplicationContext();

        //handle video
        imageOneRecipe.setVideoURI(Uri.parse(imgUrl));
        imageOneRecipe.setMediaController(mediaController);
        mediaController.setAnchorView(imageOneRecipe);
        imageOneRecipe.start();

        nameOneRecipe.setText(name);
        timeOneRecipe.setText(time);
        ingredientsOneRecipe.setText(ingredients);
        prepOneRecipe.setText(prep);
        authorOneRecipe.setText(author);
    }

    public void insertToDatabaseTable(){
        final String mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final int[] foundInAFamily= {0};

        //cautare in tabela de familii
        familyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot : snapshot.getChildren()){
                    for(DataSnapshot newpostsnapshot : postsnapshot.getChildren()){
                        String userID = newpostsnapshot.getKey();
                        if(userID.equals(mUser)){
                            String familyGroupId = postsnapshot.getKey(); //preluarea id-ului grupului

                            //incarcare ingrediente in baza de date
                            String[] result = ingredients.split("[\\r?\\n]+");
                            for(String s : result){
                                ListIngredientModel listIngredientModel = new ListIngredientModel(s,name);
                                String ingredientId = ingredientsDatabase.push().getKey();
                                ingredientsDatabase.child(familyGroupId).child(ingredientId).setValue(listIngredientModel);
                            }

                            //incarcare reteta in lista de favorite(tocook)
                            ListRecipeModel listRecipeModel = new ListRecipeModel(imgUrl,name,time,type,ingredients,prep,author);
                            String recipeId = favoritesDatabase.push().getKey();
                            favoritesDatabase.child(familyGroupId).child(recipeId).setValue(listRecipeModel);
                            foundInAFamily[0]=1;
                            break;
                        }
                    }
                    if(foundInAFamily[0]==1){
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void setOnClickListeners() {
        buttonWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToDatabaseTable();
            }
        });
    }


}
