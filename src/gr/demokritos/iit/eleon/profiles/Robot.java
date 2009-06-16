/*
 * Robot.java
 *
 * Created on 9 Μάρτιος 2009, 3:59 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gr.demokritos.iit.eleon.profiles;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author dimitris
 */
public class Robot implements Serializable{
    
       private Vector robot;
    
     public Robot(String o, String c, String e, String a, String n){
         this.robot=new Vector();
        this.robot.add(o);
        this.robot.add(c);
        this.robot.add(e);
        this.robot.add(a);
        this.robot.add(n);
    }
    
    /** Creates a new instance of robot */
    public Robot(Vector u) {
        this("50", "50", "50", "50", "50");//default robot in case u.size()!=5
        if(u.size()==5)
        this.robot=u;
            
    }
    
    public String getO(){
        return this.robot.elementAt(0).toString();
    }
        
    public String getC(){
        return this.robot.elementAt(1).toString();
    }
            
    public String getE(){
        return this.robot.elementAt(2).toString();
    }
                
    public String getA(){
        return this.robot.elementAt(3).toString();
    }
    
    public String getN(){
        return this.robot.elementAt(4).toString();
    }

    public void updateElementAt(String attributeValue, int attributeNumber) {
        this.robot.setElementAt(attributeValue, attributeNumber);
    }

    
}
