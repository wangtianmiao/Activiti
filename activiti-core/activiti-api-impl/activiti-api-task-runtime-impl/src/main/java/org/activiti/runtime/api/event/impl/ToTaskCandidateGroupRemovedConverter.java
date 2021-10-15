/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.runtime.api.event.impl;

import static org.activiti.engine.task.IdentityLinkType.CANDIDATE;

import org.activiti.api.task.runtime.events.TaskCandidateGroupRemovedEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;

import java.util.Optional;

public class ToTaskCandidateGroupRemovedConverter
        implements EventConverter<TaskCandidateGroupRemovedEvent, ActivitiEntityEvent> {

    private APITaskCandidateGroupConverter converter;

    public ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter converter) {
        this.converter = converter;
    }

    @Override
    public Optional<TaskCandidateGroupRemovedEvent> from(ActivitiEntityEvent internalEvent) {
        TaskCandidateGroupRemovedEvent event = null;
        if (internalEvent.getEntity() instanceof IdentityLinkEntity) {
            IdentityLinkEntity entity = (IdentityLinkEntity) internalEvent.getEntity();
            if (isCandidateGroupEntity(entity)) {
                event = new TaskCandidateGroupRemovedImpl(converter.from(entity));
            }
        }
        return Optional.ofNullable(event);
    }

    private boolean isCandidateGroupEntity(IdentityLink identityLinkEntity) {
        return CANDIDATE.equalsIgnoreCase(identityLinkEntity.getType())
                && identityLinkEntity.getGroupId() != null;
    }
}
