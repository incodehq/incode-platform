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
package org.incode.module.document.dom.impl.docs;

import org.joda.time.DateTime;

import org.apache.isis.applib.annotation.Programmatic;

public enum DocumentState {
    NOT_RENDERED {
        @Override public DateTime dateOf(final Document document) {
            return document.getCreatedAt();
        }
    },
    RENDERED {
        @Override public DateTime dateOf(final Document document) {
            return document.getRenderedAt();
        }
    };

    @Programmatic
    public abstract DateTime dateOf(final Document document);
}