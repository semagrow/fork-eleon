/***************

<p>Title: ELEON/XML Persistence</p>

<p>Description:
Persistence backend, based on custom XML schema.
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
@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon.persistence;

import gr.demokritos.iit.eleon.facets.Facet;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.facets.dataset.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;


public class ELEONXML implements PersistenceBackend
{
	private String filename = null;
	private Document doc = null;
	private String label = null;
	private OntModel ont = null;

	
	/*
	 * Constructors 
	 */

	
	public ELEONXML( OntModel ont )
	{
		this.ont = ont;
	}

	
	/*
	 * Getters and Setters 
	 */


	@Override
	public String getLabel() { return this.label; }

	@Override
	public String getBackend()
	{ return this.filename; }
	

	/*
	 * PersistenceBackend implementation
	 */


	@Override
	public void open( Object parameter )
	throws IllegalArgumentException, IOException
	{
		try { this.filename = (String)parameter; }
		catch( ClassCastException ex ) {
			throw new IllegalArgumentException( "Argument should be a String", ex );
		}

		try {
			File fXmlFile = new File( this.filename );
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse( fXmlFile );
			doc.getDocumentElement().normalize();
		}
		catch( ParserConfigurationException ex ) {
			// This should never happen
			assert 1 == 0;
		}
		catch( SAXException ex ) {
			throw new IllegalArgumentException( "Not an XML file", ex );
		}

		if( doc != null ) {
			String rootElement = doc.getDocumentElement().getNodeName();
			if ( ! rootElement.equals("eleon_save")) {
				throw new IllegalArgumentException( "Bad format document" );
			}

			NodeList nListTitle = doc.getElementsByTagName("dc:title");
			this.label = nListTitle.item(0).getTextContent();

			//moved to node attribute
			/*NodeList nEnpointTitle = doc.getElementsByTagName("void:sparqlEndpoint");
				this.textEndpoint.setText(nEnpointTitle.item(0).getTextContent());*/
			/*
				treePerProperty.dispose();
				table.dispose();
				createTree();
				createTable();
			 */
		}
	}
	
	@Override
	public boolean save( Facet[] facets, Object parameter )
	throws IOException
	{
		if( parameter != null ) {
			try { this.filename = (String)parameter; }
			catch( ClassCastException ex ) {
				throw new IllegalArgumentException( "Argument should be a String", ex );
			}
		}
		if( this.filename == null ) { return false; }

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = null;
		try { docBuilder = docFactory.newDocumentBuilder(); }
		catch( ParserConfigurationException ex ) {
			// This never happens
		}
		assert docBuilder != null;

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

		for( Facet facet : facets ) {

			Element element = doc.createElement( "facet" );

			if( facet instanceof PropertyTreeFacet ) {
				element.setAttribute("type", "per_property");
			}
			else if( facet instanceof TriplePatternTreeFacet ) {
				element.setAttribute("type", "per_entity");
			}
			else {
				assert 1 == 0;
			}
			rootElement.appendChild( element );

			Element treeRootEntity = doc.createElement("node");
			treeRootEntity.setAttribute("name", "root");
			element.appendChild(treeRootEntity);

			createDOMFromTree(
					((TreeFacet)facet).getTree().getItems()[0],
					treeRootEntity, doc );
		}

		// write the DOM into an XML file
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource( doc );
			StreamResult result = new StreamResult( new File(filename) );
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform( source, result );
		}
		catch( TransformerConfigurationException ex ) {
			// THis cannot happen
			assert 1 == 0; 
		}
		catch( TransformerException ex ) {
			throw new IOException( ex );
		}

		System.out.println("File " + filename + " saved!");
		return true;
	}
	
	/*
	@Override
	public void buildPropertyTree( PropertyTreeFacet facet )
	{
		Tree propertyTree = facet.getTree();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		NodeList nodeList = null;
		try {
			XPathExpression expr = xpath.compile( "//facet[@type=\"per_property\"]" );
			nodeList = (NodeList) expr.evaluate( doc, XPathConstants.NODESET );
		}
		catch( XPathExpressionException ex ) {
			// Should never happen
		}
		assert nodeList != null;

		Element eElement = (Element) nodeList.item( 0 );
		Element root = (Element) eElement.getFirstChild();
		NodeList childrenList = root.getChildNodes();
		for( int i = 0; i < childrenList.getLength(); i++ ) {
			createTreeFromDOM( childrenList.item(i), facet, propertyTree.getItems()[0] );
		}
		propertyTree.moveAbove( null );
	}
	

	@Override
	public void buildEntityTree( EntityInclusionTreeFacet facet )
	{
		Tree entityTree = facet.getTree();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		NodeList nodeList = null;
		try {
			XPathExpression expr = xpath.compile("//facet[@type=\"per_entity\"]");
			nodeList = (NodeList) expr.evaluate( doc, XPathConstants.NODESET );
		}
		catch( XPathExpressionException ex ) {
			// Should never happen
		}
		assert nodeList != null;

		Element eElement = (Element) nodeList.item(0);
		Element root = (Element) eElement.getFirstChild();
		NodeList childrenList = root.getChildNodes();
		for( int i = 0; i < childrenList.getLength(); i++ ) {
			createTreeFromDOM( childrenList.item(i), facet, entityTree.getItems()[0]);
		}
		entityTree.moveAbove(null);
	}
	*/

	/*
	 * Internals 
	 */

	
	private void createDOMFromTree(TreeItem treeItem, Element root, Document doc)
	{
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			Element treeItemNode = doc.createElement("node");
			// System.out.println(treeItem);
			//treeItemNode.setAttribute("name", ((TreeNodeData) treeItemCurrent.getData()).getOntProperty().toString());
			treeItemNode.setAttribute("name", treeItemCurrent.getText());
			DatasetNode treeNodeData = (DatasetNode) treeItemCurrent.getData();
			treeItemNode.setAttribute("dc:creator", treeNodeData.getOwner().getLocalName());
			Integer void_size = (Integer)treeNodeData.getValue( "void:triples" );
			if (void_size != null) {
				treeItemNode.setAttribute("void:triples", void_size.toString());
			}
			Integer void_distinctSubjects = (Integer)treeNodeData.getValue( "void:distinctSubjects" );
			if (void_distinctSubjects != null) {
				treeItemNode.setAttribute("void:distinctSubjects", void_distinctSubjects.toString());
			}
			Integer void_distinctObjects = (Integer)treeNodeData.getValue( "void:distinctObjects" );
			if (void_distinctObjects != null) {
				treeItemNode.setAttribute("void:distinctObjects", void_distinctObjects.toString());
			}
			treeItemNode.setAttribute("void:sparqlEndpoint", (String)treeNodeData.getValue("void:sparqlEnpoint") );
			treeItemNode.setAttribute("dc:title", treeNodeData.getLabel());
			if (treeItemCurrent.getData() instanceof TriplePatternTreeNode) {
				TriplePatternTreeNode perEntityNode = (TriplePatternTreeNode) treeItemCurrent.getData();
				String subjectPattern = null;/*perEntityNode.getSubjectPattern();*///FIXME this should not be null
				String objectPattern = null;/*perEntityNode.getObjectPattern();*///FIXME this should not be null
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
			} else if (treeItemCurrent.getData() instanceof PropertyTreeNode) {
				PropertyTreeNode perPropertyNode = (PropertyTreeNode) treeItemCurrent.getData();
				com.hp.hpl.jena.rdf.model.Resource ontProperty = perPropertyNode.getProperty();
				Element ontPropertyElement = doc.createElement("rdf:Property");
				ontPropertyElement.setAttribute( "rdf:about", ontProperty.getURI() );
				treeItemNode.appendChild(ontPropertyElement);
			}
			//treeItemNode.setAttribute("parent", treeItem.getText());
			root.appendChild(treeItemNode);
			if (treeItemCurrent.getItemCount() > 0) {
				createDOMFromTree(treeItemCurrent, treeItemNode, doc);
			}
		}
	}
	
	private void createTreeFromDOM( Node node, DatasetFacet facet, TreeItem parentTreeItem )
	{
		Tree tree = facet.getTree();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			TreeItem rootItem =tree.getItems()[0];
			
			Element eElement = (Element) node;
			String name = eElement.getAttribute("name");
			String dc_creator = eElement.getAttribute("dc:creator");
			
			DatasetNode nodeData = getDataFromExistingTreeItem(rootItem, name, dc_creator);
			
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
				if( facet.getClass().equals(PropertyTreeFacet.class) ) {
					NodeList nodeList = node.getChildNodes();
					OntProperty ontProperty = null;
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node currentNode = nodeList.item(i);
						if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
							if (currentNode.getNodeName().equals("rdf:Property")) {
								Element element = (Element) currentNode;
								String rdf_about = element.getAttribute("rdf:about");
								ontProperty = ont.createOntProperty( rdf_about );
							}
						}
					}
					PropertyTreeNode data = new PropertyTreeNode( null, facet, ontProperty );
					data.setValue( "dc:title", dc_title );
					data.setValue( "dc:creator", dc_creator );
					data.setValue( "void:triples", void_triples );
					data.setValue( "void:distinctSubjects", void_distinctSubjects );
					data.setValue( "void:distinctObjects", void_distinctObjects );
					data.setValue( "void:sparqlEndpoint", void_sparqlEndpoint );
					treeItem.setData(data);
				}
				else if( facet.getClass().equals(TriplePatternTreeFacet.class) ) {
					NodeList nodeList = node.getChildNodes();
					String subjectPattern = null;
					String objectPattern = null;
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node currentNode = nodeList.item(i);
						if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
							if( currentNode.getNodeName().equals("rdf:subject") ) {
								Element element = (Element) currentNode;
								String uriRegexPattern = element.getAttribute("void:uriRegexPattern");
								if (uriRegexPattern.equals("")) {
									subjectPattern = null;
								}
								else {
									subjectPattern = uriRegexPattern;
								}
							}
							else if( currentNode.getNodeName().equals("rdf:object") ) {
								Element element = (Element) currentNode;
								String uriRegexPattern = element.getAttribute("void:uriRegexPattern");
								if( uriRegexPattern.equals("") ) {
									objectPattern = null;
								}
								else {
									objectPattern = uriRegexPattern;
								}
							}
						}
					}
					// FIXME: Resource should not be null, but be read in from DOM
					// MIght not be worth fixing, if ELEON/XML is going to disappear
					TriplePatternTreeNode data =
							new TriplePatternTreeNode( null, facet, null, subjectPattern, null, null, objectPattern );
					data.setValue( "dc:title", dc_title );
					data.setValue( "dc:creator", dc_creator );
					data.setValue( "void:triples", void_triples );
					data.setValue( "void:distinctSubjects", void_distinctSubjects );
					data.setValue( "void:distinctObjects", void_distinctObjects );
					data.setValue( "void:sparqlEndpoint", void_sparqlEndpoint );
					treeItem.setData(data);
				}
			} else {
				treeItem.setData(nodeData);
			}
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node currentNode = nodeList.item(i);
				if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equals("node")) {
					createTreeFromDOM( currentNode, facet, treeItem );
				}
			}
		}
	}

	private DatasetNode getDataFromExistingTreeItem(TreeItem treeItem, String name, String dc_creator) {
		for (TreeItem treeItemCurrent : treeItem.getItems()) {
			DatasetNode treeNodeData = (DatasetNode) treeItemCurrent.getData();
			if (treeNodeData.getOwner().equals(dc_creator) && treeItemCurrent.getText().equals(name)) {
				return (DatasetNode) treeItemCurrent.getData();
			}
			if (treeItemCurrent.getItemCount() > 0) {
				getDataFromExistingTreeItem(treeItemCurrent, name, dc_creator);
			}
		}
		return null;
	}
	

	
}
