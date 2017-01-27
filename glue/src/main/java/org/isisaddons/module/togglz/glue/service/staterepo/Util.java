package org.isisaddons.module.togglz.glue.service.staterepo;

import org.apache.isis.core.metamodel.services.ServicesInjector;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;

class Util {

    static <T> T lookupService(final Class<T> serviceClass) {
        return getServicesInjector().lookupService(serviceClass);
    }

    private static ServicesInjector getServicesInjector() {
        return IsisContext.getSessionFactory().getServicesInjector();
    }

}
