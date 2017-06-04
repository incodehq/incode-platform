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
package org.isisaddons.module.settings.dom.jdo;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.settings.dom.UserSetting;

@Mixin
public class UserSetting_next extends AbstractService {

    public static class ActionDomainEvent
            extends SettingsModule.ActionDomainEvent<UserSetting_next> { }

    //region > constructors

    private final UserSetting current;
    public UserSetting_next(final UserSetting current) {
        this.current = current;
    }

    //endregion


    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-step-forward",
            cssClassFaPosition = ActionLayout.CssClassFaPosition.RIGHT
    )
    public UserSetting $$() {
        return firstMatch(
                new QueryDefault<>(UserSettingJdo.class,
                        "findNext",
                        "user", current.getUser(),
                        "key", current.getKey()));
    }

    public String disable$$() {
        return $$() == null? "No more settings": null;
    }


}
