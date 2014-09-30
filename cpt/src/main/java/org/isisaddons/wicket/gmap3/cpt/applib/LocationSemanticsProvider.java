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
package org.isisaddons.wicket.gmap3.cpt.applib;

import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;
import org.apache.isis.applib.profiles.Localization;

/**
 * For internal use; allows Isis to parse etc.
 */
public class LocationSemanticsProvider implements ValueSemanticsProvider<Location> {

	public DefaultsProvider<Location> getDefaultsProvider() {
		return new DefaultsProvider<Location>() {

			public Location getDefaultValue() {
				return Location.DEFAULT_VALUE;
			}
		};
	}

	public EncoderDecoder<Location> getEncoderDecoder() {
		return new EncoderDecoder<Location>() {

			public Location fromEncodedString(String encodedString) {
				return Location.fromString(encodedString);
			}

			public String toEncodedString(Location locationToEncode) {
				return locationToEncode.toString();
			}
		};
	}

	public Parser<Location> getParser() {
		return new Parser<Location>() {

			public String displayTitleOf(Location location, String usingMask) {
				return location.toString();
			}

			public String parseableTitleOf(Location existing) {
				return existing.toString();
			}

			public int typicalLength() {
				return Location.typicalLength();
			}

            public String displayTitleOf(Location location, Localization arg1) {
                return location.toString();
            }

            public Location parseTextEntry(Object object, String entry, Localization arg2) {
                return Location.fromString(entry);
            }
		};
	}

	public boolean isEqualByContent() {
		return true;
	}

	public boolean isImmutable() {
		return true;
	}

}
