package gr.demokritos.iit.eleon.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import gr.demokritos.iit.eleon.commons.Constants;
import gr.demokritos.iit.eleon.functionality.PropertyAndValues;

/**
 * @author gmouchakis
 *
 */
public class MainShell extends Shell {
	protected Text textEndpoint;
	//protected String endpoint;
	protected Table table;
	protected Tree tree;
	protected List list;
	static protected MainShell shell;
	private Text textTitle;
	private OntModel ontModel;
	private String filename = null;

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
        openItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		FileDialog dialog = new FileDialog (shell, SWT.OPEN);
        		String [] filterNames = new String [] {/*"Image Files", */"All Files (*)"};
        		String [] filterExtensions = new String [] {/*"*.gif;*.png;*.xpm;*.jpg;*.jpeg;*.tiff", */"*"};
        		//String filterPath = "/";
        		String filterPath = System.getProperty("user.dir");
        		String platform = SWT.getPlatform();
        		if (platform.equals("win32") || platform.equals("wpf")) {
        			filterNames = new String [] {/*"Image Files", */"All Files (*.*)"};
        			filterExtensions = new String [] {/*"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", */"*.*"};
        			//filterPath = "c:\\";
        		}
        		dialog.setFilterNames (filterNames);
        		dialog.setFilterExtensions (filterExtensions);
        		dialog.setFilterPath (filterPath);
        		//dialog.setFileName ("myfile");
        		System.out.println ("Open: " + dialog.open ());
        	}
        });
        openItem.setText("&Open...");
        
        MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
        saveItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		save();
        	}
        });
        saveItem.setText("&Save");
        
        MenuItem saveAsItem = new MenuItem(fileMenu, SWT.PUSH);
        saveAsItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		saveAs();
        	}
        });
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
				tree.dispose();
				createTree();
				boolean has_vocabulary = false;
				if (list.getSelection()[0].toString().equals(Constants.perProperty)) {
					ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
					for (MenuItem menuItem : vocabulariesMenu.getItems()) {
						if (menuItem.getSelection()) {//get all checked items
							if (menuItem.getText().equals("skos")) {
								ontModel.read("file:////" + (new File("vocabularies/skos.rdf")).getAbsolutePath());
								has_vocabulary = true;
							} else if (menuItem.getText().equals("t4f")) {
								ontModel.read("file:////" + (new File("vocabularies/t4f.owl")).getAbsolutePath());
								//ontModel.read("file:////" + (new File("vocabularies/void.rdf")).getAbsolutePath());
								has_vocabulary = true;
							}
						}
					}
					if (has_vocabulary) {
						fillPerPropertyTree(ontModel.listAllOntProperties().toList());
					} else {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Error");
		                box.setMessage("Choose a vocabulary from the \"Vocabularies\" menu first.");
		                box.open();
					}
				}
			}
		});
		list.setBounds(10, 84, 302, 578);
		String[] listItems = {Constants.perProperty};
		list.setItems(listItems);
		
		createTree();
		
		createTable();
				
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(10, 0, 998, 24);
		
		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setBounds(10, 30, 30, 18);
		lblTitle.setText("Title");
		
		textTitle = new Text(this, SWT.BORDER);
		textTitle.setBounds(46, 30, 266, 24);
		
		createContents();
	}
	
	protected void fillPerPropertyTree(java.util.List<OntProperty> propertiesList) {
		TreeItem root = new TreeItem(tree, SWT.NONE);
		root.setText("root");
		ArrayList<OntProperty> notInsertedPropertiesList = new ArrayList<OntProperty>();
		for (OntProperty ontProperty : propertiesList) {
			OntProperty superProperty = ontProperty.getSuperProperty();
			if (superProperty == null) {
				TreeItem treeItem = new TreeItem(root, SWT.NONE);
				treeItem.setText(ontProperty.toString());
				PropertyAndValues property = new PropertyAndValues(ontProperty);
				treeItem.setData(property);
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
			if (((PropertyAndValues) child.getData()).getOntProperty().equals(superProperty)) {
				TreeItem newtreeItem = new TreeItem(child, SWT.NONE);
				newtreeItem.setText(ontProperty.toString());
				PropertyAndValues property = new PropertyAndValues(ontProperty);
				newtreeItem.setData(property);
				inserted = true;
			}
			if ((!inserted) && child.getItemCount()>0) {//if not leaf check the children
				inserted = insertChildInTree(child, ontProperty, superProperty);
			}
		}
		return inserted;
	}
	
	protected void createTree() {
		tree = new Tree(this, SWT.BORDER);
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (tree.getSelection()[0].getText().equals("root")) return;
				PropertyAndValues propAndVal = (PropertyAndValues) tree.getSelection()[0].getData();
				String size;
				if ( ! propAndVal.hasVoid_size()) {
					size = null;
				} else {
					size = (String) propAndVal.getVoid_size().toString();
				}
				createTableContents(size);
			}
		});
		tree.setBounds(318, 84, 283, 578);
	}
	
	protected void createTable() {
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(607, 84, 401, 578);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnProperty = new TableColumn(table, SWT.NONE);
		tblclmnProperty.setWidth(198);
		tblclmnProperty.setText("Property");
		
		TableColumn tblclmnValue = new TableColumn(table, SWT.NONE);
		tblclmnValue.setWidth(100);
		tblclmnValue.setText("Value");
	}
	
	protected void createTableContents(String size) {
		
		table.dispose();
		createTable();
		
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText(new String [] {"void:size", size});
		
		final TableEditor editor = new TableEditor (table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
		
				// Identify the selected row
				TableItem item = (TableItem)e.item;
				if (item == null) return;
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(1, text.getText());
						try {
							Integer void_size = new Integer(text.getText());
							((PropertyAndValues) tree.getSelection()[0].getData()).setVoid_size(void_size);
						} catch (NumberFormatException e) {
							MessageBox box = new MessageBox(getShell(), SWT.ERROR);
			                box.setText("Error");
			                box.setMessage("Input must be integer!");
			                box.open();
						}
						
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, 1);
			}
		});
	}
	
	protected void save() {
		
		if (filename==null) {
			saveAs();
			return;
		}
		
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("eleon_save");
			doc.appendChild(rootElement);
			
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(textTitle.getText()));
			rootElement.appendChild(title);
			
			Element endpoint = doc.createElement("endpoint");
			endpoint.appendChild(doc.createTextNode(textEndpoint.getText()));
			rootElement.appendChild(endpoint);
			
			Element tree = doc.createElement("tree");
			rootElement.appendChild(tree);
			
			Element treeRoot = doc.createElement("treeItem");
			treeRoot.setAttribute("name", "root");
			tree.appendChild(treeRoot);
			
			createTreeDOM(this.tree.getItems()[0], treeRoot, doc);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File " + filename + " saved!");
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
	protected void saveAs() {
		FileDialog dialog = new FileDialog (shell, SWT.SAVE);
		dialog.setOverwrite(true);
		String [] filterNames = new String [] {"XML Files", "All Files (*)"};
		String [] filterExtensions = new String [] {"*.xml", "*"};
		//String filterPath = "/";
		String filterPath = System.getProperty("user.dir");
		String platform = SWT.getPlatform();
		if (platform.equals("win32") || platform.equals("wpf")) {
			filterNames = new String [] {"XML Files", "All Files (*.*)"};
			filterExtensions = new String [] {"*.xml", "*.*"};
			//filterPath = "c:\\";
		}
		dialog.setFilterNames (filterNames);
		dialog.setFilterExtensions (filterExtensions);
		dialog.setFilterPath (filterPath);
		dialog.setFileName ("eleon_save");
		filename = dialog.open();
		save();
		//System.out.println ("Save to: " + dialog.open ());
	}
	
	protected void createTreeDOM(TreeItem treeItem, Element root, Document doc) {
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			if (treeItemCurrent.getItemCount()>0) {
				createTreeDOM(treeItemCurrent, root, doc);
			}
			Element treeItemNode = doc.createElement("treeItem");
			//System.out.println(treeItem);
			treeItemNode.setAttribute("OntProperty", ((PropertyAndValues) treeItemCurrent.getData()).getOntProperty().toString());
			Integer void_size = ((PropertyAndValues) treeItemCurrent.getData()).getVoid_size();
			if (void_size != null) {
				treeItemNode.setAttribute("void_size", void_size.toString());
			}
			treeItemNode.setAttribute("parent", treeItem.getText());
			root.appendChild(treeItemNode);
			//((PropertyAndValues) treeItem.getData()).getOntProperty();
			//((PropertyAndValues) treeItem.getData()).getVoid_size();
		}
	}
	
	
	/*protected void clearListTreeTavle() {
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
	}*/

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
