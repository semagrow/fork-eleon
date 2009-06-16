//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;		 


/**
 * <p>Title: ExportProgressMonitor</p>
 * <p>Description: The implementation of an export progress monitor</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
//Edited and enhanced by Maria Prospathopoulou and Theofilos Nickolaou
public class ExportProgressMonitor extends JFrame implements ActionListener 
{
	private JButton okButton;
	private JProgressBar progressBar;
	//private KLabel progressLabel;
	private JPanel contentPane;
	
	private JProgressBar progressBar1,progressBar2;		
	//private KLabel progressLabel1, progressLabel2;		
	private KLabel exprimoLabel, emulatorLabel;			
	public KLabel exprimoOrEmulatorsLabel;			
	

	/**
	 * The constructor(1)
	 * @param minValue the minimum value for the progress bar
	 * @param maxValue the maximum value for the progress bar
	 * @param currentValue the current value for the progress bar
	 */
	public ExportProgressMonitor(int minValue, int maxValue, int currentValue) 
	{
		super("ELEON Export Progress Monitor");
		setIconImage(Mpiro.obj.image_corner);
		//setSize( 310, 80 );
		
		// Create the OKButton and its panel.
		JPanel OKButtonPanel = new JPanel(new BorderLayout());
		OKButtonPanel.setPreferredSize(new Dimension(100, 60));
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		OKButtonPanel.add(okButton, BorderLayout.SOUTH);
		
		// Create a label
		//progressLabel = new KLabel( "Initializing..." );						
		//progressLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));		
		//progressLabel.setPreferredSize( new Dimension( 350, 24 ) );			
		
		exprimoOrEmulatorsLabel=new KLabel("");										
		exprimoOrEmulatorsLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,12));	
		exprimoOrEmulatorsLabel.setPreferredSize( new Dimension( 350, 24 ) );		
        
		// Create a progress bar
		progressBar = new JProgressBar();
		progressBar.setPreferredSize( new Dimension( 350, 20 ) );
		progressBar.setMinimum( minValue );
		progressBar.setMaximum( maxValue );
		progressBar.setValue( currentValue );
		progressBar.setBounds( 20, 35, 260, 20 );
		progressBar.setStringPainted(true);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(exprimoOrEmulatorsLabel, BorderLayout.NORTH);		
		contentPane.add(OKButtonPanel, BorderLayout.SOUTH);
		contentPane.add(progressBar, BorderLayout.CENTER);
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		
		//repaint();

		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = this.getSize();
		setLocation( (screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2 );
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
    
    
	/**																		
	 * The constructor(2)													
	 * @param minValue1 the minimum value for the progress bar1				
	 * @param maxValue1 the maximum value for the progress bar1				
	 * @param currentValue1 the current value for the progress bar1			
	 * @param minValue2 the minimum value for the progress bar2				
	 * @param maxValue2 the maximum value for the progress bar2				
	 * @param currentValue2 the current value for the progress bar2			
	 */		
	//maria 																
	public ExportProgressMonitor(int minValue1, int maxValue1, int currentValue1, int minValue2, int maxValue2, int currentValue2) 
	{																		
	  super("ELEON Export Progress Monitor");							
	  setIconImage(Mpiro.obj.image_corner);									
	
	  // Create the OKButton and its panel.
	  JPanel OKButtonPanel = new JPanel(new BorderLayout());				
	  OKButtonPanel.setPreferredSize(new Dimension(100, 60));				
	  okButton = new JButton("OK");										
	  okButton.addActionListener(this);									
	  OKButtonPanel.add(okButton, BorderLayout.SOUTH);					
        
		// Create the labels
		//progressLabel1 = new KLabel( "Initializing..." );					
		//progressLabel1.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));	
		//progressLabel1.setPreferredSize( new Dimension( 350, 24 ) );		
		//progressLabel2 = new KLabel( "Initializing..." );					
		//progressLabel2.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));	
		//progressLabel2.setPreferredSize( new Dimension( 350, 24 ) );		
		
		exprimoLabel  = new KLabel( LangResources.getString(Mpiro.selectedLocale,"exportToExprimo_text" ));					
		exprimoLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,12));	
		exprimoLabel.setPreferredSize( new Dimension( 350, 24 ) );			
		emulatorLabel = new KLabel( LangResources.getString(Mpiro.selectedLocale,"exportToPEmulator_text" ));
		emulatorLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,12));	
		emulatorLabel.setPreferredSize( new Dimension( 350, 24 ) );			
        
    // Create the progress bars
    progressBar1 = new JProgressBar();									
    progressBar1.setPreferredSize( new Dimension( 350, 20 ) );			
    progressBar1.setMinimum( minValue1 );								
    progressBar1.setMaximum( maxValue1 );								
    progressBar1.setValue( currentValue1 );								
    progressBar1.setBounds( 20, 35, 260, 20 );							
    progressBar1.setStringPainted(true);								
    progressBar2 = new JProgressBar();									
    progressBar2.setPreferredSize( new Dimension( 350, 20 ) );			
    progressBar2.setMinimum( minValue2 );								
    progressBar2.setMaximum( maxValue2 );								
    progressBar2.setValue( currentValue2 );								
    progressBar2.setBounds( 20, 35, 260, 20 );							
    progressBar2.setStringPainted(true);								
        
    //Create the panels which contain the progress bars
    JPanel progressBar1Panel = new JPanel(new BorderLayout());			
    progressBar1Panel.setBorder(new EtchedBorder());					
    //progressBar1Panel.setPreferredSize(new Dimension(350, 60));		
    progressBar1Panel.add(exprimoLabel, BorderLayout.CENTER);			
    //progressBar1Panel.add(progressLabel1, BorderLayout.CENTER);		
    progressBar1Panel.add(progressBar1, BorderLayout.SOUTH);			
    JPanel progressBar2Panel = new JPanel(new BorderLayout());			
    progressBar2Panel.setBorder(new EtchedBorder());					
    //progressBar2Panel.setPreferredSize(new Dimension(350, 60));		
    progressBar2Panel.add(emulatorLabel, BorderLayout.CENTER);			
    //progressBar2Panel.add(progressLabel2, BorderLayout.CENTER);		
    progressBar2Panel.add(progressBar2, BorderLayout.SOUTH);			
    
    //gia na yparxei keno anamesa sta progressbars
    JPanel emptyPanel = new JPanel();									
    emptyPanel.setPreferredSize(new Dimension(20,20));					
    JPanel topPanel=new JPanel(new BorderLayout());						
    topPanel.add(progressBar2Panel, BorderLayout.NORTH);				
    topPanel.add(emptyPanel, BorderLayout.CENTER);						
        
		contentPane = new JPanel();											
		contentPane.setLayout(new BorderLayout());							
		contentPane.add(topPanel, BorderLayout.NORTH);						
		contentPane.add(OKButtonPanel, BorderLayout.SOUTH);					
		contentPane.add(progressBar1Panel, BorderLayout.CENTER);			
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));	
		setContentPane(contentPane);										
		
		pack();																
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
		Dimension size = this.getSize();									
		setLocation( (screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2 );	
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);			
		setVisible(true);													
	}																		
    

	/**
	 * If the OK-button is pressed the exportProgresMonitor disposes
	 * @param event okButton is pressed
	 */
	public void actionPerformed( ActionEvent event )
	{
		if( event.getSource() == okButton )
		{
			ExportProgressMonitor.this.dispose();
		}
	}



	/**
	 * Method that updates the progress bar
	 * @param number the current value for the progress bar
	 * @param source shows if the export is to exprimo(1), psemulator(2) or psserver(3)
	 */
	//maria 
	public void updateProgressBar(int number, int source)				
	{																	
		if(ExportDialog.doubleExport)									
		{																
			if(source==1)												
			{												
				progressBar1.setValue(number);							
				Rectangle progressRect = progressBar1.getBounds();		
				progressRect.x = 0;										
				progressRect.y = 0;										
				progressBar1.paintImmediately( progressRect );			
				Rectangle paneRect = contentPane.getBounds();  //theofilos
				contentPane.paintImmediately( paneRect );    //theofilos
			}															
			else if(source==2)											
  		{													
				progressBar2.setValue(number);						
				Rectangle progressRect = progressBar2.getBounds();	
				progressRect.x = 0;									
				progressRect.y = 0;									
				progressBar2.paintImmediately( progressRect );		
				Rectangle paneRect = contentPane.getBounds();   //theofilos
				contentPane.paintImmediately( paneRect );    //theofilos
  		}														
  		else													
  		{													
				progressBar.setValue(number);						
				Rectangle progressRect = progressBar.getBounds();	
				progressRect.x = 0;									
				progressRect.y = 0;									
				progressBar.paintImmediately( progressRect );		
				Rectangle paneRect = contentPane.getBounds();   //theofilos
				contentPane.paintImmediately( paneRect );   //theofilos
  		}														
		}																
		else															
		{																
			progressBar.setValue(number);								
			Rectangle progressRect = progressBar.getBounds();			
			progressRect.x = 0;											
			progressRect.y = 0;											
			progressBar.paintImmediately( progressRect );				
			Rectangle paneRect = contentPane.getBounds();    //theofilos
			contentPane.paintImmediately( paneRect );   //theofilos
		}																
	}																	


    /**
     * Method that updates the progress label
     * @param labelText the new text for the label
     * @param source shows if the export is to exprimo(1), psemulator(2) or psserver(3)
     */
