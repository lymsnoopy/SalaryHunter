import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private controller controller;
    private List<Map<String, String>> results;
    private String detailType; // "interview" or "benefit"

    public ButtonEditor(JCheckBox checkBox, controller controller, List<Map<String, String>> results, String detailType) {
        super(checkBox);
        this.controller = controller;
        this.results = results;
        this.detailType = detailType;

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            int selectedRow = ((JTable) getComponent()).getSelectedRow();
            Map<String, String> selectedRowData = results.get(selectedRow);

            String username = selectedRowData.get("username");
            String positionName = selectedRowData.get("positionName");

            try {
                if ("interview".equals(detailType)) {
                    // Fetch and display interview details
                    List<Map<String, String>> interviewDetails = controller.getInterviews(username, positionName);
                    new InterviewDetailsFrame(interviewDetails).setVisible(true); // Display interview frame
                } else if ("benefit".equals(detailType)) {
                    // Fetch and display benefit details
                    List<Map<String, String>> benefitDetails = controller.getBenefits(username, positionName);
                    new BenefitDetailsFrame(benefitDetails).setVisible(true); // Display benefit frame
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching details: " + ex.getMessage());
            }
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
