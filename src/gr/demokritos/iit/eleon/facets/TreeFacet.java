package gr.demokritos.iit.eleon.facets;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public interface TreeFacet extends Facet
{
	
	public Tree getTree();

	public TreeItem getRoot();

}
