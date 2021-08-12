package ru.fns.suppliers.service.scheduler;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.cdi.FTPLoad;
import ru.fns.suppliers.service.loader.Loader;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class LoadScheduler implements Scheduler {

	Logger logger = LoggerFactory.getLogger(LoadScheduler.class);

	@ConfigProperty(name = "request.law")
	private  String value;

	private final Loader loader;

	@Inject
	public LoadScheduler(@FTPLoad Loader loader) {
		this.loader = loader;
	}

	public void onStart(@Observes @Priority(Integer.MAX_VALUE)  StartupEvent ev) throws Exception {
		logger.info("The application is starting with value  - " + value);
		loader.sync(value);
	}
}
