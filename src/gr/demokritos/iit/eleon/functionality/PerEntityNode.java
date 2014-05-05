/**
 * 
 */
package gr.demokritos.iit.eleon.functionality;

/**
 * @author gmouchakis
 *
 */
public class PerEntityNode extends TreeNodeData {
	

	private String subjectPattern;
	private String objectPattern;
	
	/**
	 * @param subjectPattern
	 * @param objectPattern
	 */
	public PerEntityNode(String subjectPattern,
			String objectPattern) {
		super();
		this.subjectPattern = subjectPattern;
		this.objectPattern = objectPattern;
	}

	

	/**
	 * @param void_triples
	 * @param void_distinctSubjects
	 * @param void_distinctObjects
	 * @param dc_creator
	 * @param void_sparqlEnpoint
	 */
	public PerEntityNode(Integer void_triples, Integer void_distinctSubjects,
			Integer void_distinctObjects, String dc_creator,
			String void_sparqlEnpoint) {
		super(void_triples, void_distinctSubjects, void_distinctObjects, dc_creator,
				void_sparqlEnpoint);
	}



	/**
	 * @return the subjectPattern
	 */
	public String getSubjectPattern() {
		return subjectPattern;
	}

	/**
	 * @param subjectPattern the subjectPattern to set
	 */
	public void setSubjectPattern(String subjectPattern) {
		this.subjectPattern = subjectPattern;
	}

	/**
	 * @return the objectPattern
	 */
	public String getObjectPattern() {
		return objectPattern;
	}

	/**
	 * @param objectPattern the objectPattern to set
	 */
	public void setObjectPattern(String objectPattern) {
		this.objectPattern = objectPattern;
	}
	

}
