package immortal.server;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import immortal.database.Database;
import immortal.models.Fare;

@SuppressWarnings("serial")
public class FaresPanel extends JPanel {
	private final JTextField amountField = new JTextField();
	private final JTextField vehicleField = new JTextField();
	private final JButton fareAddButton = new JButton("Add Fare");
	private final JTable faresTable = new JTable();

	FaresPanel() {
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
		gc.gridwidth = 3;
		gc.gridx = 0;
		gc.gridy = 3;
		add(new JScrollPane(faresTable), gc);

		DefaultTableModel dtm = new DefaultTableModel(0,0);
		dtm.setColumnIdentifiers(new String[] {
				"#", "Amount", "Vehicle Type"
		});
		faresTable.setModel(dtm);

		List<Fare> fares = Database.Query(Fare.class).select();

		fares.forEach((Fare fare) -> {
		    dtm.addRow(new Object[] {1, fare.getAmount(), fare.getVehicleType()});
		});

		// Events
		fareAddButton.addActionListener((ActionEvent e) -> {
			if(amountField.getText().length() < 1 || vehicleField.getText().length() < 1) {
				JOptionPane.showMessageDialog(FaresPanel.this, "Invalid Entry!");
				return;
			}

			Database.Query(Fare.class).insert(new Fare.Builder()
				.withAmount(Integer.parseInt(amountField.getText()))
				.withVehicleType(Integer.parseInt(vehicleField.getText()))
				.build()
			);

			dtm.addRow(new Object[]{1, amountField.getText(), vehicleField.getText()});
			amountField.setText(null);
			vehicleField.setText(null);
		});
	}
}
