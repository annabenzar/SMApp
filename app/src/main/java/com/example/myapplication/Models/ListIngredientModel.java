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

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }
}
