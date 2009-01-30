package org.ofbiz.examplecomponent.weatherService;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericPK;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;

public class WeatherService {

	
	public static Map getWeather(DispatchContext dc, Map context){
		GenericDelegator gd=GenericDelegator.getGenericDelegator("default");
		Map fields= new HashMap();
		fields.put("codicefiscale", "BBBGGG75C21A785K");
		try {
			GenericValue value = gd.findByPrimaryKey("exampleUtente", fields);
			String username= value.getString("username");
			Map result= new HashMap();
			String location= (String) context.get("location");
			result.put("weather", username);
			return result;
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*File f= new File("./serviceInvoked.log");
		try {
			
			f.createNewFile();
			PrintWriter p= new PrintWriter(f);
			p.println("Invoked service with argument "+ location);
			p.flush();
			p.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return fields;
		
	}
	
}
