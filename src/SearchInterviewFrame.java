import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchInterviewFrame extends JFrame {

    public SearchInterviewFrame(Controller controller, List<Map<String, String>> interviewDetails, int job_id) {
        setTitle("Interview Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define column names
        String[] columnNames = {"Interview Type", "Description"};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table with interview details
        if (interviewDetails != null && !interviewDetails.isEmpty()) {
            for (Map<String, String> detail : interviewDetails) {
                String interviewType = detail.get("interviewType");
                String description = detail.get("interviewDescription");
                tableModel.addRow(new Object[]{interviewType, description});
            }
        } else {
            // If no details are available, add a single row indicating no data
            tableModel.addRow(new Object[]{"No interview details available", ""});
        }

        // Create the table
        JTable table = new JTable(tableModel);
        table.setEnabled(false); 

        // Add table to the frame inside a scroll pane
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
