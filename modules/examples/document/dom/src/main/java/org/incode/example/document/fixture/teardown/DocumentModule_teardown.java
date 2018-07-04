package org.incode.example.document.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.document.dom.impl.applicability.Applicability;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.DocumentAbstract;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.rendering.RenderingStrategy;
import org.incode.example.document.dom.impl.types.DocumentType;

public class DocumentModule_teardown extends TeardownFixtureAbstract2 {
    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(Paperclip.class);
        deleteFrom(Applicability.class);
        deleteFrom(Document.class);
        deleteFrom(DocumentTemplate.class);
        deleteFrom(DocumentAbstract.class);
        deleteFrom(DocumentType.class);
        deleteFrom(RenderingStrategy.class);
    }
}
