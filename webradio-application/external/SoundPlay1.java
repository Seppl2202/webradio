/** Ralph-Uwe RYll 2012 free to copy and use
* Version 1.0
* eMail: Pilula2010@gmx.de
* über eine Rückmeldung würde ich mich freuen
**/

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class SoundPlay1 {
	
	private static Sound song = new Sound();
	private  static URL station = null;

	public static void main(String[] args) {
		try {
			station = new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3");
			song.setUrl(station);
			song.setVolumen(70);
			song.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
