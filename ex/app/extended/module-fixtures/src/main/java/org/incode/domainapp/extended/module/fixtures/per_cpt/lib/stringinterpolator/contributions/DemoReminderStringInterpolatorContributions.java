package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.contributions;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.dom.DemoReminder;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class DemoReminderStringInterpolatorContributions {


    public static final String TEMPLATE = "${properties['isis.website']}/${this.documentationPage}";

    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public URL open(final DemoReminder reminder) throws MalformedURLException {
        final String urlStr = stringInterpolatorService.interpolate(reminder, TEMPLATE);
        return new URL(urlStr);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    StringInterpolatorService stringInterpolatorService;

}

