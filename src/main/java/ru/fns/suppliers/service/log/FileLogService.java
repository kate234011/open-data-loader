package ru.fns.suppliers.service.log;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import ru.fns.suppliers.cdi.Log;
import ru.fns.suppliers.model.UnfairSuppliersLogDto;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.io.*;
import java.util.*;

@Log
@Alternative
@ApplicationScoped
public class FileLogService implements LogService {

    @ConfigProperty(name = "DefaultLogFolder")
    private String logFolder;

    private final Set<UnfairSuppliersLogDto> outFileSet;
    private final Set<String> fileHashSet;

    public FileLogService() {
        outFileSet = new HashSet<>();
        fileHashSet = new HashSet<>();
    }

    @Override
    public Set<String> readLog() {
        logger.info("logReader: read log-file");
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logFolder))) {
                while (true) {
                    UnfairSuppliersLogDto logFile = (UnfairSuppliersLogDto) ois.readObject();
                    fileHashSet.add(logFile.getMd5Hash());
                    logger.info("Log String was parsed. Number - " + fileHashSet.size());
                }
            } catch (Exception e) {
                logger.warn("Deserialization problem: " + e);
            }
        logger.info("logReader: log size - " + fileHashSet.size());
        return fileHashSet;
    }

    @Override
    public void writeLog() {
        logger.info("logWriter: write new files to log. New file quantity - " + outFileSet.size());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(logFolder, true))) {
            for (UnfairSuppliersLogDto outFile : outFileSet) {
                oos.writeObject(outFile);
            }
            oos.flush();
            outFileSet.clear();
        } catch (Exception e) {
            logger.warn("Serialization problem: " + e);
        }
    }

    @Override
    public void deleteLog() {
        logger.info("There must be code for flushing file directory!");
    }

    public void formOutList(UnfairSuppliersLogDto model) {
        if (model != null) {
            if (!fileHashSet.contains(model.getMd5Hash())) {
                outFileSet.add(model);
            }
        } else {
            logger.info("File is not read!");
        }
    }
}
