package com.jidokhants.mukyojeong.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private Double ratio;
    private Food food;

    public Ingredient() {
    }

    public Ingredient(Food food) {
        this.food = food;
        this.ratio = 1.0;
    }

    public Ingredient(Double ratio, Food food) {
        this.ratio = ratio;
        this.food = food;
    }
    public Ingredient(Ingredient ingredient){
        this.ratio = ingredient.ratio;
        this.food = ingredient.food;
    }
    protected Ingredient(Parcel in) {
        if (in.readByte() == 0) {
            ratio = null;
        } else {
            ratio = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (ratio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ratio);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
