/***************

<p>Title: </p>

<p>Description:
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/


package gr.demokritos.iit.eleon.authoring;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.util.iterator.*;
import gr.demokritos.iit.eleon.profiles.Robot;
import gr.demokritos.iit.eleon.profiles.User;

import gr.demokritos.iit.eleon.ui.Equivalent;
import gr.demokritos.iit.eleon.ui.StoriesPanel;
import gr.demokritos.iit.eleon.ui.UsersPanel;

import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.tree.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class OwlImport {
    static DialogClassesToImport dialogClassesToImport;
    static DialogImportOwlURLs dialogImportOwlURLs;
    static String mpiroPath;
    static boolean owlFromMpiro;
    static Hashtable classToTreeNode;//a hashtable for finding easy the treenode that corresponds to a class
    
    public static boolean ImportFromOwlFile(File rdfFile, boolean readXMLFile) throws Exception {
        //System.out.println("0.01");
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        ontModel.add(OWL.Thing, RDF.type, OWL.Class);
        
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(rdfFile);
        NodeList nodeList = doc.getElementsByTagName("rdf:RDF");
        NamedNodeMap nameNodeMap = nodeList.item(0).getAttributes();
        
        //get the Base URL of the ontology
        if (nameNodeMap.getNamedItem("xml:base") != null){//System.out.println("ssssssss");
            mpiroPath = nameNodeMap.getNamedItem("xml:base").getNodeValue();
           // System.out.println("if"+nameNodeMap.getNamedItem("xml:base").toString());
        } else{
            if(nameNodeMap.getNamedItem("xmlns") != null)
                mpiroPath = nameNodeMap.getNamedItem("xmlns").getNodeValue();
            else  mpiroPath = "http://www.w3.org/#";
            }
        Mpiro.win.struc.setBaseURI(mpiroPath);
        
//System.out.println("0.04");
        ontModel.getSpecification().getDocumentManager().addAltEntry(mpiroPath, "file:" + rdfFile.getAbsolutePath());
        
        //get import URLs
        nodeList = doc.getElementsByTagName("owl:imports");
        
        //if there exist imports show the dialog for the ontologies paths
        if (nodeList.getLength() > 0) {
            DialogImportOwlURLs.urlNum = nodeList.getLength();
            dialogImportOwlURLs = new DialogImportOwlURLs(Mpiro.win.getFrames()[0], "Import URLs", true);
            for (int i = 0; i < nodeList.getLength(); i++) {
                nameNodeMap = nodeList.item(i).getAttributes();
                dialogImportOwlURLs.urls[i].setText(nameNodeMap.getNamedItem("rdf:resource").getNodeValue());
            }
            //show dialog for the import ontologies
            dialogImportOwlURLs.show();
            if (DialogImportOwlURLs.modalResult) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (!DialogImportOwlURLs.filePaths[i].getText().equalsIgnoreCase("Set file path") && !DialogImportOwlURLs.filePaths[i].getText().equalsIgnoreCase("��������� �� �������� ��� �������"))
                        ontModel.getSpecification().getDocumentManager().addAltEntry(DialogImportOwlURLs.urls[i].getText(),
                                "file:" + DialogImportOwlURLs.filePaths[i].getText());
                }
            } else
                return false;
        }
        
        //read the OWL file
        ontModel.read(mpiroPath, "RDF/XML-ABBREV");
//System.out.println("0.05");
        //get allclasses
        ExtendedIterator classesList = ontModel.listClasses();
        
        classesList.next();
        
        //initialize checkbox tree
        classToTreeNode = new Hashtable();//initialize the hashtable
        DialogClassesToImport.clearTreeModel();
        owlFromMpiro = false;
        
        //    if (ontModel.getOntClass(mpiroPath + "Basic-Entity-Types") != null)
        //        owlFromMpiro = true;
        
        if (owlFromMpiro) { //MPIRO created file
            for (Iterator i = rootClasses(ontModel); i.hasNext(); ) {
                OntClass basicOntClass = (OntClass) i.next();
                addClassToTree(DialogClassesToImport.root, basicOntClass, new ArrayList(), 0);
            }
        } else { //not MPIRO created file
            //create an arraylist with all the parent classes
            ArrayList parentClasses = new ArrayList();
            OntClass ontNode = null;
            for (Iterator i = ontModel.listNamedClasses(); i.hasNext(); ) {
                ontNode = (OntClass) i.next();
                if (ontNode.listSubClasses().hasNext()) {
                    parentClasses.add(ontNode);
                }
            }
            
            //find the classes with no father to be inserted in the first level of the tree
            for (Iterator i = ontModel.listNamedClasses(); i.hasNext(); ) {
                ontNode = (OntClass) i.next();
                if (classIsAChild(ontNode, parentClasses)) {
                    //do nothing
                } else { //if a class is not a child (has no parent) add it to the tree as a basic entity type
                    addClassToTree(DialogClassesToImport.root, ontNode, new ArrayList(), 0);
                }
            }
        }
        
        /** Show the sub-class hierarchy encoded by the given model */
        dialogClassesToImport = new DialogClassesToImport(Mpiro.win.getFrames()[0], "OWL Clsses", true);
        if (!dialogClassesToImport.modalResult) {
            return dialogClassesToImport.modalResult;
        }

        DataBasePanel.clearTree();
        LexiconPanel.clearTree();
        StoriesPanel.clearTree();
        UsersPanel.clearTree();
        //intitialize new domain
        Mpiro.win.clearDomain();
        Mpiro.win.initializeDomain();
//        QueryLexiconHashtable.createMainLexiconHashtable();
//        Mpiro.win.struc.createBasicEntityType("type", "Data Base");
//        Mpiro.win.struc.createBasicEntityType("Data Base", "Basic-entity-types");
//        Mpiro.win.struc.createDefaultUpperVector();
//        
        //clear all trees
        
        UsersPanel.users.add(new DefaultMutableTreeNode(new IconData(UsersPanel.ICON_USER, "NewUserType")));
        
        //START IMPORTTING DATA
        //add Basic Types
        DefaultMutableTreeNode basicEntityTypesTreeNode = (DefaultMutableTreeNode) DialogClassesToImport.root;
        
        //add upper model classes in Domain
        if (owlFromMpiro) {
            NodeVector rootVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
            Vector upperVector = (Vector) rootVector.elementAt(1);
            ExtendedIterator upperModelClasses = ontModel.getOntClass(mpiroPath + "Basic-Entity-Types").listSubClasses();
            while (upperModelClasses.hasNext()) {
                OntClass upperModelClass = (OntClass) upperModelClasses.next();
                String upperName = getNameInELEON(upperModelClass).substring(3);
                boolean exists = false;
                for (int i = 0; i < upperVector.size(); i++) {
                    if ( ( (ListData) upperVector.get(i)).m_name.equals(upperName)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists)
                    upperVector.add(new ListData(upperName));
            }
        }
        
        ExtendedIterator properties=ontModel.listOntProperties();
        //ExtendedIterator temp1=ontModel.listObjectProperties();
//getProperties(properties);
        while(properties.hasNext()){
            OntProperty p=(OntProperty)properties.next();
            
            //check the fillers to understand if it is datatype or objecttype
               
                if((!p.isObjectProperty())&&(!p.isDatatypeProperty())&&(!p.isAnnotationProperty())){
                boolean isObjectProp=false;
                OntResource dom=p.getRange();
                if(dom.canAs(OntClass.class))
                    isObjectProp=true;
                ExtendedIterator resources=ontModel.listResourcesWithProperty(p);
                
                while(resources.hasNext()){
                    OntResource res=(OntResource)resources.next();
                    if(res.isIndividual()){
                        RDFNode n=((Individual)res).getPropertyValue(p);
                        if(n.canAs(Individual.class))
                            isObjectProp=true;
                          
                    }
                }
                 if(isObjectProp)
         addObjectProperty(p.convertToObjectProperty(), ontModel);
                        else
                            addDatatypeProperty(p.convertToDatatypeProperty(), ontModel);
                }
           
        }
            
        properties=ontModel.listObjectProperties();
          while(properties.hasNext()){
                ObjectProperty nextProp = (ObjectProperty)properties.next();
                addObjectProperty(nextProp, ontModel);
                //System.out.println(getNameInELEON(nextProp));
                //   propertiesHashtableRecord rrrrrrr=new propertiesHashtableRecord();
                
            
        }
        
         properties=ontModel.listDatatypeProperties();
          while(properties.hasNext()){
           
            
                
                DatatypeProperty nextProp = (DatatypeProperty)properties.next();
           
                addDatatypeProperty(nextProp, ontModel);
           
           }
        
        
        //add default user
        Mpiro.win.struc.createDefaultUser("NewUserType");
        Mpiro.win.struc.createDefaultRobot("NewProfile");
        
        
        //add Basic-Entity-Types
        for (int i = 0; i < basicEntityTypesTreeNode.getChildCount(); i++) {
            DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) basicEntityTypesTreeNode.getChildAt(i);
            
            IconOwlData treeObject = ( (IconOwlData) currentTreeNode.getUserObject());
            //if class is selected add it
            if (treeObject.m_icon == DialogClassesToImport.ICON_OWLCLASSCHECKED) {
                NodeVector nv = new NodeVector("Basic-entity-types");
                String nvName = getNameInELEON(treeObject.getOntClass());
                OntClass test = treeObject.getOntClass();
                if (!test.isIntersectionClass()) {
                    if (nvName == null) nvName = treeObject.getOntClass().toString();
                    Mpiro.win.struc.putEntityTypeOrEntityToDB(nvName, nv);
                    ////System.out.println(nvName);
                    
                    //if (!owlFromMpiro) {
                    Mpiro.win.struc.addEntityTypeInUserModelHashtable(nvName);
                    // }
                    
                    //set upper Model types in the new types
                    //if the owl file contains uppre types
                    if (owlFromMpiro) {
                        ExtendedIterator superClasses = treeObject.getOntClass().listSuperClasses(true);
                        while (superClasses.hasNext()) {
                            OntClass upperModelClass = (OntClass) superClasses.next();
                            if (upperModelClass.isAnon())continue;
                            
                            ( (Vector) nv.get(1)).add(getNameInELEON(upperModelClass).substring(3));
                        }
                    } else { //if the class has no upper model type set it to other-abstraction
                        ( (Vector) nv.get(1)).add("other-abstraction");
                    }
                    
                    //add properties
                    createTypeProperties(nv, nvName, treeObject.getOntClass(), null);
                    //add entities
                    createEntities(nvName, treeObject.getOntClass(),"");
                    //add sub classes
                    createSubTypes(currentTreeNode, treeObject.getOntClass(), nv.getDatabaseTableVector(),"");
                }
            }
        }
        //  //System.out.println("110");
        ExtendedIterator intersection = ontModel.listIntersectionClasses();
        while (intersection.hasNext()) {
            boolean approved=true;
            Object intersectionClass = intersection.next();
            
            // IntersectionClass inter1 =ontModel.getIntersectionClass(intersectionClass.toString());
            IntersectionClass inter1= (IntersectionClass) intersectionClass;
            ////System.out.println(getNameInELEON(inter1));
            //ExtendedIterator asta=inter1.listRDFTypes(false);
            //RDFList asta=inter1.getOperands();
            //System.out.println("sdfsdgsdgsdggfsd"+inter1.toString());
            ExtendedIterator asta=inter1.listOperands();
            //System.out.println("inter"+getNameInELEON(inter1));
            multi(inter1,0,null);
            
     /*
      
      
               OntClass notr1=inter1,notr2=inter1;
               //ExtendedIterator asta=inter1.listSuperClasses();
                  //ExtendedIterator asta=inter1.listInstances();
                 // NodeVector nv = new NodeVector(asta.next().toString());
                 // String aaa=inter1.toString()+"oi";
               // Mpiro.win.struc.putEntityTypeOrEntityToDB(aaa, nv);
               // if (!owlFromMpiro)
                //   Mpiro.win.struc.addEntityTypeInUserModelHashtable(aaa);
                int notr=0,res=0;
      
                String range1=null, prop=null;
                //Object proper=null;
                   while (asta.hasNext()) {
      
                       OntClass c = (OntClass) asta.next();
                       //System.out.println(c));
      
                       //System.out.println(notr1));
                       if (c.isRestriction()) {
      
                           Restriction r = c.asRestriction();
                           ////System.out.println(r));
                           if (r.isAllValuesFromRestriction()){
                                 res++;
                               AllValuesFromRestriction av = r.asAllValuesFromRestriction();
                               //System.out.println("AllValuesFrom class " + res + notr+
                                                   av.getAllValuesFrom()) +
                                                  " on property " + av.getOnProperty()));
                               range1=av.getAllValuesFrom());
                               prop= av.getOnProperty());
                               //proper=av.getOnProperty();
      
                           }
                           // while (asta.iterator().hasNext()) {
                           // //System.out.println(asta.iterator().next().toString());
                           if (r.isMaxCardinalityRestriction()){
                                 res++;
                                 MaxCardinalityRestriction restriction = r.asMaxCardinalityRestriction();
                                              if ( restriction.getMaxCardinality() == 1) {
                                                  approved = false;
                                                  prop=restriction.getOnProperty());
                                               range1=  restriction.getOnProperty().getRange());
      
                               //proper=av.getOnProperty();
                                              }
                           }
      
      
                       }
                       else
                       {
                           if(notr1==inter1){
                          notr1=c;
                           notr++;
                           //System.out.print(res);
                           //System.out.println(notr);}
                       else
                       {
                           notr2=c;
                                                      notr++;
                                                      //System.out.print(res);
                           //System.out.println(notr);}
                       }
                       if ( res==1 && notr==1){
      
                       //System.out.print("aaa"+notr1));
                       //System.out.println(getNameInELEON(inter1));
      
                          NodeVector nv = new NodeVector(notr1));
                   //        IconOwlData treeObject = ( (IconOwlData) currentTreeNode1.getUserObject());
                Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(inter1), nv);
      
      
      
                if (!owlFromMpiro)
                    Mpiro.win.struc.addEntityTypeInUserModelHashtable(getNameInELEON(inter1));
      
Vector databaseTableVector = nv.getDatabaseTableVector();
                databaseTableVector.add(new FieldData(prop, range1, approved, ""));
               //OntClass parent=notr1.getSuperClass();
               // NodeVector nv1 = new NodeVector(parent));
               // DefaultMutableTreeNode reee=(DefaultMutableTreeNode) parent;
               // //System.out.println("oopp"+nv1.databaseTableVector.toString());
      
          ////System.out.println( QueryHashtable.mainDBHashtable.
               //OntClass asa=(OntClass)par;
               ExtendedIterator aaaaa=notr1.listDeclaredProperties();
                while (aaaaa.hasNext()) {
               // //System.out.println("oopp"+aaaaa.next());
                OntProperty ioo=(OntProperty)aaaaa.next();
      
                if (!(prop.equalsIgnoreCase(getNameInELEON(ioo)))){
                databaseTableVector.add(new FieldData(getNameInELEON(ioo), ioo.getRange()) , true, ""));
            //System.out.println("oopp"+getNameInELEON(ioo)+prop+ioo.getRange())+ioo.toString());
                }}
            //  Vector nv2=nv1.getDatabaseTableVector().
             //   IconOwlData treeObject = ( (IconOwlData) inter1.getUserObject());
 //               createTypeProperties(nv, inter1.toString(), inter1.asClass(), null);
               // createTypeProperties(nv, treeObject.toString(), treeObject.getOntClass(), inheritedProp);
      
              createEntities(inter1.toString(), inter1.asClass());
               //IconOwlData treeObject2 = ( (IconOwlData) notr1);
            //   notr1.getNode();
DefaultMutableTreeNode ssos=null;
      
      
            for (int i=0;i<basicEntityTypesTreeNode.getChildCount();i++){
      
      
           DefaultMutableTreeNode ssps= (DefaultMutableTreeNode) basicEntityTypesTreeNode.getChildAt(i);
            //System.out.println(ssps.toString());
             //System.out.println(inter1.toString());
           if (ssps.toString().equalsIgnoreCase(getNameInELEON(inter1)))
               ssos=ssps;
      
         }
         try{
             //System.out.println("popopo"+ssos.toString());
             createSubTypes(ssos, inter1.asClass(), nv.getDatabaseTableVector());
         }catch (Exception r){};
      
          }
       if ( res==0 && notr==2){
           NodeVector nv = new NodeVector(notr1));
                   //        IconOwlData treeObject = ( (IconOwlData) currentTreeNode1.getUserObject());
                Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(inter1), nv);
                if (!owlFromMpiro)
                                  Mpiro.win.struc.addEntityTypeInUserModelHashtable(getNameInELEON(inter1));
                              Vector databaseTableVector = nv.getDatabaseTableVector();
                              ExtendedIterator aaaaa=notr1.listDeclaredProperties();
                              ExtendedIterator aaaaa2=notr2.listDeclaredProperties();
                                   while (aaaaa.hasNext()) {
                                  // //System.out.println("oopp"+aaaaa.next());
                                   OntProperty ioo=(OntProperty)aaaaa.next();
                 //  while (aaaaa2.hasNext()) {
                 //      OntProperty ioo2=(OntProperty)aaaaa2.next();
                 //                  if (ioo2).equalsIgnoreCase(getNameInELEON(ioo)))
                 //                      break;
                                   databaseTableVector.add(new FieldData(getNameInELEON(ioo), ioo.getRange()) , true, ""));
                               //System.out.println("oopp"+getNameInELEON(ioo)+prop+ioo.getRange())+ioo.toString());
                                   }//}
                                ExtendedIterator aaaaa21=notr2.listDeclaredProperties();
                                while (aaaaa2.hasNext()) {
                                    OntProperty ioo2 = (OntProperty) aaaaa2.next();
                                     if (!(notr1.hasProperty(ioo2)))
                                    databaseTableVector.add(new FieldData(ioo2), ioo2.getRange()) , true, ""));
      
                                }
      
   //                             createTypeProperties(nv, inter1.toString(), inter1.asClass(), null);
                               // createTypeProperties(nv, treeObject.toString(), treeObject.getOntClass(), inheritedProp);
      
                              createEntities(inter1.toString(), inter1.asClass());
                               //IconOwlData treeObject2 = ( (IconOwlData) notr1);
                            //   notr1.getNode();
                DefaultMutableTreeNode ssos=null;
      
      
                            for (int i=0;i<basicEntityTypesTreeNode.getChildCount();i++){
      
      
                           DefaultMutableTreeNode ssps= (DefaultMutableTreeNode) basicEntityTypesTreeNode.getChildAt(i);
                            //System.out.println(ssps.toString());
                             //System.out.println(inter1.toString());
                           if (ssps.toString().equalsIgnoreCase(getNameInELEON(inter1)))
                               ssos=ssps;
      
                         }
                         try{
                             //System.out.println("popopo"+ssos.toString());
                             createSubTypes(ssos, inter1.asClass(), nv.getDatabaseTableVector());
         }catch (Exception r){};
      
       }
      
      
      }*/
            
        }
        
