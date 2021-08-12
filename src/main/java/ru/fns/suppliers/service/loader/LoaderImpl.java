package ru.fns.suppliers.service.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.cdi.*;
import ru.fns.suppliers.model.Path;
import ru.fns.suppliers.service.client.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.fns.suppliers.service.datasource.PathBuilder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@FTPLoad
@RequestScoped
public class LoaderImpl implements Loader {

	Logger logger = LoggerFactory.getLogger(LoaderImpl.class);

	private final PathBuilder pathBuilder;
	private final Client client;
	private final List<Path> srcDirectory;
	private Optional<Path> path;
	private final ReflectionScan scanner;

	@Inject
	LoaderImpl(
		@FTPClient Client client,
		@Protocol(name = LawType.FZ44) PathBuilder pathBuilder,
		@Reflection ReflectionScan scanner
	) {
		this.client = client;
		this.pathBuilder = pathBuilder;
		this.scanner = scanner;
		srcDirectory = new ArrayList<>();
	}

	@Override
	public String sync(String lawType) { // TODO Разобраться со списоком исключений, которые могли бы возникунть в процессе обработки роутов

		path = Optional.empty();

		if (scanner.isLawExisted(lawType)) {
			path = pathBuilder.lookUp();
			if (path.isPresent()) {
				srcDirectory.add(path.get());
				logger.info("Quantity of law's directions for download - " + srcDirectory.size());

				return client.load(srcDirectory);
			}
		}
		return "Fail load";
	}

}
