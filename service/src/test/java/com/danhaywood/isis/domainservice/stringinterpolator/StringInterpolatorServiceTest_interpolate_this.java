/*
 *  Copyright 2014 Dan Haywood
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
package com.danhaywood.isis.domainservice.stringinterpolator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringInterpolatorServiceTest_interpolate_this {

    private StringInterpolatorService service;
    private Map<String, String> properties;
    private Customer customer;
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
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
    public void nullField() throws Exception {
        String interpolated = service.interpolate(customer, "${this.lastName}");
        assertThat(interpolated, is(""));
    }
    
    @Test
    public void walkGraph() throws Exception {
        String interpolated = service.interpolate(customer, "${this.address.houseNumber}");
        assertThat(interpolated, is("34"));
    }
    
    @Test
    public void walkGraphToNull() throws Exception {
        String interpolated = service.interpolate(customer, "${this.address.town}");
        assertThat(interpolated, is(""));
    }
    
    @Test
    public void walkGraphThroughNull() throws Exception {
        String interpolated = service.interpolate(customer, "${this.billingAddress.town}");
        assertThat(interpolated, is("${this.billingAddress.town}"));
    }

    @Test
    public void walkGraphThroughNullStrictMode() throws Exception {
        expectedException.expectMessage("could not parse: this.billingAddress.town");
        service.withStrict(true).interpolate(customer, "${this.billingAddress.town}");
    }

    @Test
    public void conditionals() throws Exception {
        String interpolated = service.interpolate(customer, 
                "${this.firstName}"
                + "${this.lastName != null? this.lastName : ''}"
                + "${this.address != null? ' lives at ' + this.address.houseNumber + ', ' + this.address.postalCode: ''}"
                + "${this.billingAddress != null? ' , bill to ' + this.billingAddress.postTown : ''}");
        assertThat(interpolated, is("Fred lives at 34, AB12 34DF"));
    }
}
