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
package org.incode.module.document.fixture.app.applicability.rmf;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.isisaddons.module.xdocreport.dom.service.XDocReportModel;

import org.incode.module.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.fixture.dom.demo.DemoObject;

import lombok.Getter;

public class XDocReportModelOfDemoObject extends RendererModelFactoryAbstract<DemoObject> {

    public XDocReportModelOfDemoObject() {
        super(DemoObject.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DemoObject demoObject) {
        return new DataModel(demoObject);
    }

    public static class DataModel implements XDocReportModel {

        // for freemarker
        @Getter
        private final DemoObject demoObject;

        public DataModel(final DemoObject demoObject) {
            this.demoObject = demoObject;
        }

        // for XDocReport
        @Override
        public Map<String, Data> getContextData() {
            return ImmutableMap.of("demoObject", Data.object(demoObject));
        }

    }
}
