package de.dhbw.webradio.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectMultipleItemsDialog<T> {
    private JList<T> listOfElements;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;

    /**
     *
     * @param title the title to be displayed
     * @param message the message above the list
     * @param objectsToSelect a JList component of elements
     * @param listSelectionMode the desired selection type of JList: 0: single, 2: multiple, 1: interval
     *                          see @ https://docs.oracle.com/javase/10/docs/api/javax/swing/ListSelectionModel.html
     */
    public SelectMultipleItemsDialog(String title, String message, JList<T> objectsToSelect, int listSelectionMode) {
        listOfElements = objectsToSelect;
        objectsToSelect.setSelectionMode(listSelectionMode);
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
        panel.add(listOfElements, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements() {
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) listOfElements.getCellRenderer();
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

    /**
     *
     * @return a list of selected item. in case of single selection, list size = 1
     */
    public List<T> getSelectedItem() {
        return listOfElements.getSelectedValuesList();
    }
}
