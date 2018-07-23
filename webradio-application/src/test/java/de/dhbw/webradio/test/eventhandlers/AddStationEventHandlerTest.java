package de.dhbw.webradio.test.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.AddStationWindow;
import de.dhbw.webradio.h2database.H2DatabaseConnector;
import de.dhbw.webradio.h2database.InitializeH2Database;
import de.dhbw.webradio.models.Station;
import org.junit.After;
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
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddStationEventHandlerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    Map<JLabel, JTextField> inputs;
    ActionListener listener;
    AddStationWindow window;
    ArrayList<Station> stationsToDelete;

    @Before
    public void setUp() throws IllegalAccessException, NoSuchFieldException, InstantiationException, SQLException, ClassNotFoundException {
        inputs = new HashMap<JLabel, JTextField>();
        window = new AddStationWindow();
        stationsToDelete = new ArrayList<>();
        InitializeH2Database.initialiteDatabase();

    }

    @Test
    /**
     * Checks if a valid station is added and if invalid stations are rejected with an exception.
     * Test also assures invalid stations are not persisted in the database and the temporary stations list.
     * Please confirm all dialogs with okey to make the test work.
     * Exception stack trace is printed.
     */
    public void actionPerformed() throws IllegalAccessException, NoSuchFieldException, MalformedURLException {
        Field f = window.getClass().getDeclaredField("inputElements");
        f.setAccessible(true);
        LinkedHashMap<JLabel, JTextField> inputs = (LinkedHashMap<JLabel, JTextField>) f.get(window);
        Field f2 = window.getClass().getDeclaredField("save");
        f2.setAccessible(true);
        JButton saveButton = (JButton) f2.get(window);
        inputs.get(window.getInputLabels().get(0)).setText("Testsender");
        inputs.get((window.getInputLabels().get(1))).setText("http://mp3.ffh.de/radioffh/hqlivestream.mp3");
        stationsToDelete.add(new Station("Testsender", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3")));
        f.set(window, inputs);
        saveButton.doClick();
        assertTrue(WebradioPlayer.getStationList().contains(new Station("Testsender", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"))));
        inputs.get(window.getInputLabels().get(0)).setText("Testsender");
        inputs.get(window.getInputLabels().get(1)).setText("adsdasfdf");
        f.set(window, inputs);
        try {
            saveButton.doClick();
            assertTrue(!WebradioPlayer.getStationList().contains(new Station("Testsender", new URL("adsdasfdf"))));
            Field tempStationsList = WebradioPlayer.class.getDeclaredField("stationList");
            tempStationsList.setAccessible(true);
            List<Station> stationsListWebradioPlayer = (List<Station>) tempStationsList.get(WebradioPlayer.class);
            assertTrue(!stationsListWebradioPlayer.contains(new Station("Testsender", new URL("adsdasfdf"))));
        } catch (Exception e) {
            assertEquals("no protocol: adsdasfdf", e.getMessage());
        }
    }

    @Test
    public void saveStation() throws NoSuchFieldException, IllegalAccessException, MalformedURLException, NoSuchMethodException, InvocationTargetException {
        Field f = window.getClass().getDeclaredField("inputElements");
        f.setAccessible(true);
        LinkedHashMap<JLabel, JTextField> inputs = (LinkedHashMap<JLabel, JTextField>) f.get(window);
        Field f2 = window.getClass().getDeclaredField("save");
        f2.setAccessible(true);
        JButton saveButton = (JButton) f2.get(window);
        Station s = new Station("Testsender", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
        stationsToDelete.add(s);
        inputs.get(window.getInputLabels().get(0)).setText("Testsender");
        inputs.get((window.getInputLabels().get(1))).setText("http://mp3.ffh.de/radioffh/hqlivestream.mp3");
        ActionListener listener = saveButton.getActionListeners()[0];
        Method m = listener.getClass().getDeclaredMethod("saveStation");
        m.setAccessible(true);
        m.invoke(listener);
        assertTrue(WebradioPlayer.getStationList().contains(s));
    }

    @After
    public void tearDown() {
        stationsToDelete.forEach(s -> H2DatabaseConnector.getInstance().deleteStation(s));
    }
}
