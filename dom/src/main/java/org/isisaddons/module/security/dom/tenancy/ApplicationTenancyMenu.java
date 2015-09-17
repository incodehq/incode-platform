/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.security.dom.tenancy;

import java.util.List;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named="Security",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "100.30"
)
public class ApplicationTenancyMenu extends ApplicationTenancies {

        //region > findTenancies
        public static class FindTenanciesDomainEvent extends ActionDomainEvent {
                public FindTenanciesDomainEvent(final ApplicationTenancyMenu source, final Identifier identifier, final Object... args) {
                        super(source, identifier, args);
                }
        }

        @Action(
                domainEvent = FindTenanciesDomainEvent.class,
                semantics = SemanticsOf.SAFE,
                restrictTo = RestrictTo.PROTOTYPING
        )
        @ActionLayout(
                cssClassFa = "fa-list"
        )
        @MemberOrder(sequence = "100.30.5")
        public List<ApplicationTenancy> findTenancies(
                @Parameter(optionality = Optionality.OPTIONAL)
                @ParameterLayout(named = "Partial Name Or Path", describedAs = "String to search for, wildcard (*) can be used")
                final String partialNameOrPath) {
                return applicationTenancyRepository.findByNameOrPathMatching(partialNameOrPath == null ? ".*" : "(?i)" + partialNameOrPath.replaceAll("\\*", ".*"));
        }

        //endregion

}
