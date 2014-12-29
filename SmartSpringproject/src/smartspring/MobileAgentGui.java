package smartspring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MobileAgentGui extends JFrame {

	private JTextField location;
	private JTextField info;
	private MobileAgent myAgent; // Reference to the agent class
	
	public MobileAgentGui(MobileAgent ma){
		myAgent = ma;
		setTitle(myAgent.getLocalName());
		
		//Add button and text field
		Container c = getContentPane();
		JPanel base = new JPanel();
		base.setBorder(new EmptyBorder(20,20,20,20));
		getContentPane().add(base);
		base.setLayout(new BorderLayout(0,20));
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout(10,0));
		pane.add(new JLabel("Current Location : "),BorderLayout.WEST);
		location.setEditable(false);
		location.setBackground(Color.white);
		base.add(pane,BorderLayout.NORTH);
		base.add(info = new JTextField(20),BorderLayout.SOUTH);
		info.setEditable(false);
		info.setHorizontalAlignment(JTextField.CENTER);
		
		setSize(200,100);
		pack();
		setResizable(false);
		Rectangle r = getGraphicsConfiguration().getBounds();
		setLocation(r.x + r.width-getWidth(),r.y);
	}
	public void setLocation(String loc){
		this.location.setText(loc);
	}
	public void setInfo(String info){
		this.info.setText(info);
	}
}