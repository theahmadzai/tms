package immortal.client;

import javax.swing.*;

public class Client {
    final static private class ClientGui extends JFrame {
        ClientGui() {
            setTitle("Client");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




            pack();
            setVisible(true);
        }
    }

	public Client() {

	}
}
