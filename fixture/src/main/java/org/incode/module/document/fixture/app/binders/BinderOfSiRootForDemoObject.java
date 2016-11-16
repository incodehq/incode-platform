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
package org.incode.module.document.fixture.app.binders;

import java.util.Collections;
import java.util.List;

import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.fixture.dom.demo.DemoObject;

import lombok.Getter;

public class BinderOfSiRootForDemoObject implements Binder {

    public Binding newBinding(
            final DocumentTemplate documentTemplate,
            final Object domainObject) {

        if(!(domainObject instanceof DemoObject)) {
            throw new IllegalArgumentException("Domain object must be of type DemoObject");
        }
        DemoObject demoObject = (DemoObject) domainObject;

        // dataModel
        final DataModel dataModel = new DataModel(demoObject);

        // binding
        return new Binding(dataModel, determinePaperclipSpecs(domainObject));
    }

    protected List<Binding.PaperclipSpec> determinePaperclipSpecs(final Object domainObject) {
        return Collections.singletonList(new Binding.PaperclipSpec(null, domainObject));
    }

    public static class DataModel extends StringInterpolatorService.Root {
        @Getter
        private final DemoObject demoObject;

        public DataModel(final DemoObject demoObject) {
            super(demoObject);
            this.demoObject = demoObject;
        }
    }
}
