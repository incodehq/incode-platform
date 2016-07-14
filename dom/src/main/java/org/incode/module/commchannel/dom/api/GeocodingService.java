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
package org.incode.module.commchannel.dom.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.config.ConfigurationService;

/**
 * Adapted from <a href="http://stackoverflow.com/a/9600268/56880">this stackoverflow answer</a>.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class GeocodingService {

    private static final String DEFAULT_PROTOCOL = "http";
    private static final boolean DEFAULT_DEMO = false;
    private static final int DEFAULT_TIMEOUT_SECONDS = 5;

    private String apiKey;
    private String regionBias;
    private String protocol = DEFAULT_PROTOCOL;
    private int timeout = DEFAULT_TIMEOUT_SECONDS;
    private boolean demo;

    @PostConstruct
    public void init() {
        final String prefix = GeocodingService.class.getCanonicalName();
        protocol = configurationService.getProperty(prefix + ".protocol", DEFAULT_PROTOCOL);
        apiKey = configurationService.getProperty(prefix + ".apiKey");
        demo = parseBoolean(configurationService.getProperty(prefix + ".demo"), DEFAULT_DEMO);
        timeout = parseInt(configurationService.getProperty(prefix + ".timeout"), DEFAULT_TIMEOUT_SECONDS);
        regionBias = encoded(configurationService.getProperty(prefix + ".regionBias"));
    }

    @Programmatic
    public GeocodedAddress lookup(final String address) {

        if(demo) {
            return demoResponse();
        }

        final RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout * 1000)
                .setConnectTimeout(timeout * 1000)
                .build();

        final CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .useSystemProperties()
                .build();

        try {
            final String uri = buildUri(address);
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

    public enum Encoding {
        ENCODED,
        NOT_ENCODED
    }

    @Programmatic
    public String combine(final Encoding encoding, final String... parts) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String addressElement : parts) {
            if(!Strings.isNullOrEmpty(addressElement)) {
                if(!first) {
                    sb.append(",");
                    if(encoding == Encoding.NOT_ENCODED) {
                        sb.append(" ");
                    }
                }
                first = false;
                sb.append(encoding == Encoding.ENCODED? encoded(addressElement): addressElement);
            }
        }
        return sb.toString();
    }


    //region > helpers
    private String buildUri(final String address) throws UnsupportedEncodingException {

        final StringBuilder sb = new StringBuilder();
        sb.append(protocol)
          .append("://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=")
          .append(address);

        if(apiKey != null) {
            sb.append("&apiKey=").append(apiKey);
        }
        if (regionBias != null) {
            sb.append("&region=").append(regionBias);
        }
        return sb.toString();
    }

    private GeocodedAddress demoResponse() {
        final URL resource = Resources
                .getResource(getClass(), "postalAddress-45+High+St%2C+Oxford%2C+Oxfordshire+OX1%2C+UK.json");
        final String json;
        try {
            json = Resources.toString(resource, Charsets.UTF_8);
            return asGeocodedAddress(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static int parseInt(final String str, final int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static boolean parseBoolean(final String str, final boolean defaultValue) {
        try {
            return Boolean.parseBoolean(str);
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
    ConfigurationService configurationService;
}
