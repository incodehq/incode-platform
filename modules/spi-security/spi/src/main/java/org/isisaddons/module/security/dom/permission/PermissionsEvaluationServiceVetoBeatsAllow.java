package org.isisaddons.module.security.dom.permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import com.google.common.collect.Lists;

/**
 * An implementation whereby a VETO permission for a feature overrides an ALLOW (for same scope).
 */
public class PermissionsEvaluationServiceVetoBeatsAllow extends PermissionsEvaluationServiceAbstract {

    /**
     * Reverses the natural order of the {@link org.isisaddons.module.security.dom.permission.ApplicationPermissionValue}s.
     *
     * <p>
     *     This implementation relies on the fact that the {@link org.isisaddons.module.security.dom.permission.ApplicationPermissionValue}s are
     * passed through in natural order, with the leading part based on the
     * {@link org.isisaddons.module.security.dom.permission.ApplicationPermissionValue#getRule() rule} and with
     * {@link ApplicationPermissionRule} in turn comparable so that {@link ApplicationPermissionRule#ALLOW allow}
     * is ordered before {@link ApplicationPermissionRule#VETO veto}.
     * </p>
     */
    @Override
    protected Iterable<ApplicationPermissionValue> ordered(final Collection<ApplicationPermissionValue> permissionValues) {
        final ArrayList<ApplicationPermissionValue> reversed = Lists.newArrayList(permissionValues);
        Collections.reverse(reversed);
        return reversed;
    }

}
