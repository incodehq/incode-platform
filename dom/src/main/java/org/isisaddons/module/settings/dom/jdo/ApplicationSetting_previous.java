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
import org.isisaddons.module.settings.dom.ApplicationSetting;

@Mixin
public class ApplicationSetting_previous extends AbstractService {

    //region > constructor
    private final ApplicationSetting current;

    public ApplicationSetting_previous(final ApplicationSetting current) {
        this.current = current;
    }
    //endregion

    public static class ActionDomainEvent
            extends SettingsModule.ActionDomainEvent<ApplicationSetting_previous> { }

    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-step-backward",
            cssClassFaPosition = ActionLayout.CssClassFaPosition.LEFT
    )
    public ApplicationSetting $$() {
        return firstMatch(
                new QueryDefault<>(ApplicationSettingJdo.class,
                        "findPrevious",
                        "key", current.getKey()));
    }

    public String disable$$() {
        return $$() == null? "No more settings": null;
    }


}
