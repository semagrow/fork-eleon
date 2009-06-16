/***************

<p>Title: Generalized Profiles</p>

<p>Description:
This class holds arbitrary attributes that annotate the ontological entities.
The "Interest" attribute cannot be here, but must be in QueryProfileHashTable 
</p>

<p>Copyright (c) 2001-2009 National Centre for Scientific Research "Demokritos"</p>

@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009)

***************/


package gr.demokritos.iit.eleon.struct;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import gr.demokritos.iit.eleon.ui.RobotCharacteristicsPanel;

public class GeneralizedProfiles implements Serializable
{

    public Vector robotCharVector;

    public Hashtable robotCharValuesHashtable;
    
    public GeneralizedProfiles() {
        robotCharValuesHashtable=new Hashtable();
        robotCharVector=new Vector();
    }

    public GeneralizedProfiles(Hashtable values, Vector vec) {
        robotCharValuesHashtable=values;
        robotCharVector=vec;
    }
        public void createRobotCharVectorAndHash() {
        robotCharValuesHashtable=new Hashtable();

        robotCharVector=new Vector();
    }

    public Vector getRobotsCharValues(String field,String node, String username, Object[] robots) {
        if(!robotCharValuesHashtable.containsKey(node))
            robotCharValuesHashtable.put(node, new Hashtable());

        Hashtable h1=(Hashtable)robotCharValuesHashtable.get(node);

        if(!h1.containsKey(username)){
            Vector v=new Vector();
            String value="";
            for(int i=0;i<robotCharVector.size();i++){
               Vector rob=(Vector)robotCharVector.get(i);
               if (rob.elementAt(0).toString().equals(username)){
                   value=rob.elementAt(1).toString();
                   break;
               }
            }
            String[] values=value.split(" ");

            for(int i=0;i<robots.length;i++){
                boolean belongs=false;
                for(int j=0;j<values.length;j++){
                    if(robots[i].equals(values[j]))
                    {
                        belongs=true;
                        break;
                    }
                }
                if(belongs)
                    v.add("");
                else
                    v.add("-1");
            }


            h1.put(username, v);
        }
        return (Vector)h1.get(username);


    }

    void updateRobotsCharPreferenceForProperty(String field, String username, int i, String string) {
       // throw new UnsupportedOperationException("Not yet implemented");
    }

    public void addChangesInRobotCharValuesHashtable(Vector selectedItems, Object[] robots) {
        Enumeration en=robotCharValuesHashtable.elements();

        while(en.hasMoreElements()){
            Hashtable h1=(Hashtable)en.nextElement();
            String username=RobotCharacteristicsPanel.jTable1.getValueAt(RobotCharacteristicsPanel.jTable1.getSelectedRow(),0).toString();
            if(!h1.containsKey(username)){
            Vector v=new Vector();
            String value="";
            for(int i=0;i<robotCharVector.size();i++){
               Vector rob=(Vector)robotCharVector.get(i);
               if (rob.elementAt(0).toString().equals(username)){
                   value=rob.elementAt(1).toString();
                   break;
               }
            }
            String[] values=value.split(" ");

            for(int i=0;i<robots.length;i++){
                boolean belongs=false;
                for(int j=0;j<values.length;j++){
                    if(robots[i].equals(values[j]))
                    {
                        belongs=true;
                        break;
                    }
                }
                if(belongs)
                    v.add("");
                else
                    v.add("-1");
            }


            h1.put(username, v);
        }


            Vector values=(Vector)h1.get(username);
            for(int i=0;i<values.size();i++){
                if  (!selectedItems.contains(robots[i].toString()))
                    values.set(i,"-1");
            }
            for(int i=0;i<values.size();i++){
                if  (selectedItems.contains(robots[i].toString())&&values.elementAt(i).toString().equals("-1"))
                    values.set(i,"");
            }

            }
        }

    public void setValueAtRobotCharValuesHash(String node, String username, int i, String value) {
        Hashtable h1=(Hashtable)robotCharValuesHashtable.get(node);
        Vector v=(Vector)h1.get(username);

//        boolean universal=false;
//        for(int j=0;j<robotCharVector.size();j++){
//            if(((Vector)robotCharVector.elementAt(j)).elementAt(0).toString().equalsIgnoreCase(username)){
//                universal=(Boolean)((Vector)robotCharVector.elementAt(j)).elementAt(2);
//                break;
//            }
//        }
//        if(universal){
//            for(int h=0;h<v.size();h++){
//                v.setElementAt(value,h);
//            }
//        }
    //    else{
            v.setElementAt(value,i);
      //  }
    }

     public String getValueFromRobotCharValuesHash(String node, String username, int i) {
        Hashtable h1=(Hashtable)robotCharValuesHashtable.get(node);
        Vector v=(Vector)h1.get(username);
        return v.elementAt(i).toString();
    }

    void addRobotInRobotCharValuesHashtable(String name) {
        Enumeration en=robotCharValuesHashtable.elements();
        while(en.hasMoreElements()){
            Enumeration chars=((Hashtable)en.nextElement()).elements();
            while(chars.hasMoreElements()){
                Vector v=(Vector) chars.nextElement();
                v.add("-1");
            }
        }
    }

    public void renameRobotInRobotCharVector(String old, String newname) {

        for(int i=0;i<robotCharVector.size();i++){
            Vector nextProf=(Vector)robotCharVector.elementAt(i);
            String robottypes=(String)nextProf.elementAt(1);
            robottypes=robottypes.replace(" "+old," "+newname);
            robottypes=robottypes.replace(old+" ",newname+" ");
            if(robottypes.equals(old))
                robottypes=newname;
            nextProf.setElementAt(robottypes,1);
        }
    }

    public void removeRobotFromRobotCharVector(String name, Object[] r) {
        int pos=0;

        for(int i=0;i<r.length;i++){
            if(r[i].toString().equalsIgnoreCase("name"))
                pos=i;
        }


        for(int i=0;i<robotCharVector.size();i++){
            Vector nextProf=(Vector)robotCharVector.elementAt(i);

            String robottypes[]=nextProf.elementAt(1).toString().split(" ");
            String toR="";
            for(int j=0;j<robottypes.length;j++){
                if(!robottypes[j].equals(name))
                    toR=robottypes[j]+" ";
            }
            nextProf.setElementAt(toR, 1);
        }

        Enumeration els=robotCharValuesHashtable.elements();
        while(els.hasMoreElements()){
            Hashtable profiles=(Hashtable)els.nextElement();

            Enumeration vectors=profiles.elements();
            while(vectors.hasMoreElements()){
                Vector v=(Vector)vectors.nextElement();
                v.removeElementAt(pos);
            }
        }

    }

   public void renameAttributeInRobotCharValuesHashtable(String oldname, String newName) {
        Enumeration elements=robotCharValuesHashtable.elements();
        while(elements.hasMoreElements()){
            Hashtable node=(Hashtable)elements.nextElement();
            if(node.containsKey(oldname))
            {
                Vector temp=(Vector) node.get(oldname);
                node.put(newName, temp);
                node.remove(oldname);
            }
        }
    }

    public void removeAttributeFromHashtable(String name) {
       Enumeration elements=robotCharValuesHashtable.elements();
        while(elements.hasMoreElements()){
            Hashtable node=(Hashtable)elements.nextElement();
            if(node.containsKey(name))
            {

                node.remove(name);
            }
        }
    }

    
	
}
