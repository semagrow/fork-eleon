package gr.demokritos.iit.eleon.annotations;

import java.util.ArrayList;
import java.util.List;

public class NominalSet {
	
	public static String[] availableNomilas = {"class1" , "class2", "class3", "class4", "class5"};
	List<String> containingNominals = new ArrayList<String>();
	
	public NominalSet() {
		super();
	}
	
	public void setContainingNominals(List<String> containingNominals) {
		this.containingNominals = new ArrayList<String>(containingNominals); 
	}
	
	public List<String> getContainingNominals() {
		return containingNominals;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return containingNominals.toString();
	}
	
	

}
