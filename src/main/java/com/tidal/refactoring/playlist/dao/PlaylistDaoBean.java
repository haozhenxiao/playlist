package com.tidal.refactoring.playlist.dao;

import com.tidal.refactoring.playlist.data.PlayList;
import com.tidal.refactoring.playlist.data.PlayListTrack;
import com.tidal.refactoring.playlist.data.Track;

import java.util.*;

/**
 * Class faking the data layer, and returning fake playlists
 */
public class PlaylistDaoBean {

    private final Map<String, PlayList> playlists = new HashMap<String, PlayList>();

    public PlayList getOrCreatePlaylistByUUID(String uuid) {

        PlayList playList = playlists.get(uuid);

        if (playList != null) {
            return playList;
        }

        //return default playlist
        PlayList newPlaylist = createPlayList(uuid);
        playlists.put(uuid, newPlaylist);
        return newPlaylist;
    }
    
    public PlayList getPlaylistByUUID(String uuid) {
        return playlists.get(uuid);
    }

    private PlayList createPlayList(String uuid) {
        PlayList trackPlayList = new PlayList(uuid, 49834, "Collection of great songs", new Date());
        trackPlayList.setPlayListTracks(getPlaylistTracks());

        return trackPlayList;
    }

    private static List<PlayListTrack> getPlaylistTracks() {

        List<PlayListTrack> playListTracks = new ArrayList<PlayListTrack>();
        for (int i = 0; i < 376; i++) {
            PlayListTrack playListTrack = new PlayListTrack(getTrack(), new Date());
            playListTracks.add(playListTrack);
        }

        return playListTracks;
    }

    public static Track getTrack() {
        Random randomGenerator = new Random();
        int id = randomGenerator.nextInt(15); // not unique, just for testing.
        String title = "Track no: " + id;
        int artistId = randomGenerator.nextInt(10000);
        float duration = 60 * 3;
        Track track = new Track(id, artistId, duration, title);

        return track;
    }
}
