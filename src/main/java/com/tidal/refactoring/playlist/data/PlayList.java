package com.tidal.refactoring.playlist.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


/**
 * A very simplified version of TrackPlaylist
 */
public class PlayList {

    public static final int MAX_NUM_OF_TRACKS = 500;
    
    private Integer id;
    private String playListName;
    private List<PlayListTrack> playListTracks = new ArrayList<PlayListTrack>();
    private Date registeredDate;
    private Date lastUpdated;
    private String uuid;
    private boolean deleted;

    public PlayList(String uuid, int id, String playListName, Date date) {
        this.uuid = uuid;
        this.id = id;
        this.playListName = playListName;
        this.registeredDate = date;
        this.lastUpdated = date;
        this.deleted = false;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public List<PlayListTrack> getPlayListTracks() {
        return playListTracks;
    }

    public void setPlayListTracks(List<PlayListTrack> playListTracks) {
        this.playListTracks = playListTracks;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getNrOfTracks() {
        return playListTracks.size();
    }

    public float getDuration() {
        float duration = 0;
        for(PlayListTrack t : playListTracks) {
            duration += t.getTrack().getDuration();
        }
        return duration;
    }

}