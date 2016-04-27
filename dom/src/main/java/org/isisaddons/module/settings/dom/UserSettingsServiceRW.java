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
package org.isisaddons.module.settings.dom;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.MemberOrder;

public interface UserSettingsServiceRW extends UserSettingsService {

    @MemberOrder(sequence="11")
    UserSetting newBoolean(
            String user, String name, String description, Boolean defaultValue);

    @MemberOrder(sequence="12")
    UserSetting newString(
            String user, String name, String description, String defaultValue);

    @MemberOrder(sequence="13")
    UserSetting newLocalDate(
            String user, String name, String description, LocalDate defaultValue);

    @MemberOrder(sequence="14")
    UserSetting newInt(
            String user, String name, String description, Integer defaultValue);

    @MemberOrder(sequence="15")
    UserSetting newLong(
            String user, String name, String description, Long defaultValue);
    
}
