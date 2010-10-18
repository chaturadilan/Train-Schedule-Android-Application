package me.dilan.webservice;

import java.util.Calendar;

import me.dilan.obj.TrainLines;
import me.dilan.obj.TrainSchedule;
import me.dilan.obj.TrainStations;
import me.dilan.util.Functions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.util.Log;

public class RailwayWebServiceV2 {
	
	private final static String NAMESPACE = "http://ws.wso2.org/dataservice";
	private final static String ENDPOINT = "http://220.247.225.202:9080/services/RailwayWebServiceV2Proxy.RailwayWebServiceV2ProxyHttpSoap12Endpoint";
		
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
		int[] ids = new int[length];
		String[] names = new String[length];
		
		ids[0] = 0;
		names[0] = "All Stations";
		
		for(int i= 1; i < length; i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i-1);			
			ids[i] = (Integer.parseInt(result.getProperty("id").toString()));
			names[i] = Functions.capitalizeFirstLetters(String.valueOf(result.getProperty("LineName").toString()));						
		}		
		TrainLines trainLines = new TrainLines();
		trainLines.setIds(ids);
		trainLines.setNames(names);
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
		String[] codes = new String[length];
		String[] names = new String[length];
		
		
		for(int i=0; i< results.getPropertyCount();i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i);
			codes[i] = String.valueOf(result.getProperty("StationCode").toString());
			names[i] = Functions.capitalizeFirstLetters(String.valueOf(result.getProperty("StationNameEng").toString()));				
		}	
		
		TrainStations trainStations = new TrainStations();
		trainStations.setCodes(codes);
		trainStations.setNames(names);
		return trainStations;		
	}
	
	/*	public TrainSchedule getSchedule(String fromStationCode, String toStationCode, String arrivalTime, String depatureTime, String currentDate, String currentTime) throws Exception{
		
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
		
			
		for(int i=0;i<results.getPropertyCount();i++)
		{
			SoapObject result = (SoapObject) results.getProperty(i);
			Schedule schedule = new Schedule();			
			schedule.setName(String.valueOf(result.getProperty("name").toString()));
			schedule.setArrivalTime(Functions.getCalndarFromTime(String.valueOf(result.getProperty("arrival").toString())));
			schedule.setDepartureTime(Functions.getCalndarFromTime(String.valueOf(result.getProperty("departure").toString())));
			schedule.setArrivalAtDestinationTime(Functions.getCalndarFromTime(String.valueOf(result.getProperty("destination").toString())));
			schedule.setDelayTime(Functions.getCalndarFromTime(String.valueOf(result.getProperty("delay").toString())));
			schedule.setComment(String.valueOf(result.getProperty("comment").toString()));
			schedule.setFdDescription(String.valueOf(result.getProperty("fdescriptioneng").toString()));
			schedule.setTyDescription(String.valueOf(result.getProperty("tydescriptioneng").toString()));
			//schedule.setFromStation(fromStation);
			//schedule.setToStation(toStation);
			//schedules.add(schedule);			
		} 
		return schedules;
	}*/

	

}
