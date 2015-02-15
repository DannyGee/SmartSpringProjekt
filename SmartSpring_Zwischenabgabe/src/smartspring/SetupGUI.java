package smartspring;

import jade.core.behaviours.OneShotBehaviour;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;


public class SetupGUI extends JFrame {
  
    protected SetupAgent setupAgent;
    public String sensorName;
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    GridLayout gridLayout1 = new GridLayout(0,2); 
    
    JPanel pnl_head = new JPanel();
    JPanel pnl_station = new JPanel();
    JPanel pnl_sensor = new JPanel();
    JPanel pnl_sensor_details = new JPanel();
    JLabel lab_head = new JLabel();
    JLabel lab_stat = new JLabel();
    JLabel lab_sens = new JLabel();
    JLabel lab_statname = new JLabel();
    JButton btn_newstation = new JButton();
    JButton btn_newsensor = new JButton();
    JButton btn_updatesensorvalue = new JButton();
    JRadioButton rbtn_on = new JRadioButton("On");
    JRadioButton rbtn_off = new JRadioButton("Off");
    JComboBox cbox_waterrequirement;
    ButtonGroup gbtn_onoff = new ButtonGroup();
    JList lit_senslist;  
    JLabel l_actvalue = new JLabel();
    JTextField txf_stationname = new JTextField();
    JTextField txf_sensorname = new JTextField();
    Component comp_1;
    Box box_head;
    Box box_newstation;
    Box box_sensordetails;
    
    SetupGUI(SetupAgent aThis){    
         
        try {
            init();
        } catch (Exception e) {
            System.out.println("setup the GUI is failed"); 
            e.printStackTrace();
        }
        setupAgent = aThis;
    }
    
    //MainGUI Erstellung start
    private void init()throws Exception{        
    //Top
        //Headline Box
        pnl_head.setLayout(borderLayout1);
        lab_head.setText("Smart - Spring");
        box_head = Box.createHorizontalBox();
        this.getContentPane().add(pnl_head, BorderLayout.NORTH);
        pnl_head.add(box_head,BorderLayout.NORTH);
        box_head.add(lab_head,null);
        initNewStationPanel();
        initOverviewPanel();
    }
    
    private void initNewStationPanel(){
            
         //new Station Box
        //lab_stat.setText("station: ");
        btn_newsensor.setText("SensorAgent anlegen");
        
        lab_sens.setText("Sensor: ");
        txf_sensorname.setText("defaultSensor");
        
        //txf_stationname.setText("defaultStation");
        
        box_newstation = Box.createHorizontalBox();
        pnl_head.add(box_newstation,BorderLayout.CENTER);
        //box_newstation.add(lab_stat,BorderLayout.WEST);
        //box_newstation.add(txf_stationname,BorderLayout.CENTER);
        box_newstation.add(lab_sens,BorderLayout.WEST);
        box_newstation.add(txf_sensorname,BorderLayout.CENTER);
        box_newstation.add(btn_newsensor,BorderLayout.EAST);
        
        //Sensor Box 
                
        //Action Listener 
        btn_newsensor.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e){
                btn_newstation_actionPerformed(e);
            }
        });    
    }
    
    private void initOverviewPanel(){
        
        pnl_sensor.setLayout(gridLayout1);
        pnl_sensor_details.setLayout(gridLayout1);
        
        //Haupt Box Steuerelemente
        box_sensordetails = Box.createVerticalBox();

        //RadioButton On/OF
        Box box_sensordetails_onoff = Box.createHorizontalBox();
        gbtn_onoff.add(rbtn_on);
        gbtn_onoff.add(rbtn_off);
        
        box_sensordetails_onoff.add(rbtn_on);
        box_sensordetails_onoff.add(rbtn_off);
        
        //combobox & label wasserbedarf
         Box box_sensordetails_watervalue = Box.createHorizontalBox();
        String[] st_Waterrequirement = {"viel","mittel","wenig"};
        cbox_waterrequirement = new JComboBox(st_Waterrequirement);
        Dimension dim_cbox = new Dimension(400,43);
       
        JLabel l_waterrequirement = new JLabel("Wasserbedarf: ");
        box_sensordetails_watervalue.setMaximumSize(dim_cbox);
        box_sensordetails_watervalue.add(l_waterrequirement);
        box_sensordetails_watervalue.add(cbox_waterrequirement);        
        
        //Label textbox Button aktueller Wert 
        Box box_sensordetails_infovalue = Box.createHorizontalBox();
        
        JLabel l_infovalue = new JLabel("aktueller Wert: ");
        l_actvalue.setText("kommt später ");
        btn_updatesensorvalue.setText("aktualisieren");
        
        box_sensordetails_infovalue.add(l_infovalue);
        box_sensordetails_infovalue.add(l_actvalue);
        box_sensordetails_infovalue.add(btn_updatesensorvalue);        
        
        //Steuerelemente der Box übergeben
        box_sensordetails.add(box_sensordetails_onoff);
        box_sensordetails.add(box_sensordetails_watervalue);
        box_sensordetails.add(box_sensordetails_infovalue);
        
        pnl_sensor_details.add(box_sensordetails);
        
        this.getContentPane().add(pnl_sensor, BorderLayout.WEST);
        this.getContentPane().add(pnl_sensor_details,BorderLayout.CENTER);
    }
    
    private void btn_newstation_actionPerformed(ActionEvent e) {
        setupAgent.addBehaviour(new OneShotBehaviour(){
            public void action(){
                ((SetupAgent) myAgent).feedStation(txf_stationname.getText(),txf_sensorname.getText());
            }
        });
    }
}
