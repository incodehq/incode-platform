package org.incode.module.fixturesupport.dom.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import org.apache.isis.applib.services.repository.RepositoryService;

/**
 *
 * @param <D> - the data object (implementing this interface)
 * @param <T> - the corresponding domain object type.
 */
public interface DemoData<D extends DemoData<D, T>, T> {
    T asDomainObject();
    T persistWith(final RepositoryService repositoryService);
    T findWith(final RepositoryService repositoryService);

    public static class Util {

        private Util(){}

        public static <D extends DemoData<D, T>, T> T persist(
                final DemoData<D, T> data,
                final RepositoryService repositoryService) {
            final T domainObject = data.asDomainObject();
            repositoryService.persist(domainObject);
            return domainObject;
        }

        public static <D extends DemoData<D, T>, T> T uniqueMatch(
                final DemoData<D, T> data,
                final RepositoryService repositoryService) {
            final T domainObject = data.asDomainObject();
            final Class<T> domainClass = domainClassOf(data);
            return repositoryService.uniqueMatch(domainClass, x -> Objects.equals(x, domainObject));
        }

        public static <D extends DemoData<D, T>, T> T firstMatch(
                final DemoData<D, T> data,
                final RepositoryService repositoryService) {
            final T domainObject = data.asDomainObject();
            final Class<T> domainClass = domainClassOf(data);
            return repositoryService.firstMatch(domainClass, x -> Objects.equals(x, domainObject));
        }

        public static <D extends DemoData<D, T>, T> T findMatch(
                final DemoData<D, T> data,
                final RepositoryService repositoryService) {
            return firstMatch(data, repositoryService);
        }

        public static <D extends DemoData<D, T>, T> Class<T> demoDataClassOf(final DemoData<D, T> data) {
            return genericType(data, 0, "demoDataClass");
        }

        public static <D extends DemoData<D, T>, T> Class<T> domainClassOf(final DemoData<D, T> data) {
            return genericType(data, 1, "domainClass");
        }

        private static <D extends DemoData<D, T>, T> Class<T> genericType(
                final DemoData<D, T> data,
                final int i,
                final String genericTypeName) {
            final Class<? extends DemoData> aClass = data.getClass();
            final Type[] genericInterfaces = aClass.getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                final String typeName = genericInterface.getTypeName();
                if(typeName.startsWith(DemoData.class.getName() + "<")) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                    final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    return (Class) actualTypeArguments[i];
                }
            }
            throw new RuntimeException(String.format(
                    "Could not determine %s generic type of %s", genericTypeName, aClass.getName()));
        }

    }
}
