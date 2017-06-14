package org.incode.module.commchannel.dom.impl.type;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;

public enum CommunicationChannelType {

    POSTAL_ADDRESS(PostalAddress.class),
    EMAIL_ADDRESS(EmailAddress.class),
    PHONE_NUMBER(PhoneOrFaxNumber.class),
    FAX_NUMBER(PhoneOrFaxNumber.class);

    private Class<? extends CommunicationChannel> cls;
    private CommunicationChannelType(final Class<? extends CommunicationChannel> cls) {
        this.cls = cls;
    }

    public String title() {
        return enumTitle(this);
    }
    
    public static List<CommunicationChannelType> matching(final Class<? extends CommunicationChannel> cls) {
        return Lists.newArrayList(Iterables.filter(Arrays.asList(values()), input -> input.cls == cls));
    }

    //region > helpers
    private static String enumTitle(final Enum<?> anEnum) {
        if(anEnum == null) {
            return null;
        }
        return Joiner.on(" ").join(Iterables.transform(Splitter.on("_").split(anEnum.toString()), LOWER_CASE_THEN_CAPITALIZE));
    }

    private static Function<String, String> LOWER_CASE_THEN_CAPITALIZE =
            input -> input != null? capitalize(input.toLowerCase()): null;

    public static String capitalize(final String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    //endregion

}
