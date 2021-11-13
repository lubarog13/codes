package com.elegion.myfirstapplication;

import com.google.gson.annotations.SerializedName;

public class help_class {
    @SerializedName("text")
    private String text;
    @SerializedName("album_id")
    private int album_id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public help_class(String text, int album_id) {
        this.text = text;
        this.album_id = album_id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }
    public class ID {
        @SerializedName("id")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
