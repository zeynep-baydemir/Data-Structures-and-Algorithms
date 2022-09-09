
public class Student implements Comparable<Student>{

	private int ID; // ID of the student
	private String name; // Name of the student
	private int duration; // Duration of the student
	private double rating; // Rating of the student
	

	public Student(int ID, String name, int duration, double rating) {
		this.ID = ID;
		this.name = name;
		this.duration = duration;
		this.rating = rating;
	}

	public int compareTo(Student stu) {
		if (this.ID>=stu.ID) {
			return 1;
		}
		return -1;
	}

	public void timePass() { // Decreases duration by 1
		if(this.duration>0) {
			this.duration--;			
		}
		else {
			this.duration=0;
		}
	}
	

	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public int getID() {
		return ID;
	}


	public String getName() {
		return name;
	}

	public double getRating() {
		return rating;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
