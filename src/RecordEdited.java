import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RecordEdited {

    JFrame record = new JFrame("User Record");
    JTable recordTable = new JTable();
    JScrollPane scrollPane = new JScrollPane(recordTable);

    public RecordEdited(controller controller, String username) {
        try {
            ResultSet userRecord = controller.DisplayRecord(username);
            ResultSetMetaData metaData = userRecord.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }

            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            while (userRecord.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = userRecord.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
            recordTable.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        record.getContentPane().add(scrollPane);
        record.setSize(1200, 800);
        record.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        record.setVisible(true);
    }
}
