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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.custom.TableEditor;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.annotations.Annotator;
import gr.demokritos.iit.eleon.annotations.AnnotatorList;
import gr.demokritos.iit.eleon.annotations.DataSchema;
import gr.demokritos.iit.eleon.annotations.DataSchemaSet;
import gr.demokritos.iit.eleon.annotations.NominalSet;
import gr.demokritos.iit.eleon.facets.*;
import gr.demokritos.iit.eleon.facets.dataset.*;
import gr.demokritos.iit.eleon.ui.*;
import gr.demokritos.iit.eleon.persistence.*;


public class MainShell extends Shell
{
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( PropertyTreeFacet.class );
	
	public static MainShell shell;

	// Menu Items
	private Menu dataSchemaMenu;
	public final AnnotatorList annotators;
	//Menu annotationSchemaMenu;
	public String activeAnnSchemaName;
	public Integer activeAnnSchema;
	
	// infoboxes across the top of the screen
	public Text textEndpoint;
	public Text textTitle;
	
	//facet list at the left
	protected List list;

	// hierarchy trees in the middle
	TreeFacet propertyTree, entityTree;
	
	// property values at the right
	public Table table;
	
	//persistence
	public PersistenceBackend persistence;
	
	final int schemaIndex = AnnotationVocabulary.SEVOD;//TODO: to be changed when more schemas are added.

	//Ontology model
	public OntModel data = AnnotationVocabulary.getNewModel( schemaIndex );
	
	static final String schema_folder;
	static {
		String path = MainShell.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String jar_path = null;
		try {
			jar_path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		assert jar_path != null;
		String eleon_directory = (new File(jar_path)).getParent() + File.separator;
		schema_folder = eleon_directory + "resources" + File.separator + "schemas" + File.separator;
	}


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
			MainShell.shell.initializeFacets();
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
	 * @throws MalformedURLException 
	 */

	public MainShell( Display display ) throws MalformedURLException
	{
		//super(display, SWT.SHELL_TRIM);
		//forbid resize for now.
		super(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");
		
		Menu fileMenu = new Menu(this, SWT.DROP_DOWN);
		mntmFile.setMenu(fileMenu);
		
		MenuItem newItem = new MenuItem(fileMenu, SWT.PUSH);
		newItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		newButtonFunctionality();
        	}
        });
		newItem.setText("&New");
		
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
					MainShell.shell.propertyTree.getTree().setVisible(false);

