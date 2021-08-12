package ru.fns.suppliers.service.client;

import io.minio.MinioClient;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.cdi.FTPClient;
import ru.fns.suppliers.cdi.Log;
import ru.fns.suppliers.cdi.Proxy;
import ru.fns.suppliers.minio.Minio;
import ru.fns.suppliers.model.Path;
import org.apache.camel.CamelContext;
import ru.fns.suppliers.service.log.LogService;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.*;

@FTPClient
@RequestScoped
public class CamelFTPClient implements Client {

    Logger logger = LoggerFactory.getLogger(CamelFTPClient.class);

    @Dependent
    private final CamelContext context;

    @Dependent
    private final LogService logService;

    @Dependent
    private final MinioClient minioClient;

    @ConfigProperty(name = "uriTo")
    private String uriTo;

    private FTPHTTPClient ftpClient;


    @Inject
    public CamelFTPClient(
            CamelContext context,
            @Log LogService logService,
            @Minio MinioClient minioClient,
            @Proxy FTPHTTPClient ftpClient

    ) {
        this.context = context;
        this.logService = logService;
        this.minioClient = minioClient;
        this.ftpClient = ftpClient;
    }

    @Override
    public String load(List<Path> pathList) {

        context.getPropertiesComponent().setLocation("classpath:application.properties");

        Set<String> loadedSet = logService.readLog();

        context.start();

        PollOncePollStrategy pollOnce = new PollOncePollStrategy(logService);
        context.getRegistry().bind("pollOnce", pollOnce);

        context.getRegistry().bind("ftpClient", ftpClient);

        RouteBuilderImpl routeBuilder = new RouteBuilderImpl(
                                                            pathList,
                                                            uriTo,
                                                            loadedSet,
                                                            logService,
                                                            minioClient,
                                                            ftpClient,
                                                            pollOnce);

        try {
            context.addRoutes(routeBuilder);
        } catch (Exception ex) {
            logger.error("Load was interrupted ! ", ex);
            return "Load was interrupted !";
        }

        return "Load was successfully started";
    }
}
