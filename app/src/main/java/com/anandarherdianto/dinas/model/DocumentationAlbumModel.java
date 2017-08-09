package com.anandarherdianto.dinas.model;

/**
 * Created by Ananda R. Herdianto on 27/04/2017.
 */

public class DocumentationAlbumModel {

    private int doc_id, numOfImages, temp;
    private String title, thumbnail, description, latitude, longitude, location, date, uploader, uploader_image;

    public DocumentationAlbumModel() {
    }

    public DocumentationAlbumModel(String title, int numOfImages, String thumbnail) {
        this.title = title;
        this.numOfImages = numOfImages;
        this.thumbnail = thumbnail;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploaderImage() {
        return uploader_image;
    }

    public void setUploaderImage(String uploader_image) {
        this.uploader_image = uploader_image;
    }
}
