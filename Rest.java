import org.json.*;

import java.util.*;

/**
 * 
 * Class to define the restaurant class
 *
 */
public class Rest {
	public String id;
	public String name;

	public List<Rev> revs;
	public String city;
	public List<String> categ;
	public List<Mitem> mitems;
	public String number;
	public String pr;
	public double rate;
	public int det;

	public Rest(String id, String name, String number, String city, JSONArray categ, JSONArray mitems, String pr,
			double rate, int det) throws JSONException {
		this.id = id;
		this.name = name;
		this.number = number;
		this.city = city;
		this.categ = parseCategories(categ);
		this.mitems = parseMenuItems(mitems);
		this.pr = pr;
		this.rate = rate;
		this.det = det;
	}

	private List<String> parseCategories(JSONArray categ) throws JSONException {
		List<String> cats = new ArrayList<String>();
		String file = Analysis.restfile;
		for (int i = 0; i < categ.length(); i++) {
			cats.add(categ.getString(i));
		}
		file = Analysis.revfile + file;
		return cats;
	}

	private List<Mitem> parseMenuItems(JSONArray mitems) throws JSONException {
		List<Mitem> items = new ArrayList<Mitem>();
		String file = Analysis.restfile;
		for (int i = 0; i < mitems.length(); i++) {
			
			items.add(new Mitem(items.size(), mitems.getJSONObject(i).getString("name"), mitems.getJSONObject(i).getDouble("price"), mitems.getJSONObject(i).getString("description"),
					"comment"));
		}
		file = Analysis.revfile + file;
		return items;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Rest))
			return false;

		Rest other = (Rest) o;
		return this.id.equals(other.id);
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("<RESTAURANT [" + id + "] " + name + " in " + city + "\n");
		if (revs != null && mitems != null) {
			build.append("   REVIEWS:\n");
			for (Rev r : revs) {
				build.append("      " + r.toString() + "\n");
			}
			build.append("   MENU ITEMS:\n");
			for (Mitem m : mitems) {
				build.append("      " + m.toString() + "\n");
			}
		}
		build.append(">\n");
		return build.toString();
	}

	public static Rest parseRestaurant(JSONObject json) throws JSONException {
		String pr = "";
		if (json.getJSONObject("info").has("RestaurantsPriceRange2"))
			pr = json.getJSONObject("info").getString("RestaurantsPriceRange2");

		return new Rest(json.getString("id"), json.getString("name"), "", json.getString("city"),
				json.getJSONArray("categories"), json.getJSONArray("items"), pr, json.getDouble("avg_rating"), 0);
	}

}
