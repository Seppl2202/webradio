package de.dhbw.webradio.networkconnection;

import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.logger.Logger;

import javax.swing.*;
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
    private int callCount = 0;
    private JDialog checkInfoDialog;

    private NetworkConnectivityChecker() {
        showInitialCheckDialog();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
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
            Logger.logInfo("checking network connection for the " + (callCount + 1) + " time");
            callCount++;
            socket = new Socket("www.google.com", 80);
            netAccess = socket.isConnected();
            socket.close();
        } catch (ConnectException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!netAccess) {
            checkInfoDialog.dispose();
            showErrorDialog();
            GUIHandler.getInstance().toggleControls(false);
        } else {
            checkInfoDialog.dispose();
            GUIHandler.getInstance().toggleControls(true);
        }
        return netAccess;
    }

    private void showInitialCheckDialog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkInfoDialog = new JDialog();
                checkInfoDialog.setLocationRelativeTo(Gui.getInstance());
                checkInfoDialog.setTitle("Prüfung");
                checkInfoDialog.add(new JLabel("Prüfe Netzwerkverbindung..."));
                checkInfoDialog.setModal(true);
                checkInfoDialog.setSize(300,100);
                checkInfoDialog.setVisible(true);
                checkInfoDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                checkInfoDialog.pack();
            }
        }).start();

    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(Gui.getInstance(), "Es konnte keine Netzwerkverbdingung gefunden werden\r\nSie können erst Sender wiedergeben und eine Sofortaufnahme starten, wenn eine Verbindung verfügbar ist", "Netzwerkfehler", JOptionPane.ERROR_MESSAGE);
    }
}
