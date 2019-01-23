package immortal.server;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
	};
	
	public Server() {			
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Server.UserInterface();
			}
		});
	}
	
}
