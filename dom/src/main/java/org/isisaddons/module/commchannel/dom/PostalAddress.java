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
package org.isisaddons.module.commchannel.dom;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.value.Clob;

import org.isisaddons.module.commchannel.CommChannelModule;
import org.isisaddons.module.commchannel.dom.geocoding.GeocodeApiResponse;
import org.isisaddons.module.commchannel.dom.geocoding.GeocodedAddress;
import org.isisaddons.module.commchannel.dom.geocoding.GeocodingService;
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
public class PostalAddress extends CommunicationChannel<PostalAddress>  {


    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<PostalAddress, T> {
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
    @Title
    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(final String formattedAddress) {
        this.formattedAddress = formattedAddress;
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
    @javax.jdo.annotations.Column(allowsNull = "true")
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

    //region > changePostalAddress (action)

    public static class ChangePostalAddressEvent extends ActionDomainEvent {
        public ChangePostalAddressEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public ChangePostalAddressEvent(
                final PostalAddress source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ChangePostalAddressEvent(
                final PostalAddress source,
                final Identifier identifier,
                final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = ChangePostalAddressEvent.class
    )
    public PostalAddress changePostalAddress(
            @ParameterLayout(named = "Address")
            final String address) {

        final GeocodedAddress geocodedAddress = geocodingService.lookup(address);

        if(geocodedAddress == null || geocodedAddress.getStatus() != GeocodeApiResponse.Status.OK) {
            container.warnUser(
                    TranslatableString.tr("Could not find address '{address}'", "address", address),
                    PostalAddressRepository.class, "newPostal");
            return this;
        }

        updateFrom(geocodedAddress);

        return this;
    }

    public String default0ChangePostalAddress() {
        return getFormattedAddress();
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


    //region > internal helpers (updateFrom)
    void updateFrom(final GeocodedAddress geocodedAddress) {
        setFormattedAddress(geocodedAddress.getFormattedAddress());
        setGeocodeApiResponseAsJson(geocodedAddress.getApiResponseAsJson());
        setPlaceId(geocodedAddress.getPlaceId());
        setLatLng(geocodedAddress.getLatLng());
        setPostalCode(geocodedAddress.getPostalCode());
        setCountry(geocodedAddress.getCountry());
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
