package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.M3UInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectStreamDialog {
    private JList<M3UInfo> m3uStreams;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;

    public SelectStreamDialog(String title, String message, JList<M3UInfo> streamsToSelect) {
        m3uStreams = streamsToSelect;
        label = new JLabel(message);
        createAndDisplayDialog();
        dialog.setTitle(title);
    }

    private void createAndDisplayDialog() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});
        dialog = optionPane.createDialog("Bitte wählen Sie einen Stream");

    }

    private JPanel layoutComponents() {
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(m3uStreams, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements() {
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) m3uStreams.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

    }

    private void setupButtons() {
        okButton = new JButton("Auswählen");
        okButton.addActionListener(e -> handleOkButton(e));
        cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> handleCancelButton(e));
    }

    public void setOnOk(ActionListener event) {
        okEvent = event;
    }

    public void setOnClose(ActionListener event) {
        cancelEvent = event;
    }

    private void handleCancelButton(ActionEvent e) {
        if (cancelEvent != null) {
            cancelEvent.actionPerformed(e);
            hide();
        }
    }

    private void handleOkButton(ActionEvent e) {
        if (okEvent != null) {
            okEvent.actionPerformed(e);
            hide();
        }
    }

    private void hide() {
        dialog.setVisible(false);
    }

    public void show() {
        dialog.setVisible(true);
    }

    public M3UInfo getSelectedItem() {
        return m3uStreams.getSelectedValue();
    }
}
