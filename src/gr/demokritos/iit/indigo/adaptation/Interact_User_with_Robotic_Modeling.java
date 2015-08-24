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


package gr.demokritos.iit.indigo.adaptation;

public class Interact_User_with_Robotic_Modeling {
	
	private float UserInterest;
	private float RoboticInterest;
	
	public Interact_User_with_Robotic_Modeling(float User_inter, float Robotic_inter){
		
		this.UserInterest=User_inter;
		this.RoboticInterest=Robotic_inter;
		
	}
	
	//method that returns as interest the Mean value of the 2 interests
	public float Mean_of_Interests(){
		
		Float Mean=(this.UserInterest+this.RoboticInterest)/2;
		return Mean;	
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
