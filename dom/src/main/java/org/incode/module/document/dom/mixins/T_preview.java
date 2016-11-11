/*
 *  Copyright 2016 incode.org
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
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.impl.types.DocumentTypeRepository;
import org.incode.module.document.dom.services.ClassService;

public abstract class T_preview<T> {

    //region > constructor
    protected final T domainObject;

    public T_preview(final T domainObject) {
        this.domainObject = domainObject;
    }
    //endregion

    /**
     * Preview the document's content (as a URL to that content).
     */
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    @MemberOrder(name = "documents", sequence = "1")
    public URL $$(final DocumentTemplate template) throws IOException {
        final String roleName = null;
        final String additionalTextIfAny = null;

        final Binder.Binding binding = template.newBinding(domainObject, additionalTextIfAny);
        return template.preview(binding.getDataModel());
    }


    public boolean hide$$() {
        return choices0$$().isEmpty();
    }

    /**
     * All templates which are applicable to the domain object's atPath, and which can be previewed.
     */
    public List<DocumentTemplate> choices0$$() {
        return documentTemplateService.documentTemplatesForPreview(domainObject);
    }


    //region > injected services

    @Inject
    DocumentTemplateForAtPathService documentTemplateService;

    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    DocumentTypeRepository documentTypeRepository;

    @Inject
    ClassService classService;

    //endregion

}
