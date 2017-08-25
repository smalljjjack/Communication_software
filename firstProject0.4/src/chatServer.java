import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class chatServer {
	public static void main(String args[]){
			new serFrame("Server").launchFrame();
	}
}

class serFrame extends Frame {
	serFrame(String Server) {
		super(Server);
	}
	ServerSocket SerS = null;
	Socket ClientS = null;
	TextField SerTF = new TextField();
	TextArea SerTA = new TextArea();
	Panel SerP = new Panel();

	public void launchFrame() {
		SerTF.setSize(1000, 1000);
		SerTA.setSize(1000, 1000);
		Button SerB = new Button("send");
		SerB.setSize(200, 200);
		add(SerP, BorderLayout.CENTER);
		SerP.add(SerB, BorderLayout.CENTER);
		add(SerTF, BorderLayout.SOUTH);
		add(SerTA, BorderLayout.NORTH);
		SerB.addActionListener(new SerActionListener());
		SerTF.addActionListener(new SerActionListener());
		addWindowListener(new SerWindowListener());
		pack();
		setVisible(true);
		connect();
	}
	
	public void connect(){
		try{ 
			ServerSocket SerS = new ServerSocket(9274);
			while(true){
				Socket ClientS = SerS.accept();
System.out.println("A client had connected");
				DataInputStream clis = new DataInputStream(
				ClientS.getInputStream());
				String clisay = clis.readUTF();
				SerTA.setText("Client said" + clisay);
				clis.reset();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private class SerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String SerSa = SerTF.getText();
			SerTA.setText("Server had said:" +"   " +SerSa);
			SerTF.setText("");
		}
	}
	
	private class SerWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(-1);
		}
	}
}