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
package org.isisaddons.module.stringinterpolator.dom;

import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService
public class StringInterpolatorService {

    public static class Root {
        private final Object _this;
        private Map<String, String> properties;

        public Root(final Object context) {
            this._this = context;
        }

        public Object getThis() {
            return _this;
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        Root withProperties(Map<String, String> properties) {
            this.properties = properties;
            return this;
        }
    }

    private Map<String, String> properties;
    private boolean strict;

    @Programmatic
    @PostConstruct
    public void init(final Map<String, String> properties) {
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
        return interpolate(new Root(domainObject), template);
    }

    @Programmatic
    public String interpolate(final Root root, final String template) {
        final Root rootNoNull = root != null ? root : new Root(null);
        return new StringInterpolatorHelper(template, rootNoNull.withProperties(properties), strict).interpolate();
    }

}


