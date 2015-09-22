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

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public enum CommunicationChannelType {

    POSTAL_ADDRESS(PostalAddress.class), 
    EMAIL_ADDRESS(EmailAddress.class), 
    PHONE_NUMBER(PhoneOrFaxNumber.class), 
    FAX_NUMBER(PhoneOrFaxNumber.class);

    private Class<? extends CommunicationChannel> cls;

    private CommunicationChannelType(final Class<? extends CommunicationChannel> cls) {
        this.cls = cls;
    }

    public String title() {
        return enumTitle(this);
    }
    
    public static List<CommunicationChannelType> matching(final Class<? extends CommunicationChannel> cls) {
        return Lists.newArrayList(Iterables.filter(Arrays.asList(values()), new Predicate<CommunicationChannelType>() {

            @Override
            public boolean apply(final CommunicationChannelType input) {
                return input.cls == cls;
            }
        }));
    }

    //region > helpers
    private static String enumTitle(final Enum<?> anEnum) {
        if(anEnum == null) {
            return null;
        }
        return Joiner.on(" ").join(Iterables.transform(Splitter.on("_").split(anEnum.toString()), LOWER_CASE_THEN_CAPITALIZE));
    }

    private static Function<String, String> LOWER_CASE_THEN_CAPITALIZE = new Function<String, String>() {
        @Override
        public String apply(final String input) {
            return input != null? capitalize(input.toLowerCase()): null;
        }
    };

    public static String capitalize(final String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    //endregion

}
