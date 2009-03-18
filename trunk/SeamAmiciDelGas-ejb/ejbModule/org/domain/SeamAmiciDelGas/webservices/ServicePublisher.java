package org.domain.SeamAmiciDelGas.webservices;

import java.net.MalformedURLException;
import java.util.Properties;
import java.util.Vector;

import org.uddi4j.UDDIException;
import org.uddi4j.client.UDDIProxy;
import org.uddi4j.datatype.Name;
import org.uddi4j.datatype.OverviewDoc;
import org.uddi4j.datatype.binding.AccessPoint;
import org.uddi4j.datatype.binding.BindingTemplate;
import org.uddi4j.datatype.binding.BindingTemplates;
import org.uddi4j.datatype.binding.InstanceDetails;
import org.uddi4j.datatype.binding.TModelInstanceDetails;
import org.uddi4j.datatype.binding.TModelInstanceInfo;
import org.uddi4j.datatype.business.BusinessEntity;
import org.uddi4j.datatype.service.BusinessService;
import org.uddi4j.datatype.tmodel.TModel;
import org.uddi4j.response.AuthToken;
import org.uddi4j.response.BusinessDetail;
import org.uddi4j.response.TModelDetail;
import org.uddi4j.transport.TransportException;
import org.uddi4j.util.AuthInfo;
import org.uddi4j.util.CategoryBag;

public class ServicePublisher {

	public void publishService(String businessName, String serviceName, String urlWsdl, String nameSpace){
		try {
			Properties config = UDDIConfigurator.load();
			int MAX_ROWS=50;
			UDDIProxy proxy = new UDDIProxy();
			proxy.setInquiryURL(config.getProperty("inquiryURL"));
			proxy.setPublishURL(config.getProperty("publishURL"));
			AuthToken token = proxy.get_authToken(config.getProperty("userId"),
					config.getProperty("password"));
			AuthInfo authInfo = token.getAuthInfo();
			BusinessEntity entity= new BusinessEntity("", businessName);
			Vector entities= new Vector();
			entities.add(entity);
			BusinessDetail bDetail = proxy.save_business(authInfo.getText(), entities);
			BusinessEntity publishedEntity=(BusinessEntity) bDetail.getBusinessEntityVector().elementAt(0);
			String businessKey= publishedEntity.getBusinessKey();
			
			BusinessService service= new BusinessService();
			service.setName(serviceName);
			service.setBusinessKey(businessKey);
			service.setServiceKey("");
		
			
			TModel tModel= new TModel();
			OverviewDoc doc= new OverviewDoc();
			doc.setOverviewURL(urlWsdl);
			tModel.setOverviewDoc(doc);
			tModel.setName(new Name(nameSpace));
			Vector tModels= new Vector();
			tModels.add(tModel);
			TModelDetail tDetail = proxy.save_tModel(authInfo.getText(), tModels);
			TModel publishedTModel=(TModel) tDetail.getTModelVector().elementAt(0);
		
			
			TModelInstanceInfo tModelInstanceInfo =
				new TModelInstanceInfo(publishedTModel.getTModelKey());
			Vector tModelInstanceInfoList=new Vector();
			tModelInstanceInfoList.addElement(tModelInstanceInfo);
			TModelInstanceDetails tModelInstanceDetails =new TModelInstanceDetails();
			tModelInstanceDetails.setTModelInstanceInfoVector(tModelInstanceInfoList);
			BindingTemplate bindingTemplate= new BindingTemplate();
			
			AccessPoint accessPoint = new AccessPoint();
			accessPoint.setURLType("http");
			accessPoint.setText(urlWsdl.substring(0, urlWsdl.indexOf('?')));
			
			bindingTemplate.setAccessPoint(accessPoint);
			bindingTemplate.setTModelInstanceDetails(tModelInstanceDetails);
			
			BindingTemplates b= new BindingTemplates();
			b.add(bindingTemplate);
			service.setBindingTemplates(b);
			Vector businessServices= new Vector();
			businessServices.add(service);
			proxy.save_service(authInfo.getText(), businessServices);
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UDDIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	public static void main(String[] args) {
		ServicePublisher s= new ServicePublisher();
		s.publishService("1139780629", "CatalogService","http://localhost:8080/SeamCatalogoContadino-ear-SeamCatalogoContadino-ejb/CatalogService?wsdl", "http://session.SeamCatalogoContadino.domain.org/");
	}
	
}
