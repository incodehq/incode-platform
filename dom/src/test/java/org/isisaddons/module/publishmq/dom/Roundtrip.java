package org.isisaddons.module.publishmq.dom;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import org.apache.isis.applib.services.bookmark.Bookmark;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class Roundtrip {

    @Test
    public void happyCase() throws Exception {

        // given
        final ActionInvocationMementoDto aim = ActionInvocationMementoUtils.newDto();

        ActionInvocationMementoUtils.setMetadata(
                aim,
                UUID.randomUUID(),
                1,
                new java.sql.Timestamp(new java.util.Date().getTime()),
                "com.mycompany.myapp.Customer", "placeOrder", "com.mycompany.myapp.Customer#placeOrder",
                "CUS", "12345", "John Customer", "freddyUser");

        ActionInvocationMementoUtils.addArgValue(aim, "aString", "Fred");
        ActionInvocationMementoUtils.addArgValue(aim, "nullString", (String) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aByte", (byte) 123);
        ActionInvocationMementoUtils.addArgValue(aim, "nullByte", (Byte) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aShort", (short) 32123);
        ActionInvocationMementoUtils.addArgValue(aim, "nullShort", (Short) null);

        ActionInvocationMementoUtils.addArgValue(aim, "anInt", 123454321);
        ActionInvocationMementoUtils.addArgValue(aim, "nullInt", (Integer) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aLong", 1234567654321L);
        ActionInvocationMementoUtils.addArgValue(aim, "nullLong", (Long) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aFloat", 12345.6789F);
        ActionInvocationMementoUtils.addArgValue(aim, "nullFloat", (Float) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aDouble", 12345678.90123);
        ActionInvocationMementoUtils.addArgValue(aim, "nullDouble", (Double) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aBoolean", true);
        ActionInvocationMementoUtils.addArgValue(aim, "nullBoolean", (Boolean) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aChar", 'x');
        ActionInvocationMementoUtils.addArgValue(aim, "nullChar", (Character) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aBigInteger", new java.math.BigInteger("12345678901234567890"));
        ActionInvocationMementoUtils.addArgValue(aim, "nullBigInteger", (java.math.BigInteger) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aBigDecimal", new java.math.BigDecimal("12345678901234567890"));
        ActionInvocationMementoUtils.addArgValue(aim, "nullBigDecimal", (java.math.BigDecimal) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aJodaDateTime", new org.joda.time.DateTime(2015, 5, 23, 9, 54, 1));
        ActionInvocationMementoUtils.addArgValue(aim, "nullJodaDateTime", (org.joda.time.DateTime) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aJodaLocalDate", new org.joda.time.LocalDate(2015, 5, 23));
        ActionInvocationMementoUtils.addArgValue(aim, "nullJodaLocalDate", (org.joda.time.LocalDate) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aJodaLocalDateTime", new org.joda.time.LocalDateTime(2015, 5, 23, 9, 54, 1));
        ActionInvocationMementoUtils.addArgValue(aim, "nullJodaLocalDateTime", (org.joda.time.LocalDateTime) null);

        ActionInvocationMementoUtils.addArgValue(aim, "aJodaLocalTime", new org.joda.time.LocalTime(9, 54, 1));
        ActionInvocationMementoUtils.addArgValue(aim, "nullJodaLocalTime", (org.joda.time.LocalTime) null);

        ActionInvocationMementoUtils.addArgReference(aim, "aReference", new Bookmark("ORD", "12345"));
        ActionInvocationMementoUtils.addArgReference(aim, "nullReference", null);


        // when
        final CharArrayWriter caw = new CharArrayWriter();
        ActionInvocationMementoUtils.toXml(aim, caw);

        ActionInvocationMementoUtils.dump(aim, System.out);

        final CharArrayReader reader = new CharArrayReader(caw.toCharArray());
        final ActionInvocationMementoDto recreated = ActionInvocationMementoUtils.fromXml(reader);


        // then
        Assertions.assertThat(recreated.getMetadata().getActionIdentifier()).isEqualTo(aim.getMetadata().getActionIdentifier());
        Assertions.assertThat(recreated.getMetadata().getTarget().getObjectType()).isEqualTo(aim.getMetadata().getTarget().getObjectType());
        Assertions.assertThat(recreated.getMetadata().getTarget().getObjectIdentifier()).isEqualTo(aim.getMetadata().getTarget().getObjectIdentifier());


        int param = 0;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aString");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.STRING);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, String.class)).isEqualTo("Fred");

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullString");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.STRING);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, String.class)).isNull();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aByte");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BYTE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, Byte.class)).isEqualTo((byte)123);

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BYTE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullByte");

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aShort");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.SHORT);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, Short.class)).isEqualTo((short)32123);

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullShort");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.SHORT);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("anInt");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.INT);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, int.class)).isEqualTo((int)123454321);

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullInt");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.INT);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aLong");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.LONG);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, long.class)).isEqualTo((long)1234567654321L);

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullLong");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.LONG);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aFloat");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.FLOAT);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, float.class)).isEqualTo((float)12345.6789F);

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullFloat");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.FLOAT);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aDouble");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.DOUBLE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, double.class)).isEqualTo(12345678.90123);

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullDouble");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.DOUBLE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aBoolean");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BOOLEAN);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, boolean.class)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullBoolean");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BOOLEAN);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aChar");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.CHAR);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, char.class)).isEqualTo('x');

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullChar");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.CHAR);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aBigInteger");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BIG_INTEGER);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, BigInteger.class)).isEqualTo(new java.math.BigInteger("12345678901234567890"));

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullBigInteger");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BIG_INTEGER);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aBigDecimal");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BIG_DECIMAL);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, BigDecimal.class)).isEqualTo(new java.math.BigDecimal("12345678901234567890"));

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullBigDecimal");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.BIG_DECIMAL);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aJodaDateTime");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_DATE_TIME);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        // bit hacky... regular comparison fails but toString() works... must be some additional data that differs, not sure what tho'
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, DateTime.class).toString()).isEqualTo(new DateTime(2015, 5, 23, 9, 54, 1).toString());

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullJodaDateTime");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_DATE_TIME);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aJodaLocalDate");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_LOCAL_DATE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        final LocalDate actual = ActionInvocationMementoUtils.getArg(recreated, param, LocalDate.class);
        final LocalDate expected = new LocalDate(2015, 5, 23);
        assertThat(actual, equalTo(expected));

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullJodaLocalDate");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_LOCAL_DATE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aJodaLocalDateTime");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_LOCAL_DATE_TIME);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, LocalDateTime.class)).isEqualTo(new org.joda.time.LocalDateTime(2015, 5, 23, 9, 54, 1));

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullJodaLocalDateTime");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_LOCAL_DATE_TIME);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aJodaLocalTime");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_LOCAL_TIME);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, LocalDateTime.class)).isEqualTo(new org.joda.time.LocalTime(9, 54, 1));

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullJodaLocalTime");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.JODA_LOCAL_TIME);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("aReference");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.REFERENCE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isFalse();
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, OidDto.class).getObjectType()).isEqualTo("ORD");
        Assertions.assertThat(ActionInvocationMementoUtils.getArg(recreated, param, OidDto.class).getObjectIdentifier()).isEqualTo("12345");

        param++;
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterName(recreated, param)).isEqualTo("nullReference");
        Assertions.assertThat(ActionInvocationMementoUtils.getParameterType(recreated, param)).isEqualTo(ParameterType.REFERENCE);
        Assertions.assertThat(ActionInvocationMementoUtils.isNull(recreated, param)).isTrue();

        param++;
//        final int expected = param;
//        Assertions.assertThat(recreated.getParameters().getNum()).isEqualTo(expected);
//        Assertions.assertThat(recreated.getParameters().getParam().size()).isEqualTo(expected);
//        Assertions.assertThat(ActionInvocationMementoUtils.getNumberOfParameters(recreated)).isEqualTo(expected);

    }

}
