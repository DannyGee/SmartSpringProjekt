/**
 * 		SmartSpringProjekt
 * -------------------------------------------------
 *	class:	 "SetupAgent" 
 *--------------------------------------------------
 * -> 1. Benutzereingaben und Ausgaben verarbeiten
 * -> 2. Station anlegen [Name, Ort]
 * ----> [setStationAgent] 
 * -> 3. Sensor anlegen [Name,Art des Sensors, Übertragungsperiode]
 * ----> [setSensorAgent] 
 */

package smartspring;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JList;
import smartspring_ontology.SmartSpringOntology;

public class SetupAgent extends Agent {
    
    //GUI
    protected JFrame m_frame = null;
    
    //Jade 
    protected AgentController a;
    protected AgentContainer c;
    
    //Agentennamen
    protected String stationName;
    private ArrayList<String> availableAgents = new ArrayList<String>();
    
    //Ontology
    private Codec codec = new SLCodec();
    private Ontology ontology = SmartSpringOntology.getInstance();
    
    @Override
    protected void setup(){       
        try {
            //Ontology regestrieren
            getContentManager().registerLanguage(codec);
            getContentManager().registerOntology(ontology);
            
            setupGUI();
            
            setStationAgent("Station1");
        } catch (Exception e) {
        }
    }

//------------------ Agenten erstellen -----------------------------------------
    
    // SensorAgenten anlegen.....
    private void setSensorAgent(String name){
        
        availableAgents.add(name);
        Integer tmpcount = availableAgents.size() - 1;
        
        AID aid = new AID("Sensor"+tmpcount.toString(), AID.ISLOCALNAME ); 
            
            //AgentenContainer erstellen & festlegen
            AgentContainer c = getContainerController();
            try {
                    AgentController a = c.createNewAgent(("Sensor"+ tmpcount.toString()),"smartspring.SensorAgent", null);
                    a.start();
                    
                    //startet hinzufügen von Station + Sensor 
                    addBehaviour(new SendInfoToStation("Sensor"+tmpcount.toString()));
            
            }catch (Exception e){e.printStackTrace();}
            
            //GUI updaten
            setSensorOverview();
    }
    
    //Station Agenten anlegen.....
    private void setStationAgent(String name){	    
        stationName = name;
                AID aid = new AID( name, AID.ISLOCALNAME ); 
		c = getContainerController();
	    	try {
	    		a = c.createNewAgent(name,"smartspring.StationAgent", null);
	    		a.start();
	    	}catch (Exception e){e.printStackTrace();}
    }
//------------------------------------------------------------------------------ 
//---------------- Behaviours --------------------------------------------------
    
        //meldet der Station die zugehörigen Sensoren
    class SendInfoToStation extends OneShotBehaviour {
        
        String content;
        
        public SendInfoToStation(String con){
            content = con;
        }
        @Override
        public void action() {
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.addReceiver(new AID(stationName, AID.ISLOCALNAME));
            aclMessage.setContent(content);
            myAgent.send(aclMessage);
            System.out.println("\n SetupAgent");
            System.out.println(" : send INFORM Message to Station");
            System.out.println(" : with content: " +content);
        }      
    }
    
//------------------------------------------------------------------------------
//----------------- GUI -------------------------------------------------------- 
    
    //startet Fenster 
    private void setupGUI() {       
       m_frame = new SetupGUI( this );
       
       m_frame.setSize( 400, 600 );
       m_frame.setLocation( 400, 400 );
       m_frame.setVisible( true );
       m_frame.validate();
    }
    
    //fügt dem SensorenPanel die namen der sensoren hinzu....
    private void setSensorOverview(){
        int counter = availableAgents.size();
        String[] items = new String[counter];        
        
        for (int i = 0; i < counter; i++) {   
            
            items[i]= (availableAgents.get(i));
        }
        ((SetupGUI) m_frame).pnl_sensor.removeAll();
        ((SetupGUI) m_frame).lit_senslist = new JList(items);
        
        ((SetupGUI) m_frame).pnl_sensor.add(((SetupGUI) m_frame).lit_senslist);

        ((SetupGUI) m_frame).pnl_sensor.revalidate();
   }   
    
    //----------------- GUI Befehle vorarbeitung -------------------------------   
    
    //mehrere station hinzufügen 
    //...folgt...
    
    //Sensor anlegen Befehl von GUI btn:[newsensor]
    protected void feedStation(String inStationName, String inSensName){
    
        //neuer Sensor Agent anlegen
        setSensorAgent(inSensName);     
    }
    
    // Ende GUI Befehle---------------------------------------------------------
//------------------------------------------------------------------------------
}
