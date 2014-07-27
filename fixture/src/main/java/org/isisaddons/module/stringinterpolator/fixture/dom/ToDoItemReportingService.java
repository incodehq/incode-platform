package org.isisaddons.module.stringinterpolator.fixture.dom;

import java.net.MalformedURLException;
import java.net.URL;
import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;

@DomainService
public class ToDoItemReportingService {

    @NotInServiceMenu
    @NotContributed(As.ASSOCIATION) // ie contributed as action
    public URL open(ToDoItem toDoItem) throws MalformedURLException {
        String urlStr = stringInterpolatorService.interpolate(toDoItem, "${properties['isis.website']}/${this.documentationPage}");
        return new URL(urlStr);
    }

    // //////////////////////////////////////
    
    @javax.inject.Inject
    private StringInterpolatorService stringInterpolatorService;
}

