package com.example.carownerinfoapp;

import android.app.Application;

import java.util.ArrayList;

public class carApplication extends Application
{
    public static ArrayList<car> cars;
    @Override
    public void onCreate() {
        super.onCreate();

        cars=new ArrayList<car>();
        cars.add(new car("Volkswagen","Polo","Sahil Gupta","9725373826"));
        cars.add(new car("Mercedes","E200","Geetansh Verma","9462783678"));
        cars.add(new car("Mercedes","E180","Tanzeel Khan","9624642647"));
        cars.add(new car("Nissan","Almera","Vishal Karande","9246347246"));
        cars.add(new car("Volkswagen","Kombi","Shrey Chaudhary","9634627364"));
        cars.add(new car("Nissan","Navara","Saksham Gaur","9282463892"));
    }
}

