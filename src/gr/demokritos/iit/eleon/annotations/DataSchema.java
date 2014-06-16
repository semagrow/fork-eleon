/**
 * 
 */
package gr.demokritos.iit.eleon.annotations;

import java.io.File;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Giannis Mouchakis
 *
 */
public class DataSchema {
	
	private String label;
	private File schemaFile;
	private Resource resource;

	/**
	 * 
	 */
	public DataSchema() {
		super();
	}

	
	/**
	 * @param label the label of the data schema
	 * @param schemaFile the file where the data schema is stored.
	 * @param the resource representing the the file that holds the data schema
	 */
	public DataSchema(String label, File schemaFile, Resource resource) {
		this.label = label;
		this.schemaFile = schemaFile;
		this.resource = resource;
	}


	/**
	 * @return the label of this data schema
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @return the file that holds the data schema
	 */
	public File getSchemaFile() {
		return schemaFile;
	}
	
	/**
	 * @return the resource representing the the file that holds the data schema
	 */
	public Resource getResource() {
		return resource;
	}



}
