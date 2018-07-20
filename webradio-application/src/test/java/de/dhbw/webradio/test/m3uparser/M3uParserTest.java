package de.dhbw.webradio.test.m3uparser;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.gui.SelectMultipleItemsDialog;
import de.dhbw.webradio.m3uparser.M3uParser;
import de.dhbw.webradio.models.M3UInfo;
import de.dhbw.webradio.radioplayer.PlayerFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class M3uParserTest {

    File success = new File("C:\\Users\\priva\\IdeaProjects\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\testm3uparsing_success.m3u");
    File fail = new File("C:\\Users\\priva\\IdeaProjects\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\testm3uparsing_fail.m3u");
    M3uParser m3uParser = new M3uParser();
    String expectedText = "#EXTM3U\r\n" +
            "#EXTINF:-1,SWR1 Baden-Württemberg\r\n" +
            "http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3\r\n";

    @Test
    public void parseFileToString() {
        String parsedText = null;

        try {
            assertEquals(expectedText, m3uParser.parseFileToString(success));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseUrlFromString() {
        List<M3UInfo> info = new ArrayList<>();
        try {
            info = m3uParser.parseUrlFromString(m3uParser.parseFileToString(success));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (NoURLTagFoundException nufe) {
            nufe.printStackTrace();
        }
        //test m3u with valid EXTINF tag and corresponding URL
        assertEquals("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3", info.get(0).getUrl().toString());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseURLFromString() throws IOException, UnsupportedAudioFileException, NoURLTagFoundException {
        List<M3UInfo> info = new ArrayList<>();
        expectedException.expect(NoURLTagFoundException.class);
        info = m3uParser.parseUrlFromString(m3uParser.parseFileToString(fail));
    }

    @Test
    public void parseFileFromUrlToString() throws NoSuchFieldException, IllegalAccessException, IOException {
        URL url = null;
        String s = null;
        try {
            url = new URL("http://mp3-live.swr.de/swr1bw_m.m3u");
            s = m3uParser.parseFileFromUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        assertEquals(expectedText, s);
    }

    @Test
    public void testM3uInfo() {
        String s = null;
        try {
            URL url = new URL("http://mp3-live.swr.de/swr1bw_m.m3u");
            s = m3uParser.parseFileFromUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        List<M3UInfo> info = new ArrayList<>();
        try {
            info = m3uParser.parseUrlFromString(s);
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (NoURLTagFoundException nufe) {
            nufe.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //test m3u media name parsing
        assertEquals("SWR1 Baden-Württemberg", info.get(0).getTitleInfo());
    }

    @Test
    public void testMultipleM3uInfo() throws MalformedURLException {
        File f = new File("C:\\Users\\priva\\IdeaProjects\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\bigfmWebradio.m3u8");
        String parsedFile = null;
        List<M3UInfo> info = null;
        try {
            parsedFile = m3uParser.parseFileToString(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            info = m3uParser.parseUrlFromString(parsedFile);
        } catch (UnsupportedAudioFileException e) {
        } catch (NoURLTagFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertEquals(23, info.size());
        //test multiple stream selection window
        JList list = new JList<>(info.toArray());
        SelectMultipleItemsDialog dialog = new SelectMultipleItemsDialog("Bitte wählen Sie einen Stream aus", "Bitte wählen:", list, ListSelectionModel.SINGLE_SELECTION);
        M3UInfo testInfo = new M3UInfo(new URL("http://streams.bigfm.de/bigfm-deutschland-128-aac?usid=0-0-H-A-D-30"), "bigFM DEUTSCHLAND");
        dialog.setOnOk(e -> assertEquals(testInfo, (M3UInfo) dialog.getSelectedItem().get(0)));
        dialog.show();
    }

    @Test
    /**
     * Tests the multiple stream selection
     */
    public void testMultipleStreamPlayerCreation() {
        File f = new File("C:\\Users\\priva\\IdeaProjects\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\testMultiple.m3u");
        String parsedFile = null;
        List<M3UInfo> info = null;
        M3UInfo selected = null;
        try {
            parsedFile = m3uParser.parseFileToString(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            info = m3uParser.parseUrlFromString(parsedFile);
        } catch (UnsupportedAudioFileException e) {
        } catch (NoURLTagFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        PlayerFactory playerFactory = new PlayerFactory();
        try {
            selected = playerFactory.getUserSelection(parsedFile, m3uParser);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (NoURLTagFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertEquals("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3", selected.getUrl().toString());
        assertEquals("SWR1 Baden-Württemberg 2", selected.getTitleInfo());
    }

    @Test
    public void testSM3UParsing() {
        File f = new File("C:\\Users\\priva\\IdeaProjects\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\youfm_2.m3u");
        String parsedFile = null;
        List<M3UInfo> info = null;
        M3UInfo selected = null;
        try {
            parsedFile = m3uParser.parseFileToString(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            info = m3uParser.parseUrlFromString(parsedFile);
        } catch (UnsupportedAudioFileException e) {
        } catch (NoURLTagFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        PlayerFactory playerFactory = new PlayerFactory();
        try {
            selected = playerFactory.getUserSelection(parsedFile, m3uParser);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (NoURLTagFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertEquals(1, info.size());
        assertEquals("http://hr-youfm-live.cast.addradio.de/hr/youfm/live/mp3/128/stream.mp3", info.get(0).getUrl().toString());
        assertEquals("Nicht verfügbar", info.get(0).getTitleInfo());
    }

    @Test
    public void parseUrlFromEM3U() throws NoSuchMethodException, IllegalAccessException {
        String fail = "fksdjghlsdkgjhsd";
        Method m = m3uParser.getClass().getDeclaredMethod("parseUrlFromEM3UString", String.class);
        m.setAccessible(true);
        try {
            m.invoke(m3uParser, fail);
            //check if the underlying method throws an exception
            expectedException.expect(InvocationTargetException.class);
        } catch (InvocationTargetException e) {
            //check if the underlying method throws the correct exception
            assertEquals("File did not contain a valid extended M3U syntax", e.getCause().getMessage());
        }
    }
}

