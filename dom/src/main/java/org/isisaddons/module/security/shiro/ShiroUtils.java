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
package org.isisaddons.module.security.shiro;

import java.util.Collection;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;

public final class ShiroUtils {

    private ShiroUtils() {
    }

    public static synchronized RealmSecurityManager getSecurityManager() {
        org.apache.shiro.mgt.SecurityManager securityManager;
        try {
            securityManager = SecurityUtils.getSecurityManager();
        } catch(UnavailableSecurityManagerException ex) {
            throw new AuthenticationException(ex);
        }
        if(!(securityManager instanceof RealmSecurityManager)) {
            throw new AuthenticationException();
        }
        return (RealmSecurityManager) securityManager;
    }

    public static IsisModuleSecurityRealm getIsisModuleSecurityRealm() {
        final RealmSecurityManager securityManager = getSecurityManager();
        final Collection<Realm> realms = securityManager.getRealms();
        for (Realm realm : realms) {
            if(realm instanceof IsisModuleSecurityRealm) {
                IsisModuleSecurityRealm imsr = (IsisModuleSecurityRealm) realm;
                return imsr;
            }
        }
        return null;
    }
    
}