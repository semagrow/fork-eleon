/***************

<p>Title: DataBase Entity Table Model</p>

<p>Description:
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Kostas Stamatakis (2002)
@author Dimitris Spiliotopoulos (2002)
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/


package gr.demokritos.iit.eleon.authoring;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;


public class DataBaseEntityTableModel extends AbstractTableModel
{
	public static final ColumnData m_columns[] =
	{
		new ColumnData( LangResources.getString(Mpiro.selectedLocale, "fields_tabletext"), 120, JLabel.LEFT ),
		new ColumnData( LangResources.getString(Mpiro.selectedLocale, "fillers_tabletext"), 110, JLabel.LEFT ),
		//new ColumnData( "Fields", 120, JLabel.LEFT ),
		//new ColumnData( "Fillers", 110, JLabel.LEFT ),
  };

  public static final int COL_FIELD = 0;
  public static final int COL_FILLER = 1;

  static Vector m_vector;
  protected Vector m_initVector=new Vector();  //LLAST


  public DataBaseEntityTableModel(Vector initVector, boolean indepedent)
  {
		m_vector = new Vector();
		int i;
		if(indepedent)
			i = 1;
		else
			i = 0;
		for(int r = i;r<initVector.size();r++) //LLAST
		{ //
			m_initVector.addElement(initVector.elementAt(r));//
		}//
		//m_initVector = initVector;   //LLAST
                System.out.println("m_initVector"+m_initVector.toString());
		setDefaultData();
  } // Constructor

  public void setDefaultData()
  {
	  m_vector.removeAllElements();
	  m_vector = m_initVector;
  }

  public int getRowCount()
  {
	return m_vector==null ? 0 : m_vector.size();
  }

  public int getColumnCount()
  {
	return m_columns.length;
  }

  public String getColumnName(int column)
  {
	return m_columns[column].m_title;
  }

  public boolean isCellEditable(int nRow, int nCol)
  {
		FieldData f = (FieldData)m_initVector.elementAt(0);
		if (f.elementAt(0).toString().equalsIgnoreCase("type"))
		{
		  if (nCol < 1 || nRow < 1)
		  {
				return false;
		  }
		  else
		  {
				return true;
		  }
		}
		else
		{
			if (nCol < 1)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
  }

  public Object getValueAt(int nRow, int nCol)
  {
		if (nRow < 0 || nRow>=getRowCount())
		{
		  return "";
		}
		FieldData row = (FieldData)m_vector.elementAt(nRow);
             //   System.out.println("OOOOOOOO"+"m_vector.elementAt(nRow):   "+m_vector.elementAt(nRow).toString()+"row.m_filler:   "+row.m_filler);
		switch (nCol)
		{
		  case COL_FIELD: return row.m_field;
		  case COL_FILLER: return row.elementAt(1);
		}
		return "";
  }

  public void setValueAt(Object value, int nRow, int nCol)
  {
		if (nRow < 0 || nRow>=getRowCount())
		{
		  return;
		}
                
                
		FieldData row = (FieldData)m_vector.elementAt(nRow);
                String initValue=row.m_filler;
		String svalue = value.toString();

                
                switch (nCol)
		{
		  case COL_FIELD:
			row.m_field = svalue;
			break;
		  case COL_FILLER:
		row.m_filler = svalue;

		  break;
		}
                FieldData field = (FieldData)m_vector.elementAt(nRow);
		field.setElementAt(svalue, nCol);
                //System.out.println("aaaaaas");
                if(Mpiro.win.ontoPipe.isRealTime())
            Mpiro.win.ontoPipe.rebind();
       // Mpiro.win.struc.putEntityTypeOrEntityToDB(DataBasePanel.last.toString(), Mpiro.win.ontoPipe.getExtension().getEntityTypeOrEntity(DataBasePanel.last.toString()));
               //  Vector propVector=(Vector) Mpiro.win.struc.getProperty(row.m_field);
                                              /*    if (!(propVector.elementAt(5).toString().equalsIgnoreCase(""))){
                                      if(!row.m_filler.startsWith("Select")){
                                      String inverseofinverse="";
                                      String[] fillers=row.m_filler.split(" ");
                                      for(int j=0;j<fillers.length;j++){
                                      NodeVector inverse=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(fillers[j]);
                                      Vector inv=(Vector) inverse.elementAt(0);
                                      inv=(Vector) inv.elementAt(0);
                                      for(int h=3;h<inv.size();h++){
                                          Vector temp=(Vector) inv.elementAt(h);
                                          if (temp.elementAt(0).toString().equalsIgnoreCase(propVector.elementAt(5).toString())){
                                              inverseofinverse=temp.elementAt(1).toString();
                                          //    String[] fillers=temp.elementAt(1).toString().split(" ");
                                              String fillersWithoutLast=temp.elementAt(1).toString().replace(" "+DataBasePanel.last.toString(),"").replace(DataBasePanel.last.toString()+" ","").replace(DataBasePanel.last.toString(),"");
                                             if(fillersWithoutLast.startsWith("Select")||fillersWithoutLast.equalsIgnoreCase(""))
                                                  fillersWithoutLast=DataBasePanel.last.toString();
                                             else
                                                 fillersWithoutLast=fillersWithoutLast+" "+DataBasePanel.last.toString();
                                              
                                              Vector k=new Vector();
                                             String[] s=fillersWithoutLast.split(" ");
                                             for(int i=0;i<s.length;i++){
                                                 k.add(s[i]);
                                             }
                                             Vector temporary=(DDialog.checkCardinalities(k, fillers[j], propVector.elementAt(5).toString()));
                                             try{
                                                 temporary.size();
                                             }
                                             catch(java.lang.NullPointerException npe){
                                               
                                                 return;
                                             }
                                              
                                             
                                              temp.setElementAt(fillersWithoutLast,1);
                                             //  if(fillersWithoutLast.startsWith("Select ")||fillersWithoutLast.equalsIgnoreCase("")){ 
                                                //  temp.setElementAt(DataBasePanel.last.toString(),1);
                                              //}
                                              break;
                                          }
                                      }
                                      }
                                      
                                 /*     if (!initValue.equalsIgnoreCase("")&&!initValue.equals(null)&&!initValue.startsWith("Select")) {
                                      inverse=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(initValue);
                                       inv=(Vector) inverse.elementAt(0);
                                      inv=(Vector) inv.elementAt(0);
                                      for(int h=3;h<inv.size();h++){
                                          Vector temp=(Vector) inv.elementAt(h);
                                          if (temp.elementAt(0).toString().equalsIgnoreCase(propVector.elementAt(5).toString())){
                                              
                                              temp.setElementAt("",1);
                                              break;
                                          }
                                      }}}*/
                           /*           if (!inverseofinverse.equalsIgnoreCase("")&&!inverseofinverse.equals(null)&&!inverseofinverse.startsWith("Select")&&!inverseofinverse.startsWith(DataBasePanel.last.toString())) {
                                    inverseofinverse=inverseofinverse.replaceAll(" ","");
                                          inverse=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(inverseofinverse);
                                       inv=(Vector) inverse.elementAt(0);
                                      inv=(Vector) inv.elementAt(0);
                                      for(int h=3;h<inv.size();h++){
                                          Vector temp=(Vector) inv.elementAt(h);
                                          if (temp.elementAt(0).toString().equalsIgnoreCase(row.m_field)){
                                              
                                              
                                              
                                              temp.setElementAt("",1);
                                              break;
                                          }}
                                      }
                                      
                                  }*/
                
                
		
                
               
                
                
