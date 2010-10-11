package me.dilan.webservice;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.util.Log;

public class RailwayWebServiceV2 {
	
	private final String NAMESPACE;
	private final String URL;
	private static RailwayWebServiceV2 serviceRailwayProxy;
	
	public RailwayWebServiceV2() {
		NAMESPACE = "http://ws.wso2.org/dataservice";
		URL = "http://220.247.225.202:9080/services/RailwayWebServiceV2Proxy.RailwayWebServiceV2ProxyHttpSoap12Endpoint";
	}

	public static RailwayWebServiceV2 getService() {
		if (serviceRailwayProxy == null) {
			serviceRailwayProxy = new RailwayWebServiceV2();
		}
		return serviceRailwayProxy;
	}

	private SoapObject callWebService(String actionName, SoapObject request)
			throws Exception {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				URL);
		androidHttpTransport.call(actionName, envelope);
		Log.v("result", envelope.bodyOut.toString());
		SoapObject results = (SoapObject) envelope.bodyIn;
		return results;
	}

	public List<Line> getLines() throws Exception {
		String methodName = "getLines";
		String actionName = methodName;
		SoapObject request = new SoapObject(NAMESPACE, methodName);
		request.addProperty("lang", "en");

		List<Line> lines = new ArrayList<Line>();

		// add all lines
		Line lineAll = new Line();
		lineAll.setId(0);
		lineAll.setLineName("All Lines");
		lines.add(lineAll);

		SoapObject results = callWebService(actionName, request);
		for (int i = 0; i < results.getPropertyCount(); i++) {
			SoapObject result = (SoapObject) results.getProperty(i);
			Line line = new Line();
			line.setId(Integer.parseInt(result.getProperty("id").toString()));
			line.setLineName(String.valueOf(result.getProperty("LineName")
					.toString()));
			lines.add(line);
		}

		return lines;
	}

}
