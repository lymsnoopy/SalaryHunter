import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class SearchFrame extends JFrame {
    private JTextField companyNameField;
    private JTextField stateAbbrField;
    private JTextField areaField;
    private JTextField industryField;
    private JTextField positionField;

    private String companyName;
    private String stateAbbr;
    private String area;
    private String industry;
    private String position;

    private controller controller;

    public SearchFrame(controller controller) {
        this.controller = controller;
        setTitle("Function Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Create username label and password label
        JLabel companyNameLabel = new JLabel("Company Name: ");
        companyNameField = new JTextField();

        JLabel stateAbbrLabel = new JLabel("State Abrreviation: ");
        stateAbbrField = new JTextField();

        JLabel arealLabel = new JLabel("Area: ");
        JComboBox<String> areaComboBox = new JComboBox<>();

        JLabel industryLabel = new JLabel("Industry: ");
        industryField = new JTextField();

        JLabel positionLabel = new JLabel("Position: ");
        positionField = new JTextField();

        // Create login and register button
        JButton searchButton = new JButton("Start Search");
        JButton viewRecordButton = new JButton("View History Records");

        // Add listener for login button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                companyName = companyNameField.getText();
                stateAbbr = stateAbbrField.getText();
                area = (String) areaComboBox.getSelectedItem();
                industry = industryField.getText();
                position = 

                try {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        boolean checkUser = controller.checkUserExist(username);
                        if (!checkUser) {
                            JOptionPane.showMessageDialog(null, "User not exists. Please register.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            if (controller.checkPassword(username, password)) {
                                JOptionPane.showMessageDialog(null, "Login Successfully!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                            // main page?
                            } else {
                                JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Error", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or password cannot be null.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(null, er.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Add listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());
                try {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        boolean checkUser = controller.checkUserExist(username);
                        if (checkUser) {
                            JOptionPane.showMessageDialog(null, "User exists. Please login.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            boolean addUser = controller.addUserToDB(username, password);
                            if (addUser) {
                                JOptionPane.showMessageDialog(null, "Register successfully. Please login.", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or password cannot be null.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(null, er.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Add component to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }
}
