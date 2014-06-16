/*
 * ValueRestriction.java
 *
 * Created on 12 ����������� 2006, 11:30 ��
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gr.demokritos.iit.eleon.authoring;
import java.util.Vector;
/**
 *
 * @author dbilid
 */
public class ValueRestriction extends Vector{
    
    /** Creates a new instance of ValueRestriction */
    public ValueRestriction() {
        Vector allValuesFrom= new Vector();
        Vector someValuesFrom= new Vector();
        Vector hasValue=new Vector();
        Vector maxCard=new Vector();
        Vector minCard=new Vector();
        Vector Card=new Vector();
        add(allValuesFrom);
        add(someValuesFrom);
        add(hasValue);
        add(maxCard);
        add(minCard);
        add(Card);
    }
    
}
