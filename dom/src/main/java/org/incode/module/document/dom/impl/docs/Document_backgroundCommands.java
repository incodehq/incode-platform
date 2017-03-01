/*
 *  Copyright 2016 Dan Haywood
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
package org.incode.module.document.dom.impl.docs;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.T_backgroundCommands;

@Mixin
public class Document_backgroundCommands extends T_backgroundCommands<Document> {

    private final Document document;

    public Document_backgroundCommands(final Document document) {
        super(document);
        this.document = document;
    }

    public static class ActionDomainEvent extends T_backgroundCommands.ActionDomainEvent {}

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    public List<CommandJdo> $$() {
        return super.$$();
    }


    public boolean hide$$() {
        // hide for supporting documents
        final Document supportedBy = supportsEvaluator.supportedBy(document);
        return supportedBy != null;
    }

    @Inject
    Document_supports.Evaluator supportsEvaluator;

}
