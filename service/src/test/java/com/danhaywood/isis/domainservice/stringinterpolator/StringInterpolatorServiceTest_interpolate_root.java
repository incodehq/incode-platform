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

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringInterpolatorServiceTest_interpolate_root {

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
        final class CustomRoot extends StringInterpolatorService.Root {

            private Customer customer;

            public CustomRoot(Object context, Customer customer) {
                super(context);
                this.customer = customer;
            }
            
            public Customer getCustomer() {
                return customer;
            }
        }
        String interpolated = service.interpolate(new CustomRoot(null, customer), "${customer.firstName}");
        assertThat(interpolated, is("Fred"));
    }

}
