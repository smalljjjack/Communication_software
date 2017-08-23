package firstChat0;

import java.awt.*;

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
		add(txF, BorderLayout.SOUTH);
		add(txA, BorderLayout.NORTH);
		setBounds(500, 500, 1500, 1500);
		pack();
		setVisible(true);

	}
}
