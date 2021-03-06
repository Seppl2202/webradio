package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.models.ScheduledRecord;

import java.io.FilterInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IcyInputStreamReader extends FilterInputStream implements Runnable, MetainformationReader {
    private static IcyInputStreamReader reader;
    private URLConnection connection;
    private int metaDataInterval = -1;
    private int toRead = -1;
    private Map<String, String> id3Values;
    private boolean interrupted;

    /**
     * instanciates the icyReader class.
     * see @https://cast.readme.io/docs/icy for detailed informatoin about ICY and its specification
     *
     * @param icyURL the mp3 stream url
     * @throws IOException if reading the icy stream fails
     */

    public IcyInputStreamReader(URL icyURL) throws IOException {
        super(null);
        connection = icyURL.openConnection();
        connection.addRequestProperty("Icy-MetaData", "1");
        in = connection.getInputStream();
        id3Values = new HashMap<String, String>();
    }


    protected void readHeader() {
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if ("icy-metaint".equalsIgnoreCase(header.getKey())) {
                metaDataInterval = Integer.parseInt(header.getValue().get(0));
                toRead = metaDataInterval;
            }
            if (header.getKey() != null && header.getKey().toLowerCase().startsWith("icy-")) {
                for (String s : header.getValue()) {
                    fireNewIcyMetaData(header.getKey(), s);
                }
            }
        }
    }

    @Override
    public int read() throws IOException {
        if (toRead == 0) {
            readIcyInfo();
            toRead = metaDataInterval;
        }

        toRead--;
        if (toRead < 0) {
            toRead = -1;
        }
        int val = in.read();
        return val;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int size = off + len;
        for (int i = off; i < size; i++) {
            int d = read();
            if (d == -1) {
                return i - off;
            } else {
                b[i] = (byte) d;
            }
        }
        return len;
    }

    private void readIcyInfo() throws IOException {
        int value = in.read();
        int size = value * 16;
        StringBuffer s = new StringBuffer();
        while (size-- > 0) {
            s.append((char) in.read());
        }

        if (s.length() > 0) {
            fireNewIcyMetaData("Length:" + s.length() + ",", s.toString());
        }
    }

    private void fireNewIcyMetaData(String key, String value) {
        //to do: log received metadata
        //add header key-values to map
        Logger.logInfo("New icy data: " + key + value);
        if (!(key.contains("Length"))) {
            id3Values.put(key, value);
        } else {
            /*
                split the title information and add it to map. key=StreamTitle
             */
            String mapArray[] = value.split("=");
            String mapKey = mapArray[0];
            String mapValue = mapArray[1].replaceAll("'", "").replaceAll(";", "").replaceAll("StreamUrl", "");
            id3Values.put(mapKey, mapValue);
            if (icyInfoContainsTitleInfo()) {
                id3Values.put("titleInfo", mapValue);
            }
            GUIHandler.getInstance().notifyNewIcyData(this);
        }
    }

    private boolean icyInfoContainsTitleInfo() {
        String title = id3Values.get("StreamTitle");
        String icy = id3Values.get("icy-name");

        String[] titleSplit = title.split("\\s");
        String[] icySplit = icy.split("\\s");

        for (String a : titleSplit) {
            StringBuilder abuilder = new StringBuilder();
            abuilder.append(a);
            for (String b : icySplit) {
                StringBuilder builder = new StringBuilder();
                builder.append(b);
                //improve contains, not working reliable
                if (builder.toString().toLowerCase().contains(abuilder.toString().toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }


    public String getActualTitle() {
        return id3Values.get("StreamTitle");
    }

    public String getStationName() {
        return id3Values.get("icy-name");
    }

    public String getDescription() {
        return id3Values.get("icy-description");
    }

    public String getGenre() {
        return id3Values.get("icy-genre");
    }

    public String getStationUrl() {
        return id3Values.get("icy-url");
    }

    @Override
    public boolean matchesScheduledRecord(ScheduledRecord scheduledRecord) {
            String artist, title;
            String musicTitle = getActualMusicTitle();
            if (musicTitle.contains("\\")) {
                artist = musicTitle.split("\\\\")[0].trim();
                title = musicTitle.split("\\\\")[1].trim();
                if ((scheduledRecord.getActor().equalsIgnoreCase(artist) && scheduledRecord.getTitle().equalsIgnoreCase(title)) || (scheduledRecord.getActor().equalsIgnoreCase(title) && scheduledRecord.getTitle().equalsIgnoreCase(artist))) {
                    return true;
                }
            }
            if (musicTitle.contains("/")) {
                artist = musicTitle.split("/")[0].trim();
                title = musicTitle.split("/")[1].trim();
                if ((scheduledRecord.getActor().trim().equalsIgnoreCase(artist) && scheduledRecord.getTitle().trim().equalsIgnoreCase(title)) || (scheduledRecord.getActor().trim().equalsIgnoreCase(title) && scheduledRecord.getTitle().trim().equalsIgnoreCase(artist))) {
                    return true;
                }
            }
        return false;
    }

    public String getActualMusicTitle() {
        Optional<String> titleOptional = Optional.ofNullable(id3Values.get("titleInfo"));
        return titleOptional.orElse("Keine Informationen verfügbar");
    }

    public void run() {
        readHeader();
        try {
            int i = 0;
            while ((i = read()) != -1) {
                if (interrupted) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
}
