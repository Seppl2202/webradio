package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
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
        if (Gui.getInstance().getStationsTable().getRowSorter() != null) {
            int selectedRow = Gui.getInstance().getStationsTable().getSelectedRow();
            int realRow = Gui.getInstance().getStationsTable().getRowSorter().convertRowIndexToModel(selectedRow);
            s = Gui.getInstance().getStationsTableModel().getStationFromIndex(realRow);
        } else {
            s = Gui.getInstance().getStationsTableModel().getStationFromIndex(Gui.getInstance().getStationsTable().getSelectedRow());
        }
        AbstractPlayer actualPlayer = WebradioPlayer.getPlayer();
        //if no player was created yet, directly create a new one
        if (actualPlayer == null) {
            createPlayer(playerFactory, s);
        }
        if (actualPlayer.isPlaying()) {
            actualPlayer.stop();
            actualPlayer.getIcyReader().setInterrupted(true);
        } else {
            if (!(s == null)) {
                try {
                    if (!s.isURLValid()) {
                        throw new MalformedURLException("URL: " + s.getStationURL() + "did not returned status 200");
                    }
                    createPlayer(playerFactory, s);
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void createPlayer(PlayerFactory playerFactory, Station s) {
        AbstractPlayer player = playerFactory.get(s);
        if (player == null) {
            throw new IllegalArgumentException("Station " + s + "did not contain a valid file extension");
        }
        WebradioPlayer.setPlayer(player);
        player.play();
        IcyInputStreamReader icyReader = parseIcyInputFromPlayer(player.getUrl(), player);
        GUIHandler.getInstance().updateGui(s, reader, player);
        GUIHandler.getInstance().togglePlayButton();
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
