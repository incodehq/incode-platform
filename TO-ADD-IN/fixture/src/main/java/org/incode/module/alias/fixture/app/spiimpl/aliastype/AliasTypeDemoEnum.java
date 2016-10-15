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
package org.incode.module.alias.fixture.app.spiimpl.aliastype;

import org.apache.isis.applib.annotation.Title;

import org.incode.module.alias.dom.spi.AliasType;

public enum AliasTypeDemoEnum implements AliasType {

    // in UK and NL
    GENERAL_LEDGER("GL"),
    // in UK and NL
    DOCUMENT_MANAGEMENT("DOC"),
    // in UK only
    PERSONNEL_SYSTEM("HR")
    ;

    private final String id;

    AliasTypeDemoEnum(final String id) {
        this.id = id;
    }

    @Title
    @Override
    public String getId() {
        return id;
    }
}

