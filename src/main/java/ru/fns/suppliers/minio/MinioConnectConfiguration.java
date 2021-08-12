package ru.fns.suppliers.minio;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public interface MinioConnectConfiguration {
    String endpoint();

    String accessKey();

    String secretKey();
}
