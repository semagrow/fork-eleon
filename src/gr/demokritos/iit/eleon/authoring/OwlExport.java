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

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.OWL;

import gr.demokritos.iit.eleon.parser.ClassParser;
import gr.demokritos.iit.eleon.profiles.Robot;
import gr.demokritos.iit.eleon.profiles.User;

import java.io.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.StringTokenizer;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class OwlExport {
    public static void ExportToOwlFile(File rdfFile, String format, String url, String ontname, boolean exportlexicon) throws Exception {
        FileOutputStream fileOutput = new FileOutputStream(rdfFile);
        OutputStreamWriter output = new OutputStreamWriter(fileOutput, "UTF-8");
        
        //String ontName = rdfFile.getName().substring(0, rdfFile.getName().lastIndexOf('.'));
        //String mpiroNS = "http://localhost/" + ontName + "#";
        //String mpiroPath = "http://localhost/" + ontName;
        String mpiroPath = url;
        
        String mpiroNS = url;
        if(!mpiroNS.endsWith("#"))
            mpiroNS=mpiroNS+"#";
        
        OntDocumentManager ontManager = new OntDocumentManager();
        
        ontManager.addAltEntry(mpiroNS, "file:mpiro.xsd");
        OntModelSpec s = new OntModelSpec(OntModelSpec.OWL_MEM);
        s.setDocumentManager(ontManager);
        
        OntModel ontModel = ModelFactory.createOntologyModel(s, null);
        ontModel.removeNsPrefix( "vcard" );
        ontModel.removeNsPrefix( "dc" );
        ontModel.removeNsPrefix( "rss" );
        ontModel.removeNsPrefix( "jms" );
        ontModel.removeNsPrefix( "daml" );
        ontModel.setNsPrefix( ontname, mpiroNS );
        ontModel.setNsPrefix( "xsd", "http://www.w3.org/2001/XMLSchema#" );
        ontModel.setNsPrefix( "", "http://www.mpiro.gr/Mpiro-Schema#" );
        
        //add Upper Model Classes
        //  addUpperModelClasses(ontModel, mpiroNS);
        
        // Adding the entity types
        Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        // Remove the 2 entries that are not needed
        allEntityTypes.remove("Data Base");
        allEntityTypes.remove("Basic-entity-types");
        
        Enumeration allEntityTypeNames;
        Enumeration allEntityTypeParentNames;
        
        allEntityTypeNames = allEntityTypes.keys();
        allEntityTypeParentNames = allEntityTypes.elements();
        while (allEntityTypeNames.hasMoreElements()) {
            String entityTypeName = allEntityTypeNames.nextElement().toString();//.replaceFirst("2","");
            String entityTypeParentName = allEntityTypeParentNames.nextElement().toString();//.replaceFirst("2","");
            
            
            if (entityTypeName.substring(0,entityTypeName.length()-1).endsWith("occur"))
                entityTypeName=entityTypeName.substring(0,entityTypeName.length()-7) ;
            if (entityTypeParentName.substring(0,entityTypeParentName.length()-1).endsWith("occur"))
                entityTypeParentName=entityTypeParentName.substring(0,entityTypeParentName.length()-7) ;
            //add child
            OntClass childClass = ontModel.createClass(getNSFor(convertToClassName(entityTypeName), mpiroNS));
            //add parent
            if (!entityTypeParentName.equalsIgnoreCase("Basic-entity-types")) {
                OntClass parentClass = ontModel.createClass(getNSFor(convertToClassName(entityTypeParentName), mpiroNS));
                childClass.addSuperClass(parentClass);
            } //else {
            //childClass.addSuperClass(ontModel.getOntClass(mpiroNS+"Basic-Entity-Types"));
            //         NodeVector entityTypeNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
            //         Vector upperVector = (Vector) entityTypeNode.get(1);
            //         for (int i = 0; i < upperVector.size(); i++) {
            //             childClass.addSuperClass(ontModel.getOntClass(mpiroNS + convertToUpperModelName(upperVector.get(i).toString())));
            //  }
            //    }
        }
        // END Adding the entity types
        
        //  allEntityTypeNames = allEntityTypes.keys();
        //  allEntityTypeParentNames = allEntityTypes.elements();
        
        //add properties
        Enumeration propNames=Mpiro.win.struc.getPropertyNames();
        Enumeration propVectors=Mpiro.win.struc.getProperties();
        while (propNames.hasMoreElements()) {
            String propName=propNames.nextElement().toString();
            PropertiesHashtableRecord propVector=(PropertiesHashtableRecord)propVectors.nextElement();
            
            Vector range=(Vector)propVector.elementAt(1);
            //if range is a class then create an ObjectProperty
            if ((!range.contains("String")) && (!range.contains("Dimension")) && (!range.contains("Number")) && (!range.contains("Date"))) {
                
                //Create Property and Add Domain
                // if  (ontModel.getOntProperty(getNSFor(convertToPropertyName(property.m_field), mpiroNS))==null){
                
                ObjectProperty  newProp = ontModel.createObjectProperty(getNSFor(convertToPropertyName(propName), mpiroNS));
                
                
                
                Vector domain=(Vector) propVector.elementAt(0);
                
                if(domain.size()>1){
                    RDFNode[] union=new RDFNode[domain.size()];
                    for(int o=0;o<domain.size();o++){
                        if(domain.elementAt(o).toString().equalsIgnoreCase("Basic-entity-types")){
                            union[o]=OWL.Thing;
                        }else{
                            OntClass dom1 = ontModel.getOntClass(getNSFor(convertToClassName(domain.elementAt(o).toString()), mpiroNS));
                            union[o]=dom1;
                        }
                        
                    }
                    UnionClass dom=ontModel.createUnionClass(null, ontModel.createList(union));
                    newProp.addDomain(dom);} else{
                    Resource dom;
                    if(domain.elementAt(0).toString().equalsIgnoreCase("Basic-entity-types")){
                        dom=OWL.Thing;
                    }else{
                        dom = ontModel.getOntClass(getNSFor(convertToClassName(domain.elementAt(0).toString()), mpiroNS));
                    }
                    newProp.addDomain(dom);
                    }
                
                
                for(int o=0;o<range.size();o++){
                    if(range.elementAt(o).toString().equalsIgnoreCase("Basic-entity-types")) continue;
                    OntClass rang = ontModel.getOntClass(getNSFor(convertToClassName(range.elementAt(o).toString()), mpiroNS));
                    newProp.addRange(rang);
                }
                
                Vector superp=(Vector) propVector.elementAt(3);
                for(int o=0;o<superp.size();o++){
                    OntProperty superprop= ontModel.getOntProperty(getNSFor(convertToPropertyName(superp.elementAt(o).toString()), mpiroNS));
                    if(superprop!=null)
                        newProp.addSuperProperty(superprop);
                }
                Vector subp=(Vector) propVector.elementAt(2);
                for(int o=0;o<subp.size();o++){
                    OntProperty subprop= ontModel.getOntProperty(getNSFor(convertToPropertyName(subp.elementAt(o).toString()),mpiroNS));
                    if(subprop!=null)
                        subprop.addSuperProperty(newProp);
                }
                
                if(ontModel.getProperty(getNSFor(propVector.elementAt(5).toString(), mpiroNS))!=null&&!propVector.elementAt(5).toString().equalsIgnoreCase("")){
                    Property inverse=ontModel.getProperty(getNSFor(propVector.elementAt(5).toString(), mpiroNS));
                    newProp.setInverseOf(inverse);
                }
                
                if(propVector.elementAt(6).toString().equalsIgnoreCase("true")){
                    newProp.convertToFunctionalProperty();
                }
                
                if(propVector.elementAt(7).toString().equalsIgnoreCase("true")){
                    newProp.convertToInverseFunctionalProperty();
                }
                
                if(propVector.elementAt(8).toString().equalsIgnoreCase("true")){
                    newProp.convertToTransitiveProperty();
                }
                
                if(propVector.elementAt(9).toString().equalsIgnoreCase("true")){
                    newProp.convertToSymmetricProperty();
                }
                
             /*           if (!property.m_approved.booleanValue()) {
                            OntClass domain = ontModel.getOntClass(mpiroNS + convertToClassName(entityTypeName));
                            MaxCardinalityRestriction restriction = ontModel.createMaxCardinalityRestriction(null, (Property) newProp, 1);
                            domain.addSuperClass(restriction);
                        }*/
            } //else{
            //ObjectProperty newProp=ontModel.getOntProperty(getNSFor(convertToPropertyName(property.m_field), mpiroNS)).asObjectProperty();
                     /*   if (!property.m_approved.booleanValue()) {
                            OntClass domain = ontModel.getOntClass(mpiroNS + convertToClassName(entityTypeName));
                            MaxCardinalityRestriction restriction = ontModel.createMaxCardinalityRestriction(null, (Property) newProp, 1);
                            domain.addSuperClass(restriction);
                        }*/
            // }
            
            // newProp.addDomain(domain);
            //Add Range
            //newProp.addRange(ontModel.getOntClass(mpiroNS + convertToClassName(property.m_filler)));
            
            //if cardinality is 1
            
            else { //create a DatatypeProperty
                DatatypeProperty newProp;
                
                newProp = ontModel.createDatatypeProperty(getNSFor(convertToPropertyName(propName), mpiroNS));
                
                //add domain
                Vector domain=(Vector) propVector.elementAt(0);
                if(domain.size()>1){
                    RDFNode[] union=new RDFNode[domain.size()];
                    for(int o=0;o<domain.size();o++){
                        if(domain.elementAt(o).toString().equalsIgnoreCase("Basic-entity-types")){
                            union[o]=OWL.Thing;
                        }else{
                            OntClass dom1 = ontModel.getOntClass(getNSFor(convertToClassName(domain.elementAt(o).toString()), mpiroNS));
                            union[o]=dom1;
                        }
                        
                    }
                    UnionClass dom=ontModel.createUnionClass(null, ontModel.createList(union));
                    newProp.addDomain(dom);
                } else{
                    Resource dom;
                    if(domain.elementAt(0).toString().equalsIgnoreCase("Basic-entity-types")){
                        dom=OWL.Thing;
                    }else{
                        dom = ontModel.getOntClass(getNSFor(convertToClassName(domain.elementAt(0).toString()), mpiroNS));
                    }
                    newProp.addDomain(dom);
                }
                
                Vector superp=(Vector) propVector.elementAt(3);
                for(int o=0;o<superp.size();o++){
                    OntProperty superprop= ontModel.getOntProperty(getNSFor(convertToPropertyName(superp.elementAt(o).toString()), mpiroNS));
                    if(superprop!=null)
                        newProp.addSuperProperty(superprop);
                }
                Vector subp=(Vector) propVector.elementAt(2);
                for(int o=0;o<subp.size();o++){
                    OntProperty subprop= ontModel.getOntProperty(getNSFor(convertToPropertyName(subp.elementAt(o).toString()),mpiroNS));
                    if(subprop!=null)
                        subprop.addSuperProperty(newProp);
                }
                
                
                if(propVector.elementAt(6).toString().equalsIgnoreCase("true")){
                    newProp.convertToFunctionalProperty();
                }
                
                
                //Add Range
                if (range.contains("String"))
                    newProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
                else if (range.contains("Number"))
                    newProp.addRange(ontModel.getResource("http://www.mpiro.gr/Mpiro-Schema#mpiroNumber"));
                else if (range.contains("Date"))
                    newProp.addRange(ontModel.getResource("http://www.mpiro.gr/Mpiro-Schema#mpiroDate"));
                else if (range.contains("Dimension"))
                    newProp.addRange(ontModel.getResource("http://www.mpiro.gr/Mpiro-Schema#mpiroDimension"));
                
                //if cardinality is 1
                    /*if (!property.m_approved.booleanValue()) {
                        MaxCardinalityRestriction restriction = ontModel.createMaxCardinalityRestriction(null, (Property) newProp, 1);
                        domain.addSuperClass(restriction);
                    }*/
                
            }
        } //END add  properties
        
        //add entities
        Hashtable allEntities = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        Enumeration allEntityNames;
        Enumeration allEntitySubtypeOfNames;
        
        allEntityNames = allEntities.keys();
        allEntitySubtypeOfNames = allEntities.elements();
        
        while (allEntityNames.hasMoreElements()) {
            String entityName = allEntityNames.nextElement().toString();
            String entitySubtypeOfName = allEntitySubtypeOfNames.nextElement().toString();
            
            
            if (entityName.substring(0,entityName.length()-1).endsWith("occur"))
                entityName=entityName.substring(0,entityName.length()-7) ;
            if (entitySubtypeOfName.substring(0,entitySubtypeOfName.length()-1).endsWith("occur"))
                entitySubtypeOfName=entitySubtypeOfName.substring(0,entitySubtypeOfName.length()-7) ;
            
            
            //System.out.println("*********************"+entitySubtypeOfName);
            
            Individual entity = ontModel.createIndividual(getNSFor(entityName, mpiroNS),
                    ontModel.getOntClass(getNSFor(convertToClassName(entitySubtypeOfName), mpiroNS)));
            
            NodeVector entityNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityName);
            Vector entityIndependentFieldsVector = entityNode.getIndependentFieldsVector();
        }
        
        //add Properties values
        allEntityNames = allEntities.keys();
        allEntitySubtypeOfNames = allEntities.elements();
        
        while (allEntityNames.hasMoreElements()) {
            String entityName = allEntityNames.nextElement().toString();
            String entitySubtypeOfName = allEntitySubtypeOfNames.nextElement().toString();
            
            
            if (entityName.substring(0,entityName.length()-1).endsWith("occur"))
                entityName=entityName.substring(0,entityName.length()-7) ;
            if (entitySubtypeOfName.substring(0,entitySubtypeOfName.length()-1).endsWith("occur"))
                entitySubtypeOfName=entitySubtypeOfName.substring(0,entitySubtypeOfName.length()-7) ;
            
            
            
            NodeVector entityNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityName);
            Vector fileds=(Vector) entityNode.elementAt(0);
            //Language indepedent properties
            Vector entityIndependentFieldsVector =(Vector) fileds.elementAt(0);
            for (int i = 0; i < entityIndependentFieldsVector.size(); i++) {
                FieldData propertyFieldData = (FieldData) entityIndependentFieldsVector.get(i);
                
                ObjectProperty objProperty = ontModel.getObjectProperty(getNSFor(convertToPropertyName(propertyFieldData.elementAt(0).toString()), mpiroNS));
                DatatypeProperty datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertToPropertyName(propertyFieldData.elementAt(0).toString()), mpiroNS));
                if (objProperty != null) {
                    StringTokenizer strTokenizer = new StringTokenizer(propertyFieldData.elementAt(1).toString(), " ");
                    while (strTokenizer.hasMoreTokens()) {
                        String currentPropValue = strTokenizer.nextToken();
                        Individual propertyValue = ontModel.getIndividual(getNSFor(currentPropValue, mpiroNS));
                        if (propertyValue != null)
                            ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(objProperty, propertyValue);
                    }
                } else if (datatypeProperty != null) {
                    //System.out.println(entityName+"..."+propertyFieldData);
                    if (!propertyFieldData.m_filler.equals("")&&!propertyFieldData.m_filler.startsWith("Select")){
                        ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                ontModel.createTypedLiteral(propertyFieldData.m_filler,
                                com.hp.hpl.jena.datatypes.TypeMapper.getInstance().getTypeByName(datatypeProperty.getRange().getURI())));
                    }}
            }
            
            //English Properties
            Vector entityEnglishFieldsVector = (Vector) fileds.elementAt(1);
            for (int i = 0; i < entityEnglishFieldsVector.size(); i++) {
                FieldData propertyFieldData = (FieldData) entityEnglishFieldsVector.get(i);
                if(propertyFieldData.m_filler.equals("String")){
                    DatatypeProperty datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertToPropertyName(propertyFieldData.m_field), mpiroNS));
                    if (datatypeProperty != null) {
                        if (!propertyFieldData.m_filler.equals(""))
                            ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                    ontModel.createLiteral(propertyFieldData.m_filler, "EN"));
                    } else {
                        datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertMpiroToOwlPropertyName(propertyFieldData.m_field), mpiroNS));
                        if (datatypeProperty != null)
                            if (!propertyFieldData.m_filler.equals(""))
                                ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                        ontModel.createLiteral(propertyFieldData.m_filler, "EN"));
                    }
                }
            }
            
            //ontModel.createIntersectionClass(null, ontModel.createList(new RDFNode[] {ontModel.createSomeValuesFromRestriction(null, ontModel.getOntProperty("cost"), ontModel.getOntClass("Money")),ontModel.getOntClass("Computer")} ));
            //Italian Properties
            Vector entityItalianFieldsVector =(Vector) fileds.elementAt(2);
            for (int i = 0; i < entityItalianFieldsVector.size(); i++) {
                FieldData propertyFieldData = (FieldData) entityItalianFieldsVector.get(i);
                if(propertyFieldData.m_filler.equals("String")){
                    DatatypeProperty datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertToPropertyName(propertyFieldData.m_field), mpiroNS));
                    if (datatypeProperty != null) {
                        if (!propertyFieldData.m_filler.equals(""))
                            ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                    ontModel.createLiteral(propertyFieldData.m_filler, "IT"));
                    } else {
                        datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertMpiroToOwlPropertyName(propertyFieldData.m_field), mpiroNS));
                        if (datatypeProperty != null)
                            if (!propertyFieldData.m_filler.equals(""))
                                ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                        ontModel.createLiteral(propertyFieldData.m_filler, "IT"));
                    }
                }
            }
            //Greek Properties
            Vector entityGreekFieldsVector = (Vector) fileds.elementAt(3);
            for (int i = 0; i < entityGreekFieldsVector.size(); i++) {
                FieldData propertyFieldData = (FieldData) entityGreekFieldsVector.get(i);
                if(propertyFieldData.m_filler.equals("String")){
                    DatatypeProperty datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertToPropertyName(propertyFieldData.m_field), mpiroNS));
                    if (datatypeProperty != null) {
                        if (!propertyFieldData.m_filler.equals(""))
                            ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                    ontModel.createLiteral(propertyFieldData.m_filler, "GRC"));
                    } else {
                        datatypeProperty = ontModel.getDatatypeProperty(getNSFor(convertMpiroToOwlPropertyName(propertyFieldData.m_field), mpiroNS));
                        if (datatypeProperty != null)
                            if (!propertyFieldData.m_filler.equals(""))
                                ontModel.getIndividual(getNSFor(entityName, mpiroNS)).addProperty(datatypeProperty,
                                        ontModel.createLiteral(propertyFieldData.m_filler, "GRC"));
                    }
                }
            }
        }
        
        // Hashtable restrictions=QueryHashtable.valueRestrictionsHashtable;
        Enumeration keys=Mpiro.win.struc.restrictionKeys();
        Enumeration elements=Mpiro.win.struc.restrictions();
        while(keys.hasMoreElements()){
            String[] nextKey=keys.nextElement().toString().split(":");
            Vector nextElement=(Vector) elements.nextElement();
            Vector subVector=(Vector) nextElement.elementAt(0);
            for(int r=0;r<subVector.size();r++){
                try {
                    //    OntProperty p=ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1]));
                    AllValuesFromRestriction allVal= ontModel.createAllValuesFromRestriction(null,ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1])),ontModel.getOntClass(mpiroNS + convertToClassName(subVector.elementAt(r).toString())));
                    ontModel.getOntClass(getNSFor(convertToClassName(nextKey[0]), mpiroNS)).addSuperClass(allVal);
                }catch(java.lang.IllegalArgumentException h){
                    continue;
                }}
            subVector=(Vector) nextElement.elementAt(1);
            for(int r=0;r<subVector.size();r++){
                try {
                    
                    //    OntProperty p=ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1]));
                    SomeValuesFromRestriction allVal= ontModel.createSomeValuesFromRestriction(null,ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1])),ontModel.getOntClass(mpiroNS + convertToClassName(subVector.elementAt(r).toString())));
                    ontModel.getOntClass(getNSFor(convertToClassName(nextKey[0]), mpiroNS)).addSuperClass(allVal);
                }catch(java.lang.IllegalArgumentException h){
                    continue;
                }}
            
            subVector=(Vector) nextElement.elementAt(2);
            for(int r=0;r<subVector.size();r++){
                
                //    OntProperty p=ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1]));
                HasValueRestriction allVal= ontModel.createHasValueRestriction(null,ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1])),ontModel.createLiteral(subVector.elementAt(r).toString()));
                try{
                    allVal= ontModel.createHasValueRestriction(null,ontModel.getOntProperty(getNSFor(convertToPropertyName(nextKey[1]), mpiroNS)),ontModel.getIndividual(getNSFor(subVector.elementAt(r).toString(), mpiroNS)));
                }catch(java.lang.IllegalArgumentException h){
                    continue;
                }
                
                ontModel.getOntClass(getNSFor(convertToClassName(nextKey[0]),mpiroNS)).addSuperClass(allVal);
            }
            
            subVector=(Vector) nextElement.elementAt(3);
            for(int r=0;r<subVector.size();r++){
                try {
                    
                    //    OntProperty p=ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1]));
                    MaxCardinalityRestriction allVal= ontModel.createMaxCardinalityRestriction(null,ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1])),Integer.parseInt(subVector.elementAt(r).toString()));
                    ontModel.getOntClass(getNSFor(convertToClassName(nextKey[0]), mpiroNS)).addSuperClass(allVal);
                } catch (java.lang.IllegalArgumentException ex) {
                    continue;
                }
            }
            
            subVector=(Vector) nextElement.elementAt(4);
            for(int r=0;r<subVector.size();r++){
                try {
                    //    OntProperty p=ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1]));
                    MinCardinalityRestriction allVal= ontModel.createMinCardinalityRestriction(null,ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1])),Integer.parseInt(subVector.elementAt(r).toString()));
                    ontModel.getOntClass(getNSFor(convertToClassName(nextKey[0]), mpiroNS)).addSuperClass(allVal);
                } catch (java.lang.IllegalArgumentException ex) {
                    continue;
                }
            }
            
            subVector=(Vector) nextElement.elementAt(5);
            for(int r=0;r<subVector.size();r++){
                try {
                    //    OntProperty p=ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1]));
                    CardinalityRestriction allVal= ontModel.createCardinalityRestriction(null,ontModel.getOntProperty(mpiroNS + convertToPropertyName(nextKey[1])),Integer.parseInt(subVector.elementAt(r).toString()));
                    ontModel.getOntClass(getNSFor(convertToClassName(nextKey[0]), mpiroNS)).addSuperClass(allVal);
                } catch (java.lang.IllegalArgumentException ex) {
                    continue;
                }
            }
        }
        
        
        
        Enumeration echk=Mpiro.win.struc.equivalentClassesHashtableKeys();
        while(echk.hasMoreElements()){
            String cl=echk.nextElement().toString();
            Vector eqClasses=(Vector) Mpiro.win.struc.getEquivalentClasses(cl);
            for(int i=0;i<eqClasses.size();i++){
                ontModel.getOntClass(getNSFor(convertToClassName(cl), mpiroNS)).addEquivalentClass((OntClass)ClassParser.parsing(eqClasses.elementAt(i).toString(), ontModel, mpiroNS));
            }
        }
        
        Enumeration complexSuperClasses=Mpiro.win.struc.superClassesHashtableKeys();
        while(complexSuperClasses.hasMoreElements()){
            String cl=complexSuperClasses.nextElement().toString();
            Vector superClasses=(Vector) Mpiro.win.struc.getSuperClasses(cl);
            for(int i=0;i<superClasses.size();i++){
                ontModel.getOntClass(getNSFor(convertToClassName(cl), mpiroNS)).addSuperClass((OntClass)ClassParser.parsing(superClasses.elementAt(i).toString(), ontModel, mpiroNS));
            }
        }
        
        for(Enumeration anKeys=Mpiro.win.struc.annotationPropertiesHashtableKeys(), anElements=Mpiro.win.struc.annotationPropertiesHashtableElements();anKeys.hasMoreElements();){
            /// Hashtable temp=QueryHashtable.annotationPropertiesHashtable;
            Vector anProps=(Vector) anElements.nextElement();
            String className=anKeys.nextElement().toString();
            for(int l=0;l<anProps.size();l++){
                Vector anProp=(Vector)anProps.elementAt(l);
                if(anProp.elementAt(1)==null) continue;
                // System.out.println("  ::::: "+ontModel.getOntResource(mpiroNS+convertToClassName(className)).getLocalName());
                OntResource ontRes=ontModel.getOntResource(getNSFor(convertToClassName(className),mpiroNS));
                try {
                    ontRes.toString();
                } catch(java.lang.NullPointerException npe) {
                    ontRes=ontModel.getOntResource(getNSFor(className, mpiroNS));
                }
                
                try {
                    ontRes.toString();
                } catch(java.lang.NullPointerException npe) {
                    break;
                }
                Object type=anProp.elementAt(0);
                // System.out.println("^^^^^^ "+type.toString()+" ^^^^ "+className+" ^^^^  "+anProp.toString());
                if(type.equals("rdfs:isDefinedBy"))
                    ontRes.addIsDefinedBy(ontModel.getResource(getNSFor(anProp.elementAt(1).toString(), mpiroNS)));
                if(type.equals("rdfs:seeAlso"))
                    ontRes.addSeeAlso(ontModel.getResource(getNSFor(anProp.elementAt(1).toString(), mpiroNS)));
                if(type.equals("rdfs:label"))
                    ontRes.addLabel(anProp.elementAt(1).toString(), anProp.elementAt(2).toString());
                if(type.equals("rdfs:comment"))
                    ontRes.addComment(anProp.elementAt(1).toString(), anProp.elementAt(2).toString());
                if(type.equals("owl:versionInfo"))
                    ontRes.addVersionInfo(anProp.elementAt(1).toString());
            }}
        
        
        //  Resource test=ontModel.createResource();
        //  classParser.parsing("((= 666 Property:area)v(-({Individual:educational1, Individual:educational2, Individual:educational3})))", ontModel, mpiroNS);
        
        //write the OWL file
        // OntClass temp=(OntClass)ontModel.listClasses().next();
        
        RDFWriter w = ontModel.getWriter(format);
        w.setProperty("xmlbase", mpiroNS);
        w.setProperty("showXmlDeclaration","true");
        w.setProperty("showXmlDeclaration","true");
        w.write(ontModel, output, mpiroPath);
        if(exportlexicon){
            if (rdfFile.getAbsolutePath().indexOf(".")!=-1)
                exportLexiconToXmlFile(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf('.')) + "_mpiro.xml");
            else
                exportLexiconToXmlFile(rdfFile.getAbsolutePath() + "_mpiro.xml");
        } else{
            
            // ExtendedIterator ei=ontModel.listIndividuals();
            //   while(ei.hasNext()){
            //      Mpiro.comparTree.addElement(((Individual)ei.next()).getURI(),ontModel);
            //  }
            //   Mpiro.comparTree.print();
            //   Mpiro.comparTree.sortAttributes();
        }
        
        
        
        
        
        // galanis(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf(ontname)), url);
        
 /*   State s1=new State(new String("k1"),true, false);
    State s2=new State(new String("k2"));
    State s3=new State(new String("k3"),false, true);
    State[] st = {s1,s2,s3};
  
    Bottom B = new Bottom();
   // Word E = new Word();
  
    Object ob[] = {
    new String("("),
    new String("E"),
    new OntStackElement("Property",null),
    new String("v"),
    new String("-"),
    new String(")"),
    new String("C"),
    new String("P"),
    new String("$")
    };
  
    Alphabet alph=new Alphabet(ob);
    OntStackElement P=new OntStackElement("Property",null);
    OntStackElement C=new OntStackElement("Class",null);
    Object cla[] = {
    new String("v"),
    P
    };
    Object prope[] = {
    P,
    C
    };
    Object pp[] = {
    P,
    new String("(")
    };
  
  
    PDAI i[] = {
    new PDAI(s1, "(", "$", s1, new Word("$ (".split(" "))),
    new PDAI(s1, "(", P, s1, new Word(pp)),
    //new PDAI(s1, "(", "E", s1, new Word("E (".split(" "))),
    new PDAI(s1, "E", "(", s1, new Word("( E".split(" "))),
    new PDAI(s1, P, "v", s1, new Word(cla)),
    new PDAI(s1, P, "E", s1, new Word("E P".split(" "))),
    new PDAI(s1, "v", "(", s1, new Word("( v".split(" "))),
  //  new PDAI(s1, "-", new Word(), s1, new Word("-")),
    new PDAI(s1, ")", "(", s1, new Word()),
    new PDAI(s1, C, P, s2, new Word(prope)),
    new PDAI(s2, ")", "(", s2, new Word()),
    new PDAI(s2, new Word(), C, s2, new Word()),
    new PDAI(s2, new Word(), "v", s2, new Word()),
    new PDAI(s2, new Word(), "E", s2, new Word()),
    new PDAI(s2, new Word(), P, s2, new Word()),
   // new PDAI(s2, ")", "(", s1, new Word("( )".split(" "))),
    new PDAI(s2, new Word(), "$", s3, new Word())
    };
    Object input[] = {
    new String("("),
    new String("v"),
    new OntStackElement("Property","building-has-areas"),
    new String("("),
    new String("v"),
    new OntStackElement("Property","building-has-areas"),
   new OntStackElement("Class","Building"),
    new String(")"),
    new String(")")
    };
  
  
   // try {
      PDA ka = new PDA(alph,alph,"$",st,i);
      Word inp=new Word(input);
      ka.loadTape(0, inp);
      try {
          OntProperty prop;
          OntClass cl=ontModel.createClass(null);
      while(!ka.result()){
      Instruction ins=ka.step(null);
    //  System.out.println(ka.getIdentifier().toString());
  //    System.out.println(ka.getContentString());
    //  System.out.println(inp.toString());
    //  if(ins.toString().contains("(k2,,"+P.toString()))
        //  prop=ontModel.getOntProperty(((OntStackElement)ka.getIdentifier()).elementAt(1).toString());
    //  if(ins.toString().contains("(k2,,C)"))
         // cl=ontModel.getOntClass("C");
      // if(ins.toString().contains("(k2,,v)"))
          // cl=ontModel.createAllValuesFromRestriction(null,prop,cl);
      //if(ins.toString().contains("(k2,,E)"))
          // cl=ontModel.createSomeValuesFromRestriction(null,prop,cl);
      System.out.print(ins+"\t| "+ka.getConfiguration()+"\n");
     // if (ka.getStepcounter()==)
      }}
       catch(NonDeterministicException e) {
      Instruction ins[] = e.getChoices();
      StepAutomaton branch[] = ka.fork(ins.length);
      System.out.println("[BRANCH CRASHED]");
      System.out.println("Choices: ");
      for(int g=0;g<ins.length;g++) System.out.println(ins[g]);
  
      for(int x=0;x<ins.length;x++) {
        System.out.println("Starting: "+ins[x]+"\n");
        Computer com = new Computer(branch[x],ins[x]);
        com.compute();
      }
    }
      System.out.println("Automaton:OK");
  */
        
        
        
    }
    //   Computer com = new Computer(ka);
    //   com.compute();
    //  }
    // catch(Exception e) { e.printStackTrace(); }
    
    
    
    
    
    
    
    
   /* public PDAI(State cur,
            java.lang.Object isym,
            java.lang.Object ssym,
            State next,
            Word pw)
    */
    
    
    
    private static void addUpperModelClasses(OntModel ontModel, String mpiroNS) {
        OntClass mpiroBasicEntityType = ontModel.createClass(mpiroNS + "Basic-Entity-Types");
        //add UpperModel properties
        DatatypeProperty tempProp = ontModel.createDatatypeProperty(mpiroNS + "title");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "name");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "name-nominative");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "name-genitive");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "name-accusative");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "gender-name");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "shortname");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "shortname-nominative");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "shortname-genitive");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "shortname-accusative");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "gender-shortname");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "gender");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "notes");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "number");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        tempProp = ontModel.createDatatypeProperty(mpiroNS + "images");
        tempProp.addDomain(mpiroBasicEntityType);
        tempProp.addRange(com.hp.hpl.jena.vocabulary.XSD.xstring);
        
        //end add prop
        
        NodeVector rootVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
        Vector upperVector = (Vector) rootVector.elementAt(1);
        
        for (int i = 0; i < upperVector.size(); i++) {
            OntClass tempClass = ontModel.createClass(mpiroNS + convertToUpperModelName(upperVector.get(i).toString()));
            tempClass.addSuperClass(mpiroBasicEntityType);
        }
        
        /*        OntClass tempClass = ontModel.createClass(mpiroNS + "3D-physical-object");
          tempClass.addSuperClass(mpiroBasicEntityType);
         
          tempClass = ontModel.createClass(mpiroNS + "Named-time-period");
          tempClass.addSuperClass(mpiroBasicEntityType);
         
          tempClass = ontModel.createClass(mpiroNS + "Spatial-location");
          tempClass.addSuperClass(mpiroBasicEntityType);
         
          tempClass = ontModel.createClass(mpiroNS + "Human");
          tempClass.addSuperClass(mpiroBasicEntityType);
         
          tempClass = ontModel.createClass(mpiroNS + "Substance-thing");
          tempClass.addSuperClass(mpiroBasicEntityType);
         
          tempClass = ontModel.createClass(mpiroNS + "Other-abstraction");
          tempClass.addSuperClass(mpiroBasicEntityType);*/
    }
    
    public static String convertToClassName(String name) {
        if(name.matches("(?i).*http://.*"))
            return name;
        else{
            
            if (name != null && name.length() > 0)
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
            return name;}
    }
    
    private static String convertToUpperModelName(String name) {
        if (name != null && name.length() > 0)
            name = "UM_" + name;
        return name;
    }
    
    private static String convertToPropertyName(String name) {
        // if (name != null && name.length() > 0)
        //   name = name.substring(0, 1).toLowerCase() + name.substring(1);
        
        return name;
    }
    
    private static String convertMpiroToOwlPropertyName(String propName) {
        if (propName.equalsIgnoreCase("name (nominative)"))
            return "name-nominative";
        else if (propName.equalsIgnoreCase("name (genitive)"))
            return "name-genitive";
        else if (propName.equalsIgnoreCase("name (accusative)"))
            return "name-accusative";
        else if (propName.equalsIgnoreCase("shortname (nominative)"))
            return "shortname-nominative";
        else if (propName.equalsIgnoreCase("shortname (genitive)"))
            return "shortname-genitive";
        else if (propName.equalsIgnoreCase("shortname (accusative)"))
            return "shortname-accusative";
        else if (propName.equalsIgnoreCase("grammatical gender of name"))
            return "gender-name";
        else if (propName.equalsIgnoreCase("grammatical gender of shortname"))
            return "gender-shortname";
        else return null;
    }
    
    public static void exportLexicon(String fileName,String uri) throws Exception {
        // Mpiro.comparTree = new ComparisonTree();
        // Mpiro.statisticalTree = new ComparisonTree();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element RDF=doc.createElement("rdf:RDF");
        doc.appendChild(RDF);
        //  RDF.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        //  RDF.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        //   RDF.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/UserModelling#");
        //   RDF.setAttribute("xml:base","http://www.aueb.gr/users/ion/owlnl/UserModelling");
        RDF.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/UserModelling#");
        RDF.setAttribute("xml:base","http://www.aueb.gr/users/ion/owlnl/UserModelling");
        //RDF.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        
        
        
        
        Element lexicon=doc.createElement("owlnl:Lexicon");
        RDF.appendChild(lexicon);
        
        //      Element ontology=doc.createElement("owlnl:Ontology");
        //      ontology.setAttribute("rdf:resource",uri);
        //      lexicon.appendChild(ontology);
        
        Element nounsElement = doc.createElement("owlnl:NPList");
        nounsElement.setAttribute("rdf:parseType","Collection");
        //Element style=doc.createElement("xsl:stylesheet");
        //         style.setAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
        //       style.appendChild(nounsElement);
        lexicon.appendChild(nounsElement);
        
        Element Mapping=doc.createElement("owlnl:Mapping");
        RDF.appendChild(Mapping);
        Element entries=doc.createElement("owlnl:Entries");
        entries.setAttribute("rdf:parseType","Collection");
        Mapping.appendChild(entries);
        Hashtable allTypes=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type");
        Enumeration keys=allTypes.keys();
        //   Enumeration elements=allTypes.elements();
        while(keys.hasMoreElements()){
            try {
                String key=keys.nextElement().toString();
                
                NodeVector nv= (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(key);
                Vector nouns=(Vector) nv.nounVector.clone();
                Vector parents=Mpiro.win.struc.getParents(key);
                for(int i=0;i<parents.size();i++){
                    NodeVector parent=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parents.elementAt(i).toString());
                    Vector parentNouns=parent.nounVector;
                    for(int j=0;j<parentNouns.size();j++){
                        if(nouns.contains(parentNouns.elementAt(j)))
                            nouns.remove(parentNouns.elementAt(j));
                    }
                }
                if (!nouns.isEmpty()){
                    Element owlClass=doc.createElement("owlnl:owlClass");
                    owlClass.setAttribute("rdf:about",uri+'#'+convertToClassName(key));
                    Element hasNP=doc.createElement("owlnl:hasNP");
                    hasNP.setAttribute("rdf:resource","#"+nouns.elementAt(0).toString()+"-NP");//!!!! only one noun????
                    owlClass.appendChild(hasNP);
                    entries.appendChild(owlClass);
                }
            } catch (java.lang.Exception ex) {
                continue;
            }
            
        }
        Hashtable allEntities=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity+generic");
        Enumeration Entkeys=allEntities.keys();
        //    Enumeration Entelements=allTypes.elements();
        while(Entkeys.hasMoreElements()){
            //for(int i = 0; i < Instances.length; i++)
            
            //Mpiro.statisticalTree.addElement(((OntObject)Instances[i]).getURI(),ontModel);
            
            String key=Entkeys.nextElement().toString();
            NodeVector nv=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(key);
            Element owlInstance=doc.createElement("owlnl:owlInstance");
            owlInstance.setAttribute("rdf:about",uri+'#'+key);
            Element hasNP=doc.createElement("owlnl:hasNP");
            hasNP.setAttribute("rdf:resource","#"+key+"-NP");//!!!! only one noun????
            owlInstance.appendChild(hasNP);
            entries.appendChild(owlInstance);
            
            Element curNounElement = doc.createElement("owlnl:NP");
            curNounElement.setAttribute("rdf:ID",key+"-NP");//nounName to be replaced with namespace!!!!!
            nounsElement.appendChild(curNounElement);
            Element NP =doc.createElement("owlnl:LanguagesNP");
            NP.setAttribute("rdf:parseType","Collection");//!!!!!!!!! to be replaced by gender of datatype
            // NP.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            Element englishNP=doc.createElement("owlnl:EnglishNP");
            NP.appendChild(englishNP);
            Vector english=nv.englishFieldsVector;
            
            Element countable3=doc.createElement("owlnl:countable");
            //
            countable3.appendChild(doc.createTextNode("no"));
            // else
            //   countable1.appendChild(doc.createTextNode("yes"));
            englishNP.appendChild(countable3);
            Element num=doc.createElement("owlnl:num");
            Vector temp=(Vector) english.elementAt(5);
            num.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            englishNP.appendChild(num);
            Element gender=doc.createElement("owlnl:gender");
            temp=(Vector) english.elementAt(4);
            if(temp.elementAt(1).toString().equalsIgnoreCase("neuter"))
                gender.appendChild(doc.createTextNode("nonpersonal"));
            else
                gender.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            englishNP.appendChild(gender);
            Element singular=doc.createElement("owlnl:singular");
            singular.setAttribute("xml:lang","en");
            Element plural=doc.createElement("owlnl:plural");
            plural.setAttribute("xml:lang","en");
            temp=(Vector) english.elementAt(5);
            if(temp.elementAt(1).toString().equalsIgnoreCase("singular")){
                temp=(Vector) english.elementAt(1);
                singular.appendChild(doc.createTextNode(temp.elementAt(1).toString()));} else{
                temp=(Vector) english.elementAt(1);
                plural.appendChild(doc.createTextNode(temp.elementAt(1).toString()));}
            englishNP.appendChild(singular);
            englishNP.appendChild(plural);
            curNounElement.appendChild(NP);
            
            
            Vector greek=nv.greekFieldsVector;
            Element greekNP=doc.createElement("owlnl:GreekNP");
            NP.appendChild(greekNP);
            Element countable2=doc.createElement("owlnl:countable");
            //
            countable2.appendChild(doc.createTextNode("no"));
            // else
            //   countable1.appendChild(doc.createTextNode("yes"));
            greekNP.appendChild(countable2);
            Element num1=doc.createElement("owlnl:num");
            temp=(Vector) greek.elementAt(10);
            num1.appendChild(doc.createTextNode(temp.elementAt(1).toString()));//!!!!!!!!! to be replaced by number of datatype
            greekNP.appendChild(num1);
            Element gender1=doc.createElement("owlnl:gender");
            temp=(Vector) greek.elementAt(4);
            if(temp.elementAt(1).toString().equalsIgnoreCase("neuter"))
                gender1.appendChild(doc.createTextNode("neuter"));
            else
                gender1.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            greekNP.appendChild(gender1);
            Element singular1=doc.createElement("owlnl:singular");
            Element plural1=doc.createElement("owlnl:plural");
            Element singForms=doc.createElement("owlnl:singularForms");
            Element pluralForms=doc.createElement("owlnl:pluralForms");
            Element nominSin=doc.createElement("owlnl:nominative");
            Element nominPlu=doc.createElement("owlnl:nominative");
            Element genSin=doc.createElement("owlnl:genitive");
            Element genPlu=doc.createElement("owlnl:genitive");
            Element accSin=doc.createElement("owlnl:accusative");
            Element accPlu=doc.createElement("owlnl:accusative");
            nominSin.setAttribute("xml:lang","el");
            nominPlu.setAttribute("xml:lang","el");
            genSin.setAttribute("xml:lang","el");
            genPlu.setAttribute("xml:lang","el");
            accSin.setAttribute("xml:lang","el");
            accPlu.setAttribute("xml:lang","el");
            if(!greek.elementAt(10).toString().contains("plural")){
                temp=(Vector) greek.elementAt(1);
                nominSin.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(2);
                genSin.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(3);
                accSin.appendChild(doc.createTextNode(temp.elementAt(1).toString()));} else{
                temp=(Vector) greek.elementAt(1);
                nominPlu.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(2);
                genPlu.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(3);
                accPlu.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                }
            singForms.appendChild(nominSin);
            singForms.appendChild(genSin);
            singForms.appendChild(accSin);
            pluralForms.appendChild(nominPlu);
            pluralForms.appendChild(genPlu);
            pluralForms.appendChild(accPlu);
            singular1.appendChild(singForms);
            plural1.appendChild(pluralForms);
            greekNP.appendChild(singular1);
            greekNP.appendChild(plural1);
        }
        
        
        
        
        
        //       DocumentBuilder docBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //    Document doc1 = docBuilder1.newDocument();
        //     Element nounsElement1 = doc1.createElement("lexicon");
        //     nounsElement1.setAttribute("language","gr");
        //   Element style1=doc1.createElement("xsl:stylesheet");
        //          style1.setAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
        //       style1.appendChild(nounsElement1);
        //              doc1.appendChild(nounsElement1);
        Hashtable allNounHashTable =  Mpiro.win.struc.getNounsHashtable();
        //    Hashtable allVerbHashTable = (Hashtable) Mpiro.win.struc.getVerbsHashtable();
        
        //nouns
        Enumeration nounNameEnu = allNounHashTable.keys();
        Enumeration nounHashTableEnu = allNounHashTable.elements();
        while (nounNameEnu.hasMoreElements()) {
            String nounName = (String) nounNameEnu.nextElement();
            Hashtable nounHashTable = (Hashtable) nounHashTableEnu.nextElement();
            Hashtable englishNouns= (Hashtable) nounHashTable.get("English");
            Element curNounElement = doc.createElement("owlnl:NP");
            curNounElement.setAttribute("rdf:ID",nounName+"-NP");//nounName to be replaced with namespace!!!!!
            nounsElement.appendChild(curNounElement);
            Element NP =doc.createElement("owlnl:LanguagesNP");
            NP.setAttribute("rdf:parseType","Collection");//!!!!!!!!! to be replaced by gender of datatype
            // NP.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            Element englishNP=doc.createElement("owlnl:EnglishNP");
            NP.appendChild(englishNP);
            Element countable4=doc.createElement("owlnl:countable");
            if (englishNouns.get("countable").toString().equalsIgnoreCase("No"))
                countable4.appendChild(doc.createTextNode("no"));
            else
                countable4.appendChild(doc.createTextNode("yes"));
            englishNP.appendChild(countable4);
            Element num=doc.createElement("owlnl:num");
            num.appendChild(doc.createTextNode("singular"));
            englishNP.appendChild(num);
            Element gender=doc.createElement("owlnl:gender");
            gender.appendChild(doc.createTextNode("nonpersonal"));
            englishNP.appendChild(gender);
            Element singular=doc.createElement("owlnl:singular");
            singular.setAttribute("xml:lang","en");
            Element plural=doc.createElement("owlnl:plural");
            plural.setAttribute("xml:lang","en");
            singular.appendChild(doc.createTextNode(englishNouns.get("enbasetext").toString()));
            plural.appendChild(doc.createTextNode(englishNouns.get("enpluraltext").toString()));
            englishNP.appendChild(singular);
            englishNP.appendChild(plural);
            curNounElement.appendChild(NP);
            
            
            
            
            
            
            Hashtable greekNouns= (Hashtable) nounHashTable.get("Greek");
            //  Element curNounElement1 = doc1.createElement("OntClass");
            //    curNounElement1.setAttribute("name",nounName);//nounName to be replaced with namespace!!!!!
            //    nounsElement1.appendChild(curNounElement1);
            //    Element NP1 =doc1.createElement("NP");
            //    NP1.setAttribute("gender",greekNouns.get("grgender").toString().toLowerCase());
            //    if (greekNouns.get("grgender").toString().equalsIgnoreCase("Neuter"))
            //        NP1.setAttribute("gender","nonpersonal");
            //  NP1.setAttribute("gender","nonpersonal");//!!!!!!!!! to be replaced by gender of datatype
            //     NP1.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            //     if (greekNouns.get("countable").toString().equalsIgnoreCase("No"))
            //         NP1.setAttribute("countable","F");
            //     else
            //          NP1.setAttribute("countable","T");
 /*           Element singular1=doc1.createElement("singular");
            Element plural1=doc1.createElement("plural");
            Element nomsin=doc1.createElement("nominative");
            Element nomplu=doc1.createElement("nominative");
            Element gensin=doc1.createElement("genitive");
            Element genplu=doc1.createElement("genitive");
            Element accsin=doc1.createElement("accusative");
            Element accplu=doc1.createElement("accusative");
            nomsin.appendChild(doc1.createTextNode(greekNouns.get("grbasetext").toString()));
            nomplu.appendChild(doc1.createTextNode(greekNouns.get("grpluraltext").toString()));
            genplu.appendChild(doc1.createTextNode(greekNouns.get("grpgtext").toString()));
            gensin.appendChild(doc1.createTextNode(greekNouns.get("grsgtext").toString()));
            accplu.appendChild(doc1.createTextNode(greekNouns.get("grpatext").toString()));
            accsin.appendChild(doc1.createTextNode(greekNouns.get("grsatext").toString()));
            singular1.appendChild(nomsin);
            singular1.appendChild(gensin);
            singular1.appendChild(accsin);
            plural1.appendChild(nomplu);
            plural1.appendChild(genplu);
            plural1.appendChild(accplu);
            NP1.appendChild(singular1);
            NP1.appendChild(plural1);
            curNounElement1.appendChild(NP1);
  */
            
            
            
            
            
            Element greekNP=doc.createElement("owlnl:GreekNP");
            NP.appendChild(greekNP);
            Element countable1=doc.createElement("owlnl:countable");
            if (greekNouns.get("countable").toString().equalsIgnoreCase("No"))
                countable1.appendChild(doc.createTextNode("no"));
            else
                countable1.appendChild(doc.createTextNode("yes"));
            greekNP.appendChild(countable1);
            Element num1=doc.createElement("owlnl:num");
            num1.appendChild(doc.createTextNode("singular"));//!!!!!!!!! to be replaced by number of datatype
            greekNP.appendChild(num1);
            Element gender1=doc.createElement("owlnl:gender");
            if(greekNouns.get("grgender").toString().equalsIgnoreCase("neuter"))
                gender1.appendChild(doc.createTextNode("neuter"));
            else
                gender1.appendChild(doc.createTextNode(greekNouns.get("grgender").toString().toLowerCase()));
            greekNP.appendChild(gender1);
            Element singular1=doc.createElement("owlnl:singular");
            Element plural1=doc.createElement("owlnl:plural");
            Element singForms=doc.createElement("owlnl:singularForms");
            Element pluralForms=doc.createElement("owlnl:pluralForms");
            Element nominSin=doc.createElement("owlnl:nominative");
            Element nominPlu=doc.createElement("owlnl:nominative");
            Element genSin=doc.createElement("owlnl:genitive");
            Element genPlu=doc.createElement("owlnl:genitive");
            Element accSin=doc.createElement("owlnl:accusative");
            Element accPlu=doc.createElement("owlnl:accusative");
            nominSin.setAttribute("xml:lang","el");
            nominPlu.setAttribute("xml:lang","el");
            genSin.setAttribute("xml:lang","el");
            genPlu.setAttribute("xml:lang","el");
            accSin.setAttribute("xml:lang","el");
            accPlu.setAttribute("xml:lang","el");
            if(greekNouns.containsKey("grbasetext"))
                nominSin.appendChild(doc.createTextNode(greekNouns.get("grbasetext").toString()));
            if(greekNouns.containsKey("grpluraltext"))
                nominPlu.appendChild(doc.createTextNode(greekNouns.get("grpluraltext").toString()));
            if(greekNouns.containsKey("grsgtext"))
                genSin.appendChild(doc.createTextNode(greekNouns.get("grsgtext").toString()));
            if(greekNouns.containsKey("grpgtext"))
                genPlu.appendChild(doc.createTextNode(greekNouns.get("grpgtext").toString()));
            if(greekNouns.containsKey("grsatext"))
                accSin.appendChild(doc.createTextNode(greekNouns.get("grsatext").toString()));
            if(greekNouns.containsKey("grpatext"))
                accPlu.appendChild(doc.createTextNode(greekNouns.get("grpatext").toString()));
            singForms.appendChild(nominSin);
            singForms.appendChild(genSin);
            singForms.appendChild(accSin);
            pluralForms.appendChild(nominPlu);
            pluralForms.appendChild(genPlu);
            pluralForms.appendChild(accPlu);
            singular1.appendChild(singForms);
            plural1.appendChild(pluralForms);
            greekNP.appendChild(singular1);
            greekNP.appendChild(plural1);
            
            
        }
        
        
        
        Enumeration annotationKeys=Mpiro.win.struc.annotationPropertiesHashtableKeys();
        Enumeration annotationElements=Mpiro.win.struc.annotationPropertiesHashtableElements();
        while(annotationKeys.hasMoreElements()){
            String entity=annotationKeys.nextElement().toString();
            Vector elements=(Vector) annotationElements.nextElement();
            for(int i=0;i<elements.size();i++){
                Vector nextElement=(Vector) elements.elementAt(i);
                if (nextElement.elementAt(1)==null) continue;
                if(!nextElement.elementAt(0).equals("rdfs:seeAlso")&&!nextElement.elementAt(0).equals("rdfs:isDefinedBy")){
                    Element cannedText = doc.createElement("owlnl:CannedText");
                    cannedText.setAttribute("rdf:ID",entity+"-canned-text"+String.valueOf(i));//nounName to be replaced with namespace!!!!!
                    
                    Element val=doc.createElement("owlnl:Val");
                    Element nullVal=doc.createElement("owlnl:Val");
                    
                    if(nextElement.elementAt(2).equals("greek")){
                        //   continue;
                        val.setAttribute("xml:lang","el");
                        nullVal.setAttribute("xml:lang","en");
                    } else{
                        val.setAttribute("xml:lang","en");
                        nullVal.setAttribute("xml:lang","el");
                    }
                    nounsElement.appendChild(cannedText);
                    val.appendChild(doc.createTextNode(nextElement.elementAt(1).toString()));
                    nullVal.appendChild(doc.createTextNode(""));
                    cannedText.appendChild(val);
                    cannedText.appendChild(nullVal);
                    
                    String[] users=nextElement.elementAt(4).toString().split(" ");
                    for(int j=0;j<users.length;j++){
                        Element user=doc.createElement("owlnl:forUserType");
                        user.setAttribute("rdf:resource", "http://www.aueb.gr/users/ion/owlnl/UserModelling#"+users[j]);
                        cannedText.appendChild(user);
                    }
                    Element inst=doc.createElement("owlnl:owlInstance");
                    inst.setAttribute("rdf:about", uri+"#"+entity);
                    Element hasCannedText=doc.createElement("owlnl:hasCannedText");
                    hasCannedText.setAttribute("rdf:resource", "#"+entity+"-canned-text"+String.valueOf(i));
                    inst.appendChild(hasCannedText);
                    entries.appendChild(inst);
                    
                }
            }
        }
        
        File xmlFile1 = new File(fileName+"Lexicon.rdf");
        FileOutputStream output1 = new FileOutputStream(xmlFile1);
        Writer writer = new Writer();
        writer.setOutput(output1,null);
        writer.write(doc);
    }
    public static void exportUserModelling(String fileName,String uri) throws Exception {
        String base= "http://www.aueb.gr/users/ion/owlnl/UserModelling";
        File xmlFile2 = new File(fileName+"UserModelling.rdf");
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc2 = docBuilder.newDocument();
        FileOutputStream output2 = new FileOutputStream(xmlFile2);
        Element RDF2=doc2.createElement("rdf:RDF");
        doc2.appendChild(RDF2);
        RDF2.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF2.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF2.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/UserModelling#");
        RDF2.setAttribute("xml:base",base);
        //RDF2.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        
        
        Hashtable entityTypesHashtables=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        
        Element UserModelling=doc2.createElement("owlnl:UserModelling");
        RDF2.appendChild(UserModelling);
        Element usersElement = doc2.createElement("owlnl:UserTypes");
        usersElement.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(usersElement);
        
        Enumeration userNamesEnu = Mpiro.win.struc.getUserNames();
        Enumeration userValuesEnu = Mpiro.win.struc.getUserElements();
        Vector usersVector = new Vector();
        while (userNamesEnu.hasMoreElements()) {
            String userName = (String) userNamesEnu.nextElement();
            User userVector = (User) userValuesEnu.nextElement();
            usersVector.add(userName);
            Element currentUserElement = doc2.createElement("owlnl:UserType");
            currentUserElement.setAttribute("rdf:ID",userName);
            usersElement.appendChild(currentUserElement);
            
            Element sentenceElement = doc2.createElement("owlnl:MaxFactsPerSentence");
            sentenceElement.appendChild(doc2.createTextNode(userVector.getMaxFactsPerSentence()));
            Element paragraphElement = doc2.createElement("owlnl:FactsPerPage");
            paragraphElement.appendChild(doc2.createTextNode(userVector.getFactsPerPage()));
            //   Element conjunctionElement = doc2.createElement("Link-Number");
            // conjunctionElement.appendChild(doc2.createTextNode(userVector.get(2).toString()));
            Element voiceElement = doc2.createElement("owlnl:SynthesizerVoice");
            voiceElement.appendChild(doc2.createTextNode(userVector.getSynthVoice()));
            
            currentUserElement.appendChild(sentenceElement);
            currentUserElement.appendChild(paragraphElement);
            // currentUserElement.appendChild(conjunctionElement);
            currentUserElement.appendChild(voiceElement);
            
            
        }
        
        
        Element PropertiesInterests  = doc2.createElement("owlnl:PropertiesInterestsRepetitions");
        PropertiesInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(PropertiesInterests);
        Enumeration properties=Mpiro.win.struc.getPropertyNames();
        Enumeration propVectors1=Mpiro.win.struc.getProperties();
        
        while(properties.hasMoreElements()){
            //   Vector property=(Vector) properties.nextElement();
            String nextProp=properties.nextElement().toString();
            Vector nextPropVector=(Vector) propVectors1.nextElement();
            Element Property  = doc2.createElement("owlnl:Property");
            Property.setAttribute("rdf:about",uri+'#'+nextProp);
            PropertiesInterests.appendChild(Property);
            Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
            Enumeration users=usersHash.keys();
            Enumeration values=usersHash.elements();
            while(users.hasMoreElements()){
                Element DPInterest  = doc2.createElement("owlnl:DPInterestRepetitions");
                DPInterest.setAttribute("rdf:parseType","Resource");
                Property.appendChild(DPInterest);
                Element forUserType = doc2.createElement("owlnl:forUserType");
                forUserType.setAttribute("rdf:resource",base+"#"+users.nextElement().toString());
                DPInterest.appendChild(forUserType);
                Element InterestValue= doc2.createElement("owlnl:InterestValue");
                Vector value=(Vector) values.nextElement();
                if(value.elementAt(1).toString().equalsIgnoreCase(""))
                    InterestValue.appendChild(doc2.createTextNode("-1"));
                else
                    InterestValue.appendChild(doc2.createTextNode(value.elementAt(1).toString()));
                DPInterest.appendChild(InterestValue);
                Element InterestValue1= doc2.createElement("owlnl:Repetitions");
                // Vector value=(Vector) values.nextElement();
                if(value.elementAt(2).toString().equalsIgnoreCase(""))
                    InterestValue1.appendChild(doc2.createTextNode("-1"));
                else
                    InterestValue1.appendChild(doc2.createTextNode(value.elementAt(2).toString()));
                DPInterest.appendChild(InterestValue1);
            }
            if(!Mpiro.win.struc.mainUserModelHashtableContainsProperty(nextProp))
                Mpiro.win.struc.putPropertyInMainUserModelHashtable(nextProp, new Hashtable());
            Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(nextProp);
            Enumeration npkeys=np.keys();
            Enumeration npelems=np.elements();
            while(npkeys.hasMoreElements()){
                String nextDomain=npkeys.nextElement().toString();
                
                if(entityTypesHashtables.containsKey(nextDomain)){
                    
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(nextProp, nextDomain);
                    Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                    Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        // if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element CDPInterest  = doc2.createElement("owlnl:CDPInterestRepetitions");
                        CDPInterest.setAttribute("rdf:parseType","Resource");
                        Property.appendChild(CDPInterest);
                        //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                        Element forClass=doc2.createElement("owlnl:forOwlClass");
                        forClass.setAttribute("rdf:resource",uri+"#"+nextDomain);
                        CDPInterest.appendChild(forClass);
                        //String nextuser=users1.nextElement().toString();
                        //Vector usersValue=(Vector) values1.nextElement();
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+nextuser);
                        CDPInterest.appendChild(forUserType);
                        Element InterestValue= doc2.createElement("owlnl:InterestValue");
                        // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                        if(usersValue.elementAt(1).toString().equalsIgnoreCase(""))
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 1)));
                        //InterestValue.appendChild(doc2.createTextNode("-1"));
                        else
                            InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(1).toString()));
                        CDPInterest.appendChild(InterestValue);
                        
                        Element Repetitions= doc2.createElement("owlnl:Repetitions");
                        // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                        if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                            // Repetitions.appendChild(doc2.createTextNode("-1"));
                            Repetitions.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 2)));
                        else
                            Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                        CDPInterest.appendChild(Repetitions);
                    }
                } else
                    if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").containsKey(nextDomain)){
                    
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(nextProp, nextDomain);
                    Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                    Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        //  if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element IPInterest  = doc2.createElement("owlnl:IPInterestRepetitions");
                        IPInterest.setAttribute("rdf:parseType","Resource");
                        Property.appendChild(IPInterest);
                        //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                        Element forInstance =doc2.createElement("owlnl:forInstance");
                        forInstance.setAttribute("rdf:resource",uri+"#"+nextDomain);
                        IPInterest.appendChild(forInstance );
                        
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+nextuser);
                        IPInterest.appendChild(forUserType);
                        Element InterestValue= doc2.createElement("owlnl:InterestValue");
                        // Vector value=(Vector) values.nextElement();
                        if(usersValue.elementAt(1).toString().equalsIgnoreCase(""))
                            //InterestValue.appendChild(doc2.createTextNode("-1"));
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 1)));
                        else
                            InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(1).toString()));
                        IPInterest.appendChild(InterestValue);
                        Element Repetitions= doc2.createElement("owlnl:Repetitions");
                        // Vector value=(Vector) values.nextElement();
                        if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                            // Repetitions.appendChild(doc2.createTextNode("-1"));
                            Repetitions.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 2)));
                        else
                            Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                        IPInterest.appendChild(Repetitions);
                    }
                    }
            }
            
        }
        
        
