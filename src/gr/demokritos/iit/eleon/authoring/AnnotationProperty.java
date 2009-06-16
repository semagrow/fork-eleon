package gr.demokritos.iit.eleon.authoring;

import java.util.*;
import java.io.*;

public class AnnotationProperty extends Vector 
{
  public AnnotationProperty(String type, String text, String language, boolean used, String userType) 
  {
	  addElement(type);
          addElement(text);
          addElement(language);
          addElement(used);
          addElement(userType);
          
  }
}