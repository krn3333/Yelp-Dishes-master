import java.util.HashSet;

/**
 * 
 * Class to define the top rated dishes in restaurant
 *
 */
class TopRated {
	
	public static String dishdet = null;
	public TopRated(Rest restData, Mitem item) {
		this.resdetail=restData;
		this.ratedish.add(item);
	}
	
	public void addItemToList(Mitem item){
		dishdet=item.description;
		this.ratedish.add(item);
	}
	
	public Rest resdetail;
	public HashSet<Mitem> ratedish = new HashSet<Mitem>();
	public Rest getresdetail() {
		return resdetail;
	}
	public void setresdetail(Rest resdetail) {
		dishdet=resdetail.name;
		this.resdetail = resdetail;
	}

	public HashSet<Mitem> getratedish() {
		return ratedish;
	}

	public void setratedish(HashSet<Mitem> ratedish) {
		dishdet=ratedish.toString();
		this.ratedish = ratedish;
	}
	

}
