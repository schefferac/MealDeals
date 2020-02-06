public class Meal {
	private final int mealId;
	private final String mealName;
	private int cals;
	private double cost;
	private int rating;
	
	public Meal(int mealId, String mealName, int cals, double cost, int rating) {
		this.mealId = mealId;
		this.mealName = mealName;
		this.cals = cals;
		this.cost = cost;
		this.rating = rating;
	}

	public int getMealId() {
		return this.mealId;
	}

	public String getMealName() {
		return this.mealName;
	}
	public int getCals() {
		return this.cals;
	}
	public double getCost() {
		return this.cost;
	}
	public int getRating() {
		return this.rating;
	}
}