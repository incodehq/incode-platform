/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.commons.lang3.StringUtils;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Clob;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.api.geocoding.GeocodedAddress;
import org.incode.module.commchannel.dom.api.geocoding.GeocodingService;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;

@javax.jdo.annotations.PersistenceCapable
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
        editing = Editing.DISABLED
)
public class PostalAddress extends CommunicationChannel<PostalAddress> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends
            CommChannelModule.PropertyDomainEvent<PostalAddress, T> {
        public PropertyDomainEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final PostalAddress source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<PostalAddress, T> {
        public CollectionDomainEvent(final PostalAddress source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final PostalAddress source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<PostalAddress> {
        public ActionDomainEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final PostalAddress source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final PostalAddress source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
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

    //region > addressLine1 (property)

    public static class AddressLine1Event extends PropertyDomainEvent<String> {

        public AddressLine1Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public AddressLine1Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String addressLine1;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = AddressLine1Event.class,
            optionality = Optionality.MANDATORY
    )
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    //endregion

    //region > addressLine2 (property)

    public static class AddressLine2Event extends PropertyDomainEvent<String> {

        public AddressLine2Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public AddressLine2Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String addressLine2;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = AddressLine2Event.class,
            optionality = Optionality.MANDATORY
    )
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    //endregion

    //region > addressLine3 (property)

    public static class AddressLine3Event extends PropertyDomainEvent<String> {

        public AddressLine3Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public AddressLine3Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String addressLine3;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = AddressLine3Event.class,
            optionality = Optionality.MANDATORY
    )
    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(final String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    //endregion

    //region > addressLine4 (property)

    public static class AddressLine4Event extends PropertyDomainEvent<String> {

        public AddressLine4Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public AddressLine4Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String addressLine4;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = AddressLine4Event.class,
            optionality = Optionality.MANDATORY
    )
    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(final String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    //endregion

    //region > postalCode (property)

    public static class PostalCodeEvent extends PropertyDomainEvent<String> {

        public PostalCodeEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public PostalCodeEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String postalCode;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.POSTAL_CODE)
    @Property(
            domainEvent = PostalCodeEvent.class,
            optionality = Optionality.MANDATORY
    )
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    //endregion

    //region > country (property)

    public static class CountryEvent extends PropertyDomainEvent<String> {
        public CountryEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public CountryEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }
    private String country;

    // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.COUNTRY)
    @Property(
            optionality = Optionality.MANDATORY,
            domainEvent = CountryEvent.class
    )
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    //endregion

    //region > update (action)

    public static class UpdateAddressEvent extends ActionDomainEvent {
        public UpdateAddressEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public UpdateAddressEvent(
                final PostalAddress source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }

        public UpdateAddressEvent(
                final PostalAddress source,
                final Identifier identifier,
                final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = UpdateAddressEvent.class
    )
    public PostalAddress updateAddress(
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
            @ParameterLayout(named = "Address Line 1")
            final String addressLine1,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 2")
            final String addressLine2,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 3")
            final String addressLine3,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.ADDRESS_LINE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Address Line 4")
            final String addressLine4,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.POSTAL_CODE, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Postal Code")
            final String postalCode,
            @Parameter(maxLength = CommChannelModule.JdoColumnLength.COUNTRY, optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Country")
            final String country,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Lookup geocode")
            final Boolean lookupGeocode) {

        setAddressLine1(addressLine1);
        setAddressLine2(addressLine2);
        setAddressLine3(addressLine3);
        setAddressLine4(addressLine4);
        setPostalCode(postalCode);
        setCountry(country);

        lookupAndUpdateGeocode(
                lookupGeocode,
                addressLine1, addressLine2, addressLine3, addressLine4, postalCode, country);

        return this;
    }

    void lookupAndUpdateGeocode(
            final Boolean lookupGeocode,
            final String... addressParts) {

        if (lookupGeocode == null) {
            return;
        }

        if (lookupGeocode) {
            final String address = geocodingService.combine(GeocodingService.Encoding.ENCODED, addressParts);
            final GeocodedAddress geocodedAddress = geocodingService.lookup(address);

            if (GeocodedAddress.isOk(geocodedAddress)) {
                setFormattedAddress(geocodedAddress.getFormattedAddress());
                setGeocodeApiResponseAsJson(geocodedAddress.getApiResponseAsJson());
                setPlaceId(geocodedAddress.getPlaceId());
                setLatLng(geocodedAddress.getLatLng());
                setAddressComponents(geocodedAddress.getAddressComponents());
            } else {
                container.warnUser(
                        TranslatableString.tr("Could not lookup geocode for address"),
                        PostalAddressContributions.class, "newPostal");
            }
        } else {
            resetGeocode();
        }
    }

    public String default0UpdateAddress() {
        return getAddressLine1();
    }
    public String default1UpdateAddress() {
        return getAddressLine2();
    }
    public String default2UpdateAddress() {
        return getAddressLine3();
    }
    public String default3UpdateAddress() {
        return getAddressLine4();
    }
    public String default4UpdateAddress() {
        return getPostalCode();
    }
    public String default5UpdateAddress() {
        return getCountry();
    }
    public Boolean default6UpdateAddress() {
        return getPlaceId() != null ? true: null;
    }

    //endregion

    //region > formattedAddress (property)

    public static class FormattedAddressEvent extends PropertyDomainEvent<String> {
        public FormattedAddressEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public FormattedAddressEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }
    private String formattedAddress;

    @javax.jdo.annotations.Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.FORMATTED_ADDRESS)
    @Property(
            domainEvent = FormattedAddressEvent.class
    )
    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(final String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    //endregion

    //region > placeId (property)

    public static class PlaceIdEvent extends PropertyDomainEvent<String> {
        public PlaceIdEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public PlaceIdEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String placeId;

    // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(
            domainEvent = PlaceIdEvent.class
    )
    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(final String placeId) {
        this.placeId = placeId;
    }
    //endregion

    //region > latLng (property)

    public static class LatLngEvent extends PropertyDomainEvent<String> {
        public LatLngEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public LatLngEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String latLng;

    // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(
            domainEvent = LatLngEvent.class
    )
    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(final String latLng) {
        this.latLng = latLng;
    }
    //endregion

    //region > addressComponents (property)
    
    public static class AddressComponentsEvent extends PropertyDomainEvent<PostalAddress> {
        public AddressComponentsEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public AddressComponentsEvent(
                final PostalAddress source,
                final Identifier identifier,
                final PostalAddress oldValue, final PostalAddress newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }
    private String addressComponents;

    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = AddressComponentsEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(multiLine = 10)
    public String getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(final String addressComponents) {
        this.addressComponents = addressComponents;
    }
    //endregion

    //region > updateGeocode (action)
    @MemberOrder(sequence = "1")
    public PostalAddress updateGeocode(
            @ParameterLayout(named = "Address")
            final String address) {

        lookupAndUpdateGeocode(true, address);

        return this;
    }

    public String default0UpdateGeocode() {
        return geocodingService.combine(
                GeocodingService.Encoding.NOT_ENCODED,
                getAddressLine1(), getAddressLine2(), getAddressLine3(), getAddressLine4(),
                getPostalCode(), getCountry());
    }
    //endregion
    
    //region > clearGeocode (action)
    public static class ClearGeocodeEvent extends ActionDomainEvent {
        public ClearGeocodeEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public ClearGeocodeEvent(final PostalAddress source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ClearGeocodeEvent(
                final PostalAddress source,
                final Identifier identifier,
                final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = ClearGeocodeEvent.class
    )
    public PostalAddress resetGeocode() {
        setFormattedAddress(null);
        setPlaceId(null);
        setLatLng(null);
        setAddressComponents(null);
        setGeocodeApiResponseAsJson(null);
        return this;
    }

    //endregion

    //region > downloadGeocode (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    public Clob downloadGeocode(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = ".json file name")
            String fileName) {
        fileName = fileName != null ? fileName : "postalAddress-" + getFormattedAddress();
        if(!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        return new Clob(encodeAsFilename(fileName), "text/plain", getGeocodeApiResponseAsJson());
    }

    private static String encodeAsFilename(final String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e); // will not happen, UTF-8 always supported
        }
    }
    //endregion

    //region > geocodeApiResponseAsJson (property)

    public static class GeocodeApiResponseAsJsonEvent extends PropertyDomainEvent<String> {
        public GeocodeApiResponseAsJsonEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public GeocodeApiResponseAsJsonEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }
    private String geocodeApiResponseAsJson;
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = GeocodeApiResponseAsJsonEvent.class,
            hidden = Where.EVERYWHERE
    )
    @PropertyLayout(
            multiLine = 9
    )
    public String getGeocodeApiResponseAsJson() {
        return geocodeApiResponseAsJson;
    }

    public void setGeocodeApiResponseAsJson(final String geocodeApiResponseAsJson) {
        this.geocodeApiResponseAsJson = geocodeApiResponseAsJson;
    }

    //endregion

    //region > Locatable API
    @Programmatic
    @Override
    public Location getLocation() {
        final String latLng = getLatLng();
        return latLng != null? Location.fromString(latLng.replace(",",";")): null;
    }
    //endregion

    //region > Predicates
    public static class Predicates {
        private Predicates(){}

        public static Predicate<PostalAddress> equalTo(
                final String placeId) {
            return new Predicate<PostalAddress>() {
                @Override
                public boolean apply(final PostalAddress input) {
                    return Objects.equals(placeId, input.getPlaceId());
                }
            };
        }
    }
    //endregion

    @Inject
    GeocodingService geocodingService;


}
