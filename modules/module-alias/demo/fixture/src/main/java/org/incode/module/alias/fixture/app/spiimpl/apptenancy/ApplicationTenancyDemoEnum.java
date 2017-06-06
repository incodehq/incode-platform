/*
 *  Copyright 2014 Dan Haywood
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
package org.incode.module.alias.fixture.app.spiimpl.apptenancy;

/**
 * In a real (not demo) application this would probably be the isisaddons' security module's <code>ApplicationTenancy</code> entity.
 */
public enum ApplicationTenancyDemoEnum {

    GLOBAL("/"),
    UK("/uk"),
    NL("/nl"),
    IT("/it"),
    SW("/sw"),
    FR("/fr");

    private final String path;

    ApplicationTenancyDemoEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

