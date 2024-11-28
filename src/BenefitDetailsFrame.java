import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class BenefitDetailsFrame extends JFrame {

    public BenefitDetailsFrame(List<Map<String, String>> benefitDetails) {
        setTitle("Benefit Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define column names
        String[] columnNames = {"Benefit Type", "Specific Benefit"};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table with benefit details
        if (benefitDetails != null && !benefitDetails.isEmpty()) {
            for (Map<String, String> detail : benefitDetails) {
                String benefitType = detail.get("benefitType");
                String benefitName = detail.get("benefitName");
                tableModel.addRow(new Object[]{benefitType, benefitName});
            }
        } else {
            // If no details are available, add a single row indicating no data
            tableModel.addRow(new Object[]{"No benefit details available", ""});
        }

        // Create the table
        JTable table = new JTable(tableModel);
        table.setEnabled(false); // Disable editing

        // Add table to the frame inside a scroll pane
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
