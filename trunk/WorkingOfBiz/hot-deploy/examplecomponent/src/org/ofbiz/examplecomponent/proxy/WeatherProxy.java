package org.ofbiz.examplecomponent.proxy;

import java.util.HashMap;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.GenericDispatcher;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

public class WeatherProxy {

	public String getWeather(String location){
		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		LocalDispatcher dispatcher = GenericDispatcher.getLocalDispatcher("default",delegator);
		GenericValue admin = null;
		try {
			admin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "admin"));
			Map params=new HashMap();
			params.put("location", location);
			Map result = dispatcher.runSync("getWeather", params);
			String resultString=(String) result.get("weather");
			return resultString;
		} catch (GenericEntityException e1) {
			e1.printStackTrace();
		} catch (GenericServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}
}
