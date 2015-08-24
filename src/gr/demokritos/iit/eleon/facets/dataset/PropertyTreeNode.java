/***************

<p>Title: </p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


/***************

<p>Title: Property Tree Node</p>


***************/

package gr.demokritos.iit.eleon.facets.dataset;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.facets.TreeFacetNode;

import com.hp.hpl.jena.rdf.model.Resource;


public class PropertyTreeNode extends DatasetNode implements TreeFacetNode
{
	//private Resource ontProperty = null;

	/**
	 * @param ontProperty
	 */
	public PropertyTreeNode( Resource res, DatasetFacet myFacet, Resource ontProperty )
	{
		super( res, myFacet );
		//this.ontProperty = ontProperty;
		Integer active_ann = MainShell.shell.activeAnnSchema;
		for (int i = 0; i<AnnotationVocabulary.property_qnames[active_ann].length; i++) {
			String ann_name = AnnotationVocabulary.property_qnames[active_ann][i];
			if (ann_name == null) {
				break;
			} else if (ann_name.equals("void:property")) {
				this.property_values[active_ann][i] = ontProperty;
			}
		}
	}
	
	
	/**
	 * @return the ontProperty
	 */
	public Resource getProperty()
	{
		Integer active_ann = MainShell.shell.activeAnnSchema;
		for (int i = 0; i<AnnotationVocabulary.property_qnames[active_ann].length; i++) {
			String ann_name = AnnotationVocabulary.property_qnames[active_ann][i];
			if (ann_name == null) {
				break;
			} else if (ann_name.equals("void:property")) {
				return (Resource) this.property_values[active_ann][i];
			}
		}
		return null; 
	}

	/**
	 * @param ontProperty the ontProperty to set
	 */
	public void setProperty( Resource ontProperty )
	{
		Integer active_ann = MainShell.shell.activeAnnSchema;
		for (int i = 0; i<AnnotationVocabulary.property_qnames[active_ann].length; i++) {
			String ann_name = AnnotationVocabulary.property_qnames[active_ann][i];
			if (ann_name == null) {
				break;
			} else if (ann_name.equals("void:property")) {
				this.property_values[active_ann][i] = ontProperty;
			}
		}
		//this.ontProperty = ontProperty; 
	}


}


/*
This file is part of the ELEON Ontology Authoring and Enrichment Tool.

ELEON is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, see <http://www.gnu.org/licenses/>.
*/