/*    public void updateProgressLabel(String labelText, int source)		
    {																	
		if(ExportDialog.doubleExport)									
		{																
			if(source==1)												
			{															
				progressLabel1.setText(labelText);						
        		Rectangle progressRect = contentPane.getBounds();		
        		progressRect.x = 0;										
        		progressRect.y = 0;										
        		contentPane.paintImmediately( progressRect );			
        	}															
        	else if (source==2)											
        		{														
        			progressLabel2.setText(labelText);					
        			Rectangle progressRect = contentPane.getBounds();	
        			progressRect.x = 0;									
        			progressRect.y = 0;									
        			contentPane.paintImmediately( progressRect );		
        		}														
        		else													
        		{														
        			progressLabel.setText(labelText);					
        			Rectangle progressRect = contentPane.getBounds();	
        			progressRect.x = 0;									
        			progressRect.y = 0;									
        			contentPane.paintImmediately( progressRect );		
        		}														
        }																
        else															
        {																
        	progressLabel.setText(labelText);							
        	Rectangle progressRect = contentPane.getBounds();			
        	progressRect.x = 0;											
        	progressRect.y = 0;											
        	contentPane.paintImmediately( progressRect );				
        }																
			
    }*/																	


	/**
	 * Method that updates the OK-button
	 * @param enableOrDisable "true" for enabled, "false" for disabled
	 */
	public void updateOKButton(boolean enableOrDisable)
	{
	  okButton.setEnabled(enableOrDisable);
	  Rectangle progressRect = okButton.getBounds();
	  progressRect.x = 0;
	  progressRect.y = 0;
	  okButton.paintImmediately( progressRect );
	}
}