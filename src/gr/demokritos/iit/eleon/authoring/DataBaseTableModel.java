//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.MessageDialog;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.JTree;


/**
 * <p>Title: CreateSolemlDialog</p>
 * <p>Description: An AbstractTableModel extension for DataBaseTable
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos, Kostas Stamatakis
 * @version 1.0
 */
public class DataBaseTableModel extends AbstractTableModel
{
  public static final ColumnData m_columns[] = 
  {
    new ColumnData( LangResources.getString(Mpiro.selectedLocale, "fields_tabletext"), 120, JLabel.LEFT ),
    new ColumnData( LangResources.getString(Mpiro.selectedLocale, "fillerTypes_tabletext"), 120, JLabel.LEFT ),
    new ColumnData( LangResources.getString(Mpiro.selectedLocale, "many_tabletext"), 15, JLabel.CENTER ),
    new ColumnData( LangResources.getString(Mpiro.selectedLocale, "microplanning_tabletext"), 70, JLabel.LEFT ),
  };

  public static final int COL_FIELD = 0;
  public static final int COL_FILLER = 1;
  public static final int COL_APPROVED = 2;
  public static final int COL_MPLANNING = 3;

  public static final FillerCombo fillerCombo = new FillerCombo();

  static Vector m_vector;
  protected int m_rowActive;
  protected Vector m_initVector= new Vector();//maria

  /**
   * Constructor
   * @param initVector The vector that describes the model
   * @param rowActive The row number active when the table starts being editable
   */
  public DataBaseTableModel(Vector initVector, int rowActive)
  {
		m_vector = new Vector();
		//for(int s=1;s<initVector.size();s++)//maria
		//{//maria
		//	m_initVector.addElement(initVector.elementAt(s));//maria
		//}//maria
		m_initVector = initVector;//maria
		m_rowActive = rowActive;//maria -1
		setDefaultData();
  }

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

  public Class getColumnClass(int c)
  {
    return getValueAt(0, c).getClass();
  }

  public String getColumnName(int column) 
  {
    return m_columns[column].m_title;
  }

  public boolean isCellEditable(int nRow, int nCol)
  {
		if ((nCol < 4 && nRow < m_rowActive) || (nCol == 3))
		{
			return false;
		} 
		else 
		{
			return true;
		}
  }

  public Object getValueAt(int nRow, int nCol) 
  {
    if (nRow < 0 || nRow>=getRowCount())
		{
      return "";
		}
    System.out.println("m_vector.elementAt(nRow)   :  "+m_vector.elementAt(nRow).toString());
    FieldData row = (FieldData)m_vector.elementAt(nRow);
    switch (nCol) 
    {
      case COL_FIELD: return row.m_field;
      case COL_FILLER: return row.m_filler;
      case COL_APPROVED: return row.m_approved;
      case COL_MPLANNING: return row.m_mplanning;
    }
    return "";
  }


