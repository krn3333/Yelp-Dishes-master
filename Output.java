import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Class to define the output list
 *
 */
public class Output {

	private Double precision;
	private Double recall;
	Map<String,TopRated> resultMap = new HashMap<String,TopRated>();
	public Double getPrecision() {
		return precision;
	}
	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	public Double getRecall() {
		return recall;
	}
	public void setRecall(Double recall) {
		this.recall = recall;
	}
	public Map<String, TopRated> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, TopRated> resultMap) {
		this.resultMap = resultMap;
	}
	
	
}
