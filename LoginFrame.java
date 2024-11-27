import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String username;
    private String password;
    private model model = new model();

    public LoginFrame() {
        setTitle("Login Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Create username label and password label
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();

        // Create login and register button
        JButton loginButton = new JButton("Login");

        // Add listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());

                // login successfully
                try {
                    model.login(username, password);
                    JOptionPane.showMessageDialog(null, "Connected to database.", "Connected Successfully!", JOptionPane.INFORMATION_MESSAGE);
                    boolean user = model.existsUsername(username);
                    if (user == false) {
                        model.addUser(username, password);
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(null, er.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                // jump into selection page
            }
        });

        // Add component to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
    }
}
