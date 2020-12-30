package com.example.myapplication.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class OneRecipeActivity extends AppCompatActivity {

    public static Context context;
    private ImageView imageOneRecipe;
    private TextView nameOneRecipe, timeOneRecipe,ingredientsOneRecipe,prepOneRecipe;
    private TextView ingrVisible,prepVisible;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onerecipe);
        Bundle bundle = getIntent().getExtras();
        initializeViews();
        setValues(bundle);
    }

    public void initializeViews(){
        imageOneRecipe=findViewById(R.id.img_OneRecipe);

        nameOneRecipe=findViewById(R.id.name_OneRecipe);
        timeOneRecipe=findViewById(R.id.time_OneRecipe);

        ingrVisible=findViewById(R.id.ingredients_visible_OneRecipe);
        ingredientsOneRecipe=findViewById(R.id.ingredients_OneRecipe);

        prepVisible=findViewById(R.id.prep_visible_OneRecipe);
        prepOneRecipe=findViewById(R.id.preparation_OneRecipe);
    }

    public void setValues(Bundle bundle){
        String imgUrl,name,time,ingredients,prep;

        imgUrl=bundle.getString("url");
        name=bundle.getString("name");
        time=bundle.getString("time");
        ingredients=bundle.getString("ingredients");
        prep=bundle.getString("prep");

        context = getApplicationContext();

        Glide.with(OneRecipeActivity.context).load(imgUrl).into(imageOneRecipe);

        nameOneRecipe.setText(name);
        timeOneRecipe.setText(time);
        ingredientsOneRecipe.setText(ingredients);
        prepOneRecipe.setText(prep);
    }


}
