package org.incode.module.minio.dopclient.archive;

import org.incode.module.minio.common.DomainObjectPropertyValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TypeValue {

    @Getter @Setter
    DomainObjectPropertyValue.Type value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
