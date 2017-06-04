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
import java.util.Objects;

import javax.inject.Inject;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.urlencoding.UrlEncodingService;

import org.isisaddons.wicket.pdfjs.app.services.homepage.HomePageViewModel;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewerAdvisor;

@DomainService(nature = NatureOfService.DOMAIN)
public class PdfJsViewerAdvisorForDemoApp implements PdfJsViewerAdvisor {

    public static final Logger LOG = LoggerFactory.getLogger(PdfJsViewerAdvisorForDemoApp.class);

    // a more sophisticated implementation would use some sort of MRU/LRU cache.
    private final Map<InstanceKey.TypeKey, Advice.TypeAdvice> typeAdviceByTypeKey = Maps.newHashMap();

    // a more sophisticated implementation would use some sort of MRU/LRU cache.
    private final Map<InstanceKey, Integer> pageNumByInstanceKey = Maps.newHashMap();

    @Override
    public Advice advise(final InstanceKey instanceKey) {
        if(instanceKey == null) {
            return null;
        }
        Advice advice = adviceFor(instanceKey);
        dump("advise", instanceKey);
        return advice;
    }

    private Advice adviceFor(final InstanceKey instanceKey) {
        Integer pageNumber = pageNumByInstanceKey.get(instanceKey);
        if(pageNumber == null) {
            pageNumber = 1;
            pageNumByInstanceKey.put(instanceKey, pageNumber);
        }
        Advice.TypeAdvice typeAdvice = typeAdviceFor(instanceKey.getTypeKey());
        return new Advice(pageNumber, typeAdvice);
    }

    private Advice.TypeAdvice typeAdviceFor(final InstanceKey.TypeKey typeKey) {
        Advice.TypeAdvice typeAdvice = typeAdviceByTypeKey.get(typeKey);
        if(typeAdvice == null) {
            typeAdvice = new Advice.TypeAdvice(null, null);
            typeAdviceByTypeKey.put(typeKey, typeAdvice);
        }
        return typeAdvice;
    }

    @Override
    public void pageNumChangedTo(final InstanceKey instanceKey, final int pageNum) {
        pageNumByInstanceKey.put(instanceKey, pageNum);
        dump("pageNumChangedTo", instanceKey);
    }

    @Override
    public void scaleChangedTo(final InstanceKey instanceKey, final Scale scale) {
        InstanceKey.TypeKey typeKey = instanceKey.getTypeKey();
        Advice.TypeAdvice typeAdvice = typeAdviceFor(typeKey).withScale(scale);
        typeAdviceByTypeKey.put(typeKey, typeAdvice);
        dump("scaleChangedTo", instanceKey);
    }

    @Override
    public void heightChangedTo(final InstanceKey instanceKey, final int height) {
        InstanceKey.TypeKey typeKey = instanceKey.getTypeKey();
        Advice.TypeAdvice typeAdvice = typeAdviceFor(typeKey).withHeight(height);
        typeAdviceByTypeKey.put(typeKey, typeAdvice);
        dump("scaleChangedTo", instanceKey);
    }

    private void dump(final String method, final InstanceKey instanceKey) {
        LOG.debug("\n" + method + "(" + bookmarkFor(instanceKey) + "):\n");
        LOG.debug("  types:");
        for (InstanceKey.TypeKey key : typeAdviceByTypeKey.keySet()) {
            LOG.debug(String.format("    %s: %s", key.getObjectType(), typeAdviceByTypeKey.get(key)));
        }
        LOG.debug("  instances:");
        for (InstanceKey key : pageNumByInstanceKey.keySet()) {
            String idx = bookmarkFor(key);
            LOG.debug(String.format("    %s: %d", idx, pageNumByInstanceKey.get(key)));
        }
    }

    private String bookmarkFor(final InstanceKey instanceKey) {
        InstanceKey.TypeKey typeKey = instanceKey.getTypeKey();
        String objectType = typeKey.getObjectType();
        if(Objects.equals(
                objectType,
                "org.isisaddons.wicket.pdfjs.app.services.homepage.HomePageViewModel")) {

            String identifier = instanceKey.getIdentifier();
            final String xmlStr = urlEncodingService.decode(identifier);

            HomePageViewModel homePageViewModel = jaxbService.fromXml(HomePageViewModel.class, xmlStr);

            return "homePageViewModel:"+homePageViewModel.getIdx();
        } else {
            return instanceKey.asBookmark().toString();
        }
    }

    @Inject
    UrlEncodingService urlEncodingService;

    @Inject
    JaxbService jaxbService;
}
