/**
 * 
 */
package gr.demokritos.iit.eleon.annotations;

//import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import com.hp.hpl.jena.rdf.model.Resource;



/**
 * @author Giannis Mouchakis
 *
 */
public class DataSchemaSet {
	
	/*public static final String[] availableSchemasLabels;
	public static final Resource[] availableSchemasURIs;
	public static final File[] availableSchemasFiles;*/
	
	// Current selection
	List<DataSchema> containingSchemas = new ArrayList<DataSchema>();
		
	public DataSchemaSet()
	{
		super();
	}
	
	/*
	 * Object IMPLEMENTATION
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{ 
		String str = null;
		if (containingSchemas.isEmpty()) {
			str = "[]";
		} else {
			str = "[";
			for (int i=0; i<containingSchemas.size()-1; i++) {
				str += containingSchemas.get(i).getLabel() + ", ";
			}
			str += containingSchemas.get(containingSchemas.size()-1).getLabel() + "]";
		}
		return str; 
	}
	

	
	/*
	 * Specifics
	 */
	
	public void setContainingSchemas( List<DataSchema> schema_to_set )
	{
		this.containingSchemas.clear();
		this.containingSchemas.addAll(schema_to_set);
	}
	
	public List<DataSchema> getContainingSchemas()
	{
		return containingSchemas;
	}
	
}
