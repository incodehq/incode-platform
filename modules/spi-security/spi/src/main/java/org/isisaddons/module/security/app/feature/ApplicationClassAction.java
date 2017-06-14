package org.isisaddons.module.security.app.feature;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.ViewModelLayout;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;

@SuppressWarnings("UnusedDeclaration")
@DomainObject(
        objectType = "isissecurity.ApplicationClassAction"
)
@ViewModelLayout(paged=100)
public class ApplicationClassAction extends ApplicationClassMember {

    public static abstract class PropertyDomainEvent<T> extends ApplicationClassMember.PropertyDomainEvent<ApplicationClassAction, T> {}

    public static abstract class CollectionDomainEvent<T> extends ApplicationClassMember.CollectionDomainEvent<ApplicationClassAction, T> {}

    public static abstract class ActionDomainEvent extends ApplicationClassMember.ActionDomainEvent<ApplicationClassAction> {}

    // //////////////////////////////////////

    //region > constructors

    public ApplicationClassAction() {
    }

    public ApplicationClassAction(final ApplicationFeatureId featureId) {
        super(featureId);
    }
    //endregion

    // //////////////////////////////////////

    //region > returnTypeName (property)

    public static class ReturnTypeDomainEvent extends PropertyDomainEvent<String> {}

    @Property(
            domainEvent = ReturnTypeDomainEvent.class
    )
    @MemberOrder(name="Data Type", sequence = "2.6")
    public String getReturnType() {
        return getFeature().getReturnTypeName();
    }
    //endregion

    // //////////////////////////////////////

    //region > actionSemantics (property)
    public static class ActionSemanticsDomainEvent extends PropertyDomainEvent<ActionSemantics.Of> {}

    @Property(
            domainEvent = ActionSemanticsDomainEvent.class
    )
    @MemberOrder(name="Detail", sequence = "2.8")
    public SemanticsOf getActionSemantics() {
        return getFeature().getActionSemantics();
    }
    //endregion

}
