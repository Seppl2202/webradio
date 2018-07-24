package de.dhbw.webradio.test.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.gui.PlayerControlPanel;
import de.dhbw.webradio.gui.StatusBar;
import de.dhbw.webradio.h2database.InitializeH2Database;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.MP3Player;
import de.dhbw.webradio.radioplayer.PlayerFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.*;

public class TogglePlayerListenerTest {


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        InitializeH2Database.initialiteDatabase();
    }

    @Test
    public void createPlayerAndUpdateGui() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, MalformedURLException, InvocationTargetException {
        PlayerControlPanel p = Gui.getInstance().getPlayerControlPanel();
        Field f = p.getClass().getDeclaredField("togglePlayerButton");
        f.setAccessible(true);
        JButton button = (JButton) f.get(p);
        ActionListener listener = button.getActionListeners()[0];
        Method m = listener.getClass().getDeclaredMethod("createPlayerAndUpdateGui", new Class[]{de.dhbw.webradio.radioplayer.Factory.class, Station.class});
        m.setAccessible(true);
        Station failStation = new Station("Test", new URL("http://google.de"));
        PlayerFactory pf = new PlayerFactory();
        expectedException.expect(IllegalArgumentException.class);
        m.invoke(listener, new Object[]{p, failStation});
        Station successStation = new Station("Test", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
        m.invoke(listener, new Object[]{p, successStation});
        assertTrue(WebradioPlayer.getPlayer() instanceof MP3Player);
        StatusBar statusBar = Gui.getInstance().getStatusBar();
        Field statusBarLabelReflection = statusBar.getClass().getDeclaredField("actualStationLabel");
        f.setAccessible(true);
        JLabel statusBarLabel = (JLabel) f.get(statusBar);
        assertEquals("SWR1 BW", statusBarLabel.getText());
        WebradioPlayer.getPlayer().stop();
    }
}
