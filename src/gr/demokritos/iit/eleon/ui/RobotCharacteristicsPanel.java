/*
 * AnnotationPropertiesPanel.java
 *
 * Created on 3 ����� 2007, 1:19 ��
 */

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.ListData;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.QueryUsersHashtable;
import gr.demokritos.iit.eleon.authoring.RobotsCharDialog;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JPanel;
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
public class RobotCharacteristicsPanel extends JPanel {
    
    /** Creates new form AnnotationPropertiesPanel */
    public RobotCharacteristicsPanel() {
       // super(parent);
        initComponents();
       // this.setBorder(AbstractBorder());
        Mpiro.needExportToExprimo=true;
        this.setBounds(450, 150, 446, 260);
        //try {
          //  OwlImport.readLexiconRDF("G:\\crete\\projects\\authoring\\MPIRO-authoring-v4.4_with_OWL_1");
       //     OwlImport.getCannedTexts("ooo");
        //} catch (Exception ex) {
          //  ex.printStackTrace();
       // }
         jTable1 = new javax.swing.JTable();
         jTable1.setBounds(150,130, 400, 200);
         
       // Vector titles1=new Vector();
      //  titles1.add("Type");
      //  titles1.add("Value");
      //  titles1.add("Language");
      //  titles1.add(new Boolean(true));
      //  titles1.add("User Type");
       // Vector test=new Vector();
       // test.add(titles1);
        //if(!QueryHashtable.annotationPropertiesHashtable.containsKey(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString())))
      //      QueryHashtable.annotationPropertiesHashtable.put(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()), new Vector());
       // anPropVector=(Vector) QueryHashtable.annotationPropertiesHashtable.get(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString()));
        Vector test=new Vector();
        test.add("profile1");
        test.add("");
        test.add(false);
         anPropVector=QueryHashtable.robotCharVector;
         if(anPropVector.size()==0)
        anPropVector.add(test);
         Vector titles=new Vector();
        titles.add("Title");
        
        titles.add("Profiles");

        titles.add("Universal");
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                anPropVector,
                titles
                ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class 
            };
            
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                    return true;
            }
            
        });
        JScrollPane jScrollPane1=new JScrollPane();
        add(jScrollPane1);
        jScrollPane1.setViewportView(jTable1);
        

        
        TableColumn robots = jTable1.getColumnModel().getColumn(1);
        
        //JComboBox comboBox2 = new JComboBox();
        
        //Enumeration usersHashtableEnum = QueryUsersHashtable.mainUsersHashtable.keys();
        //while (usersHashtableEnum.hasMoreElements()) {
        //    comboBox2.addItem(usersHashtableEnum.nextElement());
        //}
        //users.setCellEditor(new DefaultCellEditor(comboBox2));
        jScrollPane1.setBounds(95,30, 448, 200);
        this.add(jScrollPane1);
