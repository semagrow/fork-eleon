/**
 * 
 */
package gr.demokritos.iit.eleon.functionality;

import com.hp.hpl.jena.ontology.OntProperty;

/**
 * @author gmouchakis
 *
 */
public class PropertyAndValues {
	
	private OntProperty ontProperty = null;
	private Integer void_size = null;
	
	/**
	 * @param ontProperty
	 * @param void_size
	 */
	public PropertyAndValues(OntProperty ontProperty, Integer void_size) {
		super();
		this.ontProperty = ontProperty;
		this.void_size = void_size;
	}
	
	/**
	 * @param ontProperty
	 */
	public PropertyAndValues(OntProperty ontProperty) {
		super();
		this.ontProperty = ontProperty;
	}

	/**
	 * 
	 */
	public PropertyAndValues() {
		super();
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

	/**
	 * @return the void_size
	 */
	public Integer getVoid_size() {
		return void_size;
	}

	/**
	 * @param void_size the void_size to set
	 */
	public void setVoid_size(Integer void_size) {
		this.void_size = void_size;
	}
	
	/**
	 * @return true if void_size has been set, false if not
	 */
	public boolean hasVoid_size() {
		if (void_size != null) {
			return true;
		} else {
			return false;
		}
	}
	

}
