package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
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

public class DialogImportOwlURLs
    extends JDialog {
    JPanel jPanel1 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JPanel jPanel2 = new JPanel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    static JTextField urls[];
    static JTextField filePaths[];
    static int urlNum;
    static boolean modalResult;
    private String setFilePath = "";
    JButton selectPath[];
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    DialogImportOwlURLs_jButtonSelectPath_actionAdapter pushButtonSelectPath;
    final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    JPanel jPanel6 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JTextArea jTextArea1 = new JTextArea();

    public DialogImportOwlURLs(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        modalResult = false;
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

    public DialogImportOwlURLs() {
        this(null, "", false);
    }

    private void jbInit() throws Exception {
        jButton1.setText(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
        jButton1.addActionListener(new DialogImportOwlURLs_jButton1_actionAdapter(this));
        jButton2.setText(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
        jButton2.addActionListener(new DialogImportOwlURLs_jButton2_actionAdapter(this));
        jPanel2.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jScrollPane1.setPreferredSize(new Dimension(700, 250));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanel3.setPreferredSize(new Dimension(32, 20));
        jPanel3.setLayout(borderLayout2);
        jPanel4.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanel4.setDebugGraphicsOptions(0);
        jPanel4.setPreferredSize(new Dimension(52, 20));
        jPanel4.setLayout(borderLayout3);
        jPanel6.setLayout(borderLayout1);
        jTextArea1.setBackground(UIManager.getColor("Label.background"));
        jTextArea1.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        jTextArea1.setEditable(false);
        jTextArea1.setText("...");
        jTextArea1.setWrapStyleWord(true);

        if (Mpiro.selectedLocale == Mpiro.enLocale) {
            jTextArea1.setText("The ontology you are about to open refers to the following other ontologies. If you are not connected to the\n"
                                  +" �nternet, these ontologies must be present on your local disc at the paths you will specify below.");
            jLabel2.setText("Ontology URL");
            jLabel3.setText("File path");
            setFilePath = "Set file path";
            this.setTitle("Other ontologies");
        }
        else if (Mpiro.selectedLocale == Mpiro.grLocale) {
            jTextArea1.setText("� ��������� ��� ��������� �� �������� ���������� ���� �������� ����� ����������. ��� ��� ����� ������������ ��\n"
                              +" �� Internet, �� ���������� �����  ������ �� ���������� ���� ������ ��� ����� ���� ��������� ��� �� ������� ��������.");
           jLabel2.setText("��������� ����������");
           jLabel3.setText("�������� �������");
           setFilePath = "��������� �� �������� ��� �������";
           this.setTitle("'����� ����������");
        }
        else if (Mpiro.selectedLocale == Mpiro.itLocale) {
            jTextArea1.setText("L'ontologia che sta per aprire si riferisce alle seguenti altre ontologie. Se non \u00E8 connesso ad\n"
                               +" Internet, queste ontologie devono essere presenti nel suo disco locale ai percorsi che specificher\u00E0 qui sotto.");
            jLabel2.setText("Indirizzo ontologia");
            jLabel3.setText("Percorso file");
            setFilePath = "Definire il percorso del file ";
            this.setTitle("Altre ontologie");
        }

        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton1, null);
        jPanel1.add(jButton2, null);
        this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        this.getContentPane().add(jTextArea1, BorderLayout.NORTH);
        jScrollPane1.getViewport().add(jPanel6, null);
        jPanel6.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(jPanel3, null);
        jPanel3.add(jLabel2, BorderLayout.CENTER);
        jPanel2.add(jPanel4, null);
        jPanel4.add(jLabel3, BorderLayout.CENTER);

        pushButtonSelectPath = new DialogImportOwlURLs_jButtonSelectPath_actionAdapter(this);
        gridLayout1.setRows(urlNum + 1);
        urls = new JTextField[urlNum];
        filePaths = new JTextField[urlNum];
        selectPath = new JButton[urlNum];

        for (int i = 0; i < urlNum; i++) {
            urls[i] = new JTextField("Set url", JLabel.LEFT);
            urls[i].setEditable(false);
            urls[i].setPreferredSize(new Dimension(450, 20));
            urls[i].setBackground(Color.white);
            filePaths[i] = new JTextField(setFilePath, JLabel.LEFT);
            filePaths[i].setEditable(false);
            filePaths[i].setPreferredSize(new Dimension(450, 20));
            filePaths[i].setBackground(Color.white);
            selectPath[i] = new JButton("...");
            selectPath[i].addActionListener(pushButtonSelectPath);
            JPanel temp = new JPanel();
            temp.setLayout(new BorderLayout());
            temp.add(filePaths[i], BorderLayout.CENTER);
            temp.add(selectPath[i], BorderLayout.EAST);
            jPanel2.add(urls[i], null);
            jPanel2.add(temp, null);
        }
    }

    void jButtonSelectPath_actionPerformed(ActionEvent e) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        OwlFileFilter filter = new OwlFileFilter();
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(Mpiro.win.getFrames()[0]);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File rdfFile = fileChooser.getSelectedFile();
            String fileName = fileChooser.getSelectedFile().toString();
            //fileName = OwlFileFilter.checkExtension(fileName, ".rdf");

            JButton button = (JButton) e.getSource();
            JPanel panel = (JPanel) button.getParent();
            ( (JTextField) panel.getComponent(0)).setText(fileName);
        }
        else {
            JButton button = (JButton) e.getSource();
            JPanel panel = (JPanel) button.getParent();
            ( (JTextField) panel.getComponent(0)).setText(setFilePath);
        }
    }

    void jButton1_actionPerformed(ActionEvent e) {
        modalResult = true;
        this.dispose();
    }

    void jButton2_actionPerformed(ActionEvent e) {
        modalResult = false;
        this.dispose();
    }
}

class DialogImportOwlURLs_jButtonSelectPath_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportOwlURLs adaptee;

    DialogImportOwlURLs_jButtonSelectPath_actionAdapter(DialogImportOwlURLs adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonSelectPath_actionPerformed(e);
    }
}

class DialogImportOwlURLs_jButton1_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportOwlURLs adaptee;

    DialogImportOwlURLs_jButton1_actionAdapter(DialogImportOwlURLs adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}

class DialogImportOwlURLs_jButton2_actionAdapter
    implements java.awt.event.ActionListener {
    DialogImportOwlURLs adaptee;

    DialogImportOwlURLs_jButton2_actionAdapter(DialogImportOwlURLs adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
