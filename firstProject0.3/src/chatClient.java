import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class chatClient {
	public static void main(String[] args) {
		new chatFrame("Client").launchFrame();
	}
}

class chatFrame extends Frame{
	chatFrame(String client){
		super("client");
	} 
	String s;
	TextField txF = new TextField();
	TextArea txA = new TextArea();
	Panel cent = new Panel();
	
	public void launchFrame(){
		Button send = new  Button("send");
		send.setSize(100, 100);
		txF.addActionListener(new myActionListener());
		send.addActionListener(new myActionListener());
		cent.add(send, BorderLayout.WEST);
		
		this.addWindowListener(new myWindowMointor());
		add(txF, BorderLayout.SOUTH);
		add(txA, BorderLayout.NORTH);
		add(cent, BorderLayout.CENTER);
		setBounds(500, 500, 1500, 1500);
		pack();
		setVisible(true);
		connect();
	}
	
	public void connect(){
		try{
			Socket ClientS = new Socket("127.0.0.1", 9274);
System.out.println("client had connected");
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}

	class myWindowMointor extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}
	
	class myActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String s = txF.getText();
			System.out.println(s);
			txA.setText("client had send:" + s);
			txF.setText("");
		}
		
	}
}
