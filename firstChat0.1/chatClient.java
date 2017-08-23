package firstChat;

import java.awt.*;

public class chatClient {

	public static void main(String[] args) {
		chatFrame client = new chatFrame("client");
		/*client.setLayout(new BorderLayout());
		chatPanel say= new chatPanel(); 
		say.setSize(500, 500);
		rePanel re = new rePanel();
		re.setSize(500,500);
		say.add(new TextField("please enter what you want to say!"));
		Panel tool = new Panel();
		tool.setSize(500, 200);
		tool.setLayout(new FlowLayout());
		Label lb1 = new Label("tool!");
		Button send = new Button("send");
		tool.add(lb1);
		tool.add(send);
		client.add(say, "North");
		client.add(re, "West");
		client.add(tool, "Center")*/;
		client.setBounds(500, 500, 1500, 1500);
		client.pack();
		client.setVisible(true);
	}
}

class chatFrame extends Frame{
	chatFrame(String client){
		super("client");
	}
	
	public void launchFrame(){
		setBounds(500,500,1000,1000);
		setVisible(true);
	}
}

/*class chatPanel extends Panel{
	
}

class rePanel extends Panel{
	
}*/