import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class SearchFrame extends JFrame {
    private JTextField companyNameField;
    private JTextField stateAbbrField;
    private JComboBox<String> areaComboBox;
    private JTextField industryField;
    private JTextField positionField;

    private controller controller;

    public SearchFrame(controller controller, String username) {
        this.controller = controller;

        // Frame properties
        setTitle("Function Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Company Name
        JLabel companyNameLabel = new JLabel("Company Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(companyNameLabel, gbc);

        companyNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(companyNameField, gbc);

        // State Abbreviation
        JLabel stateAbbrLabel = new JLabel("State Abbreviation:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(stateAbbrLabel, gbc);

        stateAbbrField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(stateAbbrField, gbc);

        // Area
        JLabel areaLabel = new JLabel("Area:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(areaLabel, gbc);

        areaComboBox = new JComboBox<>(new String[]{"", "Northeast", "Midwest", "South", "West"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(areaComboBox, gbc);

        // Industry
        JLabel industryLabel = new JLabel("Industry:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(industryLabel, gbc);

        industryField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(industryField, gbc);

        // Position
        JLabel positionLabel = new JLabel("Position:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(positionLabel, gbc);

        positionField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(positionField, gbc);

        // Search Button
        JButton searchButton = new JButton("Start Search");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        // View Record Button
        JButton viewRecordButton = new JButton("View History Record");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(viewRecordButton, gbc);

        // Add Action Listener to Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String position = positionField.getText().trim();
                String area = (String) areaComboBox.getSelectedItem();
                String state = stateAbbrField.getText().trim();
                String industry = industryField.getText().trim();
                String companyName = companyNameField.getText().trim();
        
                try {
                    // Fetch search results from the controller
                    List<Map<String, String>> results = controller.executeSearchFromDB(position, area, state, industry, companyName);
        
                    // Open the ResultFrame
                    ResultFrame resultFrame = new ResultFrame(results, controller, username);
                    resultFrame.setVisible(true);
        
                    // Close the current SearchFrame
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SearchFrame.this, "Error executing search.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add Action Listener to ViewRecord Button
        viewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RecordEditedFrame
                RecordEditedFrame RecordEditedFrame = new RecordEditedFrame(controller, username);
                // RecordEditedFrame.setVisible(true);

                // Close the current SearchFrame
                dispose();
            }
        });

        // Add panel to frame
        add(panel);
    }
}
