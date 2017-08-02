package org.incode.domainapp.example.dom.lib.fakedata.fixture.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import org.isisaddons.module.fakedata.dom.FakeDataService;
import org.incode.domainapp.example.dom.lib.fakedata.demo.EnumOf3;
import org.incode.domainapp.example.dom.lib.fakedata.demo.FakeDataDemoObject;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.applib.value.Money;
import org.apache.isis.applib.value.Password;

public class FakeDataDemoObjectUpdate extends DiscoverableFixtureScript {


    //region > fakeDataDemoObject (input property)
    private FakeDataDemoObject fakeDataDemoObject;

    public FakeDataDemoObject getFakeDataDemoObject() {
        return fakeDataDemoObject;
    }
    public void setFakeDataDemoObject(final FakeDataDemoObject fakeDataDemoObject) {
        this.fakeDataDemoObject = fakeDataDemoObject;
    }
    //endregion


    //region > name (input property)
    private String name;
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > someBoolean (input property)
    private Boolean someBoolean;
    public Boolean getSomeBoolean() {
        return someBoolean;
    }
    public void setSomeBoolean(final Boolean someBoolean) {
        this.someBoolean = someBoolean;
    }
    //endregion
    
    //region > someChar (input property)
    private Character someChar;
    public Character getSomeChar() {
        return someChar;
    }
    public void setSomeChar(final Character someChar) {
        this.someChar = someChar;
    }
    //endregion
    
    //region > someByte (input property)
    private Byte someByte;
    public Byte getSomeByte() {
        return someByte;
    }
    public void setSomeByte(final Byte someByte) {
        this.someByte = someByte;
    }
    //endregion
    
    //region > someShort (input property)
    private Short someShort;
    public Short getSomeShort() {
        return someShort;
    }
    public void setSomeShort(final Short someShort) {
        this.someShort = someShort;
    }
    //endregion

    //region > someInt (input property)
    private Integer someInt;
    public Integer getSomeInt() {
        return someInt;
    }
    public void setSomeInt(final Integer someInt) {
        this.someInt = someInt;
    }
    //endregion

    //region > someLong (input property)
    private Long someLong;
    public Long getSomeLong() {
        return someLong;
    }
    public void setSomeLong(final Long someLong) {
        this.someLong = someLong;
    }
    //endregion

    //region > someFloat (input property)
    private Float someFloat;
    public Float getSomeFloat() {
        return someFloat;
    }
    public void setSomeFloat(final Float someFloat) {
        this.someFloat = someFloat;
    }
    //endregion

    //region > someDouble (input property)
    private Double someDouble;
    public Double getSomeDouble() {
        return someDouble;
    }
    public void setSomeDouble(final Double someDouble) {
        this.someDouble = someDouble;
    }
    //endregion

    //region > someString (input property)
    private String someString;
    public String getSomeString() {
        return someString;
    }
    public void setSomeString(final String someString) {
        this.someString = someString;
    }
    //endregion

    //region > somePassword (input property)
    private Password somePassword;
    public Password getSomePassword() {
        return somePassword;
    }
    public void setSomePassword(final Password somePassword) {
        this.somePassword = somePassword;
    }
    //endregion

    //region > someBlob (input property)
    private Blob someBlob;
    public Blob getSomeBlob() {
        return someBlob;
    }
    public void setSomeBlob(final Blob someBlob) {
        this.someBlob = someBlob;
    }
    //endregion

    //region > someClob (input property)
    private Clob someClob;
    public Clob getSomeClob() {
        return someClob;
    }
    public void setSomeClob(final Clob someClob) {
        this.someClob = someClob;
    }
    //endregion

    //region > someJavaUtilDate (input property)
    private Date someJavaUtilDate;
    public Date getSomeJavaUtilDate() {
        return someJavaUtilDate;
    }
    public void setSomeJavaUtilDate(final Date someJavaUtilDate) {
        this.someJavaUtilDate = someJavaUtilDate;
    }
    //endregion

    //region > someJavaSqlDate (input property)
    private java.sql.Date someJavaSqlDate;
    public java.sql.Date getSomeJavaSqlDate() {
        return someJavaSqlDate;
    }
    public void setSomeJavaSqlDate(final java.sql.Date someJavaSqlDate) {
        this.someJavaSqlDate = someJavaSqlDate;
    }
    //endregion

