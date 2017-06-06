/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
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

package org.incode.module.classification.fixture.dom.demo.subscriber;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.classification.dom.impl.category.Category;

public class CustomSubscriberForTesting {

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CustomTitleSubscriber extends Category.TitleSubscriber {
        @EventHandler
        @Subscribe
        public void on(Category.TitleUiEvent ev) {
            if (ev.getTitle() != null) {
                return;
            }
            ev.setTitle("Holtkamp");
        }

    }

    @DomainService
    public static class CustomIconSubscriber extends Category.IconSubscriber {
        @EventHandler
        @Subscribe
        public void on(Category.IconUiEvent ev) {
            if (ev.getIconName() != null) {
                return;
            }
            ev.setIconName("Jodekoek.png");
        }
    }

    @DomainService
    public static class CustomCssClassSubscriber extends Category.CssClassSubscriber {
        @EventHandler
        @Subscribe
        public void on(Category.CssClassUiEvent ev) {
            if (ev.getCssClass() != null) {
                return;
            }
            ev.setCssClass("Enkhuizer.css");
        }
    }
    //endregion

}
