package com.example.movid19;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class UploadMovie implements Serializable {
    private String title;
    private String description;
    private String imageUri;
    private String mKey;

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public UploadMovie() {}

    public UploadMovie(String title, String description, String imageUri) {
        if (title.trim().equals("")) {
            title = "No name";
        }
        if (description.trim().equals("")) {
            description = "No Description";
        }
        this.title = title;
        this.description = description;
        this.imageUri = imageUri;

    }

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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
