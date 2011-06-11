/**
 * <p>Title: LangChooser</p>
 * 
 * <p>Description: The dialog for choosing the language for the tool.
 * It is shown before opening the main window.</p>
 * 
 * <p>Copyright: Copyright (c) 2001-2004 NCSR "Demokritos"</p>
 * 
 * @author Dimitris Spiliotopoulos
 * @author Theofilos Nickolaou
 * @author Dimitris Bilidas
 **/


package gr.demokritos.iit.eleon.ui.lang;


import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.ui.ELEONWindow;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class LangChooser extends JFrame implements ActionListener
{
	private KLabel en_textFieldLabel;
	private KLabel it_textFieldLabel;
	private KLabel gr_textFieldLabel;
	
	private JButton englishButton;
	private JButton italianButton;
	private JButton greekButton;
	private JButton exitButton;


	/**
	 * The constructor
	 */
	
	public LangChooser()
	{
		setTitle( "M-PIRO authoring tool" );
		
		//frameTitle = new String("M-PIRO Authoring Tool");
		// The dialog and its components from top to bottom (1-6)
		// dialog = new JDialog(this, frameTitle, true);
		setIconImage( Mpiro.obj.image_corner );
		setResizable(false);
		addWindowListener(new LangChooserWindowListener());
		addKeyListener(new LangChooserKeyListener()); //theofilos
		
		// 1
		JPanel labelPanel = new JPanel(new BorderLayout());
		labelPanel.setPreferredSize(new Dimension(400, 80));  //theofilos
		labelPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); //theofilos
		
		en_textFieldLabel = new KLabel("  Please choose the language for the tool:");
		en_textFieldLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,12));
		labelPanel.add(BorderLayout.NORTH, en_textFieldLabel);
		
		it_textFieldLabel = new KLabel("  Scegli la lingua per il software:"); //theofilos
		it_textFieldLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,12));   //theofilos
		labelPanel.add(BorderLayout.WEST, it_textFieldLabel);    //theofilos
        
		gr_textFieldLabel = new KLabel("  Παρακαλώ, επιλέξτε γλώσσα για το εργαλείο:");  //theofilos
		gr_textFieldLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,12));   //theofilos
		labelPanel.add(BorderLayout.SOUTH, gr_textFieldLabel);  //theofilos
		
		ImageIcon ICON_3DUK = new ImageIcon( Mpiro.obj.image_3dUK );
		ImageIcon ICON_3DIT = new ImageIcon( Mpiro.obj.image_3dItaly );
		ImageIcon ICON_3DGR = new ImageIcon( Mpiro.obj.image_3dGreece );
		ImageIcon ICON_EXIT = new ImageIcon( Mpiro.obj.image_exit );
		
		englishButton = new JButton();
		englishButton.setIcon(ICON_3DUK);
		englishButton.setText("English"); //theofilos
		englishButton.setHorizontalTextPosition(SwingConstants.CENTER); //theofilos
		englishButton.setVerticalTextPosition(SwingConstants.BOTTOM); //theofilos
		englishButton.setForeground(new Color(20,20,20)); //theofilos
		
		italianButton = new JButton();
		italianButton.setIcon(ICON_3DIT);
		italianButton.setText("Italiano"); //theofilos
		italianButton.setHorizontalTextPosition(SwingConstants.CENTER); //theofilos
		italianButton.setVerticalTextPosition(SwingConstants.BOTTOM); //theofilos
		italianButton.setForeground(new Color(20,20,20)); //theofilos

		greekButton = new JButton();
		greekButton.setIcon( ICON_3DGR );
		greekButton.setText("Ελληνικά"); //theofilos
		greekButton.setHorizontalTextPosition(SwingConstants.CENTER); //theofilos
		greekButton.setVerticalTextPosition(SwingConstants.BOTTOM); //theofilos 
		greekButton.setForeground(new Color(20,20,20)); //theofilos       
		
		exitButton = new JButton();
		exitButton.setIcon(ICON_EXIT);
		exitButton.setPreferredSize(new Dimension(60, 60)); //theofilos
		
		// 2
		JPanel langButtonsPanel = new JPanel(new GridBagLayout());
		langButtonsPanel.setPreferredSize(new Dimension(400, 100)); //theofilos   apo (400, 150)
		langButtonsPanel.setBorder(new EmptyBorder(50, 10, 50, 10));
		//langButtonsPanel.setBorder(new LineBorder(new Color(250,250,250), 1));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(1,1,1,1);
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridy = 0;
		c.ipady = 0; c.ipadx = 0;

		langButtonsPanel.add(englishButton, c);
		c.gridx = 1;
		langButtonsPanel.add(italianButton, c);
		c.gridx = 2;
		langButtonsPanel.add(greekButton, c);
		
		// 3
		JPanel exitButtonPanel = new JPanel(new BorderLayout());
		exitButtonPanel.setPreferredSize(new Dimension(100, 50));
		exitButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 15));
		
		exitButtonPanel.add(BorderLayout.EAST, exitButton);
		
		// Place them in the contentPane of the dialog
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(BorderLayout.NORTH, labelPanel);
		contentPane.add(BorderLayout.CENTER, langButtonsPanel);
		contentPane.add(BorderLayout.SOUTH, exitButtonPanel);
		
		// Add the actionListener
		englishButton.addActionListener(this);
		italianButton.addActionListener(this);
		greekButton.addActionListener(this);
		exitButton.addActionListener(this);
        
		//Add the KeyListener //theofilos the keylisteners
		englishButton.addKeyListener(new LangChooserKeyListener());
		italianButton.addKeyListener(new LangChooserKeyListener());
		greekButton.addKeyListener(new LangChooserKeyListener());
		exitButton.addKeyListener(new LangChooserKeyListener());
		
		// Make the dialog visible
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		setLocation( (screenSize.width - size.width) / 2,
		                    (screenSize.height - size.height) / 2 );
		setVisible(true);

	} // constructor


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == englishButton) 
		{
			this.dispose();
			Mpiro.selectedLocale = Mpiro.enLocale;
	        Mpiro.win = new ELEONWindow();
		}
		else if (e.getSource() == italianButton) 
		{
			this.dispose();
			Mpiro.selectedLocale = Mpiro.itLocale;
	        Mpiro.win = new ELEONWindow();
		}
		else if (e.getSource() == greekButton) 
		{
			this.dispose();
			Mpiro.selectedLocale = Mpiro.grLocale;
	        Mpiro.win = new ELEONWindow();
		}
		else if (e.getSource() == exitButton) 
		{
			this.dispose();
			System.exit(0);
		}
	}
    

	class LangChooserWindowListener extends WindowAdapter implements WindowListener 
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}
    
    
	//theofilos the whole class
	class LangChooserKeyListener extends KeyAdapter implements KeyListener 
	{
		public void keyPressed(KeyEvent e) 
		{
			if (e.getSource() == englishButton && e.getKeyCode() == KeyEvent.VK_ENTER) 
			{
				Mpiro.selectedLocale = Mpiro.enLocale;
				dispose();
			}
			else if (e.getSource() == italianButton && e.getKeyCode() == KeyEvent.VK_ENTER) 
			{
				Mpiro.selectedLocale = Mpiro.itLocale;
				dispose();
			}
			else if (e.getSource() == greekButton && e.getKeyCode() == KeyEvent.VK_ENTER) 
			{
				dispose();
				Mpiro.selectedLocale = Mpiro.grLocale;
			}
			else if (e.getSource() == exitButton && e.getKeyCode() == KeyEvent.VK_ENTER) 
			{
				dispose();
				System.exit(0);
			}
		}
	}
	
}
