package application;

import java.io.Serializable;
/**
 * The class represents a pizza, containing the size, 
 * the amount of cheese, ham and pepperoni. It can also produce the cost of that pizza
 * using hard-coded prices.
 * @author Julie Schneiderman
 * @version 1.0
 */
public class Pizza implements Serializable {

	private static final long serialVersionUID = -1875464720946301875L;
	private String size;
	private int cheese;
	private int ham;
	private int pepperoni;
	
	private float SMALL_COST = 7.00F;	// Includes one cheese
	private float MEDIUM_COST = 9.00F;
	private float LARGE_COST = 11.00F;
	private float COST_PER_TOPPING = 1.50F;
	
	/**
	 * The Pizza constructor.
	 * @param size Must be "small", "medium" or "large".
	 * @param cheese Must be 1, 2, or 3.
	 * @param ham Must be 0, 1, 2 or 3.
	 * @param pepperoni Must be 0, 1, 2 or 3.
	 * @throws IllegalPizza If any of the parameters are not legal or the sum of ham and
	 * pepperoni is greater than 3.
	 */
	public Pizza(String size, int cheese, int ham, int pepperoni) throws IllegalPizza {
		size = size.toLowerCase();
		if (size.equals("small") || size.equals("medium") || size.equals("large"))
			this.size = size;
		else
			throw new IllegalPizza("Illegal size!");
		if (cheese >= 1 && cheese <= 3)
			this.cheese = cheese;
		else
			throw new IllegalPizza("Illegal cheese!");
		if (ham >= 0 && ham <= 3)
			this.ham = ham;
		else
			throw new IllegalPizza("Illegal ham!");
		if (pepperoni >= 0 && pepperoni <= 3)
			this.pepperoni = pepperoni;
		else
			throw new IllegalPizza("Illegal pepperoni!");
		if (ham + pepperoni > 3)
			throw new IllegalPizza("Too much meat!");
	} // end full parameter constructor
	
	/**
	 * Another Pizza constructor.
	 * @param size Must be "small", "medium", or "large". Single cheese and pepperoni is assumed.
	 * @throws IllegalPizza If the size parameter is not legal.
	 */
	public Pizza(String size) throws IllegalPizza {
		this(size, 1, 0, 1);
	} // end second constructor
	
	/**
	 * Returns the cost of the current Pizza object.
	 * @return The cost in dollars.  No tax.
	 */
	public float getCost() {
		float cost = 0;
		if (size.equals("small"))
			cost = SMALL_COST;
		else if (size.equals("medium"))
			cost = MEDIUM_COST;
		else
			cost = LARGE_COST;
		cost += ((cheese - 1) + ham + pepperoni) * COST_PER_TOPPING;
		return cost;
	} // end getCost
	
	// Used to convert a numeric topping amount to a string equivalent.
	private String convertInt(int topping) {
		if (topping == 1)
			return "";
		else if (topping == 2)
			return "double ";
		else
			return "triple ";
	} // end convertInt
	
	/**
	 * Returns a string representation of the current object.
	 * @return A string description of the current Pizza object.
	 */
	public String toString() {
		String out = size + " pizza, ";
		out += convertInt(cheese);
		if (ham + pepperoni == 0) {
			out += "cheese only.";
			out += String.format(" Cost each: $%.2f", getCost());
			return out;
		} else
			out += "cheese, ";
		if (ham > 0)
			if (pepperoni > 0)
				out += convertInt(ham) + "ham, ";
			else
				out += convertInt(ham) + "ham. ";
		if (pepperoni > 0)
			out += convertInt(pepperoni) + "pepperoni.";
		out += String.format(" Cost each: $%.2f", getCost());
		return out;
	} // end toString
	
	/**
	 * Tests to see if the current object is equal to the supplied Pizza object. Equality is
	 * defined as all attributes being exactly equal.
	 * @param other The supplied object for comparison.
	 * @return false if the objects are not equal or the supplied object is not a Pizza, true
	 * otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof Pizza) {
			Pizza otherP = (Pizza)other;
			if (size.equals(otherP.size))
				return cheese == otherP.cheese && ham == otherP.ham && pepperoni == otherP.pepperoni;
		}
		return false;
	} // end equals
	
	/**
	 * Creates and returns a clone of the current Pizza object.
	 * @return A deep copy or clone of the current object. 
	 */
	public Pizza clone() {
		Pizza outP = null;
		try {
			outP = new Pizza(size, cheese, ham, pepperoni);
		} catch (IllegalPizza e) {
		}
		return outP;
	} // end clone
	
} // end Pizza class
