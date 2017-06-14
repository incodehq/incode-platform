package org.isisaddons.module.security.dom.permission;

import java.util.Collection;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;

public abstract class PermissionsEvaluationServiceAbstract implements PermissionsEvaluationService {

    @Programmatic
    @Override
    public ApplicationPermissionValueSet.Evaluation evaluate(
            final ApplicationFeatureId targetMemberId,
            final ApplicationPermissionMode mode,
            final Collection<ApplicationPermissionValue> permissionValues) {

        final Iterable<ApplicationPermissionValue> ordered = ordered(permissionValues);

        for (final ApplicationPermissionValue permissionValue : ordered) {
            if(permissionValue.implies(targetMemberId, mode)) {
                return new ApplicationPermissionValueSet.Evaluation(permissionValue, true);
            } else if(permissionValue.refutes(targetMemberId, mode)) {
                return new ApplicationPermissionValueSet.Evaluation(permissionValue, false);
            }
        }
        return null;
    }

    protected abstract Iterable<ApplicationPermissionValue> ordered(Collection<ApplicationPermissionValue> permissionValues);

}
