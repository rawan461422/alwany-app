package com.example.alwanyapp.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InfoDataClass implements Parcelable {
    private String title,description,image,date,id;

    public InfoDataClass() {
    }

    public InfoDataClass(String title, String description, String image, String date, String id) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.id = id;
    }

    protected InfoDataClass(Parcel in) {
        title = in.readString();
        description = in.readString();
        image = in.readString();
        date = in.readString();
        id = in.readString();
    }

    public static final Creator<InfoDataClass> CREATOR = new Creator<InfoDataClass>() {
        @Override
        public InfoDataClass createFromParcel(Parcel in) {
            return new InfoDataClass(in);
        }

        @Override
        public InfoDataClass[] newArray(int size) {
            return new InfoDataClass[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(date);
        dest.writeString(id);
    }
}
