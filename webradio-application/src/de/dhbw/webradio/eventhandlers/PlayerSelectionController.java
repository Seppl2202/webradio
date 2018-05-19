package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.m3uparser.M3uParser;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class PlayerSelectionController {
    private Station station;
    private M3uParser m3uParser;
    private FileExtensionParser fileExtensionParser;
    private AbstractPlayer player = null;
    private String m3uUrl = null;

    public PlayerSelectionController(Station s) {
        this.station = s;
        m3uParser = new M3uParser();
        fileExtensionParser = new FileExtensionParser();
    }

    private FileExtension getFileExtension() {
        return fileExtensionParser.parseFileExtension(station.getStationURL().toString());
    }

    private AbstractPlayer getCorrectPlayer() {
        if (getFileExtension().equals(FileExtension.MP3)) {
            //get fake player, to be implemented as builder pattern later
            player = WebradioPlayer.getPlayer();
            player.setUrl(station.getStationURL());
            WebradioPlayer.getGui().getStreamDetails().updateM3uUrl("Aktuell wird kein M3U-Stream wiedergegben");
            WebradioPlayer.getGui().getStreamDetails().updateM3uInfo("Kein M3U-Stream verfügbar");
            WebradioPlayer.getGui().getStreamDetails().updateStreamUrl(station.getStationURL().toString());
            WebradioPlayer.getGui().getStreamDetails().updateStationName(station.getName());
            return player;
        } else if (getFileExtension().equals(FileExtension.M3U)) {
            URL mp3url = null;
            String[] m3uInfo = null;
            player = WebradioPlayer.getPlayer();
            //parse mp3 stream from m3u file
            try {
                String parsedFile = m3uParser.parseFileFromUrlToString(station.getStationURL());
                m3uInfo = m3uParser.parseUrlFromString(parsedFile);
                mp3url = new URL(m3uInfo[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (NoURLTagFoundException nufe) {
                nufe.printStackTrace();
            }
            WebradioPlayer.getGui().getStatusBar().updateAdditionalM3uInfo(m3uInfo[1]);
            WebradioPlayer.getGui().getStreamDetails().updateM3uInfo(m3uInfo[1]);
            WebradioPlayer.getGui().getStreamDetails().updateM3uUrl(station.getStationURL().toString());
            WebradioPlayer.getGui().getStreamDetails().updateStreamUrl(mp3url.toString());
            WebradioPlayer.getGui().getStreamDetails().updateStationName(station.getName());
            player.setUrl(mp3url);
        }
        return player;
    }

    public AbstractPlayer getPlayerForFileType() {
        return getCorrectPlayer();
    }
}
