/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL, see LICENSE.php
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package me.dilan.obj;

public class Rates implements Objects{
	private float[] prices;
	private int count;
	
	public float[] getPrices() {
		return prices;
	}

	public void setPrices(float[] prices) {
		this.prices = prices;
	}
	
	public int getCount() {		
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
		
	}
	
	
}
