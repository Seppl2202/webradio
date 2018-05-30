package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.gui.SelectMultipleItemsDialog;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.m3uparser.M3uParser;
import de.dhbw.webradio.m3uparser.PLSParser;
import de.dhbw.webradio.models.M3UInfo;
import de.dhbw.webradio.models.Station;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayerFactory implements Factory {
    public AbstractPlayer get(Station s) {
        if (s == null) {
            throw new IllegalArgumentException("No station was passed. \r\n PlayerFactory needs a station to determine file extension");
        }
        FileExtensionParser fileExtensionParser = new FileExtensionParser();
        URL stationURL = s.getStationURL();
        FileExtension urlExtension = fileExtensionParser.parseFileExtension(stationURL);
        if (urlExtension.equals(FileExtension.MP3)) {
            AbstractPlayer player = new Mp3Player();
            player.setUrl(stationURL);
            return player;
        } else if (urlExtension.equals(FileExtension.AAC)) {
            AbstractPlayer player = new AACPlayer();
            player.setUrl(stationURL);
            return player;

        } else if (urlExtension.equals(FileExtension.M3U)) {
            String m3uFileContent;
            try {
                M3uParser m3uParser = new M3uParser();
                m3uFileContent = m3uParser.parseFileFromUrlToString(stationURL);
                M3UInfo userSelectedStream = getUserSelection(m3uFileContent, m3uParser);
                AbstractPlayer player = parseMP3orAAC(fileExtensionParser, userSelectedStream.getUrl());
                if (player != null) return player;
            } catch (IOException e) {
                WebradioPlayer.setPlayer(null);
                GUIHandler.getInstance().resetComponents();
                Logger.logError(e.getMessage());
            } catch (UnsupportedAudioFileException e) {
                WebradioPlayer.setPlayer(null);
                GUIHandler.getInstance().resetComponents();
                Logger.logError(e.getMessage());
            } catch (NoURLTagFoundException e) {
                WebradioPlayer.setPlayer(null);
                GUIHandler.getInstance().resetComponents();
                Logger.logError(e.getMessage());
            }
        } else if (urlExtension.equals(FileExtension.PLS)) {
            PLSParser plsParser = new PLSParser();
            List<String> plsEntries = plsParser.parsePLS(stationURL);
            try {
                String userSelectedURL = getPLSSelection(plsEntries);
                AbstractPlayer player = parseMP3orAAC(fileExtensionParser, new URL(userSelectedURL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private AbstractPlayer parseMP3orAAC(FileExtensionParser fileExtensionParser, URL userSelectedStream) throws MalformedURLException {
        if (fileExtensionParser.parseFileExtension(userSelectedStream).equals(FileExtension.MP3)) {
            AbstractPlayer player = new Mp3Player();
            player.setUrl(userSelectedStream);
            return player;
        } else if (fileExtensionParser.parseFileExtension(userSelectedStream).equals(FileExtension.AAC)) {
            AbstractPlayer aacPlayer = new AACPlayer();
            aacPlayer.setUrl(userSelectedStream);
            return aacPlayer;
        }
        return null;
    }

    private String getPLSSelection(List<String> plsEntries) throws MalformedURLException {
        if (plsEntries.size() == 1) {
            return plsEntries.get(0);
        } else {
            final String[] selectoin = new String[1];
            JList list = new JList<>(plsEntries.toArray());
            SelectMultipleItemsDialog dialog = new SelectMultipleItemsDialog<M3UInfo>("Bitte wählen Sie einen Stream aus", "Bitte wählen:", list, ListSelectionModel.SINGLE_SELECTION);
            dialog.setOnOk(e -> selectoin[0] = (String) dialog.getSelectedItem().get(0));
            dialog.show();
            return selectoin[0];
        }
    }

    /**
     * @param m3uFileContent the parsed m3u file
     * @param m3uParser      the parser that parses the different m3u objects from @m3uFileContent
     * @return a single M3UInfo: the user selection
     * @throws UnsupportedAudioFileException if no valid audio formats are found
     * @throws NoURLTagFoundException        if an EM3U file does not contain a valid syntax
     * @throws MalformedURLException         if the URLs of the M3U file are incorrect
     */
    public M3UInfo getUserSelection(String m3uFileContent, M3uParser m3uParser) throws UnsupportedAudioFileException, NoURLTagFoundException, MalformedURLException {

        List<M3UInfo> m3uStreamInformation = m3uParser.parseUrlFromString(m3uFileContent);
        if (m3uStreamInformation.size() == 1) {
            return m3uStreamInformation.get(0);
        } else {
            final Object[] m3uInfo = new M3UInfo[m3uStreamInformation.size()];
            JList list = new JList<>(m3uStreamInformation.toArray());
            SelectMultipleItemsDialog dialog = new SelectMultipleItemsDialog<M3UInfo>("Bitte wählen Sie einen Stream aus", "Bitte wählen:", list, ListSelectionModel.SINGLE_SELECTION);
            dialog.setOnOk(e -> m3uInfo[0] = dialog.getSelectedItem().get(0));
            dialog.show();
            return (M3UInfo) m3uInfo[0];
        }
    }
}
