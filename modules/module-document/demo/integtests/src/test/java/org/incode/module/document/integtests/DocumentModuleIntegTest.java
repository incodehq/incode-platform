package org.incode.module.document.integtests;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.BeforeClass;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.document.app.DocumentModuleAppManifest;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.Document_delete;
import org.incode.module.document.fixture.app.paperclips.demo.PaperclipForDemoObject;
import org.incode.module.document.fixture.app.paperclips.other.PaperclipForOtherObject;
import org.incode.module.document.fixture.dom.demo.DemoObject;
import org.incode.module.document.fixture.dom.other.OtherObject;

public abstract class DocumentModuleIntegTest extends IntegrationTestAbstract {

    @Inject
    protected FixtureScripts fixtureScripts;

    @Inject protected
    TransactionService transactionService;

    @Inject
    protected FakeDataService fakeDataService;

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

    protected static <T> List<T> asList(final Iterable<T> iterable) {
        return Lists.newArrayList(iterable);
    }

    protected static TypeSafeMatcher<Throwable> of(final Class<?> type) {
        return new TypeSafeMatcher<Throwable>() {
            @Override
            protected boolean matchesSafely(final Throwable throwable) {
                final List<Throwable> causalChain = Throwables.getCausalChain(throwable);
                return !FluentIterable.from(causalChain).filter(type).isEmpty();
            }

            @Override public void describeTo(final Description description) {
                description.appendText("Caused by " + type.getName());
            }
        };
    }


    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");

        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new DocumentModuleAppManifestForIntegTests())
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

    private static class DocumentModuleAppManifestForIntegTests extends DocumentModuleAppManifest {
        {
            withModules(DocumentModuleIntegTest.class, FakeDataModule.class);
        }
        @Override
        public Map<String, String> getConfigurationProperties() {
            final Map<String, String> map = super.getConfigurationProperties();
            Util.withJavaxJdoRunInMemoryProperties(map);
            Util.withDataNucleusProperties(map);
            Util.withIsisIntegTestProperties(map);
            return map;
        }
    }
}
