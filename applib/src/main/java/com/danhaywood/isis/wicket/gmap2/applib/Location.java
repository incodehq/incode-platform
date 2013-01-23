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


package com.danhaywood.isis.wicket.gmap2.applib;

import java.io.Serializable;

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
		final String[] split = encodedString.split(";");
		try {
			double latitude = Double.parseDouble(split[0]);
			double longitude = Double.parseDouble(split[1]);
			return new Location(latitude, longitude);
		} catch (Exception e) {
			return null;
		}
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
		return String.format("%6f;%6f", latitude, longitude);
	}

	public static int typicalLength() {
		// latitude and longitude are each up to -NNN.NNNNNN (11 chars); plus 1 for the ';'
		return 11*2+1;
	}
}