//System.out.println("111");
        //add lexicon, user models and microplans
        //    if (owlFromMpiro)
        
        
        
        ExtendedIterator restrictions=ontModel.listRestrictions();
        while(restrictions.hasNext()){
            Restriction nextRestr=(Restriction) restrictions.next();
            
            ExtendedIterator superclasses=nextRestr.listSubClasses();
            while(superclasses.hasNext()){
                OntClass nextSuper=(OntClass) superclasses.next();
                if(Mpiro.win.struc.getValueRestriction(getNameInELEON(nextSuper)+":"+getNameInELEON(nextRestr.getOnProperty()))==null)
                    
                    Mpiro.win.struc.addValueRestriction(getNameInELEON(nextSuper)+":"+getNameInELEON(nextRestr.getOnProperty()),new ValueRestriction());
               // System.out.println(getNameInELEON(nextSuper)+":"+getNameInELEON(nextRestr.getOnProperty()));
                Vector restr=Mpiro.win.struc.getValueRestriction(getNameInELEON(nextSuper)+":"+getNameInELEON(nextRestr.getOnProperty()));
                if(nextRestr.isAllValuesFromRestriction()){
                    AllValuesFromRestriction avf=nextRestr.asAllValuesFromRestriction();
                    if(avf.getAllValuesFrom().isAnon()) continue;
                    Vector allVal=(Vector) restr.elementAt(0);
                    allVal.add(getNameInELEON(avf.getAllValuesFrom()));
                    continue;
                }
                if(nextRestr.isSomeValuesFromRestriction()){
                    SomeValuesFromRestriction svf=nextRestr.asSomeValuesFromRestriction();
                    if(svf.getSomeValuesFrom().isAnon()) continue;
                    Vector someVal=(Vector) restr.elementAt(1);
                    someVal.add(getNameInELEON(svf.getSomeValuesFrom()));
                    continue;
                }
                if(nextRestr.isHasValueRestriction()){
                    HasValueRestriction hv=nextRestr.asHasValueRestriction();
                    Vector hasVal=(Vector) restr.elementAt(2);
                    try{
                        Individual ind=(Individual) hv.getHasValue();
                        hasVal.add(getNameInELEON(ind));
                    }catch(java.lang.ClassCastException cce){};
                }
                
                if(nextRestr.isMaxCardinalityRestriction()){
                    MaxCardinalityRestriction mc=nextRestr.asMaxCardinalityRestriction();
                    Vector MaxCard=(Vector) restr.elementAt(3);
                    //Individual ind=(Individual) hv.getHasValue();
                    int value=mc.getMaxCardinality();
                    MaxCard.add(String.valueOf(value));
                    
                }
                
                if(nextRestr.isMinCardinalityRestriction()){
                    MinCardinalityRestriction mc=nextRestr.asMinCardinalityRestriction();
                    Vector MinCard=(Vector) restr.elementAt(4);
                    //Individual ind=(Individual) hv.getHasValue();
                    int value=mc.getMinCardinality();
                    MinCard.add(String.valueOf(value));
                    
                }
                
                if(nextRestr.isCardinalityRestriction()){
                    CardinalityRestriction c=nextRestr.asCardinalityRestriction();
                    Vector Card=(Vector) restr.elementAt(5);
                    //Individual ind=(Individual) hv.getHasValue();
                    int value=c.getCardinality();
                    Card.add(String.valueOf(value));
                    
                }
                
            }
        }
        
      //  if(readXMLFile)
        //    importLexiconFromXmlFile(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf('.')) + "_mpiro.xml");
        
        
        
        
        
        
        
        ExtendedIterator ei=ontModel.listNamedClasses();
        while(ei.hasNext()){
            OntClass next= (OntClass) ei.next();
            Mpiro.win.struc.addEquivalentClasses(getNameInELEON(next),new Vector());
            
            ExtendedIterator eqclasses=next.listEquivalentClasses();
            while(eqclasses.hasNext()){
                OntClass nextEqClass=(OntClass) eqclasses.next();
                
                //    if(NextSuperclass.isUnionClass()||NextSuperclass.isIntersectionClass()|| NextSuperclass.isComplementClass()||NextSuperclass.isEnumeratedClass())
                //  {
                Equivalent temp=new Equivalent( Mpiro.win.getFrames()[0], true, true );
                
                
                String[] s=temp.splitIntersections(convertToClassExpression(nextEqClass)).split("A-N-D");
                temp.dispose();
                Vector eqVec=(Vector) Mpiro.win.struc.getEquivalentClasses(getNameInELEON(next));
                //    eqVec.add(convertToClassExpression(NexteqClass));
                
                
                
                //String[] s=splitIntersections(exp).split("A-N-D");
                for(int i=0;i<s.length;i++){
                    eqVec.add(s[i]);
                }
                //        if(NextSuperclass.isUnionClass())
                //      {
                //        UnionClass uc=NextSuperclass.asUnionClass();
                //      ExtendedIterator op=uc.listOperands();
                // }
                //       }
            }
            
            
            Mpiro.win.struc.addSuperClasses(getNameInELEON(next),new Vector());
            ExtendedIterator superclasses=next.listSuperClasses();
            while(superclasses.hasNext()){
                OntClass nextSuperClass=(OntClass) superclasses.next();
                if(!nextSuperClass.isAnon())
                    continue;
                //prepei kai gia simple restrictions!!!!!!!
                
                if(nextSuperClass.isRestriction()){
                    if(nextSuperClass.asRestriction().isCardinalityRestriction()||nextSuperClass.asRestriction().isMinCardinalityRestriction()||nextSuperClass.asRestriction().isMaxCardinalityRestriction())
                        continue;
                    //System.out.println("Restriction: "+nextSuperClass));
                    if(nextSuperClass.asRestriction().isAllValuesFromRestriction()){
                        if(!nextSuperClass.asRestriction().asAllValuesFromRestriction().getAllValuesFrom().isAnon())
                            continue;
                    }
                    if(nextSuperClass.asRestriction().isSomeValuesFromRestriction()){
                        if(!nextSuperClass.asRestriction().asSomeValuesFromRestriction().getSomeValuesFrom().isAnon())
                            continue;
                    }
                    // if(nextSuperClass.asRestriction().asAllValuesFromRestriction().getAllValuesFrom().isAnon())
                    //    continue;
                }
                
                //    if(NextSuperclass.isUnionClass()||NextSuperclass.isIntersectionClass()|| NextSuperclass.isComplementClass()||NextSuperclass.isEnumeratedClass())
                //  {
                Equivalent temp = new Equivalent( Mpiro.win.getFrames()[0], true, false );
                
                
                String[] s=temp.splitIntersections(convertToClassExpression(nextSuperClass)).split("A-N-D");
                temp.dispose();
                Vector supVec=(Vector) Mpiro.win.struc.getSuperClasses(getNameInELEON(next));
                //    eqVec.add(convertToClassExpression(NexteqClass));
                
                
                
                //String[] s=splitIntersections(exp).split("A-N-D");
                for(int i=0;i<s.length;i++){
                    supVec.add(s[i]);
                }
                //        if(NextSuperclass.isUnionClass())
                //      {
                //        UnionClass uc=NextSuperclass.asUnionClass();
                //      ExtendedIterator op=uc.listOperands();
                // }
                //       }
            }
            if(readXMLFile){
                Vector annotations=new Vector();
                for(ExtendedIterator labels=next.listLabels(null);labels.hasNext();){
                    Literal nextLabel=(Literal) labels.next();
                    annotations.add(new AnnotationProperty("rdfs:label", nextLabel.getLexicalForm(), nextLabel.getLanguage(), new Boolean(false), ""));
                }
                for(ExtendedIterator comments=next.listComments(null);comments.hasNext();){
                    Literal nextComment=(Literal) comments.next();
                    annotations.add(new AnnotationProperty("rdfs:comment", nextComment.getLexicalForm(), nextComment.getLanguage(), new Boolean(false), ""));
                }
                for(ExtendedIterator versionsInfo=next.listVersionInfo();versionsInfo.hasNext();){
                    String nextVersionInfo=versionsInfo.next().toString();
                    annotations.add(new AnnotationProperty("rdfs:label", nextVersionInfo, "", new Boolean(false), ""));
                }
                for(ExtendedIterator seeAlso=next.listSeeAlso();seeAlso.hasNext();){
                    OntResource nextSeeAlso=(OntResource)seeAlso.next();
                    annotations.add(new AnnotationProperty("rdfs:seeAlso", getNameInELEON(nextSeeAlso), "", new Boolean(false), ""));
                }
                for(ExtendedIterator isDefinedBy=next.listIsDefinedBy();isDefinedBy.hasNext();){
                    OntResource nextIsDefinedBy=(OntResource)isDefinedBy.next();
                    annotations.add(new AnnotationProperty("rdfs:seeAlso", getNameInELEON(nextIsDefinedBy), "", new Boolean(false), ""));
                }
                
                Mpiro.win.struc.addAnnotation(getNameInELEON(next), annotations);
            }
        }
        if(readXMLFile){
            for(ExtendedIterator instances=ontModel.listIndividuals();instances.hasNext();){
                Individual next=(Individual)instances.next();
                Vector annotations=new Vector();
                for(ExtendedIterator labels=next.listLabels(null);labels.hasNext();){
                    Literal nextLabel=(Literal) labels.next();
                    annotations.add(new AnnotationProperty("rdfs:label", nextLabel.getLexicalForm(), nextLabel.getLanguage(), new Boolean(false), ""));
                }
                for(ExtendedIterator comments=next.listComments(null);comments.hasNext();){
                    Literal nextComment=(Literal) comments.next();
                    annotations.add(new AnnotationProperty("rdfs:comment", nextComment.getLexicalForm(), nextComment.getLanguage(), new Boolean(false), ""));
                }
                for(ExtendedIterator versionsInfo=next.listVersionInfo();versionsInfo.hasNext();){
                    String nextVersionInfo=versionsInfo.next().toString();
                    annotations.add(new AnnotationProperty("rdfs:label", nextVersionInfo, "", new Boolean(false), ""));
                }
                for(ExtendedIterator seeAlso=next.listSeeAlso();seeAlso.hasNext();){
                    OntResource nextSeeAlso=(OntResource)seeAlso.next();
                    annotations.add(new AnnotationProperty("rdfs:seeAlso", getNameInELEON(nextSeeAlso), "", new Boolean(false), ""));
                }
                for(ExtendedIterator isDefinedBy=next.listIsDefinedBy();isDefinedBy.hasNext();){
                    OntResource nextIsDefinedBy=(OntResource)isDefinedBy.next();
                    annotations.add(new AnnotationProperty("rdfs:seeAlso", getNameInELEON(nextIsDefinedBy), "", new Boolean(false), ""));
                }
                
                Mpiro.win.struc.addAnnotation(getNameInELEON(next), annotations);
            }
        }
        
        
        
        
        readLexiconRDF(rdfFile.getParentFile().getAbsolutePath());
        readUserModellingRDF(rdfFile.getParentFile().getAbsolutePath());
        readRobotModellingRDF(rdfFile.getParentFile().getAbsolutePath());
        readMicroplansRDF(rdfFile.getParentFile().getAbsolutePath());
//System.out.println("112");
        //System.out.println(DialogClassesToImport.modalResult);
       // QueryProfileHashtable.robotsHashtable=new Hashtable();
        
       // QueryProfileHashtable.mainRobotsModelHashtable=new Hashtable();
        //QueryProfileHashtable.fillMainRobotsModelHashtable();
        if(Mpiro.win.struc.getRobotsVectorFromUsersHashtable().size()==0)
        Mpiro.win.struc.createDefaultRobot("NewProfile");
        return DialogClassesToImport.modalResult;
        //  return true;
    }
    
    private static String convertToClassExpression(OntClass oc){
        String r=new String();
        if(oc.isUnionClass()) {
            UnionClass uc=oc.asUnionClass();
            ExtendedIterator op=uc.listOperands();
            r+="("+convertToClassExpression((OntClass) op.next());
            while(op.hasNext()){
                r+="\u222A"+convertToClassExpression((OntClass)op.next());
            }
            r+=")";
        }
        if(oc.isIntersectionClass()) {
            IntersectionClass uc=oc.asIntersectionClass();
            ExtendedIterator op=uc.listOperands();
            r+="("+convertToClassExpression((OntClass) op.next());
            while(op.hasNext()){
                r+="\u2229"+convertToClassExpression((OntClass)op.next());
            }
            r+=")";
        }
        if(!oc.isAnon()) {
            r+="(Class:"+getNameInELEON(oc)+")";
        }
        if(oc.isComplementClass()) {
            ComplementClass uc=oc.asComplementClass();
            OntClass op=uc.getOperand();
            r+="(\u00AC"+convertToClassExpression((OntClass)op)+")";
        }
        if(oc.isEnumeratedClass()) {
            EnumeratedClass uc=oc.asEnumeratedClass();
            ExtendedIterator op=uc.listOneOf();
            r+="({Individual:"+getNameInELEON(((OntResource) op.next()));
            while(op.hasNext()){
                r+=", Individual:"+getNameInELEON(((OntResource) op.next()));
            }
            r+="})";
        }
        if(oc.isRestriction()){
            Restriction res=oc.asRestriction();
            if(res.isAllValuesFromRestriction()){
                AllValuesFromRestriction avfr=res.asAllValuesFromRestriction();
                r+="(\u2200 Property:"+getNameInELEON(avfr.getOnProperty())+" "+convertToClassExpression((OntClass)avfr.getAllValuesFrom())+")";
            }
            if(res.isSomeValuesFromRestriction()){
                SomeValuesFromRestriction svfr=res.asSomeValuesFromRestriction();
                r+="(\u2203 Property:"+getNameInELEON(svfr.getOnProperty())+" "+convertToClassExpression((OntClass)svfr.getSomeValuesFrom())+")";
            }
            if(res.isHasValueRestriction()){
                HasValueRestriction hvr=res.asHasValueRestriction();
                try{
                    r+="(\u220D Property:"+getNameInELEON(hvr.getOnProperty())+" Individual:"+(getNameInELEON((Resource)hvr.getHasValue()))+")";
                }catch(java.lang.ClassCastException cce){};
            }
            if(res.isMaxCardinalityRestriction()){
                MaxCardinalityRestriction maxCR=res.asMaxCardinalityRestriction();
                r+="(\u2264= "+String.valueOf(maxCR.getMaxCardinality())+" Property:"+getNameInELEON(maxCR.getOnProperty())+")";
            }
            if(res.isMinCardinalityRestriction()){
                MinCardinalityRestriction minCR=res.asMinCardinalityRestriction();
                r+="(\u2265 "+String.valueOf(minCR.getMinCardinality())+" Property:"+getNameInELEON(minCR.getOnProperty())+")";
            }
            if(res.isCardinalityRestriction()){
                CardinalityRestriction CR=res.asCardinalityRestriction();
                r+="(= "+String.valueOf(CR.getCardinality())+" Property:"+getNameInELEON(CR.getOnProperty())+")";
            }
        }
        return(r);
    }
    
    //create sub types of a basic entity type
    public static void createSubTypes(DefaultMutableTreeNode parentTreeNode, OntClass ontClass, Vector inheritedProp,String instan) {
        //System.out.println("60");
        String occur="";
        if (instan!="") occur="_occur";
        ///System.out.println("****************"+ ontClass.toString());
        for (int i = 0; i < parentTreeNode.getChildCount(); i++) {
            DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) parentTreeNode.getChildAt(i);
            IconOwlData treeObject = ( (IconOwlData) currentTreeNode.getUserObject());
            OntClass test = treeObject.getOntClass();
            //System.out.println("Classss::::"+ getNameInELEON(test));
            int k=0;
            ExtendedIterator superclass=test.listSuperClasses();
            for(k=0;superclass.hasNext();k++)
                superclass.next();
            
            
            if (k>1){ //System.out.println("super"+ getNameInELEON(test));
            multi(test,1,inheritedProp);
            
            continue;}
               /*      OntClass notr[]=new OntClass[k];
                   ExtendedIterator superclass1=test.listSuperClasses();
                for(int j=0;superclass1.hasNext();j++)
                   notr[j]=(OntClass) superclass1.next();
                
                
                   NodeVector nv = new NodeVector(notr[0]));
                 //        IconOwlData treeObject = ( (IconOwlData) currentTreeNode1.getUserObject());
              Mpiro.win.struc.putEntityTypeOrEntityToDB(treeObject.toString(), nv);
              if (!owlFromMpiro)
                                Mpiro.win.struc.addEntityTypeInUserModelHashtable(treeObject.toString());
                            Vector databaseTableVector = nv.getDatabaseTableVector();
                            ExtendedIterator aaaaa=notr[0].listDeclaredProperties();
                            ExtendedIterator aaaaa2=notr[1].listDeclaredProperties();
                         while (aaaaa.hasNext()) {
                              // //System.out.println("oopp"+aaaaa.next());
                               OntProperty ioo=(OntProperty)aaaaa.next();
                
                               databaseTableVector.add(new FieldData(getNameInELEON(ioo), ioo.getRange()) , true, ""));
                        //   //System.out.println("oopp"+getNameInELEON(ioo)+prop+ioo.getRange())+ioo.toString());
                               }//}
                            ExtendedIterator aaaaa21=notr[1].listDeclaredProperties();
                            while (aaaaa2.hasNext()) {
                                OntProperty ioo2 = (OntProperty) aaaaa2.next();
                                 if (!(notr[0].hasProperty(ioo2)))
                                databaseTableVector.add(new FieldData(ioo2), ioo2.getRange()) , true, ""));
                
                
                        } createTypeProperties(nv, treeObject.toString(), treeObject.getOntClass(), inheritedProp);
                
                         createEntities(treeObject.toString(), treeObject.getOntClass());
                
                         createSubTypes(currentTreeNode, treeObject.getOntClass(), nv.getDatabaseTableVector());
                
                
               continue;
               }
                *///System.out.println("okokokko");
            if (treeObject.m_icon == DialogClassesToImport.ICON_OWLCLASSCHECKED) {
                NodeVector nv = new NodeVector(parentTreeNode.getUserObject().toString()+occur+instan);
                //System.out.println("nvvvv"+ parentTreeNode.getUserObject().toString()+"treeeee"+treeObject.toString());
                Mpiro.win.struc.putEntityTypeOrEntityToDB(treeObject.toString()+occur+instan, nv);
                //System.out.println("20");
                //System.out.println("77.1");
                //if (!owlFromMpiro)
                Mpiro.win.struc.addEntityTypeInUserModelHashtable(treeObject.toString()+occur+instan);
                //System.out.println("21");
                //System.out.println("77.2");
                //System.out.println("nv"+nv.toString());
                //System.out.println("treeobj"+treeObject.toString());
                createTypeProperties(nv, treeObject.toString()+occur+instan, treeObject.getOntClass(), inheritedProp);
                // System.out.println("oolololololo");
                createEntities(treeObject.toString()+occur+instan, treeObject.getOntClass(),instan);
                
                createSubTypes(currentTreeNode, treeObject.getOntClass(), nv.getDatabaseTableVector(),instan);
            }
        }
    }
    
    public static void multi(OntClass inter1,int type1,Vector inheritedProp) {
        //    //System.out.println("70");
        //System.out.println("multi   "+getNameInELEON(inter1));
        if(!inter1.isAnon()){
            ExtendedIterator asta1,asta=null;
            int notr=0,res=0;
            if (type1==0){
                asta1 = inter1.asIntersectionClass().listOperands();} else { asta1 = inter1.listSuperClasses();}
            
            // ExtendedIterator asta1=asta2.filterKeep(null);
            while (asta1.hasNext()) {
                OntClass c=null;
                try{
                    // //System.out.println(ioo.getRange()));
                    c = (OntClass) asta1.next();
                }         catch (Exception r){};
                
                
                if (c.isRestriction())
                    res++;
                else{
                    notr++;
                    //  if (!Mpiro.win.struc.mainDBcontainsEntityOrEntityType(c.toString())) return;
                }
            }
            OntClass notres[]=new OntClass[notr];
            //OntClass restr[]=new OntClass[res]
            String range[]=new String[res];
            String prop[]=new String[res];
            boolean approved[]=new boolean[res];
            int l=0,n=0;
            notr=0;
            res=0;
            //   //System.out.println("0kai"+res);
            if (type1==0){
                asta = inter1.asIntersectionClass().listOperands();} else { asta = inter1.listSuperClasses();}
            
            
            while (asta.hasNext()) {
                //  //System.out.println("1");
                OntClass c = (OntClass) asta.next();
                ////System.out.println(c));
                
                //      //System.out.println(notr1));
                if (c.isRestriction()) {
                    //      //System.out.println("2");
                    Restriction r = c.asRestriction();
                    ////System.out.println(r));
                    if (r.isAllValuesFromRestriction()){
                        res++;////System.out.println("2.1");
                        AllValuesFromRestriction av = r.asAllValuesFromRestriction();
                        //       //System.out.println("AllValuesFrom class " + res + notr+
                        //                           av.getAllValuesFrom()) +
                        //                          " on property " + av.getOnProperty()));
                        range[l]=getNameInELEON(av.getAllValuesFrom());
                        prop[l]= getNameInELEON(av.getOnProperty());
                        // //System.out.println(range[0]);
                        approved[l]=true;
                        l++;
                        //proper=av.getOnProperty();
                        
                    }
                    // while (asta.iterator().hasNext()) {
                    // //System.out.println(asta.iterator().next().toString());
                    if (r.isMaxCardinalityRestriction()){
                        res++;////System.out.println("2.2");
                        MaxCardinalityRestriction restriction = r.asMaxCardinalityRestriction();
                        if ( restriction.getMaxCardinality() == 1) {
                            approved[l] = false;
                            
                            prop[l]=getNameInELEON(restriction.getOnProperty());
                            range[l]=  getNameInELEON(restriction.getOnProperty().getRange());
                            l++;
                            //proper=av.getOnProperty();
                        }
                    }
                    
                    
                } else    if (!c.isUnionClass()) {
                    ////System.out.println("3");
                    notres[n]=c;
                    n++;
                    notr++;
                    //System.out.println(c));
                    //      //System.out.print(res);
                    //    //System.out.println(notr);
                }
            }
            
            
            //    //System.out.print("aaa"+notr1));
            //      //System.out.println(getNameInELEON(inter1));
            if(notr>0){
                // //System.out.println("3.5");
                //  //System.out.println(notres[0]));
                //   //System.out.println(notres[0].toString());
                NodeVector nv=null;
                if(getNameInELEON(notres[0])!=null)
                    nv = new NodeVector(getNameInELEON(notres[0]));
                else
                    nv = new NodeVector(notres[0].toString());
                //  //System.out.println("3.7");
                //        IconOwlData treeObject = ((IconOwlData) currentTreeNode1.getUserObject());
                if (getNameInELEON(inter1)!=null)
                    Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(inter1), nv);
                else
                    Mpiro.win.struc.putEntityTypeOrEntityToDB(inter1.toString(), nv);
                
                // //System.out.println("4");
                
                //  if (!owlFromMpiro)
                // {
                if (getNameInELEON(inter1)!=null)
                    Mpiro.win.struc.addEntityTypeInUserModelHashtable(getNameInELEON(inter1));
                else
                    Mpiro.win.struc.addEntityTypeInUserModelHashtable(inter1.toString());
                // }
                Vector databaseTableVector = nv.getDatabaseTableVector();
                //if (inheritedProp != null)
                //           for (int i = 8; i < inheritedProp.size(); i++) {
                //if(!(databaseTableVector.contains(inheritedProp.get(i))))
                //               databaseTableVector.add(inheritedProp.get(i));
                //           }
//for(int p=0;p<l;p++){////System.out.println("OK"+p);//System.out.println(prop[p]+range[p]+approved[p]);
//     databaseTableVector.add(new FieldData(prop[p], range[p], approved[p], ""));}
                //OntClass parent=notr1.getSuperClass();
                // NodeVector nv1 = new NodeVector(parent));
                // DefaultMutableTreeNode reee=(DefaultMutableTreeNode) parent;
                // //System.out.println("oopp"+nv1.databaseTableVector.toString());
                //  //System.out.println("5");
////System.out.println( QueryHashtable.mainDBHashtable.
                //OntClass asa=(OntClass)par;
//for (int e=0;e<notr;e++){
                
//  ExtendedIterator aaaaa= inter1.listDeclaredProperties();
                for(int p=0;p<notres.length;p++ ){
                    ExtendedIterator aaaaa;
                    try {
                        aaaaa = notres[p].listDeclaredProperties(true);
                    } catch(NullPointerException npex) {
                        continue;
                    }
                    
                    while (aaaaa.hasNext()) {//System.out.println("6");
                        // //System.out.println("oopp"+aaaaa.next());
                        OntProperty ioo=(OntProperty)aaaaa.next();
                        if (l>0){
                            for (int x=0;x<l;x++){////System.out.println("7"+res);
                                if (prop[x].equalsIgnoreCase(getNameInELEON(ioo))){
                                    if (ioo.isDatatypeProperty()) {
                                        approved[x]=false;
                                        if (range[x].equalsIgnoreCase("mpiroNumber") || range[x].equalsIgnoreCase("integer") || range[x].equalsIgnoreCase("positiveInteger") ||
                                                range[x].equalsIgnoreCase("negativeInteger") || range[x].equalsIgnoreCase("nonNegativeInteger") || range[x].equalsIgnoreCase("long") ||
                                                range[x].equalsIgnoreCase("unsignedLong") || range[x].equalsIgnoreCase("int") || range[x].equalsIgnoreCase("unsignedInt") ||
                                                range[x].equalsIgnoreCase("short") || range[x].equalsIgnoreCase("unsignedShort") || range[x].equalsIgnoreCase("decimal") ||
                                                range[x].equalsIgnoreCase("float") ||
                                                range[x].equalsIgnoreCase("double") || range[x].equalsIgnoreCase("date") || range[x].equalsIgnoreCase("datetime")) {
                                            range[x] = "Number";
                                        } else if (range[x].equalsIgnoreCase("mpiroDate")) {
                                            range[x] = "Date";
                                        } else if (range[x].equalsIgnoreCase("mpiroDimension")) {
                                            range[x] = "Dimension";
                                        } else if (range[x].equalsIgnoreCase("string")) {
                                            range[x] = "String";
                                        }}
                                    if (!databaseTableVector.contains(new FieldData(prop[x], range[x], true, "")))
                                        databaseTableVector.add(new FieldData(prop[x], range[x], true, ""));
                                    addPropertyInUsersHashtable(prop[x], getNameInELEON(inter1));
                                    break;} else {
                                    if (x < (l - 1))continue;
                                    }
                                ////System.out.println("7.5");
                                try{
                                    String rangeName=ioo.getRange().getLocalName();
                                    if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                                            rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                                            rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                                            rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                                            rangeName.equalsIgnoreCase("float") ||
                                            rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                                        rangeName = "Number";
                                    } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                                        rangeName = "Date";
                                    } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                                        rangeName = "Dimension";
                                    } else if (rangeName.equalsIgnoreCase("string")) {
                                        rangeName = "String";
                                    }
                                    // //System.out.println(ioo.getRange()));
                                    if (!databaseTableVector.contains(new FieldData(getNameInELEON(ioo), rangeName, true, "")))
                                        databaseTableVector.add(new FieldData(getNameInELEON(ioo), rangeName, true, ""));
                                    addPropertyInUsersHashtable(getNameInELEON(ioo), getNameInELEON(inter1));
                                }         catch (Exception r){};
                            }} else   {
                            
                            try{
                                String rangeName=ioo.getRange().getLocalName();
                                if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                                        rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                                        rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                                        rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                                        rangeName.equalsIgnoreCase("float") ||
                                        rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                                    rangeName = "Number";
                                } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                                    rangeName = "Date";
                                } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                                    rangeName = "Dimension";
                                } else if (rangeName.equalsIgnoreCase("string")) {
                                    rangeName = "String";
                                }
                                if (!databaseTableVector.contains(new FieldData(getNameInELEON(ioo), rangeName, true, "")))
                                    databaseTableVector.add(new FieldData(getNameInELEON(ioo), rangeName, true, ""));
                                addPropertyInUsersHashtable(getNameInELEON(ioo), getNameInELEON(inter1));
                            }         catch (Exception r){};
                            }
                        ////System.out.println("oopp"+getNameInELEON(ioo)+prop+ioo.getRange())+ioo.toString());
                        
                        
                    }}//}
