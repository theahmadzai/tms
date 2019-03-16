package immortal.server;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("serial")
public class Server {
    private Socket socket = null;
    private ServerSocket server = null;

    public static class EchoThread extends Thread {
        private Socket socket = null;
        private InputStreamReader inputStream = null;
        private DataOutputStream outputStream = null;

        EchoThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                inputStream = new InputStreamReader(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
            } catch(IOException ex) {
                ex.printStackTrace();
            }

            BufferedReader bf = new BufferedReader(inputStream);

            String data;
            try {
                while((data = bf.readLine()) != null) {
                    System.out.println(data);
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

	public Server() {
	    EventQueue.invokeLater(ServerGui::new);

	    try {
	        server = new ServerSocket(5959);
            System.out.println("Server Started");
        } catch(IOException ex) {
	        ex.printStackTrace();
        }

        while(true) {
            try {
                socket = server.accept();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            new EchoThread(socket).start();
        }
	}

    final static private class ServerGui extends JFrame {
        ServerGui() {
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
}
