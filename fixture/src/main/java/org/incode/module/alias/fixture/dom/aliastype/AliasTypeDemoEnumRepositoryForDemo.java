package org.incode.module.alias.fixture.dom.aliastype;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.alias.dom.api.aliasable.AliasType;
import org.incode.module.alias.dom.api.aliasable.Aliasable;
import org.incode.module.alias.dom.spi.aliastype.AliasTypeRepository;
import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasTypeDemoEnum;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class AliasTypeDemoEnumRepositoryForDemo implements AliasTypeRepository {

    @Override
    public Collection<AliasType> aliasTypesFor(
            final Aliasable aliasable, final String atPath) {
        final AliasTypeDemoEnum[] values = AliasTypeDemoEnum.values();
        return Arrays.asList(values);
    }

    @Override
    public AliasType lookup(final String aliasTypeId) {
        final AliasTypeDemoEnum[] values = AliasTypeDemoEnum.values();
        for (AliasTypeDemoEnum value : values) {
            if(Objects.equals(value.getId(), aliasTypeId)) {
                return value;
            }
        }
        return null;
    }
}
