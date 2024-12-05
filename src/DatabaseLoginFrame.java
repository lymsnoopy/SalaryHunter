import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class DatabaseLoginFrame extends JFrame {
    private JTextField usernameField;  // Text field for the username input.
    private JPasswordField passwordField;  // Password field for the password input.
    private String username;  // Username of the user.
    private String password;  // Password of the user.
    private Controller controller = new Controller();  // Instance of Controller.

    /**
     * Constructor of the class.
     */
    public DatabaseLoginFrame() {
        // Set the title and properties.
        setTitle("Database Login Page");
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

        // Create login button
        JButton loginButton = new JButton("DatabaseLogin");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Add listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());

                try {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        controller.dbLogin(username, password);
                        JOptionPane.showMessageDialog(DatabaseLoginFrame.this, "Connected to database.", "Connected Successfully!", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        AppLoginFrame appLoginFrame = new AppLoginFrame(controller);
                        appLoginFrame.setVisible(true);
                    } else {
                    JOptionPane.showMessageDialog(DatabaseLoginFrame.this, "Username or password cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(DatabaseLoginFrame.this, er.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add panel to frame
        add(panel);
    }
}
