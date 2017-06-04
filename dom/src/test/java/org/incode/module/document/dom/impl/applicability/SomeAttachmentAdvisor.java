/*
 *
 *  Copyright 2015 incode.org
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

import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;

public class SomeAttachmentAdvisor implements AttachmentAdvisor {
    @Override public List<PaperclipSpec> advise(
            final DocumentTemplate documentTemplate, final Object domainObject, final Document createdDocument) {
        return null;
    }
}
