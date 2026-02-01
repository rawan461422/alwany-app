package com.example.alwanyapp.Domain;

public class ColorsDataClass {
    private String image,colorId;

    public ColorsDataClass() {
    }

    public ColorsDataClass(String image, String colorId) {
        this.image = image;
        this.colorId = colorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
