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
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; Roboskel 2010-2011)
@author Stasinos Konstantopoulos (INDIGO, 2009; Roboskel 2011; SemaGrow 2012-2014)
@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.custom.TableEditor;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;

import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.annotations.Annotator;
import gr.demokritos.iit.eleon.annotations.AnnotatorList;
import gr.demokritos.iit.eleon.annotations.NominalSet;
import gr.demokritos.iit.eleon.commons.Constants;
import gr.demokritos.iit.eleon.facets.*;
import gr.demokritos.iit.eleon.facets.dataset.*;
import gr.demokritos.iit.eleon.ui.*;
import gr.demokritos.iit.eleon.persistence.*;


public class MainShell extends Shell
{
	public static MainShell shell;

	// Menu Items
	private Menu dataSchemaMenu;
	public final AnnotatorList annotators;
	//Menu annotationSchemaMenu;
	public String activeAnnSchemaName;
	public Integer activeAnnSchema;
	
	// infoboxes across the top of the screen
	protected Text textEndpoint;
	private Text textTitle;
	
	//facet list at the left
	protected List list;

	// hierarchy trees in the middle
	TreeFacet propertyTree, entityTree;
	
	// property values at the right
	public Table table;
	
	//persistence
	public PersistenceBackend persistence;

	//Ontology model
	public OntModel data = AnnotationVocabulary.getNewModel( AnnotationVocabulary.SEVOD );


	/**
	 * Launch the application.
	 * @param args
	 */

	public static void main( String args[] )
	{
		try {
			Display display = Display.getDefault();
			MainShell.shell = new MainShell(display);
			MainShell.shell.persistence = new OWLFile( MainShell.shell );
			// shell.persistence = new ELEONXML();
			MainShell.shell.open();
			MainShell.shell.layout();
			while( !MainShell.shell.isDisposed() ) {
				if( !display.readAndDispatch() ) {
					display.sleep();
				}
			}
		}
		catch( Exception ex ) {
			ex.printStackTrace();
		}
	}


	/**
	 * Create the main shell.
	 * @param display
	 */

