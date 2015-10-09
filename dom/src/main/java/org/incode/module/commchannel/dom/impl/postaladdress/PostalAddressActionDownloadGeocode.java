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
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Clob;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class PostalAddressActionDownloadGeocode {


    public static class DownloadGeocodeEvent
            extends PostalAddress.ActionDomainEvent<PostalAddressActionDownloadGeocode> { }
    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = DownloadGeocodeEvent.class
    )
    public Clob downloadGeocode(
            final PostalAddress postalAddress,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = ".json file name")
            String fileName) {
        fileName = fileName != null ? fileName : "postalAddress-" + postalAddress.getFormattedAddress();
        if(!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        return new Clob(encodeAsFilename(fileName), "text/plain", postalAddress.getGeocodeApiResponseAsJson());
    }

    private static String encodeAsFilename(final String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e); // will not happen, UTF-8 always supported
        }
    }

}
