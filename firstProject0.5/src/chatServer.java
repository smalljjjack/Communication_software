import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class chatServer {
	public static void main(String args[]){
			new serFrame("Server").launchFrame();
	}
}

class serFrame extends Frame {
	serFrame(String Server) {
		super(Server);
	}
	Font jack = new Font("Jack",0,40);
	ServerSocket SerS = null;
	Socket ClientS = null;
	TextField SerTF = new TextField();
	TextArea SerTA = new TextArea();
	Panel SerP = new Panel();
	boolean started = false;
	List<SerThread> SerThreads = new ArrayList<SerThread>();

	public void launchFrame() {
		SerTF.setFont(jack);
		SerTA.setFont(jack);
		this.setSize(1000,1000);
		Button SerB = new Button("send");
		SerB.setFont(jack);
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
			SerS = new ServerSocket(9274);
		}
		catch(IOException e1){
			System.out.println("Server is working now");
			e1.printStackTrace();
			System.exit(-1);
		}	
		try{	
			started = true;
			while(started){
				ClientS = SerS.accept();
System.out.println("A client had connected");
				SerThread SerTre= new SerThread(ClientS);
				SerThreads.add(SerTre);
				new Thread(SerTre).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				ClientS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	public void start(){
		
	}
	class SerThread implements Runnable{
		private Socket Clients;
		private DataInputStream clis = null;
		private DataOutputStream dos = null;
		private boolean proc = false;
		private SerThread(Socket ClientS){
			this.Clients = ClientS;
			try{
				clis = new DataInputStream(
						ClientS.getInputStream());
				dos = new DataOutputStream(
						ClientS.getOutputStream());
				proc = true;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		public void run(){
			try{
				while(proc){
					try{String clisay = clis.readUTF();
						SerTA.setText("Client said" + clisay);
					}catch(Exception e){
System.out.println("One client had left");
						proc = false;
					}
				}
			}catch(Exception E){
				E.printStackTrace();
			}finally{
					try{
						if(clis != null) clis.close();
						if(ClientS != null) ClientS.close();
					}catch(IOException e2){
		System.out.println("client disconnect");
					}	
			}
		}	
	}
}


