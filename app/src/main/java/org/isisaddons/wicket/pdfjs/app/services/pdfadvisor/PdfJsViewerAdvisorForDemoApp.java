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
package org.isisaddons.wicket.pdfjs.app.services.pdfadvisor;

import java.util.Map;

import javax.inject.Inject;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pdfjs.PdfJsConfig;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.urlencoding.UrlEncodingService;

import org.isisaddons.wicket.pdfjs.app.services.homepage.HomePageViewModel;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerAdvisor;

@DomainService(nature = NatureOfService.DOMAIN)
public class PdfJsViewerAdvisorForDemoApp implements PdfJsViewerAdvisor {

    public static final Logger LOG = LoggerFactory.getLogger(PdfJsViewerAdvisorForDemoApp.class);

    // a more sophisticated implementation would use some sort of MRU/LRU cache.
    private final Map<PdfJsViewer.RenderKey, Advice> adviceByRenderKey = Maps.newHashMap();

    @Override
    public Advice advise(final PdfJsViewer.RenderKey renderKey) {
        // TODO: remove when done, shouldn't be required
        if(renderKey == null) {
            return null;
        }
        Advice advice = lookupElseCreate(renderKey);
        dump("advise", renderKey);
        return advice;
    }

    private Advice lookupElseCreate(final PdfJsViewer.RenderKey renderKey) {
        Advice advice = adviceByRenderKey.get(renderKey);
        if(advice == null) {
            advice = new Advice(null, null, null);
            adviceByRenderKey.put(renderKey, advice);
        }
        return advice;
    }

    @Override
    public void pageNumChangedTo(final PdfJsViewer.RenderKey renderKey, final int pageNum) {
        final Advice advice = lookupElseCreate(renderKey).withPageNum(pageNum);
        adviceByRenderKey.put(renderKey, advice);
        dump("pageNumChangedTo", renderKey);
    }

    @Override
    public void scaleChangedTo(final PdfJsViewer.RenderKey renderKey, final PdfJsConfig.Scale scale) {
        final Advice advice = lookupElseCreate(renderKey).withScale(scale);
        adviceByRenderKey.put(renderKey, advice);
        dump("scaleChangedTo", renderKey);
    }

    @Override
    public void heightChangedTo(final PdfJsViewer.RenderKey renderKey, final int height) {
        final Advice advice = lookupElseCreate(renderKey).withHeight(height);
        adviceByRenderKey.put(renderKey, advice);
        dump("heightChangedTo", renderKey);
    }

    private void dump(final String method, final PdfJsViewer.RenderKey forRenderKey) {
        LOG.debug(method + "(" + idxFor(forRenderKey) + "):\n");
        for (PdfJsViewer.RenderKey renderKey : adviceByRenderKey.keySet()) {
            int idx = idxFor(renderKey);
            LOG.debug(String.format("%d: %s", idx, adviceByRenderKey.get(renderKey)));
        }
    }

    private int idxFor(final PdfJsViewer.RenderKey renderKey) {
        Bookmark bookmark = renderKey.getBookmark();
        String identifier = bookmark.getIdentifier();
        final String xmlStr = urlEncodingService.decode(identifier);

        HomePageViewModel homePageViewModel = jaxbService.fromXml(HomePageViewModel.class, xmlStr);

        return homePageViewModel.getIdx();
    }

    @Inject
    UrlEncodingService urlEncodingService;

    @Inject
    JaxbService jaxbService;
}
