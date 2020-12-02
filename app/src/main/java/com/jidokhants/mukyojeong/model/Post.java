package com.jidokhants.mukyojeong.model;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class Post {
    public String title;
    public User writer;
    public String date;
    public String content;
    public String imagePath;
    public ArrayList<Ingredient> ingredients;
    public Food resultFood;
    public ArrayList<Comment> comments;
    public ArrayList<String> likes;

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Food getResultFood() {
        return resultFood;
    }

    public void setResultFood(Food resultFood) {
        this.resultFood = resultFood;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public Post(String title, User writer, String date, String content, String imagePath, ArrayList<Ingredient> ingredients, Food resultFood, ArrayList<Comment> comments, ArrayList<String> likes) {
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.content = content;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.resultFood = resultFood;
        this.comments = comments;
        this.likes = likes;
    }
}
