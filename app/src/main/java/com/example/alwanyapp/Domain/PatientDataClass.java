package com.example.alwanyapp.Domain;

public class PatientDataClass {
    private String name,email,image,phone,id;

    public PatientDataClass() {
    }

    public PatientDataClass(String name, String email, String image, String phone, String id) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
