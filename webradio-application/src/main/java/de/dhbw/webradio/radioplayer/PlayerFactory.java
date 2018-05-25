package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.m3uparser.M3uParser;
import de.dhbw.webradio.models.M3UInfo;
import de.dhbw.webradio.models.Station;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PlayerFactory implements Factory {
    public AbstractPlayer get(Station s) {
        if (s == null) {
            throw new IllegalArgumentException("No station was passed. \r\n PlayerFactory needs a station to determine file extension");
        }
        FileExtensionParser fileExtensionParser = new FileExtensionParser();
        URL stationURL = s.getStationURL();
        FileExtension urlExtension = fileExtensionParser.parseFileExtension(stationURL.toString());
        if (urlExtension.equals(FileExtension.MP3)) {
            AbstractPlayer player = new Mp3Player();
            player.setUrl(stationURL);
            return player;
        } else if (urlExtension.equals(FileExtension.M3U)) {
            M3uParser m3uParser = new M3uParser();
            String m3uFileContent;
            try {
                m3uFileContent = m3uParser.parseFileFromUrlToString(stationURL);
                List<M3UInfo> m3uStreamInformation = m3uParser.parseUrlFromString(m3uFileContent);
                AbstractPlayer player = new Mp3Player();
                player.setUrl(new URL(m3uStreamInformation.get(0).getUrl().toString()));
                return player;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (NoURLTagFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
