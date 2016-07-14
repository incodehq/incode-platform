/*
 *
 *  Copyright 2015 incode.org
 *
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
package org.incode.module.alias.dom.spi.aliastype;

import org.incode.module.alias.dom.AliasModule;

/**
 * The type of an alias for an aliased domain object.  The combination of the
 * alias type and applicationTenancyPath</code> unique distinguish a particular alias reference.
 *
 * <p>
 *     For example, a party might one alias for ["AcctsRec", "/Italy"] and a different alias for ["AcctsPay", "/Italy"].
 *     In this, "AcctsRec" and "AcctsPay" are the alias types.
 * </p>
 */
public interface AliasType {

    String getId();

    abstract class PropertyDomainEvent<S,T> extends AliasModule.PropertyDomainEvent<S, T> { }
    abstract class CollectionDomainEvent<S,T> extends AliasModule.CollectionDomainEvent<S, T> { }
    abstract class ActionDomainEvent<S> extends AliasModule.ActionDomainEvent<S> { }

}
