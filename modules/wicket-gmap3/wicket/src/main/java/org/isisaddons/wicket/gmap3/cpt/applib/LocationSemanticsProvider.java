package org.isisaddons.wicket.gmap3.cpt.applib;

import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;

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

            public String displayTitleOf(Location location) {
                return location.toString();
            }

            public Location parseTextEntry(Object object, String entry) {
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
