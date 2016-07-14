/*
 *
 *  Copyright 2015 incode.org
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.commchannel.dom.impl.postaladdress;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.commons.lang3.StringUtils;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.TitleBuffer;

import org.isisaddons.wicket.gmap3.cpt.applib.Location;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeCommChannel"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "PostalAddress_formattedAddress_idx",
                members = { "formattedAddress" }),
        @javax.jdo.annotations.Index (
                name = "PostalAddress_unq_idx",
                members = { "placeId" })
})
@DomainObject(
        objectType = "incodeCommChannel.PostalAddress"
)
public class PostalAddress extends CommunicationChannel<PostalAddress> {

    //region > events
    public static abstract class PropertyDomainEvent<S,T> extends CommChannelModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S,T> extends CommChannelModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> { }
    //endregion

    //region > title
    public String title() {
        if(getPlaceId() != null) {
            return getFormattedAddress();
        } else {
            final TitleBuffer buf = new TitleBuffer();
            buf.append(getAddressLine1())
                .append(",", getAddressLine2())
                .append(",", getAddressLine3())
                .append(",", getAddressLine4())
                .append(",", getPostalCode())
                .append(",", getCountry())
            ;
            return StringUtils.abbreviateMiddle(buf.toString(), "...", 30);
        }
    }
    //endregion


    public static class AddressLine1DomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = AddressLine1DomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String addressLine1;


    public static class AddressLine2DomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.ADDRESS_LINE
    )
    @Property(
            domainEvent = AddressLine2DomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String addressLine2;


    public static class AddressLine3DomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.ADDRESS_LINE
    )
    @Property(
            domainEvent = AddressLine3DomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String addressLine3;


    public static class AddressLine4DomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.ADDRESS_LINE
    )
    @Property(
            domainEvent = AddressLine4DomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String addressLine4;


    public static class PostalCodeDomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.POSTAL_CODE
    )
    @Property(
            domainEvent = PostalCodeDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String postalCode;


    public static class CountryDomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true", // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
            length = CommChannelModule.JdoColumnLength.COUNTRY
    )
    @Property(
            domainEvent = CountryDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY
    )
    private String country;


    public static class FormattedAddressDomainEvent extends PropertyDomainEvent<PostalAddress, String> {}
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.FORMATTED_ADDRESS)
    @Property(
            domainEvent = FormattedAddressDomainEvent.class
    )
    private String formattedAddress;


    public static class PlaceIdDomainEvent extends PropertyDomainEvent<PostalAddress,String> {}
    @Getter @Setter
    @javax.jdo.annotations.Column(
            allowsNull = "true" // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
    )
    @Property(
            domainEvent = PlaceIdDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String placeId;


    public static class LatLngDomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true") // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
    @Property(
            domainEvent = LatLngDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String latLng;


    public static class AddressComponentsDomainEvent extends PropertyDomainEvent<PostalAddress,PostalAddress> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = AddressComponentsDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(multiLine = 10)
    private String addressComponents;


    public static class GeocodeApiResponseAsJsonDomainEvent extends PropertyDomainEvent<PostalAddress,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = GeocodeApiResponseAsJsonDomainEvent.class,
            hidden = Where.EVERYWHERE
    )
    @PropertyLayout(
            multiLine = 9
    )
    private String geocodeApiResponseAsJson;


    //region > Locatable API
    @Programmatic
    @Override
    public Location getLocation() {
        final String latLng = getLatLng();
        return latLng != null? Location.fromString(latLng.replace(",",";")): null;
    }
    //endregion

}
