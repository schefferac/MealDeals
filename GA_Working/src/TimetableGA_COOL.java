import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TimetableGA_COOL {
	public static void main(String[] args) throws IOException {
		// Get a Timetable object with all the available information.
		
		System.out.println("Start");
		Timetable timetable = initializeTimetable();
		// Initialize GA
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 2, 5);
		// Initialize population
		Population population = ga.initPopulation(timetable);
		// Evaluate population

		// Keep track of current generation
		int generation = 1;
		System.out.println("HI");
		// Start evolution loop
		while (ga.isTerminationConditionMet(generation, 10) == false
				&& ga.isTerminationConditionMet(population) == false) {
			// Print fitness
			System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

			// Apply crossover
			population = ga.crossoverPopulation(population);
			
			// Apply mutation
			population = ga.mutatePopulation(population, timetable);
			
			// Evaluate population
			ga.evalPopulation(population, timetable);
			
			// Increment the current generation
			generation++;
		}
		
		
		// Print fitness
		timetable.createClasses(population.getFittest(0));
		System.out.println();
		System.out.println("Solution found in " + generation + " generations");
		System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
		System.out.println("Clashes: " + timetable.calcClashes());
		// Print classes
		System.out.println();
		Class classes[] = timetable.getClasses();
		int classIndex = 1;
		for (Class bestClass : classes) {
			System.out.println("Class " + classIndex + ":");
			System.out.println("Module: " + timetable.getModule(bestClass.getModuleId()).getModuleName());
			System.out.println("Group: " + timetable.getGroup(bestClass.getGroupId()).getGroupId());
			System.out.println("Meal: " + timetable.getMeal(bestClass.getMealId()).getMealName());
			System.out.println("-----");
			classIndex++;
		}
	}

	/**
	 * Creates a Timetable with all the necessary course information.
	 * 
	 * @return
	 * @throws IOException 
	 */
	private static Timetable initializeTimetable() throws IOException {
		// Create timetable
		Timetable timetable = new Timetable();
		// Set up rooms
		timetable.addRoom(1, "A1", 15);
		timetable.addRoom(2, "B1", 30);
		timetable.addRoom(4, "D1", 20);
		timetable.addRoom(5, "F1", 25);
		
		// Set up timeslots
		timetable.addTimeslot(1, "Mon 9:00 - 11:00");
		timetable.addTimeslot(2, "Mon 11:00 - 13:00");
		timetable.addTimeslot(3, "Mon 13:00 - 15:00");
		timetable.addTimeslot(4, "Tue 9:00 - 11:00");
		timetable.addTimeslot(5, "Tue 11:00 - 13:00");
		timetable.addTimeslot(6, "Tue 13:00 - 15:00");
		timetable.addTimeslot(7, "Wed 9:00 - 11:00");
		timetable.addTimeslot(8, "HAPPY BIRTHDAY ABHI!");
		timetable.addTimeslot(9, "Wed 13:00 - 15:00");
		timetable.addTimeslot(10, "Thu 9:00 - 11:00");
		timetable.addTimeslot(11, "Thu 11:00 - 13:00");
		timetable.addTimeslot(12, "Thu 13:00 - 15:00");
		timetable.addTimeslot(13, "Fri 9:00 - 11:00");
		timetable.addTimeslot(14, "Fri 11:00 - 13:00");
		timetable.addTimeslot(15, "Fri 13:00 - 15:00");
		
		
		ArrayList<Integer> breakfastIDs = new ArrayList<Integer>();
		ArrayList<Integer> lunchIDs = new ArrayList<Integer>();
		ArrayList<Integer> dinnerIDs = new ArrayList<Integer>();

		//Read the Data File and make Meals
		BufferedReader csvReader = new BufferedReader(new FileReader("recipeDataWithSlashes.csv"));
		String row = "";
		
		//Add all of the meals to the genetic algorithm and save the IDs in the corresponding meal ArrayLists
		while ((row = csvReader.readLine()) != null) { 
		    String[] data = row.split(",");
		    if (data[1].equals("ID")) {
		    	//Do Nothing --> we are skipping the first line of the input data
		    }else {
		    	//Add the meals

		    	//Is it a breakfast??
		    	if (data[10].contains("breakfast") || data[10].contains("brunch")) {
		    		timetable.addMeal(Integer.parseInt(data[1]), data[0], Integer.parseInt(data[4]), Double.parseDouble(data[2]), Integer.parseInt(data[5]));
		    		breakfastIDs.add(Integer.parseInt(data[1])); //Add the ID of the newly added meal to BreakfastIDs
		    		System.out.println(timetable.getMeal(Integer.parseInt(data[1])).getMealName());
		    	}
		    	//Is it a lunch??
		    	else if (data[10].contains("lunch") || data[10].contains("brunch")) {
		    		timetable.addMeal(Integer.parseInt(data[1]), data[0], Integer.parseInt(data[4]), Double.parseDouble(data[2]), Integer.parseInt(data[5]));
		    		lunchIDs.add(Integer.parseInt(data[1])); //Add the ID of the newly added meal to LunchIDs
		    		System.out.println(timetable.getMeal(Integer.parseInt(data[1])).getMealName());
		    	}
		    	//is it a dinner??
		    	if (data[10].contains("dinner")) {
		    		timetable.addMeal(Integer.parseInt(data[1]), data[0], Integer.parseInt(data[4]), Double.parseDouble(data[2]), Integer.parseInt(data[5]));
		    		dinnerIDs.add(Integer.parseInt(data[1])); //Add the ID of the newly added meal to DinnerIDs
		    		System.out.println(timetable.getMeal(Integer.parseInt(data[1])).getMealName());
		    	}
		    	
		    }
		}
		csvReader.close();
		
		//int x = 1/0;
		
		// Set up meals
		/*timetable.addMeal(1, "b1", 10, 1, 1);
		timetable.addMeal(2, "b2", 10, 100, 10);
		timetable.addMeal(3, "b3", 10, 1000, 10);
		timetable.addMeal(4, "l1", 10, 1, 1);
		timetable.addMeal(5, "l2", 10, 100, 10);
		timetable.addMeal(6, "l3", 10, 1000, 10);
		timetable.addMeal(7, "d1", 10, 1, 1);
		timetable.addMeal(8, "d2", 10, 100, 10);
		timetable.addMeal(9, "d3", 10, 1000, 10);
		*/
		
		// Set up modules and define the meals that teach them
		/*timetable.addModule(1, "br", "Breakfasts", new int[] { 1, 2, 3});
		timetable.addModule(2, "lu", "Lunches", new int[] { 4, 5, 6});
		timetable.addModule(3, "dn", "Dinners", new int[] { 7, 8, 9 });
		*/
		
		System.out.println(dinnerIDs.stream().mapToInt(i -> i).toArray()[3]);
		//System.exit(0);
		
		
		
		//Make the different meal times and set their corresponding meals
		timetable.addModule(1, "br", "Breakfasts", breakfastIDs.stream().mapToInt(i -> i).toArray());
		timetable.addModule(2, "lu", "Lunches", lunchIDs.stream().mapToInt(i -> i).toArray());
		timetable.addModule(3, "dn", "Dinners", dinnerIDs.stream().mapToInt(i -> i).toArray());

		// Set up student groups and the modules they take.
		timetable.addGroup(1, 4, new int[] { 1, 2, 3});
		
		return timetable;
	}
}