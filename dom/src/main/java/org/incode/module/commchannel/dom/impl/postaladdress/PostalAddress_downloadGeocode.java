/*
 *
 *  Copyright 2015 incode.org
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
package org.incode.module.commchannel.dom.impl.postaladdress;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Clob;

@Mixin
public class PostalAddress_downloadGeocode {

    //region > constructor
    private final PostalAddress postalAddress;
    public PostalAddress_downloadGeocode(final PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }
    //endregion

    public static class Event extends PostalAddress.ActionDomainEvent<PostalAddress_downloadGeocode> { }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = Event.class
    )
    public Clob __(
            @ParameterLayout(named = ".json file name")
            final String fileName) {
        return new Clob(encodeAsFilename(fileName), "text/plain", this.postalAddress.getGeocodeApiResponseAsJson());
    }

    private String default0__() {
        return "postalAddress-" + this.postalAddress.getFormattedAddress() + ".json";
    }

    public String validate0__(final String fileName) {
        return !fileName.endsWith(".json")? "Must end with '.json'": null;
    }

    private static String encodeAsFilename(final String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e); // will not happen, UTF-8 always supported
        }
    }

}
