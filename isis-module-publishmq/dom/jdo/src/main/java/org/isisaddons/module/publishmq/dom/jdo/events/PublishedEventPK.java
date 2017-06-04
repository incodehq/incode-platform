/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.publishmq.dom.jdo.events;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class PublishedEventPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEPARATOR = "_";

    @Getter @Setter
    public UUID transactionId;
    @Getter @Setter
    public int sequence;

    //region > constructor, toString (reciprocals of each other)
    public PublishedEventPK() {
    }
    
    public PublishedEventPK(final String value) {
        final StringTokenizer token = new StringTokenizer (value, SEPARATOR);
        this.transactionId = UUID.fromString(token.nextToken());
        this.sequence = Integer.parseInt(token.nextToken());
    }

    @Override
    public String toString() {
        return transactionId + SEPARATOR + sequence;
    }

    //endregion

    //region > hashCode, equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + sequence;
        result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PublishedEventPK other = (PublishedEventPK) obj;
        if (sequence != other.sequence)
            return false;
        if (transactionId == null) {
            if (other.transactionId != null)
                return false;
        } else if (!transactionId.equals(other.transactionId))
            return false;
        return true;
    }
    //endregion

}
