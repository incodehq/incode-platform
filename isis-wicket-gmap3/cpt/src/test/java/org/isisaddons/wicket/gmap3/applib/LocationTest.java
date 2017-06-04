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
package org.isisaddons.wicket.gmap3.applib;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class LocationTest {

    private Locale currentLocale;

    @Before
    public void setUp() throws Exception {
        currentLocale = Locale.getDefault();
    }
    
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(currentLocale);
    }

    @Test
    public void testFromString() {
        for(Locale locale: allLocales() ) {
            Locale.setDefault(locale);

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(6);
            nf.setMaximumFractionDigits(6);
            String locStr = nf.format(123.456) + ";" + nf.format(-30.415);

            // and not:
            //    String locStr2 = String.format("%6f;%6f", 123.456, -30.415);
            // because it is a different string for Arabic !!!
            //    String displayLanguage = locale.getDisplayLanguage();
            //    System.out.printf("%3s : %-20s : %12s : %12s : %s\n", locale.getLanguage(), displayLanguage, locStr, locStr2, (locStr.equals(locStr2)? "": "DIFFERENT!!!"));

            final Location location = Location.fromString(locStr);
            assertThat(location.getLatitude(), is(closeTo(123.456, 0.0001)));
            assertThat(location.getLongitude(), is(closeTo(-30.415, 0.0001)));
        }
    }

    @Test
    public void testFromString_ifForeign() {
        Locale.setDefault(Locale.ENGLISH);
        final Location location = Location.fromString("123,456;-30,415");
        assertThat(location.getLatitude(), is(closeTo(123.456, 0.0001)));
        assertThat(location.getLongitude(), is(closeTo(-30.415, 0.0001)));
    }

    @Test
    public void testFromString_invalid() {
        assertThat(Location.fromString(null), is(nullValue()));
        assertThat(Location.fromString("123"), is(nullValue()));
        assertThat(Location.fromString("123;456;789"), is(nullValue()));
        assertThat(Location.fromString("123.123;123.a23"), is(nullValue()));
    }

    @Test
    public void testToString() {
		final NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(6);
		nf.setMaximumFractionDigits(6);

		final String expectedResult = nf.format(123.456) + ";" + nf.format(-30.415);
        final Location location = new Location(123.456, -30.415);
        final String string = location.toString();

        assertThat(string, is(expectedResult));
    }


    
    private static Iterable<Locale> allLocales() {
        String[] isoCountries = Locale.getISOLanguages();
        List<String> asList = Arrays.asList(isoCountries);
        Iterable<Locale> locales = Iterables.transform(asList, new Function<String, Locale>(){

            public Locale apply(String input) {
                return new Locale(input);
            }});
        return locales;
    }


}
