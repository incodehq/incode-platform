package org.isisaddons.wicket.pdfjs.cpt.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.common.io.Resources;

import org.apache.commons.io.Charsets;
import org.apache.wicket.Component;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceRequestHandler;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.pdfjs.PdfJsConfig;
import org.wicketstuff.pdfjs.PdfJsPanel;
import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.user.UserService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.scalars.ScalarPanelAbstract;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerAdvisor;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerFacet;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;

/**
 *
 */
class PdfJsViewerPanel extends ScalarPanelAbstract implements IResourceListener {

    private static final long serialVersionUID = 1L;

    private static final String ID_SCALAR_NAME = "scalarName";
    private static final String ID_SCALAR_VALUE = "scalarValue";
    private static final String ID_FEEDBACK = "feedback";

    AbstractDefaultAjaxBehavior updatePageNum;
    AbstractDefaultAjaxBehavior updateScale;
    AbstractDefaultAjaxBehavior updateHeight;

    String pdfJsViewerPanelCallbacksTemplateJs;

    PdfJsViewerPanel(String id, ScalarModel scalarModel) {
        super(id, scalarModel);

        final URL resource = Resources.getResource(PdfJsViewerPanel.class, "PdfJsViewerPanelCallbacks.template.js");
        try {
            pdfJsViewerPanelCallbacksTemplateJs = Resources.toString(resource, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    interface Updater{
        void update(PdfJsViewerAdvisor advisor, final PdfJsViewerAdvisor.InstanceKey instanceKey);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        // so we have a callback URL


        updatePageNum = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget _target)
            {
                String newPageNum = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("pageNum").toString();
                try {
                    final int pageNum = Integer.parseInt(newPageNum);
                    final Updater updater = new Updater() {
                        @Override
                        public void update(
                                final PdfJsViewerAdvisor advisor,
                                final PdfJsViewerAdvisor.InstanceKey renderKey) {
                            advisor.pageNumChangedTo(renderKey, pageNum);
                        }
                    };
                    updateAdvisors(updater);
                } catch(Exception ex) {
                    // ignore
                }
            }
        };

        updateScale = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget _target)
            {
                String newScale = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("scale").toString();
                try {
                    final Scale scale = Scale.forValue(newScale);
                    final Updater updater = new Updater() {
                        @Override
                        public void update(
                                final PdfJsViewerAdvisor advisor,
                                final PdfJsViewerAdvisor.InstanceKey renderKey) {
                            advisor.scaleChangedTo(renderKey, scale);
                        }
                    };
                    updateAdvisors(updater);
                } catch(Exception ex) {
                    // ignore
                }

            }
        };

