package org.isisaddons.module.security.dom.permission;

import org.apache.isis.core.commons.lang.StringExtensions;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeature;

/**
 * Whether the permission {@link #ALLOW grants} or {@link #VETO denies} access to an
 * {@link ApplicationFeature}.
 */
public enum ApplicationPermissionRule {
    /**
     * The permission grants the ability to view/use the {@link ApplicationFeature}.
     *
     * <p>
     * The {@link ApplicationPermissionMode mode} determines whether the
     * permission is to only view or also to use the {@link ApplicationFeature}.
     * </p>
     */
    ALLOW,
    /**
     * The permission prevents the ability to view/use the {@link ApplicationFeature}.
     *
     * <p>
     * The {@link ApplicationPermissionMode mode} determines whether the
     * permission is to only view or also to use the {@link ApplicationFeature}.
     * </p>
     */
    VETO;

    @Override
    public String toString() {
        return StringExtensions.capitalize(name());
    }
}
