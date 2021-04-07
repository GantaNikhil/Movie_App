package com.example.movie_app.Models;

import com.example.movie_app.MovieCategory.Result;

import java.util.ArrayList;

public class VerticalModel {
    String title;
    ArrayList<Result> arrayList;

    public VerticalModel(String title, ArrayList<Result> arrayList) {
        this.title = title;
        this.arrayList = arrayList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Result> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Result> arrayList) {
        this.arrayList = arrayList;
    }
}
