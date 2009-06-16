package gr.demokritos.iit.eleon.authoring;

import java.io.IOException;


public class BackupDB {

	private String user;
	private String pass;
	
	//constructor
	public BackupDB(String the_user, String the_path) {
        this.user = the_user;
        this.pass = the_path;
            }
	
	
	
	public void CreateBackups(){
		
		
		try{
			Runtime.getRuntime().exec("cmd /c mysqldump -u"+ user +" -p"+ pass +" pserver>pserver.sql");
			Runtime.getRuntime().exec("cmd /c mysqldump -u"+ user +" -p"+ pass +" xenios>xenios.sql");
			Runtime.getRuntime().exec("cmd /c mysqldump -u"+ user +" -p"+ pass +" dialogue_manager_xenios>dialogue_manager_xenios.sql");
			
			//Etsi trexeis ena .bat pou einai sto local folder
			//Runtime.getRuntime().exec("Edit1.bat");
			}
			catch(Exception e){ 
			}
	}
}

	
