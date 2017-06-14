package org.isisaddons.module.servletapi.dom;

import javax.servlet.http.HttpServletRequest;
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
        return (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
    }
}
