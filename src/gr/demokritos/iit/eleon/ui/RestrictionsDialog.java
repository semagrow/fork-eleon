/*
 * restrictionsDialog.java
 *
 * Created on 12 ����������� 2006, 12:02 ��
 */

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author  dbilid
 */
public class RestrictionsDialog extends java.awt.Dialog {
    
    /** Creates new form restrictionsDialog */
    public RestrictionsDialog(java.awt.Frame parent, boolean modal, String property) {
        
        super(parent, modal);
       
        propName=property;
        initComponents();
        tf=new JTextField("");
        tf.setBounds(160,240,117,22);
        tf.setVisible(false);
        add(tf);
        jTable1= new JTable();
       // jTable1.setBackground(new Color(250,250,250));
       // jTable1.setForeground(new Color(5,5,5));
        jTable1.getTableHeader().setBackground(new Color(200,200,200));
        jTable1.getTableHeader().setFont(new Font(Mpiro.selectedFont, Font.BOLD, 12));
        JScrollPane jScrollPane1=new javax.swing.JScrollPane();
        
        String last= DataBasePanel.last.toString();
        if (last.substring(0,last.length()-1).endsWith("_occur")) last= last.substring(0,last.length()-7);
        allRestrictions=(Vector) QueryHashtable.valueRestrictionsHashtable.get(last+":"+property);
        if (allRestrictions==null){
            QueryHashtable.valueRestrictionsHashtable.put(last+":"+property,new ValueRestriction());
            allRestrictions=(Vector) QueryHashtable.valueRestrictionsHashtable.get(last+":"+property);
        }
        allValuesRestriction=(Vector) allRestrictions.elementAt(0);
        someValuesRestriction=(Vector) allRestrictions.elementAt(1);
        hasValue=(Vector) allRestrictions.elementAt(2);
        maxCard= (Vector) allRestrictions.elementAt(3);
        minCard=(Vector) allRestrictions.elementAt(4);
        Card=(Vector) allRestrictions.elementAt(5);
        //    this.jTextField1.setText(maxCard);
        //  this.jTextField2.setText(minCard);
        //this.jTextField3.setText(Card);
        //testing...
        //    allValuesRestriction.setSize(2);
        //  someValuesRestriction.setSize(1);
        //allValuesRestriction.setElementAt("11111",0);
        //  allValuesRestriction.setElementAt("2222",1);
        //someValuesRestriction.setElementAt("eeeeee",0);
        
        jTableCard= new JTable();
        jTableCard.getTableHeader().setBackground(new Color(200,200,200));
        jTableCard.getTableHeader().setFont(new Font(Mpiro.selectedFont, Font.BOLD, 12));
        
      //  jTableCard.getCellEditor().cancelCellEditing();
        JScrollPane jScrollPane1Card=new javax.swing.JScrollPane();
        
        int numberOfRestrictions=allValuesRestriction.size()+someValuesRestriction.size()+hasValue.size();
        int noOfCardRes=minCard.size()+maxCard.size()+Card.size();
        //  if (!allRestrictions.elementAt(2).toString().equalsIgnoreCase("")) numberOfRestrictions++;
        
        ob =new Object [numberOfRestrictions][2];
        obCard=new Object [noOfCardRes][2];
        
        for(int i=0;i<allValuesRestriction.size();i++){
            ob[i][0]="All Values From";
            ob[i][1]=allValuesRestriction.elementAt(i);
        }
        for (int i=0;i<someValuesRestriction.size();i++){
            ob[allValuesRestriction.size()+i][0]="Some Values From";
            ob[allValuesRestriction.size()+i][1]=someValuesRestriction.elementAt(i);
        }
        
        for (int i=0;i<hasValue.size();i++){
            ob[allValuesRestriction.size()+someValuesRestriction.size()+i][0]="Has Value";
            ob[allValuesRestriction.size()+someValuesRestriction.size()+i][1]=hasValue.elementAt(i);
        }
        
        for(int i=0;i<maxCard.size();i++){
            obCard[i][0]="Max Cardinality";
            obCard[i][1]=maxCard.elementAt(i);
        }
        for (int i=0;i<minCard.size();i++){
            obCard[maxCard.size()+i][0]="Min Cardinality";
            obCard[maxCard.size()+i][1]=minCard.elementAt(i);
        }
        
        for (int i=0;i<Card.size();i++){
            obCard[maxCard.size()+minCard.size()+i][0]="Cardinality";
            obCard[maxCard.size()+minCard.size()+i][1]=Card.elementAt(i);
        }
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                ob,
                new String [] {
            "restriction type", "entity type"
        }
        )
        {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }
        );
        
        jTableCard.setModel(new javax.swing.table.DefaultTableModel(
                obCard,
                new String [] {
            "cardinality type", "value"
        }
        )
        {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }
        );
        //jScrollPane1.setBackground(new Color(254,254,254));
        //jScrollPane1.setForeground(new Color(9,9,9));
      
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1Card.setViewportView(jTableCard);
        
        add(jScrollPane1);
        jScrollPane1.setBounds(30, 80, 230, 130);
        
        add(jScrollPane1Card);
        jScrollPane1Card.setBounds(30, 305, 230, 130);
        
        
        
        //     JTable table=new JTable(3,4);
