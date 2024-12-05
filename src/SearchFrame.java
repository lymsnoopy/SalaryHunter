import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class SearchFrame extends JFrame {

    /**
     * Constructor of the SearchFrame class.
     * 
     * @param controller The controller.
     * @param username Username of the current user log in.
     */
    public SearchFrame(Controller controller, String username) {
        // Frame properties
        setTitle("Main Page");
        setSize(600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Area
        JLabel areaLabel = new JLabel("Area:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(areaLabel, gbc);

        // Create JCheckBox for each area
        String[] areas = {"Northeast", "Midwest", "South", "West"};
        JPanel areaPanel = new JPanel();
        areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.Y_AXIS));  // Vertically stack checkboxes
        
        // Add JCheckBoxes to the area panel
        JCheckBox[] checkBoxes = new JCheckBox[areas.length];
        for (int i = 0; i < areas.length; i++) {
            checkBoxes[i] = new JCheckBox(areas[i]);
            areaPanel.add(checkBoxes[i]);
        }

        // Add JScrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(areaPanel);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(scrollPane, gbc);

        // State Abbreviation
        JLabel stateAbbrLabel = new JLabel("State Abbreviation:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(stateAbbrLabel, gbc);

        // Create JCheckBox for each state abbreviation
        String[] stateAbbrs = {
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", 
            "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", 
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", 
            "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", 
            "WI", "WY", "DC"
        };
        JPanel stateAbbrPanel = new JPanel();
        stateAbbrPanel.setLayout(new BoxLayout(stateAbbrPanel, BoxLayout.Y_AXIS));  // Vertically stack checkboxes

        // Add JCheckBoxes to the state abbreviation panel
        JCheckBox[] stateCheckBoxes = new JCheckBox[stateAbbrs.length];
        for (int i = 0; i < stateAbbrs.length; i++) {
            stateCheckBoxes[i] = new JCheckBox(stateAbbrs[i]);
            stateAbbrPanel.add(stateCheckBoxes[i]);
        }

        // Add JScrollPane to allow scrolling
        JScrollPane stateScrollPane = new JScrollPane(stateAbbrPanel);
        stateScrollPane.setPreferredSize(new Dimension(200, 200)); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(stateScrollPane, gbc);

        // Industry Name
        JLabel industryNameLabel = new JLabel("Industry Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(industryNameLabel, gbc);

        JTextField industryNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(industryNameField, gbc);

        // Company Branch
        JLabel companyBranchLabel = new JLabel("Company Branch:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(companyBranchLabel, gbc);

        JTextField companyBranchField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(companyBranchField, gbc);

        // Position Name
        JLabel positionNameLabel = new JLabel("Position Name:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(positionNameLabel, gbc);

        JTextField positionNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(positionNameField, gbc);

        // Year
        JLabel yearLabel = new JLabel("Year:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(yearLabel, gbc);

        JTextField yearField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(yearField, gbc);

        // Degree
        JLabel degreeLabel = new JLabel("Degree:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(degreeLabel, gbc);

        // Create JCheckBox for each degree
        String[] degrees = {"BS", "MS", "PhD"};
        JPanel degreePanel = new JPanel();
        degreePanel.setLayout(new BoxLayout(degreePanel, BoxLayout.Y_AXIS));  // Vertically stack checkboxes
        
        // Add JCheckBoxes to the degree panel
        JCheckBox[] checkBox = new JCheckBox[degrees.length];
        for (int i = 0; i < degrees.length; i++) {
            checkBox[i] = new JCheckBox(degrees[i]);
            degreePanel.add(checkBox[i]);
        }

        // Add JScrollPane to allow scrolling
        JScrollPane scrollPanes = new JScrollPane(degreePanel);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(scrollPanes, gbc);

        // Univeristy Name
        JLabel universityLabel = new JLabel("University Name:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(universityLabel, gbc);

        JTextField universityField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(universityField, gbc);

        // Year Of Work
        JLabel yearOfWorkLabel = new JLabel("Year of Work:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(yearOfWorkLabel, gbc);

        JTextField yearOfWorkField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(yearOfWorkField, gbc);

        // Search Button
        JButton searchButton = new JButton("Start Search");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        // View Record Button
        JButton viewRecordButton = new JButton("View History Record");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(viewRecordButton, gbc);

        // Rate Button
        JButton rateButton = new JButton("Rate Company");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(rateButton, gbc);

        // Add button
        JButton addButton = new JButton("Add New Record");
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        // Exit button
        JButton exitButton = new JButton("Exit");
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(exitButton, gbc);

        // Add Action Listener to Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // area
                StringBuilder selectedAreas = new StringBuilder();
                for (int i = 0; i < checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        if (selectedAreas.length() > 0) {
                            selectedAreas.append(",");
                        }
                        selectedAreas.append(areas[i]);
                    }
                }
                String area = selectedAreas.toString();

                // stateAbbr
                StringBuilder selectedState = new StringBuilder();
                for (int i = 0; i < stateCheckBoxes.length; i++) {
                    if (stateCheckBoxes[i].isSelected()) {
                        if (selectedState.length() > 0) {
                            selectedState.append(",");
                        }
                        selectedState.append(stateAbbrs[i]);
                    }
                }
                String stateAbbr = selectedState.toString();

                // industryName
                String industryName = industryNameField.getText();
                
                // companyBranch
                String companyBranch = companyBranchField.getText();

                // positionName
                String positionName = positionNameField.getText();

                // year
                Integer year = null;
                if (!yearField.getText().isEmpty()) {
                    year = Integer.parseInt(yearField.getText());
                }

                // degree
                StringBuilder selectedDegrees = new StringBuilder();
                for (int i = 0; i < checkBox.length; i++) {
                    if (checkBox[i].isSelected()) {
                        if (selectedDegrees.length() > 0) {
                            selectedDegrees.append(",");
                        }
                        selectedDegrees.append(degrees[i]);
                    }
                }
                String degree = selectedDegrees.toString();
                
                // universityName
                String universityName = universityField.getText();

                // yearOfWork
                Integer yearOfWork = null;
                if (!yearOfWorkField.getText().isEmpty()) {
                    yearOfWork = Integer.parseInt(yearOfWorkField.getText()); 
                }

                try {
                    List<Map<String, String>> results = controller.executeSearchFromDB(
                        area, stateAbbr, industryName, companyBranch, positionName,  
                        year, degree, universityName, yearOfWork
                    );

                    ResultFrame resultFrame = new ResultFrame(results, controller, username);
                    resultFrame.setVisible(true);

                    // Close the current SearchFrame
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(SearchFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add Action Listener to View Record Button
        viewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RecordResultFrame
                RecordResultFrame recordResultFrame = new RecordResultFrame(controller, username);
                recordResultFrame.setVisible(true);

                // Close the current SearchFrame
                dispose();
            }
        });

        // Add Action Listener to Rate Button
        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RateFrame
                RateFrame rateFrame = new RateFrame(controller, username);
                rateFrame.setVisible(true);

                // Close the current SearchFrame
                dispose();
            }
        });

        // Add Action Listener to Add Button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddRecordFrame
                AddRecordFrame addRecordFrame = new AddRecordFrame(controller, username);
                addRecordFrame.setVisible(true);

                // Close the current SearchFrame
                dispose();
            }
        });

        // Add Action Listener to Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.exit();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(SearchFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });

        // Add panel to frame
        add(panel);
        JScrollPane frameScrollPane = new JScrollPane(panel);
        add(frameScrollPane);
    }
}
