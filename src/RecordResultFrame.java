import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public RecordResultFrame(Controller controller, String username) {
        setTitle("User Record");
        setSize(2000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            results = controller.DisplayRecord(username);
            if (results.isEmpty() || results == null) {
                JOptionPane.showMessageDialog(RecordResultFrame.this, "You have no record.", "Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(RecordResultFrame.this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Define column names
        String[] columnNames = {
            "State Abbreviation", "Company Branch", "Industry Name", "Job ID", "Position Name",
            "Year", "Annual Salary Amount", "Position Description", "Degree Level", "Year of Work", 
            "University Name", "Benefit", "Interview", "Skill", "Update", "Save", "Cancel", "Delete"
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
                "View Details",  // For "Skill" column
                "Update",  // For "Update" button
                "Save",  // For "Save" button
                "Cancel",  // For "Cancel" button
                "Delete"  // For "Delete" button
            });
        }

        // Create table
        JTable table = new JTable(tableModel);

        // Set custom renderers and editors for "Interview", "Benefit", "Skill" columns, for "Update", "Save" and "Cancel" button
        table.getColumn("Benefit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Benefit").setCellEditor(new PersistentButtonEditor(controller, "benefit"));

        table.getColumn("Interview").setCellRenderer(new ButtonRenderer());
        table.getColumn("Interview").setCellEditor(new PersistentButtonEditor(controller, "interview"));

        table.getColumn("Skill").setCellRenderer(new ButtonRenderer());
        table.getColumn("Skill").setCellEditor(new PersistentButtonEditor(controller, "skill"));

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
                    JOptionPane.showMessageDialog(RecordResultFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });
        buttonPanel.add(exitButton);

        // Add button
        JButton addRecordButton = new JButton("Add New Record");
        addRecordButton.addActionListener(e -> {
            dispose();
            AddRecordFrame addRecordFrame = new AddRecordFrame(controller, username);
            addRecordFrame.setVisible(true);
        });
        buttonPanel.add(addRecordButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // ButtonRenderer class for rendering the button
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

   
        public ButtonEditor(Controller controller, JTable table, DefaultTableModel tableModel, String type) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                if (type.equals("Update")) {
                    currentRow = table.getSelectedRow();
                    isUpdateMode = true;
                    tableModel.fireTableDataChanged();

                    String[] stateOptions = {
                        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                        "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", 
                        "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", 
                        "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", 
                        "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "DC"
                    };
                    JComboBox<String> stateComboBox = new JComboBox<>(stateOptions);
                    table.getColumn("State Abbreviation").setCellEditor(new DefaultCellEditor(stateComboBox));

                    // table.getColumn("State Abbreviation").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Company Branch").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Industry Name").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Position Name").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Year").setCellEditor(new DefaultCellEditor(new JTextField()));
                    table.getColumn("Annual Salary Amount").setCellEditor(new DefaultCellEditor(new JTextField()));
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
                                    JOptionPane.showMessageDialog(RecordResultFrame.this, "Field can not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            controller.callUpdateRecord(jobID, stateAbb, companyName, industryName, positionName, year, salaryAmount, description, degree, yearOfWork, universityName);
                            isUpdateMode = false;
                            tableModel.fireTableDataChanged();
                            JOptionPane.showMessageDialog(RecordResultFrame.this, "Update successfully!", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(RecordResultFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException en) {
                            JOptionPane.showMessageDialog(RecordResultFrame.this, en.toString(), "Number Exception", JOptionPane.ERROR_MESSAGE);
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
                            JOptionPane.showMessageDialog(RecordResultFrame.this, "Delete successfully!", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                            tableModel.fireTableDataChanged();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(RecordResultFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
    private class PersistentButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
    
        public PersistentButtonEditor(Controller controller, String type) {
            button = new JButton();
            button.setOpaque(true);
    
            button.addActionListener(e -> {
                int jobID = Integer.parseInt(button.getClientProperty("job_id").toString());
                try {
                    if (type.equals("benefit")) {
                        List<Map<String, String>> benefit = controller.DisplayRecordBenefit(jobID);
                        new RecordBenefitFrame(controller, benefit, jobID).setVisible(true); // Display benefit frame
                    } else if (type.equals("interview")) {
                        List<Map<String, String>> interview = controller.DisplayRecordInterview(jobID);
                        new RecordInterviewFrame(controller, interview, jobID).setVisible(true); // Display interview frame
                    } else if (type.equals("skill")) {
                        List<Map<String, String>> skill = controller.DisplayRecordSkill(jobID);
                        new RecordSkillFrame(controller, skill, jobID).setVisible(true); // Display skill frame
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(RecordResultFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
