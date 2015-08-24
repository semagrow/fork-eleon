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