//for(int p=0;p<inter1.length;p++ ){
                ExtendedIterator cccc=inter1.listDeclaredProperties(true);
                while(cccc.hasNext()){
                    OntProperty oi=(OntProperty) cccc.next();
                    String rangeName;
                    try {
                        OntResource rangess = oi.getRange();
                        rangeName = getNameInELEON(oi.getRange());
                    } catch(NullPointerException npexc) {
                        rangeName="";
                    }
                    if (oi.isDatatypeProperty()) {
                        if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                                rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                                rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                                rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                                rangeName.equalsIgnoreCase("float") ||
                                rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                            rangeName = "Number";
                        } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                            rangeName = "Date";
                        } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                            rangeName = "Dimension";
                        } else if (rangeName.equalsIgnoreCase("string")) {
                            rangeName = "String";
                        }}
                    if (!databaseTableVector.contains(new FieldData(getNameInELEON(oi), rangeName, true, "")))
                        databaseTableVector.add(new FieldData(getNameInELEON(oi), rangeName, true, ""));
                    addPropertyInUsersHashtable(getNameInELEON(oi), getNameInELEON(inter1));
                    
                }//}
                //  Vector nv2=nv1.getDatabaseTableVector().
                //   IconOwlData treeObject = ( (IconOwlData) inter1.getUserObject());
//               createTypeProperties(nv, inter1.toString(), inter1.asClass(), null);
                // createTypeProperties(nv, treeObject.toString(), treeObject.getOntClass(), inheritedProp);
////System.out.println("7.9");
                // //System.out.println("loooo"+getNameInELEON(inter1)+inter1.toString());
                //createEntities(inter1.toString(), inter1.asClass());
                createEntities(getNameInELEON(inter1), inter1.asClass(),"",notres[0],true);
                //IconOwlData treeObject2 = ( (IconOwlData) notr1);
                //   notr1.getNode();
                
                
                DefaultMutableTreeNode ssos=null;
                DefaultMutableTreeNode basicEntityTypesTreeNode = (DefaultMutableTreeNode) DialogClassesToImport.root;
                ////System.out.println("8");
                
                
                
//DefaultMutableTreeNode s= (DefaultMutableTreeNode) basicEntityTypesTreeNode.getNextNode();
                Enumeration en=basicEntityTypesTreeNode.preorderEnumeration();
                
                while (en.hasMoreElements()){
                    
//DefaultMutableTreeNode ssps= s;
                    //System.out.println(s.toString());
                    //System.out.println(inter1.toString());
                    ssos= (DefaultMutableTreeNode) en.nextElement();
                    if (ssos.toString().equalsIgnoreCase(getNameInELEON(inter1))) {
                        //System.out.println("ssos: "+ssos+" inter1: "+inter1.toString()+"  ");
                        //System.out.println("count:  "+String.valueOf(ssos.getChildCount()));
                        createSubTypes(ssos, inter1.asClass(), nv.getDatabaseTableVector(),"");
                        break;
                    }
                    //System.out.println(ssos.toString()+"treeobject:::"+inter1.asClass().toString());
                } }
            // //System.out.println("10");
            
//createSubTypes(currentTreeNode, treeObject.getOntClass(), nv.getDatabaseTableVector(),instan);
            
            
            
            if(notr>1){
                for(int b=1;b<notr;b++){
                    // //System.out.println("3.5");
                    //  //System.out.println(notres[0]));
                    //   //System.out.println(notres[0].toString());
                    NodeVector nv=null;
                    if(getNameInELEON(notres[b])!=null)
                        nv = new NodeVector(getNameInELEON(notres[b]));
                    else
                        nv = new NodeVector(notres[b].toString());
                    //  //System.out.println("3.7");
                    //        IconOwlData treeObject = ( (IconOwlData) currentTreeNode1.getUserObject());
                    if (getNameInELEON(inter1)!=null)
                        Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(inter1)+"_occur"+String.valueOf(b+1), nv);
                    else
                        Mpiro.win.struc.putEntityTypeOrEntityToDB(inter1.toString()+"_occur"+String.valueOf(b+1), nv);
                    
                    // //System.out.println("4");
                    
                    // if (!owlFromMpiro)
                    //{
                    if (getNameInELEON(inter1)!=null)
                        Mpiro.win.struc.addEntityTypeInUserModelHashtable(getNameInELEON(inter1)+"_occur"+String.valueOf(b+1));
                    else
                        Mpiro.win.struc.addEntityTypeInUserModelHashtable(inter1.toString()+"_occur"+String.valueOf(b+1));
                    //  }
                    Vector databaseTableVector = nv.getDatabaseTableVector();
                    
//if (inheritedProp != null)
                    //          for (int i = 8; i < inheritedProp.size(); i++) {
                    ///
                    ///            databaseTableVector.add(inheritedProp.get(i));
                    //     }
//for(int p=0;p<l;p++){////System.out.println("OK"+p);//System.out.println(prop[p]+range[p]+approved[p]);
//     databaseTableVector.add(new FieldData(prop[p], range[p], approved[p], ""));}
                    //OntClass parent=notr1.getSuperClass();
                    // NodeVector nv1 = new NodeVector(parent));
                    // DefaultMutableTreeNode reee=(DefaultMutableTreeNode) parent;
                    // //System.out.println("oopp"+nv1.databaseTableVector.toString());
                    //  //System.out.println("5");
////System.out.println( QueryHashtable.mainDBHashtable.
                    //OntClass asa=(OntClass)par;
//for (int e=0;e<notr;e++){
                    
//  ExtendedIterator aaaaa= inter1.listDeclaredProperties();
                    for(int p=0;p<notres.length;p++ ){
                        ExtendedIterator aaaaa= notres[p].listDeclaredProperties(true);
                        while (aaaaa.hasNext()) {//System.out.println("6");
                            // //System.out.println("oopp"+aaaaa.next());
                            OntProperty ioo=(OntProperty)aaaaa.next();
                            if (l>0){
                                for (int x=0;x<l;x++){////System.out.println("7"+res);
                                    if (prop[x].equalsIgnoreCase(getNameInELEON(ioo))){
                                        if (ioo.isDatatypeProperty()) {
                                            approved[x]=false;
                                            if (range[x].equalsIgnoreCase("mpiroNumber") || range[x].equalsIgnoreCase("integer") || range[x].equalsIgnoreCase("positiveInteger") ||
                                                    range[x].equalsIgnoreCase("negativeInteger") || range[x].equalsIgnoreCase("nonNegativeInteger") || range[x].equalsIgnoreCase("long") ||
                                                    range[x].equalsIgnoreCase("unsignedLong") || range[x].equalsIgnoreCase("int") || range[x].equalsIgnoreCase("unsignedInt") ||
                                                    range[x].equalsIgnoreCase("short") || range[x].equalsIgnoreCase("unsignedShort") || range[x].equalsIgnoreCase("decimal") ||
                                                    range[x].equalsIgnoreCase("float") ||
                                                    range[x].equalsIgnoreCase("double") || range[x].equalsIgnoreCase("date") || range[x].equalsIgnoreCase("datetime")) {
                                                range[x] = "Number";
                                            } else if (range[x].equalsIgnoreCase("mpiroDate")) {
                                                range[x] = "Date";
                                            } else if (range[x].equalsIgnoreCase("mpiroDimension")) {
                                                range[x] = "Dimension";
                                            } else if (range[x].equalsIgnoreCase("string")) {
                                                range[x] = "String";
                                            }}
                                        if (!databaseTableVector.contains(new FieldData(prop[x], range[x], true, "")))
                                            databaseTableVector.add(new FieldData(prop[x], range[x], true, ""));
                                        addPropertyInUsersHashtable(prop[x], getNameInELEON(inter1)+"_occur"+String.valueOf(b+1));
                                        break;} else {
                                        if (x < (l - 1))continue;
                                        }
                                    ////System.out.println("7.5");
                                    try{
                                        String rangeName=getNameInELEON(ioo.getRange());
                                        if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                                                rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                                                rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                                                rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                                                rangeName.equalsIgnoreCase("float") ||
                                                rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                                            rangeName = "Number";
                                        } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                                            rangeName = "Date";
                                        } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                                            rangeName = "Dimension";
                                        } else if (rangeName.equalsIgnoreCase("string")) {
                                            rangeName = "String";
                                        }
                                        // //System.out.println(ioo.getRange()));
                                        if (!databaseTableVector.contains(new FieldData(getNameInELEON(ioo), rangeName, true, "")))
                                            databaseTableVector.add(new FieldData(getNameInELEON(ioo), rangeName, true, ""));
                                        addPropertyInUsersHashtable(getNameInELEON(ioo), getNameInELEON(inter1)+"_occur"+String.valueOf(b+1));
                                    }         catch (Exception r){};
                                }} else   {
                                
                                try{
                                    String rangeName=ioo.getRange().getLocalName();
                                    if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                                            rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                                            rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                                            rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                                            rangeName.equalsIgnoreCase("float") ||
                                            rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                                        rangeName = "Number";
                                    } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                                        rangeName = "Date";
                                    } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                                        rangeName = "Dimension";
                                    } else if (rangeName.equalsIgnoreCase("string")) {
                                        rangeName = "String";
                                    }
                                    if (!databaseTableVector.contains(new FieldData(getNameInELEON(ioo), rangeName, true, "")))
                                        databaseTableVector.add(new FieldData(getNameInELEON(ioo), rangeName, true, ""));
                                    addPropertyInUsersHashtable(getNameInELEON(ioo), getNameInELEON(inter1)+"_occur"+String.valueOf(b+1));
                                }         catch (Exception r){};
                                }
                            ////System.out.println("oopp"+getNameInELEON(ioo)+prop+ioo.getRange())+ioo.toString());
                            
                        }}//}
//for(int p=0;p<notres.length;p++ ){
                    ExtendedIterator cccc=inter1.listDeclaredProperties(true);
                    while(cccc.hasNext()){
                        OntProperty oi=(OntProperty) cccc.next();
                        // //System.out.println("@@@@@!@!@!@!@!@!@@!!@!@@@!@!@@     :      "+oi.toString());}
                        String rangeName=oi.getRange().getLocalName();
                        if (oi.isDatatypeProperty()) {
                            if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                                    rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                                    rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                                    rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                                    rangeName.equalsIgnoreCase("float") ||
                                    rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                                rangeName = "Number";
                            } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                                rangeName = "Date";
                            } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                                rangeName = "Dimension";
                            } else if (rangeName.equalsIgnoreCase("string")) {
                                rangeName = "String";
                            }} else rangeName=getNameInELEON(oi.getRange());
                        if (!databaseTableVector.contains(new FieldData(getNameInELEON(oi), rangeName, true, "")))
                            databaseTableVector.add(new FieldData(getNameInELEON(oi), rangeName, true, ""));
                        addPropertyInUsersHashtable(getNameInELEON(oi), getNameInELEON(inter1)+"_occur"+String.valueOf(b+1));
                        
                    }//}
                    //  Vector nv2=nv1.getDatabaseTableVector().
                    //   IconOwlData treeObject = ( (IconOwlData) inter1.getUserObject());
//               createTypeProperties(nv, inter1.toString(), inter1.asClass(), null);
                    // createTypeProperties(nv, treeObject.toString(), treeObject.getOntClass(), inheritedProp);
////System.out.println("7.9");
                    // //System.out.println("loooo"+getNameInELEON(inter1)+inter1.toString());
                    //createEntities(inter1.toString(), inter1.asClass());
                    createEntities(getNameInELEON(inter1)+"_occur"+String.valueOf(b+1), inter1.asClass(),String.valueOf(b+1),notres[b],true);
                    //IconOwlData treeObject2 = ( (IconOwlData) notr1);
                    //   notr1.getNode();
                    DefaultMutableTreeNode ssos=null;
                    DefaultMutableTreeNode basicEntityTypesTreeNode = (DefaultMutableTreeNode) DialogClassesToImport.root;
                    ////System.out.println("8");
                    
                    
                    
//DefaultMutableTreeNode s= (DefaultMutableTreeNode) basicEntityTypesTreeNode.getNextNode();
                    Enumeration en=basicEntityTypesTreeNode.preorderEnumeration();
                    
                    while (en.hasMoreElements()){
                        
//DefaultMutableTreeNode ssps= s;
                        //System.out.println(s.toString());
                        //System.out.println(inter1.toString());
                        ssos= (DefaultMutableTreeNode) en.nextElement();
                        if (ssos.toString().equalsIgnoreCase(getNameInELEON(inter1))) {
                            //System.out.println("ssos: "+ssos+" inter1: "+inter1.toString()+"  "+String.valueOf(b+1));
                            //System.out.println("count:  "+String.valueOf(ssos.getChildCount()));
                            createSubTypes(ssos, inter1.asClass(), nv.getDatabaseTableVector(),String.valueOf(b+1));
                            break;
                        }
                        
                        
                    };
//  //System.out.println("popopo"+ssos.toString());
                    
                }}
            
            
            
            
            
            
     /*   if(notr>1){
        for(int r=1;r<notr;r++){
        // //System.out.println("3.5");
        //  //System.out.println(notres[0]));
        //   //System.out.println(notres[0].toString());
        NodeVector nv1 = null;
        if (notres[r]) != null)
            nv1 = new NodeVector(notres[r]));
        else
            nv1 = new NodeVector(notres[r].toString());
        //  //System.out.println("3.7");
        //        IconOwlData treeObject = ( (IconOwlData) currentTreeNode1.getUserObject());
        if (getNameInELEON(inter1) != null)
            Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(inter1)+"_"+"occur"+String.valueOf(r+1) , nv1);
        else
            Mpiro.win.struc.putEntityTypeOrEntityToDB(inter1.toString()+"_"+"occur"+String.valueOf(r+1), nv1);
      
        // //System.out.println("4");
      
        if (!owlFromMpiro) {
            if (getNameInELEON(inter1) != null)
                Mpiro.win.struc.addEntityTypeInUserModelHashtable(getNameInELEON(inter1));
            else
                Mpiro.win.struc.addEntityTypeInUserModelHashtable(inter1.toString());
        }
        Vector databaseTableVector = nv1.getDatabaseTableVector();
        ExtendedIterator aaaaa= notres[r].listDeclaredProperties();
 while (aaaaa.hasNext()) {//System.out.println("6");
// //System.out.println("oopp"+aaaaa.next());
 OntProperty ioo=(OntProperty)aaaaa.next();
 if (l>0){
for (int x=0;x<l;x++){////System.out.println("7"+res);
if (prop[x].equalsIgnoreCase(getNameInELEON(ioo))){
                    if (range[x].equalsIgnoreCase("mpiroNumber") || range[x].equalsIgnoreCase("integer") || range[x].equalsIgnoreCase("positiveInteger") ||
                    range[x].equalsIgnoreCase("negativeInteger") || range[x].equalsIgnoreCase("nonNegativeInteger") || range[x].equalsIgnoreCase("long") ||
                    range[x].equalsIgnoreCase("unsignedLong") || range[x].equalsIgnoreCase("int") || range[x].equalsIgnoreCase("unsignedInt") ||
                    range[x].equalsIgnoreCase("short") || range[x].equalsIgnoreCase("unsignedShort") || range[x].equalsIgnoreCase("decimal") ||
                    range[x].equalsIgnoreCase("float") ||
                    range[x].equalsIgnoreCase("double") || range[x].equalsIgnoreCase("date") || range[x].equalsIgnoreCase("datetime")) {
                    range[x] = "Number";
                }
                else if (range[x].equalsIgnoreCase("mpiroDate")) {
                    range[x] = "Date";
                }
                else if (range[x].equalsIgnoreCase("mpiroDimension")) {
                    range[x] = "Dimension";
                }
                else if (range[x].equalsIgnoreCase("string")) {
                    range[x] = "String";
                }
    databaseTableVector.add(new FieldData(prop[x], range[x], approved[x], ""));
    break;}
else {
    if (x < (l - 1))continue;
}
//System.out.println("7.5");
      
//    /*
  //  ExtendedIterator aaaaa1= notres[0].listDeclaredProperties();
 //while (aaaaa1.hasNext()) {
   //   OntProperty ioo1=(OntProperty)aaaaa.next();
     // if (ioo1).equalsIgnoreCase(getNameInELEON(ioo)))
       //   break;
     // else{
       //   if (aaaaa1.hasNext()) continue;
      
      //}
 try{
     String rangeName=ioo.getRange());
        if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                    rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                    rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                    rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                    rangeName.equalsIgnoreCase("float") ||
                    rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                    rangeName = "Number";
                }
                else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                    rangeName = "Date";
                }
                else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                    rangeName = "Dimension";
                }
                else if (rangeName.equalsIgnoreCase("string")) {
                    rangeName = "String";
                }
   // //System.out.println(ioo.getRange()));
    databaseTableVector.add(new FieldData(getNameInELEON(ioo), rangeName, true, ""));
}        catch (Exception g){};}
}//}
     else   {
      
       try{
           String rangeName=ioo.getRange());
        if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                    rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                    rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                    rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                    rangeName.equalsIgnoreCase("float") ||
                    rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                    rangeName = "Number";
                }
                else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                    rangeName = "Date";
                }
                else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                    rangeName = "Dimension";
                }
                else if (rangeName.equalsIgnoreCase("string")) {
                    rangeName = "String";
                }
           databaseTableVector.add(new FieldData(getNameInELEON(ioo), rangeName, true, ""));
       }         catch (Exception g){};
   }
//System.out.println("oopp"+getNameInELEON(ioo)+prop+ioo.getRange())+ioo.toString());
      
}//}
//  Vector nv2=nv1.getDatabaseTableVector().
//   IconOwlData treeObject = ( (IconOwlData) inter1.getUserObject());
//               createTypeProperties(nv, inter1.toString(), inter1.asClass(), null);
// createTypeProperties(nv, treeObject.toString(), treeObject.getOntClass(), inheritedProp);
////System.out.println("7.9");
// //System.out.println("loooo"+getNameInELEON(inter1)+inter1.toString());
//createEntities(inter1.toString(), inter1.asClass());
createEntities(getNameInELEON(inter1)+"_"+"occur"+String.valueOf(r+1), inter1.asClass(),String.valueOf(r+1), notres[r], false);
//IconOwlData treeObject2 = ( (IconOwlData) notr1);
//   notr1.getNode();
DefaultMutableTreeNode ssos=null;
DefaultMutableTreeNode basicEntityTypesTreeNode = (DefaultMutableTreeNode) DialogClassesToImport.root;
//System.out.println("8");
DefaultMutableTreeNode s= (DefaultMutableTreeNode) basicEntityTypesTreeNode.getNextNode();
      
while (s!=null){
      
//DefaultMutableTreeNode ssps= s;
 //System.out.println(s.toString());
 //System.out.println(inter1.toString());
if (s.toString().equalsIgnoreCase(getNameInELEON(inter1)))
ssos=s;
 s= (DefaultMutableTreeNode) s.getNextNode();
      
};
 //System.out.println("9");
//try{
  //System.out.println(ssos.toString()+"treeobject:::"+inter1.asClass().toString());
createSubTypes(ssos, inter1.asClass(), nv1.getDatabaseTableVector(),String.valueOf(r+1));
 //    }catch (Exception g){};
     //System.out.println("10");
      
      
      
    }}*/
        }}
    //create the properties of a type
    public static void createTypeProperties(NodeVector nodeVector, String typeName, OntClass ontClass, Vector inheritedProp) {
        //System.out.println("30");
        ExtendedIterator properties = ontClass.listDeclaredProperties();
        Vector databaseTableVector = nodeVector.getDatabaseTableVector();
        //System.out.println("databaseTableVector::::"+databaseTableVector);
//System.out.println("31");
        //add inherited properties
        
        if (inheritedProp != null)
            for (int i = 0; i < inheritedProp.size(); i++) {
            String fieldname=((Vector)inheritedProp.get(i)).elementAt(0).toString();
            if(fieldname.equals("Subtype-of") || fieldname.equals("title") || fieldname.equals("name")|| fieldname.equals("shortname")|| fieldname.equals("notes")|| fieldname.equals("images")|| fieldname.equals("gender")|| fieldname.equals("number"))
                            continue;
            databaseTableVector.add(inheritedProp.get(i));
            }
//System.out.println("32");
        //add properties
        while (properties.hasNext()) {
            OntProperty property = (OntProperty) properties.next();
            if(!Mpiro.win.struc.existsProperty(getNameInELEON(property))) continue;
            //System.out.println("prin"+typeName+"  "+property.toString());
            
            if (inheritedProp != null) {
                boolean found = false;
                
                for (int i = 0; i < inheritedProp.size(); i++) {
                    if ( ( (FieldData) inheritedProp.get(i)).m_field.equals(getNameInELEON(property)))
                        found = true;
                }
                if (found)continue;
            }
//System.out.println("33");
            String propertyName = getNameInELEON(property);
            boolean approved = true;
            //System.out.println("meta"+propertyName);
            
            
            if( propertyName.equals("title") || propertyName.equals("name") || propertyName.equals("shortname") || propertyName.equals("notes") || propertyName.equals("gender")
            || propertyName.equals("number") || propertyName.equals("gender-name") || propertyName.equals("gender-shortname") || propertyName.equals("name-nominative") || propertyName.equals("name-genitive") || propertyName.equals("name-accusative")
            || propertyName.equals("shortname-nominative") || propertyName.equals("shortname-genitive") || propertyName.equals("shortname-accusative") || propertyName.equals("images")) continue;
            
            
            
//            ExtendedIterator superClasses = ontClass.listSuperClasses(true);
//            OntClass superClass;
//            while (superClasses.hasNext()) {
//                superClass = (OntClass) superClasses.next();
//                if (superClass.isRestriction())
//                    if (superClass.asRestriction().isMaxCardinalityRestriction()) {
//                    MaxCardinalityRestriction restriction = superClass.asRestriction().asMaxCardinalityRestriction();
//                    if (restriction.getOnProperty().equals(property) && restriction.getMaxCardinality() == 1) {
//                        approved = false;
//                    }
//                    }
//            }
//System.out.println("34");
            String rangeName = "";
            //if property has no range find restriction
            if (property.getRange() == null || property.getRange().isAnon()) {
                for (ExtendedIterator iter = ontClass.listSuperClasses(); iter.hasNext(); ) {
                    OntClass sup = (OntClass) iter.next();
                    if (sup.isRestriction()) {
                        Restriction r = sup.asRestriction();
                        OntProperty prop = r.getOnProperty();
                        if (prop.equals(property) && r.isAllValuesFromRestriction()) {
                            rangeName = getNameInELEON(r.asAllValuesFromRestriction().getAllValuesFrom());
                            //databaseTableVector.add(new FieldData(propertyName, rangeName, approved, ""));
                            break;
                        }
                    }
                }
                // continue;
            } else
                rangeName = getNameInELEON(property.getRange());
                if (rangeName == null) rangeName = "Basic-entity-types";
                
//System.out.println("35");
            
            
            //add new property from object property
            if (property.isObjectProperty()) {
                if (property.getRange() != null) {
                    //get the tree node of the range to see if it is checked
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) classToTreeNode.get(property.getRange());
                    //////problemhereeee
                    if (treeNode != null) {
                        IconOwlData treeObject = ( (IconOwlData) treeNode.getUserObject());
                        //if it is checked insert property else do not insert
                        
                        if (treeObject.m_icon == DialogClassesToImport.ICON_OWLCLASSCHECKED){
                            //System.out.println("insert"+propertyName+"  "+rangeName);
                            databaseTableVector.add(new FieldData(propertyName, rangeName, approved, ""));
                            //System.out.println("vector:   "+databaseTableVector.toString());
                        }}else{
                        databaseTableVector.add(new FieldData(propertyName, rangeName, approved, ""));
                        }
                }
                if (property.getRange() == null) databaseTableVector.add(new FieldData(propertyName, rangeName, approved, ""));
            }//System.out.println("36");
            //add new property from data type property
            if (property.isDatatypeProperty()) {
                try {
                    rangeName = property.getRange().getLocalName();
                } catch(java.lang.NullPointerException npe) {
                    System.err.println("WARNING: range of property "+property.getURI()+" has been set to String");
                    rangeName="String";
                }
                if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                        rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                        rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                        rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                        rangeName.equalsIgnoreCase("float") ||
                        rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                    rangeName = "Number";
                } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                    rangeName = "Date";
                } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                    rangeName = "Dimension";
                } else if (rangeName.equalsIgnoreCase("string")) {
                    rangeName = "String";
                }
                
                databaseTableVector.add(new FieldData(propertyName, rangeName, false, ""));
            }
