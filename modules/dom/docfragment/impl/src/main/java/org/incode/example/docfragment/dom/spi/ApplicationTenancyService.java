package org.incode.example.docfragment.dom.spi;

import org.apache.isis.applib.annotation.Programmatic;

public interface ApplicationTenancyService {

    @Programmatic
    String atPathFor(final Object domainObjectToRender);


}