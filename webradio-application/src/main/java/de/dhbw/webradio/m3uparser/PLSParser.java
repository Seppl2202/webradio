package de.dhbw.webradio.m3uparser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PLSParser {
    private List<String> plsEntries;

    public PLSParser() {

    }

    public List<String> parsePLS(URL fileUrl) {
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
                plsEntries.add(keyValue[1]);
            }
        }
        return plsEntries;
    }
}
