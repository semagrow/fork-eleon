/**
* <p>Title: LangResources</p>
* <p>Description: The main resource bundle</p>
* <p>Copyright: Copyright (c) 2001-2004 NCSR "Demokritos"</p>
* @author Dimitris Spiliotopoulos
* @author Maria Prospathopoulou
* @author Theofilos Nickolaou
*/

package gr.demokritos.iit.eleon.authoring;

import java.util.*;


public class LangResources 
{
	public static String getString(Locale locale, String key)
	{
	  ResourceBundle res = ResourceBundle.getBundle("gr/demokritos/iit/eleon/authoring/LangResources", locale);
	  return res.getString(key);
	}

	
}





