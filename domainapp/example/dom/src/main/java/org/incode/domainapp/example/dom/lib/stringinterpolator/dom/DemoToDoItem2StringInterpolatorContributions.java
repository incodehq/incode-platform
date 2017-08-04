package org.incode.domainapp.example.dom.lib.stringinterpolator.dom;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;

import org.incode.domainapp.example.dom.demo.dom.todo2.DemoToDoItem2;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class DemoToDoItem2StringInterpolatorContributions {


    public static final String TEMPLATE = "${properties['isis.website']}/${this.documentationPage}";

    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public URL open(final DemoToDoItem2 toDoItem) throws MalformedURLException {
        final String urlStr = stringInterpolatorService.interpolate(toDoItem, TEMPLATE);
        return new URL(urlStr);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    StringInterpolatorService stringInterpolatorService;

}

