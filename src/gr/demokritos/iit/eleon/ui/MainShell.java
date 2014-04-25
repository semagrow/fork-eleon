package gr.demokritos.iit.eleon.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.EleonTreeItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import gr.demokritos.iit.eleon.commons.Constants;

/**
 * @author gmouchakis
 *
 */
public class MainShell extends Shell {
	protected Text textEndpoint;
	protected String endpoint;
	protected Table table;
	protected Tree tree;
	protected List list;
	static protected MainShell shell;
	private Text textTitle;
	private OntModel ontModel;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new MainShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public MainShell(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");
		
		Menu fileMenu = new Menu(this, SWT.DROP_DOWN);
		mntmFile.setMenu(fileMenu);
		
		MenuItem newItem = new MenuItem(fileMenu, SWT.PUSH);
		newItem.setText("&New");
		
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
        openItem.setText("&Open...");
        
        MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
        saveItem.setText("&Save");
        
        MenuItem saveAsItem = new MenuItem(fileMenu, SWT.PUSH);
        saveAsItem.setText("Save &As...");
        
        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		getDisplay().dispose();
                System.exit(0);
        	}
        });
        exitItem.setText("&Exit");
		
		MenuItem mntmVocabularies = new MenuItem(menu, SWT.CASCADE);
		mntmVocabularies.setText("&Vocabularies");
		
		final Menu vocabulariesMenu = new Menu(mntmVocabularies);
		mntmVocabularies.setMenu(vocabulariesMenu);
		
		MenuItem mntmSkos = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmSkos.setText("skos");
		
		MenuItem mntmTf = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmTf.setText("t4f");
		
		MenuItem mntmAbout = new MenuItem(menu, SWT.NONE);
		mntmAbout.setText("&About");
		
		textEndpoint = new Text(this, SWT.BORDER);
		textEndpoint.setBounds(378, 30, 630, 24);
		
		Label lblEnpoint = new Label(this, SWT.NONE);
		lblEnpoint.setBounds(318, 30, 54, 18);
		lblEnpoint.setText("Enpoint");
		
		Label lbltree = new Label(this, SWT.NONE);
		lbltree.setBounds(318, 60, 283, 18);
		lbltree.setText("Tree");
		
		Label lblfacet = new Label(this, SWT.NONE);
		lblfacet.setBounds(10, 60, 76, 18);
		lblfacet.setText("Facet");
		
		Label lblFields = new Label(this, SWT.NONE);
		lblFields.setBounds(607, 60, 76, 18);
		lblFields.setText("Fields");
		
		list = new List(this, SWT.BORDER);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				if (list.getSelection()[0].toString().equals(Constants.perProperty)) {
					ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
					for (MenuItem menuItem : vocabulariesMenu.getItems()) {
						if (menuItem.getSelection()) {//get all checked items
							if (menuItem.getText().equals("skos")) {
								ontModel.read("file:////" + (new File("vocabularies/skos.rdf")).getAbsolutePath());
							} else if (menuItem.getText().equals("t4f")) {
								ontModel.read("file:////" + (new File("vocabularies/t4f.owl")).getAbsolutePath());
								//ontModel.read("file:////" + (new File("vocabularies/void.rdf")).getAbsolutePath());
							}
						}
					}
					createPerPropertyTree(ontModel.listAllOntProperties().toList());
				}
			}
		});
		list.setBounds(10, 84, 302, 578);
		String[] listItems = {Constants.perProperty};
		list.setItems(listItems);
		
		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(318, 84, 283, 578);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(607, 84, 401, 578);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(10, 0, 998, 24);
		
		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setBounds(10, 30, 30, 18);
		lblTitle.setText("Title");
		
		textTitle = new Text(this, SWT.BORDER);
		textTitle.setBounds(46, 30, 266, 24);
		
		createContents();
	}
	
	protected void createPerPropertyTree(java.util.List<OntProperty> propertiesList) {
		//EleonTreeItem root = new EleonTreeItem(tree, SWT.NONE, null);
		TreeItem root = new TreeItem(tree, SWT.NONE);
		root.setText("root");
		ArrayList<OntProperty> notInsertedPropertiesList = new ArrayList<OntProperty>();
		for (OntProperty ontProperty : propertiesList) {
			OntProperty superProperty = ontProperty.getSuperProperty();
			if (superProperty == null) {
				TreeItem treeItem = new TreeItem(root, SWT.NONE);
				treeItem.setText(ontProperty.toString());
				treeItem.setData(ontProperty);
				// EleonTreeItem eleonTreeItem = new EleonTreeItem(root,
				// SWT.NONE, ontProperty);
				// eleonTreeItem.setText(ontProperty.toString());
			} else {	
					boolean inserted = insertChildInTree(root, ontProperty, superProperty);
					if ( ! inserted ) {
						notInsertedPropertiesList.add(ontProperty);
					}
			}
		}
		while ( ! notInsertedPropertiesList.isEmpty()) {
			Collections.rotate(notInsertedPropertiesList, 1);
			OntProperty ontProperty = notInsertedPropertiesList.get(0);
			OntProperty superProperty = ontProperty.getSuperProperty();
			boolean inserted = insertChildInTree(root, ontProperty, superProperty);
			if (inserted) {
				notInsertedPropertiesList.remove(0);
			}
		}
	}
	
	
	protected boolean insertChildInTree(TreeItem treeItem, OntProperty ontProperty, OntProperty superProperty) {
		boolean inserted = false;
		for(TreeItem child : treeItem.getItems()) {
			//if (((EleonTreeItem) child).getProperty().equals(superProperty)) {
			if (child.getData().equals(superProperty)) {
				TreeItem newtreeItem = new TreeItem(child, SWT.NONE);
				newtreeItem.setText(ontProperty.toString());
				newtreeItem.setData(ontProperty);
				inserted = true;
				/*EleonTreeItem eleonTreeItem = new EleonTreeItem(child, SWT.NONE, ontProperty);
				eleonTreeItem.setText(ontProperty.toString());*/
			}
			if ((!inserted) && child.getItemCount()>0) {//if not leaf check the children
				inserted = insertChildInTree(child, ontProperty, superProperty);
			}
		}
		return inserted;
	}
	
	
	protected void fillTable() {
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText("New TableItem");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(198);
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
	}
	
	
	protected void clearListTreeTavle() {
		list.dispose();
		list = new List(this, SWT.BORDER);
		list.setBounds(10, 79, 302, 583);
		tree.dispose();
		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(318, 79, 283, 583);
		table.dispose();
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(607, 79, 401, 583);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Eleon");
		setSize(1024, 739);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
