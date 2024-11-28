import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            "Username", "Company Name", "State", "Area", "Position", 
            "Description", "Year", "Salary", "Interview", "Interview Description"
        };

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Map<String, String> row : results) {
            tableModel.addRow(new Object[]{
                row.get("username"), row.get("company_name"), row.get("state"), 
                row.get("area"), row.get("position"), row.get("position_description"), 
                row.get("year"), row.get("salary_amount"), row.get("interview"), 
                row.get("interview_description")
            });
        }

        // Create table
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Search");

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the result frame and return to the search frame
                dispose();
                SearchFrame searchFrame = new SearchFrame(controller, username);
                searchFrame.setVisible(true);
            }
        });

        // Add button to the panel
        buttonPanel.add(backButton);

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
