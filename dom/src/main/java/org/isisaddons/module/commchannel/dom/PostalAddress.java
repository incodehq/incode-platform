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

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Predicate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.util.TitleBuffer;

import org.isisaddons.module.commchannel.CommChannelModule;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "PostalAddress_main_idx",
                members = { "address1", "postalCode", "city", "country" })
})
@DomainObject(
        editing = Editing.DISABLED
)
public class PostalAddress extends CommunicationChannel  {

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

    //region > title
    public String title() {
        return new TitleBuffer()
                .append(getAddress1())
                .append(", ", getCity())
                .append(" ", getPostalCode())
                .append(" ", isLegal() ? "[Legal]" : "")
                .append(getPurpose() == null ? "" : "[" + getPurpose().title() + "]")
                .toString();
    }
    //endregion

    //region > address1 (property)

    public static class Address1Event extends PropertyDomainEvent<String> {

        public Address1Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public Address1Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String address1;

    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = CommChannelModule.JdoColumnLength.ADDRESS_LINE
    )
    @Property(
            domainEvent = Address1Event.class,
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(named = "Address line 1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    //endregion

    //region > address2 (property)

    public static class Address2Event extends PropertyDomainEvent<String> {

        public Address2Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public Address2Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String address2;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = Address2Event.class,
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(named = "Address line 2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }


    //endregion

    //region > address3 (property)

    public static class Address3Event extends PropertyDomainEvent<String> {

        public Address3Event(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public Address3Event(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String address3;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.ADDRESS_LINE)
    @Property(
            domainEvent = Address3Event.class,
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(named = "Address line 3")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(final String address3) {
        this.address3 = address3;
    }

    //endregion

    //region > city (property)

    public static class CityEvent extends PropertyDomainEvent<String> {

        public CityEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }

        public CityEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }
    }

    private String city;

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.PROPER_NAME)
    @Property(
            domainEvent = CityEvent.class,
            optionality = Optionality.MANDATORY
    )
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    //endregion

    //region > state (property)

    public static class StateEvent extends PropertyDomainEvent<String> {
        public StateEvent(final PostalAddress source, final Identifier identifier) {
            super(source, identifier);
        }

        public StateEvent(
                final PostalAddress source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String state;

    // optional only because of superclass inheritance strategy=SUPERCLASS_TABLE
    @javax.jdo.annotations.Column(name = "stateId", allowsNull = "true")
    @Property(
            domainEvent = StateEvent.class
    )
    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
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
    @javax.jdo.annotations.Column(name = "countryId", allowsNull = "true")
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
            @ParameterLayout(named = "Address Line 1")
            final String address1,
            @ParameterLayout(named = "Address Line 2") @Parameter(optionality = Optionality.OPTIONAL)
            final String address2,
            @ParameterLayout(named = "Address Line 3") @Parameter(optionality = Optionality.OPTIONAL)
            final String address3,
            @ParameterLayout(named = "City")
            final String city,
            @ParameterLayout(named = "State") @Parameter(optionality = Optionality.OPTIONAL)
            final String state,
            @ParameterLayout(named = "Postal Code")
            final String postalCode,
            @ParameterLayout(named = "Country")
            final String country) {
        setAddress1(address1);
        setAddress2(address2);
        setAddress3(address3);
        setCity(city);
        setPostalCode(postalCode);
        setState(state);
        setCountry(country);

        return this;
    }

    public String default0ChangePostalAddress() {
        return getAddress1();
    }

    public String default1ChangePostalAddress() {
        return getAddress2();
    }

    public String default2ChangePostalAddress() {
        return getAddress3();
    }

    public String default3ChangePostalAddress() {
        return getCity();
    }

    public String default4ChangePostalAddress() {
        return getState();
    }

    public String default5ChangePostalAddress() {
        return getPostalCode();
    }

    public String default6ChangePostalAddress() {
        return getCountry();
    }

    //endregion

    //region > Predicates
    public static class Predicates {
        private Predicates(){}

        public static Predicate<PostalAddress> equalTo(
                final String address1,
                final String postalCode,
                final String city,
                final String country) {
            return new Predicate<PostalAddress>() {
                @Override
                public boolean apply(final PostalAddress input) {
                    return Objects.equals(address1, input.getAddress1()) &&
                            Objects.equals(postalCode, input.getPostalCode()) &&
                            Objects.equals(city, input.getCity()) &&
                            Objects.equals(country, input.getCountry());
                }
            };
        }
    }
    //endregion

}
