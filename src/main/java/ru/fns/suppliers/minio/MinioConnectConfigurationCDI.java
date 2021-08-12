package ru.fns.suppliers.minio;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("Configuration")
@RegisterForReflection
@RequestScoped
public class MinioConnectConfigurationCDI implements MinioConnectConfiguration {

    @ConfigProperty(name = "quarkus.minio.url")
    private String minioEndpoint;

    @ConfigProperty(name = "quarkus.minio.access-key")
    private String minoAccessKey;

    @ConfigProperty(name = "quarkus.minio.secret-key")
    private String minoSecretKey;

    @Override
    public String endpoint() {
        return minioEndpoint;
    }

    @Override
    public String accessKey() {
        return minoAccessKey;
    }

    @Override
    public String secretKey() {
        return minoSecretKey;
    }

}
