package org.isisaddons.module.security.dom.tenancy;

import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;

/**
 * Optional hook so that alternative implementations of {@link org.isisaddons.module.security.dom.tenancy.ApplicationTenancy}.
 *
 * <p>
 *     To use, implement the interface and annotate that implementation with {@link org.apache.isis.applib.annotation.DomainService},
 *     for example:
 * </p>
 * <pre>
 *     &#64;DomainService
 *     public class MyApplicationTenancyFactory implements ApplicationTenancyFactory {
 *         public ApplicationTenancy newApplicationTenancy() {
 *             return container.newTransientInstance(MyApplicationTenancy.class);
 *         }
 *
 *         &#64;Inject
 *         DomainObjectContainer container;
 *     }
 * </pre>
 * <p>
 *     where:
 * </p>
 * <pre>
 *     public class MyApplicationTenancy extends ApplicationTenancy { ... }
 * </pre>
 */
public interface ApplicationTenancyFactory {

    public ApplicationTenancy newApplicationTenancy();

    public static class Default implements ApplicationTenancyFactory {

        public Default() {
            this(null);
        }
        Default(final DomainObjectContainer container) {
            this.container = container;
        }
        public ApplicationTenancy newApplicationTenancy() {
            return container.newTransientInstance(ApplicationTenancy.class);
        }

        @Inject
        DomainObjectContainer container;

    }

}
