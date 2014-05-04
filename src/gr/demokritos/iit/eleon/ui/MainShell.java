package gr.demokritos.iit.eleon.ui;

import java.io.File;
import java.io.IOException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import gr.demokritos.iit.eleon.commons.Constants;
import gr.demokritos.iit.eleon.functionality.PerEntityNode;
import gr.demokritos.iit.eleon.functionality.PerPropertyNode;
import gr.demokritos.iit.eleon.functionality.TreeNodeData;

/**
 * @author gmouchakis
 *
 */
public class MainShell extends Shell {
	protected Text textEndpoint;
	protected Table table;
	protected Tree tree;
	protected List list;
	static protected MainShell shell;
	private Text textTitle;
	private OntModel ontModel;
	private String filename = null;
	private String currentAuthor;
	protected Tree treePerEntity;
	//protected Tree treePerProperty;
	//private MenuItem mntmNew;

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
		
		/*MenuItem newItem = new MenuItem(fileMenu, SWT.PUSH);
		newItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		
        	}
        });
		newItem.setText("&New");*/
		
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
        openItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		FileDialog dialog = new FileDialog (shell, SWT.OPEN);
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
        		//dialog.setFileName ("myfile");
        		try {
        			String openFilename = dialog.open();
        			if (openFilename == null) return; 
					open(openFilename);
				} catch (Exception e) {
					e.printStackTrace();
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(e.toString());
	                box.open();
				}
        		//System.out.println ("Open: " + dialog.open ());
        	}
        });
        openItem.setText("&Open...");
        
        MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
        saveItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		try {
					save(tree, treePerEntity);
				} catch (Exception e) {
					e.printStackTrace();
					e.printStackTrace();
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(e.toString());
	                box.open();
				}
        	}
        });
        saveItem.setText("&Save");
        
        MenuItem saveAsItem = new MenuItem(fileMenu, SWT.PUSH);
        saveAsItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		try {
					saveAs(tree, treePerEntity);
				} catch (Exception e) {
					e.printStackTrace();
					e.printStackTrace();
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(e.toString());
	                box.open();
				}
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
		
		MenuItem mntmNew = new MenuItem(vocabulariesMenu, SWT.PUSH);
		mntmNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
        		String [] filterNames = new String [] {"Ont Files", "All Files (*)"};
        		String [] filterExtensions = new String [] {"*.rdf;*.owl;*.ttl,*.xml", "*"};
        		//String filterPath = "/";
        		String filterPath = System.getProperty("user.dir");
        		String platform = SWT.getPlatform();
        		if (platform.equals("win32") || platform.equals("wpf")) {
        			filterNames = new String [] {"Ont Files", "All Files (*.*)"};
        			filterExtensions = new String [] {"*.rdf;*.owl;*.ttl,*.xml", "*.*"};
        			//filterPath = "c:\\";
        		}
        		dialog.setFilterNames (filterNames);
        		dialog.setFilterExtensions (filterExtensions);
        		dialog.setFilterPath (filterPath);
        		//dialog.setFileName ("myfile");
        		try {
        			addVocabularyToMenu(dialog.open(), vocabulariesMenu);
        			//System.out.println(dialog.open());
				} catch (Exception ex) {
					ex.printStackTrace();
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(e.toString());
	                box.open();
				}
			}
		});
		mntmNew.setText("New...");
		
		MenuItem mntmSkos = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmSkos.setText("skos.rdf");
		
		MenuItem mntmTf = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmTf.setText("t4f.owl");
		
		MenuItem mntmAuthor = new MenuItem(menu, SWT.CASCADE);
		mntmAuthor.setText("A&uthor");
		
		final Menu AuthorMenu = new Menu(mntmAuthor);
		mntmAuthor.setMenu(AuthorMenu);
		//here
		MenuItem mntmNewAuthor = new MenuItem(AuthorMenu, SWT.PUSH);
		mntmNewAuthor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InsertAuthorDialog dialog = new InsertAuthorDialog(shell);
				final String authorName = dialog.open();
				if (authorName != null && ( ! authorName.equals("") )) {
					boolean not_found = true;
					for (MenuItem item : AuthorMenu.getItems()) {
						if (item.getText().equals(authorName)) {
							MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
			                box.setText("Author Exists");
			                box.setMessage("Author \"" + authorName + "\" already exists");
			                box.open();
			                break;
						}
					}
					if (not_found) {
						MenuItem mntmInsertedAuthor = new MenuItem(AuthorMenu, SWT.RADIO);
						mntmInsertedAuthor.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								currentAuthor = authorName;
							}
						});
						mntmInsertedAuthor.setText(authorName);
					}
				}
			}
		});
		mntmNewAuthor.setText("New...");
		
		MenuItem mntmAbout = new MenuItem(menu, SWT.PUSH);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
                box.setText("About");
                box.setMessage("Eleon 3.0 from NCSR \"Demokritos\"");
                box.open();
			}
		});
		mntmAbout.setText("&About");
		
		textEndpoint = new Text(this, SWT.BORDER);
		textEndpoint.setBounds(378, 30, 709, 24);
		
		Label lblEnpoint = new Label(this, SWT.NONE);
		lblEnpoint.setBounds(318, 30, 54, 18);
		lblEnpoint.setText("Enpdoint");
		
		Label lbltree = new Label(this, SWT.NONE);
		lbltree.setBounds(318, 60, 283, 18);
		lbltree.setText("Tree");
		
		Label lblfacet = new Label(this, SWT.NONE);
		lblfacet.setBounds(10, 60, 76, 18);
		lblfacet.setText("Facet");
		
		Label lblFields = new Label(this, SWT.NONE);
		lblFields.setBounds(693, 60, 76, 18);
		lblFields.setText("Fields");
		
		list = new List(this, SWT.BORDER);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				if ( ! hasSelectedAuthor(AuthorMenu)) {
					MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Choose author");
	                box.setMessage("Choose an author from the \"Author\" menu first.");
	                box.open();
	                return;
				}		
				if (list.getSelection()[0].toString().equals(Constants.perProperty)) {
					boolean has_vocabulary = false;
					ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
					for (MenuItem menuItem : vocabulariesMenu.getItems()) {
						if (menuItem.getSelection()) {//get all checked items
							if (menuItem.getText().equals("skos.rdf")) {
								ontModel.read("file:////" + (new File("vocabularies/skos.rdf")).getAbsolutePath());
								has_vocabulary = true;
							} else if (menuItem.getText().equals("t4f.owl")) {
								ontModel.read("file:////" + (new File("vocabularies/t4f.owl")).getAbsolutePath());
								//ontModel.read("file:////" + (new File("vocabularies/void.rdf")).getAbsolutePath());
								has_vocabulary = true;
							} else {
								ontModel.read("file:////" + (new File((String) menuItem.getData())).getAbsolutePath());
								has_vocabulary = true;
							}
						}
					}
					if (has_vocabulary) {
						//tree.dispose();
						if (tree == null) {
							createTree();
							fillPerPropertyTree(ontModel.listAllOntProperties().toList(), currentAuthor);
						}
						tree.moveAbove(null);
					} else {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Choose a vocabulary from the \"Vocabularies\" menu first.");
		                box.open();
					}
				} else if (list.getSelection()[0].toString().equals("per entity")) {
					if (treePerEntity == null) {
						createTreePerEntity();
					}
					treePerEntity.moveAbove(null);
				}
			}
		});
		list.setBounds(10, 84, 302, 578);
		String[] listItems = {Constants.perProperty, "per entity"};
		list.setItems(listItems);
		
		//createTree();
		
		//createTable();
				
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(10, 0, 1077, 24);
		
		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setBounds(10, 30, 30, 18);
		lblTitle.setText("Title");
		
		textTitle = new Text(this, SWT.BORDER);
		textTitle.setBounds(46, 30, 266, 24);
		
		createContents();
	}
	
	protected void fillPerPropertyTree(java.util.List<OntProperty> propertiesList, String author) {
		TreeItem root = new TreeItem(tree, SWT.NONE);
		root.setText("root");
		ArrayList<OntProperty> notInsertedPropertiesList = new ArrayList<OntProperty>();
		for (OntProperty ontProperty : propertiesList) {
			OntProperty superProperty = ontProperty.getSuperProperty();
			if (superProperty == null) {
				TreeItem treeItem = new TreeItem(root, SWT.NONE);
				treeItem.setText(ontProperty.toString());
				PerPropertyNode property = new PerPropertyNode(ontProperty);
				property.setDc_creator(author);
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
			if (((PerPropertyNode) child.getData()).getOntProperty().equals(superProperty)) {
				TreeItem newtreeItem = new TreeItem(child, SWT.NONE);
				newtreeItem.setText(ontProperty.toString());
				PerPropertyNode property = new PerPropertyNode(ontProperty);
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
				TreeNodeData propAndVal = (TreeNodeData) tree.getSelection()[0].getData();
				String size;
				if ( ! propAndVal.hasVoid_size()) {
					size = null;
				} else {
					size = (String) propAndVal.getVoid_size().toString();
				}
				String subjects;
				if ( ! propAndVal.hasVoid_distinctSubjects()) {
					subjects = null;
				} else {
					subjects = (String) propAndVal.getVoid_distinctSubjects().toString();
				}
				String objects;
				if ( ! propAndVal.hasVoid_distinctObjects()) {
					objects = null;
				} else {
					objects = (String) propAndVal.getVoid_distinctObjects().toString();
				}
				createTableContents(size, subjects, objects, tree);
			}
		});
		tree.setBounds(318, 84, 369, 578);
	}
	
	protected void createTreePerEntity() {
		treePerEntity = new Tree(this, SWT.BORDER);
		treePerEntity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (treePerEntity.getSelection()[0].getText().equals("root")) return;
				TreeNodeData nodeData = (TreeNodeData) treePerEntity.getSelection()[0].getData();
				String size;
				if ( ! nodeData.hasVoid_size()) {
					size = null;
				} else {
					size = (String) nodeData.getVoid_size().toString();
				}
				String subjects;
				if ( ! nodeData.hasVoid_distinctSubjects()) {
					subjects = null;
				} else {
					subjects = (String) nodeData.getVoid_distinctSubjects().toString();
				}
				String objects;
				if ( ! nodeData.hasVoid_distinctObjects()) {
					objects = null;
				} else {
					objects = (String) nodeData.getVoid_distinctObjects().toString();
				}
				createTableContents(size, subjects, objects, treePerEntity);
			}
		});
		treePerEntity.setBounds(318, 84, 369, 578);
		TreeItem root = new TreeItem(treePerEntity, SWT.NONE);
		root.setText("root");
		
		//insert menu for this tree
		final Menu treeMenu = new Menu(treePerEntity);
		treePerEntity.setMenu(treeMenu);
		
		final MenuItem insertNewChild = new MenuItem(treeMenu, SWT.NONE);
	    insertNewChild.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0) {
					// selection.setExpanded(true);
					PerEntityInsertDialog dialog = new PerEntityInsertDialog(shell);
					PerEntityNode nodeData = dialog.open();
					if (nodeData == null) {
						return;
					} else {
						TreeItem selection = selected[0];
						TreeItem item = new TreeItem(selection, SWT.NONE);
						nodeData.setDc_creator(currentAuthor);
						item.setData(nodeData);
						String subjectPattern = nodeData.getSubjectPattern();
						String objectPattern = nodeData.getObjectPattern();
						nodeData.setDc_creator(currentAuthor);
						String itemText = "";
						if (subjectPattern != null) {
							itemText += "(sbj)=" + subjectPattern + " ";
						}
						if (objectPattern != null) {
							itemText += "(obj)=" + objectPattern;
						}
						item.setText(itemText);
					}
					//System.out.println("Insert Into - " + selected[0].getText());
				} else
					System.out.println("nothing selected.");// TODO:message box...
			}

		});
	    insertNewChild.setText("Insert new node");
	    
	    final MenuItem insertExistingChild = new MenuItem(treeMenu, SWT.NONE);
	    insertExistingChild.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0) {
					SelectExistingNodesDialog dialog = new SelectExistingNodesDialog(shell);
					String selectedNodeName = dialog.open(treePerEntity.getItems()[0]);//start from root node.
					if (selectedNodeName == null) {
						return;
					}
					//TODO:implement insert existing node.
				} else {
					System.out.println("nothing selected.");// TODO:message box...
				}
			}

		});
	    insertExistingChild.setText("Insert existing node as child");
	    
	    new MenuItem(treeMenu, SWT.SEPARATOR);
	    
	    final MenuItem remove = new MenuItem(treeMenu, SWT.NONE);
	    remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0) {
					TreeItem itemToDelete = selected[0];
					String name =  itemToDelete.getText();
					TreeItem parent = itemToDelete.getParentItem();
					while(deleteNode(parent, name));//We suppose that the user can select only one item to remove.
					//treePerEntity.select(parent);
					//TODO: implement...
				} else {
					System.out.println("nothing selected.");// TODO:message box...
				}
			}

		});
	    remove.setText("Remove");
		
	}
	
	
	protected void createTable() {
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(693, 84, 394, 578);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnProperty = new TableColumn(table, SWT.NONE);
		tblclmnProperty.setWidth(198);
		tblclmnProperty.setText("Property");
		
		TableColumn tblclmnValue = new TableColumn(table, SWT.NONE);
		tblclmnValue.setWidth(100);
		tblclmnValue.setText("Value");
	}
	
	protected void createTableContents(String size, String subjects, String objects, final Tree tree) {
		
		if (table != null) {
			table.dispose();
		}
		createTable();
		
		TableItem item_size = new TableItem (table, SWT.NONE);
		item_size.setText(new String [] {"void:triples", size});
		
		TableItem item_Subjects = new TableItem (table, SWT.NONE);
		item_Subjects.setText(new String [] {"void:distinctSubjects", subjects});
		
		TableItem item_Objects = new TableItem (table, SWT.NONE);
		item_Objects.setText(new String [] {"void:distinctObjects", objects});
		
		final TableEditor editor = new TableEditor (table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String creator = ((TreeNodeData) tree.getSelection()[0].getData()).getDc_creator();
				if ( ! creator.equals(currentAuthor)) {
				//if ( ! canEditItem(tree.getSelection()[0], currentAuthor)) {
					MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Value read-only");
	                box.setMessage("You cannot edit this value because it was inserted by author \"" + creator + "\".");
	                box.open();
	                return;
				}
				
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
		
				// Identify the selected row
				TableItem item = (TableItem)e.item;
				if (item == null) return;
				
				//Get Property name
				final String property = item.getText(0);
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					//TODO:check what happens if the user imputs a non-integer and then does not change it back. maybe a bug here...
					public void modifyText(ModifyEvent me) {
						//System.out.println("ksana");
						//String oldValue = editor.getItem().getText(1);
						//System.out.println(editor.getItem().getText(1));
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(1, text.getText());
						try {
							Integer value = new Integer(text.getText());
							if (property.equals("void:triples")) {
								((TreeNodeData) tree.getSelection()[0].getData()).setVoid_size(value);
							} else if (property.equals("void:distinctSubjects")) {
								((TreeNodeData) tree.getSelection()[0].getData()).setVoid_distinctSubjects(value);
							} else if (property.equals("void:distinctObjects")) {
								((TreeNodeData) tree.getSelection()[0].getData()).setVoid_distinctObjects(value);
							}
							
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
	
	protected void save(Tree perPropertyTree, Tree perEntityTree) throws ParserConfigurationException, TransformerException {
		
		if (filename==null) {
			saveAs(perPropertyTree, perEntityTree);
			return;
		}
		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("eleon_save");
			doc.appendChild(rootElement);
			
			rootElement.setAttribute("xmlns:void", "http://rdfs.org/ns/void#");
			rootElement.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
			
			Element title = doc.createElement("dc:title");
			title.appendChild(doc.createTextNode(textTitle.getText()));
			rootElement.appendChild(title);
			
			Element endpoint = doc.createElement("void:sparqlEndpoint");
			endpoint.appendChild(doc.createTextNode(textEndpoint.getText()));
			rootElement.appendChild(endpoint);
			
			//per property tree
			Element perPropertyTreeElement = doc.createElement("facet");
			perPropertyTreeElement.setAttribute("type", "per_property");
			rootElement.appendChild(perPropertyTreeElement);
			
			Element treeRootProperty = doc.createElement("treeItem");
			treeRootProperty.setAttribute("name", "root");
			perPropertyTreeElement.appendChild(treeRootProperty);
			
			createDOMFromTree(perPropertyTree.getItems()[0], treeRootProperty, doc);
			/*
			//per element tree
			Element perEntityTreeElement = doc.createElement("tree");
			perEntityTreeElement.setAttribute("facet", "per_entity");
			rootElement.appendChild(perEntityTreeElement);
			
			Element treeRootEntity = doc.createElement("treeItem");
			treeRootEntity.setAttribute("name", "root");
			perEntityTreeElement.appendChild(treeRootEntity);
			
			createDOMFromTree(perEntityTree.getItems()[0], treeRootEntity, doc);
			
			*/
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File " + filename + " saved!");
	 
	}
	
	protected void saveAs(Tree perPropertyTree, Tree perEntityTree) throws ParserConfigurationException, TransformerException {
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
		if (filename != null) {
			save(perPropertyTree, perEntityTree);
		}
		//System.out.println ("Save to: " + dialog.open ());
	}
	
	
	protected void createDOMFromTree(TreeItem treeItem, Element root, Document doc) {
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			Element treeItemNode = doc.createElement("treeItem");
			// System.out.println(treeItem);
			//treeItemNode.setAttribute("name", ((TreeNodeData) treeItemCurrent.getData()).getOntProperty().toString());
			treeItemNode.setAttribute("name", treeItemCurrent.getText());
			treeItemNode.setAttribute("dc:creator", ((TreeNodeData) treeItemCurrent.getData()).getDc_creator());
			Integer void_size = ((TreeNodeData) treeItemCurrent.getData()).getVoid_size();
			if (void_size != null) {
				treeItemNode.setAttribute("void:triples", void_size.toString());
			}
			Integer void_distinctSubjects = ((TreeNodeData) treeItemCurrent.getData()).getVoid_distinctSubjects();
			if (void_distinctSubjects != null) {
				treeItemNode.setAttribute("void:distinctSubjects", void_distinctSubjects.toString());
			}
			Integer void_distinctObjects = ((TreeNodeData) treeItemCurrent.getData()).getVoid_distinctObjects();
			if (void_distinctObjects != null) {
				treeItemNode.setAttribute("void:distinctObjects", void_distinctObjects.toString());
			}
			if (treeItemCurrent.getData() instanceof PerEntityNode) {
				PerEntityNode perEntityNode = (PerEntityNode) treeItemCurrent.getData();
				String subjectPattern = perEntityNode.getSubjectPattern();
				String objectPattern = perEntityNode.getObjectPattern();
				if (subjectPattern != null) {
					Element subjectElement = doc.createElement("subject");
					subjectElement.setAttribute("void:uriRegexPattern", subjectPattern);
					treeItemNode.appendChild(subjectElement);
				}
				if (objectPattern != null) {
					Element objectElement = doc.createElement("object");
					objectElement.setAttribute("void:uriRegexPattern", objectPattern);
					treeItemNode.appendChild(objectElement);
				}
			}
			//treeItemNode.setAttribute("parent", treeItem.getText());
			root.appendChild(treeItemNode);
			if (treeItemCurrent.getItemCount() > 0) {
				createDOMFromTree(treeItemCurrent, treeItemNode, doc);
			}
		}
	}
	
	protected void open(String filename) throws ParserConfigurationException, SAXException, IOException, Exception{
		
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			doc.getDocumentElement().normalize();
		 
			String rootElement = doc.getDocumentElement().getNodeName();
			if ( ! rootElement.equals("eleon_save")) {
				throw new Exception("Tried to open a non-eleon_save document!");
			}
						
			NodeList nListTitle = doc.getElementsByTagName("dc:title");
			this.textTitle.setText(nListTitle.item(0).getTextContent());
			
			NodeList nEnpointTitle = doc.getElementsByTagName("void:sparqlEndpoint");
			this.textEndpoint.setText(nEnpointTitle.item(0).getTextContent());
			/*
			tree.dispose();
			table.dispose();
			createTree();
			createTable();
			*/
			ArrayList<OntProperty> propertiesList = new ArrayList<OntProperty>();
			
			OntModel ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
			NodeList nList = doc.getElementsByTagName("treeItem");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					if ( ! name.equals("root")) {
						OntProperty ontProperty = ontModel.createOntProperty(name);
						String parent = ((Element)eElement.getParentNode()).getAttribute("name");
						if ( ! parent.equals("root")) {
							boolean found_in_list = false;
							for (OntProperty list_item : propertiesList) {
								if (list_item.getURI().equals(parent)) {
									ontProperty.setSuperProperty(list_item);
									found_in_list = true;
								}
							}
							if ( ! found_in_list) {
								OntProperty ontParent = ontModel.createOntProperty(parent);
								ontProperty.setSuperProperty(ontParent);
								propertiesList.add(ontParent);
							}
						}
						propertiesList.add(ontProperty);
					}
				}
			}
			fillPerPropertyTree(propertiesList, "TEMP");
			//System.out.println(propertiesList);
			
			ArrayList<PerPropertyNode> list = new ArrayList<PerPropertyNode>();
			//the tree is created. insert void_size
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					String dc_creator = eElement.getAttribute("dc:creator");
					list.clear();
					searchTree(tree.getItems()[0], name, list);
					for (TreeNodeData propAndVal : list) {
						propAndVal.setDc_creator(dc_creator);
						String void_size_str = eElement.getAttribute("void:triples");
						if ( ! void_size_str.equals("")) {
							Integer void_size = new Integer(void_size_str);
							propAndVal.setVoid_size(void_size);
						}
						String void_distinctSubjects_str = eElement.getAttribute("void:distinctSubjects");
						if ( ! void_distinctSubjects_str.equals("")) {
							Integer void_distinctSubjects = new Integer(void_distinctSubjects_str);
							propAndVal.setVoid_distinctSubjects(void_distinctSubjects);
						}
						String void_distinctObjects_str = eElement.getAttribute("void:distinctObjects");
						if ( ! void_distinctObjects_str.equals("")) {
							Integer void_distinctObjects = new Integer(void_distinctObjects_str);
							propAndVal.setVoid_distinctObjects(void_distinctObjects);
						}
					}
				}
			}
			
	}
	
	
	protected void searchTree(TreeItem treeItem, String propertyName, java.util.List<PerPropertyNode> list){
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			if ( ((PerPropertyNode) treeItemCurrent.getData()).getOntProperty().getURI().equals(propertyName)) {
				list.add((PerPropertyNode) treeItemCurrent.getData());
			}
			if (treeItemCurrent.getItemCount() > 0) {
				searchTree(treeItemCurrent, propertyName, list);
			}
		}
	}
	
	protected void addVocabularyToMenu(String filename, Menu vocabulariesMenu) {
		if (filename == null) {
			return;
		}
		File file = new File(filename);
		MenuItem mntmNewVoc = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmNewVoc.setText(file.getName());
		mntmNewVoc.setData(filename);
	}
	
	protected boolean hasSelectedAuthor(Menu AuthorMenu) {
		for (MenuItem item : AuthorMenu.getItems()) {
			if (item.getSelection()) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean deleteNode(TreeItem treeItem, String nodeNameToDelete) {
		/*int index = 0;
		TreeItem[] items = treeItem.getItems();
		for (int i=0; i<items.length; i++) {
			if (items[i].getText().equals(nodeNameToDelete)) {
				treeItem.removeAll();
				for (int j=0; j<items.length; j++) {
					if (j!=i) {
						TreeItem sibling = new TreeItem(treeItem, SWT.NONE);
						sibling.setText("");
					}
				}
			}
		}*/
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			if (treeItemCurrent.getText().equals(nodeNameToDelete)) {
				//if ( ! treeItemCurrent.isDisposed()) {
					treeItemCurrent.dispose();
					return true;
				//}
			}
			if (treeItemCurrent.getItemCount() > 0) {
				deleteNode(treeItemCurrent, nodeNameToDelete);
			}
		}
		return false;
	}

	/*protected boolean canEditItem(TreeItem treeItem, String author) {
		String creator = ((TreeNodeData) treeItem.getData()).getDc_creator();
		if (creator.equals(author)) {
			return true;
		} else {
			return false;
		}
	}*/
	
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
		setSize(1103, 739);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
