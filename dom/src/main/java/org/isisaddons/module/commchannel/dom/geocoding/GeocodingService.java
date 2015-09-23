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
package org.isisaddons.module.commchannel.dom.geocoding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

/**
 * Adapted from <a href="http://stackoverflow.com/a/9600268/56880">this stackoverflow answer</a>.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class GeocodingService {

    private static final String DEFAULT_PROTOCOL = "http";
    private static final int DEFAULT_TIMEOUT_SECONDS = 5;

    private String apiKey;
    private String regionBias;
    private String protocol = DEFAULT_PROTOCOL;
    private int timeout = DEFAULT_TIMEOUT_SECONDS;

    @PostConstruct
    public void init() {
        final String prefix = GeocodingService.class.getName();
        protocol = container.getProperty(prefix + ".protocol", DEFAULT_PROTOCOL);
        apiKey = container.getProperty(prefix + ".apiKey");
        timeout = parseInt(container.getProperty(prefix + ".timeout"), DEFAULT_TIMEOUT_SECONDS);
        regionBias = encoded(container.getProperty(prefix + ".regionBias"));
    }

    @Programmatic
    public GeocodedAddress lookup(final String... addressElements) {

        final RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout * 1000)
                .setConnectTimeout(timeout * 1000)
                .build();

        final CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .useSystemProperties()
                .build();

        try {
            final String uri = buildUri(addressElements);
            final HttpGet httpGet = new HttpGet(uri);
            final CloseableHttpResponse response = httpClient.execute(httpGet);

            try {
                HttpEntity entity = response.getEntity();
                final String json = EntityUtils.toString(entity, "UTF-8");

                return asGeocodedAddress(json);
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Programmatic
    public GeocodedAddress asGeocodedAddress(final String jsonResponse) {
        final GeocodeApiResponse geocodeApiResponse =
                new Gson().fromJson(jsonResponse, GeocodeApiResponse.class);
        return new GeocodedAddress(geocodeApiResponse, jsonResponse);
    }

    //region > helpers
    private String buildUri(final String[] addressElements) throws UnsupportedEncodingException {

        final StringBuilder sb = new StringBuilder();
        sb.append(protocol).append("://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=");

        boolean first = true;
        for (String addressElement : addressElements) {
            if(!Strings.isNullOrEmpty(addressElement)) {
                if(!first) {
                    sb.append(",");
                }
                first = false;
                sb.append(encoded(addressElement));
            }
        }
        if(apiKey != null) {
            sb.append("&apiKey=").append(apiKey);
        }
        if (regionBias != null) {
            sb.append("&region=").append(regionBias);
        }
        return sb.toString();
    }

    private static int parseInt(final String str, final int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static String encoded(final String str)  {
        try {
            return str != null? URLEncoder.encode(str.replace(' ', '+'), "UTF-8"): null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to UTF-8 encode " + str, e);
        }
    }
    //endregion

    @Inject
    DomainObjectContainer container;
}
