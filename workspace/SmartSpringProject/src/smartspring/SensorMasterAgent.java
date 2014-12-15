package smartspring;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SensorMasterAgent extends Agent {
	private static final long serialVersionUID = 1L;
	private DFAgentDescription dfd = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	
	protected void setup(){
		searchDFAgentDescription();
	}
	
	private void searchDFAgentDescription(){
		
		sd.setType("sens_data");
		dfd.addServices(sd);
		DFAgentDescription[] result = null;
		
		try {
			result = DFService.search(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < result.length; i++) {
			System.out.println("Service" + result[i].getName().toString());
		}
	}

}
