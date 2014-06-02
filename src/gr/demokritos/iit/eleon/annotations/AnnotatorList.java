/***************

<p>Title: Annotator List</p>

<p>Description:

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

@author Stasinos Konstantopoulos (INDIGO, 2009; RoboSKEL 2011; SemaGrow 2012-2014)

***************/


package gr.demokritos.iit.eleon.annotations;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.ui.InsertInMenuDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/*
 * FIXME:
 * In various places in the code there is the assumption when referring to the
 * active annotator that login == uri.getLocalName()
 */

public class AnnotatorList
{
	public final Menu annMenu;
	private List<Annotator> annList;
	private Annotator active;
	
	public AnnotatorList( org.eclipse.swt.widgets.MenuItem parent )
	{
		this.annMenu = new Menu( parent );
		this.annList = new ArrayList<Annotator>();
		this.active = null;
	}

	public Resource getActiveResource()
	{
		if( this.active == null ) { return null; }
		else { return this.active.getResource(); }
	}
	
	public String getActiveLogin()
	{
		if( this.active == null ) { return null; }
		else { return this.active.getLogin(); }
	}
	
	public void setActive( Annotator newActive )
	{
		if( this.annList.contains(newActive) ) {
			this.active = newActive;
		}
	}
	
	public void insertAnnotator( String login ) {
		for( Annotator ann : this.annList ) {
			if( ann.getLogin().equals(login) ) {
				this.active = ann;
				return;
			}
		}
		Annotator ann = new Annotator(
				ResourceFactory.createResource("http://purl.org/dc/terms/identifier"
						+ login), login);//TODO: will wee use this resource?
		this.annList.add(ann);
	}
	
	public void setActive( String login )
	{
		for( Annotator ann : this.annList ) {
			if( ann.getLogin().equals(login) ) {
				this.active = ann;
				break;
			}
		}
	}
	
	public boolean hasSelected()
	{
		for( MenuItem item : this.annMenu.getItems() ) {
			if( item.getSelection() ) { return true; }
		}
		return false;
	}
	
	public void syncFrom( OntModel model )
	{
		annList.clear();
		Property p1 = model.getProperty( "http://www.w3.org/2007/05/powder-s#issuedby" );
		Property p2 = model.getProperty( "http://purl.org/dc/terms/identifier" );
		StmtIterator stmts = model.listStatements( null, p1, (RDFNode)null );
		while( stmts.hasNext() ) {
			RDFNode o = stmts.next().getObject();
			if( o.canAs(Resource.class) ) {
				StmtIterator stmts2 = model.listStatements( o.asResource(), p2, (RDFNode)null );
				if( stmts2.hasNext() ) {
					RDFNode o2 = stmts2.next().getObject();
					if( o2.canAs(Literal.class) ) {
						Annotator ann = new Annotator( o.asResource(), o2.asLiteral().getString() );
						if( !this.annList.contains(ann) ) { this.annList.add( ann ); }
					}
					else {
						//TODO: explode
					}
				}
			}
			else {
				//TODO: explode
			}
		}
		Collections.sort( this.annList );
		int n = this.annMenu.getItemCount();
		for( int i=0; i<n; ++i ) { this.annMenu.getItem(0).dispose(); }
		
		MenuItem mntmNewAuthor = new MenuItem(this.annMenu, SWT.PUSH);
		mntmNewAuthor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InsertInMenuDialog dialog = new InsertInMenuDialog(MainShell.shell);
				final String annotatorName = dialog.open("Insert new annotator");
				if (annotatorName != null && ( ! annotatorName.equals("") )) {
					boolean not_found = true;
					for (MenuItem item : MainShell.shell.annotators.annMenu.getItems()) {
						if (item.getText().equals(annotatorName)) {
							MessageBox box = new MessageBox(MainShell.shell, SWT.OK | SWT.ICON_INFORMATION);
			                box.setText("Annotator name already used");
			                box.setMessage("Annotator name \"" + annotatorName + "\" already used");
			                box.open();
			                not_found = false;
			                break;
						}
					}
					if (not_found) {
						MenuItem mntmInsertedAuthor = new MenuItem(MainShell.shell.annotators.annMenu, SWT.RADIO);
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

		for( Annotator ann : this.annList ) {
			MenuItem mntmInsertedAuthor = new MenuItem(this.annMenu, SWT.RADIO);
			mntmInsertedAuthor.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					active = (Annotator)e.widget.getData();
				}
			});
			mntmInsertedAuthor.setText( ann.getLogin() );
			mntmInsertedAuthor.setData( ann );
		}
		
	}

	
}
