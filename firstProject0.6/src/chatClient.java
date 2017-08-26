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
	DataInputStream gMesIn = null;
	TextField txF = new TextField();
	TextArea txA = new TextArea();
	Panel cent = new Panel();
	Font jack = new Font("Jack",0,40);
	ServerSocket Client = null;
	private boolean gMes = false;
	
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
			gMes = true;
			new Thread(new ClientThread()).start();
System.out.println("client had connected");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
		try {
			ClientS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getMessage(){		
	}
	
	private class ClientThread implements Runnable{
		String otherC = null;
		public void run() {
				try{
					while(gMes){
						try {
							gMesIn = new DataInputStream(
								ClientS.getInputStream());
							otherC = gMesIn.readUTF();
							txA.setText(txA.getText()+otherC+"\n");
System.out.println("one client said"+" "+otherC);						
						} catch (IOException e) {
							gMes = false;
							gMesIn.close();
							gMesIn = null;
						}	
					}
				}catch(Exception e){
					gMes =false;
				}
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
			//txA.setText("client had send:" + clieSay);
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