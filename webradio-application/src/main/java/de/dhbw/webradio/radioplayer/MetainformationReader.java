package de.dhbw.webradio.radioplayer;

import java.util.Map;

public interface MetainformationReader extends Runnable{
     Map<String, String> id3Values = null;

     String getActualMusicTitle();
    public String getActualTitle();

    public String getStationName();

    public String getDescription();

    public String getGenre();

    public String getStationUrl();

}