	/**
	 * Overriden method for setting new value for a cell.
	 * Methods from QueryHashtable are used which,
	 * when a value changes in a node's DataBaseTable, update
	 * all node's children so as to ensure inheritance.
	 * @param value The new value
	 * @param nRow The row number
	 * @param nCol The column number
	 */
  public void setValueAt(Object value, int nRow, int nCol) 
  {
		// Conditions which may stop process
		if (nRow < 0 || nRow>=getRowCount())
		{
			return;
		}

		// Get the old value
		Object oldObject = (Object)getValueAt(nRow, nCol);
		//Class testClass = oldObject.getClass();
		//String oldValue = oldObject.toString();
		String oldValue;
		
		// Get the new value
		FieldData row = (FieldData)m_vector.elementAt(nRow);
		String svalue = value.toString();
		
		FieldData field = (FieldData)m_vector.elementAt(nRow);
		
		// The 4 column-cases
    switch (nCol) 
    {
      case COL_FIELD:

				oldValue = oldObject.toString();
                                 //String content=DataBaseTable.dbTable.getModel().getValueAt(row, DataBaseTable.dbTable.getSelectedColumn()).toString();
               if(oldValue.contains("http://")){
                    
                     Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(null, //theofilos
                                        "Selected item cannot be renamed, because it is imported from another ontology",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                       return;         
                            
                    
//                    Model model = ModelFactory.createDefaultModel();
//                    Resource n = model.createResource(ren);
//                    ren=n.getLocalName();
                }

				String checkName = QueryHashtable.checkNameValidity(svalue);
				Vector fieldNamesVector = new Vector();
				fieldNamesVector = QueryHashtable.getExistingFieldnamesForEntityTypeAndChildren(DataBasePanel.topA);
				/*
				Vector fieldNamesVector = new Vector();
				Enumeration enum = DataBaseTable.m_data.m_vector.elements();
				while (enum.hasMoreElements()) {
				     Vector rowVector = (Vector)enum.nextElement();
				     if (rowVector.elementAt(0) != null) {
				         String fieldName = rowVector.get(0).toString();
				         fieldNamesVector.addElement(fieldName);
				     }
				}
				*/
				if ((fieldNamesVector.contains(svalue)) && (!(svalue.equalsIgnoreCase(oldValue))))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.thisFieldNameAlreadyExists_dialog);
					return;
				}
	 			else if (svalue.indexOf(" ") > 0 || svalue.startsWith(" "))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.noSpacesAreAllowedForAFieldName_dialog);
					return;
				}
				else if (svalue.equalsIgnoreCase(""))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.pleaseGiveANameForTheField_dialog);
					return;
				}
				else if (!checkName.equalsIgnoreCase("VALID"))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog + "\n" + checkName);
				}
				else
				{
					// Get the second column value (fillerType)
					String fillerType = (String)getValueAt(nRow, nCol+1);
					QueryHashtable.updateChildrenEntitiesFieldColumn(oldValue, svalue, fillerType);
					QueryHashtable.renameHashtableField(DataBasePanel.last.toString(), oldValue, svalue);
							//maria
					row.m_field = svalue;
                                        
                                  //      Object propValue=QueryHashtable.propertiesHashtable.remove(oldValue);
                                    //    if (propValue==null)
                                      //      propValue=new propertiesHashtableRecord(row.m_filler);
                                       // QueryHashtable.propertiesHashtable.put(svalue, propValue);
                                        
                                        
                                        
                                     //   occur(DataBasePanel.last.toString() , oldValue, svalue, fillerType);
                                        
                                        Vector property=(Vector) QueryHashtable.propertiesHashtable.remove(oldValue);
                                        QueryHashtable.propertiesHashtable.put(svalue, property);
                                        property=(Vector) QueryHashtable.propertiesHashtable.get(svalue);
                                        Vector Domain=(Vector) property.elementAt(0);
                                        for(int y=0;y<Domain.size();y++){
                                            occur(Domain.elementAt(y).toString() , oldValue, svalue, fillerType);
                                        }
                                        
                                }
                                QueryUsersHashtable.renameFieldInUserModelHashtable(oldValue, svalue);//==
                                QueryUsersHashtable.renameFieldInRobotsModelHashtable(oldValue, svalue);
                                QueryHashtable.renameFieldInRestrictionsHashtable(oldValue, svalue);
					Mpiro.needExportToExprimo = true;
                                break;
      case COL_FILLER:

			  oldValue = oldObject.toString();
		
			  // Get the first column value (fieldName)
			  String fieldName = (String)getValueAt(nRow, nCol-1);
			  QueryHashtable.updateChildrenEntitiesFillerColumn(DataBasePanel.last, oldValue, svalue, fieldName);
		    Mpiro.needExportToExprimo = true;		//maria
		    row.m_filler = svalue;
			  row.m_approved = new Boolean(false);
		
			  field.setElementAt(new Boolean(false), nCol+1);
                          
                          fillerUpdate( DataBasePanel.last.toString() , oldValue, svalue, fieldName);
                         
Vector propValue=(Vector) QueryHashtable.propertiesHashtable.remove(row.m_field);
                                        if (propValue==null)
                                            propValue=new PropertiesHashtableRecord(svalue);
