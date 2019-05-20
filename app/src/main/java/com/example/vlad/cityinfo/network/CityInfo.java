package com.example.vlad.cityinfo.network;

public class CityInfo {
    private String title;
    private String summary;
    private String wikipediaUrl;
    private String thumbnailImg;

    public CityInfo(String title, String summary, String wikipediaUrl, String thumbnailImg) {
        this.title = title;
        this.summary = summary;
        this.wikipediaUrl = wikipediaUrl;
        this.thumbnailImg = thumbnailImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }
}
