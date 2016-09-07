import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement substring match algorithm
 *
 */
public class SubstringMatch extends Matchalgos {

    @Override
    public List<Mitem> cmpre(Ment m) {
      List<Mitem> pmatch = new ArrayList<Mitem>();
      for (int i = 0; i < m.rest.mitems.size(); i++) {
        if (ssmatch(m, m.rest.mitems.get(i))) {
          pmatch.add(m.rest.mitems.get(i));
        }
      }
      m.findmenitem = Matchalgos.chooseitem(m, pmatch);
      return pmatch;
    }
  }
