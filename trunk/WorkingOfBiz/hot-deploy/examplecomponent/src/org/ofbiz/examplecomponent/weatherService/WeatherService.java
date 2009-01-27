package org.ofbiz.examplecomponent.weatherService;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;

public class WeatherService {

	
	public static Map getWeather(DispatchContext dc, Map context){
		Map result= new HashMap();
		String location= (String) context.get("location");
		result.put("weather", "good");
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		File f= new File("./serviceInvoked.log");
		try {
			f.createNewFile();
			PrintWriter p= new PrintWriter(f);
			p.println("Invoked service with argument "+ location);
			p.flush();
			p.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
