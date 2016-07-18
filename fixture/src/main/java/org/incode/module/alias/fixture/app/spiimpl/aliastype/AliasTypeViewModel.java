/*
 *  Copyright 2014 Dan Haywood
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
package org.incode.module.alias.fixture.app.spiimpl.aliastype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.isis.applib.annotation.Title;

import org.incode.module.alias.dom.spi.AliasType;

/**
 * In a real (not demo) application this would probably be an entity.
 *
 * <p>
 *     Can't just use the enum because - it seems - the framework doesn't pick up on enums implementing interfaces...
 * </p>
 */
@XmlRootElement
public class AliasTypeViewModel implements AliasType {

    @XmlElement
    private AliasTypeDemoEnum aliasTypeDemoEnum;

    public AliasTypeViewModel() {
    }

    public AliasTypeViewModel(final AliasTypeDemoEnum aliasTypeDemoEnum) {
        this.aliasTypeDemoEnum = aliasTypeDemoEnum;
    }

    @Title
    @XmlTransient
    @Override public String getId() {
        return aliasTypeDemoEnum.getId();
    }

}

