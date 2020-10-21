package com.jidokhants.mukyojeong.model;

import java.util.ArrayList;
import java.util.Date;

public class Record {
    private int id;
    private Date date;
    private int meal;
    private double amountRatio;
    private Food food;

    public Record() {
    }

    public Record(int id, Date date, int meal, double amountRatio, Food food) {
        this.id = id;
        this.date = date;
        this.meal = meal;
        this.amountRatio = amountRatio;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }

    public double getAmountRatio() {
        return amountRatio;
    }

    public void setAmountRatio(int amountRatio) {
        this.amountRatio = amountRatio;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
