/**
 * 					SmartSpringProjekt
 * --------------------------------------------
 *	class	 "SensorAgent" 
 *---------------------------------------------
 * - DFService "sens_data" bereitstellen
 * - Bodenfeuchtigkeit wert bereitstellen
 * - Sensor-Error wert bereitstellen
 * - Error Message anlegen????
 * - 
 */
package smartspring;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class SensorAgent extends Agent {
	
	private static final long serialVersionUID = 1L;

	protected void setup(){	
	// DFService "sens_date" anlegen
		setRegisterService();
	}

	private void ReceivedProposal(){
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		ACLMessage msg = this.receive(mt);
		
		if(msg != null){
			//CFP message received, reply Propose "sens_data" to master-agent 
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.PROPOSE);
		//Test for answer-function 
			reply.setContent(this + "hier kommt der Bodenwert");
		}
	}
	private void setRegisterService(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("sens_data");
		sd.setName("SmSeData_from_"+ this.getLocalName().toString());
		dfd.addServices(sd);
		try {
			DFService.register(this,dfd);
		} 
		catch (FIPAException fe) {fe.printStackTrace();}		
	}
}
