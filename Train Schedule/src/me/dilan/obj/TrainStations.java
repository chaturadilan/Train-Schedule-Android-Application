package me.dilan.obj;


public final class TrainStations implements Objects {
	private String[] codes;
	private String[] names;
	private int count;
	
	public String[] getCodes() {
		return codes;
	}
	public void setCodes(String[] codes) {
		this.codes = codes;
	}
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	
	public int getCount() {		
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		
	}
	
}
