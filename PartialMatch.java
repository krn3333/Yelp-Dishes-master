import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to implement partial match algorithm
 *
 */
  public class PartialMatch extends Matchalgos {

    @Override
    public List<Mitem> cmpre(Ment m) {
      List<Mitem> pmatch = new ArrayList<Mitem>();
      for (int i = 0; i < m.rest.mitems.size(); i++) {
  		  if (Matchalgos.mwexmatch(m, m.rest.mitems.get(i)) >
            m.mentxt.split(" ").length / 2) {
          pmatch.add(m.rest.mitems.get(i));
        }
      }
      m.findmenitem = Matchalgos.chooseitem(m, pmatch);
      return pmatch;
    }
  }