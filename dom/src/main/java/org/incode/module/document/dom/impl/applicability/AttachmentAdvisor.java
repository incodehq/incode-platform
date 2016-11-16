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
package org.incode.module.document.dom.impl.applicability;

import java.util.List;

import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;

import lombok.Data;

/**
 * Implementation is responsible for providing a set of {@link AttachmentAdvisor.PaperclipSpec}s which describe how to
 * attach a (newly created) {@link Document} to existing domain objects.
 *
 * <p>
 *     (Class name is) referenced by {@link Applicability#getAttachmentAdvisorClassName()} ()}.
 * </p>
 */
public interface AttachmentAdvisor {

    @Data
    public static class PaperclipSpec {
        private final String roleName;
        private final Object attachTo;
    }

    /**
     * @param documentTemplate - to which this implementation applies, as per {@link DocumentTemplate#getAppliesTo()} and {@link Applicability#getAttachmentAdvisorClassName()}
     * @param domainObject - acting as the context for document created, from which derive the objects to attach the newly created {@link Document}.
     */
    @Programmatic
    List<PaperclipSpec> advise(
            final DocumentTemplate documentTemplate,
            final Object domainObject);

}
