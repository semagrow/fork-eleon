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