//        String[] mpiroProps=new String[] {"title","name","name-nominative","name-genitive","name-accusative","gender-name","shortname","shortname-nominative","shortname-genitive","shortname-accusative","gender-shortname","gender","notes","number","images"};
//        for(int j=0;j<mpiroProps.length;j++){
//            String nextProp=mpiroProps[j];
//            Element Property  = doc2.createElement("owlnl:Property");
//            Property.setAttribute("rdf:about",uri+'#'+nextProp);
//            PropertiesInterests.appendChild(Property);
//            //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
//            Enumeration users=Mpiro.win.struc.getUserNames();
//           // Enumeration values=usersHash.elements();
//            while(users.hasMoreElements()){
//                Element DPInterest  = doc2.createElement("owlnl:DPInterestRepetitions");
//                DPInterest.setAttribute("rdf:parseType","Resource");
//                Property.appendChild(DPInterest);
//                Element forUserType = doc2.createElement("owlnl:forUserType");
//                forUserType.setAttribute("rdf:resource",base+"#"+users.nextElement().toString());
//                DPInterest.appendChild(forUserType);
//                Element InterestValue= doc2.createElement("owlnl:InterestValue");
//InterestValue.appendChild(doc2.createTextNode("0"));
//
//                DPInterest.appendChild(InterestValue);
//                Element InterestValue1= doc2.createElement("owlnl:Repetitions");
//                    InterestValue1.appendChild(doc2.createTextNode("1"));
//
//                DPInterest.appendChild(InterestValue1);
//        }}
        
        
        
     /*    Element PropertiesRepetitions  = doc2.createElement("owlnl:PropertiesRepetitions");
        PropertiesRepetitions.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(PropertiesRepetitions);
        properties=Mpiro.win.struc.getPropertyNames();
        propVectors1=Mpiro.win.struc.getProperties();
        while(properties.hasMoreElements()){
            //   Vector property=(Vector) properties.nextElement();
            String nextProp=properties.nextElement().toString();
            Vector nextPropVector=(Vector) propVectors1.nextElement();
            Element Property  = doc2.createElement("owlnl:Property");
            Property.setAttribute("rdf:about",uri+'#'+nextProp);
            PropertiesRepetitions.appendChild(Property);
            Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
            Enumeration users=usersHash.keys();
            Enumeration values=usersHash.elements();
            while(users.hasMoreElements()){
                Element DPRepetitions  = doc2.createElement("owlnl:DPRepetitions");
                DPRepetitions.setAttribute("rdf:parseType","Resource");
                Property.appendChild(DPRepetitions);
                Element forUserType = doc2.createElement("owlnl:forUserType");
                forUserType.setAttribute("rdf:resource","#"+users.nextElement().toString());
                DPRepetitions.appendChild(forUserType);
                Element Repetitions= doc2.createElement("owlnl:Repetitions");
                Vector value=(Vector) values.nextElement();
                Repetitions.appendChild(doc2.createTextNode(value.elementAt(2).toString()));
                DPRepetitions.appendChild(Repetitions);
            }
            Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(nextProp);
            Enumeration npkeys=np.keys();
            Enumeration npelems=np.elements();
            while(npkeys.hasMoreElements()){
                String nextDomain=npkeys.nextElement().toString();
                Hashtable nextElem=(Hashtable) npelems.nextElement();
                if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").containsKey(nextDomain)){
      
                    Enumeration users1=nextElem.keys();
                    Enumeration values1=nextElem.elements();
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element CDPRepetitions  = doc2.createElement("owlnl:CDPRepetitions");
                    CDPRepetitions.setAttribute("rdf:parseType","Resource");
                    Property.appendChild(CDPRepetitions);
                    //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                    Element forClass=doc2.createElement("owlnl:forOwlClass");
                    forClass.setAttribute("rdf:resource",uri+"#"+nextDomain);
                    CDPRepetitions.appendChild(forClass);
                        //String nextuser=users1.nextElement().toString();
                        //Vector usersValue=(Vector) values1.nextElement();
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource","#"+nextuser);
                        CDPRepetitions.appendChild(forUserType);
                        Element Repetitions= doc2.createElement("owlnl:Repetitions");
                        // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
      
                        Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                        CDPRepetitions.appendChild(Repetitions);
                    }
                }
                else{
      
                    Enumeration users1=nextElem.keys();
                    Enumeration values1=nextElem.elements();
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                      //  if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element IPRepetitions  = doc2.createElement("owlnl:IPRepetitions");
                    IPRepetitions.setAttribute("rdf:parseType","Resource");
                    Property.appendChild(IPRepetitions);
                    //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                    Element forInstance =doc2.createElement("owlnl:forInstance");
                    forInstance .setAttribute("rdf:resource",uri+"#"+nextDomain);
                    IPRepetitions.appendChild(forInstance );
      
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource","#"+nextuser);
                        IPRepetitions.appendChild(forUserType);
                        Element Repetitions= doc2.createElement("owlnl:Repetitions");
                        // Vector value=(Vector) values.nextElement();
                        if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                            Repetitions.appendChild(doc2.createTextNode("-1"));
                        else
                        Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                        IPRepetitions.appendChild(Repetitions);
                    }
                }
            }
      
        }*/
        
        
        //   Hashtable test=(Hashtable)((Vector)Mpiro.win.struc.getEntityTypeOrEntity("item")).elementAt(5);
        Element Appropriateness = doc2.createElement("owlnl:Appropriateness");
        Appropriateness.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(Appropriateness);
        
        
        //   Hashtable hash=entityTypesHashtables;
        entityTypesHashtables.remove("Data Base");
        entityTypesHashtables.remove("Basic-entity-types");
        
        properties=Mpiro.win.struc.getPropertyNames();
        while(properties.hasMoreElements()){
            //   Enumeration allEntityTypeParentNames = entityTypesHashtables.keys();
            
            String propName=properties.nextElement().toString();
            String domain=((Vector)((Vector)Mpiro.win.struc.getProperty(propName)).elementAt(0)).elementAt(0).toString();
            //   while(allEntityTypeParentNames.hasMoreElements()){
            
            //     String entityTypeName = allEntityTypeParentNames.nextElement().toString();
            // NodeVector nodeEntityType = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
            Vector vec= (Vector) Mpiro.win.struc.getEntityTypeOrEntity(domain);
            // TemplateVector templateVector = (TemplateVector) vec.elementAt(4);
            Hashtable microPlanningValues = (Hashtable) vec.get(5);
            
            
            for(int j=1;j<6;j++){
                Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                MicroplanApprop.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(j)+"-el");
                
                for(int i=0;i<usersVector.size();i++){
                    
                    Element Approp=doc2.createElement("owlnl:Approp");
                    Approp.setAttribute("rdf:parseType","Resource");
                    
                    Element forUserType = doc2.createElement("owlnl:forUserType");
                    forUserType.setAttribute("rdf:resource",base+"#"+usersVector.elementAt(i).toString());
                    Approp.appendChild(forUserType);
                    Element AppropValue= doc2.createElement("owlnl:AppropValue");
                    String value="";
                    try{
                        
                        value= microPlanningValues.get(String.valueOf(j)+":"+propName+":"+usersVector.elementAt(i).toString()+":"+"Greek").toString();
                    } catch(NullPointerException c){
                        value= "0";
                    }
                    MicroplanApprop.appendChild(Approp);
                    AppropValue.appendChild(doc2.createTextNode(value));
                    Approp.appendChild(AppropValue);
                    Appropriateness.appendChild(MicroplanApprop);
                    
                    
                }}
            
            for(int j=1;j<6;j++){
                Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                MicroplanApprop.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(j)+"-en");
                
                for(int i=0;i<usersVector.size();i++){
                    Element Approp=doc2.createElement("owlnl:Approp");
                    Approp.setAttribute("rdf:parseType","Resource");
                    
                    Element forUserType = doc2.createElement("owlnl:forUserType");
                    forUserType.setAttribute("rdf:resource",base+"#"+usersVector.elementAt(i).toString());
                    Approp.appendChild(forUserType);
                    Element AppropValue= doc2.createElement("owlnl:AppropValue");
                    String value="";
                    try{
                        
                        
                        value= microPlanningValues.get(String.valueOf(j)+":"+propName+":"+usersVector.elementAt(i).toString()+":"+"English").toString();
                    } catch(NullPointerException c){continue;}
                    MicroplanApprop.appendChild(Approp);
                    AppropValue.appendChild(doc2.createTextNode(value));
                    Approp.appendChild(AppropValue);
                    Appropriateness.appendChild(MicroplanApprop);
                    
                    
                }//}
                
            }}
        
        
        
        
        
        Element ClassInterests = doc2.createElement("owlnl:ClassInterestsRepetitions");
        ClassInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(ClassInterests);
        
        Hashtable alltypes=new Hashtable();
        Enumeration allTypes1=entityTypesHashtables.keys();
        while(allTypes1.hasMoreElements()){
            String next=convertToClassName(allTypes1.nextElement().toString());
            Element el=doc2.createElement("owlnl:owlClass");
            el.setAttribute("rdf:about",uri+'#'+next);
            //  Element Properties=doc2.createElement("owlnl:Properties");
            //  Properties.setAttribute("rdf:parseType","Collection");
            //  el.appendChild(Properties);
            //   ClassInterests.appendChild(el);
            alltypes.put(next,el);
            ClassInterests.appendChild(el);
        }
        
        
        
        
 /*       Element InstancesInterests = doc2.createElement("owlnl:InstancesInterests");
         InstancesInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(InstancesInterests);
  
Hashtable allElements=new Hashtable();
        Enumeration allEntities1=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity+generic").keys();
        while(allEntities1.hasMoreElements()){
            String next=allEntities1.nextElement().toString();
            Element el=doc2.createElement("owlnl:owlInstance");
            el.setAttribute("rdf:about",uri+'/'+next);
            Element Properties=doc2.createElement("owlnl:Properties");
            Properties.setAttribute("rdf:parseType","Collection");
            el.appendChild(Properties);
            Element Classes=doc2.createElement("owlnl:Classes");
            Classes.setAttribute("rdf:parseType","Collection");
            el.appendChild(Classes);
           // InstancesInterests.appendChild(el);
            allElements.put(next,el);
            InstancesInterests.appendChild(el);
        }*/
        
        
        Enumeration mainUserModelHashtableEnumKeys = Mpiro.win.struc.mainUserModelHashtableKeys();
        Enumeration mainUserModelHashtableEnumElements = Mpiro.win.struc.mainUserModelHashtableElements();
        while (mainUserModelHashtableEnumKeys.hasMoreElements()) {
            String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
            
            Hashtable fieldnameHashtable = (Hashtable) mainUserModelHashtableEnumElements.nextElement();
            if(!fieldname.equalsIgnoreCase("Subtype-of")&&!fieldname.equalsIgnoreCase("type")) continue;
            // Element fieldElement = doc2.createElement(fieldname);
            // userModelElement.appendChild(fieldElement);
            
            
            Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
            Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
            while (fieldnameHashtableEnumKeys.hasMoreElements()) {
                String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
                //  Hashtable entitynameHashtable = (Hashtable) fieldnameHashtableEnumElements.nextElement();
                //Hashtable temp=QueryHashtable.mainDBHashtable;
                if(!Mpiro.win.struc.mainDBcontainsEntityOrEntityType(entityname)) continue;
                NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entityname);
                //System.out.println(entityname);
                if(entityname.equalsIgnoreCase("Data Base")||entityname.equalsIgnoreCase("Basic-entity-types")) continue;
                if(nodeVector.size() != 6){
                    // entityname = convertToClassName(entityname);
                    
                    String ctcn=convertToClassName(Mpiro.win.struc.getParents(entityname).elementAt(0).toString());
                    Element propert =(Element) alltypes.get(ctcn);//other parents?
                    //       fieldElement.appendChild(entityElement);
                    //if(!fieldname.equalsIgnoreCase("type")){
                    //  Element propert=(Element) entityElement.getFirstChild();
                    //Element Property =doc2.createElement("owlnl:Property");
                    //propert.appendChild(Property);
                    //Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(fieldname, entityname);
                    Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                    Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                    //  Enumeration entityNameKeys = entitynameHashtable.keys();
                    //  Enumeration entityNameVectors = entitynameHashtable.elements();
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
                        
                        Element Interest = doc2.createElement("owlnl:IInterestRepetitions");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+key);
                        Element forInstance=doc2.createElement("owlnl:forInstance");
                        forInstance.setAttribute("rdf:resource",uri+'#'+entityname);
                        Element InterestValue=doc2.createElement("owlnl:InterestValue");
                        if(valueVector.get(0).toString().equalsIgnoreCase(""))
                            //InterestValue.appendChild(doc2.createTextNode("-1"));
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 0)));
                        else
                            InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                        Element InterestValue1=doc2.createElement("owlnl:Repetitions");
                        if(valueVector.get(2).toString().equalsIgnoreCase(""))
                            //InterestValue1.appendChild(doc2.createTextNode("-1"));
                            InterestValue1.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 2)));
                        else
                            InterestValue1.appendChild(doc2.createTextNode(valueVector.get(2).toString()));
                        
                        Interest.appendChild(forInstance);
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                        Interest.appendChild(InterestValue1);
                        
                        
                        //   for(int i = 0; i<valueVector.size();i++){
                        //     Element valueElement = doc.createElement("value");
                        //   valueElement.appendChild(doc.createTextNode((String)valueVector.get(i)));
                        // keyElement.appendChild(valueElement);
                        //}
                    }//}
        /*        else{
         
                    Element classes=(Element) entityElement.getLastChild();
                Element cl =doc2.createElement("owlnl:owlClass");
                classes.appendChild(cl);
                Vector data=nodeVector.independentFieldsVector;
                data=(Vector) data.elementAt(1);
                cl.setAttribute("rdf:about",data.elementAt(1).toString());//change fieldname!!!!!!!!
                Enumeration entityNameKeys = entitynameHashtable.keys();
                Enumeration entityNameVectors = entitynameHashtable.elements();
                while(entityNameKeys.hasMoreElements()){
                    String key = (String)entityNameKeys.nextElement();
                    Vector valueVector = (Vector)entityNameVectors.nextElement();
         
                    Element Interest = doc2.createElement("owlnl:Interest");
                    cl.appendChild(Interest);
                    Interest.setAttribute("rdf:parseType","Resource");
                    Element forUserType=doc2.createElement("owlnl:forUserType");
                    forUserType.setAttribute("rdf:resource",key);
                    Element InterestValue=doc2.createElement("owlnl:InterestValue");
                    InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                    Interest.appendChild(forUserType);
                    Interest.appendChild(InterestValue);
         
         
                }}*/
                } else{
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(fieldname, entityname);
                    String entitynameConverted = convertToClassName(entityname);
                    
                    Element propert =(Element) alltypes.get(entitynameConverted);
                    //    Element propert=(Element) entityElement.getFirstChild();
                    // Element Property =doc2.createElement("owlnl:Property");
                    // propert.appendChild(Property);
                    // Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    
                    Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                    Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
                        
                        Element Interest = doc2.createElement("owlnl:DInterestRepetitions");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+key);
                        Element InterestValue=doc2.createElement("owlnl:InterestValue");
                        if(valueVector.get(0).toString().equalsIgnoreCase(""))
                            //InterestValue.appendChild(doc2.createTextNode("-1"));
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 0)));
                        else
                            InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                        Element InterestValue1=doc2.createElement("owlnl:Repetitions");
                        if(valueVector.get(2).toString().equalsIgnoreCase(""))
                            //InterestValue1.appendChild(doc2.createTextNode("-1"));
                            InterestValue1.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 2)));
                        else
                            InterestValue1.appendChild(doc2.createTextNode(valueVector.get(2).toString()));
                        // Interest.appendChild(forUserType);
                        
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                        Interest.appendChild(InterestValue1);
                    }
                }
            }
            
        }
        
        
 /*       Element ClassRepetitions = doc2.createElement("owlnl:ClassRepetitions");
        ClassRepetitions.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(ClassRepetitions);
  
        alltypes=new Hashtable();
        allTypes1=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type").keys();
        while(allTypes1.hasMoreElements()){
            String next=convertToClassName(allTypes1.nextElement().toString());
            Element el=doc2.createElement("owlnl:owlClass");
            el.setAttribute("rdf:about",uri+'#'+next);
            //  Element Properties=doc2.createElement("owlnl:Properties");
            //  Properties.setAttribute("rdf:parseType","Collection");
            //  el.appendChild(Properties);
            //   ClassInterests.appendChild(el);
            alltypes.put(next,el);
            ClassRepetitions.appendChild(el);
        }
  
  
  
  
 /*       Element InstancesInterests = doc2.createElement("owlnl:InstancesInterests");
         InstancesInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(InstancesInterests);
  
Hashtable allElements=new Hashtable();
        Enumeration allEntities1=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity+generic").keys();
        while(allEntities1.hasMoreElements()){
            String next=allEntities1.nextElement().toString();
            Element el=doc2.createElement("owlnl:owlInstance");
            el.setAttribute("rdf:about",uri+'/'+next);
            Element Properties=doc2.createElement("owlnl:Properties");
            Properties.setAttribute("rdf:parseType","Collection");
            el.appendChild(Properties);
            Element Classes=doc2.createElement("owlnl:Classes");
            Classes.setAttribute("rdf:parseType","Collection");
            el.appendChild(Classes);
           // InstancesInterests.appendChild(el);
            allElements.put(next,el);
            InstancesInterests.appendChild(el);
        }*/
        
        
  /*      mainUserModelHashtableEnumKeys = Mpiro.win.struc.mainUserModelHashtableKeys();
        mainUserModelHashtableEnumElements = Mpiro.win.struc.mainUserModelHashtableElements();
        while (mainUserModelHashtableEnumKeys.hasMoreElements()) {
            String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
   
            Hashtable fieldnameHashtable = (Hashtable) mainUserModelHashtableEnumElements.nextElement();
            if(!fieldname.equalsIgnoreCase("Subtype-of")&&!fieldname.equalsIgnoreCase("type")) continue;
            // Element fieldElement = doc2.createElement(fieldname);
            // userModelElement.appendChild(fieldElement);
   
            Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
            Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
            while (fieldnameHashtableEnumKeys.hasMoreElements()) {
                String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
                Hashtable entitynameHashtable = (Hashtable) fieldnameHashtableEnumElements.nextElement();
   
   
                NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entityname);
                if(entityname.equalsIgnoreCase("Data Base")||entityname.equalsIgnoreCase("Basic-entity-types")) continue;
                if(nodeVector.size() != 6){
                    // entityname = convertToClassName(entityname);
   
                    String ctcn=convertToClassName(Mpiro.win.struc.getParents(entityname).elementAt(0).toString());
                    Element propert =(Element) alltypes.get(ctcn);//other parents?
                    //       fieldElement.appendChild(entityElement);
                    //if(!fieldname.equalsIgnoreCase("type")){
                    //  Element propert=(Element) entityElement.getFirstChild();
                    //Element Property =doc2.createElement("owlnl:Property");
                    //propert.appendChild(Property);
                    //Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    Enumeration entityNameKeys = entitynameHashtable.keys();
                    Enumeration entityNameVectors = entitynameHashtable.elements();
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
   
                        Element Interest = doc2.createElement("owlnl:IRepetitions");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource","#"+key);
                        Element forInstance=doc2.createElement("owlnl:forInstance");
                        forInstance.setAttribute("rdf:resource",uri+'#'+entityname);
                        Element InterestValue=doc2.createElement("owlnl:Repetitions");
                        InterestValue.appendChild(doc2.createTextNode(valueVector.get(2).toString()));
                        Interest.appendChild(forInstance);
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                        //   for(int i = 0; i<valueVector.size();i++){
                        //     Element valueElement = doc.createElement("value");
                        //   valueElement.appendChild(doc.createTextNode((String)valueVector.get(i)));
                        // keyElement.appendChild(valueElement);
                        //}
                    }//}
        /*        else{
   
                    Element classes=(Element) entityElement.getLastChild();
                Element cl =doc2.createElement("owlnl:owlClass");
                classes.appendChild(cl);
                Vector data=nodeVector.independentFieldsVector;
                data=(Vector) data.elementAt(1);
                cl.setAttribute("rdf:about",data.elementAt(1).toString());//change fieldname!!!!!!!!
                Enumeration entityNameKeys = entitynameHashtable.keys();
                Enumeration entityNameVectors = entitynameHashtable.elements();
                while(entityNameKeys.hasMoreElements()){
                    String key = (String)entityNameKeys.nextElement();
                    Vector valueVector = (Vector)entityNameVectors.nextElement();
   
                    Element Interest = doc2.createElement("owlnl:Interest");
                    cl.appendChild(Interest);
                    Interest.setAttribute("rdf:parseType","Resource");
                    Element forUserType=doc2.createElement("owlnl:forUserType");
                    forUserType.setAttribute("rdf:resource",key);
                    Element InterestValue=doc2.createElement("owlnl:InterestValue");
                    InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                    Interest.appendChild(forUserType);
                    Interest.appendChild(InterestValue);
   
   
                }}*/
       /*         } else{
                    entityname = convertToClassName(entityname);
                    Element propert =(Element) alltypes.get(entityname);
                    //    Element propert=(Element) entityElement.getFirstChild();
                    // Element Property =doc2.createElement("owlnl:Property");
                    // propert.appendChild(Property);
                    // Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    Enumeration entityNameKeys = entitynameHashtable.keys();
                    Enumeration entityNameVectors = entitynameHashtable.elements();
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
        
                        Element Interest = doc2.createElement("owlnl:DRepetitions");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource","#"+key);
                        Element InterestValue=doc2.createElement("owlnl:Repetitions");
                        InterestValue.appendChild(doc2.createTextNode(valueVector.get(2).toString()));
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                    }
                }
            }
        
        }
        
        */
        
        Writer writer=new Writer();
        writer.setOutput(output2,null);
        writer.write(doc2);
    }
    
    
    
    
    
    public static void exportRobotModelling(String fileName,String uri) throws Exception {
        Hashtable characteristics=exportUserCharacteristics(uri);
        Vector charVector=Mpiro.win.struc.getCharacteristics();
        File xmlFile2 = new File(fileName+"RobotModelling.rdf");
        String base= "http://www.aueb.gr/users/ion/owlnl/UserModelling";
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc2 = docBuilder.newDocument();
        FileOutputStream output2 = new FileOutputStream(xmlFile2);
        Element RDF2=doc2.createElement("rdf:RDF");
        doc2.appendChild(RDF2);
        RDF2.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF2.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF2.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/UserModelling#");
        RDF2.setAttribute("xml:base",base);
        RDF2.setAttribute("xmlns:eleon", Mpiro.win.struc.getBaseURI());
        //RDF2.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        
        
        Hashtable entityTypesHashtables=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        
        Element UserModelling=doc2.createElement("owlnl:RobotModelling");
        RDF2.appendChild(UserModelling);
        Element usersElement = doc2.createElement("owlnl:RobotTypes");
        usersElement.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(usersElement);
        
        Enumeration userNamesEnu = Mpiro.win.struc.getRobotNames();
        Enumeration userValuesEnu = Mpiro.win.struc.getRobotElements();
        Vector usersVector = new Vector();
        while (userNamesEnu.hasMoreElements()) {
            String userName = (String) userNamesEnu.nextElement();
            Robot userVector = (Robot) userValuesEnu.nextElement();
            usersVector.add(userName);
            Element currentUserElement = doc2.createElement("owlnl:RobotType");
            currentUserElement.setAttribute("rdf:ID",userName);
            usersElement.appendChild(currentUserElement);
            
            Element sentenceElement = doc2.createElement("owlnl:Openness");
            sentenceElement.appendChild(doc2.createTextNode(userVector.getO()));
            Element paragraphElement = doc2.createElement("owlnl:Conscientiousness");
            paragraphElement.appendChild(doc2.createTextNode(userVector.getC()));
            //   Element conjunctionElement = doc2.createElement("Link-Number");
            // conjunctionElement.appendChild(doc2.createTextNode(userVector.get(2).toString()));
            Element voiceElement = doc2.createElement("owlnl:Extraversion");
            voiceElement.appendChild(doc2.createTextNode(userVector.getE()));
            Element agreeableness = doc2.createElement("owlnl:Agreeableness");
            agreeableness.appendChild(doc2.createTextNode(userVector.getA()));
            Element naturalReactions = doc2.createElement("owlnl:NaturalReactions");
            naturalReactions.appendChild(doc2.createTextNode(userVector.getN()));
            
            currentUserElement.appendChild(sentenceElement);
            currentUserElement.appendChild(paragraphElement);
            // currentUserElement.appendChild(conjunctionElement);
            currentUserElement.appendChild(voiceElement);
            currentUserElement.appendChild(agreeableness);
            currentUserElement.appendChild(naturalReactions);
            
            
        }
        
        
        Element PropertiesInterests  = doc2.createElement("owlnl:RobotsPreference");
        PropertiesInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(PropertiesInterests);
        Enumeration properties=Mpiro.win.struc.getPropertyNames();
        Enumeration propVectors1=Mpiro.win.struc.getProperties();
        
        while(properties.hasMoreElements()){
            //   Vector property=(Vector) properties.nextElement();
            String nextProp=properties.nextElement().toString();
            Vector nextPropVector=(Vector) propVectors1.nextElement();
            Element Property  = doc2.createElement("owlnl:Property");
            Property.setAttribute("rdf:about",uri+'#'+nextProp);
            PropertiesInterests.appendChild(Property);
            Hashtable usersHash=(Hashtable) nextPropVector.elementAt(15);
            Enumeration users=usersHash.keys();
            Enumeration values=usersHash.elements();
            while(users.hasMoreElements()){
                Element DPInterest  = doc2.createElement("owlnl:DPPreference");
                DPInterest.setAttribute("rdf:parseType","Resource");
                Property.appendChild(DPInterest);
                Element forUserType = doc2.createElement("owlnl:forUserType");
                forUserType.setAttribute("rdf:resource",base+"#"+users.nextElement().toString());
                DPInterest.appendChild(forUserType);
                Element InterestValue= doc2.createElement("owlnl:PreferenceValue");
                Vector value=(Vector) values.nextElement();
                if(value.elementAt(0).toString().equalsIgnoreCase(""))
                    InterestValue.appendChild(doc2.createTextNode("-1"));
                else
                    InterestValue.appendChild(doc2.createTextNode(value.elementAt(0).toString()));
                DPInterest.appendChild(InterestValue);
                // Element InterestValue1= doc2.createElement("owlnl:Repetitions");
                // Vector value=(Vector) values.nextElement();
                //  if(value.elementAt(2).toString().equalsIgnoreCase(""))
                //      InterestValue1.appendChild(doc2.createTextNode("-1"));
                //   else
                //  InterestValue1.appendChild(doc2.createTextNode(value.elementAt(2).toString()));
                //     DPInterest.appendChild(InterestValue1);
            }
            if(!Mpiro.win.struc.mainUserModelHashtableContainsProperty(nextProp))
                Mpiro.win.struc.putPropertyInMainUserModelHashtable(nextProp, new Hashtable());
            Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(nextProp);
            Enumeration npkeys=np.keys();
            Enumeration npelems=np.elements();
            while(npkeys.hasMoreElements()){
                String nextDomain=npkeys.nextElement().toString();
                // Hashtable nextElem=(Hashtable) npelems.nextElement();
                if(entityTypesHashtables.containsKey(nextDomain)){
                    Vector modelling_values=Mpiro.win.struc.getRobotModelling(nextProp, nextDomain);
                    Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                    Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                    
                    // Enumeration users1=nextElem.keys();
                    // Enumeration values1=nextElem.elements();
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        // if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element CDPInterest  = doc2.createElement("owlnl:CDPPreference");
                        CDPInterest.setAttribute("rdf:parseType","Resource");
                        Property.appendChild(CDPInterest);
                        //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                        Element forClass=doc2.createElement("owlnl:forOwlClass");
                        forClass.setAttribute("rdf:resource",uri+"#"+nextDomain);
                        CDPInterest.appendChild(forClass);
                        //String nextuser=users1.nextElement().toString();
                        //Vector usersValue=(Vector) values1.nextElement();
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+nextuser);
                        CDPInterest.appendChild(forUserType);
                        for(int g=0;g<charVector.size();g++){
                            String value="";
                            if(characteristics.containsKey("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":"+nextuser+":Class:"+uri+"#"+convertToClassName(nextDomain)))
                                value=characteristics.get("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":"+nextuser+":Class:"+uri+"#"+convertToClassName(nextDomain)).toString();
                            if(characteristics.containsKey("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":universal:Class:"+uri+"#"+convertToClassName(nextDomain)))
                                value=characteristics.get("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":universal:Class:"+uri+"#"+convertToClassName(nextDomain)).toString();
                            if(!value.equals("")){
                                Element CharValue= doc2.createElement("eleon:"+charVector.elementAt(g).toString());
                                CharValue.appendChild(doc2.createTextNode(value));
                                CDPInterest.appendChild(CharValue);
                            }
                        }
                        
                        Element InterestValue= doc2.createElement("owlnl:PreferenceValue");
                        // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                        if(usersValue.elementAt(0).toString().equalsIgnoreCase(""))
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getRobotsParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 0)));
                        //InterestValue.appendChild(doc2.createTextNode("-1"));
                        else
                            InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(0).toString()));
                        CDPInterest.appendChild(InterestValue);
                        
                        //  Element Repetitions= doc2.createElement("owlnl:Repetitions");
                        // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                        //   if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                        // Repetitions.appendChild(doc2.createTextNode("-1"));
                        //        Repetitions.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 2)));
                        //  else
                        //  Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                        // CDPInterest.appendChild(Repetitions);
                    }
                } else
                    if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").containsKey(nextDomain)){
                    
                    Vector modelling_values=Mpiro.win.struc.getRobotModelling(nextProp, nextDomain);
                    Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                    Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                    // Enumeration users1=nextElem.keys();
                    // Enumeration values1=nextElem.elements();
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        //  if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element IPInterest  = doc2.createElement("owlnl:IPPreference");
                        IPInterest.setAttribute("rdf:parseType","Resource");
                        Property.appendChild(IPInterest);
                        //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                        Element forInstance =doc2.createElement("owlnl:forInstance");
                        forInstance .setAttribute("rdf:resource",uri+"#"+nextDomain);
                        IPInterest.appendChild(forInstance );
                        
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+nextuser);
                        IPInterest.appendChild(forUserType);
                        
                        for(int g=0;g<charVector.size();g++){
                            String value="";
                            if(characteristics.containsKey("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":"+nextuser+":Individual:"+uri+"#"+nextDomain))
                                value=characteristics.get("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":"+nextuser+":Individual:"+uri+"#"+nextDomain).toString();
                            if(characteristics.containsKey("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":universal:Individual:"+uri+"#"+nextDomain))
                                value=characteristics.get("Property:"+uri+'#'+nextProp+":"+charVector.elementAt(g).toString()+":universal:Individual:"+uri+"#"+nextDomain).toString();
                            if(!value.equals("")){
                                Element CharValue= doc2.createElement("eleon:"+charVector.elementAt(g).toString());
                                CharValue.appendChild(doc2.createTextNode(value));
                                IPInterest.appendChild(CharValue);
                            }
                        }
                        
                        Element InterestValue= doc2.createElement("owlnl:PreferenceValue");
                        // Vector value=(Vector) values.nextElement();
                        if(usersValue.elementAt(0).toString().equalsIgnoreCase(""))
                            //InterestValue.appendChild(doc2.createTextNode("-1"));
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getRobotsParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 0)));
                        else
                            InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(0).toString()));
                        IPInterest.appendChild(InterestValue);
                        //  Element Repetitions= doc2.createElement("owlnl:Repetitions");
                        // Vector value=(Vector) values.nextElement();
                        // if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                        // Repetitions.appendChild(doc2.createTextNode("-1"));
                        //        Repetitions.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 2)));
                        //    else
                        //    Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                        //  IPInterest.appendChild(Repetitions);
                    }
                    }
            }
            
        }
        
        
        
        
        
        String nextProp="ClassOrInd";
        FileWriter fstream = new FileWriter(fileName+"robots.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        // out.write("test");
        // out.newLine();
        
        //Close the output stream
        
        //  Element Property  = doc2.createElement("owlnl:Property");
        //    Property.setAttribute("rdf:about",uri+'#'+nextProp);
        ///  PropertiesInterests.appendChild(Property);
        if(!Mpiro.win.struc.mainUserModelHashtableContainsProperty(nextProp))
            Mpiro.win.struc.putPropertyInMainUserModelHashtable(nextProp, new Hashtable());
        Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(nextProp);
        Enumeration npkeys=np.keys();
        Enumeration npelems=np.elements();
        while(npkeys.hasMoreElements()){
            String nextDomain=npkeys.nextElement().toString();
            // Hashtable nextElem=(Hashtable) npelems.nextElement();
            if(entityTypesHashtables.containsKey(nextDomain)){
                
                Vector modelling_values=Mpiro.win.struc.getRobotModelling(nextProp, nextDomain);
                Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                // Enumeration users1=nextElem.keys();
                // Enumeration values1=nextElem.elements();
                while(users1.hasMoreElements()){
                    String nextuser=users1.nextElement().toString();
                    Vector usersValue=(Vector) values1.nextElement();
                    // if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                    //  Element CDPInterest  = doc2.createElement("owlnl:CDPPreference");
                    // CDPInterest.setAttribute("rdf:parseType","Resource");
                    //  Property.appendChild(CDPInterest);
                    
                    //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                    //  Element forClass=doc2.createElement("owlnl:forOwlClass");
                    //   forClass.setAttribute("rdf:resource",uri+"#"+nextDomain);
                    //   CDPInterest.appendChild(forClass);
                    //String nextuser=users1.nextElement().toString();
                    //Vector usersValue=(Vector) values1.nextElement();
                    //    Element forUserType = doc2.createElement("owlnl:forUserType");
                    //    forUserType.setAttribute("rdf:resource","#"+nextuser);
                    //    CDPInterest.appendChild(forUserType);
                    //   Element InterestValue= doc2.createElement("owlnl:PreferenceValue");
                    // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                    if(usersValue.elementAt(0).toString().equalsIgnoreCase("")){
                        out.write("Class:"+uri+"#"+nextDomain+":"+nextuser+":"+Mpiro.win.struc.getRobotsParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 0));
                        out.newLine();}
//   InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getRobotsParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 0)));
                    //InterestValue.appendChild(doc2.createTextNode("-1"));
                    else{
                        out.write("Class:"+uri+"#"+nextDomain+":"+nextuser+":"+usersValue.elementAt(0).toString());
                        out.newLine();}
//// InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(0).toString()));
                    //  CDPInterest.appendChild(InterestValue);
                    
                    //  Element Repetitions= doc2.createElement("owlnl:Repetitions");
                    // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                    //   if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                    // Repetitions.appendChild(doc2.createTextNode("-1"));
                    //        Repetitions.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 2)));
                    //  else
                    //  Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                    // CDPInterest.appendChild(Repetitions);
                }
            } else
                if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").containsKey(nextDomain)){
                
                Vector modelling_values=Mpiro.win.struc.getRobotModelling(nextProp, nextDomain);
                Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                // Enumeration users1=nextElem.keys();
                // Enumeration values1=nextElem.elements();
                while(users1.hasMoreElements()){
                    String nextuser=users1.nextElement().toString();
                    Vector usersValue=(Vector) values1.nextElement();
                    //  if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                    //  Element IPInterest  = doc2.createElement("owlnl:IPPreference");
                    //   IPInterest.setAttribute("rdf:parseType","Resource");
                    //  Property.appendChild(IPInterest);
                    //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                    //   Element forInstance =doc2.createElement("owlnl:forInstance");
                    //  forInstance .setAttribute("rdf:resource",uri+"#"+nextDomain);
                    //  IPInterest.appendChild(forInstance );
                    
                    //      Element forUserType = doc2.createElement("owlnl:forUserType");
                    //      forUserType.setAttribute("rdf:resource","#"+nextuser);
                    //     IPInterest.appendChild(forUserType);
                    //    Element InterestValue= doc2.createElement("owlnl:PreferenceValue");
                    // Vector value=(Vector) values.nextElement();
                    if(usersValue.elementAt(0).toString().equalsIgnoreCase("")){
                        out.write("Individual:"+uri+"#"+nextDomain+":"+nextuser+":"+Mpiro.win.struc.getRobotsParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 0));
                        out.newLine();}
                    //InterestValue.appendChild(doc2.createTextNode("-1"));
                    // InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getRobotsParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 0)));
                    else {
                        out.write("Class:"+uri+"#"+nextDomain+":"+nextuser+":"+usersValue.elementAt(0).toString());
                        out.newLine();}
                    // InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(0).toString()));
                    // IPInterest.appendChild(InterestValue);
                    //  Element Repetitions= doc2.createElement("owlnl:Repetitions");
                    // Vector value=(Vector) values.nextElement();
                    // if(usersValue.elementAt(2).toString().equalsIgnoreCase(""))
                    // Repetitions.appendChild(doc2.createTextNode("-1"));
                    //        Repetitions.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentValuesVectorForOWLExport(nextProp, nextuser, nextDomain, 2)));
                    //    else
                    //    Repetitions.appendChild(doc2.createTextNode(usersValue.elementAt(2).toString()));
                    //  IPInterest.appendChild(Repetitions);
                }
                }
        }
        out.close();
        
        
        
