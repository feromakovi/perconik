
package com.gratex.perconik.services.tag;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
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
@WebService(name = "ITagProfileWcfSvc", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ITagProfileWcfSvc {


    /**
     * 
     * @param req
     * @return
     *     returns com.gratex.perconik.tagadm.itagprofilewcfsvc.GetTagProfileResponse2
     */
    @WebMethod(operationName = "GetTagProfile", action = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc/ITagProfileWcfSvc/GetTagProfile")
    @WebResult(name = "GetTagProfileResult", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc")
    @RequestWrapper(localName = "GetTagProfile", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", className = "com.gratex.perconik.tagadm.itagprofilewcfsvc.GetTagProfile")
    @ResponseWrapper(localName = "GetTagProfileResponse", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", className = "com.gratex.perconik.tagadm.itagprofilewcfsvc.GetTagProfileResponse")
    public GetTagProfileResponse2 getTagProfile(
        @WebParam(name = "Req", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc")
        GetTagProfileRequest req);

    /**
     * 
     * @param req
     * @return
     *     returns com.gratex.perconik.tagadm.itagprofilewcfsvc.SearchTagProfileResponse2
     */
    @WebMethod(operationName = "SearchTagProfile", action = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc/ITagProfileWcfSvc/SearchTagProfile")
    @WebResult(name = "SearchTagProfileResult", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc")
    @RequestWrapper(localName = "SearchTagProfile", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", className = "com.gratex.perconik.tagadm.itagprofilewcfsvc.SearchTagProfile")
    @ResponseWrapper(localName = "SearchTagProfileResponse", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", className = "com.gratex.perconik.tagadm.itagprofilewcfsvc.SearchTagProfileResponse")
    public SearchTagProfileResponse2 searchTagProfile(
        @WebParam(name = "Req", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc")
        SearchTagProfileRequest req);

}
