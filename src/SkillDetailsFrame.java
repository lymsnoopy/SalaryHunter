import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SkillDetailsFrame extends JFrame {

    public SkillDetailsFrame(List<Map<String, String>> skillDetails) {
        setTitle("Skill Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define column names
        String[] columnNames = {"Skill Name"};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table with interview details
        if (skillDetails != null && !skillDetails.isEmpty()) {
            for (Map<String, String> detail : skillDetails) {
                String skillName = detail.get("skillName");
                tableModel.addRow(new Object[]{skillName});
            }
        } else {
            // If no details are available, add a single row indicating no data
            tableModel.addRow(new Object[]{"No skill details available", ""});
        }

        // Create the table
        JTable table = new JTable(tableModel);
        table.setEnabled(false); // Disable editing

        // Add table to the frame inside a scroll pane
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
