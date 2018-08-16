package org.incode.example.document.demo.shared.demowithurl.fixture;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrlMenu;

import lombok.Getter;

public class DocDemoObjectWithUrl_createUpTo5_fakeData extends FixtureScript {

    @javax.inject.Inject
    DocDemoObjectWithUrlMenu demoObjectMenu;

    @javax.inject.Inject
    FakeDataService fakeDataService;

    @Getter
    private Integer number ;


    public DocDemoObjectWithUrl_createUpTo5_fakeData setNumber(final Integer number) {
        this.number = number;
        return this;
    }

    @Getter
    private List<DocDemoObjectWithUrl> demoObjects = Lists.newArrayList();


    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("number", ec, 3);
        if(getNumber() < 1 || getNumber() > 5) {
            // there are 5 sample PDFs
            throw new IllegalArgumentException("number of demo objects to create must be within [1,5]");
        }

        for (int i = 0; i < getNumber(); i++) {
            final DocDemoObjectWithUrl demoObject = create(i, ec);
            getDemoObjects().add(demoObject);
        }
    }

    private DocDemoObjectWithUrl create(final int n, final ExecutionContext ec) {
        final String name = fakeDataService.name().firstName();
        final String url = "http://www.pdfpdf.com/samples/Sample" + (n+1) + ".PDF";

        final DocDemoObjectWithUrl demoObject = wrap(demoObjectMenu).createDemoObjectWithUrl(name);
        wrap(demoObject).setUrl(url);

        return ec.addResult(this, demoObject);
    }

}
