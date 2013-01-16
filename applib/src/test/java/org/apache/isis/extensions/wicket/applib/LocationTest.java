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


package org.apache.isis.extensions.wicket.applib;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.isis.extensions.wicket.view.gmap2.applib.Location;
import org.junit.Test;

public class LocationTest {


	@Test
	public void testFromString() {
		final Location location = Location.fromString("123.456;-30.415");
		assertThat(location.getLatitude(), is(closeTo(123.456, 0.0001)));
		assertThat(location.getLongitude(), is(closeTo(-30.415, 0.0001)));
	}

	@Test
	public void testToString() {
		final Location location = new Location(123.456, -30.415);
		final String string = location.toString();
		assertThat(string, is("123.456000;-30.415000"));
	}

}
