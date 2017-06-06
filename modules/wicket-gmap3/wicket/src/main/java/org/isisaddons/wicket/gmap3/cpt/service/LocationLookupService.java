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
package org.isisaddons.wicket.gmap3.cpt.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.wicket.gmap3.cpt.applib.Location;

@DomainService
public class LocationLookupService {

    // Greenwich Royal Observatory (unused)
    private static final Location DEFAULT_VALUE = new Location(51.4777479, 0.0d);

    private static final String BASEURL = "http://maps.googleapis.com/maps/api/geocode/";
    private static final String MODE = "xml";
    private static final int TIMEOUT_SECONDS = 5;

    @Programmatic
    public Location lookup(final String description) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT_SECONDS * 1000)
                .setConnectTimeout(TIMEOUT_SECONDS * 1000)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .useSystemProperties()
                .build();

        try {
            String uri = BASEURL + MODE + "?address=" + URLEncoder.encode(description, "UTF-8") + "&sensor=false";
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            try {
                HttpEntity entity = response.getEntity();
                return extractLocation(EntityUtils.toString(entity, "UTF-8"));
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            return null;
        }
    }

    private Location extractLocation(final String xml) throws JDOMException, IOException {
        final SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xml));
        Element root = doc.getRootElement();
        String lat = root.getChild("result").getChild("geometry").getChild("location").getChildTextTrim("lat");
        String lon = root.getChild("result").getChild("geometry").getChild("location").getChildTextTrim("lng");
        return Location.fromString(lat + ";" + lon);
    }
}
