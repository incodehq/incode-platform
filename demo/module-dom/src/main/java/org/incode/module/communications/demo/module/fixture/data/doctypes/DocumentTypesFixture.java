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
package org.incode.module.communications.demo.module.fixture.data.doctypes;

import javax.inject.Inject;

import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.document.dom.impl.types.DocumentType;
import org.incode.module.document.fixture.DocumentTemplateFSAbstract;

public class DocumentTypesFixture extends DocumentTemplateFSAbstract {

    public static final String DOC_TYPE_REF_RECEIPT = "RECEIPT";

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        final DocumentType docType =
                upsertType(DOC_TYPE_REF_RECEIPT, "Receipt document type", executionContext);

        executionContext.addResult(this, docType);

    }


    @Inject
    ClockService clockService;


}
