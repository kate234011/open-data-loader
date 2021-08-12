package ru.fns.suppliers.service.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.model.UnfairSuppliersLogDto;
import java.util.HashSet;
import java.util.Set;

public interface LogService {

    Logger logger = LoggerFactory.getLogger(LogService.class);

    Set<String> fileHashSet = new HashSet<String>();
    
    Set<String> readLog();
    
    void writeLog();

    void formOutList(UnfairSuppliersLogDto model);

    void deleteLog();

}
