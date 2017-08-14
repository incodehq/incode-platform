package org.incode.module.commchannel.dom.impl.postaladdress;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

@Mixin
public class PostalAddress_clearGeocode {

    private final PostalAddress postalAddress;

    //region > constructor
    public PostalAddress_clearGeocode(final PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Programmatic
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }
    //endregion

    public static class DomainEvent extends PostalAddress.ActionDomainEvent<PostalAddress_clearGeocode> { }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = DomainEvent.class
    )
    public PostalAddress $$() {
        this.postalAddress.setFormattedAddress(null);
        this.postalAddress.setPlaceId(null);
        this.postalAddress.setLatLng(null);
        this.postalAddress.setAddressComponents(null);
        this.postalAddress.setGeocodeApiResponseAsJson(null);
        return this.postalAddress;
    }

}
