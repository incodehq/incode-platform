package org.incode.domainapp.example.dom.app.services.homepage;

import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.scratchpad.Scratchpad;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;

import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomer;
import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomerMenu;
import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomerRepository;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectRepository;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlob;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlobMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotesRepository;
import org.incode.domainapp.example.dom.demo.dom.demowithurl.DemoObjectWithUrl;
import org.incode.domainapp.example.dom.demo.dom.demowithurl.DemoObjectWithUrlMenu;
import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoice;
import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoiceRepository;
import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2;
import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2Repository;
import org.incode.domainapp.example.dom.demo.dom.other.OtherObject;
import org.incode.domainapp.example.dom.demo.dom.other.OtherObjectMenu;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;
import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Cases;
import org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannel;
import org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannels;
import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAssets;
import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Parties;
import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Party;
import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.dom.impl.DocFragmentRepository;

import lombok.Getter;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "org.incode.domainapp.example.dom.app.HomePageViewModel"
)
public class HomePageViewModel {


    // docfragment

    public List<DocFragment> getDocFragmentObjects() {
        return docfragmentRepository.listAll();
    }

    public List<DemoCustomer> getDemoCustomers() {
        return demoCustomerRepository.listAll();
    }

    public List<DemoInvoice> getDemoInvoices() {
        return demoInvoiceRepository.listAll();
    }

    @javax.inject.Inject
    DocFragmentRepository docfragmentRepository;

    @javax.inject.Inject
    DemoCustomerRepository demoCustomerRepository;

    @javax.inject.Inject
    DemoInvoiceRepository demoInvoiceRepository;



    // classification

    public List<DemoObjectWithAtPath> getDemoObjectsWithAtPath() {
        return demoObjectWithAtPathMenu.listAllDemoObjectsWithAtPath();
    }

    // communications

    public List<DemoObjectWithNotes> getDemoObjectWithNotes() {
        return demoObjectWithNotesRepository.listAll();
    }


    public List<DemoInvoice2> getDemoInvoice2s() {
        return demoInvoice2Repository.listAll();
    }

    @javax.inject.Inject
    DemoObjectWithNotesRepository demoObjectWithNotesRepository;

    @javax.inject.Inject
    DemoInvoice2Repository demoInvoice2Repository;



    // document

    public List<DemoObjectWithUrl> getDemoObjectsWithUrl() {
        return demoObjectWithUrlMenu.listAll();
    }

    public List<OtherObject> getOtherObjects() {
        return otherObjectMenu.listAll();
    }

    @javax.inject.Inject
    DemoObjectWithUrlMenu demoObjectWithUrlMenu;

    @javax.inject.Inject
    OtherObjectMenu otherObjectMenu;



    // poly

    public List<CommunicationChannel> getCommunicationChannels() {
        return communicationChannelsMenu.listAll();
    }

    public List<Party> getParties() {
        return parties.listAll();
    }

    public List<FixedAsset> getFixedAssets() {
        return fixedAssets.listAll();
    }

    public List<Case> getCases() {
        return cases.listAll();
    }

    @javax.inject.Inject
    CommunicationChannels communicationChannelsMenu;

    @javax.inject.Inject
    Parties parties;

    @javax.inject.Inject
    FixedAssets fixedAssets;

    @javax.inject.Inject
    Cases cases;



    // flyway
    public List<DemoObject> getObjects() {
        return demoObjectRepository.listAll();
    }


    @javax.inject.Inject
    DemoObjectRepository demoObjectRepository;


    // fullcalendar2

    public List<DemoToDoItem> getDemoToDoItemsNotYetComplete() {
        return demoToDoItemMenu.notYetCompleteNoUi();
    }


    @javax.inject.Inject
    DemoToDoItemMenu demoToDoItemMenu;



    // pdf.js

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CssHighlighter extends AbstractSubscriber {

        @EventHandler
        @Subscribe
        public void on(DemoObjectWithBlob.CssClassUiEvent ev) {
            if(getContext() == null) {
                return;
            }
            DemoObjectWithBlob selectedDemoObject = getContext().getSelected();
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


    public List<DemoObjectWithBlob> getDemoObjectsWithBlob() {
        return demoObjectMenu.listAllDemoObjectsWithBlob();
    }


    @PdfJsViewer(initialPageNum = 1, initialScale = Scale._1_00, initialHeight = 600)
    public Blob getBlob() {
        return getSelected() != null ? getSelected().getBlob() : null;
    }



    @Property(hidden = Where.EVERYWHERE)
    public int getNumObjects() {
        return getDemoObjectsWithBlob().size();
    }


    @Property
    @Getter @Setter
    private int idx;


    @XmlTransient
    public DemoObjectWithBlob getSelected() {
        return getDemoObjectsWithBlob().get(getIdx());
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
    DemoObjectWithBlobMenu demoObjectMenu;

    @javax.inject.Inject
    CssHighlighter cssHighlighter;





    ///////////

    @javax.inject.Inject
    DemoCustomerMenu demoCustomerMenu;

    @javax.inject.Inject
    DemoObjectWithAtPathMenu demoObjectWithAtPathMenu;

    @javax.inject.Inject
    DemoObjectWithBlobMenu demoObjectWithBlobMenu;



}
