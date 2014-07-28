/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.stringinterpolator.integtests;

import java.net.URL;
import javax.inject.Inject;
import org.isisaddons.module.stringinterpolator.fixture.dom.ToDoItem;
import org.isisaddons.module.stringinterpolator.fixture.dom.ToDoItemReportingService;
import org.isisaddons.module.stringinterpolator.fixture.dom.ToDoItems;
import org.isisaddons.module.stringinterpolator.fixture.scripts.ToDoItemsFixture;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.core.runtime.system.context.IsisContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ToDoItemReportingService_open extends StringInterpolatorIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new ToDoItemsFixture());
    }

    @Inject
    private ToDoItems toDoItems;

    @Inject
    private ToDoItemReportingService toDoItemReportingService;

    @Test
    public void happyCase() throws Exception {

        // given
        assertThat(IsisContext.getConfiguration().getString("isis.website"), is("http://isis.apache.org"));
        assertThat(toDoItemReportingService.TEMPLATE, is("${properties['isis.website']}/${this.documentationPage}"));

        final ToDoItem toDoItem = toDoItems.allToDos().get(0);
        assertThat(toDoItem.getDocumentationPage(), is("documentation.html"));

        // when
        final URL url = toDoItemReportingService.open(toDoItem);

        // then
        assertThat(url.toExternalForm(), is("http://isis.apache.org/documentation.html"));
    }

}