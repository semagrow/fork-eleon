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
