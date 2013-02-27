/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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


package com.danhaywood.isis.wicket.gmap3.applib;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

import org.apache.isis.applib.annotation.Value;

/**
 * Value type representing a location on a map.
 *
 */
@Value(semanticsProviderClass=LocationSemanticsProvider.class)
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static final Location DEFAULT_VALUE = new Location(51.4777479, 0); // Greenwich Royal Observatory
	
	/**
	 * Factory method
	 * 
	 * @see #toString()
	 */
	public static Location fromString(String encodedString) {
	    if(encodedString == null) {
	        return null;
	    }
	    final String[] split = encodedString.split(";");
	    if(split.length != 2) {
	        return null;
	    }
	    for (Locale locale : Arrays.asList(Locale.getDefault(), Locale.ENGLISH, Locale.FRENCH, Locale.JAPANESE, Locale.CHINESE)) {
	        try {
	            String latStr = split[0];
	            String longStr = split[1];

	            return parse(locale, latStr, longStr);
	        } catch (Exception e) {
	            continue;
	        }
        }
	    return null;
	}

    private static Location parse(Locale locale, String latStr, String longStr) throws ParseException {
        NumberFormat nf = getNumberFormat(locale);
        double latitude = (Double) nf.parse(latStr);
        double longitude = (Double) nf.parse(longStr);
        return new Location(latitude, longitude);
    }

    protected static Location parse(NumberFormat nf, String latStr, String longStr) throws ParseException {
        nf.setMinimumFractionDigits(6);
        nf.setMaximumFractionDigits(6);
        
        double latitude = (Double) nf.parse(latStr);
        double longitude = (Double) nf.parse(longStr);
        return new Location(latitude, longitude);
    }
	
	private double latitude;
	private double longitude;

	public Location() {
		this(DEFAULT_VALUE.getLatitude(), DEFAULT_VALUE.getLongitude());
	}
	
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}

	public Location north(double d) {
		return new Location(latitude-d, longitude);
	}

	public Location south(double d) {
		return new Location(latitude+d, longitude);
	}

	public Location west(double d) {
		return new Location(latitude, longitude-d);
	}
	
	public Location east(double d) {
		return new Location(latitude, longitude+d);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}
	
	/**
	 * See {@link #fromString(String)}
	 */
	@Override
	public String toString() {
        NumberFormat nf = getNumberFormat(Locale.getDefault());
        String locStr = nf.format(latitude) + ";" + nf.format(longitude);
        
        // and not:
        //    String locStr = String.format("%6f;%6f", 123.456, -30.415);
        // because it is a different string for Arabic !!!

        return locStr;
	}

    private static NumberFormat getNumberFormat(Locale locale) {
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMinimumFractionDigits(6);
        nf.setMaximumFractionDigits(6);
        return nf;
    }

	public static int typicalLength() {
		// latitude and longitude are each up to -NNN.NNNNNN (11 chars); plus 1 for the ';'
		return 11*2+1;
	}
}
