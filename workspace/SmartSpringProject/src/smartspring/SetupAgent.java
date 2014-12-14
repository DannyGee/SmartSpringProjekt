/**
 *  					SmartSpringProjekt
 * --------------------------------------------
 *	class	 "SetupAgent" 
 *---------------------------------------------
 *
 *- Empfangen von Benutzereingaben:
 *		-> Anzahl und Eigenschaften von Sensoren
 *
 *- Erstellen von SensorAgenten
 *
 *- Starten vom SensorMasterAgenten
 *
 */

package smartspring;

import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.*;

public class SetupAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		setSensorAgent();
		System.out.println("hallo");
		
	}	
	private void setSensorAgent(){
		 String name = "Alice" ;
		 AID alice = new AID( name, AID.ISLOCALNAME );

		 AgentContainer c = getContainerController();

		         try {
		             AgentController a = c.createNewAgent(name,"smartspring.SensorAgent", null);
		             a.start();
		             System.out.println(alice.getLocalName() + " Created");
		         }catch (Exception e){
		             e.printStackTrace();
		         }


	}
	
	
}
