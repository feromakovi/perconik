
package com.gratex.perconik.services.itm;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ITMService", targetNamespace = "http://perconik.fiit.stuba.sk/ITM", wsdlLocation = "file:/D:/workspace/perconik/com.gratex.perconik.services/ITMService.svc.xml")
public class ITMService
    extends Service
{

    private final static URL ITMSERVICE_WSDL_LOCATION;
    private final static WebServiceException ITMSERVICE_EXCEPTION;
    private final static QName ITMSERVICE_QNAME = new QName("http://perconik.fiit.stuba.sk/ITM", "ITMService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/workspace/perconik/com.gratex.perconik.services/ITMService.svc.xml");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ITMSERVICE_WSDL_LOCATION = url;
        ITMSERVICE_EXCEPTION = e;
    }

    public ITMService() {
        super(__getWsdlLocation(), ITMSERVICE_QNAME);
    }

    public ITMService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ITMSERVICE_QNAME, features);
    }

    public ITMService(URL wsdlLocation) {
        super(wsdlLocation, ITMSERVICE_QNAME);
    }

    public ITMService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ITMSERVICE_QNAME, features);
    }

    public ITMService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ITMService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IITMaintenance
     */
    @WebEndpoint(name = "BasicHttpBinding_IITMaintenance")
    public IITMaintenance getBasicHttpBindingIITMaintenance() {
        return super.getPort(new QName("http://perconik.fiit.stuba.sk/ITM", "BasicHttpBinding_IITMaintenance"), IITMaintenance.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IITMaintenance
     */
    @WebEndpoint(name = "BasicHttpBinding_IITMaintenance")
    public IITMaintenance getBasicHttpBindingIITMaintenance(WebServiceFeature... features) {
        return super.getPort(new QName("http://perconik.fiit.stuba.sk/ITM", "BasicHttpBinding_IITMaintenance"), IITMaintenance.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ITMSERVICE_EXCEPTION!= null) {
            throw ITMSERVICE_EXCEPTION;
        }
        return ITMSERVICE_WSDL_LOCATION;
    }

}