//        String[] mpiroProps=new String[] {"title","name","name-nominative","name-genitive","name-accusative","gender-name","shortname","shortname-nominative","shortname-genitive","shortname-accusative","gender-shortname","gender","notes","number","images"};
//        for(int j=0;j<mpiroProps.length;j++){
//            nextProp=mpiroProps[j];
//            Element Property1  = doc2.createElement("owlnl:Property");
//            Property1.setAttribute("rdf:about",uri+'#'+nextProp);
//            PropertiesInterests.appendChild(Property1);
//            //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
//            Enumeration users=QueryProfileHashtable.getRobots();
//           // Enumeration values=usersHash.elements();
//            while(users.hasMoreElements()){
//                Element DPInterest  = doc2.createElement("owlnl:DPPreference");
//                DPInterest.setAttribute("rdf:parseType","Resource");
//                Property1.appendChild(DPInterest);
//                Element forUserType = doc2.createElement("owlnl:forUserType");
//                forUserType.setAttribute("rdf:resource",base+"#"+users.nextElement().toString());
//                DPInterest.appendChild(forUserType);
//                Element InterestValue= doc2.createElement("owlnl:Preference");
//InterestValue.appendChild(doc2.createTextNode("0"));
//
//                DPInterest.appendChild(InterestValue);
//               // Element InterestValue1= doc2.createElement("owlnl:Repetitions");
//                //    InterestValue1.appendChild(doc2.createTextNode("1"));
//
//              //  DPInterest.appendChild(InterestValue1);
//        }}
        
        
        
        
        
        
        //   Hashtable test=(Hashtable)((Vector)Mpiro.win.struc.getEntityTypeOrEntity("item")).elementAt(5);
        //  Element Appropriateness = doc2.createElement("owlnl:Appropriateness");
        //  Appropriateness.setAttribute("rdf:parseType","Collection");
        //   UserModelling.appendChild(Appropriateness);
        
        
        //   Hashtable hash=entityTypesHashtables;
        
        
        //   properties=Mpiro.win.struc.getPropertyNames();
        //   while(properties.hasMoreElements()){
        //       Enumeration allEntityTypeParentNames = entityTypesHashtables.keys();
        
        //      String propName=properties.nextElement().toString();
        //      while(allEntityTypeParentNames.hasMoreElements()){
        
        //    String entityTypeName = allEntityTypeParentNames.nextElement().toString();
        // NodeVector nodeEntityType = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
        //      Vector vec= (Vector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
        // TemplateVector templateVector = (TemplateVector) vec.elementAt(4);
        //       Hashtable microPlanningValues = (Hashtable) vec.get(5);
        
        
        /*        for(int j=1;j<6;j++){
                    Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                    MicroplanApprop.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(j)+"-el");
         
                    for(int i=0;i<usersVector.size();i++){
         
                        Element Approp=doc2.createElement("owlnl:Approp");
                        Approp.setAttribute("rdf:parseType","Resource");
         
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource","#"+usersVector.elementAt(i).toString());
                        Approp.appendChild(forUserType);
                        Element AppropValue= doc2.createElement("owlnl:AppropValue");
                        String value="";
                            try{
         
                            value= microPlanningValues.get(String.valueOf(j)+":"+propName+":"+usersVector.elementAt(i).toString()+":"+"Greek").toString();
                            } catch(NullPointerException c){
                        continue;
                        }
                            MicroplanApprop.appendChild(Approp);
                            AppropValue.appendChild(doc2.createTextNode(value));
                            Approp.appendChild(AppropValue);
                            Appropriateness.appendChild(MicroplanApprop);
         
         
                    }}
         
                for(int j=1;j<6;j++){
                    Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                    MicroplanApprop.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(j)+"-en");
         
                    for(int i=0;i<usersVector.size();i++){
                        Element Approp=doc2.createElement("owlnl:Approp");
                        Approp.setAttribute("rdf:parseType","Resource");
         
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource","#"+usersVector.elementAt(i).toString());
                        Approp.appendChild(forUserType);
                        Element AppropValue= doc2.createElement("owlnl:AppropValue");
                        String value="";
                        try{
         
         
                            value= microPlanningValues.get(String.valueOf(j)+":"+propName+":"+usersVector.elementAt(i).toString()+":"+"English").toString();
                            } catch(NullPointerException c){continue;}
                        MicroplanApprop.appendChild(Approp);
                            AppropValue.appendChild(doc2.createTextNode(value));
                            Approp.appendChild(AppropValue);
                            Appropriateness.appendChild(MicroplanApprop);
         
         
                    }}*/
        
        //  }}
        
        
        
        entityTypesHashtables.remove("Data Base");
        entityTypesHashtables.remove("Basic-entity-types");
        
        Element ClassInterests = doc2.createElement("owlnl:ClassPreference");
        ClassInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(ClassInterests);
        
        Hashtable alltypes=new Hashtable();
        Enumeration allTypes1=entityTypesHashtables.keys();
        while(allTypes1.hasMoreElements()){
            String next=convertToClassName(allTypes1.nextElement().toString());
            Element el=doc2.createElement("owlnl:owlClass");
            el.setAttribute("rdf:about",uri+'#'+next);
            //  Element Properties=doc2.createElement("owlnl:Properties");
            //  Properties.setAttribute("rdf:parseType","Collection");
            //  el.appendChild(Properties);
            //   ClassInterests.appendChild(el);
            alltypes.put(next,el);
            ClassInterests.appendChild(el);
        }
        
        
        
        
        
        
        
        Enumeration mainUserModelHashtableEnumKeys = Mpiro.win.struc.mainUserModelHashtableKeys();
        Enumeration mainUserModelHashtableEnumElements = Mpiro.win.struc.mainUserModelHashtableElements();
        while (mainUserModelHashtableEnumKeys.hasMoreElements()) {
            String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
            
            Hashtable fieldnameHashtable = (Hashtable) mainUserModelHashtableEnumElements.nextElement();
            if(!fieldname.equalsIgnoreCase("Subtype-of")&&!fieldname.equalsIgnoreCase("type")) continue;
            // Element fieldElement = doc2.createElement(fieldname);
            // userModelElement.appendChild(fieldElement);
            
            Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
            Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
            while (fieldnameHashtableEnumKeys.hasMoreElements()) {
                String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
                //  Hashtable entitynameHashtable = (Hashtable) fieldnameHashtableEnumElements.nextElement();
                if(!Mpiro.win.struc.mainDBcontainsEntityOrEntityType(entityname)) continue;
                NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entityname);
                if(entityname.equalsIgnoreCase("Data Base")||entityname.equalsIgnoreCase("Basic-entity-types")) continue;
                // System.out.println(entityname);
                if(nodeVector.size() != 6){
                    // entityname = convertToClassName(entityname);
                    
                    String ctcn=convertToClassName(Mpiro.win.struc.getParents(entityname).elementAt(0).toString());
                    Element propert =(Element) alltypes.get(ctcn);//other parents?
                    //       fieldElement.appendChild(entityElement);
                    //if(!fieldname.equalsIgnoreCase("type")){
                    //  Element propert=(Element) entityElement.getFirstChild();
                    //Element Property =doc2.createElement("owlnl:Property");
                    //propert.appendChild(Property);
                    //Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    Vector modelling_values=Mpiro.win.struc.getRobotModelling(fieldname, entityname);
                    Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                    Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                    // Enumeration entityNameKeys = entitynameHashtable.keys();
                    // Enumeration entityNameVectors = entitynameHashtable.elements();
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
                        
                        Element Interest = doc2.createElement("owlnl:IPreference");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+key);
                        Element forInstance=doc2.createElement("owlnl:forInstance");
                        forInstance.setAttribute("rdf:resource",uri+'#'+entityname);
                        Element InterestValue=doc2.createElement("owlnl:PreferenceValue");
                        if(valueVector.get(0).toString().equalsIgnoreCase(""))
                            //InterestValue.appendChild(doc2.createTextNode("-1"));
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 0)));
                        else
                            InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                        Element InterestValue1=doc2.createElement("owlnl:Repetitions");
                        //    if(valueVector.get(2).toString().equalsIgnoreCase(""))
                        //InterestValue1.appendChild(doc2.createTextNode("-1"));
                        //     InterestValue1.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 2)));
                        //    else
                        //    InterestValue1.appendChild(doc2.createTextNode(valueVector.get(2).toString()));
                        
                        
                        for(int g=0;g<charVector.size();g++){
                            String value="";
                            if(characteristics.containsKey("Individual:"+uri+'#'+entityname+":"+charVector.elementAt(g).toString()+":"+key))
                                value=characteristics.get("Individual:"+uri+'#'+entityname+":"+charVector.elementAt(g).toString()+":"+key).toString();
                            if(characteristics.containsKey("Individual:"+uri+'#'+entityname+":universal:"+key))
                                value=characteristics.get("Individual:"+uri+'#'+entityname+":universal:"+key).toString();
                            if(!value.equals("")){
                                Element CharValue= doc2.createElement("eleon:"+charVector.elementAt(g).toString());
                                CharValue.appendChild(doc2.createTextNode(value));
                                Interest.appendChild(CharValue);
                            }
                        }
                        
                        Interest.appendChild(forInstance);
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                        //   Interest.appendChild(InterestValue1);
                    }
                    
                } else{
                    Vector modelling_values=Mpiro.win.struc.getRobotModelling(fieldname, entityname);
                    String entitynameConverted = convertToClassName(entityname);
                    Element propert =(Element) alltypes.get(entitynameConverted);
                    //    Element propert=(Element) entityElement.getFirstChild();
                    // Element Property =doc2.createElement("owlnl:Property");
                    // propert.appendChild(Property);
                    // Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    
                    Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                    Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                    // Enumeration entityNameKeys = entitynameHashtable.keys();
                    // Enumeration entityNameVectors = entitynameHashtable.elements();
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
                        
                        Element Interest = doc2.createElement("owlnl:DPreference");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+key);
                        Element InterestValue=doc2.createElement("owlnl:PreferenceValue");
                        if(valueVector.get(0).toString().equalsIgnoreCase(""))
                            //InterestValue.appendChild(doc2.createTextNode("-1"));
                            InterestValue.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 0)));
                        else
                            InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                        //   Element InterestValue1=doc2.createElement("owlnl:Repetitions");
                        ///  if(valueVector.get(2).toString().equalsIgnoreCase(""))
                        //InterestValue1.appendChild(doc2.createTextNode("-1"));
                        //     InterestValue1.appendChild(doc2.createTextNode(Mpiro.win.struc.getParentTypeOfVectorForOWLExport(key, entityname, 2)));
                        //     else
                        //   InterestValue1.appendChild(doc2.createTextNode(valueVector.get(2).toString()));
                        // Interest.appendChild(forUserType);
                        
                        for(int g=0;g<charVector.size();g++){
                            String value="";
                            if(characteristics.containsKey("Class:"+uri+'#'+entitynameConverted+":"+charVector.elementAt(g).toString()+":"+key))
                                value=characteristics.get("Class:"+uri+'#'+entitynameConverted+":"+charVector.elementAt(g).toString()+":"+key).toString();
                            if(characteristics.containsKey("Class:"+uri+'#'+entitynameConverted+":universal:"+key))
                                value=characteristics.get("Class:"+uri+'#'+entitynameConverted+":universal:"+key).toString();
                            if(!value.equals("")){
                                Element CharValue= doc2.createElement("eleon:"+charVector.elementAt(g).toString());
                                CharValue.appendChild(doc2.createTextNode(value));
                                Interest.appendChild(CharValue);
                            }
                        }
                        
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                        //   Interest.appendChild(InterestValue1);
                    }
                }
            }
            
        }
        
        
        
        
        Writer writer=new Writer();
        writer.setOutput(output2,null);
        writer.write(doc2);
    }
    
    
    
    
    
    
    public static void exportMicroplans(String fileName,String uri) throws Exception {
        
        Hashtable hash=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type");
        hash.remove("Data Base");
        hash.remove("Basic-entity-types");
        
        //  Enumeration allEntityTypeNames;
        //     Enumeration allEntityTypeParentNames;
        
        
        //     allEntityTypeParentNames = hash.keys();
        File xmlFile = new File(fileName+"Microplans.rdf");
        
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc1 = docBuilder.newDocument();
        FileOutputStream output = new FileOutputStream(xmlFile);
        Element RDF1=doc1.createElement("rdf:RDF");
        doc1.appendChild(RDF1);
        RDF1.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF1.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF1.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/Microplans#");
        // RDF1.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        RDF1.setAttribute("xml:base","http://www.aueb.gr/users/ion/owlnl/Microplans");
        
        
        Element MicroplansAndOrdering= doc1.createElement("owlnl:MicroplansAndOrdering");
        RDF1.appendChild(MicroplansAndOrdering);
        //    Element ontology1=doc1.createElement("owlnl:Ontology");
        //    ontology1.setAttribute("rdf:resource",uri);
        //    MicroplansAndOrdering.appendChild(ontology1);
        Element props=doc1.createElement("owlnl:Properties");
        props.setAttribute("rdf:parseType","Collection");
        MicroplansAndOrdering.appendChild(props);
        //    Enumeration propKeys=Mpiro.win.struc.getPropertyNames();
        Enumeration propElements=Mpiro.win.struc.getProperties();
        Hashtable micros=new Hashtable();
        while (propElements.hasMoreElements()) {
            //    String entityTypeName = allEntityTypeParentNames.nextElement().toString();
            // NodeVector nodeEntityType = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
            Vector vec= (Vector) propElements.nextElement();
            TemplateVector templateVector = (TemplateVector) vec.elementAt(11);
            Hashtable microplanning=(Hashtable) vec.elementAt(10);
            for (int o=0;o<3;o=o+2){
                String lang="en";
                if (o==2) lang="el";
                Hashtable langValues = (Hashtable) templateVector.get(o);
                
                //     Hashtable italianValues = (Hashtable) templateVector.get(1);
                //   Hashtable greekValues = (Hashtable) templateVector.get(2);
                
                
                Enumeration langKeysEnu = langValues.keys();
                Enumeration langElementsEnu = langValues.elements();
                while (langKeysEnu.hasMoreElements()){
                    Object ob=langElementsEnu.nextElement();
                    String[] templ=langKeysEnu.nextElement().toString().split(":");
                    if (templ.length==2){
                        
                        //    Element prop=doc1.createElement("owlnl:Property");
                        //    prop.setAttribute("rdf:about",templ[1]);
                        //    props.appendChild(prop);
                        
                        //     Element order=doc1.createElement("owlnl:Order");
                        //    order.appendChild(doc1.createTextNode("1"));
                        //     prop.appendChild(order);
                        
                        //     Element root = doc1.createElement("xsl:template");
                        //     root.setAttribute("match",templ[1]+"[@Templ='"+templ[0]+"']");
                        //     Element style=doc1.createElement("xsl:stylesheet");
                        //  style.setAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
                        //style.appendChild(root);
                        //  doc1.appendChild(style);
                        //     Element msg = doc1.createElement("Msg");
                        //  msg.setAttribute("Order","{@Order}");
                        //  msg.setAttribute("RestrType","{name()}");
                        //         msg.setAttribute("property",templ[1]);
                        Element microplan=doc1.createElement("owlnl:Microplan");
                        microplan.setAttribute("rdf:about",uri+'#'+templ[1]+"-templ"+templ[0]+"-"+lang);
                        Element MicroplanName=doc1.createElement("owlnl:MicroplanName");
                        MicroplanName.appendChild(doc1.createTextNode("templ"+templ[0]));
                        microplan.appendChild(MicroplanName);
                        Element used=doc1.createElement("owlnl:Used");
                        if(o==2){
                            if(microplanning.get(templ[0]+":"+templ[1]+":SELECTION:Greek").toString().equalsIgnoreCase("NoMicroplanning"))
                                used.appendChild(doc1.createTextNode("false"));
                            else
                                used.appendChild(doc1.createTextNode("true"));
                        } else{
                            if(microplanning.get(templ[0]+":"+templ[1]+":SELECTION:English").toString().equalsIgnoreCase("NoMicroplanning"))
                                used.appendChild(doc1.createTextNode("false"));
                            else
                                used.appendChild(doc1.createTextNode("true"));
                        }
                        Element agr=doc1.createElement("owlnl:AggrAllowed");
                        agr.appendChild(doc1.createTextNode(langValues.get(templ[0]+":"+templ[1]+":Aggreg").toString().toLowerCase()));
                        microplan.appendChild(used);
                        microplan.appendChild(agr);
                        Element Slots=doc1.createElement("owlnl:Slots");
                        Slots.setAttribute("rdf:parseType","Collection");
                        microplan.appendChild(Slots);
                        Vector vec1=(Vector) ob;
                        for(int y=0;y<vec1.size();y++){
                            try {
                                Hashtable template=(Hashtable) vec1.elementAt(y);
                                
                                Object sel=template.get("SELECTION");
                                if (sel.toString().equalsIgnoreCase("string")){
                                    if(template.containsKey("verb")){
                                        if (template.get("verb").toString().equalsIgnoreCase("true")){
                                            String verb=template.get("string").toString();
                                            Element text1= doc1.createElement("owlnl:Verb");
                                            Element voice=doc1.createElement("owlnl:voice");
                                            voice.appendChild(doc1.createTextNode(template.get("voice").toString()));
                                            text1.appendChild(voice);
                                            Element tense=doc1.createElement("owlnl:tense");
                                            tense.appendChild(doc1.createTextNode(template.get("tense").toString()));
                                            text1.appendChild(tense);
                                            Element val=doc1.createElement("owlnl:Val");
                                            val.appendChild(doc1.createTextNode(verb));
                                            val.setAttribute("xml:lang",lang);
                                            text1.appendChild(val);
                                            String plural="";
                                            if(template.containsKey("plural"))
                                                plural=template.get("plural").toString();
                                            Element pluralVal= doc1.createElement("owlnl:pluralVal");
                                            pluralVal.setAttribute("xml:lang",lang);
                                            pluralVal.appendChild(doc1.createTextNode(plural));
                                            text1.appendChild(pluralVal);
                                            Slots.appendChild(text1);
                                            continue;
                                        }}
                                    
                                    
                                    if(template.containsKey("prep")){
                                        if (template.get("prep").toString().equalsIgnoreCase("true")){
                                            String prep=template.get("string").toString();
                                            Element text1= doc1.createElement("owlnl:Prep");
                                            // Element voice=doc1.createElement("owlnl:voice");
                                            // voice.appendChild(doc1.createTextNode(template.get("voice").toString()));
                                            // text1.appendChild(voice);
                                            // Element tense=doc1.createElement("owlnl:tense");
                                            // tense.appendChild(doc1.createTextNode(template.get("tense").toString()));
                                            //  text1.appendChild(tense);
                                            Element val=doc1.createElement("owlnl:Val");
                                            val.appendChild(doc1.createTextNode(prep));
                                            val.setAttribute("xml:lang",lang);
                                            text1.appendChild(val);
                                            Slots.appendChild(text1);
                                            continue;
                                        }}
                                    
                                    String text=template.get("string").toString();
                                    Element text1= doc1.createElement("owlnl:Text");
                                    Element val=doc1.createElement("owlnl:Val");
                                    val.setAttribute("xml:lang",lang);
                                    val.appendChild(doc1.createTextNode(text));
                                    text1.appendChild(val);
                                    Slots.appendChild(text1);
                                    //    msg.appendChild(text1);
                                }
                                if (sel.toString().equalsIgnoreCase("referring")){
                                    if (template.get("semantics").toString().equalsIgnoreCase("Field owner")){
                                        Element sem=doc1.createElement("owlnl:Owner");
                                        Element case1=doc1.createElement("owlnl:case");
                                        try{
                                            case1.appendChild(doc1.createTextNode(template.get("grCase").toString().toLowerCase()));
                                            
                                        } catch (NullPointerException m){case1.appendChild(doc1.createTextNode("nominative"));}
                                        Element retype=doc1.createElement("owlnl:RETYPE");
                                        if(template.containsKey("type")){
                                            String type=template.get("type").toString();
                                            if(type.equalsIgnoreCase("Name")){
                                                retype.appendChild(doc1.createTextNode("RE_FULLNAME"));} else if(type.equalsIgnoreCase("Pronoun")){
                                                retype.appendChild(doc1.createTextNode("RE_PRONOUN"));} else if(type.equalsIgnoreCase("Type with definite article")){
                                                retype.appendChild(doc1.createTextNode("RE_DEF_ART"));} else if(type.equalsIgnoreCase("Type with indefinite article")){
                                                retype.appendChild(doc1.createTextNode("RE_INDEF_ART"));} else retype.appendChild(doc1.createTextNode("RE_AUTO"));
                                            
                                        } else
                                            retype.appendChild(doc1.createTextNode("RE_AUTO"));
                                        sem.appendChild(retype);
                                        sem.appendChild(case1);
                                        Slots.appendChild(sem);
                                    }
                                    if(template.get("semantics").toString().equalsIgnoreCase("Field filler")){
                                        
                                        Element sem=doc1.createElement("owlnl:Filler");
                                        Element case1=doc1.createElement("owlnl:case");
                                        try{
                                            case1.appendChild(doc1.createTextNode(template.get("grCase").toString().toLowerCase()));
                                        } catch (NullPointerException m){case1.appendChild(doc1.createTextNode("nominative"));}
                                        sem.appendChild(case1);
                                        Element retype=doc1.createElement("owlnl:RETYPE");
                                        if(template.containsKey("type")){
                                            String type=template.get("type").toString();
                                            if(type.equalsIgnoreCase("Name")){
                                                retype.appendChild(doc1.createTextNode("RE_FULLNAME"));} else if(type.equalsIgnoreCase("Pronoun")){
                                                retype.appendChild(doc1.createTextNode("RE_PRONOUN"));} else if(type.equalsIgnoreCase("Type with definite article")){
                                                retype.appendChild(doc1.createTextNode("RE_DEF_ART"));} else if(type.equalsIgnoreCase("Type with indefinite article")){
                                                retype.appendChild(doc1.createTextNode("RE_INDEF_ART"));} else retype.appendChild(doc1.createTextNode("RE_AUTO"));
                                            
                                        } else
                                            retype.appendChild(doc1.createTextNode("RE_AUTO"));
                                        sem.appendChild(retype);
                                        Slots.appendChild(sem);
                                    }
                                }
                            } catch (java.lang.Exception ex) {
                                continue;
                            }
                            
                            
                        }
                        //  root.appendChild(msg);
  /*                          Writer writer = new Writer();
        writer.setOutput(output, null);
        writer.write(doc1);*/
                        micros.put(templ[1]+"-"+templ[0]+"-"+lang,microplan);
                    }}
            }
        }
        Enumeration properties=Mpiro.win.struc.getPropertyNames();
        Enumeration propVectors=Mpiro.win.struc.getProperties();
        while(properties.hasMoreElements()){
            
            Vector nextPropVector=(Vector) propVectors.nextElement();
            String propName=properties.nextElement().toString();
            Element property=doc1.createElement("owlnl:Property");
            property.setAttribute("rdf:about",uri+'#'+propName);
            Element order=doc1.createElement("owlnl:Order");
            
            if(!nextPropVector.elementAt(13).toString().equalsIgnoreCase(""))
                order.appendChild(doc1.createTextNode(nextPropVector.elementAt(13).toString()));
            else
                order.appendChild(doc1.createTextNode("1"));
            property.appendChild(order);
            Element usedForComparisons=doc1.createElement("owlnl:UsedForComparisons");
            if(!nextPropVector.elementAt(14).toString().equalsIgnoreCase("true"))
                usedForComparisons.appendChild(doc1.createTextNode("false"));
            else
                usedForComparisons.appendChild(doc1.createTextNode("true"));
            property.appendChild(usedForComparisons);
            props.appendChild(property);
            Element greekMicros=doc1.createElement("owlnl:GreekMicroplans");
            greekMicros.setAttribute("rdf:parseType","Collection");
            property.appendChild(greekMicros);
            for(int i=0;i<5;i++){
                Element nextMicro=doc1.createElement("owlnl:Microplan");
                
                try{
                    nextMicro=(Element) micros.get(propName+"-"+String.valueOf(i+1)+"-"+"el");
                    greekMicros.appendChild(nextMicro);
                } catch (NullPointerException m){
                    Element nextMicro1=doc1.createElement("owlnl:Microplan");
                    nextMicro1.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(i+1)+"-"+"el");
                    Element MicroplanName=doc1.createElement("owlnl:MicroplanName");
                    MicroplanName.appendChild(doc1.createTextNode("templ"+String.valueOf(i+1)));
                    nextMicro1.appendChild(MicroplanName);
                    Element used=doc1.createElement("owlnl:Used");
                    used.appendChild(doc1.createTextNode("false"));
                    nextMicro1.appendChild(used);
                    Element agr=doc1.createElement("owlnl:AggrAllowed");
                    agr.appendChild(doc1.createTextNode("true"));
                    
                    nextMicro1.appendChild(agr);
                    //   System.out.println("????");
                    Element Slots=doc1.createElement("owlnl:Slots");
                    Slots.setAttribute("rdf:parseType","Collection");
                    nextMicro1.appendChild(Slots);
                    greekMicros.appendChild(nextMicro1);
                }
                
            }
            
            Element englishMicros=doc1.createElement("owlnl:EnglishMicroplans");
            englishMicros.setAttribute("rdf:parseType","Collection");
            property.appendChild(englishMicros);
            for(int i=0;i<5;i++){
                Element nextMicro=doc1.createElement("owlnl:Microplan");
                
                try{
                    nextMicro=(Element) micros.get(propName+"-"+String.valueOf(i)+"-"+"en");
                    englishMicros.appendChild(nextMicro);
                } catch (NullPointerException m){
                    Element nextMicro1=doc1.createElement("owlnl:Microplan");
                    nextMicro1.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(i)+"-"+"en");
                    Element MicroplanName=doc1.createElement("owlnl:MicroplanName");
                    MicroplanName.appendChild(doc1.createTextNode("templ"+String.valueOf(i)));
                    nextMicro1.appendChild(MicroplanName);
                    Element used=doc1.createElement("owlnl:Used");
                    used.appendChild(doc1.createTextNode("false"));
                    nextMicro1.appendChild(used);
                    Element agr=doc1.createElement("owlnl:AggrAllowed");
                    agr.appendChild(doc1.createTextNode("true"));
                    
                    nextMicro1.appendChild(agr);
                    //   System.out.println("????");
                    Element Slots=doc1.createElement("owlnl:Slots");
                    Slots.setAttribute("rdf:parseType","Collection");
                    nextMicro1.appendChild(Slots);
                    englishMicros.appendChild(nextMicro1);
                }
                
            }
            
        }
        
        Writer writer = new Writer();
        writer.setOutput(output,null);
        writer.write(doc1);
    }
    
    
    
    public static void galanis(String fileName,String uri) throws Exception {
        
        Hashtable hash=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type");
        hash.remove("Data Base");
        hash.remove("Basic-entity-types");
        
        //  Enumeration allEntityTypeNames;
        //     Enumeration allEntityTypeParentNames;
        
        
        //     allEntityTypeParentNames = hash.keys();
        File xmlFile = new File(fileName+"Microplans.rdf");
        
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc1 = docBuilder.newDocument();
        FileOutputStream output = new FileOutputStream(xmlFile);
        Element RDF1=doc1.createElement("rdf:RDF");
        doc1.appendChild(RDF1);
        RDF1.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF1.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF1.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/Microplans#");
        // RDF1.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        RDF1.setAttribute("xml:base","http://www.aueb.gr/users/ion/owlnl/Microplans");
        
        
        Element MicroplansAndOrdering= doc1.createElement("owlnl:MicroplansAndOrdering");
        RDF1.appendChild(MicroplansAndOrdering);
        //    Element ontology1=doc1.createElement("owlnl:Ontology");
        //    ontology1.setAttribute("rdf:resource",uri);
        //    MicroplansAndOrdering.appendChild(ontology1);
        Element props=doc1.createElement("owlnl:Properties");
        props.setAttribute("rdf:parseType","Collection");
        MicroplansAndOrdering.appendChild(props);
        //    Enumeration propKeys=Mpiro.win.struc.getPropertyNames();
        Enumeration propElements=Mpiro.win.struc.getProperties();
        Hashtable micros=new Hashtable();
        while (propElements.hasMoreElements()) {
            //    String entityTypeName = allEntityTypeParentNames.nextElement().toString();
            // NodeVector nodeEntityType = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
            Vector vec= (Vector) propElements.nextElement();
            TemplateVector templateVector = (TemplateVector) vec.elementAt(11);
            Hashtable microplanning=(Hashtable) vec.elementAt(10);
            for (int o=0;o<3;o=o+2){
                String lang="en";
                if (o==2) lang="el";
                Hashtable langValues = (Hashtable) templateVector.get(o);
                //     Hashtable italianValues = (Hashtable) templateVector.get(1);
                //   Hashtable greekValues = (Hashtable) templateVector.get(2);
                
                
                Enumeration langKeysEnu = langValues.keys();
                Enumeration langElementsEnu = langValues.elements();
                while (langKeysEnu.hasMoreElements()){
                    Object ob=langElementsEnu.nextElement();
                    String[] templ=langKeysEnu.nextElement().toString().split(":");
                    if (templ.length==2){
                        
                        //    Element prop=doc1.createElement("owlnl:Property");
                        //    prop.setAttribute("rdf:about",templ[1]);
                        //    props.appendChild(prop);
                        
                        //     Element order=doc1.createElement("owlnl:Order");
                        //    order.appendChild(doc1.createTextNode("1"));
                        //     prop.appendChild(order);
                        
                        //     Element root = doc1.createElement("xsl:template");
                        //     root.setAttribute("match",templ[1]+"[@Templ='"+templ[0]+"']");
                        //     Element style=doc1.createElement("xsl:stylesheet");
                        //  style.setAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
                        //style.appendChild(root);
                        //  doc1.appendChild(style);
                        //     Element msg = doc1.createElement("Msg");
                        //  msg.setAttribute("Order","{@Order}");
                        //  msg.setAttribute("RestrType","{name()}");
                        //         msg.setAttribute("property",templ[1]);
                        Element microplan=doc1.createElement("owlnl:Microplan");
                        microplan.setAttribute("rdf:about",uri+'#'+templ[1]+"-templ"+templ[0]+"-"+lang);
                        Element MicroplanName=doc1.createElement("owlnl:MicroplanName");
                        MicroplanName.appendChild(doc1.createTextNode("templ"+templ[0]));
                        microplan.appendChild(MicroplanName);
                        Element used=doc1.createElement("owlnl:Used");
                        if(o==2){
                            if(microplanning.get(templ[0]+":"+templ[1]+":SELECTION:Greek").toString().equalsIgnoreCase("NoMicroplanning"))
                                used.appendChild(doc1.createTextNode("false"));
                            else
                                used.appendChild(doc1.createTextNode("true"));
                        } else{
                            if(microplanning.get(templ[0]+":"+templ[1]+":SELECTION:English").toString().equalsIgnoreCase("NoMicroplanning"))
                                used.appendChild(doc1.createTextNode("false"));
                            else
                                used.appendChild(doc1.createTextNode("true"));
                        }
                        
                        //used.appendChild(doc1.createTextNode("true"));
                        microplan.appendChild(used);
                        
                        Element agr=doc1.createElement("owlnl:AggrAllowed");
                        agr.appendChild(doc1.createTextNode(langValues.get(templ[0]+":"+templ[1]+":Aggreg").toString().toLowerCase()));
                        
                        microplan.appendChild(agr);
                        Element Slots=doc1.createElement("owlnl:Slots");
                        Slots.setAttribute("rdf:parseType","Collection");
                        microplan.appendChild(Slots);
                        Vector vec1=(Vector) ob;
                        for(int y=0;y<vec1.size();y++){
                            Hashtable template=(Hashtable) vec1.elementAt(y);
                            
                            Object sel=template.get("SELECTION");
                            if (sel.toString().equalsIgnoreCase("string")){
                                if(template.containsKey("verb")){
                                    if (template.get("verb").toString().equalsIgnoreCase("true")){
                                        String verb=template.get("string").toString();
                                        Element text1= doc1.createElement("owlnl:Verb");
                                        Element voice=doc1.createElement("owlnl:voice");
                                        voice.appendChild(doc1.createTextNode(template.get("voice").toString()));
                                        text1.appendChild(voice);
                                        Element tense=doc1.createElement("owlnl:tense");
                                        tense.appendChild(doc1.createTextNode(template.get("tense").toString()));
                                        text1.appendChild(tense);
                                        Element val=doc1.createElement("owlnl:Val");
                                        val.appendChild(doc1.createTextNode(verb));
                                        val.setAttribute("xml:lang",lang);
                                        text1.appendChild(val);
                                        Slots.appendChild(text1);
                                    } else{
                                        String text=template.get("string").toString();
                                        Element text1= doc1.createElement("owlnl:Text");
                                        Element val=doc1.createElement("owlnl:Val");
                                        val.setAttribute("xml:lang",lang);
                                        val.appendChild(doc1.createTextNode(text));
                                        text1.appendChild(val);
                                        Slots.appendChild(text1);
                                    }
                                } else{
                                    String text=template.get("string").toString();
                                    Element text1= doc1.createElement("owlnl:Text");
                                    Element val=doc1.createElement("owlnl:Val");
                                    val.setAttribute("xml:lang",lang);
                                    val.appendChild(doc1.createTextNode(text));
                                    text1.appendChild(val);
                                    Slots.appendChild(text1);
                                    //    msg.appendChild(text1);
                                }}
                            if (sel.toString().equalsIgnoreCase("referring")){
                                if (template.get("semantics").toString().equalsIgnoreCase("Field owner")){
                                    Element sem=doc1.createElement("owlnl:Owner");
                                    Element case1=doc1.createElement("owlnl:case");
                                    try{
                                        case1.appendChild(doc1.createTextNode(template.get("grCase").toString().toLowerCase()));
                                        
                                    } catch (NullPointerException m){case1.appendChild(doc1.createTextNode("nominative"));}
                                    sem.appendChild(case1);
                                    Element retype=doc1.createElement("owlnl:RETYPE");
                                    retype.appendChild(doc1.createTextNode("RE_AUTO"));
                                    sem.appendChild(retype);
                                    Slots.appendChild(sem);
                                }
                                if(template.get("semantics").toString().equalsIgnoreCase("Field filler")){
                                    
                                    Element sem=doc1.createElement("owlnl:Filler");
                                    Element case1=doc1.createElement("owlnl:case");
                                    try{
                                        case1.appendChild(doc1.createTextNode(template.get("grCase").toString().toLowerCase()));
                                    } catch (NullPointerException m){case1.appendChild(doc1.createTextNode("nominative"));}
                                    sem.appendChild(case1);
                                    Element retype=doc1.createElement("owlnl:RETYPE");
                                    retype.appendChild(doc1.createTextNode("RE_AUTO"));
                                    sem.appendChild(retype);
                                    Slots.appendChild(sem);
                                }
                            }
                            
                            
                        }
                        //  root.appendChild(msg);
  /*                          Writer writer = new Writer();
        writer.setOutput(output, null);
        writer.write(doc1);*/
                        micros.put(templ[1]+"-"+templ[0]+"-"+lang,microplan);
                    }}
            }
        }
        Enumeration properties=Mpiro.win.struc.getPropertyNames();
        Enumeration propVectors=Mpiro.win.struc.getProperties();
        while(properties.hasMoreElements()){
            
            Vector nextPropVector=(Vector) propVectors.nextElement();
            String propName=properties.nextElement().toString();
            Element property=doc1.createElement("owlnl:Property");
            property.setAttribute("rdf:about",uri+'#'+propName);
            Element order=doc1.createElement("owlnl:Order");
            if(!nextPropVector.elementAt(13).toString().equalsIgnoreCase(""))
                order.appendChild(doc1.createTextNode(nextPropVector.elementAt(13).toString()));
            else
                order.appendChild(doc1.createTextNode("1"));
            property.appendChild(order);
            props.appendChild(property);
            Element greekMicros=doc1.createElement("owlnl:GreekMicroplans");
            greekMicros.setAttribute("rdf:parseType","Collection");
            property.appendChild(greekMicros);
            for(int i=0;i<5;i++){
                Element nextMicro=doc1.createElement("owlnl:Microplan");
                
                try{
                    nextMicro=(Element) micros.get(propName+"-"+String.valueOf(i+1)+"-"+"el");
                    greekMicros.appendChild(nextMicro);
                } catch (NullPointerException m){
                    Element nextMicro1=doc1.createElement("owlnl:Microplan");
                    nextMicro1.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(i+1)+"-"+"el");
                    Element MicroplanName=doc1.createElement("owlnl:MicroplanName");
                    MicroplanName.appendChild(doc1.createTextNode("templ"+String.valueOf(i+1)));
                    nextMicro1.appendChild(MicroplanName);
                    Element used=doc1.createElement("owlnl:Used");
                    used.appendChild(doc1.createTextNode("false"));
                    nextMicro1.appendChild(used);
                    Element agr=doc1.createElement("owlnl:AggrAllowed");
                    agr.appendChild(doc1.createTextNode("true"));
                    
                    nextMicro1.appendChild(agr);
                    //   System.out.println("????");
                    Element Slots=doc1.createElement("owlnl:Slots");
                    Slots.setAttribute("rdf:parseType","Collection");
                    nextMicro1.appendChild(Slots);
                    greekMicros.appendChild(nextMicro1);
                }
                
            }
            
            Element englishMicros=doc1.createElement("owlnl:EnglishMicroplans");
            englishMicros.setAttribute("rdf:parseType","Collection");
            property.appendChild(englishMicros);
            for(int i=0;i<5;i++){
                Element nextMicro=doc1.createElement("owlnl:Microplan");
                
                try{
                    nextMicro=(Element) micros.get(propName+"-"+String.valueOf(i)+"-"+"en");
                    englishMicros.appendChild(nextMicro);
                } catch (NullPointerException m){
                    Element nextMicro1=doc1.createElement("owlnl:Microplan");
                    nextMicro1.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(i)+"-"+"en");
                    Element MicroplanName=doc1.createElement("owlnl:MicroplanName");
                    MicroplanName.appendChild(doc1.createTextNode("templ"+String.valueOf(i)));
                    nextMicro1.appendChild(MicroplanName);
                    Element used=doc1.createElement("owlnl:Used");
                    used.appendChild(doc1.createTextNode("false"));
                    nextMicro1.appendChild(used);
                    Element agr=doc1.createElement("owlnl:AggrAllowed");
                    agr.appendChild(doc1.createTextNode("true"));
                    
                    nextMicro1.appendChild(agr);
                    //   System.out.println("????");
                    Element Slots=doc1.createElement("owlnl:Slots");
                    Slots.setAttribute("rdf:parseType","Collection");
                    nextMicro1.appendChild(Slots);
                    englishMicros.appendChild(nextMicro1);
                }
                
            }
            
        }
        
        Writer writer = new Writer();
        writer.setOutput(output,null);
        writer.write(doc1);
        
        
        
        //DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element RDF=doc.createElement("rdf:RDF");
        doc.appendChild(RDF);
        //  RDF.setAttribute("xmlns:owlnl","http://www.owlnl.com/owlnl#");
        //   RDF.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        //    RDF.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/Lexicon#");
        //    RDF.setAttribute("xml:base","http://www.aueb.gr/users/ion/owlnl/Lexicon");
        RDF.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/Lexicon#");
        RDF.setAttribute("xml:base","http://www.aueb.gr/users/ion/owlnl/Lexicon");
        //RDF.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        
        
        
        
        Element lexicon=doc.createElement("owlnl:Lexicon");
        RDF.appendChild(lexicon);
        
        //      Element ontology=doc.createElement("owlnl:Ontology");
        //      ontology.setAttribute("rdf:resource",uri);
        //      lexicon.appendChild(ontology);
        
        Element nounsElement = doc.createElement("owlnl:NPList");
        nounsElement.setAttribute("rdf:parseType","Collection");
        //Element style=doc.createElement("xsl:stylesheet");
        //         style.setAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
        //       style.appendChild(nounsElement);
        lexicon.appendChild(nounsElement);
        
        Element Mapping=doc.createElement("owlnl:Mapping");
        RDF.appendChild(Mapping);
        Element entries=doc.createElement("owlnl:Entries");
        entries.setAttribute("rdf:parseType","Collection");
        Mapping.appendChild(entries);
        Hashtable allTypes=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type");
        Enumeration keys=allTypes.keys();
        //   Enumeration elements=allTypes.elements();
        while(keys.hasMoreElements()){
            String key=keys.nextElement().toString();
            
            NodeVector nv= (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(key);
            Vector nouns=(Vector) nv.nounVector.clone();
            Vector parents=Mpiro.win.struc.getParents(key);
            for(int i=0;i<parents.size();i++){
                NodeVector parent=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parents.elementAt(i).toString());
                Vector parentNouns=parent.nounVector;
                for(int j=0;j<parentNouns.size();j++){
                    if(nouns.contains(parentNouns.elementAt(j)))
                        nouns.remove(parentNouns.elementAt(j));
                }
            }
            if (!nouns.isEmpty()){
                Element owlClass=doc.createElement("owlnl:owlClass");
                owlClass.setAttribute("rdf:about",uri+'#'+convertToClassName(key));
                Element hasNP=doc.createElement("owlnl:hasNP");
                hasNP.setAttribute("rdf:resource","#"+nouns.elementAt(0).toString()+"-NP");//!!!! only one noun????
                owlClass.appendChild(hasNP);
                entries.appendChild(owlClass);
            }
            
        }
        Hashtable allEntities=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity+generic");
        Enumeration Entkeys=allEntities.keys();
        //    Enumeration Entelements=allTypes.elements();
        while(Entkeys.hasMoreElements()){
            String key=Entkeys.nextElement().toString();
            NodeVector nv=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(key);
            Element owlInstance=doc.createElement("owlnl:owlInstance");
            owlInstance.setAttribute("rdf:about",uri+'#'+key);
            Element hasNP=doc.createElement("owlnl:hasNP");
            hasNP.setAttribute("rdf:resource","#"+key+"-NP");//!!!! only one noun????
            owlInstance.appendChild(hasNP);
            entries.appendChild(owlInstance);
            
            Element curNounElement = doc.createElement("owlnl:NP");
            curNounElement.setAttribute("rdf:ID",key+"-NP");//nounName to be replaced with namespace!!!!!
            nounsElement.appendChild(curNounElement);
            Element NP =doc.createElement("owlnl:LanguagesNP");
            NP.setAttribute("rdf:parseType","Collection");//!!!!!!!!! to be replaced by gender of datatype
            // NP.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            Element englishNP=doc.createElement("owlnl:EnglishNP");
            NP.appendChild(englishNP);
            Vector english=nv.englishFieldsVector;
            
            Element countable3=doc.createElement("owlnl:countable");
            //
            countable3.appendChild(doc.createTextNode("no"));
            // else
            //   countable1.appendChild(doc.createTextNode("yes"));
            englishNP.appendChild(countable3);
            Element num=doc.createElement("owlnl:num");
            Vector temp=(Vector) english.elementAt(5);
            num.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            englishNP.appendChild(num);
            Element gender=doc.createElement("owlnl:gender");
            temp=(Vector) english.elementAt(4);
            if(temp.elementAt(1).toString().equalsIgnoreCase("neuter"))
                gender.appendChild(doc.createTextNode("nonpersonal"));
            else
                gender.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            //gender.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            englishNP.appendChild(gender);
            Element singular=doc.createElement("owlnl:singular");
            singular.setAttribute("xml:lang","en");
            Element plural=doc.createElement("owlnl:plural");
            plural.setAttribute("xml:lang","en");
            temp=(Vector) english.elementAt(5);
            if(temp.elementAt(1).toString().equalsIgnoreCase("singular")){
                temp=(Vector) english.elementAt(1);
                singular.appendChild(doc.createTextNode(temp.elementAt(1).toString()));} else{
                temp=(Vector) english.elementAt(1);
                plural.appendChild(doc.createTextNode(temp.elementAt(1).toString()));}
            englishNP.appendChild(singular);
            englishNP.appendChild(plural);
            curNounElement.appendChild(NP);
            
            
            Vector greek=nv.greekFieldsVector;
            Element greekNP=doc.createElement("owlnl:GreekNP");
            NP.appendChild(greekNP);
            Element countable2=doc.createElement("owlnl:countable");
            //
            countable2.appendChild(doc.createTextNode("no"));
            // else
            //   countable1.appendChild(doc.createTextNode("yes"));
            greekNP.appendChild(countable2);
            Element num1=doc.createElement("owlnl:num");
            temp=(Vector) greek.elementAt(10);
            num1.appendChild(doc.createTextNode(temp.elementAt(1).toString()));//!!!!!!!!! to be replaced by number of datatype
            greekNP.appendChild(num1);
            Element gender1=doc.createElement("owlnl:gender");
            temp=(Vector) greek.elementAt(4);
            if(temp.elementAt(1).toString().equalsIgnoreCase("neuter"))
                gender1.appendChild(doc.createTextNode("neuter"));
            else
                gender1.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
            greekNP.appendChild(gender1);
            Element singular1=doc.createElement("owlnl:singular");
            Element plural1=doc.createElement("owlnl:plural");
            Element singForms=doc.createElement("owlnl:singularForms");
            Element pluralForms=doc.createElement("owlnl:pluralForms");
            Element nominSin=doc.createElement("owlnl:nominative");
            Element nominPlu=doc.createElement("owlnl:nominative");
            Element genSin=doc.createElement("owlnl:genitive");
            Element genPlu=doc.createElement("owlnl:genitive");
            Element accSin=doc.createElement("owlnl:accusative");
            Element accPlu=doc.createElement("owlnl:accusative");
            nominSin.setAttribute("xml:lang","el");
            nominPlu.setAttribute("xml:lang","el");
            genSin.setAttribute("xml:lang","el");
            genPlu.setAttribute("xml:lang","el");
            accSin.setAttribute("xml:lang","el");
            accPlu.setAttribute("xml:lang","el");
            if(!greek.elementAt(10).toString().contains("plural")){
                temp=(Vector) greek.elementAt(1);
                nominSin.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(2);
                genSin.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(3);
                accSin.appendChild(doc.createTextNode(temp.elementAt(1).toString()));} else{
                temp=(Vector) greek.elementAt(1);
                nominPlu.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(2);
                genPlu.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                temp=(Vector) greek.elementAt(3);
                accPlu.appendChild(doc.createTextNode(temp.elementAt(1).toString()));
                }
            singForms.appendChild(nominSin);
            singForms.appendChild(genSin);
            singForms.appendChild(accSin);
            pluralForms.appendChild(nominPlu);
            pluralForms.appendChild(genPlu);
            pluralForms.appendChild(accPlu);
            singular1.appendChild(singForms);
            plural1.appendChild(pluralForms);
            greekNP.appendChild(singular1);
            greekNP.appendChild(plural1);
        }
        
        
        
        
        
        //       DocumentBuilder docBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //    Document doc1 = docBuilder1.newDocument();
        //     Element nounsElement1 = doc1.createElement("lexicon");
        //     nounsElement1.setAttribute("language","gr");
        //   Element style1=doc1.createElement("xsl:stylesheet");
        //          style1.setAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
        //       style1.appendChild(nounsElement1);
        //              doc1.appendChild(nounsElement1);
        Hashtable allNounHashTable =  Mpiro.win.struc.getNounsHashtable();
        //    Hashtable allVerbHashTable = (Hashtable) Mpiro.win.struc.getVerbsHashtable();
        
        //nouns
        Enumeration nounNameEnu = allNounHashTable.keys();
        Enumeration nounHashTableEnu = allNounHashTable.elements();
        while (nounNameEnu.hasMoreElements()) {
            String nounName = (String) nounNameEnu.nextElement();
            Hashtable nounHashTable = (Hashtable) nounHashTableEnu.nextElement();
            Hashtable englishNouns= (Hashtable) nounHashTable.get("English");
            Element curNounElement = doc.createElement("owlnl:NP");
            curNounElement.setAttribute("rdf:ID",nounName+"-NP");//nounName to be replaced with namespace!!!!!
            nounsElement.appendChild(curNounElement);
            Element NP =doc.createElement("owlnl:LanguagesNP");
            NP.setAttribute("rdf:parseType","Collection");//!!!!!!!!! to be replaced by gender of datatype
            // NP.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            Element englishNP=doc.createElement("owlnl:EnglishNP");
            NP.appendChild(englishNP);
            Element countable4=doc.createElement("owlnl:countable");
            if (englishNouns.get("countable").toString().equalsIgnoreCase("No"))
                countable4.appendChild(doc.createTextNode("no"));
            else
                countable4.appendChild(doc.createTextNode("yes"));
            englishNP.appendChild(countable4);
            Element num=doc.createElement("owlnl:num");
            num.appendChild(doc.createTextNode("singular"));
            englishNP.appendChild(num);
            Element gender=doc.createElement("owlnl:gender");
            gender.appendChild(doc.createTextNode("nonpersonal"));
            englishNP.appendChild(gender);
            Element singular=doc.createElement("owlnl:singular");
            singular.setAttribute("xml:lang","en");
            Element plural=doc.createElement("owlnl:plural");
            plural.setAttribute("xml:lang","en");
            singular.appendChild(doc.createTextNode(englishNouns.get("enbasetext").toString()));
            plural.appendChild(doc.createTextNode(englishNouns.get("enpluraltext").toString()));
            englishNP.appendChild(singular);
            englishNP.appendChild(plural);
            curNounElement.appendChild(NP);
            
            
            
            
            
            
            Hashtable greekNouns= (Hashtable) nounHashTable.get("Greek");
            //  Element curNounElement1 = doc1.createElement("OntClass");
            //    curNounElement1.setAttribute("name",nounName);//nounName to be replaced with namespace!!!!!
            //    nounsElement1.appendChild(curNounElement1);
            //    Element NP1 =doc1.createElement("NP");
            //    NP1.setAttribute("gender",greekNouns.get("grgender").toString().toLowerCase());
            //    if (greekNouns.get("grgender").toString().equalsIgnoreCase("Neuter"))
            //        NP1.setAttribute("gender","nonpersonal");
            //  NP1.setAttribute("gender","nonpersonal");//!!!!!!!!! to be replaced by gender of datatype
            //     NP1.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            //     if (greekNouns.get("countable").toString().equalsIgnoreCase("No"))
            //         NP1.setAttribute("countable","F");
            //     else
            //          NP1.setAttribute("countable","T");
 /*           Element singular1=doc1.createElement("singular");
            Element plural1=doc1.createElement("plural");
            Element nomsin=doc1.createElement("nominative");
            Element nomplu=doc1.createElement("nominative");
            Element gensin=doc1.createElement("genitive");
            Element genplu=doc1.createElement("genitive");
            Element accsin=doc1.createElement("accusative");
            Element accplu=doc1.createElement("accusative");
            nomsin.appendChild(doc1.createTextNode(greekNouns.get("grbasetext").toString()));
            nomplu.appendChild(doc1.createTextNode(greekNouns.get("grpluraltext").toString()));
            genplu.appendChild(doc1.createTextNode(greekNouns.get("grpgtext").toString()));
            gensin.appendChild(doc1.createTextNode(greekNouns.get("grsgtext").toString()));
            accplu.appendChild(doc1.createTextNode(greekNouns.get("grpatext").toString()));
            accsin.appendChild(doc1.createTextNode(greekNouns.get("grsatext").toString()));
            singular1.appendChild(nomsin);
            singular1.appendChild(gensin);
            singular1.appendChild(accsin);
            plural1.appendChild(nomplu);
            plural1.appendChild(genplu);
            plural1.appendChild(accplu);
            NP1.appendChild(singular1);
            NP1.appendChild(plural1);
            curNounElement1.appendChild(NP1);
  */
            
            
            
            
            
            Element greekNP=doc.createElement("owlnl:GreekNP");
            NP.appendChild(greekNP);
            Element countable1=doc.createElement("owlnl:countable");
            if (greekNouns.get("countable").toString().equalsIgnoreCase("No"))
                countable1.appendChild(doc.createTextNode("no"));
            else
                countable1.appendChild(doc.createTextNode("yes"));
            greekNP.appendChild(countable1);
            Element num1=doc.createElement("owlnl:num");
            num1.appendChild(doc.createTextNode("singular"));//!!!!!!!!! to be replaced by number of datatype
            greekNP.appendChild(num1);
            Element gender1=doc.createElement("owlnl:gender");
            if(greekNouns.get("grgender").toString().equalsIgnoreCase("neuter"))
                gender1.appendChild(doc.createTextNode("neuter"));
            else
                gender1.appendChild(doc.createTextNode(greekNouns.get("grgender").toString().toLowerCase()));
            greekNP.appendChild(gender1);
            Element singular1=doc.createElement("owlnl:singular");
            Element plural1=doc.createElement("owlnl:plural");
            Element singForms=doc.createElement("owlnl:singularForms");
            Element pluralForms=doc.createElement("owlnl:pluralForms");
            Element nominSin=doc.createElement("owlnl:nominative");
            Element nominPlu=doc.createElement("owlnl:nominative");
            Element genSin=doc.createElement("owlnl:genitive");
            Element genPlu=doc.createElement("owlnl:genitive");
            Element accSin=doc.createElement("owlnl:accusative");
            Element accPlu=doc.createElement("owlnl:accusative");
            nominSin.setAttribute("xml:lang","el");
            nominPlu.setAttribute("xml:lang","el");
            genSin.setAttribute("xml:lang","el");
            genPlu.setAttribute("xml:lang","el");
            accSin.setAttribute("xml:lang","el");
            accPlu.setAttribute("xml:lang","el");
            nominSin.appendChild(doc.createTextNode(greekNouns.get("grbasetext").toString()));
            nominPlu.appendChild(doc.createTextNode(greekNouns.get("grpluraltext").toString()));
            genSin.appendChild(doc.createTextNode(greekNouns.get("grsgtext").toString()));
            genPlu.appendChild(doc.createTextNode(greekNouns.get("grpgtext").toString()));
            accSin.appendChild(doc.createTextNode(greekNouns.get("grsatext").toString()));
            accPlu.appendChild(doc.createTextNode(greekNouns.get("grpatext").toString()));
            singForms.appendChild(nominSin);
            singForms.appendChild(genSin);
            singForms.appendChild(accSin);
            pluralForms.appendChild(nominPlu);
            pluralForms.appendChild(genPlu);
            pluralForms.appendChild(accPlu);
            singular1.appendChild(singForms);
            plural1.appendChild(pluralForms);
            greekNP.appendChild(singular1);
            greekNP.appendChild(plural1);
            
            
        }
        
        
        File xmlFile1 = new File(fileName+"Lexicon.rdf");
        FileOutputStream output1 = new FileOutputStream(xmlFile1);
        // Writer writer = new Writer();
        writer.setOutput(output1,null);
        writer.write(doc);
        
        
        
        
        //USER MODELING
        File xmlFile2 = new File(fileName+"UserModelling.rdf");
        //     DocumentBuilder docBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc2 = docBuilder.newDocument();
        FileOutputStream output2 = new FileOutputStream(xmlFile2);
        Element RDF2=doc2.createElement("rdf:RDF");
        doc2.appendChild(RDF2);
        String base= "http://www.aueb.gr/users/ion/owlnl/UserModelling";
        RDF2.setAttribute("xmlns:owlnl","http://www.aueb.gr/users/ion/owlnl#");
        RDF2.setAttribute("xmlns:rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        RDF2.setAttribute("xmlns","http://www.aueb.gr/users/ion/owlnl/UserModelling#");
        RDF2.setAttribute("xml:base",base);
        //RDF2.setAttribute("xmlns:rdfs","http://www.w3.org/2000/01/rdf-schema#");
        
        
        
        
        Element UserModelling=doc2.createElement("owlnl:UserModelling");
        RDF2.appendChild(UserModelling);
        Element usersElement = doc2.createElement("owlnl:UserTypes");
        usersElement.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(usersElement);
        
        Enumeration userNamesEnu = Mpiro.win.struc.getUserNames();
        Enumeration userValuesEnu = Mpiro.win.struc.getUserElements();
        Vector usersVector = new Vector();
        while (userNamesEnu.hasMoreElements()) {
            String userName = (String) userNamesEnu.nextElement();
            Vector userVector = (Vector) userValuesEnu.nextElement();
            usersVector.add(userName);
            Element currentUserElement = doc2.createElement("owlnl:UserType");
            currentUserElement.setAttribute("rdf:ID",userName);
            usersElement.appendChild(currentUserElement);
            
            Element sentenceElement = doc2.createElement("owlnl:MaxFactsPerSentence");
            sentenceElement.appendChild(doc2.createTextNode(userVector.get(0).toString()));
            Element paragraphElement = doc2.createElement("owlnl:FactsPerPage");
            paragraphElement.appendChild(doc2.createTextNode(userVector.get(1).toString()));
            //   Element conjunctionElement = doc2.createElement("Link-Number");
            // conjunctionElement.appendChild(doc2.createTextNode(userVector.get(2).toString()));
            Element voiceElement = doc2.createElement("owlnl:SynthesizerVoice");
            voiceElement.appendChild(doc2.createTextNode(userVector.get(3).toString()));
            
            currentUserElement.appendChild(sentenceElement);
            currentUserElement.appendChild(paragraphElement);
            // currentUserElement.appendChild(conjunctionElement);
            currentUserElement.appendChild(voiceElement);
            
            
        }
        
        
        Element PropertiesInterests  = doc2.createElement("owlnl:PropertiesInterests");
        PropertiesInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(PropertiesInterests);
        properties=Mpiro.win.struc.getPropertyNames();
        Enumeration propVectors1=Mpiro.win.struc.getProperties();
        while(properties.hasMoreElements()){
            //   Vector property=(Vector) properties.nextElement();
            String nextProp=properties.nextElement().toString();
            Vector nextPropVector=(Vector) propVectors1.nextElement();
            Element Property  = doc2.createElement("owlnl:Property");
            Property.setAttribute("rdf:about",uri+'#'+nextProp);
            PropertiesInterests.appendChild(Property);
            Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
            Enumeration users=usersHash.keys();
            Enumeration values=usersHash.elements();
            while(users.hasMoreElements()){
                Element DPInterest  = doc2.createElement("owlnl:DPInterest");
                DPInterest.setAttribute("rdf:parseType","Resource");
                Property.appendChild(DPInterest);
                Element forUserType = doc2.createElement("owlnl:forUserType");
                forUserType.setAttribute("rdf:resource",base+"#"+users.nextElement().toString());
                DPInterest.appendChild(forUserType);
                Element InterestValue= doc2.createElement("owlnl:InterestValue");
                Vector value=(Vector) values.nextElement();
                InterestValue.appendChild(doc2.createTextNode(value.elementAt(1).toString()));
                DPInterest.appendChild(InterestValue);
            }
            Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(nextProp);
            Enumeration npkeys=np.keys();
            Enumeration npelems=np.elements();
            while(npkeys.hasMoreElements()){
                String nextDomain=npkeys.nextElement().toString();
                // Hashtable nextElem=(Hashtable) npelems.nextElement();
                if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").containsKey(nextDomain)){
                    
                    
                    Vector modelling_values=Mpiro.win.struc.getRobotModelling(nextProp, nextDomain);
                    Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                    Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                    // Enumeration users1=nextElem.keys();
                    //  Enumeration values1=nextElem.elements();
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element CDPInterest  = doc2.createElement("owlnl:CDPInterest");
                        CDPInterest.setAttribute("rdf:parseType","Resource");
                        Property.appendChild(CDPInterest);
                        //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                        Element forClass=doc2.createElement("owlnl:forOwlClass");
                        forClass.setAttribute("rdf:resource",uri+"#"+nextDomain);
                        CDPInterest.appendChild(forClass);
                        //String nextuser=users1.nextElement().toString();
                        //Vector usersValue=(Vector) values1.nextElement();
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+nextuser);
                        CDPInterest.appendChild(forUserType);
                        Element InterestValue= doc2.createElement("owlnl:InterestValue");
                        // Vector value=(Vector) values.nextElement();usersValue.elementAt(1).toString()
                        
                        InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(1).toString()));
                        CDPInterest.appendChild(InterestValue);
                    }
                } else{
                    
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(nextProp, nextDomain);
                    Enumeration users1=(Enumeration)modelling_values.elementAt(0);
                    Enumeration values1=(Enumeration)modelling_values.elementAt(1);
                    // Enumeration users1=nextElem.keys();
                    // Enumeration values1=nextElem.elements();
                    while(users1.hasMoreElements()){
                        String nextuser=users1.nextElement().toString();
                        Vector usersValue=(Vector) values1.nextElement();
                        if(usersValue.elementAt(1).toString().equalsIgnoreCase("")) continue;
                        Element IPInterest  = doc2.createElement("owlnl:IPInterest");
                        IPInterest.setAttribute("rdf:parseType","Resource");
                        Property.appendChild(IPInterest);
                        //Hashtable usersHash=(Hashtable) nextPropVector.elementAt(12);
                        Element forInstance =doc2.createElement("owlnl:forInstance");
                        forInstance .setAttribute("rdf:resource",uri+"#"+nextDomain);
                        IPInterest.appendChild(forInstance );
                        
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+nextuser);
                        IPInterest.appendChild(forUserType);
                        Element InterestValue= doc2.createElement("owlnl:InterestValue");
                        // Vector value=(Vector) values.nextElement();
                        InterestValue.appendChild(doc2.createTextNode(usersValue.elementAt(1).toString()));
                        IPInterest.appendChild(InterestValue);
                    }
                }
            }
            
        }
        
        
        
        Element Appropriateness = doc2.createElement("owlnl:Appropriateness");
        Appropriateness.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(Appropriateness);
        
        
        
        
        properties=Mpiro.win.struc.getPropertyNames();
        while(properties.hasMoreElements()){
            Enumeration allEntityTypeParentNames = hash.keys();
            String propName=properties.nextElement().toString();
            while(allEntityTypeParentNames.hasMoreElements()){
                
                String entityTypeName = allEntityTypeParentNames.nextElement().toString();
                // NodeVector nodeEntityType = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
                Vector vec= (Vector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
                // TemplateVector templateVector = (TemplateVector) vec.elementAt(4);
                Hashtable microPlanningValues = (Hashtable) vec.get(5);
                
                
                for(int j=1;j<6;j++){
                    Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                    MicroplanApprop.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(j)+"-el");
                    
                    for(int i=0;i<usersVector.size();i++){
                        Element Approp=doc2.createElement("owlnl:Approp");
                        Approp.setAttribute("rdf:parseType","Resource");
                        MicroplanApprop.appendChild(Approp);
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+usersVector.elementAt(i).toString());
                        Approp.appendChild(forUserType);
                        Element AppropValue= doc2.createElement("owlnl:AppropValue");
                        try{
                            
                            
                            String value= microPlanningValues.get(String.valueOf(j)+":"+propName+":"+usersVector.elementAt(i).toString()+":"+"Greek").toString();
                            AppropValue.appendChild(doc2.createTextNode(value));
                            Approp.appendChild(AppropValue);
                            Appropriateness.appendChild(MicroplanApprop);
                        } catch(NullPointerException c){continue;}
                        
                    }}
                
                for(int j=1;j<6;j++){
                    Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                    MicroplanApprop.setAttribute("rdf:about",uri+'#'+propName+"-templ"+String.valueOf(j)+"-en");
                    
                    for(int i=0;i<usersVector.size();i++){
                        Element Approp=doc2.createElement("owlnl:Approp");
                        Approp.setAttribute("rdf:parseType","Resource");
                        MicroplanApprop.appendChild(Approp);
                        Element forUserType = doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+usersVector.elementAt(i).toString());
                        Approp.appendChild(forUserType);
                        Element AppropValue= doc2.createElement("owlnl:AppropValue");
                        
                        try{
                            
                            
                            String value= microPlanningValues.get(String.valueOf(j)+":"+propName+":"+usersVector.elementAt(i).toString()+":"+"English").toString();
                            AppropValue.appendChild(doc2.createTextNode(value));
                            Approp.appendChild(AppropValue);
                            Appropriateness.appendChild(MicroplanApprop);
                        } catch(NullPointerException c){continue;}
                        
                    }}
                
            }}
        
        
        
        
        
        Element ClassInterests = doc2.createElement("owlnl:ClassInterests");
        ClassInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(ClassInterests);
        
        Hashtable alltypes=new Hashtable();
        Enumeration allTypes1=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type").keys();
        while(allTypes1.hasMoreElements()){
            String next=convertToClassName(allTypes1.nextElement().toString());
            Element el=doc2.createElement("owlnl:owlClass");
            el.setAttribute("rdf:about",uri+'#'+next);
            //  Element Properties=doc2.createElement("owlnl:Properties");
            //  Properties.setAttribute("rdf:parseType","Collection");
            //  el.appendChild(Properties);
            //   ClassInterests.appendChild(el);
            alltypes.put(next,el);
            ClassInterests.appendChild(el);
        }
        
        
        
        
 /*       Element InstancesInterests = doc2.createElement("owlnl:InstancesInterests");
         InstancesInterests.setAttribute("rdf:parseType","Collection");
        UserModelling.appendChild(InstancesInterests);
  
Hashtable allElements=new Hashtable();
        Enumeration allEntities1=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity+generic").keys();
        while(allEntities1.hasMoreElements()){
            String next=allEntities1.nextElement().toString();
            Element el=doc2.createElement("owlnl:owlInstance");
            el.setAttribute("rdf:about",uri+'/'+next);
            Element Properties=doc2.createElement("owlnl:Properties");
            Properties.setAttribute("rdf:parseType","Collection");
            el.appendChild(Properties);
            Element Classes=doc2.createElement("owlnl:Classes");
            Classes.setAttribute("rdf:parseType","Collection");
            el.appendChild(Classes);
           // InstancesInterests.appendChild(el);
            allElements.put(next,el);
            InstancesInterests.appendChild(el);
        }*/
        
        
        Enumeration mainUserModelHashtableEnumKeys = Mpiro.win.struc.mainUserModelHashtableKeys();
        Enumeration mainUserModelHashtableEnumElements = Mpiro.win.struc.mainUserModelHashtableElements();
        while (mainUserModelHashtableEnumKeys.hasMoreElements()) {
            String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
            
            Hashtable fieldnameHashtable = (Hashtable) mainUserModelHashtableEnumElements.nextElement();
            if(!fieldname.equalsIgnoreCase("Subtype-of")&&!fieldname.equalsIgnoreCase("type")) continue;
            // Element fieldElement = doc2.createElement(fieldname);
            // userModelElement.appendChild(fieldElement);
            
            Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
            Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
            while (fieldnameHashtableEnumKeys.hasMoreElements()) {
                String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
                // Hashtable entitynameHashtable = (Hashtable) fieldnameHashtableEnumElements.nextElement();
                
                
                NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entityname);
                if(entityname.equalsIgnoreCase("Data Base")||entityname.equalsIgnoreCase("Basic-entity-types")) continue;
                if(nodeVector.size() != 6){
                    // entityname = convertToClassName(entityname);
                    
                    String ctcn=convertToClassName(Mpiro.win.struc.getParents(entityname).elementAt(0).toString());
                    Element propert =(Element) alltypes.get(ctcn);//other parents?
                    //       fieldElement.appendChild(entityElement);
                    //if(!fieldname.equalsIgnoreCase("type")){
                    //  Element propert=(Element) entityElement.getFirstChild();
                    //Element Property =doc2.createElement("owlnl:Property");
                    //propert.appendChild(Property);
                    //Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(fieldname, entityname);
                    Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                    Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                    //Enumeration entityNameKeys = entitynameHashtable.keys();
                    // Enumeration entityNameVectors = entitynameHashtable.elements();
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
                        
                        Element Interest = doc2.createElement("owlnl:IInterest");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+key);
                        Element forInstance=doc2.createElement("owlnl:forInstance");
                        forInstance.setAttribute("rdf:resource",uri+'#'+entityname);
                        Element InterestValue=doc2.createElement("owlnl:InterestValue");
                        InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                        Interest.appendChild(forInstance);
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                        //   for(int i = 0; i<valueVector.size();i++){
                        //     Element valueElement = doc.createElement("value");
                        //   valueElement.appendChild(doc.createTextNode((String)valueVector.get(i)));
                        // keyElement.appendChild(valueElement);
                        //}
                    }//}
        /*        else{
         
                    Element classes=(Element) entityElement.getLastChild();
                Element cl =doc2.createElement("owlnl:owlClass");
                classes.appendChild(cl);
                Vector data=nodeVector.independentFieldsVector;
                data=(Vector) data.elementAt(1);
                cl.setAttribute("rdf:about",data.elementAt(1).toString());//change fieldname!!!!!!!!
                Enumeration entityNameKeys = entitynameHashtable.keys();
                Enumeration entityNameVectors = entitynameHashtable.elements();
                while(entityNameKeys.hasMoreElements()){
                    String key = (String)entityNameKeys.nextElement();
                    Vector valueVector = (Vector)entityNameVectors.nextElement();
         
                    Element Interest = doc2.createElement("owlnl:Interest");
                    cl.appendChild(Interest);
                    Interest.setAttribute("rdf:parseType","Resource");
                    Element forUserType=doc2.createElement("owlnl:forUserType");
                    forUserType.setAttribute("rdf:resource",key);
                    Element InterestValue=doc2.createElement("owlnl:InterestValue");
                    InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                    Interest.appendChild(forUserType);
                    Interest.appendChild(InterestValue);
         
         
                }}*/
                } else{
                    entityname = convertToClassName(entityname);
                    Element propert =(Element) alltypes.get(entityname);
                    //    Element propert=(Element) entityElement.getFirstChild();
                    // Element Property =doc2.createElement("owlnl:Property");
                    // propert.appendChild(Property);
                    // Property.setAttribute("rdf:about",uri+'/'+fieldname);
                    //  Enumeration entityNameKeys = entitynameHashtable.keys();
                    //  Enumeration entityNameVectors = entitynameHashtable.elements();
                    Vector modelling_values=Mpiro.win.struc.getUserModelling(fieldname, entityname);
                    Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                    Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                    while(entityNameKeys.hasMoreElements()){
                        String key = (String)entityNameKeys.nextElement();
                        Vector valueVector = (Vector)entityNameVectors.nextElement();
                        
                        Element Interest = doc2.createElement("owlnl:DInterest");
                        propert.appendChild(Interest);
                        Interest.setAttribute("rdf:parseType","Resource");
                        Element forUserType=doc2.createElement("owlnl:forUserType");
                        forUserType.setAttribute("rdf:resource",base+"#"+key);
                        Element InterestValue=doc2.createElement("owlnl:InterestValue");
                        InterestValue.appendChild(doc2.createTextNode(valueVector.get(0).toString()));
                        Interest.appendChild(forUserType);
                        Interest.appendChild(InterestValue);
                    }
                }
            }
            
        }
        
        writer.setOutput(output2,null);
        writer.write(doc2);
       /*     Enumeration microPlanKeysEnu = microPlanningValues.keys();
            Enumeration microPlanElementsEnu = microPlanningValues.elements();
        
            while (microPlanKeysEnu.hasMoreElements()) {
                String microPlanKeyValue = (String) microPlanKeysEnu.nextElement();
                String microPlanElementValue = (String) microPlanElementsEnu.nextElement();
                String[] values=microPlanKeyValue.split(":");
                if(usersVector.contains(values[2])){
                    Element MicroplanApprop=doc2.createElement("owlnl:MicroplanApprop");
                    MicroplanApprop.setAttribute("rdf:about",microPlanKeyValue);
                }
        
                Element microValueElement = doc.createElement("MicroValue");
                microValueElement.setAttribute("name", microPlanKeyValue);
             //   microValuesElement.appendChild(microValueElement);
                microValueElement.appendChild(doc.createTextNode(microPlanElementValue));
            }*/
        
        
        
        
        
        
        
        
        
        
        
        
        
        
  /*       Hashtable greekNouns= (Hashtable) nounHashTable.get("Greek");
            Element curNounElement1 = doc1.createElement("OntClass");
            curNounElement1.setAttribute("name",nounName);//nounName to be replaced with namespace!!!!!
            nounsElement1.appendChild(curNounElement1);
            Element NP1 =doc1.createElement("NP");
            NP1.setAttribute("gender",greekNouns.get("grgender").toString().toLowerCase());
            if (greekNouns.get("grgender").toString().equalsIgnoreCase("Neuter"))
                NP1.setAttribute("gender","nonpersonal");
          //  NP1.setAttribute("gender","nonpersonal");//!!!!!!!!! to be replaced by gender of datatype
            NP1.setAttribute("num","singular");//!!!!!!!!! to be replaced by number of datatype
            if (greekNouns.get("countable").toString().equalsIgnoreCase("No"))
                NP1.setAttribute("countable","F");
            else
                NP1.setAttribute("countable","T");
            Element singular1=doc1.createElement("singular");
            Element plural1=doc1.createElement("plural");
            Element nomsin=doc1.createElement("nominative");
            Element nomplu=doc1.createElement("nominative");
            Element gensin=doc1.createElement("genitive");
            Element genplu=doc1.createElement("genitive");
            Element accsin=doc1.createElement("accusative");
            Element accplu=doc1.createElement("accusative");
            nomsin.appendChild(doc1.createTextNode(greekNouns.get("grbasetext").toString()));
            nomplu.appendChild(doc1.createTextNode(greekNouns.get("grpluraltext").toString()));
            genplu.appendChild(doc1.createTextNode(greekNouns.get("grpgtext").toString()));
            gensin.appendChild(doc1.createTextNode(greekNouns.get("grsgtext").toString()));
            accplu.appendChild(doc1.createTextNode(greekNouns.get("grpatext").toString()));
            accsin.appendChild(doc1.createTextNode(greekNouns.get("grsatext").toString()));
            singular1.appendChild(nomsin);
            singular1.appendChild(gensin);
            singular1.appendChild(accsin);
            plural1.appendChild(nomplu);
            plural1.appendChild(genplu);
            plural1.appendChild(accplu);
            NP1.appendChild(singular1);
            NP1.appendChild(plural1);
            curNounElement1.appendChild(NP1);
   
   
          /*  singular.appendChild(doc.createTextNode(englishNouns.get("enbasetext").toString()));
            plural.appendChild(doc.createTextNode(englishNouns.get("enpluraltext").toString()));
            NP.appendChild(singular);
            NP.appendChild(plural);
            curNounElement.appendChild(NP);
   */
     /*      File xmlFile2 = new File(fileName+"greek"+".xml");
        FileOutputStream output2 = new FileOutputStream(xmlFile2);
         Writer writer1 = new Writer();
        writer1.setOutput(output2, null);
        writer1.write(doc1);*/
        
    /*        Enumeration nounFieldsCatNameEnu = nounHashTable.keys();
            Enumeration nounFieldsCatHashTableEnu = nounHashTable.elements();
            while (nounFieldsCatNameEnu.hasMoreElements()) {
                String fieldCatName = (String) nounFieldsCatNameEnu.nextElement();
                Hashtable fieldCatHashTable = (Hashtable) nounFieldsCatHashTableEnu.nextElement();
     
                Element fieldCatElement = doc.createElement(fieldCatName);
                curNounElement.appendChild(fieldCatElement);
     
                Enumeration nounFieldsNameEnu = fieldCatHashTable.keys();
                while (nounFieldsNameEnu.hasMoreElements()) {
                    String fieldName = (String) nounFieldsNameEnu.nextElement();
                    Element fieldElement = doc.createElement(fieldName);
     
                    String value = (String) fieldCatHashTable.get(fieldName);
                    if (value != null)
                        fieldElement.appendChild(doc.createTextNode(value));
     
                    fieldCatElement.appendChild(fieldElement);
                }
            }*/
    }
    
    
    
    
    
    public static void exportLexiconToXmlFile(String fileName) throws Exception {
        File xmlFile = new File(fileName);
        FileOutputStream output = new FileOutputStream(xmlFile);
        
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("MpiroData");
        doc.appendChild(root);
        
        //export Lexicon
        Element lexiconElement = doc.createElement("Lexicon");
        root.appendChild(lexiconElement);
        Element nounsElement = doc.createElement("Noun");
        Element verbsElement = doc.createElement("Verb");
        lexiconElement.appendChild(nounsElement);
        lexiconElement.appendChild(verbsElement);
        
        Hashtable allNounHashTable =  Mpiro.win.struc.getNounsHashtable();
        Hashtable allVerbHashTable = Mpiro.win.struc.getVerbsHashtable();
        
        //nouns
        Enumeration nounNameEnu = allNounHashTable.keys();
        Enumeration nounHashTableEnu = allNounHashTable.elements();
        
        while (nounNameEnu.hasMoreElements()) {
            String nounName = (String) nounNameEnu.nextElement();
            Hashtable nounHashTable = (Hashtable) nounHashTableEnu.nextElement();
            
            Element curNounElement = doc.createElement(nounName);
            nounsElement.appendChild(curNounElement);
            
            Enumeration nounFieldsCatNameEnu = nounHashTable.keys();
            Enumeration nounFieldsCatHashTableEnu = nounHashTable.elements();
            while (nounFieldsCatNameEnu.hasMoreElements()) {
                String fieldCatName = (String) nounFieldsCatNameEnu.nextElement();
                Hashtable fieldCatHashTable = (Hashtable) nounFieldsCatHashTableEnu.nextElement();
                
                Element fieldCatElement = doc.createElement(fieldCatName);
                curNounElement.appendChild(fieldCatElement);
                
                Enumeration nounFieldsNameEnu = fieldCatHashTable.keys();
                while (nounFieldsNameEnu.hasMoreElements()) {
                    String fieldName = (String) nounFieldsNameEnu.nextElement();
                    Element fieldElement = doc.createElement(fieldName);
                    
                    String value = (String) fieldCatHashTable.get(fieldName);
                    if (value != null)
                        fieldElement.appendChild(doc.createTextNode(value));
                    
                    fieldCatElement.appendChild(fieldElement);
                }
            }
        }
        
        //verbs
        Enumeration verbNameEnu = allVerbHashTable.keys();
        Enumeration verbHashTableEnu = allVerbHashTable.elements();
        
        while (verbNameEnu.hasMoreElements()) {
            String verbName = (String) verbNameEnu.nextElement();
            Hashtable verbHashTable = (Hashtable) verbHashTableEnu.nextElement();
            
            Element curVerbElement = doc.createElement(verbName);
            verbsElement.appendChild(curVerbElement);
            
            Enumeration verbFieldsCatNameEnu = verbHashTable.keys();
            Enumeration verbFieldsCatHashTableEnu = verbHashTable.elements();
            while (verbFieldsCatNameEnu.hasMoreElements()) {
                String fieldCatName = (String) verbFieldsCatNameEnu.nextElement();
                Hashtable fieldCatHashTable = (Hashtable) verbFieldsCatHashTableEnu.nextElement();
                
                Element fieldCatElement = doc.createElement(fieldCatName);
                curVerbElement.appendChild(fieldCatElement);
                
                Enumeration verbFieldsNameEnu = fieldCatHashTable.keys();
                while (verbFieldsNameEnu.hasMoreElements()) {
                    String fieldName = (String) verbFieldsNameEnu.nextElement();
                    
                    if (!fieldName.equals("vTable") && !fieldName.equals("pTable")) {
                        Element fieldElement = doc.createElement(fieldName);
                        String value = (String) fieldCatHashTable.get(fieldName);
                        if (value != null)
                            fieldElement.appendChild(doc.createTextNode(value));
                        
                        fieldCatElement.appendChild(fieldElement);
                    } else {
                        Vector verbTable = (Vector) fieldCatHashTable.get(fieldName);
                        for (int i = 0; i < verbTable.size(); i++) {
                            Vector lexiconDefaultVector = (Vector) verbTable.get(i);
                            Element fieldElement = doc.createElement(fieldName);
                            fieldCatElement.appendChild(fieldElement);
                            for (int j = 0; j < lexiconDefaultVector.size(); j++) {
                                
                                fieldElement.setAttribute("attr" + (j + 1), lexiconDefaultVector.get(j).toString());
                            }
                        }
                    }
                }
            }
        } //end export Lexicon
        
        //export EntityType Nouns && Microplanning Values
        Element entityTypeNouns = doc.createElement("EntityType-Nouns");
        lexiconElement.appendChild(entityTypeNouns);
        
        //microplanning root element
        Element microPlanElement = doc.createElement("Microplanning");
        root.appendChild(microPlanElement);
        
        Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        // Remove the 2 entries that are not needed
        allEntityTypes.remove("Data Base");
        allEntityTypes.remove("Basic-entity-types");
        
        Enumeration allEntityTypeNames;
        Enumeration allEntityTypeParentNames;
        
        allEntityTypeNames = allEntityTypes.keys();
        allEntityTypeParentNames = allEntityTypes.elements();
        while (allEntityTypeNames.hasMoreElements()) {
            //export EntityType Nouns
            String entityTypeName = allEntityTypeNames.nextElement().toString();
            NodeVector nodeEntityType = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
            Vector nounVector = (Vector) nodeEntityType.get(2);
            
            Element entityTypeElement = doc.createElement("Entity-Type");
            entityTypeElement.setAttribute("name", convertToClassName(entityTypeName));
            entityTypeNouns.appendChild(entityTypeElement);
            
            for (int i = 0; i < nounVector.size(); i++) {
                Element nounElement = doc.createElement("Noun");
                entityTypeElement.appendChild(nounElement);
                nounElement.setAttribute("name", nounVector.get(i).toString());
            }
            //end export EntityType Nouns
            
            //export Microplanning values
            Element currentEntityTypeElement = doc.createElement(convertToClassName(entityTypeName));
            Element microValuesElement = doc.createElement("Microplanning-Values");
            currentEntityTypeElement.appendChild(microValuesElement);
            microPlanElement.appendChild(currentEntityTypeElement);
            
            Hashtable microPlanningValues = (Hashtable) nodeEntityType.get(5);
            Enumeration microPlanKeysEnu = microPlanningValues.keys();
            Enumeration microPlanElementsEnu = microPlanningValues.elements();
            
            while (microPlanKeysEnu.hasMoreElements()) {
                String microPlanKeyValue = (String) microPlanKeysEnu.nextElement();
                String microPlanElementValue = (String) microPlanElementsEnu.nextElement();
                
                Element microValueElement = doc.createElement("MicroValue");
                microValueElement.setAttribute("name", microPlanKeyValue);
                microValuesElement.appendChild(microValueElement);
                microValueElement.appendChild(doc.createTextNode(microPlanElementValue));
            }
            
            
            //export template values
            Element templateElement = doc.createElement("Template-Values");
            currentEntityTypeElement.appendChild(templateElement);
            
            Element englishValuesElement = doc.createElement("English-Values");
            Element italianValuesElement = doc.createElement("Italian-Values");
            Element greekValuesElement = doc.createElement("Greek-Values");
            templateElement.appendChild(englishValuesElement);
            templateElement.appendChild(italianValuesElement);
            templateElement.appendChild(greekValuesElement);
            
            TemplateVector templateVector = (TemplateVector) nodeEntityType.get(4);
            Hashtable englishValues = (Hashtable) templateVector.get(0);
            Hashtable italianValues = (Hashtable) templateVector.get(1);
            Hashtable greekValues = (Hashtable) templateVector.get(2);
            
            Enumeration englishKeysEnu = englishValues.keys();
            Enumeration englishElementsEnu = englishValues.elements();
            
            while (englishKeysEnu.hasMoreElements()) {
                String keyValue = (String) englishKeysEnu.nextElement();
                Object elementValue = (Object) englishElementsEnu.nextElement();
                
                Element templateValueElement = doc.createElement("TemplateValue");
                templateValueElement.setAttribute("name", keyValue);
                englishValuesElement.appendChild(templateValueElement);
                if (elementValue.getClass().getName().equals("java.lang.String")) {
                    templateValueElement.appendChild(doc.createTextNode( (String) elementValue));
                } else {
                    Vector elementVector = (Vector) elementValue;
                    for (int i = 0; i < elementVector.size(); i++) {
                        Element hashTableElement = doc.createElement("HashTable");
                        templateValueElement.appendChild(hashTableElement);
                        Enumeration elementVectorKeys = ( (Hashtable) elementVector.get(i)).keys();
                        Enumeration elementVectorValues = ( (Hashtable) elementVector.get(i)).elements();
                        while (elementVectorKeys.hasMoreElements()) {
                            String key = (String) elementVectorKeys.nextElement();
                            String value = (String) elementVectorValues.nextElement();
                            
                            //  System.out.println("********** key:   "+key);
                            //  System.out.println("********** value:   "+value);
                            
                            Element hashValueElement = doc.createElement(key);
                            hashValueElement.appendChild(doc.createTextNode(value));
                            hashTableElement.appendChild(hashValueElement);
                        }
                    }
                }
            }
            
            Enumeration italianKeysEnu = italianValues.keys();
            Enumeration italianElementsEnu = italianValues.elements();
            
            while (italianKeysEnu.hasMoreElements()) {
                String keyValue = (String) italianKeysEnu.nextElement();
                Object elementValue = (Object) italianElementsEnu.nextElement();
                
                Element templateValueElement = doc.createElement("TemplateValue");
                templateValueElement.setAttribute("name", keyValue);
                italianValuesElement.appendChild(templateValueElement);
                if (elementValue.getClass().getName().equals("java.lang.String")) {
                    templateValueElement.appendChild(doc.createTextNode( (String) elementValue));
                } else {
                    Vector elementVector = (Vector) elementValue;
                    for (int i = 0; i < elementVector.size(); i++) {
                        Element hashTableElement = doc.createElement("HashTable");
                        templateValueElement.appendChild(hashTableElement);
                        Enumeration elementVectorKeys = ( (Hashtable) elementVector.get(i)).keys();
                        Enumeration elementVectorValues = ( (Hashtable) elementVector.get(i)).elements();
                        while (elementVectorKeys.hasMoreElements()) {
                            String key = (String) elementVectorKeys.nextElement();
                            String value = (String) elementVectorValues.nextElement();
                            
                            Element hashValueElement = doc.createElement(key);
                            hashValueElement.appendChild(doc.createTextNode(value));
                            hashTableElement.appendChild(hashValueElement);
                        }
                    }
                }
            }
            
            Enumeration greekKeysEnu = greekValues.keys();
            Enumeration greekElementsEnu = greekValues.elements();
            
            while (greekKeysEnu.hasMoreElements()) {
                String keyValue = (String) greekKeysEnu.nextElement();
                Object elementValue = (Object) greekElementsEnu.nextElement();
                
                Element templateValueElement = doc.createElement("TemplateValue");
                templateValueElement.setAttribute("name", keyValue);
                greekValuesElement.appendChild(templateValueElement);
                if (elementValue.getClass().getName().equals("java.lang.String")) {
                    templateValueElement.appendChild(doc.createTextNode( (String) elementValue));
                } else {
                    Vector elementVector = (Vector) elementValue;
                    for (int i = 0; i < elementVector.size(); i++) {
                        Element hashTableElement = doc.createElement("HashTable");
                        templateValueElement.appendChild(hashTableElement);
                        Enumeration elementVectorKeys = ( (Hashtable) elementVector.get(i)).keys();
                        Enumeration elementVectorValues = ( (Hashtable) elementVector.get(i)).elements();
                        while (elementVectorKeys.hasMoreElements()) {
                            String key = (String) elementVectorKeys.nextElement();
                            String value = (String) elementVectorValues.nextElement();
                            
                            Element hashValueElement = doc.createElement(key);
                            hashValueElement.appendChild(doc.createTextNode(value));
                            hashTableElement.appendChild(hashValueElement);
                        }
                    }
                }
            }
            //end export Microplanning values
        }
        
        //export Users
        Element usersElement = doc.createElement("Users");
        root.appendChild(usersElement);
        
        Enumeration userNamesEnu = Mpiro.win.struc.getUserNames();
        Enumeration userValuesEnu = Mpiro.win.struc.getUserElements();
        
        while (userNamesEnu.hasMoreElements()) {
            String userName = (String) userNamesEnu.nextElement();
            Vector userVector = (Vector) userValuesEnu.nextElement();
            
            Element currentUserElement = doc.createElement(userName);
            usersElement.appendChild(currentUserElement);
            
            Element sentenceElement = doc.createElement("Sentence-Length");
            sentenceElement.appendChild(doc.createTextNode(userVector.get(0).toString()));
            Element paragraphElement = doc.createElement("Paragraph-Length");
            paragraphElement.appendChild(doc.createTextNode(userVector.get(1).toString()));
            Element conjunctionElement = doc.createElement("Link-Number");
            conjunctionElement.appendChild(doc.createTextNode(userVector.get(2).toString()));
            Element voiceElement = doc.createElement("Voice-Type");
            voiceElement.appendChild(doc.createTextNode(userVector.get(3).toString()));
            
            currentUserElement.appendChild(sentenceElement);
            currentUserElement.appendChild(paragraphElement);
            currentUserElement.appendChild(conjunctionElement);
            currentUserElement.appendChild(voiceElement);
        } //end export users
        
        //export user model
        Element userModelElement = doc.createElement("UserModel");
        root.appendChild(userModelElement);
        //QueryProfileHashtable.mainUserModelHashtable.remove("dddd'");
        Enumeration mainUserModelHashtableEnumKeys = Mpiro.win.struc.mainUserModelHashtableKeys();
        Enumeration mainUserModelHashtableEnumElements = Mpiro.win.struc.mainUserModelHashtableElements();
        while (mainUserModelHashtableEnumKeys.hasMoreElements()) {
            String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
            Hashtable fieldnameHashtable = (Hashtable) mainUserModelHashtableEnumElements.nextElement();
            
            Element fieldElement = doc.createElement(fieldname);
            userModelElement.appendChild(fieldElement);
            
            Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
            Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
            while (fieldnameHashtableEnumKeys.hasMoreElements()) {
                String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
                // Hashtable entitynameHashtable = (Hashtable) fieldnameHashtableEnumElements.nextElement();
                
                
                NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entityname);
                //if entityname is an entityType
                //if(nodeVector == null) continue;
                //     if (entityname.equalsIgnoreCase("Generic-Fibula"))
                //   System.out.println("fffff"+entityname);
                if(nodeVector.size() == 6)
                    entityname = convertToClassName(entityname);
                
                Element entityElement = doc.createElement(entityname);
                fieldElement.appendChild(entityElement);
                
                Vector modelling_values=Mpiro.win.struc.getUserModelling(fieldname, entityname);
                Enumeration entityNameKeys=(Enumeration)modelling_values.elementAt(0);
                Enumeration entityNameVectors=(Enumeration)modelling_values.elementAt(1);
                // Enumeration entityNameKeys = entitynameHashtable.keys();
                // Enumeration entityNameVectors = entitynameHashtable.elements();
                while(entityNameKeys.hasMoreElements()){
                    String key = (String)entityNameKeys.nextElement();
                    Vector valueVector = (Vector)entityNameVectors.nextElement();
                    
                    Element keyElement = doc.createElement(key);
                    entityElement.appendChild(keyElement);
                    for(int i = 0; i<valueVector.size();i++){
                        Element valueElement = doc.createElement("value");
                        valueElement.appendChild(doc.createTextNode((String)valueVector.get(i)));
                        keyElement.appendChild(valueElement);
                    }
                }
            }
        }
        
