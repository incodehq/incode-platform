package org.incode.domainapp.example.dom.lib.fakedata.fixture.data;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.example.dom.demo.dom.demowithall.DemoObjectWithAll;
import org.incode.domainapp.example.dom.demo.dom.demowithall.DemoObjectWithAllMenu;

import lombok.Getter;
import lombok.Setter;

public class DemoObjectWithAll_create_withFakeData extends FixtureScript {

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private String name;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Boolean someBoolean;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Character someChar;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Byte someByte;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Short someShort;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Integer someInt;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Long someLong;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Float someFloat;

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Double someDouble;

    @Getter
    private DemoObjectWithAll fakeDataDemoObject;

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // defaults
        this.defaultParam("name", executionContext, fakeDataService.name().firstName());

        this.defaultParam("someBoolean", executionContext, fakeDataService.booleans().any());
        this.defaultParam("someChar", executionContext, fakeDataService.chars().any());
        this.defaultParam("someByte", executionContext, fakeDataService.bytes().any());
        this.defaultParam("someShort", executionContext, fakeDataService.shorts().any());
        this.defaultParam("someInt", executionContext, fakeDataService.ints().any());
        this.defaultParam("someLong", executionContext, fakeDataService.longs().any());
        this.defaultParam("someFloat", executionContext, fakeDataService.floats().any());
        this.defaultParam("someDouble", executionContext, fakeDataService.doubles().any());

        // create
        this.fakeDataDemoObject =
                wrap(demoObjectWithAllMenu).createDemoObjectWithAll(getName(), getSomeBoolean(), getSomeChar(), getSomeByte(), getSomeShort(), getSomeInt(), getSomeLong(), getSomeFloat(), getSomeDouble());

        executionContext.addResult(this, fakeDataDemoObject);
    }

    @javax.inject.Inject
    DemoObjectWithAllMenu demoObjectWithAllMenu;

    @javax.inject.Inject
    FakeDataService fakeDataService;
}
