package org.isisaddons.module.commchannel.dom.geocoding;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeocodingServiceTest {

    @Test
    public void testLookup() throws Exception {

        final GeocodingService geocodingService = new GeocodingService();
        final GeocodedAddress geocodedAddress = geocodingService.lookup("45 High Street, Oxford", null, "UK");

        assertThat(geocodedAddress).isNotNull();
        assertThat(geocodedAddress.getStatus()).isEqualTo(GeocodeApiResponse.Status.OK);
        assertThat(geocodedAddress.getFormattedAddress()).isEqualTo("45 High St, Oxford, Oxfordshire OX1, UK");
        assertThat(geocodedAddress.getPlaceId()).isEqualTo("Eic0NSBIaWdoIFN0LCBPeGZvcmQsIE94Zm9yZHNoaXJlIE9YMSwgVUs");
        assertThat(geocodedAddress.getPostalCode()).isEqualTo("OX1");
        assertThat(geocodedAddress.getCountry()).isEqualTo("United Kingdom");
    }
}