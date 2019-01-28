package immortal.server;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Server {

	final static private class UserInterface extends JFrame {
		UserInterface() {
			setTitle("Server");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			final JTabbedPane pane = new JTabbedPane();

			Dimension d = new Dimension(500, 500);
			pane.setPreferredSize(d);
			pane.setSize(d);

            pane.addTab("Activities", new ActivityPanel());
            pane.addTab("Plazas", new PlazaPanel());
            pane.addTab("Fares", new FaresPanel());

			setContentPane(pane);
			pack();
			setVisible(true);
		}
	}

	public Server() {
		EventQueue.invokeLater(UserInterface::new);
	}

}
