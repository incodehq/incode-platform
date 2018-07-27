package org.incode.module.minio.docclient.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class StringValue {

    @Getter @Setter
    String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
