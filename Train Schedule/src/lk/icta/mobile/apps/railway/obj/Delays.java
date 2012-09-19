/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.obj;  

public class Delays implements Objects {

	private int count;
	
	private String[] trainNo;
	private String[] delayTime;
	private String[] endtime;
	private String[] comments;
	private String[] fromStationName;
	private String[] toStationName;
	private String[] opatetionType;	
	
	
	
	public String[] getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String[] trainNo) {
		this.trainNo = trainNo;
	}

	public String[] getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String[] delayTime) {
		this.delayTime = delayTime;
	}

	public String[] getEndtime() {
		return endtime;
	}

	public void setEndtime(String[] endtime) {
		this.endtime = endtime;
	}

	public String[] getComments() {
		return comments;
	}

	public void setComments(String[] comments) {
		this.comments = comments;
	}

	public String[] getFromStationName() {
		return fromStationName;
	}

	public void setFromStationName(String[] fromStationName) {
		this.fromStationName = fromStationName;
	}

	public String[] getToStationName() {
		return toStationName;
	}

	public void setToStationName(String[] toStationName) {
		this.toStationName = toStationName;
	}

	public String[] getOpatetionType() {
		return opatetionType;
	}

	public void setOpatetionType(String[] opatetionType) {
		this.opatetionType = opatetionType;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		
	}
	

}
