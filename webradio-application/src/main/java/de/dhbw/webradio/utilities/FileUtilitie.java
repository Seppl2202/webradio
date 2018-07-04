package de.dhbw.webradio.utilities;

import de.dhbw.webradio.WebradioPlayer;

import java.util.Optional;

public class FileUtilitie {
    public static String generateFileNameForRecording() {
        return WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle().replaceAll("[^a-zA-Z0-9]", "");
    }
}