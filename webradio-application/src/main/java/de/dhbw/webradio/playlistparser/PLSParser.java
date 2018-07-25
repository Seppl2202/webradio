package de.dhbw.webradio.playlistparser;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.models.InformationObject;
import de.dhbw.webradio.models.PLSInfo;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PLSParser implements PlaylistParser {
    private List<InformationObject> plsEntries;

    public PLSParser() {

    }

    public List<InformationObject> parsePLS(URL fileUrl) {
        M3uParser p = new M3uParser();
        plsEntries = new ArrayList<>();
        String fileContent = null;
        try {
            fileContent = p.parseFileFromUrlToString(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] splittedLines = fileContent.split("\r\n");
        checkForValidTag(splittedLines[0]);
        splitStringInLines(splittedLines);
        return plsEntries;
    }

    private void splitStringInLines(String[] splittedLines) {
        for (String s : splittedLines
                ) {
            String[] keyValue = s.split("=");
            if (keyValue[0].contains("File")) {
                plsEntries.add(new PLSInfo(keyValue[1]));
            }
        }
    }

    private void checkForValidTag(String splittedLine) {
        if (!(splittedLine.contains("playlist"))) {
            throw new IllegalArgumentException("File did not contain a valid PLS syntax");
        }
    }

    @Override
    public List<InformationObject> parseURLFromString(String fileContent) throws NoURLTagFoundException, UnsupportedAudioFileException, MalformedURLException {
        return parsePLS(new URL(fileContent));
    }
}