    //region > someJodaLocalDate (input property)
    private LocalDate someJodaLocalDate;
    public LocalDate getSomeJodaLocalDate() {
        return someJodaLocalDate;
    }
    public void setSomeJodaLocalDate(final LocalDate someJodaLocalDate) {
        this.someJodaLocalDate = someJodaLocalDate;
    }
    //endregion

    //region > someJodaDateTime (input property)
    private DateTime someJodaDateTime;
    public DateTime getSomeJodaDateTime() {
        return someJodaDateTime;
    }
    public void setSomeJodaDateTime(final DateTime someJodaDateTime) {
        this.someJodaDateTime = someJodaDateTime;
    }
    //endregion

    //region > someJavaSqlTimestamp (input property)
    private Timestamp someJavaSqlTimestamp;
    public Timestamp getSomeJavaSqlTimestamp() {
        return someJavaSqlTimestamp;
    }
    public void setSomeJavaSqlTimestamp(final Timestamp someJavaSqlTimestamp) {
        this.someJavaSqlTimestamp = someJavaSqlTimestamp;
    }
    //endregion

    //region > someBigInteger (input property)
    private BigInteger someBigInteger;
    public BigInteger getSomeBigInteger() {
        return someBigInteger;
    }
    public void setSomeBigInteger(final BigInteger someBigInteger) {
        this.someBigInteger = someBigInteger;
    }
    //endregion

    //region > someBigDecimal (input property)
    private BigDecimal someBigDecimal;
    public BigDecimal getSomeBigDecimal() {
        return someBigDecimal;
    }
    public void setSomeBigDecimal(final BigDecimal someBigDecimal) {
        this.someBigDecimal = someBigDecimal;
    }
    //endregion

    //region > someUrl (input property)
    private URL someUrl;
    public URL getSomeUrl() {
        return someUrl;
    }
    public void setSomeUrl(final URL someUrl) {
        this.someUrl = someUrl;
    }
    //endregion

    //region > someUuid (input property)
    private UUID someUuid;
    public UUID getSomeUuid() {
        return someUuid;
    }
    public void setSomeUuid(final UUID someUuid) {
        this.someUuid = someUuid;
    }
    //endregion

    //region > someMoney (input property)
    private Money someMoney;
    public Money getSomeMoney() {
        return someMoney;
    }
    public void setSomeMoney(final Money someMoney) {
        this.someMoney = someMoney;
    }
    //endregion

    //region > someEnumOf3 (input property)
    private EnumOf3 someEnumOf3;
    public EnumOf3 getSomeEnumOf3() {
        return someEnumOf3;
    }
    public void setSomeEnumOf3(final EnumOf3 someEnumOf3) {
        this.someEnumOf3 = someEnumOf3;
    }
    //endregion


