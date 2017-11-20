package com.tidal.refactoring.playlist.data;


public class Track {

    private String title;
    private float duration;

    private int artistId;
    private int id;

    public Track(int id, int artistId, float duration, String title) {
        this.id = id;
        this.artistId = artistId;
        this.duration = duration;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}