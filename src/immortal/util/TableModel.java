package immortal.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
    public TableModel(JTable table) {
        super(0,0);
        table.setModel(this);
    }

    public TableModel setColumns(String ...columns) {
        setColumnIdentifiers(columns);
        return this;
    }

    public void addRow(Object ...entries) {
        super.addRow(entries);
    }

    public static void setEmpty(JTextField ...fields) {
        for(JTextField field : fields) {
            field.setText(null);
        }
    }
}
