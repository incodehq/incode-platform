package org.incode.example.document.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.paperclips.demowithurl.PaperclipForDemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.paperclips.other.PaperclipForOtherObject;
import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.shared.other.dom.OtherObject;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.Document_delete;
import org.incode.example.document.integtests.app.DocumentModuleAppManifest;

public abstract class DocumentModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                DocumentModuleAppManifest.BUILDER.
                        withAdditionalModules(
                                ExampleDomDemoDomSubmodule.class,
                                DocumentModuleIntegTestAbstract.class,
                                FakeDataModule.class
                        )
                        .build());
    }


    protected Document_delete _delete(final Document document) {
        return mixin(Document_delete.class, document);
    }

    protected PaperclipForDemoObjectWithUrl._preview _preview(final DemoObjectWithUrl domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._preview.class, domainObject);
    }

    protected PaperclipForDemoObjectWithUrl._preview _preview(final OtherObject domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._preview.class, domainObject);
    }

    protected PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndRender _createAndAttachDocumentAndRender(final DemoObjectWithUrl demoObject) {
        return mixin(PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndRender.class, demoObject);
    }

    protected PaperclipForOtherObject._createAndAttachDocumentAndRender _createAndAttachDocumentAndRender(final OtherObject otherObject) {
        return mixin(PaperclipForOtherObject._createAndAttachDocumentAndRender.class, otherObject);
    }

    protected PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndScheduleRender _createAndAttachDocumentAndScheduleRender(final DemoObjectWithUrl domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndScheduleRender.class, domainObject);
    }

    protected PaperclipForOtherObject._createAndAttachDocumentAndScheduleRender _createAndAttachDocumentAndScheduleRender(final OtherObject domainObject) {
        return mixin(PaperclipForOtherObject._createAndAttachDocumentAndScheduleRender.class, domainObject);
    }

    protected PaperclipForDemoObjectWithUrl._documents _documents(final DemoObjectWithUrl domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._documents.class, domainObject);
    }

    protected PaperclipForOtherObject._documents _documents(final OtherObject domainObject) {
        return mixin(PaperclipForOtherObject._documents.class, domainObject);
    }



}
