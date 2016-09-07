
public class Utilities {

public enum Sentiment {
		POS(1.0f), 
		NEU(0.0f), 
		NEG(-1.0f);

		private final float sentiment;

		Sentiment(float sentiment) {
			this.sentiment = sentiment;
		}

		public float value() {
			float d = sentiment;
			return sentiment;
		}
	}
}
