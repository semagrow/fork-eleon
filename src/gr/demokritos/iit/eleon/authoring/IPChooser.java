//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.IOException;
import javax.swing.tree.*;
import javax.swing.JTree;
import javax.swing.border.*;
import java.net.*;

import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KLabel;
import gr.demokritos.iit.eleon.um.*;

/**
 * <p>Title: IPChooser</p>
 * <p>Description: The dialog that asks for the IP and Port of the personalisation server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class IPChooser extends JFrame implements ActionListener
{
	static JDialog dialog;
	static KLabel selectionLabel;
	static JPanel selectionPanel;
	private OkCancel okCancel;
	
	private JTextField pserverIP;
	private JTextField pserverPort;
	private KButton okButton;
	private KButton cancelButton;
	static String domainName;
	
	private String exportOrResetFlag;
	
	
	/**
	 * The first constructor
	 * @param domainNameFromMpiro the mpiro domain name
	 */
	public IPChooser(String domainNameFromMpiro)
	{
		domainName = domainNameFromMpiro;
		
		exportOrResetFlag = "EXPORT";
		
		// The dialog and its components
		dialog = new JDialog(this, LangResources.getString(Mpiro.selectedLocale, "mpiroExportSelection_text"), true);
		super.setIconImage( Mpiro.obj.image_corner );
		
		Vector pserverAddressVector = QueryOptionsHashtable.getPServerAddressFromMainOptionsHashtable();
		
		selectionLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "pleaseSelectAnIPAddressAndPort_text"));
		selectionLabel.setPreferredSize(new Dimension(230, 20));
		
		selectionPanel = new JPanel(new GridBagLayout());
		selectionPanel.setPreferredSize(new Dimension(160,40));

		JLabel labelone = new JLabel(" http:// ");
		JLabel labeltwo = new JLabel(" : ");
		pserverIP = new JTextField(pserverAddressVector.elementAt(0).toString());
		pserverIP.setPreferredSize(new Dimension(100, 20));
		pserverPort = new JTextField(pserverAddressVector.elementAt(1).toString());
		
		okCancel = new OkCancel();
		
		selectionPanel.add(labelone);
		selectionPanel.add(pserverIP);
		selectionPanel.add(labeltwo);
		selectionPanel.add(pserverPort);
		
		// Place them in the contentPane of the dialog
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add("North", selectionLabel);
		contentPane.add("Center", selectionPanel);
		contentPane.add("South", okCancel);

		// Add the actionListener
		okButton.addActionListener(IPChooser.this);
		cancelButton.addActionListener(IPChooser.this);
		
		// Make the dialog visible
		dialog.pack();
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = dialog.getSize();
		dialog.setLocation( (screenSize.width - size.width) / 2,
		          					(screenSize.height - size.height) / 2 );
		dialog.setVisible(true);
	} // constructor 1


	/**
	 * The second constructor
	 */
	public IPChooser()
	{
		exportOrResetFlag = "RESET";
		
		// The dialog and its components
		dialog = new JDialog(this, LangResources.getString(Mpiro.selectedLocale, "mpiroExportSelection_text"), true);
		super.setIconImage( Mpiro.obj.image_corner );
		
		Vector pserverAddressVector = QueryOptionsHashtable.getPServerAddressFromMainOptionsHashtable();
		
		selectionLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "pleaseSelectAnIPAddressAndPort_text"));
		selectionLabel.setPreferredSize(new Dimension(230, 20));
		
		selectionPanel = new JPanel(new GridBagLayout());
		selectionPanel.setPreferredSize(new Dimension(160,40));
		
		JLabel labelone = new JLabel(" http:// ");
		JLabel labeltwo = new JLabel(" : ");
		pserverIP = new JTextField(pserverAddressVector.elementAt(0).toString());
		pserverIP.setPreferredSize(new Dimension(100, 20));
		pserverPort = new JTextField(pserverAddressVector.elementAt(1).toString());
		
		okCancel = new OkCancel();
		
		selectionPanel.add(labelone);
		selectionPanel.add(pserverIP);
		selectionPanel.add(labeltwo);
		selectionPanel.add(pserverPort);

		// Place them in the contentPane of the dialog
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add("North", selectionLabel);
		contentPane.add("Center", selectionPanel);
		contentPane.add("South", okCancel);
		
		// Add the actionListener
		okButton.addActionListener(IPChooser.this);
		cancelButton.addActionListener(IPChooser.this);
		
		// Make the dialog visible
		dialog.pack();
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = dialog.getSize();
		dialog.setLocation( (screenSize.width - size.width) / 2,
												(screenSize.height - size.height) / 2 );
		dialog.setVisible(true);
	} // constructor 2



	class OkCancel extends JPanel
	{
		public OkCancel() 
		{
			setLayout(new GridLayout(1,2));
			setPreferredSize(new Dimension(160,30));
			okButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
			cancelButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
			add(okButton);
			add(cancelButton);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == okButton)
		{
			if ( ExportUtilsPServer.establishConnectionWithPServer(pserverIP.getText(), pserverPort.getText()) )
			{
				if (exportOrResetFlag.equalsIgnoreCase("EXPORT"))
				{
					try
					{
						dialog.dispose();
						ExportUtilsPServer.exportToServer(pserverIP.getText(), pserverPort.getText());
					}
					catch (UMException ume)
					{
						System.out.println("(IPChooser:ExportUtilsPServer.exportToServer)---- " + ume);
						ume.printStackTrace();
					}
				}
				else if (exportOrResetFlag.equalsIgnoreCase("RESET"))
				{
					try
					{
						dialog.dispose();
						ExportUtilsPServer.resetInteractionHistory(pserverIP.getText(), pserverPort.getText());
					}
					catch (UMException ume)
					{
						System.out.println("(IPChooser:ExportUtilsPServer.resetInteractionHistory)---- " + ume);
						ume.printStackTrace();
					}
				}
			}
		}
		
		if (e.getSource() == cancelButton)
		{
			dialog.dispose();
		}
	} // actionPerformed

} // class IPChooser