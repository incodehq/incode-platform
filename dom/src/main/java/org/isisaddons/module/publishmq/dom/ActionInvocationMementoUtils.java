package org.isisaddons.module.publishmq.dom;

import java.io.CharArrayWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import org.apache.isis.applib.services.bookmark.Bookmark;

import org.isisaddons.module.publishmq.dom.jaxbadapters.JavaSqlTimestampXmlGregorianCalendarAdapter;
import org.isisaddons.module.publishmq.dom.jaxbadapters.JodaDateTimeXMLGregorianCalendarAdapter;
import org.isisaddons.module.publishmq.dom.jaxbadapters.JodaLocalDateTimeXMLGregorianCalendarAdapter;
import org.isisaddons.module.publishmq.dom.jaxbadapters.JodaLocalDateXMLGregorianCalendarAdapter;
import org.isisaddons.module.publishmq.dom.jaxbadapters.JodaLocalTimeXMLGregorianCalendarAdapter;

public class ActionInvocationMementoUtils {

    //region > static
    private static final Function<ParamDto, String> PARAM_DTO_TO_NAME = new Function<ParamDto, String>() {
        @Override public String apply(final ParamDto input) {
            return input.getParameterName();
        }
    };
    private static final Function<ParamDto, ParameterType> PARAM_DTO_TO_TYPE = new Function<ParamDto, ParameterType>() {
        @Override public ParameterType apply(final ParamDto input) {
            return input.getParameterType();
        }
    };
    private static JAXBContext jaxbContext;
    private static JAXBContext getJaxbContext() {
        if(jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(ActionInvocationMementoDto.class);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return jaxbContext;
    }
    //endregion

    public static ActionInvocationMementoDto newDto() {
        return new ActionInvocationMementoDto();
    }

    //region > actionIdentifier, target

    public static void setMetadata(
            final ActionInvocationMementoDto aim,
            final UUID transactionId,
            final int sequence,
            final Timestamp timestamp,
            final String targetClass,
            final String targetAction,
            final String actionIdentifier,
            final String targetObjectType,
            final String targetObjectIdentifier,
            final String title,
            final String user) {
        ActionInvocationMementoDto.Metadata metadata = aim.getMetadata();
        if(metadata == null) {
            metadata = new ActionInvocationMementoDto.Metadata();
            aim.setMetadata(metadata);
        }

        metadata.setTransactionId(transactionId.toString());
        metadata.setSequence(sequence);
        metadata.setTimestamp(JavaSqlTimestampXmlGregorianCalendarAdapter.print(timestamp));

        metadata.setTargetClass(targetClass);
        metadata.setTargetAction(targetAction);
        metadata.setActionIdentifier(actionIdentifier);

        final OidDto target = new OidDto();
        target.setObjectType(targetObjectType);
        target.setObjectIdentifier(targetObjectIdentifier);
        metadata.setTarget(target);

        metadata.setTitle(title);
        metadata.setUser(user);
   }


    //endregion

    //region > addArgReference
    public static boolean addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Class<?> parameterType, final Object arg) {
        if(parameterType == String.class) {
            addArgValue(aim, parameterName, (String) arg);
        } else
        if(parameterType == byte.class || parameterType == Byte.class) {
            addArgValue(aim, parameterName, (Byte) arg);
        } else
        if(parameterType == short.class || parameterType == Short.class) {
            addArgValue(aim, parameterName, (Short) arg);
        }else
        if(parameterType == int.class || parameterType == Integer.class) {
            addArgValue(aim, parameterName, (Integer) arg);
        }else
        if(parameterType == long.class || parameterType == Long.class) {
            addArgValue(aim, parameterName, (Long) arg);
        }else
        if(parameterType == float.class || parameterType == Float.class) {
            addArgValue(aim, parameterName, (Float) arg);
        }else
        if(parameterType == double.class || parameterType == Double.class) {
            addArgValue(aim, parameterName, (Double) arg);
        }else
        if(parameterType == BigInteger.class) {
            addArgValue(aim, parameterName, (BigInteger) arg);
        }else
        if(parameterType == BigDecimal.class) {
            addArgValue(aim, parameterName, (BigDecimal) arg);
        }else
        if(parameterType == DateTime.class) {
            addArgValue(aim, parameterName, (DateTime) arg);
        }else
        if(parameterType == LocalDateTime.class) {
            addArgValue(aim, parameterName, (LocalDateTime) arg);
        }else
        if(parameterType == LocalDate.class) {
            addArgValue(aim, parameterName, (LocalDate) arg);
        }else
        if(parameterType == LocalTime.class) {
            addArgValue(aim, parameterName, (LocalTime) arg);
        }else
        {
            // none of the supported value types
            return false;
        }
        return true;
    }


    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final String argValue) {
        newParamDto(aim, parameterName, ParameterType.STRING, argValue).setString(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Byte argValue) {
        newParamDto(aim, parameterName, ParameterType.BYTE, argValue).setByte(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Short argValue) {
        newParamDto(aim, parameterName, ParameterType.SHORT, argValue).setShort(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Integer argValue) {
        newParamDto(aim, parameterName, ParameterType.INT, argValue).setInt(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Long argValue) {
        newParamDto(aim, parameterName, ParameterType.LONG, argValue).setLong(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Float argValue) {
        newParamDto(aim, parameterName, ParameterType.FLOAT, argValue).setFloat(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Double argValue) {
        newParamDto(aim, parameterName, ParameterType.DOUBLE, argValue).setDouble(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Boolean argValue) {
        newParamDto(aim, parameterName, ParameterType.BOOLEAN, argValue).setBoolean(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final Character argValue) {
        final ParamDto paramDto = newParamDto(aim, parameterName, ParameterType.CHAR, argValue);
        if(argValue != null) {
            paramDto.setChar(argValue + "");
        }
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final BigDecimal argValue) {
        newParamDto(aim, parameterName, ParameterType.BIG_DECIMAL, argValue).setBigDecimal(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final BigInteger argValue) {
        newParamDto(aim, parameterName, ParameterType.BIG_INTEGER, argValue).setBigInteger(argValue);
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final DateTime argValue) {
        newParamDto(aim, parameterName, ParameterType.JODA_DATE_TIME, argValue).setDateTime(JodaDateTimeXMLGregorianCalendarAdapter.print(argValue));
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final LocalDate argValue) {
        newParamDto(aim, parameterName, ParameterType.JODA_LOCAL_DATE, argValue).setLocalDate(JodaLocalDateXMLGregorianCalendarAdapter.print(argValue));
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final LocalDateTime argValue) {
        newParamDto(aim, parameterName, ParameterType.JODA_LOCAL_DATE_TIME, argValue).setLocalDateTime(JodaLocalDateTimeXMLGregorianCalendarAdapter.print(argValue));
    }

    public static void addArgValue(final ActionInvocationMementoDto aim, final String parameterName, final LocalTime argValue) {
        newParamDto(aim, parameterName, ParameterType.JODA_LOCAL_TIME, argValue).setLocalTime(JodaLocalTimeXMLGregorianCalendarAdapter.print(argValue));
    }

    public static void addArgReference(
            final ActionInvocationMementoDto aim,
            final String parameterName,
            final Bookmark reference) {
        OidDto argValue;
        if(reference != null) {
            argValue = new OidDto();
            argValue.setObjectType(reference.getObjectType());
            argValue.setObjectState(bookmarkObjectStateOf(reference));
            argValue.setObjectIdentifier(reference.getIdentifier());
        } else {
            argValue = null;
        }
        newParamDto(aim, parameterName, ParameterType.REFERENCE, argValue).setReference(argValue);
    }

    private static BookmarkObjectState bookmarkObjectStateOf(final Bookmark reference) {
        switch(reference.getObjectState()) {
        case PERSISTENT: return BookmarkObjectState.PERSISTENT;
        case TRANSIENT: return BookmarkObjectState.TRANSIENT;
        case VIEW_MODEL: return BookmarkObjectState.VIEW_MODEL;
        }
        throw new IllegalArgumentException(String.format("reference.objectState '%s' not recognized", reference.getObjectState()));
    }

    private static ParamDto newParamDto(
            final ActionInvocationMementoDto aim,
            final String parameterName,
            final ParameterType parameterType, final Object value) {
        final ActionInvocationMementoDto.Payload.Parameters params = getParametersHolderAutoCreate(aim);
        final ParamDto argDto = newParamDto(parameterName, parameterType);
        argDto.setNull(value == null);
        addArgValue(params, argDto);
        return argDto;
    }

    private static ParamDto newParamDto(final String parameterName, final ParameterType parameterType) {
        final ParamDto argDto = new ParamDto();
        argDto.setParameterName(parameterName);
        argDto.setParameterType(parameterType);
        return argDto;
    }

    //endregion

    //region > getNumberOfParameters, getParameters, getParameterNames, getParameterTypes
    public static int getNumberOfParameters(final ActionInvocationMementoDto aim) {
        final ActionInvocationMementoDto.Payload.Parameters params = getParametersHolderElseThrow(aim);
        if(params == null) {
            return 0;
        }
        return params.getNum();
    }
    public static List<ParamDto> getParameters(final ActionInvocationMementoDto aim) {
        final ActionInvocationMementoDto.Payload.Parameters params = getParametersHolderElseThrow(aim);
        final int parameterNumber = getNumberOfParameters(aim);
        final List<ParamDto> paramDtos = Lists.newArrayList();
        for (int i = 0; i < parameterNumber; i++) {
            final ParamDto paramDto = params.getParam().get(i);
            paramDtos.add(paramDto);
        }
        return Collections.unmodifiableList(paramDtos);
    }
    public static List<String> getParameterNames(final ActionInvocationMementoDto aim) {
        return immutableList(Iterables.transform(getParameters(aim), PARAM_DTO_TO_NAME));
    }
    public static List<ParameterType> getParameterTypes(final ActionInvocationMementoDto aim) {
        return immutableList(Iterables.transform(getParameters(aim), PARAM_DTO_TO_TYPE));
    }
    //endregion

    //region > getParameter, getParameterName, getParameterType
    public static ParamDto getParameter(final ActionInvocationMementoDto aim, final int paramNum) {
        final int parameterNumber = getNumberOfParameters(aim);
        if(paramNum > parameterNumber) {
            throw new IllegalArgumentException(String.format("No such parameter %d (the memento has %d parameters)", paramNum, parameterNumber));
        }
        final List<ParamDto> parameters = getParameters(aim);
        return parameters.get(paramNum);
    }
    public static String getParameterName(final ActionInvocationMementoDto aim, final int paramNum) {
        return PARAM_DTO_TO_NAME.apply(getParameter(aim, paramNum));
    }
    public static ParameterType getParameterType(final ActionInvocationMementoDto aim, final int paramNum) {
        return PARAM_DTO_TO_TYPE.apply(getParameter(aim, paramNum));
    }
    public static boolean isNull(final ActionInvocationMementoDto aim, int paramNum) {
        final ParamDto paramDto = getParameter(aim, paramNum);
        return paramDto.isNull();
    }
    //endregion

    //region > getArg
    public static <T> T getArg(final ActionInvocationMementoDto aim, int paramNum, Class<T> cls) {
        final ParamDto paramDto = getParameter(aim, paramNum);
        if(paramDto.isNull()) {
            return null;
        }
        switch(paramDto.getParameterType()) {
        case STRING:
            return (T) paramDto.getString();
        case BYTE:
            return (T) paramDto.getByte();
        case SHORT:
            return (T) paramDto.getShort();
        case INT:
            return (T) paramDto.getInt();
        case LONG:
            return (T) paramDto.getLong();
        case FLOAT:
            return (T) paramDto.getFloat();
        case DOUBLE:
            return (T) paramDto.getDouble();
        case BOOLEAN:
            return (T) paramDto.isBoolean();
        case CHAR:
            final String aChar = paramDto.getChar();
            if(Strings.isNullOrEmpty(aChar)) { return null; }
            return (T) (Object)aChar.charAt(0);
        case BIG_DECIMAL:
            return (T) paramDto.getBigDecimal();
        case BIG_INTEGER:
            return (T) paramDto.getBigInteger();
        case JODA_DATE_TIME:
            return (T) JodaDateTimeXMLGregorianCalendarAdapter.parse(paramDto.getDateTime());
        case JODA_LOCAL_DATE:
            return (T) JodaLocalDateXMLGregorianCalendarAdapter.parse(paramDto.getLocalDate());
        case JODA_LOCAL_DATE_TIME:
            return (T) JodaLocalDateTimeXMLGregorianCalendarAdapter.parse(paramDto.getLocalDateTime());
        case JODA_LOCAL_TIME:
            return (T) JodaLocalTimeXMLGregorianCalendarAdapter.parse(paramDto.getLocalTime());
        case REFERENCE:
            return (T) paramDto.getReference();
        }
        throw new IllegalStateException("Parameter type was not recognised (possible bug)");
    }
    //endregion

    //region > marshalling
    public static ActionInvocationMementoDto fromXml(Reader reader) {
        Unmarshaller un = null;
        try {
            un = getJaxbContext().createUnmarshaller();
            return (ActionInvocationMementoDto) un.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXml(final ActionInvocationMementoDto aim) {
        final CharArrayWriter caw = new CharArrayWriter();
        toXml(aim, caw);
        return caw.toString();
    }

    public static void toXml(final ActionInvocationMementoDto aim, final Writer writer) {
        Marshaller m = null;
        try {
            m = getJaxbContext().createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(aim, writer);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region > debugging
    public static void dump(final ActionInvocationMementoDto aim, final PrintStream out) throws JAXBException {
        out.println(toXml(aim));
    }
    //endregion

    //region > helpers
    private static void addArgValue(final ActionInvocationMementoDto.Payload.Parameters params, final ParamDto arg) {
        params.getParam().add(arg);
        Integer num = params.getNum();
        if(num == null) {
            num = 0;
        }
        params.setNum(num +1);
    }

    private static ActionInvocationMementoDto.Payload.Parameters getParametersHolderElseThrow(final ActionInvocationMementoDto aim) {
        final ActionInvocationMementoDto.Payload payload = getPayloadElseThrow(aim);
        final ActionInvocationMementoDto.Payload.Parameters parameters = payload.getParameters();
        if(parameters == null) {
            throw new IllegalStateException("No parameters have been added");
        }
        return parameters;
    }

    private static ActionInvocationMementoDto.Payload.Parameters getParametersHolderAutoCreate(final ActionInvocationMementoDto aim) {
        final ActionInvocationMementoDto.Payload payload = getPayloadAutoCreate(aim);
        ActionInvocationMementoDto.Payload.Parameters params = payload.getParameters();
        if(params == null) {
            params = new ActionInvocationMementoDto.Payload.Parameters();
            payload.setParameters(params);
        }
        return params;
    }

    private static ActionInvocationMementoDto.Payload getPayloadAutoCreate(final ActionInvocationMementoDto aim) {
        ActionInvocationMementoDto.Payload payload = aim.getPayload();
        if(payload == null) {
            payload = new ActionInvocationMementoDto.Payload();
            aim.setPayload(payload);
        }
        return payload;
    }

    private static ActionInvocationMementoDto.Payload getPayloadElseThrow(final ActionInvocationMementoDto aim) {
        ActionInvocationMementoDto.Payload payload = aim.getPayload();
        if(payload == null) {
            throw new IllegalStateException("No payload has been added");
        }
        return payload;
    }

    private static <T> List<T> immutableList(final Iterable<T> transform) {
        return Collections.unmodifiableList(
                Lists.newArrayList(
                        transform
                )
        );
    }

    //endregion



}
