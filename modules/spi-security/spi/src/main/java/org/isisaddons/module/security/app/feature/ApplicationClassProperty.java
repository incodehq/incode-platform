package org.isisaddons.module.security.app.feature;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.ViewModelLayout;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;

@SuppressWarnings("UnusedDeclaration")
@DomainObject(
        objectType = "isissecurity.ApplicationClassProperty"
)
@ViewModelLayout(paged=100)
public class ApplicationClassProperty extends ApplicationClassMember {

    public static abstract class PropertyDomainEvent<T> extends ApplicationClassMember.PropertyDomainEvent<ApplicationClassProperty, T> {}

    public static abstract class CollectionDomainEvent<T> extends ApplicationClassMember.CollectionDomainEvent<ApplicationClassProperty, T> {}

    public static abstract class ActionDomainEvent extends ApplicationClassMember.ActionDomainEvent<ApplicationClassProperty> {}

    // //////////////////////////////////////

    //region > constructors
    public ApplicationClassProperty() {
    }

    public ApplicationClassProperty(final ApplicationFeatureId featureId) {
        super(featureId);
    }
    //endregion

    //region > returnType

    public static class ReturnTypeDomainEvent extends PropertyDomainEvent<String> {}

    @Property(
            domainEvent = ReturnTypeDomainEvent.class
    )
    @MemberOrder(name="Data Type", sequence = "2.6")
    public String getReturnType() {
        return getFeature().getReturnTypeName();
    }
    //endregion

    //region > derived

    public static class DerivedDomainEvent extends PropertyDomainEvent<Boolean> {}

    @Property(
            domainEvent = DerivedDomainEvent.class
    )
    @MemberOrder(name="Detail", sequence = "2.7")
    public boolean isDerived() {
        return getFeature().isDerived();
    }
    //endregion


    //region > maxLength
    public static class MaxLengthDomainEvent extends PropertyDomainEvent<Integer> {}

    @Property(
            domainEvent = MaxLengthDomainEvent.class,
            optionality = Optionality.OPTIONAL
    )
    @MemberOrder(name="Detail", sequence = "2.8")
    public Integer getMaxLength() {
        return getFeature().getPropertyMaxLength();
    }

    public boolean hideMaxLength() {
        return !String.class.getSimpleName().equals(getReturnType());
    }

    //endregion


    //region > typicalLength
    public static class TypicalLengthDomainEvent extends PropertyDomainEvent<Integer> {}

    @Property(
            domainEvent = TypicalLengthDomainEvent.class,
            optionality = Optionality.OPTIONAL
    )
    @MemberOrder(name="Detail", sequence = "2.9")
    public Integer getTypicalLength() {
        return getFeature().getPropertyTypicalLength();
    }

    public boolean hideTypicalLength() {
        return !String.class.getSimpleName().equals(getReturnType());
    }

    //endregion

}

