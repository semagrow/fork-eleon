package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DialogDatabaseOptions extends JDialog {
	JPanel panel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JTextField jTextField2 = new JTextField();
	JLabel jLabel3 = new JLabel();
	JTextField jTextField3 = new JTextField();
	JLabel jLabel4 = new JLabel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	GridLayout gridLayout2 = new GridLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	GridLayout gridLayout1 = new GridLayout();
	JButton jButton1 = new JButton();
	JPanel jPanel4 = new JPanel();
	JButton jButton2 = new JButton();
	JPasswordField jPasswordField1 = new JPasswordField();
	Vector driverVector = new Vector();
	Vector urlVector = new Vector();
	JComboBox jComboBox1 = new JComboBox(driverVector);
	boolean modalResult = false;
	public static String driver;
	public static String url;
	public static String user;
	public static String password;


	public DialogDatabaseOptions(Frame frame, String title, boolean modal) {
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
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogDatabaseOptions() {
		this(null, "", false);
	}

	private void jbInit() throws Exception {
		modalResult = false;
		jComboBox1.addItemListener(new DialogDatabaseOptions_jComboBox1_itemAdapter(this));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		jButton1.addActionListener(new DialogDatabaseOptions_jButton1_actionAdapter(this));
		jButton2.addActionListener(new DialogDatabaseOptions_jButton2_actionAdapter(this));
		jButton2.setSelected(true);
		driverVector.add("sun.jdbc.odbc.JdbcOdbcDriver");
		urlVector.add("jdbc:odbc:odbcDataSource");
		driverVector.add("oracle.jdbc.driver.OracleDriver");
		urlVector.add("jdbc:oracle:thin:@hostname:1521:ORCL");
		driverVector.add("com.sybase.jdbc.SybDriver");
		urlVector.add("jdbc:sybase:Tds:hostname:2025");
		driverVector.add("COM.ibm.db2.jdbc.app.DB2Driver");
		urlVector.add("jdbc:db2://hostname:50002/database");
		panel1.setLayout(borderLayout1);
		jPanel3.setDebugGraphicsOptions(0);
		jPanel3.setLayout(gridLayout2);
		jPanel1.setDebugGraphicsOptions(0);
		jPanel1.setLayout(borderLayout2);
		jPanel2.setDebugGraphicsOptions(0);
		jPanel2.setLayout(gridLayout1);
		gridLayout1.setColumns(0);
		gridLayout1.setRows(4);
		gridLayout2.setRows(4);
		jButton1.setText(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
		jButton2.setText(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
		jPasswordField1.setText("");
		jTextField3.setText("");
		jTextField2.setText("");
		jComboBox1.setEditable(true);
		getContentPane().add(panel1,  BorderLayout.CENTER);
		panel1.add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(jPanel2,  BorderLayout.WEST);
		jPanel2.add(jLabel1, null);
		jPanel2.add(jLabel2, null);
		jPanel2.add(jLabel3, null);
		jPanel2.add(jLabel4, null);
		jPanel1.add(jPanel3, BorderLayout.CENTER);
		jPanel3.add(jComboBox1, null);
		jPanel3.add(jTextField2, null);
		jPanel3.add(jTextField3, null);
		jPanel3.add(jPasswordField1, null);
		this.getContentPane().add(jPanel4,  BorderLayout.SOUTH);
		jPanel4.add(jButton2, null);
		jPanel4.add(jButton1, null);
		if (Mpiro.selectedLocale == Mpiro.enLocale){
			this.setTitle("Database options");
			jLabel1.setText("JDBC Driver: ");
			jLabel2.setText("Connection URL: ");
			jLabel3.setText("User Name: ");
			jLabel4.setText("Password: ");
		}
		else if (Mpiro.selectedLocale == Mpiro.grLocale){
            this.setTitle("�������� ��� ����� ���������");
			jLabel1.setText("������ JDBC: ");
			jLabel2.setText("URL ��������: ");
            jLabel3.setText("����� ������: ");
            jLabel4.setText("�������: ");
		}
		else if (Mpiro.selectedLocale == Mpiro.itLocale){
            this.setTitle("Opzioni database");
            jLabel1.setText("Driver JDBC: ");
            jLabel2.setText("URL di connessione: ");
            jLabel3.setText("Nome utente: ");
            jLabel4.setText("Password: ");
		}
	}

	public static void main(String args[]){
		new DialogDatabaseOptions().show();
	}

	void jComboBox1_itemStateChanged(ItemEvent e) {
		if(jComboBox1.getSelectedIndex() > -1)
			jTextField2.setText(urlVector.get(jComboBox1.getSelectedIndex()).toString());
	}

	void jButton1_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void jButton2_actionPerformed(ActionEvent e) {
		modalResult = true;
		driver = jComboBox1.getSelectedItem().toString();
		url = jTextField2.getText();
		user = jTextField3.getText();
		password = String.valueOf(jPasswordField1.getPassword());
		this.dispose();
	}
}

class DialogDatabaseOptions_jComboBox1_itemAdapter implements java.awt.event.ItemListener {
	DialogDatabaseOptions adaptee;

	DialogDatabaseOptions_jComboBox1_itemAdapter(DialogDatabaseOptions adaptee) {
		this.adaptee = adaptee;
	}
	public void itemStateChanged(ItemEvent e) {
		adaptee.jComboBox1_itemStateChanged(e);
	}
}

class DialogDatabaseOptions_jButton1_actionAdapter implements java.awt.event.ActionListener {
	DialogDatabaseOptions adaptee;

	DialogDatabaseOptions_jButton1_actionAdapter(DialogDatabaseOptions adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}

class DialogDatabaseOptions_jButton2_actionAdapter implements java.awt.event.ActionListener {
	DialogDatabaseOptions adaptee;

	DialogDatabaseOptions_jButton2_actionAdapter(DialogDatabaseOptions adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}
