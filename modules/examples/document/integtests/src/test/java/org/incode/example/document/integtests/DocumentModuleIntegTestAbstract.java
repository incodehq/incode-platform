package org.incode.example.document.integtests;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.demo.shared.other.dom.DocOtherObject;
import org.incode.example.document.demo.usage.dom.paperclips.demowithurl.PaperclipForDemoObjectWithUrl;
import org.incode.example.document.demo.usage.dom.paperclips.other.PaperclipForOtherObject;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.Document_delete;

public abstract class DocumentModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new DocumentModuleIntegTestModule();
    }

    protected DocumentModuleIntegTestAbstract() {
        super(module());
    }

    protected Document_delete _delete(final Document document) {
        return mixin(Document_delete.class, document);
    }

    protected PaperclipForDemoObjectWithUrl._preview _preview(final DocDemoObjectWithUrl domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._preview.class, domainObject);
    }

    protected PaperclipForDemoObjectWithUrl._preview _preview(final DocOtherObject domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._preview.class, domainObject);
    }

    protected PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndRender _createAndAttachDocumentAndRender(final DocDemoObjectWithUrl demoObject) {
        return mixin(PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndRender.class, demoObject);
    }

    protected PaperclipForOtherObject._createAndAttachDocumentAndRender _createAndAttachDocumentAndRender(final DocOtherObject otherObject) {
        return mixin(PaperclipForOtherObject._createAndAttachDocumentAndRender.class, otherObject);
    }

    protected PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndScheduleRender _createAndAttachDocumentAndScheduleRender(final DocDemoObjectWithUrl domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._createAndAttachDocumentAndScheduleRender.class, domainObject);
    }

    protected PaperclipForOtherObject._createAndAttachDocumentAndScheduleRender _createAndAttachDocumentAndScheduleRender(final DocOtherObject domainObject) {
        return mixin(PaperclipForOtherObject._createAndAttachDocumentAndScheduleRender.class, domainObject);
    }

    protected PaperclipForDemoObjectWithUrl._documents _documents(final DocDemoObjectWithUrl domainObject) {
        return mixin(PaperclipForDemoObjectWithUrl._documents.class, domainObject);
    }

    protected PaperclipForOtherObject._documents _documents(final DocOtherObject domainObject) {
        return mixin(PaperclipForOtherObject._documents.class, domainObject);
    }

}
