/*
 * AnnotationPropertiesPanel.java
 *
 * Created on 3 ����� 2007, 1:19 ��
 */

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.*;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


/**
 *
 * @author  dbilid
 */
public class AnnotationPropertiesPanel extends java.awt.Dialog {
    
    /** Creates new form AnnotationPropertiesPanel */
    public AnnotationPropertiesPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Mpiro.needExportToExprimo=true;
        this.setBounds(450, 150, 446, 260);
        //try {
          //  OwlImport.readLexiconRDF("G:\\crete\\projects\\authoring\\MPIRO-authoring-v4.4_with_OWL_1");
       //     OwlImport.getCannedTexts("ooo");
        //} catch (Exception ex) {
          //  ex.printStackTrace();
       // }
         jTable1 = new javax.swing.JTable();
       // Vector titles1=new Vector();
      //  titles1.add("Type");
      //  titles1.add("Value");
      //  titles1.add("Language");
      //  titles1.add(new Boolean(true));
      //  titles1.add("User Type");
       // Vector test=new Vector();
       // test.add(titles1);
        if(!QueryHashtable.annotationPropertiesHashtable.containsKey(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString())))
            QueryHashtable.annotationPropertiesHashtable.put(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()), new Vector());
        anPropVector=(Vector) QueryHashtable.annotationPropertiesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
        Vector titles=new Vector();
        titles.add("Type");
        titles.add("Value");
        titles.add("Language");
        titles.add("Used");
        titles.add("User Type");
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                anPropVector,
                titles
                ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if(columnIndex>1&&(((Vector)anPropVector.elementAt(rowIndex)).elementAt(0).equals("rdfs:seeAlso")||((Vector)anPropVector.elementAt(rowIndex)).elementAt(0).equals("rdfs:isDefinedBy")))
                    return false;
                else
                    return true;
            }
            
        });
        JScrollPane jScrollPane1=new JScrollPane();
        add(jScrollPane1);
        jScrollPane1.setViewportView(jTable1);
        
        TableColumn type = jTable1.getColumnModel().getColumn(0);
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("owl:versionInfo");
        comboBox.addItem("rdfs:comment");
        comboBox.addItem("rdfs:isDefinedBy");
        comboBox.addItem("rdfs:label");
        comboBox.addItem("rdfs:seeAlso");
        type.setCellEditor(new DefaultCellEditor(comboBox));
        
        TableColumn lang = jTable1.getColumnModel().getColumn(2);
        
        JComboBox comboBox1 = new JComboBox();
        comboBox1.addItem("");
        comboBox1.addItem("english");
        comboBox1.addItem("greek");
        lang.setCellEditor(new DefaultCellEditor(comboBox1));
        
        TableColumn users = jTable1.getColumnModel().getColumn(4);
        
        //JComboBox comboBox2 = new JComboBox();
        
        //Enumeration usersHashtableEnum = QueryUsersHashtable.mainUsersHashtable.keys();
        //while (usersHashtableEnum.hasMoreElements()) {
        //    comboBox2.addItem(usersHashtableEnum.nextElement());
        //}
        //users.setCellEditor(new DefaultCellEditor(comboBox2));
        jScrollPane1.setBounds(6, 30, 548, 160);
