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
package org.isisaddons.wicket.pdfjs.app.homepage.services;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;
import org.isisaddons.wicket.pdfjs.fixture.dom.demo.DemoObject;
import org.isisaddons.wicket.pdfjs.fixture.dom.demo.DemoObjectMenu;

@XmlRootElement(name = "toDoItemDto")
@XmlType(
        propOrder = {
                "idx"
        }
)public class HomePageViewModel {


    public TranslatableString title() {
        return TranslatableString.tr("{cus} objects", "cus", getDemoObjects().size());
    }


    public List<DemoObject> getDemoObjects() {
        return demoObjectMenu.listAll();
    }


    @XmlTransient
    @PdfJsViewer(initialPageNum = 1, initialScale = 1.0d, initialHeight = 600)
    public Blob getBlob() {
        return getSelected() != null ? getSelected().getBlob() : null;
    }



    @XmlTransient
    @Property(hidden = Where.EVERYWHERE)
    public int getNumObjects() {
        return getDemoObjects().size();
    }


    private int idx;

    @XmlElement(required = true)
    @Property
    public int getIdx() {
        return idx;
    }

    public void setIdx(final int idx) {
        this.idx = idx;
    }


    @XmlTransient
    public DemoObject getSelected() {
        return getDemoObjects().get(getIdx());
    }


    public HomePageViewModel previous() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        viewModel.setIdx(getIdx()-1);
        return viewModel;
    }
    public String disablePrevious() {
        return getIdx() == 0 ? "At start" : null;
    }


    public HomePageViewModel next() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        viewModel.setIdx(getIdx()+1);
        return viewModel;
    }
    public String disableNext() {
        return getIdx() == getNumObjects() - 1 ? "At end" : null;
    }


    @javax.inject.Inject
    FactoryService factoryService;




    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

}
