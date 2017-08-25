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
	Socket ClientS = null;
	TextField txF = new TextField();
	TextArea txA = new TextArea();
	Panel cent = new Panel();
	
	public void launchFrame(){
		setLocation(500, 500);
		Button send = new  Button("send");
		send.setSize(200, 200);
		send.addActionListener(new myActionListener());
		txF.addActionListener(new myActionListener());
		this.addWindowListener(new myWindowMointor());
		cent.add(send, BorderLayout.CENTER);
		add(txF, BorderLayout.SOUTH);
		add(txA, BorderLayout.NORTH);
		add(cent, BorderLayout.CENTER);
		pack();
		setVisible(true);
		connect();
	}
	
	public void connect(){
		try{
			ClientS = new Socket("127.0.0.1", 9274);
System.out.println("client had connected");
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	class myWindowMointor extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
	
	class myActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String clieSay = txF.getText();
			txA.setText("client had send:" + clieSay);
			txF.setText("");
			try{
				DataOutputStream clis = new DataOutputStream(ClientS.getOutputStream());
				clis.writeUTF(clieSay);
				clis.flush();
				clis.close();
			}catch(IOException e5){
				e5.printStackTrace();
			}
System.out.println(clieSay);
		}
		
	}
}
