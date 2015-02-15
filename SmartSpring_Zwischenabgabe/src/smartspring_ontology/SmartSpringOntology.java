package smartspring_ontology;

import static jade.content.lang.sl.SLOntology.ONTOLOGY_NAME;
import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SmartSpringOntology extends BeanOntology {
    
    private static Ontology theInstance = null;
    
    public static Ontology getInstance(){
        if (SmartSpringOntology.theInstance == null){
            try {
                theInstance = (Ontology) new SmartSpringOntology();
            } 
            catch (BeanOntologyException ex) {
                Logger.getLogger(SmartSpringOntology.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        
        return theInstance;
    }
    private SmartSpringOntology()throws BeanOntologyException{
        super(ONTOLOGY_NAME);
        add( "smartspring_ontology" );
    }
}