///TableColumn restrictionColumn = jTable1.getColumnModel().getColumn(0);
//TableColumn typeColumn = jTable1.getColumnModel().getColumn(1);
        comboBox = new JComboBox();
        comboBox.addItem("All values from");
        comboBox.addItem("Some values from");
        comboBox.addItem("has value");
        
        comboBoxCard = new JComboBox();
        comboBoxCard.addItem("Max Cardinality");
        comboBoxCard.addItem("Min Cardinality");
        comboBoxCard.addItem("Cardinality");
        value = new javax.swing.JTextField();
        Vector allTypes=new Vector();
    
        String filler=DataBaseTable.dbTable.getValueAt(DataBaseTable.dbtl.rowNo,1).toString();
        combo2=new JComboBox();
        if(filler.equalsIgnoreCase("number")||filler.equalsIgnoreCase("date")||filler.equalsIgnoreCase("string")||filler.equalsIgnoreCase("dimension")){
            allTypes.add("Number");
            allTypes.add("Date");
            allTypes.add("String");
            allTypes.add("Dimension");
            combo2=new JComboBox(allTypes);
        }else{
         Vector r=QuickSort.quickSort(0,QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").size()-1,new Vector(QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").keySet()));
//Vector allTypes=new Vector();
                  for(int h=0;h<r.size();h++){
                    //while(allEntities1.hasMoreElements()){
                        String next=r.elementAt(h).toString();
                        if (!next.equalsIgnoreCase("Data Base")&&!next.equalsIgnoreCase("Basic-Entity-Types")&&!next.contains("_occur"))
                            combo2.addItem(next);
                    }}
        
        add(combo2);
        add(comboBox);
        add(comboBoxCard);
        add(value);
        value.setBounds(160, 460, 70, 25);
        comboBoxCard.setBounds(30, 460, 120, 25);
        jButton1.setEnabled(false);
        jButton4.setEnabled(false);
//combo2.setLocation(400,300);
//combo2.setVisible(true);
        comboBox.setBounds(30,240,117,22);
        combo2.setBounds(160, 240, 117, 22);
        
