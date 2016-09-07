import org.json.*;

import java.io.*;
import java.util.*;

/**
 * 
 * This class is used to perform the basic reading/parsing of json file into
 * object contracts and running string matching algorithms on it.
 *
 */
public class Analysis {

	public static String restfile = "src/data/restaurants.json";
	public static String revfile = "src/data/reviews.json";
	public static String menfile = "src/data/mentions";
	public static String revss = "reviews";
	public static Map<String, Rest> rests;

	public static List<Ment> tment;

	public static boolean not = false;
	public static List<Ment> allment;
	public static String exalgo = "ExactMatch";
	public static String partalgo = "PartialMatch";
	public static String subalgo = "SubstringMatch";
	public static String fuzalgo = "FuzzyMatch";
	public static Map<String, Class> relrest;

	public Output runcompare(String compare) throws JSONException {
		try {
			return this.findsame(compare);
		} catch (IOException e) {
			System.out.println("No result");
		}
		return null;
	}

	/**
	 * It reads and matches the requested algorithm 
	 * @param simalgo
	 * @return
	 * @throws IOException
	 * @throws JSONException 
	 */
	public Output findsame(String simalgo) throws IOException, JSONException {
		int index = 0;
		Matchalgos ma = findm(simalgo);
		tment = new ArrayList<Ment>();
		allment = new ArrayList<Ment>();

		if (restfile != null)
			openrest(restfile);
		index++;
		if (revfile != null)
			openrev(revfile);

		openmen(not, allment, menfile);

		ma.run(allment);
		index++;
		return calc(ma);

	}

	/**
	 * Returns the requested algorithm to perform on the reviews
	 * @param algoname
	 * @return
	 */
	private static Matchalgos findm(String algoname) {

		if (exalgo != null && algoname.equals(exalgo)) {
			return new ExactMatch();
		}

		if (partalgo != null && algoname.equals(partalgo)) {
			return new PartialMatch();
		}

		if (subalgo != null && algoname.equals(subalgo)) {
			return new SubstringMatch();
		}

		if (fuzalgo != null && algoname.equals(fuzalgo)) {
			return new FuzzyMatch();
		} else {
			return new ExactMatch();
		}

	}

	/**
	 * Calculates the precision and recall for each of the string matching algorithm
	 * @param ma
	 * @return
	 */
	private static Output calc(Matchalgos ma) {

		Output answer = new Output();

		double retrievedmen = 0.0;
		double rightretrieved = 0.0;
		Map<String, TopRated> ratemap = new HashMap<String, TopRated>();
		ma = null;
		double totalrelv = 0.0;
		double tpos = 0.0;

		int i = 0;
		while (i < allment.size()) {

			if (allment.get(i).findmenitem != null)
				retrievedmen++;
			if (allment.get(i).findmenitem != null && allment.get(i).menitem != null
					&& allment.get(i).menitem.equals(allment.get(i).findmenitem)) {
				if (ratemap.containsKey(allment.get(i).rest.id)) {
					ma = null;
					TopRated tr = ratemap.get(allment.get(i).rest.id);
					tr.addItemToList(allment.get(i).menitem);
					ratemap.put(allment.get(i).rest.id, tr);
				} else {
					ma = null;
					TopRated tr = new TopRated(allment.get(i).rest, allment.get(i).menitem);
					ratemap.put(allment.get(i).rest.id, tr);
				}
				rightretrieved++;
			}

			if (allment.get(i).menitem != null)
				totalrelv++;

			if (allment.get(i).menitem != null && allment.get(i).menitem.equals(allment.get(i).findmenitem))
				tpos++;

			i++;
		}

		double recall = tpos / totalrelv;

		double precision = rightretrieved / retrievedmen;

		answer.setPrecision(precision);
		answer.setRecall(recall);
		answer.setResultMap(ratemap);

		ma = null;
		return answer;
	}

	/**
	 * This class is used to parse restaurant json
	 * @param rfile
	 * @throws FileNotFoundException 
	 * @throws JSONException 
	 */
	private static void openrest(String rfile) throws FileNotFoundException, JSONException {
		rests = new HashMap<String, Rest>();
		File file = new File(restfile);
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				JSONObject parsedObj = new JSONObject(sc.nextLine());
				Rest restaurant = Rest.parseRestaurant(parsedObj);
				rests.put(restaurant.id, restaurant);
			}
			sc.close();
	}

	/**
	 * This class is used to parse reviews json
	 * @param rfile
	 */
	private static void openrev(String rfile) {
		File file = new File(revfile);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			String line = "";
			String iden = "id";

			while (sc.hasNext()) {
				line = sc.nextLine();
				JSONObject json = new JSONObject(line);
				List<Rev> revlist = Rev.parse(json.getJSONArray(revss),0);
				String id = json.getString(iden);

				Rest rest1 = rests.get(id);
				rest1.revs = revlist;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This class is used to parse mentions file
	 * @param rfile
	 * @throws FileNotFoundException 
	 */
	private static void openmen(boolean isTraining, List<Ment> list, String mfile) throws FileNotFoundException {
		File folder = new File(menfile);
		File[] files = folder.listFiles();
		int numFiles;
		if(isTraining){
			numFiles=(int) (files.length * 0.75);
		}else{
			numFiles=files.length;
		}
		
		
		int i = 0;
		while (i < numFiles) {
			File file = files[i];
			if (!(file.isFile() && file.toString().contains("results-") && file.toString().endsWith(".txt"))) {
				continue;
			}

			Scanner sc = null;
				sc = new Scanner(file);
				String line = "";
				while (sc.hasNext()) {
					line = sc.nextLine();
					String[] split = line.split("\t");

					Rest rt = rests.get(split[0]);
					Rev revs = rt.revs.get(Integer.parseInt(split[1]));
					int menind = Integer.parseInt(split[2]);
					String mentxt = split[3];
					int menindx = Integer.parseInt(split[4]);
					Mitem meit;
					if(menindx>0){
						meit= rt.mitems.get(menindx);
					}else{
						meit=null;
					}
					
					String sen = split[5];
					list.add(new Ment(rt, revs, menind, mentxt, meit, sen));
					//resets the reviews and sentence strings
					revs = null;
					menind = 0;
					sen = null;
				}
			i++;
		}
	}
}
