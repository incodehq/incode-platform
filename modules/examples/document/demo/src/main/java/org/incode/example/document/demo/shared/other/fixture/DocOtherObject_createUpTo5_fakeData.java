package org.incode.example.document.demo.shared.other.fixture;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.example.document.demo.shared.other.dom.DocOtherObject;
import org.incode.example.document.demo.shared.other.dom.DocOtherObjectMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class DocOtherObject_createUpTo5_fakeData extends FixtureScript {

    @javax.inject.Inject
    DocOtherObjectMenu otherObjectMenu;

    @javax.inject.Inject
    FakeDataService fakeDataService;

    @Getter @Setter
    private Integer number ;

    @Getter
    private List<DocOtherObject> otherObjects = Lists.newArrayList();


    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("number", ec, 5);
        if(getNumber() < 1 || getNumber() > 5) {
            throw new IllegalArgumentException("number of other objects to create must be within [1,5]");
        }

        for (int i = 0; i < getNumber(); i++) {
            final DocOtherObject otherObject = create(ec);
            getOtherObjects().add(otherObject);
        }
    }

    private DocOtherObject create(final ExecutionContext ec) {
        final String name = fakeDataService.name().firstName();

        final DocOtherObject otherObject = wrap(otherObjectMenu).createOtherObjects(name);

        return ec.addResult(this, otherObject);
    }

}
