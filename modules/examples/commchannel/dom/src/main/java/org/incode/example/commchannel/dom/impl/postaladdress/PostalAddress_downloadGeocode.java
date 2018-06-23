package org.incode.example.commchannel.dom.impl.postaladdress;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Clob;

@Mixin
public class PostalAddress_downloadGeocode {

    //region > constructor
    private final PostalAddress postalAddress;
    public PostalAddress_downloadGeocode(final PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Programmatic
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }
    //endregion

    public static class DomainEvent extends PostalAddress.ActionDomainEvent<PostalAddress_downloadGeocode> { }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = DomainEvent.class
    )
    public Clob $$(
            @ParameterLayout(named = ".json file name")
            final String fileName) {
        return new Clob(encodeAsFilename(fileName), "text/plain", this.postalAddress.getGeocodeApiResponseAsJson());
    }

    public String default0$$() {
        return "postalAddress-" + this.postalAddress.getFormattedAddress() + ".json";
    }

    public String validate0$$(final String fileName) {
        return !fileName.endsWith(".json")? "Must end with '.json'": null;
    }

    private static String encodeAsFilename(final String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e); // will not happen, UTF-8 always supported
        }
    }

}
