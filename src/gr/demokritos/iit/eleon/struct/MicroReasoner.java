/***************

<p>Title: </p>

<p>Description:
This class implements an OntoExtension that performs simple instance-based reasoning.
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/


package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.DefaultVector;
import gr.demokritos.iit.eleon.authoring.FieldData;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.authoring.PropertiesHashtableRecord;
import gr.demokritos.iit.eleon.authoring.TemplateVector;
import gr.demokritos.iit.eleon.interfaces.OntoExtension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;


public class MicroReasoner implements OntoExtension {

    private OntoExtension source;
    private EleonStruc result;
    private boolean reloadTree=false;

   

    @Override
    public void setPrevious(OntoExtension prev) {
        source = prev;
    }

    @Override
    public EleonStruc getExtension() {
        return result;
    }

    @Override
    public boolean isRealTime() {
        return true;
    }



    @Override
    public void rebind() {
        result=new EleonStruc();
        EleonStruc explicitModel = source.getExtension();
        result.ontoHash = new QueryHashtable(cloneHash(explicitModel.ontoHash.mainDBHashtable), cloneHash(explicitModel.ontoHash.propertiesHashtable), cloneHash(explicitModel.ontoHash.equivalentClassesHashtable),  cloneHash(explicitModel.ontoHash.superClassesHashtable),  cloneHash(explicitModel.ontoHash.valueRestrictionsHashtable), cloneHash(explicitModel.ontoHash.annotationPropertiesHashtable));
        result.lexHash=new QueryLexiconHashtable(cloneHash(explicitModel.lexHash.mainLexiconHashtable));
        result.profileHash=new QueryProfileHashtable(cloneHash(explicitModel.profileHash.mainUserModelHashtable), cloneHash(explicitModel.profileHash.mainUsersHashtable));
        result.optionsHash=new QueryOptionsHashtable(explicitModel.optionsHash.getBaseURI());
        result.gp=new GeneralizedProfiles(cloneHash(explicitModel.gp.robotCharValuesHashtable), cloneVector(explicitModel.gp.robotCharVector));
        if(reloadTree)
        DataBasePanel.reloadDBTree();

        Enumeration propNames = result.ontoHash.propertiesHashtable.keys();
        Enumeration properties = result.ontoHash.propertiesHashtable.elements();
        while (propNames.hasMoreElements()) {
            String propName = propNames.nextElement().toString();
            Vector property = (Vector) properties.nextElement();
            checkSubproperties(propName);
            checkSuperproperties(propName);
            if (property.elementAt(9).equals("true")) {
                checkSymmetricProperty(propName);
            }
            if (!property.elementAt(5).equals("")) {
                checkInverseProperty(propName, property.elementAt(5).toString());
            }
            if (property.elementAt(8).toString().equalsIgnoreCase("true")) {
                checkTransitiveProperty(propName);
            }
            if (property.elementAt(6).toString().equalsIgnoreCase("true")) {
                checkFunctionalProperty(propName);
            }
            if (property.elementAt(6).toString().equalsIgnoreCase("true")) {
                checkInverseFunctionalProperty(propName);
            }
        }
        
        Enumeration elements = result.ontoHash.valueRestrictionsHashtable.elements();
        Enumeration keys = result.ontoHash.valueRestrictionsHashtable.keys();
        while(elements.hasMoreElements()){
        Vector res=(Vector)elements.nextElement();
        
        String[] name=keys.nextElement().toString().split(":");
        String propName=name[1];
        String type=name[0];
        
        Vector hasValue=(Vector)res.elementAt(2);
        for(int j=0;j<hasValue.size();j++){
            String value=hasValue.elementAt(j).toString();
            addHasValueToEntities(type, propName, value);
        }
        
        Vector maxCard=(Vector)res.elementAt(3);
        if(!checkExistingEntities(type, propName, maxCard.toString())){
            maxCard=new Vector();
        }
        Vector card=(Vector)res.elementAt(5);
        if(!checkExistingEntities(type, propName, card.toString())){
            card=new Vector();
        }
        }

        try{
        System.out.println(" a in explicit:::: "+explicitModel.getEntityTypeOrEntity("a").toString());
        System.out.println(" b in explicit:::: "+explicitModel.getEntityTypeOrEntity("b").toString());
        System.out.println(" a in inferred:::: "+result.getEntityTypeOrEntity("a").toString());
        System.out.println(" b in inferred:::: "+result.getEntityTypeOrEntity("b").toString());
        }catch(java.lang.NullPointerException ex){};
    // 2. kane oti prepei na kaneis XWRIS NA ALLA3EIS TO tt !!!!
    // 3. grapse sto this.result
    }


     @Override
    public void reloadTree(boolean set){
        reloadTree=set;
    }

    private void checkFunctionalProperty(String propName) {
        Vector propVector = (Vector) result.ontoHash.propertiesHashtable.get(propName);
        Enumeration keys = result.ontoHash.valueRestrictionsHashtable.keys();
        Enumeration elements = result.ontoHash.valueRestrictionsHashtable.elements();
        while (keys.hasMoreElements()) {
            String nextKey = keys.nextElement().toString();
            Vector nextElement = (Vector) elements.nextElement();
            if (nextKey.endsWith(":" + propName)) {
                Vector minCard = (Vector) nextElement.elementAt(4);
                for (int y = 0; y < minCard.size(); y++) {
                    if (!minCard.elementAt(y).toString().equalsIgnoreCase("0") && !minCard.elementAt(y).toString().equalsIgnoreCase("1")) {
                        System.err.println("ELEON MicroReasoner: " + propName + " has minCardinality " + minCard.elementAt(y).toString() + " for type " + nextKey.split(":")[0] + " and cannot be functional. Definition of functional for this property removed.");
                        propVector.setElementAt("false", 6);
                        return;
                    }
                }

                Vector card = (Vector) nextElement.elementAt(5);
                for (int y = 0; y < card.size(); y++) {
                    if (!card.elementAt(y).toString().equalsIgnoreCase("0") && !card.elementAt(y).toString().equalsIgnoreCase("1")) {
                        System.err.println("ELEON MicroReasoner: " + propName + " has Cardinality " + card.elementAt(y).toString() + " for type " + nextKey.split(":")[0] + " and cannot be functional. Definition of functional for this property removed.");
                        propVector.setElementAt("false", 6);
                        return;
                    }
                }
            }
        }



        Vector dom = (Vector) propVector.elementAt(0);
        for (int i = 0; i < dom.size(); i++) {
            if (!canSetFunctional(dom.elementAt(i).toString(), propName)) {
                propVector.setElementAt("false", 6);
                break;
            }
        }

        if (propVector.elementAt(8).toString().equalsIgnoreCase("true")) {
            System.err.println("ELEON MicroReasoner: " + propName + " is transitive and functional. Ontology is OWL Full!");

        }



    }

    private void checkInverseFunctionalProperty(String propName) {

        Vector propVector = (Vector) result.ontoHash.propertiesHashtable.get(propName);
        Vector Domain = (Vector) propVector.elementAt(0);
        Vector fillers = new Vector();
        for (int k = 0; k < Domain.size(); k++) {
            Enumeration entities = result.ontoHash.getChildrenEntities(DataBasePanel.getNode(Domain.elementAt(k).toString())).elements();
            while (entities.hasMoreElements()) {
                //  Vector nextEntity=(Vector)mainDBHashtable.get(entities.nextElement().toString());
                Vector db = (Vector) entities.nextElement();
                db = (Vector) db.elementAt(0);
                Vector ind = (Vector) db.elementAt(0);
                for (int h = 3; h < ind.size(); h++) {
                    Vector field = (Vector) ind.elementAt(h);
                    if (field.elementAt(1).toString().startsWith("Select")) {
                        break;
                    }
                    String[] values = field.elementAt(1).toString().split(" ");
                    if (field.elementAt(0).toString().equalsIgnoreCase(propName)) {
                        for (int l = 0; l < values.length; l++) {
                            if (fillers.contains(values[l])) {
                                propVector.setElementAt("false", 6);
                                System.err.println("ELEON MicroReasoner: " + propName + " cannot be inverse functional, because " + values[l] + " is filler of this property for more than one entities");


                            } else {
                                fillers.add(values[l]);
                            }
                        }
                    }
                }
            }
        }

        if (!propVector.elementAt(5).toString().equals("")) {
            Vector inverse = (Vector) result.ontoHash.propertiesHashtable.get(propVector.elementAt(5).toString());
            inverse.setElementAt("true", 6);
        }
    }

    private void checkInverseProperty(String propName, String inversePropName) {
        Vector propVector = (Vector) result.ontoHash.propertiesHashtable.get(propName);
        Enumeration names = result.ontoHash.propertiesHashtable.keys();
        Enumeration allprop = result.ontoHash.propertiesHashtable.elements();

        //NodeVector temp=source.getExtension().getEntityTypeOrEntity("b");

        //check if the domain of propName is identical with the range of
        //inversePropName and vice versa
        if (!inversePropName.equalsIgnoreCase("")) {
            Hashtable backup = new Hashtable();
            Vector domain1 = new Vector((Vector) propVector.elementAt(0));
            Vector range1 = new Vector((Vector) propVector.elementAt(1));
            Vector inversePropVector = (Vector) result.getProperty(inversePropName);
            Vector inverseDomain1 = new Vector((Vector) inversePropVector.elementAt(0));
            Vector inverseRange1 = new Vector((Vector) inversePropVector.elementAt(1));
            if (!vectorsHaveSameElements(domain1, inverseRange1) || !vectorsHaveSameElements(range1, inverseDomain1)) {

                System.err.println("ELEON MicroReasoner: To set two properties as inverse, the domain of the first" +
                        " must be the same with the range of the second and vice-versa. Properties " + propName + " and" +
                        "" + inversePropName + " cannot be inverse for this reason. This definition will be removed");

                propVector.setElementAt("", 5);
                return;

            }

            if (!(inversePropVector.elementAt(5).toString().equalsIgnoreCase(""))&&!(inversePropVector.elementAt(5).toString().equalsIgnoreCase(propName))) {

                System.err.println("ELEON MicroReasoner: Property " + propName + " has as inverse the property " + inversePropName + "" +
                        ", but " + inversePropName + " has another property as its inverse. The definition will be removed.");
                propVector.setElementAt("", 5);
                return;


            }

            //remove the inverse declaration from the ex-inverse of the modified property
            while (allprop.hasMoreElements()) {
                String nextProp = names.nextElement().toString();
                Vector next = (Vector) allprop.nextElement();
                if (!nextProp.equalsIgnoreCase(propName) && !nextProp.equalsIgnoreCase(inversePropName)) {
                    if (next.elementAt(5).toString().equalsIgnoreCase(propName)) {
                        System.err.println("ELEON MicroReasoner: Property " + propName + " has as inverse the property" + inversePropName +
                                ". It cannot have as inverse the property " + nextProp + ". " +
                                "This definition wiil be removed.");

                        next.setElementAt("", 5);

                    }
                }
            }


            for (int y = 0; y < domain1.size(); y++) {
                Vector childrenEntities = result.ontoHash.getFullPathChildrenVectorFromMainDBHashtable(inverseDomain1.elementAt(y).toString(), "Entity");
                //  Vector children=getChildrenVectorFromMainDBHashtable(domain.elementAt(y).toString(),"entity");

                for (int g = 0; g < childrenEntities.size(); g++) {
                    String entityName = childrenEntities.elementAt(g).toString();
                    NodeVector indVec = (NodeVector) result.getEntityTypeOrEntity(entityName);
                    for (int r = 3; r < indVec.size(); r++) {
                        Vector propVec = (Vector) indVec.elementAt(r);
                        System.out.println("childVec.elementAt(r)" + indVec.elementAt(r).toString());
                        if (propVec.elementAt(0).toString().equalsIgnoreCase(propName)) {
                            String rangeVec = propVec.elementAt(1).toString();
                            System.out.println("rangeVec" + rangeVec);
                            String[] j = rangeVec.split(" ");
                            for (int f = 0; f < j.length; f++) {
                                String domainf = j[f];
                                System.out.println(domainf);
                                if (domainf.equalsIgnoreCase("Select") || domainf.equalsIgnoreCase("")) {
                                    break;
                                }
                                Vector vecDomUnchanged = (Vector) result.getEntityTypeOrEntity(domainf);
                                Vector vecDom = (Vector) vecDomUnchanged.clone();
                                vecDom.setElementAt(((Vector) vecDomUnchanged.elementAt(0)).clone(), 0);
                                if (!backup.containsKey(domainf)) {
                                    backup.put(domainf, new NodeVector(vecDom));
                                }
                                System.out.println("{ffffff" + vecDom);
                                Vector vecDom1 = (Vector) vecDom.elementAt(0);
                                Vector vecDomUnchanged1 = (Vector) vecDomUnchanged.elementAt(0);
                                vecDom1.setElementAt(((Vector) vecDomUnchanged1.elementAt(0)).clone(), 0);
                                Vector vecDom2 = (Vector) vecDom1.elementAt(0);
                                Vector vecDomUnchanged2 = (Vector) vecDomUnchanged1.elementAt(0);
                                System.out.println(vecDom2.toString());
                                for (int a = 3; a < vecDom2.size(); a++) {
                                    Vector nextEl = (Vector) vecDom2.elementAt(a);
                                    System.out.println(nextEl.toString());
                                    if (nextEl.elementAt(0).toString().equalsIgnoreCase(inversePropName)) {
                                        //nextEl=((Vector)(Vector) ((Vector)((Vector)((Vector)vecDomUnchanged.elementAt(0)).elementAt(0)).elementAt(a)).clone());
                                        vecDom2.setElementAt(((Vector) vecDomUnchanged2.elementAt(a)).clone(), a);
                                        String rangeVec2 = nextEl.elementAt(1).toString();
                                        if (rangeVec2.startsWith("Select")) {
                                            rangeVec2 = "";
                                        }
                                        String[] k = rangeVec2.split(" ");
                                        for (int p = 0; p < k.length; p++) {
                                            if (k[p].equalsIgnoreCase(entityName)) {
                                                break;
                                            } else {
                                                if (p < k.length - 1) {
                                                    continue;
                                                } else {
                                                    if (nextEl.elementAt(1).toString().startsWith("Select")) {
                                                        nextEl.set(1, entityName);
                                                    } else {
                                                        nextEl.set(1, entityName + " " + nextEl.elementAt(1).toString());
                                                    }
                                                    //Object obj1=mainDBHashtable.get(obj);
                                                    //obj1=(Object) nextEl;
                                                    if (result.ontoHash.getMinOfMaxCardinalities(result.ontoHash.getParents(domainf).elementAt(0).toString(), inversePropName) < nextEl.elementAt(1).toString().split(" ").length) {

                                                        System.err.println("Entity " + domainf + " has more fillers than " + result.ontoHash.getMinOfMaxCardinalities(result.ontoHash.getParents(domainf).elementAt(0).toString(), inversePropName.toString()) + "(from maxCardinality restriction");

                                                        Enumeration backupKeys = backup.keys();
                                                        Enumeration backupElements = backup.elements();
                                                        while (backupKeys.hasMoreElements()) {
                                                            result.putEntityTypeOrEntityToDB(backupKeys.nextElement().toString(), (NodeVector) backupElements.nextElement());
                                                        }
                                                        return;

                                                    }
                                                    if (result.ontoHash.getCardinality(result.ontoHash.getParents(domainf).elementAt(0).toString(), inversePropName) < nextEl.elementAt(1).toString().split(" ").length && result.ontoHash.getCardinality(result.ontoHash.getParents(domainf).elementAt(0).toString(), inversePropName) != -1) {

                                                        System.err.println("Entity " + domainf + " has more fillers than " + result.ontoHash.getCardinality(result.ontoHash.getParents(domainf).elementAt(0).toString(), inversePropName.toString()) + "(from Cardinality restriction)");


                                                        Enumeration backupKeys = backup.keys();
                                                        Enumeration backupElements = backup.elements();
                                                        while (backupKeys.hasMoreElements()) {
                                                            result.putEntityTypeOrEntityToDB(backupKeys.nextElement().toString(), (NodeVector) backupElements.nextElement());
                                                        }
                                                        return;

                                                    }

                                                }
                                            }



                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }



            for (int y = 0; y < inverseDomain1.size(); y++) {

                Vector childrenEntities = result.ontoHash.getFullPathChildrenVectorFromMainDBHashtable(inverseDomain1.elementAt(y).toString(), "Entity");
                //  Vector children=getChildrenVectorFromMainDBHashtable(domain.elementAt(y).toString(),"entity");

                for (int g = 0; g < childrenEntities.size(); g++) {
                    String entityName = childrenEntities.elementAt(g).toString();
                    NodeVector entityVector = (NodeVector) result.getEntityTypeOrEntity(entityName);

                    Vector indVec = (Vector) entityVector.getIndependentFieldsVector();

                    for (int r = 3; r < indVec.size(); r++) {
                        Vector propVec = (Vector) indVec.elementAt(r);
                        if (propVec.elementAt(0).toString().equalsIgnoreCase(inversePropName)) {
                            String rangeVec = propVec.elementAt(1).toString();
                            String[] j = rangeVec.split(" ");
                            for (int f = 0; f < j.length; f++) {
                                String domainf = j[f];
                                System.out.println(domainf);
                                if (domainf.startsWith("Select") || domainf.equalsIgnoreCase("")) {
                                    break;
                                }
                                Vector vecDomUnchanged = (Vector) result.getEntityTypeOrEntity(domainf);
                                Vector vecDom = (Vector) vecDomUnchanged.clone();
                                vecDom.setElementAt(((Vector) vecDomUnchanged.elementAt(0)).clone(), 0);
                                if (!backup.containsKey(domainf)) {
                                    backup.put(domainf, new NodeVector(vecDom));
                                }
                                Vector vecDom1 = (Vector) vecDom.elementAt(0);
                                Vector vecDomUnchanged1 = (Vector) vecDomUnchanged.elementAt(0);
                                vecDom1.setElementAt(((Vector) vecDomUnchanged1.elementAt(0)).clone(), 0);
                                Vector vecDom2 = (Vector) vecDom1.elementAt(0);
                                Vector vecDomUnchanged2 = (Vector) vecDomUnchanged1.elementAt(0);
                                System.out.println(vecDom2.toString());
                                for (int a = 3; a < vecDom2.size(); a++) {
                                    //vecDom.setElementAt((Vector)((Vector)((Vector)((Vector)vecDomUnchanged.elementAt(0)).elementAt(0)).elementAt(a)).clone(),a);
                                    Vector nextEl = (Vector) vecDom2.elementAt(a);
                                    System.out.println(nextEl.toString());
                                    if (nextEl.elementAt(0).toString().equalsIgnoreCase(propName)) {
                                        //nextEl=((Vector)(Vector) ((Vector)((Vector)((Vector)vecDomUnchanged.elementAt(0)).elementAt(0)).elementAt(a)).clone());
                                        vecDom2.setElementAt(((Vector) vecDomUnchanged2.elementAt(a)).clone(), a);
                                        String rangeVec2 = nextEl.elementAt(1).toString();
                                        if (rangeVec2.startsWith("Select")) {
                                            rangeVec2 = "";
                                        }
                                        String[] k = rangeVec2.split(" ");
                                        for (int p = 0; p < k.length; p++) {
                                            if (k[p].equalsIgnoreCase(entityName)) {
                                                break;
                                            } else {
                                                if (p < k.length - 1) {
                                                    continue;
                                                } else {
                                                    if (nextEl.elementAt(1).toString().startsWith("Select")) {
                                                        nextEl.set(1, entityName);
                                                    } else {
                                                        nextEl.set(1, entityName + " " + nextEl.elementAt(1).toString());
                                                    }
                                                    //Object obj1=mainDBHashtable.get(obj);
                                                    //obj1=(Object) nextEl;
                                                    if (result.ontoHash.getMinOfMaxCardinalities(result.ontoHash.getParents(domainf).elementAt(0).toString(), propName) < nextEl.elementAt(1).toString().split(" ").length) {
                                                   //     MessageDialog error = new MessageDialog(null, "Entity " + domainf + " has more fillers than " + result.ontoHash.getMinOfMaxCardinalities(result.ontoHash.getParents(domainf).elementAt(0).toString(), propName) + " (from maxCardinality restriction)");
                                                        //mainDBHashtable=backup;
                                                        System.err.println("ELEON MicroReasoner: " +"Entity " + domainf + " has more fillers than " + result.ontoHash.getMinOfMaxCardinalities(result.ontoHash.getParents(domainf).elementAt(0).toString(), propName) + " (from maxCardinality restriction)");
                                                        Enumeration backupKeys = backup.keys();
                                                        Enumeration backupElements = backup.elements();
                                                        while (backupKeys.hasMoreElements()) {
                                                            result.putEntityTypeOrEntityToDB(backupKeys.nextElement().toString(), (NodeVector) backupElements.nextElement());
                                                        }
                                                        return;
                                                    }
                                                    if (result.ontoHash.getCardinality(result.ontoHash.getParents(domainf).elementAt(0).toString(), propName) < nextEl.elementAt(1).toString().split(" ").length && result.ontoHash.getCardinality(result.ontoHash.getParents(domainf).elementAt(0).toString(), propName) != -1) {


                                                        System.err.println("Entity " + domainf + " has more fillers than " + result.ontoHash.getCardinality(result.ontoHash.getParents(domainf).elementAt(0).toString(), propName) + "(from Cardinality restriction)");

                                                        Enumeration backupKeys = backup.keys();
                                                        Enumeration backupElements = backup.elements();
                                                        while (backupKeys.hasMoreElements()) {
                                                            result.putEntityTypeOrEntityToDB(backupKeys.nextElement().toString(), (NodeVector) backupElements.nextElement());
                                                        }
                                                        return;

                                                    }
                                                }
                                            }



                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            propVector.setElementAt(inversePropName, 5);
            inversePropVector.setElementAt(propName, 5);
            if (propVector.elementAt(7).toString().equalsIgnoreCase("true")) {
                inversePropVector.setElementAt("true", 6);
            }
            if (inversePropVector.elementAt(7).toString().equalsIgnoreCase("true")) {
                propVector.setElementAt("true", 6);
            }
            if (inversePropName.equalsIgnoreCase(propName)) {
                propVector.setElementAt("true", 9);
            }





        } else {
            propVector.setElementAt("", 5);
        }
    }



    private void checkSubproperties(String propName) {
        Vector propVector=(Vector) result.ontoHash.propertiesHashtable.get(propName);
         Vector sub= (Vector) propVector.elementAt(2);

            for(int n=0;n<sub.size();n++){
                addSubpropertyValueToProperty(propName, sub.elementAt(n).toString());
            }


    }
    
    private void checkSuperproperties(String propName) {
        Vector propVector=(Vector) result.ontoHash.propertiesHashtable.get(propName);
         Vector sup= (Vector) propVector.elementAt(2);

            for(int n=0;n<sup.size();n++){
                addSubpropertyValueToProperty( sup.elementAt(n).toString(), propName);
            }


    }


     /* It is used  to add
      the subroperty values to the superproperty*/
      private void addSubpropertyValueToProperty(String Property, String Subproperty){
    Vector SubVector=(Vector) result.ontoHash.propertiesHashtable.get(Subproperty);
    Vector domainSub=(Vector) SubVector.elementAt(0);
    for(int h=0;h<domainSub.size();h++){
        String domain=domainSub.elementAt(h).toString();
        Enumeration en=DataBasePanel.last.preorderEnumeration();
        DefaultMutableTreeNode node=new DefaultMutableTreeNode();
        while(en.hasMoreElements()){
            node=(DefaultMutableTreeNode) en.nextElement();
            if (node.toString().equalsIgnoreCase(domain)) break;
        }
        Hashtable ht=result.ontoHash.getChildrenEntities(node);
        Enumeration entities= ht.elements();
        Vector propVec=new Vector();
        String value="";
        while(entities.hasMoreElements()){
            NodeVector nv=(NodeVector) entities.nextElement();
            Vector propVector=(Vector) nv.elementAt(0);
            propVector=(Vector) propVector.elementAt(0);
            for(int j=3;j<propVector.size();j++){
                Vector property=(Vector) propVector.elementAt(j);
                if (property.elementAt(0).toString().equalsIgnoreCase(Subproperty)){
                    value=property.elementAt(1).toString();
                }
                if(property.elementAt(0).toString().equalsIgnoreCase(Property)){
                    propVec=property;
                }
            }
            propVec.setElementAt(value,1);
        }

    }

}

    private void checkSymmetricProperty(String propName) {
        Vector propVector = (Vector) result.ontoHash.propertiesHashtable.get(propName);

        //If domain is different froma the range remove the definition of symmetry
        if (!vectorsHaveSameElements((Vector) propVector.elementAt(0), (Vector) propVector.elementAt(1))) {
            propVector.setElementAt("false", 9);
            System.err.println("ELEON MicroReasoner: Property " + propName + " cannot be symmetric" +
                    " because its range and its domain are different. Definition of Symmetry removed.");
        } else {

            //if a different property is defined as its inverse
            if (!propVector.elementAt(5).equals("") && !propVector.elementAt(5).equals(propName)) {
                System.err.println("ELEON MicroReasoner: Property " + propName + " is symmetric." +
                        " The inverse of this property must be itself. Definition of " + propVector.elementAt(5).toString() + "" +
                        " as the inverse removed.");
            }
            propVector.setElementAt(propName, 5);



            Vector domain = (Vector) propVector.elementAt(0);
            for (int y = 0; y < domain.size(); y++) {

                Vector childrenEntities = result.ontoHash.getFullPathChildrenVectorFromMainDBHashtable(domain.elementAt(y).toString(), "Entity");

                for (int k = 0; k < childrenEntities.size(); k++) {
                    NodeVector entity = (NodeVector) result.ontoHash.mainDBHashtable.get(childrenEntities.elementAt(k));
                    String entityName = childrenEntities.elementAt(k).toString();
                    Vector fields = entity.getIndependentFieldsVector();

                    for (int r = 3; r < fields.size(); r++) {
                        Vector nextField = (Vector) fields.elementAt(r);
                        if (nextField.elementAt(0).toString().equalsIgnoreCase(propName)) {
                            String rangeVec = nextField.elementAt(1).toString();
                            String[] j = rangeVec.split(" ");
                            for (int f = 0; f < j.length; f++) {
                                String domainf = j[f];
                                System.out.println(domainf);
                                if (domainf.equalsIgnoreCase("Select") || domainf.equalsIgnoreCase("")) {
                                    break;
                                }

                                result.ontoHash.addFillerToProperty(propName, domainf, entityName, 1);
                            }
                        }
                    }
                }
            }
        }
        Enumeration names = result.ontoHash.propertiesHashtable.keys();
        Enumeration allprop = result.ontoHash.propertiesHashtable.elements();
        //remove the inverse declaration from the ex-inverse of the modified property
        while (allprop.hasMoreElements()) {
            String nextProp = names.nextElement().toString();
            Vector next = (Vector) allprop.nextElement();
            if (!nextProp.equalsIgnoreCase(propName)) {
                if (next.elementAt(5).toString().equalsIgnoreCase(propName)) {
                    next.setElementAt("", 5);
                    System.err.println("ELEON MicroReasoner: Property " + nextProp + " has as inverse the property" + propName +
                            ", which is symmetric. Definition of inverse property will be removed.");
                    break;
                }
            }
        }
    }

    private void checkTransitiveProperty(String propName) {
        Vector propVector = (Vector) result.ontoHash.propertiesHashtable.get(propName);
        boolean restriction = false;
        Enumeration restrictionKeys = result.ontoHash.valueRestrictionsHashtable.keys();
        Enumeration restrictions = result.ontoHash.valueRestrictionsHashtable.elements();
        while (restrictionKeys.hasMoreElements()) {
            Vector nextRes = (Vector) restrictions.nextElement();
            if (restrictionKeys.nextElement().toString().split(":")[1].equalsIgnoreCase(propName)) {
                if (!((Vector) nextRes.elementAt(3)).isEmpty() || !((Vector) nextRes.elementAt(4)).isEmpty() || !((Vector) nextRes.elementAt(5)).isEmpty()) {
                   System.err.println("ELEON MicroReasoner: There is already a cardinality restriction. The property "+propName+" cannot be declared transitive(OWL FULL element)");

                    restriction = true;
                    break;
                }
            }
        }
        if (propVector.elementAt(6).toString().equalsIgnoreCase("true")) {
            System.err.println("ELEON MicroReasoner: The property "+propName+" is functional. It cannot be declared transitive(OWL FULL element)");
            restriction = true;
        }
        if (!restriction) {
            Vector domain = new Vector((Vector) propVector.elementAt(0));
            for (int y = 0; y < domain.size(); y++) {
                Enumeration en1 = DataBasePanel.top.preorderEnumeration();
                DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode();
                while (en1.hasMoreElements()) {
                    Object ob1 = en1.nextElement();
                    if (ob1.toString().equalsIgnoreCase(domain.elementAt(y).toString())) {
                        dmtn = (DefaultMutableTreeNode) ob1;
                        break;
                    }
                }
                //  Vector children=getChildrenVectorFromMainDBHashtable(domain.elementAt(y).toString(),"entity");
                Enumeration elems = result.ontoHash.getChildrenEntities(dmtn).elements();
                Enumeration keys = result.ontoHash.getChildrenEntities(dmtn).keys();

                while (elems.hasMoreElements()) {
                    Vector childVec1 = (Vector) elems.nextElement();
                    Object obj = keys.nextElement();
                    Vector childVec = (Vector) childVec1.elementAt(0);
                    childVec = (Vector) childVec.elementAt(0);
                    for (int r = 3; r < childVec.size(); r++) {
                        Vector propVec = (Vector) childVec.elementAt(r);
                        System.out.println("childVec.elementAt(r)" + childVec.elementAt(r).toString());
                        if (propVec.elementAt(0).toString().equalsIgnoreCase(propName)) {
                            String rangeVec = propVec.elementAt(1).toString();
                            System.out.println("rangeVec" + rangeVec);
                            String[] j = rangeVec.split(" ");
                            for (int f = 0; f < j.length; f++) {
                                String domainf = j[f];
                                System.out.println(domainf);
                                if (domainf.startsWith("Select") || domainf.equalsIgnoreCase("")) {
                                    break;
                                }
                                Vector vecDom = (Vector) result.ontoHash.mainDBHashtable.get(domainf);
                                vecDom = (Vector) vecDom.elementAt(0);
                                vecDom = (Vector) vecDom.elementAt(0);
                                System.out.println(vecDom.toString());
                                for (int a = 3; a < vecDom.size(); a++) {
                                    Vector nextEl = (Vector) vecDom.elementAt(a);
                                    System.out.println(nextEl.toString());
                                    if (nextEl.elementAt(0).toString().equalsIgnoreCase(propName) && (!nextEl.elementAt(1).toString().startsWith("Select"))) {
                                        System.out.println(nextEl.elementAt(0).toString());
                                        for (int b = 0; b < j.length; b++) {
                                            if (j[b].equalsIgnoreCase(nextEl.elementAt(1).toString())) {
                                                break;
                                            } else {
                                                if (b < j.length - 1) {
                                                    continue;
                                                } else {
                                                    propVec.set(1, rangeVec + " " + nextEl.elementAt(1).toString());
                                                    Object obj1 = result.ontoHash.mainDBHashtable.get(obj);
                                                    obj1 = (Object) childVec;
                                                //System.out.println("kkk"+propVec.elementAt(1));
                                                //System.out.println("ddddd"+mainDBHashtable.get(obj));
                                                }
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    }

                }
            }
        }
    }

    /*returns true if the two vectors have the same elements. It does
    not take into consideration the order*/
    private boolean vectorsHaveSameElements(Vector v1, Vector v2) {
        if (v1.size() != v2.size()) {
            return false;
        }
        for (int i = 0; i < v1.size(); i++) {
            if (!v1.contains(v2.elementAt(i)) || !v2.contains(v1.elementAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean canSetFunctional(String node, String name) {
        Hashtable childrenEntities;
        try{
        childrenEntities = result.ontoHash.getChildrenEntities(DataBasePanel.getNode(node));
        }catch(java.lang.NullPointerException npe){
            System.err.println("Node "+node+" not found");
            return false;
        }
        Enumeration keys = childrenEntities.keys();
        Enumeration values = childrenEntities.elements();
        while (keys.hasMoreElements()) {
            String nextKey = keys.nextElement().toString();
            Vector nextEntity = (Vector) values.nextElement();
            nextEntity = (Vector) nextEntity.elementAt(0);
            nextEntity = (Vector) nextEntity.elementAt(0);
            for (int y = 3; y < nextEntity.size(); y++) {
                Vector nextField = (Vector) nextEntity.elementAt(y);
                if (nextField.elementAt(0).toString().equals(name)) {
                    if (!nextField.elementAt(1).toString().startsWith("Select ") && nextField.elementAt(1).toString().split(" ").length > 1) {
                        //MessageDialog error = new MessageDialog(null, "Error. " + nextKey + " has more than 1 value for " + name);
                        System.err.println("ELEON MicroReasoner: " + nextKey + " has more than 1 value for " + name);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //checks if entities have more fillers for a property than the card restrictions allow
    private boolean checkExistingEntities(String type, String propName, String value) {
        Vector childrenEntities = result.ontoHash.getFullPathChildrenVectorFromMainDBHashtable(type, "Entity");
        
        for (int g=0;g<childrenEntities.size();g++) {
            String nextKey =childrenEntities.elementAt(g).toString();
            Vector nextEntity = (Vector) result.ontoHash.mainDBHashtable.get(nextKey);
            nextEntity = (Vector) nextEntity.elementAt(0);
            nextEntity = (Vector) nextEntity.elementAt(0);
            for (int y = 3; y < nextEntity.size(); y++) {
                Vector nextField = (Vector) nextEntity.elementAt(y);
                if (nextField.elementAt(0).toString().equals(propName)) {
                    int j = 0;
                    try {
                        j = Integer.parseInt(value);
                    } catch (NumberFormatException n) {
                        System.out.println("INVALLID NUMBER");
                        return false;
                    }
                    if (!nextField.elementAt(1).toString().startsWith("Select ") && nextField.elementAt(1).toString().split(" ").length > j) {
                       System.err.println("ELEON MicroReasoner: "+nextKey + " has more than " + value + " values for " + propName+" (from cardinality restriction)");
                          return false;
                    }
                }
            }
        }
        return true;
    }

    private void addHasValueToEntities(String type, String propName, String value) {
       
            
            Vector childrenEntities = result.ontoHash.getFullPathChildrenVectorFromMainDBHashtable(type, "Entity");
        
        for (int k=0;k<childrenEntities.size();k++) {
                Vector v = (Vector) result.ontoHash.mainDBHashtable.get(childrenEntities.elementAt(k).toString());
                v = (Vector) v.elementAt(0);
                Vector vec = new Vector();
                PropertiesHashtableRecord propVector=(PropertiesHashtableRecord)result.ontoHash.propertiesHashtable.get(propName);
                if (!propVector.getRange().elementAt(0).equals("String")) {
                    vec = (Vector) v.elementAt(0);
                    for (int y = 3; y < vec.size(); y++) {
                        Vector next = (Vector) vec.elementAt(y);
                        if (next.elementAt(0).toString().equalsIgnoreCase(propName)) {
                            if (next.elementAt(1).toString().startsWith("Select ") || next.elementAt(1).toString().equalsIgnoreCase("")) {
                                next.setElementAt(value, 1);
                            } else {
                                boolean add = true;
                                String[] values = next.elementAt(1).toString().split(" ");
                                for (int h = 0; h < values.length; h++) {
                                    if (values[h].equalsIgnoreCase(value)) {
                                        add = false;
                                        break;
                                    }
                                }
                                if (add) {
                                    next.setElementAt(next.elementAt(1) + " " + value, 1);
                                }
                            }
                            break;
                        }
                    }
                } else {
                    for (int g = 1; g < 4; g++) {
                        vec = (Vector) v.elementAt(g);
                        for (int y = 6; y < vec.size(); y++) {
                            Vector next = (Vector) vec.elementAt(y);
                            if (next.elementAt(0).toString().equalsIgnoreCase(propName)) {
                                if (next.elementAt(1).toString().startsWith("Select ") || next.elementAt(1).toString().equalsIgnoreCase("")) {
                                    next.setElementAt(value, 1);
                                } else {
                                    boolean add = true;
                                    String[] values = next.elementAt(1).toString().split(" ");
                                    for (int h = 0; h < values.length; h++) {
                                        if (values[h].equalsIgnoreCase(value)) {
                                            add = false;
                                            break;
                                        }
                                    }
                                    if (add) {
                                        next.setElementAt(next.elementAt(1) + " " + value, 1);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }


    /*this method returns a deep copy of a hashtable whose keys are immutable objects and
     * whose elements are hashtables or vectors or PropertiesHashtableRecords or NodeVectors or immutable objects*/
    private Hashtable cloneHash(Hashtable hash){
        Enumeration elements=hash.elements();
        Enumeration keys=hash.keys();
        Hashtable resultHash=new Hashtable();
        while(elements.hasMoreElements()){
            Object nextElement=elements.nextElement();
            Object nextKey=keys.nextElement();
            if(nextElement instanceof Hashtable){
                resultHash.put(nextKey, cloneHash((Hashtable)nextElement));
            }
            else if(nextElement instanceof NodeVector){
                 resultHash.put(nextKey, cloneNodeVector((NodeVector)nextElement));
            }
             else if(nextElement instanceof PropertiesHashtableRecord){
                 resultHash.put(nextKey, clonePropertiesHashtableRecord((PropertiesHashtableRecord)nextElement));
            }
            else if(nextElement instanceof Vector){
                 resultHash.put(nextKey, cloneVector((Vector)nextElement));
            }
            else
                resultHash.put(nextKey, nextElement);
        }
                return resultHash;
    }

    /*this method returns a deep copy of a vector whose
     elements are hashtables or NodeVectors or vectors or immutable objects*/
        private Vector cloneVector(Vector vec){
        Vector resultVec=new Vector();
        resultVec.setSize(vec.size());
        for(int i=0;i<vec.size();i++){
            Object nextElement=vec.elementAt(i);
            if(nextElement instanceof Hashtable){
                resultVec.setElementAt(cloneHash((Hashtable)nextElement), i);
            }
            else if(nextElement instanceof FieldData){
                 resultVec.setElementAt(cloneFieldData((FieldData)nextElement), i);
            }
            else if(nextElement instanceof DefaultVector){
                 resultVec.setElementAt(cloneDefaultVector((DefaultVector)nextElement), i);
            }
            else if(nextElement instanceof NodeVector){
                 resultVec.setElementAt(cloneNodeVector((NodeVector)nextElement), i);
            }
            else if(nextElement instanceof Vector){
                 resultVec.setElementAt(cloneVector((Vector)nextElement), i);
            }
            else
                resultVec.setElementAt(nextElement, i);
        }
        return resultVec;
        }

        /*this method returns a deep copy of a NodeVector*/
        private NodeVector cloneNodeVector(NodeVector vec){
        Vector resultVec=new Vector();
        resultVec.setSize(vec.size());
        for(int i=0;i<vec.size();i++){
            Object nextElement=vec.elementAt(i);
            if(nextElement instanceof Hashtable){
                resultVec.setElementAt(cloneHash((Hashtable)nextElement), i);
            }
            else if(nextElement instanceof FieldData){
                 resultVec.setElementAt(cloneFieldData((FieldData)nextElement), i);
            }
            else if(nextElement instanceof TemplateVector){
                 resultVec.setElementAt(cloneTemplateVector((TemplateVector)nextElement), i);
            }
            else if(nextElement instanceof NodeVector){
                 resultVec.setElementAt(cloneNodeVector((NodeVector)nextElement), i);
            }
            else if(nextElement instanceof DefaultVector){
                 resultVec.setElementAt(cloneDefaultVector((DefaultVector)nextElement), i);
            }
            else if(nextElement instanceof Vector){
                 resultVec.setElementAt(cloneVector((Vector)nextElement), i);
            }
            else
                resultVec.setElementAt(nextElement, i);
        }
        return new NodeVector(resultVec);
        }

        /*this method returns a deep copy of a FiedlData*/
        private FieldData cloneFieldData(FieldData fd){
            if(fd.size()==2)
                return new FieldData(fd.elementAt(0).toString(), fd.elementAt(1).toString());
            else
                return new FieldData(fd.elementAt(0).toString(), fd.elementAt(1).toString(), false, "");

        }

        private DefaultVector cloneDefaultVector(DefaultVector dv){
             Vector resultVec=new Vector();
        resultVec.setSize(dv.size());
        for(int i=0;i<dv.size();i++){
            Object nextElement=dv.elementAt(i);
            if(nextElement instanceof FieldData){
                resultVec.setElementAt(cloneFieldData((FieldData)nextElement), i);
            }
            else
                resultVec.setElementAt(nextElement, i);
        }
        return new DefaultVector(resultVec);
        }


          /*this method returns a deep copy of a PropertiesHashtableRecord*/
        private PropertiesHashtableRecord clonePropertiesHashtableRecord(PropertiesHashtableRecord p){
            Vector resultVec=new Vector();
        resultVec.setSize(p.size());
        for(int i=0;i<p.size();i++){
            Object nextElement=p.elementAt(i);
            if(nextElement instanceof Hashtable){
                resultVec.setElementAt(cloneHash((Hashtable)nextElement), i);
            }
            else if(nextElement instanceof NodeVector){
                 resultVec.setElementAt(cloneNodeVector((NodeVector)nextElement), i);
            }
            else if(nextElement instanceof TemplateVector){
                 resultVec.setElementAt(cloneTemplateVector((TemplateVector)nextElement), i);
            }
            else if(nextElement instanceof Vector){
                 resultVec.setElementAt(cloneVector((Vector)nextElement), i);
            }
            else
                resultVec.setElementAt(nextElement, i);
        }
        return new PropertiesHashtableRecord(resultVec);
        }

        private TemplateVector cloneTemplateVector(TemplateVector tv){
             Vector resultVec=new Vector();
        resultVec.setSize(tv.size());
        for(int i=0;i<tv.size();i++){
            Object nextElement=tv.elementAt(i);
            if(nextElement instanceof Hashtable){
                resultVec.setElementAt(cloneHash((Hashtable)nextElement), i);
            }
            else
                resultVec.setElementAt(nextElement, i);
        }
        return new TemplateVector(resultVec);
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
