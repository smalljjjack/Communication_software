import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class chatServer {
	public static void main(String args[]){
			new serFrame("Server").launchFrame();
			try{
				ServerSocket SerS = new ServerSocket(9274);
				while(true){
					Socket clientS = SerS.accept();
System.out.println("A client had connected");
				}
			}catch(IOException e){
				e.printStackTrace();
			}
	}
}

class serFrame extends Frame {
	serFrame(String Server) {
		super(Server);
	}

	TextField SerTF = new TextField();
	TextArea SerTA = new TextArea();
	Panel SerP = new Panel();

	public void launchFrame() {
		SerTF.setSize(500, 500);
		SerTA.setSize(500, 500);
		Button SerB = new Button("send");
		SerB.setSize(100, 100);
		SerP.add(SerB, BorderLayout.CENTER);
		add(SerP, BorderLayout.CENTER);
		add(SerTF, BorderLayout.SOUTH);
		SerP.add(SerB, BorderLayout.CENTER);
		add(SerTA, BorderLayout.NORTH);
		SerB.addActionListener(new SerActionListener());
		SerTF.addActionListener(new SerActionListener());
		addWindowListener(new SerWindowListener());
		pack();
		setVisible(true);

	}

	private class SerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String s = SerTF.getText();
			SerTA.setText("Server had said:" +"   " +s);
			SerTF.setText("");
		}

	}
	
	private class SerWindowListener extends WindowAdapter{

		public void windowClosing(WindowEvent e) {
			System.exit(-1);
		}
	}
}