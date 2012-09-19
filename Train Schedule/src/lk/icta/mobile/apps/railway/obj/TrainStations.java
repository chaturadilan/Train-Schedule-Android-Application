/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.obj;


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