jTable1.setBounds(100, 120, 220, 59);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    // Set the first visible column to 100 pixels wide

    TableColumn col = jTable1.getColumnModel().getColumn(0);
    col.setPreferredWidth(97);
    col = jTable1.getColumnModel().getColumn(1);
    col.setPreferredWidth(150);
    col = jTable1.getColumnModel().getColumn(2);
    col.setPreferredWidth(60);
    col = jTable1.getColumnModel().getColumn(3);
    col.setPreferredWidth(40);
    col = jTable1.getColumnModel().getColumn(4);
    col.setPreferredWidth(85);
        jTable1.addMouseListener(new MouseListener(){
            public void mousePressed(MouseEvent re) {
                int x = re.getX();
                int y = re.getY();
                Point p = new Point(x,y);
                int colNo = jTable1.columnAtPoint(p);
                if(colNo==1){
                    int rowNo=jTable1.rowAtPoint(p);
                    if(jTable1.getValueAt(rowNo, 0).equals("rdfs:isDefinedBy")||jTable1.getValueAt(rowNo, 0).equals("rdfs:seeAlso")){
                        TableCellEditor rrrr = jTable1.getCellEditor(rowNo, colNo);
                        rrrr.cancelCellEditing();
                   
               // Enumeration enumEntities= QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").keys();
      //  Vector entities=new Vector();
     //   while(enumEntities.hasMoreElements())
     //   {
     //       entities.add(new ListData(enumEntities.nextElement().toString()));
     //   }
                KDialog addEntityDialog = new KDialog("","", "", new Vector(), false, "ANNOTATION", true);
               // System.out.println("kkk");
            }
                    
                }
            if(colNo==4){
                    int rowNo=jTable1.rowAtPoint(p);
                    if(!(jTable1.getValueAt(rowNo, 0).equals("rdfs:isDefinedBy")||jTable1.getValueAt(rowNo, 0).equals("rdfs:seeAlso"))){
                        TableCellEditor rrrr = jTable1.getCellEditor(rowNo, colNo);
                        
                   
                Vector vec=new Vector();
                for(Enumeration usertypes= QueryUsersHashtable.mainUsersHashtable.keys();usertypes.hasMoreElements();){
                            vec.add(new ListData(usertypes.nextElement().toString()));
                }
                DDialog dDialog = new DDialog("Select User Types",
                                    null,
                                    "",
                                    vec,
                                    jTable1.getValueAt(rowNo, colNo).toString(),
                                    "ANNOTATION");
                rrrr.cancelCellEditing();
                
                    }
            }
                if(jTable1.getSelectedRow()==-1)
                    jButton2.setEnabled(false);
                else
                    jButton2.setEnabled(true);
            }
            
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        });
        jTable1.getModel().addTableModelListener(new TableModelListener() {
            
            
            
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                //Vector previousData=(Vector)anPropVector.elementAt(row);
                TableModel model = (TableModel)e.getSource();
                //String columnName = model.getColumnName(column);
                
                Object data = model.getValueAt(row, column);
                if(column==0){
                    jTable1.setValueAt("",row,1);
                    jTable1.setValueAt("",row,2);
                    jTable1.setValueAt(new Boolean(false),row,3);
                    jTable1.setValueAt("",row,4);
                }
                if(column==1){
                    // Object oldData=previousData.elementAt(column);
                    if(data.equals("rdfs:isDefinedBy")||data.equals("rdfs:seeAlso")){
                        //     if(oldData.equals("owl:versionInfo")||oldData.equals("rdfs:comment")||oldData.equals("rdfs:label")){
                        // previousData.setElementAt("",1);
                        // previousData.setElementAt("",2);
                        // previousData.setElementAt(new Boolean(false),3);
                        // previousData.setElementAt("",4);
                        
                        System.out.println("hhhhhhhhd");
                        // jTable1.revalidate();
                        //  jTable1.repaint();
                        //  }
                    }
                }
                //  previousData.setElementAt(data, column);
                
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

        setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        add(jButton1);
        jButton1.setBounds(110, 210, 90, 23);

        jButton2.setText("Remove");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        add(jButton2);
        jButton2.setBounds(220, 210, 90, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        anPropVector.add(new AnnotationProperty("rdfs:label","","",new Boolean(false),""));
        jTable1.revalidate();
        jTable1.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        anPropVector.removeElementAt(jTable1.getSelectedRow());
        jTable1.revalidate();
        jTable1.repaint();
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnnotationPropertiesPanel(new java.awt.Frame(), true).setVisible(true);
            }
        });
    }
    public static JTable jTable1;
    private Vector anPropVector;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
    
}
