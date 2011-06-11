package gr.demokritos.iit.eleon.interfaces;

import gr.demokritos.iit.eleon.struct.EleonStruc;

public interface OntoExtension
{


	public boolean isRealTime();
	
	
	/**
	 * Sets the previous extension in the pipeline 
	 * @param OntoExtension previous object in the pipeline
	 */
	public void setPrevious( OntoExtension prev );

	
	/**
	 * Performs the functionality that this extension provides
	 * and writes the results in the QueryHashtable object that
	 * is answered by getExtension()  
	 */
	public void rebind();
	

	/**
	 * Gets the output object of this extension.
	 * This must be null, if rebind() has bever been called. 
	 * @param QueryHashtable the output object
	 */
	public EleonStruc getExtension();

        public void reloadTree(boolean set);
	
	
	
}