	public MainShell( Display display )
	{
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
		
		/*propertyTree = new PropertyTreeFacet( this );
		entityTree = new EntityInclusionTreeFacet( this );*/
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
        openItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		FileDialog dialog = new FileDialog (shell, SWT.OPEN);
        		String [] filterNames = new String [] {"TTL Files", "XML Files", "All Files (*)"};
        		String [] filterExtensions = new String [] {"*.ttl", "*.xml", "*"};
        		//String filterPath = "/";
        		String filterPath = System.getProperty("user.dir");
        		String platform = SWT.getPlatform();
        		if (platform.equals("win32") || platform.equals("wpf")) {
        			filterNames = new String [] {"TTL Files", "XML Files", "All Files (*.*)"};
        			filterExtensions = new String [] {"*.ttl", "*.xml", "*.*"};
        			//filterPath = "c:\\";
        		}
        		dialog.setFilterNames (filterNames);
        		dialog.setFilterExtensions (filterExtensions);
        		dialog.setFilterPath (filterPath);
        		//dialog.setFileName ("myfile");
        		try {
        			String openFilename = dialog.open();
        			if( openFilename == null ) { return; }
        			MainShell.shell.persistence.open( openFilename );
					textTitle.setText( MainShell.shell.persistence.getLabel() );
					//create the faceted trees
					MainShell.shell.propertyTree.init();
					MainShell.shell.textTitle.setText( propertyTree.getTitle() );
					MainShell.shell.textEndpoint.setText( propertyTree.getInfo() );
					MainShell.shell.propertyTree.syncFrom( MainShell.shell.data );

					MainShell.shell.entityTree.init();
					MainShell.shell.textTitle.setText( MainShell.shell.entityTree.getTitle() );
					MainShell.shell.textEndpoint.setText( MainShell.shell.entityTree.getInfo() );
					MainShell.shell.entityTree.syncFrom( MainShell.shell.data );
				}
        		catch( Exception e ) {
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
        			Facet[] arr = new Facet[2];
        			arr[0] = propertyTree;
        			arr[1] = entityTree;
					boolean ok = persistence.save( arr, null );
					if( !ok ) {
						saveAs( propertyTree.getTree(), entityTree.getTree() );
					}
				}
        		catch( Exception ex ) {
					ex.printStackTrace();
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(ex.toString());
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
					saveAs( propertyTree.getTree(), entityTree.getTree() );
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
        exitItem.setText( "&Exit" );
		
		MenuItem mntmAuthor = new MenuItem(menu, SWT.CASCADE);
		mntmAuthor.setText( "&Annotator" );
		
		this.annotators = new AnnotatorList( mntmAuthor );
		mntmAuthor.setMenu( this.annotators.annMenu );
		
		MenuItem mntmNewAuthor = new MenuItem(this.annotators.annMenu, SWT.PUSH);
		mntmNewAuthor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InsertInMenuDialog dialog = new InsertInMenuDialog(shell);
				final String annotatorName = dialog.open("Insert new annotator");
				if (annotatorName != null && ( ! annotatorName.equals("") )) {
					boolean not_found = true;
					for (MenuItem item : annotators.annMenu.getItems()) {
						if (item.getText().equals(annotatorName)) {
							MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
			                box.setText("Annotator name already used");
			                box.setMessage("Annotator name \"" + annotatorName + "\" already used");
			                box.open();
			                not_found = false;
			                break;
						}
					}
					if (not_found) {
						MenuItem mntmInsertedAuthor = new MenuItem(annotators.annMenu, SWT.RADIO);
						Annotator ann = Annotator.getAnnotator( annotatorName );
						MainShell.shell.annotators.insert( ann );
						mntmInsertedAuthor.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								MainShell.shell.annotators.setActive( annotatorName );
							}
						});
						mntmInsertedAuthor.setText(annotatorName);
					}
				}
			}
		});
		mntmNewAuthor.setText("New...");
		
		MenuItem mntmAnnotationSchema = new MenuItem(menu, SWT.CASCADE);
		mntmAnnotationSchema.setText("Annotation &Schema");
		
		Menu annotationSchemaMenu = new Menu(mntmAnnotationSchema);
		mntmAnnotationSchema.setMenu(annotationSchemaMenu);
		
		final MenuItem mntmVoID = new MenuItem(annotationSchemaMenu, SWT.RADIO);
		mntmVoID.setText("VoID");
		mntmVoID.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (mntmVoID.getSelection()) {
					//activeAnnotationSchemaIndex = 0;
					//activeAnnotationSchema = mntmVoID.getText();
				}
			}
		});
		
		final MenuItem mntmVoID_Semagrow = new MenuItem(annotationSchemaMenu, SWT.RADIO);
		mntmVoID_Semagrow.setText("Sevod");
		mntmVoID_Semagrow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (mntmVoID_Semagrow.getSelection()) {
					//activeAnnotationSchemaIndex = 1;
					//activeAnnotationSchema = mntmVoID_Semagrow.getText();
				}
			}
		});
		mntmVoID_Semagrow.setSelection(true);
		activeAnnSchema = 1;
		activeAnnSchemaName = mntmVoID_Semagrow.getText();
		
		
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
                box.setMessage("Eleon 3.0 \\ Copyright (c) 2001-2014 National Centre for Scientific Research \"Demokritos\"");
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
				if (table != null && !table.isDisposed()) {
					table.dispose();
					table = null;
				}
				if ( ! annotators.hasSelected() ) {
					MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Choose author");
	                box.setMessage("Choose an author from the \"Annotator\" menu first.");
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
					if( has_vocabulary ) {
						//treePerProperty.dispose();
						if (propertyTree.getTree() == null) {
							propertyTree.init();
							textTitle.setText( propertyTree.getTitle() );
							textEndpoint.setText( propertyTree.getInfo() );
							//propertyTree.update();
						}
						propertyTree.getTree().moveAbove( null );
					} else {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Choose a schema from the \"Data Schema\" menu first.");
		                box.open();
		                return;
					}
				} else if (list.getSelection()[0].toString().equals("per entity")) {
					if( entityTree.getTree() == null ) {
						entityTree.init();
						textTitle.setText( entityTree.getTitle() );
						textEndpoint.setText( entityTree.getInfo() );
					}
					entityTree.getTree().moveAbove( null );
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
		
		propertyTree = new PropertyTreeFacet( this );
		entityTree = new TriplePatternTreeFacet( this );
		
		createContents();
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
	public void createTableContents(/*TreeNodeData treeNodeData, */final Tree tree) {	
		
		if (table != null && !table.isDisposed()) {
			table.dispose();
			table = null;
		}
		createTable();
		
		final DatasetNode treeNodeData = ((DatasetNode) tree.getSelection()[0].getData());
		
		//final int schemaIndex = treeNodeData.getAnnotationSchemaIndex();
		final int schemaIndex = AnnotationVocabulary.SEVOD;
		
		int i = 0;
		while(AnnotationVocabulary.property_qnames[schemaIndex][i] != null) {
			if (AnnotationVocabulary.property_is_visible[schemaIndex][i]) {
				TableItem item = new TableItem(table, SWT.NONE);
				Object value = treeNodeData.property_values[schemaIndex][i];
				String value_str = null;
				if (value != null) {
					value_str = value.toString();
				}
				item.setText(new String [] {AnnotationVocabulary.property_qnames[schemaIndex][i], value_str});
			}
			i++;
		}
		
		final TableEditor editor = new TableEditor (table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (tree.getSelectionCount() == 0) {
					MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Select item from the tree.");
	                box.setMessage("Please select an item from the tree first.");
	                box.open();
	                MainShell.this.table.dispose();
	                MainShell.this.table = null;
	                return;
				}
				
				DatasetNode treeFacetNode = (DatasetNode) tree.getSelection()[0].getData();
				Resource creator = treeFacetNode.getOwner();
				assert creator != null;  
				if( ! creator.equals(MainShell.shell.annotators.getActiveResource()) ) {
					MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Value read-only");
	                box.setMessage("You cannot edit this value because it is owned by user \"" + creator.getLocalName() + "\".");
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
				
				 /*if( AnnotationVocabulary.property_qnames[0][0].equals(property)) {
					 item.setText(1, creator.getLocalName());
					 return;
				 }*/
				
				 if (property.equals("void:vocabulary")) {//TODO:breaks independence from property names!
					SelectVocabulariesDialog dialog = new SelectVocabulariesDialog( getShell(), treeNodeData );
					java.util.List<String> selectedVocabularies = dialog.open(dataSchemaMenu);
					if (selectedVocabularies.isEmpty()) {
						return;
					}
					//FIXME: do all auto-filled facets
					OntModel schema = AnnotationVocabulary.getNewModel( AnnotationVocabulary.NONE );
					for (String selectedVocabulary : selectedVocabularies) {
						item.setText(1, item.getText(1) + selectedVocabulary + ", ");
						for (MenuItem menuItem : dataSchemaMenu.getItems()) {
							if (menuItem.getSelection()) {
								if (selectedVocabulary.equals(menuItem.getText())) {
									/*if (menuItem.getText().equals("skos.rdf")) {
										ontModel.read("file:////" + (new File("vocabularies/skos.rdf")).getAbsolutePath());
									}*/
									if (menuItem.getText().equals("crop.owl")) {
										schema.read("file:////" + (new File("resources/schemas/crop.owl")).getAbsolutePath());
									}
									else if (menuItem.getText().equals("t4f.owl")) {
										schema.read("file:////" + (new File("resources/schemas/t4f.owl")).getAbsolutePath());
									} else {
										schema.read("file:////" + (new File((String) menuItem.getData())).getAbsolutePath());
									}
								}
							}
						}
					}
					((PropertyTreeFacet)MainShell.shell.propertyTree).update( tree.getSelection()[0], schema );
					
					String proper_text = item.getText(1).substring(0, item.getText(1).length() - 2);
					
					String[][] property_names = AnnotationVocabulary.property_qnames;
					int i = 0; int index = -1;
					while( (index < 0) && (property_names[schemaIndex][i] != null) ) {
						if("void:vocabulary".equals(property_names[schemaIndex][i]) ) { 
							index = i;
							break;
						}
						++i;
					}
					assert(i>=0);
					
					Object[][] property_values = ((DatasetNode) treeFacetNode).property_values;
					property_values[schemaIndex][index] = proper_text;//TODO:check what happens if user clicks cancel in the dialog
					
					item.setText(1, proper_text);
					if( dialog.node.getFacet().isAutoFilled() ) {
						dialog.node.getFacet().update();
					}
					return;
				 }
				 
				 if (property.equals("void:subjectsTarget")) {//TODO:breaks independence from property names!
					 String[][] property_names = AnnotationVocabulary.property_qnames;
					 int i = 0; int index = -1;
					 while( (index < 0) && (property_names[schemaIndex][i] != null) ) {
						 if("void:subjectsTarget".equals(property_names[schemaIndex][i]) ) { 
							 index = i;
							 break;
						 }
						 ++i;
					 }
					 assert(i>=0);
					 
					 Object nominalSet = ((DatasetNode) treeFacetNode).property_values[schemaIndex][index];
					 if (nominalSet == null) {
						 nominalSet = new NominalSet();
					 }/* else {
						 nominalSet = (NominalSet) nominalSet;
					 }*/
					 nominalSet = ((NominalSet) nominalSet);			
					 
					 SelectNominalSetDialog dialog = new SelectNominalSetDialog(getShell());
					 java.util.List<String> selectedNominals = dialog.select(NominalSet.availableNomilas, ((NominalSet) nominalSet).getContainingNominals());
					 if (selectedNominals.isEmpty()) {
						 ((DatasetNode) treeFacetNode).property_values[schemaIndex][index] = null;
						 return;
					 }
					 ((NominalSet) nominalSet).setContainingNominals(selectedNominals);
					 ((DatasetNode) treeFacetNode).property_values[schemaIndex][index] = (NominalSet) nominalSet;

					 item.setText(1, ((NominalSet) nominalSet).toString());
					 return;
				 }
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
						
						for (int i = 0; i < AnnotationVocabulary.property_qnames[schemaIndex].length; i++) {
							if (property.equals(AnnotationVocabulary.property_qnames[schemaIndex][i])) {
								if ( ! AnnotationVocabulary.property_is_editable[schemaIndex][i] ) {
									MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
					                box.setText("Value read-only");
					                box.setMessage("You cannot edit this value because it set as read-only by the chosen annotation schema.");
					                box.open();
					                return;
								}
							}
						}
						
						//String oldValue = editor.getItem().getText(1);
						//System.out.println(editor.getItem().getText(1));
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(1, text.getText());
						
						int i = 0; int index = -1;
						while( (index < 0) && (AnnotationVocabulary.property_qnames[schemaIndex][i] != null) ) {
							if( property.equals(AnnotationVocabulary.property_qnames[schemaIndex][i]) ) { index = i; }
							++i;
						}
						
						assert(i>=0);
							
						DatasetNode treeNodeData = ((DatasetNode) tree.getSelection()[0].getData());
						//Class<?> objClass = TreeNodeData.property_value_types[voc][index].getClass();
						//boolean functional = TreeNodeData.property_is_functional[voc][index];
						//Class<?> params[] = new Class[1];
						//params[0] = String.class;
						try {
							//java.lang.reflect.Constructor<?> constr = objClass.getConstructor( params );
							//Object o = constr.newInstance( text.getText() );
							Class<?> cls[] = new Class[] { String.class };
							java.lang.reflect.Constructor<?> c = ((Class<?>) AnnotationVocabulary.property_value_types[schemaIndex][index]).getConstructor(cls);
							Object obj = c.newInstance(text.getText());
							treeNodeData.property_values[schemaIndex][index] = obj;
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
						} catch (java.lang.reflect.InvocationTargetException e) {
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
	
	private void saveAs( Tree perPropertyTree, Tree perEntityTree )
	throws IOException
	{
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
		String filename = dialog.open();
		if( filename != null ) {
			Facet[] arr = new Facet[2];
			arr[0] = propertyTree;
			arr[1] = entityTree;
			boolean ok = persistence.save( arr, filename );
			assert ok;
		}
		//System.out.println ("Save to: " + dialog.open ());
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
