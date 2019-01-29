package immortal.server;

import immortal.database.Database;
import immortal.models.Plaza;
import immortal.util.InputOutput;
import immortal.util.TableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@SuppressWarnings("serial")
class PlazaPanel extends JPanel {
	PlazaPanel() {
        final JPasswordField passwordField = new JPasswordField();
        final JTextField nameField = new JTextField();
        final JButton plazaAddButton = new JButton("Add Plaza");
        final JTable plazaTable = new JTable();

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(new JLabel("Password: "), gc);

        gc.gridy = 1;
        add(new JLabel("Plaza Name:"), gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        passwordField.setPreferredSize(new Dimension(150, 20));
        passwordField.setMinimumSize(new Dimension(150, 20));
        add(passwordField, gc);

        gc.gridy = 1;
        nameField.setPreferredSize(new Dimension(150, 20));
        nameField.setMinimumSize(new Dimension(150, 20));
        add(nameField, gc);

        gc.gridy = 2;
        add(plazaAddButton, gc);

        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 10;
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 3;
        add(new JScrollPane(plazaTable), gc);

        final TableModel tableModel = new TableModel(plazaTable).setColumns("#", "Password", "Name");

        try {
            List<Plaza> plazas = Database.Query(Plaza.class).select();

            for (Plaza plaza : plazas) {
                tableModel.addRow(plaza.getId(), plaza.getPassword(), plaza.getName());
            }

            plazaAddButton.addActionListener((ActionEvent e) -> {
                try {
                    InputOutput.verifyNotNull(passwordField, nameField);

                    String password = String.valueOf(passwordField.getPassword());
                    String name = nameField.getText();

                    int key = Database.Query(Plaza.class).insert(new Plaza.Builder()
                        .withPassword(password)
                        .withName(name)
                        .build()
                    );

                    tableModel.addRow(key, password, name);
                    TableModel.setEmpty(passwordField, nameField);

                } catch(Throwable error) {
                    JOptionPane.showMessageDialog(this, error.getMessage());
                }
            });
        } catch(Throwable error) {
            JOptionPane.showMessageDialog(this, error.getMessage());
        }
    }
}
