// import javax.swing.*;
// import java.awt.*;
// import java.math.BigDecimal;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// public class AddRecordFrame extends JFrame {
//     private JTextField positionField, yearField, salaryField, descriptionField, stateAbbrField, companyNameField, industryField;
//     private JComboBox<String> degreeComboBox;
//     private JTextField universityField, yearOfWorkField;

//     private JPanel skillPanel, interviewPanel, benefitPanel;
//     private List<JPanel> skillFields = new ArrayList<>();
//     private List<JPanel> interviewFields = new ArrayList<>();
//     private List<JPanel> benefitFields = new ArrayList<>();

//     public AddRecordFrame(Controller controller, String username) {
//         setTitle("Add New Record");
//         setSize(900, 700);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());

//         // Main form panel
//         JPanel mainPanel = new JPanel(new GridLayout(10, 2, 10, 10));
//         addFormFields(mainPanel);

//         // Dynamic panels
//         skillPanel = createSkillPanel();
//         interviewPanel = createDynamicPanelWithDelete("Interviews", interviewFields, new String[]{
//                 "Online Assessment", "Pre-Recorded Interview", "Behavioral Interview", 
//                 "Technical Interview", "Supervisor Interview"});
//         benefitPanel = createDynamicPanelWithDelete("Benefits", benefitFields, new String[]{
//                 "Insurance", "Holiday", "Stock", "Retirement", "Family"});

//         // Center split panel
//         JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//                 new JScrollPane(mainPanel), createSidePanel());
//         centerPane.setDividerLocation(450);
//         add(centerPane, BorderLayout.CENTER);

//         // Bottom button panel
//         JPanel buttonPanel = new JPanel();
//         JButton addButton = new JButton("Add Record");
//         addButton.addActionListener(e -> {
//             try {
//                 saveRecord(controller, username);
//             } catch (SQLException ex) {
//                 JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         });

//         JButton backButton = new JButton("Back");
//         backButton.addActionListener(e -> {
//             dispose();
//             new RecordResultFrame(controller, username).setVisible(true);
//         });

//         buttonPanel.add(addButton);
//         buttonPanel.add(backButton);
//         add(buttonPanel, BorderLayout.SOUTH);
//     }

//     private void addFormFields(JPanel mainPanel) {
//         mainPanel.add(new JLabel("Position Name:"));
//         positionField = new JTextField();
//         mainPanel.add(positionField);

//         mainPanel.add(new JLabel("Year:"));
//         yearField = new JTextField();
//         mainPanel.add(yearField);

//         mainPanel.add(new JLabel("Salary Amount:"));
//         salaryField = new JTextField();
//         mainPanel.add(salaryField);

//         mainPanel.add(new JLabel("Description:"));
//         descriptionField = new JTextField();
//         mainPanel.add(descriptionField);

//         mainPanel.add(new JLabel("State Abbreviation:"));
//         stateAbbrField = new JTextField();
//         mainPanel.add(stateAbbrField);

//         mainPanel.add(new JLabel("Company Name:"));
//         companyNameField = new JTextField();
//         mainPanel.add(companyNameField);

//         mainPanel.add(new JLabel("Industry Name:"));
//         industryField = new JTextField();
//         mainPanel.add(industryField);

//         mainPanel.add(new JLabel("Degree Level:"));
//         degreeComboBox = new JComboBox<>(new String[]{"BS", "MS", "PhD"});
//         mainPanel.add(degreeComboBox);

//         mainPanel.add(new JLabel("University Name:"));
//         universityField = new JTextField();
//         mainPanel.add(universityField);

//         mainPanel.add(new JLabel("Years of Work:"));
//         yearOfWorkField = new JTextField();
//         mainPanel.add(yearOfWorkField);
//     }

//     private JPanel createSkillPanel() {
//         JPanel panel = new JPanel(new BorderLayout());
//         JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
//         JScrollPane scrollPane = new JScrollPane(inputPanel);
//         panel.add(new JLabel("Skills:"), BorderLayout.NORTH);
//         panel.add(scrollPane, BorderLayout.CENTER);

//         JButton addButton = new JButton("+");
//         addButton.addActionListener(e -> {
//             JPanel newField = createSkillField(inputPanel);
//             inputPanel.add(newField);
//             skillFields.add(newField);
//             inputPanel.revalidate();
//             inputPanel.repaint();
//         });
//         panel.add(addButton, BorderLayout.SOUTH);

//         // Add initial field
//         JPanel initialField = createSkillField(inputPanel);
//         inputPanel.add(initialField);
//         skillFields.add(initialField);

//         return panel;
//     }

//     private JPanel createSkillField(JPanel inputPanel) {
//         JPanel panel = new JPanel(new BorderLayout());
//         JTextField skillField = new JTextField();
//         JButton deleteButton = new JButton("Delete");
//         deleteButton.addActionListener(e -> {
//             inputPanel.remove(panel);
//             skillFields.remove(panel);
//             inputPanel.revalidate();
//             inputPanel.repaint();
//         });
//         panel.add(skillField, BorderLayout.CENTER);
//         panel.add(deleteButton, BorderLayout.EAST);
//         return panel;
//     }

