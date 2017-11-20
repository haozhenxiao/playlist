package com.tidal.refactoring.playlist;

import com.google.inject.Inject; 
import com.tidal.refactoring.playlist.dao.PlaylistDaoBean;
import com.tidal.refactoring.playlist.data.PlayListTrack;
import com.tidal.refactoring.playlist.data.Track;
import com.tidal.refactoring.playlist.data.PlayList;
import com.tidal.refactoring.playlist.exception.PlaylistException;

import java.util.*;

public class PlaylistBusinessBean {

    private PlaylistDaoBean playlistDaoBean;

    @Inject
    public PlaylistBusinessBean(PlaylistDaoBean playlistDaoBean){
        this.playlistDaoBean = playlistDaoBean;
    }

    /**
     * Add tracks to the index
     */
    List<PlayListTrack> addTracks(String uuid, List<Track> tracksToAdd, int toIndex) throws PlaylistException {

        PlayList playList = playlistDaoBean.getOrCreatePlaylistByUUID(uuid);

        //We do not allow > 500 tracks in new playlists
        if (playList.getNrOfTracks() + tracksToAdd.size() > PlayList.MAX_NUM_OF_TRACKS) {
            throw new PlaylistException("Playlist cannot have more than " + PlayList.MAX_NUM_OF_TRACKS + " tracks");
        }

        // The index is out of bounds, put it in the end of the list.
        int size = playList.getPlayListTracks() == null ? 0 : playList.getPlayListTracks().size();
        if (toIndex > size || toIndex == -1) {
            toIndex = size;
        }

        if (!validateIndexes(toIndex, playList.getNrOfTracks())) {
            return Collections.EMPTY_LIST;
        }

        List<PlayListTrack> added = new ArrayList<PlayListTrack>(tracksToAdd.size());

        for (Track track : tracksToAdd) {
            PlayListTrack playlistTrack = new PlayListTrack(track, new Date());
            playlistTrack.setTrack(track);
            playlistTrack.setTrackPlaylistId(playList.getUuid());
            playlistTrack.setDateAdded(new Date());
            playlistTrack.setTrack(track);
            playList.getPlayListTracks().add(toIndex, playlistTrack);
            added.add(playlistTrack);
            toIndex++;
        }
        
        return added;
    }
    
	/**
	 * Remove the tracks from the playlist located at the sent indexes
	 */
	List<PlayListTrack> removeTracks(String uuid, List<Integer> indexes) {
		PlayList playList = playlistDaoBean.getPlaylistByUUID(uuid);
                if(playList == null) return Collections.EMPTY_LIST;
                SortedSet<Integer> sortedIndexes = new TreeSet<Integer>();
                sortedIndexes.addAll(indexes);
                List<PlayListTrack> removed = new ArrayList<PlayListTrack>(sortedIndexes.size());
                for(int i : sortedIndexes) {
                    if(i - removed.size() > 0 && i < playList.getPlayListTracks().size()) {
                        removed.add(playList.getPlayListTracks().remove(i - removed.size()));
                    }
                }
		return removed;
	}

    private boolean validateIndexes(int toIndex, int length) {
        return toIndex >= 0 && toIndex <= length;
    }
    
    public PlaylistDaoBean getPlaylistDaoBean() {
        return playlistDaoBean;
    }
}
