/*
 * Equivalent.java
 *
 * Created on 7 ����������� 2007, 1:29 ��
 */

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;

import java.util.Vector;

/**
 *
 * @author  dbilid
 */
public class Equivalent extends java.awt.Dialog {
    
    /** Creates new form Equivalent */
    public Equivalent(java.awt.Frame parent, boolean modal, boolean equivalent1) {
        super(parent, modal);
        initComponents();
        this.setSize(398,250);
                       this.setLocation(350,250);
        equivalent=equivalent1;
        if(DataBasePanel.last!=null){
        nameWithoutOccur=QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString());
        if(equivalent){
        if(!QueryHashtable.equivalentClassesHashtable.containsKey(nameWithoutOccur)){
                
        //Vector  eq=new Vector();
       
            QueryHashtable.equivalentClassesHashtable.put(nameWithoutOccur, new Vector());}
        eq=((Vector) QueryHashtable.equivalentClassesHashtable.get(nameWithoutOccur));
         //eq=(Vector)eq1.clone();
            //eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
 /*       Vector parents=QueryHashtable.getParents(nameWithoutOccur);    
        for(int i=0;i<parents.size();i++){
            eq.add("(Class:"+parents.elementAt(i).toString()+")");
            }
        for(Object key: QueryHashtable.valueRestrictionsHashtable.keySet()){
            if(key.toString().split(":")[0].equalsIgnoreCase(nameWithoutOccur)){
                Vector restrictions=(Vector)QueryHashtable.valueRestrictionsHashtable.get(key);
                String property=key.toString().split(":")[1];
                Vector allValuesFrom=(Vector) restrictions.elementAt(0);
                for(int h=0;h<allValuesFrom.size();h++){
                    eq.add("("+'\u2200'+"Property:"+property+" (Class:"+allValuesFrom.elementAt(h).toString()+"))");
                }
                Vector someValuesFrom=(Vector) restrictions.elementAt(1);
                for(int h=0;h<someValuesFrom.size();h++){
                    eq.add("("+'\u2203'+"Property:"+property+" (Class:"+someValuesFrom.elementAt(h).toString()+"))");
                }
                Vector hasValue=(Vector) restrictions.elementAt(2);
                for(int h=0;h<hasValue.size();h++){
                    eq.add("("+'\u220D'+"Property:"+property+" (Individual:"+hasValue.elementAt(h).toString()+"))");
                }
                Vector maxCard=(Vector) restrictions.elementAt(3);
                for(int h=0;h<maxCard.size();h++){
                    eq.add("( "+'\u2264'+maxCard.elementAt(h).toString()+ " Property:"+property+")");
                }
                Vector minCard=(Vector) restrictions.elementAt(4);
                for(int h=0;h<minCard.size();h++){
                    eq.add("( "+'\u2265'+minCard.elementAt(h).toString()+ " Property:"+property+")");
                }
                Vector card=(Vector) restrictions.elementAt(5);
                for(int h=0;h<card.size();h++){
                    eq.add("( ="+card.elementAt(h).toString()+ " Property:"+property+")");
                }
                
                
            }
        }*/
     
                jList1.setListData(eq);
        
    }
        else{
            if(!QueryHashtable.superClassesHashtable.containsKey(nameWithoutOccur)){
                
        //Vector  eq=new Vector();
       
            QueryHashtable.superClassesHashtable.put(nameWithoutOccur, new Vector());}
     eq1   =(Vector) QueryHashtable.superClassesHashtable.get(nameWithoutOccur);
         eq=(Vector)eq1.clone();
            //eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
                    Vector parents=QueryHashtable.getParents(nameWithoutOccur);    
        for(int i=0;i<parents.size();i++){
            eq.add("(Class:"+parents.elementAt(i).toString()+")");
        }
               /*     Vector v=new Vector();
                    for(Object key: QueryHashtable.valueRestrictionsHashtable.keySet()){
            if(key.toString().split(":").length==1){
                v.add(key);
                
                }}
                    for(int j=0;j<v.size();j++){
                        QueryHashtable.valueRestrictionsHashtable.remove(v.elementAt(j).toString());
                    }*/
        for(Object key: QueryHashtable.valueRestrictionsHashtable.keySet()){
            if(key.toString().contains(":")&& key.toString().split(":")[0].equalsIgnoreCase(nameWithoutOccur)){
                Vector restrictions=(Vector)QueryHashtable.valueRestrictionsHashtable.get(key);
                String property=key.toString().split(":")[1];
                Vector allValuesFrom=(Vector) restrictions.elementAt(0);
                for(int h=0;h<allValuesFrom.size();h++){
                    eq.add("("+'\u2200'+" Property:"+property+" (Class:"+allValuesFrom.elementAt(h).toString()+"))");
                }
                Vector someValuesFrom=(Vector) restrictions.elementAt(1);
                for(int h=0;h<someValuesFrom.size();h++){
                    eq.add("("+'\u2203'+" Property:"+property+" (Class:"+someValuesFrom.elementAt(h).toString()+"))");
                }
                Vector hasValue=(Vector) restrictions.elementAt(2);
                for(int h=0;h<hasValue.size();h++){
                    eq.add("("+'\u220D'+" Property:"+property+" (Individual:"+hasValue.elementAt(h).toString()+"))");
                }
                Vector maxCard=(Vector) restrictions.elementAt(3);
                for(int h=0;h<maxCard.size();h++){
                    eq.add("( "+'\u2264'+maxCard.elementAt(h).toString()+ " Property:"+property+")");
                }
                Vector minCard=(Vector) restrictions.elementAt(4);
                for(int h=0;h<minCard.size();h++){
                    eq.add("( "+'\u2265'+minCard.elementAt(h).toString()+ " Property:"+property+")");
                }
                Vector card=(Vector) restrictions.elementAt(5);
                for(int h=0;h<card.size();h++){
                    eq.add("( ="+card.elementAt(h).toString()+ " Property:"+property+")");
                }
                
                
            }
        }
        
                jList1.setListData(eq);
        }
    }}
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 40, 360, 150);

        jButton1.setText("remove");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        add(jButton1);
        jButton1.setBounds(100, 200, 80, 23);

        jButton2.setText("add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        add(jButton2);
        jButton2.setBounds(210, 200, 80, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
// TODO add your handling code here:
        if(jList1.getSelectedValue()!=null){
            jButton1.setEnabled(true);
        }
        else{
            jButton1.setEnabled(false);
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        //Vector eq=new Vector();
        
      //  if(equivalent)
       // eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(nameWithoutOccur);
        //else
         //           eq=(Vector) QueryHashtable.superClassesHashtable.get(nameWithoutOccur);
        if(!equivalent){
        if(isNamedClass(jList1.getSelectedValue().toString())){
            DataBasePanel.deleteOccur(nameWithoutOccur, jList1.getSelectedValue().toString().replace("(Class:","").replace(")",""));
        }
        else if(isSimpleRestriction(jList1.getSelectedValue().toString())){
            Vector restrictions=(Vector)QueryHashtable.valueRestrictionsHashtable.get(nameWithoutOccur+":"+jList1.getSelectedValue().toString().split("Property:")[1].split(" ")[0]);
           // String temp=jList1.getSelectedValue().toString();
                if(jList1.getSelectedValue().toString().contains("\u2200")){
                    String value= jList1.getSelectedValue().toString().split("Class:")[1].replace("))","");
                    ((Vector)restrictions.elementAt(0)).remove(value);
                }
            if(jList1.getSelectedValue().toString().contains("\u2203")){
                    String value= jList1.getSelectedValue().toString().split("Class:")[1].replace("))","");
                    ((Vector)restrictions.elementAt(1)).remove(value);
                }
            if(jList1.getSelectedValue().toString().contains("\u220D")){
                    String value= jList1.getSelectedValue().toString().split("Class:")[1].replace("))","");
                    ((Vector)restrictions.elementAt(2)).remove(value);
                }}
            else if(isCardRestriction(jList1.getSelectedValue().toString())){
            Vector restrictions=(Vector)QueryHashtable.valueRestrictionsHashtable.get(nameWithoutOccur+":"+jList1.getSelectedValue().toString().split("Property:")[1].replace(")",""));
            
                if(jList1.getSelectedValue().toString().contains("\u2264")){
                    String value= jList1.getSelectedValue().toString().split(" ")[1].replace("\u2264","");
                    ((Vector)restrictions.elementAt(3)).remove(value);
                }
            if(jList1.getSelectedValue().toString().contains("\u2265")){
                   String value= jList1.getSelectedValue().toString().split(" ")[1].replace("\u2265","");
                    ((Vector)restrictions.elementAt(1)).remove(value);
                }
            if(jList1.getSelectedValue().toString().contains("=")){
                    String value= jList1.getSelectedValue().toString().split(" ")[1].replace("=","");
                    ((Vector)restrictions.elementAt(2)).remove(value);
                }
        }else
            eq1.remove(jList1.getSelectedValue().toString());}
        eq.remove(jList1.getSelectedValue().toString());
       
      //  jList1.remove(jList1.getSelectedIndex());
        jList1.setListData(eq);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        
        Complex complexDialog=   new Complex(Mpiro.win.getFrames()[0], true, equivalent);
                complexDialog.setSize(630,600);
                complexDialog.setLocation(200,150);
                complexDialog.setTitle("Equivalent Classes");
              //  efp.jCheckBox1.setSelected(true);
                complexDialog.setVisible(true);
   /*             if(equivalent){
       Vector eq1=((Vector) QueryHashtable.equivalentClassesHashtable.get(nameWithoutOccur));
        Vector eq=(Vector)eq1.clone();
            //eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
        Vector parents=QueryHashtable.getParents(nameWithoutOccur);    
        for(int i=0;i<parents.size();i++){
            eq.add("(Class:"+parents.elementAt(i).toString()+")");
            }
        for(Object key: QueryHashtable.valueRestrictionsHashtable.keySet()){
            if(key.toString().split(":")[0].equalsIgnoreCase(nameWithoutOccur)){
                Vector restrictions=(Vector)QueryHashtable.valueRestrictionsHashtable.get(key);
                String property=key.toString().split(":")[1];
                Vector allValuesFrom=(Vector) restrictions.elementAt(0);
                for(int h=0;h<allValuesFrom.size();h++){
                    eq.add("("+'\u2200'+"Property:"+property+" (Class:"+allValuesFrom.elementAt(h).toString()+"))");
                }
                Vector someValuesFrom=(Vector) restrictions.elementAt(1);
                for(int h=0;h<someValuesFrom.size();h++){
                    eq.add("("+'\u2203'+"Property:"+property+" (Class:"+someValuesFrom.elementAt(h).toString()+"))");
                }
                Vector hasValue=(Vector) restrictions.elementAt(2);
                for(int h=0;h<hasValue.size();h++){
                    eq.add("("+'\u220D'+"Property:"+property+" (Class:"+hasValue.elementAt(h).toString()+"))");
                }
                Vector maxCard=(Vector) restrictions.elementAt(3);
                for(int h=0;h<maxCard.size();h++){
                    eq.add("( "+'\u2264'+maxCard.elementAt(h).toString()+ "Property:"+property+"))");
                }
                Vector minCard=(Vector) restrictions.elementAt(4);
                for(int h=0;h<minCard.size();h++){
                    eq.add("( "+'\u2265'+minCard.elementAt(h).toString()+ "Property:"+property+"))");
                }
                Vector card=(Vector) restrictions.elementAt(5);
                for(int h=0;h<card.size();h++){
                    eq.add("( ="+card.elementAt(h).toString()+ "Property:"+property+"))");
                }
            }
                
            }
            //eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
            
        
                jList1.setListData(eq);
                }
                else{
                    Vector eq1=(Vector) QueryHashtable.superClassesHashtable.get(nameWithoutOccur);
        Vector eq=(Vector)eq1.clone();
            //eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
                    Vector parents=QueryHashtable.getParents(nameWithoutOccur);    
        for(int i=0;i<parents.size();i++){
            eq.add("(Class:"+parents.elementAt(i).toString()+")");
            }
        for(Object key: QueryHashtable.valueRestrictionsHashtable.keySet()){
            if(key.toString().split(":")[0].equalsIgnoreCase(nameWithoutOccur)){
                Vector restrictions=(Vector)QueryHashtable.valueRestrictionsHashtable.get(key);
                String property=key.toString().split(":")[1];
                Vector allValuesFrom=(Vector) restrictions.elementAt(0);
                for(int h=0;h<allValuesFrom.size();h++){
                    eq.add("("+'\u2200'+" Property:"+property+" (Class:"+allValuesFrom.elementAt(h).toString()+")");
                }
                Vector someValuesFrom=(Vector) restrictions.elementAt(1);
                for(int h=0;h<someValuesFrom.size();h++){
                    eq.add("("+'\u2203'+" Property:"+property+" (Class:"+someValuesFrom.elementAt(h).toString()+")");
                }
                Vector hasValue=(Vector) restrictions.elementAt(2);
                for(int h=0;h<hasValue.size();h++){
                    eq.add("("+'\u220D'+" Property:"+property+" (Class:"+hasValue.elementAt(h).toString()+")");
                }
                Vector maxCard=(Vector) restrictions.elementAt(3);
                for(int h=0;h<maxCard.size();h++){
                    eq.add("( "+'\u2264'+maxCard.elementAt(h).toString()+ "Property:"+property+"))");
                }
                Vector minCard=(Vector) restrictions.elementAt(4);
                for(int h=0;h<minCard.size();h++){
                    eq.add("( "+'\u2265'+minCard.elementAt(h).toString()+ "Property:"+property+"))");
                }
                Vector card=(Vector) restrictions.elementAt(5);
                for(int h=0;h<card.size();h++){
                    eq.add("( ="+card.elementAt(h).toString()+ "Property:"+property+"))");
                }
                
            }
        }
            //eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
            
        
                jList1.setListData(eq);
                }*/jList1.setListData(eq);
    }//GEN-LAST:event_jButton2ActionPerformed
    
    public void addExpression(String exp){
        
    /*    Vector eq=new Vector();
        
        eq=(Vector) QueryHashtable.equivalentClassesHashtable.get(nameWithoutOccur);
        else
                    eq=(Vector) QueryHashtable.superClassesHashtable.get(nameWithoutOccur);*/
     //   if(!equivalent){
       // String[] s=splitIntersections(exp).split("A-N-D");
        
       // for(int i=0;i<s.length;i++){
        
        eq.add(exp);
        if(!equivalent){
        if(isNamedClass(exp)){
            KDialog temp=new KDialog("", "", "", new Vector(), false, "", false);
           // temp.setVisible(false);
            temp.createSubClass(DataBasePanel.last.toString(), exp.replace("(Class:","").replace(")",""));
            temp.dispose();
  //       new Kdialog....   KDialog.createSubClass(DataBasePanel.last.toString(), exp.replace("(Class:","").replace(")",""));
    ///?   continue;
        }
        else
        if(isSimpleRestriction(exp)){
            if(exp.charAt(1)=='\u2200'){ 
                String property=exp.substring(12).split(" ")[0];
                QueryHashtable.addRestriction("allValuesFrom", property, nameWithoutOccur, exp.split("Class:")[1].replace(")",""));
            }
            if(exp.charAt(1)=='\u2203'){ 
                String property=exp.substring(12).split(" ")[0];
                QueryHashtable.addRestriction("someValuesFrom", property, nameWithoutOccur, exp.split("Class:")[1].replace(")",""));
            }
            if(exp.charAt(1)=='\u220D'){ 
                String property=exp.substring(12).split(" ")[0];
                QueryHashtable.addRestriction("hasValue", property, nameWithoutOccur, exp.split(" ")[1].replace(")",""));
            }
        }
        else
             if(isCardRestriction(exp)){
            if(exp.charAt(2)=='\u2264'){ 
                String property=exp.split("Property:")[1].replace(")","");
                QueryHashtable.addRestriction("maxCardinality", property, nameWithoutOccur, exp.split(" ")[1].replace("\u2264",""));
            }
            if(exp.charAt(2)=='\u2265'){ 
                String property=exp.split("Property:")[1].replace(")","");
                QueryHashtable.addRestriction("minCardinality", property, nameWithoutOccur, exp.split(" ")[1].replace("\u2265",""));
            }
            if(exp.charAt(2)=='='){ 
                String property=exp.split("Property:")[1].replace(")","");
                QueryHashtable.addRestriction("cardinality", property, nameWithoutOccur, exp.split(" ")[1].replace("=",""));
            }
        }
        else
        eq1.add(exp);}
        
    //    }
        //else
            //eq.add(s[i]);
        
         jList1.setListData(eq);
         
        // jList1.repaint();
    }
    //"(Class:usage-of-area)"
    private  boolean isNamedClass(String expression){
        if (expression.contains("(Class:")&&expression.indexOf(")")==expression.length()-1&&!expression.contains("Property:")&&!expression.contains("Individual:"))
            return true;
        else
            return false;
    }
    
    private  boolean isSimpleRestriction(String expression){
        if((expression.charAt(1)=='\u2203'||expression.charAt(1)=='\u220D'||expression.charAt(1)=='\u2200')&&expression.indexOf(")")==expression.length()-1&&!expression.contains("}")&&!expression.contains("\u2265")&&!expression.contains("\u2264")&&!expression.contains("="))
            return true;
        else
            return false;
    }
    
    private  boolean isCardRestriction(String expression){
        if((expression.charAt(2)=='='||expression.charAt(2)=='\u2264'||expression.charAt(2)=='\u2265')&&expression.indexOf(")")==expression.length()-1&&!expression.contains("}"))
            return true;
        else
            return false;
    }
    
    public  String splitIntersections(String expression){
        String exp=new String();
       exp=expression.substring(1,expression.length()-1);
        int leftPar=0, rightPar=0;
        for(int i=0;i<exp.length();i++){
            char c=exp.charAt(i);
            switch(c){
                case'(':leftPar++;
                break;
                case')':rightPar++;
                break;
                case'\u2229':
                    if(leftPar==rightPar)
                        //do
                        return(new String(splitIntersections(exp.substring(0,i))+"A-N-D"+splitIntersections(exp.substring(i+1,exp.length()))));
                        ;
                    break;
                default://nothing
                    ;
              
            }
        }
         return (expression); 
    }
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
    
    private String nameWithoutOccur;
   //
    private boolean equivalent;
    private static Vector eq=null;
    private static Vector eq1=null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
