package gr.demokritos.iit.eleon.um;


import java.util.Vector;


/********************************************************************
 ***********************   UTILITY CLASSES   ************************
 ********************************************************************/


//===================================================================
// KeyValVector
//
// A general-purpose class.
//
// A Vector implementation that maps keys to values. Each key is
// mapped to one value, and duplicate keys are NOT allowed.
//===================================================================
class KeyValVector {
    private Vector keys;
    private Vector vals;

//initializers
    public KeyValVector(int capacity) {
        //capacity increment is zero!
        keys = new Vector(capacity);
        vals = new Vector(capacity);
    }
    public KeyValVector(int initialCapacity, int capacityIncrement) {
        //capacity increment specifies step that vector grows
        keys = new Vector(initialCapacity, capacityIncrement);
        vals = new Vector(initialCapacity, capacityIncrement);
    }

//class info methods
    public int capacity() {
        return keys.capacity();
    }
    public int size() {
        return keys.size();
    }
    public boolean isEmpty() {
        return keys.isEmpty();
    }

//insert, update, remove, methods
    public boolean add(Object key, Object val) {
        //may fail if 'key' already exists
        //may fail if capacity = size and increment is 0
        if (existsKey(key)) return false;
        keys.add(key);
        vals.add(val);
        return true;
    }
    public boolean updateVal(Object newVal, Object key) {
        //returns false if 'key' does not exist
        int idx = keys.indexOf(key, 0);
        if (idx == -1) return false;
        try {
            vals.setElementAt(newVal, idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    public boolean updateVal(Object newVal, int idx) {
        //returns false if idx out of size() bounds
        try {
            vals.setElementAt(newVal, idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    public boolean remove(Object key) {
        //returns false if 'key' does not exist
        int idx = keys.indexOf(key, 0);
        if (idx == -1) return false;
        try {
            keys.remove(idx);
            vals.remove(idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    public boolean remove(int idx) {
        //returns false if idx out of size() bounds
        try {
            keys.remove(idx);
            vals.remove(idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    public void clear() {
        keys.clear();
        vals.clear();
    }

//query methods
    public boolean existsKey(Object key) {
        return keys.contains(key);
    }
    public int indexOfKey(Object key) {
        //returns -1 if key not exist
        return keys.indexOf(key, 0);
    }
    public int indexOfVal(Object val) {
        //returns -1 if val not exist
        return vals.indexOf(val, 0);
    }
    public Object getKey(int idx) {
        //returns null if idx out of size() bounds
        Object key;
        try {
            key = keys.get(idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            key = null;
        }
        return key;
    }
    public Object getVal(Object key) {
        //returns null if 'key' does not exist
        int idx = keys.indexOf(key, 0);
        if (idx == -1) return null;
        Object val;
        try {
            val = vals.get(idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            val = null;
        }
        return val;
    }
    public Object getVal(int idx) {
        //returns null if idx out of size() bounds
        Object val;
        try {
            val = vals.get(idx);
        } catch(ArrayIndexOutOfBoundsException e) {
            val = null;
        }
        return val;
    }

//misc methods
    public void trimToSize() {
        //Trims the capacity to be the current size
        keys.trimToSize();
        vals.trimToSize();
    }
    public void debug() {
        //prints vector contents for debugging
        for (int i=0; i < size(); i++) {
            Object key = keys.get(i);
            Object val = vals.get(i);
            System.out.println("KEY: " + key.toString() + " VAL: " + val.toString());
       }
    }
}


//===================================================================
// DoubleKey
//
// A general purpose class.
//
// A key consisting of two fields, that will be used to identify
// a value object.
//===================================================================
class DoubleKey {
    private String field_1 = null;
    private String field_2 = null;
    
//initializers
    public DoubleKey(String fieldOne, String fieldTwo) {
        field_1 = fieldOne;
        field_2 = fieldTwo;
    }
    
//get fields of key
    public String fOne() {
        return field_1;
    }
    public String fTwo() {
        return field_2;
    }
    
//equality checks
    public boolean equalsFieldOne(String val) {
        if (field_1 == null) return false;
        if (field_1.equals(val)) return true;
        return false;
    }
    public boolean equalsFieldTwo(String val) {
        if (field_2 == null) return false;
        if (field_2.equals(val)) return true;
        return false;
    }
    public boolean equals(Object obj) {
        if (field_1 == null || field_2 == null) return false;
        if (obj == null) return false;
        String thisClass = getClass().getName();
        String objClass = obj.getClass().getName();
        if ( ! thisClass.equals(objClass)) return false;
        DoubleKey key = (DoubleKey) obj;
        if (field_1.equals(key.fOne()) && 
            field_2.equals(key.fTwo())) return true;
        return false;
    }

//other methods
    public String toString() {
        return new String("(" + field_1 + ", " + field_2 + ")");
    }
}


/********************************************************************
 *********************   APPLICATION CLASSES   **********************
 ********************************************************************/


//===================================================================
// UNameVals
//
// Values that correspond to a single user.
//===================================================================
class UNameVals {
    public String userType;
    public String targetLang;
    public String currFocus;
    
    public UNameVals() {
        //default values
        userType = "";
        targetLang = "";
        currFocus = "";
    }
    public UNameVals(String userType, String targetLang, String currFocus) {
        this.userType = userType;
        this.targetLang = targetLang;
        this.currFocus = currFocus;
    }
    public String toString() {
        return new String("(" + userType + ", " + targetLang + ", " + currFocus + ")");
    }
}


//===================================================================
// UTypeVals
//
// Values that correspond to a type (group) of users.
//===================================================================
class UTypeVals {
    public int numOfFacts;
    public int factsPerSentence;
    public int numOfForwPointers;
    public String voice;
    
    public UTypeVals() {
        //default values
        numOfFacts = -1;
        factsPerSentence = -1;
        numOfForwPointers = -1;
        voice = "";
    }
    public UTypeVals(int numOfFacts, int factsPerSentence, int numOfForwPointers, String voice) {
        this.numOfFacts = numOfFacts;
        this.factsPerSentence = factsPerSentence;
        this.numOfForwPointers = numOfForwPointers;
        this.voice = voice;
    }
    public String toString() {
        return new String("(" + numOfFacts + ", " + factsPerSentence + ", " 
                              + numOfForwPointers + ", " + voice + ")");
    }
}


//===================================================================
// EntityVals
//
// Values that correspond to entities.
//===================================================================
class EntityVals {
    public int focused;
    public int mentioned;
    
    public EntityVals() {
        //default values
        focused = 0;
        mentioned = 0;
    }
    public EntityVals(int focused, int mentioned) {
        this.focused = focused;
        this.mentioned = mentioned;
    }
    public String toString() {
        return new String("(" + focused + ", " + mentioned + ")");
    }
}


//===================================================================
// FFVals
//
// Values that correspond to both facts and functors (fact categories).
//===================================================================
class FFVals {
    public float interest;
    public float importance;
    public float assimilRate;
    public float initAssimil;
    
    public FFVals() {
        //default values
        interest = -1;
        importance = -1;
        assimilRate = -1;
        initAssimil = -1;
    }
    public FFVals(float interest, float importance, float assimilRate, float initAssimil) {
        this.interest = interest;
        this.importance = importance;
        this.assimilRate = assimilRate;
        this.initAssimil = initAssimil;
    }
    public String toString() {
        return new String("(" + interest + ", " + importance + ", " 
                              + assimilRate + ", " + initAssimil + ")");
    }
}


/********************************************************************
 ********************   BASIC STRUCTURE CLASS   *********************
 ********************************************************************/


//===================================================================
// Structures
//
// The structures that hold the data: DEFINITION and DECLARATION.
// Observing the right order when setting and getting parameter
// values in the case of DoubleKey is important.
//===================================================================
public class Structures {
//structures concerned with single user properties
    public KeyValVector users;
    //KEY: userName VAL: UNameVals(userType,targetLang,currFocus)
    public KeyValVector assimilation;
    //KEY: DoubleKey(userName,factId) VAL: assimilation
    public KeyValVector entityProp;
    //KEY: DoubleKey(userName,entityName) VAL: EntityVals(focused,mentioned)
    public KeyValVector microCount;
    //KEY: DoubleKey(userName,microPlanId) VAL: used
    public KeyValVector wordCount;
    //KEY: DoubleKey(userName,word) VAL: used
//structures concerned with user type (group) properties
    public KeyValVector userTypes;
    //KEY: userType VAL: UTypeVals(numOfFacts,factsPerSentence,numOfForwPointers,voice)
    public KeyValVector approprLexical;
    //KEY: DoubleKey(userType,lexItemId) VAL: appropr
    public KeyValVector approprMicroPlan;
    //KEY: DoubleKey(userType,microPlanId) VAL: appropr
    public KeyValVector approprSchema;
    //KEY: DoubleKey(userType,schemaId) VAL: appropr
    public KeyValVector factProp;
    //KEY: DoubleKey(userType,factId) VAL: FFVals(interest,importance,assimilRate,initAssimil)
    public KeyValVector functorProp;
    //KEY: DoubleKey(userType,functor) VAL: FFVals(interest,importance,assimilRate,initAssimil)
    
    public Structures() {
        //initial size of vector and increment step, set accordingly
        users = new KeyValVector(50, 10);
        assimilation = new KeyValVector(1000, 100);
        entityProp = new KeyValVector(1000, 100);
        microCount = new KeyValVector(500, 100);
        wordCount = new KeyValVector(500, 100);
        userTypes = new KeyValVector(50, 10);
        approprLexical = new KeyValVector(1000, 100);
        approprMicroPlan = new KeyValVector(50, 10);
        approprSchema = new KeyValVector(50, 10);
        factProp = new KeyValVector(1000, 100);
        functorProp = new KeyValVector(500, 100);
    }
    public void clear() {
        users.clear();
        assimilation.clear();
        entityProp.clear();
        microCount.clear();
        wordCount.clear();
        userTypes.clear();
        approprLexical.clear();
        approprMicroPlan.clear();
        approprSchema.clear();
        factProp.clear();
        functorProp.clear();
    }
    public void debug() {
        System.out.println("=====SINGLE USER PROPERTIES=====");
        System.out.println("");
        System.out.println("=users structure=");
        System.out.println("KEY: userName VAL: (userType,targetLang,currFocus)");
        System.out.println("=users contents=");
        users.debug();
        System.out.println("");
        System.out.println("=assimilation structure=");
        System.out.println("KEY: (userName,factId) VAL: assimilation");
        System.out.println("=assimilation contents=");
        assimilation.debug();
        System.out.println("");
        System.out.println("=entityProp structure=");
        System.out.println("KEY: (userName,entityName) VAL: (focused,mentioned)");
        System.out.println("=entityProp contents=");
        entityProp.debug();
        System.out.println("");
        System.out.println("=microCount structure=");
        System.out.println("KEY: (userName,microPlanId) VAL: used");
        System.out.println("=microCount contents=");
        microCount.debug();
        System.out.println("");
        System.out.println("=wordCount structure=");
        System.out.println("KEY: (userName,word) VAL: used");
        System.out.println("=wordCount contents=");
        wordCount.debug();
        System.out.println("");
        System.out.println("=====USER TYPE PROPERTIES=====");
        System.out.println("");
        System.out.println("=userTypes structure=");
        System.out.println("KEY: userType VAL: (numOfFacts,factsPerSentence,numOfForwPointers,voice)");
        System.out.println("=userTypes contents=");
        userTypes.debug();
        System.out.println("");
        System.out.println("=approprLexical structure=");
        System.out.println("KEY: (userType,lexItemId) VAL: appropr");
        System.out.println("=approprLexical contents=");
        approprLexical.debug();
        System.out.println("");
        System.out.println("=approprMicroPlan structure=");
        System.out.println("KEY: (userType,microPlanId) VAL: appropr");
        System.out.println("=approprMicroPlan contents=");
        approprMicroPlan.debug();
        System.out.println("");
        System.out.println("=approprSchema structure=");
        System.out.println("KEY: (userType,schemaId) VAL: appropr");
        System.out.println("=approprSchema contents=");
        approprSchema.debug();
        System.out.println("");
        System.out.println("=factProp structure=");
        System.out.println("KEY: (userType,factId) VAL: (interest,importance,assimilRate,initAssimil)");
        System.out.println("=factProp contents=");
        factProp.debug();
        System.out.println("");
        System.out.println("=functorProp structure=");
        System.out.println("KEY: (userType,functor) VAL: (interest,importance,assimilRate,initAssimil)");
        System.out.println("=functorProp contents=");
        functorProp.debug();
    }
}
