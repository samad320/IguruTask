package com.app.igurutask;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private String uploadedImageUri;


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUploadedImageUri() {
        return uploadedImageUri;
    }

    public void setUploadedImageUri(String uploadedImageUri) {
        this.uploadedImageUri = uploadedImageUri;
    }

    public void setId(int id) {
        this.id = id;
    }
}
