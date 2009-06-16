/*
 * editFieldProperties.java
 *
 * Created on 14 ������� 2006, 5:14 ��
 */

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.ListData;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.QuickSort;
import gr.demokritos.iit.eleon.authoring.RobotsModelDialog;
import gr.demokritos.iit.eleon.authoring.UserModelDialog;

import java.util.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;


/**
 *
 * @author  dbilid
 */
public class EditFieldProperties extends java.awt.Dialog {
    Vector propVector=new Vector();
    String propName;
    public JList subProp;
    public JList superProp;
    public JScrollPane jScrollPane1sub,jScrollPane1super;
    /** Creates new form editFieldProperties */
    public EditFieldProperties(java.awt.Frame parent, boolean modal, Vector propVector1, String propName1) {
        super(parent, modal);
        propVector=propVector1;
        propName=propName1;
        initComponents();
        
        //Component comp=new Component();
        
        Enumeration propKeys=QueryHashtable.propertiesHashtable.keys();
        //System.out.println("llll"+propKeys.toString());
        Vector orderedTypes=QuickSort.quickSort(0,QueryHashtable.propertiesHashtable.size()-1,new Vector(QueryHashtable.propertiesHashtable.keySet()));
        orderedTypes.remove(propName);
 /*       String[] data = new String[QueryHashtable.propertiesHashtable.size()];
        // int remove=-1;
        for(int u=0;propKeys.hasMoreElements();u++){
            data[u]=propKeys.nextElement().toString();
            if(data[u].equalsIgnoreCase(propName))
                data[u]=null;
        }*/
        jLabel4.setText("Subproperties: "+propVector.elementAt(2).toString().replace("]","").replace("[",""));
        jLabel5.setText("Superproperties: "+propVector.elementAt(3).toString().replace("]","").replace("[",""));
        jLabel6.setText("Domain: "+propVector.elementAt(0).toString().replace("]","").replace("[",""));
//  subProp = new JList(orderedTypes);
        //subProp.remove(propName);
        //subProp.remove(remove);
      //  superProp = new JList(orderedTypes);
        //superProp.remove(propName);
        //superProp.remove(remove);
    //    jScrollPane1sub = new JScrollPane();
     //   jScrollPane1super = new JScrollPane();
     //   jScrollPane1sub.setViewportView(subProp);
      //  jScrollPane1super.setViewportView(superProp);
      //  jTabbedPane1.addTab("Subproperties", jScrollPane1sub);
      //  jTabbedPane1.addTab("Superproperties", jScrollPane1super);
   //     Vector sub=(Vector) propVector.elementAt(2);
    //    System.out.println("fdddddddddd"+sub.toString());
    //    int[] values=new int[sub.size()];
     //   for(int g=0;g<sub.size();g++){
            
     //       subProp.setSelectedValue(sub.elementAt(g),true);
      //      values[g]=subProp.getSelectedIndex();
       // }
        //subProp.setSelectedIndices(values);
        
      //  Vector superp=(Vector) propVector.elementAt(3);
       // System.out.println("fdddddddddd"+sub.toString());
    //    values=new int[superp.size()];
      //  for(int g=0;g<superp.size();g++){
            
      //      superProp.setSelectedValue(superp.elementAt(g),true);
       //     values[g]=superProp.getSelectedIndex();
       // }
        ///superProp.setSelectedIndices(values);
        
        
        
        
        
        jComboBox1.addItem("");
        
        Enumeration allPropKeys=QueryHashtable.propertiesHashtable.keys();
        Enumeration allPropElem=QueryHashtable.propertiesHashtable.elements();
        while(allPropKeys.hasMoreElements()){
            Vector tv=(Vector) allPropElem.nextElement();
            tv=(Vector) tv.elementAt(1);
            Object ob1= allPropKeys.nextElement();
            try{
            if (tv.elementAt(0).toString().equalsIgnoreCase("number")||
                    tv.elementAt(0).toString().equalsIgnoreCase("string")||
                    tv.elementAt(0).toString().equalsIgnoreCase("date")||
                    tv.elementAt(0).toString().equalsIgnoreCase("dimension")) orderedTypes.remove(ob1.toString());
           // jComboBox1.addItem(ob1);
        }catch(Exception e){};}
        for(int h=0;h<orderedTypes.size();h++){
            jComboBox1.addItem(orderedTypes.elementAt(h).toString());
        }
        if (propVector.elementAt(5)==null) propVector.setElementAt("", 5);
        if (propVector.elementAt(5).toString().equalsIgnoreCase(""))
            jComboBox1.setSelectedIndex(0);
        else
            jComboBox1.setSelectedItem(propVector.elementAt(5));
        Vector range=(Vector) propVector.elementAt(1);
        if (range.elementAt(0).toString().equalsIgnoreCase("number")||
                range.elementAt(0).toString().equalsIgnoreCase("string")||
                range.elementAt(0).toString().equalsIgnoreCase("date")||
                range.elementAt(0).toString().equalsIgnoreCase("dimension")){
            jCheckBox1.setEnabled(false);
            jCheckBox4.setEnabled(false);
            //jCheckBox3.setEnabled(false);
            jCheckBox2.setEnabled(false);
            jComboBox1.setEnabled(false);
        }
        if (propVector.elementAt(8).toString().equalsIgnoreCase("true"))
            jCheckBox1.setSelected(true);
        if (propVector.elementAt(9).toString().equalsIgnoreCase("true"))
            jCheckBox2.setSelected(true);
        if (propVector.elementAt(6).toString().equalsIgnoreCase("true"))
            jCheckBox3.setSelected(true);
        if (propVector.elementAt(7).toString().equalsIgnoreCase("true"))
            jCheckBox4.setSelected(true);
        if (propVector.elementAt(14).toString().equalsIgnoreCase("true"))
            jCheckBox5.setSelected(true);
        
        
        
        
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jCheckBox5 = new javax.swing.JCheckBox();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");
        jLabel2.setText("Domain: "+propVector.elementAt(0).toString());
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 3, 12));
        jTextArea1.setRows(5);
        jTextArea1.setText("sddsdasd" + "/n" + "sdfasfasfas");
        jScrollPane1.setViewportView(jTextArea1);

        setLayout(null);

        setBackground(new java.awt.Color(227, 227, 227));
        setForeground(java.awt.Color.lightGray);
        setLocationRelativeTo(this);
        setName("dialog1");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        getAccessibleContext().setAccessibleParent(this);
        jCheckBox1.setText("Transitive");
        jCheckBox1.setOpaque(false);
        add(jCheckBox1);
        jCheckBox1.setBounds(40, 120, 140, 23);

        jButton1.setText("OK");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonPressed(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        add(jButton1);
        jButton1.setBounds(210, 360, 90, 30);

        jCheckBox2.setText("Symmetric");
        jCheckBox2.setOpaque(false);
        add(jCheckBox2);
        jCheckBox2.setBounds(40, 30, 130, 23);

        jCheckBox3.setLabel("Functional");
        jCheckBox3.setOpaque(false);
        add(jCheckBox3);
        jCheckBox3.setBounds(40, 60, 120, 23);

        jCheckBox4.setLabel("Inverse Functional");
        jCheckBox4.setOpaque(false);
        add(jCheckBox4);
        jCheckBox4.setBounds(40, 90, 150, 23);

        add(jComboBox1);
        jComboBox1.setBounds(40, 180, 163, 20);

        jLabel1.setLabelFor(jComboBox1);
        jLabel1.setText("Inverse");
        add(jLabel1);
        jLabel1.setBounds(40, 160, 70, 14);

        jButton2.setLabel("edit user modelling");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        add(jButton2);
        jButton2.setBounds(320, 190, 150, 23);

        jLabel3.setText("order:");
        add(jLabel3);
        jLabel3.setBounds(120, 320, 34, 14);

        jTextField1.setText(propVector.elementAt(13).toString());
        add(jTextField1);
        jTextField1.setBounds(160, 320, 40, 20);

        jLabel4.setText("jLabel4");
        add(jLabel4);
        jLabel4.setBounds(290, 30, 210, 14);

        jLabel5.setText("jLabel5");
        add(jLabel5);
        jLabel5.setBounds(290, 100, 210, 14);

        jButton3.setText("edit subproperties");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        add(jButton3);
        jButton3.setBounds(310, 50, 150, 23);

        jButton4.setText("edit superproperties");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        add(jButton4);
        jButton4.setBounds(310, 120, 150, 23);

        jCheckBox5.setText("Used for comparisons");
        jCheckBox5.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jCheckBox5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox5.setOpaque(false);
        add(jCheckBox5);
        jCheckBox5.setBounds(250, 320, 140, 15);

        jButton5.setText("edit robot modelling");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        add(jButton5);
        jButton5.setBounds(320, 230, 150, 23);

        jLabel6.setText("jLabel6");
        add(jLabel6);
        jLabel6.setBounds(10, 240, 220, 14);

        jButton6.setText("edit domain");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        add(jButton6);
        jButton6.setBounds(30, 260, 120, 23);

    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        Set types=QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").keySet();
        types.remove("Data Base");
        Vector orderedTypes=QuickSort.quickSort(0,types.size()-1,new Vector(types));
        Vector listProperties=new Vector();
        for(int i=0;i<orderedTypes.size();i++){
            ListData ls=new ListData(orderedTypes.elementAt(i).toString());
            listProperties.add(ls);
        }
        DDialog subprop=new DDialog("Domain", propName, "", listProperties, jLabel6.getText().replace("Domain: ","").replace(",",""),"DOMAIN");        // TODO add your handling code here:
        jLabel6.setText("Domain: "+propVector.elementAt(0).toString().replace("]","").replace("[",""));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
// TODO add your handling code here:
        RobotsModelDialog rmDialog = new RobotsModelDialog(propName,
		                                                    DataBasePanel.last.toString(),
		                                                    "Property");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //    try {
// TODO add your handling code here:
     //       OwlImport.readCanned();
    //    } catch (Exception ex) {
     //       ex.printStackTrace();
     //   }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
 Enumeration propKeys=QueryHashtable.propertiesHashtable.keys();
        
        Vector orderedTypes=QuickSort.quickSort(0,QueryHashtable.propertiesHashtable.size()-1,new Vector(QueryHashtable.propertiesHashtable.keySet()));
        orderedTypes.remove(propName);
        Vector listProperties=new Vector();
        for(int i=0;i<orderedTypes.size();i++){
            listProperties.add(new ListData(orderedTypes.elementAt(i).toString()));
        }
        DDialog subprop=new DDialog("Superproperties", propName, "", listProperties, jLabel5.getText().replace("Superproperties: ","").replace(",",""),"SUPERPROPERTIES");        // TODO add your handling code here:
        jLabel5.setText("Superproperties: "+propVector.elementAt(3).toString().replace("]","").replace("[",""));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        Enumeration propKeys=QueryHashtable.propertiesHashtable.keys();
       
        Vector orderedTypes=QuickSort.quickSort(0,QueryHashtable.propertiesHashtable.size()-1,new Vector(QueryHashtable.propertiesHashtable.keySet()));
        orderedTypes.remove(propName);
        Vector listProperties=new Vector();
        for(int i=0;i<orderedTypes.size();i++){
            listProperties.add(new ListData(orderedTypes.elementAt(i).toString()));
        }
        DDialog subprop=new DDialog("Subproperties", propName, "", listProperties, jLabel4.getText().replace("Subproperties: ","").replace(",",""),"SUBPROPERTIES");        
        jLabel4.setText("Subproperties: "+propVector.elementAt(2).toString().replace("]","").replace("[",""));
   //     String frameTitle, String textFieldTitle, String listTitle,
     //              Vector listItems, String stringItems, String dialogType
                
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        UserModelDialog umDialog = new UserModelDialog(propName,
		                                                    DataBasePanel.last.toString(),
		                                                    "Property");
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void buttonPressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonPressed
        
        
 /*       Vector sub=(Vector) propVector.elementAt(2);
        sub=(Vector) sub.clone();
//Enumeration en=sub.elements();
        Object[] selectedSub=subProp.getSelectedValues();
        Vector selectedSubProp=new Vector();
        for(int s=0;s<selectedSub.length;s++){
            selectedSubProp.add(selectedSub[s]);
        }
        Vector finalSub=new Vector();
        System.out.println("dddddddddds"+selectedSubProp.toString()+sub.toString());
        if(!selectedSubProp.equals(sub)){
            for(int n=0;n<selectedSubProp.size();n++){
                if (!sub.contains(selectedSubProp.elementAt(n))){
                    boolean domainOK=false;
                    boolean rangeOK=false;
                    Vector subprop= (Vector) QueryHashtable.propertiesHashtable.get(selectedSubProp.elementAt(n).toString());
                    Vector subDomain=(Vector) subprop.elementAt(0);
                    Vector domainsuper=(Vector) propVector.elementAt(0);
                    for(int j=0;j<domainsuper.size();j++){
                        String nextSuperdomain=domainsuper.elementAt(0).toString();
                        for(int m=0;m<subDomain.size();m++){
                            String nextDomain=subDomain.elementAt(m).toString();
                            
                            if(checkDomains(nextSuperdomain,nextDomain)){
                                domainOK=true;
                                //finalSuper.add(selectedSubProp.elementAt(n));
                                break;
                                
                            }
                            if(m==subDomain.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(editFieldProperties.this, //theofilos
                                        "inconsistent domains",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                
                    }
                    
                    
                    
                    //  Vector subprop= (Vector) QueryHashtable.propertiesHashtable.get(selectedSubProp.elementAt(n).toString());
                    Vector subRange=(Vector) subprop.elementAt(1);
                    Vector rangesuper=(Vector) propVector.elementAt(1);
                    for(int j=0;j<rangesuper.size();j++){
                        String nextSuperrange=rangesuper.elementAt(0).toString();
                        for(int m=0;m<subRange.size();m++){
                            String nextRange=subRange.elementAt(m).toString();
                            
                            if(checkDomains(nextSuperrange,nextRange)){
                                rangeOK=true;
                                
                                break;
                                
                            }
                            if(m==subRange.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(editFieldProperties.this, //theofilos
                                        "inconsistent range",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
               
                    }
                    
                    if(domainOK && rangeOK){ finalSub.add(selectedSubProp.elementAt(n));
                    addSubpropertyValueToProperty(propName,selectedSubProp.elementAt(n).toString());
                    Vector subPropVector=(Vector) QueryHashtable.propertiesHashtable.get(selectedSubProp.elementAt(n).toString());
                    subPropVector= (Vector) subPropVector.elementAt(3);
                    subPropVector.add(propName);
                    }
                    
                }
                
            }
            propVector.setElementAt(finalSub,2);
            
            
            
            
            
}
        
        Vector superp=(Vector) propVector.elementAt(3);
        superp=(Vector) superp.clone();
//Enumeration en2=superp.elements();
        Object[] selectedSuper=superProp.getSelectedValues();
        Vector selectedSuperProp=new Vector();
        for(int s=0;s<selectedSuper.length;s++){
            selectedSuperProp.add(selectedSuper[s]);
        }
        Vector finalSuper=new Vector();
        System.out.println("dddddddddds"+selectedSuperProp.toString());
        if(superp.elements()!=selectedSuperProp.elements()){
            
            for(int n=0;n<selectedSuperProp.size();n++){
                if (!superp.contains(selectedSuperProp.elementAt(n))){
                    boolean domainOK=false;
                    boolean rangeOK=false;
                    Vector subprop= (Vector) QueryHashtable.propertiesHashtable.get(selectedSuperProp.elementAt(n).toString());
                    Vector subDomain=(Vector) subprop.elementAt(0);
                    Vector domainsuper=(Vector) propVector.elementAt(0);
                    for(int j=0;j<domainsuper.size();j++){
                        String nextSuperdomain=domainsuper.elementAt(0).toString();
                        for(int m=0;m<subDomain.size();m++){
                            String nextDomain=subDomain.elementAt(m).toString();
                            
                            if(checkDomains(nextDomain,nextSuperdomain)){
                                domainOK=true;
                                //finalSuper.add(selectedSuperProp.elementAt(n));
                                break;
                                
                            }
                            if(m==subDomain.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(editFieldProperties.this, //theofilos
                                        "inconsistent domains",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                        
                    }
                    
                    
                    
                    //  Vector subprop= (Vector) QueryHashtable.propertiesHashtable.get(selectedSuperProp.elementAt(n).toString());
                    Vector subRange=(Vector) subprop.elementAt(1);
                    Vector rangesuper=(Vector) propVector.elementAt(1);
                    for(int j=0;j<rangesuper.size();j++){
                        String nextSuperrange=rangesuper.elementAt(0).toString();
                        for(int m=0;m<subRange.size();m++){
                            String nextRange=subRange.elementAt(m).toString();
                            
                            if(checkDomains(nextRange,nextSuperrange)){
                                rangeOK=true;
                                
                                break;
                                
                            }
                            if(m==subRange.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(editFieldProperties.this, //theofilos
                                        "inconsistent range",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=QueryHashtable.getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                
                    }
                    
                    if(domainOK && rangeOK){ finalSuper.add(selectedSuperProp.elementAt(n));
                    addSubpropertyValueToProperty(selectedSuperProp.elementAt(n).toString(),propName);
                    Vector superPropVector=(Vector) QueryHashtable.propertiesHashtable.get(selectedSuperProp.elementAt(n).toString());
                    superPropVector= (Vector) superPropVector.elementAt(2);
                    superPropVector.add(propName);
                    
                    }
                }
                
            }
            propVector.setElementAt(finalSuper,3);
            
        }
        
        */
        
        
        
        if (!propVector.elementAt(5).toString().equalsIgnoreCase(jComboBox1.getSelectedItem().toString())){
           QueryHashtable.setInverseProperty(propName, jComboBox1.getSelectedItem().toString());
           if(propVector.elementAt(5).toString().equals(""))
               jCheckBox2.setSelected(false);
        }
    
    
    
    
    
    
    //transitive
    if (jCheckBox1.isSelected()&&propVector.elementAt(8).toString().equalsIgnoreCase("false"))
            QueryHashtable.setPropertyTransitive(propName);
    if(!jCheckBox1.isSelected())
        propVector.setElementAt("false",8);
        
    
    if (jCheckBox2.isSelected()&&propVector.elementAt(9).toString().equalsIgnoreCase("false")){
        QueryHashtable.setPropertySymmetric(propName);
        if(propVector.elementAt(9).toString().equalsIgnoreCase("false"))
            jCheckBox2.setSelected(false);
    }
    else{
        if(!jComboBox1.getSelectedItem().toString().equalsIgnoreCase(propName))
            propVector.setElementAt("false",9);}
    
    if (jCheckBox3.isSelected()&&propVector.elementAt(6).toString().equalsIgnoreCase("false")){
            
   /*         boolean canSet=true;
            
            Enumeration keys=QueryHashtable.valueRestrictionsHashtable.keys();
            Enumeration elements=QueryHashtable.valueRestrictionsHashtable.elements();
            while(keys.hasMoreElements()){
                String nextKey=keys.nextElement().toString();
                Vector nextElement=(Vector)elements.nextElement();
                if(nextKey.endsWith(":"+propName)){
                    Vector minCard=(Vector)nextElement.elementAt(4);
                    for (int y=0;y<minCard.size();y++){
                    if (!minCard.elementAt(y).toString().equalsIgnoreCase("0")&&!minCard.elementAt(y).toString().equalsIgnoreCase("1")){
                        MessageDialog error=new MessageDialog(this, "Error. "+propName+" has minCardinality "+minCard.elementAt(y).toString()+" for type " +nextKey.split(":")[0]);
                            canSet=false;
                            break;
                    }
                }
                    
                    Vector card=(Vector)nextElement.elementAt(5);
                    for (int y=0;y<card.size();y++){
                    if (!card.elementAt(y).toString().equalsIgnoreCase("0")&&!card.elementAt(y).toString().equalsIgnoreCase("1")){
                        //ERROR
                        MessageDialog error=new MessageDialog(this, "Error. "+propName+" has Cardinality "+card.elementAt(y).toString()+" for type " +nextKey.split(":")[0]);
                            canSet=false;
                            break;
                    }
                }
            }}
            
            
            
            
            Vector dom=(Vector)propVector.elementAt(0);
            for (int i=0;i<dom.size();i++)
            {
                if(!canSetFunctional(dom.elementAt(i).toString()))
                {
                    canSet=false;
                    break;
                }
            }
            
                        Enumeration restrictionKeys=QueryHashtable.valueRestrictionsHashtable.keys();
            Enumeration restrictions=QueryHashtable.valueRestrictionsHashtable.elements();
            while(restrictionKeys.hasMoreElements()){
                Vector nextRes=(Vector) restrictions.nextElement();
                if(restrictionKeys.nextElement().toString().split(":")[1].equalsIgnoreCase(propName)){
                    if(!((Vector)nextRes.elementAt(3)).isEmpty()||!((Vector)nextRes.elementAt(4)).isEmpty()||!((Vector)nextRes.elementAt(5)).isEmpty()){
                         Object[] optionButtons = {
                "ok",
            };
            
            JOptionPane.showOptionDialog(EditFieldProperties.this, //theofilos
                    "������� ��������� ����������� �����������",
                    LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    optionButtons,
                    optionButtons[0]);
            canSet=false;
            break;
                    }
                }
            }*/
            if(QueryHashtable.setFunctional(propName))
        propVector.setElementAt("true",6);
       /* Vector vect=(Vector) QueryHashtable.mainDBHashtable.get(DataBasePanel.last.toString());
        vect=(Vector) vect.elementAt(0);
        Boolean ssss=  new Boolean(false);
        
        System.out.println("dddsdsdsdsdsd"+vect.toString());
        DataBaseTableModel dbtm = new DataBaseTableModel(vect, DataBaseTable.rowActive);
        dbtm.setValueAt(ssss, DataBaseTable.rowActive, 2);*/
        
    }
    if(!jCheckBox3.isSelected())
        propVector.setElementAt("false",6);
        
        if(!jCheckBox5.isSelected())
        propVector.setElementAt("false",14);
        else
            propVector.setElementAt("true",14);
    
    if (jCheckBox4.isSelected()&&propVector.elementAt(7).toString().equalsIgnoreCase("false")){
            QueryHashtable.setInverseFunctional(propName);
            }
           // if(QueryHashtable.setInverseFunctional(propName)){

   //         propVector.setElementAt("true",7);
     //  if(setFunctional(propVector.elementAt(5).toString())){
       //    propVector.setElementAt("true",7);
     //       if(!propVector.elementAt(5).toString().equals("")){
      // Vector inverse=(Vector) QueryHashtable.propertiesHashtable.get(propVector.elementAt(5).toString());
      // inverse.setElementAt("true",6);}
     //  }
 /*       Vector inverseProp=(Vector) QueryHashtable.propertiesHashtable.get(propName);
        String nextInv=inverseProp.elementAt(5).toString();
        if(!nextInv.equalsIgnoreCase("")){
      //          inverseProp= (Vector) inverseProp.elementAt(1);
          //      for (int h=0;h<inverseProp.size();h++){
            //        String nextInv=inverseProp.elementAt(h).toString();
              //      System.out.println("nextInv"+nextInv);
        Vector domainOfInv=(Vector) QueryHashtable.propertiesHashtable.get(nextInv);
        domainOfInv=(Vector) domainOfInv.elementAt(0);
        for (int t=0;t<domainOfInv.size();t++){
            String nextDomainOfInv=domainOfInv.elementAt(t).toString();
            System.out.println("nextDomainOfInv"+nextDomainOfInv);
            Vector vect=(Vector) QueryHashtable.mainDBHashtable.get(nextDomainOfInv);
            vect=(Vector) vect.elementAt(0);
            Boolean ssss=  new Boolean(false);
            int j=8;
            for(;j<vect.size();j++){
                Vector row=(Vector) vect.elementAt(j);
                if (row.elementAt(0).toString().equalsIgnoreCase(propName))
                    break;
            }
            DataBaseTableModel dbtm = new DataBaseTableModel(vect, j);
            System.out.println("vect"+vect+"j"+String.valueOf(j));
            dbtm.setValueAt(ssss, j-1, 2, nextDomainOfInv);
            
        }//}
    }*/
    
    if(!jCheckBox4.isSelected())
        propVector.setElementAt("false",7);
    
    try{
        if(!jTextField1.getText().equalsIgnoreCase(""))
        Integer.valueOf(jTextField1.getText());
    propVector.setElementAt(jTextField1.getText(),13);
    }
    catch(java.lang.NumberFormatException h){
    MessageDialog error=new MessageDialog(this, "Order is not a valid number");
    }
        Mpiro.needExportToExprimo=true;
    setVisible(false);
    dispose();
    }//GEN-LAST:event_buttonPressed

    


/** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
/*    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editFieldProperties(new java.awt.Frame(), true, new Vector()).setVisible(true);
            }
        });
    }
 */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    public javax.swing.JCheckBox jCheckBox1;
    public javax.swing.JCheckBox jCheckBox2;
    public javax.swing.JCheckBox jCheckBox3;
    public javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}