Vector range=(Vector) propValue.elementAt(1);
range.removeAllElements();
range.add(svalue);
                                        QueryHashtable.propertiesHashtable.put(row.m_field, propValue);
                                        System.out.println("bbbbbbbb"+propValue.toString());
                          
                          
		
			  DataBaseTable.dbTable.revalidate();
			  DataBaseTable.dbTable.repaint();
		
			  //QueryHashtable.updateChildrenBasicTableVectors(nRow, true);
        break;

   /*   case COL_APPROVED:
        
        
				row.m_approved = (Boolean)value;
				String filler = (String)getValueAt(nRow, nCol-1);
				Enumeration enu = DataBasePanel.topB.children();
				while (enu.hasMoreElements()) 
				{
				  if (filler.equalsIgnoreCase(enu.nextElement().toString()))
				  {
						value = new Boolean(false);
						row.m_approved = (Boolean)value;
				  }
				}
                                
                                approvedUpdate(DataBasePanel.last.toString(), row.m_field, row.m_approved, filler, nRow, nCol);

                           
                                
                                
                              
				if (value.toString().equalsIgnoreCase("true"))
				{
					QueryHashtable.updateChildrenEntitiesFillerColumn
										(DataBasePanel.last,
										filler, "Select multiple .....",
										getValueAt(nRow, nCol-2).toString());
					Mpiro.needExportToExprimo = true;		//maria
				}
				else
				{
					QueryHashtable.updateChildrenEntitiesFillerColumn
										(DataBasePanel.last,
				          	filler, filler,
										getValueAt(nRow, nCol-2).toString());
					Mpiro.needExportToExprimo = true;		//maria
				}
                        
                       
        break;*/

      case COL_MPLANNING:
             
        microplanningUpdate(DataBasePanel.last.toString(),row.m_field, svalue);
            
          
    //  row.m_mplanning = svalue;
      break;
		
    }
		/* Update current vector */
		field.setElementAt(value, nCol);
		
		// Updating BasicTypes & EntityTypes (children)
		QueryHashtable.updateChildrenBasicTableVectors(nRow);
	}
  
  
  
  
    public void setValueAt(Object value, int nRow, int nCol, String node) 
  {System.out.println("DDDDDDDDDDD   "+node+"  "+String.valueOf(nRow)+String.valueOf(nCol));
		// Conditions which may stop process
		if (nRow < 0 || nRow>=getRowCount())
		{
			return;
		}

		// Get the old value
		Object oldObject = (Object)getValueAt(nRow, nCol);
		//Class testClass = oldObject.getClass();
		//String oldValue = oldObject.toString();
		String oldValue;
		
		// Get the new value
		FieldData row = (FieldData)m_vector.elementAt(nRow);
		String svalue = value.toString();
		
		FieldData field = (FieldData)m_vector.elementAt(nRow);
		System.out.println("sssss");
		// The 4 column-cases
    switch (nCol) 
    {
      case COL_FIELD:

          
          
  
          
          
				oldValue = oldObject.toString();

				String checkName = QueryHashtable.checkNameValidity(svalue);
				Vector fieldNamesVector = new Vector();
				fieldNamesVector = QueryHashtable.getExistingFieldnamesForEntityTypeAndChildren(DataBasePanel.topA);
				/*
				Vector fieldNamesVector = new Vector();
				Enumeration enum = DataBaseTable.m_data.m_vector.elements();
				while (enum.hasMoreElements()) {
				     Vector rowVector = (Vector)enum.nextElement();
				     if (rowVector.elementAt(0) != null) {
				         String fieldName = rowVector.get(0).toString();
				         fieldNamesVector.addElement(fieldName);
				     }
				}
				*/
				if ((fieldNamesVector.contains(svalue)) && (!(svalue.equalsIgnoreCase(oldValue))))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.thisFieldNameAlreadyExists_dialog);
					return;
				}
	 			else if (svalue.indexOf(" ") > 0 || svalue.startsWith(" "))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.noSpacesAreAllowedForAFieldName_dialog);
					return;
				}
				else if (svalue.equalsIgnoreCase(""))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.pleaseGiveANameForTheField_dialog);
					return;
				}
				else if (!checkName.equalsIgnoreCase("VALID"))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog + "\n" + checkName);
				}
				else
				{
					// Get the second column value (fillerType)
					String fillerType = (String)getValueAt(nRow, nCol+1);
					QueryHashtable.updateChildrenEntitiesFieldColumn(oldValue, svalue, fillerType);
					QueryHashtable.renameHashtableField(node, oldValue, svalue);
							//maria
					row.m_field = svalue;
                                        
                                        Object propValue=QueryHashtable.propertiesHashtable.remove(oldValue);
                                        if (propValue==null)
                                            propValue=new PropertiesHashtableRecord(row.m_filler);
                                        QueryHashtable.propertiesHashtable.put(svalue, propValue);
                                        
                                        
                                        
                                        occur(node , oldValue, svalue, fillerType);
                                        
                                }
                                QueryUsersHashtable.renameFieldInUserModelHashtable(oldValue, svalue);//==
                                 QueryUsersHashtable.renameFieldInRobotsModelHashtable(oldValue, svalue);
					Mpiro.needExportToExprimo = true;
                                break;
      case COL_FILLER:

			  oldValue = oldObject.toString();
		
			  // Get the first column value (fieldName)
			  String fieldName = (String)getValueAt(nRow, nCol-1);
			  QueryHashtable.updateChildrenEntitiesFillerColumn(DataBasePanel.last, oldValue, svalue, fieldName);
		    Mpiro.needExportToExprimo = true;		//maria
		    row.m_filler = svalue;
			  row.m_approved = new Boolean(false);
		
			  field.setElementAt(new Boolean(false), nCol+1);
                          
                          fillerUpdate( node , oldValue, svalue, fieldName);
                         
