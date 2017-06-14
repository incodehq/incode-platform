package org.incode.module.commchannel.dom.impl.postaladdress;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.api.GeocodingService;

@Mixin
public class PostalAddress_lookupGeocode {

    //region > injected services
    @Inject
    GeocodingService geocodingService;
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > mixins
    PostalAddress_update mixinUpdatePostalAddress(final PostalAddress postalAddress) {
        return container.mixin(PostalAddress_update.class, postalAddress);
    }
    //endregion

    //region > constructor
    private final PostalAddress postalAddress;
    public PostalAddress_lookupGeocode(final PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Programmatic
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }
    //endregion

    public static class DomainEvent extends PostalAddress.ActionDomainEvent<PostalAddress_lookupGeocode> { }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = DomainEvent.class
    ) public PostalAddress $$(
            @ParameterLayout(named = "Address")
            final String address) {

        mixinUpdatePostalAddress(this.postalAddress).lookupAndUpdateGeocode(true, address);

        return this.postalAddress;
    }

    public String default0$$(
    ) {
        return geocodingService.combine(
                GeocodingService.Encoding.NOT_ENCODED,
                this.postalAddress.getAddressLine1(), this.postalAddress.getAddressLine2(),
                this.postalAddress.getAddressLine3(), this.postalAddress.getAddressLine4(),
                this.postalAddress.getPostalCode(), this.postalAddress.getCountry());
    }

}
