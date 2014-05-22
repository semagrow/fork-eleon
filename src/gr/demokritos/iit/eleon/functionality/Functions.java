/**
 * 
 */
package gr.demokritos.iit.eleon.functionality;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author gmouchakis
 *
 */
public class Functions {
	
	/**
	 * @return true if you try to copy a subset node under the root node
	 * which is prohibited by Eleon.
	 */
	public static boolean notDatasourceUnderRoot(TreeItem selectedItem, Tree treeCopy) {
		boolean isRoot = selectedItem.getText().equals("root");
		boolean isPerEntityNode = treeCopy.getSelection()[0].getData() instanceof PerEntityNode;
		boolean isPerPropertyNode = treeCopy.getSelection()[0].getData() instanceof PerPropertyNode;
		if ((isRoot && isPerEntityNode) || (isRoot && isPerPropertyNode)) {
			return true;
		} else {
			return false;
		}
	}

	
	public static void copyTree(TreeItem rootOriginal, TreeItem rootCopy) {
		for (TreeItem childOriginal : rootOriginal.getItems()) {
			TreeItem childCopy = new TreeItem(rootCopy, SWT.NONE);
			childCopy.setText(childOriginal.getText());
			childCopy.setData(childOriginal.getData());
			if (childOriginal.getItemCount() > 0) {
				copyTree(childOriginal, childCopy);
			}
		}
	}
	
}
