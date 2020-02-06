public class Class {
	private final int classId;
	private final int groupId;
	private final int moduleId;
	private int mealId;
	private int timeslotId;
	private int roomId;

	public Class(int classId, int groupId, int moduleId) {
		this.classId = classId;
		this.moduleId = moduleId;
		this.groupId = groupId;
	}

	public void addMeal(int mealId) {
		this.mealId = mealId;
	}

	public void addTimeslot(int timeslotId) {
		this.timeslotId = timeslotId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getClassId() {
		return this.classId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public int getModuleId() {
		return this.moduleId;
	}

	public int getMealId() {
		return this.mealId;
	}

	public int getTimeslotId() {
		return this.timeslotId;
	}

	public int getRoomId() {
		return this.roomId;
	}
}