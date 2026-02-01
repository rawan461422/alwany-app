package com.example.alwanyapp.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ConsultationsDataClass implements Parcelable {
    private String userName,userImage,title,content,date,replay,status,patientId,DoctorId,consultationsId;

    public ConsultationsDataClass() {
    }

    public ConsultationsDataClass(String userName, String userImage, String title, String content, String date, String replay, String status, String patientId, String doctorId, String consultationsId) {
        this.userName = userName;
        this.userImage = userImage;
        this.title = title;
        this.content = content;
        this.date = date;
        this.replay = replay;
        this.status = status;
        this.patientId = patientId;
        DoctorId = doctorId;
        this.consultationsId = consultationsId;
    }

    protected ConsultationsDataClass(Parcel in) {
        userName = in.readString();
        userImage = in.readString();
        title = in.readString();
        content = in.readString();
        date = in.readString();
        replay = in.readString();
        status = in.readString();
        patientId = in.readString();
        DoctorId = in.readString();
        consultationsId = in.readString();
    }

    public static final Creator<ConsultationsDataClass> CREATOR = new Creator<ConsultationsDataClass>() {
        @Override
        public ConsultationsDataClass createFromParcel(Parcel in) {
            return new ConsultationsDataClass(in);
        }

        @Override
        public ConsultationsDataClass[] newArray(int size) {
            return new ConsultationsDataClass[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public String getConsultationsId() {
        return consultationsId;
    }

    public void setConsultationsId(String consultationsId) {
        this.consultationsId = consultationsId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userImage);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeString(replay);
        dest.writeString(status);
        dest.writeString(patientId);
        dest.writeString(DoctorId);
        dest.writeString(consultationsId);
    }
}
