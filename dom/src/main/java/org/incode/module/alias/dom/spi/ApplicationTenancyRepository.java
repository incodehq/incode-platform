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
package org.incode.module.alias.dom.spi;

import java.util.Collection;

import org.incode.module.alias.dom.impl.Alias;

/**
 * Mandatory SPI service that returns the set of available application tenancy paths for a given aliased.
 *
 * <p>
 *     This is <i>not</i> the same as the application tenancy path of the aliased, rather it is those application
 *     tenancy paths that are available to then find alias types in order to set up an {@link Alias}.
 * </p>
 */
public interface ApplicationTenancyRepository {

    Collection<String> atPathsFor(final Object aliased);

}
