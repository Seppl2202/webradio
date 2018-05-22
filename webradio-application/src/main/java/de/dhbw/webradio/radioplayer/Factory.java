package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.models.Station;

public interface Factory {
    public AbstractPlayer get(Station s);
}
