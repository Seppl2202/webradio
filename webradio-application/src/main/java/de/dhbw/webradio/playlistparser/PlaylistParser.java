package de.dhbw.webradio.playlistparser;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.models.InformationObject;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.net.MalformedURLException;
import java.util.List;

public interface PlaylistParser {
    int redirectCount = 5;
    public List<InformationObject> parseURLFromString(String fileContent) throws NoURLTagFoundException, UnsupportedAudioFileException, MalformedURLException;
}
