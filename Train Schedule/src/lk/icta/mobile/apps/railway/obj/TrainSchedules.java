/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.obj;

public class TrainSchedules implements Objects {
	private String[] trainNames;
	private String[] arrivalTimes;
	private String[] depatureTimes;
	private String[] arrivalAtDestinationTimes;
	private String[] delayTimes;
	private String[] comments;
	private String[] fdDescriptions;
	private String[] tyDescriptions;
	private String[] startStationName;
	private String[] endStationName;
	public String[] getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String[] startStationName) {
		this.startStationName = startStationName;
	}
	public String[] getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String[] endStationName) {
		this.endStationName = endStationName;
	}

	private int count;
	
	
	public String[] getTrainNames() {
		return trainNames;
	}
	public void setTrainNames(String[] trainNames) {
		this.trainNames = trainNames;
	}
	public String[] getArrivalTimes() {
		return arrivalTimes;
	}
	public void setArrivalTimes(String[] arrivalTimes) {
		this.arrivalTimes = arrivalTimes;
	}
	public String[] getDepatureTimes() {
		return depatureTimes;
	}
	public void setDepatureTimes(String[] depatureTimes) {
		this.depatureTimes = depatureTimes;
	}
	public String[] getArrivalAtDestinationTimes() {
		return arrivalAtDestinationTimes;
	}
	public void setArrivalAtDestinationTimes(String[] arrivalAtDestinationTimes) {
		this.arrivalAtDestinationTimes = arrivalAtDestinationTimes;
	}
	public String[] getDelayTimes() {
		return delayTimes;
	}
	public void setDelayTimes(String[] delayTimes) {
		this.delayTimes = delayTimes;
	}
	public String[] getComments() {
		return comments;
	}
	public void setComments(String[] comments) {
		this.comments = comments;
	}
	public String[] getFdDescriptions() {
		return fdDescriptions;
	}
	public void setFdDescriptions(String[] fdDescriptions) {
		this.fdDescriptions = fdDescriptions;
	}
	public String[] getTyDescriptions() {
		return tyDescriptions;
	}
	public void setTyDescriptions(String[] tyDescriptions) {
		this.tyDescriptions = tyDescriptions;
	}
	
	public int getCount() {		
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
		
	}
	
	
	
}