//restrictionColumn.setCellEditor(new DefaultCellEditor(comboBox));
//typeColumn.setCellEditor(new DefaultCellEditor(combo2));
//add(table);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boolean hasRowSelected=false;
                for(int i=0;i<jTable1.getRowHeight();i++){
                    if (jTable1.isRowSelected(i)){
                        hasRowSelected=true;
                        break;
                    }
                }
                if (hasRowSelected)
                    jButton1.setEnabled(true);
                else
                    jButton1.setEnabled(false);
                
                
                hasRowSelected=false;
                for(int i=0;i<jTableCard.getRowHeight();i++){
                    if (jTableCard.isRowSelected(i)){
                        hasRowSelected=true;
                        break;
                    }
                }
                if (hasRowSelected)
                    jButton4.setEnabled(true);
                else
                    jButton4.setEnabled(false);
                
            }
        });
       jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boolean hasRowSelected=false;
                for(int i=0;i<jTable1.getRowHeight();i++){
                    if (jTable1.isRowSelected(i)){
                        hasRowSelected=true;
                        break;
                    }
                }
                if (hasRowSelected)
                    jButton1.setEnabled(true);
                else
                    jButton1.setEnabled(false);
            }
        });
        
        jTableCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boolean hasRowSelected=false;
                for(int i=0;i<jTableCard.getRowHeight();i++){
                    if (jTableCard.isRowSelected(i)){
                        hasRowSelected=true;
                        break;
                    }
                }
                if (hasRowSelected)
                    jButton4.setEnabled(true);
                else
                    jButton4.setEnabled(false);
            }
        });
        
     
        
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                combo2.removeAllItems();
                 String filler=DataBaseTable.dbTable.getValueAt(DataBaseTable.dbtl.rowNo,1).toString();
        if(filler.equalsIgnoreCase("number")||filler.equalsIgnoreCase("date")||filler.equalsIgnoreCase("string")||filler.equalsIgnoreCase("dimension")){
              if (comboBox.getSelectedItem().toString().equalsIgnoreCase("has value")){
                  tf.setVisible(true);
                  combo2.setVisible(false);
              }   else{    
                     tf.setVisible(false);
                  combo2.setVisible(true);
                  repaint();
            combo2.addItem("Number");
            combo2.addItem("Date");
            combo2.addItem("String");
            combo2.addItem("Dimension");
        }}else{
                if (comboBox.getSelectedItem().toString().equalsIgnoreCase("has value")){
                   // Enumeration allEntities1= QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").keys();
                    Vector r=QuickSort.quickSort(0,QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").size()-1,new Vector(QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").keySet()));
//Vector allEntities=new Vector();
                    for(int h=0;h<r.size();h++){
                    //while(allEntities1.hasMoreElements()){
                        String next=r.elementAt(h).toString();
                        if (!next.contains("_occur"))
                            combo2.addItem(next);
                    }
                }
                
                else{
                   // Enumeration allTypes1= QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").keys();
                                        Vector r=QuickSort.quickSort(0,QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").size()-1,new Vector(QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").keySet()));
