package org.isisaddons.module.servletapi.dom;

import javax.servlet.ServletContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ServletContextProvider {

    @Programmatic
    public ServletContext getServletContext() {
        return WebApplication.get().getServletContext();
    }

}
