/**
 * Copyright (c) 2001-2005 The Open For Business Project - www.ofbiz.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 *  OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author     Si Chen (sichen@opensourcestrategies.com)
 *   
 * a few modifications by
 *              Vincenzo Di Lorenzo   (vinci.dilorenzo@libero.it)
 *              Giovanni Montella
 */

package org.ofbiz.hello3;

import java.util.HashMap;
import java.util.Map;

import org.ofbiz.base.util.Debug;        // uses Log4J
import org.ofbiz.base.util.UtilMisc;     // helpful utility for working with Maps, Lists, etc.
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class Hello3Services {
    
    public static final String module = Hello3Services.class.getName(); // used for debugging
   
    /*
      this method implements the Ofbiz service createHelloPerson
      as defined in services.xml
      it creates a new person in the entity HelloPerson 
    */
    public static Map createHelloPerson(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();  // always passed in with DispatchContext        
        
        try {
            String helloPersonId = delegator.getNextSeqId("HelloPerson"); // gets next available key for HelloPerson
            Debug.logInfo("helloPersonId = " + helloPersonId, module); // prints to the console or console.log
            GenericValue helloPerson = delegator.makeValue("HelloPerson", 
                    UtilMisc.toMap("helloPersonId", helloPersonId)); // create a GenericValue from ID we just got
            helloPerson.setNonPKFields(context); // move non-primary key fields from input parameters to GenericValue
            delegator.create(helloPerson); // store the generic value, ie persists it
            
            Map result = ServiceUtil.returnSuccess(); // gets standard Map for successful service operations
            result.put("helloPersonId", helloPersonId); // puts output parameter into Map to return
            return result; // return Map
        } catch (GenericEntityException ex) { // required if you use delegator in Java
            return ServiceUtil.returnError(ex.getMessage());
        }
    }
    
    /*
      this method implements the Ofbiz service searchHelloPerson
      as defined in services.xml
      it searchs for a person by querying the entity HelloPerson through 
      its primary key helloPersonId
    */
    public static Map searchHelloPerson(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();  // always passed in with DispatchContext        
        
        try {
            String helloPersonId;
            
            // get the input parameter helloPersonId from the context
            helloPersonId = (String) context.get("helloPersonId");
            Debug.logInfo("helloPersonId = " + helloPersonId, module);
            
            // query the entity HelloPerson looking based on a findByPrimaryKey method 
            Map queryResult = delegator.findByPrimaryKey("HelloPerson",
                                            UtilMisc.toMap("helloPersonId",helloPersonId));
            Debug.logInfo("queryResult = " + queryResult, module);
            
            // create the output Map object
            Map result = UtilMisc.toMap("helloPersonIdOut", helloPersonId);
            // fill the output object with the query results if any
            if ( queryResult != null) {
                result.put("firstName",queryResult.get("firstName"));
                result.put("lastName",queryResult.get("lastName"));
            }
            Debug.logInfo("result = " + result, module);
            
            // return the Map
            return result; 
        } catch (GenericEntityException ex) { // required if you use delegator in Java
            return ServiceUtil.returnError(ex.getMessage());
        }
    }    
    
    
    
}