//System.out.println("37");
            //initialize field
            //========================================================================================
            if (!owlFromMpiro) {
                String[] allAttributesEnglish = {
                    "SELECTION", "Verb", "Voice", "Tense", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub",
                    "Referobj",
                    "Caseobj", "Aggreg"};
                String[] allAttributesItalianGreek = {
                    "SELECTION", "Verb", "Voice", "Tense", "Aspect", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub",
                    "Casesub",
                    "Referobj", "Caseobj", "Aggreg"};
                String[] allValuesEnglish = {
                    "NoMicroPlanning", "Choose a verb identifier", "Active", "Past", "", "", "", "", "Indicative", "revFalse", "Auto", "Nominative",
                    "Auto", "Accusative", "aggTrue"};
                String[] allValuesItalianGreek = {
                    "NoMicroPlanning", "Choose a verb identifier", "Active", "Past", "Simple", "", "", "", "", "Indicative", "revFalse", "Auto",
                    "Nominative", "Auto", "Accusative", "aggTrue"};
                
                for (int k = 1; k < 6; k++) {
                    String microplanNumber = new Integer(k).toString();
                    for (int i = 0; i < allAttributesEnglish.length; i++) {
                        Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, allAttributesEnglish[i], "English",
                                allValuesEnglish[i]);
                    }
                    
                    for (int i = 0; i < allAttributesItalianGreek.length; i++) {
                        Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, allAttributesItalianGreek[i], "Italian",
                                allValuesItalianGreek[i]);
                        Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, allAttributesItalianGreek[i], "Greek",
                                allValuesItalianGreek[i]);
                    }
//System.out.println("38");
                    // adding the default value "0" as appropriateness for every user
                    Vector usersVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
                    Enumeration usersVectorEnum = usersVector.elements();
                    while (usersVectorEnum.hasMoreElements()) {
                        String user = usersVectorEnum.nextElement().toString();
                        Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, user, "English", "0");
                        Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, user, "Italian", "0");
                        Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, user, "Greek", "0");
                    }
                }//System.out.println("38.5");//System.out.println("prop"+propertyName);//System.out.println("typ"+typeName);
                //System.out.println("ssssssss"+propertyName);
                Mpiro.win.struc.addFieldInUserModelHashtable(propertyName, typeName);
            }
            //========================================================================================
        }
//System.out.println("39");
    }
    
    //create entities
    public static void createEntities(String typeName, OntClass ontClass,String inst, OntClass Parent, boolean direct) {
        // //System.out.println("11");
        ExtendedIterator instances = ontClass.listInstances();
        while (instances.hasNext()) {
            Individual individual = (Individual) instances.next();////System.out.println("12");
            //new entity node
            NodeVector nv = new NodeVector(typeName, getNameInELEON(individual));
            String occur="";
            if (inst!="") occur="_occur";
            Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(individual)+occur+inst, nv);
            ////System.out.println( individual));
            try{
                // if (!owlFromMpiro)
                Mpiro.win.struc.addEntityInUserModelHashtable(getNameInELEON(individual));
            }catch ( Exception t){};
            // //System.out.println("13");
            //if there is a label put it in the name property
            String grName = null; //individual.getLabel("GRC");
            String enName = null; //individual.getLabel("EN");
            String itName = null; //individual.getLabel("IT");
            String indName = individual.getLabel(null);////System.out.println("13.5");
            if (indName != null) {
                if (grName == null) grName = indName;
                if (enName == null) enName = indName;
                if (itName == null) itName = indName;
            }////System.out.println("14");
            if (grName != null) {
                FieldData tempFieldData = (FieldData) nv.getGreekFieldsVector().get(1);
                tempFieldData.set(1, grName);
                tempFieldData.m_filler = grName;
            }
            if (enName != null) {
                FieldData tempFieldData = (FieldData) nv.getEnglishFieldsVector().get(1);
                tempFieldData.set(1, enName);
                tempFieldData.m_filler = enName;
            }
            if (itName != null) {
                FieldData tempFieldData = (FieldData) nv.getItalianFieldsVector().get(1);
                tempFieldData.set(1, itName);
                tempFieldData.m_filler = itName;
            } //end set label to name property
            //  //System.out.println("15");
            createEntityProperties(nv, individual, ontClass, Parent, direct);
        }
    }
    
    public static void createEntities(String typeName, OntClass ontClass,String inst) {
        // //System.out.println("11");
        ExtendedIterator instances = ontClass.listInstances();
        while (instances.hasNext()) {
            Individual individual = (Individual) instances.next();////System.out.println("12");
            //new entity node
            NodeVector nv = new NodeVector(typeName, getNameInELEON(individual));
            String occur="";
            if (inst!="") occur="_occur";
            Mpiro.win.struc.putEntityTypeOrEntityToDB(getNameInELEON(individual)+occur+inst, nv);
            ////System.out.println( individual));
            try{
                // if (!owlFromMpiro)
                Mpiro.win.struc.addEntityInUserModelHashtable(getNameInELEON(individual));
            }catch ( Exception t){};
            // //System.out.println("13");
            //if there is a label put it in the name property
            String grName = null; //individual.getLabel("GRC");
            String enName = null; //individual.getLabel("EN");
            String itName = null; //individual.getLabel("IT");
            String indName = individual.getLabel(null);////System.out.println("13.5");
            if (indName != null) {
                if (grName == null) grName = indName;
                if (enName == null) enName = indName;
                if (itName == null) itName = indName;
            }////System.out.println("14");
            if (grName != null) {
                FieldData tempFieldData = (FieldData) nv.getGreekFieldsVector().get(1);
                tempFieldData.set(1, grName);
                tempFieldData.m_filler = grName;
            }
            if (enName != null) {
                FieldData tempFieldData = (FieldData) nv.getEnglishFieldsVector().get(1);
                tempFieldData.set(1, enName);
                tempFieldData.m_filler = enName;
            }
            if (itName != null) {
                FieldData tempFieldData = (FieldData) nv.getItalianFieldsVector().get(1);
                tempFieldData.set(1, itName);
                tempFieldData.m_filler = itName;
            } //end set label to name property
            //  //System.out.println("15");
            createEntityProperties(nv, individual, ontClass);
        }
    }
    
    
    private static void addPropertyInUsersHashtable(String propertyName,String typeName){
        if (!owlFromMpiro) {
            String[] allAttributesEnglish = {
                "SELECTION", "Verb", "Voice", "Tense", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub",
                "Referobj",
                "Caseobj", "Aggreg"};
            String[] allAttributesItalianGreek = {
                "SELECTION", "Verb", "Voice", "Tense", "Aspect", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub",
                "Casesub",
                "Referobj", "Caseobj", "Aggreg"};
            String[] allValuesEnglish = {
                "NoMicroPlanning", "Choose a verb identifier", "Active", "Past", "", "", "", "", "Indicative", "revFalse", "Auto", "Nominative",
                "Auto", "Accusative", "aggTrue"};
            String[] allValuesItalianGreek = {
                "NoMicroPlanning", "Choose a verb identifier", "Active", "Past", "Simple", "", "", "", "", "Indicative", "revFalse", "Auto",
                "Nominative", "Auto", "Accusative", "aggTrue"};
            
            for (int k = 1; k < 6; k++) {
                String microplanNumber = new Integer(k).toString();
                for (int i = 0; i < allAttributesEnglish.length; i++) {
                    Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, allAttributesEnglish[i], "English",
                            allValuesEnglish[i]);
                }
                
                for (int i = 0; i < allAttributesItalianGreek.length; i++) {
                    Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, allAttributesItalianGreek[i], "Italian",
                            allValuesItalianGreek[i]);
                    Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, allAttributesItalianGreek[i], "Greek",
                            allValuesItalianGreek[i]);
                }
