package com.tidal.refactoring.playlist.data;

import java.io.Serializable;
import java.util.Date;

public class PlayListTrack implements Serializable {

    private static final long serialVersionUID = 5464240796158432162L;

    private String playListId;
    private Date dateAdded;

    private Track track;

    public PlayListTrack(Track track, Date dateAdded) {
        this.track = track;
        this.dateAdded = dateAdded;
    }

    public int getTrackId() {
        return track.getId();
    }

    public String getTrackPlayListId() {
        return playListId;
    }
    
    public void setTrackPlaylistId(String playListId) {
        this.playListId = playListId;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    public String toString() {
        return "TrackId[" + getTrackId() + "]" + "DateAdded[" + dateAdded.toString() + "]";
    }
}
