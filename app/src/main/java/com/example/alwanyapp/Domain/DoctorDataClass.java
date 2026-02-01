package com.example.alwanyapp.Domain;

public class DoctorDataClass {
    private String name,email,phone,category,status,image,id;
    public DoctorDataClass() {
    }

    public DoctorDataClass(String name, String email, String phone, String category, String status, String image, String id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.category = category;
        this.status = status;
        this.image = image;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
