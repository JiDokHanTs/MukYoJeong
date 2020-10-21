package com.jidokhants.mukyojeong.model;

public class FoodItem {
    private int id;
    private String commercial;
    private String name;
    private String from;

    public FoodItem(int id, String commercial, String name, String from) {
        this.id = id;
        this.commercial = commercial;
        this.name = name;
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommercial() {
        return commercial;
    }

    public void setCommercial(String commercial) {
        this.commercial = commercial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
