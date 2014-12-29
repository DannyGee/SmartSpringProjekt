package smartspring;

import jade.core.AID;
import jade.core.Location;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

public class MobileAgent extends GuiAgent {
	
	private static final long serialVersionUID = 1L;
	private AID controller;
	private Location destination;
	transient protected MobileAgentGui myGui; // The GUI
	
	protected void setup() {
		Object[] args = getArguments();
		controller = (AID) args[0];
		destination = here();
		
		init();
		
	}
	
	protected void init(){
		//Set up the GUI
		myGui = new MobileAgentGui(this);
		myGui.setVisible(true);
		myGui.setLocation(destination.getName());
	}
	protected void takedown(){
		System.out.println(getLocalName() +" is now shutting down");
//		if (myGui != null) {
//			myGui.setVisible(false);
//			myGui.dispose();
//		}
	}
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
