package gr.demokritos.iit.indigo.adaptation;


import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.hp.hpl.jena.vocabulary.*;

public class UMVisitImp implements UMVisit {
    //personalization server communication variables
    private InetAddress dst;  //personalization server IP address
    private int prt;          //personalization server port
    private boolean post;     //if true, HTTP POST method, else GET
    private int timeout;      //timeout period for reading server response, 0 for infinite
    
    private String username;	//for the connection with the mysql DB
    private String password;
    private String xeniosPath;	//the path for Xenios DB, which is like: "jdbc:mysql://localhost/xenios"
    private String PerServerPath; // the path for PerServer like above ...
    
    private Connection conn;
    
    //constructor
    public UMVisitImp(InetAddress dst, int prt, String username, String password, String xeniosPath, String PerServerPath) {
        try {
            this.dst = dst;
            this.prt = prt;
            post = true;    //method POST for safety
            timeout = 5000;       //in milliseconds
            this.username = username;
            this.password = password;
            this.xeniosPath = xeniosPath;
            this.PerServerPath = PerServerPath;
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(xeniosPath, username, password);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void newUser(String userName, String userType) throws UMException {
        System.err.println("newUser" + userName + " " + userType);
        
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        System.err.println("Pserver:new user :" + userName + ":" + userType + ":");
        //Insert new User to PerServer
        PSClientRequest pc;
        
        //add user to personal mode
        String request_1 = "/pers?clnt=pns|2beornot2be&com=setusr&usr=" + userName;
        pc = new PSClientRequest(dst, prt, request_1, post, timeout);
        if (pc.isError()) throw new UMException(pc.getErrorMessage());
        
        
        //add user to stereotype mode (userType is the user stereotype)
        String request = "/ster?clnt=pns|2beornot2be&com=addusr&usr=" + userName + "&" + userType + "=1";
        pc = new PSClientRequest(dst, prt, request, post, timeout);
        if (pc.isError()) throw new UMException(pc.getErrorMessage());
        
        //Insert new User to Xenios DB
        
        try {
            
            Statement Stmt = conn.createStatement();
            int count;
            
            String rows = "('" + userName + "','" + userType + "')";
            
            count = Stmt.executeUpdate("INSERT INTO users VALUES "+rows);
            
            Stmt.close();
            
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to Deposit New User ***");
            e.printStackTrace();
            throw new UMException("Connection have problem to Deposit newUser");
        }
        
    }//End of void newUser
    
    public boolean checkUserExists(String userName) throws UMException {
        System.err.println("checkUserExists" + userName + " " );
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        
        //check if exist in PS
        PSClientRequest pc;
        String request = "/pers?clnt=pns|2beornot2be&com=getusr&usr=" + userName + "&ftr=*";
        pc = new PSClientRequest(dst, prt, request, post, timeout);
        if (pc.isError()) throw new UMException(pc.getErrorMessage());
        boolean exists_in_ps = pc.getRows() > 0? true: false;
        //System.out.println("Ston PS: "+exists_in_ps);
        
        //check if exist in Xenios
        
        try {
            
            Statement Stmt = conn.createStatement();
            Stmt.executeQuery("SELECT u.id FROM users u WHERE u.id="+userName);
            ResultSet rs = Stmt.getResultSet();
            int count=0;
            
            while(rs.next()) {
                int idVal = rs.getInt("id");
                count++;
            }
            
            boolean exists_in_xenios = count > 0? true: false;
            //System.out.println("Ston Xenios: "+exists_in_xenios);
            rs.close();
            Stmt.close();
            
            boolean exists=(exists_in_xenios==true && exists_in_ps==true)? true: false;
            return exists;
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to checkUserExists ***");
            throw new UMException("Connection have problem to checkUserExists");
        }
        
    }//End of boolean checkUserExists
    
    
    public float getInterestFact(String factId, String ClassURI, String userName, String userType, String robotPersonality) throws UMException {
        System.err.println("getInterestFact " + userName + " " + userType + " " + factId);
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        //  Vector factId has struct: [InstanceURI_1, PropertyURI_1, Value_of_PropertyURI_1]
        
        //Connection conn = null;
        int index1 = factId.indexOf("," , 0);
        int index2 = factId.indexOf("," , index1 +2);
        
        
        String subURI = factId.substring(1, index1);
        String PrpURI = factId.substring(index1 + 2, index2);
        String objURI = factId.substring(index2 + 2, factId.length()-1);
        
        String Instance = subURI;
        Instance = Instance.substring(Instance.indexOf('#') + 1, Instance.length());
        String Property = PrpURI;
        
        ClassURI = ClassURI.substring(ClassURI.indexOf('#') + 1, ClassURI.length());
        
        System.out.println("*GetInterestFact for-> Inst:" + Instance + " Propert:" + Property);
        
        //if Property has value "typeOfClass" then wants the Class type_of or subtype_of
        if(Property.compareTo("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") == 0 || Property.compareTo(RDFS.subClassOf.toString()) == 0) {
            
            System.out.println("\t*Einai typou typeOfCLass");
            
            try {
                float interest = 1; // DIM Galanis to allaxa se 1 apo 0
                String inter1 = "";
                String inter2 = "";
                
                Statement Stmt = conn.createStatement();
                ResultSet rs = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='#"+ClassURI+"."+Instance+"'");
                
                while(rs.next()) {
                    inter1=rs.getString("interest");
                }
                
                //if the interest-typeOf-instance haven't defined, take the interest-typeOf-class
                if(inter1 == null || inter1.equals("")) {
                    ResultSet rs1 = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='#"+ClassURI+"'");
                    
                    while(rs1.next() ) {
                        inter2 = rs1.getString("interest");
                    }
                    
                    if(!(inter2 == null || inter2.equals("")))
                        interest = Float.parseFloat(inter2);
                    
                    rs1.close();
                    
                } else {
                    interest = Float.parseFloat(inter1);
                }
                
                //%%%%%%%%% 14/11 PROS8IKH GIA TO ROBOTIC-MODELING <1> %%%%%%%%%%%%%%%%%% 
                Stmt = conn.createStatement();
                rs = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='RobotType:"+robotPersonality+"' AND ftr='#"+ClassURI+"."+Instance+"'");
                
                String Robotic_preference_as_string=null;
                while(rs.next())
                {
                	Robotic_preference_as_string=rs.getString("interest");
                }
                
                Float Preference=Float.parseFloat(Robotic_preference_as_string);
                                
                                
                rs.close();                
                Stmt.close();
                
                Interact_User_with_Robotic_Modeling A=new Interact_User_with_Robotic_Modeling(interest,Preference);
                
                return A.Mean_of_Interests();
                //%%%%%%%%% 14/11 PROS8IKH GIA TO ROBOTIC-MODELING <\1> %%%%%%%%%%%%%%%%%%
                
            } catch (Exception e) {
                System.out.println("\n\n*** Connection have problem to getInterestFact of type_of ***");
                throw new UMException("Connection have problem to getInterestFact of type_of");
            }
            
        }//End of if
        else {
            Property = Property.substring(Property.indexOf('#') + 1,Property.length());
            
            System.out.println("\t*Einai typou [Ins, Proper, value] opou to property einai: "+Property);
            
            try {
                float interest = 1; // DIM Galanis to allaxa se 1 apo 0
                String inter1 = "";
                String inter2 = "";
                
                Statement Stmt = conn.createStatement();
                ResultSet rs = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='"+Property+"."+Instance+"'");
                
                while(rs.next()) {
                    inter1 = rs.getString("interest");
                }
                
                
                //if the interest-property-instance haven't defined, take the interest-property-class
                if(inter1 == null || inter1.equals("")) {
                    ResultSet rs1 = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='"+Property+"."+ClassURI+"'");
                    
                    while(rs1.next()) {
                        inter2 = rs1.getString("interest");
                    }
                    
                    if(inter2 == null || inter2.equals("")) {//if the interest-property-class haven't defined, take the default interest-property
                        ResultSet rs2 = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='"+Property+"'");
                        
                        while(rs2.next()) {
                            interest=rs2.getFloat("interest");
                        }
                        rs2.close();
                        
                    } else {
                        interest=Float.parseFloat(inter2);
                    }
                    
                    rs1.close();
                    
                } else {
                    interest=Float.parseFloat(inter1);
                }
                
                //%%%%%%%%% 14/11 PROS8IKH GIA TO ROBOTIC-MODELING <2> %%%%%%%%%%%%%%%%%%
                Stmt = conn.createStatement();
                rs = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='RobotType:"+robotPersonality+"' AND ftr='"+Property+"."+Instance+"'");
                
                String Robotic_preference_as_string=null;
                while(rs.next())
                {
                	Robotic_preference_as_string=rs.getString("interest");
                }
                
                Float Preference=Float.parseFloat(Robotic_preference_as_string);
                
                rs.close();                
                Stmt.close();
                
                Interact_User_with_Robotic_Modeling A=new Interact_User_with_Robotic_Modeling(interest,Preference);
                
                return A.Mean_of_Interests();
//              %%%%%%%%% 14/11 PROS8IKH GIA TO ROBOTIC-MODELING <\2> %%%%%%%%%%%%%%%%%%
                
            } catch (Exception e) {
                System.out.println("\n\n*** Connection have problem to getInterestFact of property***");
                //throw new UMException("Connection have problem to getInterestFact of property");
                e.printStackTrace();
                return 1;
            }
            
        }//End of else
        
    }//End of float getInterestFact
    
    public float getMicroPlanningAppropriateness(String microPlanningId, String userName, String userType) throws UMException {
        System.err.println("getMicroPlanningAppropriateness " + userName + " " + userType + " " + microPlanningId);
        microPlanningId = microPlanningId.substring(microPlanningId.indexOf('#')+1,microPlanningId.length());
        
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        try {
            
            Statement Stmt = conn.createStatement();
            ResultSet rs=Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='"+microPlanningId+"'");
            
            String inter="";
            float interest=0;
            
            while(rs.next()) {
                inter = rs.getString("interest");
            }
            
            if(inter == null || inter.equals("")) {
                return 1;
                // DIM Galanis evala tis 2 parakatw se sxolia
                //System.out.println("\n\n*** getMicroPlanningAppropriateness has null values THIS IS BUG! ***");
                //throw new UMException("Connection have problem to getInterestFact");
            } else {
                System.err.println("inter:" + inter);
                interest = Float.parseFloat(inter);
            }
            
            rs.close();
            Stmt.close();
            
            return interest;
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to getMicroPlanningAppropriateness ***");
            e.printStackTrace();
            return 1;
            //throw new UMException("Connection have problem to getMicroPlanningAppropriateness");
        }
        
        
    }//End of float getMicroPlanningAppropriateness
    
    public int getNumberOfFacts(String userName, String userType) throws UMException {
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        try {
            
            Statement Stmt = conn.createStatement();
            ResultSet rs = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='FPS'");
            
            String inter = "";
            int interest = 0;
            float cast = 0;
            
            while(rs.next()) {
                inter = rs.getString("interest");
            }
            
            if(inter == null) {
                System.out.println("\n\n*** getNumberOfFacts has null values THIS IS BUG! ***");
                throw new UMException("Connection have problem to getInterestFact");
            } else {
                cast=Float.parseFloat(inter);
            }
            
            rs.close();
            Stmt.close();
            interest = (int)cast;
            return interest;
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to getNumberOfFacts ***");
            throw new UMException("Connection have problem to getNumberOfFacts");
        }
        
    }//End of int getNumberOfFacts
    
    public int getMaxFactsPerSentence(String userName, String userType) throws UMException {
        
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        try {
            Statement Stmt = conn.createStatement();
            ResultSet rs=Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='MFPS'");
            
            String inter = "";
            int interest = 0;
            float cast = 0;
            
            while(rs.next()) {
                inter=rs.getString("interest");
            }
            
            if(inter == null) {
                System.out.println("\n\n*** getMaxFactsPerSentence has null values THIS IS BUG! ***");
                throw new UMException("Connection have problem to getMaxFactsPerSentence");
            } else {
                cast = Float.parseFloat(inter);
            }
            
            rs.close();
            Stmt.close();
            interest=(int)cast;
            return interest;
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to int getMaxFactsPerSentence ***");
            throw new UMException("Connection have problem to int getMaxFactsPerSentence");
        }
        
    }//End of int int getMaxFactsPerSentence
    
    public int getVoice(String userName, String userType) throws UMException {
        // return can take 3 values: 1 or 2 or 3
        //if return=1 means that SV=male, if return=2 -> SV=female, if return=2 -> SV=child
        
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        try {
            
            Statement Stmt = conn.createStatement();
            ResultSet rs = Stmt.executeQuery("SELECT interest FROM features f WHERE f.strp='"+userType+"' AND ftr='SV'");
            
            String inter = "";
            int interest = 0;
            float cast = 0;
            
            while(rs.next()) {
                inter = rs.getString("interest");
            }
            
            if(inter==null) {
                System.out.println("\n\n*** getVoice has null values THIS IS BUG! ***");
                throw new UMException("Connection have problem to getVoice");
            } else {
                cast=Float.parseFloat(inter);
            }
            
            rs.close();
            Stmt.close();
            interest=(int)cast;
            return interest;
            
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to int getVoice ***");
            throw new UMException("Connection have problem to int getVoice");
        }
        
    }//End of int int getMaxFactsPerSentence
    
    public float getAssimilationScore(String factId, String ClassURI, String userName, String userType) throws UMException {
        System.err.println("getAssimilationScore " + userName + " " + userType + " " + factId);
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        int index1 = factId.indexOf("," , 0);
        int index2 = factId.indexOf("," , index1 +2);
        //System.out.println("index1:" + index1 + " index2:" + index2);
        
        ClassURI = ClassURI.substring(ClassURI.indexOf('#')+1,ClassURI.length());
        
        String subURI = factId.substring(1, index1);
        String PrpURI = factId.substring(index1 + 2, index2);
        String objURI = factId.substring(index2 + 2, factId.length()-1);
        //factId has struct: [Instance Property Value_of_Property]
        //if we want the assimilation Score for a class and not for a property, we put "typeOfClass" instead of the propertyURI
        
        String Instance = subURI;
        Instance = Instance.substring(Instance.indexOf('#')+1,Instance.length());
        String Property = PrpURI;
        
        //------- pros8iki -----------------------------------------------------------
        //if is for Class-assimilationScore
        if(Property.compareTo("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") == 0 || Property.compareTo(RDFS.subClassOf.toString()) == 0) {
            
            try {
                float interest = 1; // DIM Galanis to allaxa se 1 apo 0
                String inter1 = "";
                
                Statement Stmt = conn.createStatement();
                ResultSet rs=Stmt.executeQuery("SELECT assimScore FROM assimilation_score a WHERE a.id='"+userName+"' AND a.strp='"+userType+"' AND a.ftr='#"+ClassURI+"."+Instance+"'");
                
                //if the fact isn't in Table "AssimilationScore" which means that
                //haven't use yet, take the initial assimScore from Table "features"
                if(!rs.next()) {
                    rs=Stmt.executeQuery("SELECT initscore FROM features WHERE strp='"+userType+"' AND ftr='#"+ClassURI+"."+Instance+"'");
                    
                    while(rs.next()) {
                        inter1 = rs.getString("initscore");
                    }
                } else {
                    inter1 = rs.getString("assimScore");
                }
                
                rs.close();
                Stmt.close();
                
                if (!( inter1 == null || inter1.equals("")))
                    interest = Float.parseFloat(inter1);
                
                return interest;
            } catch (Exception e) {
                System.out.println("\n\n*** Connection have problem to getAssimilationScore for Class***");
                throw new UMException("Connection have problem to getAssimilationScore for Class");
            }
        }//End of if-statement when the property is typeOfClass (for assimilation Score of Classes)
        else//------- pros8iki/ -----------------------------------------------------------
        {
            Property = Property.substring(Property.indexOf('#')+1,Property.length());
            
            try {
                float interest = 1; // DIM Galanis to allaxa se 1 apo 0
                String inter1 = "";
                
                Statement Stmt = conn.createStatement();
                ResultSet rs = Stmt.executeQuery("SELECT assimScore FROM assimilation_score a WHERE a.id='"+userName+"' AND a.strp='"+userType+"' AND a.ftr='"+Property+"."+Instance+"'");
                
                //if the fact isn't in Table "AssimilationScore" which means that
                //haven't use yet, take the initial assimScore from Table "features"
                
                if(!rs.next()) {
                    rs = Stmt.executeQuery("SELECT initscore FROM features WHERE strp='"+userType+"' AND ftr='"+Property+"."+Instance+"'");
                    while(rs.next()) {
                        inter1 = rs.getString("initscore");
                    }
                } else {
                    inter1 = rs.getString("assimScore");
                }
                
                rs.close();
                Stmt.close();
                
                System.out.println("inter1:" + inter1);
                
                if(inter1.equals("") || inter1 == null)
                    interest = 1;
                else
                    interest = Float.parseFloat(inter1);
                
                return interest;
            } catch (Exception e) {
                System.out.println("\n\n*** Connection have problem to getAssimilationScore ***");
                e.printStackTrace();
                throw new UMException("Connection have problem to getAssimilationScore");
            }
            
        }//End of else (in case that assimilation is for class)
        
    }//End of float getAssimilationScore
    
    public int getMentionedCount(String InstanceURI, String userName) throws UMException {
        
        InstanceURI = InstanceURI.substring(InstanceURI.indexOf('#')+1,InstanceURI.length());
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        
        
        try {
            
            Statement Stmt = conn.createStatement();
            ResultSet rs=Stmt.executeQuery("SELECT instCount FROM instances_used i WHERE i.id='"+userName+"' AND i.inst='"+InstanceURI+"'");
            
            String inter = "";
            int count = 0;
            float cast = 0;
            
            if(!rs.next()) {
                cast = 0;
            } else {
                inter=rs.getString("instCount");
                cast=Float.parseFloat(inter);
            }
            
            rs.close();
            Stmt.close();
            count=(int)cast;
            return count;
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to getMentionedCount ***");
            throw new UMException("Connection have problem to getMentionedCount");
        }
        
    }//End of int getMentionedCount
    
    
    public int getMicroPlanningCount(String microPlanningId, String userName, String userType) throws UMException {
        System.err.println("getMicroPlanningCount " + userName + " " + userType + " " + microPlanningId);
        microPlanningId = microPlanningId.substring(microPlanningId.indexOf('#')+1,microPlanningId.length());
        //userName = userName.substring(userName.indexOf('#')+1,userName.length());
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        
        try {
            Statement Stmt = conn.createStatement();
            ResultSet rs = Stmt.executeQuery("SELECT microcount FROM microplans m WHERE m.id='"+userName+"' AND m.strp='"+userType+"' AND m.ftr='"+microPlanningId+"'");
            
            String inter = "";
            int count = 0;
            float cast = 0;
            
            if(!rs.next()) {
                cast = 0;
            } else {
                inter = rs.getString("microcount");
                cast = Float.parseFloat(inter);
            }
            
            rs.close();
            Stmt.close();
            count = (int)cast;
            return count;
        } catch (Exception e) {
            System.out.println("\n\n*** Connection have problem to getMicroPlanningCount ***");
            throw new UMException("Connection have problem to getMicroPlanningCount");
        }
        
        
    }//End of int getMicroPlanningCount
    
    public void update(String[] InstanceURI, String[] Facts, String[] MicroplanURI, String userName, String userType) throws UMException {
        System.err.println("update " + userName + " " + userType + " ");
        System.err.println(InstanceURI.length + " " + MicroplanURI.length + " " + Facts.length + " ");
        
        userType = userType.substring(userType.indexOf('#')+1,userType.length());
        for(int i = 0; i < InstanceURI.length; i++) {
            System.err.println("update " + InstanceURI[i]);
        }
        
        for(int i = 0; i < MicroplanURI.length; i++) {
            System.err.println("update " + MicroplanURI[i]);
        }
        
        for(int i = 0; i < Facts.length; i++) {
            System.err.println("update " + Facts[i]);
        }
        
        //1.the InstanceURI[] has all Instances that have used e.g: CoffeShopDownstairs,computers,...
        
        //2.the Facts[] will contain in EACH position Strings EXACTLY-LIKE!! : [InstanceURI_1, Property_1, Value_of_Property_1],..,[InstanceURI_2, Property_2, Value_of_Property_2]
        //  the Facts[] could also contain to update assimilation score for Class. This can be achieved
        //  using instead of Property, "typeOfClass", like [InstURI, typeOfClass, ClassURI]
        
        //3.the MicroplanURI[] has all the Microplans that have used, e.g: Microplan_1,Microplan_2,...
        
        PSClientRequest pc;
        //Connection conn = null;
        
        
        //Update InstanceURI[]
        for (int i = 0; i < InstanceURI.length; i++) 
        {
            
            if(InstanceURI[i]!=null) {
                InstanceURI[i] = InstanceURI[i].substring(InstanceURI[i].indexOf('#')+1,InstanceURI[i].length());
                
                String request = "/pers?clnt=pns|2beornot2be&com=incval&usr="+ userName+"&Inst."+InstanceURI[i]+"=1";
                pc = new PSClientRequest(dst, prt, request, post, timeout);
                if (pc.isError()) throw new UMException(pc.getErrorMessage());
                
                String request_1 = "/ster?clnt=pns|2beornot2be&com=incval&str="+ userType+"&Inst."+InstanceURI[i]+"=1";
                pc = new PSClientRequest(dst, prt, request_1, post, timeout);
                if (pc.isError()) throw new UMException(pc.getErrorMessage());
                
                
                try {
                    Statement Stmt = conn.createStatement();
                    int count;
                    
                    ResultSet rs=Stmt.executeQuery("SELECT instCount FROM instances_used i WHERE i.id='"+userName+"' AND i.inst='"+InstanceURI[i]+"'");
                    
                    //if the Instance for that user isn't in "instances_used" (which means
                    //that the robot haven't talk yet to the user for that instance),
                    //so put it with instCount=1(the use now has informed about that once)
                    if(!rs.next()) {
                        String rows="('"+userName+"','"+InstanceURI[i]+"',1)";
                        count=Stmt.executeUpdate("INSERT INTO instances_used VALUES "+rows);
                        //System.out.println("Den upirxe kai to pros8esa "+count+"sto instances_used");
                        
                        //exist in "instances_used" (user have informed for that instance)
                        //so increase it's instCount++
                    } else {
                        int instCount=rs.getInt("instCount");
                        instCount=instCount+1;
                        count=Stmt.executeUpdate("UPDATE instances_used SET instCount="+instCount+" WHERE id='"+userName+"' AND inst='"+InstanceURI[i]+"'");
                        //System.out.println("Ypirxe kai au3isa +1 sto instances_used");
                    }
                    
                    rs.close();
                    //conn.close();
                    Stmt.close();
                } catch (Exception e) {
                    System.out.println("\n\n*** Connection have problem to Update InstanceURI[] ***");
                    e.printStackTrace();
                    //throw new UMException("Connection have problem to Update InstanceURI[]");
                }
                
                System.out.println("Have been updated Instance "+userName+ ", " + userType +": " +InstanceURI[i]);
            }//End of if InstanceURI[i]!=null
            
        }//End of for i<InstanceURI.length
        
        
        
        //---- pros8iki --------------------------------------------------------------
        
        //Update Facts[]
        for (int i=0; i<Facts.length; i++)
        {
            
            if(Facts[i]!=null){
                
                //parse array elements which are like: [Inst Proper Value]
                String Instance=Facts[i].substring(Facts[i].indexOf('[')+1,Facts[i].indexOf(","));
                Instance=Instance.substring(Instance.indexOf('#')+1,Instance.length());
                
                String temp=Facts[i].substring(Facts[i].indexOf(",")+2, Facts[i].length());
                String check=temp;
                check=check.substring(0, check.indexOf(","));
                
                //if is for class assimilationScore, like [InstURI typeOfClass ClassURI]
                
                
                if(check.compareTo("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") == 0 || check.compareTo(RDFS.subClassOf.toString()) == 0)
                {
                    
                    String forClass=temp.substring(temp.indexOf(",")+2, temp.length()-1);
                    forClass=forClass.substring(forClass.indexOf('#')+1,forClass.length());
                    //System.out.println("EINAI typeOfClass gia to class: "+forClass);
                    
                    try{
                        System.out.println("Have been updated Fact "+userName+ ", " + userType +": " +Facts[i]);
                        
                        Statement Stmt = conn.createStatement();
                        int count;
                        
                        ResultSet rs=Stmt.executeQuery("SELECT assimScore FROM assimilation_score a WHERE a.id='"+userName+"' AND a.ftr='#"+forClass+"."+Instance+"' AND a.strp='"+userType+"'");
                        
                        //if doesn't exist in "assimilation_score" add it with score=assimRate
                        if(!rs.next()){
                            
                            //take the assimRate for that property
                            float assimRate=0;
                            rs=Stmt.executeQuery("SELECT assimrate FROM features f WHERE f.ftr='#"+forClass+"."+Instance+"' AND f.strp='"+userType+"'");
                            
                            while(rs.next()){
                                assimRate=rs.getFloat("assimrate");
                            }
                            
                            //add new row in assimilation_score
                            if (assimRate==0){assimRate=1;}
                            
                            String rows="('"+userName+"','#"+forClass+"."+Instance+"',"+assimRate+",'"+userType+"')";
                            count=Stmt.executeUpdate("INSERT INTO assimilation_score VALUES "+rows);
                            
                            System.out.println("\tDen upirxe sto assimilation_score kai to ebala me score=AssimRate tou");
                            
                            //if exist in "assimilation_score" add it with score=score+assimRate
                        }else{
                            float assimScore=rs.getFloat("assimScore");
                            
                            //take the assimRate for that property
                            rs=Stmt.executeQuery("SELECT assimrate FROM features f WHERE f.ftr='#"+forClass+"."+Instance+"' AND f.strp='"+userType+"'");
                            float assimRate=0;
                            
                            while(rs.next()){
                                assimRate=rs.getFloat("assimrate");
                            }
                            
                            assimScore=assimScore+assimRate;
                            
                            
                            count=Stmt.executeUpdate("UPDATE assimilation_score SET assimScore="+assimScore+" WHERE id='"+userName+"' AND strp='"+userType+"' AND ftr='#"+forClass+"."+Instance+"'");
                            System.out.println("\tYpirxe sto assimilation_score kai to ebala me score=Score+AssimRate");
                        }
                        
                        
                        rs.close();
                        Stmt.close();
                    } 
                    catch (Exception e) {
                        System.out.println("\n\n*** Connection have problem to Update Facts[] ***");
                        e.printStackTrace();
                        //throw new UMException("Connection have problem to Update Facts[]");
                    }
                    
                    
                }                                
                else
                {// is like [InstURI, ProprtyURI, value_of_the_property]
                    temp=temp.substring(temp.indexOf('#')+1,temp.length());
                    
                    String Property=temp.substring(0, temp.indexOf(","));
                    Property=Property.substring(Property.indexOf('#')+1,Property.length());
                    
                    
                    String request = "/pers?clnt=pns|2beornot2be&com=incval&usr="+ userName+"&"+Property+"."+Instance+"=1";
                    pc = new PSClientRequest(dst, prt, request, post, timeout);
                    if (pc.isError()) throw new UMException(pc.getErrorMessage());
                    
                    String request_1 = "/ster?clnt=pns|2beornot2be&com=incval&str="+ userType+"&"+Property+"."+Instance+"=1";
                    pc = new PSClientRequest(dst, prt, request_1, post, timeout);
                    if (pc.isError()) throw new UMException(pc.getErrorMessage());
                    
                    
                    
                    try{
                        System.out.println("Have been updated Fact "+userName+ ", " + userType +": " +Facts[i]);
                        
                        Statement Stmt = conn.createStatement();
                        int count;
                        
                        ResultSet rs=Stmt.executeQuery("SELECT assimScore FROM assimilation_score a WHERE a.id='"+userName+"' AND a.ftr='"+Property+"."+Instance+"' AND a.strp='"+userType+"'");
                        
                        //if doesn't exist in "assimilation_score" add it with score=assimRate
                        if(!rs.next()){
                            
                            //take the assimRate for that property
                            float assimRate=0;
                            rs=Stmt.executeQuery("SELECT assimrate FROM features f WHERE f.ftr='"+Property+"."+Instance+"' AND f.strp='"+userType+"'");
                            
                            while(rs.next()){
                                assimRate=rs.getFloat("assimrate");
                            }
                            
                            //add new row in assimilation_score
                            if (assimRate==0){assimRate=1;}
                            
                            String rows="('"+userName+"','"+Property+"."+Instance+"',"+assimRate+",'"+userType+"')";
                            count=Stmt.executeUpdate("INSERT INTO assimilation_score VALUES "+rows);
                            
                            System.out.println("\tDen upirxe sto assimilation_score kai to ebala me score=AssimRate tou");
                            
                            //if exist in "assimilation_score" add it with score=score+assimRate
                        }else{
                            float assimScore=rs.getFloat("assimScore");
                            
                            //take the assimRate for that property
                            rs=Stmt.executeQuery("SELECT assimrate FROM features f WHERE f.ftr='"+Property+"."+Instance+"' AND f.strp='"+userType+"'");
                            float assimRate=0;
                            
                            while(rs.next()){
                                assimRate=rs.getFloat("assimrate");
                            }
                            
                            assimScore=assimScore+assimRate;
                            
                            
                            count=Stmt.executeUpdate("UPDATE assimilation_score SET assimScore="+assimScore+" WHERE id='"+userName+"' AND strp='"+userType+"' AND ftr='"+Property+"."+Instance+"'");
                            System.out.println("\tYpirxe sto assimilation_score kai to ebala me score=Score+AssimRate");
                        }
                        
                        
                        rs.close();
                        Stmt.close();
                    } catch (Exception e) {
                        System.out.println("\n\n*** Connection have problem to Update Facts[] ***");
                        e.printStackTrace();
                        //throw new UMException("Connection have problem to Update Facts[]");
                    }
                    
                }//End of else (when is [Inst Property ....])
                
            }//End of if Facts[i]!=null
            
        }//End of for i<Facts.length
        
        
        
        //---- pros8iki/ --------------------------------------------------------------
        
        //Update MicroplanURI[]
        for (int i = 0; i < MicroplanURI.length; i++) {
            
            if(MicroplanURI[i]!=null) {
                MicroplanURI[i] = MicroplanURI[i].substring(MicroplanURI[i].indexOf('#')+1,MicroplanURI[i].length());
                
                try {
                    System.out.println("Have been updated Microplan "+userName+ ", " + userType +": " +MicroplanURI[i]);
                    
                    Statement Stmt = conn.createStatement();
                    int count;
                    
                    ResultSet rs = Stmt.executeQuery("SELECT microcount FROM microplans m WHERE m.id='"+userName+"' AND m.ftr='"+MicroplanURI[i]+"' AND m.strp='"+userType+"'");
                    
                    
                    //if the Microplan for that user isn't in "microplans" (which means
                    //that the robot haven't used yet that Microplan),
                    //so put it with microcount=1(the use now has used that once)
                    if(!rs.next()) {
                        String rows="('"+userName+"','"+MicroplanURI[i]+"',1,'"+userType+"')";
                        count=Stmt.executeUpdate("INSERT INTO microplans VALUES "+rows);
                        
                        
                        //exist in "microplans" (robot have used that Microplan)
                        //so increase it's microcount++
                    } else {
                        int microcount = rs.getInt("microcount");
                        microcount = microcount + 1;
                        count = Stmt.executeUpdate("UPDATE microplans SET microcount="+microcount+" WHERE id='"+userName+"' AND ftr='"+MicroplanURI[i]+"' AND strp='"+userType+"'");
                    }
                    
                    rs.close();
                    Stmt.close();
                } catch (Exception e) {
                    System.out.println("\n\n*** Connection have problem to Update MicroplanURI[] ***");
                    e.printStackTrace();
                    //throw new UMException("Connection have problem to Update MicroplanURI[]");
                }
            }//End of if MicroplanURI[i]!=null
            
        }//End of for i<MicroplanURI.length
        
    }//End of void update
    
    public void CloseConnection() {
        try {
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}//End of UmVisitImp