					MainShell.shell.entityTree.init();
					MainShell.shell.textTitle.setText( MainShell.shell.entityTree.getTitle() );
					MainShell.shell.textEndpoint.setText( MainShell.shell.entityTree.getInfo() );
					MainShell.shell.entityTree.syncFrom( MainShell.shell.data );
					MainShell.shell.entityTree.getTree().setVisible(false);
				}
        		catch( Exception e ) {
					e.printStackTrace();
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(e.toString());
	                box.open();
				}
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
			    	MessageBox box = new MessageBox(getShell(), SWT.ERROR);
	                box.setText("Error");
	                box.setMessage(e.toString());
	                box.open();
				}
        	}
        });
        saveAsItem.setText("Save &As...");
        
        new MenuItem(fileMenu, SWT.SEPARATOR);
        
        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		int style = SWT.ICON_WARNING | SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;  
        		MessageBox messageBox = new MessageBox(MainShell.this, style);  
        		messageBox.setText("Exit Eleon?");  
        		messageBox.setMessage("Are you sure you want to exit?\n"
        				+ "Any unsaved progress will be lost.");  
        		if (messageBox.open() == SWT.YES) {
        			MainShell.this.dispose();
        		}
        		//getDisplay().dispose();
                //System.exit(0);
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
						for (MenuItem item : annotators.annMenu.getItems()) {
							item.setSelection(false);
						}
						mntmInsertedAuthor.setSelection(true);
						MainShell.shell.annotators.setActive( annotatorName );
						mntmInsertedAuthor.setText(annotatorName);
						mntmInsertedAuthor.setData(ann);
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
		activeAnnSchema = schemaIndex;
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
		
		assert MainShell.schema_folder != null;
		
		MenuItem mntmCrop = new MenuItem(dataSchemaMenu, SWT.CHECK);
		File crop_file = new File(MainShell.schema_folder + "crop.owl");
		Resource crop_r = data.createResource( crop_file.toURI().toURL().toString() );
		DataSchema crop_schema = new DataSchema(crop_file.getName(), crop_file, crop_r);
		mntmCrop.setText(crop_schema.getLabel());
		mntmCrop.setData(crop_schema);
		
		MenuItem mntmTf = new MenuItem(dataSchemaMenu, SWT.CHECK);
		File t4f_file = new File(MainShell.schema_folder + "t4f.owl");
		Resource t4f_r = data.createResource( t4f_file.toURI().toURL().toString() );
		DataSchema t4f_schema = new DataSchema(t4f_file.getName(), t4f_file, t4f_r);
		mntmTf.setText(t4f_schema.getLabel());
		mntmTf.setData(t4f_schema);
		
		MenuItem mntmOrganicEdunet = new MenuItem(dataSchemaMenu, SWT.CHECK);
		File organicEdunet_file = new File(MainShell.schema_folder + "organicEdunet.owl");
		Resource organicEdunet_r = data.createResource( organicEdunet_file.toURI().toURL().toString() );
		DataSchema organicEdunet_schema = new DataSchema(organicEdunet_file.getName(), organicEdunet_file, organicEdunet_r);
		mntmOrganicEdunet.setText(organicEdunet_schema.getLabel());
		mntmOrganicEdunet.setData(organicEdunet_schema);
		
		
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
		textEndpoint.setBounds(580, 30, 507, 24);
		textEndpoint.setVisible(false);
		
		Label lblEnpoint = new Label(this, SWT.NONE);
		lblEnpoint.setBounds(318, 30, 64, 18);
		lblEnpoint.setText("Enpdoint");
		lblEnpoint.setVisible(false);
		
		Label lbltree = new Label(this, SWT.NONE);
		lbltree.setBounds(202, 30, 283, 18);
		lbltree.setText("Hierarchy Tree");
		
		Label lblfacet = new Label(this, SWT.NONE);
		lblfacet.setBounds(10, 30, 76, 18);
		lblfacet.setText("Facet");
		
		Label lblFields = new Label(this, SWT.NONE);
		lblFields.setBounds(580, 36, 200, 18);
		lblFields.setText("Dataset Properties Editor");
		
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
				if (list.getSelection()[0].toString().equals("Per property")) {
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
						propertyTree.getTree().setVisible(true);
						propertyTree.getTree().moveAbove( null );
					} else {
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Choose a schema from the \"Data Schema\" menu first.");
		                box.open();
		                return;
					}
				} else if (list.getSelection()[0].toString().equals("Per entity")) {
					if( entityTree.getTree() == null ) {
						entityTree.init();
						textTitle.setText( entityTree.getTitle() );
						textEndpoint.setText( entityTree.getInfo() );
					}
					entityTree.getTree().setVisible(true);
					entityTree.getTree().moveAbove( null );
				}
			}
		});
		list.setBounds(10, 60, 186, 602);
		String[] listItems = {"Per property", "Per entity"};
		list.setItems(listItems);
		
		//createTree();
		
		//createTable();
				
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(10, 0, 1077, 24);
		
		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setBounds(10, 30, 30, 18);
		lblTitle.setText("Title");
		lblTitle.setVisible(false);
		
		textTitle = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		textTitle.setBounds(46, 30, 266, 24);
		textTitle.setVisible(false);
		
		//initializeFacets();
		
		createContents();
	}
	
	protected void createTable() {
		//table
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(580, 60, 507, 602);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//table columns
		TableColumn tblclmnProperty = new TableColumn(table, SWT.NONE);
		tblclmnProperty.setWidth(181);
		tblclmnProperty.setText("Property");
		
		TableColumn tblclmnValue = new TableColumn(table, SWT.NONE);
		tblclmnValue.setWidth(100);
		tblclmnValue.setText("Value");
		
		table.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT) {
					e.doit = false;
					if (table.getSelectionCount() == 1) {
						int index = table.indexOf(table.getSelection()[0]);
						int lenght = table.getItemCount();
						if (index == lenght - 1) {
							e.doit = true;
						} else {
							table.setSelection(index + 1);
							table.notifyListeners(SWT.Selection, new Event());
						}
					}
				} else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
					e.doit = false;
					if (table.getSelectionCount() == 1) {
						int index = table.indexOf(table.getSelection()[0]);
						if (index == 0) {
							e.doit = true;
						} else {
							table.setSelection(index - 1);
							table.notifyListeners(SWT.Selection, new Event());
						}
					}
				}
			}
		});
	}
	
	public void createTableContents(final Tree tree) {	
		
		if (table != null && !table.isDisposed()) {
			table.dispose();
			table = null;
		}
		
		createTable();
		
		final DatasetNode treeNodeData = ((DatasetNode) tree.getSelection()[0].getData());
		
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
				
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
		
				// Identify the selected row
				TableItem item = (TableItem)e.item;
				if (item == null) return;
				
				//Get Property name
				final String property = item.getText(0);
				
				//check if property is set editable
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
				
				
				 if (property.equals("void:vocabulary")) {//TODO:breaks independence from property names!
					SelectVocabulariesDialog dialog = new SelectVocabulariesDialog( getShell(), treeNodeData );
					java.util.List<String> selectedVocabularies = dialog.open(dataSchemaMenu);
					if ( selectedVocabularies.isEmpty() ) {
						return;
					}
					//FIXME: do all auto-filled facets
					OntModel schema = AnnotationVocabulary.getNewModel( AnnotationVocabulary.NONE );
					java.util.List<DataSchema> dataSchemaList = new ArrayList<DataSchema>();
					boolean loaded_dc = false;
					for (String selectedVocabulary : selectedVocabularies) {
						for (MenuItem menuItem : dataSchemaMenu.getItems()) {
							if (menuItem.getSelection()) {
								if (selectedVocabulary.equals(menuItem.getText())) {
									//schema.read("file:////" + ((DataSchema) menuItem.getData()).getSchemaFile().getAbsolutePath());
									try {
										if ( ! loaded_dc ) {
											//schema.read(schema_folder + "dcterms.rdf");
											schema.read(schema_folder + "dcelements.rdf");
											loaded_dc = true;
										}
										String path = ((DataSchema) menuItem.getData()).getSchemaFile().toURI().toURL().toString();
										String decoded_path = URLDecoder.decode(path, "UTF-8");
										schema.read(decoded_path);
										dataSchemaList.add((DataSchema) menuItem.getData());
									} catch (MalformedURLException ex) {
										ex.printStackTrace();
									} catch (UnsupportedEncodingException ex) {
										ex.printStackTrace();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						}
					}
					try {
						((PropertyTreeFacet)MainShell.shell.propertyTree).update( tree.getSelection()[0], schema );
					} catch (Exception exc) {//FIXME: some schemas could not be loaded. check why.
						logger.error("Error while inserting vocabulary", exc);
						MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_ERROR);
		                box.setText("Error while inserting vocabulary");
		                box.setMessage("Error while inserting vocabulary. Check log output for more information.");
		                box.open();
		                return;
					}
					
					
					//String proper_text = item.getText(1).substring(0, item.getText(1).length() - 2);
					
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
					if (dataSchemaList.isEmpty()) {
						property_values[schemaIndex][index] = null;
						item.setText(1, "[]");
					} else {
						DataSchemaSet schemaSet = new DataSchemaSet();
						schemaSet.setContainingSchemas(dataSchemaList);
						property_values[schemaIndex][index] = schemaSet;
						item.setText(1, schemaSet.toString());
					}
					
					if( dialog.node.getFacet().isAutoFilled() ) {
						dialog.node.getFacet().update();
					}
					tree.getSelection()[0].setExpanded(true);
					return;
				 }
				 
				 //nominal set
				 if ( property.equals("svd:subjectClass") || property.equals("svd:objectClass")) {

					 String[][] property_names = AnnotationVocabulary.property_qnames;
					 int i = 0; int index_class = -1; int index_vocabulary = -1;
					 while( property_names[schemaIndex][i] != null ) {
						 if( property.equals(property_names[schemaIndex][i]) ) { 
							 index_class = i;
							 //break;
						 } else if ( "void:vocabulary".equals(property_names[schemaIndex][i]) ) {
							 index_vocabulary = i;
						 }
						 ++i;
					 }
					 assert(i>=0);
					 assert(index_class>=0);
					 assert(index_vocabulary>=0);
					 
					 java.util.List<Resource> available_classes = new ArrayList<Resource>();
					 getAvailableClasses(tree.getSelection()[0], available_classes, schemaIndex, index_vocabulary);
					 
					 NominalSet nominalSet = (NominalSet) ((DatasetNode) treeFacetNode).property_values[schemaIndex][index_class];
					 if (nominalSet == null) {
						 nominalSet = new NominalSet();
					 }
					 
					 SelectNominalSetDialog dialog = new SelectNominalSetDialog(getShell());
					 java.util.List<Resource> selectedNominals = dialog.open(available_classes, ((NominalSet) nominalSet).getContainingNominals());
					 if (selectedNominals.isEmpty()) {
						 ((DatasetNode) treeFacetNode).property_values[schemaIndex][index_class] = null;
						 item.setText(1, "");
						 return;
					 }
					 nominalSet.setContainingNominals(selectedNominals);
					 ((DatasetNode) treeFacetNode).property_values[schemaIndex][index_class] = nominalSet;

					 item.setText(1, nominalSet.toString());
					 return;
				 }
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
		
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(1, text.getText());
						
						int i = 0; int index = -1;
						while( (index < 0) && (AnnotationVocabulary.property_qnames[schemaIndex][i] != null) ) {
							if( property.equals(AnnotationVocabulary.property_qnames[schemaIndex][i]) ) { index = i; }
							++i;
						}
						
						assert(i>=0);
							
						DatasetNode treeNodeData = ((DatasetNode) tree.getSelection()[0].getData());
						
						if (AnnotationVocabulary.property_value_types[schemaIndex][index] == Resource.class) {
							//TODO: check for valid URI here?
							treeNodeData.property_values[schemaIndex][index] = ResourceFactory.createResource(text.getText());
							if (property.equals("void:sparqlEndpoint")) {
								textEndpoint.setText(text.getText());
							}
							return;
						}
						
						try {
							Class<?> cls[] = new Class[] { String.class };
							java.lang.reflect.Constructor<?> c = ((Class<?>) AnnotationVocabulary.property_value_types[schemaIndex][index]).getConstructor(cls);
							Object obj = c.newInstance(text.getText());
							treeNodeData.property_values[schemaIndex][index] = obj;
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							me.getSource();/*this.restore =  true;*/
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
			                return;
						}
						
						if (property.equals("dc:title")) {
							/*if ( (treeNodeData instanceof TriplePatternTreeNode) && text.getText().equals("") ) {
								TriplePatternTreeNode data = (TriplePatternTreeNode) treeNodeData;
								String subjectPattern = data.getSubjectPattern();
								String objectPattern = data.getObjectPattern();
								String label = "";
								if (subjectPattern == null) {
									label += "?s ";
								} else {
									label += subjectPattern + " ";
								}
								label += "?p ";
								if (objectPattern == null) {
									label += "?o";
								} else {
									label += objectPattern;
								}
								text.setText(label);
							}*/
							tree.getSelection()[0].setText(text.getText());
							textTitle.setText(text.getText());
						}

					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, 1);
				
				newEditor.addTraverseListener(new TraverseListener() {
					public void keyTraversed(TraverseEvent e) {
						if (e.detail == SWT.TRAVERSE_TAB_NEXT) {
							e.doit = false;
							if (table.getSelectionCount() == 1) {
								int index = table.indexOf(table.getSelection()[0]);
								int lenght = table.getItemCount();
								if (index == lenght - 1) {
									e.doit = true;
								} else {
									Event ev = new Event();
									ev.detail = SWT.TRAVERSE_TAB_NEXT;
									ev.doit = true;
									table.notifyListeners(SWT.Traverse, ev);
									table.setFocus();
									table.notifyListeners(SWT.Selection, ev);
								}
							}
						} else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
							e.doit = false;
							if (table.getSelectionCount() == 1) {
								int index = table.indexOf(table.getSelection()[0]);
								if (index == 0) {
									e.doit = true;
								} else {
									//table.setSelection(index - 1);
									Event ev = new Event();
									ev.detail = SWT.TRAVERSE_TAB_PREVIOUS;
									ev.doit = true;
									table.notifyListeners(SWT.Traverse, ev);
									table.setFocus();
									table.notifyListeners(SWT.Selection, ev);
								}
							}
						}
					}
				});
			}
		});
	}
	
	private void saveAs( Tree perPropertyTree, Tree perEntityTree )
	throws IOException
	{
		FileDialog dialog = new FileDialog (shell, SWT.SAVE);
		dialog.setOverwrite(true);
		String [] filterNames = new String [] {"TTL Files", "XML Files", "All Files (*)"};
		String [] filterExtensions = new String [] {"*.ttl", "*.xml", "*"};
		//String filterPath = "/";
		String filterPath = System.getProperty("user.dir");
		String platform = SWT.getPlatform();
		if (platform.equals("win32") || platform.equals("wpf")) {
			filterNames = new String [] {"TTL Files", "XML Files", "All Files (*)"};
			filterExtensions = new String [] {"*.ttl", "*.xml", "*.*"};
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
	
	private void addVocabularyToMenu(String filename, Menu vocabulariesMenu) throws MalformedURLException {
		if (filename == null) {
			return;
		}
		/*File file = new File(filename);
		MenuItem mntmNewVoc = new MenuItem(vocabulariesMenu, SWT.CHECK);
		mntmNewVoc.setText(file.getName());
		mntmNewVoc.setData(filename);*/
		File schemaFile = new File(filename);
		MenuItem mntmNewVoc = new MenuItem(vocabulariesMenu, SWT.CHECK);
		Resource r = data.createResource( schemaFile.toURI().toURL().toString() );
		DataSchema dataSchema = new DataSchema(schemaFile.getName(), schemaFile, r);
		mntmNewVoc.setText(dataSchema.getLabel());
		mntmNewVoc.setData(dataSchema);
	}
	
	
	private void newButtonFunctionality() {
		int style = SWT.ICON_WARNING | SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;  
		MessageBox messageBox = new MessageBox(this, style);  
		messageBox.setText("Start new annotation?");  
		messageBox.setMessage("Are you sure you want start a new annotation session? Any unsaved progress will be lost.");  
		if (messageBox.open() == SWT.YES) {
			if (table != null && !table.isDisposed()) {
				table.dispose();
				table = null;
			}
			Tree tree = null;
			tree = entityTree.getTree();
			if (tree != null && !tree.isDisposed()) {
				tree.dispose();
				tree = null;
			}
			tree = propertyTree.getTree();
			if (tree != null && !tree.isDisposed()) {
				tree.dispose();
				tree = null;
			}
			
			initializeFacets();
		}
	}
	
	private void initializeFacets() {
		propertyTree = new PropertyTreeFacet( this );
		propertyTree.init();
		propertyTree.getTree().setVisible(false);
		entityTree = new TriplePatternTreeFacet( this );
		entityTree.init();
		entityTree.getTree().setVisible(false);
	}
	
	
	private void getAvailableClasses(final TreeItem treeItem, java.util.List<Resource> available_classes, final int schemaIndex, final int index_vocabulary) {
		if (treeItem == null) return;//finished tree
		DatasetNode datasetNode = (DatasetNode) treeItem.getData();
		assert datasetNode!=null;
		DataSchemaSet set = (DataSchemaSet) datasetNode.property_values[schemaIndex][index_vocabulary];
		if (set!=null && set.getContainingSchemas() != null && (!set.getContainingSchemas().isEmpty())) {
			for (Object data : set.getContainingSchemas()) {
				assert (data instanceof DataSchema);
				DataSchema dataSchema = (DataSchema) data;
				if (dataSchema.getLabel().equals("crop.owl")) {
					available_classes.addAll(Arrays.asList(NominalSet.cropClasses));
				} else if (dataSchema.getLabel().equals("t4f.owl")) {
					available_classes.addAll(Arrays.asList(NominalSet.t4fClasses));
				} else if (dataSchema.getLabel().equals("organicEdunet.owl")) {
					available_classes.addAll(Arrays.asList(NominalSet.organicEdunetClasses));
				} else {
					// TODO: what about new vocabularies added by the user?
				}
			}
			return;
		} else {
			getAvailableClasses(treeItem.getParentItem(), available_classes, schemaIndex, index_vocabulary);
		}
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
