package org.incode.module.docfragment.demo.module.fixture.invoices;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

public interface DemoData<DATA extends Enum<DATA>, DOMAIN_OBJECT> {
    DOMAIN_OBJECT asDomainObject();

    public static class Util {

        private Util(){}

        @Programmatic
        public static <DATA extends Enum<DATA>, DOMAIN_OBJECT> DOMAIN_OBJECT persist(
                final DemoData<DATA, DOMAIN_OBJECT> data,
                final RepositoryService repositoryService) {
            final DOMAIN_OBJECT domainObject = data.asDomainObject();
            repositoryService.persist(domainObject);
            return domainObject;
        }

        @Programmatic
        public static <DATA extends Enum<DATA>, DOMAIN_OBJECT> DOMAIN_OBJECT uniqueMatch(
                final DemoData<DATA, DOMAIN_OBJECT> data,
                final RepositoryService repositoryService) {
            final DOMAIN_OBJECT domainObject = data.asDomainObject();
            final Class<DOMAIN_OBJECT> domainClass = domainClassOf(data);
            return repositoryService.uniqueMatch(domainClass, x -> Objects.equals(x, domainObject));
        }

        @Programmatic
        public static <DATA extends Enum<DATA>, DOMAIN_OBJECT> DOMAIN_OBJECT firstMatch(
                final DemoData<DATA, DOMAIN_OBJECT> data,
                final RepositoryService repositoryService) {
            final DOMAIN_OBJECT domainObject = data.asDomainObject();
            final Class<DOMAIN_OBJECT> domainClass = domainClassOf(data);
            return repositoryService.firstMatch(domainClass, x -> Objects.equals(x, domainObject));
        }

        private static <DATA extends Enum<DATA>, DOMAIN_OBJECT> Class<DOMAIN_OBJECT> domainClassOf(final DemoData<DATA, DOMAIN_OBJECT> data) {
            final Class<? extends DemoData> aClass = data.getClass();
            final Type[] genericInterfaces = aClass.getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                final String typeName = genericInterface.getTypeName();
                if(typeName.startsWith(DemoData.class.getName() + "<")) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                    final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    return (Class<DOMAIN_OBJECT>) actualTypeArguments[1];
                }
            }
            throw new RuntimeException(String.format(
                    "Could not determine domainClass generic type of %s", aClass.getName()));
        }


    }
}