//System.out.println("38");
                // adding the default value "0" as appropriateness for every user
                Vector usersVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
                Enumeration usersVectorEnum = usersVector.elements();
                while (usersVectorEnum.hasMoreElements()) {
                    String user = usersVectorEnum.nextElement().toString();
                    Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, user, "English", "0");
                    Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, user, "Italian", "0");
                    Mpiro.win.struc.updateHashtable(typeName, microplanNumber, propertyName, user, "Greek", "0");
                }
            }//System.out.println("38.5");//System.out.println("prop"+propertyName);//System.out.println("typ"+typeName);
            //System.out.println("ssssssss"+propertyName);
            Mpiro.win.struc.addFieldInUserModelHashtable(propertyName, typeName);
        }
    }
    
    
    
    //create entity values for properties
    //nodeVector � �������� ��� mpiro ���� ����� �� ��������� �� ��������� ���
    //individual � ���������� �������� ��� OWL ����������
    //ontClass � ����� ���� ����� ������� � �������� individual
    public static void createEntityProperties(NodeVector nodeVector, Individual individual, OntClass superClass, OntClass Parent, boolean direct) {
        //    //System.out.println("40");
        Vector independentFieldsVector = nodeVector.getIndependentFieldsVector();
        Vector englishFieldsVector = nodeVector.getEnglishFieldsVector();
        Vector italianFieldsVector = nodeVector.getItalianFieldsVector();
        Vector greekFieldsVector = nodeVector.getGreekFieldsVector();
        
        //get all properties (and those that are inherited)
        Vector properties = getAllOntProperties(superClass);////System.out.println(properties.toString());
        while (!properties.isEmpty()) {
            OntProperty property = (OntProperty) properties.remove(0);
            boolean isDirect=false;
            ExtendedIterator directProperties= superClass.listDeclaredProperties(true);
            while (directProperties.hasNext()){
                if (directProperties.next()==property) isDirect=true;
            }
            if ((property.hasDomain(Parent)) || (direct && isDirect ))   {
                if (property.isObjectProperty()) {
                    //get the tree node of the range to see if it is checked
                    if(property.getRange()!=null){
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) classToTreeNode.get(property.getRange());
                        
                        IconOwlData treeObject = ( (IconOwlData) treeNode.getUserObject());
                        
                        //if it is checked insert value else do not
                        if (treeObject.m_icon == DialogClassesToImport.ICON_OWLCLASSCHECKED) {
                            NodeIterator propValues = individual.listPropertyValues(property);
                            String values = "";
                            while (propValues.hasNext()) {
                                RDFNode value = propValues.nextNode();
                                Resource resource = (Resource) value;
                                values = values + getNameInELEON(resource) + " ";
                            }
                            
                            if (values.trim().equals("")) {
                                boolean approved = true;
                                
                                OntClass tempSuperClass;
                                if (property.getDomain() != null && !property.getDomain().equals( ( (OntModel) superClass.getModel()).getProfile().THING())) {
                                    tempSuperClass = property.getDomain().asClass();
                                    ExtendedIterator superClasses = tempSuperClass.listSuperClasses(true);
                                    
                                    while (superClasses.hasNext()) {
                                        tempSuperClass = (OntClass) superClasses.next();
                                        if (tempSuperClass.isRestriction())
                                            if (tempSuperClass.asRestriction().isMaxCardinalityRestriction()) {
                                            MaxCardinalityRestriction restriction = tempSuperClass.asRestriction().asMaxCardinalityRestriction();
                                            if (restriction.getOnProperty().equals(property) && restriction.getMaxCardinality() == 1) {
                                                approved = false;
                                            }
                                            }
                                    }
                                    
                                    if (!approved)
                                        values = "Select a \"" + getNameInELEON(property.getRange().asClass()) + "\"";
                                    else
                                        values = "Select multiple .....";
                                }
                            }
                            independentFieldsVector.add(3, new FieldData(getNameInELEON(property), values.trim()));
                        }
                    }
                } else if (property.isDatatypeProperty()) {
                    NodeIterator propValues = individual.listPropertyValues(property);
                    String enValues = "";
                    String itValues = "";
                    String grValues = "";
                    String indValues = "";
                    String language = "";
                    while (propValues.hasNext()) {
                        RDFNode value = propValues.nextNode();
                        Literal literal = (Literal) value;
                        language = literal.getLanguage();
                        if (language.equalsIgnoreCase("EN"))
                            enValues = enValues + literal.getString();
                        else if (language.equalsIgnoreCase("IT"))
                            itValues = itValues + literal.getString();
                        else if (language.equalsIgnoreCase("GRC"))
                            grValues = grValues + literal.getString();
                        else
                            indValues = indValues + literal.getString() + " ";
                    }
                    String rangeName;
                    try {
                        rangeName = getNameInELEON(property.getRange());
                    } catch(java.lang.NullPointerException npe) {
                        continue;
                    }
                    
                    if (getNameInELEON(property).equals("title")) {
                        FieldData tempFieldData = (FieldData) englishFieldsVector.get(0);
                        tempFieldData.set(1, enValues);
                        tempFieldData.m_filler = enValues;
                        
                        tempFieldData = (FieldData) italianFieldsVector.get(0);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                        
                        tempFieldData = (FieldData) greekFieldsVector.get(0);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("name")) {
                        FieldData tempFieldData = (FieldData) englishFieldsVector.get(1);
                        tempFieldData.set(1, enValues);
                        tempFieldData.m_filler = enValues;
                        
                        tempFieldData = (FieldData) italianFieldsVector.get(1);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                    } else if (getNameInELEON(property).equals("shortname")) {
                        FieldData tempFieldData = (FieldData) englishFieldsVector.get(2);
                        tempFieldData.set(1, enValues);
                        tempFieldData.m_filler = enValues;
                        
                        tempFieldData = (FieldData) italianFieldsVector.get(3);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                    } else if (getNameInELEON(property).equals("notes")) {
                        FieldData tempFieldData = (FieldData) englishFieldsVector.get(3);
                        tempFieldData.set(1, enValues);
                        tempFieldData.m_filler = enValues;
                        
                        tempFieldData = (FieldData) italianFieldsVector.get(5);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                        
                        tempFieldData = (FieldData) greekFieldsVector.get(9);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("gender")) {
                        FieldData tempFieldData = (FieldData) englishFieldsVector.get(4);
                        tempFieldData.set(1, enValues);
                        tempFieldData.m_filler = enValues;
                    } else if (getNameInELEON(property).equals("number")) {
                        FieldData tempFieldData = (FieldData) englishFieldsVector.get(5);
                        tempFieldData.set(1, enValues);
                        tempFieldData.m_filler = enValues;
                        
                        tempFieldData = (FieldData) italianFieldsVector.get(6);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                        
                        tempFieldData = (FieldData) greekFieldsVector.get(10);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("gender-name")) {
                        FieldData tempFieldData = (FieldData) italianFieldsVector.get(2);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                        
                        tempFieldData = (FieldData) greekFieldsVector.get(4);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("gender-shortname")) {
                        FieldData tempFieldData = (FieldData) italianFieldsVector.get(4);
                        tempFieldData.set(1, itValues);
                        tempFieldData.m_filler = itValues;
                        
                        tempFieldData = (FieldData) greekFieldsVector.get(8);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("name-nominative")) {
                        FieldData tempFieldData = (FieldData) greekFieldsVector.get(1);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("name-genitive")) {
                        FieldData tempFieldData = (FieldData) greekFieldsVector.get(2);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("name-accusative")) {
                        FieldData tempFieldData = (FieldData) greekFieldsVector.get(3);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("shortname-nominative")) {
                        FieldData tempFieldData = (FieldData) greekFieldsVector.get(5);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("shortname-genitive")) {
                        FieldData tempFieldData = (FieldData) greekFieldsVector.get(6);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("shortname-accusative")) {
                        FieldData tempFieldData = (FieldData) greekFieldsVector.get(7);
                        tempFieldData.set(1, grValues);
                        tempFieldData.m_filler = grValues;
                    } else if (getNameInELEON(property).equals("images")) {
                        FieldData tempFieldData = (FieldData) independentFieldsVector.get(2);
                        tempFieldData.set(1, indValues.trim());
                        tempFieldData.m_filler = indValues.trim();
                    } else if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("mpiroDate") || rangeName.equalsIgnoreCase("mpiroDimension") ||
                            rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") || rangeName.equalsIgnoreCase("negativeInteger") ||
                            rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                            rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                            rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                            rangeName.equalsIgnoreCase("float") || rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("decimal") ||
                            rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                        independentFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                    } else if (indValues.equals("")) {
                        englishFieldsVector.add(new FieldData(getNameInELEON(property), enValues.trim()));
                        italianFieldsVector.add(new FieldData(getNameInELEON(property), itValues.trim()));
                        greekFieldsVector.add(new FieldData(getNameInELEON(property), grValues.trim()));
                    } else if (!getNameInELEON(property.getDomain()).equals("Basic-Entity-Types")) {
                        englishFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                        italianFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                        greekFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                    }
                }
            }
        }
    }
    
    public static void createEntityProperties(NodeVector nodeVector, Individual individual, OntClass superClass) {
        //    //System.out.println("40");
        Vector independentFieldsVector = nodeVector.getIndependentFieldsVector();
        Vector englishFieldsVector = nodeVector.getEnglishFieldsVector();
        Vector italianFieldsVector = nodeVector.getItalianFieldsVector();
        Vector greekFieldsVector = nodeVector.getGreekFieldsVector();
        
        //get all properties (and those that are inherited)
        Vector properties = getAllOntProperties(superClass);////System.out.println(properties.toString());
        while (!properties.isEmpty()) {
            OntProperty property = (OntProperty) properties.remove(0);
            
            if (property.isObjectProperty()) {
                //get the tree node of the range to see if it is checked
                if(property.getRange()!=null){
                    OntResource temp=property.getRange();
                    IconOwlData treeObject=(IconOwlData) new IconOwlData(UsersPanel.ICON_USER, "NewUserType", superClass);
                    if(!temp.isAnon()){
                        
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) classToTreeNode.get(property.getRange());
                        
                        treeObject = ( (IconOwlData) treeNode.getUserObject());
                    } else
                        
                        treeObject.m_icon = DialogClassesToImport.ICON_OWLCLASSCHECKED;
                    //if it is checked insert value else do not
                    if (treeObject.m_icon == DialogClassesToImport.ICON_OWLCLASSCHECKED) {
                        NodeIterator propValues = individual.listPropertyValues(property);
                        String values = "";
                        while (propValues.hasNext()) {
                            RDFNode value = propValues.nextNode();
                            Resource resource = (Resource) value;
                            values = values + getNameInELEON(resource) + " ";
                        }
                        
                        if (values.trim().equals("")) {
                            boolean approved = true;
                            
                            OntClass tempSuperClass;
                            if (property.getDomain() != null && !property.getDomain().equals( ( (OntModel) superClass.getModel()).getProfile().THING())) {
                                tempSuperClass = property.getDomain().asClass();
                                ExtendedIterator superClasses = tempSuperClass.listSuperClasses(true);
                                
                                while (superClasses.hasNext()) {
                                    tempSuperClass = (OntClass) superClasses.next();
                                    if (tempSuperClass.isRestriction())
                                        if (tempSuperClass.asRestriction().isMaxCardinalityRestriction()) {
                                        MaxCardinalityRestriction restriction = tempSuperClass.asRestriction().asMaxCardinalityRestriction();
                                        if (restriction.getOnProperty().equals(property) && restriction.getMaxCardinality() == 1) {
                                            approved = false;
                                        }
                                        }
                                }
                                
                                if (!approved)
                                    values = "Select a \"" + getNameInELEON(property.getRange().asClass()) + "\"";
                                else
                                    values = "Select multiple .....";
                            }
                        }
                        independentFieldsVector.add(3, new FieldData(getNameInELEON(property), values.trim()));
                    }
                }
            } else if (property.isDatatypeProperty()) {
                NodeIterator propValues = individual.listPropertyValues(property);
                String enValues = "";
                String itValues = "";
                String grValues = "";
                String indValues = "";
                String language = "";
                while (propValues.hasNext()) {
                    RDFNode value = propValues.nextNode();
                    Literal literal = (Literal) value;
                    language = literal.getLanguage();
                    if (language.equalsIgnoreCase("EN"))
                        enValues = enValues + literal.getString();
                    else if (language.equalsIgnoreCase("IT"))
                        itValues = itValues + literal.getString();
                    else if (language.equalsIgnoreCase("GRC"))
                        grValues = grValues + literal.getString();
                    else
                        indValues = indValues + literal.getString() + " ";
                }
                String rangeName;
                try {
                    rangeName = getNameInELEON(property.getRange());
                } catch(java.lang.NullPointerException npe) {
                    continue;
                }
                
                if (getNameInELEON(property).equals("title")) {
                    FieldData tempFieldData = (FieldData) englishFieldsVector.get(0);
                    tempFieldData.set(1, enValues);
                    tempFieldData.m_filler = enValues;
                    
                    tempFieldData = (FieldData) italianFieldsVector.get(0);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                    
                    tempFieldData = (FieldData) greekFieldsVector.get(0);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("name")) {
                    FieldData tempFieldData = (FieldData) englishFieldsVector.get(1);
                    tempFieldData.set(1, enValues);
                    tempFieldData.m_filler = enValues;
                    
                    tempFieldData = (FieldData) italianFieldsVector.get(1);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                } else if (getNameInELEON(property).equals("shortname")) {
                    FieldData tempFieldData = (FieldData) englishFieldsVector.get(2);
                    tempFieldData.set(1, enValues);
                    tempFieldData.m_filler = enValues;
                    
                    tempFieldData = (FieldData) italianFieldsVector.get(3);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                } else if (getNameInELEON(property).equals("notes")) {
                    FieldData tempFieldData = (FieldData) englishFieldsVector.get(3);
                    tempFieldData.set(1, enValues);
                    tempFieldData.m_filler = enValues;
                    
                    tempFieldData = (FieldData) italianFieldsVector.get(5);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                    
                    tempFieldData = (FieldData) greekFieldsVector.get(9);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("gender")) {
                    FieldData tempFieldData = (FieldData) englishFieldsVector.get(4);
                    tempFieldData.set(1, enValues);
                    tempFieldData.m_filler = enValues;
                } else if (getNameInELEON(property).equals("number")) {
                    FieldData tempFieldData = (FieldData) englishFieldsVector.get(5);
                    tempFieldData.set(1, enValues);
                    tempFieldData.m_filler = enValues;
                    
                    tempFieldData = (FieldData) italianFieldsVector.get(6);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                    
                    tempFieldData = (FieldData) greekFieldsVector.get(10);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("gender-name")) {
                    FieldData tempFieldData = (FieldData) italianFieldsVector.get(2);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                    
                    tempFieldData = (FieldData) greekFieldsVector.get(4);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("gender-shortname")) {
                    FieldData tempFieldData = (FieldData) italianFieldsVector.get(4);
                    tempFieldData.set(1, itValues);
                    tempFieldData.m_filler = itValues;
                    
                    tempFieldData = (FieldData) greekFieldsVector.get(8);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("name-nominative")) {
                    FieldData tempFieldData = (FieldData) greekFieldsVector.get(1);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("name-genitive")) {
                    FieldData tempFieldData = (FieldData) greekFieldsVector.get(2);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("name-accusative")) {
                    FieldData tempFieldData = (FieldData) greekFieldsVector.get(3);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("shortname-nominative")) {
                    FieldData tempFieldData = (FieldData) greekFieldsVector.get(5);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("shortname-genitive")) {
                    FieldData tempFieldData = (FieldData) greekFieldsVector.get(6);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("shortname-accusative")) {
                    FieldData tempFieldData = (FieldData) greekFieldsVector.get(7);
                    tempFieldData.set(1, grValues);
                    tempFieldData.m_filler = grValues;
                } else if (getNameInELEON(property).equals("images")) {
                    FieldData tempFieldData = (FieldData) independentFieldsVector.get(2);
                    tempFieldData.set(1, indValues.trim());
                    tempFieldData.m_filler = indValues.trim();
                } else if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("mpiroDate") || rangeName.equalsIgnoreCase("mpiroDimension") ||
                        rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") || rangeName.equalsIgnoreCase("negativeInteger") ||
                        rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                        rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                        rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                        rangeName.equalsIgnoreCase("float") || rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("decimal") ||
                        rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                    independentFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                } else if (indValues.equals("")) {
                    englishFieldsVector.add(new FieldData(getNameInELEON(property), enValues.trim()));
                    italianFieldsVector.add(new FieldData(getNameInELEON(property), itValues.trim()));
                    greekFieldsVector.add(new FieldData(getNameInELEON(property), grValues.trim()));
                } else if (!getNameInELEON(property.getDomain()).equals("Basic-Entity-Types")) {
                    englishFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                    italianFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                    greekFieldsVector.add(new FieldData(getNameInELEON(property), indValues));
                }
            }
        }
        
    }
    
    public static Vector getAllOntProperties(OntClass ontClass) {
        //   //System.out.println("50");
        Vector superClasses = new Vector();
        Vector properties = new Vector();
        superClasses.add(0, ontClass);
        
        while (!superClasses.isEmpty()) {
            ontClass = (OntClass) superClasses.remove(0);
            ExtendedIterator tempProperties = ontClass.listDeclaredProperties();
            while (tempProperties.hasNext()) {
                Object prop = tempProperties.next();
                if (!properties.contains(prop))
                    properties.add(0, prop);
            }
            
            ExtendedIterator tempClasses = ontClass.listSuperClasses(true);
            while (tempClasses.hasNext()) {
                Object cla = tempClasses.next();
                if (!superClasses.contains(cla))
                    superClasses.add(0, cla);
            }
        }
        return properties;
    }
    
    /**
     * Answer an iterator over the classes we will use as the roots of the depicted
     * hierarchy.  We use named classes that either have Thing as a direct super-class,
     * or which have no declared super-classes.  The first condition is helpful if
     * using a reasoner, the second otherwise.
     * @param m A model
     * @return An iterator over the named class hierarchy roots in m
     */
    protected static Iterator rootClasses(OntModel m) {
        List roots = new ArrayList();
        
        if (m.getOntClass(mpiroPath + "Basic-Entity-Types") == null) {
            for (Iterator i = m.listClasses(); i.hasNext(); ) {
                OntClass c = (OntClass) i.next();
                // too confusing to list all the restrictions as root classes
                //      if (c.isAnon()) {
                //        continue;
                //  }
                // if (c.isIntersectionClass()) continue;
                
                if (c.hasSuperClass(m.getProfile().THING(), true)) {
                    // this class is directly descended from Thing
                    roots.add(c);
                } else if (c.getCardinality(m.getProfile().SUB_CLASS_OF()) == 0) {
                    // this class has no super-classes (can occur if we're not using the reasoner)
                    roots.add(c);
                }
            }
        } else {
            OntClass basicClass = m.getOntClass(mpiroPath + "Basic-Entity-Types");
            ExtendedIterator upperModelClasses = basicClass.listSubClasses();
            while (upperModelClasses.hasNext()) {
                OntClass upperModelClass = (OntClass) upperModelClasses.next();
                ExtendedIterator classesToadd = upperModelClass.listSubClasses();
                while (classesToadd.hasNext())
                    //  {  OntClass test=(OntClass)classesToadd.next();
                    //    if(!(test.isIntersectionClass()))
                    roots.add(classesToadd.next());
                // }
            }
        }
        
        return roots.iterator();
    }
    
    /**
     *  Add a class to the tree and recursevely add all it's children
     */
    protected static void addClassToTree(DefaultMutableTreeNode treeNodeParent, OntClass cls, List occurs, int depth) {
        // //System.out.println("80");
        String clsName = getNameInELEON(cls);
        DefaultMutableTreeNode currentTreeNode;
        
        //if class is not anonymous and is not the owl:Thing class add it to the tree
        if (clsName != null && !cls.equals( ( (OntModel) cls.getModel()).getProfile().THING())) {
            //create a tree node and add it to the tree
            currentTreeNode = new DefaultMutableTreeNode(new IconOwlData(DialogClassesToImport.ICON_OWLCLASSCHECKED, clsName, cls));
            treeNodeParent.add(currentTreeNode);
            //add in the hashtable the class and the corresponding treenode
            classToTreeNode.put(cls, currentTreeNode);
        } else //the class must not be inserted but add it's children
            currentTreeNode = treeNodeParent;
        
        // recurse to the next level down to add the child classes
        if (cls.canAs(OntClass.class) && !occurs.contains(cls)) { // if the class has not benn inserted
            for (Iterator i = cls.listSubClasses(true); i.hasNext(); ) {
                OntClass sub = (OntClass) i.next();
                
                // we push this expression on the occurs list before we recurse
                occurs.add(cls);
                addClassToTree(currentTreeNode, sub, occurs, depth + 1);
                occurs.remove(cls);
            }
        }
    }
    
    public static boolean classIsAChild(OntClass candidate, ArrayList parentClasses) {
        for (int i = 0; i < parentClasses.size(); i++) {
            if (candidate.hasSuperClass( (OntClass) parentClasses.get(i))) {
                return true;
            }
        }
        return false; // Didn't find any super-classes in arraylist
    }
    
    
    
    
    public static void readUserModellingRDF(String path) throws Exception {
        //System.out.println("90");
        // LexiconPanel.addNoun("ggggggg");
        FileInputStream input;//System.out.println("90.3");
        try {
            input = new FileInputStream(path+"//UserModelling.rdf");
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return;
        }
        
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(input);
        
        if(Mpiro.win.struc.existsUser("NewUserType"))
            Mpiro.win.struc.removeUser("NewUserType");
        //QueryProfileHashtable.mainUsersHashtable.remove("NewUserType");
        
        
        NodeList userTypes=doc.getElementsByTagName("owlnl:UserType");
        for(int i=0;i<userTypes.getLength();i++){
            Node type=userTypes.item(i);
            
            Vector userVector = new Vector();
            userVector.setSize(4);
            //userVector.add(type.getAttributes().item(0).getNodeValue());
            NodeList typeCharacteristics=type.getChildNodes();
            for(int h=0;h<typeCharacteristics.getLength();h++){
                Node nextChar=typeCharacteristics.item(h);
                //  String test1=nextChar.getTextContent();
                // String test2=nextChar.getNodeValue();
                //String test3=nextChar.getNodeName();
                //String test4=nextChar.get
                if(nextChar.getNodeName().equalsIgnoreCase("owlnl:MaxFactsPerSentence"))
                    // userVector.set()
                    userVector.set(0, nextChar.getTextContent());
                if(nextChar.getNodeName().equalsIgnoreCase("owlnl:FactsPerPage"))
                    userVector.set(1, nextChar.getTextContent());
                if(nextChar.getNodeName().equalsIgnoreCase("owlnl:SynthesizerVoice"))
                    userVector.set(3,nextChar.getTextContent());
            }
            userVector.set(2, "4");
            String username=type.getAttributes().item(0).getNodeValue();
            Mpiro.win.struc.updateIndependentLexiconHashtable(username, "", "ADD");
            Mpiro.win.struc.updateAppropriatenessValuesInMicroplanningOfFields(username, "", "ADD");
            Mpiro.win.struc.addUserInUserModelHashtable(username);//==
            //QueryProfileHashtable.addUserInUserModelStoryHashtable(username);//==
            Mpiro.win.struc.addUserInPropertiesHashtable(username);
            //    userVector.add(userNodeChilds.item(1).getFirstChild().getNodeValue());
            ///  userVector.add(userNodeChilds.item(2).getFirstChild().getNodeValue());
            // userVector.add(userNodeChilds.item(3).getFirstChild().getNodeValue());
            Mpiro.win.struc.putUserOrRobotInMainUsersHashtable(username, new User(userVector));
        }
        
        NodeList propInterestsRepetitions=doc.getElementsByTagName("owlnl:Property");
        for(int i=0;i<propInterestsRepetitions.getLength();i++){
            NodeList children=propInterestsRepetitions.item(i).getChildNodes();
            String property=propInterestsRepetitions.item(i).getAttributes().item(0).getNodeValue().split("#")[1];
            if( !property.equalsIgnoreCase("title")
            && !property.equalsIgnoreCase("name")
            && !property.equalsIgnoreCase("shortname")
            && !property.equalsIgnoreCase("notes")
            && !property.equalsIgnoreCase("gender")
            && !property.equalsIgnoreCase("number")
            && !property.equalsIgnoreCase("gender-name")
            && !property.equalsIgnoreCase("gender-shortname")
            && !property.equalsIgnoreCase("name-nominative")
            && !property.equalsIgnoreCase("name-genitive")
            && !property.equalsIgnoreCase("name-accusative")
            && !property.equalsIgnoreCase("shortname-nominative")
            && !property.equalsIgnoreCase("shortname-genitive")
            && !property.equalsIgnoreCase("shortname-accusative")
            && !property.equalsIgnoreCase( "images")) {
                for(int j=0;j<children.getLength();j++){
                    Node child=children.item(j);
                    if (child.getNodeName().equalsIgnoreCase("owlnl:DPInterestRepetitions")){
                        NodeList values=child.getChildNodes();
                        Hashtable users;
                        //   try {
                        System.out.println(property);
                        users = (Hashtable) ((Vector) Mpiro.win.struc.getProperty(property)).elementAt(12);
                        //   } catch(java.lang.NullPointerException npe) {
                        //       Vector temp=(Vector) Mpiro.win.struc.getProperty(property);
                        //        System.out.println("dddddd");
                        //    }
                        //users = (Hashtable) ((Vector) Mpiro.win.struc.getProperty(property)).elementAt(12);
                        String usertype=new String();
                        String interest=new String();
                        String repetitions=new String();
                        for(int k=0;k<values.getLength();k++){
                            
                            Node nextValue=values.item(k);
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                                usertype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:InterestValue"))
                                interest=nextValue.getTextContent();
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                                repetitions=nextValue.getTextContent();
                            
                            // System.out.print("ddd");
                        }
                        Vector v=new Vector();
                        v.add(interest);
                        v.add("3");
                        v.add(repetitions);
                        users.put(usertype,v);
                    } else{
                        NodeList values=child.getChildNodes();
                        String usertype=new String();
                        String interest=new String();
                        String repetitions=new String();
                        String classOrEntityName=new String();
                        Hashtable np=new Hashtable();
                        if(!Mpiro.win.struc.mainUserModelHashtableContainsProperty(property))
                            Mpiro.win.struc.putPropertyInMainUserModelHashtable(property, new Hashtable());
                        np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(property);
                        
                        for(int k=0;k<values.getLength();k++){
                            
                            Node nextValue=values.item(k);
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                                usertype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forOwlClass")||nextValue.getNodeName().equalsIgnoreCase("owlnl:forInstance"))
                                classOrEntityName=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:InterestValue"))
                                interest=nextValue.getTextContent();
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                                repetitions=nextValue.getTextContent();
                            
                            // System.out.print("ddd");
                        }
                        //Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(classOrEntityName);
                        Hashtable forClass=new Hashtable();
                        if(!np.containsKey(classOrEntityName))
                            np.put(classOrEntityName,new Hashtable());
                        forClass=(Hashtable) np.get(classOrEntityName);
                        
                        
                        Vector v=new Vector();
                        v.add(interest);
                        v.add("3");
                        v.add(repetitions);
                        forClass.put(usertype,v);
                    }
                }
            }
        }
        
        NodeList microplans=doc.getElementsByTagName("owlnl:MicroplanApprop");
        for(int i=0;i<microplans.getLength();i++){
            Node microplan=microplans.item(i);
            String microplanName=microplan.getAttributes().item(0).getNodeValue().split("#")[1];
            NodeList values=microplan.getChildNodes();
            String usertype=new String();
            String appropValue=new String();
            for(int k=0;k<values.getLength();k++){
                NodeList valuesForUserType=values.item(k).getChildNodes();
                for(int m=0;m<valuesForUserType.getLength();m++){
                    Node nextValue=valuesForUserType.item(m);
                    if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                        usertype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                    if(nextValue.getNodeName().equalsIgnoreCase("owlnl:AppropValue"))
                        appropValue=nextValue.getTextContent();
                    
                    
                    //  System.out.print("ddd");
                }
                String[] nameSplitted=microplanName.split("-");
                String propertyName=new String();
                for(int g=nameSplitted.length-3;g>-1;g--){
                    if(g==nameSplitted.length-3)
                        propertyName=nameSplitted[g];
                    else
                        propertyName=nameSplitted[g]+"-"+propertyName;
                }
                String language="Greek";
                if(nameSplitted[nameSplitted.length-1].equalsIgnoreCase("en"))
                    language="English";
                //  System.out.println("  "+propertyName);
                if(!Mpiro.win.struc.existsProperty(propertyName)){
                    System.err.println("WARNING: property "+propertyName+" not imported");
                    continue;
                }
                Hashtable hb= (Hashtable)((Vector)Mpiro.win.struc.getProperty(propertyName)).elementAt(10);
                //     String test=nameSplitted[nameSplitted.length-2].charAt(5)+":"+propertyName+":"+usertype+":"+language;
                hb.put(nameSplitted[nameSplitted.length-2].charAt(5)+":"+propertyName+":"+usertype+":"+language,appropValue);
            }
        }
        
        NodeList classes=doc.getElementsByTagName("owlnl:owlClass");
        for(int i=0;i<classes.getLength();i++){
            String className=classes.item(i).getAttributes().item(0).getNodeValue().split("#")[1];
            NodeList children=classes.item(i).getChildNodes();
            for(int h=0;h<children.getLength();h++){
                Node child=children.item(h);
                if(child.getNodeName().equalsIgnoreCase("owlnl:DInterestRepetitions")){
                    NodeList values=child.getChildNodes();
                    String usertype=new String();
                    String interest=new String();
                    String repetitions=new String();
                    for(int k=0;k<values.getLength();k++){
                        
                        Node nextValue=values.item(k);
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                            usertype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:InterestValue"))
                            interest=nextValue.getTextContent();
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                            repetitions=nextValue.getTextContent();
                        
                        
                    }
                    Hashtable subtypeof=(Hashtable)Mpiro.win.struc.getPropertyFromMainUserModelHashtable("Subtype-of");
                    if(!subtypeof.containsKey(className))
                        subtypeof.put(className,new Hashtable());
                    Hashtable subtypeClass=(Hashtable)subtypeof.get(className);
                    Vector v=new Vector();
                    v.add(interest);
                    v.add("3");
                    v.add(repetitions);
                    subtypeClass.put(usertype,v);
                    //   System.out.print("ddd");
                }
                
                if(child.getNodeName().equalsIgnoreCase("owlnl:IInterestRepetitions")){
                    NodeList values=child.getChildNodes();
                    String usertype=new String();
                    String interest=new String();
                    String repetitions=new String();
                    String instance=new String();
                    for(int k=0;k<values.getLength();k++){
                        
                        Node nextValue=values.item(k);
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                            usertype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:InterestValue"))
                            interest=nextValue.getTextContent();
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                            repetitions=nextValue.getTextContent();
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forInstance"))
                            instance=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                        
                        
                    }
                    Hashtable subtypeof=(Hashtable)Mpiro.win.struc.getPropertyFromMainUserModelHashtable("type");
                    if(!subtypeof.containsKey(instance))
                        subtypeof.put(instance,new Hashtable());
                    Hashtable subtypeClass=(Hashtable)subtypeof.get(instance);
                    Vector v=new Vector();
                    v.add(interest);
                    v.add("3");
                    v.add(repetitions);
                    subtypeClass.put(usertype,v);
                    //  System.out.print("ddd");
                }
                
            }
        }
        
    }

     public static void readRobotModellingRDF(String path) throws Exception {
        //System.out.println("90");
        // LexiconPanel.addNoun("ggggggg");
        FileInputStream input;//System.out.println("90.3");
        try {
            input = new FileInputStream(path+"//RobotModelling.rdf");
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return;
        }
        
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(input);
        
        if(Mpiro.win.struc.existsUser("NewRobotType"))
            Mpiro.win.struc.removeUser("NewRobotType");
        //QueryProfileHashtable.mainUsersHashtable.remove("NewUserType");
        
        
        NodeList robotTypes=doc.getElementsByTagName("owlnl:RobotType");
        for(int i=0;i<robotTypes.getLength();i++){
            Node type=robotTypes.item(i);
            
            Vector robotVector = new Vector();
            robotVector.setSize(5);
            //robotVector.add(type.getAttributes().item(0).getNodeValue());
            NodeList typeCharacteristics=type.getChildNodes();
            for(int h=0;h<typeCharacteristics.getLength();h++){
                Node nextChar=typeCharacteristics.item(h);
                //  String test1=nextChar.getTextContent();
                // String test2=nextChar.getNodeValue();
                //String test3=nextChar.getNodeName();
                //String test4=nextChar.get
                if(nextChar.getNodeName().equalsIgnoreCase("owlnl:Openness"))
                    // robotVector.set()
                    robotVector.set(0, nextChar.getTextContent());
                if(nextChar.getNodeName().equalsIgnoreCase("owlnl:Conscientiousness"))
                    robotVector.set(1, nextChar.getTextContent());
                if(nextChar.getNodeName().equalsIgnoreCase("owlnl:Extraversion"))
                    robotVector.set(2,nextChar.getTextContent());
                 if(nextChar.getNodeName().equalsIgnoreCase("owlnl:Agreeableness"))
                    robotVector.set(3,nextChar.getTextContent());
                 if(nextChar.getNodeName().equalsIgnoreCase("owlnl:NaturalReactions"))
                    robotVector.set(4,nextChar.getTextContent());
            }
            String robotname=type.getAttributes().item(0).getNodeValue();
           // Mpiro.win.struc.updateIndependentLexiconHashtable(robotname, "", "ADD");
            //Mpiro.win.struc.updateAppropriatenessValuesInMicroplanningOfFields(robotname, "", "ADD");
            Mpiro.win.struc.addRobotInUserModelHashtable(robotname);//==
            //QueryProfileHashtable.addUserInUserModelStoryHashtable(robotname);//==
            Mpiro.win.struc.addRobotInPropertiesHashtable(robotname);
            //    robotVector.add(userNodeChilds.item(1).getFirstChild().getNodeValue());
            ///  robotVector.add(userNodeChilds.item(2).getFirstChild().getNodeValue());
            // robotVector.add(userNodeChilds.item(3).getFirstChild().getNodeValue());
            Mpiro.win.struc.putUserOrRobotInMainUsersHashtable(robotname, new Robot(robotVector));
        }
        
        NodeList propInterestsRepetitions=doc.getElementsByTagName("owlnl:Property");
        for(int i=0;i<propInterestsRepetitions.getLength();i++){
            NodeList children=propInterestsRepetitions.item(i).getChildNodes();
            String property=propInterestsRepetitions.item(i).getAttributes().item(0).getNodeValue().split("#")[1];
            if(!Mpiro.win.struc.existsProperty(property))
            {
                System.out.println("WARNING: Property "+property+" wasn't found in the ontology");
                continue;
            }
            if( !property.equalsIgnoreCase("title")
            && !property.equalsIgnoreCase("name")
            && !property.equalsIgnoreCase("shortname")
            && !property.equalsIgnoreCase("notes")
            && !property.equalsIgnoreCase("gender")
            && !property.equalsIgnoreCase("number")
            && !property.equalsIgnoreCase("gender-name")
            && !property.equalsIgnoreCase("gender-shortname")
            && !property.equalsIgnoreCase("name-nominative")
            && !property.equalsIgnoreCase("name-genitive")
            && !property.equalsIgnoreCase("name-accusative")
            && !property.equalsIgnoreCase("shortname-nominative")
            && !property.equalsIgnoreCase("shortname-genitive")
            && !property.equalsIgnoreCase("shortname-accusative")
            && !property.equalsIgnoreCase( "images")) {
                for(int j=0;j<children.getLength();j++){
                    Node child=children.item(j);
                    if (child.getNodeName().equalsIgnoreCase("owlnl:DPPreference")){
                        NodeList values=child.getChildNodes();
                        Hashtable robots;
                        //   try {
                        System.out.println(property);
                        
                        robots = (Hashtable) ((Vector) Mpiro.win.struc.getProperty(property)).elementAt(15);
                        //   } catch(java.lang.NullPointerException npe) {
                        //       Vector temp=(Vector) Mpiro.win.struc.getProperty(property);
                        //        System.out.println("dddddd");
                        //    }
                        
                        String robottype=new String();
                        String preference=new String();
                       // String repetitions=new String();
                        for(int k=0;k<values.getLength();k++){
                            
                            Node nextValue=values.item(k);
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                                robottype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:PreferenceValue"))
                                preference=nextValue.getFirstChild().getTextContent();
                         //   if(preference.equals("0")||preference.equals("2"))
                         //       System.out.print("dddd");
                           // if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                             //   repetitions=nextValue.getTextContent();
                            
                            // System.out.print("ddd");
                        }
                        Vector v=new Vector();
                        v.add(preference);
                       // v.add("3");
                       // v.add(repetitions);
                        robots.put(robottype,v);
                    } else{
                        NodeList values=child.getChildNodes();
                        String robottype=new String();
                        String preference=new String();
                        //String repetitions=new String();
                        String classOrEntityName=new String();
                        Hashtable np=new Hashtable();
                        if(!Mpiro.win.struc.mainUserModelHashtableContainsProperty(property))
                            Mpiro.win.struc.putPropertyInMainUserModelHashtable(property, new Hashtable());
                        np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(property);
                        
                        for(int k=0;k<values.getLength();k++){
                            
                            Node nextValue=values.item(k);
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                                robottype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forOwlClass")||nextValue.getNodeName().equalsIgnoreCase("owlnl:forInstance"))
                                classOrEntityName=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                            if(nextValue.getNodeName().equalsIgnoreCase("owlnl:PreferenceValue"))
                                preference=nextValue.getFirstChild().getTextContent();
                           //  if(preference.equals("0")||preference.equals("2"))
                           //     System.out.print("dddd");
                          //  if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                          //      repetitions=nextValue.getTextContent();
                            
                            // System.out.print("ddd");
                        }
                        //Hashtable np=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(classOrEntityName);
                        Hashtable forClass=new Hashtable();
                        if(!np.containsKey(classOrEntityName))
                            np.put(classOrEntityName,new Hashtable());
                        forClass=(Hashtable) np.get(classOrEntityName);
                        
                        
                        Vector v=new Vector();
                        v.add(preference);
                       // v.add("3");
                       // v.add(repetitions);
                        forClass.put(robottype,v);
                      //  System.out.print("Doh!");
                     ///   Hashtable temp=(Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(property);
                      //  System.out.print("ddddddd");
                    }
                }
            }
        }
        
        
        NodeList classes=doc.getElementsByTagName("owlnl:owlClass");
        for(int i=0;i<classes.getLength();i++){
            String className=classes.item(i).getAttributes().item(0).getNodeValue().split("#")[1];
            NodeList children=classes.item(i).getChildNodes();
            for(int h=0;h<children.getLength();h++){
                Node child=children.item(h);
                if(child.getNodeName().equalsIgnoreCase("owlnl:DPreference")){
                    NodeList values=child.getChildNodes();
                    String robottype=new String();
                    String preference=new String();
                    //String repetitions=new String();
                    for(int k=0;k<values.getLength();k++){
                        
                        Node nextValue=values.item(k);
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                            robottype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:PreferenceValue"))
                            preference=nextValue.getTextContent();
                       // if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                        //    repetitions=nextValue.getTextContent();
                        
                        
                    }
                    Hashtable subtypeof=(Hashtable)Mpiro.win.struc.getPropertyFromMainUserModelHashtable("Subtype-of");
                    if(!subtypeof.containsKey(className))
                        subtypeof.put(className,new Hashtable());
                    Hashtable subtypeClass=(Hashtable)subtypeof.get(className);
                    Vector v=new Vector();
                    v.add(preference);
                  //  v.add("3");
                   // v.add(repetitions);
                    subtypeClass.put(robottype,v);
                    //   System.out.print("ddd");
                }
                
                if(child.getNodeName().equalsIgnoreCase("owlnl:IPreference")){
                    NodeList values=child.getChildNodes();
                    String robottype=new String();
                    String preference=new String();
                   // String repetitions=new String();
                    String instance=new String();
                    for(int k=0;k<values.getLength();k++){
                        
                        Node nextValue=values.item(k);
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forUserType"))
                            robottype=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:PreferenceValue"))
                            preference=nextValue.getFirstChild().getTextContent();
                    //    if(nextValue.getNodeName().equalsIgnoreCase("owlnl:Repetitions"))
                    //        repetitions=nextValue.getTextContent();
                        if(nextValue.getNodeName().equalsIgnoreCase("owlnl:forInstance"))
                            instance=nextValue.getAttributes().item(0).getNodeValue().split("#")[1];
                        
                        
                    }
                    Hashtable subtypeof=(Hashtable)Mpiro.win.struc.getPropertyFromMainUserModelHashtable("type");
                    if(!subtypeof.containsKey(instance))
                        subtypeof.put(instance,new Hashtable());
                    Hashtable subtypeClass=(Hashtable)subtypeof.get(instance);
                    Vector v=new Vector();
                    v.add(preference);
                 //   v.add("3");
                //    v.add(repetitions);
                    subtypeClass.put(robottype,v);
                    //  System.out.print("ddd");
                }
                
            }
        }
        
    }
    
    public static void readMicroplansRDF(String path) throws Exception {
        // System.out.println("90");
        // LexiconPanel.addNoun("ggggggg");
        FileInputStream input;//System.out.println("90.3");
        try {
            input = new FileInputStream(path+"//Microplans.rdf");
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return;
        }
        //System.out.println("91");
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(input);
        
        NodeList properties=doc.getElementsByTagName("owlnl:Property");
        for(int i=0;i<properties.getLength();i++){
            Node property=properties.item(i);
            String propName=property.getAttributes().item(0).getNodeValue().split("#")[1];
            if(!Mpiro.win.struc.existsProperty(propName)) continue;
            Vector propVector=(Vector) Mpiro.win.struc.getProperty(propName);
            TemplateVector tv=(TemplateVector)propVector.elementAt(11);
            NodeList microplansOrOrder=property.getChildNodes();
            
            for(int j=0;j<microplansOrOrder.getLength();j++){
                if (microplansOrOrder.item(j).getNodeType() == Node.TEXT_NODE) continue;
                if(microplansOrOrder.item(j).getNodeName().equalsIgnoreCase("owlnl:Order"))
                    propVector.setElementAt(microplansOrOrder.item(j).getTextContent(),13);
                if(microplansOrOrder.item(j).getNodeName().equalsIgnoreCase("owlnl:GreekMicroplans")){
                    Hashtable greekHash=(Hashtable)tv.elementAt(2);
                    
                    char number='0';
                    NodeList greekMicros=microplansOrOrder.item(j).getChildNodes();
                    for(int k=0;k<greekMicros.getLength();k++){
                        if (greekMicros.item(k).getNodeType() == Node.TEXT_NODE) continue;
                        
                        NodeList values=greekMicros.item(k).getChildNodes();
                        Vector slotVector=new Vector();
                        for(int l=0;l<values.getLength();l++){
                            
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:MicroplanName"))
                                number=values.item(l).getTextContent().charAt(5);
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:Used")) {
                                if(values.item(l).getTextContent().equalsIgnoreCase("true"))
                                    ((Hashtable)propVector.elementAt(10)).put(number+":"+propName+":SELECTION:Greek","Template");
                                else
                                    ((Hashtable)propVector.elementAt(10)).put(number+":"+propName+":SELECTION:Greek","NoMicroplanning");
                            }
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:AggrAllowed")) {
                                if(values.item(l).getTextContent().equalsIgnoreCase("true"))
                                    greekHash.put(number+":"+propName+":Aggreg","True");
                                else
                                    greekHash.put(number+":"+propName+":Aggreg","False");
                            }
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:Slots")) {
                                Vector slotsVec=new Vector();
                                NodeList slots=values.item(l).getChildNodes();
                                if(slots.getLength()==0){
                                    Hashtable slotHash=new Hashtable();
                                    slotHash.put("SELECTION","string");
                                    slotHash.put("string","");
                                    slotVector.add(slotHash);
                                }
                                for(int m=0;m<slots.getLength();m++){
                                    if (slots.item(m).getNodeType() == Node.TEXT_NODE) continue;
                                    Hashtable slotHash=new Hashtable();
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Text")){
                                        slotHash.put("SELECTION","string");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            //   if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:voice"))
                                            //      slotHash.put("voice",elements.item(n).getTextContent());
                                            //  if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:tense"))
                                            //      slotHash.put("tense",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:Val"))
                                                slotHash.put("string",elements.item(n).getTextContent());
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Prep")){
                                        slotHash.put("SELECTION","string");
                                        slotHash.put("prep","true");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            //   if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:voice"))
                                            //      slotHash.put("voice",elements.item(n).getTextContent());
                                            //  if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:tense"))
                                            //      slotHash.put("tense",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:Val"))
                                                slotHash.put("string",elements.item(n).getTextContent());
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Verb")){
                                        slotHash.put("SELECTION","string");
                                        slotHash.put("verb","true");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:voice"))
                                                slotHash.put("voice",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:tense"))
                                                slotHash.put("tense",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:Val"))
                                                slotHash.put("string",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:pluralVal"))
                                                slotHash.put("plural",elements.item(n).getTextContent());
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Filler")){
                                        slotHash.put("SELECTION","referring");
                                        slotHash.put("semantics","Field filler");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:case"))
                                                slotHash.put("grCase",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:RETYPE")){
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_FULLNAME"))
                                                    slotHash.put("type","Name");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_PRONOUN"))
                                                    slotHash.put("type","Pronoun");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_DEF_ART"))
                                                    slotHash.put("type","Type with definite article");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_INDEF_ART"))
                                                    slotHash.put("type","Type with indefinite article");
                                            }
                                            
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Owner")){
                                        slotHash.put("SELECTION","referring");
                                        slotHash.put("semantics","Field owner");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:case"))
                                                slotHash.put("grCase",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:RETYPE")){
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_FULLNAME"))
                                                    slotHash.put("type","Name");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_PRONOUN"))
                                                    slotHash.put("type","Pronoun");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_DEF_ART"))
                                                    slotHash.put("type","Type with definite article");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_INDEF_ART"))
                                                    slotHash.put("type","Type with indefinite article");
                                            }
                                        }
                                    }
                                    slotVector.add(slotHash);
                                }
                            }
                            
                            
                        }
                        if(number!='0')
                            greekHash.put(number+":"+propName,slotVector);
                    }
                    
                    //..................
                }
                if(microplansOrOrder.item(j).getNodeName().equalsIgnoreCase("owlnl:EnglishMicroplans")){
                    Hashtable englishHash=(Hashtable)tv.elementAt(0);
                    
                    char number='0';
                    NodeList englishMicros=microplansOrOrder.item(j).getChildNodes();
                    for(int k=0;k<englishMicros.getLength();k++){
                        if (englishMicros.item(k).getNodeType() == Node.TEXT_NODE) continue;
                        NodeList values=englishMicros.item(k).getChildNodes();
                        Vector slotVector=new Vector();
                        for(int l=0;l<values.getLength();l++){
                            
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:MicroplanName"))
                                number=values.item(l).getTextContent().charAt(5);
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:Used")) {
                                if(values.item(l).getTextContent().equalsIgnoreCase("true"))
                                    ((Hashtable)propVector.elementAt(10)).put(number+":"+propName+":SELECTION:English","Template");
                                else
                                    ((Hashtable)propVector.elementAt(10)).put(number+":"+propName+":SELECTION:English","NoMicroplanning");
                            }
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:AggrAllowed")) {
                                if(values.item(l).getTextContent().equalsIgnoreCase("true"))
                                    englishHash.put(number+":"+propName+":Aggreg","True");
                                else
                                    englishHash.put(number+":"+propName+":Aggreg","False");
                            }
                            if(values.item(l).getNodeName().equalsIgnoreCase("owlnl:Slots")) {
                                Vector slotsVec=new Vector();
                                NodeList slots=values.item(l).getChildNodes();
                                if(slots.getLength()==0){
                                    Hashtable slotHash=new Hashtable();
                                    slotHash.put("SELECTION","string");
                                    slotHash.put("string","");
                                    slotVector.add(slotHash);
                                }
                                for(int m=0;m<slots.getLength();m++){
                                    if (slots.item(m).getNodeType() == Node.TEXT_NODE) continue;
                                    Hashtable slotHash=new Hashtable();
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Text")){
                                        slotHash.put("SELECTION","string");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            //   if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:voice"))
                                            //      slotHash.put("voice",elements.item(n).getTextContent());
                                            //  if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:tense"))
                                            //      slotHash.put("tense",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:Val"))
                                                slotHash.put("string",elements.item(n).getTextContent());
                                        }
                                        //slotHash.put("string",slots.item(m).getFirstChild().getTextContent());
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Prep")){
                                        slotHash.put("SELECTION","string");
                                        slotHash.put("prep","true");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            //   if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:voice"))
                                            //      slotHash.put("voice",elements.item(n).getTextContent());
                                            //  if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:tense"))
                                            //      slotHash.put("tense",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:Val"))
                                                slotHash.put("string",elements.item(n).getTextContent());
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Verb")){
                                        slotHash.put("SELECTION","string");
                                        slotHash.put("verb","true");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:voice"))
                                                slotHash.put("voice",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:tense"))
                                                slotHash.put("tense",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:Val"))
                                                slotHash.put("string",elements.item(n).getTextContent());
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Filler")){
                                        slotHash.put("SELECTION","referring");
                                        slotHash.put("semantics","Field filler");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:case"))
                                                slotHash.put("grCase",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:RETYPE")){
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_FULLNAME"))
                                                    slotHash.put("type","Name");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_PRONOUN"))
                                                    slotHash.put("type","Pronoun");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_DEF_ART"))
                                                    slotHash.put("type","Type with definite article");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_INDEF_ART"))
                                                    slotHash.put("type","Type with indefinite article");
                                            }
                                            
                                        }
                                    }
                                    if(slots.item(m).getNodeName().equalsIgnoreCase("owlnl:Owner")){
                                        slotHash.put("SELECTION","referring");
                                        slotHash.put("semantics","Field owner");
                                        NodeList elements=slots.item(m).getChildNodes();
                                        for(int n=0;n<elements.getLength();n++){
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:case"))
                                                slotHash.put("grCase",elements.item(n).getTextContent());
                                            if(elements.item(n).getNodeName().equalsIgnoreCase("owlnl:RETYPE")){
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_FULLNAME"))
                                                    slotHash.put("type","Name");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_PRONOUN"))
                                                    slotHash.put("type","Pronoun");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_DEF_ART"))
                                                    slotHash.put("type","Type with definite article");
                                                if(elements.item(n).getTextContent().equalsIgnoreCase("RE_INDEF_ART"))
                                                    slotHash.put("type","Type with indefinite article");
                                            }
                                        }
                                    }
                                    slotVector.add(slotHash);
                                }
                                
                            }
                            
                            
                        }
                        if(number!='0')
                            englishHash.put(number+":"+propName,slotVector);
                    }
                    
                    //..................
                }
            }
        }
        
    }
    
    public static void readCanned() throws Exception{
        //QueryHashtable.annotationPropertiesHashtable=new Hashtable();
        FileInputStream input;//System.out.println("90.3");
        try {
            //  input = new FileInputStream("G:\\crete\\ppp\\NLFiles-MPIRO\\Lexicon3.rdf");
            input = new FileInputStream("C:\\Lexicon.rdf");
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return;
        }
        //System.out.println("91");
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(input);
        NodeList cannedTexts=doc.getElementsByTagName("owlnl:CannedText");
        //System.out.println(String.valueOf(cannedTexts.getLength())+"       ");
        for(int i=0;i<cannedTexts.getLength();i++){
            Element cannedText=(Element)cannedTexts.item(i);
            NodeList cannedElements=cannedText.getChildNodes();
            String english="";
            String greek="";
            String userTypes="";
            for(int j=0;j<cannedElements.getLength();j++){
                try {
                    Element cannedElement;
                    try {
                        cannedElement = (Element) cannedElements.item(j);
                    } catch(java.lang.ClassCastException cce) {continue;
                    }
                    if(cannedElement.getTagName().equals("owlnl:Val")){
                        if(cannedElement.getAttribute("xml:lang").equals("el"))
                            greek=cannedElement.getFirstChild().getNodeValue();
                        
                        if(cannedElement.getAttribute("xml:lang").equals("en"))
                            english=cannedElement.getFirstChild().getNodeValue();
                    }
                    if(cannedElement.getTagName().equals("owlnl:forUserType")){
                        String usertype=cannedElement.getAttribute("rdf:resource").split("#")[1];
                        if(userTypes.equals(""))
                            userTypes=usertype;
                        else
                            userTypes=userTypes+" "+usertype;
                    }
                } catch (java.lang.NullPointerException npe) {
                    continue;
                }
            }
            
            String n="";
            if(cannedText.getAttribute("rdf:ID").contains("-canned-text"))
                n=cannedText.getAttribute("rdf:ID").split("-canned-text")[0];
            else
                n=cannedText.getAttribute("rdf:ID").substring(0,cannedText.getAttribute("rdf:ID").lastIndexOf("-"));
            
            if(!Mpiro.win.struc.existsAnnotation(n))
                Mpiro.win.struc.addAnnotation(n, new Vector());
            Vector annotationsVec=(Vector)Mpiro.win.struc.getAnnotation(n);
            annotationsVec.add(new AnnotationProperty("rdfs:comment", greek, "greek", new Boolean(true), userTypes));
            annotationsVec.add(new AnnotationProperty("rdfs:comment", english, "english", new Boolean(true), userTypes));
            
        }
    }
    
    public static void readLexiconRDF(String path) throws Exception {
        //System.out.println("90");
        // LexiconPanel.addNoun("ggggggg");
        FileInputStream input;//System.out.println("90.3");
        try {
            //  input = new FileInputStream("G:\\crete\\ppp\\NLFiles-MPIRO\\Lexicon3.rdf");
            input = new FileInputStream(path+"\\Lexicon.rdf");
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return;
        }
        //System.out.println("91");
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(input);
        //System.out.println("92");
        //noun import
        Node noun = doc.getElementsByTagName("owlnl:NPList").item(0);
        //doc.getE
        NodeList children=noun.getChildNodes();
        for(int i=0;i<children.getLength();i++){
            Node nextChild=children.item(i);
            if (nextChild.getNodeType() == Node.TEXT_NODE) continue;
            
            String  jjj=nextChild.getAttributes().item(0).getNodeValue();
            
            //  NodeList test1=nextChild.getChildNodes();
            Node language=nextChild.getFirstChild();
            while (language.getNodeType() == Node.TEXT_NODE)
                language=language.getNextSibling();
            NodeList languages=language.getChildNodes();
            Node English=languages.item(0);
            Node Greek=languages.item(0);
            for(int o=0;o<languages.getLength();o++){
                Node nextnode=languages.item(o);
                if(nextnode.getNodeName().equalsIgnoreCase("owlnl:GreekNP"))
                    Greek=nextnode;
                if(nextnode.getNodeName().equalsIgnoreCase("owlnl:EnglishNP"))
                    English=nextnode;
            }
            
            
            NodeList mappings=doc.getElementsByTagName("owlnl:hasNP");
            for(int j=0;j<mappings.getLength();j++){
                // String hhh=mappings.item(j).getAttributes().item(0).getNodeValue();
                if (mappings.item(j).getAttributes().item(0).getNodeValue().equalsIgnoreCase("#"+jjj)){
                    if(mappings.item(j).getParentNode().getNodeName().equalsIgnoreCase("owlnl:owlInstance")) {
                        String test=mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1];
                        NodeVector dbVector=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1]);
                        try{
                            if(dbVector.size()>5) continue;
                        }catch(java.lang.NullPointerException npe){continue;}
                        Vector english=dbVector.englishFieldsVector;
                        Vector greek=dbVector.greekFieldsVector;
                        
                        String num="singular";
                        NodeList englishData=doc.getElementsByTagName("test");
                        try{
                            englishData=English.getChildNodes();
                        }catch(java.lang.NullPointerException npe){continue;}
                        for(int h=0;h<englishData.getLength();h++){
                            String name=englishData.item(h).getNodeName();
                            //String value=englishData.item(h).getTextContent();
                            // String value2=englishData.item(h).getNodeValue();
                            if (name.equalsIgnoreCase("owlnl:num")){
                                Vector temp=(Vector)english.elementAt(5);
                                temp.setElementAt(englishData.item(h).getTextContent(), 1);
                                num=englishData.item(h).getTextContent();
                            }
                            if (name.equalsIgnoreCase("owlnl:singular")&&num.equals("singular")&&!(englishData.item(h).getTextContent().equalsIgnoreCase(""))){
                                Vector temp=(Vector)english.elementAt(1);
                                temp.setElementAt(englishData.item(h).getTextContent(), 1);}
                            if (name.equalsIgnoreCase("owlnl:plural")&&num.equals("plural")&&!(englishData.item(h).getTextContent().equalsIgnoreCase(""))){
                                Vector temp=(Vector)english.elementAt(1);
                                temp.setElementAt(englishData.item(h).getTextContent(), 1);}
                            if (name.equalsIgnoreCase("owlnl:gender")){
                                Vector temp=(Vector)english.elementAt(4);
                                temp.setElementAt(englishData.item(h).getTextContent(), 1);
                                if(englishData.item(h).getTextContent().equalsIgnoreCase("nonpersonal")){
                                    temp.setElementAt("neuter", 1);}
                            }}
                        
                        
                        
                        NodeList greekData=Greek.getChildNodes();
                        num="singular";
                        for(int h=0;h<greekData.getLength();h++){
                            String name=greekData.item(h).getNodeName();
                            // if (name.equalsIgnoreCase("owlnl:countable"))
                            //  currentNounGreekHashtable.put("countable", greekData.item(h).getTextContent());
                            if (name.equalsIgnoreCase("owlnl:gender")){
                                Vector temp=(Vector)greek.elementAt(4);
                                temp.setElementAt(greekData.item(h).getTextContent(),1);}
                            if (name.equalsIgnoreCase("owlnl:num")){
                                Vector temp=(Vector)greek.elementAt(10);
                                temp.setElementAt(greekData.item(h).getTextContent(), 1);
                                num=greekData.item(h).getTextContent();}
                            if (name.equalsIgnoreCase("owlnl:plural")&&num.equals("plural")){
                                Node plur=greekData.item(h).getFirstChild();
                                while (plur.getNodeType() == Node.TEXT_NODE)
                                    plur=plur.getNextSibling();
                                //  NodeList singular=sing.getChildNodes();
                                NodeList plural=plur.getChildNodes();
                                for(int k=0;k<plural.getLength();k++){
                                    String pluralForm=plural.item(k).getNodeName();
                                    if (pluralForm.equalsIgnoreCase("owlnl:nominative")&&!(plural.item(k).getTextContent().equalsIgnoreCase(""))){
                                        Vector temp=(Vector)greek.elementAt(1);
                                        temp.setElementAt(plural.item(k).getTextContent(),1);}
                                    if (pluralForm.equalsIgnoreCase("owlnl:genitive")&&!(plural.item(k).getTextContent().equalsIgnoreCase(""))){
                                        Vector temp=(Vector)greek.elementAt(2);
                                        temp.setElementAt(plural.item(k).getTextContent(),1);}
                                    if (pluralForm.equalsIgnoreCase("owlnl:accusative")&&!(plural.item(k).getTextContent().equalsIgnoreCase(""))){
                                        Vector temp=(Vector)greek.elementAt(3);
                                        temp.setElementAt(plural.item(k).getTextContent(),1);}
                                }
                                //currentNounGreekHashtable.put("grgender", greekData.item(h).getTextContent());
                            }
                            if (name.equalsIgnoreCase("owlnl:singular")&&num.equals("singular")){
                                Node sing=greekData.item(h).getFirstChild();
                                while (sing.getNodeType() == Node.TEXT_NODE)
                                    sing=sing.getNextSibling();
                                NodeList singular=sing.getChildNodes();
                                //WWWW
                                for(int k=0;k<singular.getLength();k++){
                                    String singularForm=singular.item(k).getNodeName();
                                    if (singularForm.equalsIgnoreCase("owlnl:nominative")&&!(singular.item(k).getTextContent().equalsIgnoreCase(""))){
                                        Vector temp=(Vector)greek.elementAt(1);
                                        temp.setElementAt(singular.item(k).getTextContent(),1);}
                                    if (singularForm.equalsIgnoreCase("owlnl:genitive")&&!(singular.item(k).getTextContent().equalsIgnoreCase(""))){
                                        Vector temp=(Vector)greek.elementAt(2);
                                        temp.setElementAt(singular.item(k).getTextContent(),1);}
                                    if (singularForm.equalsIgnoreCase("owlnl:accusative")&&!(singular.item(k).getTextContent().equalsIgnoreCase(""))){
                                        Vector temp=(Vector)greek.elementAt(3);
                                        temp.setElementAt(singular.item(k).getTextContent(),1);}
                                    
                                }
                                //currentNounGreekHashtable.put("grgender", greekData.item(h).getTextContent());
                            }
                            
                        }
                        
                        
                        
                        //System.out.print("");
                    } else {
                        //            String test=mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1];
                        NodeVector dbVector=new NodeVector();
                        if(Mpiro.win.struc.mainDBcontainsEntityOrEntityType(mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1]))
                            dbVector=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1]);
                        else
                            continue;
                        //   System.out.println(mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1]);
                        //    try {
                        //        if(dbVector.size()<5)continue;
                        //    } finally {
                        //        System.out.print(mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1]);
                        //   }
                        dbVector.nounVector.add(jjj);
                        Vector children1=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(mappings.item(j).getParentNode().getAttributes().item(0).getNodeValue().split("#")[1],"entity type");
                        for(int l=0;l<children1.size();l++){
                            NodeVector dbChild=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity((String)children1.elementAt(l));
                            dbChild.nounVector.add(jjj);
                        }
                        Hashtable currentNounHashtable = Mpiro.win.struc.getNounsHashtable();
                        currentNounHashtable.put(jjj, new Hashtable());
                        Hashtable currentHashtable = (Hashtable)currentNounHashtable.get(jjj);
                        currentHashtable.put("Independent",  new Hashtable());
                        currentHashtable.put("English",  new Hashtable());
                        currentHashtable.put("Italian", new Hashtable());
                        currentHashtable.put("Greek", new Hashtable());
                        
                        Hashtable currentNounIndependentHashtable = (Hashtable)currentHashtable.get("Independent");
                        Hashtable currentNounEnglishHashtable = (Hashtable)currentHashtable.get("English");
                        Hashtable currentNounItalianHashtable = (Hashtable)currentHashtable.get("Italian");
                        Hashtable currentNounGreekHashtable = (Hashtable)currentHashtable.get("Greek");
                        
                        NodeList englishData=English.getChildNodes();
                        for(int h=0;h<englishData.getLength();h++){
                            String name=englishData.item(h).getNodeName();
                            //String value=englishData.item(h).getTextContent();
                            // String value2=englishData.item(h).getNodeValue();
                            if (name.equalsIgnoreCase("owlnl:countable"))
                                currentNounEnglishHashtable.put("countable", englishData.item(h).getTextContent());
                            if (name.equalsIgnoreCase("owlnl:singular"))
                                currentNounEnglishHashtable.put("enbasetext", englishData.item(h).getTextContent());
                            if (name.equalsIgnoreCase("owlnl:plural"))
                                currentNounEnglishHashtable.put("enpluraltext", englishData.item(h).getTextContent());
                        }
                        
                        
                        NodeList greekData=Greek.getChildNodes();
                        
                        for(int h=0;h<greekData.getLength();h++){
                            String name=greekData.item(h).getNodeName();
                            if (name.equalsIgnoreCase("owlnl:countable"))
                                currentNounGreekHashtable.put("countable", greekData.item(h).getTextContent());
                            if (name.equalsIgnoreCase("owlnl:gender"))
                                currentNounGreekHashtable.put("grgender", greekData.item(h).getTextContent());
                            if (name.equalsIgnoreCase("owlnl:plural")){
                                Node plur=greekData.item(h).getFirstChild();
                                while (plur.getNodeType() == Node.TEXT_NODE)
                                    plur=plur.getNextSibling();
                                //  NodeList singular=sing.getChildNodes();
                                NodeList plural=plur.getChildNodes();
                                for(int k=0;k<plural.getLength();k++){
                                    String pluralForm=plural.item(k).getNodeName();
                                    if (pluralForm.equalsIgnoreCase("owlnl:nominative")){
                                        currentNounGreekHashtable.put("grpntext", plural.item(k).getTextContent());
                                        currentNounGreekHashtable.put("grpluraltext", plural.item(k).getTextContent());
                                    }
                                    if (pluralForm.equalsIgnoreCase("owlnl:genitive"))
                                        currentNounGreekHashtable.put("grpgtext", plural.item(k).getTextContent());
                                    if (pluralForm.equalsIgnoreCase("owlnl:accusative"))
                                        currentNounGreekHashtable.put("grpatext", plural.item(k).getTextContent());
                                }
                                //currentNounGreekHashtable.put("grgender", greekData.item(h).getTextContent());
                            }
                            if (name.equalsIgnoreCase("owlnl:singular")){
                                Node sing=greekData.item(h).getFirstChild();
                                while (sing.getNodeType() == Node.TEXT_NODE)
                                    sing=sing.getNextSibling();
                                NodeList singular=sing.getChildNodes();
                                //WWWW
                                for(int k=0;k<singular.getLength();k++){
                                    String singularForm=singular.item(k).getNodeName();
                                    if (singularForm.equalsIgnoreCase("owlnl:nominative")){
                                        currentNounGreekHashtable.put("grsntext", singular.item(k).getTextContent());
                                        currentNounGreekHashtable.put("grbasetext", singular.item(k).getTextContent());
                                    }
                                    if (singularForm.equalsIgnoreCase("owlnl:genitive"))
                                        currentNounGreekHashtable.put("grsgtext", singular.item(k).getTextContent());
                                    if (singularForm.equalsIgnoreCase("owlnl:accusative"))
                                        currentNounGreekHashtable.put("grsatext", singular.item(k).getTextContent());
                                }
                                //currentNounGreekHashtable.put("grgender", greekData.item(h).getTextContent());
                            }
                            
                        }
                        //currentNounEnglishHashtable.put("enbasetext", "");
                        //currentNounEnglishHashtable.put("countable", "Yes");
                        //currentNounEnglishHashtable.put("enpluraltext", "");
                        currentNounEnglishHashtable.put("encb", "false");
                        
                        currentNounItalianHashtable.put("itbasetext", "");
                        currentNounItalianHashtable.put("itgender", "Masculine");
                        currentNounItalianHashtable.put("countable", "Yes");
                        currentNounItalianHashtable.put("itpluraltext", "");
                        currentNounItalianHashtable.put("itcb", "false");
                        
                        //currentNounGreekHashtable.put("grbasetext", "");
                        //currentNounGreekHashtable.put("grpluraltext", "");
                        //currentNounGreekHashtable.put("grgender", "Neuter");
                        //currentNounGreekHashtable.put("countable", "Yes");
                        currentNounGreekHashtable.put("grinflection", "Inflected");
                        currentNounGreekHashtable.put("cb1", "false");
                        currentNounGreekHashtable.put("cb2", "false");
                        currentNounGreekHashtable.put("cb3", "false");
                        currentNounGreekHashtable.put("cb4", "false");
                        currentNounGreekHashtable.put("cb5", "false");
                        currentNounGreekHashtable.put("cb6", "false");
                        //currentNounGreekHashtable.put("grsntext", "");
                        //currentNounGreekHashtable.put("grsgtext", "");
                        //currentNounGreekHashtable.put("grsatext", "");
                        //currentNounGreekHashtable.put("grpntext", "");
                        //currentNounGreekHashtable.put("grpgtext", "");
                        //currentNounGreekHashtable.put("grpatext", "");
                        
        /*	Vector usersVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
                Enumeration usersVectorEnum = usersVector.elements();
                while (usersVectorEnum.hasMoreElements())
                {
                        String user = usersVectorEnum.nextElement().toString();
                        currentNounIndependentHashtable.put(user, "0");
                }*/
                        //System.out.print("");
                    }
                    break;
                }
            }
            
            //System.out.println(":)");
        }
        
        
        
        NodeList cannedTexts=doc.getElementsByTagName("owlnl:CannedText");
        for(int i=0;i<cannedTexts.getLength();i++){
            Element cannedText=(Element)cannedTexts.item(i);
            //String temp=cannedText.getAttribute("rdf:ID").toString();
            NodeList cannedElements=cannedText.getChildNodes();
            String english="";
            String greek="";
            String userTypes="";
            for(int j=0;j<cannedElements.getLength();j++){
                Element cannedElement;
                try {
                    cannedElement = (Element) cannedElements.item(j);
                } catch(java.lang.ClassCastException cce) {continue;
                }
                if(cannedElement.getTagName().equals("owlnl:Val")){
                    // System.out.println(cannedElement.getNodeValue());
                    //  System.out.println(cannedElement.getTextContent());
                    
                    if(cannedElement.getAttribute("xml:lang").equals("el"))
                        greek=cannedElement.getTextContent();
                    else
                        english=cannedElement.getTextContent();
                }
                if(cannedElement.getTagName().equals("owlnl:forUserType")){
                    //System.out.println(cannedElement.getAttribute("rdf:resource"));
                    if(cannedElement.getAttribute("rdf:resource").split("#").length<2) continue;
                    String usertype=cannedElement.getAttribute("rdf:resource").split("#")[1];
                    if(userTypes.equals(""))
                        userTypes=usertype;
                    else
                        userTypes=userTypes+" "+usertype;
                }
            }
            // Vector temp=new Vector();
            //if(Mpiro.win.struc.existsAnnotation("bestPreservedInSouth")){
            //  temp=(Vector)Mpiro.win.struc.getAnnotation("bestPreservedInSouth");}
            String n="";
            if(cannedText.getAttribute("rdf:ID").contains("-canned-text"))
                n=cannedText.getAttribute("rdf:ID").split("-canned-text")[0];
            else
                n=cannedText.getAttribute("rdf:ID").substring(0,cannedText.getAttribute("rdf:ID").lastIndexOf("-"));
            
            if(!Mpiro.win.struc.existsAnnotation(n))
                Mpiro.win.struc.addAnnotation(n, new Vector());
            Vector annotationsVec=(Vector)Mpiro.win.struc.getAnnotation(n);
            annotationsVec.add(new AnnotationProperty("rdfs:comment", greek, "greek", new Boolean(true), userTypes));
            annotationsVec.add(new AnnotationProperty("rdfs:comment", english, "english", new Boolean(true), userTypes));
            //System.out.println(temp);
        }
        
    }
    
    public static void getCannedTexts(String fileName) throws Exception{
        FileInputStream input;//System.out.println("90.3");
        try {
            input = new FileInputStream("G:\\crete\\ppp\\NLFiles-MPIRO\\Lexicon3.rdf");
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return;
        }
        //System.out.println("91");
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(input);
        NodeList cannedTexts=doc.getElementsByTagName("owlnl:CannedText");
        for(int i=0;i<cannedTexts.getLength();i++){
            Element cannedText=(Element)cannedTexts.item(i);
            NodeList cannedElements=cannedText.getChildNodes();
            String english="";
            String greek="";
            String userTypes="";
            for(int j=0;j<cannedElements.getLength();j++){
                Node cannedElement=(Node)cannedElements.item(j);
                if(cannedElement.getNodeName().equals("owlnl:Val")){
                    if(cannedElement.getAttributes().item(0).getNodeValue().equals("el"))
                        greek=cannedElement.getTextContent();
                    else
                        english=cannedElement.getTextContent();
                }
                if(cannedElement.getNodeName().equals("owlnl:forUserType")){
                    String usertype=cannedElement.getAttributes().item(0).getNodeValue().split("#")[1];
                    if(userTypes.equals(""))
                        userTypes=usertype;
                    else
                        userTypes=userTypes+" "+usertype;
                }
            }
            String n="";
            if(cannedText.getAttribute("rdf:ID").contains("-canned-text"))
                n=cannedText.getAttribute("rdf:ID").split("-canned-text")[0];
            else
                n=cannedText.getAttribute("rdf:ID").substring(0,cannedText.getAttribute("rdf:ID").lastIndexOf("-"));
            
            if(!Mpiro.win.struc.existsAnnotation(n))
                Mpiro.win.struc.addAnnotation(n, new Vector());
            Vector annotationsVec=(Vector)Mpiro.win.struc.getAnnotation(n);
            
            annotationsVec.add(new AnnotationProperty("rdfs:comment", greek, "greek", new Boolean(true), userTypes));
            annotationsVec.add(new AnnotationProperty("rdfs:comment", english, "english", new Boolean(true), userTypes));
            
        }
    }
    
