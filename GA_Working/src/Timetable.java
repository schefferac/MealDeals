import java.util.HashMap;

public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Meal> meals;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private final HashMap<Integer, Timeslot> timeslots;
	private Class classes[];
	private int numClasses = 0;

	/**
	 * Initialize new Timetable
	 *
	 */
	public Timetable() {
		this.rooms = new HashMap<Integer, Room>();
		this.meals = new HashMap<Integer, Meal>();
		this.modules = new HashMap<Integer, Module>();

		this.groups = new HashMap<Integer, Group>();
		this.timeslots = new HashMap<Integer, Timeslot>();
	}

	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.meals = cloneable.getMeals();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
	}

	private HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

	private HashMap<Integer, Timeslot> getTimeslots() {
		return this.timeslots;
	}

	private HashMap<Integer, Module> getModules() {
		return this.modules;
	}

	private HashMap<Integer, Meal> getMeals() {
		return this.meals;
	}

	/**
	 * Add new room
	 *
	 * @param roomId
	 * @param roomName
	 * @param capacity
	 */
	public void addRoom(int roomId, String roomName, int capacity) {
		this.rooms.put(roomId, new Room(roomId, roomName, capacity));
	}

	/**
	 * Add new meal
	 *
	 * @param mealId
	 * @param mealName
	 */

	public void addMeal(int mealId, String mealName, int cals, double cost, int rating) {
		this.meals.put(mealId, new Meal(mealId, mealName, cals, cost, rating));
	}

	/**
	 * Add new module
	 *
	 * @param moduleId
	 * @param moduleCode
	 * @param module
	 * @param mealIds
	 */
	public void addModule(int moduleId, String moduleCode, String module, int mealIds[]) {
		this.modules.put(moduleId, new Module(moduleId, moduleCode, module, mealIds));
	}

	/**
	 * Add new group
	 *
	 * @param groupId
	 * @param groupSize
	 * @param moduleIds
	 */
	public void addGroup(int groupId, int groupSize, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, groupSize, moduleIds));
		this.numClasses = 0;
	}

	/**
	 * Add new timeslot
	 *
	 * @param timeslotId
	 * @param timeslot
	 */
	public void addTimeslot(int timeslotId, String timeslot) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot));
	}

	/**
	 * Create classes using individual's chromosome
	 *
	 * @param individual
	 */

	public void createClasses(Individual individual) {
		// Init classes
		Class classes[] = new Class[this.getNumClasses()];
		// Get individual's chromosome
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;
		for (Group group : this.getGroupsAsArray()) {
			int moduleIds[] = group.getModuleIds();
			for (int moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);
				// Add timeslot
				classes[classIndex].addTimeslot(chromosome[chromosomePos]);
				chromosomePos++;
				// Add room
				classes[classIndex].setRoomId(chromosome[chromosomePos]);
				chromosomePos++;
				// Add meal
				classes[classIndex].addMeal(chromosome[chromosomePos]);
				chromosomePos++;
				classIndex++;
			}
		}
		this.classes = classes;
	}

	/**
	 * Get room from roomId
	 *
	 * @param roomId
	 * @return room
	 */

	public Room getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("Rooms doesn't contain key " + roomId);
		}
		return (Room) this.rooms.get(roomId);
	}

	public HashMap<Integer, Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Get random room
	 *
	 * @return room
	 */
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	/**
	 * Get meal from mealId
	 *
	 * @param mealId
	 * @return meal
	 */
	public Meal getMeal(int mealId) {
		return (Meal) this.meals.get(mealId);
	}

	/**
	 * Get module from moduleId
	 *
	 * @param moduleId
	 * @return module
	 */
	public Module getModule(int moduleId) {
		return (Module) this.modules.get(moduleId);
	}

	/**
	 * Get moduleIds of student group
	 *
	 * @param groupId
	 * @return moduleId array
	 */
	public int[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}

	/**
	 * Get group from groupId
	 *
	 * @param groupId
	 * @return group
	 */
	public Group getGroup(int groupId) {
		return (Group) this.groups.get(groupId);
	}

	/**
	 * Get all student groups
	 *
	 * @return array of groups
	 */
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	/**
	 * Get timeslot by timeslotId
	 *
	 * @param timeslotId
	 * @return timeslot
	 */
	public Timeslot getTimeslot(int timeslotId) {
		return (Timeslot) this.timeslots.get(timeslotId);
	}

	/**
	 * Get random timeslotId
	 *
	 * @return timeslot
	 */
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	/**
	 * Get classes
	 *
	 * @return classes
	 */
	public Class[] getClasses() {
		return this.classes;
	}

	/**
	 * Get number of classes that need scheduling
	 *
	 * @return numClasses
	 */
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}
		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;
		return this.numClasses;
	}

	/**
	 * Calculate the number of clashes
	 *
	 * @return numClashes
	 */
	public int calcClashes() {
		int clashes = 0;
		for (Class classA : this.classes) {
			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
			int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();
			if (roomCapacity < groupSize) {
				clashes++;
			}
			// Check if room is taken
			for (Class classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId() && classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
			// Check if meal is available
			for (Class classB : this.classes) {
				if (classA.getMealId() == classB.getMealId()
						&& classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
		}
		return clashes;
	}
	
	public double calcFitness() {
		//System.out.println(classes[0].getMealId());
		//System.out.println(classes[1].getMealId());
		//System.out.println(classes[2].getMealId());
		//System.out.println();
		int caloricPart = 0;
		double costPart = 0;
		int ratingPart = 0;
		for (int i = 0; i < classes.length; i++) {
			for(int j = 1; j <= meals.size(); j++ ) {
				if (meals.get(j).getMealId() == classes[i].getMealId()) {
					caloricPart += meals.get(j).getCals();
					costPart += meals.get(j).getCost();
					ratingPart += meals.get(j).getRating();
				}
			}
		}
		
		
		
		//System.out.println("calories = " + caloricPart);
		//System.out.println("cost = " + costPart);
		//System.out.println("rating = " + ratingPart);

		
		double finalFitness = (caloricPart/100) - (costPart/500) + (ratingPart/2000);
		
		//System.out.println("FinalFitness: " + finalFitness);

		//System.out.println("---------------");

		return finalFitness;
	}
}