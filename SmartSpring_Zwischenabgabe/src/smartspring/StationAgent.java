/*
 * Der Station Agent
 */
package smartspring;

import jade.content.ContentManager;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class StationAgent extends Agent {
    
    private static ACLMessage msg = null; 
    private static final long serialVersionUID = 1L;	
    private DFAgentDescription dfd = new DFAgentDescription();
    private ServiceDescription sd = new ServiceDescription();
    private DFAgentDescription[] result = null;
    private MessageTemplate mt_INFO = null; 
    private ContentManager manager = (ContentManager)getContentManager();
    
    //Startpunkt Station-Agent
    protected void setup(){
        System.out.println("\n Creat new SationAgent: " + this.getAID().getName());                        
        //Nachrichten empfangen start
        addBehaviour(new ReciveMessage(this));
    }
    
//---------------- Behaviours --------------------------------------------------    
        
    //Empfang von INFORM Nachrichten
    class ReciveMessage extends CyclicBehaviour{       
        MessageTemplate mt_INFO = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        
        public ReciveMessage(Agent a){ super(a); }
        
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(mt_INFO);
            if(msg != null){
                try {
                    System.out.println("\n StationAgent:");
                    System.out.println(" : Message from:" + msg.getSender().getName());
                    System.out.println(" : with content: "+ msg.getContent());
                    
                    myAgent.addBehaviour(new SendInfoToSensor((StationAgent) myAgent,myAgent.getName(),msg.getContent()));
                  
                    myAgent.addBehaviour(new SearchSensorService((StationAgent) myAgent));
                
                } catch (Exception e) {
                }
            }
        }
    }
    
    //Sucht Sensor DFService 
    class SearchSensorService extends OneShotBehaviour{
        public SearchSensorService(StationAgent aThis) {super(aThis);}
            
        //Ausgabe "Service found:...."
        @Override
        public void action() {            
                sd.setType("sens_data");
		dfd.addServices(sd);
		try {
			result = DFService.search(myAgent, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Test-Ausgabe welche Angebote gefunden
		for(int i = 0; i < result.length; i++) {
			System.out.println("Services found: " + result[i].getName().toString());
		}
        }
    }
    
    //Sendet Sensor INFORM Message:(Agent, Inhalt, Empfängername)
    class SendInfoToSensor extends OneShotBehaviour {
        
        String content;
        String aidS;
        
        public SendInfoToSensor(StationAgent aThis,String con, String inaidS){
            super(aThis);
            content = con;
            aidS = inaidS;
        }
        @Override
        public void action() {        
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.addReceiver(new AID(aidS, AID.ISLOCALNAME));
            aclMessage.setContent(content);
            myAgent.send(aclMessage); 
            System.out.println("\n StationAgent: ");
            System.out.println(" : INFORM Message to "+aidS); 
            System.out.println(" : with content: "+ content);
        }      
    }
    
    //Sendet CFP an SensorAgent 
    class SendCFPtoSensorAgend extends OneShotBehaviour{
        
        public SendCFPtoSensorAgend(StationAgent inThis){
            super(inThis);
        }
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP); 
		AID rec; 
		if(result != null){
			for (int i = 0; i < result.length; i++) {
				rec = new AID(result[i].toString(),AID.ISGUID);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP);
				aclMessage.addReceiver(rec);
				aclMessage.setContent("value");
				myAgent.send(aclMessage);
				System.out.println("da geht was raus bei" + (i+1));
			}
		}
        }
        
    }
//------------------------------------------------------------------------------
}
