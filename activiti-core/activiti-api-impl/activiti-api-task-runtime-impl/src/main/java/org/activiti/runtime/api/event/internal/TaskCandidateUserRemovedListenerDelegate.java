/*
 * Copyright 2010-2022 Alfresco Software, Ltd.
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
package org.activiti.runtime.api.event.internal;

import org.activiti.api.task.runtime.events.TaskCandidateUserRemovedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.runtime.api.event.impl.ToTaskCandidateUserRemovedConverter;

import java.util.List;

public class TaskCandidateUserRemovedListenerDelegate implements ActivitiEventListener {

    private final List<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners;

    private final ToTaskCandidateUserRemovedConverter converter;

    public TaskCandidateUserRemovedListenerDelegate(List<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners,
                                                    ToTaskCandidateUserRemovedConverter converter) {
        this.listeners = listeners;
        this.converter = converter;
    }

    @Override
    public void onEvent(ActivitiEvent event) {
        if (event instanceof ActivitiEntityEvent) {
            converter.from((ActivitiEntityEvent) event).ifPresent(
                    convertedEvent -> {
                        for (TaskRuntimeEventListener<TaskCandidateUserRemovedEvent> listener : listeners) {
                            listener.onEvent(convertedEvent);
                        }
                    }
            );
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
