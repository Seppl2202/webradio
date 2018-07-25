package de.dhbw.webradio.m3uparser;

import de.dhbw.webradio.models.InformationObject;
import de.dhbw.webradio.models.PLSInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PLSParser {
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
        if (!(splittedLines[0].contains("playlist"))) {
            throw new IllegalArgumentException("File did not contain a valid PLS syntax");
        }
        for (String s : splittedLines
                ) {
            String[] keyValue = s.split("=");
            if (keyValue[0].contains("File")) {
                plsEntries.add(new PLSInfo(keyValue[1]));
            }
        }
        return plsEntries;
    }
}
