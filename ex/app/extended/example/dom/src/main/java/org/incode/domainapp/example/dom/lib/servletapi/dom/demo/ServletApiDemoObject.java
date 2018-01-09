package org.incode.domainapp.example.dom.lib.servletapi.dom.demo;

import java.io.IOException;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Ordering;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;

import org.isisaddons.module.servletapi.dom.HttpServletRequestProvider;
import org.isisaddons.module.servletapi.dom.HttpServletResponseProvider;
import org.isisaddons.module.servletapi.dom.ServletContextProvider;

import lombok.Getter;
import lombok.Setter;

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


    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    private String name;


    /**
     * derived property
     */
    public String getServletContextName() {
        return servletContextProvider.getServletContext().getServletContextName();
    }

    /**
     * derived property
     */
    public String getRequestLocale() {
        return httpServletRequestProvider.getServletRequest().getLocale().toString();
    }



    @MemberOrder(sequence = "1")
    public ServletApiDemoObject addHeader(
            final String header,
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




    @Override
    public int compareTo(ServletApiDemoObject other) {
        return Ordering.natural().onResultOf(ServletApiDemoObject::getName).compare(this, other);
    }




    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ServletContextProvider servletContextProvider;

    @javax.inject.Inject
    private HttpServletRequestProvider httpServletRequestProvider;

    @javax.inject.Inject
    private HttpServletResponseProvider httpServletResponseProvider;

}
