/***************

<p>Title: </p>

<p>Description:
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.border.*;
import javax.swing.tree.*;
import java.util.*;

public class DialogImportFromDatabase
    extends JDialog {
    public static boolean initialized = true;
    Connection con = null;
    Statement stmt = null;
    ResultSet resultSet = null;

    boolean c_open = false;
    JPanel jPanel1 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextArea1 = new JTextArea();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JPanel jPanel3 = new JPanel();
    JButton jButton3 = new JButton();
    BorderLayout borderLayout2 = new BorderLayout();
    JScrollPane jScrollPane2 = new JScrollPane();
    JScrollPane jScrollPane3 = new JScrollPane();
    JScrollPane jScrollPane4 = new JScrollPane();
    JScrollPane jScrollPane5 = new JScrollPane();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    Vector independentComboVector = new Vector();
    Vector englishComboVector = new Vector();
    Vector italianComboVector = new Vector();
    Vector greekComboVector = new Vector();

    Vector independentLabelVector = new Vector();
    Vector englishLabelVector = new Vector();
    Vector italianLabelVector = new Vector();
    Vector greekLabelVector = new Vector();

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel9 = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    JPanel jPanel10 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();
    JPanel jPanel11 = new JPanel();
    BorderLayout borderLayout6 = new BorderLayout();
    Border border1;
    TitledBorder titledBorder1;
    JPanel jPanel12 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JComboBox jComboBox1 = new JComboBox();
    JPanel jPanel13 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    JRadioButton jRadioButton1 = new JRadioButton();
    JRadioButton jRadioButton2 = new JRadioButton();
    GridLayout gridLayout3 = new GridLayout();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    JPanel jPanel16 = new JPanel();
    JTextField jTextField1 = new JTextField();
    JSpinner jSpinner1 = new JSpinner();
    JLabel jLabel1 = new JLabel();
    GridLayout gridLayout4 = new GridLayout();
    JPanel jPanel17 = new JPanel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    GridLayout gridLayout5 = new GridLayout();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel jPanel18 = new JPanel();
    JRadioButton jRadioButton3 = new JRadioButton();
    JRadioButton jRadioButton4 = new JRadioButton();
    BorderLayout borderLayout7 = new BorderLayout();
    BorderLayout borderLayout8 = new BorderLayout();
    JPanel jPanel19 = new JPanel();
    JLabel jLabel4 = new JLabel();
    JComboBox jComboBox2 = new JComboBox();
    ButtonGroup buttonGroup2 = new ButtonGroup();
    JPanel jPanel20 = new JPanel();
    BorderLayout borderLayout9 = new BorderLayout();
    BorderLayout borderLayout10 = new BorderLayout();
    JPanel jPanel21 = new JPanel();
    JPanel jPanel22 = new JPanel();
    JPanel jPanel23 = new JPanel();
    BorderLayout borderLayout12 = new BorderLayout();
    BorderLayout borderLayout13 = new BorderLayout();
    JList jList1 = new JList();
    JList jList2 = new JList();
    Border border2;
    Border border3;
    Vector tablesVector = new Vector();
    DatabaseMetaData dbMetadata;
    Vector tableColumnsVector = new Vector();
    Vector selTableColumnsVector = new Vector();
    JScrollPane jScrollPane6 = new JScrollPane();
    JScrollPane jScrollPane7 = new JScrollPane();
    JButton jButton4 = new JButton();
    JButton jButton5 = new JButton();
    JPanel jPanel24 = new JPanel();
    JPanel jPanel25 = new JPanel();
    BorderLayout borderLayout11 = new BorderLayout();
    JButton jButton6 = new JButton();
    JButton jButton7 = new JButton();
    BorderLayout borderLayout14 = new BorderLayout();
    Border border4;
    TitledBorder titledBorder4;
    Border border5;
    TitledBorder titledBorder5;
    Border border6;
    JPanel jPanel26 = new JPanel();
    GridLayout gridLayout6 = new GridLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel27 = new JPanel();
    BorderLayout borderLayout15 = new BorderLayout();
    JLabel jLabel5 = new JLabel();

    public DialogImportFromDatabase(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        initialized = true;
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

        new DialogDatabaseOptions(Mpiro.win.getFrames()[0], "", true).show();
        try {
            Class.forName(DialogDatabaseOptions.driver);
            con = DriverManager.getConnection(DialogDatabaseOptions.url, DialogDatabaseOptions.user, DialogDatabaseOptions.password);
            c_open = true;

            //get table names
            dbMetadata = con.getMetaData();

            String tableTypes[] = {
                "TABLE"};
            ResultSet tablesResSet = dbMetadata.getTables(null, null, null, tableTypes);
            while (tablesResSet.next()) {
                tablesVector.add(tablesResSet.getString(3));
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            initialized = false;
            c_open = false;
        }
    }

    public DialogImportFromDatabase() {
        this(null, "", false);
    }

    private void jbInit() throws Exception {
        String title1 = null;
        String title2 = null;
        String title3 = null;
        String title4 = null;
        String title5 = null;
        String title6 = null;
        String title7 = null;
        String title8 = null;
        String title9 = null;
        String title10 = null;

        border4 = BorderFactory.createEmptyBorder();
        titledBorder4 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)));
        border5 = BorderFactory.createEmptyBorder();
        titledBorder5 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)));

        if (Mpiro.selectedLocale == Mpiro.enLocale) {
            this.setTitle("Import entities from DB");
            title1 = "Language-independent fields";
            title2 = "English values";
            title3 = "Italian values";
            title4 = "Greek values";
            title5 = "SQL statement";
            title6 = "Obtain entity identifiers from a DB column";
            title7 = "Select entity identifiers manually";
            title8 = "Select column";
            title9 = "Prefix";
            title10 = "Suffix start number";
            jButton1.setText("Create entities");
            jButton3.setText("Get data");
            titledBorder5.setTitle("Selected columns");
            titledBorder4 = new TitledBorder(border2, "Available columns");
            jRadioButton4.setText("Simple");
            jRadioButton3.setText("Advanced");
            jLabel4.setText("  Select table  ");
            jButton4.setToolTipText("Insert selected field");
            jButton5.setToolTipText("Delete selected field");
            jButton6.setToolTipText("Insert all");
            jButton7.setToolTipText("Delete all");
            jLabel5.setText("Link fields to database columns as follows:");
        }
        else if (Mpiro.selectedLocale == Mpiro.grLocale) {
            this.setTitle("�������� ��������� ��� ��");
            title1 = "����� ��� ��� ���������� ��� �� ������";
            title2 = "�������� �����";
            title3 = "�������� �����";
            title4 = "��������� �����";
            title5 = "������ SQL";
            title6 = "����������� ������������� ��������� ��� ��� ����� ��� ��";
            title7 = "������������ ������� �������������� ���������";
            title8 = "������� ������";
            title9 = "�������";
            title10 = "������� ������� ����������";
            jButton1.setText("���������� ���������");
            jButton3.setText("������� ���������");
            titledBorder5.setTitle("����������� ������");
            titledBorder4 = new TitledBorder(border2, "���������� ������");
            jRadioButton4.setText("��� ���������");
            jRadioButton3.setText("��� �������������");
            jLabel4.setText("  ������� ������  ");
            jButton4.setToolTipText("�������� ����������� ������");
            jButton5.setToolTipText("�������� ����������� ������");
            jButton6.setToolTipText("�������� ����");
            jButton7.setToolTipText("�������� ����");
            jLabel5.setText("�� ����� �� ��������� �� ��� ������ ��� ����� ��������� �� ����:");
        }
        else if (Mpiro.selectedLocale == Mpiro.itLocale) {
            this.setTitle("Importa entit\u00E0 da DB");
            title1 = "Campi che non dipendono dalla lingua";
            title2 = "Valori inglesi";
            title3 = "Valori italiani";
            title4 = "Valori greci";
            title5 = "Istruzione SQL";
            title6 = "Acquisisci identificativi di entit\u00E0 da una colonna del database";
            title7 = "Selezione manuale identificativi di entit\u00E0";
            title8 = "Seleziona colonna";
            title9 = "Prefisso";
            title10 = "Numero iniziale del suffisso";
            jButton1.setText("Creazione entit\u00E0");
            jButton3.setText("Estrazione dati");
            titledBorder5.setTitle("Colonne selezionate");
            titledBorder4 = new TitledBorder(border2, "Colonne disponibili");
            jRadioButton4.setText("Generale");
            jRadioButton3.setText("Avanzate");
            jLabel4.setText("  Selezione tabella  ");
            jButton4.setToolTipText("Inserisci campo selezionato");
            jButton5.setToolTipText("Cancella campo selezionato");
            jButton6.setToolTipText("Inserisci tutto");
            jButton7.setToolTipText("Cancella tutto");
            jLabel5.setText("Collega campi alle colonne del database come segue:");
        }

        border2 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(124, 124, 124), new Color(178, 178, 178));
        border3 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(124, 124, 124), new Color(178, 178, 178));
        border6 = BorderFactory.createEmptyBorder();
        jScrollPane1.setVisible(false);
        jTextArea1.setVisible(false);
        border1 = BorderFactory.createLineBorder(new Color(127, 157, 185), 2);
        titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)), title5);
        this.addWindowListener(new DialogImportFromDatabase_this_windowAdapter(this));
        jPanel1.setDebugGraphicsOptions(0);
        jPanel1.setPreferredSize(new Dimension(175, 259));
        jPanel1.setLayout(borderLayout1);
        jButton1.setEnabled(false);
        jButton1.addActionListener(new DialogImportFromDatabase_jButton1_actionAdapter(this));
        jButton2.setText(LangResources.getString(Mpiro.selectedLocale, "exit_button"));
        jButton2.addActionListener(new DialogImportFromDatabase_jButton2_actionAdapter(this));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jButton3.addActionListener(new DialogImportFromDatabase_jButton3_actionAdapter(this));
        jPanel3.setLayout(borderLayout2);
        jTextArea1.setText("");
        jScrollPane1.setEnabled(true);
        jScrollPane1.setBorder(titledBorder1);
        jScrollPane1.setPreferredSize(new Dimension(500, 150));
        jScrollPane2.setBorder(null);
        jScrollPane3.setBorder(null);
        jScrollPane4.setBorder(null);
        jScrollPane5.setBorder(null);
        jPanel3.setPreferredSize(new Dimension(164, 250));
        jPanel5.setDebugGraphicsOptions(0);
        jPanel4.setDebugGraphicsOptions(0);
        jPanel4.setLayout(borderLayout3);
        jPanel9.setLayout(borderLayout4);
        jPanel10.setLayout(borderLayout5);
        jPanel11.setLayout(borderLayout6);
        jPanel12.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        jPanel13.setLayout(gridLayout2);
        gridLayout2.setColumns(0);
        gridLayout2.setRows(3);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(title6);
        jRadioButton2.setText(title7);
        gridLayout3.setRows(3);
        gridLayout3.setColumns(0);
        jPanel14.setLayout(gridLayout3);
        jTextField1.setText("entity");
        jLabel1.setRequestFocusEnabled(true);
        jLabel1.setText(title8);
        jPanel13.setBorder(BorderFactory.createEtchedBorder());
        jPanel13.setPreferredSize(new Dimension(0, 0));
        jPanel16.setLayout(gridLayout4);
        jLabel2.setText(title9);
        jLabel3.setText(title10);
        jPanel17.setLayout(gridLayout5);
        jPanel14.setBorder(BorderFactory.createEtchedBorder());
        jRadioButton3.setHorizontalAlignment(SwingConstants.LEADING);
        jRadioButton3.addActionListener(new DialogImportFromDatabase_jRadioButton3_actionAdapter(this));
        jRadioButton4.setHorizontalAlignment(SwingConstants.LEADING);
        jRadioButton4.setSelected(true);
        jRadioButton4.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton4.addActionListener(new DialogImportFromDatabase_jRadioButton4_actionAdapter(this));
        jPanel18.setLayout(borderLayout8);
        jPanel15.setLayout(borderLayout7);
        jPanel20.setLayout(borderLayout9);
        jPanel19.setLayout(borderLayout10);
        jPanel23.setLayout(borderLayout12);
        jPanel22.setLayout(borderLayout13);
        jPanel19.setPreferredSize(new Dimension(500, 150));
        jComboBox2.addActionListener(new DialogImportFromDatabase_jComboBox2_actionAdapter(this));
        jButton4.setFont(new java.awt.Font("Arial Black", 0, 10));
        jButton4.setPreferredSize(new Dimension(48, 23));
        jButton4.setText(">");
        jButton4.addActionListener(new DialogImportFromDatabase_jButton4_actionAdapter(this));
        jButton5.setFont(new java.awt.Font("Arial Black", 0, 10));
        jButton5.setPreferredSize(new Dimension(48, 23));
        jButton5.setText("<");
        jButton5.addActionListener(new DialogImportFromDatabase_jButton5_actionAdapter(this));
        jPanel23.setBorder(titledBorder4);
        jPanel23.setPreferredSize(new Dimension(220, 145));
        jPanel22.setBorder(titledBorder5);
        jPanel22.setPreferredSize(new Dimension(220, 140));
        jPanel24.setLayout(flowLayout1);
        jPanel21.setPreferredSize(new Dimension(510, 155));
        jPanel21.setInputVerifier(null);
        jPanel21.setLayout(borderLayout14);
        jPanel25.setLayout(borderLayout11);
        jPanel25.setPreferredSize(new Dimension(510, 155));
        jButton6.setText(">>");
        jButton6.addActionListener(new DialogImportFromDatabase_jButton6_actionAdapter(this));
        jButton6.setFont(new java.awt.Font("Arial Black", 0, 10));
        jButton6.setPreferredSize(new Dimension(48, 23));
        jButton7.setText("<<");
        jButton7.addActionListener(new DialogImportFromDatabase_jButton7_actionAdapter(this));
        jButton7.setFont(new java.awt.Font("Arial Black", 0, 10));
        jButton7.setPreferredSize(new Dimension(48, 23));
        jPanel26.setPreferredSize(new Dimension(50, 110));
        jPanel26.setLayout(gridLayout6);
        gridLayout6.setColumns(1);
        gridLayout6.setHgap(2);
        gridLayout6.setRows(4);
        gridLayout6.setVgap(2);
        flowLayout1.setAlignment(FlowLayout.CENTER);
        flowLayout1.setVgap(10);
        jPanel24.setBorder(null);
        jPanel27.setLayout(borderLayout15);
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButton1, null);
        jPanel2.add(jButton2, null);
        this.getContentPane().add(jPanel15, BorderLayout.NORTH);
        jPanel15.add(jPanel18, BorderLayout.NORTH);
        jPanel18.add(jRadioButton4, BorderLayout.WEST);
        jPanel18.add(jRadioButton3, BorderLayout.CENTER);
        jPanel15.add(jScrollPane1, BorderLayout.SOUTH);
        jPanel15.add(jPanel19, BorderLayout.CENTER);
        jPanel19.add(jPanel20, BorderLayout.NORTH);
        jPanel20.add(jLabel4, BorderLayout.WEST);
        jPanel20.add(jComboBox2, BorderLayout.CENTER);
        jPanel19.add(jPanel21, BorderLayout.CENTER);
        jPanel23.add(jScrollPane6, BorderLayout.CENTER);
        jScrollPane6.getViewport().add(jList1, null);
        jPanel22.add(jScrollPane7, BorderLayout.CENTER);
        jScrollPane7.getViewport().add(jList2, null);
        jPanel24.add(jPanel26, null);
        jPanel26.add(jButton6, null);
        jPanel26.add(jButton4, null);
        jPanel26.add(jButton5, null);
        jPanel26.add(jButton7, null);
        jPanel21.add(jPanel25, BorderLayout.CENTER);
        jPanel25.add(jPanel24, BorderLayout.CENTER);
        jPanel25.add(jPanel22, BorderLayout.EAST);
        jPanel25.add(jPanel23, BorderLayout.WEST);
        jScrollPane1.getViewport().add(jTextArea1, null);
        jTabbedPane1.addTab(title1, jScrollPane2);
        jTabbedPane1.addTab(title2, jScrollPane3);
        jTabbedPane1.addTab(title3, jScrollPane4);
        jTabbedPane1.addTab(title4, jScrollPane5);
        jPanel27.add(jLabel5, BorderLayout.NORTH);
        jScrollPane5.getViewport().add(jPanel11, null);
        jPanel11.add(jPanel8, BorderLayout.NORTH);
        jScrollPane4.getViewport().add(jPanel10, null);
        jPanel10.add(jPanel7, BorderLayout.NORTH);
        jScrollPane3.getViewport().add(jPanel9, null);
        jPanel9.add(jPanel6, BorderLayout.NORTH);
        jScrollPane2.getViewport().add(jPanel4, null);
        jPanel4.add(jPanel5, BorderLayout.NORTH);
        jPanel3.add(jPanel12,  BorderLayout.NORTH);
        jPanel12.add(jPanel13, null);
        jPanel13.add(jRadioButton1, null);
        jPanel13.add(jLabel1, null);
        jPanel13.add(jComboBox1, null);
        jPanel12.add(jPanel14, null);
        jPanel14.add(jRadioButton2, null);
        jPanel14.add(jPanel17, null);
        jPanel17.add(jLabel2, null);
        jPanel17.add(jLabel3, null);
        jPanel14.add(jPanel16, null);
        jPanel16.add(jTextField1, null);
        jPanel16.add(jSpinner1, null);
        jPanel3.add(jPanel27, BorderLayout.CENTER);
        jPanel27.add(jTabbedPane1, BorderLayout.CENTER);
        jPanel1.add(jButton3, BorderLayout.NORTH);

        //get selected entity fields
        String entityName = DataBasePanel.last.toString();
        NodeVector nodeVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityName);
        Vector databaseTableVector = nodeVector.getDatabaseTableVector();

        englishLabelVector.add(new JLabel("title"));
        englishComboVector.add(new JComboBox());
        englishLabelVector.add(new JLabel("name"));
        englishComboVector.add(new JComboBox());
        englishLabelVector.add(new JLabel("shortname"));
        englishComboVector.add(new JComboBox());
        englishLabelVector.add(new JLabel("notes"));
        englishComboVector.add(new JComboBox());
        englishLabelVector.add(new JLabel("gender"));
        englishComboVector.add(new JComboBox());
        englishLabelVector.add(new JLabel("number"));
        englishComboVector.add(new JComboBox());

        italianLabelVector.add(new JLabel("title"));
        italianComboVector.add(new JComboBox());
        italianLabelVector.add(new JLabel("name"));
        italianComboVector.add(new JComboBox());
        italianLabelVector.add(new JLabel("grammatical gender of name"));
        italianComboVector.add(new JComboBox());
        italianLabelVector.add(new JLabel("shortname"));
        italianComboVector.add(new JComboBox());
        italianLabelVector.add(new JLabel("grammatical gender of shortname"));
        italianComboVector.add(new JComboBox());
        italianLabelVector.add(new JLabel("notes"));
        italianComboVector.add(new JComboBox());
        italianLabelVector.add(new JLabel("number"));
        italianComboVector.add(new JComboBox());

        greekLabelVector.add(new JLabel("title"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("name (nominative)"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("name (genitive)"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("name (accusative)"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("grammatical gender of name"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("shortname (nominative)"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("shortname (genitive)"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("shortname (accusative)"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("grammatical gender of shortname"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("notes"));
        greekComboVector.add(new JComboBox());
        greekLabelVector.add(new JLabel("number"));
        greekComboVector.add(new JComboBox());

        for (int i = 8; i < databaseTableVector.size(); i++) {
            
            FieldData fieldData = (FieldData) databaseTableVector.get(i);
            String fieldname=fieldData.m_field;
            if(fieldname.equals("Subtype-of") || fieldname.equals("title") || fieldname.equals("name")|| fieldname.equals("shortname")|| fieldname.equals("notes")|| fieldname.equals("images")|| fieldname.equals("gender")|| fieldname.equals("number"))
                            continue;

            if (!fieldData.m_filler.equalsIgnoreCase("String")) {
                independentComboVector.add(new JComboBox());
                independentLabelVector.add(new JLabel(fieldData.m_field));
            }
            else {
                englishComboVector.add(new JComboBox());
                englishLabelVector.add(new JLabel(fieldData.m_field));
                italianComboVector.add(new JComboBox());
                italianLabelVector.add(new JLabel(fieldData.m_field));
                greekComboVector.add(new JComboBox());
                greekLabelVector.add(new JLabel(fieldData.m_field));
            }
        }
        //add fileds combo and labels
        jPanel5.setLayout(new GridLayout(independentComboVector.size(), 2));
        jPanel6.setLayout(new GridLayout(englishComboVector.size(), 2));
        jPanel7.setLayout(new GridLayout(italianComboVector.size(), 2));
        jPanel8.setLayout(new GridLayout(greekComboVector.size(), 2));

        for (int i = 0; i < independentComboVector.size(); i++) {
            jPanel5.add( (JLabel) independentLabelVector.get(i));
            jPanel5.add( (JComboBox) independentComboVector.get(i));
        }
        for (int i = 0; i < englishLabelVector.size(); i++) {
            jPanel6.add( (JLabel) englishLabelVector.get(i));
            jPanel6.add( (JComboBox) englishComboVector.get(i));
        }
        for (int i = 0; i < italianLabelVector.size(); i++) {
            jPanel7.add( (JLabel) italianLabelVector.get(i));
            jPanel7.add( (JComboBox) italianComboVector.get(i));
        }
        for (int i = 0; i < greekLabelVector.size(); i++) {
            jPanel8.add( (JLabel) greekLabelVector.get(i));
            jPanel8.add( (JComboBox) greekComboVector.get(i));
        }
        buttonGroup1.add(jRadioButton2);
        buttonGroup1.add(jRadioButton1);

        setEnabledControls(false);
        buttonGroup2.add(jRadioButton3);
        buttonGroup2.add(jRadioButton4);

        jComboBox2.setModel(new DefaultComboBoxModel(tablesVector));
    }

    void this_windowClosed(WindowEvent e) {
        try {
            if (c_open)
                con.close();
        }
        catch (Exception e3) {
            System.out.println(e3.toString());
        }

    }

    void jButton2_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    String createSQLString() throws Exception {
        String sqlString = null;
        Vector tablesVector = new Vector();
        String columnName = null;
        String tableName = null;

        //get all tables for the join
        for (int i = 0; i < selTableColumnsVector.size(); i++) {
            columnName = (String) selTableColumnsVector.get(i);
            tableName = columnName.substring(0, columnName.indexOf('.'));
            if (!tablesVector.contains(tableName))
                tablesVector.add(tableName);
        }

        /*        String foreignTable = null;
                String primaryTable = null;
                String whereString = "";
                String foreignColumn = null;
                String primaryColumn = null;
                for (int i = 0; i < tablesVector.size(); i++) {
                    primaryTable = (String) tablesVector.get(i);
                    //for(int j =)
                    ResultSet importedKeysRS = dbMetadata.getExportedKeys(null, null, primaryTable);
                    while (importedKeysRS.next()) {
                        primaryColumn = importedKeysRS.getString(4);
                        foreignTable = importedKeysRS.getString(7);
                        foreignColumn = importedKeysRS.getString(8);
                        if (tablesVector.contains(foreignTable)) {
                            whereString = whereString + primaryTable + "." + primaryColumn + " = " + foreignTable + "." + foreignColumn + " AND ";
                        }
                    }
                }*/
        String selectString = selTableColumnsVector.toString();
        selectString = selectString.substring(1, selectString.length() - 1);
        String fromString = tablesVector.toString();
        fromString = fromString.substring(1, fromString.length() - 1);

        sqlString = "Select " + selectString + " from " + fromString;
        /*        if (!whereString.equals("")) {
                    whereString = " where " + whereString.substring(0, whereString.length() - 4);
                }*/
        return sqlString /* + whereString*/;
    }

    void jButton3_actionPerformed(ActionEvent e) {
        String sqlString = null;
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (jRadioButton3.isSelected()) {
                sqlString = jTextArea1.getText();
            }
            else {
                sqlString = createSQLString();
            }
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.execute(sqlString);
            resultSet = stmt.getResultSet();

            Vector columnVector = getColumns(resultSet);
            jComboBox1.setModel(new DefaultComboBoxModel(columnVector));
            for (int i = 0; i < independentComboVector.size(); i++) {
                ( (JComboBox) independentComboVector.get(i)).setModel(new DefaultComboBoxModel(columnVector));
            }
            for (int i = 0; i < englishComboVector.size(); i++) {
                ( (JComboBox) englishComboVector.get(i)).setModel(new DefaultComboBoxModel(columnVector));
            }
            for (int i = 0; i < italianComboVector.size(); i++) {
                ( (JComboBox) italianComboVector.get(i)).setModel(new DefaultComboBoxModel(columnVector));
            }
            for (int i = 0; i < greekComboVector.size(); i++) {
                ( (JComboBox) greekComboVector.get(i)).setModel(new DefaultComboBoxModel(columnVector));
            }
            if (resultSet != null)
                setEnabledControls(true);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public Vector getColumns(ResultSet rs) {
        // This method formats the result set for printing.
        Vector vector = new Vector();
        vector.add("");

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            for (int i = 1; i <= numberOfColumns; i++) {
                String columnName = rsmd.getColumnName(i);
                vector.add(columnName);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return vector;
    }

    void jButton1_actionPerformed(ActionEvent e) {
        try {
            if (jRadioButton1.isSelected()) {
                if (jComboBox1.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(this, "You must select a Column for the entity name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            int i = ( (Integer) jSpinner1.getValue()).intValue();
            while (resultSet.next()) {
                NodeVector entityNode = null;
                String entityName = null;

                if (jRadioButton2.isSelected()) {
                    entityName = jTextField1.getText() + i;
                    i++;
                }
                else {
                    entityName = resultSet.getString(jComboBox1.getSelectedItem().toString());
                    resultSet.refreshRow();
                }
                //if field is null
                if (entityName == null) {
                    //JOptionPane.showMessageDialog(this, "Null name found", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                //if entity with the this name already exists
                if (Mpiro.win.struc.getEntityTypeOrEntity(entityName) != null) {
                    if (JOptionPane.showConfirmDialog(this, "An Entity with name " + entityName + " already exists./n Replace it?", "Information",
                        JOptionPane.ERROR_MESSAGE + JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                        continue;
                }
                else
                    DataBasePanel.addEntity(entityName,"");

                entityNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityName);

                String parentName = ( (FieldData) entityNode.getIndependentFieldsVector().get(1)).m_filler;
                NodeVector parentNodeVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parentName);
                //independent values
                for (int j = 0; j < independentComboVector.size(); j++) {
                    FieldData fieldData = (FieldData) entityNode.getIndependentFieldsVector().get(j + 3);
                    String columnName = ( (JComboBox) independentComboVector.get(j)).getSelectedItem().toString();
                    if (!columnName.equals("")) {
                        String value = resultSet.getString(columnName);
                        if (value != null) {
                            fieldData.m_filler = value;
                            fieldData.set(1, fieldData.m_filler);

                            //find field type
                            FieldData parentField = null;
                            Vector databaseTableVector = parentNodeVector.getDatabaseTableVector();
                            for (int k = 0; k < databaseTableVector.size(); k++) {
                                if ( (parentField = (FieldData) databaseTableVector.get(k)).m_field.equals(fieldData.m_field)) {
                                    break;
                                }
                            }
                            //if entity in the filler does not exist create one
                            if (! (parentField.m_filler.equals("String") || parentField.m_filler.equals("Number") || parentField.m_filler.equals("Date") ||
                                   parentField.m_filler.equals("Dimension"))) {
                                if (Mpiro.win.struc.getEntityTypeOrEntity(fieldData.m_filler) == null) {
                                    findTreeNode(parentField.m_filler);
                                    DataBasePanel.addEntity(fieldData.m_filler,"");
                                    findTreeNode(parentName);
                                }
                            }
                        }
                        resultSet.refreshRow();
                    }
                }
                //english values
                for (int j = 0; j < englishComboVector.size(); j++) {
                    FieldData fieldData = (FieldData) entityNode.getEnglishFieldsVector().get(j);
                    String columnName = ( (JComboBox) englishComboVector.get(j)).getSelectedItem().toString();
                    if (!columnName.equals("")) {
                        String value = resultSet.getString(columnName);
                        if (value != null) {
                            fieldData.m_filler = value;
                            fieldData.set(1, fieldData.m_filler);
                        }
                        resultSet.refreshRow();
                    }
                }
                //italian values
                for (int j = 0; j < italianComboVector.size(); j++) {
                    FieldData fieldData = (FieldData) entityNode.getItalianFieldsVector().get(j);
                    String columnName = ( (JComboBox) italianComboVector.get(j)).getSelectedItem().toString();
                    if (!columnName.equals("")) {
                        String value = resultSet.getString(columnName);
                        if (value != null) {
                            fieldData.m_filler = value;
                            fieldData.set(1, fieldData.m_filler);
                        }
                        resultSet.refreshRow();
                    }
                }
                //greek values
                for (int j = 0; j < greekComboVector.size(); j++) {
                    FieldData fieldData = (FieldData) entityNode.getGreekFieldsVector().get(j);
                    String columnName = ( (JComboBox) greekComboVector.get(j)).getSelectedItem().toString();
                    if (!columnName.equals("")) {
                        String value = resultSet.getString(columnName);
                        if (value != null) {
                            fieldData.m_filler = value;
                            fieldData.set(1, fieldData.m_filler);
                        }
                        resultSet.refreshRow();
                    }
                }
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        setEnabledControls(false);
    }

    private void setEnabledControls(boolean bool) {
        for (int i = 0; i < jPanel13.getComponentCount(); i++)
            jPanel13.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel14.getComponentCount(); i++)
            jPanel14.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel5.getComponentCount(); i++)
            jPanel5.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel6.getComponentCount(); i++)
            jPanel6.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel7.getComponentCount(); i++)
            jPanel7.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel8.getComponentCount(); i++)
            jPanel8.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel16.getComponentCount(); i++)
            jPanel16.getComponent(i).setEnabled(bool);
        for (int i = 0; i < jPanel17.getComponentCount(); i++)
            jPanel17.getComponent(i).setEnabled(bool);
        jButton1.setEnabled(bool);
        //jTabbedPane1.setEnabled(bool);
    }

    public DefaultMutableTreeNode findTreeNode(String name) {
        DefaultMutableTreeNode currentNode = DataBasePanel.top;
        Enumeration enumer = currentNode.preorderEnumeration();
        while (enumer.hasMoreElements()) {
            currentNode = (DefaultMutableTreeNode) enumer.nextElement();
            if (currentNode.toString().equalsIgnoreCase(name)) {
                Object[] o = (Object[]) currentNode.getPath();
                TreePath treeP = new TreePath(o);
                DataBasePanel.databaseTree.expandPath(treeP);
                DataBasePanel.databaseTree.setSelectionPath(treeP);

                DataBasePanel.databaseTree.scrollPathToVisible(treeP);
                DataBasePanel.databaseTree.revalidate();
                DataBasePanel.databaseTree.repaint();
                TreePreviews.generalDataBasePreview();
                TreePreviews.setDataBaseTableAfterSearch(name, currentNode, true);
                //DataBasePanel.selpath = treeP;
                return currentNode;
            }
        }
        return null;
    }

    void jRadioButton4_actionPerformed(ActionEvent e) {
        if (jRadioButton4.isSelected()) {
            jScrollPane1.setVisible(false);
            jTextArea1.setVisible(false);
            jPanel19.setVisible(true);
        }
        else {
            jScrollPane1.setVisible(true);
            jTextArea1.setVisible(true);
            jPanel19.setVisible(false);
        }
    }

//select another table
    void jComboBox2_actionPerformed(ActionEvent e) {
        tableColumnsVector.clear();
        selTableColumnsVector.clear();
        jList2.setListData(selTableColumnsVector);

        if (! ( (String) jComboBox2.getSelectedItem()).equals("")) {
            try {
                ResultSet columnsResSet = dbMetadata.getColumns(null, null, (String) jComboBox2.getSelectedItem(), null);
                while (columnsResSet.next()) {
                    tableColumnsVector.add(columnsResSet.getString(3) + "." + columnsResSet.getString(4));
                }
                jList1.setListData(tableColumnsVector);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    void jButton4_actionPerformed(ActionEvent e) {
        if (jList1.getSelectedValue() != null) {
            selTableColumnsVector.add(jList1.getSelectedValue());
            jList2.setListData(selTableColumnsVector);
        }
    }

    void jButton5_actionPerformed(ActionEvent e) {
        selTableColumnsVector.remove(jList2.getSelectedValue());
        jList2.setListData(selTableColumnsVector);
    }

    void jButton6_actionPerformed(ActionEvent e) {
        for (int i = 0; i < tableColumnsVector.size(); i++) {
            selTableColumnsVector.add(tableColumnsVector.get(i));
        }
        jList2.setListData(selTableColumnsVector);
    }

    void jButton7_actionPerformed(ActionEvent e) {
        selTableColumnsVector.clear();
        jList2.setListData(selTableColumnsVector);

    }
}

class DialogImportFromDatabase_this_windowAdapter
    extends java.awt.event.WindowAdapter {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_this_windowAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void windowClosed(WindowEvent e) {
        adaptee.this_windowClosed(e);
    }
}

class DialogImportFromDatabase_jButton2_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton2_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jButton3_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton3_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jButton1_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton1_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jRadioButton4_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jRadioButton4_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton4_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jRadioButton3_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jRadioButton3_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton4_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jComboBox2_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jComboBox2_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBox2_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jButton4_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton4_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton4_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jButton5_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton5_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton5_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jButton6_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton6_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton6_actionPerformed(e);
    }
}

class DialogImportFromDatabase_jButton7_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportFromDatabase adaptee;

    DialogImportFromDatabase_jButton7_actionAdapter(DialogImportFromDatabase adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton7_actionPerformed(e);
    }
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
