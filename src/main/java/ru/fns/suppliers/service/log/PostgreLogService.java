package ru.fns.suppliers.service.log;


import ru.fns.suppliers.cdi.Log;
import ru.fns.suppliers.mappers.FileLogMapper;
import ru.fns.suppliers.model.UnfairSuppliersLogDto;
import ru.fns.suppliers.model.UnfairSuppliersLog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Log
@Default
@Transactional
@ApplicationScoped
public class PostgreLogService implements LogService {

	private Set<String> fileHashSet;
	private final FileLogMapper fileLogMapper;
	private final Set<UnfairSuppliersLog> outFileSet;
	private final EntityManager entityManager;

	@Inject
	public PostgreLogService(
			EntityManager entityManager,
			FileLogMapper fileLogMapper
	) {
		this.entityManager = entityManager;
		this.outFileSet = new HashSet<>();
		this.fileHashSet = new HashSet<>();
		this.fileLogMapper = fileLogMapper;
	}

	@Override
	public Set<String> readLog() {

		if (entityManager != null) {

			int logList  =entityManager.createNamedQuery("UnfairSuppliersLog.findAll", UnfairSuppliersLog.class).getMaxResults();

			logger.info("{} strings were parsed",logList);

			fileHashSet = entityManager.createNamedQuery("UnfairSuppliersLog.findAll", UnfairSuppliersLog.class)
					.getResultStream()
					.map(UnfairSuppliersLog::getSha256HexHash)
					.collect(Collectors.toSet());
		}


		logger.info("{} strings were parsed",fileHashSet.size());

		return fileHashSet;
	}

	@Override
	public void writeLog() {
		if (!outFileSet.isEmpty()) {
			logger.info("Start load data to PostgreSQL table. {} strings will be loaded",outFileSet.size());
			outFileSet.forEach(entityManager::persist);
		} else {
			logger.warn("There are no log-data for load.");
		}
	}

	@Override
	public void deleteLog() {
		logger.info("Clean up PostgreSQL table!");
		entityManager.clear();
	}

	public void formOutList(UnfairSuppliersLogDto model) {
		if (model != null) {
			if (!fileHashSet.contains(model.getMd5Hash())) {
				outFileSet.add(fileLogMapper.map(model));
			}
		}
	}
}
