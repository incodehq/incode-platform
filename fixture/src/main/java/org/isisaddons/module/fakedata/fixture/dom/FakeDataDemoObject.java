/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Programmatic;
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
        objectType = "FAKEDATA_DEMO_OBJECT"
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
    private Boolean someBooleanWrapperOptional;

    @Column(allowsNull = "true")
    public Boolean getSomeBooleanWrapperOptional() {
        return someBooleanWrapperOptional;
    }

    public void setSomeBooleanWrapperOptional(final Boolean someBooleanWrapperOptional) {
        this.someBooleanWrapperOptional = someBooleanWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBooleanWrapperOptional(
            @Parameter(optionality = Optionality.OPTIONAL)
            final Boolean i) {
        setSomeBooleanWrapperOptional(i);
        return this;
    }
    public Boolean default0UpdateSomeBooleanWrapperOptional() {
        return getSomeBooleanWrapperOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBooleanWrapperOptional() {
        setSomeBooleanWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someByteWrapperOptional (property)
    private Byte someByteWrapperOptional;

    @Column(allowsNull = "true")
    public Byte getSomeByteWrapperOptional() {
        return someByteWrapperOptional;
    }

    public void setSomeByteWrapperOptional(final Byte someByteWrapperOptional) {
        this.someByteWrapperOptional = someByteWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeByteWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Byte b) {
        setSomeByteWrapperOptional(b);
        return this;
    }

    public Byte default0UpdateSomeByteWrapperOptional() {
        return getSomeByteWrapperOptional();
    }
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeByteWrapperOptional() {
        setSomeByteWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someShortWrapperOptional (property)
    private Short someShortWrapperOptional;

    @Column(allowsNull = "true")
    public Short getSomeShortWrapperOptional() {
        return someShortWrapperOptional;
    }

    public void setSomeShortWrapperOptional(final Short someShortWrapperOptional) {
        this.someShortWrapperOptional = someShortWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeShortWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Short s) {
        setSomeShortWrapperOptional(s);
        return this;
    }
    public Short default0UpdateSomeShortWrapperOptional() {
        return getSomeShortWrapperOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeShortWrapperOptional() {
        setSomeShortWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someIntegerWrapperOptional (property)
    private Integer someIntegerWrapperOptional;

    @Column(allowsNull = "true")
    public Integer getSomeIntegerWrapperOptional() {
        return someIntegerWrapperOptional;
    }

    public void setSomeIntegerWrapperOptional(final Integer someIntegerWrapperOptional) {
        this.someIntegerWrapperOptional = someIntegerWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeIntegerWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Integer i) {
        setSomeIntegerWrapperOptional(i);
        return this;
    }
    public Integer default0UpdateSomeIntegerWrapperOptional() {
        return getSomeIntegerWrapperOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeIntegerWrapperOptional() {
        setSomeIntegerWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someLongWrapperOptional (property)
    private Long someLongWrapperOptional;

    @Column(allowsNull = "true")
    public Long getSomeLongWrapperOptional() {
        return someLongWrapperOptional;
    }

    public void setSomeLongWrapperOptional(final Long someLongWrapperOptional) {
        this.someLongWrapperOptional = someLongWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeLongWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Long l) {
        setSomeLongWrapperOptional(l);
        return this;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeLongWrapperOptional() {
        setSomeLongWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someFloatWrapperOptional (property)
    private Float someFloatWrapperOptional;

    @Column(allowsNull = "true")
    public Float getSomeFloatWrapperOptional() {
        return someFloatWrapperOptional;
    }

    public void setSomeFloatWrapperOptional(final Float someFloatWrapperOptional) {
        this.someFloatWrapperOptional = someFloatWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeFloatWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Float f) {
        setSomeFloatWrapperOptional(f);
        return this;
    }
    public Float default0UpdateSomeFloatWrapperOptional() {
        return getSomeFloatWrapperOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeFloatWrapperOptional() {
        setSomeFloatWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someDoubleWrapperOptional (property)
    private Double someDoubleWrapperOptional;

    @Column(allowsNull = "true")
    public Double getSomeDoubleWrapperOptional() {
        return someDoubleWrapperOptional;
    }

    public void setSomeDoubleWrapperOptional(final Double someDoubleWrapperOptional) {
        this.someDoubleWrapperOptional = someDoubleWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeDoubleWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Double d) {
        setSomeDoubleWrapperOptional(d);
        return this;
    }
    public Double default0UpdateSomeDoubleWrapperOptional() {
        return getSomeDoubleWrapperOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeDoubleWrapperOptional() {
        setSomeDoubleWrapperOptional(null);
        return this;
    }
    //endregion

    //region > someCharacterWrapperOptional (property)
    private Character someCharacterWrapperOptional;

    @Column(allowsNull = "true")
    public Character getSomeCharacterWrapperOptional() {
        return someCharacterWrapperOptional;
    }

    public void setSomeCharacterWrapperOptional(final Character someCharacterWrapperOptional) {
        this.someCharacterWrapperOptional = someCharacterWrapperOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeCharacterWrapperOptional(@Parameter(optionality = Optionality.OPTIONAL) final Character i) {
        setSomeCharacterWrapperOptional(i);
        return this;
    }
    public Character default0UpdateSomeCharacterWrapperOptional() {
        return getSomeCharacterWrapperOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeCharacterWrapperOptional() {
        setSomeCharacterWrapperOptional(null);
        return this;
    }
    //endregion


    //region > someStringOptional (property)
    private String someStringOptional;

    @Column(allowsNull = "true")
    public String getSomeStringOptional() {
        return someStringOptional;
    }

    public void setSomeStringOptional(final String someStringOptional) {
        this.someStringOptional = someStringOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeStringOptional(@Parameter(optionality = Optionality.OPTIONAL) final String i) {
        setSomeStringOptional(i);
        return this;
    }
    public String default0UpdateSomeStringOptional() {
        return getSomeStringOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeStringOptional() {
        setSomeStringOptional(null);
        return this;
    }
    //endregion

    //region > somePasswordOptional (property)
    private String somePasswordOptionalStr;

    @Column(allowsNull = "true")
    @Programmatic
    public String getSomePasswordOptionalStr() {
        return somePasswordOptionalStr;
    }

    public void setSomePasswordOptionalStr(final String somePasswordOptionalStr) {
        this.somePasswordOptionalStr = somePasswordOptionalStr;
    }

    @javax.jdo.annotations.NotPersistent
    @Property(optionality=Optionality.OPTIONAL)
    public Password getSomePasswordOptional() {
        return new Password(somePasswordOptionalStr);
    }
    public void setSomePasswordOptional(final Password somePasswordOptional) {
        this.somePasswordOptionalStr = somePasswordOptional != null? somePasswordOptional.getPassword(): null;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomePasswordOptional(@Parameter(optionality = Optionality.OPTIONAL) final Password password) {
        setSomePasswordOptional(password);
        return this;
    }
    public Password default0UpdateSomePasswordOptional() {
        return getSomePasswordOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomePasswordOptional() {
        setSomePasswordOptionalStr(null);
        return this;
    }
    //endregion


    //region > someBlob (property)
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "someBlob_name"),
            @javax.jdo.annotations.Column(name = "someBlob_mimetype"),
            @javax.jdo.annotations.Column(name = "someBlob_bytes", jdbcType = "BLOB", sqlType = "BLOB")
    })
    private Blob someBlob;

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
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "someClob_name"),
            @javax.jdo.annotations.Column(name = "someClob_mimetype"),
            @javax.jdo.annotations.Column(name = "someClob_chars", jdbcType = "CLOB", sqlType = "CLOB")
    })
    private Clob someClob;

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
    private java.util.Date someJavaUtilDateOptional;

    @Column(allowsNull = "true")
    public java.util.Date getSomeJavaUtilDateOptional() {
        return someJavaUtilDateOptional;
    }

    public void setSomeJavaUtilDateOptional(final java.util.Date someJavaUtilDateOptional) {
        this.someJavaUtilDateOptional = someJavaUtilDateOptional;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJavaUtilDateOptional(@Parameter(optionality = Optionality.OPTIONAL) final Date i) {
        setSomeJavaUtilDateOptional(i);
        return this;
    }
    public java.util.Date default0UpdateSomeJavaUtilDateOptional() {
        return getSomeJavaUtilDateOptional();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJavaUtilDateOptional() {
        setSomeJavaUtilDateOptional(null);
        return this;
    }
    //endregion

    //region > someJavaSqlDateOptional (property)
    private java.sql.Date someJavaSqlDateOptional;

    @Column(allowsNull = "true")
    public java.sql.Date getSomeJavaSqlDateOptional() {
        return someJavaSqlDateOptional;
    }

    public void setSomeJavaSqlDateOptional(final java.sql.Date someJavaSqlDateOptional) {
        this.someJavaSqlDateOptional = someJavaSqlDateOptional;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJavaSqlDateOptional(@Parameter(optionality = Optionality.OPTIONAL) final java.sql.Date i) {
        setSomeJavaSqlDateOptional(i);
        return this;
    }
    public java.sql.Date default0UpdateSomeJavaSqlDateOptional() {
        return getSomeJavaSqlDateOptional();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJavaSqlDateOptional() {
        setSomeJavaSqlDateOptional(null);
        return this;
    }
    //endregion

    //region > someJodaLocalDateOptional (property)
    private org.joda.time.LocalDate someJodaLocalDateOptional;

    @Column(allowsNull = "true")
    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    public org.joda.time.LocalDate getSomeJodaLocalDateOptional() {
        return someJodaLocalDateOptional;
    }

    public void setSomeJodaLocalDateOptional(final org.joda.time.LocalDate someJodaLocalDateOptional) {
        this.someJodaLocalDateOptional = someJodaLocalDateOptional;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaLocalDateOptional(@Parameter(optionality = Optionality.OPTIONAL) final LocalDate i) {
        setSomeJodaLocalDateOptional(i);
        return this;
    }
    public org.joda.time.LocalDate default0UpdateSomeJodaLocalDateOptional() {
        return getSomeJodaLocalDateOptional();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJodaLocalDateOptional() {
        setSomeJodaLocalDateOptional(null);
        return this;
    }
    //endregion

    // region > JodaLocalDateTime (commented out)

    /*

    //region > someJodaLocalDateTimeMandatory (property)
    private org.joda.time.LocalDateTime someJodaLocalDateTimeMandatory;

    @Column(allowsNull = "false")
    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    public org.joda.time.LocalDateTime getSomeJodaLocalDateTimeMandatory() {
        return someJodaLocalDateTimeMandatory;
    }

    public void setSomeJodaLocalDateTimeMandatory(final org.joda.time.LocalDateTime someJodaLocalDateTimeMandatory) {
        this.someJodaLocalDateTimeMandatory = someJodaLocalDateTimeMandatory;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaLocalDateTimeMandatory(final LocalDateTime i) {
        setSomeJodaLocalDateTimeMandatory(i);
        return this;
    }
    public org.joda.time.LocalDateTime default0UpdateSomeJodaLocalDateTimeMandatory() {
        return getSomeJodaLocalDateTimeMandatory();
    }
    //endregion

    //region > someJodaLocalDateTimeOptional (property)
    private org.joda.time.LocalDateTime someJodaLocalDateTimeOptional;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    @Column(allowsNull = "true")
    public org.joda.time.LocalDateTime getSomeJodaLocalDateTimeOptional() {
        return someJodaLocalDateTimeOptional;
    }

    public void setSomeJodaLocalDateTimeOptional(final org.joda.time.LocalDateTime someJodaLocalDateTimeOptional) {
        this.someJodaLocalDateTimeOptional = someJodaLocalDateTimeOptional;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaLocalDateTimeOptional(@Parameter(optionality = Optionality.OPTIONAL) final LocalDateTime i) {
        setSomeJodaLocalDateTimeOptional(i);
        return this;
    }
    public org.joda.time.LocalDateTime default0UpdateSomeJodaLocalDateTimeOptional() {
        return getSomeJodaLocalDateTimeOptional();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJodaLocalDateTimeOptional() {
        setSomeJodaLocalDateTimeOptional(null);
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
    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    private org.joda.time.DateTime someJodaDateTimeOptional;

    @Column(allowsNull = "true")
    public org.joda.time.DateTime getSomeJodaDateTimeOptional() {
        return someJodaDateTimeOptional;
    }

    public void setSomeJodaDateTimeOptional(final org.joda.time.DateTime someJodaDateTimeOptional) {
        this.someJodaDateTimeOptional = someJodaDateTimeOptional;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJodaDateTimeOptional(@Parameter(optionality = Optionality.OPTIONAL) final DateTime i) {
        setSomeJodaDateTimeOptional(i);
        return this;
    }
    public org.joda.time.DateTime default0UpdateSomeJodaDateTimeOptional() {
        return getSomeJodaDateTimeOptional();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJodaDateTimeOptional() {
        setSomeJodaDateTimeOptional(null);
        return this;
    }
    //endregion

    //region > someJavaSqlTimestampOptional (property)
    private java.sql.Timestamp someJavaSqlTimestampOptional;

    @Column(allowsNull = "true")
    public java.sql.Timestamp getSomeJavaSqlTimestampOptional() {
        return someJavaSqlTimestampOptional;
    }

    public void setSomeJavaSqlTimestampOptional(final java.sql.Timestamp someJavaSqlTimestampOptional) {
        this.someJavaSqlTimestampOptional = someJavaSqlTimestampOptional;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeJavaSqlTimestampOptional(@Parameter(optionality = Optionality.OPTIONAL) final Timestamp i) {
        setSomeJavaSqlTimestampOptional(i);
        return this;
    }
    public java.sql.Timestamp default0UpdateSomeJavaSqlTimestampOptional() {
        return getSomeJavaSqlTimestampOptional();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeJavaSqlTimestampOptional() {
        setSomeJavaSqlTimestampOptional(null);
        return this;
    }
    //endregion


    //region > someBigIntegerOptional (property)
    private BigInteger someBigIntegerOptional;

    @Column(allowsNull = "true")
    public BigInteger getSomeBigIntegerOptional() {
        return someBigIntegerOptional;
    }

    public void setSomeBigIntegerOptional(final BigInteger someBigIntegerOptional) {
        this.someBigIntegerOptional = someBigIntegerOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBigIntegerOptional(
            @Parameter(optionality = Optionality.OPTIONAL)
            final BigInteger d) {
        setSomeBigIntegerOptional(d);
        return this;
    }
    public BigInteger default0UpdateSomeBigIntegerOptional() {
        return getSomeBigIntegerOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBigIntegerOptional() {
        setSomeBigIntegerOptional(null);
        return this;
    }
    //endregion

    //region > someBigDecimalOptional (property)
    private BigDecimal someBigDecimalOptional;

    @Column(allowsNull = "true")
    public BigDecimal getSomeBigDecimalOptional() {
        return someBigDecimalOptional;
    }

    public void setSomeBigDecimalOptional(final BigDecimal someBigDecimalOptional) {
        this.someBigDecimalOptional = someBigDecimalOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeBigDecimalOptional(@Parameter(optionality = Optionality.OPTIONAL) final BigDecimal d) {
        setSomeBigDecimalOptional(d);
        return this;
    }
    public BigDecimal default0UpdateSomeBigDecimalOptional() {
        return getSomeBigDecimalOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeBigDecimalOptional() {
        setSomeBigDecimalOptional(null);
        return this;
    }
    //endregion


    //region > someUrlOptional (property)
    private java.net.URL someUrlOptional;

    @Column(allowsNull = "true")
    public java.net.URL getSomeUrlOptional() {
        return someUrlOptional;
    }

    public void setSomeUrlOptional(final java.net.URL someUrlOptional) {
        this.someUrlOptional = someUrlOptional;
    }

    @Action(semantics= SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeUrlOptional(@Parameter(optionality = Optionality.OPTIONAL) final URL i) {
        setSomeUrlOptional(i);
        return this;
    }
    public java.net.URL default0UpdateSomeUrlOptional() {
        return getSomeUrlOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeUrlOptional() {
        setSomeUrlOptional(null);
        return this;
    }
    //endregion

    //region > someUuidOptional (property)
    private java.util.UUID someUuidOptional;

    @Column(allowsNull = "true")
    public java.util.UUID getSomeUuidOptional() {
        return someUuidOptional;
    }

    public void setSomeUuidOptional(final java.util.UUID someUuidOptional) {
        this.someUuidOptional = someUuidOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeUuidOptional(@Parameter(optionality = Optionality.OPTIONAL) final UUID i) {
        setSomeUuidOptional(i);
        return this;
    }
    public java.util.UUID default0UpdateSomeUuidOptional() {
        return getSomeUuidOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeUuidOptional() {
        setSomeUuidOptional(null);
        return this;
    }
    //endregion

    //region > someMoneyOptional (property)
    private Money someMoneyOptional;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true", columns = {
            @javax.jdo.annotations.Column(name = "someMoneyOptional_amount"),
            @javax.jdo.annotations.Column(name = "someMoneyOptional_currency")
    })
    @Column(allowsNull = "true")
    public Money getSomeMoneyOptional() {
        return someMoneyOptional;
    }

    public void setSomeMoneyOptional(final Money someMoneyOptional) {
        this.someMoneyOptional = someMoneyOptional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeMoneyOptional(@Parameter(optionality = Optionality.OPTIONAL) final Money i) {
        setSomeMoneyOptional(i);
        return this;
    }
    public Money default0UpdateSomeMoneyOptional() {
        return getSomeMoneyOptional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeMoneyOptional() {
        setSomeMoneyOptional(null);
        return this;
    }
    //endregion


    //region > someEnumOf3Optional (property)
    private EnumOf3 someEnumOf3Optional;

    @Column(allowsNull = "true")
    public EnumOf3 getSomeEnumOf3Optional() {
        return someEnumOf3Optional;
    }

    public void setSomeEnumOf3Optional(final EnumOf3 someEnumOf3Optional) {
        this.someEnumOf3Optional = someEnumOf3Optional;
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject updateSomeEnumOf3Optional(@Parameter(optionality = Optionality.OPTIONAL) final EnumOf3 i) {
        setSomeEnumOf3Optional(i);
        return this;
    }
    public EnumOf3 default0UpdateSomeEnumOf3Optional() {
        return getSomeEnumOf3Optional();
    }

    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public FakeDataDemoObject resetSomeEnumOf3Optional() {
        setSomeEnumOf3Optional(null);
        return this;
    }
    //endregion



    //region > compareTo

    @Override
    public int compareTo(FakeDataDemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
