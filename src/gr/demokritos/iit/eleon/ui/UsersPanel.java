//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class UsersPanel extends JPanel implements ActionListener 
{
	public static ImageIcon ICON_USERS = new ImageIcon(Mpiro.obj.image_users);
	public static ImageIcon ICON_USER = new ImageIcon(Mpiro.obj.image_user);
        
        public static ImageIcon ICON_ROBOTS = new ImageIcon(Mpiro.obj.image_robots);
	public static ImageIcon ICON_ROBOT = new ImageIcon(Mpiro.obj.image_robot);
        public static ImageIcon ICON_ROBOTSANDUSERS = new ImageIcon(Mpiro.obj.image_robotsAndUsers);
        public static ImageIcon ICON_ROBOTBRAIN = new ImageIcon(Mpiro.obj.image_robotbrain);
	
	
	public static JLabel label01;
	public static ImageIcon im;
	public static DefaultMutableTreeNode last;
	public static ImageIcon parentim;
	public static DefaultMutableTreeNode parent;
	
	public static NumberComboBox maxFactsCombo;
	public static NumberComboBox factsPerPageCombo;
	public static NumberComboBox linksPerPageCombo;
	public static KComboBox synthVoiceCombo;
        
        public static JSlider openness;
        public static JSlider conscientiousness;
        public static JSlider extraversion;
        public static JSlider agreeableness;
        public static JSlider naturalReactions;
       // jSlider1.setBounds(190, 260, 200, 23);
	
	public static JPanel multipanel;
	public static JPanel theFourPanels;
        public static JPanel theFivePanels;
        
        public static RobotCharacteristicsPanel robotsChar;
        
	static JTree userstree;
	JPanel diafora;
	static DefaultTreeModel treeModel;
	public static DefaultMutableTreeNode users, usersAndRobots, robots, robotCharacteristics;
	
	protected IconCellRenderer renderer;
	protected IconCellEditor editor;
	protected JPopupMenu popupusers;
	protected JPopupMenu popupuser;
        protected JPopupMenu popuprobots;
        
	protected JPopupMenu popuprobot;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static DefaultMutableTreeNode unode = null;

  // LangResources
  private String addNewUser_action = LangResources.getString(Mpiro.selectedLocale, "addNewUser_action");
  private String rename_action = LangResources.getString(Mpiro.selectedLocale, "rename_action");
  private String delete_action = LangResources.getString(Mpiro.selectedLocale, "delete_action");

  private String addUser_text = LangResources.getString(Mpiro.selectedLocale, "addUser_text");
  private String renameNode_text = LangResources.getString(Mpiro.selectedLocale, "renameNode_text");
  private String newName_text = LangResources.getString(Mpiro.selectedLocale, "newName_text");


  public UsersPanel() 
  {
	  setLayout(new BorderLayout());
	
          usersAndRobots=new DefaultMutableTreeNode(new IconData(ICON_ROBOTSANDUSERS, "User and Robot Modelling"));
	  users = new DefaultMutableTreeNode(new IconData(ICON_USERS, "User types"));
	  users.add(new DefaultMutableTreeNode(new IconData(ICON_USER, "NewUserType")));
          
          robots = new DefaultMutableTreeNode(new IconData(ICON_ROBOTS, "Profiles"));
	  robots.add(new DefaultMutableTreeNode(new IconData(ICON_ROBOT, "NewProfile")));
          
          robotCharacteristics = new DefaultMutableTreeNode(new IconData(ICON_ROBOTBRAIN, "Profile Attributes"));
          
          usersAndRobots.add(users);
          usersAndRobots.add(robots);
          usersAndRobots.add(robotCharacteristics);
	
	  treeModel = new DefaultTreeModel(usersAndRobots);
	  userstree = new JTree(treeModel);
	
	  userstree.getSelectionModel().setSelectionMode(
	  TreeSelectionModel.SINGLE_TREE_SELECTION);
	  userstree.setShowsRootHandles(true);
	  userstree.setEditable(false);
	  userstree.putClientProperty("JTree.lineStyle", "Angled");
	
	  renderer = new IconCellRenderer();
	  userstree.setCellRenderer(renderer);
	  editor = new IconCellEditor(userstree);
	  userstree.setCellEditor(editor);
	  userstree.setInvokesStopCellEditing(true);
	
	  addPopups();
	  userstree.addMouseListener(mlisten);

	  JScrollPane treeView = new JScrollPane(userstree);
	  treeView.setPreferredSize(new Dimension(300,500));
	  treeView.setMinimumSize(new Dimension(290,500));
	
	  /** The label */
	  label01 = new JLabel(LangResources.getString(Mpiro.selectedLocale, "clickTheHierarchyToYourLeft_text"));
	  label01.setFont(new Font(Mpiro.selectedFont,Font.BOLD,13));
	  label01.setForeground(Color.black);
	  label01.setPreferredSize(new Dimension(380, 23));
	
	  /* The labelPanel */
	  //JPanel labelPanel = new JPanel(new GridBagLayout());
	  JPanel labelPanel = new JPanel(new BorderLayout());
	  labelPanel.setPreferredSize(new Dimension(300, 50));
	  //labelPanel.setBackground(new Color(200,100,100));
	  labelPanel.setBorder(new EmptyBorder(new Insets(0,3,0,13)));
	  labelPanel.add("North", label01);

    // the 4 panels
    JPanel maxFactsPanel = new JPanel(new BorderLayout());
    JPanel factsPerPagePanel = new JPanel(new BorderLayout());
    JPanel linksPerPagePanel = new JPanel(new BorderLayout());
    JPanel synthVoicePanel = new JPanel(new BorderLayout());

    KLabel maxFactsLabel =     new KLabel(LangResources.getString(Mpiro.selectedLocale, "maximumFactsPerSentence_text"));
    KLabel factsPerPageLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "factsPerPage_text"));
    KLabel linksPerPageLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "linksPerPage_text"));
    KLabel synthVoiceLabel =   new KLabel(LangResources.getString(Mpiro.selectedLocale, "synthesizerVoice_text"));

    maxFactsLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    factsPerPageLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    linksPerPageLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    synthVoiceLabel.setPreferredSize(new Dimension(265, 20)); //theofilos

    maxFactsCombo = new NumberComboBox(10);
    factsPerPageCombo = new NumberComboBox(100);
    linksPerPageCombo = new NumberComboBox(50);
    synthVoiceCombo = new SynthVoiceCombo();

    maxFactsPanel.setPreferredSize(new Dimension(320, 20)); //theofilos
    factsPerPagePanel.setPreferredSize(new Dimension(320, 20)); //theofilos
    linksPerPagePanel.setPreferredSize(new Dimension(320, 20)); //theofilos
    synthVoicePanel.setPreferredSize(new Dimension(320, 20)); //theofilos

    maxFactsPanel.add("West", maxFactsLabel);
    maxFactsPanel.add("Center", maxFactsCombo);
    factsPerPagePanel.add("West", factsPerPageLabel);
    factsPerPagePanel.add("Center", factsPerPageCombo);
    linksPerPagePanel.add("West", linksPerPageLabel);
    linksPerPagePanel.add("Center", linksPerPageCombo);
    synthVoicePanel.add("West", synthVoiceLabel);
    synthVoicePanel.add("Center", synthVoiceCombo);

    theFourPanels = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.insets = new Insets(1,0,1,0);
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0; c.weighty = 1.0;

    //c.gridx = 0;
    c.gridy = 0;
    theFourPanels.add(maxFactsPanel, c);
    c.gridy = 1;
    theFourPanels.add(factsPerPagePanel, c);
    c.gridy = 2;
    theFourPanels.add(linksPerPagePanel, c);
    c.gridy = 3;
    theFourPanels.add(synthVoicePanel, c);
    
    
    //********************ROBOTS********************
   
    JPanel opennessPanel = new JPanel(new BorderLayout());
    JPanel conscientiousnessPanel = new JPanel(new BorderLayout());
    JPanel extraversionPanel = new JPanel(new BorderLayout());
    JPanel agreeablenessPanel = new JPanel(new BorderLayout());
    JPanel naturalReactionsPanel = new JPanel(new BorderLayout());
   

    KLabel opennessLabel =     new KLabel("Openness to Experience");
    KLabel conscientiousnessLabel = new KLabel("Conscientiousness - Work Ethic");
    KLabel extraversionLabel = new KLabel("Extraversion");
    KLabel agreeablenessLabel =   new KLabel("Agreeableness");
    KLabel naturalReactionsLabel =   new KLabel("Natural Reactions ");
    
    //values.setPreferredSize(420, 25);
    
    opennessLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    conscientiousnessLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    extraversionLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    agreeablenessLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    naturalReactionsLabel.setPreferredSize(new Dimension(265, 20)); //theofilos
    
    openness= new JSlider();
         conscientiousness= new JSlider();
        extraversion= new JSlider();
         agreeableness= new JSlider();
     naturalReactions= new JSlider();
     