//Vector allTypes=new Vector();
                  for(int h=0;h<r.size();h++){
                    //while(allEntities1.hasMoreElements()){
                        String next=r.elementAt(h).toString();
                        if (!next.equalsIgnoreCase("Data Base")&&!next.equalsIgnoreCase("Basic-Entity-Types")&&!next.contains("_occur"))
                            combo2.addItem(next);
                    }
                }}
                
                
            }
        });
        
        
    }
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setLayout(null);

        setBackground(new java.awt.Color(228, 228, 228));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jButton1.setText("Remove");
        jButton1.setPreferredSize(new java.awt.Dimension(73, 22));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteRestriction(evt);
            }
        });

        add(jButton1);
        jButton1.setBounds(280, 170, 73, 22);

        jButton2.setText("Add");
        jButton2.setMaximumSize(new java.awt.Dimension(73, 22));
        jButton2.setMinimumSize(new java.awt.Dimension(73, 22));
        jButton2.setPreferredSize(new java.awt.Dimension(73, 22));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newRestriction(evt);
            }
        });

        add(jButton2);
        jButton2.setBounds(280, 240, 70, 22);

        jButton3.setText("Add");
        jButton3.setPreferredSize(new java.awt.Dimension(73, 22));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        add(jButton3);
        jButton3.setBounds(280, 460, 75, 22);

        jButton4.setText("Remove");
        jButton4.setPreferredSize(new java.awt.Dimension(73, 22));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        add(jButton4);
        jButton4.setBounds(280, 390, 73, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
     //testing racer   
       
        String restrictionToDelete=obCard[jTableCard.getSelectedRow()][0].toString();
        //   String entityType=ob[jTable1.getSelectedRow()][1].toString();
        if(restrictionToDelete.equalsIgnoreCase("Max Cardinality"))
            deleteCardRestriction(DataBasePanel.last.toString(),"3");
        if(restrictionToDelete.equalsIgnoreCase("Min Cardinality"))
            deleteCardRestriction(DataBasePanel.last.toString(),"4");
        
        if(restrictionToDelete.equalsIgnoreCase("Cardinality"))
            deleteCardRestriction(DataBasePanel.last.toString(),"5");
        
        
        obCard =new Object [maxCard.size()+minCard.size()+Card.size()][2];
        for(int i=0;i<maxCard.size();i++){
            obCard[i][0]="Max Cardinality";
            obCard[i][1]=maxCard.elementAt(i);
        }
        for (int i=0;i<minCard.size();i++){
            obCard[maxCard.size()+i][0]="Min Cardinality";
            obCard[maxCard.size()+i][1]=minCard.elementAt(i);
        }
        
        for (int i=0;i<Card.size();i++){
            obCard[maxCard.size()+minCard.size()+i][0]="Cardinality";
            obCard[maxCard.size()+minCard.size()+i][1]=Card.elementAt(i);
        }
        jTableCard.setModel(new javax.swing.table.DefaultTableModel(
                obCard,
                new String [] {
            "cardinality type", "value"
        }
        )
        {
            boolean[] canEdit = new boolean [] {
                 false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }
        );
        jTableCard.repaint();
         jButton4.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed
    
    private boolean checkExistingEntities(String node){
        Hashtable childrenEntities= QueryHashtable.getChildrenEntities(DataBasePanel.getNode(node));
        Enumeration keys=childrenEntities.keys();
        Enumeration values=childrenEntities.elements();
        while(keys.hasMoreElements()){
            String nextKey=keys.nextElement().toString();
            Vector nextEntity=(Vector) values.nextElement();
            nextEntity=(Vector) nextEntity.elementAt(0);
            nextEntity=(Vector) nextEntity.elementAt(0);
            for(int y=3;y<nextEntity.size();y++){
                Vector nextField=(Vector) nextEntity.elementAt(y);
                if (nextField.elementAt(0).toString().equals(propName)){
                    int j=0;
                    try{
                        j=Integer.parseInt(value.getText());
        } catch(NumberFormatException n){
            MessageDialog error=new MessageDialog(this, "INVALLID NUMBER");
            System.out.println("INVALLID NUMBER");
            return false;
        }
                    if(!nextField.elementAt(1).toString().startsWith("Select ")&&nextField.elementAt(1).toString().split(" ").length>j){
                        MessageDialog error=new MessageDialog(this, "Error. "+nextKey+" has more than "+value.getText()+" values for "+propName);
                        System.out.println("ERROR: "+nextKey);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
        Vector property=(Vector) QueryHashtable.propertiesHashtable.get(propName);
        if(property.elementAt(8).toString().equalsIgnoreCase("true"))
        {
             Object[] optionButtons = {
                "ok",
            };
            
            JOptionPane.showOptionDialog(RestrictionsDialog.this, //theofilos
                    "H �������� "+propName+" ����� ����������. ��� ����������� ���������� �� ��������� ��� �� ���������� ����������� (�������� OWL FULL)",
                    LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    optionButtons,
                    optionButtons[0]);
            
        }else{
        if (comboBoxCard.getSelectedItem().toString().equalsIgnoreCase("Max Cardinality")){
            if(maxCard.contains(value.getText())||!checkExistingEntities(DataBasePanel.last.toString()))
                return;
            addCardRestriction(DataBasePanel.last.toString(),"3");
        
        }
        //allValuesRestriction.add(combo2.getSelectedItem().toString());}
        if (comboBoxCard.getSelectedItem().toString().equalsIgnoreCase("Min Cardinality")){
            if(minCard.contains(value.getText()))
                return;
            addCardRestriction(DataBasePanel.last.toString(),"4");}
        //            someValuesRestriction.add(combo2.getSelectedItem().toString());}
        
        if (comboBoxCard.getSelectedItem().toString().equalsIgnoreCase("Cardinality")){
            if(Card.contains(value.getText())||!checkExistingEntities(DataBasePanel.last.toString()))
                return;
            if(Card.size()>0){
                MessageDialog error=new MessageDialog(this, "there is already a cardinality restriction");
                return;
            }
            addCardRestriction(DataBasePanel.last.toString(),"5");
            //         hasValue.add(combo2.getSelectedItem().toString());
          // checkExistingEntities(DataBasePanel.last.toString());
            
        }
        }
        obCard =new Object [maxCard.size()+minCard.size()+Card.size()][2];
        for(int i=0;i<maxCard.size();i++){
            obCard[i][0]="Max Cardinality";
            obCard[i][1]=maxCard.elementAt(i);
        }
        for (int i=0;i<minCard.size();i++){
            obCard[maxCard.size()+i][0]="Min Cardinality";
            obCard[maxCard.size()+i][1]=minCard.elementAt(i);
        }
        
        for (int i=0;i<Card.size();i++){
            obCard[maxCard.size()+minCard.size()+i][0]="Cardinality";
            obCard[maxCard.size()+minCard.size()+i][1]=Card.elementAt(i);
        }
        jTableCard.setModel(new javax.swing.table.DefaultTableModel(
                obCard,
                new String [] {
            "restriction type", "value"
        }
        )
        {
            boolean[] canEdit = new boolean [] {
                 false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCard.repaint();
jButton4.setEnabled(false);
        
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void addCardRestriction(String name, String type){
        try{
            int j=Integer.parseInt(value.getText());
        } catch(NumberFormatException n){
            System.out.println("INVALLID NUMBER");
            MessageDialog error=new MessageDialog(this, "INVALLID NUMBER");
            return;
        }
        name=QueryHashtable.nameWithoutOccur(name);
        Vector temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(name+":"+propName);
        if(temp==null){
            QueryHashtable.valueRestrictionsHashtable.put(name+":"+propName, new ValueRestriction());
            temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(name+":"+propName);
        }
        if(type.equalsIgnoreCase("3"))
            temp=(Vector) temp.elementAt(3);
        if(type.equalsIgnoreCase("4"))
            temp=(Vector) temp.elementAt(4);
        if(type.equalsIgnoreCase("5"))
            temp=(Vector) temp.elementAt(5);
        if(temp.contains(value.getText()))
            return;
        else{
            temp.add((value.getText()));
            
            //   if(!types.elementAt(i).toString().contains("_occur")){
            Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(name,"entity type");
            for(int y=0;y<children.size();y++){
                addCardRestriction(children.elementAt(y).toString(),type);
            }
        }
        
    }
    
 /*   private void setCardinalities(String name, String cardValue, String cardType){
         Vector types=QueryHashtable.getAllOccurrences(name);
        for(int i=0;i<types.size();i++){
            Vector parents =QueryHashtable.getParents(types.elementAt(i).toString());
            Vector temp=new ValueRestriction();
             for(int j=0;j<parents.size();j++){
                if(QueryHashtable.valueRestrictionsHashtable.get(parents.elementAt(i).toString()+":"+propName)!=null)
                {
                    temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(parents.elementAt(i).toString()+":"+propName);
                    break;
                }
             }
            Vector cur=(Vector) QueryHashtable.valueRestrictionsHashtable.get(types.elementAt(i).toString()+":"+propName);
            if(cur==null){
                QueryHashtable.valueRestrictionsHashtable.put(types.elementAt(i).toString()+":"+propName,new ValueRestriction());
                cur=(Vector) QueryHashtable.valueRestrictionsHashtable.get(types.elementAt(i).toString()+":"+propName);
            }
            if(cardType.equalsIgnoreCase("maxCard")){
                if(!temp.elementAt(3).toString().equalsIgnoreCase("")){
                     if(Integer.parseInt(temp.elementAt(4).toString())<Integer.parseInt(cardValue))
                    return;}
  
                cur.setElementAt(cardValue,3);}
            if(cardType.equalsIgnoreCase("minCard")){
                 if(!temp.elementAt(4).toString().equalsIgnoreCase("")){
                     if(Integer.parseInt(temp.elementAt(4).toString())>Integer.parseInt(cardValue))
                             return;
                 }
  
                cur.setElementAt(cardValue,4);}
            if(cardType.equalsIgnoreCase("Card")){
                if(!temp.elementAt(5).toString().equalsIgnoreCase("")&Integer.parseInt(temp.elementAt(5).toString())==Integer.parseInt(cardValue))
                    return;
                cur.setElementAt(cardValue,5);}
            if(!types.elementAt(i).toString().contains("_occur")){
                Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(types.elementAt(i).toString(),"entity type");
                for(int y=0;y<children.size();y++){
                    setCardinalities(children.elementAt(y).toString(), cardValue, cardType);
                    }
                }
        }
    }
  */
    private void deleteCardRestriction(String name, String type){
        name=QueryHashtable.nameWithoutOccur(name);
        
        Vector parents=QueryHashtable.getParents(name);
        for(int m=0;m<parents.size();m++){
            String parent=parents.elementAt(m).toString();
            if(parent.contains("_occur")) continue;
            Vector parentVector=(Vector) QueryHashtable.valueRestrictionsHashtable.get(parent+":"+propName);
            if(parentVector!=null){
                if(type.equalsIgnoreCase("3")){
                    parentVector=(Vector)parentVector.elementAt(3);
                }
                if(type.equalsIgnoreCase("4")){
                    parentVector=(Vector)parentVector.elementAt(4);
                }
                if(type.equalsIgnoreCase("5")){
                    parentVector=(Vector)parentVector.elementAt(5);
                }
                if(parentVector.contains(obCard[jTableCard.getSelectedRow()][1].toString())){
                    System.out.println("ERROR: INHERITED RESTRICTION");
                    MessageDialog error=new MessageDialog(this, "This restriction is inherited. You must delete it from the parent node");
                    return;
                }}}
        
        Vector temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(name+":"+propName);
        //  Vector parentVector=(Vector) QueryHashtable.valueRestrictionsHashtable.get(parent+":"+propName);
        if(type.equalsIgnoreCase("5")){
            //  parentVector=(Vector)parentVector.elementAt(2);
            temp=(Vector) temp.elementAt(5);}
        if(type.equalsIgnoreCase("3")){
            //  parentVector=(Vector)parentVector.elementAt(3);
            temp=(Vector) temp.elementAt(3);}
        if(type.equalsIgnoreCase("4")){
            // parentVector=(Vector)parentVector.elementAt(4);
            temp=(Vector) temp.elementAt(4);}
        //       if(parentVector.contains(obCard[jTableCard.getSelectedRow()][1].toString())){
        //           System.out.println("ERROR: INHERITED RESTRICTION");
        //           return;
        //      }
        temp.remove(obCard[jTableCard.getSelectedRow()][1].toString());
        //temp.add((combo2.getSelectedItem().toString()));
        //    if(!types.elementAt(i).toString().contains("_occur")){
        Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(name,"entity type");
        for(int y=0;y<children.size();y++){
            deleteCardRestriction(children.elementAt(y).toString(),type);
        }
        //   }
        // }
    }
    
    private void deleteValuesRestriction(String name, String type){
        //   Vector types=QueryHashtable.getAllOccurrences(name);
        // for(int i=0;i<types.size();i++){
        name=QueryHashtable.nameWithoutOccur(name);
        //   String parent=DataBasePanel.getNode(name).getParent().toString();
        Vector parents=QueryHashtable.getParents(name);
        for(int m=0;m<parents.size();m++){
            String parent=parents.elementAt(m).toString();
            if(parent.contains("_occur")) continue;
            Vector parentVector=(Vector) QueryHashtable.valueRestrictionsHashtable.get(parent+":"+propName);
            if(parentVector!=null){
                if(type.equalsIgnoreCase("0")){
                    parentVector=(Vector)parentVector.elementAt(0);
                }
                if(type.equalsIgnoreCase("1")){
                    parentVector=(Vector)parentVector.elementAt(1);
                }
                if(type.equalsIgnoreCase("2")){
                    parentVector=(Vector)parentVector.elementAt(2);
                }
                if(parentVector.contains(ob[jTable1.getSelectedRow()][1].toString())){
                    System.out.println("ERROR: INHERITED RESTRICTION");
                    MessageDialog error=new MessageDialog(this, "This restriction is inherited. You must delete it from the parent node");
                    return;
                }}}
        
        
        
        Vector temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(name+":"+propName);
        if(type.equalsIgnoreCase("0")){
            //parentVector=(Vector)parentVector.elementAt(0);
            temp=(Vector) temp.elementAt(0);}
        if(type.equalsIgnoreCase("1")){
            // parentVector=(Vector)parentVector.elementAt(1);
            temp=(Vector) temp.elementAt(1);}
        if(type.equalsIgnoreCase("2")){
            //  parentVector=(Vector)parentVector.elementAt(2);
            temp=(Vector) temp.elementAt(2);}
        //       if(parentVector.contains(ob[jTable1.getSelectedRow()][1].toString())){
        //         System.out.println("ERROR: INHERITED RESTRICTION");
        //         return;
        //      }
        temp.remove(ob[jTable1.getSelectedRow()][1].toString());
        //temp.add((combo2.getSelectedItem().toString()));
        //    if(!types.elementAt(i).toString().contains("_occur")){
        Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(name,"entity type");
        for(int y=0;y<children.size();y++){
            deleteValuesRestriction(children.elementAt(y).toString(),type);
        }
        //   }
        // }
    }
    private void deleteRestriction(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteRestriction
// TODO add your handling code here:
        // int row= jTable1.getSelectedRow();
        //  int numberOfRestrictions=allValuesRestriction.size()+someValuesRestriction.size();
//      if (!allRestrictions.elementAt(2).toString().equalsIgnoreCase("")) numberOfRestrictions++;
        
        String restrictionTypeToDelete=ob[jTable1.getSelectedRow()][0].toString();
        // String entityType=ob[jTable1.getSelectedRow()][1].toString();
        if(restrictionTypeToDelete.equalsIgnoreCase("All values from"))
            deleteValuesRestriction(DataBasePanel.last.toString(),"0");
        if(restrictionTypeToDelete.equalsIgnoreCase("Some values from"))
            deleteValuesRestriction(DataBasePanel.last.toString(),"1");
        
        if(restrictionTypeToDelete.equalsIgnoreCase("has value"))
            deleteValuesRestriction(DataBasePanel.last.toString(),"2");
        
        
        ob =new Object [allValuesRestriction.size()+someValuesRestriction.size()+hasValue.size()][2];
        for(int i=0;i<allValuesRestriction.size();i++){
            ob[i][0]="All Values From";
            ob[i][1]=allValuesRestriction.elementAt(i);
        }
        for (int i=0;i<someValuesRestriction.size();i++){
            ob[allValuesRestriction.size()+i][0]="Some Values From";
            ob[allValuesRestriction.size()+i][1]=someValuesRestriction.elementAt(i);
        }
        
        for (int i=0;i<hasValue.size();i++){
            ob[allValuesRestriction.size()+someValuesRestriction.size()+i][0]="Has Value";
            ob[allValuesRestriction.size()+someValuesRestriction.size()+i][1]=hasValue.elementAt(i);
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                ob,
                new String [] {
            "restriction type", "entity type"
        }
        )
        {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }
        );
        jTable1.repaint();
        jButton1.setEnabled(false);
    }//GEN-LAST:event_deleteRestriction
    
    private void addValuesRestriction(String name, String type){
        String value="";
        if(tf.isVisible())
            value=tf.getText();
        else
            value=combo2.getSelectedItem().toString();
        //    Vector types=QueryHashtable.getAllOccurrences(name);
        //  for(int i=0;i<types.size();i++){
        name=QueryHashtable.nameWithoutOccur(name);
        Vector temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(name+":"+propName);
        if(temp==null){
            QueryHashtable.valueRestrictionsHashtable.put(name+":"+propName, new ValueRestriction());
            temp=(Vector) QueryHashtable.valueRestrictionsHashtable.get(name+":"+propName);
        }
        if(type.equalsIgnoreCase("0"))
            temp=(Vector) temp.elementAt(0);
        if(type.equalsIgnoreCase("1"))
            temp=(Vector) temp.elementAt(1);
        if(type.equalsIgnoreCase("2"))
            temp=(Vector) temp.elementAt(2);
        if(temp.contains(value))
            return;
        else{
            temp.add(value);
            if(type.equalsIgnoreCase("2")){
                Enumeration en=DataBasePanel.top.preorderEnumeration();
                DefaultMutableTreeNode dmtn=new DefaultMutableTreeNode();
                while(en.hasMoreElements()){
                    dmtn=(DefaultMutableTreeNode) en.nextElement();
                    if(dmtn.toString().equalsIgnoreCase(name)) break;
                }
                Hashtable entities= QueryHashtable.getChildrenEntities(dmtn);
                Enumeration en1=entities.keys();
                while(en1.hasMoreElements()){
                    Vector v=(Vector) QueryHashtable.mainDBHashtable.get(en1.nextElement().toString());
                    v=(Vector) v.elementAt(0);
                    Vector vec=new Vector();
                    if(!DataBaseTable.dbTable.getValueAt(DataBaseTable.dbtl.rowNo,1).toString().equalsIgnoreCase("String")){
                    vec=(Vector) v.elementAt(0);
                    for(int y =3;y<vec.size();y++){
                        Vector next=(Vector) vec.elementAt(y);
                        if (next.elementAt(0).toString().equalsIgnoreCase(propName)){
                            if(next.elementAt(1).toString().startsWith("Select ")||next.elementAt(1).toString().equalsIgnoreCase(""))
                                next.setElementAt(value,1);
                            else{
                                boolean add=true;
                                String[] values=next.elementAt(1).toString().split(" ");
                                for (int h=0;h<values.length;h++){
                                    if (values[h].equalsIgnoreCase(value)) {
                                        add=false;
                                        break;
                                    }}
                                if(add)
                                    next.setElementAt(next.elementAt(1)+" "+value,1);
                            }
                            break;
                        }
                    }
                    }
                    else{
                        for(int g=1;g<4;g++){
                         vec=(Vector) v.elementAt(g);
                    for(int y =6;y<vec.size();y++){
                        Vector next=(Vector) vec.elementAt(y);
                        if (next.elementAt(0).toString().equalsIgnoreCase(propName)){
                            if(next.elementAt(1).toString().startsWith("Select ")||next.elementAt(1).toString().equalsIgnoreCase(""))
                                next.setElementAt(value,1);
                            else{
                                boolean add=true;
                                String[] values=next.elementAt(1).toString().split(" ");
                                for (int h=0;h<values.length;h++){
                                    if (values[h].equalsIgnoreCase(value)) {
                                        add=false;
                                        break;
                                    }}
                                if(add)
                                    next.setElementAt(next.elementAt(1)+" "+value,1);
                            }
                            break;
                        }
                    }
                    }
                    }
                }
            }
            //   if(!types.elementAt(i).toString().contains("_occur")){
            Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(name,"entity type");
            for(int y=0;y<children.size();y++){
                addValuesRestriction(children.elementAt(y).toString(),type);
            }
        }
    }
    //  }
    // }
    
    
    private void newRestriction(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newRestriction
// TODO add your handling code here:
        String value="";
        if(tf.isVisible())
            value=tf.getText();
        else
            value=combo2.getSelectedItem().toString();
        if (comboBox.getSelectedItem().toString().equalsIgnoreCase("All values from")){
            if(allValuesRestriction.contains(value))
                return;
            addValuesRestriction(DataBasePanel.last.toString(),"0");}
        //allValuesRestriction.add(combo2.getSelectedItem().toString());}
        if (comboBox.getSelectedItem().toString().equalsIgnoreCase("Some values from")){
            if(someValuesRestriction.contains(value))
                return;
            addValuesRestriction(DataBasePanel.last.toString(),"1");}
        //            someValuesRestriction.add(combo2.getSelectedItem().toString());}
        
        if (comboBox.getSelectedItem().toString().equalsIgnoreCase("has value")){
            if(hasValue.contains(value))
                return;
            addValuesRestriction(DataBasePanel.last.toString(),"2");
            //         hasValue.add(combo2.getSelectedItem().toString());
            
            
        }
        
        ob =new Object [allValuesRestriction.size()+someValuesRestriction.size()+hasValue.size()][2];
        for(int i=0;i<allValuesRestriction.size();i++){
            ob[i][0]="All Values From";
            ob[i][1]=allValuesRestriction.elementAt(i);
        }
        for (int i=0;i<someValuesRestriction.size();i++){
            ob[allValuesRestriction.size()+i][0]="Some Values From";
            ob[allValuesRestriction.size()+i][1]=someValuesRestriction.elementAt(i);
        }
        
        for (int i=0;i<hasValue.size();i++){
            ob[allValuesRestriction.size()+someValuesRestriction.size()+i][0]="has Value";
            ob[allValuesRestriction.size()+someValuesRestriction.size()+i][1]=hasValue.elementAt(i);
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                ob,
                new String [] {
            "restriction type", "entity type"
        }
        )
        {
            boolean[] canEdit = new boolean [] {
                 false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }
        );
        jTable1.repaint();
        jButton1.setEnabled(false);
    }//GEN-LAST:event_newRestriction
    
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
 /*   public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new restrictionsDialog(new java.awt.Frame(), true).setVisible(true);
            }
        });
    }*/
    //  Object [][] ob;
    private Vector allValuesRestriction=new Vector();
    private Vector someValuesRestriction=new Vector();
    private   Vector allRestrictions=new Vector();
    public Vector maxCard,minCard,Card;
    private JComboBox comboBox,comboBoxCard;
    private JComboBox combo2;
    private JTextField tf;
    private Object[][] ob,obCard;
    private javax.swing.JTable jTable1,jTableCard;
    private Vector hasValue;
    private javax.swing.JTextField value;
    String propName;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    // End of variables declaration//GEN-END:variables
    
}
