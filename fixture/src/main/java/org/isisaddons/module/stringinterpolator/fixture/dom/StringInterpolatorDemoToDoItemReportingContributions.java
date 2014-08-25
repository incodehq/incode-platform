package org.isisaddons.module.stringinterpolator.fixture.dom;

import java.net.MalformedURLException;
import java.net.URL;
import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;

@DomainService
public class StringInterpolatorDemoToDoItemReportingContributions {

    public static final String TEMPLATE = "${properties['isis.website']}/${this.documentationPage}";

    @NotInServiceMenu
    @NotContributed(As.ASSOCIATION) // ie contributed as action
    public URL open(StringInterpolatorDemoToDoItem toDoItem) throws MalformedURLException {
        String urlStr = stringInterpolatorService.interpolate(toDoItem, TEMPLATE);
        return new URL(urlStr);
    }

    // //////////////////////////////////////
    
    @javax.inject.Inject
    private StringInterpolatorService stringInterpolatorService;
}

