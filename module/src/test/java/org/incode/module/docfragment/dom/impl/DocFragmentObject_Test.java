/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.incode.module.docfragment.dom.impl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class DocFragmentObject_Test {

    DocFragmentObject domainObject;

    @Before
    public void setUp() throws Exception {
        domainObject = new DocFragmentObject("Foobar");
    }

    public static class Name extends DocFragmentObject_Test {

        @Test
        public void happyCase() throws Exception {
            // given
            Assertions.assertThat(domainObject.getName()).isEqualTo("Foobar");

            // when
            String name = "Foobar - updated";
            domainObject.setName(name);

            // then
            Assertions.assertThat(domainObject.getName()).isEqualTo(name);
        }
    }

}
