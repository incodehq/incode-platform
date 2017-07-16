package domainapp.modules.exampledom.module.document.fixture.data;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import domainapp.modules.exampledom.module.document.dom.demo.DemoObject;
import domainapp.modules.exampledom.module.document.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

import lombok.Getter;

public class DemoObjectsFixture extends FixtureScript {

    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

    @javax.inject.Inject
    FakeDataService fakeDataService;

    @Getter
    private Integer number ;
    private DocumentTypeAndTemplatesApplicableForDemoObjectFixture documentTypeAndTemplatesApplicableForDemoObjectFixture;

    public DemoObjectsFixture setNumber(final Integer number) {
        this.number = number;
        return this;
    }

    @Getter
    private List<DemoObject> demoObjects = Lists.newArrayList();


    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("number", ec, 3);
        if(getNumber() < 1 || getNumber() > 5) {
            // there are 5 sample PDFs
            throw new IllegalArgumentException("number of demo objects to create must be within [1,5]");
        }

        for (int i = 0; i < getNumber(); i++) {
            final DemoObject demoObject = create(i, ec);
            getDemoObjects().add(demoObject);
        }
    }

    private DemoObject create(final int n, final ExecutionContext ec) {
        final String name = fakeDataService.name().firstName();
        final String url = "http://www.pdfpdf.com/samples/Sample" + (n+1) + ".PDF";

        final DemoObject demoObject = wrap(demoObjectMenu).create(name);
        wrap(demoObject).setUrl(url);

        return ec.addResult(this, demoObject);
    }

}
