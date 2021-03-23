package com.example.myapplication.Models;

public class ListIngredientModel {

    private String ingredient,nameRecipe;
    public ListIngredientModel(String ingredient,String nameRecipe){
        this.ingredient=ingredient;
        this.nameRecipe = nameRecipe;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }
}
