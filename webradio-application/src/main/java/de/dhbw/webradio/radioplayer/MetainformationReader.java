package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.models.ScheduledRecord;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MetainformationReader extends Runnable{
     Map<String, String> id3Values = null;

     String getActualMusicTitle();
    public String getActualTitle();

    public String getStationName();

    public String getDescription();

    public String getGenre();

    public String getStationUrl();

    public boolean matchesScheduledRecord(ScheduledRecord scheduledRecord);

}
