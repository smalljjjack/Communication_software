import java.awt.*;
import java.awt.event.*;

public class chatClient {
	public static void main(String[] args) {
		new chatFrame("Client").launchFrame();
	}
}

class chatFrame extends Frame{
	chatFrame(String client){
		super("client");
	} 
	TextField txF = new TextField();
	TextArea txA = new TextArea();
	
	public void launchFrame(){
		this.addWindowListener(new myWindowMointor());
		add(txF, BorderLayout.SOUTH);
		add(txA, BorderLayout.NORTH);
		setBounds(500, 500, 1500, 1500);
		pack();
		setVisible(true);
	}

	class myWindowMointor extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}
}
