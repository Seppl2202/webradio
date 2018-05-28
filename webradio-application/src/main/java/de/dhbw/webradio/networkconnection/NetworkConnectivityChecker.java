package de.dhbw.webradio.networkconnection;

import de.dhbw.webradio.gui.GUIHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.*;

public class NetworkConnectivityChecker {
    private static NetworkConnectivityChecker networkConnectivityChecker = new NetworkConnectivityChecker();

    private NetworkConnectivityChecker() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable<Object>() {
            public Object call() throws Exception {
                checkNetworkConnectivity();
                return "Executed";
            }
        }, 5, TimeUnit.SECONDS);

    }

    public static NetworkConnectivityChecker getInstance() {
        return networkConnectivityChecker;
    }

    public boolean isNetworkInterfaceAvailable() {
        boolean isAvailable = false;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            List<NetworkInterface> availableInterfaces = new ArrayList<>();
            while (interfaces.hasMoreElements()) {
                NetworkInterface nic = interfaces.nextElement();
                if (nic.isUp()) {
                    isAvailable = true;
                    availableInterfaces.add(nic);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return isAvailable;
    }

    public boolean checkNetworkConnectivity() {
        Socket socket = null;
        boolean netAccess = false;
        try {
            socket = new Socket("www.google.com", 80);
            netAccess = socket.isConnected();
            socket.close();
        } catch (ConnectException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        if (!netAccess) {
            System.err.println("toggled button");
            GUIHandler.getInstance().toggleControls(false);
        } else {
            GUIHandler.getInstance().toggleControls(true);
        }
        return netAccess;
    }
}
