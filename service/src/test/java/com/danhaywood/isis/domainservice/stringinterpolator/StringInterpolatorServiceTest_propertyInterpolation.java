/*
 *  Copyright 2013~2014 Dan Haywood
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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;

public class StringInterpolatorServiceTest_propertyInterpolation {

    private StringInterpolatorService service;
    private Map<String, String> properties;
    
    @Before
    public void setUp() throws Exception {
        service = new StringInterpolatorService();
        
        properties = ImmutableMap.of(
                "isis.asf.website", "http://isis.apache.org", 
                "isis.asf.website.noScheme", "isis.apache.org", 
                "isis.asf.website.documentationPage", "documentation.html", 
                "isis.github.site", "http://github.com/isis/isis");
                
        service.init(properties);
    }
    
    @Test
    public void simple() throws Exception {
        String interpolated = service.interpolate(null, "${properties['isis.asf.website']}");
        assertThat(interpolated, is("http://isis.apache.org"));
    }

    @Test
    public void suffix() throws Exception {
        String interpolated = service.interpolate(null, "${properties['isis.asf.website']}/documentation.html");
        assertThat(interpolated, is("http://isis.apache.org/documentation.html"));
    }


    @Test
    public void prefix() throws Exception {
        String interpolated = service.interpolate(null, "http://${properties['isis.asf.website.noScheme']}");
        assertThat(interpolated, is("http://isis.apache.org"));
    }

    @Test
    public void multiple() throws Exception {
        String interpolated = service.interpolate(null, "${properties['isis.asf.website']}/${properties['isis.asf.website.documentationPage']}");
        assertThat(interpolated, is("http://isis.apache.org/documentation.html"));
    }

    @Test
    public void complex() throws Exception {
        String interpolated = service.interpolate(null, "http://${properties['isis.asf.website.noScheme']}/${properties['isis.asf.website.documentationPage']}#Core");
        assertThat(interpolated, is("http://isis.apache.org/documentation.html#Core"));
    }


}
