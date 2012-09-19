/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.webservice;

import lk.icta.mobile.apps.railway.obj.Delays;
import lk.icta.mobile.apps.railway.obj.Rates;
import lk.icta.mobile.apps.railway.obj.TrainLines;
import lk.icta.mobile.apps.railway.obj.TrainSchedules;
import lk.icta.mobile.apps.railway.obj.TrainStations;
import lk.icta.mobile.apps.railway.util.Functions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.util.Log;

public class RailwayWebServiceV2 {
	
	private final static String NAMESPACE = "http://ws.wso2.org/dataservice";
	private final static String ENDPOINT = "http://103.11.35.13:9080/services/RailwayWebServiceV2Proxy.RailwayWebServiceV2ProxyHttpSoap12Endpoint";
		
	private RailwayWebServiceV2() {
		
	}	

	private static SoapObject callWebService(String actionName, SoapObject request)
			throws Exception {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				ENDPOINT);
		androidHttpTransport.call(actionName, envelope);
		Log.v("result", envelope.bodyOut.toString());
		SoapObject results = (SoapObject) envelope.bodyIn;
		return results;
	}
	
	public static TrainLines getLines() throws Exception {
		String methodName = "getLines";
		String actionName = methodName;
		SoapObject request = new SoapObject(NAMESPACE,methodName);
		request.addProperty("lang", "en");
		SoapObject results = callWebService(actionName, request);
		
		int length = results.getPropertyCount() + 1;			
		TrainLines trainLines = new TrainLines();
		trainLines.setCount(length);
		trainLines.setIds(new int[length]);
		trainLines.setNames(new String[length]);
		
		trainLines.getIds()[0] = 0;
		trainLines.getNames()[0] = "All Stations";
		
		for(int i= 1; i < length; i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i-1);			
			trainLines.getIds()[i] = (Integer.parseInt(result.getProperty("id").toString()));
			trainLines.getNames()[i] = Functions.capitalizeFirstLetters(String.valueOf(result.getProperty("LineName").toString()));						
		}		
		
		return trainLines;		
	}
	
	
	public static TrainStations getTrainStations(int lineId) throws Exception{		
		String methodName;
		if(lineId == 0){
			methodName = "getAllStations";
		}else{
			methodName = "getStations";
		}
		String actionName = methodName;
		SoapObject request = new SoapObject(NAMESPACE,methodName);
		request.addProperty("line", String.valueOf(lineId));
		request.addProperty("lang", "en");
		
		SoapObject results = callWebService(actionName, request);
		
		int length = results.getPropertyCount();				
		TrainStations trainStations = new TrainStations();
		trainStations.setCount(length);
		trainStations.setCodes(new String[length]);
		trainStations.setNames(new String[length]);
		
		
		for(int i=0;  i < length; i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i);
			trainStations.getCodes()[i] = String.valueOf(result.getProperty("StationCode").toString());
			trainStations.getNames()[i] = Functions.capitalizeFirstLetters(String.valueOf(result.getProperty("StationNameEng").toString()));				
		}		
		
		return trainStations;		
	}
	
	public static TrainSchedules getSchedule(String fromStationCode, String toStationCode, String arrivalTime, String depatureTime, String currentDate, String currentTime) throws Exception{
		
		if(currentTime == null){
			//currentTime = String.format("%1$tH:%1$tM:%1$tS", Calendar.getInstance());
		}		
		
		String methodName = "getSchedule";
		String actionName = methodName;
		SoapObject request = new SoapObject(NAMESPACE,methodName);
		request.addProperty("StartStationCode", fromStationCode);
		request.addProperty("EndStationCode", toStationCode);
		request.addProperty("ArrivalTime", arrivalTime);
		request.addProperty("DepatureTime", depatureTime);
		request.addProperty("CurrentDate", currentDate);
		request.addProperty("CurrentTime", currentTime);
		request.addProperty("lang", "en");
		
		SoapObject results = callWebService(actionName, request);
		
		int length = results.getPropertyCount();				
		TrainSchedules trainSchedules = new TrainSchedules();
		trainSchedules.setCount(length);
		trainSchedules.setTrainNames(new String[length]);
		trainSchedules.setArrivalTimes(new String[length]);
		trainSchedules.setDepatureTimes(new String[length]);
		trainSchedules.setArrivalAtDestinationTimes(new String[length]);
		trainSchedules.setDelayTimes(new String[length]);
		trainSchedules.setComments(new String[length]);
		trainSchedules.setFdDescriptions(new String[length]);
		trainSchedules.setTyDescriptions(new String[length]);
		trainSchedules.setStartStationName(new String[length]);
		trainSchedules.setEndStationName(new String[length]);
			
		for(int i=0; i < length; i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i);			
			trainSchedules.getTrainNames()[i] = (String.valueOf(result.getProperty("name").toString()));
			trainSchedules.getArrivalTimes()[i] = (String.valueOf(result.getProperty("arrival").toString()));
			trainSchedules.getDepatureTimes()[i] = (String.valueOf(result.getProperty("departure").toString()));
			trainSchedules.getArrivalAtDestinationTimes()[i] = (String.valueOf(result.getProperty("destination").toString()));
			trainSchedules.getDelayTimes()[i] = removeAnyType(String.valueOf(result.getProperty("delay").toString()));
			trainSchedules.getComments()[i] =  removeAnyType(String.valueOf(result.getProperty("comment").toString()));
			trainSchedules.getFdDescriptions()[i] = (String.valueOf(result.getProperty("fdescriptioneng").toString()));
			trainSchedules.getTyDescriptions()[i] = (String.valueOf(result.getProperty("tydescriptioneng").toString()));
			trainSchedules.getStartStationName()[i] = (String.valueOf(result.getProperty("frtrstationnameeng").toString()));
			trainSchedules.getEndStationName()[i] = (String.valueOf(result.getProperty("totrstationnameeng").toString()));
		} 
		return trainSchedules;
	} 
	
	
	public static Rates getRates(String fromStationCode, String toStationCode) throws Exception{
		
		String methodName = "getRates";
		String actionName = methodName;
		SoapObject request = new SoapObject(NAMESPACE,methodName);
		request.addProperty("StartStationCode", fromStationCode);
		request.addProperty("EndStationCode", toStationCode);		
		
		SoapObject results = callWebService(actionName, request);
		
		int length = results.getPropertyCount();
		Rates rates = new Rates();
		rates.setPrices(new float[length]);
		
		for(int i=0; i < length; i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i);			
			rates.getPrices()[i] = (Float.valueOf(result.getProperty("price").toString()));
		}		
		
		return rates;
	}
	
	public static Delays getDelays(String currentDate, String currentTime) throws Exception{
		
		String methodName = "getDelaySnippet";
		String actionName = methodName;
		SoapObject request = new SoapObject(NAMESPACE,methodName);		
		request.addProperty("cDate", currentDate);
		request.addProperty("cTime", currentTime);
		request.addProperty("lang", "en");
		
		SoapObject results = callWebService(actionName, request);
		
		int length = results.getPropertyCount();		
		Delays delays = new Delays();
		delays.setCount(length);
		delays.setTrainNo(new String[length]);
		delays.setDelayTime(new String[length]);
		delays.setEndtime(new String[length]);
		delays.setComments(new String[length]);
		delays.setFromStationName(new String[length]);
		delays.setToStationName(new String[length]);
		delays.setOpatetionType(new String[length]);
	
		for(int i=0; i < length; i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i);			
			delays.getTrainNo()[i] = (String.valueOf(result.getProperty("TrainNo").toString()));
			delays.getDelayTime()[i] = (String.valueOf(result.getProperty("delayTime").toString()));
			delays.getEndtime()[i] = (String.valueOf(result.getProperty("endtime").toString()));
			delays.getComments()[i] = removeAnyType(String.valueOf(result.getProperty("comment").toString()));
			delays.getFromStationName()[i] = Functions.capitalizeFirstLetters(String.valueOf(result.getProperty("FrTrStationNameEng").toString()));
			delays.getToStationName()[i] = Functions.capitalizeFirstLetters(String.valueOf(result.getProperty("ToTrStationNameEng").toString()));
			delays.getOpatetionType()[i] = removeAnyType(String.valueOf(result.getProperty("opatetiontype").toString()));
			
		}		
		
		return delays;
	}
	
	public static String removeAnyType(String type){
		if (type.equals("anyType{}")){
			return null;
		}else{
			return type;
		}
	}

	

}
