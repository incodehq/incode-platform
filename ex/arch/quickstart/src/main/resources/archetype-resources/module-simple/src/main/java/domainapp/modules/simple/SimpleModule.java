#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package domainapp.modules.simple;

public final class SimpleModule {
    private SimpleModule(){}

    public static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {}
    public static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {}
    public static class ActionDomainEvent<S> extends
            org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {}
}
