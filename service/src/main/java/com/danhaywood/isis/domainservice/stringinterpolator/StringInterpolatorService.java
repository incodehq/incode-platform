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

import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Sets;

import org.apache.isis.applib.annotation.Programmatic;

public class StringInterpolatorService {

    private Map<String, String> properties;
    private boolean strict;
    
    @Programmatic
    @PostConstruct
    public void init(final Map<String,String> properties) {
        this.properties = properties;
    }

    /**
     * To assist in unit testing expressions.
     */
    @Programmatic
    public boolean isStrict() {
        return strict;
    }
    @Programmatic
    public StringInterpolatorService withStrict(boolean strict) {
        this.strict = strict;
        return this;
    }
    

    @Programmatic
    public String interpolate(final Object domainObject, final String template) {
        return new StringInterpolatorHelper(template, properties, domainObject, strict).interpolate();
    }
   
}


