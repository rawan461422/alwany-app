package com.example.alwanyapp.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class HelperAppsDataClass implements Parcelable {
    private String name,link,description,image,appId;

    public HelperAppsDataClass() {
    }

    public HelperAppsDataClass(String name, String link, String description, String image, String appId) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.image = image;
        this.appId = appId;
    }

    protected HelperAppsDataClass(Parcel in) {
        name = in.readString();
        link = in.readString();
        description = in.readString();
        image = in.readString();
        appId = in.readString();
    }

    public static final Creator<HelperAppsDataClass> CREATOR = new Creator<HelperAppsDataClass>() {
        @Override
        public HelperAppsDataClass createFromParcel(Parcel in) {
            return new HelperAppsDataClass(in);
        }

        @Override
        public HelperAppsDataClass[] newArray(int size) {
            return new HelperAppsDataClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(appId);
    }
}
