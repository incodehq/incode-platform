package org.incode.domainapp.extended.integtests.examples.alias.integration.spi.aliastype;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.alias.dom.spi.AliasType;
import org.incode.example.alias.dom.spi.AliasTypeRepository;

/**
 * Mandatory implementation of the {@link AliasTypeRepository} SPI.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class AliasTypeDemoEnumRepository implements AliasTypeRepository {

    @Override
    public Collection<AliasType> aliasTypesFor(
            final Object aliased, final String atPath) {
        final AliasTypeDemoEnum[] values = AliasTypeDemoEnum.values();
        return Lists.newArrayList(
                FluentIterable.from(Arrays.asList(values))
                .transform(x -> new AliasTypeViewModel(x))
        );
    }
}
