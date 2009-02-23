
/**
 * CatalogMatchMakerCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */

    package org.domain.SeamAmiciDelGas.webservices;

    /**
     *  CatalogMatchMakerCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class CatalogMatchMakerCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public CatalogMatchMakerCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public CatalogMatchMakerCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getDescription method
            * override this method for handling normal response from getDescription operation
            */
           public void receiveResultgetDescription(
                    org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetDescriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDescription operation
           */
            public void receiveErrorgetDescription(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getItemsForCategory method
            * override this method for handling normal response from getItemsForCategory operation
            */
           public void receiveResultgetItemsForCategory(
                    org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetItemsForCategoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getItemsForCategory operation
           */
            public void receiveErrorgetItemsForCategory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getItems method
            * override this method for handling normal response from getItems operation
            */
           public void receiveResultgetItems(
                    org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetItemsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getItems operation
           */
            public void receiveErrorgetItems(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategories method
            * override this method for handling normal response from getCategories operation
            */
           public void receiveResultgetCategories(
                    org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetCategoriesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategories operation
           */
            public void receiveErrorgetCategories(java.lang.Exception e) {
            }
                


    }
    