/**
 * 		SmartSpringProjekt
 * -------------------------------------------------
 *	class:	 "SensorAgent" 
 *--------------------------------------------------
 * -> 1. empfängt Nachricht von Zugehöriger Station 
 *       für Werteübermittlung
 * ----> [ReceivedInformMessage]
 * -> 2. sendet periodisch der Station werte
 * ----> [SendDataToStation] 
 * -> 3. Sensor-Error bereitstellen
 * ----> Error Message anlegen?
 */
package smartspring;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SensorAgent extends Agent {
	
    private static final long serialVersionUID = 1L;
    
    private int sendTime;               //TickerBehaviour Periote value
    private String stationAgent;        // Name StationAgent für Addressierung
    
     //inaktiv
     //DF registrien (Auslagerung aus Setup)
    private void setRegisterService(){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("sens_data");
        sd.setName("SmSeData_from_"+ this.getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this,dfd);
        } 
        catch (FIPAException fe) {fe.printStackTrace();}		
    }
    
    //Sartpoint SensorAgent
    protected void setup(){	
        System.out.println("\n Creat new SensorAgent: " + this.getAID().getName());
        
        // DFService "sens_date" anlegen
        //setRegisterService();
        
        //INFORM Nachrichten verarbeiten [CyclicBehaviour]
        addBehaviour(new ReceivedInformMessage(this));                  
    }

//---------------- Behaviours --------------------------------------------------
    
    //INFORM Nachrichten Entpfangen.... 
    class ReceivedInformMessage extends CyclicBehaviour{
        
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        
        public ReceivedInformMessage(Agent a){ super(a); }
       
        public void action(){ 
            ACLMessage msg = myAgent.receive(mt);
            if(msg != null){
                
                //INFORM Message Content auswerten
                if (msg.getContent().charAt(0)== '/') {
                    
                }
                else{
                    stationAgent = msg.getContent();
                    
                    // werte an Station senden [TickerBehaviour]
                    myAgent.addBehaviour(new SendDataToStation(myAgent,2000));
                    
                    System.out.println("\n SensorAgent: " );
                    System.out.println(" : INFORM Message from: "+ msg.getSender().getName());
                    System.out.println(" : content: "+ msg.getContent());
                }
            }
        }            
    }

    //Werte an Station übermitteln
    class SendDataToStation extends TickerBehaviour{

        public SendDataToStation(Agent a, long period) {super(a, period);}
        @Override
        protected void onTick() { 
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.addReceiver(new AID(stationAgent, AID.ISLOCALNAME));
            aclMessage.setContent("0");
            myAgent.send(aclMessage); 
            System.out.println("\n SensorAgent: ");
            System.out.println(" : INFORM Message to "+stationAgent); 
            System.out.println(" : with content: "+ aclMessage.getContent());
        }       
    }
//------------------------------------------------------------------------------

}
