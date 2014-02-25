# isis-domainservice-stringinterpolator #

[![Build Status](https://travis-ci.org/danhaywood/isis-domainservice-stringinterpolator.png?branch=master)](https://travis-ci.org/danhaywood/isis-domainservice-stringinterpolator)

The StringInterpolatorService, intended for use within [Apache Isis](http://isis.apache.org), will interpolate string templates with either Isis system properties or values obtained from a domain object (possibly walking relationships).

One use case for this service (and the original use case) is in building URLs based on an object's state, parameterized by environment (prod/test/dev etc).  These URLs could be anything; in the original use case they were to a reporting service:

    ${property['reportServerBase']}/ReportServer/Pages/ReportViewer.aspx?/Estatio/Invoices&dueDate=${dueDate}&propertyId=${this.property.id}

where the context for the evaluation of the URL (`this`) is a domain object that has a `property` field, which in turn has an `id` field:

When initialized by Isis, the Isis system properties are exposed as the `properties` map, while the target object is exposed as the `this` object.

## API ##

The main API is:
 
    public class StringInterpolatorService {

        // called by Isis (which passes in all Isis properties)
        @PostConstruct
        public void init(final Map<String,String> properties) { ... }

        // public API
        public String interpolate(Object domainObject, String template) { ... }
    }

## Usage ##

The interpolation replaces each occurrence of `${...}` with its interpolated value.  The expression in within the braces is interpreted using [OGNL](http://commons.apache.org/proper/commons-ognl/).

The examples below are adapted from the service's unit tests.

#### Property Interpolation

These tests only interpolate the Isis properties, and so pass in `null` for the object context:

        private StringInterpolatorService service;
        private Map<String, String> properties;
        
        @Before
        public void setUp() throws Exception {
            service = new StringInterpolatorService();
            
            properties = ImmutableMap.of(
                    "isis.asf.website.noScheme", "isis.apache.org", 
                    "isis.asf.website.documentationPage", "documentation.html");
                    
            service.init(properties);
        }
        
        @Test
        public void complex() throws Exception {
            String interpolated = service.interpolate(
                null, "http://${properties['isis.asf.website.noScheme']}/${properties['isis.asf.website.documentationPage']}#Core");
            assertThat(interpolated, is("http://isis.apache.org/documentation.html#Core"));
        }
    }

#### Object graph interpolation

These tests interpolate an instance of the `Customer` class, that in turn has relationships to the `Address` class:

    static class Customer {
        private String firstName;
        private String lastName;
        private Address address;
        private Address billingAddress;
        // getters and setters omitted
    }
    static class Address {
        private int houseNumber;
        private String town;
        private String postalCode;
        // getters and setters omitted
    }
    
    @Before
    public void setUp() throws Exception {
        service = new StringInterpolatorService();
        
        customer = new Customer();
        customer.setFirstName("Fred");
        
        Address address = new Address();
        address.setHouseNumber(34);
        address.setPostalCode("AB12 34DF");
        customer.setAddress(address);
                
        service.init(properties);
    }
    
    @Test
    public void simple() throws Exception {
        String interpolated = service.interpolate(customer, "${this.firstName}");
        assertThat(interpolated, is("Fred"));
    }

    @Test
    public void walkGraph() throws Exception {
        String interpolated = service.interpolate(customer, "${this.address.houseNumber}");
        assertThat(interpolated, is("34"));
    }
    
    @Test
    public void conditionals() throws Exception {
        String interpolated = service.withStrict(true).interpolate(customer, 
                "${this.firstName}"
                + "${this.lastName != null? this.lastName : ''}"
                + "${this.address != null? ' lives at ' + this.address.houseNumber + ', ' + this.address.postalCode: ''}"
                + "${this.billingAddress != null? ' , bill to ' + this.billingAddress.postTown : ''}");
        assertThat(interpolated, is("Fred lives at 34, AB12 34DF"));
    }

By default, any expression that cannot be parsed or would generate an exception (eg null pointer exception) is instead returned unchanged in the interpolated string.

The service also provides a "strict" mode, which is useful for testing expressions:

    StringInterpolatorService service = new StringInterpolatorService().withStrict(true);
    
If enabled, then an exception is thrown instead.


## Maven Configuration

In the `pom.xml` for your "dom" module, add:
    
    <dependency>
        <groupId>com.danhaywood.isis.domainservice</groupId>
        <artifactId>danhaywood-isis-domainservice-stringinterpolator</artifactId>
        <version>x.y.z</version>
    </dependency>

where `x.y.z` currently is 1.4.0-SNAPSHOT (though the plan is to release this code into the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-domainservice-stringinterpolator)).

## Registering the service

In the `WEB-INF\isis.properties` file, add:

    isis.services = ...,\
                    com.danhaywood.isis.domainservice.stringinterpolator.StringInterpolatorService,\
                    ...



## Legal Stuff ##
 
### License ###

    Copyright 2014 Dan Haywood

    Licensed under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.


### Dependencies

