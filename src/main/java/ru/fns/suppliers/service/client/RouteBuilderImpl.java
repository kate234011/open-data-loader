package ru.fns.suppliers.service.client;

import io.minio.MinioClient;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.minio.MinioConstants;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.model.UnfairSuppliersLogDto;
import ru.fns.suppliers.model.Path;
import ru.fns.suppliers.service.log.LogService;

import java.util.List;
import java.util.Set;

public class RouteBuilderImpl extends RouteBuilder {


    private final String uriTo;
    private final FTPHTTPClient ftpClient;
    private UnfairSuppliersLogDto unfairSuppliersLogDto;
    private final LogService logService;
    private final Set<String> loadedLogSet;
    private final List<Path> uriFromList;
    private final MinioClient minioClient;
    private final PollOncePollStrategy pollOnce;

    private final Logger logger = LoggerFactory.getLogger(RouteBuilderImpl.class);

    public RouteBuilderImpl(
            List<Path> uriFromList,
            String uriTo,
            Set<String> loadedLogSet,
            LogService logService,
            MinioClient minioClient,
            FTPHTTPClient ftpClient,
            PollOncePollStrategy pollOnce
    ) {
        this.uriFromList = uriFromList;
        this.uriTo = uriTo;
        this.loadedLogSet = loadedLogSet;
        this.logService = logService;
        this.minioClient = minioClient;
        this.ftpClient = ftpClient;
        this.pollOnce = pollOnce;
    }

    @Override
    public void configure() {

        for (Path paths : uriFromList) { // Список всех директорий
            List<String> uriPaths = paths.getListPath();

            for (String uriFrom : uriPaths) {

                from(uriFrom)
                        .filter(this::validateExchange)  // Валидируем хэш полученного файла с выгрузкой лога
                        .process(exchange -> {

                                    logger.info(exchange.getMessage().toString());

                                    exchange.getIn().setHeader(MinioConstants.OBJECT_NAME, exchange.getMessage().getHeader(Exchange.FILE_NAME_ONLY, String.class));
                                }
                        )
                        .to(uriTo)
                        .process(
                                exchange -> {

                                    UnfairSuppliersLogDto file = getFileLog();

                                    if (file != null) {
                                        logService.formOutList(file);    // Логируем загруженный архив
                                    }

                                }
                        )
                        .end();

            }
        }
        logService.writeLog();

    }

    private boolean validateExchange(Exchange exchange) {

        try {
            GenericFile bytes = exchange.getIn().getBody(GenericFile.class);

            if (bytes != null && !bytes.isDirectory()) {
                unfairSuppliersLogDto = new UnfairSuppliersLogDto(bytes.getFileName(), bytes.getLastModified());
            }

        } catch (IllegalArgumentException e) {
            logger.warn("No-File object was send in exchange !!!");
           // return false;
        }
        return (loadedLogSet.size() == 0) || !loadedLogSet.contains(unfairSuppliersLogDto.getMd5Hash());
    }

    public UnfairSuppliersLogDto getFileLog() {
        return unfairSuppliersLogDto;
    }
}
