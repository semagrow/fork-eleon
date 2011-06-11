//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.ui.DDialog;
import gr.demokritos.iit.eleon.ui.NumberCombo;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.JTree;
import java.util.*;


/**
 * <p>Title: DataBaseEntityTableListener</p>
 * <p>Description: The listener for the DataBaseEntityTable</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class DataBaseEntityTableListener extends MouseAdapter {
    
    Object cellContent = " ";
    JPopupMenu popup;
    public static int rowNo;
    public static int colNo;
    static String selectedField;
    
    private String editInterestImportanceRepetitions_action = LangResources.getString(Mpiro.selectedLocale, "editInterestImportanceRepetitions_action");
    
    public void mouseReleased(MouseEvent re) {
        if (SwingUtilities.isRightMouseButton(re)) {
            mousePressed(re);
        }
    }
    
    public void mousePressed(MouseEvent re) {
      //  Hashtable hash=QueryHashtable.valueRestrictionsHashtable;
    //    if(DataBaseEntityTable.dbeTable.getValueAt(rowNo, 0).toString().equalsIgnoreCase("number")||DataBaseEntityTable.dbeTable.getValueAt(rowNo, 0).toString().equalsIgnoreCase("grammatical gender of name")) return;
        if (re.getClickCount() > 1) {
            
            //opou ki an kanw double click einai san na kanw edit(akoma kai gia number h string
            Mpiro.needExportToExprimo=true;			//maria
            
            //System.out.println("(DataBaseEntityTableListener)--- === DOUBLE CLICK ===");
            int x = re.getX();
            int y = re.getY();
            Point p = new Point(x,y);
            rowNo = DataBaseEntityTable.dbeTable.rowAtPoint(p);
           
            String entityFieldName = DataBaseEntityTable.dbeTable.getValueAt(rowNo, 0).toString();
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
            NodeVector parentNodeVector = (NodeVector)Mpiro.win.ontoPipe.getExtension().getEntityTypeOrEntity(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(parentNode.toString()));
            Vector parentTableVector = (Vector)parentNodeVector.get(0); // DataBaseTable
            
            String parentFieldName = new String();
            String parentFillerType = new String();
            //String parentSetValued = new String();
            
            if (entityFieldName.compareTo("images") == 0) {
                TableCellEditor rrrr = DataBaseEntityTable.dbeTable.rm.getEditor(rowNo);
                rrrr.cancelCellEditing();
            } else {
                for (int m=0; m<parentTableVector.size(); m++) {
                    // if(entityFieldName.equalsIgnoreCase("number")) break;
                    // Get parent node's DataBaseTable rows at start to end
                    // and find the field we want.
                    
                    FieldData pfd = (FieldData)parentTableVector.elementAt(m);
                    //DefaultMutableTreeNode fieldNameNode = (DefaultMutableTreeNode)pfd.elementAt(0);/////
                    parentFieldName = pfd.elementAt(0).toString();
                    parentFillerType = pfd.elementAt(1).toString();
                 //   parentSetValued = pfd.elementAt(2).toString();
                    //System.out.println("(DataBaseEntityTableListener) " + entityFieldName + "     " + parentFieldName + " " + parentFillerType + " " + parentSetValued);
                    
                    if (entityFieldName.equalsIgnoreCase(parentFieldName) ) {
                        // check if selected field is Date, Dimension, or Set
                        //if (entityFieldName.equalsIgnoreCase(parentFieldName) )
                        //{
                        
                        //}
                        
                        if (parentFillerType.equalsIgnoreCase("Date")) {
                            TableCellEditor rrrr = DataBaseEntityTable.dbeTable.rm.getEditor(rowNo);
                            rrrr.cancelCellEditing();
                            break;
                        }
                        
                        if (parentFillerType.equalsIgnoreCase("Dimension")) {
                            TableCellEditor rrrr = DataBaseEntityTable.dbeTable.rm.getEditor(rowNo);
                            rrrr.cancelCellEditing();
                            break;
                        }
                        if (parentFillerType.equalsIgnoreCase("Number")) {
                            break;
                        }
                        
                    //    if (parentSetValued.equalsIgnoreCase("true") ) {
                    //        TableCellEditor rrrr = DataBaseEntityTable.dbeTable.rm.getEditor(rowNo);
                    //        try{
                    //        rrrr.cancelCellEditing();
                    //        }catch(java.lang.NullPointerException npe){}
                    //    }
                    }
                }
            }
            return;
        }
        if (SwingUtilities.isLeftMouseButton(re)) { // left has been clicked
            
            int x = re.getX();
            int y = re.getY();
            Point p = new Point(x,y);
            rowNo = DataBaseEntityTable.dbeTable.rowAtPoint(p);
            colNo = DataBaseEntityTable.dbeTable.columnAtPoint(p);
            if (colNo == 0) {return;}
            String cellContent = DataBaseEntityTable.dbeTable.getValueAt(rowNo, 1).toString();
            
            String entityFieldName = DataBaseEntityTable.dbeTable.getValueAt(rowNo, 0).toString();
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
            NodeVector parentNodeVector = (NodeVector)Mpiro.win.ontoPipe.getExtension().getEntityTypeOrEntity(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(parentNode.toString()));
            Vector parentTableVector = (Vector)parentNodeVector.get(0); // DataBaseTable
            
             
         //   TableCellEditor tce=DataBaseEntityTable.dbeTable.getCellEditor(rowNo,colNo);
          //  tce=
         //   String parentFieldName = new String();
          //  String parentFillerType = new String();
      //      String parentSetValued = new String();
            
            if(entityFieldName.equalsIgnoreCase("number")){
                TableCellEditor tce=new DefaultCellEditor(new NumberCombo());
             RowEditorModel rem= DataBaseEntityTable.dbeTable.getRowEditorModel();
             rem.addEditorForRow(rowNo,tce);
             return;
            }
            if(entityFieldName.equalsIgnoreCase("gender")){
                TableCellEditor tce=new DefaultCellEditor(new GenderCombo());
             RowEditorModel rem= DataBaseEntityTable.dbeTable.getRowEditorModel();
             rem.addEditorForRow(rowNo,tce);
             return;
            }
           if(entityFieldName.equalsIgnoreCase("notes") || entityFieldName.equalsIgnoreCase("grammatical gender of name")) return;
            // check if selected field is Images
            if (entityFieldName.equalsIgnoreCase("Images")) {
                TableCellEditor rrrr = DataBaseEntityTable.dbeTable.rm.getEditor(rowNo);
                rrrr.cancelCellEditing();
                DDialog dDialog = new DDialog(LangResources.getString(Mpiro.selectedLocale, "selectImages_text"),
                        null,
                        LangResources.getString(Mpiro.selectedLocale, "newImages_text"),
                        null, //new Vector(),
                        cellContent,
                        "IMAGES");
            }
            
            else {
               // for (int m=0; m<parentTableVector.size(); m++) {//
                    if(!(entityFieldName.equalsIgnoreCase("number")||entityFieldName.equalsIgnoreCase("name")||entityFieldName.equalsIgnoreCase("notes")||entityFieldName.equalsIgnoreCase("title")||entityFieldName.equalsIgnoreCase("gender")||entityFieldName.equalsIgnoreCase("shortname"))){
                    // Get parent node's DataBaseTable rows at start to end
                    // and find the field we want.
                         Vector vec = new Vector();
                         Vector domains=new Vector();
                //    if(entityFieldName.equalsIgnoreCase("number")) break;
                  //  FieldData pfd = (FieldData)parentTableVector.elementAt(m);
                    //DefaultMutableTreeNode fieldNameNode = (DefaultMutableTreeNode)pfd.elementAt(0);/////
               domains=     ((Vector)((Vector)Mpiro.win.ontoPipe.getExtension().getProperty(entityFieldName)).elementAt(1));
               //     parentFillerType=parentFieldName = pfd.elementAt(0).toString();
                  //  parentFillerType =  pfd.elementAt(1).toString();
              //      parentSetValued = pfd.elementAt(2).toString();
                    //System.out.println("(DataBaseEntityTableListener) " + entityFieldName + "     " + parentFieldName + " " + parentFillerType + " " + parentSetValued);
                    
                    // check if selected field is Date, Dimension, or Set
                //    if (entityFieldName.equalsIgnoreCase(parentFieldName)) {
                        if (!Mpiro.win.ontoPipe.getExtension().existsValueRestriction(parentNode.toString()+":"+entityFieldName))
                            Mpiro.win.ontoPipe.getExtension().addValueRestriction(parentNode.toString()+":"+entityFieldName, new ValueRestriction());
                        Vector allValues=Mpiro.win.ontoPipe.getExtension().getValueRestriction(parentNode.toString()+":"+entityFieldName);
                        //   String cardinalities= allValues.elementAt(3).toString().equalsIgnoreCase("")?"-1 ": allValues.elementAt(3).toString()+" ";
                        // cardinalities= allValues.elementAt(4).toString().equalsIgnoreCase("")?cardinalities+"-1 ":cardinalities+allValues.elementAt(4).toString()+" ";
                        //cardinalities= allValues.elementAt(5).toString().equalsIgnoreCase("")?cardinalities+"-1 ":cardinalities+allValues.elementAt(5).toString()+" ";
                        allValues=(Vector) allValues.elementAt(0);
                        
                        if(!allValues.isEmpty()){
                            domains.clear();
                            domains.add(allValues.elementAt(0).toString());}
                        // Then check whether parent is set-valued.
                        // If "true" then trigger a DDialog for SET
                        
                            String name=domains.elementAt(0).toString();
                        if (!name.equalsIgnoreCase("Date")&&!name.equalsIgnoreCase("Number")&&!name.equalsIgnoreCase("Dimension")&&!name.equalsIgnoreCase("String") ) {
                           for(int i=0;i<domains.size();i++){
                               name=domains.elementAt(i).toString();
                                DefaultMutableTreeNode targetNode = null;
                            Enumeration eni = DataBasePanel.top.preorderEnumeration();
                            while (eni.hasMoreElements()) {
                                DefaultMutableTreeNode node = (DefaultMutableTreeNode)eni.nextElement();
                                String nodeName = node.toString();
                                if (nodeName.equalsIgnoreCase(name)) {
                                    targetNode = node;
                                    break;
                                }
                            }
                            
                            Hashtable childrenEntitiesHashtable = (Hashtable)Mpiro.win.ontoPipe.getExtension().getChildrenEntities(targetNode);
                           // Vector vec = new Vector();
                            Enumeration ena = childrenEntitiesHashtable.keys();
                            
                            while (ena.hasMoreElements()) {
                                String next=ena.nextElement().toString();
                                boolean contains=false;
                                for(int k=0;k<vec.size();k++){
                                    if(vec.elementAt(k).toString().equalsIgnoreCase(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(next)))
                                    {contains=true;
                                    break;
                                }}
                                if(!contains)
                                    vec.addElement(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(next));
                                //vec.addElement(new ListData(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(next)));
                            }
                            
                           }
                           vec=QuickSort.quickSort(0, vec.size()-1, vec);
                             Vector listDataVec=new Vector();
                            for(int j=0;j<vec.size();j++){
                                listDataVec.addElement(new ListData(vec.elementAt(j).toString()));
                            }
                            DDialog dDialog = new DDialog(LangResources.getString(Mpiro.selectedLocale, "selectMultipleEntities_text"),
                                    null,
                                    LangResources.getString(Mpiro.selectedLocale, "multipleEntities_text"),
                                    listDataVec,
                                    cellContent,
                                    "SET");
                         /*   Hashtable childrenEntitiesHashtable = (Hashtable)Mpiro.win.ontoPipe.getExtension().getChildrenEntities(targetNode);
                            Vector vec = new Vector();
                            Enumeration ena = childrenEntitiesHashtable.keys();
                            while (ena.hasMoreElements()) {
                                String next=ena.nextElement().toString();
                                boolean contains=false;
                                for(int i=0;i<vec.size();i++){
                                    if(((ListData)vec.elementAt(i)).m_name.equalsIgnoreCase(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(next)))
                                    {contains=true;
                                    break;
                                }}
                                if(!contains)
                                vec.addElement(new ListData(Mpiro.win.ontoPipe.getExtension().nameWithoutOccur(next)));
                            }
                            
                            DDialog dDialog = new DDialog(LangResources.getString(Mpiro.selectedLocale, "selectMultipleEntities_text"),
                                    null,
                                    LangResources.getString(Mpiro.selectedLocale, "multipleEntities_text"),
                                    vec,
                                    cellContent,
                                    "SET");  */
                            DataBaseEntityTable.dbeTable.revalidate();
                            DataBaseEntityTable.dbeTable.repaint();
                        
                        }
                        
                        else if (name.equalsIgnoreCase("Date")) {
                            DDialog dDialog = new DDialog(LangResources.getString(Mpiro.selectedLocale, "selectNewDate_text"),
                                    null,
                                    LangResources.getString(Mpiro.selectedLocale, "newDate_text"),
                                    null,
                                    cellContent,
                                    "DATE");
                        }
                        
                        else if (name.equalsIgnoreCase("Dimension")) {
                           // DatatypeValue dtv=new DatatypeValue(Mpiro.getFrames()[0], true, "number", parentFieldName, cellContent);
                            //dtv.setVisible(true);
                            DDialog dDialog = new DDialog(LangResources.getString(Mpiro.selectedLocale, "selectNewDimension_text"),
                                    null,
                                    LangResources.getString(Mpiro.selectedLocale, "newDimension_text"),
                                    null,
                                    cellContent,
                                    "DIMENSION");
                        } else {
                            //System.out.println("(DataBaseEntityTableListener)--- not a Date, Dimension, or Set");
                            if(!(name.equalsIgnoreCase("Number")||name.equalsIgnoreCase("String")))	//maria
                            {	Mpiro.needExportToExprimo=true;		}	//maria
                        }
                //    } else if (!(entityFieldName.equalsIgnoreCase(parentFieldName))) {
                  //      System.out.println("(DataBaseEntityTableListener)--- field not found");
                  //  }
                    
              //  }//for
                    }
        }}// left has been clicked
        
        else
            if (SwingUtilities.isRightMouseButton(re)) {
            if(re.isPopupTrigger()) {
                
                // Get the exact point of the click
                int x = re.getX();
                int y = re.getY();
                Point p = new Point(x,y);
                rowNo = DataBaseEntityTable.dbeTable.rowAtPoint(p);
                colNo = DataBaseEntityTable.dbeTable.columnAtPoint(p);
                DataBaseEntityTable.dbeTable.getSelectionModel().setLeadSelectionIndex(rowNo);
                DataBaseEntityTable.dbeTable.clearSelection();
                
                // Put the general preview at the preview-area
                //TreePreviews.generalDataBasePreview();
                
                // Determine which field and language are selected
                selectedField = DataBaseEntityTable.dbeTable.getValueAt(rowNo, 0).toString();
                
                // only for right-click
                setPopups();
                
                // determine the row for the popupUser
                if ( (FlagPanel.langID == 0) && ((rowNo >= 2) || (rowNo == 0)) )//3) || (rowNo == 1)) )
                {
                    popup.show(DataBaseEntityTable.dbeTable, re.getX(), re.getY());
                }
                if (FlagPanel.langID == 1 && ((rowNo >= 6) || (rowNo == 1)) ) {
                    popup.show(DataBaseEntityTable.dbeTable, re.getX(), re.getY());
                }
                if (FlagPanel.langID == 2 && ((rowNo >= 7) || (rowNo == 1)) ) {
                    popup.show(DataBaseEntityTable.dbeTable, re.getX(), re.getY());
                }
                if (FlagPanel.langID == 3 && ((rowNo >= 11) || (rowNo == 1)) ) {
                    popup.show(DataBaseEntityTable.dbeTable, re.getX(), re.getY());
                }
            }
            }
        //System.out.println("aaaaa");
        if(Mpiro.win.ontoPipe.isRealTime())
            Mpiro.win.ontoPipe.rebind();

    } //mousePressed
    
    
    public void setPopups() {
        
        Action a3 = new AbstractAction(editInterestImportanceRepetitions_action) {
            public void actionPerformed(ActionEvent e) {
                UserModelDialog umDialog = new UserModelDialog(selectedField,
                        DataBasePanel.last.toString(),
                        "ENTITY");
            }
        };
        
        Action robots = new AbstractAction("Edit profiles preference...") {
            public void actionPerformed(ActionEvent e) {
                RobotsModelDialog umDialog = new RobotsModelDialog(selectedField,
                        DataBasePanel.last.toString(),
                        "ENTITY");
            }
        };
        
        Action robotsChar = new AbstractAction("Profile Attributes...") {
            public void actionPerformed(ActionEvent e) {
                 RobotsCharDialog umDialog = new RobotsCharDialog("Char",
                        DataBasePanel.last.toString()+":"+selectedField,
                        "CHAR");
            }
        };
        
        popup = new JPopupMenu();
        popup.add(a3);
        popup.add(robots);
        popup.add(robotsChar);
        DataBaseEntityTable.dbeTable.add(popup);
        
    } // setPopups()
    
} // DataBaseTableEntityListener