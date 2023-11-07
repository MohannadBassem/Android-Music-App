package com.example.musicalstructureappprototype2;

public class Files {
    private String artist;
    private String album;
    private String duration;
    private String path;
    private String title;

    public Files(String artist, String album, String duration, String path, String title) {
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.path = path;
        this.title = title;
    }

    public Files() {
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
