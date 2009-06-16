//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.IconCellEditor;
import gr.demokritos.iit.eleon.authoring.IconCellRenderer;
import gr.demokritos.iit.eleon.authoring.IconData;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.TreePreviews;
import gr.demokritos.iit.eleon.authoring.ViewPanel;

import java.net.URL;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;


public class StoriesPanel extends JPanel 
{
	private URL storyURL;
	
	public static JLabel label01;
	public static JPanel multiPanel;
	static JTree storiesTree;
	static DefaultTreeModel storiesModel;
	static StoriesTreeListener storiesTreeListener;
	
	public static JPanel previewPanel;
	static JScrollPane htmlScroll;
	//static JEditorPane htmlPane;
	static ViewPanel htmlPane;
	public static JScrollPane storyScroll;
	public static JTextArea storyText;
	
	JPanel diafora;
	static TextAreaListener tal;
	static DefaultMutableTreeNode topStories;
	
	public static ImageIcon im;

	public TreePath selpath;
	static String name = "";
	public static DefaultMutableTreeNode last;
	
	
	public StoriesPanel() 
	{
		String storyPreviewArea_text = LangResources.getString(Mpiro.selectedLocale, "storyPreviewArea_text");
		
		setLayout(new BorderLayout());
		
		/* The Tree and the ScrollPane. Then, put the first into the second */
		topStories = new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_TOP, "Stories"));
		storiesModel = new DefaultTreeModel(topStories);
		DataBasePanel.createNodes(topStories, 2);
		storiesTree = new JTree(storiesModel);
		
		storiesTreeListener = new StoriesTreeListener();
		storiesTree.addMouseListener(storiesTreeListener);
		
		storiesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		storiesTree.setShowsRootHandles(true);
		storiesTree.setEditable(false);
		storiesTree.putClientProperty("JTree.lineStyle", "Angled");
		
		IconCellRenderer cellrenderer = new IconCellRenderer();
		storiesTree.setCellRenderer(cellrenderer);
		IconCellEditor celleditor = new IconCellEditor(storiesTree);
		storiesTree.setCellEditor(celleditor);
		storiesTree.setInvokesStopCellEditing(true);
		
		JScrollPane treeView = new JScrollPane(storiesTree);
		treeView.setPreferredSize(new Dimension(300,500));
		treeView.setMinimumSize(new Dimension(290,500));
		
		htmlPane = new ViewPanel(DataBasePanel.ICON_MPIRO, storyPreviewArea_text, 14);
		
		//htmlPane = new JEditorPane();
		//htmlPane.setEditable(false);
		//initStory();
		htmlScroll = new JScrollPane(htmlPane);

    previewPanel = new JPanel(new BorderLayout());
    previewPanel.add("Center", htmlScroll);

    /** The story viewing pane */
    storyText = new JTextArea();
    //storyText.setEditable(true);
    storyText.setLineWrap(true);
    storyText.setWrapStyleWord(true);
    storyScroll = new JScrollPane(storyText);

    tal = new TextAreaListener();
    storyText.addFocusListener(tal);

    /** The label */
    label01 = new JLabel(LangResources.getString(Mpiro.selectedLocale, "clickTheHierarchyToYourLeft_text"));
    label01.setFont(new Font(Mpiro.selectedFont,Font.BOLD,13));
    label01.setForeground(Color.black);
    label01.setPreferredSize(new Dimension(380, 23));

		/** A multiPanel containing nothing or the StoriesTable */
		multiPanel = new JPanel(new BorderLayout());
		
		/** The panel of the up-right side of the SplitPanes */
		diafora = new JPanel(new BorderLayout());
		diafora.add("North", label01);
		diafora.add("Center", multiPanel);
		
		// Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JSplitPane spl = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		spl.setTopComponent(diafora);
		spl.setBottomComponent(previewPanel);
		splitPane.setBottomComponent(spl);
		
		spl.setDividerLocation(280);
		//splitPane.setDividerLocation(290); //XXX: ignored in some releases
		//of Swing. bug 4101306
		//workaround for bug 4101306:

    spl.setPreferredSize(new Dimension(280, 530));
    splitPane.setPreferredSize(new Dimension(725, 530));
    spl.setMinimumSize(new Dimension(280, 530));
    splitPane.setMinimumSize(new Dimension(725, 530));

    //Add the split pane to the panel.
    add("Center", splitPane);
	}

	class StoriesTreeListener extends MouseAdapter implements MouseListener
	{
		//private TreePath selpath;
		//private String name = "";
		//public DefaultMutableTreeNode last;
		//private ImageIcon im;
		
		public StoriesTreeListener(){ }  // constructor

		public void mouseClicked(MouseEvent me)  
		{
			if (SwingUtilities.isRightMouseButton(me)) 
			{
				int x = me.getX();
				int y = me.getY();
				TreePath path = storiesTree.getPathForLocation(x,y);
				if (path != null) 
				{
					storiesTree.setSelectionPath(path);
					selpath = path;
					last = (DefaultMutableTreeNode)(path.getLastPathComponent());
					Object obj = (Object)(last.getUserObject());
					name = obj.toString();
					IconData id = (IconData)obj;
					Icon ii = id.getIcon();
					im = (ImageIcon)ii;
					TreePreviews.generalStoryPreview();
					TreePreviews.setStoriesTable(name);
					if (im == DataBasePanel.ICON_TOP)
					{}
					if (im == DataBasePanel.ICON_TOP_A)
					{
						//puptopA.show(storiesTree, x, y);
					}
					if (im == DataBasePanel.ICON_BASIC)
					{
						//pupbasic.show(storiesTree, x, y);
					}
					if (im == DataBasePanel.ICON_BOOK)
					{
						//pupsub.show(storiesTree, x, y);
					}
					if (im == DataBasePanel.ICON_GEI || im == DataBasePanel.ICON_GENERIC)
					{
						//pupentity.show(storiesTree, x, y);
					}
				}
			}
			else 
			{
				//Left has been clicked
				int x = me.getX();
				int y = me.getY();
				TreePath path = storiesTree.getPathForLocation(x,y);
				if (path != null) 
				{
					storiesTree.setSelectionPath(path);
					selpath = path;
					last = (DefaultMutableTreeNode)(path.getLastPathComponent());
					Object obj = (Object)(last.getUserObject());
					name = obj.toString();
					IconData id = (IconData)obj;
					Icon ii = id.getIcon();
					im = (ImageIcon)ii;
					TreePreviews.generalStoryPreview();
					TreePreviews.setStoriesTable(name);
				}
			}
		}
	} // class StoriesTreeListener


	public static void clearTree() 
	{
		DefaultMutableTreeNode firstChild = (DefaultMutableTreeNode)topStories.getFirstChild();
		firstChild.removeAllChildren();
		storiesModel.reload();
		storiesTree.repaint();
	}

	/* The method that initializes the previewScroll
	 * setting the file StoryInfo.html
	 */
	private void initStory() 
	{
		String s = null;
		try 
		{
			s = "file:"
					+ System.getProperty("user.dir")
					+ System.getProperty("file.separator")
					+ "StoryInfo.html";
			storyURL = new URL(s);
			//displayURL(storyURL);
		} 
		catch (Exception e) 
		{
			System.err.println("Couldn't create story URL: " + s);
		}
	}
	
	/*
	public static void displayURL(URL url) 
	{
	  try 
	  {
	  	htmlPane.setPage(url);
	  } 
	  catch (IOException e) 
	  {
	  	System.err.println("Attempted to read a bad URL: " + url);
	  }
	}
	*/

	/**
	 *  Reloads the Stories Tree
	 *  It is used when loading a new domain
	 */
	public static void reloadStoriesTree() 
	{
		// Clear the tree
		clearTree();

		// Set the *first* node
		DefaultMutableTreeNode first = (DefaultMutableTreeNode)topStories.getChildAt(0);
		
		Vector tempVector = new Vector();
		tempVector.addElement("Basic-entity-types");
		Vector childrenVector = new Vector();
		
		// Create 2 hashtables with keys=entity-type/entity, values=parent
		Hashtable allEntityTypes = new Hashtable();
		Hashtable allEntities = new Hashtable();
		Hashtable allGeneric = new Hashtable();
		allEntityTypes = (Hashtable)QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		allEntities = (Hashtable)QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
		allGeneric = (Hashtable)QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Generic");
		
		// Remove the 2 entries that are not needed
		allEntityTypes.remove("Data Base");
		allEntityTypes.remove("Basic-entity-types");
		
		String nodename = new String("Basic-entity-types");
		int childrenNumber = 0;
		
		// Adding the basic entity types
		childrenVector = QueryHashtable.getChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
		addChildren(first, childrenVector);
		for (Enumeration k = childrenVector.elements(); k.hasMoreElements();) 
		{
			String entitytype = k.nextElement().toString();
			allEntityTypes.remove(entitytype);
		}

		// Adding the entity types
		while (allEntityTypes.size() != 0) 
		{
			Enumeration enu1 = first.preorderEnumeration();
			while (enu1.hasMoreElements()) 
			{
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)enu1.nextElement();
				if (!currentNode.isNodeSibling(first)) 
				{
					String node = currentNode.toString();
					if (allEntityTypes.containsValue(node))
					{
						childrenVector = QueryHashtable.getChildrenVectorFromMainDBHashtable(node, "Entity type");
						Enumeration enu2 = childrenVector.elements();
						while (enu2.hasMoreElements()) 
						{
							String child = enu2.nextElement().toString();
						  currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_BOOK, child)));
						  allEntityTypes.remove(child);
						}
						storiesTree.revalidate();
						storiesTree.repaint();
					}
				}
		  }
	  }

		// Adding the entities
		childrenVector.clear();
		childrenNumber = 0;
		
		while (allEntities.size() != 0) 
		{
			Enumeration enu1 = first.preorderEnumeration();
			while (enu1.hasMoreElements()) 
			{
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)enu1.nextElement();
				if (!currentNode.isNodeSibling(first)) 
				{
					String node = currentNode.toString();
					if (allEntities.containsValue(node))
					{
	          childrenVector = QueryHashtable.getChildrenVectorFromMainDBHashtable(node, "Entity");
	          Enumeration enu2 = childrenVector.elements();
	          while (enu2.hasMoreElements()) 
	          {
              String child = enu2.nextElement().toString();
              currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_GEI, child)));
              allEntities.remove(child);
	          }
	          storiesTree.revalidate();
	          storiesTree.repaint();
					}
				}
		  }
	  }

		// Adding the generic entities
		childrenVector.clear();
		childrenNumber = 0;
		
		while (allGeneric.size() != 0) 
		{
			Enumeration enu1 = first.preorderEnumeration();
			while (enu1.hasMoreElements())
			{
	      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)enu1.nextElement();
	      if (!currentNode.isNodeSibling(first))
	      {
          String node = currentNode.toString();
          if (allGeneric.containsValue(node))
          {
	          childrenVector = QueryHashtable.getChildrenVectorFromMainDBHashtable(node, "Generic");
	          Enumeration enu2 = childrenVector.elements();
	          while (enu2.hasMoreElements())
	          {
              String child = enu2.nextElement().toString();
              currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_GENERIC, child)));
              allGeneric.remove(child);
	          }
	          storiesTree.revalidate();
	          storiesTree.repaint();
					}
				}
			}
		}
		storiesTree.revalidate();
		storiesTree.repaint();
	} //reloadDBTree()


	public static void addChildren(DefaultMutableTreeNode currentNode, Vector childrenVector) 
	{
		Enumeration enumer = childrenVector.elements();
		while (enumer.hasMoreElements()) 
		{
			String child = enumer.nextElement().toString();
			currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_BASIC, child)));
		}
	}

}//StoriesPanel