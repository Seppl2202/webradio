package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.gui.SelectMultipleItemsDialog;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.playlistparser.FileExtensionParser;
import de.dhbw.webradio.playlistparser.M3uParser;
import de.dhbw.webradio.playlistparser.PLSParser;
import de.dhbw.webradio.models.InformationObject;
import de.dhbw.webradio.models.M3UInfo;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.playlistparser.PlaylistParser;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayerFactory implements Factory {
    private static Factory factory = new PlayerFactory();

    public static Factory getInstance() {
        return factory;
    }

    public AbstractPlayer get(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("No station was passed. \r\n PlayerFactory needs a station to determine file extension");
        }
        FileExtensionParser fileExtensionParser = new FileExtensionParser();
        URL stationURL = station.getStationURL();
        FileExtension urlExtension = fileExtensionParser.parseFileExtension(stationURL);
        if (urlExtension.equals(FileExtension.MP3)) {
            AbstractPlayer player = new MP3Player();
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
                InformationObject userSelectedStream = getUserSelection(m3uFileContent, m3uParser);
                AbstractPlayer player = parseMP3orAAC(fileExtensionParser, userSelectedStream.getUrl());
                return player;
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
            List<InformationObject> plsEntries = plsParser.parsePLS(stationURL);
            try {
                String userSelectedURL = getPLSSelection(plsEntries);
                AbstractPlayer player = parseMP3orAAC(fileExtensionParser, new URL(userSelectedURL));
                return player;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private AbstractPlayer parseMP3orAAC(FileExtensionParser fileExtensionParser, URL userSelectedStream) throws MalformedURLException {
        if (fileExtensionParser.parseFileExtension(userSelectedStream).equals(FileExtension.MP3)) {
            AbstractPlayer player = new MP3Player();
            player.setUrl(userSelectedStream);
            return player;
        } else if (fileExtensionParser.parseFileExtension(userSelectedStream).equals(FileExtension.AAC)) {
            AbstractPlayer aacPlayer = new AACPlayer();
            aacPlayer.setUrl(userSelectedStream);
            return aacPlayer;
        }
        return null;
    }

    private String getPLSSelection(List<InformationObject> plsEntries) throws MalformedURLException {
        if (plsEntries.size() == 1) {
            return plsEntries.get(0).getUrl().toString();
        } else {
            final Object[] selection = new String[1];
            JList list = new JList<>(plsEntries.toArray());
            SelectMultipleItemsDialog dialog = new SelectMultipleItemsDialog<InformationObject>("Bitte wählen Sie einen Stream aus", "Bitte wählen:", list, ListSelectionModel.SINGLE_SELECTION);
            dialog.setOnOk(e -> selection[0] = (String) dialog.getSelectedItem().get(0));
            dialog.show();
            InformationObject[] object = (InformationObject[]) selection;
            return object[0].getUrl().toString();
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
    public InformationObject getUserSelection(String m3uFileContent, PlaylistParser m3uParser) throws UnsupportedAudioFileException, NoURLTagFoundException, MalformedURLException {

        List<InformationObject> m3uStreamInformation = m3uParser.parseURLFromString(m3uFileContent);
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
