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
	private Integer void_distinctSubjects = null;
	private Integer void_distinctObjects = null;
	private String dc_creator = null;
	
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
	 * @return the void_distinctSubjects
	 */
	public Integer getVoid_distinctSubjects() {
		return void_distinctSubjects;
	}

	/**
	 * @param void_distinctSubjects the void_distinctSubjects to set
	 */
	public void setVoid_distinctSubjects(Integer void_distinctSubjects) {
		this.void_distinctSubjects = void_distinctSubjects;
	}

	/**
	 * @return the void_distinctObjects
	 */
	public Integer getVoid_distinctObjects() {
		return void_distinctObjects;
	}

	/**
	 * @param void_distinctObjects the void_distinctObjects to set
	 */
	public void setVoid_distinctObjects(Integer void_distinctObjects) {
		this.void_distinctObjects = void_distinctObjects;
	}
	
	

	/**
	 * @return the dc_creator
	 */
	public String getDc_creator() {
		return dc_creator;
	}

	/**
	 * @param dc_creator the dc_creator to set
	 */
	public void setDc_creator(String dc_creator) {
		this.dc_creator = dc_creator;
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
	
	/**
	 * @return true if void_distinctSubjects has been set, false if not
	 */
	public boolean hasVoid_distinctSubjects() {
		if (void_distinctSubjects != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return true if void_distinctObjects has been set, false if not
	 */
	public boolean hasVoid_distinctObjects() {
		if (void_distinctObjects != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return true if dc_creator has been set, false if not
	 */
	public boolean hasDc_creator() {
		if (dc_creator != null) {
			return true;
		} else {
			return false;
		}
	}

}
