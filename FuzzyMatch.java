import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class for fuzzy match algorithm
 *
 */
public class FuzzyMatch extends Matchalgos {

	@Override
	public List<Mitem> cmpre(Ment m) {
		List<Mitem> pmatch = new ArrayList<Mitem>();
		for (int i = 0; i < m.rest.mitems.size(); i++) {
			if (Matchalgos.fzmatch(m, m.rest.mitems.get(i))) {
				pmatch.add(m.rest.mitems.get(i));
			}
		}
		m.findmenitem = Matchalgos.chooseitem(m, pmatch);
		return pmatch;
	}
}