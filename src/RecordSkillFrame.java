import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class RecordSkillFrame extends JFrame {
    private boolean isUpdateMode = false;
    private int currentRow = -1;

    public RecordSkillFrame(Controller controller, List<Map<String, String>> skillDetails, int job_id) {
        setTitle("Skill Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define column names
        String[] columnNames = {"Skill Name", "Update", "Save", "Cancel", "Delete"};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 1 && column <= 4) {
                    return true;
                }
                if (isUpdateMode && column == 0 && row == currentRow) {
                    return true;
                }
                return false;
            }
        };

        // Populate the table with interview details
        if (skillDetails != null && !skillDetails.isEmpty()) {
            for (Map<String, String> detail : skillDetails) {
                String skillName = detail.get("skillName");
                tableModel.addRow(new Object[]{skillName, "Update", "Save", "Cancel", "Delete"});
            }
        } else {
            // If no details are available, add a single row indicating no data
            tableModel.addRow(new Object[]{"No skill details available", ""});
        }

        // Create the table
        JTable table = new JTable(tableModel);

        table.getColumn("Update").setCellRenderer(new ButtonRenderer());
        table.getColumn("Update").setCellEditor(new ButtonEditor(controller, table, tableModel, "Update", job_id));

        table.getColumn("Save").setCellRenderer(new ButtonRenderer());
        table.getColumn("Save").setCellEditor(new ButtonEditor(controller, table, tableModel, "Save", job_id));

        table.getColumn("Cancel").setCellRenderer(new ButtonRenderer());
        table.getColumn("Cancel").setCellEditor(new ButtonEditor(controller, table, tableModel, "Cancel", job_id));

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(controller, table, tableModel, "Delete", job_id));

        // Add table to the frame inside a scroll pane
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // ButtonRenderer class for rendering the "Update", "Save", "Cancel", and "Delete" button
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

        public ButtonEditor(Controller controller, JTable table, DefaultTableModel tableModel, String type, int job_id) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                if (type.equals("Update")) {
                    currentRow = table.getSelectedRow();
                    isUpdateMode = true;
                    tableModel.fireTableDataChanged();
                    table.getColumn("Skill Name").setCellEditor(new DefaultCellEditor(new JTextField()));
                } else if (type.equals("Save")) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            String skillName = (String) tableModel.getValueAt(selectedRow, 0);
                            controller.callUpdateSkill(job_id, skillName);
                            isUpdateMode = false;
                            tableModel.fireTableDataChanged();
                            JOptionPane.showMessageDialog(RecordSkillFrame.this, "Update successfully!", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(RecordSkillFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException en) {
                            JOptionPane.showMessageDialog(RecordSkillFrame.this, en.toString(), "Number Exception", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                } else if (type.equals("Cancel")) {
                    isUpdateMode = false;
                    tableModel.fireTableDataChanged();
                } else if (type.equals("Delete")) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            controller.callDeleteSkill(job_id);
                            tableModel.removeRow(selectedRow);
                            tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
                            JOptionPane.showMessageDialog(RecordSkillFrame.this, "Delete successfully!", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                            tableModel.fireTableDataChanged();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(RecordSkillFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
}