//Hashtable labelTable = new Hashtable();
//labelTable.put( new Integer( 0 ), new JLabel("0") );
//labelTable.put( new Integer( 20 ), new JLabel("20") );
//labelTable.put( new Integer( 40 ), new JLabel("40") );
//labelTable.put( new Integer( 60 ), new JLabel("60") );
//labelTable.put( new Integer( 80 ), new JLabel("80") );
//labelTable.put( new Integer( 100 ), new JLabel("100") );

//Font font = new Font("arial", Font.BOLD, 12);


//openness.setLabelTable( labelTable );
//openness.setPaintLabels(true);
//openness.setMajorTickSpacing(20);
//openness.setMinorTickSpacing(10);
//openness.setFont(font);
//conscientiousness.setLabelTable( labelTable );
//conscientiousness.setPaintLabels(true);
//conscientiousness.setMajorTickSpacing(20);
//conscientiousness.setFont(font);
//extraversion.setLabelTable( labelTable );
//extraversion.setPaintLabels(true);
//extraversion.setMajorTickSpacing(20);
//extraversion.setFont(font);
//agreeableness.setLabelTable( labelTable );
//agreeableness.setPaintLabels(true);
//agreeableness.setMajorTickSpacing(20);
//agreeableness.setFont(font);
//naturalReactions.setLabelTable( labelTable );
//naturalReactions.setPaintLabels(true);
//naturalReactions.setMajorTickSpacing(20);
//naturalReactions.setFont(font);

   
     opennessPanel.setPreferredSize(new Dimension(420, 25));
    conscientiousnessPanel.setPreferredSize(new Dimension(420, 25));
    extraversionPanel.setPreferredSize(new Dimension(420, 25));
    agreeablenessPanel.setPreferredSize(new Dimension(420, 25));
     naturalReactionsPanel.setPreferredSize(new Dimension(420, 25));

     
    opennessPanel.add("West", opennessLabel);
    opennessPanel.add("Center", openness);
    conscientiousnessPanel.add("West",conscientiousnessLabel);
    conscientiousnessPanel.add("Center", conscientiousness);
    extraversionPanel.add("West", extraversionLabel);
    extraversionPanel.add("Center", extraversion);
    agreeablenessPanel.add("West", agreeablenessLabel);
    agreeablenessPanel.add("Center", agreeableness);
    naturalReactionsPanel.add("West", naturalReactionsLabel);
    naturalReactionsPanel.add("Center", naturalReactions);

    theFivePanels = new JPanel(new GridBagLayout());
    GridBagConstraints c1 = new GridBagConstraints();
    c1.fill = GridBagConstraints.NONE;
    c1.insets = new Insets(1,0,1,0);
    c1.anchor = GridBagConstraints.WEST;
    c1.weightx = 1.5; //c1.weighty = 1.5;

    //c.gridx = 0;
    c1.gridy = 0;
   // c1.gridx = 0;
   // GridBagConstraints c2 = new GridBagConstraints();
   // c2.fill = GridBagConstraints.NONE;
   // c2.insets = new Insets(1,100,0,0);
    //c2.anchor = GridBagConstraints.EAST;
    //c2.weightx = 1.5; //c1.weighty = 1.5;
    KLabel values=new KLabel("                                                                                         0     20     40     60     80     100");
   JPanel valuesPanel=new JPanel();
    valuesPanel.setPreferredSize(new Dimension(420, 25));
   // valuesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    //valuesPanel.setAlignmentX(61.0f);
    valuesPanel.add(BorderLayout.EAST,values);
    
    theFivePanels.add(valuesPanel, c1);
  //  c1.gridx = 0;
    c1.gridy = 1;
    theFivePanels.add(opennessPanel, c1);
    c1.gridy = 2;
    theFivePanels.add(conscientiousnessPanel, c1);
    c1.gridy = 3;
    theFivePanels.add(extraversionPanel, c1);
    c1.gridy = 4;
    theFivePanels.add(agreeablenessPanel, c1);
    c1.gridy = 5;
    theFivePanels.add(naturalReactionsPanel, c1);
    openness.setSize(200,23);