/*        //export user model story ??????
        Element userModelStoryElement = doc.createElement("UserModelStory");
        root.appendChild(userModelStoryElement);
 
        Enumeration mainUserModelStoryHashtableEnumKeys = QueryProfileHashtable.mainUserModelStoryHashtable.keys();
        Enumeration mainUserModelStoryHashtableEnumElements = QueryProfileHashtable.mainUserModelStoryHashtable.elements();
        while (mainUserModelStoryHashtableEnumKeys.hasMoreElements()){
            String entityName =  (String)mainUserModelStoryHashtableEnumKeys.nextElement();
            Hashtable entityHashTable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
 
            NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entityName);
            //if entityname is an entityType
            if(nodeVector!=null && nodeVector.size() == 6)
                entityName = convertToClassName(entityName);
 
 
            Element entityElement = doc.createElement("Entity");
            entityElement.setAttribute("name", entityName);
            userModelStoryElement.appendChild(entityElement);
 
            Enumeration entityHashTableKeys = entityHashTable.keys();
            Enumeration entityHashTableValues = entityHashTable.elements();
            while(entityHashTableKeys.hasMoreElements()){
                String entityHashTableKey = (String)entityHashTableKeys.nextElement();
                Hashtable entityHashTableValue = (Hashtable)entityHashTableValues.nextElement();
 
                Element valueElement = doc.createElement(entityHashTableKey);
                entityElement.appendChild(valueElement);
 
 
                //valueElement.appendChild(doc.createTextNode(entityHashTableValue));
 
            }
        }//end user model story
 */
        //export Options
        Element optionsElement = doc.createElement("Options");
        root.appendChild(optionsElement);
        
        Enumeration optionKeys = Mpiro.win.struc.mainOptionsHashtableKeys();
        Enumeration optionElements = Mpiro.win.struc.mainOptionsHashtableElements();
        while(optionKeys.hasMoreElements()){
            String key = (String)optionKeys.nextElement();
            Object element = optionElements.nextElement();
            
            Element optionElement = doc.createElement(key);
            optionsElement.appendChild(optionElement);
            if(element.getClass().getName().equals("java.lang.String")){
                optionElement.appendChild(doc.createTextNode((String)element));
            } else{
                for(int i = 0;i<((Vector)element).size();i++){
                    Element value = doc.createElement("Value");
                    value.appendChild(doc.createTextNode((String)((Vector)element).get(i)));
                    optionElement.appendChild(value);
                }
            }
        }
        
        Writer writer = new Writer();
        writer.setOutput(output, null);
        writer.write(doc);
        
    }
    
    public static Hashtable exportUserCharacteristics(String uri) throws IOException{
        if(!uri.endsWith("#"))
            uri=uri+"#";
        Hashtable result=new Hashtable();
        FileWriter fstream = new FileWriter("robotsChar.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        
        Object[] usertypes=Mpiro.win.struc.getRobotNamesToArray();
        Enumeration keys=Mpiro.win.struc.robotCharValuesHashtableKeys();
        Enumeration elements=Mpiro.win.struc.robotCharValuesHashtableElements();
        
        while(keys.hasMoreElements()){
            String nextKey=(String)keys.nextElement();
            String name="";
            String property="";
            if(nextKey.split(":").length==1){
                if (nextKey.equals("Thing"))
                    name="Class:"+OWL.Thing.getURI();
                else if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").containsKey(nextKey))
                    name="Individual:"+uri+nextKey;
                else
                    name="Class:"+uri+convertToClassName(nextKey);
            } else if(nextKey.split(":").length==2){
                String[] prop=nextKey.split(":");
                if(Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity").containsKey(prop[0]))
                    name="Individual:"+uri+prop[0];
                else
                    name="Class:"+uri+convertToClassName(prop[0]);
                
                property="Property:"+uri+convertToPropertyName(prop[1]);
            }
            
            
            Hashtable element=(Hashtable)elements.nextElement();
            Enumeration robottypes=element.keys();
            Enumeration values=element.elements();
            while(robottypes.hasMoreElements()){
                String robottype=(String)robottypes.nextElement();
                Vector valuesVec=(Vector)values.nextElement();
                boolean universal=false;
                for(int k=0;k<Mpiro.win.struc.getRobotCharVectorSize();k++){
                    if(((Vector)Mpiro.win.struc.getRobotCharVectorElementAt(k)).elementAt(0).toString().equalsIgnoreCase(robottype)){
                        universal=(Boolean)((Vector)Mpiro.win.struc.getRobotCharVectorElementAt(k)).elementAt(2);
                        break;
                    }}
                
                for(int i=0;i<valuesVec.size();i++){
                    if(!universal){
                        if(!((String)valuesVec.elementAt(i)).equals("-1")&&!((String)valuesVec.elementAt(i)).equals("")){
                            if(nextKey.contains(":")){
                                result.put(property+":"+robottype+":"+(String)usertypes[i]+":"+name, (String)valuesVec.elementAt(i));
                                out.write(property+":"+robottype+":"+(String)usertypes[i]+":"+name+":"+(String)valuesVec.elementAt(i));
                            }else{
                                result.put(name+":"+robottype+":"+(String)usertypes[i], (String)valuesVec.elementAt(i));
                                out.write(name+":"+robottype+":"+(String)usertypes[i]+":"+(String)valuesVec.elementAt(i));}
                            out.newLine();
                        }} else{
                        if(!((String)valuesVec.elementAt(i)).equals("-1")&&!((String)valuesVec.elementAt(i)).equals("")){
                            if(nextKey.contains(":")){
                                result.put(property+":"+robottype+":"+"universal"+":"+name, (String)valuesVec.elementAt(i));
                                out.write(property+":"+robottype+":"+"universal"+":"+name+":"+(String)valuesVec.elementAt(i));} else{
                                result.put(name+":"+robottype+":"+"universal", (String)valuesVec.elementAt(i));
                                out.write(name+":"+robottype+":"+"universal"+":"+(String)valuesVec.elementAt(i));}
                            out.newLine();
                        }
                        
                        break;
                        }}
                
            }}
        out.close();
        return result;
    }
    
    
    public static String getNSFor(String name, String mpiroNS) {
        if(name.matches("(?i).*http://.*"))
            return name;
        else
            return (mpiroNS + name);
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
