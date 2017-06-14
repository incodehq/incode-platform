package org.isisaddons.wicket.pdfjs.app.services.homepage;

import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.scratchpad.Scratchpad;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;
import org.isisaddons.wicket.pdfjs.fixture.dom.demo.DemoObject;
import org.isisaddons.wicket.pdfjs.fixture.dom.demo.DemoObjectMenu;

@XmlRootElement(name = "toDoItemDto")
@XmlType(
        propOrder = {
                "idx"
        }
)public class HomePageViewModel {


    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CssHighlighter extends AbstractSubscriber {

        @EventHandler
        @Subscribe
        public void on(DemoObject.CssClassUiEvent ev) {
            if(getContext() == null) {
                return;
            }
            DemoObject selectedDemoObject = getContext().getSelected();
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

    public TranslatableString title() {
        // set during rendering
        cssHighlighter.setContext(this);
        return TranslatableString.tr("{cus} objects", "cus", getDemoObjects().size());
    }


    public List<DemoObject> getDemoObjects() {
        return demoObjectMenu.listAll();
    }


    @XmlTransient
    @PdfJsViewer(initialPageNum = 1, initialScale = Scale._1_00, initialHeight = 600)
    public Blob getBlob() {
        return getSelected() != null ? getSelected().getBlob() : null;
    }



    @XmlTransient
    @Property(hidden = Where.EVERYWHERE)
    public int getNumObjects() {
        return getDemoObjects().size();
    }


    private int idx;

    @XmlElement(required = true)
    @Property
    public int getIdx() {
        return idx;
    }

    public void setIdx(final int idx) {
        this.idx = idx;
    }


    @XmlTransient
    public DemoObject getSelected() {
        return getDemoObjects().get(getIdx());
    }


    public HomePageViewModel previous() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        viewModel.setIdx(getIdx()-1);
        return viewModel;
    }
    public String disablePrevious() {
        return getIdx() == 0 ? "At start" : null;
    }


    public HomePageViewModel next() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        viewModel.setIdx(getIdx()+1);
        return viewModel;
    }
    public String disableNext() {
        return getIdx() == getNumObjects() - 1 ? "At end" : null;
    }


    @javax.inject.Inject
    FactoryService factoryService;




    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

    @javax.inject.Inject
    CssHighlighter cssHighlighter;

}
