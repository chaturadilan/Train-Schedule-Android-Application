/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL, see LICENSE.php
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package me.dilan.obj;

public class TrainLines implements Objects {
	
	private  int[] ids;
	private  String[] names;
	private int count;
	
	
	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
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
