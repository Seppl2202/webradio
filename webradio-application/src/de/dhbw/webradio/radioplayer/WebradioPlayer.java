package de.dhbw.webradio.radioplayer;


import java.net.MalformedURLException;
import java.net.URL;

public class WebradioPlayer {
    public static void main(String[] args) {
        SoundPlayer player = new SoundPlayer();
       try {
       URL station = new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3");
        player.setUrl(station);
        player.setVolume(80);
        player.play();
       } catch (MalformedURLException mue) {
           mue.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
