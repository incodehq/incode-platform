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


package com.danhaywood.isis.wicket.gmap3.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.danhaywood.isis.wicket.gmap3.applib.Location;

public class LocationLookupServiceTest {

	private LocationLookupService locationLookupService;
	
	@Before
	public void setup() {
		locationLookupService = new LocationLookupService();
	}
	
	@Test
	public void whenValid() {
		Location location = locationLookupService.lookup("10 Downing Street,London,UK");
		assertThat(location, is(not(nullValue())));
		assertThat(location.getLatitude(), is(51.5033549));
		assertThat(location.getLongitude(), is(-0.1275645));
	}

	@Test
	public void whenInvalid() {
		Location location = locationLookupService.lookup("$%$%^Y%^fgnsdlfk glfg");
		assertThat(location, is(nullValue()));
	}

}