conscientiousness.setSize(200,23);
extraversion.setSize(200,23);
agreeableness.setSize(200,23);
naturalReactions.setSize(200,23);
    //***********************************************
   // c.gridy = 4;
    //theFourPanels.add(synthVoicePanel, c);



robotsChar = new RobotCharacteristicsPanel();

    

    multipanel = new JPanel(new BorderLayout());

    diafora = new JPanel(new BorderLayout());
    diafora.setBackground(new Color(200,200,200));
    diafora.add("North", labelPanel);
    diafora.add("Center", multipanel);

    
    openness.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                opennessStateChanged(evt);
            }
        });
        conscientiousness.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                conscientiousnessStateChanged(evt);
            }
        });
        extraversion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                extraversionStateChanged(evt);
            }
        });
        naturalReactions.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                naturalReactionsStateChanged(evt);
            }
        });
        
                 agreeableness.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                agreeablenessStateChanged(evt);
            }
        });
        
   // openness.addChangeListener(UsersPanel.this);
   //      conscientiousness.addChangeListener(UsersPanel.this);
    //    extraversion.addChangeListener(UsersPanel.this);
    //     agreeableness.addChangeListener(UsersPanel.this);
    // naturalReactions.addChangeListener(UsersPanel.this);
    
    maxFactsCombo.addActionListener(UsersPanel.this);
    factsPerPageCombo.addActionListener(UsersPanel.this);
    linksPerPageCombo.addActionListener(UsersPanel.this);
    synthVoiceCombo.addActionListener(UsersPanel.this);

    //Add the scroll panes in a split pane.
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setTopComponent(treeView);	//Add it Up to the splitPane
    splitPane.setBottomComponent(diafora);
    //splitPane.setDividerLocation(290); //XXX: ignored in some releases
                                         //of Swing. bug 4101306
    //workaround for bug 4101306:

    splitPane.setPreferredSize(new Dimension(725, 530));
    splitPane.setMinimumSize(new Dimension(725, 530));

    //Add the split pane to the panel.
    add("Center", splitPane);
    //clearTree();
	} // constructor


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == maxFactsCombo) 
		{
			QueryUsersHashtable.updateUserInfo(UsersPanel.last.toString(), 0, maxFactsCombo.getSelectedItem().toString());
			//Mpiro.needExportToEmulator=true;		//maria
		}
		else if (e.getSource() == factsPerPageCombo) 
		{
			QueryUsersHashtable.updateUserInfo(UsersPanel.last.toString(), 1, factsPerPageCombo.getSelectedItem().toString());
			//Mpiro.needExportToEmulator=true;		//maria
		}
		else if (e.getSource() == linksPerPageCombo) 
		{
			QueryUsersHashtable.updateUserInfo(UsersPanel.last.toString(), 2, linksPerPageCombo.getSelectedItem().toString());
			//Mpiro.needExportToEmulator=true;		//maria
		}
		else if (e.getSource() == synthVoiceCombo) 
		{
			QueryUsersHashtable.updateUserInfo(UsersPanel.last.toString(), 3, synthVoiceCombo.getSelectedItem().toString());
			//Mpiro.needExportToEmulator=true;		//maria
		}
	}



	public static void clearTree() 
	{
		users.removeAllChildren();
                robots.removeAllChildren();
                //robotCharacteristics.removeAllChildren();
		treeModel.reload();
		userstree.repaint();
	}


	/** The MouseListener */
	MouseListener mlisten = new MouseAdapter() 
	{
		public void mouseClicked (MouseEvent me)  
		{
			if (SwingUtilities.isRightMouseButton(me)) 
			{
				int x = me.getX();
				int y = me.getY();
				TreePath path = userstree.getPathForLocation(x,y);
				if (path != null) 
				{
					userstree.setSelectionPath(path);
					last = (DefaultMutableTreeNode)(userstree.getLastSelectedPathComponent());
					
					Object obj = last.getUserObject();
					String name = obj.toString();
					if (obj instanceof IconData) 
					{
						IconData id = (IconData)obj;
						Icon ii = id.getIcon();
						im = (ImageIcon)ii;
						TreePreviews.setUsersPanel(last.toString());
						if (im == ICON_USERS) 
						{ 
							popupusers.show(userstree, x, y); 
						}
                                                if (im == ICON_ROBOTS) 
						{ 
							popuprobots.show(userstree, x, y); 
						}
						if (im == ICON_USER)
						{
							a3.setEnabled(true);
							popupuser.show(userstree, x, y);
							if (users.getChildCount() == 1)
							{
								a3.setEnabled(false);
							}
						}
                                                if (im == ICON_ROBOT)
						{
							a6.setEnabled(true);
							popuprobot.show(userstree, x, y);
							if (robots.getChildCount() == 1)
							{
								a6.setEnabled(false);
							}
						}
					}
				}
			}
			else
			{
				//Left has been clicked
				int x = me.getX();
				int y = me.getY();
				TreePath path = userstree.getPathForLocation(x,y);
				if (path != null)
				{
					userstree.setSelectionPath(path);
					last = (DefaultMutableTreeNode)(userstree.getLastSelectedPathComponent());
					Object o = last.getUserObject();
					String name = o.toString();
					if (o instanceof IconData)
					{
						IconData id = (IconData)o;
						Icon ii = id.getIcon();
						im = (ImageIcon)ii;
						TreePreviews.setUsersPanel(last.toString());
					}
				}
			}
		}
	};


	/** The actions */
	Action a1 = new AbstractAction(addNewUser_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			KDialog addUserDialog = new KDialog(addUser_text, newName_text, "", new Vector(), false, "USER", true);
			userstree.repaint();
		}
	};

	Action a2 = new AbstractAction(rename_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			renameUsersNode();
			userstree.repaint();
		}
	};
   
	Action a3 = new AbstractAction(delete_action) //theofilos
	{
		public void actionPerformed(ActionEvent e)
		{
			Object[] optionButtons = {LangResources.getString(Mpiro.selectedLocale, "ok_button"),   
																LangResources.getString(Mpiro.selectedLocale, "cancel_button") };   
      
			int j = JOptionPane.showOptionDialog(userstree,
		         															LangResources.getString(Mpiro.selectedLocale, "confirm_deletion_dialog_2"),   
																					LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),   
																					JOptionPane.WARNING_MESSAGE,   
																					JOptionPane.OK_CANCEL_OPTION,   
																					null,   
																					optionButtons,   
																					optionButtons[1]);   
			
			if (j==0) 
			{
				removeCurrentNode();
				Mpiro.needExportToEmulator=true;		//maria
				userstree.repaint();
			} 
			else 
			{
				userstree.repaint();
			}
		}
	};
        
        Action a4 = new AbstractAction("add new profile")
	{
		public void actionPerformed(ActionEvent e)
		{
			KDialog addUserDialog = new KDialog("Add Profile", newName_text, "", new Vector(), false, "ROBOT", true);
			userstree.repaint();
		}
	};


        
	//Action a5 = new AbstractAction("rename robot")
	//{
	//	public void actionPerformed(ActionEvent e)
	///	{
	//		renameRobotsNode();
	//		userstree.repaint();
	//	}
	//};
   
	Action a6 = new AbstractAction("delete robot") //theofilos
	{
		public void actionPerformed(ActionEvent e)
		{
			Object[] optionButtons = {LangResources.getString(Mpiro.selectedLocale, "ok_button"),   
																LangResources.getString(Mpiro.selectedLocale, "cancel_button") };   
      
			int j = JOptionPane.showOptionDialog(userstree,
		         															LangResources.getString(Mpiro.selectedLocale, "confirm_deletion_dialog_2"),   
																					LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),   
																					JOptionPane.WARNING_MESSAGE,   
																					JOptionPane.OK_CANCEL_OPTION,   
																					null,   
																					optionButtons,   
																					optionButtons[1]);   
			
			if (j==0) 
			{
				removeCurrentNodeRobot();
				Mpiro.needExportToEmulator=true;		//maria
				userstree.repaint();
			} 
			else 
			{
				userstree.repaint();
			}
		}
	};


	class NumberComboBox extends KComboBox 
	{
		public NumberComboBox(int max) 
		{
			for (int i=1; i<=max; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number);
			}
			this.setSize(new Dimension(100, 20));
		}
                

	}



	class SynthVoiceCombo extends KComboBox 
	{
		public SynthVoiceCombo() 
		{
			addItem("male");
			addItem("female");
			addItem("child");
			this.setSize(new Dimension(100, 20));
		}
	}



	/** The method that ads the popups */
	public void addPopups() 
	{
		popupusers = new JPopupMenu();
		popupusers.add(a1);
		
		popupuser = new JPopupMenu();
		popupuser.add(a2);
		popupuser.add(a3);
		
		userstree.add(popupusers);
		userstree.add(popupuser);
                popuprobots = new JPopupMenu();
		popuprobots.add(a4);
		
		popuprobot = new JPopupMenu();
		popuprobot.add(a2);
		popuprobot.add(a6);
		
		userstree.add(popuprobots);
		userstree.add(popuprobot);
                
              
	}


	/** Defining the methods to clear the userstree,
	   to add, rename or delete nodes. */

	/** Remove the currently selected node. */
	public void removeCurrentNode() 
	{
	  TreePath currentSelection = userstree.getSelectionPath();
	  if (currentSelection != null) 
	  {
	    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
	    MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
	    if (parent != null) 
	    {
	      treeModel.removeNodeFromParent(currentNode);
	      QueryUsersHashtable.removeUser(currentNode.toString());
              QueryHashtable.removeRobotFromRobotCharVector(currentNode.toString());
	      QueryUsersHashtable.updateIndependentLexiconHashtable(currentNode.toString(), "", "REMOVE");
	      QueryUsersHashtable.updateAppropriatenessValuesInMicroplanningOfFields(currentNode.toString(), "", "REMOVE");
	      QueryUsersHashtable.removeUserInUserModelHashtable(currentNode.toString());
	      QueryUsersHashtable.removeUserInUserModelStoryHashtable(currentNode.toString());
	      TreePreviews.setLexPanel();
	      TreePreviews.generalDataBasePreview();
	      TreePreviews.generalUsersPreview();
	      clearDBPanels();
	      return;
			}
		}
		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}
        
        public void removeCurrentNodeRobot() 
	{
	  TreePath currentSelection = userstree.getSelectionPath();
	  if (currentSelection != null) 
	  {
	    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
	    MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
	    if (parent != null) 
	    {
	      treeModel.removeNodeFromParent(currentNode);
	      QueryUsersHashtable.removeRobot(currentNode.toString());
              QueryHashtable.removeRobotFromRobotCharVector(currentNode.toString());
	     // QueryUsersHashtable.updateIndependentLexiconHashtable(currentNode.toString(), "", "REMOVE");
	     // QueryUsersHashtable.updateAppropriatenessValuesInMicroplanningOfFields(currentNode.toString(), "", "REMOVE");
	      QueryUsersHashtable.removeRobotInRobotsModelHashtable(currentNode.toString());
              //QueryHashtable.removeRobotFromPropertiesHashtable(currentNode.toString());
	   //   QueryUsersHashtable.removeRobotInRobotModelStoryHashtable(currentNode.toString());
	    //  TreePreviews.setLexPanel();
	      TreePreviews.generalDataBasePreview();
	      TreePreviews.generalUsersPreview();
	      clearDBPanels();
	      return;
			}
		}
		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}


	/** Rename the currently selected node. */
	public void renameUsersNode()
	{
		DefaultMutableTreeNode nnn = (DefaultMutableTreeNode)(userstree.getLastSelectedPathComponent());
		KDialog renameDialog = new KDialog(renameNode_text, newName_text, "", new Vector(), false, "RENAME", true);
		String newname = UsersPanel.last.toString();
		TreePreviews.setLexPanel();
		reloadUsersTree();
		UsersPanel.userstree.expandRow(0);

		// set the selection in userstree
		int rowNo=1;
		Enumeration e = usersAndRobots.children();
		while (e.hasMoreElements())
		{
			String node = e.nextElement().toString();
			if (node.equalsIgnoreCase(last.toString()))
			{
				UsersPanel.userstree.setSelectionRow(rowNo);
				break;
			}
			rowNo++;
		}
		///////////////////////////////////////////////////
		UsersPanel.userstree.revalidate();
		UsersPanel.userstree.repaint();
		TreePreviews.setUsersPanel(UsersPanel.last.toString());
	}
        
       // public void renameRobotsNode()
	///{
	/*	DefaultMutableTreeNode nnn = (DefaultMutableTreeNode)(userstree.getLastSelectedPathComponent());
		KDialog renameDialog = new KDialog(renameNode_text, newName_text, "", new Vector(), false, "RENAME", true);
		String newname = UsersPanel.last.toString();
		//TreePreviews.setLexPanel();
		reloadUsersTree();
		UsersPanel.userstree.expandRow(0);

		// set the selection in userstree
		int rowNo=1;
		Enumeration e = usersAndRobots.children();
		while (e.hasMoreElements())
		{
			String node = e.nextElement().toString();
			if (node.equalsIgnoreCase(last.toString()))
			{
				UsersPanel.userstree.setSelectionRow(rowNo);
				break;
			}
			rowNo++;
		}
		///////////////////////////////////////////////////
		UsersPanel.userstree.revalidate();
		UsersPanel.userstree.repaint();
		TreePreviews.setUsersPanel(UsersPanel.last.toString());
	}*/


	public static boolean addUser(String nodeName)
	{//Hashtable test=(Hashtable)((Vector)QueryHashtable.mainDBHashtable.get("item")).elementAt(5);
		DefaultMutableTreeNode node = null;
		node = (DefaultMutableTreeNode)(userstree.getLastSelectedPathComponent());
		Object obj = node.getUserObject();
		if (obj == null) return false;

		DefaultMutableTreeNode h = new DefaultMutableTreeNode( new IconData(ICON_USER, nodeName));
		node.add(h);
		
		treeModel.insertNodeInto(h, node, (node.getChildCount()-1));
		userstree.scrollPathToVisible(new TreePath(h.getPath()));
		userstree.repaint();
                
		QueryUsersHashtable.createDefaultUser(h.toString());
		QueryUsersHashtable.updateIndependentLexiconHashtable(nodeName, "", "ADD");
		QueryUsersHashtable.updateAppropriatenessValuesInMicroplanningOfFields(nodeName, "", "ADD");
		QueryUsersHashtable.addUserInUserModelHashtable(nodeName);//==
		QueryUsersHashtable.addUserInUserModelStoryHashtable(nodeName);//==
                QueryHashtable.addUserInPropertiesHashtable(nodeName);
		TreePreviews.setLexPanel();
		TreePreviews.generalDataBasePreview();
		reloadUsersTree();
		userstree.expandRow(0);
		return true;
	}

        
        public static boolean addRobot(String nodeName)
	{//Hashtable test=(Hashtable)((Vector)QueryHashtable.mainDBHashtable.get("item")).elementAt(5);
		DefaultMutableTreeNode node = null;
		node = (DefaultMutableTreeNode)(userstree.getLastSelectedPathComponent());
		Object obj = node.getUserObject();
		if (obj == null) return false;

		DefaultMutableTreeNode h = new DefaultMutableTreeNode( new IconData(ICON_ROBOT, nodeName));
		node.add(h);
		
		treeModel.insertNodeInto(h, node, (node.getChildCount()-1));
		userstree.scrollPathToVisible(new TreePath(h.getPath()));
		userstree.repaint();
                
		QueryUsersHashtable.createDefaultRobot(h.toString());
		//QueryUsersHashtable.updateIndependentLexiconHashtable(nodeName, "", "ADD");
		//QueryUsersHashtable.updateAppropriatenessValuesInMicroplanningOfFields(nodeName, "", "ADD");
		QueryUsersHashtable.addRobotInRobotsModelHashtable(nodeName);//==
		//QueryUsersHashtable.addUserInUserModelStoryHashtable(nodeName);//==
                QueryHashtable.addRobotInPropertiesHashtable(nodeName);
		//TreePreviews.setLexPanel();
		//TreePreviews.generalDataBasePreview();
		reloadUsersTree();
		userstree.expandRow(0);
		return true;
	}

	public static void reloadUsersTree() 
	{
		clearTree();
		
		Vector usersVector = QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
		Enumeration enm = usersVector.elements();
		while (enm.hasMoreElements())
		{
			String user = enm.nextElement().toString();
			users.add(new DefaultMutableTreeNode(new IconData(UsersPanel.ICON_USER, user)));
		}
                Vector robotsVector = QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
		Enumeration enmRob = robotsVector.elements();
		while (enmRob.hasMoreElements())
		{
			String robot = enmRob.nextElement().toString();
			robots.add(new DefaultMutableTreeNode(new IconData(UsersPanel.ICON_ROBOT, robot)));
		}
             //   usersAndRobots.add(users);
              //  usersAndRobots.add(robots);
		UsersPanel.userstree.revalidate();
		UsersPanel.userstree.repaint();
	}
        
      //  public static void reloadRobotsTree() 
	//{
	//	clearTree();
	//	
	//	Vector usersVector = QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
	//	Enumeration enm = usersVector.elements();
	//	while (enm.hasMoreElements())
	//	{
	//		String user = enm.nextElement().toString();
	//		users.add(new DefaultMutableTreeNode(new IconData(UsersPanel.ICON_USER, user)));
	//	}
             //   usersAndRobots.add(users);
              //  usersAndRobots.add(robots);
	//	UsersPanel.userstree.revalidate();
	//	UsersPanel.userstree.repaint();
	//}


	static void clearDBPanels() 
	{
		if (DataBasePanel.last != null) 
		{
			String lll = DataBasePanel.last.toString();
			TreePreviews.setDataBaseTable(lll);
		} 
		else 
		{
			TreePreviews.setDataBaseTable("Data Base");
		}
	}
        
        private void opennessStateChanged(javax.swing.event.ChangeEvent evt) {
            			QueryUsersHashtable.updateRobotInfo(UsersPanel.last.toString(), 0, String.valueOf(openness.getValue()));

    }
        private void conscientiousnessStateChanged(javax.swing.event.ChangeEvent evt) {
QueryUsersHashtable.updateRobotInfo(UsersPanel.last.toString(), 1, String.valueOf(conscientiousness.getValue()));
    }
        private void extraversionStateChanged(javax.swing.event.ChangeEvent evt) {
QueryUsersHashtable.updateRobotInfo(UsersPanel.last.toString(), 2, String.valueOf(extraversion.getValue()));
    }
        private void naturalReactionsStateChanged(javax.swing.event.ChangeEvent evt) {
QueryUsersHashtable.updateRobotInfo(UsersPanel.last.toString(), 4, String.valueOf(naturalReactions.getValue()));
    }
     //   private void openessStateChanged(javax.swing.event.ChangeEvent evt) {
//QueryUsersHashtable.updateRobotInfo(UsersPanel.last.toString(), 0, String.valueOf(openness.getValue()));
 //   }
        private void agreeablenessStateChanged(javax.swing.event.ChangeEvent evt) {
QueryUsersHashtable.updateRobotInfo(UsersPanel.last.toString(), 3, String.valueOf(agreeableness.getValue()));
    }
        
       public static void setPersonalityValues(Integer at1, Integer at2, Integer at3, String at4){
        maxFactsCombo.setSelectedItem(at1);
	factsPerPageCombo.setSelectedItem(at2);
        linksPerPageCombo.setSelectedItem(at3);
	synthVoiceCombo.setSelectedItem(at4);
       }

}	//class UsersPanel