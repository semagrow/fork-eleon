//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;


import gr.demokritos.iit.eleon.ui.KDialog;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.Position; //theofilos

public class LexiconPanel extends JPanel 
{
	public static ImageIcon ICON_NOUN = new ImageIcon(Mpiro.obj.image_noun);
	public static ImageIcon ICON_VERB = new ImageIcon(Mpiro.obj.image_verb);
	public static ImageIcon ICON_L = new ImageIcon(Mpiro.obj.image_l);
	public static ImageIcon ICON_N = new ImageIcon(Mpiro.obj.image_n);
	public static ImageIcon ICON_V = new ImageIcon(Mpiro.obj.image_v);
	public static ImageIcon ICON_EN = new ImageIcon(Mpiro.obj.image_enLeaf);
	public static ImageIcon ICON_IT = new ImageIcon(Mpiro.obj.image_itLeaf);
	public static ImageIcon ICON_GR = new ImageIcon(Mpiro.obj.image_grLeaf);
	public static ImageIcon im;
	public static DefaultMutableTreeNode n;
	public static ImageIcon parentim;
	public static DefaultMutableTreeNode parent;
	
	static JPanel multipanel;
	public static JTree lexicontree;
	JPanel diafora;
	static DefaultTreeModel treeModel;
	static DefaultMutableTreeNode top;

	protected IconCellRenderer renderer;
	protected IconCellEditor editor;
	protected JPopupMenu popupnoun;
	protected JPopupMenu popupverb;
	protected JPopupMenu popupn;
	protected JPopupMenu popupv;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static DefaultMutableTreeNode nnode = null;
	public static DefaultMutableTreeNode vnode = null;
	public TreePath selpath;
	
	private String addNewNoun_action = LangResources.getString(Mpiro.selectedLocale, "addNewNoun_action");
	private String addNewVerb_action = LangResources.getString(Mpiro.selectedLocale, "addNewVerb_action");
	private String rename_action = LangResources.getString(Mpiro.selectedLocale, "rename_action");
	private String delete_action = LangResources.getString(Mpiro.selectedLocale, "delete_action");
	private String addNoun_text = LangResources.getString(Mpiro.selectedLocale, "addNoun_text");
	private String addVerb_text = LangResources.getString(Mpiro.selectedLocale, "addVerb_text");
	private String renameNode_text = LangResources.getString(Mpiro.selectedLocale, "renameNode_text");
	private String newName_text = LangResources.getString(Mpiro.selectedLocale, "newName_text");

  /**
   * <p>Title: DataBasePanel</p>
   * <p>Description: The whole panel for LEXICON tab</p>
   * <p>Copyright: Copyright (c) 2002</p>
   * <p>Company: NCSR "Demokritos"</p>
   * @author Kostas Stamatakis, Dimitris Spiliotopoulos
   * @version 1.0
   */
	public LexiconPanel() 
	{
		setLayout(new BorderLayout());
		
		//Create tree and ScrollPane and put the first into the second.
		top = new DefaultMutableTreeNode(new IconData(ICON_L, "Domain-dependent lexicon"));
		nnode = new DefaultMutableTreeNode(new IconData(ICON_NOUN, "Nouns"));
		top.add(nnode);
		vnode = new DefaultMutableTreeNode(new IconData(ICON_VERB, "Verbs"));
		top.add(vnode);
		treeModel = new DefaultTreeModel(top);
		lexicontree = new JTree(treeModel);
		
		lexicontree.getSelectionModel().setSelectionMode(
		TreeSelectionModel.SINGLE_TREE_SELECTION);
		lexicontree.setShowsRootHandles(true);
		lexicontree.setEditable(false);
		lexicontree.putClientProperty("JTree.lineStyle", "Angled");
		
		renderer = new IconCellRenderer();
		lexicontree.setCellRenderer(renderer);
		editor = new IconCellEditor(lexicontree);
		lexicontree.setCellEditor(editor);
		lexicontree.setInvokesStopCellEditing(true);

		addPopups();
		lexicontree.addMouseListener(mlisten);
		
		JScrollPane treeView = new JScrollPane(lexicontree);
		treeView.setPreferredSize(new Dimension(300,500));
		treeView.setMinimumSize(new Dimension(290,500));
		
		multipanel = new JPanel(new BorderLayout());
		
		diafora = new JPanel(new BorderLayout());
		diafora.setBackground(new Color(200,200,200));
		diafora.add("Center", multipanel);
		
		//Add the scroll panes in a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);  //Add it Up to the splitPane
		splitPane.setBottomComponent(diafora);
		//splitPane.setDividerLocation(290); //XXX: ignored in some releases of Swing. bug 4101306
		//workaround for bug 4101306:

		splitPane.setPreferredSize(new Dimension(725, 530));
		splitPane.setMinimumSize(new Dimension(725, 530));
		
		//Add the split pane to the panel.
		add("Center", splitPane);
	}

