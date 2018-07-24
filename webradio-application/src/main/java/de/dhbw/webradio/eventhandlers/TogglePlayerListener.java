package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.gui.Handler;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.Factory;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;
import de.dhbw.webradio.radioplayer.PlayerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TogglePlayerListener implements ActionListener {
    private IcyInputStreamReader reader;
    private Station s;

    public void actionPerformed(ActionEvent e) {
        PlayerFactory playerFactory = new PlayerFactory();
        s = getStation();
        AbstractPlayer actualPlayer = WebradioPlayer.getPlayer();
        //if no player was created yet, directly create a new one
        if (actualPlayer == null) {
            createPlayerAndUpdateGui(playerFactory, s);
        }
        if (!(actualPlayer == null)) {
            if (actualPlayer.isPlaying() && !(actualPlayer == null)) {
                actualPlayer.stop();
                actualPlayer.getIcyReader().setInterrupted(true);
                GUIHandler.getInstance().resetComponents();
            } else {
                if (!(s == null)) {
                    try {
                        if (!s.isURLValid()) {
                            throw new MalformedURLException("URL: " + s.getStationURL() + "did not returned status 200");
                        }
                        createPlayerAndUpdateGui(playerFactory, s);
                    } catch (MalformedURLException mue) {
                        WebradioPlayer.setPlayer(null);
                        GUIHandler.getInstance().resetComponents();
                        Logger.logError(mue.getMessage());
                    } catch (Exception ex) {
                        WebradioPlayer.setPlayer(null);
                        GUIHandler.getInstance().resetComponents();
                        Logger.logError(ex.getMessage());
                    }
                }
            }
        }
    }

    private Station getStation() {
        if (Gui.getInstance().getStationsTable().getRowSorter() != null) {
            int selectedRow = Gui.getInstance().getStationsTable().getSelectedRow();
            int realRow = Gui.getInstance().getStationsTable().getRowSorter().convertRowIndexToModel(selectedRow);
            return Gui.getInstance().getStationsTableModel().getStationFromIndex(realRow);
        }
        return Gui.getInstance().getStationsTableModel().getStationFromIndex(Gui.getInstance().getStationsTable().getSelectedRow());
    }

    private void createPlayerAndUpdateGui(Factory playerFactory, Station s) {
        AbstractPlayer player = playerFactory.get(s);
        if (player == null) {
            throw new IllegalArgumentException("Station " + s + "did not contain a valid file extension");
        }
        WebradioPlayer.setPlayer(player);
        player.play();
        IcyInputStreamReader icyReader = parseIcyInputFromPlayer(player.getUrl(), player);
        Handler guiHandler = GUIHandler.getInstance();
        guiHandler.updateGui(s, reader, player);
        guiHandler.togglePlayButton();
    }

    private IcyInputStreamReader parseIcyInputFromPlayer(URL url, AbstractPlayer player) {
        reader = null;
        try {
            reader = new IcyInputStreamReader(url);
            Thread t = new Thread(reader);
            t.start();
            player.setIcyReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
