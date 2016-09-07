/**
 * 
 * Class to define mentions from reviews
 *
 */
public class Ment {
	public Rest rest;
	public Rev rev;
	public String context;
	public int ind;
	public String mentxt;
	public Mitem menitem;
	public Mitem findmenitem;
	public Utilities.Sentiment sent;
	public boolean ismenguess;
	public String positive = "pos";
	public String negative = "neg";

	public Ment(Rest rest, Rev rev, int ind, String mentxt, Mitem menitem, String sent) {
		this.rest = rest;
		this.rev = rev;
		this.ind = ind;
		this.mentxt = mentxt;
		this.menitem = menitem;
		this.findmenitem = null;
		this.sent = getSentiment(sent);
		this.ismenguess = false;
		this.context = getrevContext();
	}

	public boolean isFoodMention() {
		return menitem != null;
	}

	public boolean hasSentiment() {
		return menitem != null;
	}

	public Utilities.Sentiment getSentiment(String sentiment) {
		if (sentiment.equals(positive))
			return Utilities.Sentiment.POS;
		else if (sentiment.equals(negative))
			return Utilities.Sentiment.NEG;
		else
			return Utilities.Sentiment.NEU;
	}

	public String getrevContext() {
		String revText = rev.comment;
		String comp = null;
		int mentionEnd = ind + mentxt.length();

		int startIdx = revText.lastIndexOf(".", ind);
		int idx = 0;
		startIdx = Math.max(revText.lastIndexOf("?", ind), startIdx);
		comp = revText;
		startIdx = Math.max(revText.lastIndexOf("!", ind), startIdx);

		int endIdx = revText.indexOf(".", mentionEnd);
		idx = endIdx;
		int tempEndIdx = revText.indexOf("?", mentionEnd);
		if (tempEndIdx != -1 && tempEndIdx < endIdx) {
			endIdx = tempEndIdx;
			idx = tempEndIdx;
		}
		tempEndIdx = revText.indexOf("!", mentionEnd);
		if (tempEndIdx != -1 && tempEndIdx < endIdx) {
			endIdx = tempEndIdx;
			idx = endIdx;
		}
		comp = null;
		startIdx = startIdx != -1 ? startIdx + 1 : 0;
		endIdx = endIdx == -1 ? revText.length() : endIdx + 1;
		return revText.substring(startIdx, endIdx);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Ment))
			return false;

		Ment other = (Ment) o;
		return this.rest.equals(other.rest) && this.rev.equals(other.rev) && this.ind == other.ind;
	}
}
