/**
 * 		SmartSpringProjekt
 * -------------------------------------------------
 *	class:	 "SensorAgent" 
 *--------------------------------------------------
 * -> 1. bei start abfrage von Wetterdaten 
 * ----> [BuildGoogleWeatherDataStream] - wird noch bearbeitet
 * -> 2. stellt DF Service bereit
 * ----> [RegistryWeatherService] 
 * -> 3. Wetterstream-Error bereitstellen - Fehlerverarbeitung folgt
 * ----> Error Message anlegen?
 */

package smartspring;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;


public class WeatherAgent extends Agent{
    protected void setup(){
         
    }
    
//---------------- Behaviours --------------------------------------------------
    
    //DFService weather_data anmelden....
    class RegistryWeatherService extends OneShotBehaviour{
        
        public void action(){
            DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("weather_data");
        sd.setName("WeatherData_from_"+ myAgent.getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(myAgent,dfd);
        } 
        catch (FIPAException fe) {fe.printStackTrace();}
        }
    }
    
    //inaktiv 
    class BuildGoogleWeatherDataStream extends OneShotBehaviour{
        
        @Override
        public void action(){
            try{
                URL url = new URL("http://www.google.com/ig/api?weather=Berlin");
                InputStream inputStream = url.openStream();
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(inputStream);
                
                inputStream.close();
                
                String result = XPathFactory.newInstance().newXPath().evaluate("/xml_api_reply/weather/forecast_infomation/city/@data",doc);
                System.out.println(result);
                        
            }
            catch(javax.xml.parsers.ParserConfigurationException pce){
            pce.printStackTrace();
            }
            catch(org.xml.sax.SAXException sa){sa.printStackTrace();}
            catch(IOException ioe){ioe.printStackTrace();}
            catch(XPathExpressionException xpe){xpe.printStackTrace();}
        }
    }

//------------------------------------------------------------------------------
}
