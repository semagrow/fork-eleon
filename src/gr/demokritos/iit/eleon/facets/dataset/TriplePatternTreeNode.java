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

<p>Title: Entity Inclusion Tree Node</p>

***************/

package gr.demokritos.iit.eleon.facets.dataset;

import com.hp.hpl.jena.rdf.model.Resource;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.facets.TreeFacetNode;

public class TriplePatternTreeNode extends DatasetNode implements TreeFacetNode
{
	
	
	public TriplePatternTreeNode(  Resource res, DatasetFacet facet,
			String subjectVocabulary, String subjectPattern, 
			String predicate, String objectVocabulary, String objectPattern )//FIXME: is predicate used anywhere?
	{
		super( res, facet );
		Integer active_ann = MainShell.shell.activeAnnSchema;
		for (int i = 0; i<AnnotationVocabulary.property_qnames[active_ann].length; i++) {
			String ann_name = AnnotationVocabulary.property_qnames[active_ann][i];
			if (ann_name == null) {
				break;
			} else if (ann_name.equals("svd:subjectRegexPattern")) {
				this.property_values[active_ann][i] = subjectPattern;
			} else if (ann_name.equals("svd:subjectVocabulary")) {
				this.property_values[active_ann][i] = subjectVocabulary;
			} else if (ann_name.equals("svd:objectRegexPattern")) {
				this.property_values[active_ann][i] = objectPattern;
			} else if (ann_name.equals("svd:objectVocabulary")) {
				this.property_values[active_ann][i] = objectVocabulary;
			}
		}
	}
	
	public String getSubjectPattern() {
		Integer active_ann = MainShell.shell.activeAnnSchema;
		for (int i = 0; i<AnnotationVocabulary.property_qnames[active_ann].length; i++) {
			String ann_name = AnnotationVocabulary.property_qnames[active_ann][i];
			if (ann_name == null) {
				break;
			} else if (ann_name.equals("svd:subjectRegexPattern")) {
				return (String) this.property_values[active_ann][i];
			}
		}
		return null;
	}
	
	public String getObjectPattern() {
		Integer active_ann = MainShell.shell.activeAnnSchema;
		for (int i = 0; i<AnnotationVocabulary.property_qnames[active_ann].length; i++) {
			String ann_name = AnnotationVocabulary.property_qnames[active_ann][i];
			if (ann_name == null) {
				break;
			} else if (ann_name.equals("svd:objectRegexPattern")) {
				return (String) this.property_values[active_ann][i];
			}
		}
		return null;
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
