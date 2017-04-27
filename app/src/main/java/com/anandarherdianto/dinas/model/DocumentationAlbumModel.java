package com.anandarherdianto.dinas.model;

/**
 * Created by Ananda R. Herdianto on 27/04/2017.
 */

public class DocumentationAlbumModel {

    private String title;
    private int numOfImages;
    private int thumbnail;

    public DocumentationAlbumModel() {
    }

    public DocumentationAlbumModel(String title, int numOfImages, int thumbnail) {
        this.title = title;
        this.numOfImages = numOfImages;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumOfImages() {
        return numOfImages;
    }

    public void setNumOfImages(int numOfImages) {
        this.numOfImages = numOfImages;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
