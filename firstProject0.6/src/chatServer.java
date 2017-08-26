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
			SerTA.setText(SerTA.getText()+"Server had said:" +"   " +SerSa+"\n");
			SerTF.setText("");
		}
	}
	
	private class SerWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(-1);
		}
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
		
		public void send(String clisay){
			try {
				dos.writeUTF(clisay);
			} catch(EOFException e){
System.out.println("Byebye!");
			}catch (SocketException e) {
				SerThreads.remove(this);
System.out.println("a client quit, I had removed it ");
			} catch (IOException e){
				System.out.print("117line is worng about IOException");
			}
			
		}
		
		public void run(){
			String clisay = null;
			try{
				while(proc){
					clisay = clis.readUTF();
System.out.println(clisay);		
					for(int i = 0; i < SerThreads.size(); i++){
						SerThread SerTre = SerThreads.get(i);
						SerTre.send(clisay);
					}
					
					/*for(Iterator<SerThread> it = SerThreads.iterator(); it.hasNext();){
						it.next().send(clisay);
					}*/
					
					/*Iterator<SerThread> it = SerThreads.iterator();
					while(it.hasNext()){
						it.next().send(clisay);
					}*/
					
					SerTA.setText(SerTA.getText()+"Client said" + clisay+"\n");
				} 
			}catch(EOFException e){
System.out.println("142line wrong");	
			}catch(IOException e){
System.out.println("148line is wrong");
				e.printStackTrace();
System.out.println("Server IOException!");
			}finally{
				try{
					if(clis != null) clis.close();
					if(ClientS != null) ClientS.close();
					if(dos != null) {
						proc = false;
						dos.close();
						dos = null; //optional   
					}
				}catch(IOException e){
System.out.println("client disconnect");
				}
			}	
		}	
	}
}


