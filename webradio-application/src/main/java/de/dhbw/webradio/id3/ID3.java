package de.dhbw.webradio.id3;

import java.io.File;

public interface ID3 extends MetainformationTag{
    public void writeId3ToFile(File f);
    public void setTitle(String title);
    public void setAlbum(String album);
    public void setGenre(int genre);
    public void setArtist(String artist);
    public void setYear(String year);
    public void setComment(String comment);
    public void setTrack(String track);
    public void writeId3Info();
    public String getTitle();
    public String getArtist();
    public String getAlbum();
    public int getGenre();
    public String getYear();
    public String getComment();
    public String getTrack();

}
