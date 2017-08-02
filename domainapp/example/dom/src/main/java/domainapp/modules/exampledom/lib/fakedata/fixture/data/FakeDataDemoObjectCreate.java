package domainapp.modules.exampledom.lib.fakedata.fixture.data;

import org.isisaddons.module.fakedata.dom.FakeDataService;
import domainapp.modules.exampledom.lib.fakedata.dom.demo.FakeDataDemoObject;
import domainapp.modules.exampledom.lib.fakedata.dom.demo.FakeDataDemoObjects;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class FakeDataDemoObjectCreate extends DiscoverableFixtureScript {

    //region > name (input property)
    private String name;
    @Programmatic
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > someBoolean (input property)
    private Boolean someBoolean;
    @Programmatic
    public Boolean getSomeBoolean() {
        return someBoolean;
    }
    public void setSomeBoolean(final Boolean someBoolean) {
        this.someBoolean = someBoolean;
    }
    //endregion
    
    //region > someChar (input property)
    private Character someChar;
    @Programmatic
    public Character getSomeChar() {
        return someChar;
    }
    public void setSomeChar(final Character someChar) {
        this.someChar = someChar;
    }
    //endregion
    
    //region > someByte (input property)
    private Byte someByte;
    @Programmatic
    public Byte getSomeByte() {
        return someByte;
    }
    public void setSomeByte(final Byte someByte) {
        this.someByte = someByte;
    }
    //endregion
    
    //region > someShort (input property)
    private Short someShort;
    @Programmatic
    public Short getSomeShort() {
        return someShort;
    }
    public void setSomeShort(final Short someShort) {
        this.someShort = someShort;
    }
    //endregion

    //region > someInt (input property)
    private Integer someInt;
    @Programmatic
    public Integer getSomeInt() {
        return someInt;
    }
    public void setSomeInt(final Integer someInt) {
        this.someInt = someInt;
    }
    //endregion

    //region > someLong (input property)
    private Long someLong;
    @Programmatic
    public Long getSomeLong() {
        return someLong;
    }
    public void setSomeLong(final Long someLong) {
        this.someLong = someLong;
    }
    //endregion

    //region > someFloat (input property)
    private Float someFloat;
    @Programmatic
    public Float getSomeFloat() {
        return someFloat;
    }
    public void setSomeFloat(final Float someFloat) {
        this.someFloat = someFloat;
    }
    //endregion

    //region > someDouble (input property)
    private Double someDouble;
    @Programmatic
    public Double getSomeDouble() {
        return someDouble;
    }
    public void setSomeDouble(final Double someDouble) {
        this.someDouble = someDouble;
    }
    //endregion

    //region > fakeDataDemoObject (output property)
    private FakeDataDemoObject fakeDataDemoObject;

    public FakeDataDemoObject getFakeDataDemoObject() {
        return fakeDataDemoObject;
    }
    //endregion

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
                wrap(fakeDataDemoObjects).create(getName(), getSomeBoolean(), getSomeChar(), getSomeByte(), getSomeShort(), getSomeInt(), getSomeLong(), getSomeFloat(), getSomeDouble());

        executionContext.addResult(this, fakeDataDemoObject);
    }

    // //////////////////////////////////////

    // //////////////////////////////////////

    @javax.inject.Inject
    private FakeDataDemoObjects fakeDataDemoObjects;

    @javax.inject.Inject
    private FakeDataService fakeDataService;
}
