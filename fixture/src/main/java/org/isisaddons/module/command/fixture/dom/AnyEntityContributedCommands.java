/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.command.fixture.dom;

import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.objectstore.jdo.applib.service.background.BackgroundCommandServiceJdoRepository;
import org.apache.isis.objectstore.jdo.applib.service.command.CommandJdo;
import org.apache.isis.objectstore.jdo.applib.service.command.CommandServiceJdoRepository;

@DomainService
public class AnyEntityContributedCommands {

    @NotInServiceMenu
    @Render(Render.Type.EAGERLY)
    @NotContributed(NotContributed.As.ACTION) // ie contributed as collection
    @ActionSemantics(ActionSemantics.Of.SAFE)
    public List<CommandJdo> commands(Object entity) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(entity);
        return commandServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
    }

    public boolean hideCommands(Object entity) {
        // because CommandServiceContributions already contributes to HasTransactionId
        return entity instanceof HasTransactionId;
    }



    @Inject
    private BookmarkService bookmarkService;

    @Inject
    private CommandServiceJdoRepository commandServiceJdoRepository;

    @Inject
    private BackgroundCommandServiceJdoRepository backgroundCommandServiceJdoRepository;

}
