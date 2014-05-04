/**
 * 
 */
package gr.demokritos.iit.eleon.functionality;

/**
 * @author gmouchakis
 *
 */
public class TreeNodeData {
	
	private Integer void_triples = null;
	private Integer void_distinctSubjects = null;
	private Integer void_distinctObjects = null;
	private String dc_creator = null;
	
	/**
	 * 
	 */
	public TreeNodeData() {
		super();
	}
	
	/**
	 * @param void_triples
	 * @param void_distinctSubjects
	 * @param void_distinctObjects
	 * @param dc_creator
	 */
	public TreeNodeData(Integer void_triples, Integer void_distinctSubjects,
			Integer void_distinctObjects, String dc_creator) {
		super();
		this.void_triples = void_triples;
		this.void_distinctSubjects = void_distinctSubjects;
		this.void_distinctObjects = void_distinctObjects;
		this.dc_creator = dc_creator;
	}

	/**
	 * @return the void_triples
	 */
	public Integer getVoid_size() {
		return void_triples;
	}

	/**
	 * @param void_triples the void_triples to set
	 */
	public void setVoid_size(Integer void_size) {
		this.void_triples = void_size;
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
	 * @return true if void_triples has been set, false if not
	 */
	public boolean hasVoid_size() {
		if (void_triples != null) {
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
