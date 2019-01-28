package immortal.server;

import immortal.database.Database;
import immortal.models.Fare;
import immortal.util.InputOutput;
import immortal.util.TableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@SuppressWarnings("serial")
class FaresPanel extends JPanel {
	FaresPanel() {
        final JTextField amountField = new JTextField();
        final JTextField vehicleField = new JTextField();
        final JButton fareAddButton = new JButton("Add Fare");
        final JTable faresTable = new JTable();

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0.5;
		gc.weighty = 0.5;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy = 0;
		add(new JLabel("Amount: "), gc);

		gc.gridy = 1;
		add(new JLabel("Vehicle Type: "), gc);

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		amountField.setPreferredSize(new Dimension(150, 20));
		amountField.setMinimumSize(new Dimension(150, 20));
		add(amountField, gc);

		gc.gridy = 1;
		vehicleField.setPreferredSize(new Dimension(150, 20));
		vehicleField.setMinimumSize(new Dimension(150, 20));
		add(vehicleField, gc);

		gc.gridy = 2;
		add(fareAddButton, gc);

		gc.fill = GridBagConstraints.BOTH;
		gc.weighty = 10;
		gc.gridwidth = 2;
		gc.gridx = 0;
		gc.gridy = 3;
		add(new JScrollPane(faresTable), gc);

		final TableModel tableModel = new TableModel(faresTable).setColumns("#", "Amount", "Vehicle Type");

		try {
		    List<Fare> fares = Database.Query(Fare.class).select();

            for (Fare fare : fares) {
                tableModel.addRow(fare.getId(), fare.getAmount(), fare.getVehicleType());
            }

            fareAddButton.addActionListener((ActionEvent e) -> {
                try {
                    InputOutput.verifyNotNull(amountField, vehicleField);

                    int amount = Integer.parseInt(amountField.getText());
                    int vehicleType = Integer.parseInt(vehicleField.getText());

                    int key = Database.Query(Fare.class).insert(new Fare.Builder()
                        .withAmount(amount)
                        .withVehicleType(vehicleType)
                        .build()
                    );

                    tableModel.addRow(key, amount, vehicleType);
                    TableModel.setEmpty(amountField, vehicleField);
                } catch(Throwable error) {
                    JOptionPane.showMessageDialog(this, error.getMessage());
                }
            });
        } catch(Throwable error) {
            JOptionPane.showMessageDialog(this, error.getMessage());
        }
	}
}
