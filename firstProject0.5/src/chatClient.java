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
	DataOutputStream clis = null;
	TextField txF = new TextField();
	TextArea txA = new TextArea();
	Panel cent = new Panel();
	Font jack = new Font("Jack",0,40);
	
	public void launchFrame(){
		this.setSize(1000,1000);
		this.setLocation(500, 500);
		txF.setFont(jack);
		txA.setFont(jack);
		Button send = new  Button("send");
		send.setFont(jack);
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
	
	public void disconnect(){
		try {
			ClientS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class myWindowMointor extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			disconnect();
			System.exit(0);
		}
	}
	
	class myActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String clieSay = txF.getText();
			txA.setText("client had send:" + clieSay);
			txF.setText("");
			try{
				clis = new DataOutputStream(ClientS.getOutputStream());
				clis.writeUTF(clieSay);
				clis.flush();
			}catch(IOException e5){
				e5.printStackTrace();
			}
System.out.println(clieSay);
		}
		
	}
}
