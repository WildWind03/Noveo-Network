package com.noveogroup.network.model;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("id")
    private final String id;

    @SerializedName("image")
    private final String imageUrl;

    @SerializedName("pubDate")
    private final String date;

    @SerializedName("title")
    private final String title;

    @SerializedName("topics")
    private final String[] topics;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public News(String id, String imageUrl, String date, String title, String[] topics) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.date = date;
        this.title = title;
        this.topics = topics;
    }
}
