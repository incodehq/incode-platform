package org.isisaddons.module.fakedata.fixture.dom;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.Digits;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.applib.value.Money;
import org.apache.isis.applib.value.Password;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "fakedata")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        objectType = "FAKEDATA_DEMO_OBJECT",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class FakeDataDemoObject implements Comparable<FakeDataDemoObject> {

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion


    //region > someBoolean (property)
    private boolean someBoolean;

    public boolean getSomeBoolean() {
        return someBoolean;
    }

    public void setSomeBoolean(final boolean aBoolean) {
        this.someBoolean = aBoolean;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBoolean(final boolean b) {
        setSomeBoolean(b);
        return this;
    }
    public boolean default0UpdateSomeBoolean() {
        return getSomeBoolean();
    }
    //endregion

    //region > someByte (property)
    private byte someByte;

    public byte getSomeByte() {
        return someByte;
    }

    public void setSomeByte(final byte aByte) {
        this.someByte = aByte;
    }

    @Action(semantics= SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeByte(final byte b) {
        setSomeByte(b);
        return this;
    }
    public byte default0UpdateSomeByte() {
        return getSomeByte();
    }
    //endregion

    //region > someShort (property)
    private short someShort;

    public short getSomeShort() {
        return someShort;
    }

    public void setSomeShort(final short someShort) {
        this.someShort = someShort;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeShort(final short s) {
        setSomeShort(s);
        return this;
    }
    public short default0UpdateSomeShort() {
        return getSomeShort();
    }
    //endregion

    //region > someInt (property)
    private int someInt;

    @PropertyLayout(describedAs = "description of some int")
    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(final int someInt) {
        this.someInt = someInt;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeInt(final int i) {
        setSomeInt(i);
        return this;
    }
    public int default0UpdateSomeInt() {
        return getSomeInt();
    }
    //endregion

    //region > someLong (property)
    private long someLong;

    public long getSomeLong() {
        return someLong;
    }

    public void setSomeLong(final long someLong) {
        this.someLong = someLong;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeLong(final long l) {
        setSomeLong(l);
        return this;
    }
    public long default0UpdateSomeLong() {
        return getSomeLong();
    }
    //endregion

    //region > someFloat (property)
    private float someFloat;

    public float getSomeFloat() {
        return someFloat;
    }

    public void setSomeFloat(final float someFloat) {
        this.someFloat = someFloat;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeFloat(final float f) {
        setSomeFloat(f);
        return this;
    }
    public float default0UpdateSomeFloat() {
        return getSomeFloat();
    }
    //endregion

    //region > someDouble (property)
    private double someDouble;

    public double getSomeDouble() {
        return someDouble;
    }

    public void setSomeDouble(final double someDouble) {
        this.someDouble = someDouble;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeDouble(final double d) {
        setSomeDouble(d);
        return this;
    }
    public double default0UpdateSomeDouble() {
        return getSomeDouble();
    }
    //endregion

    //region > someChar (property)
    private char someChar;

    public char getSomeChar() {
        return someChar;
    }

    public void setSomeChar(final char someChar) {
        this.someChar = someChar;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeChar(final char i) {
        setSomeChar(i);
        return this;
    }
    public char default0UpdateSomeChar() {
        return getSomeChar();
    }
    //endregion


    //region > someBooleanWrapperOptional (property)
    private Boolean someBooleanWrapper;

    @Column(allowsNull = "true")
    public Boolean getSomeBooleanWrapper() {
        return someBooleanWrapper;
    }

    public void setSomeBooleanWrapper(final Boolean someBooleanWrapper) {
        this.someBooleanWrapper = someBooleanWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBooleanWrapper(
            @Parameter(optionality = Optionality.OPTIONAL)
            final Boolean i) {
        setSomeBooleanWrapper(i);
        return this;
    }
    public Boolean default0UpdateSomeBooleanWrapper() {
        return getSomeBooleanWrapper();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBooleanWrapper() {
        setSomeBooleanWrapper(null);
        return this;
    }
    //endregion

    //region > someByteWrapperOptional (property)
    private Byte someByteWrapper;

    @Column(allowsNull = "true")
    public Byte getSomeByteWrapper() {
        return someByteWrapper;
    }

    public void setSomeByteWrapper(final Byte someByteWrapper) {
        this.someByteWrapper = someByteWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeByteWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Byte b) {
        setSomeByteWrapper(b);
        return this;
    }

    public Byte default0UpdateSomeByteWrapper() {
        return getSomeByteWrapper();
    }
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeByteWrapper() {
        setSomeByteWrapper(null);
        return this;
    }
    //endregion

    //region > someShortWrapperOptional (property)
    private Short someShortWrapper;

    @Column(allowsNull = "true")
    public Short getSomeShortWrapper() {
        return someShortWrapper;
    }

    public void setSomeShortWrapper(final Short someShortWrapper) {
        this.someShortWrapper = someShortWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeShortWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Short s) {
        setSomeShortWrapper(s);
        return this;
    }
    public Short default0UpdateSomeShortWrapper() {
        return getSomeShortWrapper();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeShortWrapper() {
        setSomeShortWrapper(null);
        return this;
    }
    //endregion

    //region > someIntegerWrapperOptional (property)
    private Integer someIntegerWrapper;

    @Column(allowsNull = "true")
    public Integer getSomeIntegerWrapper() {
        return someIntegerWrapper;
    }

    public void setSomeIntegerWrapper(final Integer someIntegerWrapper) {
        this.someIntegerWrapper = someIntegerWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeIntegerWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Integer i) {
        setSomeIntegerWrapper(i);
        return this;
    }
    public Integer default0UpdateSomeIntegerWrapper() {
        return getSomeIntegerWrapper();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeIntegerWrapper() {
        setSomeIntegerWrapper(null);
        return this;
    }
    //endregion

    //region > someLongWrapperOptional (property)
    private Long someLongWrapper;

    @Column(allowsNull = "true")
    public Long getSomeLongWrapper() {
        return someLongWrapper;
    }

    public void setSomeLongWrapper(final Long someLongWrapper) {
        this.someLongWrapper = someLongWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeLongWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Long l) {
        setSomeLongWrapper(l);
        return this;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeLongWrapper() {
        setSomeLongWrapper(null);
        return this;
    }
    //endregion

    //region > someFloatWrapperOptional (property)
    private Float someFloatWrapper;

    @Column(allowsNull = "true")
    public Float getSomeFloatWrapper() {
        return someFloatWrapper;
    }

    public void setSomeFloatWrapper(final Float someFloatWrapper) {
        this.someFloatWrapper = someFloatWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeFloatWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Float f) {
        setSomeFloatWrapper(f);
        return this;
    }
    public Float default0UpdateSomeFloatWrapper() {
        return getSomeFloatWrapper();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeFloatWrapper() {
        setSomeFloatWrapper(null);
        return this;
    }
    //endregion

    //region > someDoubleWrapperOptional (property)
    private Double someDoubleWrapper;

    @Column(allowsNull = "true")
    public Double getSomeDoubleWrapper() {
        return someDoubleWrapper;
    }

    public void setSomeDoubleWrapper(final Double someDoubleWrapper) {
        this.someDoubleWrapper = someDoubleWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeDoubleWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Double d) {
        setSomeDoubleWrapper(d);
        return this;
    }
    public Double default0UpdateSomeDoubleWrapper() {
        return getSomeDoubleWrapper();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeDoubleWrapper() {
        setSomeDoubleWrapper(null);
        return this;
    }
    //endregion

    //region > someCharacterWrapperOptional (property)
    private Character someCharacterWrapper;

    @Column(allowsNull = "true")
    public Character getSomeCharacterWrapper() {
        return someCharacterWrapper;
    }

    public void setSomeCharacterWrapper(final Character someCharacterWrapper) {
        this.someCharacterWrapper = someCharacterWrapper;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeCharacterWrapper(@Parameter(optionality = Optionality.OPTIONAL) final Character i) {
        setSomeCharacterWrapper(i);
        return this;
    }
    public Character default0UpdateSomeCharacterWrapper() {
        return getSomeCharacterWrapper();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeCharacterWrapper() {
        setSomeCharacterWrapper(null);
        return this;
    }
    //endregion


    //region > someStringOptional (property)
    private String someString;

    @Column(allowsNull = "true")
    public String getSomeString() {
        return someString;
    }

    public void setSomeString(final String someString) {
        this.someString = someString;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeString(@Parameter(optionality = Optionality.OPTIONAL) final String i) {
        setSomeString(i);
        return this;
    }
    public String default0UpdateSomeString() {
        return getSomeString();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeString() {
        setSomeString(null);
        return this;
    }
    //endregion

    //region > somePasswordOptional (property)
    private Password somePassword;

    @javax.jdo.annotations.Persistent(
    )
    @Column(allowsNull = "true")
    @Property(optionality=Optionality.OPTIONAL)
    public Password getSomePassword() {
        return somePassword;
    }
    public void setSomePassword(final Password somePassword) {
        this.somePassword = somePassword;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomePassword(@Parameter(optionality = Optionality.OPTIONAL) final Password password) {
        setSomePassword(password);
        return this;
    }
    public Password default0UpdateSomePassword() {
        return getSomePassword();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomePassword() {
        setSomePassword(null);
        return this;
    }
    //endregion


    //region > someBlob (property)
    private Blob someBlob;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "someBlob_name"),
            @javax.jdo.annotations.Column(name = "someBlob_mimetype"),
            @javax.jdo.annotations.Column(name = "someBlob_bytes", jdbcType = "BLOB", sqlType = "LONGVARBINARY")
    })
    @Property(optionality = Optionality.OPTIONAL)
    public Blob getSomeBlob() {
        return someBlob;
    }

    public void setSomeBlob(final Blob someBlob) {
        this.someBlob = someBlob;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBlob(
            @Parameter(optionality = Optionality.OPTIONAL)
            final Blob blob) {
        setSomeBlob(blob);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBlob() {
        setSomeBlob(null);
        return this;
    }
    //endregion

    //region > someClob (property)
    private Clob someClob;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
        @javax.jdo.annotations.Column(name = "someClob_name"),
        @javax.jdo.annotations.Column(name = "someClob_mimetype"),
        @javax.jdo.annotations.Column(name = "someClob_chars", jdbcType = "CLOB", sqlType = "LONGVARCHAR")
    })
    @Property(optionality=Optionality.OPTIONAL)
    public Clob getSomeClob() {
        return someClob;
    }

    public void setSomeClob(final Clob someClob) {
        this.someClob = someClob;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeClob(
            @Parameter(optionality = Optionality.OPTIONAL)
            final Clob clob) {
        setSomeClob(clob);
        return this;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeClob() {
        setSomeClob(null);
        return this;
    }
    //endregion


    //region > someJavaUtilDateOptional (property)
    private java.util.Date someJavaUtilDate;

    @Column(allowsNull = "true")
    public java.util.Date getSomeJavaUtilDate() {
        return someJavaUtilDate;
    }

    public void setSomeJavaUtilDate(final java.util.Date someJavaUtilDate) {
        this.someJavaUtilDate = someJavaUtilDate;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJavaUtilDate(@Parameter(optionality = Optionality.OPTIONAL) final Date i) {
        setSomeJavaUtilDate(i);
        return this;
    }
    public java.util.Date default0UpdateSomeJavaUtilDate() {
        return getSomeJavaUtilDate();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJavaUtilDate() {
        setSomeJavaUtilDate(null);
        return this;
    }
    //endregion

    //region > someJavaSqlDateOptional (property)
    private java.sql.Date someJavaSqlDate;

    @Column(allowsNull = "true")
    public java.sql.Date getSomeJavaSqlDate() {
        return someJavaSqlDate;
    }

    public void setSomeJavaSqlDate(final java.sql.Date someJavaSqlDate) {
        this.someJavaSqlDate = someJavaSqlDate;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJavaSqlDate(@Parameter(optionality = Optionality.OPTIONAL) final java.sql.Date i) {
        setSomeJavaSqlDate(i);
        return this;
    }
    public java.sql.Date default0UpdateSomeJavaSqlDate() {
        return getSomeJavaSqlDate();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJavaSqlDate() {
        setSomeJavaSqlDate(null);
        return this;
    }
    //endregion

    //region > someJodaLocalDateOptional (property)
    private org.joda.time.LocalDate someJodaLocalDate;

    @Column(allowsNull = "true")
    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    public org.joda.time.LocalDate getSomeJodaLocalDate() {
        return someJodaLocalDate;
    }

    public void setSomeJodaLocalDate(final org.joda.time.LocalDate someJodaLocalDate) {
        this.someJodaLocalDate = someJodaLocalDate;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaLocalDate(@Parameter(optionality = Optionality.OPTIONAL) final LocalDate i) {
        setSomeJodaLocalDate(i);
        return this;
    }
    public org.joda.time.LocalDate default0UpdateSomeJodaLocalDate() {
        return getSomeJodaLocalDate();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJodaLocalDate() {
        setSomeJodaLocalDate(null);
        return this;
    }
    //endregion

    // region > JodaLocalDateTime (commented out)

    /*

    //region > someJodaLocalDateTime (property)
    private org.joda.time.LocalDateTime someJodaLocalDateTime;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    @Column(allowsNull = "true")
    public org.joda.time.LocalDateTime getSomeJodaLocalDateTime() {
        return someJodaLocalDateTime;
    }

    public void setSomeJodaLocalDateTime(final org.joda.time.LocalDateTime someJodaLocalDateTime) {
        this.someJodaLocalDateTime = someJodaLocalDateTime;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaLocalDateTime(@Parameter(ity = ity.) final LocalDateTime i) {
        setSomeJodaLocalDateTime(i);
        return this;
    }
    public org.joda.time.LocalDateTime default0UpdateSomeJodaLocalDateTime() {
        return getSomeJodaLocalDateTime();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJodaLocalDateTime() {
        setSomeJodaLocalDateTime(null);
        return this;
    }
    //endregion
     */
    //endregion

    //
    // the applib datetime/date classes are commented out because they haven't been mapped to the DataNucleus objectstore,
    // and will almost certainly remove in Isis 2.0
    //

    //region > someJodaDateTimeOptional (property)
    private org.joda.time.DateTime someJodaDateTime;

    @Column(allowsNull = "true")
    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    public org.joda.time.DateTime getSomeJodaDateTime() {
        return someJodaDateTime;
    }

    public void setSomeJodaDateTime(final org.joda.time.DateTime someJodaDateTime) {
        this.someJodaDateTime = someJodaDateTime;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaDateTime(@Parameter(optionality = Optionality.OPTIONAL) final DateTime i) {
        setSomeJodaDateTime(i);
        return this;
    }
    public org.joda.time.DateTime default0UpdateSomeJodaDateTime() {
        return getSomeJodaDateTime();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJodaDateTime() {
        setSomeJodaDateTime(null);
        return this;
    }
    //endregion

    //region > someJavaSqlTimestampOptional (property)
    private java.sql.Timestamp someJavaSqlTimestamp;

    @Column(allowsNull = "true")
    public java.sql.Timestamp getSomeJavaSqlTimestamp() {
        return someJavaSqlTimestamp;
    }

    public void setSomeJavaSqlTimestamp(final java.sql.Timestamp someJavaSqlTimestamp) {
        this.someJavaSqlTimestamp = someJavaSqlTimestamp;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJavaSqlTimestamp(@Parameter(optionality = Optionality.OPTIONAL) final Timestamp i) {
        setSomeJavaSqlTimestamp(i);
        return this;
    }
    public java.sql.Timestamp default0UpdateSomeJavaSqlTimestamp() {
        return getSomeJavaSqlTimestamp();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJavaSqlTimestamp() {
        setSomeJavaSqlTimestamp(null);
        return this;
    }
    //endregion


    //region > someBigIntegerOptional (property)
    private BigInteger someBigInteger;

    @Column(allowsNull = "true")
    public BigInteger getSomeBigInteger() {
        return someBigInteger;
    }

    public void setSomeBigInteger(final BigInteger someBigInteger) {
        this.someBigInteger = someBigInteger;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBigInteger(
            @Parameter(optionality = Optionality.OPTIONAL)
            final BigInteger d) {
        setSomeBigInteger(d);
        return this;
    }
    public BigInteger default0UpdateSomeBigInteger() {
        return getSomeBigInteger();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBigInteger() {
        setSomeBigInteger(null);
        return this;
    }
    //endregion

    //region > someBigDecimalOptional (property)
    private BigDecimal someBigDecimal;

    @Column(allowsNull = "true", length = 14, scale = 4)
    public BigDecimal getSomeBigDecimal() {
        return someBigDecimal;
    }

    public void setSomeBigDecimal(final BigDecimal someBigDecimal) {
        this.someBigDecimal = someBigDecimal;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBigDecimal(
            @Parameter(optionality = Optionality.OPTIONAL) @Digits(integer = 10, fraction = 4) final BigDecimal d) {
        setSomeBigDecimal(d);
        return this;
    }
    public BigDecimal default0UpdateSomeBigDecimal() {
        return getSomeBigDecimal();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBigDecimal() {
        setSomeBigDecimal(null);
        return this;
    }
    //endregion


    //region > someUrlOptional (property)
    private java.net.URL someUrl;

    @Column(allowsNull = "true")
    public java.net.URL getSomeUrl() {
        return someUrl;
    }

    public void setSomeUrl(final java.net.URL someUrl) {
        this.someUrl = someUrl;
    }

    @Action(semantics= SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeUrl(@Parameter(optionality = Optionality.OPTIONAL) final URL i) {
        setSomeUrl(i);
        return this;
    }
    public java.net.URL default0UpdateSomeUrl() {
        return getSomeUrl();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeUrl() {
        setSomeUrl(null);
        return this;
    }
    //endregion

    //region > someUuidOptional (property)
    private java.util.UUID someUuid;

    @Column(allowsNull = "true")
    public java.util.UUID getSomeUuid() {
        return someUuid;
    }

    public void setSomeUuid(final java.util.UUID someUuid) {
        this.someUuid = someUuid;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeUuid(@Parameter(optionality = Optionality.OPTIONAL) final UUID i) {
        setSomeUuid(i);
        return this;
    }
    public java.util.UUID default0UpdateSomeUuid() {
        return getSomeUuid();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeUuid() {
        setSomeUuid(null);
        return this;
    }
    //endregion

    //region > someMoneyOptional (property)
    private Money someMoney;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true", columns = {
            @javax.jdo.annotations.Column(name = "someMoneyOptional_amount"),
            @javax.jdo.annotations.Column(name = "someMoneyOptional_currency")
    })
    @Property(optionality = Optionality.OPTIONAL)
    public Money getSomeMoney() {
        return someMoney;
    }

    public void setSomeMoney(final Money someMoney) {
        this.someMoney = someMoney;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeMoney(@Parameter(optionality = Optionality.OPTIONAL) final Money i) {
        setSomeMoney(i);
        return this;
    }
    public Money default0UpdateSomeMoney() {
        return getSomeMoney();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeMoney() {
        setSomeMoney(null);
        return this;
    }
    //endregion


    //region > someEnumOf3Optional (property)
    private EnumOf3 someEnumOf3;

    @Column(allowsNull = "true")
    public EnumOf3 getSomeEnumOf3() {
        return someEnumOf3;
    }

    public void setSomeEnumOf3(final EnumOf3 someEnumOf3) {
        this.someEnumOf3 = someEnumOf3;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeEnumOf3(@Parameter(optionality = Optionality.OPTIONAL) final EnumOf3 i) {
        setSomeEnumOf3(i);
        return this;
    }
    public EnumOf3 default0UpdateSomeEnumOf3() {
        return getSomeEnumOf3();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeEnumOf3() {
        setSomeEnumOf3(null);
        return this;
    }
    //endregion



    //region > compareTo

    @Override
    public int compareTo(final FakeDataDemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
