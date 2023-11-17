package com.example.carownerinfoapp;

public class car
{
    private String make;
    private String model;
    private String ownername;
    private String ownertel;

    public car(String make, String model, String ownername, String ownertel) {
        this.make = make;
        this.model = model;
        this.ownername = ownername;
        this.ownertel = ownertel;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getOwnertel() {
        return ownertel;
    }

    public void setOwnertel(String ownertel) {
        this.ownertel = ownertel;
    }
}

