package org.domain.SeamAmiciDelGas.webservices;
/*
 * The source code contained herein is licensed under the IBM Public License
 * Version 1.0, which has been approved by the Open Source Initiative.
 * Copyright (C) 2001, International Business Machines Corporation
 * Copyright (C) 2001, Hewlett-Packard Company
 * All Rights Reserved.
 *
 */

import java.util.Properties;

import org.uddi4j.transport.TransportFactory;

/**
 * Configures the environment for the UDDI4J samples.
 * <OL>
 * <LI>Reads samples property file.
 * <LI>Sets SOAP transport according to property file.
 * <LI>Configures SSL/JSSE provider
 * </OL>
 * 
 * @author David Melgar (dmelgar@us.ibm.com)
 */

public class UDDIConfigurator {

	public static final String DEFAULT_TRANSPORT_CLASS_NAME="org.uddi4j.transport.ApacheAxisTransport";
	private static final String DEFAULT_LOG_ENABLED = "false";
	private static final String DEFAULT_INQUIRY_URL="http://192.168.2.6:8787/juddi/inquiry";
	private static final String DEFAULT_PUBLISH_URL="http://192.168.2.6:8787/juddi/publish";
	private static final String DEFAULT_ADMIN_URL="http://192.168.2.6:8787/juddi/admin";
	private static final String DEFAULT__USERID="juddi";
   /**
    * Loads configuration file. File may require
    * modification before running samples.
    * 
    * @return Loaded properties object    
    */
   public static Properties load() {
      Properties config = new Properties();
      try {
         config.load(new java.io.FileInputStream("samples.prop"));
      } catch (Exception e) {
         System.out.println("Error loading samples property file\n" + e);
      }

      if(config.getProperty("TransportClassName")==null)
    	  config.setProperty("TransportClassName", UDDIConfigurator.DEFAULT_TRANSPORT_CLASS_NAME);
      if(config.getProperty("logEnabled")==null)
    	  config.setProperty("logEnabled", UDDIConfigurator.DEFAULT_LOG_ENABLED);
     if(config.getProperty("inquiryURL")==null)
    	  config.setProperty("inquiryURL", UDDIConfigurator.DEFAULT_INQUIRY_URL);
     if(config.getProperty("publishURL")==null)
    	  config.setProperty("publishURL", UDDIConfigurator.DEFAULT_PUBLISH_URL);
     if(config.getProperty("adminURL")==null)
    	  config.setProperty("adminURL", UDDIConfigurator.DEFAULT_ADMIN_URL);
     if(config.getProperty("userId")==null)
    	  config.setProperty("userId", UDDIConfigurator.DEFAULT__USERID);
   
      // Configure UDDI4J system properties. Normally set on commandline or elsewhere
      // SOAP transport being used
      if (System.getProperty(TransportFactory.PROPERTY_NAME)==null) {
         System.setProperty(TransportFactory.PROPERTY_NAME, config.getProperty("TransportClassName"));
      }               
      // Logging
      if (System.getProperty("org.uddi4j.logEnabled")==null) {
         System.setProperty("org.uddi4j.logEnabled", config.getProperty("logEnabled"));
      }               

      // Configure JSSE support
      try {
         System.setProperty("java.protocol.handler.pkgs", config.getProperty("handlerPackageName"));

         // Dynamically loads security provider based on properties. Typically configured in JRE
         java.security.Security.addProvider((java.security.Provider)
            Class.forName(config.getProperty("securityClassName")).newInstance());
      } catch (Exception e) {
         System.out.println("Error configuring JSSE provider. Make sure JSSE is in classpath.\n" + e);
      }
      return config;
   }
}
