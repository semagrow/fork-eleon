/**
 * 
 */
package gr.demokritos.iit.eleon.functionality;

import com.hp.hpl.jena.ontology.OntProperty;

/**
 * @author gmouchakis
 *
 */
public class PerPropertyNode extends TreeNodeData {
	
	private OntProperty ontProperty = null;

	/**
	 * @param ontProperty
	 */
	public PerPropertyNode(OntProperty ontProperty) {
		super();
		this.ontProperty = ontProperty;
	}
	
	
	/**
	 * @param void_triples
	 * @param void_distinctSubjects
	 * @param void_distinctObjects
	 * @param dc_creator
	 * @param void_sparqlEnpoint
	 * @param dc_title
	 */
	public PerPropertyNode(Integer void_triples, Integer void_distinctSubjects,
			Integer void_distinctObjects, String dc_creator,
			String void_sparqlEnpoint, String dc_title) {
		super(void_triples, void_distinctSubjects, void_distinctObjects, dc_creator,
				void_sparqlEnpoint, dc_title);
	}

	/**
	 * @return the ontProperty
	 */
	public OntProperty getOntProperty() {
		return ontProperty;
	}

	/**
	 * @param ontProperty the ontProperty to set
	 */
	public void setOntProperty(OntProperty ontProperty) {
		this.ontProperty = ontProperty;
	}

	

}
