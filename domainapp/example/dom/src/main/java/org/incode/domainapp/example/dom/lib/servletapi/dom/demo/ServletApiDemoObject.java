package org.incode.domainapp.example.dom.lib.servletapi.dom.demo;

import java.io.IOException;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.module.servletapi.dom.HttpServletRequestProvider;
import org.isisaddons.module.servletapi.dom.HttpServletResponseProvider;
import org.isisaddons.module.servletapi.dom.ServletContextProvider;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleLibServletApi"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class ServletApiDemoObject implements Comparable<ServletApiDemoObject> {

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion

    //region > servlet context name (derived property)
    public String getServletContextName() {
        return servletContextProvider.getServletContext().getServletContextName();
    }
    //endregion

    //region > request locale name (derived property)
    public String getRequestLocale() {
        return httpServletRequestProvider.getServletRequest().getLocale().toString();
    }
    //endregion

    //region > cookie (action)
    @MemberOrder(sequence = "1")
    public ServletApiDemoObject addHeader(
            @ParameterLayout(named = "Header")
            final String header,
            @ParameterLayout(named = "Value")
            final String value) throws IOException {
        httpServletResponseProvider.getServletResponse().addHeader(header, value);
        return this;
    }

    public String default0AddHeader() {
        return "x-isisaddons-module-servletapi-demo";
    }
    public String default1AddHeader() {
        return "hello!";
    }
    //endregion


    //region > compareTo

    @Override
    public int compareTo(ServletApiDemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ServletContextProvider servletContextProvider;

    @javax.inject.Inject
    private HttpServletRequestProvider httpServletRequestProvider;

    @javax.inject.Inject
    private HttpServletResponseProvider httpServletResponseProvider;
    //endregion

}
