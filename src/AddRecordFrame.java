import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddRecordFrame extends JFrame {
    private JTextField positionField, yearField, salaryField, descriptionField, stateAbbrField, companyNameField, industryField;
    private JComboBox<String> degreeComboBox;
    private JTextField universityField, yearOfWorkField;

    private JPanel skillPanel, interviewPanel, benefitPanel;
    private List<JTextField> skillFields = new ArrayList<>();
    private List<JPanel> interviewFields = new ArrayList<>();
    private List<JPanel> benefitFields = new ArrayList<>();

    public AddRecordFrame(Controller controller, String username) {
        setTitle("Add New Record");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main form panel
        JPanel mainPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        addFormFields(mainPanel);

        // Dynamic panels
        skillPanel = createDynamicSkillPanel();
        interviewPanel = createDynamicPanelWithDelete("Interviews", interviewFields, new String[]{
                "Online Assessment", "Pre-Recorded Interview", "Behavioral Interview",
                "Technical Interview", "Supervisor Interview"});
        benefitPanel = createDynamicPanelWithDelete("Benefits", benefitFields, new String[]{
                "Insurance", "Holiday", "Stock", "Retirement", "Family"});

        // Center split panel
        JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(mainPanel), createSidePanel());
        centerPane.setDividerLocation(450);
        add(centerPane, BorderLayout.CENTER);

        // Bottom button panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Record");
        saveButton.addActionListener(e -> saveRecord(controller, username));
        buttonPanel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new RecordResultFrame(controller, username).setVisible(true);
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormFields(JPanel mainPanel) {
        mainPanel.add(new JLabel("Position Name:"));
        positionField = new JTextField();
        mainPanel.add(positionField);

        mainPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        mainPanel.add(yearField);

        mainPanel.add(new JLabel("Salary Amount:"));
        salaryField = new JTextField();
        mainPanel.add(salaryField);

        mainPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        mainPanel.add(descriptionField);

        mainPanel.add(new JLabel("State Abbreviation:"));
        stateAbbrField = new JTextField();
        mainPanel.add(stateAbbrField);

        mainPanel.add(new JLabel("Company Name:"));
        companyNameField = new JTextField();
        mainPanel.add(companyNameField);

        mainPanel.add(new JLabel("Industry Name:"));
        industryField = new JTextField();
        mainPanel.add(industryField);

        mainPanel.add(new JLabel("Degree Level:"));
        degreeComboBox = new JComboBox<>(new String[]{"BS", "MS", "PhD"});
        mainPanel.add(degreeComboBox);

        mainPanel.add(new JLabel("University Name:"));
        universityField = new JTextField();
        mainPanel.add(universityField);

        mainPanel.add(new JLabel("Years of Work:"));
        yearOfWorkField = new JTextField();
        mainPanel.add(yearOfWorkField);
    }

    private JPanel createDynamicSkillPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(new JLabel("Skills:"), BorderLayout.NORTH);
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

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        sidePanel.add(skillPanel);
        sidePanel.add(interviewPanel);
        sidePanel.add(benefitPanel);
        return sidePanel;
    }

    private void saveRecord(Controller controller, String username) {
        try {
            String companyName = companyNameField.getText();
            String stateAbbr = stateAbbrField.getText();

            // Validate and get or insert company
            int companyId = controller.getOrCreateCompanyId(companyName, stateAbbr);

            String positionName = positionField.getText();
            String description = descriptionField.getText();
            int year = Integer.parseInt(yearField.getText());
            BigDecimal salary = new BigDecimal(salaryField.getText());

            // Insert Job Position and get jobId
            int jobId = controller.insertJobPosition(positionName, description, year, salary, companyId, username);

            // Insert Skills
            for (JTextField skillField : skillFields) {
                controller.insertSkill(jobId, skillField.getText());
            }

            // Insert Benefits
            for (JPanel benefitField : benefitFields) {
                JPanel inputSubPanel = (JPanel) benefitField.getComponent(0);
                JComboBox<?> comboBox = (JComboBox<?>) inputSubPanel.getComponent(0);
                JTextField textField = (JTextField) inputSubPanel.getComponent(1);
                controller.insertBenefit(jobId, (String) comboBox.getSelectedItem(), textField.getText());
            }

            // Insert Interviews
            for (JPanel interviewField : interviewFields) {
                JPanel inputSubPanel = (JPanel) interviewField.getComponent(0);
                JComboBox<?> comboBox = (JComboBox<?>) inputSubPanel.getComponent(0);
                JTextField textField = (JTextField) inputSubPanel.getComponent(1);
                controller.insertInterview(jobId, (String) comboBox.getSelectedItem(), textField.getText());
            }

            JOptionPane.showMessageDialog(this, "Record added successfully!");
            dispose();
            new RecordResultFrame(controller, username).setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Invalid number format", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Component Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
