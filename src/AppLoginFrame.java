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

    public AppLoginFrame(Controller controller) {
        setTitle("App Login Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create username label and password label
        JLabel usernameLabel = new JLabel("Username: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // Create login and register button
        JButton loginButton = new JButton("AppLogin");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("AppRegister");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

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
                            JOptionPane.showMessageDialog(AppLoginFrame.this, "User not exists. Please register.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (controller.checkPassword(username, password)) {
                                JOptionPane.showMessageDialog(AppLoginFrame.this, "Login Successfully!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                SearchFrame SearchFrame = new SearchFrame(controller, username);
                                SearchFrame.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(AppLoginFrame.this, "Incorrect password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(AppLoginFrame.this, "Username or password cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(AppLoginFrame.this, er.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
                            JOptionPane.showMessageDialog(AppLoginFrame.this, "User exists. Please login.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean addUser = controller.addUserToDB(username, password);
                            if (addUser) {
                                JOptionPane.showMessageDialog(AppLoginFrame.this, "Register successfully. Please login.", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(AppLoginFrame.this, "Username or password cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(AppLoginFrame.this, er.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add panel to frame
        add(panel);
    }
}
