package immortal.server;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import immortal.constants.Gender;
import immortal.database.Query;
import immortal.models.Fare;
import immortal.models.Person;
import immortal.models.Plaza;
import immortal.models.Trip;
import immortal.models.Vehicle;

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

//		Query.where("id",3).all().select(Fare.class);
		Query.insert( new Fare.Builder()
			.withAmount(1)
			.withVehicleType(2)
			.build()
		);
		System.out.println(Query.all().delete(Fare.class));

		// Events
		fareAddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(amountField.getText().length() < 1 || vehicleField.getText().length() < 1) {
					JOptionPane.showMessageDialog(FaresPanel.this, "Invalid Entry!");
					return;
				}

				Query.insert( new Fare.Builder()
					.withAmount(Integer.parseInt(amountField.getText()))
					.withVehicleType(Integer.parseInt(vehicleField.getText()))
					.build()
				);

				Query.insert( new Plaza.Builder()
					.withPassword("hello")
					.build()
				);

				Query.insert( new Person.Builder()
					.withCnic("1221 123")
					.withName("Javed")
					.withAge(15)
					.withGender(Gender.MALE)
					.build()
				);

				Query.insert( new Vehicle.Builder()
					.withNumberPlate("234")
					.withModel("151")
					.withFareId(2)
					.build()
				);

				Query.insert( new Trip.Builder()
					.withPlazaId(1)
					.withPersonId(2)
					.withVehicleId(2)
					.withDate("12-123-123")
					.build()
				);

				dtm.addRow(new Object[]{1, amountField.getText(), vehicleField.getText()});
				amountField.setText(null);
				vehicleField.setText(null);
			}
		});
	}
}
