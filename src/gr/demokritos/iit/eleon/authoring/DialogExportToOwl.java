package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import java.awt.event.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DialogExportToOwl
    extends JDialog {
    final JFileChooser d = new JFileChooser(System.getProperty("user.dir"));
    public boolean modalResult;
    public static File rdfFile;
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel1 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JLabel jLabel2 = new JLabel();
    public static JTextField jTextField1 = new JTextField();
    JLabel jLabel3 = new JLabel();
    public static JTextField jTextField2 = new JTextField();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel2 = new JPanel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    static Vector exportFormatVector = new Vector();
    public static JComboBox jComboBox1 = new JComboBox(exportFormatVector);
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel5 = new JPanel();
    JButton jButton3 = new JButton();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel jLabel5 = new JLabel();
    public JTextField jTextField3 = new JTextField();

    public DialogExportToOwl(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
            //Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = this.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            this.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogExportToOwl() {
        this(null, "", false);
    }

    private void jbInit() throws Exception {
        modalResult = false;
        jPanel3.setLayout(borderLayout2);
        jPanel4.setDebugGraphicsOptions(0);
        jPanel4.setPreferredSize(new Dimension(250, 200));
        jPanel4.setLayout(gridLayout2);
        gridLayout2.setColumns(0);
        gridLayout2.setRows(4);
        jTextField1.setBackground(Color.white);
        jTextField1.setPreferredSize(new Dimension(200, 20));
        jTextField2.setBackground(Color.white);
        jTextField2.setPreferredSize(new Dimension(150, 20));
        jTextField2.setEditable(false);
        jComboBox1.setPreferredSize(new Dimension(200, 20));
        jComboBox1.setEditable(false);
        jPanel1.setPreferredSize(new Dimension(200, 60));
        if (Mpiro.selectedLocale == Mpiro.enLocale) {
            this.setTitle("Save to OWL");
            jLabel1.setText("OWL save options: ");
            jLabel3.setText("Ontology URL: ");
            jLabel2.setText("File path: ");
            jLabel4.setText("File format: ");
            jLabel5.setText("File of linguistic resources and user types: ");
        }
        else if (Mpiro.selectedLocale == Mpiro.grLocale) {
            this.setTitle("���������� �� OWL");
            jLabel1.setText("�������� ��� ��� ���������� �� OWL: ");
            jLabel3.setText("URL ����������: ");
            jLabel2.setText("�������� �������: ");
            jLabel4.setText("���������� �������: ");
            jLabel5.setText("������ ������������� ����� ��� ����� �������: ");
        }
        else if (Mpiro.selectedLocale == Mpiro.itLocale) {
            this.setTitle("Salva in OWL");
            jLabel1.setText("Opzioni di salvataggio in OWL: ");
            jLabel3.setText("URL di ontologia: ");
            jLabel2.setText("Percorso file: ");
            jLabel4.setText("Formato file: ");
            jLabel5.setText("File di risorse linguistiche e tipi di utenti: ");
        }

        jPanel5.setLayout(borderLayout3);
        jButton3.setText("...");
        jButton3.addActionListener(new DialogExportToOwl_jButton3_actionAdapter(this));
        jPanel5.setPreferredSize(new Dimension(200, 20));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jButton2.addActionListener(new DialogExportToOwl_jButton2_actionAdapter(this));
        jButton1.addActionListener(new DialogExportToOwl_jButton1_actionAdapter(this));
        exportFormatVector.clear();
        panel1.setPreferredSize(new Dimension(500, 100));
        jTextField3.setBackground(Color.white);
        jTextField3.setEditable(false);
        exportFormatVector.add("RDF/XML-ABBREV");
        exportFormatVector.add("RDF/XML");
        jComboBox1.setSelectedIndex(0);

        panel1.setLayout(borderLayout1);
        jPanel1.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        gridLayout1.setRows(4);
        jLabel2.setPreferredSize(new Dimension(50, 15));
        jLabel3.setPreferredSize(new Dimension(50, 15));
        jLabel4.setPreferredSize(new Dimension(50, 15));
        jButton1.setText(LangResources.getString(Mpiro.selectedLocale, "save_button"));
        jButton2.setText(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
        getContentPane().add(panel1);
        jPanel1.add(jTextField1, null);
        jPanel1.add(jComboBox1, null);
        jPanel1.add(jPanel5, null);
        jPanel1.add(jTextField3, null);
        jPanel3.add(jPanel4, BorderLayout.WEST);
        jPanel4.add(jLabel3, null);
        jPanel4.add(jLabel4, null);
        jPanel4.add(jLabel2, null);
        jPanel4.add(jLabel5, null);
        panel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jPanel1, BorderLayout.CENTER);
        panel1.add(jLabel1, BorderLayout.NORTH);
        jPanel5.add(jButton3, BorderLayout.EAST);
        jPanel5.add(jTextField2, BorderLayout.CENTER);

        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButton1, null);
        jPanel2.add(jButton2, null);
        d.setSelectedFile(new File(jTextField2.getText()));
        rdfFile = d.getSelectedFile();
    }

    public static void main(String args[]) {
        new DialogExportToOwl().show();
    }

    void jButton3_actionPerformed(ActionEvent e) {
        d.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        OwlFileFilter filter = new OwlFileFilter();
        d.setFileFilter(filter);

        int returnVal = d.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            rdfFile = d.getSelectedFile();
            String fileName = d.getSelectedFile().toString();
            fileName = OwlFileFilter.checkExtension(fileName, ".rdf");
            jTextField2.setText(fileName);
            jTextField3.setText(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf('.')) + "_mpiro.xml");
            String ontName = d.getSelectedFile().getName().substring(0, d.getSelectedFile().getName().lastIndexOf('.'));
            jTextField1.setText(QueryOptionsHashtable.getBaseURI());
        }
        d.removeChoosableFileFilter(filter);
    }

    void jButton2_actionPerformed(ActionEvent e) {
        dispose();
    }

    void jButton1_actionPerformed(ActionEvent e) {
        modalResult = true;
        dispose();
    }
}

class DialogExportToOwl_jButton3_actionAdapter
    implements java.awt.event.ActionListener {
    DialogExportToOwl adaptee;

    DialogExportToOwl_jButton3_actionAdapter(DialogExportToOwl adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}

class DialogExportToOwl_jButton2_actionAdapter
    implements java.awt.event.ActionListener {
    DialogExportToOwl adaptee;

    DialogExportToOwl_jButton2_actionAdapter(DialogExportToOwl adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}

class DialogExportToOwl_jButton1_actionAdapter
    implements java.awt.event.ActionListener {
    DialogExportToOwl adaptee;

    DialogExportToOwl_jButton1_actionAdapter(DialogExportToOwl adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
