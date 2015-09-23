/*
 *  Copyright 2013~2015 Dan Haywood
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
package org.isisaddons.module.commchannel;

import java.util.List;
import org.apache.isis.applib.Identifier;

public final class CommChannelModule {

    //region > constants

    public static class JdoColumnLength {


        private JdoColumnLength(){}

        public final static int TYPE_ENUM = 30;
        public final static int PROPER_NAME = 50;
        public final static int REFERENCE = 24;

        //http://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
        public static final int EMAIL_ADDRESS = 254;

        public final static int PHONE_NUMBER = 20;

        public static final int FORMATTED_ADDRESS = 254;
        public static final int POSTAL_CODE = 12;

    }

    public static class Regex {
        public static final String PHONE_NUMBER = "[+]?[0-9 -]*";

        private Regex(){}
        public static final String EMAIL_ADDRESS = "[^@ ]*@{1}[^@ ]*[.]+[^@ ]*";
    }

    //endregion

    private CommChannelModule(){}

    //region > event classes
    public abstract static class ActionDomainEvent<S> extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {
        public ActionDomainEvent(final S source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final S source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final S source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    public abstract static class CollectionDomainEvent<S,T> extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {
        public CollectionDomainEvent(final S source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final S source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public abstract static class PropertyDomainEvent<S,T> extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {
        public PropertyDomainEvent(final S source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final S source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }
    //endregion
}
