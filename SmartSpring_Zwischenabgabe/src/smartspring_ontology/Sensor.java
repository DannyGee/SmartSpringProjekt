package smartspring_ontology;

import jade.content.Concept;

public class Sensor implements Concept {
    
    private String name;
    private String type;
    private int sendPeriod;
    private double actValue;
    //private double minVal;
    //private double maxVal;
    
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    
    public void setType(String type){this.type = type;}
    public String getType(){return type;}
    
    public void setSendPeriod(int sendperiod){this.sendPeriod = sendperiod;}
    public int getSendPeriod(){return sendPeriod;}
    
    public void setActValue(double actValue){this.actValue = actValue;}
    public double getActValue(){return actValue;}    
}
