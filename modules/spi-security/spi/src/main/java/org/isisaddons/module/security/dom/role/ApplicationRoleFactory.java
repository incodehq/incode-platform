package org.isisaddons.module.security.dom.role;

import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;

/**
 * Optional hook so that alternative implementations of {@link org.isisaddons.module.security.dom.role.ApplicationRole}.
 *
 * <p>
 *     To use, simply implement the interface and annotate that implementation with {@link org.apache.isis.applib.annotation.DomainService},
 *     for example:
 * </p>
 * <pre>
 *     @DomainService
 *     public class MyApplicationRoleFactory implements ApplicationRoleFactory {
 *         public ApplicationRole newApplicationRole() {
 *             return container.newTransientInstance(MyApplicationRole.class);
 *         }
 *
 *         @Inject
 *         DomainObjectContainer container;
 *     }
 * </pre>
 * <p>
 *     where:
 * </p>
 * <pre>
 *     public class MyApplicationRole extends ApplicationRole { ... }
 * </pre>
 */
public interface ApplicationRoleFactory {

    public ApplicationRole newApplicationRole();

    public static class Default implements ApplicationRoleFactory {

        public Default() {
            this(null);
        }
        Default(final DomainObjectContainer container) {
            this.container = container;
        }
        public ApplicationRole newApplicationRole() {
            return container.newTransientInstance(ApplicationRole.class);
        }

        @Inject
        DomainObjectContainer container;

    }

}
