import java.util.ArrayList;
import java.util.List;

/**
 * Class for Exact Match algorithm
 *
 */
  public class ExactMatch extends Matchalgos {

    @Override
    public List<Mitem> cmpre(Ment m) {
      List<Mitem> pmatch = new ArrayList<Mitem>();
      for (int i = 0; i < m.rest.mitems.size(); i++) {
        if (exmatch(m, m.rest.mitems.get(i))) {
          m.findmenitem = m.rest.mitems.get(i);
          return pmatch;
        }
      }
      return pmatch;
    }
  }