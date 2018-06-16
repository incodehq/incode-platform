package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.ui;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.annotation.DomainService;

import org.incode.example.classification.dom.impl.category.Category;

@DomainService
public class CustomIconSubscriber extends Category.IconSubscriber {
    @EventHandler
    @Subscribe
    public void on(Category.IconUiEvent ev) {
        if (ev.getIconName() != null) {
            return;
        }
        ev.setIconName("Jodekoek.png");
    }
}
