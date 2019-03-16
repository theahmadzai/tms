package immortal.client;

import immortal.constants.Gender;
import immortal.database.Database;
import immortal.models.Fare;
import immortal.models.Person;
import immortal.models.Vehicle;
import immortal.util.ComboItem;
import immortal.util.InputOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Socket socket = null;
    private OutputStreamWriter stream = null;

	public Client() {
        EventQueue.invokeLater(ClientGui::new);

        try {
            socket = new Socket("localhost", 5959);
            stream = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bf = new BufferedWriter(stream);

            Scanner sc = new Scanner(System.in);
            while(true){
                String line = sc.nextLine();
                stream.write(line + "\n");
                stream.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

    final static private class ClientGui extends JFrame {
        ClientGui() {
            setTitle("Client");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(7, 2, 10, 10));

            JTextField cnicField = new JTextField(15);
            JTextField nameField = new JTextField(15);
            JTextField ageField = new JTextField(15);

            JRadioButton maleRadio = new JRadioButton();
            maleRadio.setText("Male");
            JRadioButton femaleRadio = new JRadioButton();
            femaleRadio.setText("Female");

            ButtonGroup genderField = new ButtonGroup();
            genderField.add(maleRadio);
            genderField.add(femaleRadio);

            JPanel genderPanel = new JPanel();
            genderPanel.add(maleRadio);
            genderPanel.add(femaleRadio);

            JComboBox<ComboItem> vehicleModelField = new JComboBox<>();

            try {
                List<Fare> fares = Database.Query(Fare.class).select();

                for(Fare fare : fares) {
                    vehicleModelField.addItem(new ComboItem(fare.getId(), String.valueOf(fare.getVehicleType())));
                }

            } catch(Throwable error) {
                JOptionPane.showMessageDialog(null, error.getMessage());
            }


            JTextField vehicleNumberPlateField = new JTextField(15);
            JButton addButton = new JButton("Add Record");

            JLabel lbl = new JLabel("CNIC: ");
            lbl.setHorizontalAlignment(JLabel.RIGHT);
            add(lbl);
            add(cnicField);

            lbl = new JLabel("Name: ");
            lbl.setHorizontalAlignment(JLabel.RIGHT);
            add(lbl);
            add(nameField);

            lbl = new JLabel("Age: ");
            lbl.setHorizontalAlignment(JLabel.RIGHT);
            add(lbl);
            add(ageField);

            lbl = new JLabel("Gender: ");
            lbl.setHorizontalAlignment(JLabel.RIGHT);
            add(lbl);
            add(genderPanel);

            lbl = new JLabel("Vehicle Model: ");
            lbl.setHorizontalAlignment(JLabel.RIGHT);
            add(lbl);
            add(vehicleModelField);

            lbl = new JLabel("Vehicle Number Plate: ");
            lbl.setHorizontalAlignment(JLabel.RIGHT);
            add(lbl);
            add(vehicleNumberPlateField);

            add(new JLabel());
            add(addButton);

            addButton.addActionListener((ActionEvent e) -> {
                try {
                    InputOutput.verifyNotNull(cnicField, nameField, ageField, vehicleNumberPlateField);

                    // PERSON CLASS INSERTION TO DATABASE
                    String cnic = cnicField.getText();
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    Gender gender = Gender.MALE;
                    if(femaleRadio.isSelected()) gender = Gender.FEMALE;

                    Database.Query(Person.class).insert(new Person.Builder()
                        .withCnic(cnic)
                        .withAge(age)
                        .withName(name)
                        .withGender(gender)
                        .build()
                    );

                    // VEHICLE CLASS INSERTION TO DATABASE
                    String numberPlate = vehicleNumberPlateField.getText();
                    int fareId = ((ComboItem) vehicleModelField.getSelectedItem()).value;

                    Database.Query(Vehicle.class).insert(new Vehicle.Builder()
                        .withFareId(fareId)
                        .withNumberPlate(numberPlate)
                    );

                } catch(Throwable error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }

            });

            pack();
            setVisible(true);
        }
    }
}




