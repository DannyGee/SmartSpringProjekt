package smartspring;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SensorMasterAgent extends Agent {
	private static final long serialVersionUID = 1L;
	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	private DFAgentDescription[] result = null;
	
	protected void setup(){
		searchDFAgentDescription();
		sendCallOfProposalToSensorAgent();
	}
	private void searchDFAgentDescription(){	
		sd.setType("sens_data");
		dfd.addServices(sd);
		try {
			result = DFService.search(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Test-Ausgabe welche Angebote gefunden
		for (int i = 0; i < result.length; i++) {
			System.out.println("Service" + result[i].getName().toString());
		}
	}
	private void sendCallOfProposalToSensorAgent(){
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP); 
		AID rec; 
		if(result != null){
			for (int i = 0; i < result.length; i++) {
				rec = new AID(result[i].toString(),AID.ISGUID);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP);
				aclMessage.addReceiver(rec);
				aclMessage.setContent("Pong");
				this.send(aclMessage);
				System.out.println("da geht was raus bei" + (i+1));
			}
		}
	}

}
