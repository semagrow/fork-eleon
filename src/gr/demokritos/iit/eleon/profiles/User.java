/*
 * User.java
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
public class User implements Serializable{

    private Vector user;
    
     public User(String mfps, String fpp, String links, String synthVoice){
             this.user=new Vector();
        this.user.add(mfps);
        this.user.add(fpp);
        
        this.user.add(links);
        this.user.add(synthVoice);
    }
    
    /** Creates a new instance of User */
    public User(Vector u) {
        this("4", "10", "4", "male");//default user in case u.size()!=4
        if(u.size()==4)
        this.user=u;
            
    }
    
    public String getMaxFactsPerSentence(){
        return this.user.elementAt(0).toString();
    }
        
    public String getFactsPerPage(){
        return this.user.elementAt(1).toString();
    }
            
    public String getLinks(){
        return this.user.elementAt(2).toString();
    }
                
    public String getSynthVoice(){
        return this.user.elementAt(3).toString();
    }

    public void updateElementAt(String attributeValue, int attributeNumber) {
        this.user.setElementAt(attributeValue, attributeNumber);
    }

    
}