//    public static void importLexiconFromXmlFile(String fileName) throws Exception {
//        //System.out.println("90");
//        FileInputStream input;//System.out.println("90.3");
//        try {
//            input = new FileInputStream(fileName);
//        } catch (IOException ioe) {
//            //ioe.printStackTrace();
//            return;
//        }
//        //System.out.println("91");
//        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        Document doc = docBuilder.parse(input);
//        //System.out.println("92");
//        //noun import
//        Node noun = doc.getElementsByTagName("Noun").item(0);
//        NodeList nounElements = noun.getChildNodes();
//        //System.out.println("93");
//        for (int i = 0; i < nounElements.getLength(); i++) {
//            Node currentNounNode = nounElements.item(i);
//            
//            Hashtable currentNounHashtable =  Mpiro.win.struc.getNounsHashtable();
//            currentNounHashtable.put(currentNounNode.getNodeName(), new Hashtable());
//            Hashtable currentHashtable = (Hashtable) currentNounHashtable.get(currentNounNode.getNodeName());
//            currentHashtable.put("Independent", new Hashtable());
//            currentHashtable.put("English", new Hashtable());
//            currentHashtable.put("Italian", new Hashtable());
//            currentHashtable.put("Greek", new Hashtable());
//            
//            Hashtable currentNounIndependentHashtable = (Hashtable) currentHashtable.get("Independent");
//            Hashtable currentNounEnglishHashtable = (Hashtable) currentHashtable.get("English");
//            Hashtable currentNounItalianHashtable = (Hashtable) currentHashtable.get("Italian");
//            Hashtable currentNounGreekHashtable = (Hashtable) currentHashtable.get("Greek");
//            
//            NodeList languagesElements = currentNounNode.getChildNodes();
//            
//            NodeList currentGreekElements = null;
//            NodeList currentEnglishElements = null;
//            NodeList currentItalianElements = null;
//            NodeList currentIndepedentElements = null;
//            
//            for (int k = 0; k < languagesElements.getLength(); k++) {
//                if (languagesElements.item(k).getNodeName().equals("Greek"))
//                    currentGreekElements = languagesElements.item(k).getChildNodes();
//                else if (languagesElements.item(k).getNodeName().equals("English"))
//                    currentEnglishElements = languagesElements.item(k).getChildNodes();
//                else if (languagesElements.item(k).getNodeName().equals("Italian"))
//                    currentItalianElements = languagesElements.item(k).getChildNodes();
//                else
//                    currentIndepedentElements = languagesElements.item(k).getChildNodes();
//            }
//            
//            for (int j = 0; j < currentGreekElements.getLength(); j++) {
//                Node greekElement = currentGreekElements.item(j);
//                if (greekElement.getFirstChild() != null)
//                    currentNounGreekHashtable.put(greekElement.getNodeName(), greekElement.getFirstChild().getNodeValue());
//                else
//                    currentNounGreekHashtable.put(greekElement.getNodeName(), "");
//            }
//            
//            for (int j = 0; j < currentEnglishElements.getLength(); j++) {
//                Node englishElement = currentEnglishElements.item(j);
//                if (englishElement.getFirstChild() != null)
//                    currentNounEnglishHashtable.put(englishElement.getNodeName(), englishElement.getFirstChild().getNodeValue());
//                else
//                    currentNounEnglishHashtable.put(englishElement.getNodeName(), "");
//            }
//            
//            for (int j = 0; j < currentItalianElements.getLength(); j++) {
//                Node italianElement = currentItalianElements.item(j);
//                if (italianElement.getFirstChild() != null)
//                    currentNounItalianHashtable.put(italianElement.getNodeName(), italianElement.getFirstChild().getNodeValue());
//                else
//                    currentNounItalianHashtable.put(italianElement.getNodeName(), "");
//            }
//            
//            for (int j = 0; j < currentIndepedentElements.getLength(); j++) {
//                Node indepedentElement = currentIndepedentElements.item(j);
//                if (indepedentElement.getFirstChild() != null)
//                    currentNounIndependentHashtable.put(indepedentElement.getNodeName(), indepedentElement.getFirstChild().getNodeValue());
//            }
//            //   //System.out.println("95");
//            /*			Vector usersVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
//               Enumeration usersVectorEnum = usersVector.elements();
//               while (usersVectorEnum.hasMoreElements()) {
//             String user = usersVectorEnum.nextElement().toString();
//             currentNounIndependentHashtable.put(user, "0");
//               }
//             */
//        }
//        //end noun import
//        
//        
//        //verb import
//        Node verb = doc.getElementsByTagName("Verb").item(0);
//        NodeList verbElements = verb.getChildNodes();
//        //    System.out.println("96");
//        for (int i = 0; i < verbElements.getLength(); i++) {
//            Node currentVerbNode = verbElements.item(i);
//            Hashtable currentVerbHashtable = Mpiro.win.struc.getVerbsHashtable();
//            currentVerbHashtable.put(currentVerbNode.getNodeName(), new Hashtable());
//            Hashtable currentHashtable = (Hashtable) currentVerbHashtable.get(currentVerbNode.getNodeName());
//            currentHashtable.put("Independent", new Hashtable());
//            currentHashtable.put("English", new Hashtable());
//            currentHashtable.put("Italian", new Hashtable());
//            currentHashtable.put("Greek", new Hashtable());
//            
//            Hashtable currentVerbIndependentHashtable = (Hashtable) currentHashtable.get("Independent");
//            Hashtable currentVerbEnglishHashtable = (Hashtable) currentHashtable.get("English");
//            Hashtable currentVerbItalianHashtable = (Hashtable) currentHashtable.get("Italian");
//            Hashtable currentVerbGreekHashtable = (Hashtable) currentHashtable.get("Greek");
//            
//            NodeList languagesElements = currentVerbNode.getChildNodes();
//            
//            NodeList currentGreekElements = null;
//            NodeList currentEnglishElements = null;
//            NodeList currentItalianElements = null;
//            NodeList currentIndepedentElements = null;
//            
//            for (int k = 0; k < languagesElements.getLength(); k++) {
//                if (languagesElements.item(k).getNodeName().equals("Greek"))
//                    currentGreekElements = languagesElements.item(k).getChildNodes();
//                else if (languagesElements.item(k).getNodeName().equals("English"))
//                    currentEnglishElements = languagesElements.item(k).getChildNodes();
//                else if (languagesElements.item(k).getNodeName().equals("Italian"))
//                    currentItalianElements = languagesElements.item(k).getChildNodes();
//                else
//                    currentIndepedentElements = languagesElements.item(k).getChildNodes();
//            }
//            
//            Vector grvTable = new Vector();
//            Vector grpTable = new Vector();
//            currentVerbGreekHashtable.put("vTable", grvTable);
//            currentVerbGreekHashtable.put("pTable", grpTable);
//            
//            Vector itvTable = new Vector();
//            Vector itpTable = new Vector();
//            currentVerbItalianHashtable.put("vTable", itvTable);
//            currentVerbItalianHashtable.put("pTable", itpTable);
//            
//            for (int j = 0; j < currentGreekElements.getLength(); j++) {
//                Node greekElement = currentGreekElements.item(j);
//                String name = greekElement.getNodeName();
//                if (name.equals("vTable")) {
//                    NamedNodeMap attributes = greekElement.getAttributes();
//                    boolean bool;
//                    if (attributes.item(5).getNodeValue().equals("true")) bool = true;
//                    else bool = false;
//                    grvTable.addElement(new LexiconFieldData(attributes.item(0).getNodeValue(), attributes.item(1).getNodeValue(),
//                            attributes.item(2).getNodeValue(), attributes.item(3).getNodeValue(), attributes.item(4).getNodeValue(),
//                            bool));
//                } else if (name.equals("pTable")) {
//                    NamedNodeMap attributes = greekElement.getAttributes();
//                    boolean bool;
//                    if (attributes.item(2).getNodeValue().equals("true")) bool = true;
//                    else bool = false;
//                    grpTable.addElement(new LexiconFieldData(attributes.item(0).getNodeValue(), attributes.item(1).getNodeValue(),
//                            bool));
//                } else if (greekElement.getFirstChild() != null)
//                    currentVerbGreekHashtable.put(name, greekElement.getFirstChild().getNodeValue());
//                else
//                    currentVerbGreekHashtable.put(name, "");
//            }
//            
//            for (int j = 0; j < currentEnglishElements.getLength(); j++) {
//                Node englishElement = currentEnglishElements.item(j);
//                if (englishElement.getFirstChild() != null)
//                    currentVerbEnglishHashtable.put(englishElement.getNodeName(), englishElement.getFirstChild().getNodeValue());
//                else
//                    currentVerbEnglishHashtable.put(englishElement.getNodeName(), "");
//            }
//            
//            for (int j = 0; j < currentItalianElements.getLength(); j++) {
//                Node italianElement = currentItalianElements.item(j);
//                String name = italianElement.getNodeName();
//                if (name.equals("vTable")) {
//                    NamedNodeMap attributes = italianElement.getAttributes();
//                    boolean bool;
//                    if (attributes.item(4).getNodeValue().equals("true")) bool = true;
//                    else bool = false;
//                    itvTable.addElement(new LexiconFieldData(attributes.item(0).getNodeValue(), attributes.item(1).getNodeValue(),
//                            attributes.item(2).getNodeValue(), attributes.item(3).getNodeValue(), bool));
//                } else if (name.equals("pTable")) {
//                    NamedNodeMap attributes = italianElement.getAttributes();
//                    boolean bool;
//                    if (attributes.item(3).getNodeValue().equals("true")) bool = true;
//                    else bool = false;
//                    itpTable.addElement(new LexiconFieldData(attributes.item(0).getNodeValue(), attributes.item(1).getNodeValue(),
//                            attributes.item(2).getNodeValue(),
//                            bool));
//                } else if (italianElement.getFirstChild() != null)
//                    currentVerbItalianHashtable.put(italianElement.getNodeName(), italianElement.getFirstChild().getNodeValue());
//                else
//                    currentVerbItalianHashtable.put(italianElement.getNodeName(), "");
//            }
//        }
//        System.out.println("98");
//        //import EntityType-Noun
//        Node entityTypeNounsElement = doc.getElementsByTagName("EntityType-Nouns").item(0);
//        NodeList entityTypeElements = entityTypeNounsElement.getChildNodes();
//        for (int i = 0; i < entityTypeElements.getLength(); i++) {
//            Node entityTypeElement = entityTypeElements.item(i);
//            NodeVector nodeVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeElement.getAttributes().item(0).getNodeValue());
//            if (nodeVector != null) {
//                NodeList nounNodes = entityTypeElement.getChildNodes();
//                for (int j = 0; j < nounNodes.getLength(); j++) {
//                    ( (Vector) nodeVector.get(2)).add(nounNodes.item(j).getAttributes().item(0).getNodeValue());
//                }
//            }
//        }
//        
//        //import Microplanning Values
//        Node microplanningValues = doc.getElementsByTagName("Microplanning").item(0);
//        NodeList entityTypeNodes = microplanningValues.getChildNodes();
//        for (int i = 0; i < entityTypeNodes.getLength(); i++) {
//            Node entityTypeElement = entityTypeNodes.item(i);
//            NodeVector nodeVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(entityTypeElement.getNodeName());
//            //System.out.println("99");
//            //if entity type has been imported
//            if (nodeVector != null) {
//                NodeList microValues = entityTypeElement.getChildNodes().item(0).getChildNodes();
//                for (int j = 0; j < microValues.getLength(); j++) {
//                    String key = microValues.item(j).getAttributes().item(0).getNodeValue();
//                    String value = "";
//                    if (microValues.item(j).getFirstChild() != null)
//                        value = microValues.item(j).getFirstChild().getNodeValue();
//                    
//                    ( (Hashtable) nodeVector.get(5)).put(key, value);
//                }
//                
//                //import Template Values
//                TemplateVector templateVector = (TemplateVector) nodeVector.get(4);
//                
//                Node templateValues = entityTypeElement.getChildNodes().item(1);
//                Node englishNode = templateValues.getChildNodes().item(0);
//                Node italianNode = templateValues.getChildNodes().item(1);
//                Node greekNode = templateValues.getChildNodes().item(2);
//                
//                //english templates
//                NodeList englishValues = englishNode.getChildNodes();
//                for (int j = 0; j < englishValues.getLength(); j++) {
//                    String name = englishValues.item(j).getAttributes().item(0).getNodeValue();
//                    Node valueNode = englishValues.item(j).getFirstChild();
//                    if (valueNode.getNodeType() == Node.TEXT_NODE) {
//                        ( (Hashtable) templateVector.get(0)).put(name, valueNode.getNodeValue());
//                    } else {
//                        Vector templateValueVector = new Vector();
//                        NodeList hashTableNodes = englishValues.item(j).getChildNodes();
//                        for (int k = 0; k < hashTableNodes.getLength(); k++) {
//                            NodeList hashTableValues = hashTableNodes.item(k).getChildNodes();
//                            Hashtable hashTable = new Hashtable();
//                            for (int l = 0; l < hashTableValues.getLength(); l++) {
//                                String value = "";
//                                if (hashTableValues.item(l).getFirstChild() != null)
//                                    value = hashTableValues.item(l).getFirstChild().getNodeValue();
//                                hashTable.put(hashTableValues.item(l).getNodeName(), value);
//                            }
//                            templateValueVector.add(hashTable);
//                        }
//                        ( (Hashtable) templateVector.get(0)).put(name, templateValueVector);
//                    }
//                }
//                
//                //italian templates
//                NodeList italianValues = italianNode.getChildNodes();
//                for (int j = 0; j < italianValues.getLength(); j++) {
//                    String name = italianValues.item(j).getAttributes().item(0).getNodeValue();
//                    Node valueNode = italianValues.item(j).getFirstChild();
//                    if (valueNode.getNodeType() == Node.TEXT_NODE) {
//                        ( (Hashtable) templateVector.get(1)).put(name, valueNode.getNodeValue());
//                    } else {
//                        Vector templateValueVector = new Vector();
//                        NodeList hashTableNodes = italianValues.item(j).getChildNodes();
//                        for (int k = 0; k < hashTableNodes.getLength(); k++) {
//                            NodeList hashTableValues = hashTableNodes.item(k).getChildNodes();
//                            Hashtable hashTable = new Hashtable();
//                            for (int l = 0; l < hashTableValues.getLength(); l++) {
//                                String value = "";
//                                if (hashTableValues.item(l).getFirstChild() != null)
//                                    value = hashTableValues.item(l).getFirstChild().getNodeValue();
//                                hashTable.put(hashTableValues.item(l).getNodeName(), value);
//                            }
//                            templateValueVector.add(hashTable);
//                        }
//                        ( (Hashtable) templateVector.get(1)).put(name, templateValueVector);
//                    }
//                }
//                
//                //greek templates
//                NodeList greekValues = greekNode.getChildNodes();
//                for (int j = 0; j < greekValues.getLength(); j++) {
//                    String name = greekValues.item(j).getAttributes().item(0).getNodeValue();
//                    Node valueNode = greekValues.item(j).getFirstChild();
//                    if (valueNode.getNodeType() == Node.TEXT_NODE) {
//                        ( (Hashtable) templateVector.get(2)).put(name, valueNode.getNodeValue());
//                    } else {
//                        Vector templateValueVector = new Vector();
//                        NodeList hashTableNodes = greekValues.item(j).getChildNodes();
//                        for (int k = 0; k < hashTableNodes.getLength(); k++) {
//                            NodeList hashTableValues = hashTableNodes.item(k).getChildNodes();
//                            Hashtable hashTable = new Hashtable();
//                            for (int l = 0; l < hashTableValues.getLength(); l++) {
//                                String value = "";
//                                if (hashTableValues.item(l).getFirstChild() != null)
//                                    value = hashTableValues.item(l).getFirstChild().getNodeValue();
//                                hashTable.put(hashTableValues.item(l).getNodeName(), value);
//                            }
//                            templateValueVector.add(hashTable);
//                        }
//                        ( (Hashtable) templateVector.get(2)).put(name, templateValueVector);
//                    }
//                }
//                //System.out.println("99.1");
//                //set field micropllanning text
//                Vector fieldVector = nodeVector.getDatabaseTableVector();
//                NodeVector parentNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity( ( (FieldData) fieldVector.get(0)).m_filler);
//                /*           for (int j = 8; j < parentNode.getDatabaseTableVector().size(); j++) {
//                   FieldData fieldData = (FieldData) fieldVector.get(j);
//                   fieldData.m_mplanning = "";
//                   fieldData.set(3, fieldData.m_mplanning);
//                  }*/
//                
//                for (int j = parentNode.getDatabaseTableVector().size(); j < fieldVector.size(); j++) {
//                    FieldData fieldData = (FieldData) fieldVector.get(j);
//                    fieldData.m_mplanning = updateMicroplanningIndex(fieldData.m_field, entityTypeElement.getNodeName());
//                    fieldData.set(3, fieldData.m_mplanning);
//                }
//            }
//        }//System.out.println("99.2");
//        
//        //import users
//        NodeList usersElements = doc.getElementsByTagName("Users").item(0).getChildNodes();
//        for (int i = 0; i < usersElements.getLength(); i++) {
//            NodeList userNodeChilds = usersElements.item(i).getChildNodes();
//            String userName = usersElements.item(i).getNodeName();
//            //System.out.println("99.3");
//            Vector userVector = new Vector();
//            userVector.add(userNodeChilds.item(0).getFirstChild().getNodeValue());
//            userVector.add(userNodeChilds.item(1).getFirstChild().getNodeValue());
//            userVector.add(userNodeChilds.item(2).getFirstChild().getNodeValue());
//            userVector.add(userNodeChilds.item(3).getFirstChild().getNodeValue());
//            Mpiro.win.struc.putUserOrRobotInMainUsersHashtable(userName, new User(userVector));
//        }
//        //System.out.println("99.4");
//        //import user model
//        NodeList userModelElements = doc.getElementsByTagName("UserModel").item(0).getChildNodes();
//        for (int i = 0; i < userModelElements.getLength(); i++) {
//            Node fieldNode = userModelElements.item(i);
//            Hashtable fieldHashTable = new Hashtable();
//            Mpiro.win.struc.putPropertyInMainUserModelHashtable(fieldNode.getNodeName(), fieldHashTable);
//            
//            NodeList entityElements = fieldNode.getChildNodes();
//            for (int j = 0; j < entityElements.getLength(); j++) {
//                Node entityNode = entityElements.item(j);
//                Hashtable entityHashTable = new Hashtable();
//                fieldHashTable.put(entityNode.getNodeName(), entityHashTable);
//                
//                NodeList userElements = entityNode.getChildNodes();
//                for (int k = 0; k < userElements.getLength(); k++) {
//                    Node userNode = userElements.item(k);
//                    Vector userValues = new Vector();
//                    entityHashTable.put(userNode.getNodeName(), userValues);
//                    
//                    NodeList valuesElements = userNode.getChildNodes();
//                    for (int l = 0; l < valuesElements.getLength(); l++) {
//                        String value = "";
//                        if (valuesElements.item(l).getFirstChild() != null)
//                            value = valuesElements.item(l).getFirstChild().getNodeValue();
//                        userValues.add(value);
//                    }
//                }
//            }
//        } //end import usr model
//        
//        /*        //import user model story
//          NodeList userModelStoryElements = doc.getElementsByTagName("UserModelStory").item(0).getChildNodes();
//          for (int i = 0; i < userModelStoryElements.getLength(); i++) {
//           String entityName = userModelStoryElements.item(i).getAttributes().item(0).getNodeValue();
//           Hashtable valueHashTable = new Hashtable();
//           QueryProfileHashtable.mainUserModelStoryHashtable.put(entityName, valueHashTable);
//          }
//         */
//        //import options
//        NodeList optionElements = doc.getElementsByTagName("Options").item(0).getChildNodes();
//        for (int i = 0; i < optionElements.getLength(); i++) {
//            String optionName = optionElements.item(i).getNodeName();
//            Object value = optionElements.item(i).getFirstChild().getNodeValue();
//            
//            if (!optionName.equals("pserverAddress"))
//                QueryOptionsHashtable.mainOptionsHashtable.put(optionName, (String) value);
//            else {
//                NodeList serverValueNodes = optionElements.item(i).getChildNodes();
//                Vector vector = new Vector();
//                for (int j = 0; j < serverValueNodes.getLength(); j++) {
//                    vector.add(serverValueNodes.item(j).getFirstChild().getNodeValue());
//                }
//                QueryOptionsHashtable.mainOptionsHashtable.put(optionName, vector);
//            }
//        }
//    }
    
 /*   private static void getProperties( ExtendedIterator properties)
    {
        while(properties.hasNext()){
           ObjectProperty nextProp = (ObjectProperty) properties.next();
        //   propertiesHashtableRecord rrrrrrr=new propertiesHashtableRecord();
           ExtendedIterator extit=nextProp.listDomain();
           if (!extit.hasNext()) continue;
           Mpiro.win.struc.addProperty(getNameInELEON(nextProp),new propertiesHashtableRecord(false));
           Vector propVec=(Vector) Mpiro.win.struc.getProperty(getNameInELEON(nextProp));
           Vector vect=(Vector) propVec.elementAt(0);
  
           while (extit.hasNext()){
             //  String test=extit.next().toString();
OntClass next= (OntClass) extit.next();
               vect.add(getNameInELEON(next));
           }
           vect= (Vector) propVec.elementAt(1);
           extit= nextProp.listRange();
           while(extit.hasNext()){
OntClass next= (OntClass) extit.next();
               vect.add(getNameInELEON(next));
           }
           vect= (Vector) propVec.elementAt(2);
           extit= nextProp.listSubProperties();
           while(extit.hasNext()){
               OntClass next= (OntClass) extit.next();
               vect.add(getNameInELEON(next));
           }
                      vect= (Vector) propVec.elementAt(3);
           extit= nextProp.listSuperProperties();
           while(extit.hasNext()){
OntClass next= (OntClass) extit.next();
               vect.add(getNameInELEON(next));
           }
                      vect= (Vector) propVec.elementAt(4);
           extit= nextProp.listEquivalentProperties();
           while(extit.hasNext()){
OntClass next= (OntClass) extit.next();
               vect.add(getNameInELEON(next));
           }
  
           if(nextProp.hasInverse()) propVec.setElementAt(nextProp.getInverseOf()),5);
           if(nextProp.isFunctionalProperty()) propVec.setElementAt("true",6);
           if(nextProp.isInverseFunctionalProperty()) propVec.setElementAt("true",7);
           if(nextProp.isTransitiveProperty()) propVec.setElementAt("true",8);
           if(nextProp.isSymmetricProperty()) propVec.setElementAt("true",9);
  
        }
  
    }*/
    
 /*    private static void getDataTypeProperties( ExtendedIterator properties)
    {
        while(properties.hasNext()){
           DatatypeProperty nextProp = (DatatypeProperty) properties.next();
        //   propertiesHashtableRecord rrrrrrr=new propertiesHashtableRecord();
           Mpiro.win.struc.addProperty(getNameInELEON(nextProp),new propertiesHashtableRecord(false));
           Vector propVec=(Vector) Mpiro.win.struc.getProperty(getNameInELEON(nextProp));
           Vector vect=(Vector) propVec.elementAt(0);
           ExtendedIterator extit=nextProp.listDomain();
           while (extit.hasNext()){
               vect.add(extit.next().toString());
           }
           vect= (Vector) propVec.elementAt(1);
           extit= nextProp.listRange();
           while(extit.hasNext()){
               vect.add(extit.next().toString());
           }
           vect= (Vector) propVec.elementAt(2);
           extit= nextProp.listSubProperties();
           while(extit.hasNext()){
               vect.add(extit.next().toString());
           }
                      vect= (Vector) propVec.elementAt(3);
           extit= nextProp.listSuperProperties();
           while(extit.hasNext()){
               vect.add(extit.next().toString());
           }
                      vect= (Vector) propVec.elementAt(4);
           extit= nextProp.listEquivalentProperties();
           while(extit.hasNext()){
               vect.add(extit.next().toString());
           }
  
           if(nextProp.hasInverse()) propVec.setElementAt(nextProp.getInverseOf().toString(),5);
           if(nextProp.isFunctionalProperty()) propVec.setElementAt("true",6);
           if(nextProp.isInverseFunctionalProperty()) propVec.setElementAt("true",7);
           if(nextProp.isTransitiveProperty()) propVec.setElementAt("true",8);
           if(nextProp.isSymmetricProperty()) propVec.setElementAt("true",9);
  
        }
  
    }*/
    
    public static String updateMicroplanningIndex(String fieldName, String entityType) {
        //System.out.println("100");
        int numberOfEnglishMicroplans = 0;
        int numberOfItalianMicroplans = 0;
        int numberOfGreekMicroplans = 0;
        String result = new String("");
        Vector entityTypeVector = (Vector) Mpiro.win.struc.getEntityTypeOrEntity(entityType);
        Hashtable mpHashtable = (Hashtable) entityTypeVector.get(5);
        String selectionEnglish = new String("");
        String selectionItalian = new String(""); //maria
        String selectionGreek = new String(""); //maria
        for (int i = 1; i <= 5; i++) { //maria
            //System.out.print("4rTTre45".toLowerCase());
            try{
                selectionEnglish = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:English").toString(); //maria
                selectionItalian = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:Italian").toString(); //maria
                selectionGreek = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:Greek").toString(); //maria
            }catch(NullPointerException e){
                mpHashtable.put(i + ":" + fieldName + ":" + "SELECTION:English", "NoMicroPlanning");
                mpHashtable.put(i + ":" + fieldName + ":" + "SELECTION:Italian", "NoMicroPlanning");
                mpHashtable.put(i + ":" + fieldName + ":" + "SELECTION:Greek", "NoMicroPlanning");
                selectionEnglish="NoMicroPlanning";
                selectionItalian="NoMicroPlanning";
                selectionGreek="NoMicroPlanning";
            };
            if (!selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) { //maria
                numberOfEnglishMicroplans++; //maria
            } //maria
            if (!selectionItalian.equalsIgnoreCase("NoMicroPlanning")) { //maria
                numberOfItalianMicroplans++; //maria
            } //maria
            if (!selectionGreek.equalsIgnoreCase("NoMicroPlanning")) { //maria
                numberOfGreekMicroplans++; //maria
            } //maria
        } //maria
        result = numberOfEnglishMicroplans + "EN  " + numberOfItalianMicroplans + "IT  " + numberOfGreekMicroplans + "EL"; //maria
        return result; //maria
    }
    
    private static String getNameInELEON(Resource res){
        
        //System.out.println(res.toString());
        if(res.getNameSpace().equalsIgnoreCase(mpiroPath)||res.getNameSpace().equalsIgnoreCase(mpiroPath+"#"))
            return res.getLocalName();
        else
            return res.getURI();
    }

    private static void addObjectProperty(ObjectProperty nextProp, OntModel ontModel) {
        try {
                ExtendedIterator extit=nextProp.listDomain();
                Mpiro.win.struc.addProperty(getNameInELEON(nextProp),new PropertiesHashtableRecord(false));
                Vector propVec=(Vector) Mpiro.win.struc.getProperty(getNameInELEON(nextProp));
                Vector vect=(Vector) propVec.elementAt(0);
                if (!extit.hasNext()){
                    vect.add("Basic-entity-types");
                    //  continue;
                }
                
                
                //    while (extit.hasNext()){
                while(extit.hasNext()){
                    OntClass temp=(OntClass)extit.next();
                    if(temp.isUnionClass()){
                       UnionClass uc=temp.asUnionClass();
                       ExtendedIterator ops=uc.listOperands();
                       Vector tempDom=new Vector();
                       while(ops.hasNext()){
                           OntClass nextDom=(OntClass) ops.next();
                           if(nextDom.isAnon()){//It's not union of named classes
                               tempDom.clear();
                               tempDom.add("Basic-entity-types");
                               System.err.println("Domain of "+getNameInELEON(nextProp)+" is complicated. It will be inserted with domain Basic-entity-types");
                               break;
                           }
                           else{
                           tempDom.add(getNameInELEON(nextDom));}
                       }
                       propVec.setElementAt(tempDom,0);
                    } else if(!temp.isAnon()){
                        //String test=getNameInELEON(temp);
                        vect.add(getNameInELEON(temp));}
                    else{
                         vect.add("Basic-entity-types");
                         System.err.println("Domain of "+getNameInELEON(nextProp)+" is complicated. It will be inserted with domain Basic-entity-types");  
                    }
                }
                //   }
                vect= (Vector) propVec.elementAt(1);
                //extit= nextProp.getR.listRange();
                 //               while(extit.hasNext()){
                 //   OntClass temp=(OntClass)extit.next();
                 //   if(temp.isUnionClass()){
                 //       UnionClass uc=temp.asUnionClass();
                 //       ExtendedIterator ops=uc.listOperands();
                 //       while(ops.hasNext()){
                 //           vect.add(getNameInELEON(ontModel.getOntClass(ops.next().toString())));
                 //       }
                //    } else{
                        //String test=getNameInELEON(temp);
                //        vect.add(getNameInELEON(ontModel.getOntClass(temp.toString())));}
                //}
                OntResource range= nextProp.getRange();
                if(range==null || range.isAnon())
                     vect.add("Basic-entity-types");
                else
                    vect.add(getNameInELEON(range));
                    

                vect= (Vector) propVec.elementAt(2);
                extit= nextProp.listSubProperties(true);
                while(extit.hasNext()){
                    vect.add(getNameInELEON(ontModel.getOntProperty(extit.next().toString())));
                }
                vect= (Vector) propVec.elementAt(3);
                extit= nextProp.listSuperProperties(true);
                while(extit.hasNext()){
                    vect.add(getNameInELEON(ontModel.getOntProperty(extit.next().toString())));
                }
                vect= (Vector) propVec.elementAt(4);
                extit= nextProp.listEquivalentProperties();
                while(extit.hasNext()){
                    vect.add(getNameInELEON(ontModel.getOntProperty(extit.next().toString())));
                }
                try {
                    
                    if(nextProp.hasInverse()) propVec.setElementAt(getNameInELEON(nextProp.getInverseOf()),5);
                } catch(NullPointerException exc) {
                    return;
                }
                if(nextProp.isFunctionalProperty()) propVec.setElementAt("true",6);
                if(nextProp.isInverseFunctionalProperty()) propVec.setElementAt("true",7);
                if(nextProp.isTransitiveProperty()) propVec.setElementAt("true",8);
                if(nextProp.isSymmetricProperty()) propVec.setElementAt("true",9);
                //System.out.println(getNameInELEON(nextProp));
            } catch (java.lang.Exception e){
                System.err.println("Problem occured while inserting property into properties hashtable");
                return;}
    }

    private static void addDatatypeProperty(DatatypeProperty nextProp, OntModel ontModel) {
         if( !getNameInELEON(nextProp).equalsIgnoreCase("title")
            && !getNameInELEON(nextProp).equalsIgnoreCase("name")
            && !getNameInELEON(nextProp).equalsIgnoreCase("shortname")
            && !getNameInELEON(nextProp).equalsIgnoreCase("notes")
            && !getNameInELEON(nextProp).equalsIgnoreCase("gender")
            && !getNameInELEON(nextProp).equalsIgnoreCase("number")
            && !getNameInELEON(nextProp).equalsIgnoreCase("gender-name")
            && !getNameInELEON(nextProp).equalsIgnoreCase("gender-shortname")
            && !getNameInELEON(nextProp).equalsIgnoreCase("name-nominative")
            && !getNameInELEON(nextProp).equalsIgnoreCase("name-genitive")
            && !getNameInELEON(nextProp).equalsIgnoreCase("name-accusative")
            && !getNameInELEON(nextProp).equalsIgnoreCase("shortname-nominative")
            && !getNameInELEON(nextProp).equalsIgnoreCase("shortname-genitive")
            && !getNameInELEON(nextProp).equalsIgnoreCase("shortname-accusative")
            && !getNameInELEON(nextProp).equalsIgnoreCase( "images")) {
                
                //   propertiesHashtableRecord rrrrrrr=new propertiesHashtableRecord();
                //System.out.println("a");
                Mpiro.win.struc.addProperty(getNameInELEON(nextProp),new PropertiesHashtableRecord(false));
                Vector propVec=(Vector) Mpiro.win.struc.getProperty(getNameInELEON(nextProp));
                //System.out.println("b");
                Vector vect=(Vector) propVec.elementAt(0);
                ExtendedIterator extit=nextProp.listDomain();
                //System.out.println("c"+getNameInELEON(nextProp));
                
                while (extit.hasNext()){
                   // OntResource temp=(OntResource) extit.next();
                    if(getNameInELEON(nextProp).equalsIgnoreCase("date")) continue;
                                   
                    OntClass temp=(OntClass)extit.next();
                    if(temp.isUnionClass()){
                       UnionClass uc=temp.asUnionClass();
                       ExtendedIterator ops=uc.listOperands();
                       Vector tempDom=new Vector();
                       while(ops.hasNext()){
                           OntClass nextDom=(OntClass) ops.next();
                           if(nextDom.isAnon()){//It's not union of named classes
                               tempDom.clear();
                               tempDom.add("Basic-entity-types");
                               System.err.println("Domain of "+getNameInELEON(nextProp)+" is complicated. It will be inserted with domain Basic-entity-types");
                               break;
                           }
                           else{
                           tempDom.add(getNameInELEON(nextDom));}
                       }
                       propVec.setElementAt(tempDom,0);
                    } else if(!temp.isAnon()){
                        //String test=getNameInELEON(temp);
                        vect.add(getNameInELEON(temp));}
                    else{
                         vect.add("Basic-entity-types");
                         System.err.println("Domain of "+getNameInELEON(nextProp)+" is complicated. It will be inserted with domain Basic-entity-types");  
                    }
                
                    //ontClass tem1p=extit.next();
                    //    String[] next=extit.next().toString().split("#");
                    //        System.out.println(ob.toString());
                    //    OntClass temp=(OntClass) ob;
                    //System.out.println(getNameInELEON(temp));
                    //OntClass temp=ontModel.getOntClass(extit.next().toString());
                    // if(!temp.isAnon())
                    //if (temp.isClass())
                     //   vect.add(getNameInELEON(temp));
                }
                if(vect.size()==0)
                    vect.add("Basic-entity-types");
                //System.out.println("d");
                vect= (Vector) propVec.elementAt(1);
                extit= nextProp.listRange();
                
                //System.out.println("e");
                while(extit.hasNext()){
                    String[] test=extit.next().toString().split("#");
                    String rangeName= test[1];
                    if (rangeName.equalsIgnoreCase("mpiroNumber") || rangeName.equalsIgnoreCase("integer") || rangeName.equalsIgnoreCase("positiveInteger") ||
                            rangeName.equalsIgnoreCase("negativeInteger") || rangeName.equalsIgnoreCase("nonNegativeInteger") || rangeName.equalsIgnoreCase("long") ||
                            rangeName.equalsIgnoreCase("unsignedLong") || rangeName.equalsIgnoreCase("int") || rangeName.equalsIgnoreCase("unsignedInt") ||
                            rangeName.equalsIgnoreCase("short") || rangeName.equalsIgnoreCase("unsignedShort") || rangeName.equalsIgnoreCase("decimal") ||
                            rangeName.equalsIgnoreCase("float") ||
                            rangeName.equalsIgnoreCase("double") || rangeName.equalsIgnoreCase("date") || rangeName.equalsIgnoreCase("datetime")) {
                        rangeName = "Number";
                    } else if (rangeName.equalsIgnoreCase("mpiroDate")) {
                        rangeName = "Date";
                    } else if (rangeName.equalsIgnoreCase("mpiroDimension")) {
                        rangeName = "Dimension";
                    } else if (rangeName.equalsIgnoreCase("string")) {
                        rangeName = "String";
                    }
                    vect.add(rangeName);//changeeeeeeeeeeeeeeeees
                }
                //System.out.println("f");
                vect= (Vector) propVec.elementAt(2);
                extit= nextProp.listSubProperties(true);
                //System.out.println("g");
                while(extit.hasNext()){
                    vect.add(getNameInELEON(ontModel.getOntProperty(extit.next().toString())));
                }
                vect= (Vector) propVec.elementAt(3);
                //System.out.println("h");
                extit= nextProp.listSuperProperties(true);
                while(extit.hasNext()){
                    vect.add(getNameInELEON(ontModel.getOntProperty(extit.next().toString())));
                }
                //System.out.println("i");
                vect= (Vector) propVec.elementAt(4);
                extit= nextProp.listEquivalentProperties();
                //System.out.println("j");
                while(extit.hasNext()){
                    vect.add(getNameInELEON(ontModel.getOntProperty(extit.next().toString())));
                }
                //System.out.println("k");
                
                if(nextProp.hasInverse()) propVec.setElementAt(getNameInELEON(nextProp.getInverseOf()),5);
                if(nextProp.isFunctionalProperty()) propVec.setElementAt("true",6);
                if(nextProp.isInverseFunctionalProperty()) propVec.setElementAt("true",7);
                if(nextProp.isTransitiveProperty()) propVec.setElementAt("true",8);
                if(nextProp.isSymmetricProperty()) propVec.setElementAt("true",9);
                
            }
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
