import java.awt.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class RecordResultFrame extends JFrame {
    private List<Map<String, String>> results = new ArrayList<>();
    private boolean isUpdateMode = false;
    private int currentRow = -1;

    public RecordResultFrame(controller controller, String username) {
        setTitle("User Record");
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            ResultSet rs = controller.DisplayRecord(username);
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "You have no record.", "Message", JOptionPane.ERROR_MESSAGE);
            }
            while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("state_abbr", rs.getString("state_abbr"));
                    row.put("company_name", rs.getString("company_name"));
                    row.put("industry_name", rs.getString("industry_name"));
                    row.put("job_id", rs.getString("job_id"));
                    row.put("position_name", rs.getString("position_name"));
                    row.put("year", rs.getString("year"));
                    row.put("salary_amount", rs.getString("salary_amount"));
                    row.put("description", rs.getString("description"));
                    row.put("degree_level", rs.getString("degree_level"));
                    row.put("year_of_work", rs.getString("year_of_work"));
                    row.put("university_name", rs.getString("university_name"));

                    results.add(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Define column names
        String[] columnNames = {
            "State Abbreviation", "Company Branch", "Industry Name", "Job ID", "Position Name",
            "Year", "Salary Amount", "Position Description", "Degree Level", "Year of Work", 
            "University Name", "Benefit", "Interview", "Skills", "Update", "Save", "Cancel", "Delete"
        };

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 11 && column <= 17) {
                    return true;
                }
                if (isUpdateMode && column >= 0 && column <= 10 && column != 3 && row == currentRow) {
                    return true;
                }
                return false;
            }
        };
        for (Map<String, String> row : results) {
            tableModel.addRow(new Object[]{
                row.get("state_abbr"),
                row.get("company_name"),
                row.get("industry_name"),
                row.get("job_id"),
                row.get("position_name"),
                row.get("year"),
                row.get("salary_amount"),
                row.get("description"),
                row.get("degree_level"),
                row.get("year_of_work"),
                row.get("university_name"),
                "View Details",  // For "Benefit" column
                "View Details",  // For "Interview" column
                "View Details",  // For "Skills" column
                "Update",  // For "Update" button
                "Save",  // For "Save" button
                "Cancel",  // For "Cancel" button
                "Delete",  // For "Delete" button
            });
        }

        // Create table
        JTable table = new JTable(tableModel);

        // Set custom renderers and editors for "Interview", "Benefit", "Skills" columns, for "Update", "Save" and "Cancel" button
        table.getColumn("Benefit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Benefit").setCellEditor(new PersistentButtonEditor(controller, "benefit"));

        table.getColumn("Interview").setCellRenderer(new ButtonRenderer());
        table.getColumn("Interview").setCellEditor(new PersistentButtonEditor(controller, "interview"));

        table.getColumn("Skills").setCellRenderer(new ButtonRenderer());
        table.getColumn("Skills").setCellEditor(new PersistentButtonEditor(controller, "skill"));

        table.getColumn("Update").setCellRenderer(new ButtonRenderer());
        table.getColumn("Update").setCellEditor(new ButtonEditor(controller, table, tableModel, "Update"));

        table.getColumn("Save").setCellRenderer(new ButtonRenderer());
        table.getColumn("Save").setCellEditor(new ButtonEditor(controller, table, tableModel, "Save"));

        table.getColumn("Cancel").setCellRenderer(new ButtonRenderer());
        table.getColumn("Cancel").setCellEditor(new ButtonEditor(controller, table, tableModel, "Cancel"));

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(controller, table, tableModel, "Delete"));

        // Add table to the frame
        add(new JScrollPane(table), BorderLayout.CENTER);

        // // Back button
        // JPanel buttonPanel = new JPanel();
        // JButton backButton = new JButton("Back to Search");
        // backButton.addActionListener(e -> {
        //     dispose();
        //     SearchFrame searchFrame = new SearchFrame(controller, username);
        //     searchFrame.setVisible(true);
        // });
        // buttonPanel.add(backButton);

        // add(buttonPanel, BorderLayout.SOUTH);
    }

    // ButtonRenderer class for rendering the "View Details" button
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JButton button;

   
        public ButtonEditor(controller controller, JTable table, DefaultTableModel tableModel, String type) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                if (type.equals("Update")) {
                    currentRow = table.getSelectedRow();
                    isUpdateMode = true;
                    tableModel.fireTableDataChanged();
                    table.getColumn("State Abbreviation").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Company Branch").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Industry Name").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Position Name").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Year").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Salary Amount").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Position Description").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Year of Work").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("University Name").setCellEditor(new DefaultCellEditor(new JTextField()));

                    String[] degreeOptions = {"BS", "MS", "PhD"};
                    JComboBox<String> degreeComboBox = new JComboBox<>(degreeOptions);
                    table.getColumn("Degree Level").setCellEditor(new DefaultCellEditor(degreeComboBox));
                } else if (type.equals("Save")) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            int jobID = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 3));
                            String stateAbb = (String) tableModel.getValueAt(selectedRow, 0);
                            String companyName = (String) tableModel.getValueAt(selectedRow, 1);
                            String industryName = (String) tableModel.getValueAt(selectedRow, 2);
                            String positionName = (String) tableModel.getValueAt(selectedRow, 4);
                            int year = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 5));
                            BigDecimal salaryAmount = new BigDecimal((String) tableModel.getValueAt(selectedRow, 6));
                            String description = (String) tableModel.getValueAt(selectedRow, 7);
                            String degree = (String) tableModel.getValueAt(selectedRow, 8);
                            int yearOfWork = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 9));
                            String universityName = (String) tableModel.getValueAt(selectedRow, 10);
                            if (stateAbb.isEmpty() || companyName.isEmpty() || industryName.isEmpty() || positionName.isEmpty() 
                                || description.isEmpty() || degree.isEmpty() || universityName.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Field can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            controller.callUpdateRecord(jobID, stateAbb, companyName, industryName, positionName, year, salaryAmount, description, degree, yearOfWork, universityName);
                            isUpdateMode = false;
                            tableModel.fireTableDataChanged();
                            JOptionPane.showMessageDialog(null, "Update successfully", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException en) {
                            JOptionPane.showMessageDialog(null, en.toString(), "Number Exception", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (type.equals("Cancel")) {
                    isUpdateMode = false;
                    tableModel.fireTableDataChanged();
                } else if (type.equals("Delete")) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int jobID = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 3));
                        try {
                            controller.callDeleteRecord(jobID);
                            tableModel.removeRow(selectedRow);
                            tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(value != null ? value.toString() : "");
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }

    // PersistentButtonEditor class for handling "View Details" button clicks without disappearing
    private static class PersistentButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
    
        public PersistentButtonEditor(controller controller, String type) {
            button = new JButton();
            button.setOpaque(true);
    
            button.addActionListener(e -> {
                int jobID = Integer.parseInt(button.getClientProperty("job_id").toString());
                try {
                    if (type.equals("benefit")) {
                        List<Map<String, String>> benefit = new ArrayList<>();
                        ResultSet rsb = controller.DisplayRecordBenefit(jobID);
                        while (rsb.next()) {
                            Map<String, String> rowb = new HashMap<>();
                            rowb.put("benefitType", rsb.getString("benefit_type"));
                            rowb.put("benefitName", rsb.getString("benefit_name"));
                            benefit.add(rowb);
                        }
                        new BenefitDetailsFrame(benefit).setVisible(true); // Display benefit frame
                    } else if (type.equals("interview")) {
                        List<Map<String, String>> interview = new ArrayList<>();
                        ResultSet rsi = controller.DisplayRecordInterview(jobID);
                        while (rsi.next()) {
                            Map<String, String> rowi = new HashMap<>();
                            rowi.put("interviewType", rsi.getString("interview_type"));
                            rowi.put("interviewDescription", rsi.getString("description"));
                            interview.add(rowi);
                        }
                        new InterviewDetailsFrame(interview).setVisible(true); // Display interview frame
                    } else if (type.equals("skill")) {
                        List<Map<String, String>> skill = new ArrayList<>();
                        ResultSet rss = controller.DisplayRecordSkill(jobID);
                        while (rss.next()) {
                            Map<String, String> rows = new HashMap<>();
                            rows.put("skillName", rss.getString("skill_name"));
                            skill.add(rows);
                        }
                        new SkillDetailsFrame(skill).setVisible(true); // Display skill frame
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            
            });
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(value != null ? value.toString() : "");
            String jobID = table.getModel().getValueAt(row, 3).toString();
            button.putClientProperty("job_id", jobID);
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    
        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }
}
