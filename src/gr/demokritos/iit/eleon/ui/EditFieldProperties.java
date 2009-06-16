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
import gr.demokritos.iit.eleon.authoring.QuickSort;
import gr.demokritos.iit.eleon.authoring.RobotsModelDialog;
import gr.demokritos.iit.eleon.authoring.UserModelDialog;
import gr.demokritos.iit.eleon.authoring.PropertiesHashtableRecord;

import java.util.*;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


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
        
       // Enumeration propKeys=Mpiro.win.ontoPipe.getExtension().getPropertyNames();
        //System.out.println("llll"+propKeys.toString());
        Vector orderedTypes=QuickSort.quickSort(0,Mpiro.win.ontoPipe.getExtension().getNoOfProperties()-1,new Vector(Mpiro.win.ontoPipe.getExtension().getPropertiesKeySet()));
        orderedTypes.remove(propName);
 /*       String[] data = new String[Mpiro.win.ontoPipe.getExtension().getNoOfProperties()];
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
        
        Enumeration allPropKeys=Mpiro.win.ontoPipe.getExtension().getPropertyNames();
        Enumeration allPropElem=Mpiro.win.ontoPipe.getExtension().getProperties();
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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

        setBackground(new java.awt.Color(227, 227, 227));
        setForeground(java.awt.Color.lightGray);
        setLocationRelativeTo(this);
        setName("dialog1"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(null);

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
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
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
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
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

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        Set types=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").keySet();
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
 //Enumeration propKeys=Mpiro.win.struc.getPropertyNames();
        
        Vector orderedTypes=QuickSort.quickSort(0,Mpiro.win.struc.getNoOfProperties()-1,new Vector(Mpiro.win.struc.getPropertiesKeySet()));
        orderedTypes.remove(propName);
        Vector listProperties=new Vector();
        for(int i=0;i<orderedTypes.size();i++){
            listProperties.add(new ListData(orderedTypes.elementAt(i).toString()));
        }
        DDialog subprop=new DDialog("Superproperties", propName, "", listProperties, jLabel5.getText().replace("Superproperties: ","").replace(",",""),"SUPERPROPERTIES");        // TODO add your handling code here:
        jLabel5.setText("Superproperties: "+propVector.elementAt(3).toString().replace("]","").replace("[",""));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        //Enumeration propKeys=Mpiro.win.struc.getPropertyNames();
       
        Vector orderedTypes=QuickSort.quickSort(0,Mpiro.win.struc.getNoOfProperties()-1,new Vector(Mpiro.win.struc.getPropertiesKeySet()));
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
        
        PropertiesHashtableRecord propertyInInferredModel=Mpiro.win.ontoPipe.getExtension().getProperty(propName);

        
        
        
        if (!propVector.elementAt(5).toString().equalsIgnoreCase(jComboBox1.getSelectedItem().toString())){
           Mpiro.win.struc.setInverseProperty(propName, jComboBox1.getSelectedItem().toString());
           if(jComboBox1.getSelectedItem().toString().equals("")&& !propertyInInferredModel.elementAt(5).equals("")){
               if(propVector.elementAt(5).toString().equalsIgnoreCase("")){
                   Object[] optionButtons = {
				"OK",
				};

			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
                                                 "This property has inverse only in inferred model. This information cannot be removed.",
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null,
												 optionButtons,
												 optionButtons[0]);
                                                     return;
            }
            else
            propVector.setElementAt("false",8);

           }
               
        }
    
    
    
    
    
    
    //transitive
    if (jCheckBox1.isSelected()&&propVector.elementAt(8).toString().equalsIgnoreCase("false"))
            Mpiro.win.struc.setPropertyTransitive(propName);

         if(!jCheckBox1.isSelected() && propertyInInferredModel.elementAt(8).equals("true")){
            if(propVector.elementAt(8).toString().equalsIgnoreCase("false")){
                //information is inferred
                 Object[] optionButtons = {
				"OK",
				};

			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
                                                 "This property is transitive only in inferred model. This information cannot be removed.",
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null,
												 optionButtons,
												 optionButtons[0]);
                                                     return;
            }
            else
            propVector.setElementAt("false",8);
        }
    if(!jCheckBox1.isSelected())
        propVector.setElementAt("false",8);
        
    
    if (jCheckBox2.isSelected()&&propVector.elementAt(9).toString().equalsIgnoreCase("false")){
        Mpiro.win.struc.setPropertySymmetric(propName);
    }
        if(!jCheckBox2.isSelected() && propertyInInferredModel.elementAt(9).equals("true")){
            if(propVector.elementAt(9).toString().equalsIgnoreCase("false")){
                //information is inferred
                 Object[] optionButtons = {
				"OK",
				};

			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
                                                 "This property is sytmmetric only in inferred model. This information cannot be removed.",
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null,
												 optionButtons,
												 optionButtons[0]);
                                                     return;
            }
            else
            propVector.setElementAt("false",9);
        }
    
    if (jCheckBox3.isSelected()&&propVector.elementAt(6).toString().equalsIgnoreCase("false")){
            
           if(Mpiro.win.struc.setFunctional(propName))
        propVector.setElementAt("true",6);
    }
         if (!jCheckBox3.isSelected()&&propertyInInferredModel.elementAt(6).toString().equalsIgnoreCase("true")){
            
           if(propVector.elementAt(6).toString().equalsIgnoreCase("false")){
                //information is inferred
                 Object[] optionButtons = {
				"OK",
				};

			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
                                                 "This property is functional only in inferred model. This information cannot be removed.",
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null,
												 optionButtons,
												 optionButtons[0]);
                                                     return;
            }
            else
            propVector.setElementAt("false",6);
    }

        
        if(!jCheckBox5.isSelected())
        propVector.setElementAt("false",14);
        else
            propVector.setElementAt("true",14);
    
    if (jCheckBox4.isSelected()&&propVector.elementAt(7).toString().equalsIgnoreCase("false")){
            Mpiro.win.struc.setInverseFunctional(propName);
            }
 
    
    if (!jCheckBox4.isSelected()&&propertyInInferredModel.elementAt(7).toString().equalsIgnoreCase("true")){

           if(propVector.elementAt(7).toString().equalsIgnoreCase("false")){
                //information is inferred
                 Object[] optionButtons = {
				"OK",
				};

			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
                                                 "This property is inverse functional only in inferred model. This information cannot be removed.",
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null,
												 optionButtons,
												 optionButtons[0]);
                                                     return;
            }
            else
            Mpiro.win.struc.setInverseFunctional(propName);
    }


        
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

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    
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
