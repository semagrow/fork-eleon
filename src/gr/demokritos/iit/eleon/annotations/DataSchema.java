/**
 * 
 */
package gr.demokritos.iit.eleon.annotations;

import java.io.File;

/**
 * @author Giannis Mouchakis
 *
 */
public class DataSchema {
	
	private String label;
	private File schemaFile;

	/**
	 * 
	 */
	public DataSchema() {
		super();
	}

	
	/**
	 * @param label the label of the data schema
	 * @param schemaFile the file where the data schema is stored.
	 */
	public DataSchema(String label, File schemaFile) {
		this.label = label;
		this.schemaFile = schemaFile;
	}


	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}



	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}



	/**
	 * @return the schemaFile
	 */
	public File getSchemaFile() {
		return schemaFile;
	}



	/**
	 * @param schemaFile the schemaFile to set
	 */
	public void setSchemaFile(File schemaFile) {
		this.schemaFile = schemaFile;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return label;
	}
	
	

}
