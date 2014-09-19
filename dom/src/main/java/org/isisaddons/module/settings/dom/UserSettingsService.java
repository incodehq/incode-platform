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

import java.util.List;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

public interface UserSettingsService {

    @MemberOrder(sequence="1")
    UserSetting find(@Named("User") String user, @Named("Key") String key);
    
    @MemberOrder(sequence="2")
    List<UserSetting> listAll();

    @MemberOrder(sequence="3")
    List<UserSetting> listAllFor(@Named("User") String user);
}
