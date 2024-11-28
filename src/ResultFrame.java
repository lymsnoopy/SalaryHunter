import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ResultFrame extends JFrame {

    public ResultFrame(List<Map<String, String>> results, controller controller, String username) {
        setTitle("Search Results");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define column names
        String[] columnNames = {
            "Username", "Company Branch", "State Abbreviation", "Area", "Position Name",
            "Position Description", "Year", "Salary Amount", "Interview", "Benefit"
        };

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Map<String, String> row : results) {
            tableModel.addRow(new Object[]{
                row.get("username"),
                row.get("companyBranch"),
                row.get("stateAbbr"),
                row.get("area"),
                row.get("positionName"),
                row.get("positionDescription"),
                row.get("year"),
                row.get("salaryAmount"),
                "View Details", // For "Interview" column
                "View Details"  // For "Benefit" column
            });
        }

        // Create table
        JTable table = new JTable(tableModel);

        // Set custom renderers and editors for "Interview" and "Benefit" columns
        table.getColumn("Interview").setCellRenderer(new ButtonRenderer());
        table.getColumn("Interview").setCellEditor(new PersistentButtonEditor(controller, results, "interview"));

        table.getColumn("Benefit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Benefit").setCellEditor(new PersistentButtonEditor(controller, results, "benefit"));

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

    // PersistentButtonEditor class for handling "View Details" button clicks without disappearing
    private static class PersistentButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String type;
        private controller controller;
        private List<Map<String, String>> results;
        private int currentRow = -1;
    
        public PersistentButtonEditor(controller controller, List<Map<String, String>> results, String type) {
            this.controller = controller;
            this.results = results;
            this.type = type;
    
            button = new JButton();
            button.setOpaque(true);
    
            button.addActionListener(e -> {
                if (currentRow >= 0) {
                    Map<String, String> selectedRow = results.get(currentRow);
                    String username = selectedRow.get("username");
                    String positionName = selectedRow.get("positionName");
    
                    try {
                        if (type.equals("interview")) {
                            List<Map<String, String>> interviewDetails = controller.getInterviews(username, positionName);
                            new InterviewDetailsFrame(interviewDetails).setVisible(true); // Display interview frame
                        } else if (type.equals("benefit")) {
                            List<Map<String, String>> benefitDetails = controller.getBenefits(username, positionName);
                            new BenefitDetailsFrame(benefitDetails).setVisible(true); // Display benefit frame
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(button, "Error fetching details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(value != null ? value.toString() : "");
            currentRow = row; // Store the current row for context
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    
        @Override
        public boolean stopCellEditing() {
            currentRow = -1; // Reset the row context
            return super.stopCellEditing();
        }
    }
}
