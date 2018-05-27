/** Ralph-Uwe RYll 2011 free to copy and use
* Version 2.0
* eMail: Pilula2010@gmx.de
* über eine Rückmeldung würde ich mich freuen
**/

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Runnable
{
	private Thread runner = new Thread(this); //AbspielThread
	private static int BufferSize = 1024; // Anzahl der Daten, die aufeinmal an die Soundkarte geschickt werden.
    private static byte[]  buffer = new byte[BufferSize];
    private int gainPercent = 90;  //gibt die Lautstärke in Prozent an.  (0% = -80dB und 100% = 6dB) 
	private Boolean stop = false;
	private Boolean loopPlay = false;
	private File song = new File("");
	private URL url = null;
	private long songDuration = 0;
	private long actuallySongTime = 0; 
	private int sampleSizeInBits = 0;
	private long songLaenge = 0;
	private boolean reset = false;
	private Boolean isPlaying = false;
	private long resetKorrektur = 0;
	private boolean pause = false;
	private boolean mute = false;
	private int lautstaerke = gainPercent;
	private int bitRate = 0;
	private int audioFormatChannels;
	private float audioFormatFrameRate;
	private int audioFormatFrameSize;
	private float audioFormatSampleRate;
	private int audioFormatSampleSizeInBits;
	private String audioFormatEncoding;
	private Map<String,Object> audioFormatproperties;		
	private AudioInputStream ais = null;
	
	

	/**
	 * damit mp3-Dateien abgespielt werden können, muss das mp3plugin von Sun im Classpath stehen oder
	 * (zB. unter Eclipse) als Bibliothek eingetragen sein.  
	 * Das gleiche gilt für flac-Dateien.
	 * 
	 * Die Lautstärke "gainLevel" ist logarhytmisch von -80dB bis ca. 6dB. Daher ist die 
	 * prozentuale Lautstärkeregelung nicht ganz korrekt. 
	 */
	
	
	/**
	 * gibt die aktuelle Zeit an
	 */
	public long getActuallySongTime() {
		return actuallySongTime;
	}



	/**
	 * gibt zurück, ob das Musikstück wiederholt wird
	 */
	public boolean isLoopPlay(){
		return loopPlay;
	}
	
	/**
	 * startet eine Endlosschleife
	 *
	 */
	public void setLoopPlay(Boolean loop){
		loopPlay = loop;
	}
	
	/**
	 * stoppt die Wiedergabe
	 *
	 */
	public void stop()
	{
		stop = true;
		actuallySongTime = 0;
	}
	
	/**
	 * starten der Wiedergabe
	 *
	 */
	public void play()
	{
			stop = false;
			if (!runner.isAlive())
			{
				runner = new Thread(this);
				runner.start();
			}
	}
	
	/**
	 * gibt die aktuelle Lautstärke zurück
	 */
	public int getVolume(){
		return gainPercent;
	}
	
	/**
	 * Wert zwischen 0% und 100%
	 * @param volumen
	 */
	public void setVolumen(int volumen){
		if ((volumen <= 100) && (volumen >= 0)){
			gainPercent = volumen;
		}
		
	}
	
	private void setStream(){
		try {
			ais = AudioSystem.getAudioInputStream(url);
//			System.out.println("aktuelle Format : " + ais.getFormat());
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * der WiedergabeThread
	 */
	public void run() 
	
	{
		do{
		try {
			resetKorrektur = 0;
			AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, ais);
			AudioFormat audioFormat = in.getFormat();
			audioFormatChannels = audioFormat.getChannels();
			audioFormatFrameRate = audioFormat.getFrameRate();
			audioFormatFrameSize = audioFormat.getFrameSize();
			audioFormatSampleRate = audioFormat.getSampleRate();
			audioFormatSampleSizeInBits = audioFormat.getSampleSizeInBits();
			audioFormatEncoding = audioFormat.getEncoding().toString();
			audioFormatproperties = audioFormat.properties();
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
		    line.open(audioFormat);
		    FloatControl gainControl = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
		    line.start();
			
//			System.out.println("audioFormat.getChannels: " + audioFormat.getChannels());
//			System.out.println("audioFormat.getFrameSize: " + audioFormat.getFrameSize());
//			System.out.println("audioFormat.getSampleRate: " + audioFormat.getSampleRate());
//			System.out.println("audioFormat.getSampleSizeInBits: " + audioFormat.getSampleSizeInBits());
//			System.out.println("audioFormat.getEncoding: " + audioFormat.getEncoding());
//			System.out.println("audioFormat.properties: " + audioFormat.properties());					    
//			System.out.println("audioFormat.getChannels: " + audioFormat.getChannels());
//			System.out.println("durch:  audioFormat.getFrameRate: " + audioFormat.getFrameRate());
//			System.out.println("audioFormat.getFrameSize: " + audioFormat.getFrameSize());
//			System.out.println("audioFormat.getSampleRate: " + audioFormat.getSampleRate());
//			System.out.println("audioFormat.getSampleSizeInBits: " + audioFormat.getSampleSizeInBits());
//			System.out.println("audioFormat.getEncoding: " + audioFormat.getEncoding());
//			System.out.println("audioFormat.properties: " + audioFormat.properties());
//			System.out.println("in.available: " + in.available());
//			System.out.println("in.getFrameLength: " + in.getFrameLength());
//			System.out.println("audioFormat.getFrameRate: " + audioFormat.getFrameRate());
//			System.out.println("dies geteilt: in.FrameLength: " + in.getFrameLength());
//			System.out.println("in.getFormat: " + in.getFormat());
//			System.out.println("line.available: " + line.available());
//			System.out.println("line.getBufferSize: " + line.getBufferSize());
//			System.out.println("line.available: " + line.getFramePosition());
//			System.out.println("line.getFramePosition: " + line.getLevel());
//			System.out.println("line.getLongFramePosition: " + line.getLongFramePosition());
//			System.out.println("line.getMicrosecondPosition: " + line.getMicrosecondPosition());
//			System.out.println("line.getControls: " + line.getControls());
//			System.out.println("line.getFormat: " + line.getFormat());
//			System.out.println("line.getLineInfo: " + line.getLineInfo());

			
			
		    songLaenge = song.length();
		    sampleSizeInBits = audioFormat.getSampleSizeInBits();	
		    
		    if (in.getFrameLength() == -1){
		    	songDuration = songLaenge/sampleSizeInBits/1000; // berechnung für mp3
		    	bitRate = audioFormat.getSampleSizeInBits() * audioFormat.getFrameSize() * audioFormat.getChannels();
		    }
		    else{
		    	songDuration = in.getFrameLength()/(long)audioFormat.getFrameRate(); // berechnung für wav
		    	bitRate = (int) (in.available() / (in.getFrameLength()/ (long)audioFormat.getFrameRate())*8/1000);
		    }
		    
		    in.mark(in.available());
		    while ((true)&&(!stop)){
		    	isPlaying = true;
		    	int gainLevel= (int) ((int)gainControl.getMinimum()+((gainControl.getMaximum()-gainControl.getMinimum())/100*gainPercent));
		    	gainControl.setValue(gainLevel);
		    	if (!pause){
			    	int n = in.read(buffer, 0, buffer.length);
			    	if ((n < 0)||(stop)) {
			        	break;
			        }
			    	if (reset) {
			    		resetKorrektur =  line.getMicrosecondPosition()/1000;
			    		in.reset();
			    		reset = false;
			    	}
			    	actuallySongTime = line.getMicrosecondPosition()/1000000 - resetKorrektur;
			    	line.write(buffer, 0, n);
		    	}
		      }
		      line.drain();
		      line.close();
		      in.close();
		    
			} catch (IOException e) {
				System.out.println("Datei nicht gefunden" + e );
			} catch (LineUnavailableException e) {
				System.out.println("Soundkartenfehler");
			}
		}while (loopPlay && !stop);

		isPlaying = false;
	}

	private void songDatenSammeln(){
		try {
			resetKorrektur = 0;
			AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(url));
			AudioFormat audioFormat = in.getFormat();
			audioFormatChannels = audioFormat.getChannels();
			audioFormatFrameRate = audioFormat.getFrameRate();
			audioFormatFrameSize = audioFormat.getFrameSize();
			audioFormatSampleRate = audioFormat.getSampleRate();
			audioFormatSampleSizeInBits = audioFormat.getSampleSizeInBits();
			audioFormatEncoding = audioFormat.getEncoding().toString();
			audioFormatproperties = audioFormat.properties();
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
		    line.open(audioFormat);
		    line.start();
		    songLaenge = song.length();
		    sampleSizeInBits = audioFormat.getSampleSizeInBits();	
		    
		    if (in.getFrameLength() == -1){
		    	songDuration = songLaenge/sampleSizeInBits/1000; // berechnung für mp3
		    	bitRate = audioFormat.getSampleSizeInBits() * audioFormat.getFrameSize() * audioFormat.getChannels();
		    }
		    else{
		    	songDuration = in.getFrameLength()/(long)audioFormat.getFrameRate(); // berechnung für wav
		    	bitRate = (int) (in.available() / (in.getFrameLength()/ (long)audioFormat.getFrameRate())*8/1000);
		    }
		      line.close();
		      in.close();
			} catch (UnsupportedAudioFileException e) {
			    System.out.println("nicht unterstütztes Format");
			} catch (IOException e) {
				System.out.println("Datei nicht gefunden" + e );
			} catch (LineUnavailableException e) {
				System.out.println("Soundkartenfehler");
			}
			BufferSize = bitRate/8*1000/10; // Buffergröße auf Anzahl der benötigten Bytes pro 1/10s
		    buffer = new byte[BufferSize];
	}
	
	
	
	/**
	 * Name und Pfad der Sounddatei
	 * @param song
	 * @throws MalformedURLException 
	 */
	
	public void setSong(String song) throws MalformedURLException {
		this.song = new File(song);
		this.url = new URL("file:/" + this.song);
		setStream();
		songDatenSammeln();
	}

	/** 
	 * Name und Pfad der Sounddatei
	 * @return
	 */
	public File getSong() {
		return song;
	}

	/**
	 * die Gesamtlänge des Musikstücks
	 * @return
	 */
	public long getSongDuration() {
		return songDuration;
	}

	/**
	 * setzt den aktuellen Titel zurück
	 * @param reset
	 */

	public void reset(boolean reset) {
		this.reset = reset;
	}
	/**
	 * gibt zurück, ob gerade ein Titel abgespielt wird
	 * @return
	 */
	public Boolean isPlaying() {
		return isPlaying;
	}
	

	/**
	 * Titel pausieren
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}


	/**
	 * gibt zurück, ob der Titel pausiert
	 * @return
	 */
	public boolean isPause() {
		return pause;
	}


	/**
	 * schaltet die Wiedergabe stumm
	 * @param mute
	 */
	public void setMute(boolean mute) {
		if ((mute) &&(!this.mute)){
			lautstaerke = this.getVolume();
			this.setVolumen(0);
		}else{
			this.setVolumen(lautstaerke);
		}
		this.mute = mute;
	}


	/**
	 * gibt zurück, ob die Wiedergabe stumm geschaltet ist
	 * @return
	 */
	public boolean isMute() {
		return mute;
	}


	public void setUrl(URL url) {
		this.song = new File("");
		this.url = url;
		if (url != null){
			setStream();
		}
	}



	public URL getUrl() {
		return url;
	}

	public int getAudioFormatChannels() {
		return audioFormatChannels;
	}

	public float getAudioFormatFrameRate() {
		return audioFormatFrameRate;
	}


	public int getAudioFormatFrameSize() {
		return audioFormatFrameSize;
	}

	public float getAudioFormatSampleRate() {
		return audioFormatSampleRate;
	}

	public int getAudioFormatSampleSizeInBits() {
		return audioFormatSampleSizeInBits;
	}


	public String getAudioFormatEncoding() {
		return audioFormatEncoding;
	}

	public Map<String,Object> getAudioFormatproperties() {
		return audioFormatproperties;
	}

	public boolean isNetConnection() {
		if (song.length()== 0 && !(url == null)){
			return true;
		}
		else {
			return false;
		}
	}


	public int getBitRate() {
		return bitRate;
	}

	


}