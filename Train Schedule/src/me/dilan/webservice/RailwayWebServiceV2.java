package me.dilan.webservice;

import java.util.ArrayList;
import java.util.List;

import me.dilan.obj.TrainLines;
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

	

}
