/**
 *  					SmartSpringProjekt
 * --------------------------------------------
 *	class	 "SetupAgent" 
 *---------------------------------------------
 *
 *- Empfangen von Benutzereingaben:
 *		-> Anzahl und Eigenschaften von Sensoren
 *
 *- Starten von x SensorAgenten
 *- Starten vom SensorMasterAgenten
 *
 *
 */

package smartspring;

import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.*;

public class SetupAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	
	protected void setup() {
	//Testanzahl 3 Sensoren
		int sens_count = 3;
		
	// Starten von (x = sens_count) - Agenten
		setSensorAgent(sens_count);
		
	//Starten von MasterAgent
		setSensorMasterAgent();
	}
	
	private void setSensorAgent(int in_sens_count){
		
		for (int i = 0; i < in_sens_count; i++) {
			String name = "Sensor"+(i);		    
			AID alice = new AID( name, AID.ISLOCALNAME );
		    AgentContainer c = getContainerController();
	    	try {
	    		AgentController a = c.createNewAgent(name,"smartspring.SensorAgent", null);
	    		a.start();
	    		System.out.println(alice.getLocalName() + " Created");
	    	}catch (Exception e){e.printStackTrace();}		
		}
	}
	
	private void setSensorMasterAgent(){
		String name = "SensorMater";		    
		AID alice = new AID( name, AID.ISLOCALNAME ); 
		AgentContainer c = getContainerController();
	    	try {
	    		AgentController a = c.createNewAgent(name,"smartspring.SensorMasterAgent", null);
	    		a.start();
	    		System.out.println(alice.getLocalName() + " Created");
	    	}catch (Exception e){e.printStackTrace();}
	}
}
