package org.incode.module.document.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.module.document.app.DocumentModuleAppManifest;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.Document_delete;

import domainapp.modules.exampledom.module.document.dom.demo.DemoObject;
import domainapp.modules.exampledom.module.document.dom.demo2.OtherObject;
import domainapp.modules.exampledom.module.document.dom.paperclips.demo.PaperclipForDemoObject;
import domainapp.modules.exampledom.module.document.dom.paperclips.demo2.PaperclipForOtherObject;

public abstract class DocumentModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                DocumentModuleAppManifest.BUILDER.
                        withAdditionalModules(DocumentModuleIntegTestAbstract.class, FakeDataModule.class)
                        .build());
    }


    protected Document_delete _delete(final Document document) {
        return mixin(Document_delete.class, document);
    }

    protected PaperclipForDemoObject._preview _preview(final DemoObject domainObject) {
        return mixin(PaperclipForDemoObject._preview.class, domainObject);
    }

    protected PaperclipForDemoObject._preview _preview(final OtherObject domainObject) {
        return mixin(PaperclipForDemoObject._preview.class, domainObject);
    }

    protected PaperclipForDemoObject._createAndAttachDocumentAndRender _createAndAttachDocumentAndRender(final DemoObject demoObject) {
        return mixin(PaperclipForDemoObject._createAndAttachDocumentAndRender.class, demoObject);
    }

    protected PaperclipForOtherObject._createAndAttachDocumentAndRender _createAndAttachDocumentAndRender(final OtherObject otherObject) {
        return mixin(PaperclipForOtherObject._createAndAttachDocumentAndRender.class, otherObject);
    }

    protected PaperclipForDemoObject._createAndAttachDocumentAndScheduleRender _createAndAttachDocumentAndScheduleRender(final DemoObject domainObject) {
        return mixin(PaperclipForDemoObject._createAndAttachDocumentAndScheduleRender.class, domainObject);
    }

    protected PaperclipForOtherObject._createAndAttachDocumentAndScheduleRender _createAndAttachDocumentAndScheduleRender(final OtherObject domainObject) {
        return mixin(PaperclipForOtherObject._createAndAttachDocumentAndScheduleRender.class, domainObject);
    }

    protected PaperclipForDemoObject._documents _documents(final DemoObject domainObject) {
        return mixin(PaperclipForDemoObject._documents.class, domainObject);
    }

    protected PaperclipForOtherObject._documents _documents(final OtherObject domainObject) {
        return mixin(PaperclipForOtherObject._documents.class, domainObject);
    }



}
