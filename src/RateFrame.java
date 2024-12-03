import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class RateFrame extends JFrame {

    public RateFrame(Controller controller, String username) {

        // Frame properties
        setTitle("Rate Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Company Branch
        JLabel companyBranchLabel = new JLabel("Company Branch:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(companyBranchLabel, gbc);
        List<String> companyNames = new ArrayList<>();
        try {
            companyNames = controller.searchCompanyList();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(RateFrame.this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JComboBox<String> companyComboBox = new JComboBox<>(companyNames.toArray(new String[0]));
        // JTextField companyBranchField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(companyComboBox, gbc);

        // Rate
        JLabel rateLabel = new JLabel("Rate (0 - 5) :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(rateLabel, gbc);

        JTextField rateField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(rateField, gbc);

        // Add Rate Button
        JButton rateButton = new JButton("Add Rate");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(rateButton, gbc);

        // Add Action Listener to Add Rate Button
        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String companyBranch = (String) companyComboBox.getSelectedItem();
                String rates = rateField.getText();
                int rate = -1;
                if (!rates.isEmpty()) {
                    try {
                        rate = Integer.parseInt(rates);
                        if (rate >= 0 && rate <= 5) {
                            try {
                                boolean update = controller.addRateToDB(username, companyBranch, rate);
                                if (update) {
                                    BigDecimal averageRate = controller.showRate(companyBranch);
                                    JOptionPane.showMessageDialog(
                                        RateFrame.this, 
                                        "Rate Successfully!\nThe average rate of company " + companyBranch + " is " + averageRate + " .", 
                                        "Rate", JOptionPane.INFORMATION_MESSAGE
                                    );
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(RateFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(RateFrame.this, "Rate should be number between 0 - 5", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException  en) {
                        JOptionPane.showMessageDialog(RateFrame.this, "Rate should be number between 0 - 5", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }  
                } else {
                    JOptionPane.showMessageDialog(RateFrame.this, "Field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back button
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Main Page");
        backButton.addActionListener(e -> {
            dispose();
            SearchFrame searchFrame = new SearchFrame(controller, username);
            searchFrame.setVisible(true);
        });
        buttonPanel.add(backButton);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.exit();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(RateFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add panel to frame
        add(panel);
    }
}
