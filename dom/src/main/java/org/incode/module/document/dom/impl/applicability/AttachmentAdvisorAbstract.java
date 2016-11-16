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
package org.incode.module.document.dom.impl.applicability;

import java.util.List;

import org.incode.module.document.dom.impl.docs.DocumentTemplate;

/**
 * C
 * @param <T>
 */
public abstract class AttachmentAdvisorAbstract<T> implements AttachmentAdvisor {

    private final Class<T> expectedInputType;

    public AttachmentAdvisorAbstract(Class<T> expectedInputType) {
        this.expectedInputType = expectedInputType;
    }

    public final List<PaperclipSpec> advise(
            final DocumentTemplate documentTemplate,
            final Object domainObject) {

        checkInputClass(domainObject);

        return doAdvise(documentTemplate, (T)domainObject);
    }

    /**
     * Optional hook; default implementation checks that the input type is of the correct type.
     */
    protected void checkInputClass(final Object domainObject) {
        final Class<?> actualInputType = domainObject.getClass();
        if(!(expectedInputType.isAssignableFrom(actualInputType))) {
            throw new IllegalArgumentException("The input document is required to be of type: " + expectedInputType.getName());
        }
    }

    /**
     * Mandatory hook.
     */
    protected abstract List<PaperclipSpec> doAdvise(
            final DocumentTemplate documentTemplate,
            final T domainObject);


}
