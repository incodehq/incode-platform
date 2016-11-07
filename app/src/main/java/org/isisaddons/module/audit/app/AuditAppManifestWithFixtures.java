/*
 *  Copyright 2013~2015 Dan Haywood
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
package org.isisaddons.module.audit.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.audit.fixture.scripts.AuditDemoAppFixture;

public class AuditAppManifestWithFixtures extends AuditAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Arrays.<Class<? extends FixtureScript>>asList(
                AuditDemoAppFixture.class);

    }
    @Override
    public Map<String, String> getConfigurationProperties() {
        final Map<String, String> props = Maps.newHashMap();
        props.put("isis.persistor.datanucleus.install-fixtures", "true");
        return props;
    }

}
