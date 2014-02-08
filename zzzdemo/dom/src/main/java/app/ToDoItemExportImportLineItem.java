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
package app;

import java.math.BigDecimal;

import dom.todo.ToDoItem;
import dom.todo.ToDoItem.Category;
import dom.todo.ToDoItem.Subcategory;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.services.bookmark.BookmarkService;

@Bookmarkable
public class ToDoItemExportImportLineItem 
        extends AbstractViewModel 
        implements Comparable<ToDoItemExportImportLineItem> {

    
    // //////////////////////////////////////
    // ViewModel implementation
    // //////////////////////////////////////
    
    private ToDoItem toDoItem;

    @Override
    public String viewModelMemento() {
        return toDoItemsExcelContributions.identifierFor(toDoItem);
    }

    @Override
    public void viewModelInit(String memento) {
        this.toDoItem = toDoItemsExcelContributions.lookupByIdentifier(memento);
        setDescription(toDoItem.getDescription());
        setCategory(toDoItem.getCategory());
        setSubcategory(toDoItem.getSubcategory());
        setComplete(toDoItem.isComplete());
        setCost(toDoItem.getCost());
        setDueBy(toDoItem.getDueBy());
        setNotes(toDoItem.getNotes());
        setOwnedBy(toDoItem.getOwnedBy());
    }

    // //////////////////////////////////////
    // Description (property)
    // //////////////////////////////////////
    
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    // //////////////////////////////////////
    // DueBy (property)
    // //////////////////////////////////////

    private LocalDate dueBy;

    public LocalDate getDueBy() {
        return dueBy;
    }

    public void setDueBy(final LocalDate dueBy) {
        this.dueBy = dueBy;
    }

    
    // //////////////////////////////////////
    // Category and Subcategory (property)
    // //////////////////////////////////////

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    // //////////////////////////////////////

    private Subcategory subcategory;

    public Subcategory getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(final Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    // //////////////////////////////////////
    // OwnedBy (property)
    // //////////////////////////////////////
    
    private String ownedBy;

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }


    // //////////////////////////////////////
    // Complete (property), 
    // Done (action), Undo (action)
    // //////////////////////////////////////

    private boolean complete;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }


    // //////////////////////////////////////
    // Cost (property), UpdateCost (action)
    // //////////////////////////////////////

    private BigDecimal cost;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(final BigDecimal cost) {
        this.cost = cost!=null?cost.setScale(2):null;
    }
    

    // //////////////////////////////////////
    // Notes (property)
    // //////////////////////////////////////

    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }


    // //////////////////////////////////////
    // compareTo
    // //////////////////////////////////////

    @Override
    public int compareTo(ToDoItemExportImportLineItem other) {
        return this.toDoItem.compareTo(other.toDoItem);
    }

    
    // //////////////////////////////////////
    // injected services
    // //////////////////////////////////////
    
    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private ToDoItemExportImportService toDoItemsExcelContributions;
}