    @Override
    protected void execute(final ExecutionContext executionContext) {

        // mandatory
        this.checkParam("fakeDataDemoObject", executionContext, FakeDataDemoObject.class);

        // defaults
        this.defaultParam("someBoolean", executionContext, fakeDataService.booleans().any());
        this.defaultParam("someChar", executionContext, fakeDataService.chars().any());
        this.defaultParam("someByte", executionContext, fakeDataService.bytes().any());
        this.defaultParam("someShort", executionContext, fakeDataService.shorts().any());
        this.defaultParam("someInt", executionContext, fakeDataService.ints().any());
        this.defaultParam("someLong", executionContext, fakeDataService.longs().any());
        this.defaultParam("someFloat", executionContext, fakeDataService.floats().any());
        this.defaultParam("someDouble", executionContext, fakeDataService.doubles().any());

        this.defaultParam("someString", executionContext, fakeDataService.lorem().sentence());
        this.defaultParam("somePassword", executionContext, fakeDataService.isisPasswords().any());

        this.defaultParam("someBlob", executionContext, fakeDataService.isisBlobs().any());
        this.defaultParam("someClob", executionContext, fakeDataService.isisClobs().any());

        this.defaultParam("someJavaUtilDate", executionContext, fakeDataService.javaUtilDates().any());
        this.defaultParam("someJavaSqlDate", executionContext, fakeDataService.javaSqlDates().any());
        this.defaultParam("someJodaLocalDate", executionContext, fakeDataService.jodaLocalDates().any());
        this.defaultParam("someJodaDateTime", executionContext, fakeDataService.jodaDateTimes().any());
        this.defaultParam("someJavaSqlTimestamp", executionContext, fakeDataService.javaSqlTimestamps().any());

        this.defaultParam("someBigDecimal", executionContext, fakeDataService.bigDecimals().any(14,4));
        this.defaultParam("someBigInteger", executionContext, fakeDataService.bigIntegers().any());

        this.defaultParam("someUrl", executionContext, fakeDataService.urls().any());
        this.defaultParam("someUuid", executionContext, fakeDataService.uuids().any());
        this.defaultParam("someMoney", executionContext, fakeDataService.isisMoneys().any());
        this.defaultParam("someEnumOf3", executionContext, fakeDataService.enums().anyOf(EnumOf3.class));

        // updates
        final FakeDataDemoObject fakeDataDemoObject = getFakeDataDemoObject();
        
        wrap(fakeDataDemoObject).updateSomeBoolean(getSomeBoolean());
        wrap(fakeDataDemoObject).updateSomeBooleanWrapper(getSomeBoolean());

        wrap(fakeDataDemoObject).updateSomeByte(getSomeByte());
        wrap(fakeDataDemoObject).updateSomeByteWrapper(getSomeByte());

        wrap(fakeDataDemoObject).updateSomeShort(getSomeShort());
        wrap(fakeDataDemoObject).updateSomeShortWrapper(getSomeShort());

        wrap(fakeDataDemoObject).updateSomeInt(getSomeInt());
        wrap(fakeDataDemoObject).updateSomeIntegerWrapper(getSomeInt());

        wrap(fakeDataDemoObject).updateSomeLong(getSomeLong());
        wrap(fakeDataDemoObject).updateSomeLongWrapper(getSomeLong());

        wrap(fakeDataDemoObject).updateSomeFloat(getSomeFloat());
        wrap(fakeDataDemoObject).updateSomeFloatWrapper(getSomeFloat());

        wrap(fakeDataDemoObject).updateSomeDouble(getSomeDouble());
        wrap(fakeDataDemoObject).updateSomeDoubleWrapper(getSomeDouble());

        wrap(fakeDataDemoObject).updateSomeChar(getSomeChar());
        wrap(fakeDataDemoObject).updateSomeCharacterWrapper(getSomeChar());

        wrap(fakeDataDemoObject).updateSomeString(getSomeString());
        wrap(fakeDataDemoObject).updateSomePassword(getSomePassword());

        wrap(fakeDataDemoObject).updateSomeBlob(getSomeBlob());
        wrap(fakeDataDemoObject).updateSomeClob(getSomeClob());

        wrap(fakeDataDemoObject).updateSomeJavaUtilDate(getSomeJavaUtilDate());
        wrap(fakeDataDemoObject).updateSomeJavaSqlDate(getSomeJavaSqlDate());
        wrap(fakeDataDemoObject).updateSomeJodaLocalDate(getSomeJodaLocalDate());
        wrap(fakeDataDemoObject).updateSomeJodaDateTime(getSomeJodaDateTime());
        wrap(fakeDataDemoObject).updateSomeJavaSqlTimestamp(getSomeJavaSqlTimestamp());

        wrap(fakeDataDemoObject).updateSomeBigDecimal(getSomeBigDecimal());
        wrap(fakeDataDemoObject).updateSomeBigInteger(getSomeBigInteger());

        wrap(fakeDataDemoObject).updateSomeUrl(getSomeUrl());
        wrap(fakeDataDemoObject).updateSomeUuid(getSomeUuid());
        wrap(fakeDataDemoObject).updateSomeMoney(getSomeMoney());

        wrap(fakeDataDemoObject).updateSomeEnumOf3(getSomeEnumOf3());

        executionContext.addResult(this, this.fakeDataDemoObject);
    }

    @javax.inject.Inject
    private FakeDataService fakeDataService;
}
