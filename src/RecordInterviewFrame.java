import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class RecordInterviewFrame extends JFrame {
    private boolean isUpdateMode = false;  // Flag indicating if the table is in update mode.
    private int currentRow = -1;  // The index number of the row currently being updated.
    private List<Map<String, String>> originalResults = new ArrayList<>(); // Store the original values of a row before editing.

    /**
     * Constructor of the RecordInterviewFrame class.
     * 
     * @param controller The controller.
     * @param interviewDetails List of maps containing interview details to be displayed in the table.
     * @param job_id The job ID associated with the interview.
     */
    public RecordInterviewFrame(Controller controller, List<Map<String, String>> interviewDetails, int job_id) {
        setTitle("Interview Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define column names
        String[] columnNames = {"Interview Type", "Description", "Update", "Save", "Cancel", "Delete"};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 2 && column <= 5) {
                    return true;
                }
                if (isUpdateMode && column >= 0 && column <= 1 && row == currentRow) {
                    return true;
                }
                return false;
            }
        };

        // Populate the table with interview details
        if (interviewDetails != null && !interviewDetails.isEmpty()) {
            for (Map<String, String> detail : interviewDetails) {
                String interviewType = detail.get("interviewType");
                String description = detail.get("interviewDescription");
                tableModel.addRow(new Object[]{interviewType, description, "Update", "Save", "Cancel", "Delete"});
            }
        } else {
            // If no details are available, add a single row indicating no data
            tableModel.addRow(new Object[]{"No interview details available", "", "Update", "Save", "Cancel", "Delete"});
        }

        // Create the table
        JTable table = new JTable(tableModel);

        // Set custom renderers and editors for "Update", "Save" and "Cancel" button
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

    /**
     * ButtonRenderer class renders the buttons in the "Update", "Save", "Cancel", and "Delete" columns.
     */
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

    /**
     * ButtonEditor class handles the button click actions in the table.
     */
    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JButton button;  // The button used for each action.
   
        /**
         * Constructor of the ButtonEditor class.
         * 
         * @param controller The controller.
         * @param table The JTable containing the benefit details.
         * @param tableModel The DefaultTableModel used by the table.
         * @param type The action type.
         * @param job_id The job ID associated with the interview.
         */
        public ButtonEditor(Controller controller, JTable table, DefaultTableModel tableModel, String type, int job_id) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                if (type.equals("Update")) {
                    currentRow = table.getSelectedRow();
                    isUpdateMode = true;
                    tableModel.fireTableDataChanged();

                    // Store the original values before making changes.
                    originalResults.clear();
                    Map<String, String> originalRow = new HashMap<>();
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        originalRow.put(table.getColumnName(col), table.getValueAt(currentRow, col).toString());
                    }
                    originalResults.add(originalRow);

                    table.getColumn("Description").setCellEditor(new DefaultCellEditor(new JTextField()));

                    String[] interviewOptions = {"Online Assessment", "Pre-Recorded Interview", "Behavioral Interview", "Technical Interview", "Supervisor Interview"};
                    JComboBox<String> degreeComboBox = new JComboBox<>(interviewOptions);
                    table.getColumn("Interview Type").setCellEditor(new DefaultCellEditor(degreeComboBox));
                } else if (type.equals("Save")) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            String interviewType = (String) tableModel.getValueAt(selectedRow, 0);
                            String description = (String) tableModel.getValueAt(selectedRow, 1);
                            if (interviewType.isEmpty() || description.isEmpty()) {
                                JOptionPane.showMessageDialog(RecordInterviewFrame.this, "Field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                controller.callUpdateInterview(job_id, interviewType, description);
                                isUpdateMode = false;
                                tableModel.fireTableDataChanged();
                                JOptionPane.showMessageDialog(RecordInterviewFrame.this, "Update successfully!", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(RecordInterviewFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException en) {
                            JOptionPane.showMessageDialog(RecordInterviewFrame.this, en.toString(), "Number Exception", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                } else if (type.equals("Cancel")) {
                    isUpdateMode = false;
                    if (!originalResults.isEmpty()) {
                        Map<String, String> originalRow = originalResults.get(0);
                        for (int col = 0; col < table.getColumnCount(); col++) {
                            table.setValueAt(originalRow.get(table.getColumnName(col)), currentRow, col);
                        }
                    }
                    tableModel.fireTableDataChanged();
                } else if (type.equals("Delete")) {
                    int selectedRow = table.getSelectedRow();
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();  // Stop the active editor
                    }
                    if (selectedRow != -1) {
                        try {
                            controller.callDeleteInterview(job_id);
                            tableModel.removeRow(selectedRow);
                            tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
                            JOptionPane.showMessageDialog(RecordInterviewFrame.this, "Delete successfully!", "Update Message", JOptionPane.INFORMATION_MESSAGE);
                            int newSelectedRow = -1;
                            if (tableModel.getRowCount() > 0) {
                                if (selectedRow < tableModel.getRowCount()) {
                                    newSelectedRow = selectedRow;
                                } else {
                                    newSelectedRow = selectedRow - 1;
                                }
                            } else {
                                tableModel.addRow(new Object[]{"No skill details available", "", "Update", "Save", "Cancel", "Delete"});
                            }
                            if (newSelectedRow != -1) {
                                table.setRowSelectionInterval(newSelectedRow, newSelectedRow);
                            } else {
                                table.clearSelection();
                            }
                            tableModel.fireTableDataChanged();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(RecordInterviewFrame.this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
