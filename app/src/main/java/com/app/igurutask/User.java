package com.app.igurutask;

public class User {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    private byte[] uploadedImageUri;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public byte[] getUploadedImageUri() {
        return uploadedImageUri;
    }

    public void setUploadedImageUri(byte[] uploadedImageUri) {
        this.uploadedImageUri = uploadedImageUri;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
