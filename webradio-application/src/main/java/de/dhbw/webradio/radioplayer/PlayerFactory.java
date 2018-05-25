package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.gui.SelectStreamDialog;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.m3uparser.M3uParser;
import de.dhbw.webradio.models.M3UInfo;
import de.dhbw.webradio.models.Station;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
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
            String m3uFileContent;
            try {
                M3uParser m3uParser = new M3uParser();
                m3uFileContent = m3uParser.parseFileFromUrlToString(stationURL);
                M3UInfo userSelectedStream = getUserSelection(m3uFileContent, m3uParser);
                AbstractPlayer player = new Mp3Player();
                player.setUrl(new URL(userSelectedStream.getUrl().toString()));
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

    public M3UInfo getUserSelection(String m3uFileContent, M3uParser m3uParser) throws UnsupportedAudioFileException, NoURLTagFoundException {

        List<M3UInfo> m3uStreamInformation = m3uParser.parseUrlFromString(m3uFileContent);
        if (m3uStreamInformation.size() == 1) {
            return m3uStreamInformation.get(0);
        } else {
            final M3UInfo[] m3uInfo = new M3UInfo[1];
            JList list = new JList<>(m3uStreamInformation.toArray());
            SelectStreamDialog dialog = new SelectStreamDialog("Bitte wählen Sie einen Stream aus", "Bitte wählen:", list);
            dialog.setOnOk(e -> m3uInfo[0] = dialog.getSelectedItem());
            dialog.show();
            return m3uInfo[0];
        }
    }
}
