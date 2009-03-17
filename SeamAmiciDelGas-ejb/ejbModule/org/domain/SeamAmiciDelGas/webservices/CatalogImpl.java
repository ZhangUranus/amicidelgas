package org.domain.SeamAmiciDelGas.webservices;


import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.uddi4j.client.UDDIProxy;
import org.uddi4j.datatype.Name;
import org.uddi4j.datatype.OverviewDoc;
import org.uddi4j.datatype.binding.BindingTemplate;
import org.uddi4j.datatype.binding.InstanceDetails;
import org.uddi4j.datatype.binding.TModelInstanceInfo;
import org.uddi4j.datatype.tmodel.TModel;
import org.uddi4j.response.AuthToken;
import org.uddi4j.response.BindingDetail;
import org.uddi4j.response.BusinessInfo;
import org.uddi4j.response.BusinessList;
import org.uddi4j.response.ServiceInfo;
import org.uddi4j.response.ServiceList;
import org.uddi4j.response.TModelDetail;
import org.uddi4j.util.FindQualifier;
import org.uddi4j.util.FindQualifiers;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;




public class CatalogImpl {

	public static Hashtable instances= new Hashtable();
	
	public static CatalogInterface getInstanceForContadino(String idContadino){
		if(idContadino == null)
			return null;
		CatalogInterface  cat = (CatalogInterface) instances.get(idContadino);
		boolean sviluppoClelioStefano=false;
		if(sviluppoClelioStefano){
			return CatalogImpl.staticCatalog();
		}
		if(cat==null){
			cat= (CatalogInterface) retrieveServiceFromUddi(idContadino, "CatalogService", "CatalogServicePort");
			instances.put(idContadino, cat);
		}
		return cat;
	}
	
	private static CatalogInterface staticCatalog(){
		return new CatalogNoServiceImpl();
	}
	
	private static Remote retrieveServiceFromUddi(String businessName, String serviceName, String servicePort){
		try{
			Properties config = UDDIConfigurator.load();
			int MAX_ROWS=50;
			UDDIProxy proxy = new UDDIProxy();
			proxy.setInquiryURL(config.getProperty("inquiryURL"));
			proxy.setPublishURL(config.getProperty("publishURL"));
			AuthToken token = proxy.get_authToken(config.getProperty("userId"),
					config.getProperty("password"));
			
			Vector businessNames= new Vector();
			businessNames.add(new Name(businessName));
			FindQualifiers findQualifiers = new FindQualifiers();
			Vector qualifier = new Vector();
			qualifier.add(new FindQualifier(FindQualifier.exactNameMatch));
			findQualifiers.setFindQualifierVector(qualifier);
			
			BusinessList bl = proxy.find_business(businessNames, null, null, null, null, findQualifiers, 50);
			BusinessInfo bInfo= (BusinessInfo) bl.getBusinessInfos().getBusinessInfoVector().elementAt(0);
			String businessKey=bInfo.getBusinessKey();
			Vector names = new Vector();
			names.add(new Name(serviceName));
			ServiceList serviceList = proxy.find_service(businessKey, names, null,null, findQualifiers, MAX_ROWS);
			ServiceInfo sInfo= (ServiceInfo) serviceList.getServiceInfos().getServiceInfoVector().elementAt(0);
			BindingDetail btd = proxy.find_binding(null, sInfo.getServiceKey(), null, MAX_ROWS);
			BindingTemplate bt = (BindingTemplate)btd.getBindingTemplateVector().elementAt(0);
			Vector tModelInstanceInfoVector=bt.getTModelInstanceDetails().getTModelInstanceInfoVector();
			TModelInstanceInfo tModelInstanceInfo= (TModelInstanceInfo) tModelInstanceInfoVector.elementAt(0);
	       	InstanceDetails tModelInstanceDetails=tModelInstanceInfo.getInstanceDetails();
			String tModelKey=tModelInstanceInfo.getTModelKey();
			TModelDetail tModelDetail = proxy.get_tModelDetail(tModelKey);
			Vector tModelVector = tModelDetail.getTModelVector();
			TModel tModel= (TModel) tModelVector.elementAt(0);
			OverviewDoc doc = tModel.getOverviewDoc();
			String wsdlUrl = 	doc.getOverviewURL().getText();
			String nameSpaceUri= tModel.getName().getText();
			Service afs = Service.create(new java.net.URL(wsdlUrl),new QName(nameSpaceUri, "CatalogService"));
			QName portName=null;
			Iterator ports=afs.getPorts();
			while(ports.hasNext()){
			   QName port= (QName) ports.next();
			   if(port.getLocalPart().equalsIgnoreCase(servicePort))
				   portName=port;
			}
			
			Remote rProxy= afs.getPort(portName, CatalogInterface.class);
			    		
			  return rProxy;
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}
	
}
