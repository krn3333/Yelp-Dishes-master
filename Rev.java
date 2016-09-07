import org.json.*;

import java.text.*;
import java.util.*;

/**
 * 
 * Class to define review class
 *
 */
public class Rev {
	public int index;
	public Date date;
	public double rating;
	public String comment;
	public String desc;

	public Rev(int index, String date, double rating, String comment, String desc) {
		this.index = index;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			this.date = df.parse(date);
		} catch (ParseException e) {
			this.date = null;
		}
		this.rating = rating;
		this.comment = comment;
		this.desc = desc;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Rev))
			return false;

		Rev other = (Rev) o;
		return this.index == other.index;
	}

	@Override
	public String toString() {
		return "<REVIEW [" + index + "] " + "date: " + date + ", rating: " + rating + ", comment: \""
				+ comment.substring(0, Math.min(25, comment.length())) + "...\">";
	}

	public static List<Rev> parse(JSONArray array, int size) throws JSONException {
		List<Rev> reviews = new ArrayList<Rev>();
		int len = size;
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonRev = array.getJSONObject(i);
			Rev review = new Rev(i, jsonRev.getString("date"), jsonRev.getDouble("rating"),
					jsonRev.getString("comment"), String.valueOf(len));
			reviews.add(review);
		}
		return reviews;
	}
}
