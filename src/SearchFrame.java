import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class SearchFrame extends JFrame {
    private JTextField companyBranchField;
    private JTextField stateAbbrField;
    private JComboBox<String> areaComboBox;
    private JTextField industryNameField;
    private JTextField positionNameField;

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

        // Company Branch
        JLabel companyBranchLabel = new JLabel("Company Branch:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(companyBranchLabel, gbc);

        companyBranchField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(companyBranchField, gbc);

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

        // Industry Name
        JLabel industryNameLabel = new JLabel("Industry Name:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(industryNameLabel, gbc);

        industryNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(industryNameField, gbc);

        // Position Name
        JLabel positionNameLabel = new JLabel("Position Name:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(positionNameLabel, gbc);

        positionNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(positionNameField, gbc);

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
                String positionName = positionNameField.getText().trim();
                String area = (String) areaComboBox.getSelectedItem();
                String stateAbbr = stateAbbrField.getText().trim();
                String industryName = industryNameField.getText().trim();
                String companyBranch = companyBranchField.getText().trim();
        
                System.out.println("Search Parameters: ");
                System.out.println("Position Name: " + positionName);
                System.out.println("Area: " + area);
                System.out.println("State Abbreviation: " + stateAbbr);
                System.out.println("Industry Name: " + industryName);
                System.out.println("Company Branch: " + companyBranch);
        
                try {
                    List<Map<String, String>> results = controller.executeSearchFromDB(positionName, area, stateAbbr, industryName, companyBranch);
                    System.out.println("Results fetched: " + results.size());
        
                    ResultFrame resultFrame = new ResultFrame(results, controller, username);
                    resultFrame.setVisible(true);
        
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SearchFrame.this, "Error executing search.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        // Add Action Listener to View Record Button
        viewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RecordEditedFrame
                RecordResultFrame recordResultFrame = new RecordResultFrame(controller, username);
                recordResultFrame.setVisible(true);

                // Close the current SearchFrame
                dispose();
            }
        });

        // Add panel to frame
        add(panel);
    }
}
