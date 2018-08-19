package org.incode.platformapp.appdefn.services.homepage;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.scratchpad.Scratchpad;

import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.dom.PdfJsDemoObjectWithBlob;

@DomainService(nature = NatureOfService.DOMAIN)
public class CssHighlighter extends AbstractSubscriber {

    @EventHandler
    @Subscribe
    public void on(PdfJsDemoObjectWithBlob.CssClassUiEvent ev) {
        if(getContext() == null) {
            return;
        }
        PdfJsDemoObjectWithBlob selectedDemoObject = getContext().getWktPdfjsSelected();
        if(ev.getSource() == selectedDemoObject) {
            ev.setCssClass("selected");
        }
    }

    private HomePageViewModel getContext() {
        return (HomePageViewModel) scratchpad.get("context");
    }
    void setContext(final HomePageViewModel homePageViewModel) {
        scratchpad.put("context", homePageViewModel);
    }

    @Inject
    Scratchpad scratchpad;
}