//reeee();
		// Update current vector
//		FieldData field = (FieldData)m_vector.elementAt(nRow);
//		field.setElementAt(svalue, nCol);

                
                                                   
//                  if(propVector==null) propVector=new PropertiesHashtableRecord();
//                  if (propVector.elementAt(9).toString().equalsIgnoreCase("true")){
//                    String[] j=row.m_filler.split(" ");
//                    for (int f=0;f<j.length;f++)
//                    {
//                        String domainf= j[f];
//
//                        if (domainf.equalsIgnoreCase("Select")||domainf.equalsIgnoreCase(""))
//                            break;
//                            Vector vecDom=(Vector) Mpiro.win.struc.getEntityTypeOrEntity(domainf);
//
//                            vecDom=(Vector) vecDom.elementAt(0);
//                            vecDom=(Vector) vecDom.elementAt(0);
//
//                            for(int a=3;a<vecDom.size();a++)
//                            {
//                                Vector nextEl=(Vector) vecDom.elementAt(a);
//
//                                if(nextEl.elementAt(0).toString().equalsIgnoreCase(row.m_field))
//                                {
//                                    String rangeVec2=nextEl.elementAt(1).toString();
//                                    if (rangeVec2.startsWith("Select")) rangeVec2="";
//                                  String[] k=rangeVec2.split(" ");
//                    for (int p=0;p<k.length;p++)
//                    {
//                                    if (k[p].equalsIgnoreCase(DataBasePanel.last.toString()))
//                                    break;
//                                    else{
//                                            if(p<k.length-1) continue;
//                                            else {
//                                        if(nextEl.elementAt(1).toString().startsWith("Select"))
//                                       nextEl.set(1, DataBasePanel.last.toString());
//                                                else
//                                                  nextEl.set(1, DataBasePanel.last.toString()+" "+nextEl.elementAt(1).toString());
//
//
//                                }}}}}}
//
//
//
//
//                  }
//
//
//                                   if (propVector.elementAt(8).toString().equalsIgnoreCase("true")){
//                                      String[] j=row.m_filler.split(" ");
//                    for (int f=0;f<j.length;f++)
//                    {
//                        String domainf= j[f];
//
//                        if (domainf.startsWith("Select"))
//                            break;
//                            Vector vecDom=(Vector) Mpiro.win.struc.getEntityTypeOrEntity(domainf);
//                            vecDom=(Vector) vecDom.elementAt(0);
//                            vecDom=(Vector) vecDom.elementAt(0);
//
//                            for(int a=3;a<vecDom.size();a++)
//                            {
//                                Vector nextEl=(Vector) vecDom.elementAt(a);
//
//                                if(nextEl.elementAt(0).toString().equalsIgnoreCase(row.m_field)&&(!nextEl.elementAt(1).toString().startsWith("Select")))
//                                {
//                                    for(int b=0;b<j.length;b++){
//                                        if(j[b].equalsIgnoreCase(nextEl.elementAt(1).toString())) break;
//                                        else{
//                                            if(b<j.length-1) continue;
//                                            else {row.m_filler= row.m_filler+" "+nextEl.elementAt(1).toString();
//                                            NodeVector obj1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
//
//                                           Vector u= (Vector)obj1.elementAt(0);
//                                           u=(Vector) u.elementAt(0);
//
//                                            for(int g=3;g<u.size();g++)
//                            {
//                                Vector p=(Vector) u.elementAt(g);
//
//                                if(p.elementAt(0).toString().equalsIgnoreCase(row.m_field))
//                                {
//                                    p.setElementAt(row.m_filler,1);
//
//
//                                }
//                                }
//
//                            }}
//                                    }
//
//                                }
//                            }
//
//                    }
//
//                                       Vector domain=new Vector((Vector) propVector.elementAt(0));
//    for(int y=0;y<domain.size();y++){
//        if(row.m_filler.startsWith("Select ")) break;
//        Enumeration en1=DataBasePanel.top.preorderEnumeration();
//        DefaultMutableTreeNode dmtn=new  DefaultMutableTreeNode();
//        while (en1.hasMoreElements())
//        {Object ob1=en1.nextElement();
//            if(ob1.toString().equalsIgnoreCase(domain.elementAt(y).toString()))
//            {
//                dmtn=(DefaultMutableTreeNode) ob1;
//                break;
//            }
//        }
//        Enumeration elems= Mpiro.win.struc.getChildrenEntities(dmtn).elements();
//        Enumeration keys= Mpiro.win.struc.getChildrenEntities(dmtn).keys();
//      //  sMoreElements()){
//        while (elems.hasMoreElements()){
//            Vector childVec1=(Vector) elems.nextElement();
//            Object obj=keys.nextElement();
//
//            Vector childVec=(Vector) childVec1.elementAt(0);
//            childVec=(Vector) childVec.elementAt(0);
//            for(int r=3;r<childVec.size();r++)
//            {
//                Vector propVect= (Vector) childVec.elementAt(r);
//
//                if(propVect.elementAt(0).toString().equalsIgnoreCase(row.m_field))
//                {
//                    String rangeVec= propVect.elementAt(1).toString();
//
//                    String[] j2=rangeVec.split(" ");
//                     for (int f=0;f<j2.length;f++)
//                    {
//                        if (j2[f].equalsIgnoreCase(DataBasePanel.last.toString())){
//                            String newValue=row.m_filler;
//                            for(int u=0;u<j2.length;u++){
//                                if(!newValue.equalsIgnoreCase(j2[u])&&!newValue.contains(" "+j2[u])&&!newValue.contains(j2[u]+" "))
//                                newValue=newValue+" "+j2[u];
//                            }
//
//                            propVect.setElementAt(newValue,1);
//                           // rangeVec=rangeVec+" "+row.m_field;
//                            break;
//                        }
//                    }
                    
                  /*  Vector children=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(domain.elementAt(y).toString(),"entity");
        
       System.out.println("DDDDDd"+Mpiro.win.struc.getChildrenEntities(dmtn).toString());
      //  System.out.println("CCCCC"+childVector.toString());
        while(elems.hasMoreElements()){
            Vector childVect1=(Vector) elems.nextElement();
            Object obj1=keys.nextElement();
            Vector childVect=(Vector) childVect1.elementAt(0);
            childVect=(Vector) childVect.elementAt(0);
            for(int r1=3;r<childVect.size();r++)
            {
                Vector propVect= (Vector) childVect.elementAt(r1);
                System.out.println("childVec.elementAt(r)"+childVec.elementAt(r1).toString());
                if(propVect.elementAt(0).toString().equalsIgnoreCase(row.m_field))
                {
                    String rangeVect= propVect.elementAt(1).toString();
                    System.out.println("rangeVec"+rangeVec);
                    String[] j3=rangeVect.split(" ");
                    for (int f=0;f<j3.length;f++)
                    {
                        if (j3[f].equalsIgnoreCase(DataBasePanel.last.toString())){
                            j3[f]=j3[f]+row.m_field;
                            break;
                        }
                    }
//                    }}}*///}}}}
//
//                                   }
//
//
//                                  Vector Superproperties=(Vector) propVector.elementAt(3);
//                                  for(int l=0;l<Superproperties.size();l++){
//                                      String superProp= Superproperties.elementAt(l).toString();
//                                      Vector entity=(Vector) Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
//                                      entity= (Vector) entity.elementAt(0);
//                                      entity= (Vector) entity.elementAt(0);
//                                      for(int g=3;g<entity.size();g++){
//                                          Vector property=(Vector) entity.elementAt(g);
//                                          if (property.elementAt(0).toString().equalsIgnoreCase(superProp))
//                                          property.set(1,row.m_filler);
//
//                                      }
//                                      if(l==Superproperties.size()-1) DataBaseEntityTable.dbeTable.repaint();
//                                  }
//
////DataBaseEntityTable.dbeTable.repaint();
//  }
//  public void reeee(){
//      String lastSelected=DataBasePanel.last.toString();
//                                                    NodeVector oooo= (NodeVector)  Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
//         if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur"))
//                {
//                    lastSelected=lastSelected.substring(0, lastSelected.length()-7);
//                }
               //  Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
                //    Enumeration allTypesNames=allEntityTypes.keys();
                //    while(allTypesNames.hasMoreElements())
                //    {DefaultMutableTreeNode nextNode=null;
                 //       Object nextEl=allTypesNames.nextElement();
               
                        
                  //       System.out.println(nextEl.toString());
                   //     if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))&&(!(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))))
                    //    {
//                         NodeVector iii= (NodeVector)  Mpiro.win.struc.getEntityTypeOrEntity(lastSelected);
//iii.independentFieldsVector=oooo.independentFieldsVector;
//iii.englishFieldsVector=oooo.englishFieldsVector;
//iii.greekFieldsVector=oooo.greekFieldsVector;
//iii.italianFieldsVector=oooo.independentFieldsVector;
				//}}
                    

                    
  }

  /*
  public void insert(int row) {

	 // insert element (1 row)in current table vector
	if (row < 0)
	  row = 0;
	if (row > m_vector.size())
	  row = m_vector.size();
	m_vector.insertElementAt(new FieldData(), row);

  }

  public boolean delete(int row) {
	if (row < 0 || row >= m_vector.size())
	 {
	  return false;
	 }
	 // remove element (1 row) from current table's vector
	m_vector.remove(row);
	return true;
  }
  */

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
