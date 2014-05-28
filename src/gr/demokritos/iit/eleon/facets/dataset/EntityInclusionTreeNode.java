/***************

<p>Title: Entity Inclusion Tree Node</p>

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

import gr.demokritos.iit.eleon.facets.TreeFacetNode;

public class EntityInclusionTreeNode extends DatasetNode implements TreeFacetNode
{
	
	private String subjectPattern;
	private String objectPattern;
	
	/**
	 * @param subjectPattern
	 * @param objectPattern
	 */
	public EntityInclusionTreeNode(String subjectPattern,
			String objectPattern, String activeAnnotationSchema) {
		super(activeAnnotationSchema);
		this.subjectPattern = subjectPattern;
		this.objectPattern = objectPattern;
	}

	/**
	 * @param void_triples
	 * @param void_distinctSubjects
	 * @param void_distinctObjects
	 * @param dc_creator
	 * @param void_sparqlEnpoint
	 * @param dc_title
	 */
	public EntityInclusionTreeNode(Integer void_triples, Integer void_distinctSubjects,
			Integer void_distinctObjects, String dc_creator,
			String void_sparqlEnpoint, String dc_title) {
		super(void_triples, void_distinctSubjects, void_distinctObjects, dc_creator,
				void_sparqlEnpoint, dc_title);
	}



	/**
	 * @return the subjectPattern
	 */
	public String getSubjectPattern() {
		return subjectPattern;
	}

	/**
	 * @param subjectPattern the subjectPattern to set
	 */
	public void setSubjectPattern(String subjectPattern) {
		this.subjectPattern = subjectPattern;
	}

	/**
	 * @return the objectPattern
	 */
	public String getObjectPattern() {
		return objectPattern;
	}

	/**
	 * @param objectPattern the objectPattern to set
	 */
	public void setObjectPattern(String objectPattern) {
		this.objectPattern = objectPattern;
	}
	

}
