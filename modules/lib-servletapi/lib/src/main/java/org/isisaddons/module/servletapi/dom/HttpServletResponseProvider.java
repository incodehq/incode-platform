package org.isisaddons.module.servletapi.dom;

import javax.servlet.http.HttpServletResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class HttpServletResponseProvider {

    @Programmatic
    public HttpServletResponse getServletResponse() {
        return (HttpServletResponse) RequestCycle.get().getResponse().getContainerResponse();
    }
}
