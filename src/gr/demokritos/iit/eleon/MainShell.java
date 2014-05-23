/***************

<p>Title: Main Shell</p>

<p>Description:
Entry point for the ELEON application.
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.
<br> Copyright (c) 2001-2014 National Centre for Scientific Research "Demokritos"
</p>

<p>
ELEON is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
</p>

<p>
ELEON is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
</p>

<p>
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
</p>

@author Dimitris Spiliotopoulos (MPIRO, 2001-2004)
@author Kostas Stamatakis
@author Theofilos Nikolaou
@author Maria Prospathopoulou
@author Spyros Kallonis
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)
@author Stasinos Konstantopoulos (INDIGO, 2009; RoboSKEL 2011; SemaGrow 2012-2014)
@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;

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
import gr.demokritos.iit.eleon.ui.CopyExistingNodeDialog;
import gr.demokritos.iit.eleon.ui.InsertInMenuDialog;
import gr.demokritos.iit.eleon.ui.PerEntityInsertDialog;
import gr.demokritos.iit.eleon.ui.SelectVocabulariesDialog;

public class MainShell extends Shell {
	protected Text textEndpoint;
	protected Table table;
	protected Tree treePerProperty;
	protected List list;
	static protected MainShell shell;
	private Text textTitle;
	private String filename = null;
	private String currentAuthor;
	protected Tree treePerEntity;
	private Menu dataSchemaMenu;
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
					save(treePerProperty, treePerEntity);
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
					saveAs(treePerProperty, treePerEntity);
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
		
		MenuItem mntmAuthor = new MenuItem(menu, SWT.CASCADE);
		mntmAuthor.setText("A&uthor");
		
		final Menu AuthorMenu = new Menu(mntmAuthor);
		mntmAuthor.setMenu(AuthorMenu);
		
		MenuItem mntmNewAuthor = new MenuItem(AuthorMenu, SWT.PUSH);
		mntmNewAuthor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InsertInMenuDialog dialog = new InsertInMenuDialog(shell);
				final String authorName = dialog.open("Insert author");
				if (authorName != null && ( ! authorName.equals("") )) {
					boolean not_found = true;
					for (MenuItem item : AuthorMenu.getItems()) {
						if (item.getText().equals(authorName)) {
							MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
			                box.setText("Author Exists");
			                box.setMessage("Author \"" + authorName + "\" already exists");
			                box.open();
			                not_found = false;
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
		
		MenuItem mntmAnnotationSchema = new MenuItem(menu, SWT.CASCADE);
		mntmAnnotationSchema.setText("Annotation &Schema");
		
		Menu AnnotationSchemaMenu = new Menu(mntmAnnotationSchema);
		mntmAnnotationSchema.setMenu(AnnotationSchemaMenu);
		
		MenuItem mntmVoID = new MenuItem(AnnotationSchemaMenu, SWT.RADIO);
		mntmVoID.setText("VoID");
		MenuItem mntmVoID_Semagrow = new MenuItem(AnnotationSchemaMenu, SWT.RADIO);
		mntmVoID_Semagrow.setText("VoID/SemaGrow extension");
		mntmVoID_Semagrow.setSelection(true);
		
		
		MenuItem mntmDataSchema = new MenuItem(menu, SWT.CASCADE);
		mntmDataSchema.setText("&Data Schema");
		
		dataSchemaMenu = new Menu(mntmDataSchema);
		mntmDataSchema.setMenu(dataSchemaMenu);
		
		MenuItem mntmNew = new MenuItem(dataSchemaMenu, SWT.PUSH);
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
        			addVocabularyToMenu(dialog.open(), dataSchemaMenu);
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
		
		/*MenuItem mntmSkos = new MenuItem(dataSchemaMenu, SWT.CHECK);
		mntmSkos.setText("skos.rdf");*/
		
		MenuItem mntmCrop = new MenuItem(dataSchemaMenu, SWT.CHECK);
		mntmCrop.setText("crop.owl");
		
		MenuItem mntmTf = new MenuItem(dataSchemaMenu, SWT.CHECK);
		mntmTf.setText("t4f.owl");
		
		
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
		
		textEndpoint = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		textEndpoint.setBounds(388, 30, 699, 24);
		
		Label lblEnpoint = new Label(this, SWT.NONE);
		lblEnpoint.setBounds(318, 30, 64, 18);
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
					for (MenuItem menuItem : dataSchemaMenu.getItems()) {
						if (menuItem.getSelection()) {
							has_vocabulary = true;
							break; 
						}
					}
					if (has_vocabulary) {
						//treePerProperty.dispose();
						if (treePerProperty == null) {
							createPerPropertyTree();
							//fillPerPropertyTree(ontModel.listAllOntProperties().toList(), currentAuthor, treePerProperty.getItems()[0]);
						}
						treePerProperty.moveAbove(null);
					} else {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Choose a schema from the \"Data Schema\" menu first.");
		                box.open();
					}
				} else if (list.getSelection()[0].toString().equals("per entity")) {
					if (treePerEntity == null) {
						createPerEntityTree();
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
		
		textTitle = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		textTitle.setBounds(46, 30, 266, 24);
		
		createContents();
	}
	
	protected void fillPerPropertyTree(java.util.List<OntProperty> propertiesList, String author, TreeItem root) {
		/*TreeItem root = new TreeItem(treePerProperty, SWT.NONE);
		root.setText("root");*/
		ArrayList<OntProperty> notInsertedPropertiesList = new ArrayList<OntProperty>();
		for (OntProperty ontProperty : propertiesList) {
			OntProperty superProperty = ontProperty.getSuperProperty();
			if (superProperty == null) {
				TreeItem treeItem = new TreeItem(root, SWT.NONE);
				treeItem.setText("?s " + ontProperty.toString() + " ?o");
				PerPropertyNode property = new PerPropertyNode(ontProperty);
				property.setDc_creator(author);
				treeItem.setData(property);
			} else {	
					boolean inserted = insertChildInTree(root, ontProperty, superProperty, author);
					if ( ! inserted ) {
						notInsertedPropertiesList.add(ontProperty);
					}
			}
		}
		while ( ! notInsertedPropertiesList.isEmpty()) {
			Collections.rotate(notInsertedPropertiesList, 1);
			OntProperty ontProperty = notInsertedPropertiesList.get(0);
			OntProperty superProperty = ontProperty.getSuperProperty();
			boolean inserted = insertChildInTree(root, ontProperty, superProperty, author);
			if (inserted) {
				notInsertedPropertiesList.remove(0);
			}
		}
	}
	
	
	protected boolean insertChildInTree(TreeItem treeItem, OntProperty ontProperty, OntProperty superProperty, String author) {
		boolean inserted = false;
		for(TreeItem child : treeItem.getItems()) {
			if (((PerPropertyNode) child.getData()).getOntProperty().equals(superProperty)) {
				TreeItem newtreeItem = new TreeItem(child, SWT.NONE);
				newtreeItem.setText(ontProperty.toString());
				PerPropertyNode property = new PerPropertyNode(ontProperty);
				property.setDc_creator(author);
				newtreeItem.setData(property);
				inserted = true;
			}
			if ((!inserted) && child.getItemCount()>0) {//if not leaf check the children
				inserted = insertChildInTree(child, ontProperty, superProperty, author);
			}
		}
		return inserted;
	}
	
	private void createPerPropertyTree() {
		treePerProperty = new Tree(this, SWT.BORDER);
		treePerProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//check to avoid strange bug when the listener is activated but no selection exists
				//causing java.lang.ArrayIndexOutOfBoundsException: 0
				if (treePerProperty.getSelection().length==0) {
					textTitle.setText("");
					textEndpoint.setText("");
					if ( table!=null && !table.isDisposed() ) {
						table.dispose();
						table = null;
					}
					return;
				}
				if (treePerProperty.getSelection()[0].getText().equals("root")) {
					textTitle.setText("");
					textEndpoint.setText("");
					if ( table!=null && !table.isDisposed() ) {
						table.dispose();
						table = null;
					}
					return;
				}
				TreeNodeData treeNodeData = (TreeNodeData) treePerProperty.getSelection()[0].getData();
				if (treeNodeData.getDc_title() != null) {
					textTitle.setText(treeNodeData.getDc_title());
				} else {
					textTitle.setText("");
				}
				if (treeNodeData.getVoid_sparqlEnpoint() != null) {
					textEndpoint.setText(treeNodeData.getVoid_sparqlEnpoint());
				} else {
					textEndpoint.setText("");
				}
				createTableContents(treePerProperty);
			}
		});
		treePerProperty.setBounds(318, 84, 369, 578);
		
		TreeItem root = new TreeItem(treePerProperty, SWT.NONE);
		root.setText("root");
		
		//insert menu for this tree
		final Menu treeMenu = new Menu(treePerProperty);
		treePerProperty.setMenu(treeMenu);
				
		final MenuItem insertNewDatasource = new MenuItem(treeMenu, SWT.NONE);
		insertNewDatasource.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerProperty.getSelection();
				if (selected.length > 0/* && selected[0].getText().equals("root")*/) {
					InsertInMenuDialog insert = new InsertInMenuDialog(getShell());
					String label = insert.open("Insert dataset label");
					if (label == null) return;
					TreeItem newItem = new TreeItem(selected[0], SWT.NONE);
					TreeNodeData data = new TreeNodeData();
					data.setDc_creator(currentAuthor);
					newItem.setData(data);
					newItem.setText(label);
				}
			}
		});
		insertNewDatasource.setText("Insert dataset label");
		
		new MenuItem(treeMenu, SWT.SEPARATOR);

	    final MenuItem remove = new MenuItem(treeMenu, SWT.NONE);
	    remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerProperty.getSelection();
				if (selected.length > 0) {
					TreeItem itemToDelete = selected[0];
					if (itemToDelete.getText().equals("root")) {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Cannot remove root node.");
		                box.open();
		                return;
					}
					itemToDelete.dispose();//simple delete. to be changed later.
					//String name =  itemToDelete.getText();
					//TreeItem parent = itemToDelete.getParentItem();
					//while(deleteNode(parent, name));//We suppose that the user can select only one item to remove.
					//treePerEntity.select(parent);
				} else {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
				}
			}

		});
	    remove.setText("Remove");
		
	}
	
	private void createPerEntityTree() {
		treePerEntity = new Tree(this, SWT.BORDER);
		treePerEntity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//check to avoid strange bug when the listener is activated but no selection exists
				//causing java.lang.ArrayIndexOutOfBoundsException: 0
				if (treePerEntity.getSelection().length==0) {
					textTitle.setText("");
					textEndpoint.setText("");
					if ( table!=null && !table.isDisposed() ) {
						table.dispose();
						table = null;
					}
					return;
				}
				if (treePerEntity.getSelection()[0].getText().equals("root")) {
					textTitle.setText("");
					textEndpoint.setText("");
					if ( table!=null && !table.isDisposed() ) {
						table.dispose();
						table = null;
					}	
					return;
				}
				TreeNodeData treeNodeData = (TreeNodeData) treePerEntity.getSelection()[0].getData();
				if (treeNodeData.getDc_title() != null) {
					textTitle.setText(treeNodeData.getDc_title());
				} else {
					textTitle.setText("");
				}
				if (treeNodeData.getVoid_sparqlEnpoint() != null) {
					textEndpoint.setText(treeNodeData.getVoid_sparqlEnpoint());
				} else {
					textEndpoint.setText("");
				}
				createTableContents(treePerEntity);
			}
		});
		treePerEntity.setBounds(318, 84, 369, 578);
		
		TreeItem root = new TreeItem(treePerEntity, SWT.NONE);
		root.setText("root");
		
		//insert menu for this tree
		final Menu treeMenu = new Menu(treePerEntity);
		treePerEntity.setMenu(treeMenu);
		
		final MenuItem insertNewDatasource = new MenuItem(treeMenu, SWT.NONE);
		insertNewDatasource.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0/* && selected[0].getText().equals("root")*/) {
					InsertInMenuDialog insert = new InsertInMenuDialog(getShell());
					String label = insert.open("Insert dataset label");
					if (label == null) return;
					TreeItem newItem = new TreeItem(selected[0], SWT.NONE);
					TreeNodeData data = new TreeNodeData();
					data.setDc_creator(currentAuthor);
					newItem.setData(data);
					newItem.setText(label);
				}
			}
		});
		insertNewDatasource.setText("Insert dataset label");
		
		final MenuItem insertNewChild = new MenuItem(treeMenu, SWT.NONE);
	    insertNewChild.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0) {
					if (selected[0].getText().equals("root")){
						MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("You cannot add a subset directly under root.");
		                box.open();
		                return;
					}
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
						/*if (subjectPattern != null) {
							itemText += "(sbj)=" + subjectPattern + " ";
						}
						if (objectPattern != null) {
							itemText += "(obj)=" + objectPattern;
						}*/
						if (subjectPattern == null) {
							itemText += "?s ";
						} else {
							itemText += subjectPattern + " ";
						}
						itemText += "?p ";
						if (objectPattern == null) {
							itemText += "?o";
						} else {
							itemText += objectPattern;
						}
						//TODO:check if node already exists.
						item.setText(itemText);
					}
					//System.out.println("Insert Into - " + selected[0].getText());
				} else {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
				}
			}

		});
	    insertNewChild.setText("Insert new subset");
	    
	    final MenuItem insertExistingChild = new MenuItem(treeMenu, SWT.NONE);
	    insertExistingChild.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0) {
					CopyExistingNodeDialog dialogCopy = new CopyExistingNodeDialog(shell);
					dialogCopy.copy(treePerEntity, selected[0]);
				} else {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
				}
			}

		});
	    insertExistingChild.setText("Add existing dataset or subset as child");
	    
	    new MenuItem(treeMenu, SWT.SEPARATOR);
	    
	    final MenuItem remove = new MenuItem(treeMenu, SWT.NONE);
	    remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerEntity.getSelection();
				if (selected.length > 0) {
					TreeItem itemToDelete = selected[0];
					if (itemToDelete.getText().equals("root")) {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Cannot remove root node.");
		                box.open();
		                return;
					}
					itemToDelete.dispose();//simple delete. to be changed later.
				} else {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
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
	
	//protected void createTableContents(String triples, String subjects, String objects, String sparqlEnpoint, String title, final Tree tree) {
	protected void createTableContents(/*TreeNodeData treeNodeData, */final Tree tree) {	
		
		if (table != null) {
			table.dispose();
		}
		createTable();
		
		TreeNodeData treeNodeData = ((TreeNodeData) tree.getSelection()[0].getData());
		
		final int voc = 0;
		int i = 0;
		while(TreeNodeData.property_names[voc][i] != null) {
			TableItem item = new TableItem(table, SWT.NONE);
			Object value = treeNodeData.property_values[voc][i];
			String value_str = null;
			if (value != null) {
				value_str = value.toString();
			}
			item.setText(new String [] {TreeNodeData.property_names[voc][i], value_str});
			i++;
		}
		
		final TableEditor editor = new TableEditor (table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String creator = ((TreeNodeData) tree.getSelection()[0].getData()).getDc_creator();
				if ( ! creator.equals(currentAuthor)) {
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
				
				 if (property.equals("void:vocabulary")) {
					SelectVocabulariesDialog dialog = new SelectVocabulariesDialog(getShell());
					java.util.List<String> selectedVocabularies = dialog.open(dataSchemaMenu);
					if (selectedVocabularies.isEmpty()) {
						return;
					}
					OntModel ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
					for (String selectedVocabulary : selectedVocabularies) {
						item.setText(1, item.getText(1) + selectedVocabulary + ", ");
						for (MenuItem menuItem : dataSchemaMenu.getItems()) {
							if (menuItem.getSelection()) {
								if (selectedVocabulary.equals(menuItem.getText())) {
									/*if (menuItem.getText().equals("skos.rdf")) {
										ontModel.read("file:////" + (new File("vocabularies/skos.rdf")).getAbsolutePath());
									}*/
									if (menuItem.getText().equals("crop.owl")) {
										ontModel.read("file:////" + (new File("schemas/crop.owl")).getAbsolutePath());
									}
									else if (menuItem.getText().equals("t4f.owl")) {
										ontModel.read("file:////" + (new File("schemas/t4f.owl")).getAbsolutePath());
									} else {
										ontModel.read("file:////" + (new File((String) menuItem.getData())).getAbsolutePath());
									}
								}
							}
						}
					}
					item.setText(1, item.getText(1).substring(0, item.getText(1).length() - 2));
					fillPerPropertyTree(ontModel.listAllOntProperties().toList(), currentAuthor, tree.getSelection()[0]);
					return;
				 }
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
						//String oldValue = editor.getItem().getText(1);
						//System.out.println(editor.getItem().getText(1));
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(1, text.getText());
						
						int i = 0; int index = -1;
						while( (index < 0) && (TreeNodeData.property_names[0][i] != null) ) {
							if( property.equals(TreeNodeData.property_names[0][i]) ) { index = i; }
							++i;
						}
						
						assert(i>=0);
							
						TreeNodeData treeNodeData = ((TreeNodeData) tree.getSelection()[0].getData());
						//Class<?> objClass = TreeNodeData.property_value_types[voc][index].getClass();
						//boolean functional = TreeNodeData.property_is_functional[voc][index];
						//Class<?> params[] = new Class[1];
						//params[0] = String.class;
						try {
							//java.lang.reflect.Constructor<?> constr = objClass.getConstructor( params );
							//Object o = constr.newInstance( text.getText() );
							Class<?> cls[] = new Class[] { String.class };
							Constructor<?> c = ((Class<?>) TreeNodeData.property_value_types[voc][index]).getConstructor(cls);
							Object obj = c.newInstance(text.getText());
							treeNodeData.property_values[0][index] = obj;
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							MessageBox box = new MessageBox(getShell(), SWT.ERROR);
			                box.setText("Error");
			                box.setMessage("Invalid input!");
			                box.open();
			                
						}
						
						if (property.equals("void:sparqlEndpoint")) {
							//((TreeNodeData) tree.getSelection()[0].getData()).setVoid_sparqlEnpoint(text.getText());
							textEndpoint.setText(text.getText());
							return;
						} else if (property.equals("dc:title")) {
							//((TreeNodeData) tree.getSelection()[0].getData()).setDc_title(text.getText());
							textTitle.setText(text.getText());
							return;
						}
						/*} catch (NumberFormatException e) {
							MessageBox box = new MessageBox(getShell(), SWT.ERROR);
			                box.setText("Error");
			                box.setMessage("Input must be integer!");
			                box.open();
						}*/
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, 1);
			}
		});
	}
	
	private void save(Tree perPropertyTree, Tree perEntityTree) throws ParserConfigurationException, TransformerException {
		
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
			rootElement.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			
			//moved to node attribute
			/*Element title = doc.createElement("dc:title");
			title.appendChild(doc.createTextNode(textTitle.getText()));
			rootElement.appendChild(title);*/
			
			//moved to node attribute
			/*Element endpoint = doc.createElement("void:sparqlEndpoint");
			endpoint.appendChild(doc.createTextNode(textEndpoint.getText()));
			rootElement.appendChild(endpoint);*/
			
			//per property
			Element perPropertyTreeElement = doc.createElement("facet");
			perPropertyTreeElement.setAttribute("type", "per_property");
			rootElement.appendChild(perPropertyTreeElement);
			
			Element treeRootProperty = doc.createElement("node");
			treeRootProperty.setAttribute("name", "root");
			perPropertyTreeElement.appendChild(treeRootProperty);
			
			createDOMFromTree(perPropertyTree.getItems()[0], treeRootProperty, doc);
			
			//per entity
			Element perEntityTreeElement = doc.createElement("facet");
			perEntityTreeElement.setAttribute("type", "per_entity");
			rootElement.appendChild(perEntityTreeElement);
			
			Element treeRootEntity = doc.createElement("node");
			treeRootEntity.setAttribute("name", "root");
			perEntityTreeElement.appendChild(treeRootEntity);
			
			createDOMFromTree(perEntityTree.getItems()[0], treeRootEntity, doc);
			
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
	
	private void saveAs(Tree perPropertyTree, Tree perEntityTree) throws ParserConfigurationException, TransformerException {
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
	
	
	private void createDOMFromTree(TreeItem treeItem, Element root, Document doc) {
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			Element treeItemNode = doc.createElement("node");
			// System.out.println(treeItem);
			//treeItemNode.setAttribute("name", ((TreeNodeData) treeItemCurrent.getData()).getOntProperty().toString());
			treeItemNode.setAttribute("name", treeItemCurrent.getText());
			TreeNodeData treeNodeData = (TreeNodeData) treeItemCurrent.getData();
			treeItemNode.setAttribute("dc:creator", treeNodeData.getDc_creator());
			Integer void_size = treeNodeData.getVoid_triples();
			if (void_size != null) {
				treeItemNode.setAttribute("void:triples", void_size.toString());
			}
			Integer void_distinctSubjects = treeNodeData.getVoid_distinctSubjects();
			if (void_distinctSubjects != null) {
				treeItemNode.setAttribute("void:distinctSubjects", void_distinctSubjects.toString());
			}
			Integer void_distinctObjects = treeNodeData.getVoid_distinctObjects();
			if (void_distinctObjects != null) {
				treeItemNode.setAttribute("void:distinctObjects", void_distinctObjects.toString());
			}
			treeItemNode.setAttribute("void:sparqlEndpoint", treeNodeData.getVoid_sparqlEnpoint());
			treeItemNode.setAttribute("dc:title", treeNodeData.getDc_title());
			if (treeItemCurrent.getData() instanceof PerEntityNode) {
				PerEntityNode perEntityNode = (PerEntityNode) treeItemCurrent.getData();
				String subjectPattern = perEntityNode.getSubjectPattern();
				String objectPattern = perEntityNode.getObjectPattern();
				if (subjectPattern != null) {
					Element subjectElement = doc.createElement("rdf:subject");
					subjectElement.setAttribute("void:uriRegexPattern", subjectPattern);
					treeItemNode.appendChild(subjectElement);
				}
				if (objectPattern != null) {
					Element objectElement = doc.createElement("rdf:object");
					objectElement.setAttribute("void:uriRegexPattern", objectPattern);
					treeItemNode.appendChild(objectElement);
				}
			} else if (treeItemCurrent.getData() instanceof PerPropertyNode) {
				PerPropertyNode perPropertyNode = (PerPropertyNode) treeItemCurrent.getData();
				OntProperty ontProperty = perPropertyNode.getOntProperty();
				Element ontPropertyElement = doc.createElement("rdf:Property");
				ontPropertyElement.setAttribute("rdf:about", ontProperty.toString());
				treeItemNode.appendChild(ontPropertyElement);
			}
			//treeItemNode.setAttribute("parent", treeItem.getText());
			root.appendChild(treeItemNode);
			if (treeItemCurrent.getItemCount() > 0) {
				createDOMFromTree(treeItemCurrent, treeItemNode, doc);
			}
		}
	}
	
	private void open(String filename) throws ParserConfigurationException, SAXException, IOException, Exception{
		
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
			
			//moved to node attribute
			/*NodeList nEnpointTitle = doc.getElementsByTagName("void:sparqlEndpoint");
			this.textEndpoint.setText(nEnpointTitle.item(0).getTextContent());*/
			
			/*
			treePerProperty.dispose();
			table.dispose();
			createTree();
			createTable();
			*/
			
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			
			//create the per property tree
			createPerPropertyTree();
			XPathExpression expr = xpath.compile("//facet[@type=\"per_property\"]");
			NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			Element eElement = (Element) nodeList.item(0);
			Element root = (Element) eElement.getFirstChild();
			NodeList childrenList = root.getChildNodes();
			for (int i = 0; i < childrenList.getLength(); i++) {
				createTreeFromDOM(childrenList.item(i), treePerProperty, "per_property", treePerProperty.getItems()[0]);
			}
			treePerProperty.moveAbove(null);
			
			//create the per entity treePerProperty
			createPerEntityTree();
			expr = xpath.compile("//facet[@type=\"per_entity\"]");
			nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			eElement = (Element) nodeList.item(0);
			root = (Element) eElement.getFirstChild();
			childrenList = root.getChildNodes();
			for (int i = 0; i < childrenList.getLength(); i++) {
				createTreeFromDOM(childrenList.item(i), treePerEntity, "per_property", treePerEntity.getItems()[0]);
			}
			treePerEntity.moveAbove(null);

	}
	
	
	private TreeNodeData getDataFromExistingTreeItem(TreeItem treeItem, String name, String dc_creator) {
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			TreeNodeData treeNodeData = (TreeNodeData) treeItemCurrent.getData();
			if (treeNodeData.getDc_creator().equals(dc_creator) && treeItemCurrent.getText().equals(name)) {
				return (TreeNodeData) treeItemCurrent.getData();
			}
			if (treeItemCurrent.getItemCount() > 0) {
				getDataFromExistingTreeItem(treeItemCurrent, name, dc_creator);
			}
		}
		return null;
	}
	
	private void addVocabularyToMenu(String filename, Menu vocabulariesMenu) {
		if (filename == null) {
			return;
		}
		File file = new File(filename);
		MenuItem mntmNewVoc = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmNewVoc.setText(file.getName());
		mntmNewVoc.setData(filename);
	}
	
	
	private boolean hasSelectedAuthor(Menu AuthorMenu) {
		for (MenuItem item : AuthorMenu.getItems()) {
			if (item.getSelection()) {
				return true;
			}
		}
		return false;
	}
	
	/*
	private boolean deleteNode(TreeItem treeItem, String nodeNameToDelete) {
		int index = 0;
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
		}
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
	*/
	
	private void createTreeFromDOM(Node node, Tree tree, String facetType, TreeItem parentTreeItem) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			TreeItem rootItem =tree.getItems()[0];
			
			Element eElement = (Element) node;
			String name = eElement.getAttribute("name");
			String dc_creator = eElement.getAttribute("dc:creator");
			
			TreeNodeData nodeData = getDataFromExistingTreeItem(rootItem, name, dc_creator);
			
			TreeItem treeItem = new TreeItem(parentTreeItem, SWT.NONE);
			treeItem.setText(name);
			
			if (nodeData == null) {
				Integer void_triples = null;
				String void_size_str = eElement.getAttribute("void:triples");
				if ( ! void_size_str.equals("")) {
					void_triples = new Integer(void_size_str);
				}			
				Integer void_distinctSubjects = null;
				String void_distinctSubjects_str = eElement.getAttribute("void:distinctSubjects");
				if ( ! void_distinctSubjects_str.equals("")) {
					void_distinctSubjects = new Integer(void_distinctSubjects_str);
				}		
				Integer void_distinctObjects = null;
				String void_distinctObjects_str = eElement.getAttribute("void:distinctObjects");
				if ( ! void_distinctObjects_str.equals("")) {
					void_distinctObjects = new Integer(void_distinctObjects_str);
				}
				String void_sparqlEndpoint = eElement.getAttribute("void:sparqlEndpoint");
				if (void_sparqlEndpoint.equals("")) {
					void_sparqlEndpoint = null;
				}
				String dc_title = eElement.getAttribute("dc:title");
				if (dc_title.equals("")) {
					dc_title = null;
				}
				
				//create data to insert
				if (facetType.equals("per_property")) {
					PerPropertyNode data = new PerPropertyNode(void_triples, void_distinctSubjects, void_distinctObjects, dc_creator, void_sparqlEndpoint, dc_title);
					OntModel ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
					NodeList nodeList = node.getChildNodes();
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node currentNode = nodeList.item(i);
						if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
							if (currentNode.getNodeName().equals("rdf:Property")) {
								Element element = (Element) currentNode;
								String rdf_about = element.getAttribute("rdf:about");
								OntProperty ontProperty = ontModel.createOntProperty(rdf_about);
								data.setOntProperty(ontProperty);
							}
						}
					}
					treeItem.setData(data);
				} else if (facetType.equals("per_entity")) {
					PerEntityNode data = new PerEntityNode(void_triples, void_distinctSubjects, void_distinctObjects, dc_creator, void_sparqlEndpoint, dc_title);
					NodeList nodeList = node.getChildNodes();
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node currentNode = nodeList.item(i);
						if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
							if (currentNode.getNodeName().equals("rdf:subject")) {
								Element element = (Element) currentNode;
								String subjectPattern = null;
								String uriRegexPattern = element.getAttribute("void:uriRegexPattern");
								if (uriRegexPattern.equals("")) {
									subjectPattern = null;
								}
								data.setSubjectPattern(subjectPattern);
							} else if  (currentNode.getNodeName().equals("rdf:object")) {
								Element element = (Element) currentNode;
								String objectPattern = null;
								String uriRegexPattern = element.getAttribute("void:uriRegexPattern");
								if (uriRegexPattern.equals("")) {
									objectPattern = null;
								}
								data.setSubjectPattern(objectPattern);
							}
						}
					}
					treeItem.setData(data);
				}
			} else {
				treeItem.setData(nodeData);
			}
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node currentNode = nodeList.item(i);
				if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equals("node")) {
					createTreeFromDOM(currentNode, tree, facetType, treeItem);
				}
			}
		}
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
		treePerProperty.dispose();
		treePerProperty = new Tree(this, SWT.BORDER);
		treePerProperty.setBounds(318, 79, 283, 583);
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