//jTable1.setBounds(100, 120, 220, 59);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    // Set the first visible column to 100 pixels wide

    TableColumn col = jTable1.getColumnModel().getColumn(0);
    col.setPreferredWidth(200);
    col = jTable1.getColumnModel().getColumn(1);
    col.setPreferredWidth(170);
    
 
        jTable1.addMouseListener(new MouseListener(){
            public void mousePressed(MouseEvent re) {
                int x = re.getX();
                int y = re.getY();
                Point p = new Point(x,y);
                int colNo = jTable1.columnAtPoint(p);
            
            if(colNo==1){
                    int rowNo=jTable1.rowAtPoint(p);
                    
                        TableCellEditor rrrr = jTable1.getCellEditor(rowNo, colNo);
                        
                   
                Vector vec=new Vector();
                
                for(Enumeration robottypes= QueryUsersHashtable.robotsHashtable.keys();robottypes.hasMoreElements();){
                            vec.add(new ListData(robottypes.nextElement().toString()));
                }
                DDialog dDialog = new DDialog("Select Profiles",
                                    null,
                                    "",
                                    vec,
                                    jTable1.getValueAt(rowNo, colNo).toString(),
                                    "ROBOT");
                rrrr.cancelCellEditing();
                
                    }
                if(colNo==0){
                     int rowNo=jTable1.rowAtPoint(p);
                    oldVal=jTable1.getValueAt(rowNo, colNo).toString();
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
                Vector previousData=(Vector)anPropVector.elementAt(row);
                TableModel model = (TableModel)e.getSource();
                String columnName = model.getColumnName(column);
        ///        
            String data = model.getValueAt(row, column).toString();
            QueryHashtable.renameAttributeInRobotCharValuesHashtable(oldVal, data);
            
            
     //           if(column==0){
   //                 jTable1.setValueAt("",row,1);
 //                   jTable1.setValueAt("",row,2);
                ///    jTable1.setValueAt(new Boolean(false),row,3);
                   // jTable1.setValueAt("",row,4);
              //  }
            //    if(column==1){
              //       String oldData=((Vector)previousData.elementAt(column)).elementAt(row).toString();
          //          if(data.equals("rdfs:isDefinedBy")||data.equals("rdfs:seeAlso")){
                        //     if(oldData.equals("owl:versionInfo")||oldData.equals("rdfs:comment")||oldData.equals("rdfs:label")){
                        // previousData.setElementAt("",1);
                        // previousData.setElementAt("",2);
                        // previousData.setElementAt(new Boolean(false),3);
                        // previousData.setElementAt("",4);
                        
                 //       System.out.println("hhhhhhhhd");
                        // jTable1.revalidate();
                        //  jTable1.repaint();
                        //  }
               //     }
             //   }
                //  previousData.setElementAt(data, column);
                
            }
        });
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        defaultButton= new javax.swing.JButton();
        
       setLayout(null);

  //      addWindowListener(new java.awt.event.WindowAdapter() {
    //        public void windowClosing(java.awt.event.WindowEvent evt) {
      //          closeDialog(evt);
        //    }
        //});

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        add(jButton1);
        jButton1.setBounds(175, 260, 90, 23);

        jButton2.setText("Remove");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        add(jButton2);
        jButton2.setBounds(368, 260, 90, 23);
        
         defaultButton.setText("Set Default Values");
        //defaultButton.setEnabled(false);
        defaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultButtonActionPerformed(evt);
            }
        });

        add(defaultButton);
        defaultButton.setBounds(245, 320, 150, 23);

        //pack();
    }// </editor-fold>                        
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
// TODO add your handling code here:
        String name="new_attribute";
        for(int j=1;true;j++){
            boolean exists=false;
        for(int i=0;i<anPropVector.size();i++){
            String existingname=((Vector)anPropVector.elementAt(i)).elementAt(0).toString();
            if(existingname.equalsIgnoreCase(name)){
                exists=true;
            
                break;
            }
        }
            if(!exists) break;
        name="new_attribute"+String.valueOf(j);
        }
        Vector newV=new Vector();
        newV.add(name);
        newV.add("");
        newV.add(false);
        anPropVector.add(newV);
        jTable1.revalidate();
        jTable1.repaint();
    }                                        
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
// TODO add your handling code here:
        QueryHashtable.removeAttributeFromHashtable(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
        anPropVector.removeElementAt(jTable1.getSelectedRow());
        jTable1.revalidate();
        jTable1.repaint();
        
    }     
    private void defaultButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
// TODO add your handling code here:
  /*      String owlPath = "OwlTemp.pprj";
  String roboPath = "robotsChar.txt";

  Domain dom = new OWLDomain( owlPath );
  RoboModel robo = new FlatFileRoboModel( roboPath );
  gr.demokritos.iit.reasoning.Reasoner r = new RandDL();  // will be provided

  Personality p = new RoboPersonality( dom, robo, r );



  String individualName = new String( "temple-of-ares" );
  String conceptName = new String( "Interesting" );

  // get all degrees (explicit and inferred) of all individuals
  HashMap<String,Realization> allr = p.getAllRealizations(); 

  // get all degrees (explicit and inferred) per individual
  Realization indReal = allr.get( individualName );

  // a Realization is Iterable
  Iterator it = indReal.iterator();
 // ...

  // a Realization is also directly accessible
  Double deg = indReal.get( conceptName );

  // you can also get stuff per concept, if you prefer
  HashMap<String,Extension> allExt = p.getAllExtensions();
  Extension concExt = allExt.get( conceptName );
  it = concExt.iterator();
  if( concExt.get( individualName ).equals( deg ) ) {
   // diff path to the same value!
  }


  // you can also request a single Realization or Extension and not
  // the whole HashMap

  indReal = p.getRealization( individualName );
  concExt = p.getExtension( conceptName );*/
        RobotsCharDialog umDialog = new RobotsCharDialog("Char",
                        "Thing",
                        "CHAR");
        
    }    
    
                       
    

    public static JTable jTable1;
    private static String oldVal="";
    private Vector anPropVector;
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton defaultButton;
    // End of variables declaration                   
    
}