        updateHeight = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget _target)
            {
                String newHeight = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("height").toString();
                try {
                    final int height = Integer.parseInt(newHeight);
                    final Updater updater = new Updater() {
                        @Override
                        public void update(
                                final PdfJsViewerAdvisor advisor,
                                final PdfJsViewerAdvisor.InstanceKey renderKey) {
                            advisor.heightChangedTo(renderKey, height);
                        }
                    };
                    updateAdvisors(updater);
                } catch(Exception ex) {
                    // ignore
                }
            }

        };

        add(updatePageNum, updateScale, updateHeight);
    }

    private void updateAdvisors(final Updater updater) {
        PdfJsViewerAdvisor.InstanceKey instanceKey = buildKey();
        List<PdfJsViewerAdvisor> advisors = getServicesInjector().lookupServices(PdfJsViewerAdvisor.class);
        if(advisors != null) {
            for (final PdfJsViewerAdvisor advisor : advisors) {
                updater.update(advisor, instanceKey);
            }
        }
    }

    private PdfJsViewerAdvisor.InstanceKey buildKey() {
        UserService userService = getServicesInjector().lookupService(UserService.class);
        String userName = userService.getUser().getName();

        ScalarModel model = getModel();
        String propertyId = model.getPropertyMemento().getIdentifier();
        Bookmark bookmark = model.getParentEntityModel().getObjectAdapterMemento().asBookmark();
        String objectType = bookmark.getObjectType();
        String identifier = bookmark.getIdentifier();

        return new PdfJsViewerAdvisor.InstanceKey(objectType, identifier, propertyId, userName);
    }

    @Override
    protected MarkupContainer addComponentForRegular() {

        MarkupContainer containerIfRegular = new WebMarkupContainer("scalarIfRegular");
        addOrReplace(containerIfRegular);

        final ObjectAdapter adapter = scalarModel.getObject();
        if (adapter != null) {
            final PdfJsViewerFacet pdfJsViewerFacet = scalarModel.getFacet(PdfJsViewerFacet.class);
            final PdfJsViewerAdvisor.InstanceKey instanceKey = buildKey();
            final PdfJsConfig config = pdfJsViewerFacet != null ? pdfJsViewerFacet.configFor(instanceKey) : new PdfJsConfig();
            config.withDocumentUrl(urlFor(IResourceListener.INTERFACE, null));
            PdfJsPanel pdfJsPanel = new PdfJsPanel(ID_SCALAR_VALUE, config);

            MarkupContainer prevPageButton = createComponent("prevPage", config);
            MarkupContainer nextPageButton = createComponent("nextPage", config);
            MarkupContainer currentZoomSelect = createComponent("currentZoom", config);
            MarkupContainer currentPageLabel = createComponent("currentPage", config);
            MarkupContainer totalPagesLabel = createComponent("totalPages", config);

            MarkupContainer currentHeightSelect = createComponent("currentHeight", config);
            MarkupContainer printButton = createComponent("print", config);

            //MarkupContainer downloadButton = createComponent("download", config);

            final Blob blob = getBlob();
            final IResource bar = new ByteArrayResource(blob.getMimeType().getBaseType(), blob.getBytes(), blob.getName());
            final ResourceLink<Void> downloadLink = new ResourceLink<>("download", bar);

            containerIfRegular.addOrReplace(
                    pdfJsPanel, prevPageButton, nextPageButton, currentPageLabel, totalPagesLabel,
                    currentZoomSelect, currentHeightSelect, printButton, downloadLink);


//            Label fileNameIfCompact = new Label("fileNameIfCompact", blob.getName());
//            downloadLink.add(fileNameIfCompact);


            containerIfRegular.addOrReplace(new NotificationPanel(ID_FEEDBACK, pdfJsPanel, new ComponentFeedbackMessageFilter(pdfJsPanel)));
        } else {
            permanentlyHide(ID_SCALAR_VALUE, ID_FEEDBACK);
        }

        return containerIfRegular;
    }

    @Override
    protected Component addComponentForCompact() {
        final Blob blob = getBlob();
        if (blob == null) {
            return null;
        }
        WebMarkupContainer containerIfCompact = new WebMarkupContainer("scalarIfCompact");
        addOrReplace(containerIfCompact);

        final IResource bar = new ByteArrayResource(blob.getMimeType().getBaseType(), blob.getBytes(), blob.getName());
        final ResourceLink<Void> downloadLink = new ResourceLink<>("scalarIfCompactDownload", bar);
        containerIfCompact.add(downloadLink);

        Label fileNameIfCompact = new Label("fileNameIfCompact", blob.getName());
        downloadLink.add(fileNameIfCompact);

        return containerIfCompact;
    }

    private MarkupContainer createComponent(final String id, final PdfJsConfig config) {
        return new WebMarkupContainer(id) {
            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("data-canvas-id", config.getCanvasId());
            }
        };
    }

    @Override
    protected void addFormComponentBehavior(final Behavior behavior) {
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(CssHeaderItem.forReference(new CssResourceReference(PdfJsViewerPanel.class, "PdfJsViewerPanel.css")));
        response.render(JavaScriptHeaderItem.forReference(new PdfJsViewerReference()));

         renderFunctionsForUpdateCallbacks(response);
    }

    private void renderFunctionsForUpdateCallbacks(final IHeaderResponse response) {

        String script = pdfJsViewerPanelCallbacksTemplateJs
                .replace("__updatePageNum_getCallbackUrl()__", updatePageNum.getCallbackUrl())
                .replace("__updateScale_getCallbackUrl()__", updateScale.getCallbackUrl())
                .replace("__updateHeight_getCallbackUrl()__", updateHeight.getCallbackUrl());

        response.render(JavaScriptHeaderItem.forScript(script, "pdfJsViewerCallbacks"));
    }

    @Override
    public void onResourceRequested() {
        Blob pdfBlob = getBlob();
        if (pdfBlob != null) {
            final byte[] bytes = pdfBlob.getBytes();
            final ByteArrayResource resource = new ByteArrayResource("application/pdf", bytes) {
                @Override protected void configureResponse(
                        final ResourceResponse response, final Attributes attributes) {
                    super.configureResponse(response, attributes);
                    response.setCacheDuration(Duration.NONE);
                }
            };
            final ResourceRequestHandler handler = new ResourceRequestHandler(resource, null);
            getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
        } else {
            throw new AbortWithHttpErrorCodeException(404);
        }
    }

    private Blob getBlob() {
        Blob pdfBlob = null;
        final ObjectAdapter adapter = getModel().getObject();
        if (adapter != null) {
            pdfBlob = (Blob) adapter.getObject();
        }
        return pdfBlob;
    }

}
