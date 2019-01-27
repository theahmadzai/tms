package immortal.server;

import immortal.database.Database;
import immortal.models.Plaza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class PlazaPanel extends JPanel {
    private final JTextField passwordField = new JTextField();
    private final JButton plazaAddButton = new JButton("Add Plaza");
    private final JTable plazaTable = new JTable();

	PlazaPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.5;
        gc.weighty = 2;

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(new JLabel("Password: "), gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        passwordField.setPreferredSize(new Dimension(150, 20));
        passwordField.setMinimumSize(new Dimension(150, 20));
        add(passwordField, gc);

        gc.gridy = 2;
        add(plazaAddButton, gc);

        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 8;
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 3;
        add(new JScrollPane(plazaTable), gc);

        DefaultTableModel dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(new String[] {
            "#", "Password"
        });

        plazaTable.setModel(dtm);

        List<Plaza> plazas = Database.Query(Plaza.class).select();

        for (Plaza plaza : plazas) {
            dtm.addRow(new Object[]{plaza.getId(), plaza.getPassword()});
        }
    }

}
