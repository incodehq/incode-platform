/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.wicket.summernote.fixture.scripts.todo;

import java.math.BigDecimal;
import org.isisaddons.wicket.summernote.fixture.dom.SummernoteEditorToDoItem;
import org.isisaddons.wicket.summernote.fixture.dom.SummernoteEditorToDoItems;
import org.joda.time.LocalDate;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SummernoteEditorToDoItemsFixture extends FixtureScript {

    private final String user;

    public SummernoteEditorToDoItemsFixture() {
        this(null);
    }
    public SummernoteEditorToDoItemsFixture(final String ownedBy) {
        this.user = ownedBy;
    }


    @Override
    protected void execute(final ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();

        installFor(ownedBy, executionContext);
        getContainer().flush();
    }

    private void installFor(final String user, final ExecutionContext executionContext) {

        createToDoItemForUser("Buy milk", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("0.75"), new BigDecimal("0.68"), executionContext);
        createToDoItemForUser("Buy bread", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("1.75"), new BigDecimal("1.70"), executionContext);
        createToDoItemForUser("Buy stamps", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("10.00"), new BigDecimal("9.75"), executionContext).setComplete(true);
        createToDoItemForUser("Pick up laundry", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Chores, user, daysFromToday(6), new BigDecimal("7.50"), new BigDecimal("6.90"), executionContext);
        createToDoItemForUser("Mow lawn", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Garden, user, daysFromToday(6), null, null, executionContext);
        createToDoItemForUser("Vacuum house", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Housework, user, daysFromToday(3), null, null, executionContext);
        createToDoItemForUser("Sharpen knives", SummernoteEditorToDoItem.Category.Domestic, SummernoteEditorToDoItem.Subcategory.Chores, user, daysFromToday(14), null, null, executionContext);

        createToDoItemForUser("Write to penpal", SummernoteEditorToDoItem.Category.Other, SummernoteEditorToDoItem.Subcategory.Other, user, null, null, null, executionContext);

        createToDoItemForUser("Write blog post", SummernoteEditorToDoItem.Category.Professional, SummernoteEditorToDoItem.Subcategory.Marketing, user, daysFromToday(7), null, null, executionContext).setComplete(true);
        createToDoItemForUser("Organize brown bag", SummernoteEditorToDoItem.Category.Professional, SummernoteEditorToDoItem.Subcategory.Consulting, user, daysFromToday(14), null, null, executionContext);
        createToDoItemForUser("Submit conference session", SummernoteEditorToDoItem.Category.Professional, SummernoteEditorToDoItem.Subcategory.Education, user, daysFromToday(21), null, null, executionContext);
        createToDoItemForUser("Stage Isis release", SummernoteEditorToDoItem.Category.Professional, SummernoteEditorToDoItem.Subcategory.OpenSource, user, null, null, null, executionContext);

        getContainer().flush();
    }


    // //////////////////////////////////////

    private SummernoteEditorToDoItem createToDoItemForUser(
            final String description,
            final SummernoteEditorToDoItem.Category category, SummernoteEditorToDoItem.Subcategory subcategory,
            final String user, final LocalDate dueBy,
            final BigDecimal cost, BigDecimal previousCost,
            final ExecutionContext executionContext) {
        return executionContext.add(this, description, toDoItems.newToDo(description, category, subcategory, user, dueBy, cost, previousCost));
    }

    private static LocalDate daysFromToday(final int i) {
        final LocalDate date = new LocalDate(Clock.getTimeAsDateTime());
        return date.plusDays(i);
    }


    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private SummernoteEditorToDoItems toDoItems;

}
