package com.mirosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Rules control elements on form and take/give data from/to its elements to other classes.
 */
public class Viewer  {

    private JFrame mainFrame;
    private String message;
    private String name;
    private JTextArea jTextArea;

    /**
     * Initialize fields with new values.
     */
    public Viewer() {
        initializeFields();
        setWindowProperties();
        addElementsOnWindow();
    }

    private void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    private JTextArea getjTextArea() {
        return this.jTextArea;
    }

    private void initializeFields() {
        setMainFrame(new JFrame());
        setjTextArea(new JTextArea());
        setMessage("");
        setName("");
    }

    private void addElementsOnWindow() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(getjTextArea());
        getjTextArea().setBounds(0, 0, 800, 500);

        getjTextArea().setEditable(false);

        getjTextArea().setFont(new Font("System", 0, 16));

        TextField textField = new TextField();
        textField.setBounds(0, 501, 650, 30);
        textField.setFont(new Font("System", Font.PLAIN, 16));

        JButton jButton = new JButton("Отправить");
        jButton.setBounds(651, 501, 149, 30);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                setMessage(text);
                textField.setText("");
            }
        });

        panel.add(textField);
        panel.add(jButton);

        getMainFrame().setContentPane(panel);
    }

    private void setWindowProperties() {

        JFrame frame = getMainFrame();
        frame.setTitle("miChat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 800, 570);

    }

    public void ShowMessageInConsole(String message) {
        System.out.print(message);
    }

    public void ShowMessageLineInConsole(String message) {
        System.out.println(message);
    }

    private void setMainFrame(JFrame jFrame) {
        this.mainFrame = jFrame;
    }

    private JFrame getMainFrame() {
        return this.mainFrame;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getName() {
        return this.name;
    }

    public void addToTextArea(String text) {
        getjTextArea().append("\n" + text);
    }

    /**
     * Shows the main client window.
     */
    public void showWidndow() {
        getMainFrame().setVisible(true);
    }

    /**
     * Shows dialog for choose name.
     * @return name that user entered.
     */
    public String showNameDialog() {
        String s;

        s = JOptionPane.showInputDialog(getMainFrame(), "Введите ваше имя", "Добро пожаловать в miChat!", JOptionPane.QUESTION_MESSAGE);

        return s;
    }

}