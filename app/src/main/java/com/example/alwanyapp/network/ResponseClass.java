package com.example.alwanyapp.network;

import com.google.gson.annotations.SerializedName;

public class ResponseClass {
    @SerializedName("accuracy")
    String accuracy;
    @SerializedName("color")
    String color;

    public ResponseClass(String accuracy, String color) {
        this.accuracy = accuracy;
        this.color = color;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
