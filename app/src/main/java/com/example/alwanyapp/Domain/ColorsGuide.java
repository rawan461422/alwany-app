package com.example.alwanyapp.Domain;

public class ColorsGuide {
    private String colorName,image,colorId;

    public ColorsGuide() {
    }

    public ColorsGuide(String colorName, String image, String colorId) {
        this.colorName = colorName;
        this.image = image;
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
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
