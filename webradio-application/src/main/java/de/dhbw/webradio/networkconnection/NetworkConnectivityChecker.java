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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NetworkConnectivityChecker {
    private static NetworkConnectivityChecker networkConnectivityChecker = new NetworkConnectivityChecker();
    private int callCount = 0;
    private JDialog checkInfoDialog;
    private boolean errorDialogShown = false;

    private NetworkConnectivityChecker() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                checkNetworkConnectivity();
            }
        }, 1, 10, TimeUnit.SECONDS);

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
            showInitialCheckDialog();
            Logger.logInfo("checking network connection for the " + (callCount + 1) + " time");
            callCount++;
            socket = new Socket("www.google.com", 80);
            netAccess = socket.isConnected();
            socket.close();
        } catch (ConnectException e) {
            handleCheckResult(netAccess);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            handleCheckResult(netAccess);
            e.printStackTrace();
        }
        handleCheckResult(netAccess);
        return netAccess;
    }

    public void handleCheckResult(boolean netAccess) {
        if (!netAccess) {
            if (!errorDialogShown) {
                checkInfoDialog.dispose();
                showErrorDialog();
                errorDialogShown = true;
                GUIHandler.getInstance().toggleControls(false);
            }
        } else {
            checkInfoDialog.dispose();
            errorDialogShown = false;
            GUIHandler.getInstance().toggleControls(true);
        }
    }

    private void showInitialCheckDialog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (callCount == 0) {
                    checkInfoDialog = new JDialog();
                    checkInfoDialog.setLocationRelativeTo(Gui.getInstance());
                    checkInfoDialog.setTitle("Prüfung");
                    checkInfoDialog.add(new JLabel("Prüfe Netzwerkverbindung..."));
                    checkInfoDialog.setModal(true);
                    checkInfoDialog.setSize(300, 100);
                    checkInfoDialog.setVisible(true);
                    checkInfoDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    checkInfoDialog.pack();
                }
            }
        }).start();

    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(Gui.getInstance(), "Es konnte keine Netzwerkverbdingung gefunden werden\r\nSie können erst Sender wiedergeben und eine Sofortaufnahme starten, wenn eine Verbindung verfügbar ist" +
                "\r\nSobald eine Verbindung besteht, werden die Steuerelemente freigegeben", "Netzwerkfehler", JOptionPane.ERROR_MESSAGE);
    }
}
