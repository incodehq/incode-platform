/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
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
package org.incode.module.document.dom.mixins;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.background.BackgroundCommandService;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;

public abstract class T_createAndAttachDocumentAndScheduleRender<T> extends T_createAndAttachDocumentAbstract<T> {

    public T_createAndAttachDocumentAndScheduleRender(final T domainObject) {
        super(domainObject);
    }


    @MemberOrder(name = "documents", sequence = "2.2")
    @Override
    public Document $$(final DocumentTemplate template) throws IOException {
        return super.$$(template);
    }

    @Override
    protected Document doCreate(
            final DocumentTemplate template,
            final boolean shouldPersist,
            final String additionalTextIfAny) {
        return template.createAndScheduleRender(domainObject, additionalTextIfAny);
    }

    @Override
    public TranslatableString disable$$() {
        return backgroundCommandService == null
                ? TranslatableString.tr("Application is not configured to support background rendering")
                : null;
    }

    @Inject
    BackgroundCommandService backgroundCommandService;

}
