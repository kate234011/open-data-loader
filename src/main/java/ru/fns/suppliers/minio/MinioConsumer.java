package ru.fns.suppliers.minio;

import io.minio.MinioClient;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@RequestScoped
public class MinioConsumer {

    @Inject
    MinioConnectConfiguration connectConfiguration;

    @Produces
    @Minio
    public MinioClient minioClient() {
        MinioClient.Builder client = MinioClient.builder()
                .endpoint(connectConfiguration.endpoint())
                .credentials(connectConfiguration.accessKey(), connectConfiguration.secretKey());

        return client.build();
    }
}
