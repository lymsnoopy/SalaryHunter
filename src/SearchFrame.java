import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SearchFrame extends JFrame {
    private JTextField companyNameField;
    private JTextField stateAbbrField;
    private JComboBox<String> areaComboBox;
    private JTextField industryField;
    private JTextField positionField;

    private String companyName;
    private String stateAbbr;
    private String area;
    private String industry;
    private String position;

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
        gbc.gridy = 5; // Place it in row 5
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        panel.add(searchButton, gbc);

        // View Record Button
        JButton viewRecordButton = new JButton("View History Record");
        gbc.gridx = 0;
        gbc.gridy = 6; // Place it in row 6 (below the Search Button)
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        panel.add(viewRecordButton, gbc);

        // Add Action Listener to Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                companyName = companyNameField.getText();
                stateAbbr = stateAbbrField.getText();
                area = (String) areaComboBox.getSelectedItem();
                industry = industryField.getText();
                position = positionField.getText();

                StringBuilder query = new StringBuilder("SELECT * FROM Job_Postings WHERE 1=1");
                List<String> parameters = new ArrayList<>();

                if (companyName != null && !companyName.trim().isEmpty()) {
                    query.append(" AND company_name LIKE ?");
                    parameters.add("%" + companyName.trim() + "%");
                }
                if (stateAbbr != null && !stateAbbr.trim().isEmpty()) {
                    query.append(" AND state_abbr = ?");
                    parameters.add(stateAbbr.trim());
                }
                if (area != null && !area.trim().isEmpty()) {
                    query.append(" AND area = ?");
                    parameters.add(area.trim());
                }
                if (industry != null && !industry.trim().isEmpty()) {
                    query.append(" AND industry LIKE ?");
                    parameters.add("%" + industry.trim() + "%");
                }
                if (position != null && !position.trim().isEmpty()) {
                    query.append(" AND position LIKE ?");
                    parameters.add("%" + position.trim() + "%");
                }

                try {
                    controller.executeSearchFromDB(query.toString(), parameters);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add Action Listener to ViewRecord Button
        viewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RecordEditedFrame RecordEditedFrame = new RecordEditedFrame(controller, username);
                // RecordEditedFrame.setVisible(true);
            }
        });

        // Add panel to frame
        add(panel);
    }
}
