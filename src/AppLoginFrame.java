import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class AppLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String username;
    private String password;
    private controller controller;

    public AppLoginFrame(controller controller) {
        this.controller = controller;
        setTitle("App Login Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Create username label and password label
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();

        // Create login and register button
        JButton loginButton = new JButton("AppLogin");
        JButton registerButton = new JButton("AppRegister");

        // Add listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());
                try {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        boolean checkUser = controller.checkUserExist(username);
                        if (!checkUser) {
                            JOptionPane.showMessageDialog(null, "User not exists. Please register.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            if (controller.checkPassword(username, password)) {
                                JOptionPane.showMessageDialog(null, "Login Successfully!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                SearchFrame SearchFrame = new SearchFrame(controller, username);
                                SearchFrame.setVisible(true);
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
