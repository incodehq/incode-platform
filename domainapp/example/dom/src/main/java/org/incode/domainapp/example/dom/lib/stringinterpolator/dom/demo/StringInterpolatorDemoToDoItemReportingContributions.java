package org.incode.domainapp.example.dom.lib.stringinterpolator.dom.demo;

import java.net.MalformedURLException;
import java.net.URL;
import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.lib.stringinterpolator.dom.demo.StringInterpolatorDemoToDoItem;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class StringInterpolatorDemoToDoItemReportingContributions {

    public static final String TEMPLATE = "${properties['isis.website']}/${this.documentationPage}";

    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public URL open(final StringInterpolatorDemoToDoItem toDoItem) throws MalformedURLException {
        final String urlStr = stringInterpolatorService.interpolate(toDoItem, TEMPLATE);
        return new URL(urlStr);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private StringInterpolatorService stringInterpolatorService;
}

