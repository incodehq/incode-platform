package org.incode.module.commchannel.dom.api.geocoding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

public class GeocodingServiceTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    GeocodingService geocodingService;
    @Mock
    DomainObjectContainer mockContainer;

    public static class LookupTest extends GeocodingServiceTest {

        @Before
        public void setUp() throws Exception {
            geocodingService = new GeocodingService();
            geocodingService.container = mockContainer;
        }

        @Test
        public void normal_mode() throws Exception {

            assumeThat(isInternetReachable(), is(true));

            // when
            final String address = geocodingService.combine(GeocodingService.Encoding.ENCODED, "45 High Street, Oxford", null, "UK");
            final GeocodedAddress geocodedAddress = geocodingService.lookup(address);

            // then
            assertThat(geocodedAddress).isNotNull();
            assertThat(geocodedAddress.getStatus()).isEqualTo(GeocodeApiResponse.Status.OK);
            assertThat(geocodedAddress.getFormattedAddress()).startsWith("45 High St, Oxford, Oxfordshire OX1");
            assertThat(geocodedAddress.getFormattedAddress()).endsWith("UK");
            assertThat(geocodedAddress.getPlaceId()).isNotNull();
            assertThat(geocodedAddress.getPostalCode()).startsWith("OX1");
            assertThat(geocodedAddress.getCountry()).isEqualTo("United Kingdom");
        }

        @Test
        public void demo_mode() throws Exception {

            // allowing
            context.checking(new Expectations() {{
                allowing(mockContainer).getProperty(GeocodingService.class.getName() + ".demo");
                will(returnValue("true"));
                allowing(mockContainer);
            }});

            // given
            geocodingService.init();

            // when
            final String address = geocodingService.combine(GeocodingService.Encoding.ENCODED, "45 High Street, Oxford", null, "UK");
            final GeocodedAddress geocodedAddress = geocodingService.lookup(address);

            // then
            assertThat(geocodedAddress).isNotNull();
            assertThat(geocodedAddress.getStatus()).isEqualTo(GeocodeApiResponse.Status.OK);
            assertThat(geocodedAddress.getFormattedAddress()).isEqualTo("45 High St, Oxford, Oxfordshire OX1, UK");
            assertThat(geocodedAddress.getPlaceId()).isEqualTo("Eic0NSBIaWdoIFN0LCBPeGZvcmQsIE94Zm9yZHNoaXJlIE9YMSwgVUs");
            assertThat(geocodedAddress.getPostalCode()).isEqualTo("OX1");
            assertThat(geocodedAddress.getCountry()).isEqualTo("United Kingdom");
        }
    }


    /**
     * Tries to retrieve some content, 1 second timeout.
     */
    private static boolean isInternetReachable()
    {
        try {
            final URL url = new URL("http://www.google.com");
            final HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(1000);
            urlConnect.getContent();
            urlConnect.disconnect();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }


}