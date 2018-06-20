package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;

public interface Handler {
    public void notifyNewIcyData(IcyInputStreamReader reader);

    public void updateGui(Station station, IcyInputStreamReader icyReader, AbstractPlayer player);

    public void updatePlayerDetails(AbstractPlayer player);

    public void mutePlayer();

    public void updatePlayerVolume(AbstractPlayer player);

    public void resetComponents();

    public void togglePlayButton();

    public void updateAudioDetails(AbstractPlayer player);

    public void toggleControls(boolean statusToSet);
}
