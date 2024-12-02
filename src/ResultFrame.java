import java.awt.*;
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

public class ResultFrame extends JFrame {

    public ResultFrame(List<Map<String, String>> results, Controller controller, String username) {
        setTitle("Search Result");
        setSize(2000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(ResultFrame.this, "Search has no result.", "Message", JOptionPane.ERROR_MESSAGE);
        }
            
        // Define column names
        String[] columnNames = {
            "Area", "State Abbreviation", "Company Branch", "Industry Name", "Job ID", "Position Name",
            "Year", "Salary Amount", "Position Description", "Degree Level", "Year of Work", 
            "University Name", "Benefit", "Interview", "Skills"
        };

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 12 && column <= 14) {
                    return true;
                }
                return false;
            }
        };
        for (Map<String, String> row : results) {
            tableModel.addRow(new Object[]{
                row.get("area"),
                row.get("stateAbbr"),
                row.get("companyBranch"),
                row.get("industryName"),
                row.get("jobID"),
                row.get("positionName"),
                row.get("year"),
                row.get("salaryAmount"),
                row.get("positionDescription"),
                row.get("degree"),
                row.get("yearOfWork"),
                row.get("university"),
                "View Details",  // For "Benefit" column
                "View Details",  // For "Interview" column
                "View Details",  // For "Skills" column
            });
        }

        // Create table
        JTable table = new JTable(tableModel);

        // Set custom renderers and editors for "Interview", "Benefit"
        table.getColumn("Benefit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Benefit").setCellEditor(new PersistentButtonEditor(controller, "benefit"));

        table.getColumn("Interview").setCellRenderer(new ButtonRenderer());
        table.getColumn("Interview").setCellEditor(new PersistentButtonEditor(controller, "interview"));

        table.getColumn("Skills").setCellRenderer(new ButtonRenderer());
        table.getColumn("Skills").setCellEditor(new PersistentButtonEditor(controller, "skill"));

        // Add table to the frame
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Back button
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Search");
        backButton.addActionListener(e -> {
            dispose();
            SearchFrame searchFrame = new SearchFrame(controller, username);
            searchFrame.setVisible(true);
        });
        buttonPanel.add(backButton);

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
                        List<Map<String, String>> benefit = new ArrayList<>();
                        ResultSet rsb = controller.DisplayRecordBenefit(jobID);
                        while (rsb.next()) {
                            Map<String, String> rowb = new HashMap<>();
                            rowb.put("benefitType", rsb.getString("benefit_type"));
                            rowb.put("benefitName", rsb.getString("benefit_name"));
                            benefit.add(rowb);
                        }
                        new SearchBenefitFrame(controller, benefit, jobID).setVisible(true); // Display benefit frame
                    } else if (type.equals("interview")) {
                        List<Map<String, String>> interview = new ArrayList<>();
                        ResultSet rsi = controller.DisplayRecordInterview(jobID);
                        while (rsi.next()) {
                            Map<String, String> rowi = new HashMap<>();
                            rowi.put("interviewType", rsi.getString("interview_type"));
                            rowi.put("interviewDescription", rsi.getString("description"));
                            interview.add(rowi);
                        }
                        new SearchInterviewFrame(controller, interview, jobID).setVisible(true); // Display interview frame
                    } else if (type.equals("skill")) {
                        List<Map<String, String>> skill = new ArrayList<>();
                        ResultSet rss = controller.DisplayRecordSkill(jobID);
                        while (rss.next()) {
                            Map<String, String> rows = new HashMap<>();
                            rows.put("skillName", rss.getString("skill_name"));
                            skill.add(rows);
                        }
                        new SearchSkillFrame(controller, skill, jobID).setVisible(true); // Display skill frame
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ResultFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            
            });
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(value != null ? value.toString() : "");
            String jobID = table.getModel().getValueAt(row, 4).toString();
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
