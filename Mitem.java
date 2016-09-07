import org.json.*;

import java.text.*;
import java.util.*;

/**
 * 
 * Class to define menu items from menus
 *
 */
public class Mitem implements Comparable<Mitem> {
	public int index;
	public String name;
	public double price;
	public String description;
	public String comment;

	public Mitem(int index, String name, double price, String description, String comment) {
		this.index = index;
		this.name = name;
		this.price = price;
		this.comment = comment;
		this.description = description;
	}

	@Override
	public int compareTo(Mitem o) {
		if (this.price < o.price)
			return -1;
		else if (this.price == o.price)
			return 0;
		else
			return 1;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Mitem))
			return false;

		Mitem other = (Mitem) o;
		return this.index == other.index;
	}

	@Override
	public String toString() {
		String mtemdesc = description.length() <= 25 ? description
				: description.substring(0, Math.min(25, description.length()));

		return "<ITEM [" + index + "] " + name + " (" + price + "): \"" + mtemdesc + "...\">";
	}
	
}
