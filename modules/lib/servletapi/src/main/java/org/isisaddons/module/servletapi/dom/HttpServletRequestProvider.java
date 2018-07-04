package org.isisaddons.module.servletapi.dom;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class HttpServletRequestProvider {

    @Programmatic
    public HttpServletRequest getServletRequest() {
        final RequestCycle requestCycle = RequestCycle.get();
        if(requestCycle == null) {
            return null;
        }

        final Request request = requestCycle.getRequest();
        if(request == null) {
            return null;
        }

        final Object containerRequest = request.getContainerRequest();
        if (!(containerRequest instanceof HttpServletRequest)) {
            return null;
        }

        return (HttpServletRequest) containerRequest;
    }
}
