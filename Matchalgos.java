import java.util.*;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * Class to call different matching algorithms
 *
 */
public class Matchalgos {
	

	

	public List<Mitem> cmpre(Ment m) {
		return new ArrayList<Mitem>();
	}

	public void run(List<Ment> mentions) {
		
		for (int i = 0; i < mentions.size(); i++) {
			cmpre(mentions.get(i));
		}
	}

	public static Mitem chooseitem(Ment m, List<Mitem> items) {
		if (items.size() > 250)
			return null;

		List<Mitem> possibleMatches = new ArrayList<Mitem>();
		int maxNumWordMatches = 0;
		for (int i = 0; i < items.size(); i++) {
			
			int numWordMatches = Matchalgos.mwexmatch(m, items.get(i));
			if (numWordMatches > maxNumWordMatches) {
				possibleMatches.clear();
				possibleMatches.add(items.get(i));
				maxNumWordMatches = numWordMatches;
			}
		}

		Collections.sort(possibleMatches);
		int medianIdx = possibleMatches.size();
		if (medianIdx == 0)
			return null;
		return possibleMatches.get(medianIdx / 2);
	}

	protected static int mwexmatch(Ment m, Mitem item) {
		String[] words = m.mentxt.split(" ");
		int numMatches = 0;
		for (int i = 0; i < words.length; i++) {
			if ((item.name.replaceAll("[^\\w\\s]", "").toLowerCase().trim()).contains((words[i].replaceAll("[^\\w\\s]", "").toLowerCase().trim())))
				numMatches += 2;
			else if ((item.description.replaceAll("[^\\w\\s]", "").toLowerCase().trim()).contains((words[i].replaceAll("[^\\w\\s]", "").toLowerCase().trim())))
				numMatches++;
		}
		return numMatches;
	}


	public static boolean exmatch(Ment mention, Mitem menuItem) {
		return (menuItem.name.replaceAll("[^\\w\\s]", "").toLowerCase().trim()).equals((mention.mentxt.replaceAll("[^\\w\\s]", "").toLowerCase().trim()));
	}

	public static boolean ssmatch(Ment mention, Mitem menuItem) {
		return (menuItem.name.replaceAll("[^\\w\\s]", "").toLowerCase().trim()).contains((mention.mentxt.replaceAll("[^\\w\\s]", "").toLowerCase().trim()));
	};

	public static boolean fzmatch(Ment m, Mitem item) {
		String[] mentionWords = (m.mentxt.replaceAll("[^\\w\\s]", "").toLowerCase().trim()).split(" ");
		int numMatches = nwmatch(m, item);
		return numMatches >= mentionWords.length / 2;
	}

	public static int nwmatch(Ment m, Mitem item) {
		String[] mentionWords = (m.mentxt.replaceAll("[^\\w\\s]", "").toLowerCase().trim()).split(" ");
		String[] itemWords = ((item.name + " " + item.description).replaceAll("[^\\w\\s]", "").toLowerCase().trim()).split(" ");

		int numMatches = 0;
		for (int i = 0; i < mentionWords.length; i++) {
			for (int j = 0; j < itemWords.length; j++) {
				if ((itemWords[j].replaceAll(" ", "")).length() < 1)
					continue;

				try {
					if (StringUtils.getLevenshteinDistance(mentionWords[i], itemWords[j]) <= 3) {
						numMatches++;
						break;
					}
				} catch (Exception e) {
				}
			}
		}
		return numMatches;
	}
}
