package de.dhbw.webradio.test.networkconnection;

import de.dhbw.webradio.networkconnection.NetworkConnectivityChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class NetworkConnectivityCheckerTest {

    @Before
    public void setUp() {

    }

    @Test
    public void getInstance() {
        assertTrue(NetworkConnectivityChecker.getInstance() instanceof NetworkConnectivityChecker);
    }

    /**
     * this test requires a valid network interface (NO active network connection)
     */
    @Test
    public void isNetworkInterfaceAvailable() {
        assertTrue(NetworkConnectivityChecker.getInstance().isNetworkInterfaceAvailable());
    }

    /**
     * this test requires an active network connection
     * NullPointerException because
     */
    @Test(expected = NullPointerException.class)
    public void checkNetworkConnectivity() {
        assertTrue(NetworkConnectivityChecker.getInstance().checkNetworkConnectivity());
    }
}
