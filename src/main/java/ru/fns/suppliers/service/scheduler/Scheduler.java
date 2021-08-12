package ru.fns.suppliers.service.scheduler;

import io.quarkus.runtime.StartupEvent;

import javax.annotation.Priority;
import javax.enterprise.event.Observes;

public interface Scheduler {

	void onStart(@Observes @Priority(Integer.MAX_VALUE) StartupEvent ev) throws Exception;

}
