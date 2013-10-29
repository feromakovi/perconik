
package com.gratex.perconik.services.vs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IVsActivityWatcherService", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IVsActivityWatcherService {


    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeStateChange", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeStateChange")
    @RequestWrapper(localName = "NotifyIdeStateChange", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeStateChange")
    @ResponseWrapper(localName = "NotifyIdeStateChangeResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeStateChangeResponse")
    public void notifyIdeStateChange(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeStateChangeDto eventDto);

    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeDocumentOperation", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeDocumentOperation")
    @RequestWrapper(localName = "NotifyIdeDocumentOperation", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeDocumentOperation")
    @ResponseWrapper(localName = "NotifyIdeDocumentOperationResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeDocumentOperationResponse")
    public void notifyIdeDocumentOperation(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeDocumentOperationDto eventDto);

    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeProjectOperation", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeProjectOperation")
    @RequestWrapper(localName = "NotifyIdeProjectOperation", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeProjectOperation")
    @ResponseWrapper(localName = "NotifyIdeProjectOperationResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeProjectOperationResponse")
    public void notifyIdeProjectOperation(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeProjectOperationDto eventDto);

    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeCheckin", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeCheckin")
    @RequestWrapper(localName = "NotifyIdeCheckin", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeCheckin")
    @ResponseWrapper(localName = "NotifyIdeCheckinResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeCheckinResponse")
    public void notifyIdeCheckin(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeCheckinDto eventDto);

    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeCodeOperation", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeCodeOperation")
    @RequestWrapper(localName = "NotifyIdeCodeOperation", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeCodeOperation")
    @ResponseWrapper(localName = "NotifyIdeCodeOperationResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeCodeOperationResponse")
    public void notifyIdeCodeOperation(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeCodeOperationDto eventDto);

    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeFindOperation", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeFindOperation")
    @RequestWrapper(localName = "NotifyIdeFindOperation", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeFindOperation")
    @ResponseWrapper(localName = "NotifyIdeFindOperationResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeFindOperationResponse")
    public void notifyIdeFindOperation(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeFindOperationDto eventDto);

    /**
     * 
     * @param eventDto
     */
    @WebMethod(operationName = "NotifyIdeCodeElementEvent", action = "http://tempuri.org/IVsActivityWatcherService/NotifyIdeCodeElementEvent")
    @RequestWrapper(localName = "NotifyIdeCodeElementEvent", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeCodeElementEvent")
    @ResponseWrapper(localName = "NotifyIdeCodeElementEventResponse", targetNamespace = "http://tempuri.org/", className = "com.gratex.perconik.services.activity.NotifyIdeCodeElementEventResponse")
    public void notifyIdeCodeElementEvent(
        @WebParam(name = "eventDto", targetNamespace = "http://tempuri.org/")
        IdeCodeElementEventDto eventDto);

}
