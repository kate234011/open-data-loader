package ru.fns.suppliers.service.flusher;

import com.google.common.collect.Lists;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.cdi.Flush;
import ru.fns.suppliers.cdi.Log;
import ru.fns.suppliers.cdi.Reflection;
import ru.fns.suppliers.cdi.ReflectionScan;
import ru.fns.suppliers.minio.Minio;
import ru.fns.suppliers.service.log.LogService;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Flush
@ApplicationScoped
public class Flusher44FZ implements Flusher{

	Logger logger = LoggerFactory.getLogger(Flusher44FZ.class);

	@ConfigProperty(name = "minio.bucket-name")
	private String minioBucket;

	private final MinioClient minioClient;

	private final ReflectionScan scanner;

	private final LogService logService;

	public Flusher44FZ(
		@Minio MinioClient minioClient,
		@Reflection ReflectionScan scanner,
		@Log LogService logService
	) {
		this.minioClient = minioClient;
		this.scanner = scanner;
		this.logService = logService;
	}

	@Override
	public String cleanUpRepository(String lawType) {
		if(scanner.isLawExisted(lawType)){

			List<Result<Item>> objects = Lists.newArrayList(minioClient.listObjects(ListObjectsArgs.builder().bucket(minioBucket).build()));

			for (Result<Item> object : objects) {
				try {
					minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioBucket).object(object.get().objectName()).build());
				} catch (ErrorResponseException | InsufficientDataException | InternalException |
						InvalidKeyException | InvalidResponseException | IOException |
						NoSuchAlgorithmException | ServerException | XmlParserException e) {
					e.printStackTrace();
				}
			}

		} else {
			return "Specified in the request lawType data are not processed with application!";
		}
		return "MinIO directory was cleaned up. Application is ready for the next load.";
	}

	@Override
	public String flushRepository(String lawType) {

		cleanUpRepository(lawType);

		logService.deleteLog();

		return "All data were flushed. Application is ready for restart.";
	}
}
