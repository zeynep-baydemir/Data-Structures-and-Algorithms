
public class House implements Comparable<House>{

	private int ID; // ID of the house
	private int duration; // duration of the house
	private double rating; //rating of the house
	

	public House(int ID, int duration, double rating) {
		this.ID = ID;
		this.duration = duration;
		this.rating = rating;
	}
	
	public int compareTo(House house) {
		if (this.duration>house.duration) {
			return 1;
		}
		else if (this.duration<house.duration) {
			return -1;
		}
		else {
			return this.ID-house.ID;
			
		}
	}
	
	public void addDuration(int duration) { // Adds student duration to the duration of the house
		this.duration += duration; 
	}
	
	
	public void timePass() { // Decreases duration by 1
		if (!(this.duration==0)) {
			this.duration--;
		}
		else {
			this.duration=0;
		}
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getRating() {
		return rating;
	}
		
}
