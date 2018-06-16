package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.ui;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.classification.dom.impl.category.Category;

@DomainService(nature = NatureOfService.DOMAIN)
public class CustomTitleSubscriber extends Category.TitleSubscriber {
    @EventHandler
    @Subscribe
    public void on(Category.TitleUiEvent ev) {
        if (ev.getTitle() != null) {
            return;
        }
        ev.setTitle("Holtkamp");
    }

}
