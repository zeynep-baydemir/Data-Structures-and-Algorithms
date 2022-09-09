import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;


public class project1main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		PriorityQueue<House>houses = new PriorityQueue<House>();
		ArrayList<Student> students = new ArrayList<Student>();
		
		while(in.hasNext()){   // input reading
			String input = in.next();
			if (input.equals("h")) {
				int ID = in.nextInt();
				int duration = in.nextInt();
				double rating = Double.parseDouble(in.next());	
				houses.add(new House(ID,duration,rating));
			}
			else {
				int ID = in.nextInt();
				String name = in.next();
				int duration = in.nextInt();
				double rating = Double.parseDouble(in.next());
				students.add(new Student(ID,name,duration,rating));
			}	 
		}
		
		Collections.sort(students);
		
		Stack<House> houseSt = new Stack<House>(); // stack of houses to keep houses
		
			for (int i=1; i<=8; i++) {
				
				while ((!houses.isEmpty()) && houses.peek().getDuration()==0) {
					
					House house = houses.poll();
					
					for (Student student : students) { // finding a student to the house
						if (student.getDuration()==0) {
							continue;
						}
						if (student.getRating() <= house.getRating()) { // if student can stay at the house
							house.addDuration(student.getDuration());
							houses.add(house);
							students.remove(student);
							break;
						}
						else {		// if student cannot stay at the house
							if(students.size()==1) {
								house.timePass();
								houseSt.push(house);
							}
							continue;
						}		
					}
				}
				for (Student student:students) { // decreasing durations of students 
					student.timePass();
				}
				Collections.sort(students);	
				
				for (House house:houses) { // decreasing durations of houses
					house.timePass();
				}
				while (!houseSt.isEmpty()) {
					houses.add(houseSt.pop());
				}
			}
			for (Student student:students) {
				out.println(student.getName());
			}
			

		}
	}
	
