package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DialogClassesToImport
	extends JDialog {
	static ImageIcon ICON_OWLCLASSCHECKED = new ImageIcon(Mpiro.obj.image_owlClassChecked);
	static ImageIcon ICON_OWLCLASSUNCHECKED = new ImageIcon(Mpiro.obj.image_owlClassUnchecked);

	public static ImageIcon im;
	public static boolean modalResult = false;
	JPanel panel1 = new JPanel();
	static DefaultMutableTreeNode root = new DefaultMutableTreeNode(new IconOwlData(ICON_OWLCLASSCHECKED, "Classes", null));
	static DefaultTreeModel treeModel = new DefaultTreeModel(root);
	ClassesTreeListener classesTreeListener = new ClassesTreeListener();
	IconCellRenderer cellrenderer = new IconCellRenderer();
	JTree jTreeClasses = new JTree(treeModel);
	IconCellEditor celleditor = new IconCellEditor(jTreeClasses);
	JLabel jLabel1 = new JLabel();
	JScrollPane jScrollPane1 = new JScrollPane();
	BorderLayout borderLayout1 = new BorderLayout();
	TreePath selpath;
	String name = "";
	DefaultMutableTreeNode last;
	JPanel jPanel1 = new JPanel();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();

	JPopupMenu jPopupMenuTree = new JPopupMenu();
	JMenuItem jMenuItemSelectClassAndSubclasses = new JMenuItem();
	JMenuItem jMenuItemUnSelectClassAndSubclasses = new JMenuItem();

	public static void clearTreeModel(){
		root = new DefaultMutableTreeNode(new IconOwlData(ICON_OWLCLASSCHECKED, "Classes", null));
		treeModel = new DefaultTreeModel(root);
	}

	public DialogClassesToImport(Frame frame, String title, boolean modal) {
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
			this.setVisible(true);

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogClassesToImport() {
		this(null, "", false);
	}

	private void jbInit() throws Exception {
		panel1.setLayout(borderLayout1);
		jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
		jLabel1.setOpaque(false);
		jLabel1.setPreferredSize(new Dimension(600, 15));
		jLabel1.setText("...");
		if (Mpiro.selectedLocale == Mpiro.enLocale){
			this.setTitle("OWL classes");
            jLabel1.setText("Select the classes to import.");
            jMenuItemSelectClassAndSubclasses.setText("Select this class and all its sub-classes");
            jMenuItemUnSelectClassAndSubclasses.setText("Deselect this class and all its sub-classes");
		}
		else if (Mpiro.selectedLocale == Mpiro.grLocale){
            this.setTitle("������ OWL");
            jLabel1.setText("�������� ��� ������ ��� ������ �� ����������.");
            jMenuItemSelectClassAndSubclasses.setText("������� ����� ��� ����� ��� ���� ��� ��������� ���");
            jMenuItemUnSelectClassAndSubclasses.setText("�������� �������� ����� ��� ����� ��� ���� ��� ��������� ���");
		}
		else if (Mpiro.selectedLocale == Mpiro.itLocale){
			this.setTitle("OWL classi");
            jLabel1.setText("Selezionare le classi da importare");
            jMenuItemSelectClassAndSubclasses.setText("Seleziona questa classe e tutte le sue sottoclassi");
            jMenuItemUnSelectClassAndSubclasses.setText("Deseleziona questa classe e tutte le sue sottoclassi");
		}
		jTreeClasses.setExpandsSelectedPaths(true);
		jTreeClasses.setShowsRootHandles(true);
		jTreeClasses.setCellRenderer(cellrenderer);
		jTreeClasses.setCellEditor(celleditor);
		jTreeClasses.setInvokesStopCellEditing(true);
		jTreeClasses.setRootVisible(false);
		jTreeClasses.addMouseListener(classesTreeListener);

		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		jScrollPane1.setPreferredSize(new Dimension(600, 500));
		panel1.setPreferredSize(new Dimension(600, 15));
		jButton1.setText(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
		jButton1.addActionListener(new DialogClassesToImport_jButton1_actionAdapter(this));
		jButton2.setText(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
		jButton2.addActionListener(new DialogClassesToImport_jButton2_actionAdapter(this));
		jMenuItemSelectClassAndSubclasses.addActionListener(new DialogClassesToImport_jMenuItemSelectClassAndSubclasses_actionAdapter(this));
		jMenuItemUnSelectClassAndSubclasses.addActionListener(new DialogClassesToImport_jMenuItemUnSelectClassAndSubclasses_actionAdapter(this));
		getContentPane().add(panel1, BorderLayout.NORTH);
		panel1.add(jLabel1, BorderLayout.NORTH);
		this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
		this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
		jPanel1.add(jButton1, null);
		jPanel1.add(jButton2, null);
		jScrollPane1.getViewport().add(jTreeClasses, null);

		jTreeClasses.add(jPopupMenuTree);
		jPopupMenuTree.add(jMenuItemSelectClassAndSubclasses);
		jPopupMenuTree.add(jMenuItemUnSelectClassAndSubclasses);
		expandTree();
	}

	class ClassesTreeListener
		extends MouseAdapter
		implements MouseListener {
		public ClassesTreeListener() {} // constructor

		public void mouseClicked(MouseEvent me) {
			if (SwingUtilities.isLeftMouseButton(me)) {
				int x = me.getX();
				int y = me.getY();
				TreePath path = jTreeClasses.getPathForLocation(x, y);
				if (path != null) {
					jTreeClasses.setSelectionPath(path);
					selpath = path;
					last = (DefaultMutableTreeNode) (path.getLastPathComponent());
					Object obj = (Object) (last.getUserObject());
					name = obj.toString();
					IconOwlData id = (IconOwlData) obj;
					Icon ii = id.getIcon();
					im = (ImageIcon) ii;
					//uncheck class and all its children
					if (im == ICON_OWLCLASSCHECKED) {
						id.m_icon = ICON_OWLCLASSUNCHECKED;
						uncheckAllChildren(last);
					}
					//check class and its parents to the root
					if (im == ICON_OWLCLASSUNCHECKED) {
						id.m_icon = ICON_OWLCLASSCHECKED;
						checkAllParents(last);
					}
				}
			}
			else if (SwingUtilities.isRightMouseButton(me)) { //Left has been clicked
				//microPlanPanel.setVisible(false);//theofilos
				int x = me.getX();
				int y = me.getY();
				TreePath path = jTreeClasses.getPathForLocation(x, y);
				if (path != null) {
					jTreeClasses.setSelectionPath(path);
					selpath = path;
					jPopupMenuTree.show(jTreeClasses, x, y);
				}
			}
		}
	} // class ClassTreeListener

	public void uncheckAllChildren(DefaultMutableTreeNode parent){
		for(int i = 0;  i < parent.getChildCount(); i++){
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getChildAt(i);
			((IconOwlData)child.getUserObject()).m_icon = ICON_OWLCLASSUNCHECKED;
			uncheckAllChildren(child);
		}
		jTreeClasses.repaint();
	}

	//select all children of a class
	public void checkAllChildren(DefaultMutableTreeNode parent){
		for(int i = 0;  i < parent.getChildCount(); i++){
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getChildAt(i);
			((IconOwlData)child.getUserObject()).m_icon = ICON_OWLCLASSCHECKED;
			checkAllChildren(child);
		}
		jTreeClasses.repaint();
	}

	public void checkAllParents(DefaultMutableTreeNode child) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) child.getParent();
		if(parent!=null){
			( (IconOwlData) parent.getUserObject()).m_icon = ICON_OWLCLASSCHECKED;
			checkAllParents(parent);
		}
		jTreeClasses.repaint();
	}

	public void expandTree() {
		int row = 0;
		while (row < jTreeClasses.getRowCount()) {
			jTreeClasses.expandRow(row);
			row++;
		}
	}

	void jMenuItemSelectClassAndSubclasses_actionPerformed(ActionEvent e) {
		last = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
		checkAllParents(last);
		((IconOwlData)last.getUserObject()).m_icon = ICON_OWLCLASSCHECKED;
		checkAllChildren(last);
	}

	void jMenuItemUnSelectClassAndSubclasses_actionPerformed(ActionEvent e) {
		last = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
		((IconOwlData)last.getUserObject()).m_icon = ICON_OWLCLASSUNCHECKED;
		uncheckAllChildren(last);
	}

	void jButton2_actionPerformed(ActionEvent e) {
		modalResult = false;
		dispose();
	}

	void jButton1_actionPerformed(ActionEvent e) {
		modalResult = true;
		dispose();
	}


}

class DialogClassesToImport_jMenuItemSelectClassAndSubclasses_actionAdapter implements java.awt.event.ActionListener {
	DialogClassesToImport adaptee;

	DialogClassesToImport_jMenuItemSelectClassAndSubclasses_actionAdapter(DialogClassesToImport adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemSelectClassAndSubclasses_actionPerformed(e);
	}
}

class DialogClassesToImport_jMenuItemUnSelectClassAndSubclasses_actionAdapter implements java.awt.event.ActionListener {
	DialogClassesToImport adaptee;

	DialogClassesToImport_jMenuItemUnSelectClassAndSubclasses_actionAdapter(DialogClassesToImport adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemUnSelectClassAndSubclasses_actionPerformed(e);
	}
}

class DialogClassesToImport_jButton2_actionAdapter implements java.awt.event.ActionListener {
	DialogClassesToImport adaptee;

	DialogClassesToImport_jButton2_actionAdapter(DialogClassesToImport adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}

class DialogClassesToImport_jButton1_actionAdapter implements java.awt.event.ActionListener {
	DialogClassesToImport adaptee;

	DialogClassesToImport_jButton1_actionAdapter(DialogClassesToImport adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}
