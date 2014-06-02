/***************

<p>Title: Functions</p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.
<br> Copyright (c) 2001-2014 National Centre for Scientific Research "Demokritos"
</p>

<p>
ELEON is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
</p>

<p>
ELEON is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
</p>

<p>
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
</p>

@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon.facets.dataset;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class Functions {
	
	/**
	 * @return true if you try to copy a subset node under the root node
	 * which is prohibited by Eleon.
	 */
	public static boolean notDatasourceUnderRoot(TreeItem selectedItem, Tree treeCopy) {
		boolean isRoot = selectedItem.getText().equals("root");
		boolean isPerEntityNode = treeCopy.getSelection()[0].getData() instanceof TriplePatternTreeNode;
		boolean isPerPropertyNode = treeCopy.getSelection()[0].getData() instanceof PropertyTreeNode;
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
