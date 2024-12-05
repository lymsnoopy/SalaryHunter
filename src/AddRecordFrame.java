import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AddRecordFrame extends JFrame {
    // Input for company, position and salary related fields.
    private JTextField positionField, yearField, salaryField, descriptionField, companyNameField, industryField, universityField, yearOfWorkField;
    private JComboBox<String> stateComboBox, degreeComboBox;

    // Panels to dynamically add skill, interview, and benefit information.
    private final JPanel skillPanel, interviewPanel, benefitPanel;

    // Lists to store skill, interview, and benefit information.
    private final List<JTextField> skillFields = new ArrayList<>();
    private final List<JPanel> interviewFields = new ArrayList<>();
    private final List<JPanel> benefitFields = new ArrayList<>();

     /**
     * Constructor for the AddRecordFrame class.
     * Sets up the main frame layout.
     * User can search, add, view, rate and exit.
     *
     * @param controller The controller.
     * @param username The username of the current user log in.
     */
    public AddRecordFrame(Controller controller, String username) {
        setTitle("Add New Record");
        setSize(2000, 1200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main form panel
        JPanel mainPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        addFormFields(mainPanel);

        // Dynamic panels
        skillPanel = createDynamicSkillPanel();
        interviewPanel = createDynamicPanelWithDelete("Interview", interviewFields, new String[]{
                "Online Assessment", "Pre-Recorded Interview", "Behavioral Interview",
                "Technical Interview", "Supervisor Interview"});
        benefitPanel = createDynamicPanelWithDelete("Benefit", benefitFields, new String[]{
                "Insurance", "Holiday", "Stock", "Retirement", "Family"});

        // Center split panel
        JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(mainPanel), createSidePanel());
        centerPane.setDividerLocation(800);
        add(centerPane, BorderLayout.CENTER);

        // Bottom button panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Record");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecord(controller, username);
            }
        });
        buttonPanel.add(saveButton);

        // Back button
        JButton backButton = new JButton("Back to Main Page");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            SearchFrame searchFrame = new SearchFrame(controller, username);
            searchFrame.setVisible(true);
            }
        });
        buttonPanel.add(backButton);

        // View Record button
        JButton recordButton = new JButton("View Record");
        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            RecordResultFrame recordResultFrame = new RecordResultFrame(controller, username);
            recordResultFrame.setVisible(true);
            }
        });
        buttonPanel.add(recordButton);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.exit();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AddRecordFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Add fields to the main panel.
     * 
     * @param mainPanel The panel to which fields will be added.
     */
    private void addFormFields(JPanel mainPanel) {
        mainPanel.add(new JLabel("State Abbreviation:"));
        stateComboBox = new JComboBox<>(new String[]{
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", 
            "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", 
            "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", 
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "DC"
        });
        mainPanel.add(stateComboBox);

        mainPanel.add(new JLabel("Company Name:"));
        companyNameField = new JTextField();
        mainPanel.add(companyNameField);

        mainPanel.add(new JLabel("Industry Name:"));
        industryField = new JTextField();
        mainPanel.add(industryField);

        mainPanel.add(new JLabel("Position Name:"));
        positionField = new JTextField();
        mainPanel.add(positionField);

        mainPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        mainPanel.add(descriptionField);

        mainPanel.add(new JLabel("Year (number):"));
        yearField = new JTextField();
        mainPanel.add(yearField);

        mainPanel.add(new JLabel("Annual Salary Amount (number):"));
        salaryField = new JTextField();
        mainPanel.add(salaryField);

        mainPanel.add(new JLabel("Degree Level:"));
        degreeComboBox = new JComboBox<>(new String[]{"BS", "MS", "PhD"});
        mainPanel.add(degreeComboBox);

        mainPanel.add(new JLabel("University Name:"));
        universityField = new JTextField();
        mainPanel.add(universityField);

        mainPanel.add(new JLabel("Years of Work (number):"));
        yearOfWorkField = new JTextField();
        mainPanel.add(yearOfWorkField);
    }

    /**
     * Creates a dynamic panel for adding skill information.
     * 
     * @return A JPanel with a list of skill input fields.
     */
    private JPanel createDynamicSkillPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(new JLabel("Skill:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.addActionListener(e -> {
            JPanel fieldPanel = new JPanel(new BorderLayout());
            JTextField skillField = new JTextField();
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(ev -> {
                inputPanel.remove(fieldPanel);
                skillFields.remove(skillField);
                inputPanel.revalidate();
                inputPanel.repaint();
            });
            fieldPanel.add(skillField, BorderLayout.CENTER);
            fieldPanel.add(deleteButton, BorderLayout.EAST);
            inputPanel.add(fieldPanel);
            skillFields.add(skillField);
            inputPanel.revalidate();
            inputPanel.repaint();
        });
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates a dynamic panel for adding interview or benefit information.
     * 
     * @param title The title for the section.
     * @param fieldList A list to store the added fields.
     * @param comboBoxOptions Options to populate the combo box.
     * 
     * @return A JPanel containing dynamic input fields.
     */
    private JPanel createDynamicPanelWithDelete(String title, List<JPanel> fieldList, String[] comboBoxOptions) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(new JLabel(title + ":"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.addActionListener(e -> {
            JPanel fieldPanel = new JPanel(new BorderLayout());
            JComboBox<String> comboBox = new JComboBox<>(comboBoxOptions);
            JTextField textField = new JTextField();
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(ev -> {
                inputPanel.remove(fieldPanel);
                fieldList.remove(fieldPanel);
                inputPanel.revalidate();
                inputPanel.repaint();
            });
            JPanel inputSubPanel = new JPanel(new GridLayout(1, 2, 5, 5));
            inputSubPanel.add(comboBox);
            inputSubPanel.add(textField);
            fieldPanel.add(inputSubPanel, BorderLayout.CENTER);
            fieldPanel.add(deleteButton, BorderLayout.EAST);
            inputPanel.add(fieldPanel);
            fieldList.add(fieldPanel);
            inputPanel.revalidate();
            inputPanel.repaint();
        });
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates a panel containing dynamic sections for skill, interview, and benefit.
     * 
     * @return A JPanel containing the skill, interview, and benefit panels.
     */
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        sidePanel.add(skillPanel);
        sidePanel.add(interviewPanel);
        sidePanel.add(benefitPanel);
        return sidePanel;
    }

    /**
     * Saves the record entered in the form to the database.
     *
     * @param controller The controller.
     * @param username The username of the current user log in.
     */
    private void saveRecord(Controller controller, String username) {
        try {
            String companyName = companyNameField.getText();
            String stateAbbr = (String) stateComboBox.getSelectedItem();
            String industryName = industryField.getText();
            String positionName = positionField.getText();
            String description = descriptionField.getText();
            int year = Integer.parseInt(yearField.getText());
            BigDecimal salary = new BigDecimal(salaryField.getText());
            String degree = (String) degreeComboBox.getSelectedItem();
            String universityName = universityField.getText();
            int yearOfWork = Integer.parseInt(yearOfWorkField.getText());
            int jobId = -1;

            // check fields not empty
            if (companyName.isEmpty() || stateAbbr.isEmpty() || industryName.isEmpty() || positionName.isEmpty() 
                || description.isEmpty() || degree.isEmpty() || universityName.isEmpty() || checkBenefitEmpty()
                || checkInterviewEmpty() || checkSkillEmpty()) {
                    JOptionPane.showMessageDialog(AddRecordFrame.this, "Field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Validate and get or insert company
                int companyId = controller.getOrCreateCompanyId(companyName, stateAbbr, industryName);
            
                // Insert Job Position and get jobId
                jobId = controller.insertJobPosition(positionName, description, year, salary, companyId, username);
                controller.insertBackground(jobId, degree, universityName, yearOfWork, username);
           
                // Insert Benefits
                for (JPanel benefitField : benefitFields) {
                    JPanel inputSubPanel = (JPanel) benefitField.getComponent(0);
                    JComboBox<?> comboBox = (JComboBox<?>) inputSubPanel.getComponent(0);
                    JTextField textField = (JTextField) inputSubPanel.getComponent(1);
                    String benefitType = (String) comboBox.getSelectedItem();
                    String benefitName = textField.getText();
                    if (benefitType.isEmpty() || benefitName.isEmpty()) {
                        JOptionPane.showMessageDialog(AddRecordFrame.this, "Benefit field cannot be empty!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        controller.insertBenefit(jobId, benefitType, benefitName);
                    }
                }
                
                // Insert Skills
                for (JTextField skillField : skillFields) {
                    String skillName = skillField.getText();
                    if (skillName.isEmpty()) {
                        JOptionPane.showMessageDialog(AddRecordFrame.this, "Skill field cannot be empty!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        controller.insertSkill(jobId, skillName);
                    }
                }

                // Insert Interviews
                for (JPanel interviewField : interviewFields) {
                    JPanel inputSubPanel = (JPanel) interviewField.getComponent(0);
                    JComboBox<?> comboBox = (JComboBox<?>) inputSubPanel.getComponent(0);
                    JTextField textField = (JTextField) inputSubPanel.getComponent(1);
                    String interviewType = (String) comboBox.getSelectedItem();
                    String InterviewDescription = textField.getText();
                    if (interviewType.isEmpty() || InterviewDescription.isEmpty()) {
                        JOptionPane.showMessageDialog(AddRecordFrame.this, "Interview field cannot be empty!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        controller.insertInterview(jobId, interviewType, InterviewDescription);
                    }
                }

                JOptionPane.showMessageDialog(AddRecordFrame.this, "Add record successfully!", "Add Successfully", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(AddRecordFrame.this, "Invalid number format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(AddRecordFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(AddRecordFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * checks whether any of the benefit fields are empty.
     * 
     * @return true if any of the benefit fields is empty, otherwise false.
     */
    private boolean checkBenefitEmpty() {
        for (JPanel benefitField : benefitFields) {
            JPanel inputSubPanel = (JPanel) benefitField.getComponent(0);
            JComboBox<?> comboBox = (JComboBox<?>) inputSubPanel.getComponent(0);
            JTextField textField = (JTextField) inputSubPanel.getComponent(1);
            String benefitType = (String) comboBox.getSelectedItem();
            String benefitName = textField.getText();
            if (benefitType.isEmpty() || benefitName.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks whether any of the skill fields are empty.
     * 
     * @return true if any of the skill fieldss is empty, otherwise false.
     */
    private boolean checkSkillEmpty() {
        for (JTextField skillField : skillFields) {
            String skillName = skillField.getText();
            if (skillName.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks whether any of the interview fields are empty.
     * 
     * @return true if any of the interview fields is empty, otherwise false.
     */
    private boolean checkInterviewEmpty() {
        for (JPanel interviewField : interviewFields) {
            JPanel inputSubPanel = (JPanel) interviewField.getComponent(0);
            JComboBox<?> comboBox = (JComboBox<?>) inputSubPanel.getComponent(0);
            JTextField textField = (JTextField) inputSubPanel.getComponent(1);
            String interviewType = (String) comboBox.getSelectedItem();
            String InterviewDescription = textField.getText();
            if (interviewType.isEmpty() || InterviewDescription.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
