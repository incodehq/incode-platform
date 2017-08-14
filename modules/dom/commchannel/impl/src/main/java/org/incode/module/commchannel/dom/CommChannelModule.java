package org.incode.module.commchannel.dom;

public final class CommChannelModule {

    public static class JdoColumnLength {


        private JdoColumnLength(){}

        public static final int TYPE_ENUM = 30;
        public static final int PURPOSE = 30;

        /**
         * http://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
         */
        public static final int EMAIL_ADDRESS = 254;
        public static final int PHONE_NUMBER = 20;

        public static final int ADDRESS_LINE = 50;
        public static final int POSTAL_CODE = 12;
        public static final int COUNTRY = 50;

        public static final int FORMATTED_ADDRESS = 254;

        public static final int BOOKMARK = 2000;

    }

    public static class Regex {
        public static final String PHONE_NUMBER = "[+]?[0-9 -]*";

        private Regex(){}
        public static final String EMAIL_ADDRESS = "[^@ ]*@{1}[^@ ]*[.]+[^@ ]*";
    }


    private CommChannelModule(){}


    //region > ui event classes
    public abstract static class TitleUiEvent<S>
            extends org.apache.isis.applib.services.eventbus.TitleUiEvent<S> { }
    public abstract static class IconUiEvent<S>
            extends org.apache.isis.applib.services.eventbus.IconUiEvent<S> { }
    public abstract static class CssClassUiEvent<S>
            extends org.apache.isis.applib.services.eventbus.CssClassUiEvent<S> { }
    //endregion

    //region > domain event classes

    public abstract static class ActionDomainEvent<S>
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {}
    public abstract static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {}
    public abstract static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {}
    //endregion

}