//     private JPanel createDynamicPanelWithDelete(String title, List<JPanel> fieldList, String[] comboBoxOptions) {
//         JPanel panel = new JPanel(new BorderLayout());
//         JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
//         JScrollPane scrollPane = new JScrollPane(inputPanel);
//         panel.add(new JLabel(title + ":"), BorderLayout.NORTH);
//         panel.add(scrollPane, BorderLayout.CENTER);

//         JButton addButton = new JButton("+");
//         addButton.addActionListener(e -> {
//             JPanel newField = createDynamicFieldWithDelete(inputPanel, comboBoxOptions);
//             inputPanel.add(newField);
//             fieldList.add(newField);
//             inputPanel.revalidate();
//             inputPanel.repaint();
//         });
//         panel.add(addButton, BorderLayout.SOUTH);

//         // Add initial field
//         JPanel initialField = createDynamicFieldWithDelete(inputPanel, comboBoxOptions);
//         inputPanel.add(initialField);
//         fieldList.add(initialField);

//         return panel;
//     }

//     private JPanel createDynamicFieldWithDelete(JPanel inputPanel, String[] comboBoxOptions) {
//         JPanel panel = new JPanel(new BorderLayout());
//         JComboBox<String> comboBox = new JComboBox<>(comboBoxOptions);
//         JTextField descriptionField = new JTextField();
//         JButton deleteButton = new JButton("Delete");
//         deleteButton.addActionListener(e -> {
//             inputPanel.remove(panel);
//             inputPanel.revalidate();
//             inputPanel.repaint();
//         });

//         JPanel fieldsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
//         fieldsPanel.add(comboBox);
//         fieldsPanel.add(descriptionField);
//         panel.add(fieldsPanel, BorderLayout.CENTER);
//         panel.add(deleteButton, BorderLayout.EAST);
//         return panel;
//     }

//     private JPanel createSidePanel() {
//         JPanel sidePanel = new JPanel(new GridLayout(3, 1, 10, 10));
//         sidePanel.add(skillPanel);
//         sidePanel.add(interviewPanel);
//         sidePanel.add(benefitPanel);
//         return sidePanel;
//     }

//     private void saveRecord(Controller controller, String username) throws SQLException {
//         String position = positionField.getText();
//         String yearStr = yearField.getText();
//         String salaryStr = salaryField.getText();
//         String description = descriptionField.getText();
//         String stateAbbr = stateAbbrField.getText();
//         String companyName = companyNameField.getText();
//         String industry = industryField.getText();
//         String degree = (String) degreeComboBox.getSelectedItem();
//         String university = universityField.getText();
//         String yearOfWorkStr = yearOfWorkField.getText();

//         // Validate required fields
//         if (position.isEmpty() || yearStr.isEmpty() || salaryStr.isEmpty() || description.isEmpty() ||
//                 stateAbbr.isEmpty() || companyName.isEmpty() || industry.isEmpty() || university.isEmpty() || yearOfWorkStr.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         int year = Integer.parseInt(yearStr);
//         BigDecimal salary = new BigDecimal(salaryStr);
//         int yearOfWork = Integer.parseInt(yearOfWorkStr);

//         // Validate skills
//         for (JPanel panel : skillFields) {
//             JTextField skillField = (JTextField) panel.getComponent(0);
//             if (skillField.getText().isEmpty()) {
//                 JOptionPane.showMessageDialog(this, "All skill fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
//             controller.callUpdateSkill(-1, skillField.getText());
//         }

//         // Validate interviews
//         for (JPanel panel : interviewFields) {
//             JComboBox<String> comboBox = (JComboBox<String>) ((JPanel) panel.getComponent(0)).getComponent(0);
//             JTextField descField = (JTextField) ((JPanel) panel.getComponent(0)).getComponent(1);
//             if (comboBox.getSelectedItem() == null || descField.getText().isEmpty()) {
//                 JOptionPane.showMessageDialog(this, "All interview fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
//             controller.callUpdateInterview(-1, comboBox.getSelectedItem().toString(), descField.getText());
//         }

//         // Validate benefits
//         for (JPanel panel : benefitFields) {
//             JComboBox<String> comboBox = (JComboBox<String>) ((JPanel) panel.getComponent(0)).getComponent(0);
//             JTextField descField = (JTextField) ((JPanel) panel.getComponent(0)).getComponent(1);
//             if (comboBox.getSelectedItem() == null || descField.getText().isEmpty()) {
//                 JOptionPane.showMessageDialog(this, "All benefit fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
//             controller.callUpdateBenefit(-1, comboBox.getSelectedItem().toString(), descField.getText());
//         }

//         // Save main record
//         controller.callUpdateRecord(-1, stateAbbr, companyName, industry, position, year, salary, description, degree, yearOfWork, university);

//         JOptionPane.showMessageDialog(this, "Record added successfully!");
//         dispose();
//         new RecordResultFrame(controller, username).setVisible(true);
//     }
// }
