package org.ofbiz.practice;

import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class PracticeService{
	
	public static final String module = PracticeService.class.getName(); 
	
	public static Map createProva(DispatchContext dctx, Map context){
		GenericDelegator delegator = dctx.getDelegator();
		
		try{
			String helloPersonId = delegator.getNextSeqId("HelloPerson");
			Debug.logInfo("Hellopersonid = " + helloPersonId, module);
			GenericValue helloPerson = delegator.makeValue("HelloPerson", UtilMisc.toMap("helloPersonId", helloPersonId));
			helloPerson.setNonPKFields(context);
			delegator.create(helloPerson);
			
			Map result = ServiceUtil.returnSuccess();
			result.put("hellpPersonId", helloPersonId);
			return result;
			
		} catch (GenericEntityException ex){
			return ServiceUtil.returnError(ex.getMessage());
		}
	}
}