	public static void clearTree() 
	{
		nnode.removeAllChildren();
		vnode.removeAllChildren();
		treeModel.reload();
		lexicontree.repaint();
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
				TreePath path = lexicontree.getPathForLocation(x,y);
				if (path != null)
				{
					lexicontree.setSelectionPath(path);
					selpath = path;
					n = (DefaultMutableTreeNode)(lexicontree.getLastSelectedPathComponent());
					if (n != top)
					{
						parent = (DefaultMutableTreeNode)(n.getParent());
						Object parentobj = parent.getUserObject();
						String parentname = parentobj.toString();
						if (parentobj instanceof IconData)
						{
							IconData id = (IconData)parentobj;
							Icon ii = id.getIcon();
							parentim = (ImageIcon)ii;
						}
					}
          Object obj = n.getUserObject();
          String name = obj.toString();
          if (obj instanceof IconData)
          {
						IconData id = (IconData)obj;
						Icon ii = id.getIcon();
						im = (ImageIcon)ii;
						TreePreviews.setLexPanel();
						if (im == ICON_NOUN) { popupnoun.show(lexicontree, x, y); }
						if (im == ICON_VERB) { popupverb.show(lexicontree, x, y); }
						if (im == ICON_N) { popupn.show(lexicontree, x, y); }
						if (im == ICON_V) { popupv.show(lexicontree, x, y); }
					}
				}
			}
      else
      {
	      //Left has been clicked
	      int x = me.getX();
	      int y = me.getY();
	      TreePath path = lexicontree.getPathForLocation(x,y);
	      if (path != null)
	      {
	        lexicontree.setSelectionPath(path);
	        selpath = path;
	        n = (DefaultMutableTreeNode)(lexicontree.getLastSelectedPathComponent());
	        if (n != top)
	        {
						parent = (DefaultMutableTreeNode)(n.getParent());
						Object parentobj = parent.getUserObject();
						String parentname = parentobj.toString();
						if (parentobj instanceof IconData)
						{
							IconData id = (IconData)parentobj;
							Icon ii = id.getIcon();
							parentim = (ImageIcon)ii;
						}
					}
					Object o = n.getUserObject();
					String name = o.toString();
					if (o instanceof IconData)
					{
						IconData id = (IconData)o;
						Icon ii = id.getIcon();
						im = (ImageIcon)ii;
						TreePreviews.setLexPanel();
					}
				}
			}
		}
	};


	/** The actions */
	Action a1 = new AbstractAction(addNewNoun_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			KDialog addNounDialog = new KDialog(addNoun_text, newName_text, "", new Vector(), false, "LEXICON-NOUN", true);
			lexicontree.repaint();
		}
	};
	
	Action a2 = new AbstractAction(addNewVerb_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			KDialog addVerbDialog = new KDialog(addVerb_text, newName_text, "", new Vector(), false, "LEXICON-VERB", true);
			lexicontree.repaint();
		}
	};
	
	Action a3n = new AbstractAction(rename_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			renameNounNode();
			lexicontree.repaint();
		}
	};
   
	Action a3v = new AbstractAction(rename_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			renameVerbNode();
			Mpiro.needExportToExprimo = true;		//maria
			lexicontree.repaint();
		}
	};
   
	Action a4 = new AbstractAction(delete_action)
	{
		public void actionPerformed(ActionEvent e)
		{
			Object[] optionButtons = {LangResources.getString(Mpiro.selectedLocale, "ok_button"),   //theofilos  10-02-2004
													LangResources.getString(Mpiro.selectedLocale, "cancel_button") };   //theofilos  10-02-2004
			
			int j = JOptionPane.showOptionDialog(lexicontree,   //theofilos
										LangResources.getString(Mpiro.selectedLocale, "confirm_deletion_dialog_2"),   //theofilos
										LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),   //theofilos
										JOptionPane.WARNING_MESSAGE,
										JOptionPane.OK_CANCEL_OPTION,
										new ImageIcon("img/mpirotext.gif"),
										optionButtons,   //theofilos   10-02-2004
										optionButtons[1]);   //theofilos  10-02-2004

			if (j==0) 
			{
				removeCurrentNode();
				Mpiro.needExportToEmulator = true;		//maria
				Mpiro.needExportToExprimo = true;		//maria
				lexicontree.repaint();
			} 
			else {lexicontree.repaint();}
		}
	};
   
	/** Removed from version 1.1
	Action a5 = new AbstractAction("Delete entire hierarchy")
	{
		public void actionPerformed(ActionEvent e)
		{
			int k = JOptionPane.showOptionDialog(lexicontree,
									"You are about to DELETE the entire hierarchy!",
									"Warning",
									JOptionPane.WARNING_MESSAGE,
									JOptionPane.OK_CANCEL_OPTION,
									new ImageIcon("img/mpirotext.gif"),
									null,
									JOptionPane.UNINITIALIZED_VALUE);
			if (k==0) 
			{
				clear();
				lexicontree.repaint();
			} 
			else {lexicontree.repaint();}
		}
	}; */


	/** The method that ads the popups */
	public void addPopups() 
	{
		popupnoun = new JPopupMenu();
		popupnoun.add(a1);
		
		popupverb = new JPopupMenu();
		popupverb.add(a2);
		
		popupn = new JPopupMenu();
		popupn.add(a3n);
		popupn.add(a4);
		
		popupv = new JPopupMenu();
		popupv.add(a3v);
		popupv.add(a4);
		
		lexicontree.add(popupnoun);
		lexicontree.add(popupverb);
		lexicontree.add(popupn);
		lexicontree.add(popupv);
	}


	/** Defining the methods to clear the lexicontree,
	   to add, rename or delete nodes. */


	/** Remove the currently selected node. */
	public void removeCurrentNode() 
	{
		TreePath currentSelection = lexicontree.getSelectionPath();
		if (currentSelection != null) 
		{
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
							(currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
			if (parent != null) 
			{
				treeModel.removeNodeFromParent(currentNode);
				QueryLexiconHashtable.removeLexiconEntry(currentNode.toString());
				TreePreviews.generalDataBasePreview();
				LexiconPanel.lexicontree.setSelectionRow(0);
				LexiconPanel.multipanel.removeAll();
				LexiconPanel.multipanel.revalidate();
				LexiconPanel.multipanel.repaint();
				clearDBPanels();
				return;
			}
		}
		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}

	/** Rename the currently selected node. */
	public void renameNounNode()
	{
		DefaultMutableTreeNode nnn = (DefaultMutableTreeNode)(lexicontree.getLastSelectedPathComponent());
		KDialog renameDialog = new KDialog(renameNode_text, newName_text, "", new Vector(), false, "RENAME", true);
		TreePreviews.setLexPanel();
	}

	public void renameVerbNode()
	{
		DefaultMutableTreeNode nnn = (DefaultMutableTreeNode)(lexicontree.getLastSelectedPathComponent());
		KDialog renameDialog = new KDialog(renameNode_text, newName_text, "", new Vector(), false, "RENAME", true);
		TreePreviews.generalDataBasePreview();
		TreePreviews.setLexPanel();
	}

	public static boolean addNoun(String nodeName)
	{
		DefaultMutableTreeNode node = null;
		node = (DefaultMutableTreeNode)(lexicontree.getLastSelectedPathComponent());
		Object obj = node.getUserObject();
		if (obj == null) return false;

		DefaultMutableTreeNode h = new DefaultMutableTreeNode( new IconData(ICON_N, nodeName));
		node.add(h);
		h.add(new DefaultMutableTreeNode( new IconData(ICON_EN, "English")));
		h.add(new DefaultMutableTreeNode( new IconData(ICON_IT, "Italian")));
		h.add(new DefaultMutableTreeNode( new IconData(ICON_GR, "Greek")));
		treeModel.insertNodeInto(h, node, (node.getChildCount()-1));
		lexicontree.scrollPathToVisible(new TreePath(h.getPath()));
		lexicontree.repaint();
		QueryLexiconHashtable.createDefaultLexiconNoun(h.toString());
		TreePreviews.generalDataBasePreview();
		return true;
	}

	public static boolean addVerb(String nodeName)
	{
		DefaultMutableTreeNode node = null;
		TreePath path = lexicontree.getSelectionPath();
		node = (DefaultMutableTreeNode)(path.getLastPathComponent());
		Object obj = node.getUserObject();
		if (obj == null) return false;

		DefaultMutableTreeNode h = new DefaultMutableTreeNode( new IconData(ICON_V, nodeName));
		node.add(h);
		h.add(new DefaultMutableTreeNode( new IconData(ICON_EN, "English")));
		h.add(new DefaultMutableTreeNode( new IconData(ICON_IT, "Italian")));
		h.add(new DefaultMutableTreeNode( new IconData(ICON_GR, "Greek")));
		treeModel.insertNodeInto(h, node, (node.getChildCount()-1));
		lexicontree.scrollPathToVisible(new TreePath(h.getPath()));
		lexicontree.repaint();
		QueryLexiconHashtable.createDefaultLexiconVerb(h.toString());
		TreePreviews.generalDataBasePreview();
		return true;
	}

	public static void reloadLexiconTree() 
	{
		clearTree();
		DefaultMutableTreeNode nounTop = (DefaultMutableTreeNode)top.getChildAt(0);
		DefaultMutableTreeNode verbTop = (DefaultMutableTreeNode)top.getChildAt(1);
		
		Vector nounVector = QueryLexiconHashtable.getNounsVectorFromMainLexiconHashtable();
		Enumeration enm = nounVector.elements();
		while (enm.hasMoreElements()) 
		{
			String noun = enm.nextElement().toString();
			nounTop.add(new DefaultMutableTreeNode (new IconData(LexiconPanel.ICON_N, noun)));
		}
		for (int i = 0; i < nounTop.getChildCount(); i++) 
		{
			DefaultMutableTreeNode currentNounNode = (DefaultMutableTreeNode)nounTop.getChildAt(i);
			DefaultMutableTreeNode enLeaf = new DefaultMutableTreeNode(new IconData(ICON_EN, "English"));
			DefaultMutableTreeNode itLeaf = new DefaultMutableTreeNode(new IconData(ICON_IT, "Italian"));
			DefaultMutableTreeNode grLeaf = new DefaultMutableTreeNode(new IconData(ICON_GR, "Greek"));
			currentNounNode.add(enLeaf);
			currentNounNode.add(itLeaf);
			currentNounNode.add(grLeaf);
		}

		Vector verbVector = QueryLexiconHashtable.getVerbsVectorFromMainLexiconHashtable();
		Enumeration enme = verbVector.elements();
		while (enme.hasMoreElements()) 
		{
			String verb = enme.nextElement().toString();
			verbTop.add(new DefaultMutableTreeNode (new IconData(LexiconPanel.ICON_V, verb)));
		}
		for (int i = 0; i < verbTop.getChildCount(); i++) 
		{
			DefaultMutableTreeNode currentVerbNode = (DefaultMutableTreeNode)verbTop.getChildAt(i);
			DefaultMutableTreeNode enLeaf = new DefaultMutableTreeNode(new IconData(ICON_EN, "English"));
			DefaultMutableTreeNode itLeaf = new DefaultMutableTreeNode(new IconData(ICON_IT, "Italian"));
			DefaultMutableTreeNode grLeaf = new DefaultMutableTreeNode(new IconData(ICON_GR, "Greek"));
			currentVerbNode.add(enLeaf);
			currentVerbNode.add(itLeaf);
			currentVerbNode.add(grLeaf);
		}
    LexiconPanel.lexicontree.revalidate();
    LexiconPanel.lexicontree.repaint();
	}


	public static void clearDBPanels() 
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

}	//class