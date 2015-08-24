/***************

<p>Title: </p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


package gr.demokritos.iit.eleon.profiles;

import java.io.Serializable;
import java.util.Vector;

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


/*
This file is part of the ELEON Ontology Authoring and Enrichment Tool.

ELEON is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, see <http://www.gnu.org/licenses/>.
*/