Object propValue=QueryHashtable.propertiesHashtable.remove(row.m_field);
                                        if (propValue==null)
                                            propValue=new PropertiesHashtableRecord(svalue);
                                        QueryHashtable.propertiesHashtable.put(row.m_field, propValue);
                          
                          
		
			  DataBaseTable.dbTable.revalidate();
			  DataBaseTable.dbTable.repaint();
		
			  //QueryHashtable.updateChildrenBasicTableVectors(nRow, true);
        break;

      case COL_APPROVED:
        
        System.out.println("vvvvvvvvvvv");
				row.m_approved = (Boolean)value;
				String filler = (String)getValueAt(nRow, nCol-1);
				Enumeration enu = DataBasePanel.topB.children();
                                System.out.println("ooooooooooooo");
				while (enu.hasMoreElements()) 
				{
				  if (filler.equalsIgnoreCase(enu.nextElement().toString()))
				  {
						value = new Boolean(false);
						row.m_approved = (Boolean)value;
				  }
				}
                                System.out.println("dddddd");
                                approvedUpdate(node, row.m_field, row.m_approved, filler, nRow, nCol);

                                                                                                             
                 // if (elementAt(6).toString().equalsIgnoreCase("true")){  
                 
                //  }
                                 Vector propVector=(Vector) QueryHashtable.propertiesHashtable.get(row.m_field);                  
                  if(propVector==null) propVector=new PropertiesHashtableRecord();     
                              
				if (value.toString().equalsIgnoreCase("true"))
				{
                               propVector.setElementAt("false",6);
					QueryHashtable.updateChildrenEntitiesFillerColumn
										(DataBasePanel.last,
										filler, "Select multiple .....",
										getValueAt(nRow, nCol-2).toString());
					Mpiro.needExportToExprimo = true;		//maria
				}
				else
				{
                               propVector.setElementAt("true",6);
					QueryHashtable.updateChildrenEntitiesFillerColumn
										(DataBasePanel.last,
				          	filler, filler,
										getValueAt(nRow, nCol-2).toString());
					Mpiro.needExportToExprimo = true;		//maria
				}
                        
                       
        break;

      case COL_MPLANNING:
             
        microplanningUpdate(node, row.m_field, svalue);
            
          
    //  row.m_mplanning = svalue;
      break;
		
    }
		/* Update current vector */
		field.setElementAt(value, nCol);
		
		// Updating BasicTypes & EntityTypes (children)
		QueryHashtable.updateChildrenBasicTableVectors(nRow);
	}
  
  
  
  public void microplanningUpdate(String last, String rowMField, String svalue)
  {
      String lastSelected2=last;     
                                                                 
                if(lastSelected2.substring(0,lastSelected2.length()-1).endsWith("_occur"))
                {
                    lastSelected2=lastSelected2.substring(0, lastSelected2.length()-7);
                }
                 Hashtable allEntityTypes2 = (Hashtable) QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames2=allEntityTypes2.keys();
                    while(allTypesNames2.hasMoreElements())
                    {DefaultMutableTreeNode nextNode=null;
                        Object nextEl=allTypesNames2.nextElement();
               
                        
                         
                        if ((nextEl.toString().startsWith(lastSelected2+"_occur"))||(nextEl.toString().equalsIgnoreCase(lastSelected2)))
                        {
                             Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                         
                         while(topchildren.hasMoreElements())
                         {
                               nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                                if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                                   break;
                         }
                               NodeVector entityTypeParentNode = (NodeVector) QueryHashtable.mainDBHashtable.get(nextNode.toString());
                             Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
                             FieldData property=null;
                             for (int h=parentDatabaseTableVector.size();h>0;h--){
                             property = (FieldData) parentDatabaseTableVector.elementAt(h-1);
                             if (property.elementAt(0).toString().equalsIgnoreCase(rowMField)) break;
                             }
                       property.remove(3);
                             property.add(3,svalue);
                             FieldData prop= (FieldData) property;
                             prop.m_mplanning=svalue;
                        }}
                    
             //       Vector Children=QueryHashtable.getChildrenVectorFromMainDBHashtable(last,"entity type");
               //     for(int c=0;c<Children.size();c++)
                 //   {
                   //     microplanningUpdate(Children.elementAt(c).toString(), rowMField, svalue);
                     //   System.out.println(Children.elementAt(c).toString());
                    //}
  }
  
  public void approvedUpdate(String last, String rowMField, Boolean value, String filler, int nRow, int nCol)
  {
                                   String lastSelected1=last;     
                                        System.out.println("llllast"+last+"row"+rowMField+"filler"+filler);                         
                if(lastSelected1.substring(0,lastSelected1.length()-1).endsWith("_occur"))
                {
                    lastSelected1=lastSelected1.substring(0, lastSelected1.length()-7);
                }
                 Hashtable allEntityTypes1 = (Hashtable) QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames1=allEntityTypes1.keys();
                    while(allTypesNames1.hasMoreElements())
                    {DefaultMutableTreeNode nextNode=null;
                        Object nextEl=allTypesNames1.nextElement();
               
                        
                         
                        if (nextEl.toString().startsWith(lastSelected1+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected1)))
                        {
                             Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                         
                         while(topchildren.hasMoreElements())
                         {
                               nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                                if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                                   break;
                         }
                              Vector propVector=(Vector) QueryHashtable.propertiesHashtable.get(rowMField);                  
                  if(propVector==null) propVector=new PropertiesHashtableRecord();     
                            if (String.valueOf(value).equalsIgnoreCase("true"))
				{
                                  propVector.setElementAt("false",6);
					QueryHashtable.updateChildrenEntitiesFillerColumn
										(nextNode,
										filler, "Select multiple .....",
										getValueAt(nRow, nCol-2).toString());
					Mpiro.needExportToExprimo = true;	
                                        //maria
                                        //System.out.println("testttt1"+nextNode.toString());
                                        NodeVector entityTypeParentNode = (NodeVector) QueryHashtable.mainDBHashtable.get(nextNode.toString());
                             Vector parentDatabaseTableVector = (Vector) entityTypeParentNode.elementAt(0);
                              FieldData property=null;
                             for (int h=parentDatabaseTableVector.size();h>0;h--){
                             property = (FieldData) parentDatabaseTableVector.elementAt(h-1);
                             if (property.elementAt(0).toString().equalsIgnoreCase(rowMField)) break;
                             }
                             property.remove(2);
                             property.add(2, "true");
                             FieldData prop=(FieldData) property;
                             prop.m_approved=new Boolean("True");
				}
				else
				{
                                  propVector.setElementAt("true",6);
					QueryHashtable.updateChildrenEntitiesFillerColumn
										(nextNode,
				          	filler, filler,
										getValueAt(nRow, nCol-2).toString());
					Mpiro.needExportToExprimo = true;		//maria
                                        //System.out.println("testttt2"+nextNode.toString());
                                         NodeVector entityTypeParentNode = (NodeVector) QueryHashtable.mainDBHashtable.get(nextNode.toString());
                             Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
                             FieldData property=null;
                             for (int h=parentDatabaseTableVector.size();h>0;h--){
                             property = (FieldData) parentDatabaseTableVector.elementAt(h-1);
                             if (property.elementAt(0).toString().equalsIgnoreCase(rowMField)) break;
                             }
                             property.remove(2);
                             property.add(2, "false");
                              FieldData prop=(FieldData) property;
                             prop.m_approved=new Boolean("False");
				}
                            
                            
                        }}
                    
                    Vector Children=QueryHashtable.getChildrenVectorFromMainDBHashtable(last,"entity type");
                    for(int c=0;c<Children.size();c++)
                    {
                        approvedUpdate(Children.elementAt(c).toString(), rowMField, value, filler, nRow, nCol);
                        System.out.println(Children.elementAt(c).toString());
                    }
  }
  
  
  public void fillerUpdate(String last, String oldValue, String svalue, String fieldName)
  {
                                String lastSelected=last;
                if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur"))
                {
                    lastSelected=lastSelected.substring(0, lastSelected.length()-7);
                }
                 Hashtable allEntityTypes = (Hashtable) QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames=allEntityTypes.keys();
                    while(allTypesNames.hasMoreElements())
                    {DefaultMutableTreeNode nextNode=null;
                        Object nextEl=allTypesNames.nextElement();
               
                        
                         
                        if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected))))
                        {
                            //System.out.println("sddssdsdfsdfsdfadasad"+nextEl.toString());
                             Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                         
                         while(topchildren.hasMoreElements())
                         {
                               nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                               //System.out.println("2121212221212"+nextNode.toString());
                               if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                                   break;
                         }
                             			  QueryHashtable.updateChildrenEntitiesFillerColumn(nextNode, oldValue, svalue, fieldName);
                             NodeVector entityTypeParentNode = (NodeVector) QueryHashtable.mainDBHashtable.get(nextNode.toString());
                             Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
                           //  entityTypeParentNode.databaseTableVector
                              FieldData property=null;
                             for (int h=parentDatabaseTableVector.size();h>0;h--){
                             property = (FieldData) parentDatabaseTableVector.elementAt(h-1);
                           //  System.out.println("LLLLLLLLLLL"+property.elementAt(0).toString()+getValueAt(nRow, 0).toString());
                             if (property.elementAt(0).toString().equalsIgnoreCase(fieldName)) break;
                             }
                             
                              
                             property.remove(1);
                             property.add(1, svalue);
                             property.remove(2);
                             property.add(2, "false");
                             FieldData prop= (FieldData) property;
                             prop.m_filler=svalue;
                             prop.m_approved= new Boolean("False");
                             
                            // parentDatabaseTableVector.removeElementAt(parentDatabaseTableVector.size()-1);
                            // //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa"+property.toString());
                            // parentDatabaseTableVector.add(parentDatabaseTableVector.size(), property);
				}
	   		
                    }
                  
                     Vector Children=QueryHashtable.getChildrenVectorFromMainDBHashtable(last,"entity type");
                    for(int c=0;c<Children.size();c++)
                    {
                        fillerUpdate(Children.elementAt(c).toString() , oldValue, svalue, fieldName);
                        System.out.println(Children.elementAt(c).toString());
                    }
  }
  
  
  public void occur(String last, String oldValue, String svalue, String fillerType)
  {
      String lastSelected=last;
                if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur"))
                {
                    lastSelected=lastSelected.substring(0, lastSelected.length()-7);
                }
                 Hashtable allEntityTypes = (Hashtable) QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames=allEntityTypes.keys();
                    while(allTypesNames.hasMoreElements())
                    {DefaultMutableTreeNode nextNode=null;
                        Object nextEl=allTypesNames.nextElement();
               
                        
                         System.out.println(nextEl.toString());
                        if (nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))//&&(!(nextEl.toString().equalsIgnoreCase(last))))
                        {
                           // System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXxx"+nextEl.toString()+last+"  "+lastSelected);
                             Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                         
                         while(topchildren.hasMoreElements())
                         {
                               nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                               //System.out.println("2121212221212"+nextNode.toString());
                               if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                                   break;
                         }
                            // System.out.println("sddssdsdfsdfsdfadasad"+nextEl.toString());
                             QueryHashtable.updateChildrenEntitiesFieldColumn(oldValue, svalue, fillerType, nextNode);
                             QueryHashtable.renameHashtableField(nextEl.toString(), oldValue, svalue);
                             NodeVector entityTypeParentNode = (NodeVector) QueryHashtable.mainDBHashtable.get(nextEl.toString());
                             Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
                             Vector property=null;
                             for (int h=parentDatabaseTableVector.size();h>0;h--){
                             property = (Vector) parentDatabaseTableVector.elementAt(h-1);
                             System.out.println("GGGGGGGGGGGGGG"+property.toString());
                             System.out.println("SSSSSSSSS"+oldValue+property.elementAt(0).toString());
                     
                             if (property.elementAt(0).toString().equalsIgnoreCase(oldValue)) break;
                             }
                             //System.out.println(property.m_field+"aaaaas"+svalue);
                             if (!(property.elementAt(0).toString().equalsIgnoreCase("Subtype-of"))){
                             property.remove(0);
                             property.add(0, svalue);
                            FieldData prop= (FieldData) property;
                            prop.m_field=svalue;
                            // property.m_field=svalue;
                            // parentDatabaseTableVector.removeElementAt(parentDatabaseTableVector.size()-1);
                            // //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa"+property.toString());
                            // parentDatabaseTableVector.add(parentDatabaseTableVector.size(), property);
				}}
	   		
                    }
                    
                    Vector Children=QueryHashtable.getChildrenVectorFromMainDBHashtable(last,"entity type");
                    for(int c=0;c<Children.size();c++)
                    {
                        System.out.println("FFFSDSSSSSA"+Children.elementAt(c).toString());
                        occur(Children.elementAt(c).toString() , oldValue, svalue, fillerType);
                        
                    }
  }

  public void insert(int row) 
  {
		QueryHashtable.insertRowInDataBaseTable(row);
  }

  public boolean delete(int row) 
  {
		String field = (String)getValueAt(row, 0);
		String filler = (String)getValueAt(row, 1);
		QueryHashtable.removeHashtableField(DataBasePanel.last.toString(), field);
		////!!!!!!! TO BE FIXED!!!!!!QueryUsersHashtable.removeFieldInUserModelHashtable(field);
                QueryHashtable.DeletePropertyFromPropertiesHashtable(field);
                //QueryHashtable.removeFieldFromDomain(field, QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
		return (boolean)QueryHashtable.removeRowFromDataBaseTable(field, filler, row);
  }
  
  public boolean delete(int row,String node) 
  {
		String field = (String)getValueAt(row, 0);
		String filler = (String)getValueAt(row, 1);
		QueryHashtable.removeHashtableField(node, field);
                //System.out.println("pppppppopkl"+field);
		////!!!!!!! TO BE FIXED!!!!!!QueryUsersHashtable.removeFieldInUserModelHashtable(field);
		return (boolean)QueryHashtable.removeRowFromDataBaseTable(field, filler, row, node);
  }

  /*
  public Vector getDataVector() 
  {
		return m_vector;
  }

  public void setDataVector(Vector v) 
  {
		m_vector.removeAllElements();
		m_vector = v;
  }
  */

}