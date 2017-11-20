package com.tidal.refactoring.playlist;

import com.google.inject.Inject;
import com.tidal.refactoring.playlist.data.PlayList;
import com.tidal.refactoring.playlist.data.PlayListTrack;
import com.tidal.refactoring.playlist.data.Track;
import com.tidal.refactoring.playlist.exception.PlaylistException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.AssertJUnit.*;

@Guice(modules = TestBusinessModule.class)
public class PlaylistBusinessBeanTest {

    @Inject
    PlaylistBusinessBean playlistBusinessBean;
    
    String uuid = UUID.randomUUID().toString();

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    private void assertPlaylistDaoBean(int expectedNrOfTracks, int indexAdded, List<Track> expectedAddedTrackList, float expectedDuration) {
        PlayList actualPlaylist = playlistBusinessBean.getPlaylistDaoBean().getPlaylistByUUID(uuid);
        assertNotNull(actualPlaylist);
        assertEquals(actualPlaylist.getNrOfTracks(), expectedNrOfTracks);
        for(int i = 0; i < expectedAddedTrackList.size(); ++i) {
            PlayListTrack actualPlayListTrack = actualPlaylist.getPlayListTracks().get(indexAdded + i);
            Track expectedTrack = expectedAddedTrackList.get(i);
            Track actualTrack = actualPlayListTrack.getTrack();
            assertEquals(actualPlayListTrack.getTrackId(), expectedTrack.getId());
            assertTracksEqual(actualTrack, expectedTrack);
        }
        assertEquals(actualPlaylist.getDuration(), expectedDuration);
    }
    
    private void assertTracksEqual(Track actual, Track expected) {
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getArtistId(), expected.getArtistId());
        assertEquals(actual.getDuration(), expected.getDuration());
    }
    
    @Test
    public void test1AddOneTrackToTheMiddleOfPlaylist() throws Exception {
        List<Track> trackList = new ArrayList<Track>();

        Track track = new Track(76868, 4, 60, "A brand new track");

        trackList.add(track);
        int indexToAdd = 5;
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, trackList, indexToAdd);

        assertEquals(playListTracks.size(), 1);
        assertPlaylistDaoBean(377, indexToAdd, trackList, 67740);
    }
    
    @Test
    public void test2AddTracksToTheMiddleOfPlaylist() throws Exception {
        List<Track> trackList = new ArrayList<Track>();

        Track track1 = new Track(76868, 4, 60 * 2, "Track1");
        Track track2 = new Track(76869, 4, 50 * 2, "Track2");
        Track track3 = track1;

        trackList.add(track1);
        trackList.add(track2);
        trackList.add(track3);
        int indexToAdd = 200;
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, trackList, indexToAdd);

        assertEquals(playListTracks.size(), 3);
        assertPlaylistDaoBean(380, indexToAdd, trackList, 68080);
    }
    
    @Test
    public void test3AddOneTrackWithMinusOneIndex() throws Exception {
        List<Track> trackList = new ArrayList<Track>();

        Track track = new Track(76870, 4, 55 * 2, "A brand new track");

        trackList.add(track);
        int indexToAdd = -1;
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, trackList, indexToAdd);

        assertEquals(playListTracks.size(), 1);
        assertPlaylistDaoBean(381, 380, trackList, 68190);
    }
    
    @Test
    public void test4AddOneTrackWithNegativeIndex() throws Exception {
        List<Track> trackList = new ArrayList<Track>();

        Track track = new Track(76870, 4, 55 * 2, "A brand new track");

        trackList.add(track);
        int indexToAdd = -5;
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, trackList, indexToAdd);

        assertEquals(playListTracks.size(), 0);
    }
    
    @Test
    public void test5AddTracksToTheEndOfPlaylist() throws Exception {
        List<Track> trackList = new ArrayList<Track>();

        Track track1 = new Track(76871, 4, 70, "Track1"); 
        Track track2 = new Track(76872, 4, 70, "Track2");

        trackList.add(track1);
        trackList.add(track2);
        int indexToAdd = 600;
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, trackList, indexToAdd);

        assertEquals(playListTracks.size(), 2);
        assertPlaylistDaoBean(383, 381, trackList, 68330);
    }
    
    @Test
    public void test6AddTracksExceedLimit() throws Exception {
        List<Track> trackList = new ArrayList<Track>();

        Track track = new Track(76873, 4, 90, "Track1");

        for(int i = 0; i < 118; ++i)
            trackList.add(track);
        int indexToAdd = 300;
        boolean exceptionThrown = false;
        try {
            playlistBusinessBean.addTracks(uuid, trackList, indexToAdd);
        } catch(PlaylistException e) {
            assertEquals(e.getMessage(), "Playlist cannot have more than " + PlayList.MAX_NUM_OF_TRACKS + " tracks");
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
    
    @Test
    public void test7DeleteTracksFromPlaylist() {
        List<Integer> toRemove = new ArrayList<Integer>();
        toRemove.add(201);
        toRemove.add(201);
        toRemove.add(5);
        toRemove.add(-1);
        toRemove.add(700);
        toRemove.add(380);
        toRemove.add(380);
        Track track5 = new Track(76868, 4, 60, "A brand new track");
        Track track201 = new Track(76869, 4, 50 * 2, "Track2");
        Track track380 = new Track(76870, 4, 55 * 2, "A brand new track");
        List<Track> expectedRemovedTracks = new ArrayList<Track>();
        expectedRemovedTracks.add(track5);
        expectedRemovedTracks.add(track201);
        expectedRemovedTracks.add(track380);
        List<PlayListTrack> removedTracks = playlistBusinessBean.removeTracks(uuid, toRemove);
        assertEquals(removedTracks.size(), expectedRemovedTracks.size());
        for(int i = 0; i < removedTracks.size(); ++i)
        {
            assertEquals(removedTracks.get(i).getTrackId(), expectedRemovedTracks.get(i).getId());
            assertTracksEqual(removedTracks.get(i).getTrack(), expectedRemovedTracks.get(i));
        }
        assertPlaylistDaoBean(380, -1, new ArrayList<Track>(), 68060);
    }
}