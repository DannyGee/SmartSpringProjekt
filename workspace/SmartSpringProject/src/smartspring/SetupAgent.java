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

import jade.core.Agent;
import jade.wrapper.*;

public class SetupAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		setSensorAgent();
		System.out.println("hallo");
		
	}	
	private void setSensorAgent(){
		
		int nGuests = 2;
		PlatformController container = getContainerController(); // get a container controller for creating new agents
        // create N guest agents
        try {
            for (int i = 0;  i < nGuests;  i++) {
                // create a new agent
		String localName = "guest_"+i;
		AgentController guest = container.createNewAgent(localName, "smartspring.SensorAgent", null);
		guest.start();
                //Agent guest = new GuestAgent();
                //guest.doStart( "guest_" + i );
            }
        }
        catch (Exception e) {
            System.err.println( "Exception while adding guests: " + e );
            e.printStackTrace();
        }
	}
	
	
}
