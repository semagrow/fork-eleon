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


package gr.demokritos.iit.eleon.authoring;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Encapsulates a DOM parser.
 *
 * @version $Id: ParserWrapper.java,v 1.3 2002/01/29 01:15:05 lehors Exp $
 */
public interface ParserWrapper {

    //
    // ParserWrapper methods
    //

    /** Parses the specified URI and returns the document. */
    public Document parse(String uri) throws Exception;

    /**
     * Set the state of a feature.
     *
     * Set the state of any feature in a SAX2 parser.  The parser
     * might not recognize the feature, and if it does recognize
     * it, it might not be able to fulfill the request.
     *
     * @param featureId The unique identifier (URI) of the feature.
     * @param state The requested state of the feature (true or false).
     *
     * @exception org.xml.sax.SAXNotRecognizedException If the
     *            requested feature is not known.
     * @exception org.xml.sax.SAXNotSupportedException If the
     *            requested feature is known, but the requested
     *            state is not supported.
     * @exception org.xml.sax.SAXException If there is any other
     *            problem fulfilling the request.
     */
    public void setFeature(String featureId, boolean state)
        throws  SAXNotRecognizedException, SAXNotSupportedException;

    /** Returns the document information. */
    public DocumentInfo getDocumentInfo();

    //
    // Interfaces
    //

    /**
     * This interface is here to query information about the document
     * implementation returned by the <code>ParserWrapper#parse</code>
     * method.
     *
     * @author Andy Clark, IBM
     */
    public interface DocumentInfo {

        //
        // DocumentInfo methods
        //

        /**
         * Returns true if the specified text node is ignorable whitespace.
         */
        public boolean isIgnorableWhitespace(Text text);

    } // interface DocumentInfo

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
