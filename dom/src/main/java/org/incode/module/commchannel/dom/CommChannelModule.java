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
package org.incode.module.commchannel.dom;

public final class CommChannelModule {

    public static class JdoColumnLength {

        private JdoColumnLength(){}

        public static final int TYPE_ENUM = 30;
        public static final int PURPOSE = 30;

        /**
         * http://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
         */
        public static final int EMAIL_ADDRESS = 254;
        public static final int PHONE_NUMBER = 20;

        public static final int ADDRESS_LINE = 50;
        public static final int POSTAL_CODE = 12;
        public static final int COUNTRY = 30;

        public static final int FORMATTED_ADDRESS = 254;

        public static final int OBJECT_TYPE = 255;
        public static final int OBJECT_IDENTIFIER = 255;

    }

    public static class Regex {
        public static final String PHONE_NUMBER = "[+]?[0-9 -]*";

        private Regex(){}
        public static final String EMAIL_ADDRESS = "[^@ ]*@{1}[^@ ]*[.]+[^@ ]*";
    }


    private CommChannelModule(){}

    public abstract static class ActionDomainEvent<S>
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {}

    public abstract static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {}

    public abstract static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {}

}
