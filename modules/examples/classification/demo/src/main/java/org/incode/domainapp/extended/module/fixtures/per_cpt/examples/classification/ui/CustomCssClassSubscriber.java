package org.incode.example.alias.demo.examples.classification.ui;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.annotation.DomainService;

import org.incode.example.classification.dom.impl.category.Category;

@DomainService
public class CustomCssClassSubscriber extends Category.CssClassSubscriber {
    @EventHandler
    @Subscribe
    public void on(Category.CssClassUiEvent ev) {
        if (ev.getCssClass() != null) {
            return;
        }
        ev.setCssClass("Enkhuizer.css");
    }
}
